import request from './request'

/**
 * 接口统一封装
 */

// 后台：认证
export const authApi = {
  login: (data) => request.post('/admin/auth/login', data),
  me: () => request.get('/admin/auth/me'),
  changePassword: (data) => request.put('/admin/auth/password', data)
}

// 前台公开数据
export const publicApi = {
  resume: () => request.get('/public/resume')
}

// 基本信息（单行）
export const basicApi = {
  get: () => request.get('/admin/basic'),
  update: (data) => request.put('/admin/basic', data)
}

// 通用列表栏目 CRUD 工厂
function createSectionApi(module) {
  return {
    list: () => request.get(`/admin/${module}`),
    add: (data) => request.post(`/admin/${module}`, data),
    update: (data) => request.put(`/admin/${module}`, data),
    del: (id) => request.delete(`/admin/${module}/${id}`)
  }
}

export const educationApi = createSectionApi('education')
export const experienceApi = createSectionApi('experience')
export const projectApi = createSectionApi('project')
export const skillApi = createSectionApi('skill')
export const awardApi = createSectionApi('award')
