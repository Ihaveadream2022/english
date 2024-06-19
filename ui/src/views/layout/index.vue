<template>
    <el-container class="container">
        <el-header>
            <sectionHeader></sectionHeader>
        </el-header>
        <el-container>
            <el-aside>
                <sectionSidemenu v-on:menuLoadCallback="menuLoadCallback"></sectionSidemenu>
            </el-aside>
            <el-main>
                <router-view v-if="menuLoaded"></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>
<script>
    import sectionHeader from "./header";
    import sectionSidemenu from "./sidemenu";
    export default {
        components: {
            sectionHeader,
            sectionSidemenu,
        },
        data() {
            return {
                menuLoaded: false,
            };
        },
        computed: {},
        methods: {
            menuLoadCallback() {
                console.log("Side menu have loaded");
                this.menuLoaded = true;
            },
        },
    };
</script>
<style lang="scss" scoped>
    .container {
        .el-header {
            padding: 0 !important;
            height: 36px !important;
        }
        .el-aside {
            width: 90px !important;
            background: #001529;
            height: calc(100vh - 36px);
            overflow: hidden;
            &.collapse {
                width: 64px !important;
            }
        }
        .el-main {
            // margin-left: 10px;
            background: #fff;
            padding: 0 !important;
            padding-top: 10px !important;
            height: calc(100vh - 36px);
        }
    }
</style>
