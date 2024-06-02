import axios from "axios";
import store from "@/stores";
import segments from "./segments";

const request = axios.create({
    baseURL: segments.host,
    timeout: 10000000,
    responseType: "blob",
});

request.interceptors.request.use(
    (config) => {
        config.headers["Authorization"] = "Bearer " + store.getters["auth/ACCESS_TOKEN"];

        return config;
    },
    (error) => {
        console.log(error);
        return Promise.reject(error);
    }
);

request.interceptors.response.use(
    (response) => {
        if (parseInt(response.status) === 200) {
            return response.data;
        }
        return Promise.reject(response.data);
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default request;
