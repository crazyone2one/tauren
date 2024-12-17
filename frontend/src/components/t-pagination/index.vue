<script setup lang="ts">
import {NPagination} from "naive-ui";
import {ref, watchEffect} from "vue";

const props = withDefaults(defineProps<{
  page: number
  pageSize: number,
  count: number,
  pageSlot?: number
}>(), {
  pageSlot: 10,
  page: 1,
  count: 0,
  pageSize: 10
})
const emits = defineEmits(['updatePage', 'updatePageSize']);
const currentPage = ref(props.page)
const currentPageSize = ref(props.pageSize)
const handleUpdatePageSize = (pageSize: number) => {
  emits('updatePageSize', pageSize)
}
const handleUpdatePage = (page: number) => {
  emits('updatePage', page)
}
watchEffect(() => {
  currentPage.value = props.page;
  currentPageSize.value = props.pageSize;
});
</script>

<template>
  <div class="mt-2 flex justify-end">
    <n-pagination v-model:page="currentPage"
                  v-model:page-size="currentPageSize"
                  :item-count="props.count"
                  show-size-picker
                  :page-sizes="[10, 20, 30, 40]"
                  :page-slot="pageSlot"
                  @update:page="handleUpdatePage"
                  @update:page-size="handleUpdatePageSize"/>
  </div>
</template>

<style scoped>

</style>