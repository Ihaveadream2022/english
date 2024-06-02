<template>
    <div></div>
</template>
<script>
    /* eslint-disable */
    export default {
        data() {
            return {};
        },
        created() {},
        mounted() {},
        methods: {
            onKeyVehicleNumber() {
                this.addDisable = false;
                const patt = /[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领]{1}[A-Z]{1}[A-Z0-9]{5,6}/g;
                if (patt.test(this.addFormData.carNumber) === true) {
                    const loading3 = this.$loading({
                        lock: true,
                        text: "正在获取人车信息...",
                    });
                    detailByPlateNumberDriverVehicle({ outstockPlateNumber: this.addFormData.carNumber })
                        .then((res) => {
                            loading3.close();
                            if (res instanceof Object && res.marker != undefined && res.marker == "黑名单") {
                                this.addDisable = true;
                                DonMessage.error("该车牌号已被加入黑名单");
                            } else {
                                if (res instanceof Object) {
                                    this.addFormData.driverName = res.driverName;
                                    this.addFormData.telephone = res.mobile;
                                    this.addFormData.idNumber = res.identityNo;
                                }
                            }
                        })
                        .catch((error) => {
                            loading3.close();
                            DonMessage.error(error.message);
                        });
                }
            },
        },
    };
</script>
<style lang="scss" scoped></style>
