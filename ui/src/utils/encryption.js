import CryptoJS from "crypto-js";

export const encryption = (params) => {
    let { data, type, param, key } = params;
    const result = JSON.parse(JSON.stringify(data));
    if (type === "Base64") {
        param.forEach((ele) => {
            result[ele] = btoa(result[ele]);
        });
    } else {
        key = CryptoJS.enc.Latin1.parse(key);
        param.forEach((ele) => {
            var data = result[ele];
            var iv = key;
            // 加密
            var encrypted = CryptoJS.AES.encrypt(data, key, {
                iv: iv,
                mode: CryptoJS.mode.CBC,
                padding: CryptoJS.pad.ZeroPadding,
            });
            result[ele] = encrypted.toString();
        });
    }
    return result;
};