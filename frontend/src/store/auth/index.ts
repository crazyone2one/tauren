import {defineStore} from "pinia";
import {fetchLogin} from "/@/api/login/index.ts";
import {cleanToken, setToken} from "/@/utils/auth.ts";


export const useAuthStore = defineStore('auth', {
    actions: {
        async login(username: string, password: string) {
            try {
                const data = await fetchLogin({username, password})
                // 处理登录信息
                await this.handleLoginInfo(data)
            } catch (e) {
                cleanToken()
                console.warn('[Login Error]:', e)
            }
        },
        async handleLoginInfo(data: any) {
            setToken(data.access_token, data.refresh_token)
        }
    }
})