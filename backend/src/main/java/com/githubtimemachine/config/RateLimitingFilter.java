package com.githubtimemachine.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Order(1)
public class RateLimitingFilter implements Filter {

    private static final int MAX_REQUESTS_PER_MINUTE = 120;
    private static final long CLEANUP_INTERVAL_MS = 300000; // 5 Minutes
    private final Map<String, RequestCounter> requestCounts = new ConcurrentHashMap<>();
    private long lastCleanupTime = System.currentTimeMillis();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String clientIp = getClientIP(httpRequest);
        long now = System.currentTimeMillis();

        // Memory leak prevention: Periodically evict stale counters
        if (now - lastCleanupTime > CLEANUP_INTERVAL_MS) {
            evictStaleEntries(now);
            lastCleanupTime = now;
        }

        RequestCounter counter = requestCounts.compute(clientIp, (ip, existingCounter) -> {
            if (existingCounter == null || now - existingCounter.startTime > 60000) {
                return new RequestCounter(now, new AtomicInteger(1));
            } else {
                existingCounter.count.incrementAndGet();
                return existingCounter;
            }
        });

        if (counter.count.get() > MAX_REQUESTS_PER_MINUTE) {
            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"success\":false,\"message\":\"Rate limit exceeded. Please try again later.\"}");
            return;
        }

        chain.doFilter(request, response);
    }

    private void evictStaleEntries(long currentTime) {
        requestCounts.entrySet().removeIf(entry -> currentTime - entry.getValue().startTime > 120000);
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isBlank()) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0].trim();
    }

    private static class RequestCounter {
        final long startTime;
        final AtomicInteger count;

        RequestCounter(long startTime, AtomicInteger count) {
            this.startTime = startTime;
            this.count = count;
        }
    }
}
