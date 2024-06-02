import Cookies from "js-cookie";
import { OAuthLogin, OAuthLogout, OAuthGetMenu } from "../../api/requestOAuth";
export default {
    namespaced: true,
    state: {
        AUTH_INFO: Cookies.get(`${process.env.APP.APP_DATA_PREFIX}AUTH`),
        MENU_INFO: [],
    },
    mutations: {
        AUTH_INFO(state, auth) {
            const jsonStr = JSON.stringify(auth);
            state.AUTH_INFO = jsonStr;
            if (auth.expires_in) {
                Cookies.set(`${process.env.APP.APP_DATA_PREFIX}AUTH`, jsonStr, { expires: Math.ceil(auth.expires_in / 86400) });
            } else {
                Cookies.set(`${process.env.APP.APP_DATA_PREFIX}AUTH`, jsonStr);
            }
        },
        AUTH_INFO_CLEAR(state) {
            state.AUTH_INFO = "";
            Cookies.remove(`${process.env.APP.APP_DATA_PREFIX}AUTH`);
        },
        MENU_INFO(state, menu) {
            state.MENU_INFO = menu;
        },
        MENU_INFO_CLEAR(state) {
            state.MENU_INFO = [];
        },
    },
    actions: {
        Login(context, form) {
            return new Promise((resolve, reject) => {
                OAuthLogin(form).then(
                    (response) => {
                        console.log("actions OAuthToken:", response);
                        if (response.code === 1) {
                            context.commit("AUTH_INFO", response.data);
                            resolve(response);
                        } else {
                            reject(response);
                        }
                    },
                    (error) => {
                        console.log("actions OAuthToken error:", error);
                        reject(error);
                    }
                );
            });
        },
        Logout(context) {
            return new Promise((resolve, reject) => {
                console.log(context.getters.ACCESS_TOKEN_PURE);
                OAuthLogout(context.getters.ACCESS_TOKEN_PURE).then(
                    (response) => {
                        console.log("actions OAuthTokenClear: ", response);
                        if (response.code === 1) {
                            context.commit("AUTH_INFO_CLEAR");
                            context.commit("MENU_INFO_CLEAR");
                            resolve(response);
                        } else {
                            console.log("actions OAuthTokenClear: ", response);
                            reject(response);
                        }
                    },
                    (error) => {
                        reject(error);
                    }
                );
            });
        },
        OAuthMenu(context) {
            return new Promise((resolve, reject) => {
                OAuthGetMenu(context.getters["ACCESS_TOKEN"], { tenantCode: context.getters["USER_INFO"]["tenantList"][0]["tenantCode"] ? context.getters["USER_INFO"]["tenantList"][0]["tenantCode"] : context.getters["USER_INFO"]["tenantCode"] }).then(
                    (response) => {
                        console.log("menu", response);
                        if (response.code === 1) {
                            context.commit("MENU_INFO", JSON.parse(response.data));
                            resolve(response);
                        } else if (parseInt(response.code) === 40001) {
                            context.commit("AUTH_INFO_CLEAR");
                            context.commit("MENU_INFO_CLEAR");
                        } else {
                            reject(response);
                        }
                    },
                    (error) => {
                        reject(error);
                    }
                );
            });
        },
    },
    getters: {
        AUTH_INFO: (state) => {
            if (state.AUTH_INFO) {
                const AUTH = JSON.parse(state.AUTH_INFO);
                const expired = Date.now() - parseInt(AUTH.create_time) < parseInt(AUTH.expires_in);
                if (AUTH && expired) {
                    return AUTH;
                } else {
                    return {};
                }
            } else {
                return {};
            }
        },
        ACCESS_TOKEN: (state) => {
            if (state.AUTH_INFO) {
                const AUTH_INFO = JSON.parse(state.AUTH_INFO);
                let token = AUTH_INFO.access_token;
                let create_time = AUTH_INFO.create_time;
                let expires_in = AUTH_INFO.expires_in;
                let expires_from = expires_in * 0.3;
                let starttime = create_time + expires_from;
                let endtime = create_time + (expires_in - 10000);
                let currTime = Date.now();
                if (currTime > starttime && currTime < endtime) {
                    return "refresh_token";
                } else {
                    return token;
                }
            } else {
                return "";
            }
        },
        ACCESS_TOKEN_PURE: (state) => {
            if (state.AUTH_INFO) {
                const AUTH_INFO = JSON.parse(state.AUTH_INFO);
                if (AUTH_INFO.access_token) {
                    return AUTH_INFO.access_token;
                } else {
                    return "";
                }
            } else {
                return "";
            }
        },
        MENU_INFO: (state) => (state.MENU_INFO ? state.MENU_INFO : []),
    },
};
