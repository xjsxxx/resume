<template>
  <el-card shadow="never" v-loading="loading" class="page-card">
    <div class="toolbar">
      <div>
        <h3>工作 / 实习经历</h3>
        <p class="sub">按「排序值」从小到大展示</p>
      </div>
      <el-button type="primary" :icon="Plus" @click="openAdd">新增经历</el-button>
    </div>

    <el-table :data="list" border stripe>
      <el-table-column prop="sortOrder" label="排序" width="70" align="center" />
      <el-table-column prop="company" label="公司" min-width="150" show-overflow-tooltip />
      <el-table-column prop="position" label="职位" min-width="120" show-overflow-tooltip />
      <el-table-column label="类型" width="90" align="center">
        <template #default="{ row }">
          <el-tag :type="row.type === '实习' ? 'warning' : 'primary'" size="small" effect="light">
            {{ row.type || '工作' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="时间" width="180">
        <template #default="{ row }">{{ row.startDate || '?' }} ~ {{ row.endDate || '至今' }}</template>
      </el-table-column>
      <el-table-column prop="description" label="工作内容" min-width="240" show-overflow-tooltip />
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
        <el-empty description="暂无经历，点击右上角新增" :image-size="80" />
      </template>
    </el-table>

    <el-dialog v-model="visible" :title="editing ? '编辑经历' : '新增经历'" width="680px" :close-on-click-modal="false">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="16">
          <el-col :xs="24" :sm="12">
            <el-form-item label="公司" prop="company">
              <el-input v-model="form.company" placeholder="公司名称" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="职位">
              <el-input v-model="form.position" placeholder="担任职位" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="类型">
              <el-radio-group v-model="form.type">
                <el-radio-button label="工作" />
                <el-radio-button label="实习" />
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="排序值">
              <el-input-number v-model="form.sortOrder" :min="0" :max="999" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="开始时间">
              <el-input v-model="form.startDate" placeholder="如 2023-06" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="结束时间">
              <el-input v-model="form.endDate" placeholder="如 2023-09 / 留空表示至今" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="工作内容">
              <el-input v-model="form.description" type="textarea" :rows="5" maxlength="2000" show-word-limit placeholder="职责与业绩，每行一条，可用 · 开头" />
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
import { experienceApi } from '../../../api/http'

const loading = ref(false)
const saving = ref(false)
const list = ref([])
const visible = ref(false)
const editing = ref(false)
const formRef = ref()
const emptyForm = () => ({ id: null, company: '', position: '', type: '工作', startDate: '', endDate: '', description: '', sortOrder: 0 })
const form = reactive(emptyForm())

const rules = {
  company: [{ required: true, message: '请输入公司名称', trigger: 'blur' }]
}

async function load() {
  loading.value = true
  try {
    list.value = await experienceApi.list()
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
      await experienceApi.update({ ...form })
    } else {
      await experienceApi.add({ ...form })
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
  await experienceApi.del(row.id)
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
