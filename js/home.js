/**
 * GitHub Time Machine - Home Experience Module
 * Hardened input validation and terminal error messaging.
 */

(function (window) {
  'use strict';

  const HomeModule = {
    elements: {},
    state: {
      currentMode: 'public',
      introFinished: false,
      activeTimer: null
    },

    init() {
      this.cacheDOM();
      this.bindEvents();
      this.startIntroSequence();
    },

    cacheDOM() {
      const U = window.Utils;
      this.elements.introOverlay = U ? U.$('#intro-overlay') : document.getElementById('intro-overlay');
      this.elements.introTextEl = U ? U.$('#intro-text') : document.getElementById('intro-text');
      this.elements.terminalWrapper = U ? U.$('#terminal-wrapper') : document.getElementById('terminal-wrapper');
      this.elements.terminalForm = U ? U.$('#terminal-form') : document.getElementById('terminal-form');
      this.elements.usernameInput = U ? U.$('#username-input') : document.getElementById('username-input');
      this.elements.modeItems = U ? U.$$('.mode-item') : document.querySelectorAll('.mode-item');
      this.elements.quickTags = U ? U.$$('.quick-tag') : document.querySelectorAll('.quick-tag');
      this.elements.btnSubmit = U ? U.$('#btn-submit') : document.getElementById('btn-submit');
      this.elements.btnOAuth = U ? U.$('#btn-github-oauth') : document.getElementById('btn-github-oauth');
    },

    bindEvents() {
      if (this.elements.modeItems) {
        this.elements.modeItems.forEach(item => {
          item.addEventListener('click', () => {
            const mode = item.getAttribute('data-mode');
            this.setMode(mode);
          });
        });
      }

      if (this.elements.quickTags) {
        this.elements.quickTags.forEach(tag => {
          tag.addEventListener('click', () => {
            const val = tag.getAttribute('data-val');
            if (this.elements.usernameInput) {
              this.elements.usernameInput.value = val;
              this.elements.usernameInput.focus();
            }
          });
        });
      }

      if (this.elements.terminalForm) {
        this.elements.terminalForm.addEventListener('submit', (e) => {
          e.preventDefault();
          this.handleExecute();
        });
      }

      if (this.elements.btnOAuth) {
        this.elements.btnOAuth.addEventListener('click', () => {
          this.handleOAuthLogin();
        });
      }

      if (this.elements.introOverlay) {
        this.elements.introOverlay.addEventListener('click', () => {
          if (!this.state.introFinished) {
            this.skipIntro();
          }
        });
      }
    },

    async startIntroSequence() {
      const U = window.Utils;
      const sleepFn = U ? U.sleep : (ms) => new Promise(r => setTimeout(r, ms));

      await sleepFn(1000);
      if (this.state.introFinished) return;

      await this.typeWriter("Welcome to GitHub Time Machine", 40);
      if (this.state.introFinished) return;
      await sleepFn(1200);
      if (this.state.introFinished) return;

      await this.fadeOutIntroText();
      if (this.state.introFinished) return;

      await this.typeWriter("Ready to Time Travel?", 50);
      if (this.state.introFinished) return;
      await sleepFn(1200);
      if (this.state.introFinished) return;

      this.finishIntro();
    },

    skipIntro() {
      if (this.state.activeTimer) {
        clearInterval(this.state.activeTimer);
        this.state.activeTimer = null;
      }
      this.state.introFinished = true;
      this.finishIntro();
    },

    finishIntro() {
      this.state.introFinished = true;
      if (this.state.activeTimer) {
        clearInterval(this.state.activeTimer);
        this.state.activeTimer = null;
      }

      if (this.elements.introOverlay) {
        this.elements.introOverlay.classList.add('fade-out');
        setTimeout(() => {
          if (this.elements.introOverlay) this.elements.introOverlay.style.display = 'none';
        }, 800);
      }

      if (this.elements.terminalWrapper) {
        this.elements.terminalWrapper.classList.remove('hidden');
      }

      if (this.elements.usernameInput) {
        setTimeout(() => {
          if (this.elements.usernameInput) this.elements.usernameInput.focus();
        }, 600);
      }
    },

    typeWriter(text, speed = 40) {
      return new Promise((resolve) => {
        if (!this.elements.introTextEl) return resolve();
        this.elements.introTextEl.textContent = '';
        this.elements.introTextEl.style.opacity = '1';

        let i = 0;
        if (this.state.activeTimer) {
          clearInterval(this.state.activeTimer);
        }

        this.state.activeTimer = setInterval(() => {
          if (this.state.introFinished) {
            clearInterval(this.state.activeTimer);
            this.state.activeTimer = null;
            return resolve();
          }
          if (i < text.length) {
            this.elements.introTextEl.textContent += text.charAt(i);
            i++;
          } else {
            clearInterval(this.state.activeTimer);
            this.state.activeTimer = null;
            resolve();
          }
        }, speed);
      });
    },

    fadeOutIntroText() {
      return new Promise((resolve) => {
        if (!this.elements.introTextEl) return resolve();
        this.elements.introTextEl.style.transition = 'opacity 0.4s ease';
        this.elements.introTextEl.style.opacity = '0';
        setTimeout(() => {
          if (this.elements.introTextEl) {
            this.elements.introTextEl.textContent = '';
            this.elements.introTextEl.style.opacity = '1';
          }
          resolve();
        }, 400);
      });
    },

    setMode(mode) {
      this.state.currentMode = mode;
      if (this.elements.modeItems) {
        this.elements.modeItems.forEach(item => {
          if (item.getAttribute('data-mode') === mode) {
            item.classList.add('active');
          } else {
            item.classList.remove('active');
          }
        });
      }

      if (this.elements.usernameInput) {
        this.elements.usernameInput.placeholder = mode === 'auth'
          ? 'click login or enter username for profile'
          : 'enter username or repo URL (e.g. torvalds or facebook/react)';
      }
    },

    handleExecute() {
      const rawQuery = this.elements.usernameInput ? this.elements.usernameInput.value : '';
      const query = rawQuery ? rawQuery.trim() : '';

      if (!query) {
        this.showTerminalInputError('err: target username or repository URL cannot be empty');
        return;
      }

      // Check for illegal script tags or control characters
      if (/<[^>]*>/g.test(query)) {
        this.showTerminalInputError('err: target input contains illegal characters');
        return;
      }

      const event = new CustomEvent('time-travel:start', {
        detail: {
          query: query,
          mode: this.state.currentMode
        }
      });
      window.dispatchEvent(event);
    },

    showTerminalInputError(msg) {
      const promptLine = document.querySelector('.prompt-line');
      if (promptLine) {
        promptLine.style.borderColor = '#DA3633';
      }

      if (this.elements.usernameInput) {
        const oldPlaceholder = this.elements.usernameInput.placeholder;
        this.elements.usernameInput.value = '';
        this.elements.usernameInput.placeholder = msg;
        setTimeout(() => {
          if (promptLine) promptLine.style.borderColor = 'var(--border)';
          if (this.elements.usernameInput) this.elements.usernameInput.placeholder = oldPlaceholder;
        }, 2000);
      }
    },

    async handleOAuthLogin() {
      try {
        console.log('[HomeModule] Requesting OAuth URL from ApiService...');
        const authUrlRes = await window.ApiService.getAuthUrl();
        const url = typeof authUrlRes === 'string'
          ? authUrlRes
          : (authUrlRes ? (authUrlRes.url || (authUrlRes.data && authUrlRes.data.url)) : null);

        console.log('[HomeModule] Final OAuth Redirect URL:', url);

        if (url) {
          window.location.href = url;
        } else {
          console.error('[HomeModule] Failed to retrieve valid OAuth URL from backend.');
        }
      } catch (err) {
        console.error('[HomeModule] Exception while initiating OAuth login:', err);
      }
    }
  };

  window.HomeModule = HomeModule;
})(window);
