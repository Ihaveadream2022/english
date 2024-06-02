import Vue from "vue";
import store from "./stores";
import App from "./App.vue";
import scroll from "vue-seamless-scroll/src";
import ElementUI from "element-ui";
import locale from "element-ui/lib/locale/lang/en";
import "element-ui/lib/theme-chalk/index.css";

Vue.use(ElementUI, { locale });
Vue.use(scroll);

import * as filters from "./filters";
Object.keys(filters).forEach((k) => {
    Vue.filter(k, filters[k]);
});

import router from "./routers";

import "@/styles/common.scss";
import "@/styles/element.scss";
import "@/styles/amap.scss";
import "font-awesome/css/font-awesome.css"; // font-awesome

import "./icons"; // icons 模块

Vue.config.productionTip = false;

new Vue({
    router,
    store,
    render: (h) => h(App),
}).$mount("#app");
