<template>
    <!-- //出库计划 -->
    <div class="chuKuZuoyeWarp">
        <div class="chuKuZuoyeWarp-body">
            <div>
                <div ref="searchBox">
                    <!-- 标题 -->
                    <div class="detailsTitle">
                        <h4>出库计划</h4>
                    </div>
                    <!-- 查询条件 -->
                    <div>
                        <div class="inpBox">
                            <el-form :inline="true" :model="formInline" class="demo-form-inline">
                                <el-form-item label="车牌号:">
                                    <el-input v-model="formInline.carNum" maxlength="20" size="mini" placeholder="请输入车牌号"></el-input>
                                </el-form-item>
                                <el-form-item label="客户名称:">
                                    <el-input v-model="formInline.userName" maxlength="50" size="mini" placeholder="请输入客户名称"></el-input>
                                </el-form-item>
                                <el-form-item label="捆包号:">
                                    <el-input v-model="formInline.gangJuanHao" maxlength="50" size="mini" placeholder="请输入捆包号"></el-input>
                                </el-form-item>
                                <el-form-item label="规格:">
                                    <el-input v-model="formInline.guiGe" maxlength="100" size="mini" placeholder="请输入规格"></el-input>
                                </el-form-item>
                                <el-form-item label="重量（吨）:">
                                    <el-input v-model="formInline.weightSearch" maxlength="30" size="mini" placeholder="请输入重量"></el-input>
                                </el-form-item>
                                <el-form-item label="汽运单号:">
                                    <el-input v-model="formInline.wayNo" maxlength="30" size="mini" placeholder="请输入汽运单号"></el-input>
                                </el-form-item>
                                <el-form-item>
                                    <el-button type="primary" size="mini" @click="chaxun" btnCode="003P005B001" v-if="btnFlg[0]">查询</el-button>
                                    <el-button type="primary" size="mini" @click="chongZhi" btnCode="003P005B002" v-if="btnFlg[1]">重置</el-button>
                                    <el-button type="primary" size="mini" @click="addBtn" btnCode="003P005B003" v-if="btnFlg[2]">添加</el-button>
                                    <el-button type="primary" size="mini" @click="xiugaiBtn" btnCode="003P005B004" v-if="btnFlg[3]">修改</el-button>
                                    <el-button type="primary" size="mini" @click="deleteBtn" btnCode="003P005B005" v-if="btnFlg[4]">删除</el-button>
                                    <el-button type="primary" size="mini" @click="daYinBtn" btnCode="003P005B009" v-if="btnFlg[8]">打印</el-button>
                                    <el-button type="primary" v-if="btnFlg[10]" size="mini" @click="sendMessage">发送短信</el-button>
                                    <el-button type="primary" size="mini" v-if="btnFlg[11]" @click="zhuangchedanDY">打印装车单</el-button>
                                </el-form-item>
                            </el-form>
                        </div>
                    </div>
                </div>
                <!--表格-->
                <div class="chuKuZuoyeWarp-body-table" :style="{ height: h }">
                    <el-table height="100%" size="mini" ref="tabref1" :data="chukuTableData" style="width: 100%" :row-style="{ height: '20px' }" :cell-style="{ padding: '0px' }" class="elTab" :header-cell-style="{ background: '#F5F5F5', color: 'black' }" @row-click="dianjixuanzhong" highlight-current-row>
                        <el-table-column type="index" align="center" label="序号" fixed width="60"></el-table-column>
                        <el-table-column label="操作" align="center" fixed width="100">
                            <template slot-scope="scope">
                                <div>
                                    <span v-if="scope.row.productType == '4' || scope.row.productType == '5' || scope.row.productType == '6'">
                                        <el-button type="text" size="small" @click="addProductTypeClick(scope.row)" btnCode="003P005B008" v-if="btnFlg[7]">添加货物</el-button>
                                    </span>
                                    <span v-else>
                                        <el-button type="text" size="small" @click="guanLianClick(scope.row)" btnCode="003P005B006" v-if="btnFlg[5]">关联货物</el-button>
                                    </span>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column :show-overflow-tooltip="true" prop="outstockNo" label="出库单编号" align="center" width="250"></el-table-column>
                        <el-table-column :show-overflow-tooltip="true" prop="" label="装车短信" align="center" width="120">
                            <template v-slot="scope">
                                <span>{{ scope.row.sendFlag == "1" ? "已发送" : "未发送" }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column :show-overflow-tooltip="true" prop="sourceName" label="出库单来源" align="center" width="150"></el-table-column>
                        <el-table-column :show-overflow-tooltip="true" prop="customerName" label="客户名称" align="center" width="250"></el-table-column>
                        <el-table-column :show-overflow-tooltip="true" prop="productTypeName" label="货物类型" align="center" width="150"></el-table-column>
                        <el-table-column :show-overflow-tooltip="true" prop="outstockPlateNumber" label="出库车牌号" align="center" width="150"></el-table-column>

                        <el-table-column :show-overflow-tooltip="true" prop="driverName" label="司机姓名" align="center" width="150"></el-table-column>
                        <el-table-column :show-overflow-tooltip="true" prop="idNumber" label="司机身份证号" align="center" width="200"></el-table-column>
                        <el-table-column :show-overflow-tooltip="true" prop="unloadingSite" label="卸货地" align="center" width="200"></el-table-column>
                        <!-- <el-table-column
              :show-overflow-tooltip="true"
              prop="oddNumbers"
              label="单号"
              align="center"
              width="200"
            ></el-table-column>-->
                        <el-table-column :show-overflow-tooltip="true" prop="deliveryUnit" label="提货单位" align="center" width="200"></el-table-column>
                        <el-table-column :show-overflow-tooltip="true" prop="wayNo" label="汽运单号" align="center" width="200"></el-table-column>
                        <el-table-column :show-overflow-tooltip="true" prop="externalOrderNo" label="外部单号" align="center" width="200"></el-table-column>
                        <el-table-column :show-overflow-tooltip="true" prop="shipName" label="船名" align="center" width="150"></el-table-column>
                        <el-table-column :show-overflow-tooltip="true" prop="cabin" label="船舱" align="center" width="150"></el-table-column>
                        <el-table-column :show-overflow-tooltip="true" prop="payCustomerName" label="结算客户" align="center" width="200"></el-table-column>
                        <el-table-column :show-overflow-tooltip="true" prop="feeModeTypeName" label="结算方式" align="center" width="120"></el-table-column>
                        <el-table-column :show-overflow-tooltip="true" prop="createUser" label="制单人" align="center" width="150"></el-table-column>

                        <el-table-column :show-overflow-tooltip="true" prop="remark" label="备注" align="center" width="150"></el-table-column>
                    </el-table>
                </div>
                <!--分页-->
                <div class="pags" style="text-align: right">
                    <el-pagination background @current-change="handleCurrentChangeA" :current-page="current" :page-size="pagesize" :page-sizes="[10, 20, 30, 40, 50]" layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChangeA" :total="total"></el-pagination>
                </div>
            </div>
            <!-- 标签页切换 -->
            <div class="market_out">
                <el-tabs v-model="activeName" @tab-click="handleClick">
                    <!--出库单详情-->
                    <el-tab-pane label="出库单详情" name="first" v-if="luoWenGangXiangQingLook">
                        <div>
                            <!--按钮-->
                            <div ref="searchBox2" style="margin-top: 5px">
                                <el-button type="primary" size="mini" @click="associated" btnCode="003P005B007" v-if="btnFlg[6]">取消关联</el-button>
                                <span>
                                    总重量:
                                    <span style="color: red">{{ allweight }}</span> &nbsp;吨
                                </span>
                                <span style="margin-left: 10px">
                                    总件数:
                                    <span style="color: red">{{ allNumber }}</span> &nbsp;件
                                </span>
                                <!-- <span style="margin-left:15px">
                  总磅重:
                  <span style="color:red;">{{allPoundWeight}}</span> &nbsp;吨
                </span>-->
                            </div>
                            <!--详情表格-->
                            <div class="chuKuZuoyeWarp-body-detailsTab">
                                <div class="chuKuZuoyeWarp-body-table" :style="{ height: detailsH }">
                                    <el-table
                                        height="100%"
                                        size="mini"
                                        ref="tabref2"
                                        :data="zibiaoTableData"
                                        style="width: 100%"
                                        :header-cell-style="{
                                            background: '#F5F5F5',
                                            color: 'black',
                                        }"
                                        @row-click="xuanzhongXiangqing"
                                        highlight-current-row
                                    >
                                        <el-table-column type="index" align="center" fixed label="序号" width="60"></el-table-column>
                                        <el-table-column fixed :show-overflow-tooltip="true" prop="outstockCheckName" label="查验状态" align="center" width="150"></el-table-column>
                                        <el-table-column :show-overflow-tooltip="true" prop="productTypeName" label="货物类型" align="center" width="150"></el-table-column>
                                        <el-table-column :show-overflow-tooltip="true" prop="cargoBarcode" label="捆包号" align="center" width="230"></el-table-column>
                                        <el-table-column :show-overflow-tooltip="true" prop="agreementNumber" label="合约号" align="center" width="200"></el-table-column>
                                        <el-table-column :show-overflow-tooltip="true" prop="steelMillName" label="品牌" width="200" align="center"></el-table-column>
                                        <el-table-column :show-overflow-tooltip="true" prop="productName" label="品名" align="center" width="200"></el-table-column>
                                        <el-table-column :show-overflow-tooltip="true" prop="specification" label="规格" align="center" width="200"></el-table-column>
                                        <el-table-column :show-overflow-tooltip="true" width="150" prop="model" label="材质" align="center"></el-table-column>
                                        <el-table-column :show-overflow-tooltip="true" prop="weight" label="重量（吨）" align="center" width="150"></el-table-column>
                                        <el-table-column :show-overflow-tooltip="true" prop="sheets" label="张数" align="center" width="150"></el-table-column>
                                        <el-table-column :show-overflow-tooltip="true" prop="vehicleNumber" label="车船号" align="center" width="150"></el-table-column>
                                        <el-table-column :show-overflow-tooltip="true" prop="surfaceQuality" label="表面质量" align="center" width="100"></el-table-column>
                                        <el-table-column :show-overflow-tooltip="true" prop="warehouseName" label="仓库名称" align="center" width="150"></el-table-column>
                                        <el-table-column :show-overflow-tooltip="true" prop="locationName" label="区位名称" align="center" width="150"></el-table-column>
                                        <el-table-column :show-overflow-tooltip="true" prop="binName" label="货位名称" align="center" width="150"></el-table-column>
                                        <el-table-column :show-overflow-tooltip="true" prop="oynumber" label="货号" align="center" width="150"></el-table-column>
                                        <el-table-column :show-overflow-tooltip="true" prop="contractNumber" label="合同号" align="center" width="200"></el-table-column>
                                        <el-table-column :show-overflow-tooltip="true" prop="oddNumbers" label="单号" align="center" width="200"></el-table-column>
                                        <el-table-column :show-overflow-tooltip="true" prop="heatNo" label="炉号" align="center" width="150"></el-table-column>
                                        <el-table-column :show-overflow-tooltip="true" prop="instockPlateNumber" label="入库车牌号" align="center" width="150"></el-table-column>
                                        <!-- <el-table-column :show-overflow-tooltip="true" prop="oynumber" label="欧冶编码" align="center"></el-table-column> -->
                                        <el-table-column :show-overflow-tooltip="true" prop="remark" label="备注" align="center" width="150"> </el-table-column>
                                    </el-table>
                                </div>
                                <!--分页-->
                                <div class="pags" style="text-align: right">
                                    <el-pagination background @current-change="handleCurrentChangeXQ" :current-page="currentXQ" :page-size="pagesizeXQ" :page-sizes="[10, 20, 30, 40, 50]" layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChangeXQ" :total="totalXQ"></el-pagination>
                                </div>
                            </div>
                        </div>
                    </el-tab-pane>
                    <!-- 出库单汇总 -->
                    <el-tab-pane label="出库单汇总" name="second" v-if="luoWenGangLook">
                        <!--按钮-->
                        <div ref="searchBox2" style="margin-top: 5px">
                            <span>
                                总重量:
                                <span style="color: red">{{ allWeightHuizong }}</span> &nbsp;吨
                            </span>
                            <!-- <span style="margin-left:10px">
                  总件数:
                  <span style="color:red;">{{allNumberHuizong}}</span> &nbsp;件
                </span> -->
                            <el-button style="margin-left: 15px" type="primary" size="mini" @click="delHuiZong" btnCode="003P005B00A" v-if="btnFlg[9]">删除</el-button>

                            <!-- <span style="margin-left:15px">
                总磅重:
                <span style="color:red;">{{allPoundWeightHuizong}}</span> &nbsp;吨
              </span>-->
                        </div>
                        <!--详情表格-->
                        <div class="chuKuZuoyeWarp-body-detailsTab">
                            <div class="chuKuZuoyeWarp-body-table" :style="{ height: detailsH }">
                                <el-table border height="100%" size="mini" ref="tabrefHuiZong" :data="HuizongTableData" style="width: 100%" :header-cell-style="{ background: '#F5F5F5', color: 'black' }" @row-click="xuanzhongHuiZong" highlight-current-row>
                                    <el-table-column type="index" align="center" label="序号" width="60"></el-table-column>
                                    <el-table-column :show-overflow-tooltip="true" prop="vehicleNumber" label="车船号" align="center" width="150"></el-table-column>
                                    <el-table-column :show-overflow-tooltip="true" prop="steelMillName" label="品牌" align="center" width="250"></el-table-column>
                                    <el-table-column :show-overflow-tooltip="true" prop="productName" label="品名" align="center" width="230"></el-table-column>
                                    <el-table-column :show-overflow-tooltip="true" prop="model" label="材质" align="center" width="200"></el-table-column>
                                    <el-table-column :show-overflow-tooltip="true" prop="specification" label="规格（mm）" width="150" align="center"></el-table-column>
                                    <el-table-column v-if="lengthLook" :show-overflow-tooltip="true" prop="length" label="长度（m）" align="center" width="200"></el-table-column>
                                    <el-table-column prop="number" label="件数" align="center" width="100"></el-table-column>
                                    <!-- <el-table-column
                    :show-overflow-tooltip="true"
                    prop="outPoundWeight"
                    label="出库磅重（吨）"
                    align="center"
                  ></el-table-column> -->
                                    <el-table-column :show-overflow-tooltip="true" prop="weight" label="重量（吨）" align="center"></el-table-column>
                                    <el-table-column :show-overflow-tooltip="true" prop="oddNumbers" label="单号" align="center" width="150"></el-table-column>
                                </el-table>
                            </div>
                            <!--分页-->
                            <div class="pags" style="text-align: right">
                                <el-pagination background @current-change="handleCurrentChangeHuiZong" :current-page="currentHuiZong" :page-size="pagesizeHuiZong" :page-sizes="[10, 20, 30, , 40, 50]" layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChangeHuiZong" :total="totalHuizong"></el-pagination>
                            </div>
                        </div>
                    </el-tab-pane>
                </el-tabs>
            </div>
            <!--点击添加按钮弹出框-->
            <div class="addMarkBox">
                <el-drawer :wrapperClosable="false" style="margin-bottom: 20px" :close-on-click-modal="false" v-loading="loading" title="出库单信息" direction="ltr" size="650px" :visible.sync="addMark" @close="closeDialog">
                    <!--form表单-->
                    <div>
                        <el-form :model="addFormData" ref="addFormData" label-width="100px" class="demo-dynamic" style="width: 350px; margin: 30px auto">
                            <el-form-item
                                label="客户名称"
                                :rules="[
                                    {
                                        required: true,
                                        message: '请输入客户名称',
                                        trigger: 'change',
                                    },
                                ]"
                                prop="kehuName"
                            >
                                <el-select size="mini" filterable v-model="addFormData.kehuName" @change="changeKehuAdd(addFormData.kehuName)" placeholder="请选择">
                                    <el-option v-for="(item, index) in kehuMing" :label="item.custerName" :key="index" :value="item.custerName"></el-option>
                                </el-select>
                            </el-form-item>

                            <el-form-item
                                label="货物类型"
                                :rules="[
                                    {
                                        required: true,
                                        message: '请输入货物类型',
                                        trigger: 'change',
                                    },
                                ]"
                                prop="goodsType"
                            >
                                <el-select size="mini" v-model="addFormData.goodsType" placeholder="请选择" @change="changeCode(addFormData.goodsType)">
                                    <el-option v-for="item in typeData" :label="item.show_name" :key="item.show_name" :value="item.item_no"></el-option>
                                </el-select>
                            </el-form-item>

                            <el-form-item label="当前库存" prop="COUNT(1)">
                                <el-input v-model="COUNT" :disabled="true" size="mini" style="width: 193px">
                                    <i slot="suffix">件</i>
                                </el-input>
                            </el-form-item>
                            <el-form-item
                                v-on:keyup.native="onKeyVehicleNumber"
                                prop="carNumber"
                                label="出库车牌号"
                                :rules="[
                                    {
                                        required: true,
                                        message: '请输入出库车牌号',
                                        trigger: 'change',
                                    },
                                ]"
                            >
                                <el-input size="mini" v-model.trim="addFormData.carNumber" style="width: 193px"></el-input>
                            </el-form-item>
                            <el-form-item
                                label="司机姓名"
                                prop="siJiName"
                                :rules="[
                                    {
                                        required: true,
                                        message: '请输入司机姓名',
                                        trigger: 'change',
                                    },
                                ]"
                            >
                                <el-input size="mini" v-model="addFormData.siJiName" maxlength="10" autocomplete="off" style="width: 193px"></el-input>
                                <el-button style="margin-left: 10px" type="text" @click="selectDriver()">选择</el-button>
                            </el-form-item>
                            <el-form-item label="司机手机" prop>
                                <el-input size="mini" v-model="addFormData.phone" maxlength="11" autocomplete="off" style="width: 193px"></el-input>
                            </el-form-item>
                            <el-form-item label="身份证号" prop>
                                <el-input size="mini" v-model="addFormData.numberId" maxlength="18" autocomplete="off" style="width: 193px"></el-input>
                            </el-form-item>
                            <el-form-item label="卸货地" prop>
                                <el-input size="mini" v-model="addFormData.xieHuoAddress" maxlength="50" autocomplete="off" style="width: 193px"></el-input>
                            </el-form-item>
                            <el-form-item
                                label="结算客户"
                                :rules="[
                                    {
                                        required: true,
                                        message: '请选择结算客户',
                                        trigger: 'change',
                                    },
                                ]"
                                prop="payCustomerName"
                                v-if="feeMode"
                            >
                                <el-select v-model="addFormData.payCustomerName" size="mini" placeholder="请选择" filterable clearable style="width: 193px" @change="jiesuanKehu">
                                    <el-option v-for="(item, index) in kehuMing" :key="index" :label="item.custerName" :value="item.custerName"> </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item
                                label="结算方式"
                                :rules="[
                                    {
                                        required: true,
                                        message: '请选择结算方式',
                                        trigger: 'change',
                                    },
                                ]"
                                prop="feeModeType"
                                v-if="feeMode"
                            >
                                <el-select size="mini" @change="jiesuanfangshi" v-model="addFormData.feeModeType" placeholder="请选择">
                                    <el-option v-for="item in jsMethods" :label="item.show_name" :key="item.item_no" :value="item.item_no"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="汽运单号" prop>
                                <el-input size="mini" v-model="addFormData.wayNo" maxlength="50" autocomplete="off" style="width: 193px"></el-input>
                            </el-form-item>
                            <el-form-item label="提货单位" prop>
                                <el-input maxlength="30" size="mini" v-model="addFormData.tiHuo" autocomplete="off" style="width: 193px"></el-input>
                            </el-form-item>
                            <el-form-item prop="externalOrderNo" label="外部单号">
                                <el-input v-model="addFormData.externalOrderNo" size="mini" placeholder="请输入" style="width: 200px"></el-input>
                            </el-form-item>
                            <el-form-item prop="shipName" label="船名">
                                <el-input v-model="addFormData.shipName" size="mini" placeholder="请输入" style="width: 200px"></el-input>
                            </el-form-item>
                            <el-form-item prop="cabin" label="船舱">
                                <el-input v-model="addFormData.cabin" size="mini" placeholder="请输入" style="width: 200px"></el-input>
                            </el-form-item>
                            <el-form-item label="备注" prop>
                                <el-input size="mini" v-model="addFormData.beizhu" maxlength="200" autocomplete="off" style="width: 193px"></el-input>
                            </el-form-item>
                        </el-form>
                    </div>
                    <!-- 表单按钮 -->
                    <div class="footerBox">
                        <el-button type="primary" @click="addOutboundOk('addFormData')" :disabled="addDisable">确定</el-button>
                        <el-button type="primary" @click="addQuXiao('addFormData')">取消</el-button>
                    </div>
                    <!-- 司机信息 -->
                    <el-drawer style="margin-bottom: 20px" title="选择司机" :visible.sync="table" direction="rtl" size="50%" :modal="false" @opened="addGoodOpen">
                        <div ref="searchBox" style="margin: 26px 0">
                            <el-form :inline="true" :model="derverFrom" ref="derverFrom" class="demo-form-inline" label-width="86px" size="mini">
                                <div class="searchBox inpBox">
                                    <div>
                                        <el-form-item label="司机姓名:" prop="name">
                                            <el-input placeholder type="text" v-model="derverFrom.name"></el-input>
                                        </el-form-item>
                                        <el-form-item label="联系电话:" prop="mobile">
                                            <el-input placeholder v-model="derverFrom.mobile"></el-input>
                                        </el-form-item>
                                    </div>
                                </div>
                                <div class="searchBtnBox" style="padding-left: 20px">
                                    <el-form-item>
                                        <el-button type="primary" @click="SJsearchFn">查询</el-button>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button type="primary" @click="resetForm('derverFrom')">重置</el-button>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button type="primary" @click="addDerver()">新增司机信息</el-button>
                                    </el-form-item>
                                </div>
                            </el-form>
                        </div>
                        <div :style="{ height: sjh }">
                            <el-table :data="gridData" ref="xzdz" height="100%" highlight-current-row @current-change="selderverChange">
                                <el-table-column type="index" width="55" label="序号"></el-table-column>
                                <el-table-column prop="name" width="120" :show-overflow-tooltip="true" label="司机姓名"></el-table-column>
                                <el-table-column prop="mobile" width="150" label="联系电话"></el-table-column>
                                <el-table-column prop="identityNo" width="200" :show-overflow-tooltip="true" label="身份证号"></el-table-column>
                                <el-table-column prop="bankCard" width="160" :show-overflow-tooltip="true" label="银行卡号"></el-table-column>
                                <el-table-column prop="bankName" width="160" :show-overflow-tooltip="true" label="开户行"></el-table-column>
                                <el-table-column prop="bankCardUser" label="收款人"></el-table-column>
                            </el-table>
                        </div>
                        <!-- 分页 -->
                        <div>
                            <el-pagination background @size-change="derverChange" @current-change="derverCurrentChange" :current-page="derverFrom.pageNo" :page-sizes="[25, 50, 100, 200, 1000]" :page-size="derverFrom.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="siJiTotal"></el-pagination>
                        </div>
                    </el-drawer>
                </el-drawer>
                <!-- </el-dialog> -->
            </div>
            <!--点击关联货物-->
            <div class="guanLianBox">
                <el-dialog v-loading="loading1" title="货物清单" :visible.sync="guanLianMark" :close-on-click-modal="false" width="90%" center>
                    <!--输入框-->
                    <div class="guanLianBox-input">
                        <ul>
                            <li>
                                捆包号：
                                <el-input type="textarea" v-model="guanLianGangJuanNum" style="width: 200px; vertical-align: top" size="mini"></el-input>
                            </li>
                            <!-- <li>
                客户名称：
                <el-input
                  type="text"
                  maxlength="50"
                  v-model="guanLianCustomerName"
                  style="width:185px;"
                  size="mini"
                ></el-input>
              </li>-->
                        </ul>
                        <ul>
                            <li>
                                材质：
                                <el-input type="text" maxlength="20" v-model="guanLianCaiZhi" style="width: 150px" size="mini"></el-input>
                            </li>
                            <li>
                                规格：
                                <el-input type="text" maxlength="20" v-model="guanLianGuiGe" style="width: 150px" size="mini"></el-input>
                            </li>
                        </ul>
                        <ul>
                            <li>
                                &nbsp;&nbsp;&nbsp;重量：
                                <el-input type="text" maxlength="20" v-model="guanLianWeight" style="width: 150px" size="mini"></el-input>
                            </li>
                            <li>
                                合约号：
                                <el-input type="text" v-model="guanLianHeYueNum" style="width: 150px" size="mini" maxlength="20"></el-input>
                            </li>
                        </ul>
                        <ul>
                            <li>
                                货物状态:
                                <el-select size="mini" filterable v-model="lockState" @change="searchLockState(lockState)" placeholder="请选择">
                                    <el-option v-for="item in options" :label="item.name" :key="item.name" :value="item.name"></el-option>
                                </el-select>
                            </li>
                            <li>
                                &nbsp;&nbsp;&nbsp;&nbsp;炉号：
                                <el-input type="text" v-model="luhao" style="width: 190px" size="mini" maxlength="20"></el-input>
                            </li>
                        </ul>
                        <ul>
                            <li>
                                <el-button type="primary" size="mini" @click="guanlianCX">查询</el-button>
                                <el-button type="primary" size="mini" @click="guanlianCZ">重置</el-button>
                            </li>
                        </ul>
                    </div>
                    <div class="guanLianBox-Table">
                        <el-table size="mini" ref="tabref" :data="relevanceTableData" style="width: 100%" height="40vh" :header-cell-style="{ background: '#F5F5F5', color: 'black' }" @select="selectTable" @select-all="selectAll" @selection-change="selectGoods" :default-sort="{ prop: 'createTime', order: 'descending' }">
                            <el-table-column type="selection" align="center" fixed width="60"></el-table-column>

                            <el-table-column type="index" align="center" fixed label="序号" width="60"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="customerName" label="客户名称" align="center" width="230"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="lockState" label="锁定状态" align="center" width="150"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="productTypeName" label="货物类型" align="center"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="cargoBarcode" label="捆包号" align="center" width="200"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="contractNumber" label="合同号" align="center" width="150"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="agreementNumber" label="合约号" align="center" width="150"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="steelMillName" label="品牌" width="150" align="center"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="productName" label="品名" align="center" width="150"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="specification" label="规格" align="center" width="150"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="model" width="150" label="材质" align="center"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="weight" label="重量（吨）" align="center" width="100"></el-table-column>
                            <el-table-column v-if="sheetsShow" :show-overflow-tooltip="true" prop="sheets" label="张数" align="center" width="100"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="vehicleNumber" label="车船号" align="center" width="150"></el-table-column>
                            <!-- <el-table-column
                :show-overflow-tooltip="true"
                prop="lockState"
                label="锁定状态"
                align="center"
                width="100"
              ></el-table-column> -->
                            <!-- <el-table-column :show-overflow-tooltip="true" prop="oyBillId" label="提单号" align="center" width="100"></el-table-column> -->
                            <!-- <el-table-column :show-overflow-tooltip="true" prop="oynumber" label="欧冶编码" align="center" width="100"></el-table-column> -->
                            <el-table-column :show-overflow-tooltip="true" prop="warehouseName" label="仓库名称" align="center"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="locationName" label="区位名称" align="center"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="binName" label="货位名称" align="center"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="heatNo" label="炉号" align="center" width="150"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="oynumber" label="货号" align="center"></el-table-column>
                            <el-table-column v-if="sheetsShow" :show-overflow-tooltip="true" prop="outsheets" label="出库张数" align="center" fixed="right">
                                <template slot-scope="scope">
                                    <el-input size="mini" @blur="inputNumber(scope.row)" v-model="scope.row.outsheets" v-number-input.float="0" v-if="scope.row.inputShow"></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column v-if="sheetsShow" :show-overflow-tooltip="true" prop="outweight" label="出库重量（吨）" align="center" fixed="right" width="120"></el-table-column>
                            <el-table-column v-if="sheetsShow" :show-overflow-tooltip="true" prop="createTime" label="入库时间" align="center" width="180" sortable></el-table-column>
                        </el-table>
                        <!--分页-->
                        <div class="pags" style="text-align: right">
                            <el-pagination background @current-change="handleCurrentChangeGL" :current-page="currentGL" :page-size="pagesizeGL" :page-sizes="[10, 20, 30, 40, 50]" layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChangeGL" :total="totalGL"></el-pagination>
                        </div>
                    </div>
                    <div style="width: 158px; margin: 20px auto">
                        <el-button type="primary" @click="guanlianOk" :disabled="isDisable">确定</el-button>
                        <el-button type="primary" @click="guanLianQuXiao">取消</el-button>
                    </div>
                </el-dialog>
            </div>
            <!--手动提单点击关联货物-->
            <div class="guanLianBox">
                <el-dialog v-loading="loading1" title="货物清单" :visible.sync="SDTDMark" :close-on-click-modal="false" width="90%" center>
                    <div class="inpBox">
                        <el-form :inline="true">
                            <el-form-item label="提单号：">
                                {{ TDnumber }}
                            </el-form-item>
                            <br />
                            <el-form-item label="捆包号：">
                                <el-input type="textarea" v-model="guanLianGangJuanNum" style="width: 200px; vertical-align: top" size="mini"></el-input>
                            </el-form-item>
                            <el-form-item label="仓库名：">
                                <el-select v-model="cangKuName" size="mini" style="width: 150px" @change="changeXiugaiKW(cangKuName)" placeholder="请选择">
                                    <el-option v-for="item in addKuwei" :label="item.warehouseName" :key="item.warehouseId" :value="item.warehouseId"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="区位名：">
                                <el-select v-model="quWeiName" size="mini" style="width: 150px" @change="changeXiugaiQW(quWeiName)" placeholder="请选择">
                                    <el-option v-for="item in addQ" :label="item.locationName" :key="item.locationId" :value="item.locationId"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="货位名：">
                                <el-select v-model="huoWeiName" size="mini" style="width: 150px" @change="changeXiugaiHW(huoWeiName)" placeholder="请选择">
                                    <el-option v-for="item in addH" :label="item.binName" :key="item.binId" :value="item.binId"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="规格：">
                                <el-input type="text" v-model="specification" style="width: 150px" size="mini" maxlength="20"></el-input>
                            </el-form-item>
                            <br />
                            <el-form-item>
                                <el-button type="primary" size="mini" @click="SDTDCX">查询</el-button>
                                <el-button type="primary" size="mini" @click="SDTDCZ">重置</el-button>
                                <span style="margin-left: 15px"
                                    >总吨数：<span style="color: red">{{ allWeightGL }}</span> 吨</span
                                >
                                <span style="margin-left: 15px">
                                    已选：<span style="color: red">{{ selectWeightGL }}</span> 吨,
                                    <span style="color: red; margin-left: 5px">{{ selectNumberGL }}</span>
                                    件
                                </span>
                            </el-form-item>
                        </el-form>
                    </div>
                    <div class="guanLianBox-Table">
                        <el-table size="mini" ref="tabref" :data="SDTDTableData" style="width: 100%" height="40vh" :header-cell-style="{ background: '#F5F5F5', color: 'black' }" @select="selectTable" @select-all="selectAll" @selection-change="selectGoods" :default-sort="{ prop: 'specification', order: 'descending' }">
                            <el-table-column type="selection" align="center" fixed width="50"></el-table-column>
                            <el-table-column type="index" align="center" fixed label="序号" width="50"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="cargoBarcode" label="捆包号" align="center" width="200"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="productTypeName" label="货物类型" align="center"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="productName" label="品名" align="center" width="100"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="specification" sortable label="规格" align="center" width="100"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="model" width="100" label="材质" align="center"></el-table-column>
                            <el-table-column v-if="sheetsShow" :show-overflow-tooltip="true" prop="sheets" width="100" label="张数" align="center"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="weight" label="重量（吨）" align="center" width="100"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="steelMillName" label="产地" align="center"></el-table-column>
                            <el-table-column :show-overflow-tooltip="true" prop="wlbName" sortable label="货位" align="center" width="200"></el-table-column>
                            <el-table-column v-if="sheetsShow" :show-overflow-tooltip="true" prop="outsheets" label="出库张数" align="center" fixed="right">
                                <template slot-scope="scope">
                                    <el-input size="mini" @blur="inputNumber(scope.row)" v-model="scope.row.outsheets" v-number-input.float="0" v-if="scope.row.inputShow"></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column v-if="sheetsShow" :show-overflow-tooltip="true" prop="outweight" label="出库重量（吨）" align="center" fixed="right" width="120"></el-table-column>
                            <el-table-column v-if="sheetsShow" :show-overflow-tooltip="true" prop="" label="入库时间" align="center" width="180" sortable></el-table-column>
                        </el-table>
                    </div>
                    <div style="width: 158px; margin: 20px auto">
                        <el-button type="primary" @click="SDTDOk" :loading="GLloading">确定</el-button>
                        <el-button type="primary" @click="SDTDQuXiao">取消</el-button>
                    </div>
                </el-dialog>
            </div>
            <!--点击添加货物-->
            <div class="guanLianBox">
                <el-dialog v-loading="loading1" title="添加货物" :visible.sync="addProductTypeMark" :close-on-click-modal="false" width="90%" center>
                    <div>
                        <el-table :data="copyTableList" style="width: 100%">
                            <el-table-column prop="cheChuanHaoValue" align="center" label="车船号"></el-table-column>
                            <el-table-column prop="chanDiValue" align="center" label="品牌"></el-table-column>
                            <el-table-column prop="pinMingValue" align="center" label="品名"></el-table-column>
                            <el-table-column prop="caiZhiValue" align="center" label="材质"></el-table-column>
                            <el-table-column prop="guiGeValue" align="center" label="规格（mm）"></el-table-column>
                            <el-table-column prop="changDuValue" align="center" label="长度（m）"></el-table-column>
                            <el-table-column prop="kuCunJianShuValue" align="center" label="库存件数"></el-table-column>
                            <el-table-column prop="jianShuValue" align="center" label="件数"></el-table-column>
                            <el-table-column prop="danHaoValue" align="center" label="单号"></el-table-column>
                            <el-table-column prop="date" label="编辑" width="180">
                                <template slot-scope="scope3">
                                    <el-button @click="delClickCopy(scope3.$index, scope3.row)" type="text" size="small">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                    <div style="margin-left: 15px; margin-top: 15px">
                        <el-button type="primary" @click="addHuoWuBtn">添加</el-button>
                        <!-- <el-button type="primary" @click="additiveOk">确定</el-button>
            <el-button type="primary" @click="additiveQuXiao">取消</el-button>-->
                    </div>
                    <el-table size="mini" :data="detailsDataCopyAdd" ref="demoTable" style="width: 100%; margin-top: 20px" :header-cell-style="{ background: '#f5f5f5' }" empty-text="请点击上方添加按钮添加货物">
                        <el-table-column prop="cheChuanHaoValue" align="center" label="车船号">
                            <template slot-scope="scope1">
                                <el-select filterable v-model="scope1.row.cheChuanHaoValue" @change="changeCheChuanHao(scope1.row, scope1.row.cheChuanHaoValue)" size="mini" placeholder="请选择车船号">
                                    <el-option v-for="(item, index) in cheChuanHaoOptions" :key="index" :label="item.value" :value="item.value"></el-option>
                                </el-select>
                            </template>
                        </el-table-column>
                        <el-table-column prop="chanDiValue" align="center" label="品牌">
                            <template slot-scope="scope1">
                                <el-select filterable @change="changeChanDi(scope1.row, scope1.row.chanDiValue)" v-model="scope1.row.chanDiValue" size="mini" :disabled="disChanDi" placeholder="请选择品牌">
                                    <el-option v-for="(item, index) in chanDiOptions" :key="index" :label="item.label" :value="item.value"></el-option>
                                </el-select>
                            </template>
                        </el-table-column>
                        <el-table-column prop="pinMingValue" align="center" label="品名">
                            <template slot-scope="scope1">
                                <el-select filterable @change="changePingMing(scope1.row, scope1.row.pinMingValue)" v-model="scope1.row.pinMingValue" size="mini" :disabled="disPingMing" placeholder="请选择品名">
                                    <el-option v-for="(item, index) in pingMingOptions" :key="index" :label="item.label" :value="item.value"></el-option>
                                </el-select>
                            </template>
                        </el-table-column>
                        <el-table-column prop="caiZhiValue" align="center" label="材质">
                            <template slot-scope="scope1">
                                <el-select filterable @change="changeCaiZhi(scope1.row, scope1.row.caiZhiValue)" v-model="scope1.row.caiZhiValue" size="mini" :disabled="disCaiZhi" placeholder="请选择材质">
                                    <el-option v-for="(item, index) in caiZhiOptions" :key="index" :label="item.label" :value="item.value"></el-option>
                                </el-select>
                            </template>
                        </el-table-column>
                        <el-table-column prop="guiGeValue" align="center" label="规格（mm）">
                            <template slot-scope="scope1">
                                <el-select filterable @change="changeGuiGe(scope1.row, scope1.row.guiGeValue)" v-model="scope1.row.guiGeValue" :disabled="disGuige" size="mini" placeholder="请选择规格（mm）">
                                    <el-option v-for="(item, index) in guiGeOptions" :key="index" :label="item.label" :value="item.value"></el-option>
                                </el-select>
                            </template>
                        </el-table-column>
                        <el-table-column prop="changDuValue" align="center" label="长度（m）">
                            <template slot-scope="scope1">
                                <el-select filterable @change="changeChangDu(scope1.row, scope1.row.changDuValue)" v-model="scope1.row.changDuValue" :disabled="disChangDu" size="mini" placeholder="请选择长度（m）">
                                    <el-option v-for="(item, index) in changDuOptions" :key="index" :label="item.label" :value="item.value"></el-option>
                                </el-select>
                            </template>
                        </el-table-column>
                        <el-table-column prop="kuCunJianShuValue" align="center" label="库存件数">
                            <template slot-scope="scope1">
                                <el-input :disabled="disKuCunJianShu" v-model="scope1.row.kuCunJianShuValue" maxlength="50" placeholder="请输入库存件数" size="mini"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column prop="jianShuValue" align="center" label="件数">
                            <template slot-scope="scope1">
                                <el-input onkeyup="value=value.replace(/[^\d]/g,'')" @change="changeJianShu(scope1.row, scope1.row.jianShuValue)" :disabled="disJianShu" v-model="scope1.row.jianShuValue" maxlength="50" placeholder="请输入件数" size="mini"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column prop="danHaoValue" align="center" label="单号">
                            <template slot-scope="scope1">
                                <el-input v-model="scope1.row.danHaoValue" maxlength="50" placeholder="请输入单号" size="mini"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column prop align="center" label="编辑">
                            <template slot-scope="scope2">
                                <el-button @click="delClick(scope2.$index, scope2.row)" type="text" size="small">删除</el-button>
                                <el-button @click="addOkClick(scope2.$index, scope2.row)" type="text" size="small">保存</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div style="margin: 15px auto" class="el-dialog--center">
                        <el-button type="primary" @click="additiveOk()">确定</el-button>
                        <el-button type="primary" @click="additiveQuXiao">取消</el-button>
                    </div>
                </el-dialog>
            </div>
            <!-- 点击修改按钮 -->
            <div class="xiugai">
                <el-drawer :wrapperClosable="false" style="margin-bottom: 20px" :close-on-click-modal="false" title="出库单信息" direction="ltr" size="650px" :visible.sync="dialogVisiblexiugai" @close="closeEdit">
                    <!-- 表单 -->
                    <div>
                        <!--form表单-->
                        <el-form :model="xiugaiFormData" ref="xiugaiFormData" label-width="100px" class="demo-dynamic" style="width: 350px; margin: 30px auto">
                            <el-form-item
                                label="客户名称"
                                prop="kehuName"
                                :rules="[
                                    {
                                        required: true,
                                        message: '请选择客户名称',
                                        trigger: 'change',
                                    },
                                ]"
                            >
                                <!-- <el-input v-model="xiugaiFormData.kehuName" style="width:193px;"></el-input> -->
                                <el-select disabled size="mini" filterable v-model="xiugaiFormData.kehuName" @change="changeKehu(xiugaiFormData.kehuName)" placeholder="请选择">
                                    <el-option v-for="(item, index) in kehuMing" :label="item.custerName" :key="index" :value="item.custerName"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item
                                label="货物类型"
                                prop="goodsType"
                                :rules="[
                                    {
                                        required: true,
                                        message: '请选择货物类型',
                                        trigger: 'change',
                                    },
                                ]"
                            >
                                <el-select size="mini" v-model="xiugaiFormData.goodsType" placeholder="请选择" @change="changeCode(xiugaiFormData.goodsType)" :disabled="productTypeDis">
                                    <el-option v-for="(item, index) in typeData" :label="item.show_name" :key="index" :value="item.item_no"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item
                                prop="carNumber"
                                label="出库车牌号"
                                :rules="[
                                    {
                                        required: true,
                                        message: '请输入出库车牌号',
                                        trigger: 'change',
                                    },
                                ]"
                            >
                                <el-select v-model="xiugaiFormData.carNumber" filterable allow-create size="mini" clearable placeholder="请选择出库车牌号" @change="Changecar()" v-if="xuanzhongArr.source == 5 || xuanzhongArr.source == 3">
                                    <el-option v-for="(item, index) in optionsCK" :key="index" :label="item.plateNumber" :value="item.plateNumber"> </el-option>
                                </el-select>
                                <el-input v-else size="mini" v-model.trim="xiugaiFormData.carNumber" style="width: 193px" :disabled="disableCar"></el-input>
                            </el-form-item>

                            <el-form-item label="司机" prop="siji" :rules="[{ required: true, message: '请输入司机', trigger: 'change' }]">
                                <el-select v-model="xiugaiFormData.siji" size="mini" placeholder="请选择司机姓名" allow-create clearable filterable @change="ChangeSJ()" v-if="xuanzhongArr.source == 5 || xuanzhongArr.source == 3">
                                    <el-option v-for="(item, index) in optionsSJ" :key="index" :label="item.driverName" :value="item.driverName"></el-option>
                                </el-select>
                                <el-input v-else size="mini" v-model="xiugaiFormData.siji" autocomplete="off" style="width: 193px" maxlength="10" :disabled="disableCar"></el-input>
                                <el-button style="margin-left: 10px" type="text" @click="selectDriverChange()" :disabled="disableCar">选择</el-button>
                            </el-form-item>
                            <el-form-item label="司机手机" prop>
                                <el-input size="mini" v-model="xiugaiFormData.phone" autocomplete="off" style="width: 193px" maxlength="11"></el-input>
                            </el-form-item>
                            <el-form-item label="身份证号" prop>
                                <el-input size="mini" v-model="xiugaiFormData.numberId" autocomplete="off" style="width: 193px" maxlength="18"></el-input>
                            </el-form-item>
                            <el-form-item label="卸货地" prop>
                                <el-input size="mini" v-model="xiugaiFormData.xieHuoAddress" autocomplete="off" style="width: 193px" maxlength="50"></el-input>
                            </el-form-item>
                            <el-form-item label="提货单位" prop>
                                <el-input maxlength="30" size="mini" v-model="xiugaiFormData.tiHuo" autocomplete="off" style="width: 193px"></el-input>
                            </el-form-item>
                            <el-form-item
                                label="结算客户"
                                :rules="[
                                    {
                                        required: true,
                                        message: '请选择结算客户',
                                        trigger: 'change',
                                    },
                                ]"
                                prop="payCustomerName"
                                v-if="feeMode"
                            >
                                <el-select v-model="xiugaiFormData.payCustomerName" size="mini" placeholder="请选择" filterable clearable @change="jiesuanKehuxg" style="width: 193px">
                                    <el-option v-for="(item, index) in kehuMing" :key="index" :label="item.custerName" :value="item.custerName"> </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item
                                label="结算方式"
                                :rules="[
                                    {
                                        required: true,
                                        message: '请选择结算方式',
                                        trigger: 'change',
                                    },
                                ]"
                                prop="feeModeType"
                                v-if="feeMode"
                            >
                                <el-select size="mini" @change="jiesuanfangshixg" v-model="xiugaiFormData.feeModeType" placeholder="请选择">
                                    <el-option v-for="item in jsMethods" :label="item.show_name" :key="item.item_n0" :value="item.item_no"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item prop="externalOrderNo" label="外部单号">
                                <el-input v-model="xiugaiFormData.externalOrderNo" size="mini" placeholder="请输入" style="width: 200px"></el-input>
                            </el-form-item>
                            <el-form-item prop="shipName" label="船名">
                                <el-input v-model="xiugaiFormData.shipName" size="mini" placeholder="请输入" style="width: 200px"></el-input>
                            </el-form-item>
                            <el-form-item prop="cabin" label="船舱">
                                <el-input v-model="xiugaiFormData.cabin" size="mini" placeholder="请输入" style="width: 200px"></el-input>
                            </el-form-item>
                            <el-form-item label="备注" prop>
                                <el-input size="mini" v-model="xiugaiFormData.beizhu" autocomplete="off" style="width: 193px" maxlength="200"></el-input>
                            </el-form-item>
                            <!-- <el-form-item label="单号" prop>
                <el-input
                  maxlength="20"
                  size="mini"
                  v-model="xiugaiFormData.danHao"
                  autocomplete="off"
                  style="width:193px;"
                ></el-input>
              </el-form-item>-->
                        </el-form>
                    </div>
                    <!-- 表单按钮1111 -->
                    <div class="footerBox">
                        <el-button type="primary" @click="xiugaiqueding('xiugaiFormData')">确 定</el-button>
                        <el-button type="primary" @click="closeEdit">取 消</el-button>
                    </div>
                    <!-- 司机信息 -->
                    <el-drawer style="margin-bottom: 20px" title="选择司机" :visible.sync="table" direction="rtl" size="50%" :modal="false" @opened="addGoodOpen">
                        <div ref="searchBox" style="margin: 26px 0">
                            <el-form :inline="true" :model="derverFrom" ref="derverFrom" class="demo-form-inline" label-width="86px" size="mini">
                                <div class="searchBox inpBox">
                                    <div>
                                        <el-form-item label="司机姓名:" prop="name">
                                            <el-input placeholder type="text" v-model="derverFrom.name"></el-input>
                                        </el-form-item>
                                        <el-form-item label="联系电话:" prop="mobile">
                                            <el-input placeholder v-model="derverFrom.mobile"></el-input>
                                        </el-form-item>
                                    </div>
                                </div>
                                <div class="searchBtnBox" style="padding-left: 20px">
                                    <el-form-item>
                                        <el-button type="primary" @click="SJsearchFn">查询</el-button>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button type="primary" @click="resetForm('derverFrom')">重置</el-button>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button type="primary" @click="addDerver()">新增司机信息</el-button>
                                    </el-form-item>
                                </div>
                            </el-form>
                        </div>
                        <div :style="{ height: sjh }">
                            <el-table :data="gridData" ref="xzdz" height="100%" highlight-current-row @current-change="selderverChange">
                                <el-table-column type="index" width="55" label="序号"></el-table-column>
                                <el-table-column prop="name" width="120" :show-overflow-tooltip="true" label="司机姓名"></el-table-column>

                                <el-table-column prop="mobile" width="150" label="联系电话"></el-table-column>
                                <el-table-column prop="identityNo" width="200" :show-overflow-tooltip="true" label="身份证号"></el-table-column>
                                <el-table-column prop="bankCard" width="160" :show-overflow-tooltip="true" label="银行卡号"></el-table-column>
                                <el-table-column prop="bankName" width="160" :show-overflow-tooltip="true" label="开户行"></el-table-column>
                                <el-table-column prop="bankCardUser" label="收款人"></el-table-column>
                            </el-table>
                        </div>
                        <!-- 分页------ -->
                        <div>
                            <el-pagination background @size-change="derverChange" @current-change="derverCurrentChange" :current-page="derverFrom.pageNo" :page-sizes="[25, 50, 100, 200, 1000]" :page-size="derverFrom.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="siJiTotal"></el-pagination>
                        </div>
                    </el-drawer>
                </el-drawer>
            </div>
            <!-- 点击取消关联 -->
            <div class="xiugai">
                <el-dialog title="取消关联" :visible.sync="disassociateMark" :close-on-click-modal="false" width="40%" center>
                    <!--form表单-->
                    <el-form :model="disassociateData" ref="disassociateDataForm" label-width="100px" class="demo-dynamic" style="width: 350px; margin: 30px auto">
                        <el-form-item
                            label="撤销原因"
                            prop="returnData"
                            :rules="[
                                {
                                    required: true,
                                    message: '请输入撤销原因',
                                    trigger: 'change',
                                },
                            ]"
                        >
                            <el-input type="textarea" :rows="3" size="mini" v-model="disassociateData.returnData" autocomplete="off" style="width: 250px" maxlength="120"></el-input>
                        </el-form-item>
                    </el-form>
                    <div style="width: 200px; margin: 15px auto">
                        <el-button type="primary" @click="disassociateOk('disassociateDataForm')">确 定</el-button>
                        <el-button type="primary" @click="disassociateNo('disassociateDataForm')">取 消</el-button>
                    </div>
                    <span slot="footer" class="dialog-footer"></span>
                </el-dialog>
            </div>
        </div>
        <el-dialog title="提示信息" :visible.sync="showTishi" :close-on-click-modal="false" width="50%" center>
            <div class="ModelText">{{ this.addFormData.kehuName }}，{{ this.typeName }}，剩余货物小于5件</div>
            <div class="ModelText">请联系仓库结算员，仓储费结算情况，确认是否放货</div>
            <div slot="footer">
                <el-button type="primary" @click="showTishi = false">知 晓</el-button>
            </div>
        </el-dialog>
        <el-dialog title="提示信息" :visible.sync="showTishiJS" :close-on-click-modal="false" width="55%" center>
            <div class="ModelText" style="color: #000">{{ this.addFormData.payCustomerName || this.xiugaiFormData.payCustomerName }}，系统默认结算方式, {{ feeModeTypeName }}</div>
            <div class="ModelText">
                是否按,
                {{ this.addFormData.feeModeTypeName || this.xiugaiFormData.feeModeTypeName }}
                ,付款
            </div>
            <div slot="footer">
                <el-button type="primary" @click="clickFou">否</el-button>
                <el-button type="primary" @click="showTishiJS = false">是</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import {
        // cDlist, // 司机信息
        chukuList, // 主数据查询
        getWhOutstockDetailListSummary, // 汇总
        detailsList, // 子数据-详情查询
        addWhOutstock, // 添加出库单
        fingById, // 修改查询
        updateOk, // 修改
        deleteWhOutstock, // 删除
        deleteTOutstockSummary, // 删除-子
        getWhOutstockPlan, // 关联货物查询
        okAddWhOutstock, // 确认关联货物
        cancelWhOutstock, // 取消关联
        zidian, // 字典-货物类型
        getLwgStockVehicleNumber, // 字典-车船号
        getLwgStockCargoCount, // 字典-车船号-库存件数
        addLwgWhOutstockDetail, // 字典-车船号-库存件数-保存
        selectScrewThreadSteelAttribute, // 螺纹钢信息
        getWhManageOutstockDetailList, // 备注
        getWhOutstockPlanCargoList,
        addWhOutstockNM, // 耐磨板剪切板确定
        getStockNumber,
    } from "@/api/wms-access";
    import { detailByPlateNumberDriverVehicle } from "@/api/wms-stock.js";
    import { cDlist } from "@/api/vgs-driver";
    import DonMessage from "@/utils/message";
    import { getBtn } from "@/utils/getButton";
    import {
        getRelationCustomer, // 字典-客户
        zidianKuwei, // 字典库位
        zidianQuwei, // 字典区位
        zidianHuowei, // 字典货位
        getDictionaryJson,
        getPickUpList,
        sendsOutPlan,
        callSettlementMethod,
        getFeeMode,
    } from "@/api/wms-stock";
    export default {
        name: "ChuKuPlan",
        data() {
            return {
                xiangqingHuiZongArr: null,
                lengthLook: true,

                // 添加货物
                copyEnd: [],
                copyTableList: [],
                addOkNumber: 0, // 当前行螺纹钢或盘螺显示
                addSXList: [], // 当前行螺纹钢或盘螺显示
                listData: {}, // 当前行螺纹钢或盘螺显示
                luoWenGangLook: false, // 当前行螺纹钢或盘螺显示
                luoWenGangXiangQingLook: true, // 当前行螺纹钢或盘螺显示
                addProductType: "",
                // 添加货物
                addProductTypeMark: false,
                detailsDataCopyAdd: [], // 添加货物数据
                // 标签页
                activeName: "first",
                // 车船号选择框
                cheChuanHaoValue: "",
                cheChuanHaoOptions: [],
                // 品牌选择框
                chanDiValue: "",
                chanDiOptions: [],
                disChanDi: false,
                // 品名选择框
                pinMingValue: "",
                pingMingOptions: [],
                disPingMing: true,
                // 材质选择框
                caiZhiValue: "",
                caiZhiOptions: [],
                disCaiZhi: true,
                // 规格选择框
                guiGeValue: "",
                guiGeOptions: [],
                disGuige: true,
                // 长度选择框
                changDuValue: "",
                changDuOptions: [],
                disChangDu: true,
                // 库存件数
                kuCunJianShuValue: "",
                disKuCunJianShu: true,
                // 单号
                danHaoValue: "",
                // 件数
                jianShuValue: "",

                disJianShu: true,
                disDanHao: true,
                // -----------------------------------------
                panLuo: true,
                customerNameGuanLian: "",
                numName: 0,
                loading: false,
                loading1: false,
                allweight: "0.000",
                allNumber: "0",
                allPoundWeight: "0.000",
                allWeightHuizong: "0.000",
                allNumberHuizong: "0",
                allPoundWeightHuizong: "0.000",
                h: "500px",
                detailsH: "200px",
                guanLianH: "150px",
                // 锁状态 0未锁 1已锁
                options: [
                    {
                        id: "0",
                        name: "未锁定",
                    },
                    {
                        id: "1",
                        name: "已锁定",
                    },
                    {
                        id: "2",
                        name: "加工锁定",
                    },
                ],
                loactId: "",
                loactName: "",
                lockState: "", // 关联货物查询
                outstockId: "",
                tableHeight: window.innerHeight * 0.36,
                xqtableHeight: window.innerHeight * 0.3,
                xiangqingInALlId: [],
                xiangqingAllID: "",
                defaultSelected: "",
                typeFlag: true,
                // ---------------------------------主表
                chukuTableData: [], // 主数据
                xuanzhongArr: [], // 主表选中当前行
                typeData: [], // 字典-冷热卷
                // ----------------------------------添加
                addClientSbm: "", // 客户识别码
                addClientId: "", // 客户id
                addClientName: "", // 客户名称
                typeId: "", // 货物类型
                typeName: "", // 货物类型
                //= ============----------------------修改
                dialogVisiblexiugai: false,
                isDisable: false,
                addDisable: false,
                xiugaiTableData: {},
                handIdId: "",
                handIdName: "",
                handIdSbm: "",
                // -------------------------------关联货物
                relevanceTableData: [], // 关联货物
                arrInDetailId: [], // 选中子数据id
                ZSList: [], // 判断张数
                stringID: "", // id集合
                // ---------------------------------子表
                zibiaoTableData: [], // 主数据
                HuizongTableData: [], // 主数据
                xiangqingListArr: null, // 获取选中行
                formInline: {
                    carNum: "", // 车牌号
                    userName: "", // 客户名称
                    gangJuanHao: "", // 捆包号
                    guiGe: "", // 规格
                    weightSearch: "", // 重量
                    wayNo: "",
                },
                disassociateMark: false,
                disassociateData: {
                    returnData: "", // 退回原因
                },

                addMark: false, // 添加按钮弹框
                guanLianMark: false, // 关联未入库货物清单
                // 添加弹框form表单
                addFormData: {
                    kehuName: "", // 客户名称
                    cunHuoType: "", // 存货类型
                    goodsType: "", // 货物类型
                    siJiName: "", // 司机姓名
                    phone: "", // 司机电话
                    numberId: "", // 司机身份证
                    carNumber: "", // 出库车牌号
                    beizhu: "", // 备注
                    xieHuoAddress: "", // 卸货地
                    danHao: "", // 卸货地
                    tiHuo: "", // 卸货地
                    feeModeType: "1",
                    externalOrderNo: "",
                    shipName: "",
                    cabin: "",
                    payCustomerName: "",
                },
                // 司机信息
                siJiTotal: 0,
                derverFrom: {
                    pageNo: 1,
                    pageSize: 50,
                    // name: '', // 司机姓名
                    // mobile: '' // 司机电话
                },
                table: false,
                // 司机信息表格
                gridData: [],
                sjh: "500",
                sijiChange: false,
                // 修改弹框form表单
                xiugaiFormData: {
                    kehuName: "", // 客户名称
                    cunHuoType: "", // 存货类型
                    goodsType: "", // 货物类型
                    planNum: "", // 计划号
                    siji: "", // 司机
                    phone: "", // 司机电话
                    numberId: "", // 司机身份证
                    carNumber: "", // 入库车牌号
                    beizhu: "", // 备注
                    danHao: "", // 备注
                    tiHuo: "", // 备注
                    xieHuoAddress: "", // 卸货地
                    feeModeType: "1",
                    externalOrderNo: "",
                    shipName: "",
                    cabin: "",
                    payCustomerName: "",
                },
                guanLianGangJuanNum: "", // 关联弹框捆包号
                // guanLianCustomerName: '', // 关联弹框捆包号
                guanLianHeYueNum: "", // 关联弹框合约号
                luhao: "", // 关联弹框合约号
                guanLianCaiZhi: "", // 关联弹框材质
                guanLianGuiGe: "", // 关联弹框规格
                guanLianWeight: "", // 关联弹框重量
                customerTaxId: "", // 关联货物数据默认识别码
                kehuMing: [],
                // 主数据分页
                pagesize: 20,
                current: 1,
                total: 0,
                // 详情分页
                pagesizeXQ: 10,
                currentXQ: 1,
                totalXQ: 0,
                // 详情分页--汇总
                totalHuizong: 0,
                pagesizeHuiZong: 10,
                currentHuiZong: 1,
                // 关联货物分页
                pagesizeGL: 10,
                currentGL: 1,
                totalGL: 0,
                // 按钮
                btnFlg: [],
                btnIdArr: ["003P005B001", "003P005B002", "003P005B003", "003P005B004", "003P005B005", "003P005B006", "003P005B007", "003P005B008", "003P005B009", "003P005B00A", "003M0c0100", "003M0c0200"],
                // 手动提单关联货物弹窗
                SDTDMark: false,
                TDnumber: "", // 提单号
                allWeightGL: 0, // 总重量
                selectWeightGL: 0, // 选择重量
                selectNumberGL: 0, // 选择件数
                SDTDTableData: [], // 表格数据
                cangKuName: "", // 仓库名
                quWeiName: "", // 区位名
                huoWeiName: "", // 货位名
                specification: "", // 规格
                addKuwei: [],
                addQ: [],
                addH: [],
                GLloading: false,
                productTypeDis: false,
                jsMethods: [], // 结算方式
                feeMode: false, // 是否显示结算方式选项
                disableCar: false,
                sheetsShow: false,
                optionsCK: [], // 出库车牌号
                optionsSJ: [], // 修改司机
                optionRC: [],
                COUNT: "",
                showTishi: false,
                shiyong: "",
                feeModeType: "",
                feeModeTypeName: "",
                showTishiJS: false,
            };
        },
        mounted() {
            let h = this.$refs.searchBox.offsetHeight;
            let h2 = this.$refs.searchBox2.offsetHeight;
            let pH = document.documentElement.clientHeight;
            this.h = 0.6 * pH - h - 90 + "px";
            this.detailsH = 0.4 * pH - h2 - 105 + "px";
            this.guanLianH = 0.6 * pH + "px";
        },
        created() {
            this.btnFlg = getBtn(this.btnIdArr, "/chuKuPlan");

            this.chukuListS(); // 主数据
            this.getRelationCustomerS(); // 主数据
            this.zidianS(); // 字典-冷热卷
            this.zidianKuweiS();
            this.getFeeModeList();
        },
        methods: {
            onKeyVehicleNumber() {
                this.addDisable = false;
                const patt = /[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领]{1}[A-Z]{1}[A-Z0-9]{5,6}/g;
                if (patt.test(this.addFormData.carNumber) === true) {
                    const loading3 = this.$loading({
                        lock: true,
                        text: "正在获取人车信息...",
                    });
                    detailByPlateNumberDriverVehicle({
                        outstockPlateNumber: this.addFormData.carNumber,
                    })
                        .then((res) => {
                            loading3.close();
                            if (res instanceof Object && res.marker != undefined && res.marker == "黑名单") {
                                this.addDisable = true;
                                DonMessage.error("该车牌号已被加入黑名单");
                            } else {
                                if (res instanceof Object) {
                                    console.log(res);
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
            clickFou() {
                if (this.addMark) {
                    this.addFormData.feeModeType = this.feeModeType;
                    this.addFormData.feeModeTypeName = this.feeModeTypeName;
                } else {
                    this.xiugaiFormData.feeModeType = this.feeModeType;
                    this.xiugaiFormData.feeModeTypeName = this.feeModeTypeName;
                }

                this.showTishiJS = false;
            },
            jiesuanfangshi(val) {
                console.log(val);
                this.addFormData.feeModeTypeName = this.jsMethods.filter((item) => item.item_no == val)[0].show_name;
                if (this.shiyong == 1 && this.feeModeType != val && this.feeModeType != "") {
                    this.showTishiJS = true;
                }
            },
            jiesuanKehu(val) {
                console.log(32);
                if (this.shiyong == 1) {
                    const customerTaxId = this.kehuMing.filter((item) => item.custerName == this.addFormData.payCustomerName)[0].taxpayerNumber;
                    getFeeMode({ customerTaxId })
                        .then((res) => {
                            if (res.length > 0) {
                                this.feeModeType = res[0].feeModeType;
                                this.feeModeTypeName = res[0].feeModeTypeName;
                                this.addFormData.feeModeType = res[0].feeModeType;
                                this.addFormData.feeModeTypeName = res[0].feeModeTypeName;
                            } else {
                                this.feeModeType = "";
                                this.feeModeTypeName = "";
                            }
                        })
                        .catch((err) => {
                            this.$message({ message: err.message, type: "error" });
                        });
                }
            },
            jiesuanfangshixg(val) {
                console.log(val);
                this.xiugaiFormData.feeModeTypeName = this.jsMethods.filter((item) => item.item_no == val)[0].show_name;
                if (this.shiyong == 1 && this.feeModeType != val && this.feeModeType != "") {
                    this.showTishiJS = true;
                }
            },
            jiesuanKehuxg(val) {
                console.log(32);
                if (this.shiyong == 1) {
                    const customerTaxId = this.kehuMing.filter((item) => item.custerName == this.xiugaiFormData.payCustomerName)[0].taxpayerNumber;
                    getFeeMode({ customerTaxId })
                        .then((res) => {
                            if (res.length > 0) {
                                this.feeModeType = res[0].feeModeType;
                                this.feeModeTypeName = res[0].feeModeTypeName;
                                this.xiugaiFormData.feeModeType = res[0].feeModeType;
                                this.xiugaiFormData.feeModeTypeName = res[0].feeModeTypeName;
                            } else {
                                this.feeModeType = "";
                                this.feeModeTypeName = "";
                            }
                        })
                        .catch((err) => {
                            this.$message({ message: err.message, type: "error" });
                        });
                }
            },
            zhuangchedanDY() {
                this.$router.push({
                    path: "/zhuangchedanXH",
                    query: { id: this.xuanzhongArr.outstockId },
                });
            },
            sendMessage() {
                if (this.xuanzhongArr.telephone == "") {
                    this.$message.error("请填写司机手机号");
                    return;
                }
                const rule = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
                if (!rule.test(this.xuanzhongArr.telephone)) {
                    this.$message.error("请填写正确的司机手机号");
                    return;
                }
                sendsOutPlan({ outstockId: this.xuanzhongArr.outstockId })
                    .then((res) => {
                        this.$message.success("发送成功");
                        this.chaxun();
                    })
                    .catch((err) => {
                        this.$message({ message: err.message, type: "error" });
                    });
            },

            // 出库张数
            inputNumber(row) {
                console.log(row);
                if (row.outsheets == undefined) {
                    this.$set(row, "outweight", "");
                } else {
                    this.$set(row, "outweight", Number((row.outsheets / row.sheets) * row.weight).toFixed(3));
                    this.ZSList.forEach((item) => {
                        if (item.cargoId == row.cargoId) {
                            this.$set(item, "outsheets", row.outsheets);
                            this.$set(item, "outweight", Number((row.outsheets / row.sheets) * row.weight).toFixed(3));
                        }
                    });
                    this.selectWeightGL = this.ZSList.reduce((pre, cur) => Number(pre / 1 + cur.outweight / 1).toFixed(3), 0);
                }
                console.log(this.ZSList);
            },
            getFeeModeList() {
                zidian({ groupNo: "fee_mode_type" })
                    .then((res) => {
                        console.log(res);
                        this.jsMethods = res;
                    })
                    .catch((err) => {
                        this.$message({ message: err.message, type: "error" });
                    });
            },
            getWhOutstockPlanCargoLists() {
                this.guanLianGangJuanNum = this.guanLianGangJuanNum.replace(/\n/g, ",");
                this.guanLianGangJuanNum = this.guanLianGangJuanNum
                    .split(",")
                    .filter((item) => item != "")
                    .join(",");
                var reg = /^(?=.*\d.*\b)/;
                if (!reg.test(this.guanLianGangJuanNum)) {
                    this.guanLianGangJuanNum = "";
                }
                getWhOutstockPlanCargoList({
                    cargoBarcodes: this.guanLianGangJuanNum,
                    warehouseId: this.kuweiId, // 仓库名（下拉框key）
                    locationId: this.QId, //  区位名（下拉框key）
                    binId: this.HId, // 货位名（下拉框key）
                    specification: this.specification,
                    billId: this.xuanzhongArr.billId,
                    productType: this.xuanzhongArr.productType,
                })
                    .then((res) => {
                        console.log(res);
                        let List = res.filter((item) => item.productType == this.xuanzhongArr.productType);
                        this.SDTDTableData = List;
                        this.allWeightGL = List.reduce((pre, cur) => Number(pre / 1 + cur.weight / 1).toFixed(3), 0);
                    })
                    .catch((error) => {
                        DonMessage.error(error.message);
                    });
            },
            // 手动提单关联货物弹窗查询
            SDTDCX() {
                this.getWhOutstockPlanCargoLists();
            },
            SDTDCZ() {
                this.guanLianGangJuanNum = "";
                this.cangKuName = "";
                this.quWeiName = "";
                this.huoWeiName = "";
                this.specification = "";
                this.kuweiId = "";
                this.QId = "";
                this.HId = "";
                this.getWhOutstockPlanCargoLists();
            },
            SDTDOk() {
                if (this.stringID == "") {
                    this.$message.error("请勾选需要关联的货物！");
                } else if (this.ZSList.some((item) => item.outsheets) && this.ZSList.some((item) => item.outsheets / 1 > item.sheets / 1)) {
                    DonMessage.error("出库张数不可大于张数");
                } else if (this.ZSList.some((item) => item.outsheets / 1 == 0)) {
                    DonMessage.error("出库张数不能等于0或空");
                } else {
                    if (this.sheetsShow) {
                        this.addWhOutstockNM();
                    } else {
                        this.okAddWhOutstockS();
                    }
                }
            },
            SDTDQuXiao() {
                this.SDTDMark = false;
            },
            // 字典--库位
            zidianKuweiS() {
                zidianKuwei({})
                    .then((res) => {
                        this.addKuwei = res;
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            },
            // 获取库位下拉框其他值
            changeXiugaiKW: function (valueS) {
                let choosenKuwei = this.addKuwei.filter((item) => item.warehouseId === valueS)[0];
                this.kuweiId = choosenKuwei.warehouseId;
                this.kuweiName = choosenKuwei.warehouseName;
                this.quWeiName = "";
                this.huoWeiName = "";
                this.QId = "";
                this.HId = "";
                zidianQuwei({
                    warehouseId: this.kuweiId,
                })
                    .then((res) => {
                        this.addQ = res;
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            },
            // 获取区位下拉框其他值
            changeXiugaiQW: function (valueS) {
                let choosenQ = this.addQ.filter((item) => item.locationId === valueS)[0];
                this.QId = choosenQ.locationId;
                this.QName = choosenQ.locationName;
                this.huoWeiName = "";
                this.HId = "";
                zidianHuowei({
                    locationid: this.QId,
                })
                    .then((res) => {
                        this.addH = res;
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            },
            // 获取货位下拉框其他值
            changeXiugaiHW: function (valueS) {
                let choosenH = this.addH.filter((item) => item.binId === valueS)[0];
                this.HId = choosenH.binId;
                this.HName = choosenH.binName;
            },
            closeDialog() {
                this.addFormData = {
                    kehuName: "", // 客户名称
                    cunHuoType: "", // 存货类型
                    goodsType: "", // 货物类型
                    siJiName: "", // 司机姓名
                    phone: "", // 司机电话
                    numberId: "", // 司机身份证
                    carNumber: "", // 出库车牌号
                    beizhu: "", // 备注
                    xieHuoAddress: "", // 卸货地
                    danHao: "", // 卸货地
                    tiHuo: "", // 卸货地
                    feeModeType: "1",
                    externalOrderNo: "",
                    shipName: "",
                    cabin: "",
                    payCustomerName: "",
                };
                if (this.$refs.addFormData !== undefined) {
                    this.$refs.addFormData.resetFields();
                }
            },
            closeEdit() {
                this.xiugaiFormData = {
                    kehuName: "", // 客户名称
                    cunHuoType: "", // 存货类型
                    goodsType: "", // 货物类型
                    planNum: "", // 计划号
                    siji: "", // 司机
                    phone: "", // 司机电话
                    numberId: "", // 司机身份证
                    carNumber: "", // 入库车牌号
                    beizhu: "", // 备注
                    danHao: "", // 备注
                    tiHuo: "", // 备注
                    xieHuoAddress: "", // 卸货地
                    feeModeType: "1",
                    externalOrderNo: "",
                    shipName: "",
                    cabin: "",
                    payCustomerName: "",
                };
                this.dialogVisiblexiugai = false;
                if (this.$refs.xiugaiFormData !== undefined) {
                    this.$refs.xiugaiFormData.resetFields();
                }
            },
            // 点击汇总删除
            delHuiZong() {
                console.log(this.xiangqingHuiZongArr);
                if (this.xiangqingHuiZongArr == null) {
                    DonMessage.error("请选择要操作的数据");
                } else if (this.xuanzhongArr.source == 3) {
                    DonMessage.error("出库单来源为货主端,不可进行操作!");
                } else {
                    this.$confirm("此操作将删除该条数据, 是否继续?", "提示", {
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        type: "warning",
                    })
                        .then(() => {
                            deleteTOutstockSummary({
                                outstockSummaryId: this.xiangqingHuiZongArr.outstockSummaryId, // 出库单汇总表id
                                outstockId: this.xuanzhongArr.outstockId, // 出库单id
                            })
                                .then((res) => {
                                    DonMessage.success("操作成功");
                                    this.chukuListS();
                                    this.getWhOutstockDetailListSummaryS(); // 汇总数据
                                })
                                .catch((err) => {
                                    DonMessage.error(err.message);
                                });
                        })
                        .catch(() => {});
                }
            },
            // 汇总选中
            xuanzhongHuiZong(row) {
                this.xiangqingHuiZongArr = null;
                this.xiangqingHuiZongArr = row;
            },
            // 标签页切换
            handleClick(tab, event) {},
            // 车船号选择
            changeCheChuanHao: function (row, valueS) {
                // console.log(valueS, this.cheChuanHaoOptions, 'this.cheChuanHaoOptions')
                // let choosenItem = this.typeDataChange.filter(
                //   item => item.value === valueS
                // )[0]
                // // this.detailsDataCopyAdd = []
                // this.typeCode = choosenItem.code
                // this.typeValueS = choosenItem.value
                this.cheChuanHaoValue = row.cheChuanHaoValue;
                if (row.changDuValue !== "" || row.jianShuValue !== "") {
                    // 库存件数
                    getLwgStockCargoCount({
                        customerTaxId: this.xuanzhongArr.customerTaxId, //  客户纳税人识别号
                        productTypeName: this.xuanzhongArr.productTypeName, // 货物类型描述
                        vehicleNumber: row.cheChuanHaoValue, // 车船
                        productName: row.pinMingValue, // 品名
                        model: row.caiZhiValue, // 材质
                        specification: row.guiGeValue, // 规格
                        length: row.changDuValue, // 长度
                        steelMillName: row.chanDiValue, // 品牌
                        number: row.jianShuValue, // 件数
                    })
                        .then((res) => {
                            this.kuCunJianShuValue = "";
                            this.jianShuValue = "";
                            this.danHaoValue = "";
                            row.jianShuValue = "";
                            row.kuCunJianShuValue = res.number;
                            this.disJianShu = false;
                            this.kuCunJianShuValue = row.kuCunJianShuValue;
                        })
                        .catch((err) => {
                            DonMessage.error(err.message);
                        });
                }
            },
            // 品牌选择-从品牌开始联动
            changeChanDi: function (row, valueS) {
                // row.chanDiValue = ''
                // row.cheChuanHaoValue = ''
                row.pinMingValue = "";

                row.guiGeValue = "";
                row.caiZhiValue = "";
                row.changDuValue = "";
                row.jianShuValue = "";
                row.danHaoValue = "";

                row.kuCunJianShuValue = "";
                // 品名选择框
                // this.pinMingValue = ''
                this.disPingMing = false;
                // 材质选择框
                // this.caiZhiValue = ''
                this.disCaiZhi = true;
                // 规格选择框
                // this.guiGeValue = ''
                this.disGuige = true;

                // 长度选择框
                // this.changDuValue = ''
                this.disChangDu = true;
                // 库存件数
                // this.kuCunJianShuValue = ''
                this.disKuCunJianShu = true;
                // 件数
                // this.jianShuValue = ''
                this.disJianShu = true;
                this.disDanHao = true;
                selectScrewThreadSteelAttribute({
                    productType: this.addProductType, // 类型
                    placeOfOrigin: row.chanDiValue, // 品牌
                    productName: row.pinMingValue, // 品名
                    specification: row.guiGeValue, // 规格
                    model: row.caiZhiValue, // 材质
                    length: row.changDuValue, // 长度
                })
                    .then((res) => {
                        // this.chanDiDisabled = false
                        this.pingMingOptions = []; // 品名
                        for (var i = 0; i < res.length; i++) {
                            var pingMinglist = {}; // 品名对象
                            pingMinglist.value = res[i].productName;
                            this.pingMingOptions.push(pingMinglist);
                        }
                        // 对象去重
                        this.pingMingOptions = this.pingMingOptions.reduce((preVal, curVal) => {
                            pingMinglist[curVal.value] ? "" : (pingMinglist[curVal.value] = preVal.push(curVal));
                            return preVal;
                        }, []);
                    })
                    .catch((err) => {
                        DonMessage.error(err.message);
                    });
            },
            // 品名选择
            changePingMing: function (row, valueS) {
                row.guiGeValue = "";
                row.caiZhiValue = "";
                row.changDuValue = "";
                row.jianShuValue = "";
                row.danHaoValue = "";

                row.kuCunJianShuValue = "";
                // 材质选择框
                this.disCaiZhi = false;
                // 规格选择框
                this.disGuige = true;
                // 长度选择框
                this.disChangDu = true;
                // 库存件数
                this.disKuCunJianShu = true;
                // 件数
                this.disJianShu = true;
                this.disDanHao = true;

                selectScrewThreadSteelAttribute({
                    productType: this.addProductType, // 类型
                    placeOfOrigin: row.chanDiValue, // 品牌
                    productName: row.pinMingValue, // 品名
                    specification: row.guiGeValue, // 规格
                    model: row.caiZhiValue, // 材质
                    length: row.changDuValue, // 长度
                })
                    .then((res) => {
                        this.caiZhiOptions = []; // 品名
                        for (var i = 0; i < res.length; i++) {
                            var caiZhilist = {}; // 品名对象
                            caiZhilist.value = res[i].model;
                            this.caiZhiOptions.push(caiZhilist);
                        }
                        // 对象去重
                        this.caiZhiOptions = this.caiZhiOptions.reduce((preVal, curVal) => {
                            caiZhilist[curVal.value] ? "" : (caiZhilist[curVal.value] = preVal.push(curVal));
                            return preVal;
                        }, []);
                    })
                    .catch((err) => {
                        DonMessage.error(err.message);
                    });
            },
            // 材质选择
            changeCaiZhi: function (row, valueS) {
                row.guiGeValue = "";
                row.changDuValue = "";
                row.jianShuValue = "";
                row.danHaoValue = "";
                row.kuCunJianShuValue = "";

                // 规格选择框
                this.disGuige = false;
                // 长度选择框
                this.disChangDu = true;
                // 库存件数
                this.disKuCunJianShu = true;
                // 件数
                this.disJianShu = true;
                this.disDanHao = true;

                selectScrewThreadSteelAttribute({
                    productType: this.addProductType, // 类型
                    placeOfOrigin: row.chanDiValue, // 品牌
                    productName: row.pinMingValue, // 品名
                    specification: row.guiGeValue, // 规格
                    model: row.caiZhiValue, // 材质
                    length: row.changDuValue, // 长度
                })
                    .then((res) => {
                        this.guiGeOptions = []; // 品名
                        for (var i = 0; i < res.length; i++) {
                            var guiGelist = {}; // 品名对象
                            guiGelist.value = res[i].specification;
                            this.guiGeOptions.push(guiGelist);
                        }
                        // 对象去重
                        this.guiGeOptions = this.guiGeOptions.reduce((preVal, curVal) => {
                            guiGelist[curVal.value] ? "" : (guiGelist[curVal.value] = preVal.push(curVal));
                            return preVal;
                        }, []);
                    })
                    .catch((err) => {
                        DonMessage.error(err.message);
                    });
            },
            // 规格选择
            changeGuiGe: function (row, valueS) {
                row.changDuValue = "";
                row.danHaoValue = "";
                row.jianShuValue = "";
                row.kuCunJianShuValue = "";
                // 长度选择框
                this.disChangDu = false;
                // 库存件数
                this.disKuCunJianShu = true;
                // 件数
                this.disJianShu = true;
                this.disDanHao = true;

                selectScrewThreadSteelAttribute({
                    productType: this.addProductType, // 类型
                    placeOfOrigin: row.chanDiValue, // 品牌
                    productName: row.pinMingValue, // 品名
                    specification: row.guiGeValue, // 规格
                    model: row.caiZhiValue, // 材质
                    length: row.changDuValue, // 长度
                })
                    .then((res) => {
                        this.changDuOptions = []; // 品名
                        for (var i = 0; i < res.length; i++) {
                            var changDulist = {}; // 品名对象
                            changDulist.value = res[i].length;
                            this.changDuOptions.push(changDulist);
                        }

                        // 对象去重
                        this.changDuOptions = this.changDuOptions.reduce((preVal, curVal) => {
                            changDulist[curVal.value] ? "" : (changDulist[curVal.value] = preVal.push(curVal));
                            return preVal;
                        }, []);
                    })
                    .catch((err) => {
                        DonMessage.error(err.message);
                    });
            },
            // 长度选择
            changeChangDu: function (row, valueS) {
                this.pinMingValue = row.pinMingValue; // 品名
                this.caiZhiValue = row.caiZhiValue; // 材质
                this.guiGeValue = row.guiGeValue; // 规格
                this.changDuValue = row.changDuValue; // 长度
                this.chanDiValue = row.chanDiValue; // 品牌

                row.jianShuValue = "";
                row.kuCunJianShuValue = "";
                row.danHaoValue = "";
                // 库存件数
                this.disKuCunJianShu = true;
                // 件数
                this.disJianShu = true;
                this.disDanHao = true;
                this.disDanHao = false;

                // 库存件数
                getLwgStockCargoCount({
                    customerTaxId: this.xuanzhongArr.customerTaxId, // 客户纳税人识别号
                    productTypeName: this.xuanzhongArr.productTypeName, // 货物类型描述
                    vehicleNumber: row.cheChuanHaoValue, // 车船
                    productName: row.pinMingValue, // 品名
                    model: row.caiZhiValue, // 材质
                    specification: row.guiGeValue, // 规格
                    length: row.changDuValue, // 长度
                    steelMillName: row.chanDiValue, // 品牌
                    number: row.jianShuValue, // 件数
                })
                    .then((res) => {
                        this.kuCunJianShuValue = "";
                        this.danHaoValue = "";
                        row.kuCunJianShuValue = res.number;
                        this.disJianShu = false;
                        this.kuCunJianShuValue = row.kuCunJianShuValue;
                    })
                    .catch((err) => {
                        DonMessage.error(err.message);
                    });
            },
            // 输入件数-判断与库存件数差距
            changeJianShu: function (row, valueS) {
                if (row.jianShuValue > row.kuCunJianShuValue) {
                    DonMessage.error("总件数不能大于此规格型号的库存件数");
                }
                this.jianShuValue = "";
                this.kuCunJianShuValue = "";
                this.jianShuValue = row.jianShuValue; // 件数D
                this.kuCunJianShuValue = row.kuCunJianShuValue; // 件数D
            },
            // 输入单号
            changeDanHao(row, valueS) {},
            // 点击添加货物
            addProductTypeClick(row) {
                if (this.xuanzhongArr.source == 3) {
                    DonMessage.error("出库单来源为货主端,不可进行操作!");
                } else {
                    this.copyTableList = [];
                    if (row.productType == "5") {
                        this.panLuo = false;
                    }
                    this.detailsDataCopyAdd = [];
                    this.cheChuanHaoValue = "";
                    // 品牌选择框
                    this.chanDiValue = "";
                    this.disChanDi = false;

                    // 品名选择框
                    this.pinMingValue = "";
                    this.disPingMing = true;

                    // 材质选择框
                    this.caiZhiValue = "";
                    this.disCaiZhi = true;

                    // 规格选择框
                    this.guiGeValue = "";
                    this.disGuige = true;

                    // 长度选择框
                    this.changDuValue = "";
                    this.disChangDu = true;
                    this.addProductTypeMark = true;
                    this.panLuo = true;
                    this.addProductType = row.productType;
                    var d = {
                        // this.cheChuanHaoValue = ''
                        cheChuanHaoValue: "", // 车船号
                        chanDiValue: "", // 品牌
                        pinMingValue: "", // 品名
                        caiZhiValue: "", // 材质
                        guiGeValue: "", // 规格
                        changDuValue: "", // 长度
                        danHaoValue: "", // 单号
                        kuCunJianShuValue: "", // 库存件数
                        jianShuValue: "", // 件数
                    };
                    this.detailsDataCopyAdd.push(d);
                    selectScrewThreadSteelAttribute({
                        productType: this.addProductType, // 类型
                        productName: "", // 品名
                        placeOfOrigin: "", // 品牌
                        specification: "", // 规格
                        model: "", // 材质
                        length: "", // 长度
                        root: "", // 支数（支/捆）
                    })
                        .then((res) => {
                            this.chanDiDisabled = false;
                            this.chanDiOptions = []; // 品牌
                            for (var i = 0; i < res.length; i++) {
                                var chanDilist = {}; // 品牌对象
                                chanDilist.value = res[i].placeOfOrigin;
                                this.chanDiOptions.push(chanDilist);
                            }
                            // 对象去重
                            this.chanDiOptions = this.chanDiOptions.reduce((preVal, curVal) => {
                                chanDilist[curVal.value] ? "" : (chanDilist[curVal.value] = preVal.push(curVal));
                                return preVal;
                            }, []);
                        })
                        .catch((err) => {
                            DonMessage.error(err.message);
                        });
                    // 车船号
                    getLwgStockVehicleNumber({
                        customerTaxId: row.customerTaxId, // 客户纳税人识别号
                        productTypeName: row.productTypeName, // 货物类型描述
                        vehicleNumber: "", // 车船号（模糊查询）
                    })
                        .then((res) => {
                            this.cheChuanHaoOptions = []; // 车船号
                            // this.cheChuanHaoOptions = res// 车船号
                            for (var i = 0; i < res.length; i++) {
                                var cheChuanHaolist = {}; // 车船号对象
                                cheChuanHaolist.value = res[i].vehicleNumber;
                                this.cheChuanHaoOptions.push(cheChuanHaolist);
                            }
                            // 对象去重
                            this.cheChuanHaoOptions = this.cheChuanHaoOptions.reduce((preVal, curVal) => {
                                cheChuanHaolist[curVal.value] ? "" : (cheChuanHaolist[curVal.value] = preVal.push(curVal));
                                return preVal;
                            }, []);
                        })
                        .catch((err) => {
                            DonMessage.error(err.message);
                        });
                }
            },
            // 点击添加属性新增按钮
            addHuoWuBtn() {
                if (this.detailsDataCopyAdd.length >= 1) {
                    this.copyEnd = this.detailsDataCopyAdd.slice(-1);
                    if (this.copyEnd[0].cheChuanHaoValue == "") {
                        DonMessage.error("请先完善当前数据！");
                    } else if (this.copyEnd[0].chanDiValue == "") {
                        DonMessage.error("请先完善当前数据！");
                    } else if (this.copyEnd[0].pinMingValue == "") {
                        DonMessage.error("请先完善当前数据！");
                    } else if (this.copyEnd[0].caiZhiValue == "") {
                        DonMessage.error("请先完善当前数据！");
                    } else if (this.copyEnd[0].guiGeValue == "") {
                        DonMessage.error("请先完善当前数据！");
                    } else if (this.copyEnd[0].jianShuValue == "") {
                        DonMessage.error("请先完善当前数据！");
                    } else {
                        var d = {
                            cheChuanHaoValue: "", // 车船号
                            chanDiValue: "", // 品牌
                            pinMingValue: "", // 品名
                            caiZhiValue: "", // 材质
                            guiGeValue: "", // 规格
                            changDuValue: "", // 长度
                            danHaoValue: "", // 单号
                            kuCunJianShuValue: "", // 库存件数
                            jianShuValue: "", // 件数
                        };
                        this.detailsDataCopyAdd.push(d);
                        this.copyTableList.push(this.detailsDataCopyAdd[0]);
                        this.detailsDataCopyAdd.shift();
                    }
                } else {
                    this.disPingMing = true;
                    // 材质选择框
                    this.disCaiZhi = true;
                    // 规格选择框
                    this.disGuige = true;
                    // 长度选择框
                    this.disChangDu = true;
                    // 库存件数
                    this.disKuCunJianShu = true;
                    // 件数
                    this.disJianShu = true;
                    this.disDanHao = true;

                    var d = {
                        cheChuanHaoValue: "", // 车船号
                        chanDiValue: "", // 品牌
                        pinMingValue: "", // 品名
                        caiZhiValue: "", // 材质
                        guiGeValue: "", // 规格
                        changDuValue: "", // 长度
                        danHaoValue: "", // 单号
                        kuCunJianShuValue: "", // 库存件数
                        jianShuValue: "", // 件数
                    };
                    this.detailsDataCopyAdd.push(d);
                }
            },
            // 点击添加属性删除-下表格
            delClick(index, row) {
                this.detailsDataCopyAdd.splice(index, 1);
            },
            // 点击添加属性删除----上表格
            delClickCopy(index, row) {
                this.copyTableList.splice(index, 1);
            },
            // 点击表格保存
            addOkClick(index, row) {
                console.log(row.danHaoValue);
                if (row.cheChuanHaoValue == "") {
                    DonMessage.error("请先完善当前数据！");
                } else if (row.chanDiValue == "") {
                    DonMessage.error("请先完善当前数据！");
                } else if (row.pinMingValue == "") {
                    DonMessage.error("请先完善当前数据！");
                } else if (row.caiZhiValue == "") {
                    DonMessage.error("请先完善当前数据！");
                } else if (row.guiGeValue == "") {
                    DonMessage.error("请先完善当前数据！");
                } else if (row.jianShuValue == "") {
                    DonMessage.error("请先完善当前数据！");
                } else if (row.jianShuValue > row.kuCunJianShuValue) {
                    DonMessage.error("总件数不能大于此规格型号的库存件数");
                } else if (row.jianShuValue == 0) {
                    DonMessage.error("件数不能为0");
                } else {
                    this.copyTableList.push(this.detailsDataCopyAdd[0]);
                    this.detailsDataCopyAdd.shift();
                }
                console.log(this.copyTableList);
            },
            // 添加属性确定
            additiveOk() {
                this.addSXList = [];
                this.addOkNumber = 0;
                this.copyTableList.forEach((item, index) => {
                    if (item.cheChuanHaoValue == "") {
                        DonMessage.error("添加失败，车船号不能为空");
                        this.addOkNumber = 1;
                    } else if (item.chanDiValue == "") {
                        DonMessage.error("添加失败，品牌不能为空");
                        this.addOkNumber = 1;
                    } else if (item.pinMingValue == "") {
                        DonMessage.error("添加失败，品名不能为空");
                        this.addOkNumber = 1;
                    } else if (item.caiZhiValue == "") {
                        DonMessage.error("添加失败，材质不能为空");
                        this.addOkNumber = 1;
                    } else if (item.guiGeValue == "") {
                        DonMessage.error("添加失败，规格不能为空");
                        this.addOkNumber = 1;
                    } else if (item.jianShuValue <= 0) {
                        DonMessage.error("添加失败，件数必须大于零");
                        this.addOkNumber = 1;
                    } else {
                        // console.log(this.detailsDataCopyAdd, 'detailsDataCopyAdd')
                        const obj = {
                            vehicleNumber: item.cheChuanHaoValue, // 车船号
                            productName: item.pinMingValue, // 品名
                            model: item.caiZhiValue, // 材质
                            specification: item.guiGeValue, // 规格
                            length: item.changDuValue, // 长度
                            steelMillName: item.chanDiValue, // 品牌
                            number: item.jianShuValue, // 件数
                            oddNumbers: item.danHaoValue, // 件数
                            productTypeName: this.xuanzhongArr.productTypeName, // 货物类型名称
                        };
                        this.addSXList.push(obj);
                    }
                });
                if (this.addSXList.length == 0) {
                    DonMessage.error("请填写需要添加的货物");
                } else if (this.addOkNumber == 1) {
                } else {
                    if (this.jianShuValue > this.kuCunJianShuValue) {
                        DonMessage.error("总件数不能大于此规格型号的库存件数");
                    } else {
                        this.listData = {
                            outstockId: this.xuanzhongArr.outstockId,
                            list: this.addSXList,
                        };
                        addLwgWhOutstockDetail(this.listData)
                            .then((res) => {
                                DonMessage.success("操作成功!");
                                // this.additive = false
                                this.addProductTypeMark = false;
                                // 刷新子表
                                this.detailsListS();
                                this.getWhOutstockDetailListSummaryS(); // 汇总数据
                            })
                            .catch((err) => {
                                DonMessage.error(err.message);
                            });
                    }
                }
            },
            // 添加属性按钮弹框取消按钮
            additiveQuXiao() {
                this.detailsDataCopyAdd = [];
                this.addProductTypeMark = false;
                // this.productSpuFieldListS()
            },
            // 字典-查询客户
            getRelationCustomerS() {
                getRelationCustomer({
                    // groupNo: "change_status"
                })
                    .then((res) => {
                        this.kehuMing = res;
                    })
                    .catch((err) => {
                        DonMessage.error(err.message);
                    });
            },
            // 字典--冷热卷
            zidianS() {
                zidian({
                    groupNo: "product_type",
                })
                    .then((res) => {
                        this.typeData = res;
                    })
                    .catch((err) => {
                        DonMessage.error(err.message);
                    });
            },
            // 出库计划-主数据
            chukuListS() {
                chukuList({
                    pageNo: this.current,
                    pageSize: this.pagesize,
                    customerName: this.formInline.userName, // 客户名称
                    outstockPlateNumber: this.formInline.carNum, // 车牌号
                    cargoBarcode: this.formInline.gangJuanHao, // 捆包号
                    specification: this.formInline.guiGe, //  规格
                    weight: this.formInline.weightSearch, // 重量
                    wayNo: this.formInline.wayNo, // 汽运单号
                })
                    .then((res) => {
                        this.total = res.total;
                        this.chukuTableData = [];
                        this.xiangqingHuiZongArr = null;
                        this.chukuTableData = res.list;
                        if (this.chukuTableData[0]) {
                            this.xuanzhongArr = this.chukuTableData[0];
                            this.defaultSelected = this.chukuTableData[0].outstockId; // 出库主表id
                            setTimeout(() => {
                                this.$refs.tabref1.setCurrentRow(this.chukuTableData[0]);
                                if (this.chukuTableData[0].productType == 4 || this.chukuTableData[0].productType == 5 || this.chukuTableData[0].productType == 6) {
                                    if (this.xuanzhongArr.productType == 4) {
                                        this.lengthLook = true;
                                    } else {
                                        this.lengthLook = false;
                                    }
                                    this.getWhOutstockDetailListSummaryS(); // 汇总数据
                                }

                                if (this.chukuTableData[0].productType == 4 || this.chukuTableData[0].productType == 5 || this.chukuTableData[0].productType == 6) {
                                    this.luoWenGangLook = true;
                                    this.luoWenGangXiangQingLook = false;
                                    this.activeName = "second";
                                } else {
                                    this.luoWenGangLook = false;
                                    this.luoWenGangXiangQingLook = true;
                                    this.activeName = "first";
                                }
                            }, 10);
                            this.detailsListS(); // 详情数据
                        }

                        if (this.chukuTableData.length == 0) {
                            this.allWeightHuizong = "0.000";
                            this.allNumberHuizong = "0";
                            this.allPoundWeightHuizong = "0.000";
                            this.allweight = "0.000";
                            this.allNumber = "0";
                            this.zibiaoTableData = [];
                            this.HuizongTableData = [];
                            this.totalHuizong = 0;
                            this.totalXQ = 0;
                        }
                        // if (this.weight == {}) {
                        //   this.allWeight = '0.000'
                        //   this.allPoundWeight = '0.000'
                        //   this.zibiaoTableData = []
                        // }
                    })
                    .catch((err) => {
                        DonMessage.error(err.message);
                    });
            },
            // -----------------------------------------------添加
            // 添加-冷热卷
            changeCode: function (valueS) {
                let choosenItem = this.typeData.filter((item) => item.item_no === valueS)[0];
                this.typeId = choosenItem.item_no;
                this.typeName = choosenItem.show_name;
                console.log(this.addFormData);
                if (this.addFormData.goodsType != "" && this.addFormData.kehuName != "") {
                    let choosenItemKehu = this.kehuMing.filter((item) => item.custerName === this.addFormData.kehuName)[0];
                    getStockNumber({
                        customerTaxId: choosenItemKehu.taxpayerNumber,
                        productType: valueS,
                    })
                        .then((res) => {
                            this.COUNT = res["COUNT(1)"];
                            if (this.COUNT < 5 && this.COUNT > 0) {
                                this.showTishi = true;
                            }
                        })
                        .catch((err) => {
                            this.$message({ message: err.message, type: "error" });
                        });
                }
            },
            // 添加-客户信息
            changeKehuAdd: function (valueS) {
                console.log(valueS);
                let choosenItemKehu = this.kehuMing.filter((item) => item.custerName === valueS)[0];
                this.addClientName = choosenItemKehu.custerName;
                this.addClientSbm = choosenItemKehu.taxpayerNumber;
                this.addFormData.payCustomerName = valueS;

                if (this.shiyong == 1) {
                    const customerTaxId = this.kehuMing.filter((item) => item.custerName == this.addFormData.payCustomerName)[0].taxpayerNumber;
                    getFeeMode({ customerTaxId })
                        .then((res) => {
                            console.log(res);
                            if (res.length > 0) {
                                this.feeModeType = res[0].feeModeType;
                                this.feeModeTypeName = res[0].feeModeTypeName;
                                this.addFormData.feeModeType = res[0].feeModeType;
                                this.addFormData.feeModeTypeName = res[0].feeModeTypeName;
                            } else {
                                this.feeModeType = "";
                                this.feeModeTypeName = "";
                            }
                        })
                        .catch((err) => {
                            this.$message({ message: err.message, type: "error" });
                        });
                }
                if (this.addFormData.goodsType != "" && this.addFormData.kehuName != "") {
                    getStockNumber({
                        customerTaxId: choosenItemKehu.taxpayerNumber,
                        productType: this.addFormData.goodsType,
                    })
                        .then((res) => {
                            this.COUNT = res["COUNT(1)"];
                            if (this.COUNT < 5 && this.COUNT > 0) {
                                this.showTishi = true;
                            }
                        })
                        .catch((err) => {
                            this.$message({ message: err.message, type: "error" });
                        });
                }
            },
            // 添加-客户信息
            searchLockState: function (valueS) {
                let choosenItemKehu = this.options.filter((item) => item.name === valueS)[0];
                this.loactName = choosenItemKehu.name;
                this.loactId = choosenItemKehu.id;
            },
            // 出库计划-添加出库单
            addWhOutstockS() {
                this.loading = true;

                if (this.addFormData.feeModeType !== "") {
                    this.addFormData.feeModeTypeName = this.jsMethods.filter((item) => item.item_no == this.addFormData.feeModeType)[0].show_name;
                }
                let payCustomerNameTaxId;
                if (this.feeMode) {
                    payCustomerNameTaxId = this.kehuMing.filter((item) => item.custerName == this.addFormData.payCustomerName)[0].taxpayerNumber;
                } else {
                    payCustomerNameTaxId = null;
                    this.addFormData.payCustomerName = null;
                    this.addFormData.feeModeType = null;
                    this.addFormData.feeModeTypeName = null;
                }
                console.log(this.addFormData);
                addWhOutstock({
                    // customerId: this.addClientId, // 客户id
                    customerName: this.addClientName, // 客户名称
                    customerTaxId: this.addClientSbm, // 客户纳税识别码
                    productType: this.typeId, // 货物类型
                    productTypeName: this.typeName, // 货物类型名称
                    outstockPlateNumber: this.addFormData.carNumber, // 车牌号
                    driverName: this.addFormData.siJiName, // 司机 phone: '', // 司机电话   numberId: '', // 司机身份证
                    telephone: this.addFormData.phone, // 司机
                    idNumber: this.addFormData.numberId, // 司机
                    remark: this.addFormData.beizhu, // 司机
                    unloadingSite: this.addFormData.xieHuoAddress, // 卸货地
                    oddNumbers: this.addFormData.danHao, // 卸货地
                    deliveryUnit: this.addFormData.tiHuo, // 卸货地
                    feeModeType: this.addFormData.feeModeType,
                    feeModeTypeName: this.addFormData.feeModeTypeName,
                    externalOrderNo: this.addFormData.externalOrderNo,
                    shipName: this.addFormData.shipName,
                    cabin: this.addFormData.cabin,
                    wayNo: this.addFormData.wayNo,
                    payCustomerNameTaxId,
                    payCustomerName: this.addFormData.payCustomerName,
                })
                    .then((res) => {
                        DonMessage.success("操作成功!");
                        this.chukuListS();
                        this.addFormData.kehuName = "";
                        this.addFormData.goodsType = "";
                        this.addFormData.kehuName = "";
                        this.addFormData.siJiName = "";
                        this.addFormData.phone = "";
                        this.addFormData.numberId = "";
                        this.addFormData.carNumber = "";
                        this.addFormData.beizhu = "";
                        this.addFormData.xieHuoAddress = "";
                        this.addFormData.danHao = "";
                        this.addFormData.tiHuo = "";
                        this.addFormData.externalOrderNo = "";
                        this.addFormData.shipName = "";
                        this.addFormData.cabin = "";
                        this.loading = false;
                        this.addDisable = false; // 点击一次时隔两秒后才能再次点击
                        this.addMark = false;
                        this.$refs.addFormData.resetFields();
                    })
                    .catch((err) => {
                        this.loading = false;
                        this.addDisable = false; // 点击一次时隔两秒后才能再次点击

                        DonMessage.error(err.message);
                    });
            },
            // 点击出库单添加确认
            addOutboundOk(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (this.addFormData.carNumber) {
                            const loading2 = this.$loading({
                                lock: true,
                                text: "正在校验车牌号...",
                            });
                            detailByPlateNumberDriverVehicle({
                                outstockPlateNumber: this.addFormData.carNumber,
                            })
                                .then((res) => {
                                    loading2.close();
                                    console.log("detailByPlateNumberDriverVehicle", res);
                                    if (res instanceof Object && res.marker != undefined && res.marker == "黑名单") {
                                        DonMessage.error("该车牌号已被加入黑名单");
                                    } else {
                                        this.addWhOutstockS();
                                    }
                                })
                                .catch((error) => {
                                    loading2.close();
                                    DonMessage.error(error.message);
                                });
                        }
                        // this.$refs[formName].resetFields()
                    } else {
                        console.log("error submit!!");
                        return false;
                    }
                });
            },
            // 点击选择司机
            selectDriver() {
                this.table = true;
                this.cDlistS();
            },
            // 点击选择司机
            selectDriverChange() {
                this.sijiChange = true;
                this.table = true;
                this.cDlistS();
            },
            // 司机信息接口
            cDlistS() {
                cDlist(this.derverFrom)
                    .then((res) => {
                        this.siJiTotal = res.total;
                        this.gridData = [];
                        this.gridData = res.list;
                    })
                    .catch((err) => {
                        DonMessage.error(err.message);
                    });
            },
            // 司机查询
            SJsearchFn() {
                this.cDlistS();
            },
            // 司机重置
            resetForm(formName) {
                this.derverFrom = {
                    pageNo: 1,
                    pageSize: 50,
                    name: "", // 司机姓名
                    mobile: "", // 司机电话
                };
                this.cDlistS();
            },
            // 新增司机信息
            addDerver() {
                this.$router.push({ name: "driver" });
            },
            // 打开司机信息弹窗
            addGoodOpen() {
                let pH = document.documentElement.clientHeight;
                this.sjh = pH - 90 - 126 + "px";
            },
            // 点击司机表格当前行
            selderverChange(row) {
                if (row) {
                    this.$set(this.addFormData, "siJiName", row.name); // 司机姓名
                    this.$set(this.addFormData, "phone", row.mobile); // 司机电话
                    this.$set(this.addFormData, "numberId", row.identityNo); // 身份证
                    if (this.sijiChange == true) {
                        this.$set(this.xiugaiFormData, "siji", row.name); // 司机姓名
                        this.$set(this.xiugaiFormData, "phone", row.mobile); // 司机电话
                        this.$set(this.xiugaiFormData, "numberId", row.identityNo); // 身份证
                    }
                }
            },
            derverChange(val) {
                this.derverFrom.pageSize = val;
                // this.getDerverList()
            },
            derverCurrentChange(val) {
                this.derverFrom.pageNo = val;
                // this.getDerverList()
            },
            // ---------------------------------------------------修改fingById
            // 出库计划-修改查询
            fingByIdS() {
                fingById({
                    outstockId: this.xuanzhongArr.outstockId, // 出库主表id
                })
                    .then((res) => {
                        this.xiugaiTableData = res;
                        this.xiugaiFormData.kehuName = res.customerName; // 客户名
                        this.handIdName = this.xiugaiFormData.kehuName;
                        this.handIdSbm = res.customerTaxId;
                        this.xiugaiFormData.goodsType = res.productTypeName; // 货物类型
                        this.xiugaiFormData.siji = res.driverName; // 司机
                        this.xiugaiFormData.phone = res.telephone; // 司机
                        this.xiugaiFormData.numberId = res.idNumber; // 司机
                        this.xiugaiFormData.carNumber = res.outstockPlateNumber; // 车牌号
                        this.xiugaiFormData.xieHuoAddress = res.unloadingSite; // 卸货地
                        // this.xiugaiFormData.danHao = res.oddNumbers // 卸货地
                        this.xiugaiFormData.tiHuo = res.deliveryUnit; // 卸货地
                        this.xiugaiFormData.beizhu = res.remark; // beizhu
                        this.typeId = res.productType; // beizhu
                        this.typeName = res.productTypeName; // beizhu
                        this.xiugaiFormData.feeModeType = res.feeModeType; // 结算方式
                        this.xiugaiFormData.externalOrderNo = res.externalOrderNo; // 外部单号
                        this.xiugaiFormData.shipName = res.shipName; // 船名
                        this.xiugaiFormData.cabin = res.cabin; // 船舱
                        this.xiugaiFormData.payCustomerName = res.payCustomerName; // 结算客户
                    })
                    .catch((err) => {
                        DonMessage.error(err.message);
                    });
            },
            // 入库作业-修改确定
            updateOkS() {
                if (this.xiugaiFormData.feeModeType !== "") {
                    this.xiugaiFormData.feeModeTypeName = this.jsMethods.filter((item) => item.item_no == this.xiugaiFormData.feeModeType)[0].show_name;
                }
                let payCustomerNameTaxId;
                if (this.feeMode) {
                    payCustomerNameTaxId = this.kehuMing.filter((item) => item.custerName == this.xiugaiFormData.payCustomerName)[0].taxpayerNumber;
                } else {
                    payCustomerNameTaxId = null;
                    this.xiugaiFormData.payCustomerName = null;
                    this.xiugaiFormData.feeModeType = null;
                    this.xiugaiFormData.feeModeTypeName = null;
                }
                updateOk({
                    outstockId: this.xuanzhongArr.outstockId, // 主表id
                    // customerId: this.handIdId || this.xuanzhongArr.customerId, // 客户id
                    customerName: this.handIdName || this.xuanzhongArr.customerName, // 客户名称
                    customerTaxId: this.handIdSbm || this.xuanzhongArr.customerTaxId, // 客户纳税识别码
                    productType: this.typeId || this.xuanzhongArr.productType, // 货物类型
                    productTypeName: this.typeName || this.xuanzhongArr.productTypeName, // 货物类型名称
                    outstockPlateNumber: this.xiugaiFormData.carNumber, // 车牌号
                    remark: this.xiugaiFormData.beizhu, // 车牌号
                    driverName: this.xiugaiFormData.siji, // 司机
                    telephone: this.xiugaiFormData.phone, // 司机
                    idNumber: this.xiugaiFormData.numberId, // 司机
                    deliveryUnit: this.xiugaiFormData.tiHuo, // 卸货地
                    // oddNumbers: this.xiugaiFormData.danHao, // 卸货地
                    feeModeType: this.xiugaiFormData.feeModeType,
                    feeModeTypeName: this.xiugaiFormData.feeModeTypeName,
                    unloadingSite: this.xiugaiFormData.xieHuoAddress, // 卸货地
                    externalOrderNo: this.xiugaiFormData.externalOrderNo, // 外部单号
                    shipName: this.xiugaiFormData.shipName, // 船名
                    cabin: this.xiugaiFormData.cabin, // 船舱
                    payCustomerNameTaxId,
                    payCustomerName: this.xiugaiFormData.payCustomerName,
                })
                    .then((res) => {
                        DonMessage.success("操作成功!");
                        // this.detailsListS() // 详情数据
                        this.chukuListS();
                    })
                    .catch((err) => {
                        DonMessage.error(err.message);
                    });
            },
            // 点击修改确定按钮
            xiugaiqueding(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.updateOkS();
                        this.dialogVisiblexiugai = false;
                    } else {
                        console.log("error submit!!");
                        return false;
                    }
                });
            },
            // 修改选择出库车号
            Changecar() {
                if (this.xiugaiFormData.carNumber != "") {
                    const driverName = this.optionRC.filter((item) => item.plateNumber == this.xiugaiFormData.carNumber);
                    console.log(driverName);
                    if (driverName.length == 0) {
                        return this.xiugaiFormData.siji;
                    } else {
                        this.xiugaiFormData.siji = driverName[0].driverName;
                        this.xiugaiFormData.numberId = driverName[0].idNumber;
                        this.xiugaiFormData.phone = driverName[0].telephone;
                    }
                }
            },
            // 修改司机
            ChangeSJ() {
                if (this.xiugaiFormData.siji == "") {
                    this.xiugaiFormData.numberId = "";
                    this.xiugaiFormData.phone = "";
                } else if (this.xiugaiFormData.siji != "") {
                    const numberId = this.optionRC.filter((item) => item.driverName == this.xiugaiFormData.siji);
                    if (numberId.length == 0) {
                        this.FormData.idNumber = "";
                    } else {
                        this.xiugaiFormData.numberId = numberId[0].idNumber;
                        this.xiugaiFormData.phone = numberId[0].telephone;
                        this.xiugaiFormData.carNumber = numberId[0].plateNumber;
                    }
                }
            },
            // 提货车辆接口
            getPickUpLists() {
                getPickUpList({
                    billId: this.xuanzhongArr.billId,
                })
                    .then((res) => {
                        console.log(res);
                        this.optionRC = res;
                        this.optionsCK = res.filter((item) => item.plateNumber != "");
                        this.optionsSJ = res.filter((item) => item.driverName != "");
                    })
                    .catch((error) => {
                        DonMessage.error(error.message);
                    });
            },
            // 修改
            xiugaiBtn() {
                if (this.xuanzhongArr.source == 4) {
                    DonMessage.error("此单为共享出库单，不可修改");
                } else {
                    if (this.xuanzhongArr.length == 0) {
                        this.xuanzhongArr = this.chukuTableData[0];
                    }
                    if (this.zibiaoTableData.length > 0) {
                        this.productTypeDis = true;
                    } else {
                        this.productTypeDis = false;
                    }
                    if (this.xuanzhongArr.length == 0) {
                        DonMessage.error("请选择一条数据！");
                    } else if (this.xuanzhongArr.length > 1) {
                        DonMessage.error("只能选择一条数据！");
                    } else {
                        if (this.xuanzhongArr.source == 6) {
                            this.disableCar = true;
                        }
                        callSettlementMethod()
                            .then((res) => {
                                console.log(res);
                                this.shiyong = res;
                                if (this.shiyong == 1) {
                                    const customerTaxId = this.kehuMing.filter((item) => item.custerName == this.xuanzhongArr.payCustomerName)[0].taxpayerNumber;
                                    getFeeMode({ customerTaxId })
                                        .then((res) => {
                                            if (res.length > 0) {
                                                this.feeModeType = res[0].feeModeType;
                                                this.feeModeTypeName = res[0].feeModeTypeName;
                                                // this.xiugaiFormData.feeModeType = res[0].feeModeType
                                                // this.xiugaiFormData.feeModeTypeName = res[0].feeModeTypeName
                                            } else {
                                                this.feeModeType = "";
                                                this.feeModeTypeName = "";
                                            }
                                        })
                                        .catch((err) => {
                                            this.$message({ message: err.message, type: "error" });
                                        });
                                }
                            })
                            .catch((err) => {
                                this.$message({ message: err.message, type: "error" });
                            });
                        this.fingByIdS(); // 修改查询
                        // 租户是否需要结算
                        getDictionaryJson({
                            groupNo: "settlement_flag",
                            itemNo: window.localStorage.getItem("tenantCode"),
                        })
                            .then((res) => {
                                if (res.length > 0) {
                                    this.feeMode = true;
                                } else {
                                    this.feeMode = false;
                                }
                                this.getPickUpLists();
                                this.dialogVisiblexiugai = true;
                            })
                            .catch((err) => {
                                this.$message({ message: err.messgage, type: "error" });
                            });
                    }
                }
            },
            changeKehu: function (valueS) {
                let choosenItemKehu = this.kehuMing.filter((item) => item.custerName === valueS)[0];

                this.handIdName = choosenItemKehu.custerName;
                this.handIdSbm = choosenItemKehu.taxpayerNumber;
                this.xiugaiFormData.payCustomerName = valueS;
            },
            // ---------------------------------------------------删除
            // 入库作业--删除
            deleteWhOutstockS() {
                deleteWhOutstock({
                    outstockId: this.xuanzhongArr.outstockId, // 出库主表id
                })
                    .then((res) => {
                        DonMessage.success("操作成功!");

                        this.chukuListS();
                    })
                    .catch((err) => {
                        DonMessage.error(err.message);
                    });
            },
            // 点击删除
            deleteBtn() {
                if (this.xuanzhongArr.source == 3 && (this.xuanzhongArr.productType == 4 || this.xuanzhongArr.productType == 5 || this.xuanzhongArr.productType == 6)) {
                    DonMessage.error("出库单来源为货主端,不可进行操作!");
                } else if (this.xuanzhongArr.source == 4) {
                    DonMessage.error("此单为共享出库单，不可删除");
                } else {
                    if (this.xuanzhongArr.length == 0) {
                        this.xuanzhongArr = this.chukuTableData[0];
                    }
                    if (this.xuanzhongArr.length == 0) {
                        DonMessage.error("请选择一条数据进行删除！");
                    } else if (this.xuanzhongArr.length > 1) {
                        DonMessage.error("只能选择一条数据进行删除！");
                    } else {
                        this.$confirm("此操作将删除该条数据, 是否继续?", "提示", {
                            confirmButtonText: "确定",
                            cancelButtonText: "取消",
                            type: "warning",
                        })
                            .then(() => {
                                this.deleteWhOutstockS();
                            })
                            .catch(() => {});
                    }
                }
            },
            // 点击打印
            daYinBtn() {
                if (this.xuanzhongArr.productType == "4" || this.xuanzhongArr.productType == "5" || this.xuanzhongArr.productType == "6") {
                    this.$router.push({
                        // 核心语句
                        path: "/luoWenGangPrint", // 跳转的路径
                        query: {
                            // 路由传参时push和query搭配使用 ，作用时传递参数
                            id: this.xuanzhongArr.outstockId,
                        },
                    });
                } else {
                    DonMessage.error("请选择货物类型为螺纹钢/盘螺/线材！");
                }
            },
            // ---------------------------------------------------关联货物
            // 关联货物数据 -货主
            getWhOutstockPlanHZ() {
                this.guanLianGangJuanNum = this.guanLianGangJuanNum.replace(/\n/g, ",");
                this.guanLianGangJuanNum = this.guanLianGangJuanNum
                    .split(",")
                    .filter((item) => item != "")
                    .join(",");

                var reg = /^(?=.*\d.*\b)/;
                if (!reg.test(this.guanLianGangJuanNum)) {
                    this.guanLianGangJuanNum = "";
                }
                getWhOutstockPlan({
                    pageNo: this.currentGL,
                    pageSize: this.pagesizeGL,
                    billNo: this.xuanzhongArr.billNo,
                    agreementNumber: this.guanLianHeYueNum, // 合约号 luhao
                    heatNo: this.luhao, // 合约号 luhao
                    cargoBarcodes: this.guanLianGangJuanNum, // 捆包号：多个捆包号以“,”为分割的字符串。例：捆包号1，捆包号2
                    // customerName: this.guanLianCustomerName, // 材质
                    model: this.guanLianCaiZhi, // 材质
                    specification: this.guanLianGuiGe, // 规格
                    weight: this.guanLianWeight, // 重量
                    lockState: this.loactId, // 锁定状态
                    customerTaxId: this.customerTaxId,
                    productType: this.xuanzhongArr.productType,
                })
                    .then((res) => {
                        this.guanLianMark = true;

                        this.totalGL = res.total;
                        // this.relevanceTableData = []
                        this.relevanceTableData = res.list;
                        this.relevanceTableData.forEach((item, index) => {
                            if (item.lockState == "1") {
                                item.lockState = "已锁定";
                            } else if (item.lockState == "0") {
                                item.lockState = "未锁定";
                            } else if (item.lockState == "2") {
                                item.lockState = "加工锁定";
                            }
                            if (this.sheetsShow) {
                                this.$set(item, "outsheets", "");
                                this.$set(item, "outweight", "");
                                this.$set(item, "inputShow", false); // 显示输入框
                            }
                        });
                    })
                    .catch((err) => {
                        DonMessage.error(err.message);
                    });
            },
            // 关联货物数据
            getWhOutstockPlanS() {
                this.guanLianGangJuanNum = this.guanLianGangJuanNum.replace(/\n/g, ",");

                this.guanLianGangJuanNum = this.guanLianGangJuanNum
                    .split(",")
                    .filter((item) => item != "")
                    .join(",");
                var reg = /^(?=.*\d.*\b)/;
                if (!reg.test(this.guanLianGangJuanNum)) {
                    this.guanLianGangJuanNum = "";
                }
                getWhOutstockPlan({
                    pageNo: this.currentGL,
                    pageSize: this.pagesizeGL,
                    agreementNumber: this.guanLianHeYueNum, // 合约号
                    heatNo: this.luhao, // 合约号
                    cargoBarcodes: this.guanLianGangJuanNum, // 捆包号：多个捆包号以“,”为分割的字符串。例：捆包号1，捆包号2
                    model: this.guanLianCaiZhi, // 材质
                    specification: this.guanLianGuiGe, // 规格
                    weight: this.guanLianWeight, // 重量
                    lockState: this.loactId, // 锁定状态
                    customerTaxId: this.customerTaxId,
                    billIdIsNull: "1",
                    productType: this.xuanzhongArr.productType,
                    billNo: this.xuanzhongArr.billNo,
                })
                    .then((res) => {
                        this.guanLianMark = true;
                        this.totalGL = res.total;
                        this.relevanceTableData = res.list;
                        this.relevanceTableData.forEach((item, index) => {
                            if (item.lockState == "1") {
                                item.lockState = "已锁定";
                            } else if (item.lockState == "0") {
                                item.lockState = "未锁定";
                            } else if (item.lockState == "2") {
                                item.lockState = "加工锁定";
                            }
                            if (this.sheetsShow) {
                                this.$set(item, "outsheets", "");
                                this.$set(item, "outweight", "");
                                this.$set(item, "inputShow", false); // 显示输入框
                            }
                        });
                    })
                    .catch((err) => {
                        DonMessage.error(err.message);
                    });
            },
            // 耐磨板剪切板关联货物确认
            addWhOutstockNM() {
                let G = [];
                for (let i = 0; i < this.ZSList.length; i++) {
                    let obj = {};
                    obj.outsheets = this.ZSList[i].outsheets;
                    obj.cargoId = this.ZSList[i].cargoId;
                    obj.outweight = this.ZSList[i].outweight;
                    G.push(obj);
                }
                console.log(G);
                this.loading1 = true;
                addWhOutstockNM({
                    goods: {
                        outstockId: this.xuanzhongArr.outstockId,
                        G,
                    },
                })
                    .then((res) => {
                        console.log(res);
                        DonMessage.success("操作成功!");
                        this.currentGL = 1;
                        this.relevanceTableData = [];
                        if (this.xuanzhongArr.source == 3) {
                            this.getWhOutstockPlanHZ();
                        } else if (this.xuanzhongArr.source == 5) {
                            this.SDTDMark = false;
                        } else {
                            this.getWhOutstockPlanS();
                        }
                        this.detailsListS();
                        this.isDisable = false; // 点击一次时隔两秒后才能再次点击
                        this.loading1 = false;
                        this.SDTDMark = false;
                    })
                    .catch((error) => {
                        this.loading1 = false;
                        this.isDisable = false; // 点击一次时隔两秒后才能再次点击
                        DonMessage.error(error.message);
                    });
            },
            // 确认guanlain关联货物数据
            okAddWhOutstockS() {
                this.loading1 = true;
                okAddWhOutstock({
                    outstockId: this.xuanzhongArr.outstockId, // 出库主表id
                    cargoIds: this.stringID, // 物id：多个货物id以“,”为分割的字符串。例：货物id1，货物id2
                })
                    .then((res) => {
                        DonMessage.success("操作成功!");
                        this.currentGL = 1;
                        this.relevanceTableData = [];
                        if (this.xuanzhongArr.source == 3) {
                            this.getWhOutstockPlanHZ();
                        } else if (this.xuanzhongArr.source == 5) {
                            this.SDTDMark = false;
                        } else {
                            this.getWhOutstockPlanS();
                        }
                        this.detailsListS();
                        this.isDisable = false; // 点击一次时隔两秒后才能再次点击
                        this.loading1 = false;
                        this.SDTDMark = false;
                    })
                    .catch((err) => {
                        this.loading1 = false;
                        this.isDisable = false; // 点击一次时隔两秒后才能再次点击

                        DonMessage.error(err.message);
                    });
            },
            // 获取选中的多选框
            selectTable(selection, row) {
                row.inputShow = !row.inputShow;
                if (!row.inputShow) {
                    row.outweight = "";
                    row.outsheets = "";
                }
                this.ZSList = selection;
                this.arrInDetailId = [];
                selection.forEach((item) => {
                    this.arrInDetailId.push(item.cargoId); // id数组
                    this.arrInDetailId = Array.from(new Set(this.arrInDetailId)); // 去重
                    this.stringID = this.arrInDetailId.join(",");
                });
            },
            // 选择数据
            selectGoods(selection, row) {
                // row.inputShow = !row.inputShow
                // if (!row.inputShow) {
                //   row.outweight = ''
                //   row.outsheets = ''
                // }
                this.ZSList = selection;
                console.log(this.ZSList);
                if (this.ZSList.some((item) => !item.outweight)) {
                    this.selectWeightGL = selection.reduce((pre, cur) => Number(pre / 1 + cur.weight / 1).toFixed(3), 0);
                } else {
                    this.selectWeightGL = selection.reduce((pre, cur) => Number(pre / 1 + cur.outweight / 1).toFixed(3), 0);
                }
                this.selectNumberGL = selection.length;
                let arr = [];
                let arrName = [];
                selection.forEach((item) => {
                    if (item.instockCheckName == "已查验") {
                        this.flag = false;
                    }
                    arr.push(item.cargoId);
                    arrName.push(item.customerName);
                });
                arr = Array.from(new Set(arr)); // 去重获取删除的id
                this.stringID = arr.join(",");
                arrName = Array.from(new Set(arrName)); // 去重获取删除的id
                this.stringArrName = arrName.join(",");
                // if (this.stringArrName.indexOf(this.customerNameGuanLian) !== -1) {
                //   this.numName = 1 // 找到！==-1
                // } else {
                //   this.numName = 0
                // }
            },
            // // 点击全选
            selectAll(selection, row) {
                if (selection.length == 0) {
                    this.relevanceTableData.forEach((item) => {
                        item.inputShow = false;
                        item.outweight = "";
                        item.outsheets = "";
                    });
                } else {
                    selection.forEach((item) => {
                        item.inputShow = true;
                    });
                }
            },

            // 点击确认关联
            guanlianOk() {
                // console.log(this.stringID.split(',').length > 10,'......')
                console.log(
                    this.ZSList.some((item) => item.outsheets == ""),
                    this.ZSList.some((item) => item.outsheets / 1 == 0)
                );
                if (this.stringID == "") {
                    DonMessage.error("请勾选需要关联的货物！");
                } else if (this.stringID.split(",").length > 50) {
                    DonMessage.error("请选择50条以下（包括50条）数据进行操作！");
                } else if (this.ZSList.some((item) => item.outsheets) && this.ZSList.some((item) => item.outsheets / 1 > item.sheets / 1)) {
                    DonMessage.error("出库张数不可大于张数");
                } else if (this.ZSList.some((item) => item.outsheets / 1 == 0)) {
                    console.log(123);
                    DonMessage.error("出库张数不能等于0或空");
                } else {
                    if (this.sheetsShow) {
                        this.isDisable = true;
                        this.addWhOutstockNM();
                    } else {
                        this.isDisable = true;
                        this.okAddWhOutstockS();
                    }
                }
            },
            // 关联未入库货物清单
            guanLianClick(row) {
                this.customerNameGuanLian = "";
                this.customerTaxId = "";
                this.customerNameGuanLian = row.customerName;
                this.customerTaxId = row.customerTaxId;
                this.outstockId = row.outstockId;
                this.xuanzhongArr = row;
                console.log(row);
                if (row.productType == 9 || row.productType == 8 || row.productType == "h") {
                    this.sheetsShow = true;
                } else {
                    this.sheetsShow = false;
                }
                if (row.source == 3) {
                    this.guanLianGangJuanNum = ""; // 关联弹框捆包号
                    // this.guanLianCustomerName = '' // 关联弹框捆包号
                    this.guanLianHeYueNum = ""; // 关联弹框合约号
                    this.luhao = ""; // 关联弹框合约号
                    this.guanLianCaiZhi = ""; // 关联弹框材质
                    this.guanLianGuiGe = ""; // 关联弹框规格/*  */
                    this.lockState = ""; // 关联弹框规格
                    this.loactId = ""; // 关联弹框规格
                    this.guanLianWeight = ""; // 关联弹框重量
                    this.currentGL = 1;
                    this.relevanceTableData = [];
                    this.getWhOutstockPlanHZ();
                    // this.guanLianMark = true
                } else if (row.source == 4) {
                    DonMessage.error("此单为共享出库单，不可关联货物!");
                } else if (row.source == 5) {
                    this.guanLianGangJuanNum = "";
                    this.cangKuName = "";
                    this.quWeiName = "";
                    this.huoWeiName = "";
                    this.specification = "";
                    this.SDTDMark = true;
                    this.TDnumber = row.billNo;
                    this.kuweiId = "";
                    this.QId = "";
                    this.HId = "";
                    this.getWhOutstockPlanCargoLists();
                } else {
                    this.guanLianGangJuanNum = ""; // 关联弹框捆包号
                    // this.guanLianCustomerName = '' // 关联弹框捆包号
                    this.guanLianHeYueNum = ""; // 关联弹框合约号
                    this.luhao = ""; // 关联弹框合约号
                    this.guanLianCaiZhi = ""; // 关联弹框材质
                    this.guanLianGuiGe = ""; // 关联弹框规格/*  */
                    this.lockState = ""; // 关联弹框规格
                    this.loactId = ""; // 关联弹框规格
                    this.guanLianWeight = ""; // 关联弹框重量
                    this.currentGL = 1;
                    this.relevanceTableData = [];
                    this.getWhOutstockPlanS();
                    // this.guanLianMark = true
                }
            },
            // 关联弹框取消按钮
            guanLianQuXiao() {
                this.guanLianMark = false;
                // this.currentGL==1;
                this.detailsListS(); // 详情数据
            },
            // 点击关联查询
            guanlianCX() {
                if (this.xuanzhongArr.source == 3) {
                    this.getWhOutstockPlanHZ();
                } else {
                    this.getWhOutstockPlanS();
                }
            },
            // 点击关联重置
            guanlianCZ() {
                this.guanLianGangJuanNum = ""; // 关联弹框捆包号
                // this.guanLianCustomerName = '' // 关联弹框捆包号
                this.guanLianHeYueNum = ""; // 关联弹框合约号
                this.luhao = ""; // 关联弹框合约号

                this.guanLianCaiZhi = ""; // 关联弹框材质
                this.guanLianGuiGe = ""; // 关联弹框规格
                this.lockState = ""; // 关联弹框规格
                this.loactId = ""; // 关联弹框规格
                this.guanLianWeight = ""; // 关联弹框重量
                this.currentGL = 1;
                this.relevanceTableData = [];
                if (this.xuanzhongArr.source == 3) {
                    this.getWhOutstockPlanHZ();
                } else {
                    this.getWhOutstockPlanS();
                }
            },
            // ---------------------------------------------------详情取消关联

            // 点击选中详情需要修改的行
            xuanzhongXiangqing(row) {
                this.xiangqingListArr = null;
                this.xiangqingListArr = row;
            },
            // 取消关联
            associated() {
                if (this.xiangqingListArr == null) {
                    DonMessage.error("请选择一条数据进行取消关联！");
                } else {
                    if (this.xuanzhongArr.source == 3) {
                        this.disassociateData.returnData = "";
                        this.disassociateMark = true;
                        // DonMessage.error('出库单来源为货主端,不可进行操作')
                    } else {
                        console.log(this.xiangqingListArr.intelligenceTag, "this.xiangqingListArr");
                        console.log(this.xiangqingListArr, "this.xiangqingListArr");
                        var zncflag = false;
                        if (this.xiangqingListArr.intelligenceTag !== null && this.xiangqingListArr.intelligenceTag == 1 && this.xiangqingListArr.outstockCheckName == "已查验") {
                            zncflag = true;
                        }
                        this.$confirm("是否确定取消关联?", "提示", {
                            confirmButtonText: "确定",
                            cancelButtonText: "取消",
                            type: "warning",
                        })
                            .then(() => {
                                // 出库计划-详情-取消关联
                                cancelWhOutstock({
                                    outstockId: this.xuanzhongArr.outstockId || this.defaultSelected, // 出库主表id
                                    outstockLineId: this.xiangqingListArr.outstockLineId, // 出库子表id
                                    cargoId: this.xiangqingListArr.cargoId, // 货物id
                                    cancelReason: this.disassociateData.returnData, // 退回原因
                                })
                                    .then((res) => {
                                        // intelligenceTag  1 智能仓-并且已查验
                                        this.$message({
                                            showClose: true,
                                            message: "取消关联成功！",
                                            type: "success",
                                        });
                                        if (zncflag == true) {
                                            setTimeout(() => {
                                                this.$message({
                                                    showClose: true,
                                                    message: "已取消，现场需重新理货确定位置！",
                                                    type: "warning",
                                                });
                                            }, 5000);
                                        }
                                        this.detailsListS();
                                    })
                                    .catch((err) => {
                                        DonMessage.error(err.message);
                                    });
                            })
                            .catch(() => {});
                    }
                }
            },
            // 取消关联成功
            disassociateOk(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        cancelWhOutstock({
                            outstockId: this.xuanzhongArr.outstockId || this.defaultSelected, // 出库主表id
                            outstockLineId: this.xiangqingListArr.outstockLineId, // 出库子表id
                            cargoId: this.xiangqingListArr.cargoId, // 货物id
                            cancelReason: this.disassociateData.returnData, // 退回原因
                        })
                            .then((res) => {
                                DonMessage.success("取消关联成功!");
                                this.disassociateMark = false;
                                this.disassociateData.returnData = "";
                                this.detailsListS();
                            })
                            .catch((err) => {
                                DonMessage.error(err.message);
                            });
                    } else {
                        console.log("error submit!!");
                        return false;
                    }
                });
            },
            // 取消关联取消
            disassociateNo(formName) {
                this.$refs[formName].resetFields();
                this.disassociateMark = false;
            },
            // ---------------------------------------------------详情
            // 出库计划-汇总数据
            getWhOutstockDetailListSummaryS() {
                getWhOutstockDetailListSummary({
                    pageNo: this.currentHuiZong,
                    pageSize: this.pagesizeHuiZong,
                    outstockLineStatus: "0",

                    outstockId: this.xuanzhongArr.outstockId || this.defaultSelected,
                })
                    .then((res) => {
                        this.totalHuizong = 0;
                        this.xiangqingHuiZongArr = null;
                        this.HuizongTableData = [];
                        if (res.pageInfo.total == 0 || res.pageInfo.total == null) {
                            this.totalHuizong = 0;
                        } else {
                            this.totalHuizong = res.pageInfo.total;
                        }
                        this.HuizongTableData = res.pageInfo.list;
                        if (res.weight == null) {
                            this.allWeightHuizong = "0.000";
                            this.allNumberHuizong = "0";
                            this.allPoundWeightHuizong = "0.000";
                        } else if (res.weight == {}) {
                            this.allWeightHuizong = "0.000";
                            this.allNumberHuizong = "0";
                            this.allPoundWeightHuizong = "0.000";
                        } else {
                            this.allWeightHuizong = res.weight.allWeight;
                            this.allNumberHuizong = res.weight.allnumber;
                            this.allPoundWeightHuizong = res.weight.allPoundWeight;
                            if (this.allPoundWeightHuizong == "0") {
                                this.allPoundWeightHuizong = "0.000";
                            }
                            if (this.allWeightHuizong == "0") {
                                this.allWeightHuizong = "0.000";
                                this.allNumberHuizong = "0";
                            }
                        }
                        // console.log(res.weight)
                    })
                    .catch((err) => {
                        DonMessage.error(err.message);
                    });
            },
            // 出库计划-详情数据
            detailsListS() {
                detailsList({
                    pageNo: this.currentXQ,
                    pageSize: this.pagesizeXQ,
                    outstockId: this.xuanzhongArr.outstockId || this.defaultSelected,
                })
                    .then((res) => {
                        this.totalXQ = res.pageInfo.total;
                        // this.allWeight = '0.000'
                        // this.allPoundWeight = '0.000'
                        this.zibiaoTableData = res.pageInfo.list;
                        this.xiangqingListArr = null;
                        if (this.zibiaoTableData.length < 0) {
                            // this.$refs.tabref2.setCurrentRow(this.zibiaoTableData[0]);
                            setTimeout(() => {
                                this.$refs.tabref2.setCurrentRow(this.zibiaoTableData[0]);
                            }, 10);
                            this.xiangqingListArr = this.zibiaoTableData[0];
                        }
                        if (res.weight == null) {
                            this.allweight = "0.000";
                            this.allNumber = "0";
                            this.allPoundWeight = "0.000";
                        } else if (res.weight == {}) {
                            this.allweight = "0.000";
                            this.allNumber = "0";
                            this.allPoundWeight = "0.000";
                        } else {
                            this.allweight = res.weight.allweight;
                            this.allNumber = res.weight.allnumber;
                            this.allPoundWeight = res.weight.allPoundWeight;
                            if (this.allPoundWeight == "0") {
                                this.allPoundWeight = "0.000";
                            }
                            if (this.allweight == "0") {
                                this.allweight = "0.000";
                            }
                        }
                    })
                    .catch((err) => {
                        DonMessage.error(err.message);
                    });
            },

            // 点击选中需要修改的行
            dianjixuanzhong(row) {
                // this.luoWenGangLook = false
                this.xuanzhongArr = {};
                this.xuanzhongArr = row;

                this.currentXQ = 1;
                this.currentHuiZong = 1;
                if (this.xuanzhongArr.productType == 4 || this.xuanzhongArr.productType == 5 || this.xuanzhongArr.productType == 6) {
                    this.luoWenGangLook = true;
                    this.luoWenGangXiangQingLook = false;
                    this.activeName = "second";
                    if (this.xuanzhongArr.productType == 4) {
                        this.lengthLook = true;
                    } else {
                        this.lengthLook = false;
                    }
                    this.detailsListS(); // 子表数据
                    this.getWhOutstockDetailListSummaryS(); // 汇总数据
                } else {
                    this.detailsListS(); // 子表数据
                    this.luoWenGangLook = false;
                    this.luoWenGangXiangQingLook = true;
                    this.activeName = "first";
                }
            },
            // 查询
            chaxun() {
                this.chukuListS();
            },
            // 重置
            chongZhi() {
                this.formInline.carNum = "";
                this.formInline.userName = "";
                this.formInline.gangJuanHao = "";
                this.formInline.guiGe = "";
                this.formInline.weightSearch = "";
                this.formInline.wayNo = "";

                this.chukuListS();
            },
            // 点击添加
            addBtn() {
                callSettlementMethod()
                    .then((res) => {
                        console.log(res);
                        this.shiyong = res;
                    })
                    .catch((err) => {
                        this.$message({ message: err.message, type: "error" });
                    });
                // 租户是否需要结算
                getDictionaryJson({
                    groupNo: "settlement_flag",
                    itemNo: window.localStorage.getItem("tenantCode"),
                })
                    .then((res) => {
                        if (res.length > 0) {
                            this.feeMode = true;
                        } else {
                            this.feeMode = false;
                        }
                        this.COUNT = "";
                        this.addMark = true;
                    })
                    .catch((err) => {
                        this.$message({ message: err.messgage, type: "error" });
                    });
            },

            // 点击添加弹出框取消按钮
            addQuXiao(formName) {
                // this.$refs[formName].resetFields()
                this.addMark = false;
                this.chukuListS();
                this.addFormData.kehuName = "";
                this.addFormData.goodsType = "";
                this.addFormData.kehuName = "";
                this.addFormData.siJiName = "";
                this.addFormData.phone = "";
                this.addFormData.numberId = "";
                this.addFormData.carNumber = "";
                this.addFormData.beizhu = "";
                this.addFormData.xieHuoAddress = "";
                this.addFormData.danHao = "";
                this.addFormData.tiHuo = "";
                this.addFormData.feeModeType = "1";
                this.addFormData.externalOrderNo = "";
                this.addFormData.shipName = "";
                this.addFormData.cabin = "";
                this.addFormData.payCustomerName = "";
            },

            // 表格分页-主数据
            handleCurrentChangeA(val) {
                this.current = val;
                this.chukuListS();
            },
            handleSizeChangeA(val) {
                this.pagesize = val;
                this.chukuListS();
            },
            // 表格分页-详情
            handleCurrentChangeXQ(val) {
                this.currentXQ = val;
                this.detailsListS();
            },
            // 表格分页-详情
            handleSizeChangeXQ(val) {
                this.pagesizeXQ = val;
                this.detailsListS();
            },
            // 表格分页-详情-汇总
            handleCurrentChangeHuiZong(val) {
                this.currentHuiZong = val;
                this.getWhOutstockDetailListSummaryS();
            },
            // 表格分页-详情
            handleSizeChangeHuiZong(val) {
                this.pagesizeHuiZong = val;
                this.getWhOutstockDetailListSummaryS();
            },
            // 表格分页-关联货物
            handleCurrentChangeGL(val) {
                this.currentGL = val;
                if (this.xuanzhongArr.source == 3) {
                    this.getWhOutstockPlanHZ();
                } else {
                    this.getWhOutstockPlanS();
                }
            },
            handleSizeChangeGL(val) {
                this.pagesizeGL = val;
                if (this.xuanzhongArr.source == 3) {
                    this.getWhOutstockPlanHZ();
                } else {
                    this.getWhOutstockPlanS();
                }
            },
            // 表单
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        alert("submit!");
                    } else {
                        console.log("error submit!!");
                        return false;
                    }
                });
            },
        },
    };
