<template>
  <div class="chuKuZuoyeWarp">
    <div class="chuKuZuoyeWarp-body">
      <!-- 标题 -->
      <div ref="searchBox">
        <div class="detailsTitle">
          <h4>班组设置</h4>
        </div>
        <!-- 查询条件 -->
        <div class="chuKuZuoyeWarp-body-queryIpt">
          <el-form :inline="true" :model="formInline" class="demo-form-inline">
            <el-form-item label="班组名称：">
              <el-input
                class="ipt"
                maxlength="20"
                style="width: 180px"
                v-model="formInline.teamName"
                placeholder="请输入班组名称"
                size="mini"
              ></el-input>
            </el-form-item>
            <el-form-item label="钢厂名称：">
              <el-input
                class="ipt"
                maxlength="20"
                style="width: 180px"
                v-model="formInline.steelMillName"
                placeholder="请输入钢厂名称"
                size="mini"
              ></el-input>
            </el-form-item>

            <el-form-item label="">
              <el-button
                type="primary"
                size="mini"
                @click="chaxun"
                btnCode="00LP001B001"
                v-if="btnFlg[0]"
                >查询</el-button
              >
              <el-button
                type="primary"
                size="mini"
                @click="chongZhi"
                btnCode="00LP001B002"
                v-if="btnFlg[1]"
                >重置</el-button
              >
              <el-button
                type="primary"
                size="mini"
                @click="addBtn"
                btnCode="00LP001B003"
                v-if="btnFlg[2]"
                >新增</el-button
              >
            </el-form-item>
          </el-form>
        </div>
      </div>
      <!--表格-->
      <div class="chuKuZuoyeWarp-body-table" :style="{ height: h }">
        <el-table
          height="100%"
          size="mini"
          ref="tabref"
          :data="cangchuTableData"
          style="width: 100%"
          class="elTab"
          :header-cell-style="{ background: '#F5F5F5', color: 'black' }"
        >
          <el-table-column
            type="index"
            align="center"
            label="序号"
            width="60"
          ></el-table-column>
          <el-table-column label="操作" align="center" fixed width="80px">
            <template slot-scope="scope">
              <div>
                <el-button
                type="text"
                size="small"
                btnCode="00LP001B004"
                v-if="btnFlg[3]"
                @click="detClick(scope.row)"
                >删除</el-button
              >
              </div>
              
            </template>
          </el-table-column>
          <el-table-column
            :show-overflow-tooltip="true"
            prop="teamName"
            label="班组名称"
            align="center"
          ></el-table-column>
          <el-table-column
            :show-overflow-tooltip="true"
            prop="steelMillName"
            label="钢厂名称"
            align="center"
          ></el-table-column>
          <el-table-column
            :show-overflow-tooltip="true"
            prop="createUser"
            label="创建人"
            align="center"
          ></el-table-column>
          <el-table-column
            :show-overflow-tooltip="true"
            prop="createTime"
            label="打包时间"
            align="center"
            :formatter="dateTimeFormat"
            width="180"
          ></el-table-column>
        </el-table>
        <!--分页-->
        <div class="pags" style="text-align: right">
          <el-pagination
            background
            @current-change="handleCurrentChangeA"
            :current-page="current"
            :page-size="pagesize"
            :page-sizes="[10, 20, 30, 40, 50]"
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="handleSizeChangeA"
            :total="total"
          ></el-pagination>
        </div>
      </div>
      <el-dialog title="新增班组" :visible.sync="addmark" width="40%" center>
        <el-form
          :rules="rules"
          :model="form"
          ref="form"
          label-width="100px"
          class="demo-dynamic"
          style="width: 400px; margin: 30px auto"
        >
          <el-form-item label="班组名称:" prop="teamName">
            <el-input
              class="ipt"
              maxlength="20"
              style="width: 180px"
              v-model="form.teamName"
              placeholder="请输入班组名称"
              size="mini"
            ></el-input>
          </el-form-item>
          <el-form-item label="钢厂名称:" prop="steelMillName">
            <el-select
              size="mini"
              v-model="form.steelMillName"
              placeholder="请选择钢厂名称"
              style="width: 180px"
              @change="select_status($event)"
            >
              <el-option
                v-for="(item, index) in gangchangList"
                :label="item.steelMillName"
                :key="item.steelMillNameId"
                :value="index"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="addmarkOK('form')">确定</el-button>
          <el-button @click="addmark = false">取消</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import DonMessage from '@/utils/message'
