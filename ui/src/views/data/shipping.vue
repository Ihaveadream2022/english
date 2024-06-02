<template>
    <div class="box">
        <div class="head">
            <div class="img">
                <img src="../../assets/daping/logo.png" alt />
            </div>
            <div class="title">海运业务</div>
            <div class="date">
                <span>{{ nowDate }}</span>
            </div>
        </div>
        <div class="container">
            <el-row :gutter="20">
                <!-- gutter是间距 -->
                <el-col :span="6" class="left">
                    <div class="wrapper">
                        <div class="top">
                            <div class="title">
                                <span>船运单状态</span>
                            </div>
                            <div id="CQZTpie" style="height: 42vh"></div>
                        </div>
                        <div class="bottom">
                            <div class="title">
                                <span>业务趋势</span>
                            </div>
                            <div id="YWQSzhe" style="height: 40vh"></div>
                        </div>
                    </div>
                </el-col>
                <el-col :span="12" class="middle">
                    <div class="mapBox">
                        <div class="title">
                            历史海运总量：<span>{{ totalData }}</span
                            >吨
                        </div>
                        <div class="map" id="map"></div>
                    </div>
                </el-col>
                <el-col :span="6" class="right">
                    <div class="wrapper">
                        <div class="title">
                            <span>船运单据</span>
                        </div>
                        <div class="table">
                            <vue-seamless-scroll :data="orderData" :class-option="optionSingleHeightTime" class="seamless-warp">
                                <ul>
                                    <li class="dingdan" v-for="item in orderData" :key="item.id">
                                        <div class="itemA">
                                            <div>
                                                <p>船运状态： {{ item.shipStatusName }}</p>
                                            </div>
                                            <div>
                                                <p>发布者：{{ item.createUserName }}</p>
                                            </div>
                                        </div>
                                        <div class="itemA">
                                            <div>
                                                <p>船舶名称： {{ item.shipName }}</p>
                                            </div>
                                            <div>
                                                <p>航次号：{{ item.shipDateNo }}</p>
                                            </div>
                                        </div>
                                        <div class="itemB">
                                            <p style="color: #fe4830">{{ item.deliverAddressName }}</p>
                                            <span>&gt;</span>
                                            <p style="color: #fbad00">{{ item.receivingAddressName }}</p>
                                        </div>
                                        <div class="itemA">
                                            <p>创建时间{{ item.createTime | dateFormat }}</p>
                                        </div>
                                    </li>
                                </ul>
                            </vue-seamless-scroll>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>
    </div>
