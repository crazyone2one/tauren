import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import type {App} from "vue";

// 安装pinia全局状态库
export function installPinia(app: App) {
    const pinia = createPinia()
    pinia.use(piniaPluginPersistedstate)
    app.use(pinia)
}