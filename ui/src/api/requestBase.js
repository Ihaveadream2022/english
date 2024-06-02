import axios from "axios";
import store from "@/stores";
import segments from "./segments";

const request = axios.create({
    baseURL: segments.host,
    timeout: 10000000,
});

request.interceptors.request.use(
    (config) => {
        var token = store.getters["auth/ACCESS_TOKEN"];
        if (token) {
            config.headers["Authorization"] = "Bearer " + token;

            return config;
        } else {
            store.commit("auth/AUTH_INFO_CLEAR");
            store.commit("auth/MENU_INFO_CLEAR");
            window.location.href = "./#/login";
        }
    },
    (error) => {
        console.log(error);
        return Promise.reject(error);
    }
);

request.interceptors.response.use(
    (response) => {
        console.log("base response:", response);
        if (parseInt(response.status) === 200 && parseInt(response.data.code) === 1) {
            return response.data;
        }
        return Promise.reject(response.data);
    },
    (error) => {
        console.log(error);
        if (error.response !== undefined) {
            if (parseInt(error.response.status) === 401) {
                store.commit("auth/AUTH_INFO_CLEAR");
                store.commit("auth/MENU_INFO_CLEAR");
                window.location.href = "./#/login";
            } else if (parseInt(error.response.status) === 428) {
                store.commit("auth/AUTH_INFO_CLEAR");
                store.commit("auth/MENU_INFO_CLEAR");
                window.location.href = "./#/login";
            }
        }
        return Promise.reject(error);
    }
);

export default request;