</template>
<script>
    import { yearWaterTransList, listTotal, listTrans } from "@/api/request";
    import echarts from "echarts"; //  注册
    import "echarts/map/js/world.js";
    import "echarts/map/js/china.js";
    import "echarts/extension-src/bmap/bmap.js";
    export default {
        data() {
            return {
                nowDate: "", // 当前日期
                nowTimer: null,
                orderData: [
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "万里阳光号",
                        shipDateNo: "0509",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20230509-000001",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "已靠泊",
                        receivingAddressName: "上海码头",
                        deliverAddressName: "上海码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2023-05-09T06:17:13.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "汪汪队",
                        shipDateNo: "11",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20230223-000001",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "未开始",
                        receivingAddressName: "华埠码头",
                        deliverAddressName: "深圳码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2023-02-23T05:40:08.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "汪汪队",
                        shipDateNo: "2323",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20221008-000009",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "未开始",
                        receivingAddressName: "华埠码头",
                        deliverAddressName: "上海码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-10-08T06:11:34.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "汪汪队",
                        shipDateNo: "1",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20221008-000002",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "已靠泊",
                        receivingAddressName: "上海码头",
                        deliverAddressName: "深圳码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-10-08T05:12:03.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "小松鼠",
                        shipDateNo: "23213213",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20220920-000001",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "未开始",
                        receivingAddressName: "上海码头",
                        deliverAddressName: "深圳码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-09-20T05:22:14.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "汪汪队",
                        shipDateNo: "3445221",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20220823-000003",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "未开始",
                        receivingAddressName: "津港码头",
                        deliverAddressName: "深圳码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-08-23T08:13:51.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "小松鼠",
                        shipDateNo: "125",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20220725-000001",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "已抵锚",
                        receivingAddressName: "上海码头",
                        deliverAddressName: "上海宝山罗泾码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-07-25T02:32:18.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "奕淳3",
                        shipDateNo: "4000111",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20220722-000003",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "未开始",
                        receivingAddressName: "上海码头",
                        deliverAddressName: "上海码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-07-22T01:58:46.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "汪汪队",
                        shipDateNo: "3453-00001",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20220722-000001",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "已完成",
                        receivingAddressName: "上海码头,妙蛙种子",
                        deliverAddressName: "深圳码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-07-22T01:56:15.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "小松鼠",
                        shipDateNo: "12",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20220718-000003",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "已完成",
                        receivingAddressName: "华埠码头",
                        deliverAddressName: "深圳码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-07-18T01:42:21.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "小松鼠",
                        shipDateNo: "123",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20220718-000001",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "已抵锚",
                        receivingAddressName: "上海码头",
                        deliverAddressName: "呆呆兽",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运小程序/扫码枪",
                        createTime: "2022-07-18T01:12:50.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "普通一号",
                        shipDateNo: "姜姜号",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20220707-000005",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "已起锚",
                        receivingAddressName: "上海宝山罗泾码头,妙蛙种子,深圳码头,呆呆兽,上海码头,津港码头,华埠码头",
                        deliverAddressName: "深圳码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运小程序/扫码枪",
                        createTime: "2022-07-07T06:50:09.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "汪汪队",
                        shipDateNo: "124354",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20220707-000003",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "未开始",
                        receivingAddressName: "上海宝山罗泾码头,呆呆兽",
                        deliverAddressName: "深圳码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运小程序/扫码枪",
                        createTime: "2022-07-07T05:55:22.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "汪汪队",
                        shipDateNo: "1232312312",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20220707-000001",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "未开始",
                        receivingAddressName: "津港码头",
                        deliverAddressName: "上海宝山罗泾码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-07-07T03:18:52.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "汪汪队",
                        shipDateNo: "十分士大夫士大夫",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20220706-000001",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "未开始",
                        receivingAddressName: "上海宝山罗泾码头,华埠码头,上海码头",
                        deliverAddressName: "深圳码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-07-06T08:18:06.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "汪汪队",
                        shipDateNo: "123321",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20220704-000001",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "已完成",
                        receivingAddressName: "上海码头",
                        deliverAddressName: "上海码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-07-04T01:29:59.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "小松鼠",
                        shipDateNo: "2022062300",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20220623-000004",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "已完成",
                        receivingAddressName: "妙蛙种子,华埠码头",
                        deliverAddressName: "深圳码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-06-23T05:12:12.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "奕淳3",
                        shipDateNo: "lj哦哦我哦我",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20220623-000001",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "已完成",
                        receivingAddressName: "上海码头,深圳码头,华埠码头",
                        deliverAddressName: "华埠码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运小程序/扫码枪",
                        createTime: "2022-06-23T02:43:38.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "11",
                        shipDateNo: "22",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20220622-000009",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "已完成",
                        receivingAddressName: "华埠码头",
                        deliverAddressName: "津港码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-06-22T08:31:20.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "小松鼠",
                        shipDateNo: "20220609001",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20220609-000002",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "已完成",
                        receivingAddressName: "妙蛙种子,呆呆兽",
                        deliverAddressName: "华埠码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-06-09T02:32:40.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "汪汪队",
                        shipDateNo: "1288",
                        shipNo: null,
                        shipTransportNo: "CQ-04N-20220602-000006",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: "已完成",
                        receivingAddressName: "津港码头,妙蛙种子",
                        deliverAddressName: "华埠码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-06-02T05:42:26.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "串儿船",
                        shipDateNo: "Ceshi",
                        shipNo: null,
                        shipTransportNo: "",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: null,
                        receivingAddressName: "华埠码头",
                        deliverAddressName: "津港码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-05-24T05:26:08.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "京海旺1号",
                        shipDateNo: "测试",
                        shipNo: null,
                        shipTransportNo: "",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: null,
                        receivingAddressName: "华埠码头",
                        deliverAddressName: "津港码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-05-23T08:57:47.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "小松鼠",
                        shipDateNo: "测试",
                        shipNo: null,
                        shipTransportNo: "",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: null,
                        receivingAddressName: "呆呆兽",
                        deliverAddressName: "华埠码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运系统-管理员",
                        createTime: "2022-04-20T02:38:59.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "京海旺1号",
                        shipDateNo: "jj",
                        shipNo: null,
                        shipTransportNo: "",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: null,
                        receivingAddressName: "津港码头",
                        deliverAddressName: "华埠码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运系统-管理员",
                        createTime: "2022-03-31T06:16:29.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "京海旺1号",
                        shipDateNo: "姜",
                        shipNo: null,
                        shipTransportNo: "",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: null,
                        receivingAddressName: "津港码头",
                        deliverAddressName: "华埠码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运系统-管理员",
                        createTime: "2022-03-29T09:09:28.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "小松鼠",
                        shipDateNo: "ZH032502",
                        shipNo: null,
                        shipTransportNo: "",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: null,
                        receivingAddressName: "妙蛙种子",
                        deliverAddressName: "华埠码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-03-25T02:36:29.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "汪汪队",
                        shipDateNo: "ZH032501",
                        shipNo: null,
                        shipTransportNo: "",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: null,
                        receivingAddressName: "呆呆兽",
                        deliverAddressName: "妙蛙种子",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-03-25T01:54:52.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "小松鼠",
                        shipDateNo: "2022032301",
                        shipNo: null,
                        shipTransportNo: "",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: null,
                        receivingAddressName: "妙蛙种子",
                        deliverAddressName: "呆呆兽",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-03-23T03:13:08.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "串儿船",
                        shipDateNo: "20220322",
                        shipNo: null,
                        shipTransportNo: "",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: null,
                        receivingAddressName: "呆呆兽",
                        deliverAddressName: "华埠码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-03-22T10:19:05.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "京海旺1号",
                        shipDateNo: "pyj-202203",
                        shipNo: null,
                        shipTransportNo: "",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: null,
                        receivingAddressName: "津港码头",
                        deliverAddressName: "华埠码头",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-03-22T02:28:44.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "奕淳3",
                        shipDateNo: "20220319",
                        shipNo: null,
                        shipTransportNo: "",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: null,
                        receivingAddressName: "妙蛙种子",
                        deliverAddressName: "呆呆兽",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-03-19T04:26:06.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "奕淳3",
                        shipDateNo: "001",
                        shipNo: null,
                        shipTransportNo: "",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: null,
                        receivingAddressName: "妙蛙种子",
                        deliverAddressName: "呆呆兽",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-03-12T03:56:28.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "小松鼠",
                        shipDateNo: "0312",
                        shipNo: null,
                        shipTransportNo: "",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: null,
                        receivingAddressName: "妙蛙种子",
                        deliverAddressName: "呆呆兽",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "海运PC",
                        createTime: "2022-03-12T03:11:31.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                    {
                        id: null,
                        tenantId: null,
                        tenantName: null,
                        shipId: null,
                        shipName: "小松鼠",
                        shipDateNo: "20220305",
                        shipNo: null,
                        shipTransportNo: "",
                        contractId: null,
                        contractNo: null,
                        contractSysNo: null,
                        shipStatus: null,
                        shipStatusName: null,
                        receivingAddressName: "妙蛙种子",
                        deliverAddressName: "呆呆兽",
                        productWeight: null,
                        weight: null,
                        number: null,
                        estimateArrivalTime: null,
                        estimateShipMentTime: null,
                        shipMentTime: null,
                        arrivalTime: null,
                        dealDuration: null,
                        factDemurrageDay: null,
                        isDelete: null,
                        createUserId: null,
                        createUserName: "信风海运",
                        createTime: "2022-03-05T06:21:57.000+0000",
                        createMonth: null,
                        createDay: null,
                    },
                ],
                totalData: {},
                allTenants: [
                    {
                        id: 72,
                        tenantCode: "002",
                        tenantName: "唐山曹妃甸兴瀚钢铁物流有限公司",
                        longitude: 118.503978,
                        latitude: 38.976345,
                        taxpayerNumber: "91130230795465353W",
                        isEnable: 1,
                        createTime: "2022-04-13T17:02:58.000+0000",
                    },
                    {
                        id: 79,
                        tenantCode: "00a",
                        tenantName: "深圳前海龙海物流供应链管理有限公司",
                        longitude: 113.903937,
                        latitude: 22.512424,
                        taxpayerNumber: "91440300326702248J",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 82,
                        tenantCode: "02R",
                        tenantName: "武汉聚敛时代科技有限公司",
                        longitude: 116.32739,
                        latitude: 39.939862,
                        taxpayerNumber: "91420100MA49BXX88B",
                        isEnable: 1,
                        createTime: "2022-04-13T17:02:58.000+0000",
                    },
                    {
                        id: 83,
                        tenantCode: "03X",
                        tenantName: "中国远洋海运集团有限公司",
                        longitude: 116.32739,
                        latitude: 39.939862,
                        taxpayerNumber: "91310000MA1FL1MMXL",
                        isEnable: 1,
                        createTime: "2022-04-13T17:02:58.000+0000",
                    },
                    {
                        id: 84,
                        tenantCode: "03D",
                        tenantName: "河南晋开化工投资控股集团有限责任公司",
                        longitude: 116.328405,
                        latitude: 39.899243,
                        taxpayerNumber: "91410212761699637C",
                        isEnable: 1,
                        createTime: "2022-04-13T17:02:58.000+0000",
                    },
                    {
                        id: 85,
                        tenantCode: "03b",
                        tenantName: "湖南妙涵企业管理咨询有限公司",
                        longitude: 116.365561,
                        latitude: 39.87377,
                        taxpayerNumber: "91430104MA7AXYPC96",
                        isEnable: 1,
                        createTime: "2022-04-13T17:02:58.000+0000",
                    },
                    {
                        id: 86,
                        tenantCode: "04N",
                        tenantName: "大连信风海运有限公司",
                        longitude: 116.559066,
                        latitude: 39.883342,
                        taxpayerNumber: "91210200796932603Y",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 87,
                        tenantCode: "03N",
                        tenantName: "天津立业钢铁集团有限公司",
                        longitude: 116.363439,
                        latitude: 39.887909,
                        taxpayerNumber: "911200001037814289",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 88,
                        tenantCode: "04V",
                        tenantName: "上海偕勤国际贸易有限公司",
                        longitude: 116.458539,
                        latitude: 39.931175,
                        taxpayerNumber: "91310000MA7B5Q657P",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 89,
                        tenantCode: "04W",
                        tenantName: "佛山市海天调味食品股份有限公司",
                        longitude: 116.374425,
                        latitude: 39.913587,
                        taxpayerNumber: "91440600722448755D",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 90,
                        tenantCode: "04X",
                        tenantName: "浙江钱鸿海运集团有限公司",
                        longitude: 116.397771,
                        latitude: 39.89326,
                        taxpayerNumber: "91330109768216554Y",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 91,
                        tenantCode: "05H",
                        tenantName: "青岛飞科石业有限公司",
                        longitude: 116.456617,
                        latitude: 39.884691,
                        taxpayerNumber: "91370214682561533L",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 92,
                        tenantCode: "06G",
                        tenantName: "营口中成海运有限公司",
                        longitude: 116.391303,
                        latitude: 39.927613,
                        taxpayerNumber: "91210804561377756K",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 93,
                        tenantCode: "06P",
                        tenantName: "营口交通运输集团有限公司",
                        longitude: 122.176207,
                        latitude: 40.665647,
                        taxpayerNumber: "91210800667271271J",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 94,
                        tenantCode: "086",
                        tenantName: "广西长荣海运有限公司",
                        longitude: 116.572111,
                        latitude: 39.832677,
                        taxpayerNumber: "914500006821146969",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 95,
                        tenantCode: "089",
                        tenantName: "西安恩格商贸有限公司",
                        longitude: 116.332665,
                        latitude: 39.914308,
                        taxpayerNumber: "9161013105158646XF",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 96,
                        tenantCode: "08A",
                        tenantName: "杭州海康威视数字技术股份有限公司",
                        longitude: 116.409444,
                        latitude: 39.875155,
                        taxpayerNumber: "91330000733796106P",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 97,
                        tenantCode: "08C",
                        tenantName: "韩新海运（成都）有限公司",
                        longitude: 116.483602,
                        latitude: 39.884561,
                        taxpayerNumber: "91510100580011944A",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 98,
                        tenantCode: "08P",
                        tenantName: "腾讯云计算（北京）有限责任公司",
                        longitude: 116.337505,
                        latitude: 39.974965,
                        taxpayerNumber: "911101085636549482",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 99,
                        tenantCode: "09I",
                        tenantName: "上海诗意居毯业有限公司",
                        longitude: 116.472272,
                        latitude: 39.879292,
                        taxpayerNumber: "91310118MA1JNHXQ0E",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 100,
                        tenantCode: "09U",
                        tenantName: "深圳市保得海运有限公司",
                        longitude: 116.471271,
                        latitude: 39.847917,
                        taxpayerNumber: "9144030070845610XB",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 101,
                        tenantCode: "09X",
                        tenantName: "南京两江海运股份有限公司",
                        longitude: 116.629915,
                        latitude: 39.808442,
                        taxpayerNumber: "91320100797114506Q",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 102,
                        tenantCode: "0B6",
                        tenantName: "河北豪瑞节能科技有限公司",
                        longitude: 116.402235,
                        latitude: 39.930871,
                        taxpayerNumber: "91130922MA0GFQ2L9T",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 103,
                        tenantCode: "0B9",
                        tenantName: "郑州市利万农机械有限公司",
                        longitude: 116.444463,
                        latitude: 39.90309,
                        taxpayerNumber: "91410182MA9G3YKF7M",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 104,
                        tenantCode: "0BK",
                        tenantName: "海南有趣科技有限公司",
                        longitude: 108.982561,
                        latitude: 30.008518,
                        taxpayerNumber: "91460000MA5T92TJ7C",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 105,
                        tenantCode: "0BM",
                        tenantName: "珠海杰慧商贸有限公司",
                        longitude: 118.402723,
                        latitude: 39.511118,
                        taxpayerNumber: "91440402MA55H23X89",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                    {
                        id: 106,
                        tenantCode: "0BN",
                        tenantName: "河南永凡律师事务所",
                        longitude: 115.793867,
                        latitude: 39.901234,
                        taxpayerNumber: "31410000MD02542193",
                        isEnable: 1,
                        createTime: "2023-08-09T17:00:30.000+0000",
                    },
                ],
                statusData: [
                    {
                        shipStatusName: "未开始",
                        statusCount: 8,
                        shipStatus: 1001,
                    },
                    {
                        shipStatusName: "已抵锚",
                        statusCount: 2,
                        shipStatus: 1101,
                    },
                    {
                        shipStatusName: "已起锚",
                        statusCount: 1,
                        shipStatus: 1102,
                    },
                    {
                        shipStatusName: "已靠泊",
                        statusCount: 2,
                        shipStatus: 1103,
                    },
                    {
                        shipStatusName: "已完成",
                        statusCount: 8,
                        shipStatus: 1301,
                    },
                ],
                seriesMatou: [
                    {
                        name: "妙蛙种子",
                        value: [129.458347, 42.885376],
                        type: "matou",
                        isUsed: 1,
                    },
                    {
                        name: "呆呆兽",
                        value: [112.575793, 26.891872],
                        type: "matou",
                        isUsed: 1,
                    },
                    {
                        name: "津港码头",
                        value: [117.730779, 38.982124],
                        type: "matou",
                        isUsed: 1,
                    },
                    {
                        name: "华埠码头",
                        value: [116.016341, 40.073986],
                        type: "matou",
                        isUsed: 0,
                    },
                    {
                        name: "上海码头",
                        value: [121.691626, 31.281634],
                        type: "matou",
                        isUsed: 1,
                    },
                    {
                        name: "深圳码头",
                        value: [113.814064, 22.602031],
                        type: "matou",
                        isUsed: 1,
                    },
                ],
                seriesLine: [
                    [
                        [113.814064, 22.602031],
                        [129.458347, 42.885376],
                        [112.575793, 26.891872],
                        [117.730779, 38.982124],
                        [116.016341, 40.073986],
                        [121.691626, 31.281634],
                        [113.814064, 22.602031],
                        [116.528406, 39.918101],
                    ],
                    [
                        [112.575793, 26.891872],
                        [121.691626, 31.281634],
                    ],
                    [
                        [113.814064, 22.602031],
                        [121.691626, 31.281634],
                    ],
                    [
                        [121.691626, 31.281634],
                        [121.691626, 31.281634],
                    ],
                ],
            };
        },
        created() {
            this.getDate();
            this.nowTimer = setInterval(this.getDate, 500);
        },
        mounted() {
            const tenant = this.allTenants[6];
            this.YWQSzheFn(tenant.tenantCode);
            this.getTotalData(tenant.tenantCode);
            this.$nextTick(() => {
                this.peiFn(tenant.tenantCode);
            });
            this.getMap(tenant.tenantCode);
        },
        methods: {
            // 地图
            getMap() {
                map(this.seriesMatou, this.seriesLine);
                function map(matou, shiArr) {
                    let myChart = echarts.init(document.getElementById("map"));
                    let chartOption = {
                        color: ["#fdfd01"],
                        legend: {
                            orient: "vertical",
                            id: 1,
                            y: "top",
                            x: "right",
                            itemWidth: 15,
                            data: [
                                {
                                    name: "码头",
                                },
                            ],
                            textStyle: {
                                color: "#fff",
                            },
                        },
                        geo: {
                            // 地理坐标系组件用于地图的绘制
                            map: "china", // 表示中国地图
                            roam: false, // 是否开启鼠标缩放和平移漫游
                            zoom: 2.1, // 当前视角的缩放比例（地图的放大比例）
                            center: [117.232389, 31.311958],
                            scaleLimit: {
                                // 滚轮缩放的极限控制
                                min: 1,
                            },
                            label: {
                                show: true,
                                color: "#03faec",
                            },
                            itemStyle: {
                                // 地图区域的多边形 图形样式。
                                borderColor: "rgba(0, 0, 0, 0.2)",
                                normal: {
                                    areaColor: "#01145D", // 区域背景色 地图
                                    borderColor: "#03DBD9", // 每个区域的边框色
                                },
                                emphasis: {
                                    areaColor: "#02229b", // 区域背景色 点击后颜色
                                },
                            },
                            emphasis: {
                                label: {
                                    color: "#00FFFF",
                                },
                            },
                        },
                        series: [
                            {
                                name: "码头",
                                type: "scatter",
                                coordinateSystem: "geo",
                                data: matou,
                                label: {
                                    normal: {
                                        show: true,
                                        formatter: function (params) {
                                            if (params.data.isUsed == 1) {
                                                return "";
                                            } else {
                                                return params.name;
                                            }
                                        },
                                        position: "bottom",
                                        color: "#fff",
                                        fontSize: "11",
                                    },
                                    emphasis: {
                                        show: false,
                                        color: "#00d5e9",
                                    },
                                },
                            },
                            {
                                // 地图线的动画效果
                                type: "lines",
                                zlevel: 2,
                                effect: {
                                    show: true,
                                    period: 8, // 箭头指向速度，值越小速度越快
                                    trailLength: 0.1, // 特效尾迹长度[0,1]值越大，尾迹越长重
                                    symbol: "path://M30.9,53.2C16.8,53.2,5.3,41.7,5.3,27.6S16.8,2,30.9,2C45,2,56.4,13.5,56.4,27.6S45,53.2,30.9,53.2z M30.9,3.5C17.6,3.5,6.8,14.4,6.8,27.6c0,13.3,10.8,24.1,24.101,24.1C44.2,51.7,55,40.9,55,27.6C54.9,14.4,44.1,3.5,30.9,3.5z M36.9,35.8c0,0.601-0.4,1-0.9,1h-1.3c-0.5,0-0.9-0.399-0.9-1V19.5c0-0.6,0.4-1,0.9-1H36c0.5,0,0.9,0.4,0.9,1V35.8z M27.8,35.8 c0,0.601-0.4,1-0.9,1h-1.3c-0.5,0-0.9-0.399-0.9-1V19.5c0-0.6,0.4-1,0.9-1H27c0.5,0,0.9,0.4,0.9,1L27.8,35.8L27.8,35.8z",
                                    symbolSize: 9, // 图标大小
                                },
                                lineStyle: {
                                    normal: {
                                        color: "#1DE9B6",
                                        width: 1, // 线条宽度
                                        opacity: 1, // 尾迹线条透明度
                                        curveness: 0.3, // 尾迹线条曲直度
                                    },
                                },
                                polyline: true,
                                data: shiArr,
                            },
                        ],
                    };
                    myChart.setOption(chartOption);
                }
            },
            // 疏港统计
            SGSJzhuFn(code) {
                listTrans({ tenantCode: code })
                    .then((res) => {
                        // 柱状图数据
                        var map4Data1 = [];
                        var map4Data2 = [];
                        this.nameData = [];
                        res.data.forEach((item, index) => {
                            this.nameData.push(item.transportDate);
                            map4Data1.push(item.storageWeight);
                            map4Data2.push(item.trafficWeight);
                        });
                        let myChart = echarts.init(document.getElementById("SGSJzhu"));

                        // 绘制图表--柱状
                        myChart.setOption({
                            color: ["#0DD9FE", "#52F23A"],
                            // backgroundColor: "#323a5e",
                            grid: {
                                left: "2%",
                                right: "4%",
                                bottom: "9%",
                                top: "25%",
                                containLabel: true,
                            },
                            legend: {
                                icon: "circle",
                                data: ["入库量", "汽运量"],
                                textStyle: {
                                    color: "#fff",
                                },
                            },
                            // ---  提示框 ----
                            tooltip: {
                                show: true, // 是否显示提示框，默认为true
                                trigger: "item", // 数据项图形触发
                                axisPointer: {
                                    // 指示样式
                                    type: "shadow",
                                    axis: "auto",
                                },
                                formatter: function (value) {
                                    return value.marker + value.seriesName + "：" + value.value;
                                },
                                padding: 5,
                                textStyle: {
                                    // 提示框内容的样式
                                    color: "#fff",
                                },
                            },
                            calculable: true,
                            xAxis: {
                                type: "category",
                                data: this.nameData,
                                axisLine: {
                                    lineStyle: {
                                        color: "white",
                                    },
                                },
                                axisLabel: {
                                    // interval: 0,
                                    // rotate: 40,
                                    textStyle: {
                                        fontFamily: "Microsoft YaHei",
                                    },
                                },
                            },
                            yAxis: {
                                type: "value",
                                data: ["2000", "4000", "6000", "8000"],
                                axisLine: {
                                    show: false,
                                    lineStyle: {
                                        color: "white",
                                    },
                                },
                                splitLine: {
                                    show: true,
                                    lineStyle: {
                                        color: "rgba(255,255,255,0.3)",
                                    },
                                },
                                axisLabel: {},
                            },
                            series: [
                                {
                                    name: "入库量",
                                    type: "bar",
                                    barGap: 0,
                                    barWidth: "15%",
                                    data: map4Data1,
                                },
                                {
                                    name: "汽运量",
                                    type: "bar",
                                    barGap: 0,
                                    barWidth: "15%",
                                    data: map4Data2,
                                },
                            ],
                        });
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            },
            // 船期水运状态
            peiFn() {
                const data = this.statusData;
                const id = "CQZTpie";
                // 黄色内环
                var img = "data:image/png;base64,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";
                let idChart = echarts.init(document.getElementById(id));
                var dataArr = [];
                var color = ["#00ffff", "#00cfff", "#006ced", "#ffe000", "#ffa800", "#ff5b00", "#ff3000", "#00ffff"];
                for (var i = 0; i < data.length; i++) {
                    dataArr.push(
                        {
                            value: data[i].statusCount,
                            name: data[i].shipStatusName,
                            itemStyle: {
                                normal: {
                                    borderWidth: 5,
                                    shadowBlur: 20,
                                    borderColor: color[i],
                                    shadowColor: color[i],
                                },
                            },
                        },
                        {
                            // 目的是间隔
                            value: 1,
                            name: "",
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: false,
                                    },
                                    labelLine: {
                                        show: false,
                                    },
                                    color: "rgba(0, 0, 0, 0)",
                                    borderColor: "rgba(0, 0, 0, 0)",
                                    borderWidth: 0,
                                },
                            },
                        },
                    );
                }
                var seriesOption = [
                    {
                        name: "状态",
                        type: "pie",
                        clockWise: false,
                        radius: [70, 75],
                        hoverAnimation: false,
                        itemStyle: {
                            normal: {
                                label: {
                                    show: true,
                                    position: "outside",
                                    color: "#ddd",
                                    formatter: function (params) {
                                        if (params.name) {
                                            // 过滤间隔的元素
                                            return params.name + ":" + params.value;
                                        }
                                    },
                                },
                            },
                        },
                        data: dataArr,
                    },
                ];
                var option = {
                    backgroundColor: "rgba(255,255,255,0)",
                    color: color,
                    title: {
                        text: "船期状态",
                        top: "48%",
                        textAlign: "center", // title轮廓的中心位置
                        left: "49%",
                        textStyle: {
                            // title字体相关的样式
                            color: "#fff",
                            fontSize: 18,
                            fontWeight: "400",
                        },
                    },
                    graphic: {
                        elements: [
                            {
                                type: "image",
                                z: 3,
                                style: {
                                    image: img,
                                    width: 128,
                                    height: 128,
                                },
                                left: "center",
                                top: "center",
                            },
                        ],
                    },
                    // tooltip: {
                    //     trigger: "item",
                    //     backgroundColor: "rgba(0,0,0,0.5)",
                    //     formatter: function (params) {
                    //         var percent = 0;
                    //         var total = 0;
                    //         for (var i = 0; i < data.length; i++) {
                    //             total += data[i].statusCount;
                    //         }
                    //         if (total == 0) {
                    //             percent = 0;
                    //         } else {
                    //             percent = ((params.value / total) * 100).toFixed(0);
                    //         }

                    //         return params.marker + '<span style="color:' + params.color + '">' + params.data["name"] + "\n" + params.data["value"] + "</span></br>" + "占百分比：" + percent + "%";
                    //     },
                    // },
                    legend: {
                        show: true,
                        left: 20,
                        top: 20,
                        textStyle: {
                            color: "#fff",
                        },
                    },
                    toolbox: {
                        show: false,
                    },
                    series: seriesOption,
                };
                idChart.setOption(option);
            },
            // 疏港数据
            getTotalData(code) {
                listTotal({ tenantCode: code }).then((res) => {
                    this.totalData = res.data;
                });
            },
            // 业务趋势
            YWQSzheFn(code) {
                yearWaterTransList({ tenantCode: code }).then((res) => {
                    zhe(res.data);
                });
                function zhe(data) {
                    let SYdata = [];
                    let CQdata = [];
                    let DateData = [];
                    data.forEach((item) => {
                        CQdata.push({ value: item.shipDateCount, weight: item.monthWeight });
                        DateData.push(item.createMonth);
                    });
                    let idChart = echarts.init(document.getElementById("YWQSzhe"));
                    // 绘制图表--折线图
                    var idChartOption = {
                        tooltip: {
                            trigger: "axis",
                            formatter: function (value) {
                                return "数量:" + value[0].data.value + "艘<br/>重量:" + value[0].data.weight + "吨";
                            },
                        },
                        legend: {
                            data: ["船期"],
                            color: ["#fff"],
                            textStyle: {
                                color: "#fff",
                            },
                        },
                        grid: {
                            left: "6%",
                            right: "8%",
                            bottom: "3%",
                            containLabel: true,
                        },
                        xAxis: {
                            type: "category",
                            data: DateData,
                            // 横坐标轴颜色
                            axisLine: {
                                lineStyle: {
                                    color: "#14D5FF",
                                },
                            },
                        },
                        yAxis: {
                            type: "value",
                            // 横坐标轴颜色
                            axisLine: {
                                lineStyle: {
                                    color: "#14D5FF",
                                },
                            },
                            splitLine: {
                                lineStyle: {
                                    color: "#14D5FF",
                                },
                            },
                        },
                        series: [
                            {
                                name: "船期",
                                type: "line",
                                color: "#3BE232",
                                data: CQdata,
                            },
                        ],
                    };
                    idChart.setOption(idChartOption);
                }
            },
            // 获取当前时间
            getDate: function () {
                var _this = this;
                let yy = new Date().getFullYear();
                let mm = new Date().getMonth() + 1;
                let dd = new Date().getDate();
                let week = new Date().getDay();
                let hh = new Date().getHours();
                let mf = new Date().getMinutes() < 10 ? "0" + new Date().getMinutes() : new Date().getMinutes();
                let weekStr = "";
                if (week == 1) {
                    weekStr = "星期一";
                } else if (week == 2) {
                    weekStr = "星期二";
                } else if (week == 3) {
                    weekStr = "星期三";
                } else if (week == 4) {
                    weekStr = "星期四";
                } else if (week == 5) {
                    weekStr = "星期五";
                } else if (week == 6) {
                    weekStr = "星期六";
                } else {
                    weekStr = "星期日";
                }
                _this.nowDate = yy + "/" + mm + "/" + dd + " " + hh + ":" + mf + " " + weekStr;
            },
        },
        computed: {
            optionSingleHeightTime() {
                return {
                    step: 0.5, // 数值越大速度滚动越快
                    limitMoveNum: this.orderData.length, // 开始无缝滚动的数据量 this.dataList.length
                    hoverStop: true, // 是否开启鼠标悬停stop
                    direction: 1, // 0向下 1向上 2向左 3向右
                    openWatch: true, // 开启数据实时监控刷新dom
                    singleHeight: 0, // 单步运动停止的高度(默认值0是无缝不停止的滚动) direction => 0/1
                    singleWidth: 0, // 单步运动停止的宽度(默认值0是无缝不停止的滚动) direction => 2/3
                    waitTime: 1200, // 单步运动停止的时间(默认值1000ms)
                };
            },
        },
        filters: {
            statusFormat: function (value) {
                switch (value) {
                    case 1001:
                        return "待装船";
                    case 1002:
                        return "装船中";
                    case 1003:
                        return "装船完成";
                    case 2001:
                        return "运输中";
                    case 2002:
                        return "已到港";
                    case 3001:
                        return "卸船中";
                    case 3002:
                        return "卸船完成";
                }
            },
            dateFormat(data) {
                // msg 为固定的参数 即是你需要过滤的数据
                var t = new Date(data); // row 表示一行数据, transferTime 表示要格式化的字段名称
                var month = t.getMonth() + 1 < 10 ? "0" + (t.getMonth() + 1) : t.getMonth() + 1;
                var date = t.getDate() < 10 ? "0" + t.getDate() : t.getDate();
                var hour = t.getHours() < 10 ? "0" + t.getHours() : t.getHours();
                var minutes = t.getMinutes() < 10 ? "0" + t.getMinutes() : t.getMinutes();
                var seconds = t.getSeconds() < 10 ? "0" + t.getSeconds() : t.getSeconds();
                var str = "";
                if (data == null || data == "") {
                    str = "--";
                } else {
                    str = t.getFullYear() + "-" + month + "-" + date + " " + hour + ":" + minutes + ":" + seconds;
                }
                return str;
            },
        },
        destroyed() {
            clearInterval(this.nowTimer);
        },
    };
