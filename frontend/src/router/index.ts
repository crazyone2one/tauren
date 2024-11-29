import { createRouter, createWebHashHistory } from "vue-router";

import HomeView from "/@/layout/index.vue";
// import AboutView from './AboutView.vue'

const routes = [
  { path: "/", component: HomeView },
  //   { path: '/about', component: AboutView },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
  scrollBehavior() {
    return { top: 0 };
  },
});
export default router;
