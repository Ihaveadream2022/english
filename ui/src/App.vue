<template>
    <div id="app">
        <router-view></router-view>
    </div>
</template>

<script>
    export default {
        name: "app",
        provide() {
            return {
                appReload: this.appReload,
            };
        },
        data() {
            return {
                appShow: true,
            };
        },
        methods: {
            appReload() {
                this.appShow = false;
                this.$nextTick(() => (this.appShow = true));
            },
        },
    };
</script>
<style scoped>
    #app {
        background-color: #e5e5e5;
    }
</style>
