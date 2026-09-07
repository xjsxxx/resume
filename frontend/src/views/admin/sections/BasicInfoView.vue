<template>
  <div class="page-card">
    <el-card shadow="never" v-loading="loading">
      <template #header>
        <div class="card-head">
          <span>基本信息 / 个人简介</span>
          <span class="tip">此处修改会即时同步到前台展示页</span>
        </div>
      </template>

      <el-form v-if="form" :model="form" label-width="96px" class="basic-form">
        <div class="form-section">
          <div class="section-title">个人档案</div>
          <el-row :gutter="20">
            <el-col :xs="24" :md="12">
              <el-form-item label="姓名">
                <el-input v-model="form.name" placeholder="例如：张启航" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :md="12">
              <el-form-item label="求职意向岗位">
                <el-input v-model="form.jobTitle" placeholder="例如：Java 后端开发工程师" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :md="12">
              <el-form-item label="头像地址(URL)">
                <el-input v-model="form.avatarUrl" placeholder="留空则前台用姓名首字占位头像" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :md="12">
              <el-form-item label="经验时长">
                <el-input v-model="form.yearsOfExp" placeholder="例如：3 年 / 应届 · 2024 届" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :md="12">
              <el-form-item label="出生年月">
                <el-input v-model="form.birthDate" placeholder="例如：2001-08" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :md="12">
              <el-form-item label="所在城市">
                <el-input v-model="form.city" placeholder="例如：上海市" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :md="12">
              <el-form-item label="联系电话">
                <el-input v-model="form.phone" placeholder="例如：138-0000-0000" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :md="12">
              <el-form-item label="邮箱">
                <el-input v-model="form.email" placeholder="name@example.com" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :md="12">
              <el-form-item label="GitHub">
                <el-input v-model="form.github" placeholder="https://github.com/xxx" />
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <div class="form-section">
          <div class="section-title">关于我（个人简介）</div>
          <el-form-item label="简介">
            <el-input
              v-model="form.summary"
              type="textarea"
              :rows="6"
              maxlength="2000"
              show-word-limit
              placeholder="一句话介绍自己的专业背景、技术特长与求职意向……"
            />
          </el-form-item>
        </div>

        <div class="actions">
          <el-button type="primary" size="large" :loading="saving" @click="save">保存修改</el-button>
          <el-button size="large" @click="load">重置</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { basicApi } from '../../../api/http'

const loading = ref(false)
const saving = ref(false)
const form = reactive({})

async function load() {
  loading.value = true
  try {
    const data = await basicApi.get()
    Object.assign(form, data || {})
  } finally {
    loading.value = false
  }
}

async function save() {
  saving.value = true
  try {
    await basicApi.update({ ...form })
    ElMessage.success('已保存，前台展示已更新')
  } catch (e) {
    /* 拦截器提示 */
  } finally {
    saving.value = false
  }
}

onMounted(load)
</script>

<style scoped>
.card-head {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 600;
}
.card-head .tip {
  color: #94a3b8;
  font-weight: 400;
  font-size: 13px;
}
.form-section {
  border: 1px solid #f0f2f6;
  border-radius: 12px;
  padding: 18px 20px 6px;
  margin-bottom: 22px;
  background: #fafbfd;
}
.section-title {
  font-weight: 600;
  margin-bottom: 14px;
  color: #334155;
  padding-left: 10px;
  border-left: 3px solid #6366f1;
}
.basic-form {
  max-width: 900px;
}
.actions {
  padding-left: 4px;
}
</style>
