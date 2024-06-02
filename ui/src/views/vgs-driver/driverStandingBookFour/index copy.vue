<template>
    <div>
        <!-- 搜索条件 -->
        <div class="searchBox" ref="searchBox">
            <el-form :inline="true" :model="r.searchFrom" ref="searchFrom" class="demo-form-inline" label-width="120px" size="mini">
                <div class="inpBox">
                    <el-form-item label="环保登记编码:" prop="vhcRegistCode">
                        <el-input placeholder="" v-model="r.searchFrom.vhcRegistCode"></el-input>
                    </el-form-item>
                    <el-form-item label="机械环保代码:" prop="vhcMachineCode">
                        <el-input placeholder="" v-model="r.searchFrom.vhcMachineCode"></el-input>
                    </el-form-item>
                    <el-form-item label="上报状态 :" prop="reportStatus">
                        <el-select v-model="r.searchFrom.reportStatus" placeholder="请选择">
                            <el-option label="请选择" value=""></el-option>
                            <el-option label="待上报" value="0"></el-option>
                            <el-option label="上报中" value="1"></el-option>
                            <el-option label="已上报" value="2"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="上报时间:" prop="reportTime" class="long">
                        <el-date-picker v-model="r.searchFrom.reportTimeArr" format="yyyy-MM-dd" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
                    </el-form-item>
                    <el-form-item label="创建时间:" prop="createTime" class="long">
                        <el-date-picker v-model="r.searchFrom.createTimeArr" format="yyyy-MM-dd" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
                    </el-form-item>
                </div>
                <div class="searchBtnBox">
                    <el-form-item btnCode="00BP007B001">
                        <el-button type="primary" @click="RSearchFn">查询</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="RResetForm">重置</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="CUDAddVeicle">新建</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="RExportFn" :disabled="r.exportBtnDisable">导出</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </div>
        <!--  列表 -->
        <div class="table" :style="{ height: r.h }">
            <el-table :data="r.tableList" ref="table" v-loading="r.loading" bordersize="mini" @row-click="RHandleRowClick" height="100%" style="width: 100%" center border>
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column fixed type="index" width="55" label="序号"></el-table-column>
                <el-table-column label="操作" width="120">
                    <template slot-scope="scope">
                        <div>
                            <el-button type="text" size="mini" @click="CUDModifyVeicle(scope.row)">修改1</el-button>
                            <el-button type="text" size="mini" v-if="scope.row.reportStatus != 2" @click="CUDDelFN(scope.row)">删除</el-button>
                            <el-button type="text" size="mini" @click="RDetailFN(scope.row)">详情</el-button>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column property="reportStatus" label="上报状态">
                    <template scope="scope">
                        <div>
                            <span>{{ ["待上报", "上报中", "已上报"][scope.row.reportStatus] }}</span>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="reportTime" width="160" :formatter="UtilsDateFormat" label="上报时间"></el-table-column>
                <el-table-column property="reportDescribe" min-width="170" :show-overflow-tooltip="true" label="上报描述"></el-table-column>
                <el-table-column prop="vhcRegistCode" min-width="220" :show-overflow-tooltip="true" label="环保登记编码"></el-table-column>
                <el-table-column prop="productDate" width="160" :formatter="UtilsDateFormat1" label="生产日期"></el-table-column>
                <el-table-column prop="standardsName" min-width="160" :show-overflow-tooltip="true" label="排放阶段"></el-table-column>
                <el-table-column prop="vhcMachineCode" min-width="220" :show-overflow-tooltip="true" label="机械环保代码"></el-table-column>
                <el-table-column prop="createUserName" width="170" label="创建者"></el-table-column>
                <el-table-column prop="createTime" width="170" :formatter="UtilsDateFormat" label="创建时间"></el-table-column>
            </el-table>
        </div>
        <!-- 分页码 -->
        <div>
            <el-pagination background @size-change="RHandleSizeChange" @current-change="RHandleCurrentChange" :current-page="r.searchFrom.pageNo" :page-sizes="[25, 50, 100]" :page-size="r.searchFrom.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="r.total"> </el-pagination>
        </div>
        <!-- 新建或编辑 -->
        <el-dialog :title="cud.core === 'c' ? '新建' : '编辑'" :close-on-click-modal="false" :close-on-press-escape="false" :visible.sync="cud.dialogVisible" @close="CUDCloseDialog" width="1100px" center>
            <span>
                <el-form :inline="true" :model="cud.addFrom" ref="addFrom" :rules="cud.rules" class="demo-form-inline yanzheng jiaoyan" label-width="110px" size="mini">
                    <div class="inpBox">
                        <el-form-item label="环保登记编码:" prop="vhcRegistCode">
                            <el-input placeholder="" v-model="cud.addFrom.vhcRegistCode" maxlength="20"></el-input>
                        </el-form-item>
                        <el-form-item label="生产日期:" prop="productDate">
                            <el-date-picker v-model="cud.addFrom.productDate" format="yyyy-MM-dd" value-format="timestamp" type="date" placeholder="选择日期时间"></el-date-picker>
                        </el-form-item>
                        <el-form-item label="排放阶段:" prop="standards">
                            <el-select placeholder="请选择" v-model="cud.addFrom.standards">
                                <el-option label="国 0" value="1"></el-option>
                                <el-option label="国 1" value="2"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="机械环保代码:" prop="vhcMachineCode">
                            <el-input placeholder="" v-model="cud.addFrom.vhcMachineCode" maxlength="20"></el-input>
                        </el-form-item>
                        <div class="addressBox">
                            <h4 class="title">
                                <span><i class="fa fa-picture-o iconFont" aria-hidden="true"></i>发动机铭牌照片</span>
                            </h4>
                        </div>
                        <div class="imgBox">
                            <el-upload action="" :file-list="cud.listImg" ref="upload" :auto-upload="false" :on-change="CUDImgPreview" list-type="picture-card" accept="image/*" :limit="1" :on-preview="CUDHandlePictureCardPreview" :on-remove="CUDHandleRemove" :class="cud.uploadIconClass"> </el-upload>
                        </div>
                    </div>
                </el-form>
            </span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="cud.dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="CUDSaveFn">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 查看图片 -->
        <el-dialog title="查看图片" :visible.sync="cud.lookImgVisible" center="">
            <img width="100%" :src="cud.dialogImageUrl" alt="" />
        </el-dialog>
    </div>
