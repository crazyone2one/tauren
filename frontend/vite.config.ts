import vue from "@vitejs/plugin-vue";
import * as path from "node:path";
import {defineConfig, loadEnv} from "vite";
import UnoCSS from 'unocss/vite'
// https://vite.dev/config/
export default defineConfig(({command, mode}) => {
    // Load env file based on `mode` in the current working directory.
    // Set the third parameter to '' to load all env regardless of the `VITE_` prefix.
    const env = loadEnv(mode, process.cwd(), "");
    const serverConfig: { [key: string]: boolean | object } = {
        host: true,
        proxy: {
            '/api': {
                target: env.VITE_APP_PROXY_URL,
                changeOrigin: true,
                // rewrite: (path: string) => path.replace(/^\/api/, ""),
            },
        },
    }
    // todo 根据command的类型，处理是否增加rewrite配置
    if (command === 'serve') {
        // dev 独有配置
        serverConfig.proxy = {
            '/api': {
                target: env.VITE_APP_PROXY_URL,
                changeOrigin: true,
                rewrite: (path: string) => path.replace(/^\/api/, ""),
            },
        };
    } else {
        serverConfig.proxy = {
            '/api': {
                target: env.VITE_APP_PROXY_URL,
                changeOrigin: true,
            },
        };
    }
    return {
        // vite config
        plugins: [vue(), UnoCSS()],
        server: serverConfig,
        resolve: {
            alias: [
                {
                    find: /\/@\//,
                    replacement: path.resolve(__dirname, ".", "src") + "/",
                },
            ],
            extensions: [".mjs", ".js", ".ts", ".jsx", ".tsx", ".json", ".vue"], // 自动匹配文件后缀名
        },
        build: {
            rollupOptions: {
                output: {
                    manualChunks: {
                        "naive-ui": ["naive-ui"],
                    },
                },
            },
        },
        define: {
            __APP_ENV__: JSON.stringify(env.APP_ENV),
            __APP_VERSION__: JSON.stringify("v1.0.0"),
        },
    };
});
