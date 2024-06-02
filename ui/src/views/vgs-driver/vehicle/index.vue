<template>
    <div>
        <div class="searchBox" ref="searchBox">
            <el-form :inline="true" :model="r.searchFrom" ref="searchFrom" class="demo-form-inline" label-width="110px" size="mini">
                <div class="inpBox">
                    <el-form-item label="车牌号:" prop="vhcNo">
                        <el-input placeholder="" v-model="r.searchFrom.vhcNo"></el-input>
                    </el-form-item>
                </div>
                <div class="searchBtnBox">
                    <el-form-item btnCode="00BP001B000" v-if="r.btnFlg[0]">
                        <el-button type="primary" @click="RSearchFn">查询</el-button>
                    </el-form-item>
                    <el-form-item btnCode="00BP001B006" v-if="r.btnFlg[6]">
                        <el-button type="primary" @click="RTHimportFn">车辆导入</el-button>
                    </el-form-item>
                    <el-form-item btnCode="00BP001B006" v-if="r.btnFlg[6]">
                        <el-button type="primary" @click="RModelDown">模板下载</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </div>
        <!--  表格 -->
        <div class="table" :style="{ height: r.h }">
            <el-table :data="r.tableList" ref="table" v-loading="r.loading" bordersize="mini" height="100%" style="width: 100%" center border>
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column fixed type="index" width="55" label="序号"></el-table-column>
                <el-table-column label="操作" width="120">
                    <template slot-scope="scope">
                        <div>
                            <el-button type="text" size="mini" btnCode="00BP001B004" v-if="r.btnFlg[4]" @click="CUDModifyVeicle(scope.row)">修改</el-button>
                            <el-button type="text" size="mini" btnCode="00BP001B005" v-if="r.btnFlg[5]" v-show="scope.row.vhcPhoto1 != '' || scope.row.vhcPhoto2 != ''" @click="RLookImg(scope.row)">查看照片</el-button>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="vhcNo" width="100" label="车牌号"></el-table-column>
                <el-table-column property="vhcNoColour" label="车牌颜色">
                    <template scope="scope">
                        <span v-if="scope.row.vhcNoColour !== null"> {{ ["其他", "******", "蓝底白字", "黄底黑字", "白底黑字", "黑底白字", "绿地黑字", "渐变绿色", "黄绿双拼"][scope.row.vhcNoColour + 1] }}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="vhcTypeName" width="150" :show-overflow-tooltip="true" label="车辆类型"></el-table-column>
                <el-table-column prop="tenantName" width="200" label="所属车队" v-if="r.searchFrom.isZhb == '0'"></el-table-column>
                <el-table-column prop="vhcT2linkName" width="150" :show-overflow-tooltip="true" label="机型/吨位"></el-table-column>
                <el-table-column prop="purposeName" width="150" :show-overflow-tooltip="true" label="车辆用途"></el-table-column>
                <el-table-column property="vhcClassify" label="车辆所属">
                    <template scope="scope">
                        {{ ["市场车辆", "自家车辆"][scope.row.vhcClassify - 1] }}
                    </template>
                </el-table-column>
                <el-table-column property="vhcCompanyName" width="240" :show-overflow-tooltip="true" label="所属单位/个人"> </el-table-column>
                <el-table-column property="energyType" label="能源类型" width="80">
                    <template scope="scope">
                        <span v-if="scope.row.energyType !== null">{{ ["其他", "******", "汽油", "柴油", "气", "电"][scope.row.energyType + 1] }}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="weight" width="100" :show-overflow-tooltip="true" label="载重(吨)"></el-table-column>
                <el-table-column prop="standardsName" width="80" :show-overflow-tooltip="true" label="排放标准"></el-table-column>
                <el-table-column prop="boardList" width="200" :show-overflow-tooltip="true" label="随车清单"></el-table-column>
                <el-table-column prop="drivingLicense" width="170" :show-overflow-tooltip="true" label="行驶证号"></el-table-column>
                <el-table-column prop="vhcVinNo" width="170" :show-overflow-tooltip="true" label="车架号"></el-table-column>
                <el-table-column prop="vhcEngineNo" width="100" :show-overflow-tooltip="true" label="发动机号"></el-table-column>
                <el-table-column property="vhcFlow" label="车辆流向">
                    <template scope="scope">
                        {{ ["集港", "外发"][scope.row.vhcFlow - 1] }}
                    </template>
                </el-table-column>
                <el-table-column prop="registerDate" width="160" :formatter="UtilsDateFormat1" label="注册日期"></el-table-column>
                <el-table-column prop="createTime" width="160" :formatter="UtilsDateFormat" label="创建时间"></el-table-column>
            </el-table>
        </div>
        <!-- 分页码 -->
        <div>
            <el-pagination background @size-change="RHandleSizeChange" @current-change="RHandleCurrentChange" :current-page="r.searchFrom.pageNo" :page-sizes="[25, 50, 100]" :page-size="r.searchFrom.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="r.total"> </el-pagination>
        </div>
    </div>
