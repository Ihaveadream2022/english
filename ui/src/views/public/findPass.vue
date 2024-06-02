<template>
    <div class="registerBox">
        <el-header height="46px">
            <span type="text" class="text">
                <router-link to="/login">登录</router-link>
            </span>
        </el-header>
        <div class="body-text">
            <span>忘记密码</span>
        </div>
        <div class="phoneBox">
            <el-form :rules="rules" :model="findForm" ref="findForm" class="" label-width="0">
                <el-form-item prop="username">
                    <el-input v-model="findForm.username" maxlength="30" placeholder="请输入用户名" autocomplete="new-password"></el-input>
                </el-form-item>
                <el-form-item prop="newPassword">
                    <el-tooltip class="item" effect="dark" content placement="top-end">
                        <div slot="content">长度为6-20个字符 <br />必须包含数字，大小写字母 <br />不允许有特殊字符</div>
                        <el-input type="password" v-model="findForm.newPassword" show-password maxlength="20" auto-complete="new-password" placeholder="请输入新密码"></el-input>
                    </el-tooltip>
                </el-form-item>
                <el-form-item prop="checkPass">
                    <el-tooltip class="item" effect="dark" content placement="top-end">
                        <div slot="content">长度为6-20个字符<br />必须包含数字，大小写字母 <br />不允许有特殊字符</div>
                        <el-input type="password" v-model="findForm.checkPass" placeholder="再次输入密码" show-password maxlength="20"></el-input>
                    </el-tooltip>
                </el-form-item>
                <el-form-item prop="mobile">
                    <el-input v-model="findForm.mobile" maxlength="11" placeholder="请输入手机号" onkeyup="value=value.replace(/[^\d]/g,'')" autocomplete="new-password"></el-input>
                </el-form-item>
                <el-form-item prop="smsCode" class="sms-code">
                    <el-input v-model="findForm.smsCode" maxlength="" placeholder="请输入验证码"></el-input>
                    <span @click="sendSmsCode" class="cd-btn">{{ smsBtnText }}</span>
                </el-form-item>
                <el-button type="primary" @click="handleSubmit" class="bigBtn" style="width: 100%; margin-top: 20px">确定</el-button>
            </el-form>
        </div>
    </div>
</template>
<script>
    import { OAuthSendSMSCodeFindPass, OAuthFindPassword } from "../../api/requestOAuth";
    import { Message } from "element-ui";
    export default {
        data() {
            return {
                findForm: {},
                rules: {
                    username: { required: true, message: "请输入用户名", trigger: "blur" },
                    mobile: [{ required: true, message: "请输入手机号", trigger: "blur" }],
                    smsCode: { required: true, message: "请输入验证码", trigger: "blur" },
                    newPassword: [
                        { required: true, message: "请输入新密码", trigger: "blur" },
                        { min: 6, message: "请输入至少6位的密码", trigger: "blur" },
                        { pattern: /^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])[0-9a-zA-Z]{6,20}$/, message: "密码设置不符合要求", trigger: "blur" },
                        {
                            trigger: "blur",
                            validator: (rule, value, callback) => {
                                if (value === "") {
                                    callback(new Error("请再次输入密码"));
                                } else if (this.findForm.checkPass && value !== this.findForm.checkPass) {
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
                                } else if (this.findForm.newPassword && value !== this.findForm.newPassword) {
                                    callback(new Error("两次输入密码不一致!"));
                                } else {
                                    callback();
                                }
                            },
                        },
                    ],
                },
                sms: {
                    title: "发送短信",
                    countdown: 60,
                    intervalHandler: null,
                },
            };
        },
        computed: {
            smsBtnText() {
                if (this.sms.intervalHandler === null) {
                    return "发送验证码";
                } else {
                    return `${this.sms.countdown} s后可重试`;
                }
            },
        },
        methods: {
            handleSubmit() {
                this.$refs["findForm"].validate((valid) => {
                    if (valid) {
                        const loading = this.$loading({
                            lock: true,
                            text: "正在加载...",
                        });
                        OAuthFindPassword(this.findForm).then(
                            (res) => {
                                Message({
                                    message: `${res.message}`,
                                    type: "success",
                                    duration: 1500,
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
            sendSmsCode() {
                if (!this.findForm.mobile) {
                    Message({
                        message: "请填写手机号码",
                        type: "error",
                        duration: 1500,
                    });
                } else {
                    if (this.sms.intervalHandler === null) {
                        OAuthSendSMSCodeFindPass({ mobile: this.findForm.mobile }).then(
                            (res) => {
                                Message({
                                    message: res.message,
                                    type: "success",
                                    duration: 1500,
                                });
                                this.sms.intervalHandler = setInterval(() => {
                                    this.sms.countdown--;
                                    if (this.sms.countdown === 0) {
                                        clearInterval(this.sms.intervalHandler);
                                        this.sms.countdown = 60;
                                        this.sms.intervalHandler = null;
                                    }
                                }, 1000);
                            },
                            (res) => {
                                Message({
                                    message: res.message,
                                    type: "error",
                                    duration: 1500,
                                });
                            }
                        );
                    }
                }
            },
        },
    };
</script>
<style lang="scss" scoped>
    body {
        width: 100%;
        height: 100%;
        position: relative;
    }
    .registerBox {
        width: 100%;
        height: 100%;
        background-color: #fff;
        position: absolute;
        left: 0;
        top: 0;
        .el-header {
            background-color: rgb(88, 204, 2);
            line-height: 46px;
            .text {
                float: right;
                color: #fff;
                font-size: 13px;
                cursor: pointer;
            }
        }
        .body-text {
            font-size: 20px;
            text-align: center;
            padding: 40px 0 50px 0;
            color: rgb(88, 204, 2);
        }
        .phoneBox {
            width: 20%;
            height: 200px;
            margin: 0 auto;
            .sms-code {
                position: relative;
                .cd-btn {
                    position: absolute;
                    right: 10px;
                    cursor: pointer;
                }
            }
        }
    }
</style>
