<template>
  <div class="login-page">
    <div class="login-card">
      <div class="login-aside">
        <div class="aside-inner">
          <div class="logo">简历简历 · 内容管理</div>
          <h1>在线维护你的<br />求职简历</h1>
          <p>基本信息、教育、经历、项目、技能、荣誉，随改随存，前台即时生效。</p>
          <a class="back-link" href="/">← 返回前台简历</a>
        </div>
      </div>
      <div class="login-form-wrap">
        <h2>管理登录</h2>
        <p class="tip">默认账号：admin / admin123</p>
        <el-form ref="formRef" :model="form" :rules="rules" size="large" @keyup.enter="onSubmit">
          <el-form-item prop="username">
            <el-input v-model="form.username" placeholder="请输入用户名" :prefix-icon="User" clearable />
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="form.password" type="password" placeholder="请输入密码" :prefix-icon="Lock" show-password />
          </el-form-item>
          <el-button type="primary" class="submit-btn" :loading="loading" @click="onSubmit">
            登 录
          </el-button>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { User, Lock } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { authApi } from '../../api/http'
import { useAuthStore } from '../../store/auth'

const router = useRouter()
const route = useRoute()
const auth = useAuthStore()

const formRef = ref()
const loading = ref(false)
const form = reactive({ username: '', password: '' })
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

async function onSubmit() {
  await formRef.value.validate().catch(() => Promise.reject())
  loading.value = true
  try {
    const data = await authApi.login({ ...form })
    auth.setSession(data)
    ElMessage.success(`欢迎回来，${data.nickname || data.username}`)
    router.push(typeof route.query.redirect === 'string' ? route.query.redirect : '/admin/basic')
  } catch (e) {
    /* 错误已由拦截器提示 */
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 24px;
  background: linear-gradient(135deg, #0f172a, #1e1b4b 55%, #0e7490);
}
.login-card {
  width: 880px;
  max-width: 100%;
  min-height: 500px;
  border-radius: 20px;
  display: flex;
  overflow: hidden;
  background: #fff;
  box-shadow: 0 30px 80px rgba(0, 0, 0, 0.35);
}
.login-aside {
  flex: 1.1;
  color: #fff;
  padding: 48px 40px;
  background:
    radial-gradient(400px 240px at 20% 10%, rgba(34, 211, 238, 0.25), transparent 60%),
    linear-gradient(160deg, #312e81, #1e40af 60%, #0e7490);
  display: flex;
  align-items: center;
}
.logo {
  letter-spacing: 2px;
  opacity: 0.85;
  font-size: 14px;
  margin-bottom: 34px;
}
.aside-inner h1 {
  font-size: 30px;
  line-height: 1.5;
  margin: 0 0 18px;
}
.aside-inner p {
  opacity: 0.85;
  line-height: 1.9;
  font-size: 14px;
}
.back-link {
  display: inline-block;
  margin-top: 26px;
  color: #a5f3fc;
  font-size: 14px;
}
.login-form-wrap {
  flex: 1;
  padding: 56px 44px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.login-form-wrap h2 {
  margin: 0 0 6px;
  color: #16213b;
  font-size: 26px;
}
.login-form-wrap .tip {
  margin: 0 0 30px;
  color: #94a3b8;
  font-size: 13px;
}
.submit-btn {
  width: 100%;
  height: 44px;
  font-size: 15px;
  margin-top: 4px;
}
@media (max-width: 760px) {
  .login-aside {
    display: none;
  }
}
</style>
