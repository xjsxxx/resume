<template>
  <div class="portfolio">
    <!-- 顶部导航 -->
    <header class="top-nav" :class="{ scrolled: scrolled }">
      <div class="nav-inner">
        <a class="brand" href="#top">
          <span class="brand-logo">{{ initial }}</span>
          <span class="brand-name">{{ basic.name || '我的简历' }}</span>
        </a>
        <nav class="nav-links">
          <a v-for="item in navItems" :key="item.href" :href="item.href">{{ item.label }}</a>
          <a class="nav-admin" href="/admin/login">后台管理</a>
        </nav>
      </div>
    </header>

    <div id="top"></div>

    <!-- Hero -->
    <section class="hero">
      <div class="hero-bg" aria-hidden="true"></div>
      <div class="hero-inner">
        <div class="hero-avatar">
          <img v-if="basic.avatarUrl" :src="basic.avatarUrl" alt="头像" />
          <span v-else>{{ initial }}</span>
        </div>
        <h1 class="hero-name">{{ basic.name || '你的名字' }}</h1>
        <p class="hero-title">
          <span class="tag">{{ basic.jobTitle || '求职意向岗位' }}</span>
          <span v-if="basic.yearsOfExp" class="tag light">{{ basic.yearsOfExp }}</span>
        </p>
        <p class="hero-slogan">{{ slogan }}</p>

        <div class="hero-contact">
          <a v-if="basic.city" class="chip" href="#contact">
            <svg viewBox="0 0 24 24"><path d="M12 2C8.1 2 5 5.1 5 9c0 5.2 7 13 7 13s7-7.8 7-13c0-3.9-3.1-7-7-7zm0 9.5A2.5 2.5 0 1 1 12 6.5a2.5 2.5 0 0 1 0 5z"/></svg>
            {{ basic.city }}
          </a>
          <a v-if="basic.phone" class="chip" :href="'tel:' + basic.phone">
            <svg viewBox="0 0 24 24"><path d="M6.6 10.8c1.4 2.8 3.8 5.1 6.6 6.6l2.2-2.2c.3-.3.7-.4 1-.2 1.1.4 2.3.6 3.6.6.6 0 1 .4 1 1V20c0 .6-.4 1-1 1C10.6 21 3 13.4 3 4c0-.6.4-1 1-1h3.5c.6 0 1 .4 1 1 0 1.2.2 2.4.6 3.6.1.3 0 .7-.2 1l-2.3 2.2z"/></svg>
            {{ basic.phone }}
          </a>
          <a v-if="basic.email" class="chip" :href="'mailto:' + basic.email">
            <svg viewBox="0 0 24 24"><path d="M20 4H4c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 4-8 5-8-5V6l8 5 8-5v2z"/></svg>
            {{ basic.email }}
          </a>
          <a v-if="basic.github" class="chip" :href="basic.github" target="_blank" rel="noopener">
            <svg viewBox="0 0 24 24"><path d="M12 2C6.5 2 2 6.6 2 12.2c0 4.5 2.9 8.3 6.8 9.7.5.1.7-.2.7-.5v-1.8c-2.8.6-3.4-1.2-3.4-1.2-.4-1.2-1.1-1.5-1.1-1.5-.9-.6.1-.6.1-.6 1 .1 1.5 1 1.5 1 .9 1.6 2.4 1.1 3 .9.1-.7.4-1.1.6-1.4-2.2-.3-4.6-1.1-4.6-5 0-1.1.4-2 1-2.7-.1-.3-.4-1.3.1-2.7 0 0 .8-.3 2.7 1a9.4 9.4 0 0 1 5 0c1.9-1.3 2.7-1 2.7-1 .5 1.4.2 2.4.1 2.7.6.7 1 1.6 1 2.7 0 3.9-2.4 4.7-4.6 5 .4.3.7.9.7 1.9v2.8c0 .3.2.6.7.5 4-1.4 6.8-5.2 6.8-9.7C22 6.6 17.5 2 12 2z"/></svg>
            GitHub
          </a>
        </div>

        <a class="scroll-hint" href="#about"><span class="dot"></span></a>
      </div>
    </section>

    <main class="content">
      <!-- 关于我 -->
      <section v-if="basic.summary" id="about" class="block">
        <div class="block-head">
          <span class="kicker">About</span>
          <h2>关于我</h2>
        </div>
        <div class="about-card glass">
          <p class="about-text">{{ basic.summary }}</p>
        </div>
      </section>

      <!-- 专业技能 -->
      <section v-if="skillGroups.length" id="skills" class="block alt">
        <div class="block-head">
          <span class="kicker">Skills</span>
          <h2>专业技能</h2>
        </div>
        <div class="skills-grid">
          <div v-for="group in skillGroups" :key="group.category" class="skill-group">
            <h3 class="skill-cat">{{ group.category }}</h3>
            <div v-for="s in group.items" :key="s.id" class="skill-row">
              <div class="skill-meta">
                <span class="skill-name">{{ s.name }}</span>
                <span class="skill-val">{{ s.level }}%</span>
              </div>
              <div class="bar">
                <div class="bar-fill" :style="{ width: Math.min(s.level, 100) + '%' }"></div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- 工作与实习经历 -->
      <section v-if="experience.length" id="experience" class="block">
        <div class="block-head">
          <span class="kicker">Experience</span>
          <h2>工作与实习经历</h2>
        </div>
        <div class="timeline">
          <article v-for="item in experience" :key="item.id" class="timeline-item">
            <div class="timeline-dot"></div>
            <div class="timeline-card glass">
              <header>
                <h3>{{ item.company }}</h3>
                <span class="date">{{ item.startDate }} ~ {{ item.endDate || '至今' }}</span>
              </header>
              <p class="role">
                <span class="type-chip" :class="item.type === '实习' ? 'intern' : 'work'">{{ item.type || '工作' }}</span>
                {{ item.position }}
              </p>
              <p class="desc">{{ item.description }}</p>
            </div>
          </article>
        </div>
      </section>

      <!-- 教育经历 -->
      <section v-if="education.length" id="education" class="block alt">
        <div class="block-head">
          <span class="kicker">Education</span>
          <h2>教育经历</h2>
        </div>
        <div class="edu-list">
          <article v-for="item in education" :key="item.id" class="edu-card glass">
            <div class="edu-icon"><span>校</span></div>
            <div class="edu-body">
              <div class="edu-row">
                <h3>{{ item.school }}</h3>
                <span v-if="item.degree" class="degree">{{ item.degree }}</span>
              </div>
              <p class="edu-major">{{ item.major }} · {{ item.startDate }} ~ {{ item.endDate || '至今' }}</p>
              <p v-if="item.description" class="desc">{{ item.description }}</p>
            </div>
          </article>
        </div>
      </section>

      <!-- 项目经历 -->
      <section v-if="projects.length" id="projects" class="block">
        <div class="block-head">
          <span class="kicker">Projects</span>
          <h2>项目经历</h2>
        </div>
        <div class="proj-grid">
          <article v-for="item in projects" :key="item.id" class="proj-card glass">
            <div class="proj-top">
              <h3>{{ item.name }}</h3>
              <a v-if="item.link" class="proj-link" :href="item.link" target="_blank" rel="noopener" title="项目链接">
                <svg viewBox="0 0 24 24"><path d="M10.6 13.4a1 1 0 0 1-1.4 0l-2.1-2.1a4.9 4.9 0 0 1 6.9-6.9l1.8 1.8a1 1 0 1 1-1.4 1.4l-1.8-1.8a2.9 2.9 0 0 0-4.1 4.1l2.1 2.1a1 1 0 0 1 0 1.4zM13.4 10.6a1 1 0 0 1 1.4 0l2.1 2.1a4.9 4.9 0 0 1-6.9 6.9l-1.8-1.8a1 1 0 1 1 1.4-1.4l1.8 1.8a2.9 2.9 0 0 0 4.1-4.1l-2.1-2.1a1 1 0 0 1 0-1.4z"/></svg>
              </a>
            </div>
            <p class="proj-role">{{ item.role }}<template v-if="item.startDate"> · {{ item.startDate }} ~ {{ item.endDate || '至今' }}</template></p>
            <div v-if="techList(item.techStack).length" class="tags">
              <span v-for="t in techList(item.techStack)" :key="t" class="tech-tag">{{ t }}</span>
            </div>
            <p class="desc">{{ item.description }}</p>
          </article>
        </div>
      </section>

      <!-- 荣誉证书 -->
      <section v-if="awards.length" id="awards" class="block alt">
        <div class="block-head">
          <span class="kicker">Awards</span>
          <h2>荣誉证书</h2>
        </div>
        <div class="award-list">
          <article v-for="item in awards" :key="item.id" class="award-row glass">
            <div class="award-badge"><span>★</span></div>
            <div class="award-main">
              <h3>{{ item.name }}</h3>
              <p class="award-sub">
                <template v-if="item.issuer">颁发单位：{{ item.issuer }}</template>
              </p>
            </div>
            <div class="award-side">
              <span v-if="item.level" class="level-tag">{{ item.level }}</span>
              <span v-if="item.date" class="date">{{ item.date }}</span>
            </div>
          </article>
        </div>
      </section>

      <!-- 页脚 -->
      <footer id="contact" class="footer">
        <p>如果你对我感兴趣，欢迎通过邮箱联系我：<a :href="'mailto:' + (basic.email || '')">{{ basic.email || '—' }}</a></p>
        <p class="footer-sub">© {{ year }} {{ basic.name || '个人简历' }} · Powered by Vue3 + Spring Boot</p>
      </footer>
    </main>

    <!-- 加载失败提示 -->
    <div v-if="loadError" class="load-error glass">
      <h3>暂时无法加载简历内容</h3>
      <p>{{ loadError }}</p>
      <button class="retry-btn" @click="load">重试</button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { publicApi } from '../../api/http'

