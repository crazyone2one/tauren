<script setup lang="ts">
import type {DataTableColumns, DataTableRowKey} from "naive-ui";
import {NButton, NCard, NDataTable, NFlex, NSwitch} from "naive-ui";
import {usePagination, useRequest} from "alova/client";
import {changeTaskStatus, deleteTask, fetchQuartzTaskPage, pauseTask, resumeTask, runTask} from "/@/api/quartz";
import {IJob, ITableQueryParams} from "/@/api/types/common.ts";
import {h, onMounted, ref, useTemplateRef, watch} from "vue";
import JobInfoModel from "/@/views/quartz/JobInfoModel.vue";

const jobInfoModel = useTemplateRef<InstanceType<typeof JobInfoModel>>('jobInfoModel')
const jobInfo = ref()
const {
  data, send: loadJob
} = usePagination(
    // Method实例获取函数，它将接收page和pageSize，并返回一个Method实例
    (page, pageSize) => {
      const param: ITableQueryParams = {page, pageSize};
      return fetchQuartzTaskPage(param)
    },
    {
      immediate: false,
      // 请求前的初始数据（接口返回的数据格式）
      initialData: {
        total: 0,
        data: []
      },
      data: response => response.records,
      total: response => response.total,
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
    key: 'invokeTarget'
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
          h(NButton, {text: true, type: 'warning', onClick: () => handlePauseTask(row)}, {default: () => "pause"}),
          h(NButton, {text: true, type: 'warning', onClick: () => handleResumeTask(row)}, {default: () => "resume"}),
          h(NButton, {text: true, type: 'error', onClick: () => handleDeleteTask(row)}, {default: () => "delete"})
        ]
      })
    }
  }
]
const pagination = {pageSize: 5}
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
const handlePauseTask = async (data: IJob) => {
  await pauseTaskReq(data)
}
const handleResumeTask = async (data: IJob) => {
  await resumeTaskReq(data)
}
const handleDeleteTask = async (data: IJob) => {
  await deleteTaskReq(data);
  await loadJob()
}
const handleEditJob = (param: IJob) => {
  showModal.value = true
  jobInfo.value = param
}
const handleCreateJob = () => {
  showModal.value = true
}
watch(() => showModal.value, (newValue) => {
  if (!newValue) {
    loadJob()
  }
})
onMounted(() => {
  loadJob()
})
</script>

<template>
  <n-card>
    <n-button size="tiny" type="info" @click="handleCreateJob">创建任务</n-button>
    <n-data-table
        :columns="columns"
        :data="data"
        :pagination="pagination"
        :row-key="(row:IJob)=>row.id as string"
        @update:checked-row-keys="handleCheck"
    />
  </n-card>

  <job-info-model ref="jobInfoModel" v-model:showModal="showModal" v-model:job-info="jobInfo"/>
</template>

<style scoped>

</style>