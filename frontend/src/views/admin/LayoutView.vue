<template>
  <el-container class="layout">
    <!-- 侧边栏 -->
    <el-aside width="220px" class="aside">
      <div class="aside-brand">
        <span class="brand-dot">简</span>
        <span>简历后台管理</span>
      </div>
      <el-menu :default-active="$route.path" router class="menu">
        <el-menu-item v-for="m in menus" :key="m.path" :index="'/admin/' + m.path">
          <el-icon><component :is="m.icon" /></el-icon>
          <span>{{ m.title }}</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <!-- 顶栏 -->
      <el-header class="header">
        <div class="header-title">栏目维护 · {{ currentTitle }}</div>
        <div class="header-right">
          <a class="view-site" href="/" target="_blank" rel="noopener">
            <el-icon><View /></el-icon> 查看前台
          </a>
          <el-dropdown trigger="click" @command="onCommand">
            <div class="user">
              <el-avatar :size="32" :src="auth.avatar || undefined">{{ avatarText }}</el-avatar>
              <span>{{ auth.nickname || auth.username }}</span>
              <el-icon><ArrowDown /></el-icon>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="password">
                  <el-icon><Key /></el-icon>修改密码
                </el-dropdown-item>
                <el-dropdown-item command="logout" divided>
                  <el-icon><SwitchButton /></el-icon>退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <!-- 内容区 -->
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>

    <!-- 修改密码 -->
    <el-dialog v-model="pwdVisible" title="修改密码" width="420px" :close-on-click-modal="false">
      <el-form ref="pwdFormRef" :model="pwdForm" :rules="pwdRules" label-width="88px">
        <el-form-item label="原密码" prop="oldPassword">
          <el-input v-model="pwdForm.oldPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="pwdForm.newPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirm">
          <el-input v-model="pwdForm.confirm" type="password" show-password />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="pwdVisible = false">取消</el-button>
        <el-button type="primary" :loading="pwdLoading" @click="submitPwd">确定</el-button>
      </template>
    </el-dialog>
  </el-container>
</template>

<script setup>
import { computed, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { authApi } from '../../api/http'
import { useAuthStore } from '../../store/auth'

const route = useRoute()
const router = useRouter()
const auth = useAuthStore()

const menus = [
  { path: 'basic', title: '基本信息', icon: 'Postcard' },
  { path: 'education', title: '教育经历', icon: 'School' },
  { path: 'experience', title: '工作经历', icon: 'Suitcase' },
  { path: 'project', title: '项目经历', icon: 'FolderOpened' },
  { path: 'skill', title: '专业技能', icon: 'TrendCharts' },
  { path: 'award', title: '荣誉证书', icon: 'Trophy' }
]

const currentTitle = computed(() => route.meta.title || '管理')

const avatarText = computed(() => (auth.nickname || auth.username || '管').charAt(0))

function onCommand(cmd) {
  if (cmd === 'logout') {
    ElMessageBox.confirm('确定退出登录吗？', '提示', { type: 'warning' })
      .then(() => {
        auth.logout()
        router.push('/admin/login')
      })
      .catch(() => {})
  } else if (cmd === 'password') {
    pwdForm.oldPassword = ''
    pwdForm.newPassword = ''
    pwdForm.confirm = ''
    pwdVisible.value = true
  }
}

// ---- 修改密码 ----
const pwdVisible = ref(false)
const pwdLoading = ref(false)
const pwdFormRef = ref()
const pwdForm = reactive({ oldPassword: '', newPassword: '', confirm: '' })
const pwdRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '新密码至少 6 位', trigger: 'blur' }
  ],
  confirm: [
    { required: true, message: '请再次输入新密码', trigger: 'blur' },
    {
      validator: (rule, value, cb) => (value === pwdForm.newPassword ? cb() : cb(new Error('两次密码不一致'))),
      trigger: 'blur'
    }
  ]
}

async function submitPwd() {
  await pwdFormRef.value.validate().catch(() => Promise.reject())
  pwdLoading.value = true
  try {
    await authApi.changePassword({ oldPassword: pwdForm.oldPassword, newPassword: pwdForm.newPassword })
    ElMessage.success('密码修改成功，请重新登录')
    pwdVisible.value = false
    auth.logout()
    router.push('/admin/login')
  } catch (e) {
    /* 拦截器已提示 */
  } finally {
    pwdLoading.value = false
  }
}
</script>

<style scoped>
.layout {
  height: 100vh;
}
.aside {
  background: #111a2e;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
.aside-brand {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  color: #fff;
  font-weight: 600;
  letter-spacing: 1px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
}
.brand-dot {
  width: 28px;
  height: 28px;
  border-radius: 8px;
  display: grid;
  place-items: center;
  background: linear-gradient(135deg, #6366f1, #22d3ee);
}
.menu {
  flex: 1;
  border-right: none;
  background: transparent;
  padding-top: 8px;
}
.menu :deep(.el-menu-item) {
  color: rgba(255, 255, 255, 0.72);
}
.menu :deep(.el-menu-item:hover) {
  background: rgba(255, 255, 255, 0.06);
  color: #fff;
}
.menu :deep(.el-menu-item.is-active) {
  color: #22d3ee;
  background: linear-gradient(90deg, rgba(34, 211, 238, 0.16), transparent);
}
.header {
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #eef0f4;
  padding: 0 20px;
}
.header-title {
  font-weight: 600;
  color: #334155;
}
.header-right {
  display: flex;
  align-items: center;
  gap: 18px;
}
.view-site {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  color: #4f46e5;
  font-size: 14px;
}
.user {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  color: #334155;
  font-size: 14px;
  outline: none;
}
.main {
  background: #f5f6fa;
  overflow-y: auto;
}
</style>
