<template>
    <div class="container">
        <div class="left">
            <div class="logo">
                <img src="../../assets/images/LoginLogo.png" />
            </div>
        </div>
        <div class="right">
            <div class="rcont">
                <div class="top">
                    <span v-on:click="requestLogout">欢迎登录</span>
                    <span class="bar"></span>
                </div>
                <div class="bottom">
                    <el-form ref="loginForm" :model="loginForm" :rules="loginRules">
                        <div style="margin-bottom: 60px">
                            <el-form-item prop="username">
                                <i class="fa fa-user-o"></i>
                                <el-input maxlength="20" v-model="loginForm.username" placeholder="请输入用户名" />
                            </el-form-item>
                            <el-form-item prop="password">
                                <i class="fa el-icon-lock"></i>
                                <el-input maxlength="20" v-model="loginForm.password" show-password placeholder="请输入密码"></el-input>
                            </el-form-item>
                            <el-form-item prop="code">
                                <i class="fa el-icon-set-up"></i>
                                <el-input maxlength="5" v-model="loginForm.code" placeholder="请输入验证码" class="code" />
                                <img :src="captchSrc" class="code-img" v-on:click="loadCaptch" />
                            </el-form-item>
                            <el-form-item class="login-btn">
                                <el-button type="primary" :loading="submitButtonLoading" v-on:click="requestLogin">登 录</el-button>
                            </el-form-item>
                        </div>
                    </el-form>
                </div>
            </div>
        </div>
        <div class="footer">{{ footer }}</div>
    </div>
</template>

