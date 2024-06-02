import Cookies from "js-cookie";
export default {
    namespaced: true,
    state: {
        sidemenuCollapse: Cookies.get(`${process.env.APP.APP_DATA_PREFIX}MENU_COLLAPSE`) ? !!+Cookies.get(`${process.env.APP.APP_DATA_PREFIX}MENU_COLLAPSE`) : true,
    },
    mutations: {
        TOGGLE_SIDEMENU_COLLAPSE: (state, value) => {
            if (value !== undefined) {
                state.sidemenuCollapse = value;
                Cookies.set(`${process.env.APP.APP_DATA_PREFIX}MENU_COLLAPSE`, value);
            } else {
                state.sidemenuCollapse = !state.sidemenuCollapse;
                Cookies.set(`${process.env.APP.APP_DATA_PREFIX}MENU_COLLAPSE`, state.sidemenuCollapse ? 1 : 0);
            }
        },
    },
    actions: {
        toggleSidemenuCollapse({ commit }, value) {
            commit("TOGGLE_SIDEMENU_COLLAPSE", value);
        },
    },
    getters: {
        sidemenuCollapse: (state) => state.sidemenuCollapse,
    },
};
