/**
 * GitHub Time Machine — Shared Frontend Utilities
 * Single Source of Truth for async helpers, sanitization, and formatting.
 */

(function (window) {
  'use strict';

  const Utils = {
    /**
     * Async sleep delay
     * @param {number} ms 
     * @returns {Promise<void>}
     */
    sleep(ms) {
      return new Promise((resolve) => setTimeout(resolve, ms));
    },

    /**
     * Escape HTML strings to prevent XSS injection
     * @param {string} str 
     * @returns {string}
     */
    escapeHtml(str) {
      if (!str) return '';
      return String(str)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#039;');
    },

    /**
     * Format numbers with commas (e.g. 224150 -> 224,150)
     * @param {number} num 
     * @returns {string}
     */
    formatNumber(num) {
      if (num === null || num === undefined) return '0';
      return new Intl.NumberFormat('en-US').format(num);
    },

    /**
     * Safely query DOM element
     * @param {string} selector 
     * @returns {HTMLElement|null}
     */
    $(selector) {
      return document.querySelector(selector);
    },

    /**
     * Safely query all matching DOM elements
     * @param {string} selector 
     * @returns {NodeListOf<HTMLElement>}
     */
    $$(selector) {
      return document.querySelectorAll(selector);
    }
  };

  window.Utils = Utils;
})(window);
