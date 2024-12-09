<script setup lang="ts">
import {
  NConfigProvider,
  NDialogProvider,
  NGlobalStyle,
  NLoadingBarProvider,
  NMessageProvider,
  NModalProvider,
  NNotificationProvider,
} from "naive-ui";
import ProviderView from "./components/ProviderView.vue";
import {useEventListener, useWindowSize} from "@vueuse/core";
import {onBeforeMount} from "vue";
import useAppStore from "/@/store/app";

const appStore = useAppStore()
onBeforeMount(() => {
  const {height} = useWindowSize();
  appStore.innerHeight = height.value;
})
/** 屏幕大小改变时重新赋值innerHeight */
useEventListener(window, 'resize', () => {
  const {height} = useWindowSize();
  appStore.innerHeight = height.value;
});
</script>

<template>
  <n-config-provider>
    <n-global-style/>
    <n-loading-bar-provider>
      <n-message-provider>
        <n-dialog-provider>
          <n-notification-provider>
            <n-modal-provider>
              <slot/>
              <provider-view/>
            </n-modal-provider>
          </n-notification-provider>
        </n-dialog-provider>
      </n-message-provider>
    </n-loading-bar-provider>
  </n-config-provider>
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}

.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}

.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