</script>
<style lang="scss" scoped>
    .box {
        width: 100%;
        height: 100vh;
        overflow: hidden;
        background: url("../../assets/daping/bg.png") 100%;
        .head {
            width: 100%;
            height: 6vh;
            line-height: 6vh;
            display: flex;
            background: url("../../assets/daping/标题光条.png") no-repeat;
            background-size: 100% 100%;
            text-align: center;
            .img {
                height: 6vh;
                img {
                    height: 100%;
                }
            }
            .title {
                flex: 1;
                font-size: 30px;
                font-weight: 700;
                color: #03faec;
                margin-right: 60px;
            }
            .date {
                font-size: 16px;
                color: #ffdd17;
                line-height: 4vh;
            }
        }
        .container {
            width: 100%;
            height: 92vh;
            position: relative;
            margin-top: 2vh;
            .left {
                .top,
                .bottom {
                    box-sizing: border-box;
                    height: 46vh;
                    overflow: hidden;
                    width: 100%;
                    background: url("../../assets/daping/bg1.png") no-repeat;
                    background-size: 100% 100%;
                    .title {
                        height: 30px;
                        line-height: 30px;
                        color: #03faec;
                        font-size: 20px;
                        background: url("../../assets/daping/mk-title-bg.png") no-repeat;
                        background-size: 100% 100%;
                        span {
                            float: left;
                            margin-left: 10px;
                            font-size: 16px;
                        }
                    }
                }
            }
            .middle {
                .mapBox {
                    position: relative;
                    .title {
                        width: 100%;
                        color: #fff;
                        position: absolute;
                        top: 0;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        z-index: 999999999;
                        span {
                            font-size: 28px;
                        }
                    }
                    .map {
                        width: 100%;
                        height: 92vh;
                    }
                }
            }
            .right {
                .wrapper {
                    height: 92vh;
                    box-sizing: border-box;
                    overflow: hidden;
                    width: 100%;
                    background: url("../../assets/daping/bg1.png") no-repeat;
                    background-size: 100% 100%;
                    .title {
                        height: 30px;
                        line-height: 30px;
                        color: #03faec;
                        font-size: 20px;
                        background: url("../../assets/daping/mk-title-bg.png") no-repeat;
                        background-size: 100% 100%;
                        span {
                            float: left;
                            margin-left: 10px;
                            font-size: 16px;
                        }
                    }
                    .table {
                        .seamless-warp {
                            overflow: hidden;

                            ul {
                                margin: 0;
                                padding: 0;
                                color: #03faec;
                                li {
                                    background: rgba(3, 250, 236, 0.2);
                                    padding: 10px;
                                    font-size: 12px;
                                    margin-bottom: 10px;
                                    .itemA {
                                        display: flex;
                                        div {
                                            flex: 1;
                                        }
                                        p {
                                            margin-bottom: 10px;
                                        }
                                    }
                                    .itemB {
                                        font-size: 16px;
                                        display: flex;
                                        align-items: center;
                                        margin-bottom: 10px;
                                        p {
                                            white-space: nowrap;
                                            text-overflow: ellipsis;
                                            overflow: hidden;
                                            word-break: break-all;
                                            margin: 0;
                                        }
                                        span {
                                            margin: 0 10px;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
</style>