const basic = ref({})
const education = ref([])
const experience = ref([])
const projects = ref([])
const skills = ref([])
const awards = ref([])
const loadError = ref('')
const scrolled = ref(false)

const navItems = [
  { href: '#about', label: '关于我' },
  { href: '#skills', label: '专业技能' },
  { href: '#experience', label: '工作经历' },
  { href: '#education', label: '教育经历' },
  { href: '#projects', label: '项目经历' },
  { href: '#awards', label: '荣誉' }
]

const year = new Date().getFullYear()

const initial = computed(() => (basic.value.name || '个').trim().charAt(0).toUpperCase())

const slogan = computed(() => {
  const s = (basic.value.summary || '').trim()
  return s.length > 90 ? s.slice(0, 90) + '…' : s
})

const skillGroups = computed(() => {
  const map = new Map()
  for (const s of skills.value) {
    const cat = s.category || '其他'
    if (!map.has(cat)) map.set(cat, [])
    map.get(cat).push(s)
  }
  return [...map.entries()].map(([category, items]) => ({ category, items }))
})

function techList(str) {
  if (!str) return []
  return str.split(/[,，;；、]/).map((t) => t.trim()).filter(Boolean)
}

function onScroll() {
  scrolled.value = window.scrollY > 10
}

async function load() {
  loadError.value = ''
  try {
    const data = await publicApi.resume()
    basic.value = data.basic || {}
    education.value = data.education || []
    experience.value = data.experience || []
    projects.value = data.project || []
    skills.value = data.skill || []
    awards.value = data.award || []
  } catch (e) {
    loadError.value = e.message || '网络异常'
  }
}