</template>
<script>
    /* eslint-disable */
    import { cVIlist } from "@/api/request";
    import { Loading } from "element-ui";
    import { compressImage } from "@/utils/compressImage";
    import { getBtn } from "@/utils/btn";
    export default {
        data() {
            return {
                r: {
                    h: "500px",
                    loading: false,
                    total: 0,
                    tableList: [],
                    searchFrom: {
                        isZhb: "1",
                        pageNo: 1,
                        pageSize: 50,
                    },
                    btnFlg: [],
                    btnIdArr: ["00BP001B000", "00BP001B001", "00BP001B002", "00BP001B003", "00BP001B004", "00BP001B005", "00BP001B006"],
                },
                cud: {
                    core: "c",
                },
            };
        },
        created() {
            this.RGetlist(this.r.searchFrom);

            this.r.btnFlg = getBtn(this.r.btnIdArr, "/vehicle");

            console.log("btnFlg", this.r.btnFlg);
        },
        mounted() {
            let h = this.$refs.searchBox.offsetHeight;
            let pH = document.documentElement.clientHeight;
            this.r.h = pH - h - 96 + "px";
        },
        methods: {
            RHandleRowClick() {},
            RDetailFN() {},
            RLookImg() {},
            RSearchFn() {
                this.RGetlist(this.r.searchFrom);
            },
            RGetlist(searchFrom) {
                this.r.loading = true;
                cVIlist(searchFrom).then((res) => {
                    this.r.loading = false;
                    this.r.tableList = res.data.list;
                    this.r.total = res.data.total;
                });
            },
            RTHimportFn() {},
            RModelDown() {
                var tempwindow = window.open("_blank"); // 先打开页面
                tempwindow.location = "https://public-zw56.oss-cn-zhangjiakou.aliyuncs.com/vgs/%E8%BD%A6%E8%BE%86%E5%AF%BC%E5%85%A5%E6%A8%A1%E6%9D%BF.xlsx"; // 后更改页面地
            },
            RHandleSizeChange(val) {
                this.r.searchFrom.pageSize = val;
                this.RGetlist(this.r.searchFrom);
            },
            RHandleCurrentChange(val) {
                this.r.searchFrom.pageNo = val;
                this.RGetList(this.r.searchFrom);
            },
            UtilsDateFormat: function (row, column, cellValue) {
                if (cellValue) {
                    var t = new Date(cellValue); // row 表示一行数据, transferTime 表示要格式化的字段名称
                    var month = t.getMonth() + 1 < 10 ? "0" + (t.getMonth() + 1) : t.getMonth() + 1;
                    var date = t.getDate() < 10 ? "0" + t.getDate() : t.getDate();
                    var hour = t.getHours() < 10 ? "0" + t.getHours() : t.getHours();
                    var minutes = t.getMinutes() < 10 ? "0" + t.getMinutes() : t.getMinutes();
                    var seconds = t.getSeconds() < 10 ? "0" + t.getSeconds() : t.getSeconds();
                    return t.getFullYear() + "-" + month + "-" + date + " " + hour + ":" + minutes + ":" + seconds;
                }
            },
            UtilsDateFormat1: function (row, column, cellValue) {
                var t = new Date(cellValue); // row 表示一行数据, transferTime 表示要格式化的字段名称
                var month = t.getMonth() + 1 < 10 ? "0" + (t.getMonth() + 1) : t.getMonth() + 1;
                var date = t.getDate() < 10 ? "0" + t.getDate() : t.getDate();
                let str = "";
                if (cellValue) {
                    str = t.getFullYear() + "-" + month + "-" + date;
                }
                return str;
            },
        },
    };
</script>
<style lang="scss" scoped>
    .searchBox {
        display: flex;
        .searchBtnBox {
            padding-left: 10px;
            margin-left: 10px;
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
                color: rgb(88, 204, 2);
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
        }
    }
    .el-dialog__wrapper {
        ::v-deep .el-dialog__header {
            padding: 8px 20px !important;
            background-color: rgb(88, 204, 2);
            .el-dialog__title {
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
            font-size: 16px;
            margin-bottom: 20px;
            .iconFont {
                color: rgb(88, 204, 2);
                margin-right: 4px;
                font-size: 14px;
            }
        }
        ::v-deep .el-upload--picture-card {
            background-image: url("../../../assets/images/upload.png");
            background-size: 150%;
            background-position: 50% 50%;
        }
    }
</style>
