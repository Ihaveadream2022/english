<template>
    <div>
        <div class="title">
            <h4>Grammar Management</h4>
        </div>
        <!-- Search Area -->
        <div class="searchBox" ref="searchBox">
            <el-form :inline="true" :model="table.searchForm" ref="searchForm" class="demo-form-inline" label-width="100px" size="mini">
                <div class="inpBox">
                    <el-form-item label="Keyword :" prop="keyword">
                        <el-input placeholder="keyword" v-model="table.searchForm.keyword" @keyup.enter.native="handleEnter"></el-input>
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
                    <el-form-item>
                        <el-button type="primary" @click="grammarGenerate">Generate JSON Files</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </div>
        <div class="table" :style="{ height: table.height }">
            <el-table :data="table.tableList" ref="table" v-loading="table.loading" @row-click="tableHandleRowClick" highlight-current-row bordersize="mini" height="100%" style="width: 100%" center border>
                <el-table-column prop="name" min-width="200" label="Name"> </el-table-column>
                <el-table-column prop="sort" min-width="150" label="Sort"></el-table-column>
                <el-table-column label="Operation" min-width="300">
                    <template slot-scope="scope">
                        <div class="panel">
                            <el-button type="primary" size="mini" @click="editDialogEdit(scope.row)">Edit</el-button>
                            <el-button type="danger" size="mini" @click="editDelete(scope.row)">Delete</el-button>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div>
            <el-pagination background @size-change="pagerHandleSizeChange" @current-change="pagerHandleCurrentChange" :current-page="table.searchForm.pageNo" :page-sizes="[10, 20, 50, 100]" :page-size="table.searchForm.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="table.total"> </el-pagination>
        </div>
        <!-- 新建或编辑单词弹窗 -->
        <el-dialog :title="editDialog.title" :close-on-click-modal="false" :close-on-press-escape="false" class="vehm" :visible.sync="editDialog.visible" @close="editDialogClose" @opened="editDialogOpen" width="1200px" top="0" center>
            <span>
                <el-form :inline="true" :rules="editDialog.rules" :model="editDialog.form" ref="editDialogForm" class="demo-form-inline jiaoyan" label-width="120px" size="mini">
                    <el-form-item label="语法名称:" prop="name">
                        <el-input placeholder="" v-model="editDialog.form.name"></el-input>
                    </el-form-item>
                    <el-form-item label="序号:" prop="sort">
                        <el-input placeholder="" v-model="editDialog.form.sort"></el-input>
                    </el-form-item>
                    <el-descriptions direction="vertical" :column="1" :labelStyle="{ background: '#409EFF', color: '#fff', 'text-align': 'center' }" border>
                        <el-descriptions-item label="">
                            <el-input placeholder="" type="textarea" v-model="editDialog.form.content" maxlength="10240"></el-input>
                        </el-descriptions-item>
                    </el-descriptions>
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
    import { grammarList, grammarAdd, grammarEdit, grammarDelete, grammarGenerate } from "@/api/request";
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
                    title: "Create Grammar",
                    operation: "add",
                    form: {},
                    visible: false,
                    rules: {
                        name: [{ required: true, message: "Please input grammar name", trigger: "blur" }],
                    },
                },
            };
        },
        created() {
            this.tableGetList(this.table.searchForm);
        },
        mounted() {
            let h = this.$refs.searchBox.offsetHeight;
            let pH = document.documentElement.clientHeight;
            this.table.height = pH - h - 120 + "px";
        },
        methods: {
            // 编辑窗口: 关闭对话框后(渲染结束)执行. 恢复窗口初始状态
            editDialogClose() {
                this.$nextTick(() => {
                    this.editDialog.form = {};
                    this.editDialog.title = "Create Grammar";
                    this.editDialog.operation = "add";
                    this.copiedText = "";
                });
            },
            // 编辑窗口: 打开. 通用设置
            editDialogOpen() {
                this.editDialog.visible = true;
            },
            // 编辑窗口: 编辑. 同时触发editDialogOpen()
            editDialogEdit(row) {
                this.editDialog.title = "Edit Grammar";
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
                            text: "Loading...",
                        });
                        if (this.editDialog.operation == "add") {
                            grammarAdd(this.editDialog.form)
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
                                    },
                                )
                                .catch((error) => {
                                    console.log(error);
                                });
                        } else if (this.editDialog.operation == "edit") {
                            grammarEdit(this.editDialog.form)
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
                                    },
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
                    text: "Loading...",
                });
                grammarEdit({ ...row, status: value }).then(
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
                    },
                );
            },
            editDelete(row) {
                this.$confirm("Are you sure you want to delete this?", "Tips", {
                    confirmButtonText: "Confirm",
                    cancelButtonText: "Cancel",
                }).then(
                    () => {
                        const loading = this.$loading({
                            lock: true,
                            text: "Loading...",
                        });
                        grammarDelete(row).then(
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
                            },
                        );
                    },
                    () => {},
                );
            },
            // 表格: 列表查询方法
            tableGetList(searchForm) {
                grammarList(searchForm.createTime ? { ...searchForm, createTime: `${searchForm.createTime[0]},${searchForm.createTime[1]}` } : { ...searchForm })
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
                        },
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
                this.table.searchForm = { ...this.table.searchForm, pageNo: 1, pageSize: 10 };
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
            grammarGenerate() {
                const loading = this.$loading({
                    lock: true,
                    text: "Loading...",
                });
                grammarGenerate().then(
                    (res) => {
                        loading.close();
                        this.$message({
                            message: "success",
                            type: "success",
                        });
                    },
                    (res) => {
                        loading.close();
                        this.$message({
                            message: "success",
                            type: "success",
                        });
                    },
                );
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
            font-size: 12px;
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
                color: rgb(204, 0, 0);
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
            .panel {
                margin: 2px 0 !important;
                .el-button--primary {
                    background: #409eff;
                }
                .el-button--danger {
                    color: #fff;
                    background: rgb(204, 0, 0);
                }
            }
        }
    }
    .el-dialog__wrapper {
        ::v-deep .el-dialog__body {
            padding: 15px 15px 15px;
        }
        ::v-deep .el-form-item {
            margin-bottom: 10px;
        }
        ::v-deep .el-form-item__label {
            font-size: 12px;
        }
        ::v-deep .el-divider--horizontal {
            margin: 20px 0;
        }
        ::v-deep .el-dialog__header {
            padding: 8px 20px !important;
            background-color: rgb(88, 204, 2);
            .el-dialog__title {
                line-height: 20px;
                font-size: 14px;
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
        ::v-deep .el-input__inner,
        ::v-deep .el-textarea__inner {
            padding: 4px 8px;
        }
        ::v-deep .el-textarea__inner {
            width: 100% !important;
            height: 600px;
            margin-bottom: 0 !important;
            border: 0;
            resize: none;
            padding: 0;
        }
        .el-divider__text {
            color: #fff;
            background: rgb(88, 204, 2);
        }
        .el-divider {
            background: rgb(88, 204, 2);
        }
        ::v-deep .el-collapse-item__wrap {
            padding: 30px 0;
            background: #eee;
        }
        ::v-deep .el-collapse-item__header {
            background-color: rgb(88, 204, 2);
            padding-left: 10px;
            height: 32px;
            line-height: 32px;
        }
    }
</style>