onMounted(() => {
  load()
  window.addEventListener('scroll', onScroll, { passive: true })
})
</script>

<style scoped>
.portfolio {
  color: #22304a;
}

/* ---------- 顶栏 ---------- */
.top-nav {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 100;
  transition: background 0.3s ease, box-shadow 0.3s ease;
  background: rgba(15, 23, 42, 0);
}
.top-nav.scrolled {
  background: rgba(255, 255, 255, 0.92);
  box-shadow: 0 2px 18px rgba(15, 23, 42, 0.08);
  backdrop-filter: blur(8px);
}
.nav-inner {
  max-width: 1100px;
  margin: 0 auto;
  padding: 0 24px;
  height: 62px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.brand {
  display: flex;
  align-items: center;
  gap: 10px;
}
.brand-logo {
  width: 34px;
  height: 34px;
  border-radius: 10px;
  display: grid;
  place-items: center;
  color: #fff;
  font-weight: 700;
  background: linear-gradient(135deg, #4f46e5, #22d3ee);
  box-shadow: 0 4px 12px rgba(79, 70, 229, 0.35);
}
.top-nav.scrolled .brand-name,
.top-nav:not(.scrolled) .brand-name {
  font-weight: 600;
}
.brand-name {
  color: #fff;
  font-size: 16px;
}
.top-nav.scrolled .brand-name {
  color: #22304a;
}
.nav-links {
  display: flex;
  align-items: center;
  gap: 20px;
  font-size: 14px;
}
.nav-links a {
  color: rgba(255, 255, 255, 0.9);
  transition: color 0.2s;
}
.top-nav.scrolled .nav-links a {
  color: #475569;
}
.nav-links a:hover {
  color: #22d3ee;
}
.nav-links .nav-admin {
  border: 1px solid currentColor;
  border-radius: 999px;
  padding: 4px 14px;
  opacity: 0.9;
}

/* ---------- Hero ---------- */
.hero {
  position: relative;
  min-height: 92vh;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  padding: 120px 24px 60px;
  overflow: hidden;
  background: linear-gradient(135deg, #0f172a 0%, #1e1b4b 45%, #0e7490 100%);
  color: #fff;
}
.hero-bg {
  position: absolute;
  inset: 0;
  background:
    radial-gradient(600px 320px at 15% 10%, rgba(34, 211, 238, 0.18), transparent 60%),
    radial-gradient(700px 380px at 85% 85%, rgba(99, 102, 241, 0.22), transparent 60%);
}
.hero-inner {
  position: relative;
  z-index: 1;
  max-width: 760px;
}
.hero-avatar {
  width: 120px;
  height: 120px;
  margin: 0 auto 22px;
  border-radius: 50%;
  overflow: hidden;
  border: 4px solid rgba(255, 255, 255, 0.5);
  background: linear-gradient(135deg, #6366f1, #22d3ee);
  display: grid;
  place-items: center;
  font-size: 52px;
  font-weight: 700;
  color: #fff;
  box-shadow: 0 12px 40px rgba(34, 211, 238, 0.35);
}
.hero-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.hero-name {
  margin: 0 0 12px;
  font-size: 46px;
  letter-spacing: 2px;
  font-weight: 800;
}
.hero-title {
  display: flex;
  justify-content: center;
  gap: 10px;
  flex-wrap: wrap;
  margin: 0 0 18px;
}
.tag {
  background: rgba(255, 255, 255, 0.14);
  border: 1px solid rgba(255, 255, 255, 0.3);
  padding: 4px 14px;
  border-radius: 999px;
  font-size: 14px;
}
.tag.light {
  background: rgba(34, 211, 238, 0.16);
}
.hero-slogan {
  max-width: 620px;
  margin: 0 auto 26px;
  line-height: 1.8;
  color: rgba(255, 255, 255, 0.85);
}
.hero-contact {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  gap: 10px;
}
.chip {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.25);
  border-radius: 999px;
  padding: 7px 14px;
  font-size: 13px;
  transition: background 0.2s, transform 0.2s;
}
.chip:hover {
  background: rgba(255, 255, 255, 0.2);
  transform: translateY(-2px);
}
.chip svg {
  width: 15px;
  height: 15px;
  fill: currentColor;
}
.scroll-hint {
  position: absolute;
  left: 50%;
  bottom: 26px;
  transform: translateX(-50%);
  width: 26px;
  height: 42px;
  border: 2px solid rgba(255, 255, 255, 0.6);
  border-radius: 20px;
  display: flex;
  justify-content: center;
  padding-top: 8px;
}
.scroll-hint .dot {
  width: 4px;
  height: 8px;
  border-radius: 4px;
  background: #fff;
  animation: drop 1.6s infinite;
}
@keyframes drop {
  0% { transform: translateY(0); opacity: 1; }
  70% { transform: translateY(10px); opacity: 0; }
  100% { opacity: 0; }
}

/* ---------- 内容通用 ---------- */
.content {
  max-width: 1100px;
  margin: 0 auto;
  padding: 40px 24px 80px;
}
.block {
  padding: 56px 0 10px;
  scroll-margin-top: 60px;
}
.block.alt {
  /* 交替底色 */
}
.block-head {
  margin-bottom: 34px;
}
.block-head .kicker {
  font-size: 13px;
  letter-spacing: 4px;
  text-transform: uppercase;
  color: #6366f1;
  font-weight: 600;
}
.block-head h2 {
  margin: 6px 0 0;
  font-size: 30px;
  color: #16213b;
}
.glass {
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 10px 30px rgba(15, 23, 42, 0.07);
}

/* 关于我 */
.about-card {
  padding: 28px 30px;
}
.about-text {
  line-height: 2;
  font-size: 15px;
  color: #3b4a63;
  margin: 0;
  white-space: pre-line;
}

/* 技能 */
.skills-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 22px;
}
.skill-group {
  background: #fff;
  border-radius: 16px;
  padding: 22px 24px;
  box-shadow: 0 10px 30px rgba(15, 23, 42, 0.06);
}
.skill-cat {
  margin: 0 0 18px;
  font-size: 16px;
  color: #16213b;
  position: relative;
  padding-left: 14px;
}
.skill-cat::before {
  content: '';
  position: absolute;
  left: 0;
  top: 3px;
  bottom: 3px;
  width: 4px;
  border-radius: 4px;
  background: linear-gradient(#6366f1, #22d3ee);
}
.skill-row {
  margin-bottom: 16px;
}
.skill-row:last-child {
  margin-bottom: 0;
}
.skill-meta {
  display: flex;
  justify-content: space-between;
  font-size: 14px;
  margin-bottom: 7px;
}
.skill-name {
  color: #22304a;
}
.skill-val {
  color: #94a3b8;
}
.bar {
  height: 8px;
  background: #e8edf5;
  border-radius: 8px;
  overflow: hidden;
}
.bar-fill {
  height: 100%;
  border-radius: 8px;
  background: linear-gradient(90deg, #6366f1, #22d3ee);
  transition: width 0.8s ease;
}

/* 时间轴（经历） */
.timeline {
  position: relative;
  padding-left: 26px;
}
.timeline::before {
  content: '';
  position: absolute;
  left: 6px;
  top: 8px;
  bottom: 8px;
  width: 2px;
  background: linear-gradient(#6366f1, #22d3ee);
  opacity: 0.4;
}
.timeline-item {
  position: relative;
  margin-bottom: 24px;
}
.timeline-dot {
  position: absolute;
  left: -26px;
  top: 22px;
  width: 12px;
  height: 12px;
  margin-left: -3px;
  border-radius: 50%;
  background: #6366f1;
  border: 3px solid #fff;
  box-shadow: 0 0 0 2px #6366f1;
}
.timeline-card {
  padding: 20px 24px;
}
.timeline-card header {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  flex-wrap: wrap;
  gap: 8px;
}
.timeline-card h3 {
  margin: 0;
  font-size: 18px;
}
.date {
  color: #94a3b8;
  font-size: 13px;
}
.role {
  margin: 8px 0;
  color: #475569;
}
.type-chip {
  display: inline-block;
  font-size: 12px;
  padding: 1px 8px;
  border-radius: 6px;
  margin-right: 6px;
  vertical-align: 1px;
}
.type-chip.work {
  color: #0e7490;
  background: rgba(14, 116, 144, 0.12);
}
.type-chip.intern {
  color: #7c3aed;
  background: rgba(124, 58, 237, 0.12);
}
.desc {
  margin: 8px 0 0;
  line-height: 1.8;
  color: #4b5a74;
  font-size: 14px;
  white-space: pre-line;
}

/* 教育 */
.edu-list {
  display: grid;
  gap: 18px;
}
.edu-card {
  display: flex;
  gap: 18px;
  padding: 22px 24px;
  align-items: flex-start;
}
.edu-icon {
  flex: 0 0 52px;
  height: 52px;
  border-radius: 14px;
  display: grid;
  place-items: center;
  font-size: 18px;
  color: #fff;
  background: linear-gradient(135deg, #6366f1, #22d3ee);
}
.edu-body {
  flex: 1;
}
.edu-row {
  display: flex;
  align-items: center;
  gap: 12px;
  flex-wrap: wrap;
}
.edu-row h3 {
  margin: 0;
  font-size: 18px;
}
.degree {
  background: rgba(99, 102, 241, 0.12);
  color: #4f46e5;
  font-size: 12px;
  padding: 2px 10px;
  border-radius: 999px;
}
.edu-major {
  margin: 8px 0 0;
  color: #64748b;
  font-size: 14px;
}

/* 项目 */
.proj-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 22px;
}
.proj-card {
  padding: 22px 24px;
  display: flex;
  flex-direction: column;
  transition: transform 0.25s, box-shadow 0.25s;
}
.proj-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 18px 40px rgba(15, 23, 42, 0.12);
}
.proj-top {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 8px;
}
.proj-top h3 {
  margin: 0;
  font-size: 18px;
  color: #16213b;
}
.proj-link {
  flex: 0 0 auto;
  width: 32px;
  height: 32px;
  border-radius: 8px;
  display: grid;
  place-items: center;
  background: #eef2ff;
  color: #4f46e5;
}
.proj-link svg {
  width: 18px;
  height: 18px;
  fill: currentColor;
}
.proj-link:hover {
  background: #4f46e5;
  color: #fff;
}
.proj-role {
  margin: 6px 0 12px;
  color: #94a3b8;
  font-size: 13px;
}
.tags {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
  margin-bottom: 12px;
}
.tech-tag {
  font-size: 12px;
  padding: 2px 9px;
  border-radius: 6px;
  background: #f1f5f9;
  color: #475569;
  border: 1px solid #e2e8f0;
}

/* 荣誉 */
.award-list {
  display: grid;
  gap: 14px;
}
.award-row {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 16px 22px;
}
.award-badge {
  flex: 0 0 44px;
  height: 44px;
  border-radius: 12px;
  display: grid;
  place-items: center;
  font-size: 20px;
  color: #d97706;
  background: linear-gradient(135deg, #fef3c7, #fde68a);
}
.award-main {
  flex: 1;
}
.award-main h3 {
  margin: 0;
  font-size: 15px;
}
.award-sub {
  margin: 4px 0 0;
  font-size: 13px;
  color: #94a3b8;
}
.award-side {
  display: flex;
  align-items: center;
  gap: 10px;
  flex: 0 0 auto;
}
.level-tag {
  font-size: 12px;
  color: #7c3aed;
  background: rgba(124, 58, 237, 0.1);
  padding: 2px 10px;
  border-radius: 999px;
}

/* 页脚 */
.footer {
  margin-top: 70px;
  padding: 40px 0 10px;
  text-align: center;
  border-top: 1px dashed #e2e8f0;
}
.footer a {
  color: #4f46e5;
  font-weight: 600;
}
.footer-sub {
  margin-top: 10px;
  color: #94a3b8;
  font-size: 13px;
}

/* 加载错误 */
.load-error {
  position: fixed;
  left: 50%;
  top: 40%;
  transform: translate(-50%, -50%);
  z-index: 200;
  padding: 30px 40px;
  text-align: center;
  box-shadow: 0 24px 60px rgba(15, 23, 42, 0.2);
}
.load-error h3 {
  margin: 0 0 8px;
}
.load-error p {
  color: #ef4444;
  margin: 0 0 16px;
}
.retry-btn {
  border: none;
  background: linear-gradient(90deg, #4f46e5, #22d3ee);
  color: #fff;
  padding: 8px 22px;
  border-radius: 8px;
  cursor: pointer;
}

@media (max-width: 720px) {
  .nav-links {
    display: none;
  }
  .hero-name {
    font-size: 34px;
  }
}
</style>
