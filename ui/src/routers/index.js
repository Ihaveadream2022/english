import Vue from "vue";
import VueRouter from "vue-router";
import store from "@/stores";

Vue.use(VueRouter);

const routes = [
    {
        name: "login",
        path: "/login",
        component: () => import(/* webpackChunkName: 'dynamic' */ "@/views/public/login.vue"),
    },
    {
        name: "",
        path: "/",
        redirect: "/index",
        component: () => import(/* webpackChunkName: 'dynamic' */ "@/views/layout/index.vue"),
        children: [
            {
                name: "index",
                path: "/index",
                component: () => import(/* webpackChunkName: 'dynamic' */ "@/views/common/index.vue"),
            },
            {
                name: "authUser",
                path: "/authUser",
                component: () => import(/* webpackChunkName: 'dynamic' */ "@/views/auth/user/index.vue"),
            },
            {
                name: "authRole",
                path: "/authRole",
                component: () => import(/* webpackChunkName: 'dynamic' */ "@/views/auth/role/index.vue"),
            },
            {
                name: "item",
                path: "/item",
                component: () => import(/* webpackChunkName: 'dynamic' */ "@/views/item/index.vue"),
            },
            {
                name: "grammar",
                path: "/grammar",
                component: () => import(/* webpackChunkName: 'dynamic' */ "@/views/grammar/index.vue"),
            },
        ],
    },
    {
        path: "*",
        name: "404",
        component: () => import(/* webpackChunkName: 'dynamic' */ "@/views/common/404.vue"),
    },
];

const router = new VueRouter({
    routes,
    mode: "hash",
    scrollBehavior: () => ({ y: 0 }),
});

router.beforeEach(async (to, from, next) => {
    if (store.state.auth.AUTH_INFO) {
        if (to.path === "/login") {
            next({ path: "/" });
        } else {
            next();
        }
    } else {
        if (to.path === "/login" || to.path === "/other") {
            next();
        } else {
            next({ path: "/login" });
        }
    }
});

export default router;
