<template>
  <el-card shadow="never" v-loading="loading" class="page-card">
    <div class="toolbar">
      <div>
        <h3>专业技能</h3>
        <p class="sub">同分类在展示页自动归组，按「排序值」排列</p>
      </div>
      <el-button type="primary" :icon="Plus" @click="openAdd">新增技能</el-button>
    </div>

    <el-table :data="list" border stripe>
      <el-table-column prop="category" label="分类" width="160" show-overflow-tooltip>
        <template #default="{ row }">
          <el-tag effect="plain" color="#eef2ff" style="color: #4f46e5; border-color: #c7d2fe">{{ row.category || '其他' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="技能名称" min-width="160" />
      <el-table-column label="熟练度" width="220">
        <template #default="{ row }">
          <div class="level-cell">
            <el-progress :percentage="Math.min(row.level ?? 0, 100)" :stroke-width="10" />
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="sortOrder" label="排序" width="80" align="center" />
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
        <el-empty description="暂无技能，点击右上角新增" :image-size="80" />
      </template>
    </el-table>

    <el-dialog v-model="visible" :title="editing ? '编辑技能' : '新增技能'" width="520px" :close-on-click-modal="false">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="技能名称" prop="name">
          <el-input v-model="form.name" placeholder="如 Java / Vue3 / MySQL" />
        </el-form-item>
        <el-form-item label="分类" prop="category">
          <el-select v-model="form.category" filterable allow-create default-first-option placeholder="选择或输入新分类" style="width: 100%">
            <el-option v-for="c in categories" :key="c" :label="c" :value="c" />
          </el-select>
        </el-form-item>
        <el-form-item label="熟练度">
          <div class="slider-row">
            <el-slider v-model="form.level" :min="0" :max="100" />
            <span class="val">{{ form.level }}%</span>
          </div>
        </el-form-item>
        <el-form-item label="排序值">
          <el-input-number v-model="form.sortOrder" :min="0" :max="999" />
        </el-form-item>
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
import { skillApi } from '../../../api/http'

const presets = ['后端', '前端', '数据库与中间件', '工具']
const loading = ref(false)
const saving = ref(false)
const list = ref([])
const visible = ref(false)
const editing = ref(false)
const formRef = ref()
const categories = ref([...presets])
const emptyForm = () => ({ id: null, category: '', name: '', level: 0, sortOrder: 0 })
const form = reactive(emptyForm())

const rules = {
  name: [{ required: true, message: '请输入技能名称', trigger: 'blur' }],
  category: [{ required: true, message: '请选择或输入分类', trigger: 'change' }]
}

async function load() {
  loading.value = true
  try {
    const data = await skillApi.list()
    list.value = data
    // 汇总已有分类供下拉选择
    const set = new Set(presets)
    data.forEach((s) => s.category && set.add(s.category))
    categories.value = [...set]
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
      await skillApi.update({ ...form })
    } else {
      await skillApi.add({ ...form })
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
  await skillApi.del(row.id)
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
.level-cell {
  display: flex;
  align-items: center;
  gap: 10px;
}
.slider-row {
  display: flex;
  align-items: center;
  gap: 14px;
  width: 100%;
}
.slider-row .el-slider {
  flex: 1;
}
.val {
  width: 40px;
  text-align: right;
  color: #4f46e5;
  font-weight: 600;
}
</style>
