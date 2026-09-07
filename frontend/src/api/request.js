import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '../router'
import { useAuthStore } from '../store/auth'

/**
 * axios 实例：统一 baseURL、Token、错误处理
 * 后端返回结构 { code, msg, data }，本拦截器解包后直接返回 data 字段
 */
const request = axios.create({
  baseURL: '/api',
  timeout: 15000
})

// 请求拦截：附带 Token
request.interceptors.request.use((config) => {
  const auth = useAuthStore()
  if (auth.token) {
    config.headers.Authorization = `Bearer ${auth.token}`
  }
  return config
})

// 响应拦截：解包 + 错误提示 + 401 跳登录
request.interceptors.response.use(
  (response) => {
    const res = response.data
    if (res.code === 200) {
      return res.data
    }
    if (res.code === 401) {
      const auth = useAuthStore()
      auth.logout()
      ElMessage.error(res.msg || '登录已过期，请重新登录')
      router.push('/admin/login')
      return Promise.reject(new Error(res.msg || '未授权'))
    }
    ElMessage.error(res.msg || '请求失败')
    return Promise.reject(new Error(res.msg || '请求失败'))
  },
  (error) => {
    ElMessage.error(error.response?.data?.msg || '网络异常，请稍后再试')
    return Promise.reject(error)
  }
)

export default request