<script>
    import { OAuthCaptcha } from "../../api/requestOAuth";
    import { Message } from "element-ui";
    export default {
        data() {
            return {
                loginForm: {
                    username: "",
                    password: "",
                    code: "",
                    uuid: "",
                },
                loginRules: {
                    username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
                    password: [{ required: true, message: "请输入密码", trigger: "blur" }],
                    code: [{ required: true, message: "请输入验证码", trigger: "blur" }],
                },
                captchSrc: "",
                submitButtonLoading: false,
                footer: "XXX公司",
            };
        },
        computed: {},
        created() {
            this.loadCaptch();
        },
        beforeMount() {
            this.bindDomEvents();
        },
        methods: {
            bindDomEvents() {
                document.onkeydown = () => {
                    let key = window.event.keyCode;
                    if (key === 13) {
                        this.requestLogin();
                    }
                };
            },
            loadCaptch() {
                OAuthCaptcha()
                    .then(
                        (res) => {
                            this.loginForm.uuid = res.data.uuid;
                            this.captchSrc = "data:image/gif;base64," + res.data.image;
                        },
                        () => {
                            Message({
                                message: "验证码加载失败",
                                type: "error",
                                duration: 1500,
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
            },
            requestLogin() {
                this.$refs.loginForm.validate((valid) => {
                    if (valid) {
                        this.submitButtonLoading = true;
                        this.$store
                            .dispatch("auth/Login", this.loginForm)
                            .then(
                                () => {
                                    this.submitButtonLoading = false;
                                    Message({
                                        message: "登录成功",
                                        type: "success",
                                        duration: 1500,
                                        onClose: () => {
                                            this.$router.push({
                                                path: "/",
                                            });
                                        },
                                    });
                                },
                                (res) => {
                                    this.submitButtonLoading = false;
                                    this.loadCaptch();
                                    Message({
                                        message: "登录失败 - " + res.message,
                                        type: "error",
                                        duration: 1500,
                                    });
                                }
                            )
                            .catch((error) => {
                                this.submitButtonLoading = false;
                                this.loadCaptch();
                                this.$message({
                                    showClose: true,
                                    message: error instanceof Object && error.message != undefined ? error.message : "操作失败",
                                    type: "error",
                                    onClose() {},
                                });
                            });
                    } else {
                        console.log("inputs invalid");
                    }
                });
            },
            requestLogout() {
                this.$store.dispatch("auth/Logout").then(
                    () => {
                        Message({
                            message: "登出成功",
                            type: "success",
                            duration: 1500,
                            onClose: () => {
                                this.$router.push({
                                    path: "/",
                                });
                            },
                        });
                    },
                    () => {
                        Message({
                            message: "登出失败",
                            type: "error",
                            duration: 1500,
                        });
                    }
                );
            },
        },
    };
</script>

<style scoped lang="scss">
    .container {
        width: 100%;
        height: 100vh;
        min-width: 990px;
        min-height: 500px;
        background: #fff;
        display: flex;
        .left {
            width: 75%;
            background: url("../../assets/images/loginBJ_new.jpg");
            background-repeat: no-repeat;
            background-size: cover;
            background-position: 50% 0;
            .logo {
                width: 100%;
                padding: 30px;
                box-sizing: border-box;
                padding-left: 5%;
                background-image: linear-gradient(to right, rgba(0, 0, 0, 0.5), rgba(255, 255, 255, 0));
                margin-top: 18%;
                img {
                    width: 65%;
                }
            }
        }
        .right {
            width: 25%;
            .rcont {
                width: 80%;
                margin: 0 auto;
                margin-top: 36%;
                .top {
                    text-align: center;
                    color: #333333;
                    font-size: 22px;
                    border-bottom: 1px solid #ddd;
                    padding-bottom: 10px;
                    position: relative;
                    margin-bottom: 50px;
                    .bar {
                        display: inline-block;
                        width: 60px;
                        height: 3px;
                        background-color: rgb(88, 204, 2);
                        position: absolute;
                        bottom: -3px;
                        left: 50%;
                        transform: translate(-50%, 0);
                    }
                }
                .bottom {
                    .el-form-item {
                        border: 1px solid rgba(0, 0, 0, 0.1);
                        border-radius: 5px;
                        color: #454545;
                        box-sizing: border-box;
                        height: 44px;
                        ::v-deep .el-form-item__content {
                            line-height: 42px;
                            .fa {
                                margin: 0 10px;
                                font-size: 18px;
                                color: rgb(88, 204, 2);
                                width: 16px;
                                vertical-align: middle;
                            }
                            .el-input {
                                border: 0;
                                position: initial;
                                width: calc(100% - 68px);

                                .el-input__inner {
                                    border: 0;
                                    position: initial;
                                    height: 42px;
                                    line-height: 42px;
                                    padding: 0;
                                    background: transparent;
                                    border-radius: 0px;
                                }
                                &.code {
                                    width: calc(100% - 156px) !important;
                                }
                            }
                        }
                        .code-img {
                            width: 120px;
                            height: 42px;
                            vertical-align: top;
                            cursor: pointer;
                        }
                        &.login-btn {
                            border: 0;
                            .el-button--primary {
                                width: 100%;
                                background-color: rgb(88, 204, 2);
                                border-color: rgb(88, 204, 2);
                                &:hover {
                                    background: #0b59bd;
                                    border-color: #0b59bd;
                                    color: #fff;
                                }
                            }
                            .find-pass {
                                color: rgb(88, 204, 2);
                            }
                        }
                    }
                }
            }
        }
    }
    .footer {
        position: fixed;
        bottom: 18px;
        width: 100%;
        font-size: 12px;
        color: #fff;
        text-align: center;
    }
    .sms-dialog {
        .el-dialog__header {
            padding: 15px;
            border-bottom: 1px solid #ccc;
        }
        .el-dialog--center .el-dialog__body {
            text-align: initial;
            padding: 30px 20px;
        }
        .el-input--mini .el-input__inner {
            height: 30px;
            line-height: 30px;
        }
        .el-button--mini {
            height: 30px;
        }
        .cd-btn {
            margin-left: 20px;
            .seconds-span {
                color: red;
            }
        }
    }
</style>
