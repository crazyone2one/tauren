<template>
  <div>
    <n-button type="info" @click="showMsg">index</n-button>
  </div>
</template>

<script setup lang="ts">
import { NButton } from "naive-ui";
const showMsg = () => window.$message.info("message");
</script>

<style scoped></style>
