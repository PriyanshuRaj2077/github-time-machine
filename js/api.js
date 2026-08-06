/**
 * GitHub Time Machine - API Layer
 * Optimized with client-side 5-minute TTL memory caching, AbortController timeouts, and zero redundant requests.
 */

(function (window) {
  'use strict';

  const API_BASE =
    window.location.hostname === 'localhost'
      ? 'http://localhost:8080'
      : 'https://github-time-machine-7s7f.onrender.com';
  const DEFAULT_TIMEOUT_MS = 10000;
  const CACHE_TTL_MS = 300000; // 5-minute client-side memory cache TTL

  // Client-Side In-Memory Cache Store
  const requestCache = new Map();

  const ApiService = {
    /**
     * Optimized request wrapper with Client Caching, Auth headers & AbortController timeout
     */
    async _request(endpoint, options = {}) {
      const method = options.method || 'GET';
      const now = Date.now();

      // Performance Optimization: Check in-memory client cache first for GET requests
      if (method === 'GET' && requestCache.has(endpoint)) {
        const cached = requestCache.get(endpoint);
        if (now - cached.timestamp < CACHE_TTL_MS) {
          return cached.data;
        } else {
          requestCache.delete(endpoint);
        }
      }

      const controller = new AbortController();
      const timeoutId = setTimeout(() => controller.abort(), DEFAULT_TIMEOUT_MS);

      const headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      };

      const token = localStorage.getItem('gtm_auth_token');
      if (token) {
        headers['Authorization'] = `Bearer ${token}`;
      }

      try {
        const fetchOptions = {
          method,
          headers,
          signal: controller.signal
        };

        if (options.body) {
          fetchOptions.body = JSON.stringify(options.body);
        }

        const response = await fetch(`${API_BASE}${endpoint}`, fetchOptions);

        clearTimeout(timeoutId);

        if (response.status === 429) {
          throw new Error('Backend API rate limit exceeded. Please wait a moment.');
        }

        if (!response.ok) {
          throw new Error(`Backend HTTP Error (${response.status})`);
        }

        const data = await response.json();
        const payload = (data && data.data !== undefined) ? data.data : data;

        // Cache successful GET response
        if (method === 'GET') {
          requestCache.set(endpoint, {
            timestamp: now,
            data: payload
          });
        }

        return payload;
      } catch (err) {
        clearTimeout(timeoutId);
        throw new Error(err.message || 'Unable to connect to backend server');
      }
    },

    async analyze(usernameOrRepo) {
      if (!usernameOrRepo || !usernameOrRepo.trim()) {
        throw new Error('Target query cannot be empty.');
      }
      const cleanTarget = encodeURIComponent(usernameOrRepo.trim());
      return await this._request(`/api/analyze/${cleanTarget}`);
    },

    async getProfile(username) {
      if (!username) throw new Error('Username is required');
      return await this._request(`/api/profile/${encodeURIComponent(username.trim())}`);
    },

    async getRepositories(username) {
      if (!username) return [];
      return await this._request(`/api/repositories/${encodeURIComponent(username.trim())}`);
    },

    async getTimeline(username) {
      return await this._request(`/api/timeline/${encodeURIComponent((username || 'developer').trim())}`);
    },

    async getReplay(username) {
      return await this._request(`/api/replay/${encodeURIComponent((username || 'developer').trim())}`);
    },

    async getWrapped(username) {
      return await this._request(`/api/wrapped/${encodeURIComponent((username || 'developer').trim())}`);
    },

    async getInsights(username) {
      return await this._request(`/api/insights/${encodeURIComponent((username || 'developer').trim())}`);
    },

    async getAuthUrl() {
      const res = await this._request('/api/auth/github/url');
      const url = (res && typeof res === 'object') ? (res.url || (res.data && res.data.url)) : res;
      console.log('[ApiService] Retrieved GitHub Auth URL:', url);
      return url;
    },

    async handleOAuthCallback(code) {
      console.log('[ApiService] Initiating OAuth code exchange with backend. Code:', code ? code.substring(0, 8) + '...' : 'null');

      const response = await this._request('/api/auth/github/callback', {
        method: 'POST',
        body: { code: (code || '').trim() }
      });

      console.log('[ApiService] Backend OAuth exchange raw response:', response);

      let token = null;
      let user = null;

      if (response) {
        if (response.data && response.data.token) {
          token = response.data.token;
          user = response.data.user;
        } else if (response.token) {
          token = response.token;
          user = response.user;
        }
      }

      if (token) localStorage.setItem('gtm_auth_token', token);
      if (user) localStorage.setItem('gtm_auth_user', JSON.stringify(user));

      return { token, user, data: { token, user } };
    },

    async getCurrentUser() {
      const cached = localStorage.getItem('gtm_auth_user');
      let cachedUser = null;
      if (cached) {
        try { cachedUser = JSON.parse(cached); } catch (e) {}
      }

      try {
        const res = await this._request('/api/auth/me');
        const user = (res && res.username) ? res : (res && res.data ? res.data : cachedUser);
        if (user) {
          localStorage.setItem('gtm_auth_user', JSON.stringify(user));
        }
        return user;
      } catch (err) {
        return cachedUser;
      }
    },

    async getUserHistory() {
      return await this._request('/api/user/history');
    },

    async getAdminDashboard() {
      return await this._request('/api/admin/dashboard');
    },

    async getAdminUsers() {
      return await this._request('/api/admin/users');
    },

    async getAdminAnalytics() {
      return await this._request('/api/admin/analytics');
    },

    async getAdminSystem() {
      return await this._request('/api/admin/system');
    },

    logout() {
      localStorage.removeItem('gtm_auth_token');
      localStorage.removeItem('gtm_auth_user');
      window.location.href = window.location.pathname;
    }
  };

  window.ApiService = ApiService;
})(typeof window !== 'undefined' ? window : this);
