<template>
    <div>
        <div class="detailsTitle">
            <h4>电子围栏</h4>
        </div>
        <!-- 搜索条件区域 -->
        <div class="searchBox" ref="searchBox">
            <el-form :inline="true" :model="searchFrom" ref="searchFrom" class="demo-form-inline" label-width="110px" size="mini">
                <div class="inpBox">
                    <el-form-item label="围栏名称:" prop="name">
                        <el-input placeholder="" v-model="searchFrom.name"></el-input>
                    </el-form-item>
                    <!-- <el-form-item label="围栏用途:" prop="useType">
                        <el-select placeholder="请选择" v-model="searchFrom.useType">
                            <el-option v-for="item in wlyt" :key="item.value" :label="item.label" :value="item.value"></el-option>
                        </el-select>
                    </el-form-item> -->
                    <el-form-item label="围栏类型:" prop="fenceType">
                        <el-select placeholder="请选择" v-model="searchFrom.fenceType">
                            <el-option v-for="item in wllx" :key="item.value" :label="item.label" :value="item.value"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="中心位置:" prop="address">
                        <el-input placeholder="" v-model="searchFrom.address"></el-input>
                    </el-form-item>
                    <el-form-item label="启用状态:" prop="status">
                        <el-select placeholder="请选择" v-model="searchFrom.status">
                            <el-option v-for="item in qyzt" :key="item.value" :label="item.label" :value="item.value"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="创建时间:" prop="createTime">
                        <el-date-picker v-model="searchFrom.createTime" format="yyyy-MM-dd" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
                    </el-form-item>
                </div>
                <div class="searchBtnBox">
                    <el-form-item v-if="btnFlg[0]">
                        <el-button type="primary" @click="searchFn">查询</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button v-if="btnFlg[1]" type="primary" @click="resetForm('searchFrom')">重置</el-button>
                    </el-form-item>
                    <el-form-item v-if="btnFlg[2]">
                        <el-button type="primary" @click="addFn">新建</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </div>
        <!--  列表 -->
        <div class="table" style="height: 500px">
            <el-table :data="tableList" ref="table" v-loading="loading" bordersize="mini" @selection-change="selectGoods" @row-click="handleRowClick" height="100%" style="width: 100%" center border>
                <el-table-column type="index" width="55" label="序号"></el-table-column>
                <el-table-column label="操作" width="120">
                    <template slot-scope="scope">
                        <div>
                            <el-button type="text" size="mini" v-if="btnFlg[3]" v-show="scope.row.status == 1" @click="endFn(scope.row, 2)">停用</el-button>
                            <el-button type="text" size="mini" v-if="btnFlg[4]" v-show="scope.row.status == 2" @click="endFn(scope.row, 1)">启用</el-button>
                            <el-button type="text" size="mini" v-if="btnFlg[5]" @click="lookFn(scope.row)">查看围栏</el-button>
                            <el-button type="text" size="mini" v-if="btnFlg[6]" v-show="scope.row.status == 2" @click="modifyFn(scope.row)">修改</el-button>
                            <el-button type="text" size="mini" v-if="btnFlg[7]" v-show="scope.row.status == 2" @click="delFn(scope.row)">删除</el-button>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column property="status" label="启用状态">
                    <template scope="scope">
                        {{ ["已启用", "已停用"][scope.row.status - 1] }}
                    </template>
                </el-table-column>
                <el-table-column prop="name" width="120" :show-overflow-tooltip="true" label="围栏名称"></el-table-column>
                <!-- <el-table-column property="useType" label="围栏用途">
                    <template scope="scope">
                        {{ ["卸点用途"][scope.row.useType - 1] }}
                    </template>
                </el-table-column> -->
                <el-table-column property="fenceType" label="围栏类型">
                    <template scope="scope">
                        {{ ["中心点", "多边形"][scope.row.fenceType - 1] }}
                    </template>
                </el-table-column>
                <el-table-column prop="objKey" width="260" :show-overflow-tooltip="true" label="中心位置"></el-table-column>
                <el-table-column prop="centerCoordinate" width="220" :show-overflow-tooltip="true" label="经纬度"></el-table-column>
                <el-table-column prop="centerRadius" width="160" :show-overflow-tooltip="true" label="半径(米)"></el-table-column>
                <!-- <el-table-column prop="time" width="160" :formatter="dateFormat1" label="过期日期"></el-table-column> -->
                <el-table-column prop="createUserName" width="160" :show-overflow-tooltip="true" label="创建人"></el-table-column>
                <el-table-column prop="createTime" width="160" :formatter="dateFormat" label="创建时间"></el-table-column>
            </el-table>
        </div>
        <!-- 分页 -->
        <div>
            <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="searchFrom.pageNo" :page-sizes="[10, 20, 30, 40, 50]" :page-size="searchFrom.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total"> </el-pagination>
        </div>

        <!-- 新建 或 编辑围栏 -->
        <el-dialog :title="title" :close-on-click-modal="false" :close-on-press-escape="false" class="vehm" :visible.sync="dialogVisible" @close="closeDialog" @opened="openedDialog" width="1200px" center>
            <span>
                <el-form :inline="true" :rules="rules" :model="addFrom" ref="addFrom" class="demo-form-inline jiaoyan" label-width="100px" size="mini">
                    <el-form-item label="围栏名称:" prop="name">
                        <el-input placeholder="" v-model="addFrom.name"></el-input>
                    </el-form-item>
                    <!-- <el-form-item label="围栏用途:" prop="useType">
                        <el-select placeholder="请选择" v-model="addFrom.useType">
                            <el-option v-for="item in wlyt1" :key="item.value" :label="item.label" :value="item.value"></el-option>
                        </el-select>
                    </el-form-item> -->
                    <el-form-item label="围栏类型:" prop="fenceType">
                        <el-select placeholder="请选择" v-model="addFrom.fenceType" @change="fenceTypeChange">
                            <el-option v-for="item in wllx1" :key="item.value" :label="item.label" :value="item.value"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="辐射半径:" prop="centerRadius" v-if="addFrom.fenceType == 1">
                        <el-input placeholder="0~10000" maxlength="5" v-number-input.float="0" @blur="FSBJfn()" v-model="addFrom.centerRadius">
                            <i class="radius-unit" slot="suffix">米</i>
                        </el-input>
                    </el-form-item>
                    <!-- <el-form-item label="过期日期" prop="time">
                        <el-date-picker v-model="addFrom.time" value-format="timestamp" placeholder="选择日期" :picker-options="pickerOptions"></el-date-picker>
                    </el-form-item> -->
                    <el-form-item label="省市县:" class="diqu2 is-required">
                        <shengShiXian :rowData="this.addFrom" @handleChange="handleChange" v-if="flag"></shengShiXian>
                    </el-form-item>
                    <el-form-item label="中心位置:" prop="objKey" class="diqu2 disabled">
                        <el-input placeholder="" v-model="addFrom.objKey" :readonly="true"></el-input>
                    </el-form-item>
                    <el-form-item label="经纬度:" prop="centerCoordinate" class="diqu2 disabled">
                        <el-input placeholder="" v-model="addFrom.centerCoordinate" :readonly="true"></el-input>
                    </el-form-item>
                    <el-form-item btnCode="00GP001B001">
                        <el-button type="primary" @click="resetForm1()">重置</el-button>
                    </el-form-item>
                </el-form>
                <div class="mapBox">
                    <div class="map" id="map"></div>
                    <div class="info" v-if="addFrom.fenceType == 2">操作说明：多边形需要通过点击来绘制，双击结束绘制</div>
                    <div class="input-item">
                        <div class="input-item-prepend">
                            <span class="input-item-text" style="width: 8rem">请输入关键字</span>
                        </div>
                        <input id="tipinput" ref="tipinput" type="text" autocomplete="off" />
                    </div>
                </div>
            </span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveFn('addFrom')">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 查看围栏对话框 -->
        <el-dialog title="查看围栏" :close-on-click-modal="false" :close-on-press-escape="false" class="vehm" :visible.sync="lookVisible" @close="closeDialog" @opened="openedDialog1" width="1200px" center>
            <span>
                <div class="mapBox">
                    <div class="map" id="map1"></div>
                    <div class="input-item box1">
                        <div class="item">
                            <span class="name">围栏名称：</span><span>{{ addFrom.name }}</span>
                        </div>
                        <div class="item">
                            <span class="name">中心位置：</span><span>{{ addFrom.objKey }}</span>
                        </div>
                        <div class="item">
                            <span class="name">半径(米)：</span><span>{{ addFrom.centerRadius }}</span>
                        </div>
                        <!-- <div class="item">
                            <span class="name">有效期：</span><span>{{ addFrom.time | dateFormat1 }}</span>
                        </div> -->
                    </div>
                </div>
            </span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="lookVisible = false">取 消</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import { getBtn } from "@/utils/getButton";
    import shengShiXian from "@/components/common/shengShiXian.vue";
    import { fenceList, fenceAdd, fenceDelete, fenceUpdate, updateFence } from "@/api/lj";
    import AMap from "AMap";
    export default {
        components: {
            shengShiXian,
        },
        data() {
            return {
                searchFrom: {
                    pageNo: 1,
                    pageSize: 20,
                },
                loading: "false",
                addFrom: { multiCoordinate1: [] },
                isAdd: false,
                title: "新建电子围栏",
                rules: {
                    name: [{ required: true, message: "请输入围栏名称", trigger: "blur" }],
                    //useType: [{ required: true, message: "请选择围栏用途", trigger: ["blur", "change"] }],
                    fenceType: [{ required: true, message: "请选择围栏类型", trigger: ["blur", "change"] }],
                    areaCode1: [{ required: true, message: "请选择省市县", trigger: ["blur", "change"] }],
                    centerRadius: [{ required: true, message: "请输入辐射半径", trigger: ["blur", "change"] }, this.rule.centerRadius],
                },
                wlyt: [
                    { value: "", label: "全部" },
                    { value: 1, label: "卸点用途" },
                ],
                wlyt1: [{ value: 1, label: "卸点用途" }],
                wllx: [
                    { value: "", label: "全部" },
                    { value: 1, label: "中心点" },
                    { value: 2, label: "多边形" },
                ],
                wllx1: [
                    { value: 1, label: "中心点" },
                    { value: 2, label: "多边形" },
                ],
                qyzt: [
                    { value: "", label: "全部" },
                    { value: 1, label: "已启用" },
                    { value: 2, label: "已停用" },
                ],
                total: 0,
                tableList: [],
                dialogVisible: false,
                lookVisible: false,
                map: null,
                auto: null,
                circle: null,
                mouseTool: null,
                marker: null,
                polygon: null,
                overlays: [],
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() < Date.now() - 8.64e7;
                    },
                },
                flag: true,
                btnFlg: [],
                btnIdArr: ["01K0060100", "01K0060300", "01K0060200", "01K0060400", "01K0060500", "01K0060600", "01K0060700", "01K0060800"],
            };
        },
        created() {
            // 从缓存中获取菜单. 数据来自登录时角色拥有的菜单
            this.btnFlg = getBtn(this.btnIdArr, "/electronicFence");
            console.log("btnFlg:2", this.btnFlg);
            // 渲染围栏列表
            this.getList(this.searchFrom);
        },
        mounted() {
            let h = this.$refs.searchBox.offsetHeight;
            let pH = document.documentElement.clientHeight;
            this.h = pH - h - 92 + "px";
        },
        methods: {
            // 新建
            addFn() {
                this.title = "新建电子围栏";
                this.dialogVisible = true;
                this.isAdd = true;
                this.flag = true;
                this.$nextTick(() => {
                    this.$refs.addFrom.clearValidate();
                });
            },
            // 修改
            modifyFn(row) {
                this.title = "修改电子围栏";
                this.flag = true;
                this.dialogVisible = true;
                this.isAdd = false;
                this.addFrom = JSON.parse(JSON.stringify(row));
                this.addFrom.multiCoordinate1 = this.addFrom.multiCoordinate.split(";");
                let arr = this.addFrom.centerCoordinate.split(",");
                this.addFrom.lng = arr[0];
                this.addFrom.lat = arr[1];
                this.$nextTick(() => {
                    this.$refs.addFrom.clearValidate();
                });
            },
            // 查看围栏
            lookFn(row) {
                this.lookVisible = true;
                this.addFrom = JSON.parse(JSON.stringify(row));
                this.addFrom.multiCoordinate1 = this.addFrom.multiCoordinate.split(";");
                let arr = this.addFrom.centerCoordinate.split(",");
                this.addFrom.lng = arr[0];
                this.addFrom.lat = arr[1];
            },
            initMap() {
                let that = this;
                this.map = new AMap.Map("map", {
                    center: [116.397428, 39.90923], // [纬度，经度]
                    resizeEnable: true,
                    zoom: 12,
                });
                var placeSearch = new AMap.PlaceSearch({
                    map: this.map,
                    pageSize: 1,
                    extensions: "all",
                    // autoFitView: false
                });
                this.auto = new AMap.Autocomplete({
                    input: "tipinput",
                });
                AMap.event.addListener(this.auto, "select", select); // 注册监听，当选中某条记录时会触发console.log(e)
                function select(e) {
                    if (that.marker) {
                        that.map.remove(that.marker);
                    }
                    if (e.poi.location == "") {
                        that.$alert("请选择详细地址", "系统提示");
                    } else {
                        console.log(e);
                        placeSearch.setCity(e.poi.adcode);
                        placeSearch.search(e.poi.name); // 关键字查询查询
                        that.$set(that.addFrom, "objKey", e.poi.district + e.poi.address);
                        that.$set(that.addFrom, "centerCoordinate", e.poi.location.lng + "," + e.poi.location.lat);
                        that.addFrom.lng = e.poi.location.lng;
                        that.addFrom.lat = e.poi.location.lat;
                        that.addFrom.multiCoordinate1 = [];
                        that.creatMarkers();
                    }
                }
                this.map.setFitView();
            },
            // 画覆盖物
            creatMarkers() {
                let that = this;
                if (this.circle) {
                    this.map.remove(this.circle);
                }
                if (this.overlays) {
                    this.map.remove(this.overlays);
                }
                if (this.polygon) {
                    this.map.remove(this.polygon);
                }
                if (this.addFrom.fenceType == 1 && this.addFrom.centerRadius && this.addFrom.centerCoordinate) {
                    this.circle = new AMap.Circle({
                        center: new AMap.LngLat(this.addFrom.lng, this.addFrom.lat), // 圆心位置
                        radius: this.addFrom.centerRadius, // 半径
                        strokeColor: "#F33", // 线颜色
                        strokeOpacity: 1, // 线透明度
                        strokeWeight: 3, // 线粗细度
                        fillColor: "#ee2200", // 填充颜色
                        fillOpacity: 0.35, // 填充透明度
                    });
                    this.map.add(this.circle);
                    this.map.setFitView();
                } else if (this.addFrom.fenceType == 2 && this.addFrom.centerCoordinate && that.addFrom.multiCoordinate1.length < 2) {
                    that.$set(that.addFrom, "centerRadius", "");
                    this.mouseTool = new AMap.MouseTool(this.map);
                    this.mouseTool.polygon({
                        fillColor: "#F33",
                        strokeColor: "#ee2200",
                        // 同Polygon的Option设置
                    });
                    this.overlays = [];
                    that.addFrom.multiCoordinate1 = [];
                    this.mouseTool.on("draw", function (e) {
                        that.overlays.push(e.obj);
                        e.obj.w.path.forEach((item) => {
                            that.addFrom.multiCoordinate1.push(item.lng + "," + item.lat);
                        });
                        that.mouseTool.close();
                    });
                    this.map.setFitView();
                } else if (this.addFrom.fenceType == 2 && this.addFrom.centerCoordinate && that.addFrom.multiCoordinate1.length > 2) {
                    let arr = [];
                    that.addFrom.multiCoordinate1.forEach((itme) => {
                        arr.push(itme.split(","));
                    });
                    this.polygon = new AMap.Polygon({
                        path: arr,
                        strokeColor: "#ee2200",
                        strokeWeight: 6,
                        strokeOpacity: 0.2,
                        fillOpacity: 0.4,
                        fillColor: "#F33",
                        zIndex: 50,
                    });
                    this.map.add(this.polygon);
                    this.map.setFitView();
                }
            },
            // 围栏类型改变
            fenceTypeChange() {
                if (this.circle) {
                    this.map.remove(this.circle);
                }
                if (this.overlays) {
                    this.map.remove(this.overlays);
                }
                this.creatMarkers();
            },
            FSBJfn() {
                this.creatMarkers();
            },
            saveFn(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (!this.addFrom.provinceName) {
                            this.$alert("请选择省市县", "系统提示");
                        } else if (!this.addFrom.objKey) {
                            this.$alert("请选择中心点", "系统提示");
                        } else {
                            let str1 = this.addFrom.provinceName.substring(0, 2);
                            let str2 = this.addFrom.objKey.substring(0, 2);
                            if (this.addFrom.fenceType == 2 && this.addFrom.multiCoordinate1.length < 3) {
                                this.$alert("请点击地图绘制多边形围栏", "系统提示");
                            } else if (str1 != str2) {
                                this.$alert("省市县与中心位置不在同一个城市", "系统提示");
                            } else {
                                this.addFrom.multiCoordinate = this.addFrom.multiCoordinate1.join(";");
                                if (this.isAdd) {
                                    const loading = this.$loading({
                                        lock: true,
                                        text: "正在处理...",
                                    });
                                    fenceAdd(this.addFrom)
                                        .then((res) => {
                                            loading.close();
                                            this.$message({
                                                message: "添加成功",
                                                type: "success",
                                            });
                                            this.dialogVisible = false;
                                            this.getList(this.searchFrom);
                                        })
                                        .catch((error) => {
                                            this.$message({
                                                showClose: true,
                                                message: error.message,
                                                type: "error",
                                                onClose() {
                                                    loading.close();
                                                },
                                            });
                                        });
                                } else {
                                    const loading = this.$loading({
                                        lock: true,
                                        text: "正在处理...",
                                    });
                                    updateFence(this.addFrom)
                                        .then((res) => {
                                            loading.close();
                                            this.$message({
                                                message: "操作成功",
                                                type: "success",
                                            });
                                            this.dialogVisible = false;
                                            this.getList(this.searchFrom);
                                        })
                                        .catch((error) => {
                                            this.$message({
                                                showClose: true,
                                                message: error.message,
                                                type: "error",
                                                onClose() {
                                                    loading.close();
                                                },
                                            });
                                        });
                                }
                            }
                        }
                    } else {
                        return false;
                    }
                });
            },
            // 打开弹框
            openedDialog() {
                this.initMap();
                if (!this.isAdd) {
                    this.marker = new AMap.Marker({
                        position: [this.addFrom.lng, this.addFrom.lat],
                    });
                    // 将创建的点标记添加到已有的地图实例：
                    this.map.add(this.marker);
                }
                this.creatMarkers();
            },
            openedDialog1() {
                let map = new AMap.Map("map1", {
                    center: [116.397428, 39.90923], // [纬度，经度]
                    resizeEnable: true,
                    zoom: 12,
                });
                let marker = new AMap.Marker({
                    position: [this.addFrom.lng, this.addFrom.lat],
                });
                // 将创建的点标记添加到已有的地图实例：
                map.add(marker);
                if (this.addFrom.fenceType == 1) {
                    let circle = new AMap.Circle({
                        center: new AMap.LngLat(this.addFrom.lng, this.addFrom.lat), // 圆心位置
                        radius: this.addFrom.centerRadius, // 半径
                        strokeColor: "#F33", // 线颜色
                        strokeOpacity: 1, // 线透明度
                        strokeWeight: 3, // 线粗细度
                        fillColor: "#ee2200", // 填充颜色
                        fillOpacity: 0.35, // 填充透明度
                    });
                    map.add(circle);
                    map.setFitView();
                } else if (this.addFrom.fenceType == 2) {
                    let arr = [];
                    this.addFrom.multiCoordinate1.forEach((itme) => {
                        arr.push(itme.split(","));
                    });
                    let polygon = new AMap.Polygon({
                        path: arr,
                        strokeColor: "#ee2200",
                        strokeWeight: 6,
                        strokeOpacity: 0.2,
                        fillOpacity: 0.4,
                        fillColor: "#F33",
                        zIndex: 50,
                    });
                    map.add(polygon);
                    map.setFitView();
                }
            },
            // 删除
            delFn(row) {
                this.$confirm("是否执行删除操作?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                })
                    .then(() => {
                        const loading = this.$loading({
                            lock: true,
                            text: "正在处理...",
                        });
                        fenceDelete({ id: row.id })
                            .then((res) => {
                                loading.close();
                                this.$message({
                                    message: "删除成功",
                                    type: "success",
                                });
                                this.getList(this.searchFrom);
                            })
                            .catch((error) => {
                                this.$message({
                                    showClose: true,
                                    message: error.message,
                                    type: "error",
                                    onClose() {
                                        loading.close();
                                    },
                                });
                            });
                    })
                    .catch(() => {});
            },
            // 停用启用
            endFn(row, n) {
                const loading = this.$loading({
                    lock: true,
                    text: "正在处理...",
                });
                fenceUpdate({ id: row.id, status: n })
                    .then((res) => {
                        loading.close();
                        this.$message({
                            message: "操作成功",
                            type: "success",
                        });
                        this.getList(this.searchFrom);
                    })
                    .catch((error) => {
                        this.$message({
                            showClose: true,
                            message: error.message,
                            type: "error",
                            onClose() {
                                loading.close();
                            },
                        });
                    });
            },
            // 查选
            searchFn() {
                if (this.searchFrom.cargoBarcodes) {
                    this.searchFrom.cargoBarcodes = this.searchFrom.cargoBarcodes.split(/[(\r\n)\r\n]+/);
                }

                this.getList(this.searchFrom);
            },
            // 选择数据
            selectGoods(selection) {},
            // 点击选中
            handleRowClick(row, column, event) {
                this.$refs.table.toggleRowSelection(row);
            },
            // 获取列表
            getList(searchFrom) {
                this.loading = true;
                fenceList(searchFrom).then((res) => {
                    this.loading = false;
                    this.tableList = res.list;
                    this.total = res.total;
                });
            },
            // 每页条数
            handleSizeChange(val) {
                this.searchFrom.pageSize = val;
                this.getList(this.searchFrom);
            },
            // 当前页
            handleCurrentChange(val) {
                this.searchFrom.pageNo = val;
                this.getList(this.searchFrom);
            },
            // 地区选中
            handleChange(value) {
                if (value) {
                    this.addFrom.provinceCode = value[0].areaCode;
                    this.addFrom.provinceName = value[0].name;
                    this.addFrom.cityCode = value[1].areaCode;
                    this.addFrom.areaCode = value[2].areaCode;
                    this.addFrom.cityName = value[1].name;
                    this.addFrom.areaName = value[2].name;
                }
            },
            closeDialog() {
                this.addFrom = { multiCoordinate1: [] };
                this.$refs.tipinput.value = "";
                this.flag = false;
            },
            resetForm1() {
                const id = this.addFrom.id;
                this.addFrom.id = "";
                this.flag = false;
                this.$nextTick(() => {
                    this.flag = true;
                });
                this.addFrom.id = id;
                this.addFrom.areaCode = "";
                this.addFrom.areaName = "";
                this.addFrom.centerCoordinate = "";
                this.addFrom.centerRadius = "";
                this.addFrom.cityCode = "";
                this.addFrom.cityName = "";
                this.addFrom.fenceType = 1;
                this.addFrom.lat = "";
                this.addFrom.lng = "";
                this.addFrom.multiCoordinate = "";
                this.addFrom.multiCoordinate1 = [];
                this.addFrom.name = "";
                this.addFrom.objKey = "";
                this.addFrom.provinceCode = "";
                this.addFrom.provinceName = "";
                // this.addFrom.time = "";
                //this.addFrom.useType = 1;
                this.map.clearMap();
                this.$refs.tipinput.value = "";
            },
            // 重置
            resetForm(formName) {
                // this.$refs[formName].resetFields()
                // this.searchFrom.createTime=null
                this.searchFrom = {
                    pageNo: 1,
                    pageSize: 20,
                    createTime: null,
                };
            },
            // 时间格式化
            dateFormat: function (row, column, cellValue) {
                var t = new Date(cellValue); // row 表示一行数据, transferTime 表示要格式化的字段名称
                var month = t.getMonth() + 1 < 10 ? "0" + (t.getMonth() + 1) : t.getMonth() + 1;
                var date = t.getDate() < 10 ? "0" + t.getDate() : t.getDate();
                var hour = t.getHours() < 10 ? "0" + t.getHours() : t.getHours();
                var minutes = t.getMinutes() < 10 ? "0" + t.getMinutes() : t.getMinutes();
                var seconds = t.getSeconds() < 10 ? "0" + t.getSeconds() : t.getSeconds();
                var str = "";
                if (cellValue == null) {
                    str = "";
                } else {
                    str = t.getFullYear() + "-" + month + "-" + date + " " + hour + ":" + minutes + ":" + seconds;
                }
                return str;
                // return t.getFullYear() + "-" + (t.getMonth() + 1) + "-" + t.getDate(); // 年月日
            },
            // 时间格式化
            dateFormat1: function (row, column, cellValue) {
                var t = new Date(cellValue); // row 表示一行数据, transferTime 表示要格式化的字段名称
                var month = t.getMonth() + 1 < 10 ? "0" + (t.getMonth() + 1) : t.getMonth() + 1;
                var date = t.getDate() < 10 ? "0" + t.getDate() : t.getDate();
                var str = "";
                if (cellValue == null) {
                    str = "";
                } else {
                    str = t.getFullYear() + "-" + month + "-" + date;
                }
                return str;
                // return t.getFullYear() + "-" + (t.getMonth() + 1) + "-" + t.getDate(); // 年月日
            },
        },
    };
</script>

<style scoped lang="less">
    .searchBtnBox {
        margin-left: 10px;
    }
    .imgBox {
        display: flex;
        .item {
            display: flex;
            width: 50%;
            justify-content: space-around;
        }
        /deep/ .hide .el-upload--picture-card {
            display: none;
        }
    }
    /deep/ .el-dialog__body {
        margin-top: 10px;
    }

    /deep/.jiaoyan .el-date-editor .el-input__inner {
        width: 150px;
    }
    /deep/.diqu2 .el-form-item__content .el-input__inner {
        width: 240px !important;
    }
    .mapBox {
        width: 100%;
        height: 560px;
        position: relative;
    }
    .map {
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
    .info hr {
        margin-right: 0;
        margin-left: 0;
        border-top-color: grey;
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
    /deep/.el-range-editor.is-active,
    .el-range-editor.is-active:hover {
        border-color: rgb(88, 204, 2);
        width: 451px;
    }
    .inpBox .el-date-editor {
        width: 423px !important;
    }
    // .inpBox /deep/.el-date-editor {
    //   width: 421px ;
    // }
    /deep/ .inpBox {
        .el-form-item {
            margin-right: 0 !important;
        }
    }
    .radius-unit {
        font-style: initial;
    }
</style>
