<template>
    <div>
        <div class="searchBox" ref="searchBox">
            <div class="searchBtnBox">
                <el-button type="primary" @click="GitPush">Git Push To Remote</el-button>
            </div>
        </div>
        <div style="width: 500px; margin: 10px">
            <el-descriptions direction="vertical" :column="1" :labelStyle="{ background: '#409EFF', color: '#fff' }" border>
                <el-descriptions-item label="单词总数1">
                    {{ statics.total }}
                </el-descriptions-item>
                <el-descriptions-item label="总页数">
                    {{ statics.totalPage }}
                </el-descriptions-item>
                <el-descriptions-item label="今天页数">
                    {{ statics.nowPage }}
                </el-descriptions-item>
                <el-descriptions-item label="今天要背">
                    {{ statics.todo }}
                </el-descriptions-item>
            </el-descriptions>
        </div>
    </div>
</template>
<script>
    /* eslint-disable */
    import { GitPush, IndexTodo } from "@/api/request";
    export default {
        data() {
            return {
                statics: {},
            };
        },
        created() {
            this.indexTodo();
        },
        mounted() {},
        methods: {
            GitPush() {
                const loading = this.$loading({
                    lock: true,
                    text: "Loading...",
                });
                GitPush().then(
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
            indexTodo() {
                IndexTodo()
                    .then(
                        (res) => {
                            this.statics = res.data;
                            console.log("res", res);
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
</style>