</script>

<style lang="less" scoped>
    .market_out .el-tabs__item {
        padding: 50px !important;
    }
    .inpBox {
        .el-form-item {
            margin-left: 10px;
            margin-bottom: 10px;
        }
    }
    .inpBox .el-date-editor {
        width: 350px !important;
    }
    .pags {
        width: 100%;
        text-align: center;
    }
    .chuKuZuoyeWarp {
        width: 100%;
        position: relative;
        // height: 100%;

        // border: solid 1px blue;
        .chuKuZuoyeWarp-body {
            width: 100%;
            position: absolute;

            .chuKuZuoyeWarp-body-queryIpt {
                margin-top: 5px;
                width: 100%;
                display: flex;
                justify-content: space-between;
                margin-bottom: 5px;
                font-size: 14px;
                ul {
                    margin: 0;
                    width: 100%;
                    display: flex;
                    // flex-direction: column;
                    li {
                        margin-top: 5px;
                        list-style: none;
                        flex: 1;
                    }
                }
            }
            .chuKuZuoyeWarp-body-btn {
                margin-top: 5px;
            }
            .chuKuZuoyeWarp-body-table {
                margin-top: 5px;
            }
        }

        // 添加弹框
        .addMarkBox {
            .el-dialog {
                margin-left: 0 !important;
            }
            .addMarkBox-title {
                width: 100px;
                font-size: 18px;
                font-weight: 700;
                margin: 0 auto;
            }
        }
        .addMarkBox-title {
            width: 150px;
            font-size: 18px;
            font-weight: 700;
            margin: 0 auto;
        }
        // 关联未入库货物清单
        .guanLianBox {
            .guanLianBox-title {
                width: 142px;
                font-size: 18px;
                font-weight: 700;
                margin: 10px auto;
            }
            .guanLianBox-input {
                width: 100%;
                display: flex;
                ul {
                    flex: 1;
                    margin-left: 10px;
                    display: flex;
                    flex-direction: column;
                    li {
                        list-style: none;
                        margin-top: 5px;
                        flex: 1;
                    }
                }
                button {
                    margin-top: 10px;
                }
            }
            .guanLianBox-Table {
                margin-top: 20px;
            }
        }
    }
    .ModelText {
        font-size: 20px;
        color: red;
        width: 65%;
        margin: 20px auto;
    }
</style>
