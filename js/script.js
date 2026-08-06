/**
 * GitHub Time Machine — Main Application Controller
 * Optimized with DocumentFragment single-pass DOM rendering for high 60fps performance.
 */

(function (window) {
  'use strict';

  const App = {
    elements: {},
    state: {
      currentSection: 'home-screen',
      targetQuery: '',
      isRepoQuery: false,
      profileData: null,
      reposData: [],
      timelineData: [],
      replayTimeline: [],
      replayIndex: 0,
      activeCardTimer: null,
      dashboardRevealed: false
    },

    init() {
      console.log('[GitHub Time Machine] Performance Engine Initialized.');
      this.cacheDOM();
      
      if (window.HomeModule) {
        window.HomeModule.init();
      }

      this.bindEvents();
      this.checkAuthAndCallback();
    },

    cacheDOM() {
      const U = window.Utils;
      if (!U) return;

      this.elements = {
        appContainer: U.$('#app'),
        btnStartReplay: U.$('#btn-start-replay'),
        btnSkipReplay: U.$('#btn-skip-replay'),
        btnReplayPrev: U.$('#btn-replay-prev'),
        btnReplayNext: U.$('#btn-replay-next'),
        btnNewSearch: U.$('#btn-new-search'),
        btnViewWrapped: U.$('#btn-view-wrapped'),
        btnWrappedRestart: U.$('#btn-wrapped-restart'),
        btnBackHomeList: U.$$('.btn-back-home'),
        btnBackDashboardList: U.$$('.btn-back-dashboard'),
        userGreetingTitle: U.$('#user-greeting-title'),
        statYears: U.$('#stat-years'),
        statMonths: U.$('#stat-months'),
        statDays: U.$('#stat-days'),
        replayCard: U.$('#replay-card'),
        replayDate: U.$('#replay-date'),
        replayText: U.$('#replay-text'),
        replayProgressText: U.$('#replay-progress-text'),
        replayDots: U.$('#replay-dots'),
        headerUserTag: U.$('#header-user-tag'),
        userProfileBadge: U.$('#user-profile-badge'),
        headerUserAvatar: U.$('#header-user-avatar'),
        headerUserName: U.$('#header-user-name'),
        btnSavedAnalyses: U.$('#btn-saved-analyses'),
        btnLogout: U.$('#btn-logout'),
        btnAdminDashboard: U.$('#btn-admin-dashboard'),
        adminModal: U.$('#admin-modal'),
        btnCloseAdmin: U.$('#btn-close-admin'),
        adminTabs: U.$$('.admin-tab'),
        adminTabContents: U.$$('.admin-tab-content'),
        historyModal: U.$('#history-modal'),
        historyList: U.$('#history-list'),
        btnCloseHistory: U.$('#btn-close-history'),
        summaryYears: U.$('#summary-years'),
        summaryRepos: U.$('#summary-repos'),
        summaryFollowers: U.$('#summary-followers'),
        summaryLang: U.$('#summary-primary-lang'),
        timelineList: U.$('#timeline-list'),
        reposGrid: U.$('#repos-grid'),
        repoFullName: U.$('#repo-full-name'),
        repoOwner: U.$('#repo-owner'),
        wrappedUser: U.$('#wrapped-username')
      };
    },

    bindEvents() {
      window.addEventListener('time-travel:start', (e) => {
        this.startAnalysis(e.detail.query);
      });

      if (this.elements.btnStartReplay) {
        this.elements.btnStartReplay.addEventListener('click', () => this.startReplaySequence());
      }

      window.addEventListener('keydown', (e) => {
        if (this.state.currentSection === 'replay-intro-screen' && e.key === 'Enter') {
          this.startReplaySequence();
        } else if (this.state.currentSection === 'replay-screen') {
          if (e.key === 'ArrowRight' || e.key === 'Enter') {
            this.nextReplaySlide();
          } else if (e.key === 'ArrowLeft') {
            this.prevReplaySlide();
          }
        }
      });

      if (this.elements.btnSkipReplay) {
        this.elements.btnSkipReplay.addEventListener('click', () => this.showDashboard());
      }

      if (this.elements.btnReplayPrev) {
        this.elements.btnReplayPrev.addEventListener('click', () => this.prevReplaySlide());
      }

      if (this.elements.btnReplayNext) {
        this.elements.btnReplayNext.addEventListener('click', () => this.nextReplaySlide());
      }

      if (this.elements.btnNewSearch) {
        this.elements.btnNewSearch.addEventListener('click', () => this.restartApp());
      }

      if (this.elements.btnBackHomeList) {
        this.elements.btnBackHomeList.forEach(btn => btn.addEventListener('click', () => this.restartApp()));
      }

      if (this.elements.btnViewWrapped) {
        this.elements.btnViewWrapped.addEventListener('click', () => this.showWrapped());
      }

      if (this.elements.btnWrappedRestart) {
        this.elements.btnWrappedRestart.addEventListener('click', () => this.restartApp());
      }

      if (this.elements.btnBackDashboardList) {
        this.elements.btnBackDashboardList.forEach(btn => btn.addEventListener('click', () => this.showDashboard()));
      }

      if (this.elements.btnLogout) {
        this.elements.btnLogout.addEventListener('click', () => {
          this.updateAuthUI(null);
          if (window.ApiService) window.ApiService.logout();
        });
      }

      if (this.elements.btnSavedAnalyses) {
        this.elements.btnSavedAnalyses.addEventListener('click', () => this.showHistoryModal());
      }

      if (this.elements.btnCloseHistory) {
        this.elements.btnCloseHistory.addEventListener('click', () => this.closeHistoryModal());
      }

      if (this.elements.btnAdminDashboard) {
        this.elements.btnAdminDashboard.addEventListener('click', () => this.showAdminModal());
      }

      if (this.elements.btnCloseAdmin) {
        this.elements.btnCloseAdmin.addEventListener('click', () => this.closeAdminModal());
      }

      if (this.elements.adminTabs) {
        this.elements.adminTabs.forEach(tab => {
          tab.addEventListener('click', () => {
            const targetTab = tab.getAttribute('data-tab');
            this.switchAdminTab(targetTab);
          });
        });
      }
    },

    async checkAuthAndCallback() {
      // 1. Check if OAuth code is in URL search params
      const urlParams = new URLSearchParams(window.location.search);
      const code = urlParams.get('code');

      if (code) {
        // Clean URL to prevent re-submitting code on refresh
        window.history.replaceState({}, document.title, window.location.pathname);
        try {
          if (window.ApiService) {
            const authRes = await window.ApiService.handleOAuthCallback(code);
            const user = authRes ? (authRes.user || (authRes.data && authRes.data.user)) : null;
            if (user && user.username) {
              this.updateAuthUI(user);
            } else {
              this.updateAuthUI(null);
            }
          }
        } catch (err) {
          console.error('[OAuth Callback Error]', err);
          this.updateAuthUI(null);
        }
      } else {
        // 2. Check cached user or fetch /api/auth/me
        const token = localStorage.getItem('gtm_auth_token');
        if (token && window.ApiService) {
          try {
            const user = await window.ApiService.getCurrentUser();
            if (user && user.username) {
              this.updateAuthUI(user);
            } else {
              this.updateAuthUI(null);
            }
          } catch (err) {
            console.warn('[Auth Check Error] Session expired or offline');
            this.updateAuthUI(null);
          }
        } else {
          this.updateAuthUI(null);
        }
      }
    },

    updateAuthUI(user) {
      if (!user) {
        if (this.elements.btnAdminDashboard) {
          this.elements.btnAdminDashboard.classList.add('hidden');
        }
        if (this.elements.userProfileBadge) {
          this.elements.userProfileBadge.classList.add('hidden');
        }
        if (this.elements.headerUserTag) {
          this.elements.headerUserTag.style.display = 'block';
        }
        return;
      }
      if (this.elements.userProfileBadge) {
        this.elements.userProfileBadge.classList.remove('hidden');
      }
      if (this.elements.headerUserTag) {
        this.elements.headerUserTag.style.display = 'none';
      }
      if (this.elements.headerUserAvatar) {
        this.elements.headerUserAvatar.src = user.avatarUrl || 'https://github.com/identicons/octocat.png';
      }
      if (this.elements.headerUserName) {
        this.elements.headerUserName.textContent = user.displayName || `@${user.username}`;
      }
      if (this.elements.btnAdminDashboard) {
        if (user.role === 'ROLE_ADMIN') {
          this.elements.btnAdminDashboard.classList.remove('hidden');
        } else {
          this.elements.btnAdminDashboard.classList.add('hidden');
        }
      }
    },

    async showHistoryModal() {
      if (!this.elements.historyModal || !this.elements.historyList) return;

      this.elements.historyModal.classList.remove('hidden');
      this.elements.historyList.innerHTML = '<p class="empty-history-text">Loading saved history...</p>';

      try {
        if (window.ApiService) {
          const history = await window.ApiService.getUserHistory();
          if (Array.isArray(history) && history.length > 0) {
            this.elements.historyList.innerHTML = history.map(item => `
              <div class="history-item">
                <div>
                  <div class="history-item-target">${item.target}</div>
                  <div class="history-item-date">${new Date(item.createdAt).toLocaleString()}</div>
                </div>
                <button type="button" class="history-item-action" onclick="window.App.startAnalysis('${item.target}')">Replay Again</button>
              </div>
            `).join('');
          } else {
            this.elements.historyList.innerHTML = '<p class="empty-history-text">No saved analyses found.</p>';
          }
        }
      } catch (err) {
        this.elements.historyList.innerHTML = '<p class="empty-history-text">Failed to load history.</p>';
      }
    },

    closeHistoryModal() {
      if (this.elements.historyModal) {
        this.elements.historyModal.classList.add('hidden');
      }
    },

    navigateTo(sectionId) {
      const currentEl = document.getElementById(this.state.currentSection);
      const targetEl = document.getElementById(sectionId);

      if (!targetEl) return;

      if (currentEl) {
        currentEl.classList.add('fade-out');
        setTimeout(() => {
          currentEl.classList.add('hidden');
          currentEl.classList.remove('fade-out', 'active');

          targetEl.classList.remove('hidden');
          targetEl.classList.add('active');
          window.scrollTo(0, 0);
          this.state.currentSection = sectionId;
        }, 600);
      } else {
        targetEl.classList.remove('hidden');
        targetEl.classList.add('active');
        window.scrollTo(0, 0);
        this.state.currentSection = sectionId;
      }
    },

    async startAnalysis(query) {
      const U = window.Utils;
      this.state.targetQuery = query;
      this.state.isRepoQuery = query.includes('/') || query.includes('github.com');

      this.navigateTo('loading-screen');

      const targetNameEl = document.getElementById('loading-target-name');
      if (targetNameEl) {
        targetNameEl.textContent = `@${query.replace('https://github.com/', '')}`;
      }

      const loadingLog = document.getElementById('loading-log');
      if (loadingLog) loadingLog.innerHTML = '';

      const steps = [
        { label: 'Connecting to GitHub...', bar: '████░░░░░░', req: () => window.ApiService.analyze(query) },
        { label: 'Fetching profile...', bar: '█████░░░░░', req: () => window.ApiService.getProfile(query) },
        { label: 'Reading repositories...', bar: '███████░░░', req: () => window.ApiService.getRepositories(query) },
        { label: 'Reading commits...', bar: '██████████', req: () => window.ApiService.getTimeline(query) },
        { label: 'Analyzing languages...', bar: '██████████', req: () => window.ApiService.getInsights(query) },
        { label: 'Generating timeline...', bar: '██████████', req: () => window.ApiService.getReplay(query) },
        { label: 'Preparing replay...', bar: '██████████', req: () => window.ApiService.getWrapped(query) }
      ];

      for (let i = 0; i < steps.length; i++) {
        const step = steps[i];
        
        const line = document.createElement('div');
        line.className = 'log-line';
        line.innerHTML = `
          <span class="log-text">${U ? U.escapeHtml(step.label) : step.label}</span>
          <span class="log-progress">${step.bar}</span>
        `;
        if (loadingLog) loadingLog.appendChild(line);

        try {
          const res = await step.req();
          if ((i === 0 || i === 1) && res) this.state.profileData = res;
          if (i === 2 && Array.isArray(res)) this.state.reposData = res;
          if ((i === 3 || i === 5) && Array.isArray(res)) this.state.timelineData = res;
        } catch (e) {
          console.warn('[App] Resilient fallback handling triggered');
        }

        if (U) await U.sleep(250);
        else await new Promise(r => setTimeout(r, 250));
      }

      if (U) await U.sleep(300);
      else await new Promise(r => setTimeout(r, 300));

      if (this.state.isRepoQuery) {
        this.showRepoAnalysis(query);
      } else {
        this.showReplayIntro();
      }
    },

    showReplayIntro() {
      const data = this.state.profileData || {
        username: this.state.targetQuery,
        yearsCoding: 4,
        monthsCoding: 7,
        daysCoding: 18
      };

      if (this.elements.userGreetingTitle) {
        this.elements.userGreetingTitle.textContent = `Hello ${data.username || this.state.targetQuery}`;
      }

      if (this.elements.statYears) this.elements.statYears.textContent = data.yearsCoding || '4';
      if (this.elements.statMonths) this.elements.statMonths.textContent = data.monthsCoding || '7';
      if (this.elements.statDays) this.elements.statDays.textContent = data.daysCoding || '18';

      this.navigateTo('replay-intro-screen');
    },

    startReplaySequence() {
      const years = (this.state.profileData && this.state.profileData.yearsCoding) || 4;
      const currentYear = new Date().getFullYear();
      const startYear = currentYear - years;

      const defaultReplay = [
        { date: `September ${startYear}`, text: 'Created account & first repository on GitHub.' },
        { date: `June ${startYear + Math.max(1, Math.floor(years / 4))}`, text: 'Mastered Git workflow & committed 100th change.' },
        { date: `March ${startYear + Math.max(2, Math.floor((years * 2) / 4))}`, text: 'Architected first full-stack application.' },
        { date: `August ${startYear + Math.max(3, Math.floor((years * 3) / 4))}`, text: 'Contributed to open source systems.' },
        { date: 'Today', text: "You're still building. The story continues." }
      ];

      this.state.replayTimeline = (this.state.timelineData && this.state.timelineData.length) 
        ? this.state.timelineData 
        : defaultReplay;

      this.state.replayIndex = 0;
      this.navigateTo('replay-screen');
      this.renderReplayDots();
      this.displayReplaySlide(0);
    },

    renderReplayDots() {
      if (!this.elements.replayDots) return;
      
      // Performance Optimization: Use DocumentFragment for single-pass DOM insertion
      const fragment = document.createDocumentFragment();
      this.state.replayTimeline.forEach((_, idx) => {
        const dot = document.createElement('div');
        dot.className = `dot ${idx === 0 ? 'active' : ''}`;
        fragment.appendChild(dot);
      });

      this.elements.replayDots.innerHTML = '';
      this.elements.replayDots.appendChild(fragment);
    },

    displayReplaySlide(index) {
      if (index < 0 || index >= this.state.replayTimeline.length) return;

      this.state.replayIndex = index;
      const slide = this.state.replayTimeline[index];

      if (this.elements.replayProgressText) {
        this.elements.replayProgressText.textContent = `EVENT ${index + 1} / ${this.state.replayTimeline.length}`;
      }

      if (this.elements.btnReplayPrev) {
        if (index === 0) this.elements.btnReplayPrev.classList.add('disabled');
        else this.elements.btnReplayPrev.classList.remove('disabled');
      }

      if (this.elements.btnReplayNext) {
        this.elements.btnReplayNext.textContent = (index === this.state.replayTimeline.length - 1) ? 'DASHBOARD →' : 'NEXT →';
      }

      const dots = document.querySelectorAll('#replay-dots .dot');
      dots.forEach((dot, idx) => {
        if (idx === index) dot.classList.add('active');
        else dot.classList.remove('active');
      });

      if (this.state.activeCardTimer) {
        clearTimeout(this.state.activeCardTimer);
        this.state.activeCardTimer = null;
      }

      if (this.elements.replayCard) {
        this.elements.replayCard.classList.add('exiting');
        this.state.activeCardTimer = setTimeout(() => {
          if (this.elements.replayDate) this.elements.replayDate.textContent = slide.date;
          if (this.elements.replayText) this.elements.replayText.textContent = slide.text;
          if (this.elements.replayCard) {
            this.elements.replayCard.classList.remove('exiting');
            this.elements.replayCard.classList.add('entering');
            setTimeout(() => {
              if (this.elements.replayCard) this.elements.replayCard.classList.remove('entering');
            }, 50);
          }
        }, 200);
      }
    },

    nextReplaySlide() {
      if (this.state.replayIndex < this.state.replayTimeline.length - 1) {
        this.displayReplaySlide(this.state.replayIndex + 1);
      } else {
        this.showDashboard();
      }
    },

    prevReplaySlide() {
      if (this.state.replayIndex > 0) {
        this.displayReplaySlide(this.state.replayIndex - 1);
      }
    },

    /**
     * Performance Optimized Dashboard Rendering using DocumentFragment
     */
    showDashboard() {
      const U = window.Utils;
      this.navigateTo('dashboard-screen');

      if (this.elements.headerUserTag) {
        this.elements.headerUserTag.textContent = `@${this.state.targetQuery}`;
      }

      const data = this.state.profileData || {};
      const yearsVal = data.yearsCoding !== undefined ? data.yearsCoding : 1;
      const reposVal = data.publicReposCount !== undefined ? data.publicReposCount : (this.state.reposData ? this.state.reposData.length : 0);
      const followersVal = data.followersCount !== undefined ? data.followersCount : 0;

      if (this.elements.summaryYears) this.elements.summaryYears.textContent = `${yearsVal} YRS`;
      if (this.elements.summaryRepos) this.elements.summaryRepos.textContent = U ? U.formatNumber(reposVal) : String(reposVal);
      if (this.elements.summaryFollowers) this.elements.summaryFollowers.textContent = U ? U.formatNumber(followersVal) : String(followersVal);

      const topLang = (this.state.reposData && this.state.reposData.length > 0 && this.state.reposData[0].primaryLanguage)
        ? this.state.reposData[0].primaryLanguage
        : 'Code';
      if (this.elements.summaryLang) this.elements.summaryLang.textContent = topLang;

      // Performance Optimization: DocumentFragment single-pass insertion for Timeline
      if (this.elements.timelineList) {
        this.elements.timelineList.innerHTML = '';
        const items = this.state.replayTimeline || [];

        if (items.length === 0) {
          this.elements.timelineList.innerHTML = `
            <div class="empty-state-box">
              <span class="empty-state-title">[SYSTEM NOTICE]</span>
              <p>No historical commit milestones recorded for this period yet.</p>
            </div>
          `;
        } else {
          const fragment = document.createDocumentFragment();
          items.forEach(item => {
            const div = document.createElement('div');
            div.className = 'timeline-item';
            div.innerHTML = `
              <span class="timeline-item-date">${U ? U.escapeHtml(item.date) : item.date}</span>
              <span class="timeline-item-text">${U ? U.escapeHtml(item.text) : item.text}</span>
            `;
            fragment.appendChild(div);
          });
          this.elements.timelineList.appendChild(fragment);
        }
      }

      // Performance Optimization: DocumentFragment single-pass insertion for Repositories Grid
      if (this.elements.reposGrid) {
        this.elements.reposGrid.innerHTML = '';
        const realRepos = (this.state.reposData && this.state.reposData.length > 0)
          ? this.state.reposData.map(r => ({
              name: r.repoName || r.name || 'repository',
              desc: r.description || 'Public GitHub Repository',
              lang: r.primaryLanguage || 'Code',
              stars: r.starsCount !== undefined ? r.starsCount : 0
            }))
          : [];

        if (realRepos.length === 0) {
          this.elements.reposGrid.innerHTML = `
            <div class="empty-state-box">
              <span class="empty-state-title">[NO REPOSITORIES]</span>
              <p>No public repositories found for @${U ? U.escapeHtml(this.state.targetQuery) : this.state.targetQuery}.</p>
            </div>
          `;
        } else {
          const fragment = document.createDocumentFragment();
          realRepos.forEach(repo => {
            const card = document.createElement('div');
            card.className = 'repo-card';
            card.innerHTML = `
              <div>
                <h3 class="repo-card-title">❯ ${U ? U.escapeHtml(repo.name) : repo.name}</h3>
                <p class="repo-card-desc">${U ? U.escapeHtml(repo.desc) : repo.desc}</p>
              </div>
              <div class="repo-card-meta">
                <span>● ${U ? U.escapeHtml(repo.lang) : repo.lang}</span>
                <span>★ ${repo.stars} stars</span>
              </div>
            `;
            fragment.appendChild(card);
          });
          this.elements.reposGrid.appendChild(fragment);
        }
      }

      this.triggerGradualReveals();
    },

    triggerGradualReveals() {
      const revealBlocks = document.querySelectorAll('.reveal-block');
      revealBlocks.forEach((block, idx) => {
        setTimeout(() => {
          block.classList.add('revealed');
        }, (idx + 1) * 200);
      });
    },

    showRepoAnalysis(repoUrl) {
      this.navigateTo('repo-screen');

      const parts = repoUrl.replace('https://github.com/', '').split('/');
      const owner = parts[0] || 'facebook';
      const repoName = parts[1] || 'react';

      if (this.elements.repoFullName) this.elements.repoFullName.textContent = `${owner}/${repoName}`;
      if (this.elements.repoOwner) this.elements.repoOwner.textContent = owner;

      const metricBlocks = document.querySelectorAll('.metric-block');
      metricBlocks.forEach((block, idx) => {
        setTimeout(() => {
          block.classList.add('revealed');
        }, (idx + 1) * 200);
      });
    },

    showWrapped() {
      this.navigateTo('wrapped-screen');
      if (this.elements.wrappedUser) {
        this.elements.wrappedUser.textContent = `@${this.state.targetQuery}`;
      }
    },

    async showAdminModal() {
      if (!this.elements.adminModal) return;

      this.elements.adminModal.classList.remove('hidden');
      this.switchAdminTab('overview');

      try {
        if (window.ApiService) {
          const stats = await window.ApiService.getAdminDashboard();
          if (stats) {
            const setVal = (id, val) => {
              const el = document.getElementById(id);
              if (el) el.textContent = val !== undefined ? val : '0';
            };

            setVal('adm-total-users', stats.totalUsers);
            setVal('adm-total-logins', stats.totalLogins);
            setVal('adm-new-users', stats.newUsers24h);
            setVal('adm-dau', stats.dailyActiveUsers);
            setVal('adm-total-analyses', stats.totalAnalyses);
            setVal('adm-user-searches', stats.githubSearches);
            setVal('adm-repo-searches', stats.repositorySearches);
            setVal('adm-ai-requests', stats.aiRequests);
            setVal('adm-api-usage', stats.apiUsageCount);
            setVal('adm-db-status', stats.databaseStatus || 'Healthy (PostgreSQL / H2)');
            setVal('adm-uptime', stats.applicationUptime || '0 days, 0 hrs');

            const topUsersList = document.getElementById('adm-top-users-list');
            if (topUsersList && Array.isArray(stats.mostSearchedUsers)) {
              topUsersList.innerHTML = stats.mostSearchedUsers.map(u => `
                <li><span>${u.target}</span> <span class="tag">${u.count} searches</span></li>
              `).join('');
            }

            const topReposList = document.getElementById('adm-top-repos-list');
            if (topReposList && Array.isArray(stats.mostSearchedRepositories)) {
              topReposList.innerHTML = stats.mostSearchedRepositories.map(r => `
                <li><span>${r.target}</span> <span class="tag">${r.count} searches</span></li>
              `).join('');
            }
          }

          // Fetch User List
          const users = await window.ApiService.getAdminUsers();
          const tbody = document.getElementById('adm-users-tbody');
          if (tbody && Array.isArray(users)) {
            tbody.innerHTML = users.map(u => `
              <tr>
                <td><img src="${u.avatarUrl || 'https://github.com/identicons/octocat.png'}" class="user-avatar" width="24" height="24"/></td>
                <td><strong>@${u.username}</strong></td>
                <td>${u.displayName || '-'}</td>
                <td>${u.email || '-'}</td>
                <td><span class="user-role-badge ${u.role === 'ROLE_ADMIN' ? 'admin' : 'user'}">${u.role}</span></td>
                <td>${u.lastLogin ? new Date(u.lastLogin).toLocaleString() : 'Never'}</td>
              </tr>
            `).join('');
          }

          // Fetch System Status
          const sys = await window.ApiService.getAdminSystem();
          const sysLogs = document.getElementById('adm-system-logs');
          if (sysLogs && sys) {
            sysLogs.textContent = `SYSTEM STATUS: ${sys.status || 'UP'}
DATABASE: ${sys.database || 'CONNECTED'}
UPTIME: ${Math.floor((sys.uptimeMs || 0) / 1000)} seconds
JVM MEMORY FREE: ${Math.floor((sys.jvmMemoryFreeBytes || 0) / (1024 * 1024))} MB
JVM MEMORY TOTAL: ${Math.floor((sys.jvmMemoryTotalBytes || 0) / (1024 * 1024))} MB
AVAILABLE PROCESSORS: ${sys.availableProcessors || 4}`;
          }
        }
      } catch (err) {
        console.error('[Admin Dashboard Error]', err);
      }
    },

    closeAdminModal() {
      if (this.elements.adminModal) {
        this.elements.adminModal.classList.add('hidden');
      }
    },

    switchAdminTab(tabName) {
      if (this.elements.adminTabs) {
        this.elements.adminTabs.forEach(t => {
          if (t.getAttribute('data-tab') === tabName) {
            t.classList.add('active');
          } else {
            t.classList.remove('active');
          }
        });
      }

      if (this.elements.adminTabContents) {
        this.elements.adminTabContents.forEach(c => {
          if (c.id === `admin-tab-${tabName}`) {
            c.classList.remove('hidden');
            c.classList.add('active');
          } else {
            c.classList.add('hidden');
            c.classList.remove('active');
          }
        });
      }
    },

    restartApp() {
      if (this.state.activeCardTimer) {
        clearTimeout(this.state.activeCardTimer);
        this.state.activeCardTimer = null;
      }
      this.state.targetQuery = '';
      this.state.isRepoQuery = false;
      this.navigateTo('home-screen');
      if (window.HomeModule) {
        window.HomeModule.finishIntro();
      }
    }
  };

  document.addEventListener('DOMContentLoaded', () => {
    App.init();
  });

  window.App = App;
})(window);