import { getBtn } from '@/utils/getButton'
import { dateTimeFormat, emptyFn } from '@/utils/util'
import { exportExcel } from '@/utils/fileDown.js'

import {
  tPackagTeamList,
  selectSpuAttributeList,
  tPackagTeamAdd,
  tPackagTeamDet,
  updatePackagRecord
} from '@/api/wms-stock'
// import "../../../node_modules/element-ui/lib/theme-chalk/index.css";
export default {
  inject: ['reload'],
  name: 'DeleteHistory',
  data () {
    return {
      dateTimeFormat: dateTimeFormat, // 列表时间格式化
      rules: {
        steelMillName: [
          { required: true, message: '请选择钢厂', trigger: 'blur' }
        ],
        teamName: [
          { required: true, message: '请输入班组名称', trigger: 'blur' }
        ]
      },
      // 按钮
      btnFlg: [],
      gangchangList: [],
      isDisable: false,
      addmark: false,
      ids: '',
      num: '0',
      selList: [],
      options: [],
      btnIdArr: ['00LP001B001', '00LP001B002', '00LP001B003', '00LP001B004'],
      h: '500px',
      cangchuTableData: [],
      form: {},
      formInline: { goodsFrom: '', printType: '' },
      pagesize: 20,
      current: 1,
      total: 0
    }
  },
  mounted () {
    let h = this.$refs.searchBox.offsetHeight // 头部搜索盒子高度
    let pH = document.documentElement.clientHeight // 浏览器高度
    this.h = pH - h - 105 + 'px'
    // 218 ->页面除盒子外的高度
  },
  methods: {
    select_status (index) {
      this.form.steelMillId = this.gangchangList[index].steelMillNameId
      this.form.steelMillName = this.gangchangList[index].steelMillName
    },
    detClick (row) {
      this.$confirm('是否确认删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          tPackagTeamDet({ packagTeamId: row.packagTeamId })
            .then((res) => {
              DonMessage.success('操作成功!')
              this.tPackagTeamListS()
            })
            .catch((err) => {
              DonMessage.error(err.message)
            })
        })
        .catch(() => {})
    },
    addBtn () {
      this.addmark = true
      this.form = {}
      if (this.$refs.form) {
        this.$refs.form.clearValidate()
      }
    },
    addmarkOK (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          tPackagTeamAdd(this.form)
            .then((res) => {
              DonMessage.success('操作成功!')
              this.tPackagTeamListS()
              this.addmark = false
              this.form = {}
            })
            .catch((err) => {
              DonMessage.error(err.message)
            })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    // 点击导出
    daochu () {
      this.isDisable = true
      setTimeout(() => {
        this.isDisable = false // 点击一次时隔两秒后才能再次点击
      }, 2000)
      if (this.formInline.cargoBarcode == undefined) {
        this.formInline.cargoBarcode = ''
      } else {
        this.formInline.cargoBarcode = this.formInline.cargoBarcode.replace(
          /\n/g,
          ','
        )
      }
      var reg = /^(?=.*\d.*\b)/
      if (!reg.test(this.formInline.cargoBarcode)) {
        this.formInline.cargoBarcode = ''
      }
      let form = {
        cargoBarcode: this.formInline.cargoBarcode, //	捆包号
        origin: this.formInline.origin, // 产地
        createUser: this.formInline.createUser, // 打包员
        goodsFrom: this.formInline.goodsFrom, // 货物来源 0：手动，1：扫码
        createTimeStart: this.formInline.createTimeStart, // 日期开始
        createTimeEnd: this.formInline.createTimeEnd, // 日期结束
        printType: this.formInline.printType // 打印类型：0：无打印 1：未打印 2：已打印'
      }
      // fetch(
      exportExcel(
        this.global.wmsStockName + '/packag/exportPackagRecordList',
        form,
        '打包记录表'
      )
    },
    forceInput () {
      this.$forceUpdate()
    },
    selChange (selection) {
      console.log(selection, 'selList')
      this.selList = []
      this.selList = selection
    },
    // 点击打印
    dayin (row) {
      if (this.selList.length == 0) {
        DonMessage.error('请选择要操作的数据!')
      } else {
        let ids = []
        this.selList.forEach((item, index) => {
          ids.push(item.packagId)
        })
        this.ids = ids.toString()

        updatePackagRecord({
          ids: this.ids
        })
          .then((res) => {
            this.$router.push({
              path: '/packingRecordDayin',
              query: {
                list: JSON.stringify(this.selList)
              }
            })
          })
          .catch((err) => {
            DonMessage.error(err.message)
          })
      }
    },

    // 主数据
    tPackagTeamListS () {
      tPackagTeamList({
        pageNo: this.current, // 页数
        pageSize: this.pagesize, // 条数
        teamName: this.formInline.teamName, // （班组名称 非必填）
        steelMillName: this.formInline.steelMillName // （钢厂名称 非必填）
      })
        .then((res) => {
          this.total = res.total
          this.cangchuTableData = res.list
        })
        .catch((err) => {
          DonMessage.error(err.message)
        })
    },
    // 查询
    chaxun () {
      this.tPackagTeamListS()
    },
    // 重置
    chongZhi () {
      this.formInline = { goodsFrom: '', printType: '' }
      this.tPackagTeamListS()
    },

    // 表格分页
    handleCurrentChangeA (val) {
      this.current = val
      this.tPackagTeamListS()
    },
    handleSizeChangeA (val) {
      this.pagesize = val
      this.tPackagTeamListS()
    }
  },
  created () {
    this.btnFlg = getBtn(this.btnIdArr, '/packingBanzu')

    this.tPackagTeamListS()
    selectSpuAttributeList({
      spuName: '包装类别'
    })
      .then((res) => {
        // this.total = res.total
        this.gangchangList = res
        let newobj = {}
        this.gangchangList = this.gangchangList.reduce((preVal, curVal) => {
          newobj[curVal.steelMillNameId]
            ? ''
            : (newobj[curVal.steelMillNameId] = preVal.push(curVal))
          return preVal
        }, [])
      })
      .catch((err) => {
        DonMessage.error(err.message)
      })
  }
}
</script>

<style lang="less" scoped>
.huoQuanChangeWarp-body-title {
  width: 100%;
  height: 35px;
  border-bottom: 1px solid #ccc;
  padding: 5px;
  box-sizing: border-box;
  span {
    font-size: 14px;
    font-weight: 700;
    line-height: 20px;
    color: #2f2f2f;
    display: inline;
    line-height: 30px;
    // margin-left: 20px;
  }
}
.chuKuZuoyeWarp {
  width: 100%;
  position: relative;
  height: 100%;
  .pags {
    width: 100%;
    text-align: center;
  }
  // border: solid 1px blue;
  .chuKuZuoyeWarp-body {
    width: 100%;
    position: absolute;

    .chuKuZuoyeWarp-body-queryIpt {
      width: 100%;
      display: flex;
      font-size: 14px;
      ul {
        padding-left: 10px;
        margin: 0;
        flex: 1;
        flex-direction: column;
        // display: flex;
        li {
          float: left;
          margin-top: 5px;
          margin-left: 10px;
          list-style: none;
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
  /deep/ .el-textarea .el-textarea__inner {
    resize: none;
  }
}
</style>
