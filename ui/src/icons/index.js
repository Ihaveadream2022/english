import Vue from "vue";
import SvgIcon from "@/components/SvgIcon";

Vue.component("svg-icon", SvgIcon);

const webpackContext = require.context("./svg", false, /(up|tab|fullscreen|eye|eye-open|fold|dashboard|lock|table|zip|user|password)\.svg$/);
webpackContext.keys().map(webpackContext);
