<template>
    <div class="container">
        <el-scrollbar wrap-class="scrollbar-wrapper">
            <el-menu :default-active="$route.path" :unique-opened="true" background-color="#001529" text-color="#fff" :collapse-transition="false" active-text-color="rgb(88, 204, 2)" :router="true">
                <template v-for="item in menuList">
                    <template v-if="!item.childMenu">
                        <el-menu-item :index="item.accessUrl" :route="item.accessUrl" :key="item.id">
                            <span slot="title">{{ item.name }}</span>
                        </el-menu-item>
                    </template>
                    <template v-else>
                        <el-submenu :index="item.id + ''" :key="item.id">
                            <template slot="title">
                                <span slot="title">{{ item.name }}</span>
                            </template>
                            <template v-for="i in item.childMenu">
                                <el-menu-item :index="i.accessUrl" :route="i.accessUrl" :key="i.id">{{ i.name }}</el-menu-item>
                            </template>
                        </el-submenu>
                    </template>
                </template>
            </el-menu>
        </el-scrollbar>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                menuList: [
                    {
                        code: "000M0j",
                        accessUrl: "/auth",
                        picType: 1,
                        name: "Auth",
                        id: 291,
                        type: 1,
                        parentId: 0,
                        childMenu: [
                            {
                                code: "000M0k",
                                accessUrl: "/authUser",
                                picType: 1,
                                name: "User",
                                id: 292,
                                type: 1,
                                parentId: 291,
                                buttonDetails: [
                                    {
                                        buttonDetails: [],
                                        code: "00d00B0100",
                                        name: "添加",
                                        id: 31,
                                        type: 2,
                                        parentId: 292,
                                    },
                                    {
                                        buttonDetails: [],
                                        code: "00d00B0200",
                                        name: "修改",
                                        id: 32,
                                        type: 2,
                                        parentId: 292,
                                    },
                                ],
                            },
                            {
                                code: "000M1k",
                                accessUrl: "/authRole",
                                picType: 1,
                                name: "Role",
                                id: 301,
                                type: 1,
                                parentId: 291,
                                buttonDetails: [
                                    {
                                        buttonDetails: [],
                                        code: "00d00B0100",
                                        name: "添加",
                                        id: 51,
                                        type: 2,
                                        parentId: 301,
                                    },
                                    {
                                        buttonDetails: [],
                                        code: "00d00B0200",
                                        name: "修改",
                                        id: 52,
                                        type: 2,
                                        parentId: 301,
                                    },
                                ],
                            },
                        ],
                    },
                    {
                        code: "000M0j",
                        accessUrl: "/item",
                        picType: 1,
                        name: "Item",
                        id: 292,
                        type: 1,
                        parentId: 0,
                        childMenu: [
                            {
                                code: "000M0k",
                                accessUrl: "/item",
                                picType: 1,
                                name: "Item",
                                id: 292,
                                type: 1,
                                parentId: 291,
                                buttonDetails: [
                                    {
                                        buttonDetails: [],
                                        code: "00d00B0100",
                                        name: "添加",
                                        id: 31,
                                        type: 2,
                                        parentId: 292,
                                    },
                                    {
                                        buttonDetails: [],
                                        code: "00d00B0200",
                                        name: "修改",
                                        id: 32,
                                        type: 2,
                                        parentId: 292,
                                    },
                                ],
                            },
                        ],
                    },
                    {
                        code: "000M2j",
                        accessUrl: "/grammar",
                        picType: 1,
                        name: "Grammar",
                        id: 281,
                        type: 1,
                        parentId: 0,
                        childMenu: [
                            {
                                code: "000M2k",
                                accessUrl: "/grammar",
                                picType: 1,
                                name: "Grammar",
                                id: 282,
                                type: 1,
                                parentId: 281,
                                buttonDetails: [
                                    {
                                        buttonDetails: [],
                                        code: "00d00B0120",
                                        name: "添加",
                                        id: 51,
                                        type: 2,
                                        parentId: 282,
                                    },
                                    {
                                        buttonDetails: [],
                                        code: "00d00B0220",
                                        name: "修改",
                                        id: 52,
                                        type: 2,
                                        parentId: 282,
                                    },
                                ],
                            },
                        ],
                    },
                ],
            };
        },
        created() {
            this.$emit("menuLoadCallback");
        },
        computed: {},
        methods: {},
    };
</script>
<style lang="scss" scoped>
    .container {
        width: 100%;
        background: #001529;
        height: 100%;
        overflow: hidden;
        .el-scrollbar {
            overflow: hidden;
            position: relative;
            height: 100%;
            ::v-deep .scrollbar-wrapper {
                overflow-x: hidden !important;
                margin-bottom: -18px !important;
                margin-right: -18px !important;
                .el-scrollbar__view {
                    margin-bottom: 40px;
                }
            }
            .el-menu {
                border: 0 !important;

                // 有子菜单
                .el-submenu {
                    border-bottom: 1px solid #aaa;
                    ::v-deep .el-submenu__title {
                        height: 46px !important;
                        line-height: 46px !important;
                    }
                    ::v-deep .el-icon-arrow-down {
                        color: #fff !important;
                        right: 25px !important;
                    }
                    ::v-deep .el-submenu__title {
                        text-align: center;
                        padding: 0 15px !important;
                        &:hover {
                            background-color: rgba(255, 255, 255, 0.1) !important;
                        }
                        .el-submenu__icon-arrow {
                            display: none;
                        }
                    }
                    .el-menu-item {
                        text-align: center;
                        height: 46px !important;
                        line-height: 46px !important;
                        padding: 0 15px !important;
                        min-width: 100%;
                        &:hover {
                            background-color: rgba(255, 255, 255, 0.1) !important;
                        }
                    }
                }
                // 无子菜单
                .el-menu-item {
                    padding: 0 20px !important;
                    &:hover {
                        background-color: rgba(255, 255, 255, 0.1) !important;
                    }
                }
            }
            .el-menu--collapse {
                .el-submenu {
                    ::v-deep .el-submenu__title {
                        line-height: 46px !important;
                        span {
                            visibility: visible;
                            width: 14px;
                            height: auto;
                            overflow: hidden;
                        }
                    }
                }
                .el-menu-item {
                    ::v-deep .el-tooltip {
                        text-align: center !important;
                    }
                    .title-collapse {
                        height: auto;
                        width: 14px;
                        visibility: visible;
                    }
                }
            }
        }
        .fold-icon {
            position: absolute;
            bottom: 0;
            width: 100%;
            text-align: center;
            color: #fff;
            font-size: 22px;
            border-top: 1px solid #ccc;
            padding: 8px 0;
            z-index: 999;
            background: rgb(0, 17, 33);
            cursor: pointer;
        }
    }
</style>
