<script setup lang="ts">
import type {FormInst, FormRules} from "naive-ui";
import {
  NButton,
  NFlex,
  NForm,
  NFormItemGi,
  NGrid,
  NInput,
  NModal,
  NRadioButton,
  NRadioGroup,
  NTooltip,
  NSelect
} from "naive-ui";
import {IJob} from "/@/api/types/common.ts";
import {computed, ref, watch} from "vue";
import {useForm} from "alova/client";
import {createTask, modifyTask} from "/@/api/quartz";

const showModal = defineModel<boolean>('showModal', {default: false})
const jobInfo = defineModel<IJob>('jobInfo', {
  default: () => {
  }
})
const formRef = ref<FormInst | null>(null)
const headerTitle = computed(() => {
  return jobInfo.value?.id ? '修改' + jobInfo.value.jobName : '创建'
})
const rules: FormRules = {
  jobName: [{required: true, message: '任务名称不能为空', trigger: 'blur'}],
  invokeTarget: [{required: true, message: '调用目标字符串不能为空', trigger: 'blur'}],
  cronExpression: [{required: true, message: 'cron执行表达式不能为空', trigger: 'blur'}],
}
const jobGroupOptions = [
  {label: '默认分组', value: 'DEFAULT'},
  {label: '系统分组', value: 'SYSTEM'}
];
const handleCancel = () => {
  showModal.value = false
}
const {form, send: submit} = useForm(formData => {
  return formData.id ? modifyTask(formData) : createTask(formData)
}, {
  initialForm: {
    jobName: '',
    jobGroup: 'DEFAULT',
    cronExpression: '',
    invokeTarget: '',
    misfirePolicy: '3',
    concurrent: '0',
    status: '1',
    id: undefined,
    cronJob: true
  },
  resetAfterSubmiting: true
})
const handleSave = (e: MouseEvent) => {
  e.preventDefault()
  formRef.value?.validate(e => {
    if (!e) {
      submit().then(() => {
        showModal.value = false
        formRef.value?.restoreValidation()
      })
    }
  })
}
watch(() => jobInfo.value, (newValue) => {
  form.value = newValue
})
</script>

<template>
  <n-modal v-model:show="showModal" preset="dialog" title="Dialog" style="width: 50%">
    <template #header>
      <div>{{ headerTitle }}</div>
    </template>
    <div>
      <n-form
          ref="formRef"
          :model="form"
          :rules="rules"
          label-placement="left"
          label-width="auto"
          require-mark-placement="right-hanging"
      >
        <n-grid :cols="24" :x-gap="24">
          <n-form-item-gi :span="12" label="任务名称" path="jobName">
            <n-input v-model:value="form.jobName" placeholder="请输入任务名称"/>
          </n-form-item-gi>
          <n-form-item-gi :span="12" label="任务分组" path="jobGroup">
            <!--            <n-input v-model:value="form.jobGroup" placeholder="请选择任务分组"/>-->
            <n-select v-model:value="form.jobGroup" :options="jobGroupOptions"/>
          </n-form-item-gi>
        </n-grid>
        <n-grid>
          <n-form-item-gi :span="24" label="cron表达式" path="cronExpression">
            <n-input v-model:value="form.cronExpression" placeholder="请输入cron执行表达式"/>
          </n-form-item-gi>
        </n-grid>
        <n-grid>
          <n-form-item-gi :span="24" label="调用方法" path="invokeTarget">
            <n-input v-model:value="form.invokeTarget" placeholder="请输入调用目标字符串"/>
            <n-tooltip>
              <template #trigger>
                <n-button text class="i-my-icons:question"/>
              </template>
              <div>Bean调用示例：ryTask.ryParams('ry')</div>
              <br>Class类调用示例：com.example.quartz.job.customTask.params('hello')
              <br>参数说明：支持字符串，布尔类型，长整型，浮点型，整型
            </n-tooltip>
          </n-form-item-gi>
        </n-grid>
        <n-grid>
          <n-form-item-gi :span="24" label="执行策略" path="inputValue">
            <n-radio-group v-model:value="form.misfirePolicy" name="misfirePolicy">
              <n-radio-button value="1">立即执行</n-radio-button>
              <n-radio-button value="2">执行一次</n-radio-button>
              <n-radio-button value="3">放弃执行</n-radio-button>
            </n-radio-group>
          </n-form-item-gi>
        </n-grid>
        <n-grid>
          <n-form-item-gi :span="12" label="是否并发" path="inputValue">
            <n-radio-group v-model:value="form.concurrent" name="concurrent">
              <n-radio-button value="0">允许</n-radio-button>
              <n-radio-button value="1">禁止</n-radio-button>
            </n-radio-group>
          </n-form-item-gi>
        </n-grid>
      </n-form>
    </div>
    <template #action>
      <n-flex>
        <n-button secondary size="small" @click="handleCancel">cancel</n-button>
        <n-button type="primary" size="small" @click="handleSave">ok</n-button>
      </n-flex>
    </template>
  </n-modal>
</template>

<style scoped>
.text-wrapper {
  white-space: pre-wrap;
}
</style>