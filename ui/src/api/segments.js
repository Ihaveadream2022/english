const host = process.env.APP.APP_HOST;
const key = "platformplatform";
const urlSegmentOAuth = "auth-server/auth-api/v2"; // 授权
const urlSegmentTMS = "tms-server/tms";
const urlSegmentVGS = "vgs-monitor-server/monitor";
const urlDriverName = "/vgs-driver-server/driver"; //中厚板
const urlLeju = "lj-server/lj";
const urlSegmentCommon = "common-signature-server/common-signature"; // 省市三级联动, 消息推送等
const urlSegmentStock = "wms-stock-server/wmsStock"; // 仓库
const urlDataCenter = "data-center-server/dataCenter";
const urlAss = "";
const headerBasic = "platform:a050c7db77eb4b1bb8b2b";

export default {
    host,
    key,
    urlSegmentOAuth,
    urlSegmentTMS,
    urlSegmentVGS,
    urlSegmentStock,
    urlSegmentCommon,
    urlDriverName,
    urlLeju,
    urlDataCenter,
    urlAss,
    headerBasic,
};
