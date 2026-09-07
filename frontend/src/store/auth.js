import { defineStore } from 'pinia'

const TOKEN_KEY = 'resume_admin_token'

/**
 * 管理员登录态
 */
export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem(TOKEN_KEY) || '',
    username: localStorage.getItem('resume_admin_username') || '',
    nickname: localStorage.getItem('resume_admin_nickname') || '',
    avatar: localStorage.getItem('resume_admin_avatar') || ''
  }),
  getters: {
    isLogin: (state) => !!state.token
  },
  actions: {
    setSession({ token, username, nickname, avatar }) {
      this.token = token || ''
      this.username = username || ''
      this.nickname = nickname || ''
      this.avatar = avatar || ''
      localStorage.setItem(TOKEN_KEY, this.token)
      localStorage.setItem('resume_admin_username', this.username)
      localStorage.setItem('resume_admin_nickname', this.nickname)
      localStorage.setItem('resume_admin_avatar', this.avatar)
    },
    setProfile(profile) {
      this.username = profile.username || this.username
      this.nickname = profile.nickname || this.nickname
      this.avatar = profile.avatar || this.avatar
      localStorage.setItem('resume_admin_username', this.username)
      localStorage.setItem('resume_admin_nickname', this.nickname)
      localStorage.setItem('resume_admin_avatar', this.avatar)
    },
    logout() {
      this.token = ''
      this.username = ''
      this.nickname = ''
      this.avatar = ''
      localStorage.removeItem(TOKEN_KEY)
      localStorage.removeItem('resume_admin_username')
      localStorage.removeItem('resume_admin_nickname')
      localStorage.removeItem('resume_admin_avatar')
    }
  }
})
