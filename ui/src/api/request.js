import segments from "./segments";
import requestBase from "./requestBase";
import requestBaseFile from "./requestBaseFile";

// 省市县三级联动
export const areasList = (paramters) => requestBase.post(`${segments.urlSegmentCommon}/api/bAreasList`, paramters);

// 围栏
export const fenceList = (paramters) => requestBase.post(`${segments.urlSegmentVGS}/fence/tmsList`, paramters);
export const fenceUpdate = (paramters) => requestBase.post(`${segments.urlSegmentVGS}/fence/update`, paramters);
export const fenceCreate = (paramters) => requestBase.post(`${segments.urlSegmentVGS}/fence/add`, paramters);
export const fenceDelete = (paramters) => requestBase.post(`${segments.urlSegmentVGS}/fence/delete`, paramters);

/** 星瀚 **/
// 打包
export const packageList = (paramters) => requestBase.post(`${segments.urlSegmentStock}/packag/getPackagRecordList`, paramters);

/** 中厚板 **/
// 非道路机械台账页面接口
export const noroadinfoList = (paramters) => requestBase.post(segments.urlDriverName + "/vehicle/noroadinfo/list", paramters);
export const noroadinfoListExport = (paramters) => requestBaseFile.post(segments.urlDriverName + "/vehicle/noroadinfo/exportList", paramters);
export const noroadinfoAdd = (paramters) => requestBase.post(segments.urlDriverName + "/vehicle/noroadinfo/add", paramters);
export const noroadinfoUpdate = (paramters) => requestBase.post(segments.urlDriverName + "/vehicle/noroadinfo/update", paramters);
export const noroadinfoDelete = (paramters) => requestBase.post(segments.urlDriverName + "/vehicle/noroadinfo/delete", paramters);

// 车辆管理
export const cVIlist = (paramters) => requestBase.post(segments.urlDriverName + "/cVehicleInfo/list", paramters);

// 台账预警
export const warningList = (paramters) => requestBase.post(segments.urlDriverName + "/warning/list", paramters);
export const warningListExport = (paramters) => requestBaseFile.post(segments.urlDriverName + "/warning/exportlist", paramters);
export const warningDelete = (paramters) => requestBase.post(segments.urlDriverName + "/warning/delete", paramters);
export const warningUpdate = (paramters) => requestBase.post(segments.urlDriverName + "/warning/update", paramters);

// 进仓登记
export const getQueueSettingsList = (paramters) => requestBase.post(segments.urlLeju + "/factory/getQueueSettingsList", paramters);
export const getJobStatusList = (paramters) => requestBase.post(segments.urlLeju + "/factory/getJobStatusList", paramters);
export const getSchedulingAssignmentList = (paramters) => requestBase.post(segments.urlLeju + "/factory/getSchedulingAssignmentList", paramters);

export const searchPhoneNumber = (paramters) => requestBase.post(segments.urlLeju + "/driver/listByAccount", paramters);
export const register = (paramters) => requestBase.post(segments.urlLeju + "/scheduling/vehicleRegisterPC", paramters);

export const driverVehicleInList = (paramters) => requestBase.post(segments.urlSegmentStock + "/driverVehicleInformation/getDriverVehicleInList", paramters);
export const markDriverVehicle = (paramters) => requestBase.post(segments.urlSegmentStock + "/driverVehicleInformation/markDriverVehicleIn", paramters);
export const deleteDriverVehicle = (paramters) => requestBase.post(segments.urlSegmentStock + "/driverVehicleInformation/deleteDriverVehicleIn", paramters);
export const detailByIDDriverVehicle = (paramters) => requestBase.post(segments.urlSegmentStock + "/driverVehicleInformation/detail", paramters);
export const detailByPlateNumberDriverVehicle = (paramters) => requestBase.post(segments.urlSegmentStock + "/driverVehicleInformation/getDriverVehicleInfo", paramters);

