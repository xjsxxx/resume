import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../store/auth'

const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import('../views/public/PortfolioView.vue')
  },
  {
    path: '/admin/login',
    name: 'admin-login',
    component: () => import('../views/admin/LoginView.vue'),
    meta: { title: '管理登录' }
  },
  {
    path: '/admin',
    component: () => import('../views/admin/LayoutView.vue'),
    redirect: '/admin/basic',
    meta: { requiresAuth: true },
    children: [
      {
        path: 'basic',
        name: 'admin-basic',
        component: () => import('../views/admin/sections/BasicInfoView.vue'),
        meta: { title: '基本信息', requiresAuth: true }
      },
      {
        path: 'education',
        name: 'admin-education',
        component: () => import('../views/admin/sections/EducationView.vue'),
        meta: { title: '教育经历', requiresAuth: true }
      },
      {
        path: 'experience',
        name: 'admin-experience',
        component: () => import('../views/admin/sections/ExperienceView.vue'),
        meta: { title: '工作经历', requiresAuth: true }
      },
      {
        path: 'project',
        name: 'admin-project',
        component: () => import('../views/admin/sections/ProjectView.vue'),
        meta: { title: '项目经历', requiresAuth: true }
      },
      {
        path: 'skill',
        name: 'admin-skill',
        component: () => import('../views/admin/sections/SkillView.vue'),
        meta: { title: '专业技能', requiresAuth: true }
      },
      {
        path: 'award',
        name: 'admin-award',
        component: () => import('../views/admin/sections/AwardView.vue'),
        meta: { title: '荣誉证书', requiresAuth: true }
      }
    ]
  },
  { path: '/:pathMatch(.*)*', redirect: '/' }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫：后台页需登录
router.beforeEach((to) => {
  const auth = useAuthStore()
  if (to.meta.requiresAuth && !auth.isLogin) {
    return { path: '/admin/login', query: { redirect: to.fullPath } }
  }
  if (to.path === '/admin/login' && auth.isLogin) {
    return { path: '/admin/basic' }
  }
  return true
})

export default router
