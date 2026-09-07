<template>
  <el-card shadow="never" v-loading="loading" class="page-card">
    <div class="toolbar">
      <div>
        <h3>项目经历</h3>
        <p class="sub">按「排序值」从小到大展示</p>
      </div>
      <el-button type="primary" :icon="Plus" @click="openAdd">新增项目</el-button>
    </div>

    <el-table :data="list" border stripe>
      <el-table-column prop="sortOrder" label="排序" width="70" align="center" />
      <el-table-column prop="name" label="项目名称" min-width="170" show-overflow-tooltip />
      <el-table-column prop="role" label="角色" width="110" show-overflow-tooltip />
      <el-table-column prop="techStack" label="技术栈" min-width="180" show-overflow-tooltip />
      <el-table-column label="时间" width="180">
        <template #default="{ row }">{{ row.startDate || '?' }} ~ {{ row.endDate || '至今' }}</template>
      </el-table-column>
      <el-table-column prop="description" label="项目描述" min-width="220" show-overflow-tooltip />
      <el-table-column label="操作" width="150" align="center" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" @click="openEdit(row)">编辑</el-button>
          <el-popconfirm title="确定删除这条记录吗？" @confirm="del(row)">
            <template #reference>
              <el-button link type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="暂无项目，点击右上角新增" :image-size="80" />
      </template>
    </el-table>

    <el-dialog v-model="visible" :title="editing ? '编辑项目' : '新增项目'" width="700px" :close-on-click-modal="false">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="16">
          <el-col :xs="24" :sm="14">
            <el-form-item label="项目名称" prop="name">
              <el-input v-model="form.name" placeholder="项目名称" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="10">
            <el-form-item label="担任角色">
              <el-input v-model="form.role" placeholder="如 全栈开发" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="开始时间">
              <el-input v-model="form.startDate" placeholder="如 2023-03" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="结束时间">
              <el-input v-model="form.endDate" placeholder="如 2023-05 / 留空表示至今" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="技术栈">
              <el-input v-model="form.techStack" placeholder="用逗号分隔，如：Spring Boot, MyBatis-Plus, MySQL, Vue3" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="项目链接">
              <el-input v-model="form.link" placeholder="https:// 项目地址 / Demo / 代码仓库，可为空" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="排序值">
              <el-input-number v-model="form.sortOrder" :min="0" :max="999" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="项目描述">
              <el-input v-model="form.description" type="textarea" :rows="5" maxlength="4000" show-word-limit placeholder="项目背景、个人职责、亮点成果，每行一条" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" :loading="saving" @click="submit">保存</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { projectApi } from '../../../api/http'

const loading = ref(false)
const saving = ref(false)
const list = ref([])
const visible = ref(false)
const editing = ref(false)
const formRef = ref()
const emptyForm = () => ({ id: null, name: '', role: '', techStack: '', link: '', startDate: '', endDate: '', description: '', sortOrder: 0 })
const form = reactive(emptyForm())

const rules = {
  name: [{ required: true, message: '请输入项目名称', trigger: 'blur' }]
}

async function load() {
  loading.value = true
  try {
    list.value = await projectApi.list()
  } finally {
    loading.value = false
  }
}

function openAdd() {
  Object.assign(form, emptyForm())
  form.sortOrder = list.value.length
  editing.value = false
  visible.value = true
}

function openEdit(row) {
  Object.assign(form, emptyForm(), row)
  editing.value = true
  visible.value = true
}

async function submit() {
  await formRef.value.validate().catch(() => Promise.reject())
  saving.value = true
  try {
    if (editing.value) {
      await projectApi.update({ ...form })
    } else {
      await projectApi.add({ ...form })
    }
    ElMessage.success('保存成功')
    visible.value = false
    load()
  } catch (e) {
    /* 拦截器提示 */
  } finally {
    saving.value = false
  }
}

async function del(row) {
  await projectApi.del(row.id)
  ElMessage.success('已删除')
  load()
}

onMounted(load)
</script>

<style scoped>
.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}
.toolbar h3 {
  margin: 0;
  color: #16213b;
}
.toolbar .sub {
  margin: 4px 0 0;
  color: #94a3b8;
  font-size: 13px;
}
</style>
