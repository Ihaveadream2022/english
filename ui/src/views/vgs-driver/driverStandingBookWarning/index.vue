<template>
    <div>
        <!-- 搜索条件 -->
        <div class="searchBox" ref="searchBox">
            <el-form :inline="true" :model="r.searchForm" ref="searchForm" class="demo-form-inline" label-width="100px" size="mini">
                <div class="inpBox">
                    <el-form-item label="委托单号 :" prop="billId">
                        <el-input placeholder="" v-model="r.searchForm.billId"></el-input>
                    </el-form-item>
                    <el-form-item label="车牌号 :" prop="vhcNo">
                        <el-input placeholder="" v-model="r.searchForm.vhcNo"></el-input>
                    </el-form-item>
                    <el-form-item label="进出类型 :" prop="accessType">
                        <el-select v-model="r.searchForm.accessType" placeholder="请选择">
                            <el-option label="请选择" value=""></el-option>
                            <el-option label="进厂" :value="0"></el-option>
                            <el-option label="出厂" :value="1"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="处理状态 :" prop="status">
                        <el-select v-model="r.searchForm.status" placeholder="请选择">
                            <el-option label="请选择" value=""></el-option>
                            <el-option label="未处理" :value="0"></el-option>
                            <el-option label="已处理" :value="1"></el-option>
                            <el-option label="不处理" :value="2"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="称重时间 :" prop="realTime">
                        <el-date-picker v-model="r.searchForm.realTime" format="yyyy-MM-dd" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
                    </el-form-item>
                    <el-form-item label="创建时间 :" prop="createTime">
                        <el-date-picker v-model="r.searchForm.createTime" format="yyyy-MM-dd" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
                    </el-form-item>
                </div>
                <div class="searchBtnBox">
                    <el-form-item v-if="r.btnFlg[0]">
                        <el-button type="primary" @click="RSearchFn">查询</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="RResetForm">重置</el-button>
                    </el-form-item>
                    <el-form-item v-if="r.btnFlg[1]">
                        <el-button type="primary" @click="RExportFn" :disabled="r.exportBtnDisable">导出</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </div>
        <!--  列表 -->
        <div class="table" :style="{ height: r.h }">
            <el-table :data="r.tableList" ref="table" v-loading="r.loading" bordersize="mini" @row-click="RHandleRowClick" height="100%" style="width: 100%" center border>
                <!-- <el-table-column type="selection" width="55"></el-table-column> -->
                <el-table-column fixed type="index" width="55" label="序号"></el-table-column>
                <el-table-column label="操作" width="120">
                    <template slot-scope="scope">
                        <div>
                            <el-button type="text" size="mini" @click="CUDDeal(scope.row)">处理</el-button>
                            <el-button type="text" size="mini" @click="CUDDelete(scope.row)">删除</el-button>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column property="status" label="处理状态">
                    <template scope="scope">
                        <div>
                            <span>{{ ["未处理", "已处理", "不处理"][scope.row.status] }}</span>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="description" min-width="220" :show-overflow-tooltip="true" label="预警描述"></el-table-column>
                <el-table-column prop="reason" min-width="220" :show-overflow-tooltip="true" label="处理原因"></el-table-column>
                <el-table-column prop="billId" min-width="220" :show-overflow-tooltip="true" label="委托单号"></el-table-column>
                <el-table-column prop="vhcNo" min-width="120" :show-overflow-tooltip="true" label="车牌号"></el-table-column>
                <el-table-column prop="realWeight" min-width="120" :show-overflow-tooltip="true" label="净重(吨)"></el-table-column>
                <el-table-column prop="realTime" min-width="180" :formatter="UtilsDateFormat" label="称重时间"></el-table-column>
                <el-table-column prop="receiveUnit" min-width="220" :show-overflow-tooltip="true" label="收货单位"></el-table-column>
                <el-table-column prop="provider" min-width="220" :show-overflow-tooltip="true" label="发货单位"></el-table-column>
                <el-table-column prop="materielName" min-width="220" :show-overflow-tooltip="true" label="物料信息"></el-table-column>
                <el-table-column property="accessType" label="进出类型">
                    <template scope="scope">
                        <div>
                            <span>{{ ["进厂", "出厂"][scope.row.accessType] }}</span>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" min-width="180" :formatter="UtilsDateFormat" label="创建时间"></el-table-column>
                <el-table-column prop="updateTime" min-width="180" :formatter="UtilsDateFormat" label="修改时间"></el-table-column>
            </el-table>
        </div>
        <!-- 分页码 -->
        <div>
            <el-pagination background @size-change="RHandleSizeChange" @current-change="RHandleCurrentChange" :current-page="r.searchForm.pageNo" :page-sizes="[25, 50, 100]" :page-size="r.searchForm.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="r.total"> </el-pagination>
        </div>
        <!-- 处理数据 -->
        <el-dialog title="处理数据" :close-on-click-modal="false" :close-on-press-escape="false" :visible.sync="cud.dialogVisible" @close="CUDHandleCloseDialog" @opened="CUDHandleOpenedDialog" width="500px" center>
            <span>
                <el-form :rules="cud.updateFormRules" :model="cud.updateForm" ref="CUDUpdateForm" label-width="100px" size="mini">
                    <el-form-item label="车牌号 :" class="medium">
                        {{ cud.updateRow.vhcNo }}
                    </el-form-item>
                    <el-form-item label="预警描述 :" class="medium">
                        {{ cud.updateRow.description }}
                    </el-form-item>
                    <el-form-item label="处理状态 : " prop="status" class="medium">
                        <el-select placeholder="请选择" v-model="cud.updateForm.status">
                            <el-option label="请选择" value=""></el-option>
                            <el-option label="未处理" :value="0"></el-option>
                            <el-option label="已处理" :value="1"></el-option>
                            <el-option label="不处理" :value="2"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="处理原因 :" prop="reason" class="medium">
                        <el-input placeholder="" type="textarea" v-model="cud.updateForm.reason"></el-input>
                    </el-form-item>
                </el-form>
            </span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="cud.dialogVisible = false">取 消</el-button>
                <el-button @click="CUDHandleSubmit" type="primary">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
    /* eslint-disable */
    import { warningList, warningListExport, warningUpdate, warningDelete } from "@/api/request";
    import { Loading } from "element-ui";
    import { getBtn } from "@/utils/btn";
    export default {
        data() {
            return {
                r: {
                    h: "500px",
                    exportBtnDisable: false,
                    loading: false,
                    total: 0,
                    tableList: [],
                    searchForm: {
                        pageNo: 1,
                        pageSize: 50,
                    },
                    btnFlg: [],
                    btnIdArr: ["00BM0H0100", "00BM0H0200"],
                },
                cud: {
                    core: "c",
                    dialogVisible: false,
                    updateRow: {},
                    updateForm: {},
                    updateFormRules: {
                        reason: [{ required: true, message: "请输入处理原因", trigger: "blur" }],
                        status: [
                            { required: true, message: "请选择处理状态", trigger: ["blur"] },
                            { pattern: /^[0-2]$/, message: "请选择处理状态" },
                        ],
                    },
                    uploadIconClass: [],
                    lookImgVisible: false,
                    dialogImageUrl: "",
                },
            };
        },
        created() {
            this.RGetList(this.r.searchForm);

            this.r.btnFlg = getBtn(this.r.btnIdArr, "/driverStandingBookWarning");

            console.log("btnFlg", this.r.btnFlg);
        },
        mounted() {
            let h = this.$refs.searchBox.offsetHeight;
            let pH = document.documentElement.clientHeight;
            this.r.h = pH - h - 96 + "px";
        },
        methods: {
            RHandleRowClick() {},
            RExportFn() {
                this.r.exportBtnDisable = true;
                setTimeout(() => {
                    this.r.exportBtnDisable = false;
                }, 5000);
                const downloadLoadingInstance = Loading.service({
                    text: "正在处理...",
                    spinner: "el-icon-loading",
                    background: "rgba(0, 0, 0, 0.7)",
                });
                warningListExport(this.r.searchForm).then(
                    (response) => {
                        console.log("response", response);
                        downloadLoadingInstance.close();
                        let blob = new Blob([response], {
                            type: "application/vnd.ms-excel",
                        });
                        let date = new Date();
                        let newTime = date.getFullYear() + "-" + (date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1) + "-" + date.getDate();
                        let fileName = "台账预警" + newTime + ".xls";
                        if (window.navigator.msSaveOrOpenBlob) {
                            navigator.msSaveBlob(blob, fileName);
                        } else {
                            var link = document.createElement("a");
                            link.href = window.URL.createObjectURL(blob);
                            link.download = fileName;
                            link.click();
                            window.URL.revokeObjectURL(link.href); // 释放内存
                        }
                    },
                    (error) => {
                        downloadLoadingInstance.close();
                        console.log("error", error);
                    }
                );
            },
            RSearchFn() {
                console.log(this.r.searchForm);
                this.RGetList(this.r.searchForm);
            },
            RResetForm() {
                this.r.searchForm = {
                    pageNo: 1,
                    pageSize: 50,
                };
            },
            RHandleSizeChange(val) {
                this.r.searchForm.pageSize = val;
                this.RGetList(this.r.searchForm);
            },
            RHandleCurrentChange(val) {
                this.r.searchForm.pageNo = val;
                this.RGetList(this.r.searchForm);
            },
            RGetList(searchForm) {
                this.r.loading = true;
                warningList(searchForm).then(
                    (res) => {
                        this.r.loading = false;
                        this.r.tableList = res.data.list;
                        this.r.total = res.data.total;
                        console.log(this.r.tableList);
                        if (!this.r.tableList.length && !this.r.total) {
                            this.r.tableList = [
                                {
                                    id: 1, // 主键ID
                                    tenantId: "tenantId", // 租户id
                                    tenantName: "tenantName", // 租户名称
                                    billId: "billId", // 委托单号
                                    vhcNo: "vhcNo", // 车牌号
                                    realWeight: "realWeight", // 净重
                                    realTime: 1652406446000, // 称重时间
                                    receiveUnit: "receiveUnit", // 收货单位
                                    provider: "provider", // 发货单位
                                    materielName: "materielName", //物料信息
                                    accessType: "1", //进出类型：0：进厂；1出厂
                                    status: 1, //状态：0 未处理，1 已处理，2不处理
                                    description: "description", //预警描述
                                    reason: "reason", // 处理原因
                                    createTime: 1652406446000, // 创建时间
                                    updateTime: 1652406446000, // 修改时间
                                },
                                {
                                    id: 2, // 主键ID
                                    tenantId: "tenantId2", // 租户id
                                    tenantName: "tenantName2", // 租户名称
                                    billId: "billId2", // 委托单号
                                    vhcNo: "vhcNo2", // 车牌号
                                    realWeight: "realWeight2", // 净重
                                    realTime: 1652406445000, // 称重时间
                                    receiveUnit: "receiveUnit2", // 收货单位
                                    provider: "provider2", // 发货单位
                                    materielName: "materielName2", //物料信息
                                    accessType: "1", //进出类型：0：进厂；1出厂
                                    status: 2, //状态：0 未处理，1 已处理，2不处理
                                    description: "description2", //预警描述
                                    reason: "reason2", // 处理原因
                                    createTime: 1652406445000, // 创建时间
                                    updateTime: 1652406445000, // 修改时间
                                },
                                {
                                    id: 3, // 主键ID
                                    tenantId: "tenantId2", // 租户id
                                    tenantName: "tenantName2", // 租户名称
                                    billId: "billId2", // 委托单号
                                    vhcNo: "vhcNo2", // 车牌号
                                    realWeight: "realWeight2", // 净重
                                    realTime: 1652406445000, // 称重时间
                                    receiveUnit: "receiveUnit2", // 收货单位
                                    provider: "provider2", // 发货单位
                                    materielName: "materielName2", //物料信息
                                    accessType: "1", //进出类型：0：进厂；1出厂
                                    status: 0, //状态：0 未处理，1 已处理，2不处理
                                    description: "description2", //预警描述
                                    reason: "reason2", // 处理原因
                                    createTime: 1652406445000, // 创建时间
                                    updateTime: 1652406445000, // 修改时间
                                },
                            ];
                            this.r.total = 3;
                        }
                    },
                    (res) => {
                        this.$message({
                            showClose: true,
                            message: res.message,
                            type: "error",
                            onClose: () => {
                                this.r.loading = false;
                            },
                        });
                    }
                );
            },
            CUDDelete(row) {
                this.$confirm("是否执行删除操作?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                })
                    .then(() => {
                        this.r.loading = true;
                        warningDelete({ id: row.id }).then(
                            (res) => {
                                this.r.loading = false;
                                this.$message({
                                    message: res.message,
                                    type: "success",
                                    duration: 1500,
                                });
                                this.RGetList(this.r.searchForm);
                            },
                            (res) => {
                                this.$message({
                                    showClose: true,
                                    message: res.message,
                                    type: "error",
                                    duration: 1500,
                                    onClose: () => {
                                        this.r.loading = false;
                                    },
                                });
                            }
                        );
                    })
                    .catch(() => {});
            },
            CUDDeal(row) {
                this.cud.dialogVisible = true;
                this.cud.updateRow = row;
                this.cud.updateForm = {
                    id: row.id,
                    status: row.status,
                    reason: row.reason,
                };
            },
            CUDHandleOpenedDialog() {},
            CUDHandleCloseDialog() {
                this.cud.updateForm = {};
                this.$refs.CUDUpdateForm.clearValidate();
            },
            CUDHandleSubmit() {
                this.$refs["CUDUpdateForm"].validate((valid) => {
                    console.log(this.cud.updateForm);
                    if (valid) {
                        warningUpdate(this.cud.updateForm).then(
                            (res) => {
                                this.cud.dialogVisible = false;
                                this.$message({
                                    message: res.message,
                                    type: "success",
                                    duration: 1500,
                                });
                                this.RGetList(this.r.searchForm);
                            },
                            (res) => {
                                this.cud.dialogVisible = false;
                                this.$message({
                                    showClose: true,
                                    message: res.message,
                                    type: "error",
                                    duration: 1500,
                                    onClose: () => {},
                                });
                            }
                        );
                    } else {
                        console.log("valid no:", this.cud.updateForm);
                    }
                });
            },
            UtilsDateFormat: function (row, column, cellValue) {
                if (cellValue) {
                    var t = new Date(cellValue); // row 表示一行数据, transferTime 表示要格式化的字段名称
                    var month = t.getMonth() + 1 < 10 ? "0" + (t.getMonth() + 1) : t.getMonth() + 1;
                    var date = t.getDate() < 10 ? "0" + t.getDate() : t.getDate();
                    var hour = t.getHours() < 10 ? "0" + t.getHours() : t.getHours();
                    var minutes = t.getMinutes() < 10 ? "0" + t.getMinutes() : t.getMinutes();
                    var seconds = t.getSeconds() < 10 ? "0" + t.getSeconds() : t.getSeconds();
                    return t.getFullYear() + "-" + month + "-" + date + " " + hour + ":" + minutes + ":" + seconds;
                }
            },
            UtilsDateFormat1: function (row, column, cellValue) {
                var t = new Date(cellValue); // row 表示一行数据, transferTime 表示要格式化的字段名称
                var month = t.getMonth() + 1 < 10 ? "0" + (t.getMonth() + 1) : t.getMonth() + 1;
                var date = t.getDate() < 10 ? "0" + t.getDate() : t.getDate();
                let str = "";
                if (cellValue) {
                    str = t.getFullYear() + "-" + month + "-" + date;
                }
                return str;
            },
        },
    };
</script>
<style lang="scss" scoped>
    .searchBox {
        display: flex;
        .searchBtnBox {
            padding-left: 10px;
            margin-left: 10px;
            ::v-deep .el-form-item--mini.el-form-item,
            ::v-deep .el-form-item--small.el-form-item {
                margin-bottom: 6px !important;
            }
        }
        .inpBox {
            ::v-deep .el-form-item--mini.el-form-item,
            ::v-deep .el-form-item--small.el-form-item {
                margin-bottom: 6px !important;
            }
            // ::v-deep .el-form-item.long {
            //     width: 100%;
            //     margin-right: 0 !important;
            // }
            // ::v-deep .el-form-item.medium {
            //     width: 50%;
            //     margin-right: 0 !important;
            // }
            ::v-deep .el-input__inner {
                width: 150px !important;
            }
            ::v-deep .el-date-editor {
                width: 350px !important;
            }
        }
    }
    .table {
        ::v-deep .el-table {
            color: #333;
            th {
                background-color: #f5f5f5;
                height: 40px;
                color: #333333 !important;
                padding: 2px 0 !important;
                &:first-child .cell {
                    padding: 0 10px 0 10px;
                }
            }
            tr {
                height: 32px;
                &:nth-child(even) {
                    background-color: #f8fafd;
                }
            }
            td {
                padding: 2px 0 !important;
            }
            .cell {
                text-align: center;
                padding: 0 10px;
            }
            .el-button--primary {
                background-color: rgb(88, 204, 2);
            }
            .el-button--primary:focus,
            .el-button--primary:hover {
                background: #0b59bd;
                border-color: #0b59bd;
                color: #fff;
            }
            .el-button {
                padding: 7px 15px;
            }
            .el-button--text {
                padding: 0;
            }
            .bigBtn {
                padding: 12px 28px;
            }
            .el-button--text {
                color: rgb(88, 204, 2);
            }
            .el-button--text:focus,
            .el-button--text:hover {
                color: #0b59bd;
            }
            .el-table__body tr.hover-row > td {
                background-color: rgba(14, 110, 236, 0.3) !important;
            }
            .el-table__body tr.current-row > td {
                background-color: rgba(14, 110, 236, 0.3) !important;
            }
            .el-table--enable-row-hover .el-table__body tr:hover > td {
                background-color: rgba(14, 110, 236, 0.3) !important;
            }
        }
    }
    .el-dialog__wrapper {
        ::v-deep .el-dialog__header {
            padding: 8px 20px !important;
            background-color: rgb(88, 204, 2);
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
        .hidden ::v-deep .el-upload--picture-card {
            display: none;
        }
        .addressBox .title {
            margin-top: 0;
            margin-left: 10px;
            font-size: 16px;
            margin-bottom: 20px;
            .iconFont {
                color: rgb(88, 204, 2);
                margin-right: 4px;
                font-size: 14px;
            }
        }
        ::v-deep .el-upload--picture-card {
            background-image: url("../../../assets/images/upload.png");
            background-size: 150%;
            background-position: 50% 50%;
        }
        ::v-deep .el-input__inner,
        ::v-deep .el-textarea__inner {
            width: 250px !important;
        }
        ::v-deep .el-textarea__inner {
            height: 100px;
            margin-bottom: 0 !important;
        }
        .el-dialog__footer {
            .el-button {
                padding: 8px 18px !important;
            }
        }
    }
</style>
