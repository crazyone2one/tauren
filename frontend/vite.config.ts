import vue from "@vitejs/plugin-vue";
import * as path from "node:path";
import {defineConfig, loadEnv} from "vite";
import UnoCSS from 'unocss/vite'
// https://vite.dev/config/
export default defineConfig(({mode}) => {
    // Load env file based on `mode` in the current working directory.
    // Set the third parameter to '' to load all env regardless of the `VITE_` prefix.
    const env = loadEnv(mode, process.cwd(), "");
    console.log(env.APP_ENV);

    return {
        // vite config
        plugins: [vue(), UnoCSS()],
        server: {
            host: true,
            proxy: {
                [env.VITE_APP_BASE_API]: {
                    target: env.VITE_APP_PROXY_URL,
                    changeOrigin: true,
                    rewrite: (path) =>
                        path.replace(new RegExp("^" + env.VITE_APP_BASE_API), ""),
                },
            },
        },
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
