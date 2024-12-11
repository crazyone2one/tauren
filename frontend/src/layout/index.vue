<script setup lang="ts">
import {NLayout, NLayoutHeader, NLayoutSider, NLayoutFooter, NMenu} from "naive-ui";
import type {MenuOption} from "naive-ui";
import useAppStore from "/@/store/app";
import {RouterLink} from "vue-router";
import {h} from "vue";

const appStore = useAppStore();
const menuOptions: MenuOption[] = [
  {
    label: () =>
        h(
            RouterLink,
            {
              to: {
                name: 'quartz',
              }
            },
            {default: () => '定时任务'}
        ),
    key: 'quartz',
  }
]

</script>
<template>
  <n-layout :style="{ height: appStore.innerHeight + 'px' }">
    <n-layout-header style="height: 64px; padding: 24px" bordered>
      颐和园路
    </n-layout-header>
    <n-layout position="absolute" style="top: 64px; bottom: 24px" has-sider>
      <n-layout-sider
          content-style="padding: 24px;"
          :native-scrollbar="false"
          bordered
      >
        <n-menu :options="menuOptions"/>
      </n-layout-sider>
      <n-layout content-style="padding: 24px;" :native-scrollbar="false">
        <router-view/>
      </n-layout>
    </n-layout>
    <n-layout-footer
        position="absolute"
        style="height: 24px; padding: 24px"
        bordered
    >
      城府路
    </n-layout-footer>
  </n-layout>

</template>


<style scoped></style>
