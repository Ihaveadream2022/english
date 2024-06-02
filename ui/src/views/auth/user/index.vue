<template>
    <div>
        <div class="title">
            <h4>User Management</h4>
        </div>
        <!-- 搜索框 -->
        <div class="searchBox" ref="searchBox">
            <el-form :inline="true" :model="table.searchForm" ref="searchForm" class="demo-form-inline" label-width="100px" size="mini">
                <div class="inpBox">
                    <el-form-item label="Username :" prop="username">
                        <el-input placeholder="" v-model="table.searchForm.username"></el-input>
                    </el-form-item>
                    <el-form-item label="Phone :" prop="phone">
                        <el-input placeholder="" v-model="table.searchForm.phone"></el-input>
                    </el-form-item>
                    <el-form-item label="Email :" prop="email">
                        <el-input placeholder="" v-model="table.searchForm.email"></el-input>
                    </el-form-item>
                    <el-form-item label="Gender :" prop="marker">
                        <el-select v-model="table.searchForm.gender" placeholder="Please Select">
                            <el-option label="Please Select" value=""></el-option>
                            <el-option label="Male" value="0"></el-option>
                            <el-option label="Female" value="1"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="Create Time :" prop="createTime">
                        <el-date-picker v-model="table.searchForm.createTime" format="yyyy-MM-dd" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="From" end-placeholder="To"></el-date-picker>
                    </el-form-item>
                </div>
                <div class="searchBtnBox">
                    <el-form-item>
                        <el-button type="primary" @click="tableSearch">Query</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="tableResetForm">Reset</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="editDialogOpen">Create</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </div>
        <div class="table" :style="{ height: table.height }">
            <el-table :data="table.tableList" ref="table" v-loading="table.loading" @row-click="tableHandleRowClick" highlight-current-row bordersize="mini" height="100%" style="width: 100%" center border>
                <el-table-column fixed type="index" width="55" label="No."></el-table-column>
                <el-table-column label="Operation" width="180">
                    <template slot-scope="scope">
                        <div>
                            <el-button type="text" size="mini" v-show="scope.row.status == 0" @click="editStatus(scope.row, 1)">Block</el-button>
                            <el-button type="text" size="mini" v-show="scope.row.status == 1" @click="editStatus(scope.row, 0)">Resume</el-button>
                            <el-button type="text" size="mini" @click="editDialogEdit(scope.row)">Edit</el-button>
                            <el-button type="text" size="mini" @click="editDelete(scope.row)">Delete</el-button>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="username" min-width="180" label="Username"></el-table-column>
                <el-table-column prop="email" min-width="180" label="Email"></el-table-column>
                <el-table-column prop="phone" min-width="120" label="Phone"></el-table-column>
                <el-table-column prop="gender" min-width="100" label="Gender">
                    <template scope="scope">
                        {{ ["Male", "Female"][scope.row.gender] }}
                    </template>
                </el-table-column>
                <el-table-column prop="role" min-width="100" label="Role">
                    <template scope="scope">
                        {{ scope.row.role && scope.row.role.name }}
                    </template>
                </el-table-column>
                <el-table-column prop="lastLoginTime" min-width="180" label="Last Login Time"></el-table-column>
                <el-table-column prop="lastLoginIp" min-width="180" label="Last Login IP"></el-table-column>
                <el-table-column prop="createdAt" min-width="180" label="Created At"></el-table-column>
                <el-table-column prop="updatedAt" min-width="180" label="Updated At"></el-table-column>
            </el-table>
        </div>
        <div>
            <el-pagination background @size-change="pagerHandleSizeChange" @current-change="pagerHandleCurrentChange" :current-page="table.searchForm.pageNo" :page-sizes="[10, 20, 50, 100]" :page-size="table.searchForm.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="table.total"> </el-pagination>
        </div>
        <!-- 新建或编辑弹窗 -->
        <el-dialog :title="editDialog.title" :close-on-click-modal="false" :close-on-press-escape="false" class="vehm" :visible.sync="editDialog.visible" @close="editDialogClose" @opened="editDialogOpen" width="1200px" center>
            <span>
                <el-form :inline="true" :rules="editDialog.rules" :model="editDialog.form" ref="editDialogForm" class="demo-form-inline jiaoyan" label-width="100px" size="mini">
                    <el-form-item label="Username:" prop="username" v-if="editDialog.operation == 'add'">
                        <el-input placeholder="" v-model="editDialog.form.username" :readonly="editDialog.operation == 'edit'"></el-input>
                    </el-form-item>
                    <el-form-item label="Password:" prop="password" v-if="editDialog.operation == 'add'">
                        <el-input placeholder="" v-model="editDialog.form.password"></el-input>
                    </el-form-item>
                    <el-form-item label="Email:" prop="email">
                        <el-input placeholder="" v-model="editDialog.form.email"></el-input>
                    </el-form-item>
                    <el-form-item label="Phone:" prop="phone">
                        <el-input placeholder="" v-model="editDialog.form.phone"></el-input>
                    </el-form-item>
                    <el-form-item label="Gender:" prop="gender">
                        <el-select placeholder="Please Select" v-model="editDialog.form.gender">
                            <el-option v-for="item in editDialog.genderList" :key="item.value" :label="item.label" :value="item.value"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="Role:" prop="role">
                        <el-select placeholder="Please Select" v-model="editDialog.form.roleId">
                            <el-option v-for="item in editDialog.roleList" :key="item.value" :label="item.label" :value="item.value"></el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
            </span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editDialog.visible = false">Cancle</el-button>
                <el-button type="primary" @click="editDialogSave">Confirm</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
    /* eslint-disable */
    import { userList, userAdd, userEdit, userDelete } from "@/api/request";
    import { Message } from "element-ui";
    export default {
        data() {
            return {
                table: {
                    height: "500px",
                    loading: false,
                    total: 0,
                    tableList: [],
                    searchForm: {
                        pageNo: 1,
                        pageSize: 10,
                    },
                },
                editDialog: {
                    title: "Create a User",
                    operation: "add",
                    form: {},
                    visible: false,
                    genderList: [
                        { value: 0, label: "男" },
                        { value: 1, label: "女" },
                        { value: 2, label: "保密" },
                    ],
                    roleList: [
                        { value: 1, label: "管理员1" },
                        { value: 2, label: "管理员2" },
                        { value: 3, label: "管理员3" },
                    ],
                    rules: {
                        username: [{ required: true, message: "请输入账号", trigger: "blur" }],
                        password: [{ required: true, message: "请输入账号", trigger: "blur" }],
                        phone: [{ required: false, message: "请输入手机", trigger: "blur" }],
                        email: [{ required: false, message: "请输入邮箱", trigger: "blur" }],
                        gender: [{ required: false, message: "请选择性别", trigger: ["blur", "change"] }],
                    },
                },
            };
        },
        created() {
            this.tableGetList(this.table.searchForm);
        },
        mounted() {},
        methods: {
            // 编辑窗口: 关闭对话框后(渲染结束)执行. 恢复窗口初始状态
            editDialogClose() {
                this.$nextTick(() => {
                    this.editDialog.form = {};
                    this.editDialog.title = "Create a User";
                    this.editDialog.operation = "add";
                    this.$refs.editDialogForm.clearValidate();
                });
            },
            // 编辑窗口: 打开. 通用设置
            editDialogOpen() {
                this.editDialog.visible = true;
            },
            // 编辑窗口: 编辑. 同时触发editDialogOpen()
            editDialogEdit(row) {
                this.editDialog.title = "Edit a User";
                this.editDialog.visible = true;
                this.editDialog.operation = "edit";
                this.editDialog.form = row;
            },
            editDialogDelete(row) {},
            editDialogSave() {
                this.$refs.editDialogForm.validate((valid) => {
                    if (valid) {
                        // 全屏遮罩. 不是表格遮罩
                        const loading = this.$loading({
                            lock: true,
                            text: "正在处理...",
                        });
                        if (this.editDialog.operation == "add") {
                            userAdd(this.editDialog.form)
                                .then(
                                    (res) => {
                                        Message({
                                            message: `${res.message}`,
                                            type: "success",
                                            duration: 800,
                                            onClose: () => {
                                                console.log("res", res);
                                                loading.close();
                                                this.editDialog.visible = false;
                                                this.tableGetList(this.table.searchForm);
                                            },
                                        });
                                    },
                                    (res) => {
                                        Message({
                                            message: `${res.message}`,
                                            type: "error",
                                            duration: 1500,
                                            onClose: () => {
                                                loading.close();
                                            },
                                        });
                                    }
                                )
                                .catch((error) => {
                                    console.log(error);
                                });
                        } else if (this.editDialog.operation == "edit") {
                            userEdit(this.editDialog.form)
                                .then(
                                    (res) => {
                                        Message({
                                            message: `${res.message}`,
                                            type: "success",
                                            duration: 800,
                                            onClose: () => {
                                                console.log("res", res);
                                                loading.close();
                                                this.editDialog.visible = false;
                                                this.tableGetList(this.table.searchForm);
                                            },
                                        });
                                    },
                                    (res) => {
                                        Message({
                                            message: `${res.message}`,
                                            type: "error",
                                            duration: 1500,
                                            onClose: () => {
                                                loading.close();
                                            },
                                        });
                                    }
                                )
                                .catch((error) => {
                                    this.$message({
                                        showClose: true,
                                        message: error instanceof Object && error.message !== undefined ? error.message : "Cannot parse response",
                                        type: "error",
                                        onClose() {},
                                    });
                                });
                        }
                    }
                });
            },
            editStatus(row, value) {
                const loading = this.$loading({
                    lock: true,
                    text: "正在处理...",
                });
                userEdit({ ...row, status: value }).then(
                    (res) => {
                        console.log("res", res);
                        loading.close();
                        this.tableGetList(this.table.searchForm);
                    },
                    (res) => {
                        Message({
                            message: res.message,
                            type: "error",
                            duration: 1500,
                            onClose: () => {
                                loading.close();
                            },
                        });
                    }
                );
            },
            editDelete(row) {
                this.$confirm("Are you sure you want to delete this item?", "Tips", {
                    confirmButtonText: "Confirm",
                    cancelButtonText: "Cancel",
                }).then(
                    () => {
                        const loading = this.$loading({
                            lock: true,
                            text: "正在处理...",
                        });
                        userDelete(row).then(
                            (res) => {
                                loading.close();
                                this.$message({
                                    message: res.message,
                                    type: "success",
                                });
                                this.tableGetList(this.table.searchForm);
                            },
                            (res) => {
                                this.$message({
                                    showClose: true,
                                    message: res.message,
                                    type: "error",
                                    onClose() {
                                        loading.close();
                                    },
                                });
                            }
                        );
                    },
                    () => {}
                );
            },
            // 表格: 列表查询方法
            tableGetList(searchForm) {
                userList(searchForm.createTime ? { ...searchForm, createTime: `${searchForm.createTime[0]},${searchForm.createTime[1]}` } : { ...searchForm })
                    .then(
                        (res) => {
                            this.table.total = res.data.total;
                            this.table.tableList = res.data.list;
                            console.log("this.table.tableList", this.table.tableList);
                        },
                        (res) => {
                            Message({
                                message: `${res.message}`,
                                type: "error",
                                duration: 1500,
                            });
                        }
                    )
                    .catch((error) => {
                        Message({
                            message: `${error}`,
                            type: "error",
                            duration: 1500,
                        });
                    });
            },
            // 表格: 点击重置搜索条件时
            tableResetForm() {
                this.table.searchForm = {
                    pageNo: 1,
                    pageSize: 10,
                };
                this.tableGetList(this.table.searchForm);
            },
            // 表格: 点击表格的行时
            tableHandleRowClick(row, column, event) {
                this.$refs.table.toggleRowSelection(row);
            },
            // 表格: 点击搜索按钮时
            tableSearch() {
                console.log(this.table.searchForm);
                this.tableGetList(this.table.searchForm);
            },
            // 分页器: 每页条数发生变化时
            pagerHandleSizeChange(val) {
                this.table.searchForm.pageSize = val;
                this.tableGetList(this.table.searchForm);
            },
            // 分页器: 翻页时
            pagerHandleCurrentChange(val) {
                this.table.searchForm.pageNo = val;
                this.tableGetList(this.table.searchForm);
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
            font-size: 14px;
            color: #333;
            padding-bottom: 10px;
            border-bottom: 2px solid rgb(88, 204, 2);
        }
    }
    .searchBox {
        display: flex;
        .searchBtnBox {
            padding-left: 10px;
            // margin-left: 10px;
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
            ::v-deep .el-form-item.long {
                width: 100%;
                margin-right: 0 !important;
            }
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
            font-size: 14px;
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
    }
</style>
