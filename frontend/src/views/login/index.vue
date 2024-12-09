<script setup lang="ts">
import {NForm, NFormItem, NButton, NInput, NEl, NH3} from "naive-ui";
import type {FormInst} from 'naive-ui'
import {computed, ref} from "vue";
import {useAuthStore} from "/@/store/auth/index.ts";
import {useRouter} from "vue-router";

const authStore = useAuthStore()
const router = useRouter();
const formRef = ref<FormInst | null>(null);
const formValue = ref(({
  username: "",
  password: ''
}))
const rules = computed(() => {
  return {
    username: {required: true, message: '请输入账号', trigger: 'blur'},
    password: {required: true, message: '请输入密码', trigger: 'blur'},
  }
})
const handleLogin = (e: MouseEvent) => {
  e.preventDefault()
  formRef.value?.validate(async e => {
    if (!e) {
      const {username, password} = formValue.value
      await authStore.login(username, password)
      const {redirect, ...othersQuery} = router.currentRoute.value.query;
      await router.push({
        path: redirect as string,
        query: {
          ...othersQuery
        }
      })
      window.$message.success("登录成功")
    }
  })
}
</script>

<template>
  <n-el class="wh-full flex-center">
    <n-el class="p-4xl h-full w-full sm:w-450px sm:h-700px">
      <div class="w-full flex flex-col items-center">
        <n-h3>xxx</n-h3>
        <div class="w-85%">
          <n-form ref="formRef" :model="formValue" :rules="rules" :show-label="false">
            <n-form-item label="账号" path="username">
              <n-input v-model:value="formValue.username" clearable placeholder="输入账号"/>
            </n-form-item>
            <n-form-item label="密码" path="password">
              <n-input v-model:value="formValue.password" type="password" clearable show-password-on="click"
                       placeholder="输入密码"/>
            </n-form-item>
            <n-form-item>
              <n-button block type="primary" size="large" @click="handleLogin">
                登录
              </n-button>
            </n-form-item>
          </n-form>
        </div>

      </div>
    </n-el>


  </n-el>
</template>

<style scoped>

</style>