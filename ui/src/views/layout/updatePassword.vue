<template>
    <el-dialog title="修改密码" :visible.sync="visibleDialog" center class="updateDialog" :close-on-click-modal="false" :close-on-press-escape="false">
        <el-form :model="passwordForm" :rules="rules" ref="passwordForm" label-width="90px" size="small">
            <el-form-item label="旧密码" prop="passwordOld">
                <el-input type="password" v-model="passwordForm.passwordOld" autocomplete="off" show-password></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="passwordNew">
                <el-tooltip class="item" effect="dark" content placement="top-end">
                    <div slot="content">长度为6-20个字符<br />必须包含数字，大小写字母 <br />不允许有特殊字符</div>
                    <el-input type="password" v-model="passwordForm.passwordNew" autocomplete="off" show-password maxlength="20"></el-input>
                </el-tooltip>
            </el-form-item>
            <el-form-item label="确认密码" prop="checkPass">
                <el-tooltip class="item" effect="dark" content placement="top-end">
                    <div slot="content">长度为6-20个字符<br />必须包含数字，大小写字母 <br />不允许有特殊字符</div>
                    <el-input type="password" v-model="passwordForm.checkPass" autocomplete="off" show-password maxlength="20"></el-input>
                </el-tooltip>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm">提 交</el-button>
                <el-button @click="resetForm">重 置</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
</template>
<script>
    import { OAuthUpdatePassword } from "../../api/requestOAuth";
    import { Message } from "element-ui";
    export default {
        name: "updatePassword",
        props: {
            visibleDialog: {
                type: Boolean,
                required: true,
            },
        },
        data() {
            return {
                passwordForm: {
                    passwordOld: "",
                    passwordNew: "",
                    checkPass: "",
                },
                rules: {
                    passwordOld: [{ required: true, message: "请输入旧密码", trigger: "blur" }],
                    passwordNew: [
                        { required: true, message: "请输入新密码", trigger: "blur" },
                        { min: 6, message: "请输入至少6位的密码", trigger: "blur" },
                        { pattern: /^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])[0-9a-zA-Z]{6,20}$/, message: "密码设置不符合要求", trigger: "blur" },
                        {
                            trigger: "blur",
                            validator: (rule, value, callback) => {
                                if (value === "") {
                                    callback(new Error("请再次输入密码"));
                                } else if (this.passwordForm.checkPass && value !== this.passwordForm.checkPass) {
                                    callback(new Error("两次输入密码不一致!"));
                                } else {
                                    callback();
                                }
                            },
                        },
                    ],
                    checkPass: [
                        { min: 6, message: "请输入至少6位的密码", trigger: "blur" },
                        { pattern: /^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])[0-9a-zA-Z]{6,20}$/, message: "密码设置不符合要求", trigger: "blur" },
                        {
                            trigger: "blur",
                            validator: (rule, value, callback) => {
                                if (value === "") {
                                    callback(new Error("请再次输入密码"));
                                } else if (this.passwordForm.passwordNew && value !== this.passwordForm.passwordNew) {
                                    callback(new Error("两次输入密码不一致!"));
                                } else {
                                    callback();
                                }
                            },
                        },
                    ],
                },
            };
        },
        methods: {
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
                                        this.$store.dispatch("auth/OAuthTokenClear").then(
                                            () => {
                                                this.$router.push({ path: "/login" });
                                            },
                                            (res) => {
                                                console.log("Fail OAuthTokenClear: ", res);
                                            }
                                        );
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
                            }
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
        .updateDialog {
            ::v-deep .el-dialog {
                border-radius: 4px 4px 0 0;
                width: 420px !important;
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
</style>
