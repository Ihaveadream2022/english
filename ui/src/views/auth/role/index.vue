<template>
    <div>
        <div class="title">
            <h4>Role Management</h4>
        </div>
        <!-- 搜索框 -->
        <div class="searchBox" ref="searchBox">
            <el-form :inline="true" :model="table.searchForm" ref="searchForm" class="demo-form-inline" label-width="100px" size="mini">
                <div class="inpBox">
                    <el-form-item label="Name :" prop="name">
                        <el-input placeholder="" v-model="table.searchForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="Key :" prop="key">
                        <el-input placeholder="" v-model="table.searchForm.key"></el-input>
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
                        <el-button type="primary" @click="editDialog.visible = true">Create a Role</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </div>
        <div class="table" :style="{ height: table.height }">
            <el-table :data="table.tableList" ref="table" v-loading="table.loading" @row-click="tableHandleRowClick" highlight-current-row bordersize="mini" height="100%" style="width: 100%" center border>
                <el-table-column fixed type="index" width="55" label="No."></el-table-column>
                <el-table-column label="Operation" width="250">
                    <template slot-scope="scope">
                        <div>
                            <el-button type="text" size="mini" v-show="scope.row.status == 0" @click="editStatus(scope.row, 1)">Block</el-button>
                            <el-button type="text" size="mini" v-show="scope.row.status == 1" @click="editStatus(scope.row, 0)">Resume</el-button>
                            <el-button type="text" size="mini" @click="editDialogEdit(scope.row)">Edit</el-button>
                            <el-button type="text" size="mini" @click="editDialogDelete(scope.row)">Delete</el-button>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="name" min-width="180" label="Name"></el-table-column>
                <el-table-column prop="key" min-width="180" label="Key"></el-table-column>
                <el-table-column prop="status" min-width="100" label="Status">
                    <template scope="scope">
                        {{ ["Active", "Blocked"][scope.row.status] }}
                    </template>
                </el-table-column>
                <el-table-column prop="createdAt" min-width="180" label="Create Time"></el-table-column>
                <el-table-column prop="updatedAt" min-width="180" label="Update Time"></el-table-column>
            </el-table>
        </div>
        <div>
            <el-pagination background @size-change="pagerHandleSizeChange" @current-change="pagerHandleCurrentChange" :current-page="table.searchForm.pageNo" :page-sizes="[10, 20, 50, 100]" :page-size="table.searchForm.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="table.total"> </el-pagination>
        </div>
        <!-- 新建或编辑弹窗 -->
        <el-dialog :title="editDialog.title" :close-on-click-modal="false" :close-on-press-escape="false" class="vehm" :visible.sync="editDialog.visible" @close="editDialogClose" @opened="editDialogOpen" width="1200px" center>
            <span>
                <el-form :inline="true" :rules="editDialog.rules" :model="editDialog.form" ref="editDialogForm" class="demo-form-inline jiaoyan" label-width="100px" size="mini">
                    <el-form-item label="Name:" prop="name">
                        <el-input placeholder="" v-model="editDialog.form.name"></el-input>
                    </el-form-item>
                    <el-form-item label="Key:" prop="key" v-if="editDialog.operation == 'add'">
                        <el-input placeholder="" v-model="editDialog.form.key"></el-input>
                    </el-form-item>
                </el-form>
            </span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editDialog.visible = false">Cancel</el-button>
                <el-button type="primary" @click="editDialogSave">Confirm</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
    /* eslint-disable */
    import { roleList, roleAdd, roleEdit, roleDelete, roleBatchDelete } from "@/api/request";
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
                    title: "Create a Role",
                    operation: "add",
                    form: {},
                    visible: false,
                    rules: {
                        name: [{ required: true, message: "请输入名称", trigger: "blur" }],
                        key: [{ required: true, message: "请输入标识", trigger: "blur" }],
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
                    this.editDialog.title = "Create a Role";
                    this.editDialog.operation = "add";
                    this.editDialog.form = {};
                    this.$refs.editDialogForm.clearValidate();
                });
            },
            // 编辑窗口: 打开. 通用设置
            editDialogOpen() {},
            // 编辑窗口: 编辑. 同时触发editDialogOpen()
            editDialogEdit(row) {
                this.editDialog.title = "Edit a Role";
                this.editDialog.operation = "edit";
                this.editDialog.form = row;
                this.editDialog.visible = true;
            },
            editStatus(row, value) {
                const loading = this.$loading({
                    lock: true,
                    text: "Loading...",
                });
                roleEdit({ ...row, status: value }).then(
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
            editDialogDelete(row) {
                this.$confirm("是否执行删除操作?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                })
                    .then(() => {
                        this.table.loading = true;
                        roleDelete({ id: row.id }).then(
                            (res) => {
                                this.table.loading = false;
                                this.tableGetList(this.table.searchForm);
                                console.log("editDialogDelete succ:", res);
                            },
                            (res) => {
                                this.table.loading = false;
                                console.log("editDialogDelete fail:", res);
                            }
                        );
                    })
                    .catch(() => {});
            },
            editDialogSave() {
                this.$refs.editDialogForm.validate((valid) => {
                    if (valid) {
                        const loading = this.$loading({
                            lock: true,
                            text: "正在处理...",
                        });
                        if (this.editDialog.operation == "add") {
                            roleAdd(this.editDialog.form)
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
                            console.log(this.editDialog.form);
                            roleEdit(this.editDialog.form)
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
                        }
                    }
                });
            },
            // 表格: 列表查询方法
            tableGetList(searchForm) {
                roleList(searchForm.createTime ? { ...searchForm, createTime: `${searchForm.createTime[0]},${searchForm.createTime[1]}` } : { ...searchForm })
                    .then((res) => {
                        this.table.total = res.data.total;
                        this.table.tableList = res.data.list;
                        console.log("res", res);
                    })
                    .catch((error) => {
                        console.log(error);
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
        margin-left: 10px;
        justify-content: space-between;
        padding-right: 10px 10px;
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
