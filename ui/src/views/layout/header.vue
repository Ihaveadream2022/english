<template>
    <div>
        <div class="container">
            <div class="logo">
                <span class="logo-text">English</span>
            </div>
            <div class="panel">
                <el-dropdown class="item user">
                    <span class="el-dropdown-link">
                        <span class="username">Admin</span>
                        <i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item icon="el-icon-s-home" @click.native="goHome()">Home</el-dropdown-item>
                        <el-dropdown-item icon="el-icon-edit-outline" @click.native="visibleDialog = true">Update Password</el-dropdown-item>
                        <el-dropdown-item icon="el-icon-unlock" @click.native="logout()">Logout</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>
        <div class="password">
            <el-dialog title="Update Password" :visible.sync="visibleDialog" center class="update-password" :close-on-click-modal="false" :close-on-press-escape="false">
                <el-form :model="passwordForm" :rules="rules" ref="passwordForm" label-width="140px" size="small">
                    <el-form-item label="Original Password" prop="oldPassword">
                        <el-input type="password" v-model="passwordForm.oldPassword" autocomplete="off" show-password></el-input>
                    </el-form-item>
                    <el-form-item label="New Password" prop="newPassword">
                        <el-input type="password" v-model="passwordForm.newPassword" autocomplete="off" show-password maxlength="20"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm">提 交</el-button>
                        <el-button @click="resetForm">重 置</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
        </div>
    </div>
</template>
<script>
    import { OAuthUpdatePassword } from "../../api/requestOAuth";
    import { Message } from "element-ui";
    export default {
        data() {
            return {
                visibleDialog: false,
                passwordForm: {
                    oldPassword: "",
                    newPassword: "",
                },
                rules: {
                    oldPassword: [{ required: true, message: "请输入旧密码", trigger: "blur" }],
                    newPassword: [
                        { required: true, message: "请输入新密码", trigger: "blur" },
                        { min: 6, message: "请输入至少6位的密码", trigger: "blur" },
                        //{ pattern: /^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])[0-9a-zA-Z]{6,20}$/, message: "密码设置不符合要求", trigger: "blur" },
                    ],
                },
            };
        },
        created() {},
        computed: {},
        methods: {
            logout() {
                const loading = this.$loading({
                    lock: true,
                    text: "正在退出...",
                });
                this.$store
                    .dispatch("auth/Logout")
                    .then(
                        () => {
                            this.$router.push({ path: "/login" });
                            loading.close();
                        },
                        () => {
                            this.$message({
                                showClose: true,
                                message: "操作失败",
                                type: "error",
                                onClose() {
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
            },
            goHome() {
                if (this.$route.path !== "/") {
                    this.$router.push({ path: "/" });
                }
            },
            submitForm() {
                this.$refs["passwordForm"].validate((valid) => {
                    if (valid) {
                        const loading = this.$loading({
                            lock: true,
                            text: "正在加载...",
                        });
                        OAuthUpdatePassword(this.$store.getters["auth/ACCESS_TOKEN"], this.passwordForm).then(
                            (res) => {
                                Message({
                                    message: `${res.message}`,
                                    type: "success",
                                    duration: 1500,
                                    onClose: () => {
                                        this.logout();
                                    },
                                });
                                loading.close();
                            },
                            (res) => {
                                Message({
                                    message: `${res.message}`,
                                    type: "error",
                                    duration: 1500,
                                });
                                loading.close();
                            },
                        );
                    }
                });
            },
            resetForm() {
                this.$refs["passwordForm"].resetFields();
            },
        },
    };
</script>
<style lang="scss" scoped>
    .container {
        background-color: rgb(88, 204, 2);
        padding: 0 !important;
        height: 36px !important;
        display: flex;
        justify-content: space-between;
        align-items: center;
        .logo {
            margin-left: 20px;
            .logo-text {
                font-family: Source Han Sans CN;
                font-size: 18px;
                font-weight: 900;
                color: #fff;
            }
        }
        .panel {
            padding-right: 20px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            .item {
                & + .item {
                    margin-left: 20px;
                }
                color: #fff;
                font-size: 12px;
                display: flex;
                align-items: center;
                justify-content: space-between;
                .el-icon-message-solid,
                .el-i-down {
                    font-size: 14px;
                    margin-right: 4px;
                    position: relative;
                    top: 1px;
                }
                .el-i-down {
                    transform: rotate(90deg);
                }
                .el-badge {
                    ::v-deep .el-badge__content.is-fixed {
                        right: 0 !important;
                    }
                }
            }
            .el-dropdown.user {
                .el-dropdown-link {
                    color: #fff;
                    font-size: 12px;
                    display: flex;
                    align-items: center;
                    justify-content: space-between;
                    .username {
                        margin-left: 5px;
                    }
                    .avatar {
                        width: 30px;
                        height: 30px;
                    }
                }
            }
        }
        .password {
            .update-password {
                ::v-deep .el-dialog {
                    border-radius: 4px 4px 0 0;
                    width: 450px !important;
                    .el-dialog__header {
                        padding: 8px 20px;
                        background-color: rgb(88, 204, 2);
                        border-radius: 4px 4px 0 0;
                        .el-dialog__title {
                            color: #fff;
                            line-height: 26px;
                        }
                        .el-dialog__headerbtn {
                            top: 12px;
                            right: 12px;
                            .el-dialog__close {
                                color: #fff;
                            }
                        }
                    }
                    .el-dialog__body {
                        .el-form-item {
                            margin-bottom: 24px;
                            &:last-child {
                                margin: 0;
                            }
                            .el-input__inner {
                                width: 250px;
                                padding: 0 10px;
                            }
                        }
                        .el-button + .el-button {
                            margin-left: 20px;
                        }
                    }
                }
            }
        }
    }
    .el-dropdown-menu {
        top: 36px !important;
        margin: 0 !important;
        padding: 0 !important;
        border-radius: 0;
        border-bottom-left-radius: 4px;
        border-bottom-right-radius: 4px;
        .el-dropdown-menu__item {
            &:first-child {
                border: 0;
            }
            line-height: inherit;
            padding: 10px 20px;
            border-top: 1px solid #eee;
        }
    }
</style>
