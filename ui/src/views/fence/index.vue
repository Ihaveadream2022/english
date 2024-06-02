<template>
    <div>
        <div class="title">
            <h4>电子围栏</h4>
        </div>
        <!-- 搜索条件区域 -->
        <div class="searchBox" ref="searchBox">
            <el-form :inline="true" :model="r.searchForm" ref="searchForm" class="demo-form-inline" label-width="76px" size="mini">
                <div class="inpBox">
                    <el-form-item label="围栏名称:" prop="name">
                        <el-input placeholder="" v-model="r.searchForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="围栏类型:" prop="fenceType">
                        <el-select placeholder="请选择" v-model="r.searchForm.fenceType">
                            <el-option v-for="item in r.fenceTypes" :key="item.value" :label="item.label" :value="item.value"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="中心位置:" prop="address">
                        <el-input placeholder="" v-model="r.searchForm.address"></el-input>
                    </el-form-item>
                    <el-form-item label="启用状态:" prop="status">
                        <el-select placeholder="请选择" v-model="r.searchForm.status">
                            <el-option v-for="item in r.status" :key="item.value" :label="item.label" :value="item.value"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="创建时间:" prop="createTime">
                        <el-date-picker v-model="r.searchForm.createTime" format="yyyy-MM-dd" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" :editable="true"></el-date-picker>
                    </el-form-item>
                </div>
                <div class="searchBtnBox">
                    <el-form-item v-if="r.btnFlg[0]">
                        <el-button type="primary" @click="RHandleSearch">查询</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button v-if="r.btnFlg[1]" type="primary" @click="RHandleReset()">重置</el-button>
                    </el-form-item>
                    <el-form-item v-if="r.btnFlg[2]">
                        <el-button type="primary" @click="CUDHandleCreaet">新建</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </div>
        <!--  表格 -->
        <div class="table" style="height: 500px">
            <el-table :data="r.list" ref="table" v-loading="r.loading" bordersize="mini" @selection-change="RSelectGoods" @row-click="RHandleRowClick" height="100%" style="width: 100%" center border>
                <el-table-column type="index" prop="date" width="55" label="序号" align="center"></el-table-column>
                <el-table-column label="操作" min-width="200" align="center">
                    <template slot-scope="scope">
                        <div class="panel">
                            <el-button type="text" size="mini" v-if="r.btnFlg[3]" v-show="scope.row.status == 1" @click="CUDHandleStatus(scope.row, 2)">停用</el-button>
                            <el-button type="text" size="mini" v-if="r.btnFlg[4]" v-show="scope.row.status == 2" @click="CUDHandleStatus(scope.row, 1)">启用</el-button>
                            <el-button type="text" size="mini" v-if="r.btnFlg[5]" @click="RViewFence(scope.row)">查看围栏</el-button>
                            <el-button type="text" size="mini" v-if="r.btnFlg[6]" v-show="scope.row.status == 2" @click="CUDHandleUpdate(scope.row)">修改</el-button>
                            <el-button type="text" size="mini" v-if="r.btnFlg[7]" v-show="scope.row.status == 2" @click="CUDHandleDelete(scope.row)">删除</el-button>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column property="status" label="启用状态" align="center">
                    <template scope="scope">
                        {{ ["已启用", "已停用"][scope.row.status - 1] }}
                    </template>
                </el-table-column>
                <el-table-column prop="name" width="120" :show-overflow-tooltip="true" label="围栏名称"></el-table-column>
                <el-table-column property="fenceType" label="围栏类型" align="center">
                    <template scope="scope">
                        {{ ["中心点", "多边形"][scope.row.fenceType - 1] }}
                    </template>
                </el-table-column>
                <el-table-column prop="objKey" width="260" :show-overflow-tooltip="true" label="中心位置"></el-table-column>
                <el-table-column prop="centerCoordinate" width="220" :show-overflow-tooltip="true" label="经纬度" align="center"></el-table-column>
                <el-table-column prop="centerRadius" width="160" :show-overflow-tooltip="true" label="半径(米)" align="center"></el-table-column>
                <el-table-column prop="time" width="160" label="过期日期" align="center">
                    <template slot-scope="scope"> {{ utilsParseTime(scope.row.time, "{y}-{m}-{d}") }}</template>
                </el-table-column>
                <el-table-column prop="createUserName" width="160" :show-overflow-tooltip="true" label="创建人" align="center"></el-table-column>
                <el-table-column prop="createTime" width="160" label="创建时间" align="center">
                    <template slot-scope="scope"> {{ utilsParseTime(scope.row.createTime, "{y}-{m}-{d} {h}:{i}:{s}") }}</template>
                </el-table-column>
            </el-table>
        </div>
        <!-- 分页 -->
        <div>
            <el-pagination background @size-change="RHandleSizeChange" @current-change="RHandleCurrentChange" :current-page="r.searchForm.pageNo" :page-sizes="[10, 20, 30, 40, 50]" :page-size="r.searchForm.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="r.total"> </el-pagination>
        </div>
        <!-- 新建或编辑围栏 -->
        <el-dialog :title="cud.cORu === 'c' ? '新建电子围栏' : '修改电子围栏'" :close-on-click-modal="false" :close-on-press-escape="false" :visible.sync="cud.dialogVisible" @close="CUDHandleCloseDialog" @opened="CUDHandleOpenedDialog" width="1200px" center>
            <span>
                <el-form :inline="true" :rules="cud.CURules" :model="cud.CUForm" ref="CUForm" label-width="100px" size="mini">
                    <el-form-item label="围栏名称:" prop="name">
                        <el-input placeholder="" v-model="cud.CUForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="围栏类型:" prop="fenceType">
                        <el-select placeholder="请选择" v-model="cud.CUForm.fenceType" @change="CUDChangeFenceType">
                            <el-option label="中心点" value="1"></el-option>
                            <el-option label="多边形" value="2"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="辐射半径:" prop="centerRadius" v-if="cud.CUForm.fenceType == 1">
                        <el-input placeholder="0~10000" maxlength="5" v-model="cud.CUForm.centerRadius">
                            <i class="radius-unit" slot="suffix">米</i>
                        </el-input>
                    </el-form-item>
                    <el-form-item label="省市县:">
                        <AreaCascader v-if="cud.visibleAreaCascader" :updateData="getAreaData" @HandleOnSelect="CUDHandleAreaSelect"></AreaCascader>
                    </el-form-item>
                    <el-form-item label="中心位置:" prop="objKey">
                        <el-input placeholder="" v-model="cud.CUForm.objKey" :readonly="true" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="经纬度:" prop="centerCoordinate">
                        <el-input placeholder="" v-model="cud.CUForm.centerCoordinate" :readonly="true" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="CUDHandleResetForm()">重置</el-button>
                    </el-form-item>
                </el-form>
                <div class="mapBox">
                    <div class="map" id="map"></div>
                    <div class="info" v-if="cud.CUForm.fenceType == 2">操作说明：多边形需要通过点击来绘制，双击结束绘制</div>
                    <div class="input-item">
                        <div class="input-item-prepend">
                            <span class="input-item-text" style="width: 8rem">请输入关键字</span>
                        </div>
                        <input id="tipinput" ref="tipinput" type="text" autocomplete="off" />
                    </div>
                </div>
            </span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="cud.dialogVisible = false">取 消</el-button>
                <el-button @click="CUDHandleSubmit" type="primary">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 查看围栏 -->
        <el-dialog title="查看围栏" :close-on-click-modal="false" :close-on-press-escape="false" :visible.sync="r.viewDialogVisible" @close="RHandleCloseViewDialog" @opened="RHandleOpenedViewDialog" width="1200px" center>
            <span>
                <div class="mapBox">
                    <div class="map" id="map-view"></div>
                    <div class="input-item box1">
                        <div class="item">
                            <span class="name">围栏名称：</span><span>{{ r.viewForm.name }}</span>
                        </div>
                        <div class="item">
                            <span class="name">中心位置：</span><span>{{ r.viewForm.objKey }}</span>
                        </div>
                        <div class="item">
                            <span class="name">半径(米)：</span><span>{{ r.viewForm.centerRadius }}</span>
                        </div>
                    </div>
                </div>
            </span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="r.viewDialogVisible = false">取 消</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
    import { getBtn } from "@/utils/btn";
    import { parseTime } from "@/utils/time";
    import { fenceList, fenceCreate, fenceUpdate, fenceDelete } from "@/api/request";
    import { Message } from "element-ui";
    import AreaCascader from "@/components/AreaCascader";
    import AMap from "AMap";
    export default {
        components: {
            AreaCascader,
        },
        data() {
            return {
                r: {
                    list: [],
                    total: 0,
                    searchForm: {
                        pageNo: 1,
                        pageSize: 20,
                        name: null,
                        fenceType: null,
                        address: null, // 围栏中心位置 === 搜索关键字 objKey
                        status: null,
                        createTime: null,
                    },
                    fenceTypes: [
                        { value: null, label: "全部" },
                        { value: 1, label: "中心点" },
                        { value: 2, label: "多边形" },
                    ],
                    status: [
                        { value: null, label: "全部" },
                        { value: 1, label: "已启用" },
                        { value: 2, label: "已停用" },
                    ],
                    loading: false,
                    btnFlg: [],
                    btnIdArr: ["01F01S0100", "01F01S0100", "01F01S0200", "01F01S0300", "01F01S0400", "01F01S0500", "01F01S0600", "01F01S0700", "01F01S0800"],
                    viewDialogVisible: false,
                    viewForm: {
                        name: "",
                        useType: "1", // 围栏用途
                        fenceType: "1", // 围栏类型
                        time: "", // 过期时间

                        provinceCode: "", // 省市县
                        provinceName: "",
                        cityCode: "",
                        cityName: "",
                        areaCode: "",
                        areaName: "",

                        centerRadius: "", // 辐射半径
                        objKey: "", // 搜索关键字 objKey === 中心位置 adress
                        centerCoordinate: "", // 中心坐标
                        lng: "", // 经度
                        lat: "", // 纬度
                        multiCoordinate: "", //
                        multiCoordinate1: [], //
                    },
                },
                cud: {
                    cORu: "c",
                    dialogVisible: false,
                    visibleAreaCascader: true,
                    CURules: {
                        name: [{ required: true, message: "请输入围栏名称", trigger: "blur" }],
                        fenceType: [{ required: true, message: "请选择围栏类型", trigger: ["blur"] }],
                        objKey: [{ required: true, message: "中心位置不能为空", trigger: ["blur"] }],
                        // v-if 隐藏时, 不会校验该规则
                        centerRadius: [
                            { required: true, message: "请输入辐射半径", trigger: ["blur"] },
                            { pattern: /^(([1-9]\d{0,3}|10000))$/, message: "最大输入10000" },
                        ],
                        centerCoordinate: [{ required: true, message: "经纬度不能为空", trigger: ["blur"] }],
                    },
                    CUForm: {
                        name: "",
                        useType: "1", // 围栏用途
                        fenceType: "1", // 围栏类型
                        time: "", // 过期时间

                        provinceCode: "", // 省市县
                        provinceName: "",
                        cityCode: "",
                        cityName: "",
                        areaCode: "",
                        areaName: "",

                        centerRadius: "", // 辐射半径
                        objKey: "", // 搜索关键字 objKey === 中心位置 adress
                        centerCoordinate: "", // 中心坐标
                        lng: "", // 经度
                        lat: "", // 纬度
                        multiCoordinate: "", //
                        multiCoordinate1: [], //
                    },
                },
                amap: {
                    instance: null,
                    inputHandler: null,
                    placeSearchHandler: null,
                    mouseTool: null, // 绘制实例
                    coverCircle: null, // 圆形覆盖物
                    coverPolygon: [], // 多边形覆盖物
                },
            };
        },
        computed: {
            getAreaData() {
                console.log("this.cud.CUForm:", this.cud.CUForm);
                return this.cud.CUForm;
            },
        },
        created() {
            // 对比CRM和租户CRM按钮权限
            this.r.btnFlg = getBtn(this.r.btnIdArr, "/groundFence");
            // 获取列表
            this.RFetchList();

            console.log("AMap123", AMap);
        },
        methods: {
            RFetchList() {
                this.r.loading = true;
                fenceList(this.r.searchForm).then(
                    (res) => {
                        this.r.list = res.data.list;
                        this.r.loading = false;
                    },
                    (res) => {
                        console.log("fenceList fail:", res);
                        Message({
                            message: `${res.message}`,
                            type: "error",
                            duration: 1500,
                        });
                        this.r.loading = false;
                    }
                );
            },
            RHandleSizeChange(val) {
                this.r.searchForm.pageSize = val;
                this.RFetchList();
            },
            // 当前页
            RHandleCurrentChange(val) {
                this.r.searchForm.pageNo = val;
                this.RFetchList();
            },
            RSelectGoods(selection) {},
            RHandleRowClick(row, column, event) {
                console.log("current row: ", row);
                this.$refs.table.toggleRowSelection(row);
            },
            RHandleSearch() {
                console.log(this.r.searchForm);
                this.RFetchList();
            },
            RHandleReset() {
                this.r.searchForm = {
                    pageNo: 1,
                    pageSize: 20,
                    name: null,
                    fenceType: null,
                    address: null,
                    status: null,
                    createTime: null,
                };
            },
            RViewFence(row) {
                this.r.viewForm = row;
                this.r.viewForm.multiCoordinate1 = this.r.viewForm.multiCoordinate.split(";");
                const arr = this.r.viewForm.centerCoordinate.split(",");
                this.r.viewForm.lng = arr[0];
                this.r.viewForm.lat = arr[1];
                this.r.viewDialogVisible = true;
            },
            RHandleCloseViewDialog() {
                this.r.viewForm = {
                    name: "",
                    useType: "1", // 围栏用途
                    fenceType: "1", // 围栏类型
                    time: "", // 过期时间
                    provinceCode: "", // 省市县
                    provinceName: "",
                    cityCode: "",
                    cityName: "",
                    areaCode: "",
                    areaName: "",
                    centerRadius: "", // 辐射半径
                    objKey: "", // 搜索关键字 objKey === 中心位置 adress
                    centerCoordinate: "", // 中心坐标
                    lng: "", // 经度
                    lat: "", // 纬度
                    multiCoordinate: "", //
                    multiCoordinate1: [], //
                };
                this.amap.instance.clearMap(); // 清除地图绘制，覆盖物等信息
            },
            RHandleOpenedViewDialog() {
                document.body.style.overflow = "hidden";
                this.initAMapForView();
            },
            CUDHandleCreaet() {
                this.cud.cORu = "c";
                this.cud.dialogVisible = true;
                this.$nextTick(() => {
                    this.$refs.CUForm.clearValidate();
                });
            },
            CUDHandleUpdate(row) {
                console.log("row:", row);
                this.cud.cORu = "u";
                this.cud.CUForm = JSON.parse(JSON.stringify(row));
                this.cud.CUForm.fenceType = `${this.cud.CUForm.fenceType}`;
                this.cud.CUForm.multiCoordinate1 = this.cud.CUForm.multiCoordinate.split(";");
                const arr = this.cud.CUForm.centerCoordinate.split(",");
                this.cud.CUForm.lng = arr[0];
                this.cud.CUForm.lat = arr[1];
                this.cud.visibleAreaCascader = false;
                this.cud.dialogVisible = true;
                this.$nextTick(() => {
                    this.cud.visibleAreaCascader = true;
                    this.$refs.CUForm.clearValidate();
                });
            },
            CUDHandleStatus(row, value) {
                this.r.loading = true;
                fenceUpdate({ id: row.id, status: value }).then(
                    (res) => {
                        this.r.loading = false;
                        this.RFetchList();
                        console.log("fenceList succ:", res);
                    },
                    (res) => {
                        this.r.loading = false;
                        console.log("fenceList fail:", res);
                    }
                );
            },
            CUDHandleDelete(row) {
                this.$confirm("是否执行删除操作?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                }).then(
                    () => {
                        this.r.loading = true;
                        fenceDelete({ id: row.id }).then(
                            (res) => {
                                this.r.loading = false;
                                this.$message({
                                    message: res.message,
                                    type: "success",
                                });
                                this.RFetchList();
                            },
                            (res) => {
                                this.$message({
                                    showClose: true,
                                    message: res.message,
                                    type: "error",
                                    onClose() {
                                        this.r.loading = false;
                                    },
                                });
                                console.log("fenceList fail:", res);
                            }
                        );
                    },
                    () => {}
                );
            },
            CUDHandleSubmit() {
                if (this.cud.CUForm.provinceCode === "" || this.cud.CUForm.provinceName === "" || this.cud.CUForm.cityCode === "" || this.cud.CUForm.cityName === "" || this.cud.CUForm.areaCode === "" || this.cud.CUForm.areaName === "") {
                    Message({
                        message: "缺少省市县信息",
                        type: "error",
                        duration: 1500,
                    });
                    return false;
                }
                this.$refs["CUForm"].validate((valid) => {
                    if (valid) {
                        console.log("valid yes:", this.cud.CUForm);
                        if (this.cud.cORu === "c") {
                            fenceCreate(this.cud.CUForm).then(
                                (res) => {
                                    this.RFetchList();
                                    this.cud.dialogVisible = false;
                                    console.log("CUDHandleSubmit succ:", res);
                                },
                                (res) => {
                                    console.log("CUDHandleSubmit fail:", res);
                                    Message({
                                        message: `${res.message}`,
                                        type: "error",
                                        duration: 1500,
                                    });
                                }
                            );
                        } else {
                            fenceUpdate(this.cud.CUForm).then(
                                (res) => {
                                    this.RFetchList();
                                    this.cud.dialogVisible = false;
                                    console.log("CUDHandleSubmit succ:", res);
                                },
                                (res) => {
                                    console.log("CUDHandleSubmit fail:", res);
                                    Message({
                                        message: `${res.message}`,
                                        type: "error",
                                        duration: 1500,
                                    });
                                }
                            );
                        }
                    } else {
                        console.log("valid no:", this.cud.CUForm);
                    }
                });
            },
            CUDHandleCloseDialog() {
                this.CUDHandleResetForm();
            },
            CUDHandleOpenedDialog() {
                document.body.style.overflow = "hidden";
                this.initAMap();
            },
            CUDHandleResetForm() {
                if (this.cud.CUForm.id) {
                    this.cud.CUForm = {
                        id: this.cud.CUForm.id,
                        name: "",
                        useType: "1", // 围栏用途
                        fenceType: "1", // 围栏类型
                        time: "", // 过期时间
                        provinceCode: "", // 省市县
                        provinceName: "",
                        cityCode: "",
                        cityName: "",
                        areaCode: "",
                        areaName: "",
                        centerRadius: "", // 辐射半径
                        objKey: "", // 搜索关键字 objKey === 中心位置 adress
                        centerCoordinate: "", // 中心坐标
                        lng: "", // 经度
                        lat: "", // 纬度
                        multiCoordinate: "", //
                        multiCoordinate1: [], //
                    };
                } else {
                    this.cud.CUForm = {
                        name: "",
                        useType: "1", // 围栏用途
                        fenceType: "1", // 围栏类型
                        time: "", // 过期时间
                        provinceCode: "", // 省市县
                        provinceName: "",
                        cityCode: "",
                        cityName: "",
                        areaCode: "",
                        areaName: "",
                        centerRadius: "", // 辐射半径
                        objKey: "", // 搜索关键字 objKey === 中心位置 adress
                        centerCoordinate: "", // 中心坐标
                        lng: "", // 经度
                        lat: "", // 纬度
                        multiCoordinate: "", //
                        multiCoordinate1: [], //
                    };
                }
                this.$refs.tipinput.value = ""; // 清除地图搜索框
                this.amap.instance.clearMap(); // 清除地图绘制，覆盖物等信息
                this.cud.visibleAreaCascader = false;
                this.$nextTick(() => {
                    this.cud.visibleAreaCascader = true; // 重新渲染省市联动组件, 以重置它
                    this.$refs.CUForm.clearValidate();
                });
            },
            CUDHandleAreaSelect(value) {
                if (value) {
                    this.cud.CUForm.provinceCode = value[0].areaCode;
                    this.cud.CUForm.provinceName = value[0].name;
                    this.cud.CUForm.cityCode = value[1].areaCode;
                    this.cud.CUForm.cityName = value[1].name;
                    this.cud.CUForm.areaCode = value[2].areaCode;
                    this.cud.CUForm.areaName = value[2].name;
                }
            },
            CUDChangeFenceType() {
                console.log(this.cud.CUForm);
                if (parseInt(this.cud.CUForm.fenceType) === 2) {
                    this.initAMapStartDraw();
                }
            },
            initAMap() {
                // 创建Map实例
                this.amap.instance = new AMap.Map("map", {
                    center: [116.397428, 39.90923], // 北京中心
                    resizeEnable: true, // 是否监控地图尺寸变化
                    zoom: 12, // 缩放级别
                });
                // 创建Map搜索实例，并关联Map实例
                this.amap.placeSearchHandler = new AMap.PlaceSearch({
                    map: this.amap.instance,
                    pageSize: 1,
                    extensions: "all",
                });
                // 注册输入框提示
                this.amap.inputHandler = new AMap.Autocomplete({
                    input: "tipinput",
                });
                // 注册点击提示输入框内的项
                AMap.event.addListener(this.amap.inputHandler, "select", (e) => {
                    this.amap.placeSearchHandler.setCity(e.poi.adcode);
                    this.amap.placeSearchHandler.search(e.poi.name); // 关键字查询查询
                    // 更新绑定信息
                    this.cud.CUForm.objKey = e.poi.district + e.poi.address;
                    this.cud.CUForm.centerCoordinate = e.poi.location.lng + "," + e.poi.location.lat;
                    this.cud.CUForm.lng = e.poi.location.lng;
                    this.cud.CUForm.lat = e.poi.location.lat;
                    this.cud.CUForm.multiCoordinate1 = [];
                    // 注册绘制实例
                    if (parseInt(this.cud.CUForm.fenceType) === 2) {
                        this.initAMapStartDraw();
                    }
                }); // 注册监听，当选中某条记录时会触发console.log(e)

                // 更新围栏逻辑
                if (this.cud.cORu === "u") {
                    // 创建并添加水滴标记
                    const marker = new AMap.Marker({
                        position: [this.cud.CUForm.lng, this.cud.CUForm.lat],
                    });
                    this.amap.instance.add(marker);

                    // 创建圆形覆盖标记
                    if (parseInt(this.cud.CUForm.fenceType) === 1 && this.cud.CUForm.centerRadius && this.cud.CUForm.centerCoordinate) {
                        this.amap.coverCircle = new AMap.Circle({
                            center: new AMap.LngLat(this.cud.CUForm.lng, this.cud.CUForm.lat), // 圆心位置
                            radius: this.cud.CUForm.centerRadius, // 半径
                            strokeColor: "#F33", // 线颜色
                            strokeOpacity: 1, // 线透明度
                            strokeWeight: 3, // 线粗细度
                            fillColor: "#ee2200", // 填充颜色
                            fillOpacity: 0.35, // 填充透明度
                        });
                        // 为实例添加圆形覆盖标记
                        this.amap.instance.add(this.amap.coverCircle);
                        /**
                         * 实例方法: setFitView()
                         * 根据地图上覆盖物分布情况，自动缩放地图到合适的视野级别, 参数均可缺省
                         * overlayList  Array 覆盖物数组
                         * immediately bool 是否需要动画
                         **/
                        this.amap.instance.setFitView();
                    }
                    // 创建多边形覆盖物
                    if (parseInt(this.cud.CUForm.fenceType) === 2 && this.cud.CUForm.centerCoordinate && this.cud.CUForm.multiCoordinate1.length > 2) {
                        const pathArr = [];
                        this.cud.CUForm.multiCoordinate1.forEach((itme) => {
                            pathArr.push(itme.split(","));
                        });
                        this.amap.coverPolygon = new AMap.Polygon({
                            path: pathArr,
                            strokeColor: "#ee2200",
                            strokeWeight: 6,
                            strokeOpacity: 0.2,
                            fillOpacity: 0.4,
                            fillColor: "#F33",
                            zIndex: 50,
                        });
                        this.amap.instance.add(this.amap.coverPolygon);
                        this.amap.instance.setFitView();
                    }
                }
            },
            initAMapForView() {
                console.log(this.r.viewForm);
                // 创建Map实例
                this.amap.instance = new AMap.Map("map-view", {
                    center: [116.397428, 39.90923], // 北京中心
                    resizeEnable: true, // 是否监控地图尺寸变化
                    zoom: 12, // 缩放级别
                });
                // // 创建并添加水滴标记
                const marker = new AMap.Marker({
                    position: [this.r.viewForm.lng, this.r.viewForm.lat],
                });
                this.amap.instance.add(marker);

                // 创建圆形覆盖标记
                if (parseInt(this.r.viewForm.fenceType) === 1 && this.r.viewForm.centerRadius && this.r.viewForm.centerCoordinate) {
                    this.amap.coverCircle = new AMap.Circle({
                        center: new AMap.LngLat(this.r.viewForm.lng, this.r.viewForm.lat), // 圆心位置
                        radius: this.r.viewForm.centerRadius, // 半径
                        strokeColor: "#F33", // 线颜色
                        strokeOpacity: 1, // 线透明度
                        strokeWeight: 3, // 线粗细度
                        fillColor: "#ee2200", // 填充颜色
                        fillOpacity: 0.35, // 填充透明度
                    });
                    // 为实例添加圆形覆盖标记
                    this.amap.instance.add(this.amap.coverCircle);
                    /**
                     * 实例方法: setFitView()
                     * 根据地图上覆盖物分布情况，自动缩放地图到合适的视野级别, 参数均可缺省
                     * overlayList  Array 覆盖物数组
                     * immediately bool 是否需要动画
                     **/
                    this.amap.instance.setFitView();
                }
                // 创建多边形覆盖物
                if (parseInt(this.r.viewForm.fenceType) === 2 && this.r.viewForm.centerCoordinate && this.r.viewForm.multiCoordinate1.length > 2) {
                    const pathArr = [];
                    this.r.viewForm.multiCoordinate1.forEach((itme) => {
                        pathArr.push(itme.split(","));
                    });
                    this.amap.coverPolygon = new AMap.Polygon({
                        path: pathArr,
                        strokeColor: "#ee2200",
                        strokeWeight: 6,
                        strokeOpacity: 0.2,
                        fillOpacity: 0.4,
                        fillColor: "#F33",
                        zIndex: 50,
                    });
                    this.amap.instance.add(this.amap.coverPolygon);
                    this.amap.instance.setFitView();
                }
            },
            initAMapStartDraw() {
                // 清除已存在的多边形覆盖物
                console.log("this.amap.mouseToolPolygonStorage", this.amap.mouseToolPolygonStorage);
                if (this.amap.coverPolygon) {
                    this.amap.instance.remove(this.amap.coverPolygon);
                }
                // 清除已存在的水滴覆盖物
                if (this.amap.coverCircle) {
                    this.amap.instance.remove(this.amap.coverCircle);
                }
                // 创建绘制实例
                this.amap.mouseTool = new AMap.MouseTool(this.amap.instance);
                // 绘制类型为多边形
                this.amap.mouseTool.polygon({
                    fillColor: "#F33",
                    strokeColor: "#ee2200",
                });
                // 绘制完毕回调
                this.amap.mouseTool.on("draw", (e) => {
                    console.log(e);
                    e.obj.w.path.forEach((item) => {
                        this.cud.CUForm.multiCoordinate1.push(item.lng + "," + item.lat);
                        this.cud.CUForm.multiCoordinate = this.cud.CUForm.multiCoordinate1.join(";");
                    });
                    this.amap.coverPolygon = e.obj;
                    this.amap.mouseTool.close(); // 关闭绘制状态
                });
            },
            utilsParseTime(time, formate) {
                return parseTime(time, formate);
            },
        },
    };
