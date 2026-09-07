<template>
  <el-card shadow="never" v-loading="loading" class="page-card">
    <div class="toolbar">
      <div>
        <h3>教育经历</h3>
        <p class="sub">按「排序值」从小到大展示</p>
      </div>
      <el-button type="primary" :icon="Plus" @click="openAdd">新增教育经历</el-button>
    </div>

    <el-table :data="list" border stripe>
      <el-table-column prop="sortOrder" label="排序" width="70" align="center" />
      <el-table-column prop="school" label="学校" min-width="140" show-overflow-tooltip />
      <el-table-column prop="major" label="专业" min-width="130" show-overflow-tooltip />
      <el-table-column prop="degree" label="学历" width="90" align="center" />
      <el-table-column label="时间" width="180">
        <template #default="{ row }">{{ row.startDate || '?' }} ~ {{ row.endDate || '至今' }}</template>
      </el-table-column>
      <el-table-column prop="description" label="描述" min-width="220" show-overflow-tooltip />
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
        <el-empty description="暂无教育经历，点击右上角新增" :image-size="80" />
      </template>
    </el-table>

    <el-dialog v-model="visible" :title="editing ? '编辑教育经历' : '新增教育经历'" width="640px" :close-on-click-modal="false">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="16">
          <el-col :xs="24" :sm="12">
            <el-form-item label="学校" prop="school">
              <el-input v-model="form.school" placeholder="学校名称" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="专业" prop="major">
              <el-input v-model="form.major" placeholder="专业" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="学历">
              <el-select v-model="form.degree" filterable allow-create placeholder="选择或输入" style="width: 100%">
                <el-option v-for="d in degrees" :key="d" :label="d" :value="d" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="排序值">
              <el-input-number v-model="form.sortOrder" :min="0" :max="999" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="开始时间">
              <el-input v-model="form.startDate" placeholder="如 2019-09" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12">
            <el-form-item label="结束时间">
              <el-input v-model="form.endDate" placeholder="如 2023-06 / 留空表示至今" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="描述">
              <el-input v-model="form.description" type="textarea" :rows="4" maxlength="1000" show-word-limit placeholder="主要课程、GPA、奖学金、毕业设计等" />
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
import { educationApi } from '../../../api/http'

const degrees = ['本科', '硕士', '博士', '大专', '高中']
const loading = ref(false)
const saving = ref(false)
const list = ref([])
const visible = ref(false)
const editing = ref(false)
const formRef = ref()
const emptyForm = () => ({ id: null, school: '', major: '', degree: '', startDate: '', endDate: '', description: '', sortOrder: 0 })
const form = reactive(emptyForm())

const rules = {
  school: [{ required: true, message: '请输入学校名称', trigger: 'blur' }]
}

async function load() {
  loading.value = true
  try {
    list.value = await educationApi.list()
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
      await educationApi.update({ ...form })
    } else {
      await educationApi.add({ ...form })
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
  await educationApi.del(row.id)
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
