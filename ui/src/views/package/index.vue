<template>
    <div class="container">
        <div class="container-inner">
            <div class="title" ref="title">
                <h4>打包记录</h4>
            </div>
            <div class="search-box" ref="searchBox">
                <el-form :inline="true" class="demo-form-inline">
                    <el-form-item label="捆包号：">
                        <el-input type="input" :rows="3" style="width: 175px; vertical-align: top" size="mini"></el-input>
                    </el-form-item>
                    <el-form-item label="打包员：">
                        <el-input type="input" style="width: 175px; vertical-align: top" size="mini"></el-input>
                    </el-form-item>
                    <el-form-item label="数据来源：">
                        <el-select filterable size="mini" placeholder="请选择">
                            <el-option label="全部" value="">全部</el-option>
                            <el-option label="手动" value="0">手动</el-option>
                            <el-option label="扫码" value="1">扫码</el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="">
                        <el-button type="primary" size="mini" btnCode="00LP002B001" v-if="r.btnFlg[0]">查询</el-button>
                        <el-button type="primary" size="mini" btnCode="00LP002B002" v-if="r.btnFlg[1]">重置</el-button>
                        <el-button type="primary" size="mini" btnCode="00LP002B003" v-if="r.btnFlg[2]">打印</el-button>
                        <el-button type="primary" size="mini" btnCode="00LP002B004" v-if="r.btnFlg[3]">导出</el-button>
                        <el-button type="primary" size="mini" btnCode="00LP002B005" v-if="r.btnFlg[4]">新增</el-button>
                        <el-button type="primary" size="mini" btnCode="00LP002B006" v-if="r.btnFlg[5]">导入</el-button>
                        <el-button type="primary" size="mini" btnCode="00LP002B007" v-if="r.btnFlg[6]">下载模板</el-button>
                        <el-button type="primary" size="mini" btnCode="00LP002B009" v-if="r.btnFlg[8]">删除</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div class="table" :style="{ height: r.h }">
                <el-table height="100%" size="mini" ref="searchForm" :data="r.list" style="width: 100%" :header-cell-style="{ background: '#F5F5F5', color: 'black' }">
                    <el-table-column type="selection" width="55"> </el-table-column>
                    <el-table-column type="index" align="center" label="序号" width="60"></el-table-column>
                    <el-table-column label="操作" align="center" width="80px">
                        <template slot-scope="scope">
                            <div>
                                <el-button type="text" size="small" btnCode="00LP002B008" v-if="r.btnFlg[7]" @click="CUDHandleUpdate(scope.row)">修改</el-button>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="printTimes" label="打印次数" align="center" width="130">
                        <template slot-scope="scope">
                            <div>
                                <span v-if="scope.row.printTimes">{{ scope.row.printTimes }}</span>
                                <span v-else-if="scope.row.printTimes == null">0</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="cargoBarcode" label="钢卷号" align="center" width="180"></el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="origin" label="产地" align="center" width="100"></el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="productName" label="品名" align="center" width="100"></el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="model" label="材质" align="center" width="100"></el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="specification" label="规格" align="center" width="100"></el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="number" label="数量" align="center" width="100"
                        ><template slot-scope="scope">
                            <div><span>1</span></div>
                        </template>
                    </el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="weight" label="重量（吨）" align="center" width="100"></el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="customerName" label="客户名称" align="center" width="200"></el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="contractNumber" label="合同号" align="center" width="180"></el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="agreementNumber" label="合约号" width="180" align="center"></el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="bzModeName" label="包装方式" width="180" align="center"></el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="steelMillName" label="工作地点" align="center" width="180"></el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="teamName" label="班组名称" align="center" width="180"></el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="workingHoursName" label="工作时段" align="center" width="120"></el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="createUser" label="打包员" align="center"></el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="createTime" label="打包时间" align="center" width="180"></el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="printUser" label="打印人员" align="center"></el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="printDate" label="打印时间" align="center" width="180"></el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="goodsFrom" label="数据来源" align="center"
                        ><template slot-scope="scope">
                            <div>
                                <span v-if="scope.row.goodsFrom == ''">-</span>
                                <span v-else-if="scope.row.goodsFrom == 0">手动</span>
                                <span v-else-if="scope.row.goodsFrom == 1">扫码</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="updateUser" label="修改人" align="center"></el-table-column>
                    <el-table-column :show-overflow-tooltip="true" prop="updateTime" label="修改时间" align="center" width="180"></el-table-column>
                </el-table>
                <!-- 分页 -->
                <div>
                    <el-pagination background @size-change="RHandleSizeChange" @current-change="RHandleCurrentChange" :current-page="r.searchForm.pageNo" :page-sizes="[10, 20, 30, 40, 50]" :page-size="r.searchForm.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="r.total"> </el-pagination>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import { Message } from "element-ui";
    import { getBtn } from "@/utils/btn";
    import { packageList } from "@/api/request";
    export default {
        data() {
            return {
                r: {
                    h: "500px",
                    list: [],
                    total: 0,
                    loading: false,
                    btnFlg: [],
                    btnIdArr: ["00LP002B001", "00LP002B002", "00LP002B003", "00LP002B004", "00LP002B005", "00LP002B006", "00LP002B007", "00LP002B008", "00LP002B009"],
                    searchForm: {
                        pageNo: 1,
                        pageSize: 20,
                        cargoBarcodes: "", // 打包编号
                        goodsFrom: "", // 数据来源 0:手动 1:扫码
                        printType: "",
                        origin: "", // 产地
                        createTimeStart: "",
                        createTimeEnd: "",
                        teamName: "", // 班组名称
                        createUser: "", // 创建者，打包员
                    },
                },
            };
        },
        created() {
            this.r.btnFlg = getBtn(this.r.btnIdArr, "/packingRecord");
            this.RFetchList();
        },
        mounted() {
            let h = this.$refs.searchBox.offsetHeight; // 头部搜索盒子高度
            let H_title = this.$refs.title.offsetHeight; // 头部搜索盒子高度
            let pH = document.documentElement.clientHeight; // 浏览器高度
            this.r.h = pH - h - H_title - 100 + "px";
        },
        methods: {
            RFetchList() {
                this.r.loading = true;
                packageList(this.r.searchForm).then(
                    (res) => {
                        this.r.list = res.data.list;
                        this.r.loading = false;
                    },
                    (res) => {
                        console.log("packageList fail:", res);
                        Message({
                            message: `${res.message}`,
                            type: "error",
                            duration: 1500,
                        });
                        this.r.loading = false;
                    }
                );
            },
            RHandleSizeChange(val) {
                this.r.searchForm.pageSize = val;
                this.RFetchList();
            },
            // 当前页
            RHandleCurrentChange(val) {
                this.r.searchForm.pageNo = val;
                this.RFetchList();
            },
            CUDHandleUpdate() {},
        },
    };
</script>

<style lang="scss" scoped>
    .container {
        width: 100%;
        position: relative;
        height: 100%;
        .container-inner {
            .title {
                display: flex;
                justify-content: space-between;
                padding-right: 10px 10px;
                margin-left: 10px;
                border-bottom: 1px solid #dcdfe6;
                margin-bottom: 10px;
                h4 {
                    margin: 0;
                    font-size: 16px;
                    color: #333;
                    padding-bottom: 10px;
                    border-bottom: 2px solid rgb(88, 204, 2);
                }
            }
            .search-box {
                width: 100%;
                display: flex;
                font-size: 14px;
                padding: 0 10px;
                ul {
                    padding-left: 10px;
                    margin: 0;
                    flex: 1;
                    flex-direction: column;
                    li {
                        float: left;
                        margin-top: 5px;
                        margin-left: 10px;
                        list-style: none;
                    }
                }
                .el-form-item {
                    margin-bottom: 10px;
                }
            }
            .table {
                .el-table {
                    ::v-deep th {
                        background-color: #f5f5f5 !important;
                    }
                }
                ::v-deep .el-table-column--selection .cell {
                    padding: 0 10px;
                }
            }
        }
    }
</style>