export const yearWaterTransList = (paramters) => requestBase.post(segments.urlDataCenter + "/dShipDate/weightByMonth", paramters);
export const recordList = (paramters) => requestBase.post(segments.urlDataCenter + "/dShipDate/shipDateList", paramters);
export const listTotal = (paramters) => requestBase.post(segments.urlDataCenter + "/dShipDate/sumHistoryWeight", paramters);
export const waterStatusList = (paramters) => requestBase.post(segments.urlDataCenter + "/dShipDate/statusCount", paramters);
export const listTrans = (paramters) => requestBase.post(segments.urlDataCenter + "/freighter/listTrans", paramters);
export const addressList = (paramters) => requestBase.post(segments.urlDataCenter + "/freighter/addressList", paramters);
export const codeDataList = (paramters) => requestBase.post(segments.urlDataCenter + "/dShipDate/shipAddressAndCoordinate", paramters);

// User
export const userList = (params) =>
    requestBase.request({
        method: "get",
        url: segments.urlAss + "/users",
        params: params,
    });
export const userAdd = (data) =>
    requestBase.request({
        method: "post",
        url: segments.urlAss + "/users",
        data: data,
    });
export const userEdit = (data) =>
    requestBase.request({
        method: "put",
        url: segments.urlAss + "/users/" + data.id,
        data: data,
    });
export const userDelete = (data) =>
    requestBase.request({
        method: "delete",
        url: segments.urlAss + "/users/" + data.id,
    });

// Role
export const roleList = (params) =>
    requestBase.request({
        method: "get",
        url: segments.urlAss + "/roles",
        params: params,
    });
export const roleAdd = (data) =>
    requestBase.request({
        method: "post",
        url: segments.urlAss + "/roles",
        data: data,
    });
export const roleEdit = (data) =>
    requestBase.request({
        method: "put",
        url: segments.urlAss + "/roles/" + data.id,
        data: data,
    });
export const roleDelete = (data) =>
    requestBase.request({
        method: "delete",
        url: segments.urlAss + "/roles/" + data.id,
    });
export const roleBatchDelete = (data) =>
    requestBase.request({
        method: "post",
        url: segments.urlAss + "/roles/batchDelete",
        data: data,
    });
export const profileUpdatePassword = (paramters) => requestBase.put(segments.urlAss + "/profile/updatePassword", paramters);

// Item
export const itemList = (params) =>
    requestBase.request({
        method: "get",
        url: segments.urlAss + "/items",
        params: params,
    });
export const itemAdd = (data) =>
    requestBase.request({
        method: "post",
        url: segments.urlAss + "/items",
        data: data,
    });
export const itemEdit = (data) =>
    requestBase.request({
        method: "put",
        url: segments.urlAss + "/items/" + data.id,
        data: data,
    });

export const itemDelete = (data) =>
    requestBase.request({
        method: "delete",
        url: segments.urlAss + "/items/" + data.id,
    });
export const itemGenerate = () =>
    requestBase.request({
        method: "get",
        url: segments.urlAss + "/items/generate",
    });

// Grammar
export const grammarList = (params) =>
    requestBase.request({
        method: "get",
        url: segments.urlAss + "/grammars",
        params: params,
    });
export const grammarAdd = (data) =>
    requestBase.request({
        method: "post",
        url: segments.urlAss + "/grammars",
        data: data,
    });
export const grammarEdit = (data) =>
    requestBase.request({
        method: "put",
        url: segments.urlAss + "/grammars/" + data.id,
        data: data,
    });

export const grammarDelete = (data) =>
    requestBase.request({
        method: "delete",
        url: segments.urlAss + "/grammars/" + data.id,
    });
export const grammarGenerate = () =>
    requestBase.request({
        method: "get",
        url: segments.urlAss + "/grammars/generate",
    });

// Placeholder
export const placeholder = (params) =>
    requestBase.request({
        method: "get",
        url: segments.urlAss + "/placeholder",
        params: params,
    });

export const ttsGenerate = () =>
    requestBase.request({
        method: "get",
        url: segments.urlAss + "/tts/generate",
    });

export const GitPush = () =>
    requestBase.request({
        method: "get",
        url: segments.urlAss + "/git/push",
    });
