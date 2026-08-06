package com.githubtimemachine.util;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtils {

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private DateTimeUtils() {
        // Utility class constructor
    }

    public static String formatIso(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(ISO_FORMATTER) : null;
    }

    public static long toEpochMillis(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toInstant(ZoneOffset.UTC).toEpochMilli() : 0L;
    }

    public static int calculateYearsBetween(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) return 0;
        return Period.between(startDate.toLocalDate(), endDate.toLocalDate()).getYears();
    }
}
