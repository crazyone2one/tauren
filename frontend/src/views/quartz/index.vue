<script setup lang="ts">
import type {DataTableColumns, DataTableRowKey} from "naive-ui";
import {NButton, NCard, NDataTable, NFlex, NSwitch, NInput} from "naive-ui";
import {usePagination, useRequest} from "alova/client";
import {changeTaskStatus, deleteTask, fetchQuartzTaskPage, pauseTask, resumeTask, runTask} from "/@/api/quartz";
import {IJob} from "/@/api/types/common.ts";
import {h, onMounted, ref, useTemplateRef, watch} from "vue";
import JobInfoModel from "/@/views/quartz/JobInfoModel.vue";
import TPagination from '/@/components/t-pagination/index.vue'

const jobInfoModel = useTemplateRef<InstanceType<typeof JobInfoModel>>('jobInfoModel')
const jobInfo = ref()
const keyword = ref<string>('')
const {
  data, send: loadJob, page, pageSize, total
} = usePagination(
    // Method实例获取函数，它将接收page和pageSize，并返回一个Method实例
    (page, pageSize) => fetchQuartzTaskPage(page, pageSize, keyword.value),
    {
      immediate: false,
      // 请求前的初始数据（接口返回的数据格式）
      initialData: {
        total: 0,
        data: []
      },
      data: response => response.records,
      total: response => response.totalRow,
      initialPage: 1, // 初始页码，默认为1
      initialPageSize: 10 // 初始每页数据条数，默认为10
    }
);
const columns: DataTableColumns<IJob> = [
  {
    type: 'selection',
  },
  {
    title: '任务名称',
    key: 'jobName'
  },
  {
    title: '任务组名',
    key: 'jobGroup'
  },
  {
    title: '调用目标字符串',
    key: 'jobClass'
  },
  {
    title: 'cron执行表达式',
    key: 'cronExpression'
  },
  {
    title: '状态',
    key: 'status',
    render(row) {
      return h(NSwitch, {
        checkedValue: '0',
        uncheckedValue: '1',
        value: row.status,
        onUpdateValue: () => handleChangeJobStatus(row)
      }, {})
    }
  },
  {
    title: 'remark',
    key: 'remark'
  },
  {
    title: '操作',
    key: 'actions',
    render(row) {
      return h(NFlex, {}, {
        default: () => [
          h(NButton, {text: true, type: 'info', onClick: () => handleRunTask(row)}, {default: () => "run"}),
          h(NButton, {text: true, type: 'warning', onClick: () => handleEditJob(row)}, {default: () => "edit"}),
          h(NButton, {
            text: true,
            type: 'warning',
            disabled: row.status === '1',
            onClick: () => handlePauseTask(row)
          }, {default: () => "pause"}),
          h(NButton, {
            text: true,
            type: 'success',
            disabled: row.status === '0',
            onClick: () => handleResumeTask(row)
          }, {default: () => "resume"}),
          h(NButton, {text: true, type: 'error', onClick: () => handleDeleteTask(row)}, {default: () => "delete"})
        ]
      })
    }
  }
]
const showModal = ref(false)
const checkedRowKeysRef = ref<DataTableRowKey[]>([])
const handleCheck = (rowKeys: DataTableRowKey[]) => {
  checkedRowKeysRef.value = rowKeys
}
const handleChangeJobStatus = (row: IJob) => {
  const text = row.status === '0' ? '停用' : '启用'
  window.$dialog.warning({
    title: '警告',
    content: '确认要"' + text + '""' + row.jobName + '"任务吗？',
    positiveText: '确定',
    negativeText: '取消',
    onPositiveClick: async () => {
      await changeTaskStatus(row.id as string, row.status === '0' ? '1' : '0');
      await loadJob()
    }
  })
}
const {send: runTaskReq} = useRequest((param) => runTask(param), {immediate: false})
const {send: deleteTaskReq} = useRequest((param) => deleteTask(param), {immediate: false})
const {send: pauseTaskReq} = useRequest((param) => pauseTask(param), {immediate: false})
const {send: resumeTaskReq} = useRequest((param) => resumeTask(param), {immediate: false})
const handleRunTask = (data: IJob) => {
  runTaskReq(data)
}
/**
 * 暂停任务
 * @param data
 */
const handlePauseTask = async (data: IJob) => {
  window.$dialog.warning({
    title: 'tips',
    content: `确定暂停【${data.jobName}】任务？`,
    positiveText: '确定',
    negativeText: '不确定',
    maskClosable: false,
    onPositiveClick: async () => {
      await pauseTaskReq(data)
    }
  })
}
/**
 * 恢复任务
 * @param data
 */
const handleResumeTask = async (data: IJob) => {
  window.$dialog.warning({
    title: 'tips',
    content: `确定恢复【${data.jobName}】任务？`,
    positiveText: '确定',
    negativeText: '不确定',
    maskClosable: false,
    onPositiveClick: async () => {
      await resumeTaskReq(data)
    }
  })
}
const handleDeleteTask = (data: IJob) => {
  window.$dialog.error({
    title: 'tips',
    content: `确定删除【${data.jobName}】任务？`,
    positiveText: '确定',
    negativeText: '不确定',
    maskClosable: false,
    onPositiveClick: async () => {
      await deleteTaskReq(data);
      await loadJob()
    }
  })
}
/**
 * 编辑功能
 * @param data
 */
const handleEditJob = (data: IJob) => {
  showModal.value = true
  jobInfo.value = data
  jobInfo.value.param = jobInfo.value.param === null ? "" : JSON.stringify(jobInfo.value.param)
}
const handleCreateJob = () => {
  showModal.value = true
}
const handleSetPage = (param: number) => page.value = param
const handleSetPageSize = (param: number) => pageSize.value = param
watch(() => showModal.value, (newValue) => {
  if (!newValue) {
    loadJob()
  }
})
// watch(() => keyword.value, (newValue) => {
//   if (!newValue) {
//     loadJob()
//   }
// })
onMounted(() => {
  loadJob()
})
</script>

<template>
  <n-card>
    <div class="flex flex-row items-center justify-between mb-2">
      <div class="flex items-stretch">
        <n-button size="small" type="info" @click="handleCreateJob">创建任务</n-button>
        <n-button size="small" type="warning" disabled>批量暂停</n-button>
        <n-button size="small" type="success" disabled>批量恢复</n-button>
        <n-button size="small" type="error" disabled>批量删除</n-button>
      </div>
      <div class="flex flex-row gap-[8px]">
        <n-input v-model:value="keyword" placeholder="输入查询条件" clearable @keydown.enter="loadJob"/>
      </div>
    </div>
    <n-data-table
        :columns="columns"
        :data="data"
        :row-key="(row:IJob)=>row.id as string"
        @update:checked-row-keys="handleCheck"
    />
    <t-pagination :page-size="pageSize" :page="page" :count="total"
                  @update-page="handleSetPage"
                  @update-page-size="handleSetPageSize"/>
  </n-card>

  <job-info-model ref="jobInfoModel" v-model:showModal="showModal" v-model:job-info="jobInfo"/>
</template>

<style scoped>

</style>