</script>
<style lang="scss" scoped>
    .title {
        display: flex;
        justify-content: space-between;
        padding-right: 10px 10px;
        margin-left: 10px;
        border-bottom: 1px solid #dcdfe6;
        margin-bottom: 10px;
        h4 {
            margin: 0;
            font-size: 16px;
            color: #333;
            padding-bottom: 10px;
            border-bottom: 2px solid rgb(88, 204, 2);
        }
    }
    .searchBox {
        padding-top: 15px;
        .inpBox {
            padding-left: 20px;
        }
        .searchBtnBox {
            padding-left: 20px;
        }
        .el-button {
            background-color: rgb(88, 204, 2);
            border-color: rgb(88, 204, 2);
        }
    }
    .table {
        .el-table {
            border-left: none;
            ::v-deep .el-table__row td {
                padding: 8px 0 !important;
            }
            .panel {
                white-space: nowrap;
            }
        }
    }
    .radius-unit {
        font-style: initial;
    }
    .mapBox {
        width: 100%;
        height: 560px;
        position: relative;
        border: 1px solid #dcdfe6;
        #map,
        #map-view {
            width: 100%;
            height: 100%;
        }
        .input-item {
            padding: 0.75rem 1.25rem;
            margin-bottom: 1rem;
            border-radius: 0.25rem;
            position: absolute;
            top: 1rem;
            background-color: white;
            border-width: 0;
            left: 1rem;
            box-shadow: 0 2px 6px 0 rgba(114, 124, 245, 0.5);
            font-size: 12px;
            display: flex;
            .input-item-prepend {
                border: 1px solid #ddd;
                padding: 6px;
                border-right: none;
            }
            #tipinput {
                border: 1px solid #ddd;
            }
            #tipinput:focus {
                border: 1px solid #ddd;
                outline: none;
            }
        }
        .info {
            padding: 0.75rem 1.25rem;
            margin-bottom: 1rem;
            border-radius: 0.25rem;
            position: absolute;
            top: 1rem;
            background-color: white;
            width: auto;
            border-width: 0;
            right: 1rem;
            box-shadow: 0 2px 6px 0 rgba(114, 124, 245, 0.5);
            font-size: 12px;
        }
        .box1 {
            display: flex;
            flex-wrap: wrap;
            flex-direction: column;
            .item {
                margin-right: 10px;
                padding: 4px 0;
                display: flex;
            }
            .name {
                color: rgb(88, 204, 2);
                flex-shrink: 0;
            }
            .item1 {
                width: 100%;
            }
        }
    }
    ::v-deep .el-dialog__header {
        padding: 8px 20px !important;
        background-color: rgb(88, 204, 2) !important;
        .el-dialog__title {
            color: #fff;
        }
        .el-dialog__headerbtn {
            top: 12px;
            .el-dialog__close {
                color: #fff;
            }
        }
    }
</style>
