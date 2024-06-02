<template>
    <div>
        <div class="detailsTitle">
            <h4>人车信息</h4>
        </div>
        <!-- 搜索条件 -->
        <div class="searchBox" ref="searchBox">
            <el-form :inline="true" :model="r.searchForm" ref="searchForm" class="demo-form-inline" label-width="100px" size="mini">
                <div class="inpBox">
                    <el-form-item label="司机姓名 :" prop="driverName">
                        <el-input placeholder="" v-model="r.searchForm.driverName"></el-input>
                    </el-form-item>
                    <el-form-item label="身份证号 :" prop="identityNo">
                        <el-input placeholder="" v-model="r.searchForm.identityNo"></el-input>
                    </el-form-item>
                    <el-form-item label="联系电话 :" prop="mobile">
                        <el-input placeholder="" v-model="r.searchForm.mobile"></el-input>
                    </el-form-item>
                    <el-form-item label="车牌号 :" prop="vehicleNo">
                        <el-input placeholder="" v-model="r.searchForm.vehicleNo"></el-input>
                    </el-form-item>
                    <el-form-item label="黑白标记 :" prop="marker">
                        <el-select v-model="r.searchForm.marker" placeholder="请选择">
                            <el-option label="请选择" value=""></el-option>
                            <el-option label="黑名单" value="黑名单"></el-option>
                            <el-option label="白名单" value="白名单"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="创建时间 :" prop="createTime">
                        <el-date-picker v-model="r.searchForm.createTime" format="yyyy-MM-dd" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
                    </el-form-item>
                </div>
                <div class="searchBtnBox">
                    <el-form-item v-if="r.btnFlg[1]">
                        <el-button type="primary" @click="RSearch">查询</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="RResetForm">重置</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </div>
        <!--  列表 -->
        <div class="table" :style="{ height: r.h }">
            <el-table :data="r.tableList" ref="table" v-loading="r.loading" bordersize="mini" @row-click="RHandleRowClick" height="100%" style="width: 100%" center border>
                <el-table-column fixed type="index" width="55" label="序号"></el-table-column>
                <el-table-column label="操作" width="180" v-if="r.btnFlg[2] || r.btnFlg[0]">
                    <template slot-scope="scope">
                        <div>
                            <el-button type="text" size="mini" v-if="r.btnFlg[2]" @click="CUDUpdateBlack(scope.row)">标记</el-button>
                            <el-button type="text" size="mini" v-if="r.btnFlg[0]" @click="CUDDelete(scope.row)">删除</el-button>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="marker" min-width="120" :show-overflow-tooltip="true" label="标记黑白"></el-table-column>
                <el-table-column prop="remark" min-width="220" :show-overflow-tooltip="true" label="标记备注"></el-table-column>
                <el-table-column prop="vehicleNo" min-width="120" :show-overflow-tooltip="true" label="车牌号"></el-table-column>
                <el-table-column prop="driverName" min-width="120" :show-overflow-tooltip="true" label="司机姓名"></el-table-column>
                <el-table-column prop="mobile" min-width="120" :show-overflow-tooltip="true" label="联系电话"></el-table-column>
                <el-table-column prop="identityNo" min-width="220" :show-overflow-tooltip="true" label="身份证号"></el-table-column>
                <el-table-column prop="createTime" min-width="180" :formatter="UtilsDateFormat" label="创建时间"></el-table-column>
            </el-table>
        </div>
        <!-- 分页码 -->
        <div>
            <el-pagination background @size-change="RHandleSizeChange" @current-change="RHandleCurrentChange" :current-page="r.searchForm.pageNo" :page-sizes="[25, 50, 100]" :page-size="r.searchForm.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="r.total"> </el-pagination>
        </div>
        <!-- 黑名单 -->
        <el-dialog title="标记黑白" :close-on-click-modal="false" :close-on-press-escape="false" :visible.sync="cud.dialogVisibleBlack" @close="CUDHandleCloseDialogBlack" @opened="CUDHandleOpenedDialogBlack" width="470px" center>
            <span>
                <el-form :rules="cud.CURulesBlack" :model="cud.CUFormBlack" ref="CUFormBlack" label-width="100px" size="mini">
                    <el-form-item label="标记黑白 : " class="medium" prop="marker">
                        <el-radio v-model="cud.CUFormBlack.marker" label="黑名单">黑名单</el-radio>
                        <el-radio v-model="cud.CUFormBlack.marker" label="白名单">白名单</el-radio>
                    </el-form-item>
                    <el-form-item label="标记备注 :" class="medium" prop="remark">
                        <el-input placeholder="" type="textarea" v-model="cud.CUFormBlack.remark"></el-input>
                    </el-form-item>
                </el-form>
            </span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="CUDHandleCloseDialogBlack">取 消</el-button>
                <el-button @click="CUDHandleSubmitBlack" type="primary">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
    /* eslint-disable */
    import { driverVehicleInList, markDriverVehicle, deleteDriverVehicle, detailByIDDriverVehicle, detailByPlateNumberDriverVehicle } from "@/api/request";
    import { getBtn } from "@/utils/btn";
    export default {
        data() {
            return {
                r: {
                    h: "500px",
                    loading: false,
                    total: 0,
                    tableList: [],
                    searchForm: {
                        pageNo: 1,
                        pageSize: 50,
                    },
                    btnFlg: [],
                    btnIdArr: ["01300A0100", "01300A0200", "01300A0300"],
                },
                cud: {
                    core: "c",
                    dialogVisibleBlack: false,
                    CURulesBlack: {
                        marker: [{ required: true, message: "选择标记类型", trigger: ["change"] }],
                    },
                    CUFormBlack: {
                        marker: "", // 标记黑白
                        remark: "", // 备注
                    },
                },
            };
        },
        created() {
            this.RGetList(this.r.searchForm);

            this.r.btnFlg = getBtn(this.r.btnIdArr, "/driverVehicle");

            console.log("btnFlg", this.r.btnFlg);
        },
        mounted() {
            let h = this.$refs.searchBox.offsetHeight;
            let pH = document.documentElement.clientHeight;
            this.r.h = pH - h - 135 + "px";
        },
        methods: {
            RHandleRowClick() {},
            RSearch() {
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
                detailByIDDriverVehicle({ id: 3 }).then((res) => {
                    console.log("detailByIDDriverVehicle", res);
                });
                detailByPlateNumberDriverVehicle({ outstockPlateNumber: "京B66666" }).then((res) => {
                    console.log("detailByPlateNumberDriverVehicle", res);
                });
                if (searchForm.createTime) {
                    searchForm.createTimeStart = searchForm.createTime[0] ;
                    searchForm.createTimeEnd = searchForm.createTime[1];
                }
                console.log("searchForm", searchForm);
                this.r.loading = true;
                driverVehicleInList(searchForm)
                    .then((res) => {
                        console.log("res", res);
                        this.r.loading = false;
                        if (res.data) {
                            this.r.tableList = res.data.list;
                            this.r.total = res.data.total;
                        } else {
                            this.r.tableList = res.list;
                            this.r.total = res.total;
                        }
                    })
                    .catch((error) => {
                        this.r.loading = false;
                        this.$message({
                            showClose: true,
                            message: error instanceof Object && error.message != undefined ? error.message : "操作失败",
                            type: "error",
                            onClose() {},
                        });
                    });
            },
            CUDDelete(row) {
                this.$confirm("是否执行删除操作?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                })
                    .then(() => {
                        const loading = this.$loading({
                            lock: true,
                            text: "正在处理...",
                        });
                        deleteDriverVehicle({ id: row.id })
                            .then((res) => {
                                loading.close();
                                this.RGetList(this.r.searchForm);
                                this.$message({
                                    message: res instanceof Object && res.message != undefined ? res.message : "操作成功",
                                    type: "success",
                                    duration: 1500,
                                });
                            })
                            .catch((error) => {
                                loading.close();
                                this.RGetList(this.r.searchForm);
                                this.$message({
                                    showClose: true,
                                    message: error instanceof Object && error.message != undefined ? error.message : "操作失败",
                                    type: "error",
                                    onClose() {},
                                });
                            });
                    })
                    .catch(() => {});
            },
            CUDUpdateBlack(row) {
                this.cud.dialogVisibleBlack = true;
                this.cud.CUFormBlack = {
                    id: row.id,
                    marker: row.marker,
                    remark: row.remark,
                };
            },
            CUDHandleOpenedDialog() {},
            CUDHandleOpenedDialogBlack() {},
            CUDHandleCloseDialogBlack() {
                this.cud.CUFormBlack = {};
                this.$nextTick(() => {
                    this.$refs.CUFormBlack.clearValidate();
                    this.cud.dialogVisibleBlack = false;
                });
            },
            CUDHandleSubmitBlack() {
                this.$refs["CUFormBlack"].validate((valid) => {
                    console.log(this.cud.CUFormBlack);
                    if (valid) {
                        const loading = this.$loading({
                            lock: true,
                            text: "正在处理...",
                        });
                        markDriverVehicle(this.cud.CUFormBlack)
                            .then((res) => {
                                loading.close();
                                this.CUDHandleCloseDialogBlack();
                                this.RGetList(this.r.searchForm);
                                this.$message({
                                    message: res instanceof Object && res.message != undefined ? res.message : "操作成功",
                                    type: "success",
                                    duration: 1500,
                                    onClose() {},
                                });
                            })
                            .catch((error) => {
                                loading.close();
                                this.CUDHandleCloseDialogBlack();
                                this.RGetList(this.r.searchForm);
                                this.$message({
                                    showClose: true,
                                    message: error instanceof Object && error.message != undefined ? error.message : "操作失败",
                                    type: "error",
                                    onClose() {},
                                });
                            });
                    } else {
                        console.log("valid no:", this.cud.CUFormBlack);
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
    .detailsTitle {
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
        .el-form-item--mini.el-form-item,
        .el-form-item--small.el-form-item {
            margin-bottom: 22px;
        }
    }
</style>
