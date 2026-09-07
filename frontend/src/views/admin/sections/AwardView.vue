<template>
  <el-card shadow="never" v-loading="loading" class="page-card">
    <div class="toolbar">
      <div>
        <h3>荣誉证书</h3>
        <p class="sub">按「排序值」从小到大展示</p>
      </div>
      <el-button type="primary" :icon="Plus" @click="openAdd">新增荣誉</el-button>
    </div>

    <el-table :data="list" border stripe>
      <el-table-column prop="sortOrder" label="排序" width="70" align="center" />
      <el-table-column prop="name" label="奖项名称" min-width="220" show-overflow-tooltip />
      <el-table-column label="级别" width="110" align="center">
        <template #default="{ row }">
          <el-tag :type="levelType(row.level)" size="small" effect="light">{{ row.level || '-' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="date" label="获奖时间" width="120" align="center" />
      <el-table-column prop="issuer" label="颁发单位" min-width="140" show-overflow-tooltip />
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
        <el-empty description="暂无荣誉，点击右上角新增" :image-size="80" />
      </template>
    </el-table>

    <el-dialog v-model="visible" :title="editing ? '编辑荣誉' : '新增荣誉'" width="560px" :close-on-click-modal="false">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="奖项名称" prop="name">
          <el-input v-model="form.name" placeholder="奖项 / 证书名称" />
        </el-form-item>
        <el-row :gutter="16">
          <el-col :xs="24" :sm="12">
            <el-form-item label="级别">
              <el-select v-model="form.level" filterable allow-create placeholder="选择或输入" style="width: 100%">
                <el-option v-for="l in levels" :key="l" :label="l" :value="l" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="获奖时间">
              <el-input v-model="form.date" placeholder="如 2023-05" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="颁发单位">
              <el-input v-model="form.issuer" placeholder="颁发 / 认证机构" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="排序值">
              <el-input-number v-model="form.sortOrder" :min="0" :max="999" />
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
import { awardApi } from '../../../api/http'

const levels = ['国家级', '省级', '校级', '院级', '其他']
const loading = ref(false)
const saving = ref(false)
const list = ref([])
const visible = ref(false)
const editing = ref(false)
const formRef = ref()
const emptyForm = () => ({ id: null, name: '', level: '', date: '', issuer: '', sortOrder: 0 })
const form = reactive(emptyForm())

const rules = {
  name: [{ required: true, message: '请输入奖项名称', trigger: 'blur' }]
}

function levelType(level) {
  const map = { 国家级: 'danger', 省级: 'warning', 校级: 'success', 院级: 'info' }
  return map[level] || 'primary'
}

async function load() {
  loading.value = true
  try {
    list.value = await awardApi.list()
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
      await awardApi.update({ ...form })
    } else {
      await awardApi.add({ ...form })
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
  await awardApi.del(row.id)
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