</template>
<script>
    /* eslint-disable */
    import { noroadinfoList, noroadinfoListExport, noroadinfoAdd, noroadinfoUpdate, noroadinfoDelete } from "@/api/request";
    import { Loading } from "element-ui";
    import { compressImage } from "@/utils/compressImage";
    export default {
        data() {
            return {
                r: {
                    h: "500px",
                    exportBtnDisable: false,
                    loading: false,
                    total: 0,
                    tableList: [],
                    searchFrom: {
                        pageNo: 1,
                        pageSize: 50,
                    },
                },
                cud: {
                    core: "c",
                    dialogVisible: false,
                    addFrom: {},
                    listImg: [],
                    uploadIconClass: [],
                    lookImgVisible: false,
                    dialogImageUrl: "",
                    imgfile: new FormData(),
                    rules: {
                        vhcRegistCode: [{ required: true, message: "请输入环保登记编码", trigger: "blur" }],
                        productDate: [{ required: true, message: "请选择生产日期", trigger: "blur" }],
                        standards: [{ required: true, message: "请输入选择排放阶段", trigger: "blur" }],
                        vhcMachineCode: [{ required: true, message: "请输入机械环保代码", trigger: "blur" }],
                    },
                },
            };
        },
        created() {
            this.RGetlist(this.r.searchFrom);
        },
        mounted() {
            let h = this.$refs.searchBox.offsetHeight;
            let pH = document.documentElement.clientHeight;
            this.r.h = pH - h - 96 + "px";
        },
        methods: {
            RHandleRowClick() {},
            RDetailFN() {},
            RSearchFn() {
                console.log(this.r.searchFrom);
                this.RGetlist(this.r.searchFrom);
            },
            RResetForm() {
                this.r.searchFrom = {
                    pageNo: 1,
                    pageSize: 50,
                };
            },
            RHandleSizeChange(val) {
                this.r.searchFrom.pageSize = val;
                this.RGetlist(this.r.searchFrom);
            },
            RHandleCurrentChange(val) {
                this.r.searchFrom.pageNo = val;
                this.RGetList(this.r.searchFrom);
            },
            RGetlist(searchFrom) {
                this.r.loading = true;
                noroadinfoList(searchFrom).then((res) => {
                    this.r.loading = false;
                    this.r.tableList = res.data.list;
                    this.r.total = res.data.total;
                });
            },
            RExportFn() {
                this.r.exportBtnDisable = true;
                setTimeout(() => {
                    this.r.exportBtnDisable = false;
                }, 5000);
                const downloadLoadingInstance = Loading.service({
                    text: "正在处理...",
                    spinner: "el-icon-loading",
                    background: "rgba(0, 0, 0, 0.7)",
                });
                noroadinfoListExport(this.r.searchFrom).then(
                    (response) => {
                        console.log("response", response);
                        downloadLoadingInstance.close();
                        let blob = new Blob([response], {
                            type: "application/vnd.ms-excel",
                        });
                        let date = new Date();
                        let newTime = date.getFullYear() + "-" + (date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1) + "-" + date.getDate();
                        let fileName = "非道路机械台账" + newTime + ".xls";
                        if (window.navigator.msSaveOrOpenBlob) {
                            navigator.msSaveBlob(blob, fileName);
                        } else {
                            var link = document.createElement("a");
                            link.href = window.URL.createObjectURL(blob);
                            link.download = fileName;
                            link.click();
                            window.URL.revokeObjectURL(link.href); // 释放内存
                        }
                    },
                    (error) => {
                        downloadLoadingInstance.close();
                        console.log("error", error);
                    }
                );
            },
            // 上传回调
            CUDImgPreview(file, fileList) {
                console.log("imgPreview:", file);
                console.log("imgPreview:", fileList);
                let fileName = file.name;
                let regex = /(.jpg|.jpeg|.gif|.png|.bmp)$/;
                if (regex.test(fileName.toLowerCase())) {
                    if (fileList.length < 1) {
                        this.cud.uploadIconClass = [];
                    } else {
                        this.cud.uploadIconClass = ["hidden"];
                    }
                } else {
                    fileList.pop();
                    this.$alert("请选择jpg, jpeg, gif, png, bmpd格式的图片", "系统提示");
                    return false;
                }
                this.cud.listImg = fileList;
            },
            // 放大图像
            CUDHandlePictureCardPreview(file) {
                console.log("handlePictureCardPreview:", file);
                this.cud.dialogImageUrl = file.url;
                this.cud.lookImgVisible = true;
            },
            // 删除图片
            CUDHandleRemove(file, fileList) {
                console.log("handleRemove:", file);
                console.log("handleRemove:", fileList);
                if (fileList.length < 1) {
                    this.cud.uploadIconClass = [];
                }
                this.cud.listImg = fileList;
            },
            CUDSaveFn() {
                this.$refs["addFrom"].validate((valid) => {
                    if (valid) {
                        this.cud.addFrom.standards = {
                            createTime: 1594282878000,
                            createUserId: "0000020000010010000",
                            createUserName: "客客",
                            ext1: "",
                            ext2: "",
                            ext3: "",
                            id: 6,
                            isDelete: 1,
                            name: "国 5",
                            remark: "5",
                            updateTime: null,
                            updateUserId: "",
                            updateUserName: "",
                        };
                        this.cud.addFrom.standardsName = this.cud.addFrom.standards.name;
                        this.cud.addFrom.standardsId = this.cud.addFrom.standards.id;
                        this.cud.imgfile.set("params", JSON.stringify(this.cud.addFrom));
                        if (this.cud.listImg.length > 0) {
                            console.log(this.cud.listImg);
                            if (!this.cud.listImg[0].flag) {
                                // flag表示是更新操作, 无需压缩
                                compressImage(this.cud.listImg[0].url, this.cud.listImg[0].raw, {
                                    width: 1000,
                                }).then((result) => {
                                    this.cud.imgfile.set("vhcEngineFile", result); // result即为压缩后的结果
                                    this.CUDSaveHandler();
                                });
                            } else {
                                this.CUDSaveHandler();
                            }
                        } else {
                            this.$alert("", "请上传发动机铭牌照片");
                        }
                    } else {
                        console.log("no valid");
                    }
                });
            },
            CUDSaveHandler() {
                const loading = this.$loading({
                    lock: true,
                    text: "正在处理...",
                });
                if (this.cud.core === "c") {
                    noroadinfoAdd(this.cud.imgfile).then(
                        (res) => {
                            console.log(res);
                            loading.close();
                            this.$message({
                                message: "添加成功",
                                type: "success",
                            });
                        },
                        (error) => {
                            loading.close();
                            console.log(error);
                        }
                    );
                } else {
                    noroadinfoUpdate(this.cud.imgfile).then(
                        (res) => {
                            console.log(res);
                            loading.close();
                            this.$message({
                                message: "修改成功",
                                type: "success",
                            });
                        },
                        (error) => {
                            loading.close();
                            console.log(error);
                        }
                    );
                }
            },
            CUDAddVeicle() {
                this.cud.dialogVisible = true;
                this.cud.core = "c";
                if (this.$refs.addFrom) {
                    this.$refs.addFrom.clearValidate();
                }
            },
            CUDModifyVeicle(row) {
                if (this.$refs.addFrom) {
                    this.$refs.addFrom.clearValidate();
                }
                this.cud.core = "e";
                this.cud.addFrom = JSON.parse(JSON.stringify(row));
                this.cud.addFrom["standards"] = { id: row.standardsId, name: row.standardsName };
                if (row.vhcEngineAccessUrl) {
                    this.$set(this.cud.listImg, 0, { status: 'ready', url: row.vhcEngineAccessUrl, flag: true })
                } else {
                    this.cud.listImg = [];
                }
                console.log(row);
                console.log(this.cud.listImg);
                this.cud.dialogVisible = true;
            },
            CUDDelFN() {},
            CUDCloseDialog() {
                this.cud.addFrom = {};
                this.cud.listImg = [];
                this.cud.uploadIconClass = [];
                this.$refs.table.clearSelection();
                this.$refs.addFrom.clearValidate();
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
