import axios from "axios";
import segments from "./segments";

const OAuthRequest = axios.create({
    baseURL: `${segments.host}/`,
    timeout: 10000000,
});

OAuthRequest.interceptors.response.use(
    (response) => {
        if (parseInt(response.status) === 200 && parseInt(response.data.code) === 1) {
            return response.data;
        }
        return Promise.reject(response.data);
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Login
const OAuthLogin = (params) => {
    return OAuthRequest.post("/login", params);
};

// Logout
const OAuthLogout = (access_token) => {
    return OAuthRequest.request({
        headers: { Authorization: "Bearer " + access_token },
        method: "get",
        url: "/logout",
    });
};

// Update Password
const OAuthUpdatePassword = (access_token, data) => {
    return OAuthRequest.request({
        headers: { Authorization: "Bearer " + access_token },
        method: "put",
        url: "/profile/updatePassword",
        data: data,
    });
};

// Load captcha
const OAuthCaptcha = () => {
    return OAuthRequest.request({
        method: "get",
        url: "/captcha",
    });
};

export { OAuthLogin, OAuthLogout, OAuthCaptcha, OAuthUpdatePassword };
