import {createRouter, createWebHashHistory} from "vue-router";
import {hasToken} from "/@/utils/auth.ts";

import HomeView from "/@/layout/index.vue";
// import AboutView from './AboutView.vue'

const routes = [
    {
        path: "/", name: "Home", component: HomeView,
        children: [
            {
                path: '/quartz', name: 'quartz', component: () => import('/@/views/quartz/index.vue'),
                meta: {
                    title: 'quartz',
                },
            },
        ]
    },
    {
        path: '/login', name: 'login', component: () => import('/@/views/login/index.vue'),
        meta: {
            title: '登录',
        },
    },

];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
    scrollBehavior() {
        return {top: 0};
    },
});

router.beforeEach((to, _from, next) => {
    if (!hasToken()) {
        if (to.name === 'login') {
            next()
        }
        if (to.name !== 'login') {
            const redirect = to.name === '404' ? undefined : to.fullPath
            next({path: '/login', query: {redirect}})
        }
        return false
    }
    if (to.name === 'login') {
        next({path: '/'})
        return false
    }
    next()
})
export default router;
