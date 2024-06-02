/* eslint-disable */
const IS_PROD = process.env.NODE_ENV === "production" ? true : false;
const TMS = {
    // eslint-disable-line
    YB: {
        APP_TYPE: "TMS",
        APP_DATA_PREFIX: IS_PROD ? "TMS_YB_" : "TMS_DEV_YB_",
        APP_HOST: IS_PROD ? "" : "https://tms.zw56.cn",
        APP_TITLE: "绝味食品股份有限公司",
    },
};
const EN = {
    // eslint-disable-line
    EN: {
        APP_TYPE: "EN",
        APP_DATA_PREFIX: IS_PROD ? "EN_" : "EN_DEV_",
        APP_HOST: IS_PROD ? "http://localhost:8080" : "http://localhost:8080",
        APP_TITLE: "English",
    },
};
module.exports = EN.EN;
