import { createApp } from "vue";
import App from "./App.vue";
import AppLoad from "./components/AppLoad.vue";
import router from "./router";
import "./style.css";
const appLoading = createApp(AppLoad);
appLoading.mount("#appLoading");

const app = createApp(App);
app.use(router);
// 卸载载入动画
appLoading.unmount();
app.mount("#app");
