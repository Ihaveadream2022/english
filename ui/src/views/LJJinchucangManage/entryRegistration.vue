<template>
    <div>
        <div class="detailsTitle">
            <h4>进仓登记</h4>
        </div>
        <div class="searchBox" ref="searchBox">
            <el-form :inline="true" class="demo-form-inline" label-width="110px" size="mini">
                <div class="inpBox">
                    <el-form-item label="登记日期">
                        <el-date-picker format="yyyy-MM-dd" value-format="yyyy-MM-dd" v-model="value1" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"> </el-date-picker>
                    </el-form-item>
                    <el-form-item label="队列">
                        <el-select v-model="searchFrom.queueId" placeholder="请选择">
                            <el-option v-for="item in duilieList" :key="item.queueName" :label="item.queueName" :value="item.id"> </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="车牌号">
                        <el-input size="mini" v-model.trim="searchFrom.vehicleNo" placeholder=""></el-input>
                    </el-form-item>
                    <el-form-item label="状态">
                        <el-select v-model="searchFrom.jobStatus" placeholder="请选择">
                            <el-option v-for="item in zhuangtaiList" :key="item.itemNo" :label="item.showName" :value="item.itemNo"> </el-option>
                        </el-select>
                    </el-form-item>
                    <div style="margin: 10px 0 0 20px">
                        <el-form-item>
                            <el-button type="primary" @click="searchFn" btnCode="01K00A0100" v-if="btnFlg[0]">查询</el-button>
                            <el-button type="primary" @click="resetForm" btnCode="01K00A0200" v-if="btnFlg[1]">重置</el-button>
                            <el-button type="primary" @click="createRegisterDialog = true" btnCode="01K00A0300" v-if="btnFlg[2]">新增</el-button>
                        </el-form-item>
                    </div>
                </div>
            </el-form>
        </div>
        <!--  表格 -->
        <div class="table" :style="{ height: h }">
            <el-table :data="tableList" ref="table" bordersize="mini" @selection-change="selectGoods" style="width: 100%" height="100%" center border>
                <el-table-column type="index" prop="date" width="55" label="序号" align="center"></el-table-column>
                <el-table-column property="registrationNo" label="登记编号"></el-table-column>
                <el-table-column property="queueName" label="队列"></el-table-column>
                <el-table-column prop="lineUpNo" label="排队序号"></el-table-column>
                <el-table-column prop="vehicleNo" label="车牌号"></el-table-column>
                <el-table-column :show-overflow-tooltip="true" prop="mobilePhone" label="手机号" align="center" width="150"></el-table-column>
                <el-table-column prop="jobStatusName" label="作业类型">
                    <template slot-scope="scope">
                        <div>
                            <!-- 1：排队中 2：已叫号 3：已过号 4：已失效 5：已进场 6：进厂过磅完成 7：出厂过主磅完成 8：出厂过副磅完成 9：已出厂 -->
                            <span v-if="scope.row.jobStatusName == '排队中'" style="color: blue"> 排队中 </span>
                            <span v-else-if="scope.row.jobStatusName == '已叫号'" style="color: red"> 已叫号 </span>
                            <span v-else-if="scope.row.jobStatusName == '已过号'" style="color: rgb(242, 159, 25)"> 已过号 </span>
                            <span v-else>
                                {{ scope.row.jobStatusName }}
                            </span>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="createUser" label="创建人"></el-table-column>
                <el-table-column prop="productType" label="货物类型"></el-table-column>
                <!-- <el-table-column prop="jobStatusName" label="作业状态"></el-table-column> -->
                <el-table-column prop="registrationTime" :formatter="dateTimeFormat" label="登记时间"></el-table-column>
            </el-table>
        </div>
        <!-- 分页 -->
        <div>
            <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="searchFrom.pageNo" :page-sizes="[20, 30, 40, 50]" :page-size="searchFrom.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total"> </el-pagination>
        </div>
        <!-- 新增登记 -->
        <el-dialog title="新增登记" :close-on-click-modal="false" :close-on-press-escape="false" :visible.sync="createRegisterDialog" @close="createRegisterCloseDialog" @opened="createRegisterOpenedDialog" width="400px" center>
            <span>
                <el-form :rules="createRegisterFormRules" :model="createRegisterForm" ref="createRegisterForm" label-width="100px" size="mini">
                    <!-- 手动输入车牌号 -->
                    <el-form-item label="车牌号 :" class="medium" prop="vehicleNo">
                        <el-input placeholder="请输入车牌号" v-model="createRegisterForm.vehicleNo" v-on:input.native="createRegisterUpperVehicleNo" style="width: 190px"></el-input>
                    </el-form-item>
                    <!-- 模糊搜索手机号 -->
                    <el-form-item label="手机号 :" class="medium" prop="mobilePhone">
                        <el-select v-model="createRegisterForm.mobilePhone" :multiple="false" filterable remote reserve-keyword placeholder="请输入手机号" :remote-method="createRegisterRemoteMethod" :loading="createRegisterLoading" loading-text="搜索中..." @change="createRegisterSelect">
                            <el-option v-for="item in createRegisterMobilePhoneOptons" :key="item.value" :label="item.label" :value="item.value"> </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="队列 :" prop="queueId">
                        <el-select v-model="createRegisterForm.queueId" placeholder="请选择">
                            <el-option v-for="item in duilieList" :key="item.queueName" :label="item.queueName" :value="item.id"> </el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
            </span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="createRegisterDialog = false">取 消</el-button>
                <el-button @click="createRegisterSubmit" type="primary">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import { getBtn } from "@/utils/btn";
    import { emptyFn, dateTimeFormat, dateFormat } from "@/utils/util";
    import DonMessage from "@/utils/message";
    import { getSchedulingAssignmentList, getQueueSettingsList, getJobStatusList, searchPhoneNumber, register } from "@/api/request";
    export default {
        data() {
            return {
                h: 500,
                dateTimeFormat: dateTimeFormat,
                dateFormat: dateFormat,
                emptyFn: emptyFn,
                tableList: [],
                searchFrom: {
                    pageNo: 1,
                    pageSize: 20,
                },
                total: 0,
                duilieList: [],
                zhuangtaiList: [],
                btnFlg: [],
                btnIdArr: ["01K00A0100", "01K00A0200", "01K00A0300"],
                createRegisterFormRules: {
                    vehicleNo: [
                        { required: true, message: "请输入车牌号", trigger: ["blur", "change"] },
                        { pattern: /[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领]{1}[A-Z]{1}[A-Z0-9]{5,6}/g, message: "请输入正确的车牌号" },
                    ],
                    mobilePhone: [{ required: true, message: "请输入手机号", trigger: ["change"] }],
                    queueId: [{ required: true, message: "请选择队列", trigger: ["change"] }],
                },
                createRegisterForm: {},
                createRegisterDialog: false,
                createRegisterLoading: false,
                createRegisterMobilePhoneOptons: [],
            };
        },
        created() {
            this.btnFlg = getBtn(this.btnIdArr, "/entryRegistration");
            this.value1 = this.getNowTime();
            this.getList();

            getQueueSettingsList(this.searchFrom)
                .then((res) => {
                    this.duilieList = res.data;
                })
                .catch((err) => {
                    DonMessage.error(err.message);
                });
            getJobStatusList(this.searchFrom)
                .then((res) => {
                    this.zhuangtaiList = res.data;
                })
                .catch((err) => {
                    DonMessage.error(err.message);
                });
        },
        mounted() {
            let h = this.$refs.searchBox.offsetHeight;
            let pH = document.documentElement.clientHeight;
            this.h = pH - h - 134 + "px";
        },
        methods: {
            //初始默认执行(重点)
            getNowTime() {
                const start = new Date(new Date().getTime() - 3600 * 1000 * 24 * 7).toISOString().split("T")[0]; //默认开始时间7天前
                const end = new Date(new Date().getTime()).toISOString().split("T")[0]; //默认结束时间1天前
                console.log([start, end]);
                return [start, end];
            },
            // 审核
            passClick(row) {
                this.addFrom = { ...row };
                this.drawerFlag = true;
            },
            successFn() {
                this.getList();
            },
            destroy() {
                this.drawerFlag = false;
            },

            // 查选
            searchFn() {
                this.getList(this.searchFrom);
            },
            // 选择数据
            selectGoods(selection) {},
            // 获取列表
            getList() {
                const loading = this.$loading({
                    lock: true,
                    text: "正在处理...",
                });
                if (this.value1) {
                    this.searchFrom.registrationTimeStart = this.value1[0];
                    this.searchFrom.registrationTimeEnd = this.value1[1];
                    // this.searchFrom.value1=undefined
                }
                getSchedulingAssignmentList(this.searchFrom)
                    .then((res) => {
                        this.tableList = res.data.list;
                        this.total = res.data.total;
                        loading.close();
                    })
                    .catch((err) => {
                        loading.close();

                        DonMessage.error(err.message);
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
            // 重置
            resetForm(formName) {
                // debugger
                this.searchFrom = {
                    pageNo: 1,
                    pageSize: 20,
                };
                this.value1 = this.getNowTime();
                this.getList();
            },

            closeDialog() {
                this.$refs.table.clearSelection();
                this.$refs.addFrom.clearValidate();
            },
            createRegisterCloseDialog() {
                console.log("createRegisterCloseDialog");
                this.createRegisterDialog = false;
                this.createRegisterForm = {};
                this.$nextTick(() => {
                    this.$refs["createRegisterForm"].clearValidate();
                });
            },
            createRegisterOpenedDialog() {
                console.log("createRegisterOpenedDialog");
            },
            createRegisterSubmit() {
                this.$refs["createRegisterForm"].validate((valid) => {
                    if (valid) {
                        const form = {
                            driverId: this.createRegisterForm.driverId,
                            queueId: this.createRegisterForm.queueId,
                            vehicleNo: this.createRegisterForm.vehicleNo,
                        };
                        const loading2 = this.$loading({
                            lock: true,
                            text: "正在处理...",
                        });
                        register(form)
                            .then(
                                (res) => {
                                    console.log("res1", res);
                                    if (res.code == 0) {
                                        DonMessage.success(res.message);
                                    } else {
                                        DonMessage.error(res.message);
                                    }
                                    loading2.close();
                                    this.createRegisterCloseDialog();
                                },
                                (res) => {
                                    DonMessage.error(res.message);
                                    console.log("res2", res);
                                    loading2.close();
                                }
                            )
                            .catch((err) => {
                                console.log("catch err", err);
                                loading2.close();
                                DonMessage.error(err.message);
                            });
                        console.log("valid", form);
                    } else {
                        console.log("invalid", this.createRegisterForm);
                    }
                });
            },
            createRegisterUpperVehicleNo() {
                // const abc = ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"];
                // const r = this.createRegisterForm.vehicleNo.split("").map((v) => {
                //     if (abc.indexOf(v) >= 0) {
                //         return v.toUpperCase();
                //     } else {
                //         return v;
                //     }
                // });
                // this.createRegisterForm.vehicleNo = r.join("");
            },
            createRegisterSelect(v) {
                this.createRegisterForm.driverId = v;
                console.log("createRegisterSelect", v);
            },
            createRegisterRemoteMethod(query) {
                console.log("query", query);
                if (query !== "") {
                    this.createRegisterLoading = true;
                    searchPhoneNumber({ account: query }).then(
                        (res) => {
                            console.log("success:", res);
                            this.createRegisterMobilePhoneOptons = res.data.map((v) => {
                                return { value: v.id, label: v.account };
                            });
                            this.createRegisterLoading = false;
                        },
                        (res) => {
                            console.log("fail:", res);
                            DonMessage.error(res.message);
                            this.createRegisterLoading = false;
                        }
                    );
                }
            },
        },
    };
</script>

<style lang="scss" scoped>
    .searchBtnBox {
        margin-left: 10px;
    }

    .inpBox .el-date-editor {
        width: 350px !important;
    }
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
            width: 190px !important;
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
