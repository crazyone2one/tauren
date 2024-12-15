import {createAlova} from "alova";
import {createServerTokenAuthentication} from "alova/client";
import adapterFetch from "alova/fetch";
import VueHook from "alova/vue";
import {getToken} from "/@/utils/auth.ts";
import {fetchUpdateToken} from "/@/api/login";


const {onAuthRequired, onResponseRefreshToken} =
    createServerTokenAuthentication({
        refreshTokenOnSuccess: {
            // 当服务端返回401时，表示token过期
            isExpired: (response, method) => {
                const isExpired = method.meta && method.meta.isExpired;
                return response.status === 401 && !isExpired;
            },
            handler: async (_response, method) => {
                if (!method.meta) {
                    method.meta = {isExpired: true};
                } else {
                    method.meta.isExpired = true;
                }
                console.log('token 过期了')
                fetchUpdateToken(getToken().refreshToken).then(res => {
                    console.log(res)
                })
            },
        },
    });
export const alovaInstance = createAlova({
    requestAdapter: adapterFetch(),
    baseURL: import.meta.env.VITE_APP_BASE_API,
    timeout: 5000,
    statesHook: VueHook,
    beforeRequest: onAuthRequired((method) => {
        if (!method.meta?.ignoreToken) {
            method.config.headers.Authorization = `Bearer ${getToken().accessToken}`;
        }
    }),
    responded: onResponseRefreshToken({
        onSuccess: async (response, method) => {
            const {status} = response;

            if (status === 200) {
                // 返回blob数据
                if (method.meta?.isBlob) {
                    return response.blob();
                }
                const json = await response.json();
                if (json.code === 200) {
                    return json.data;
                }
            }
        },
        onError: (error, method) => {
            const tip = `[${method.type}] - [${method.url}] - ${error.message}`;
            window.$message?.warning(tip);
        },
    }),
});
