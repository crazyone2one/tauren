<script setup lang="ts">
import type {FormInst, FormRules} from "naive-ui";
import {NButton, NForm, NFormItemGi, NInput, NModal, NGrid, NRadioGroup, NRadioButton} from "naive-ui";
import {IJob} from "/@/api/types/common.ts";
import {computed, ref} from "vue";
import {useForm} from "alova/client";
import {createTask} from "/@/api/quartz";

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
const handleCancel = () => {
  showModal.value = false
}
const {form, send: submit} = useForm(formData => createTask(formData), {
  initialForm: {
    jobName: '',
    jobGroup: '',
    cronExpression: '',
    invokeTarget: '',
    misfirePolicy: '3',
    concurrent: '0',
    status: '1'
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
            <n-input v-model:value="form.jobGroup" placeholder="请选择任务分组"/>
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
      <div>
        <n-button @click="handleCancel">cancel</n-button>
        <n-button @click="handleSave">ok</n-button>
      </div>
    </template>
  </n-modal>
</template>

<style scoped>

</style>