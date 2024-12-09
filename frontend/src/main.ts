import {createApp} from "vue";
import App from "./App.vue";
// import AppLoad from "./components/AppLoad.vue";
import router from "./router";
import "./styles/index.css";
import {installPinia} from '/@/store/index.ts'
import 'virtual:uno.css'

const setupApp = async () => {
    const app = createApp(App);
    // const appLoading = createApp(AppLoad);
    // appLoading.mount("#appLoading");
    app.use(router);
    installPinia(app)
    // 卸载载入动画
    // appLoading.unmount();
    app.mount("#app");
}
setupApp().then(() => console.log('welcome to tauren.'))

