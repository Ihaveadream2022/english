<template>
  <div>
    <div style="display: flex; margin-bottom: 20px; margin-left: 40px">
      <el-page-header
        @back="back"
        content="打印标签"
        style="margin-right: 20px"
      ></el-page-header>
      <button type="primary" @click="printContext">打印</button>
    </div>
    <div id="printMe" ref="print">
      <!-- 打印 -->
      <div v-for="(item, index) in list" :key="index" class="div">
      <!-- <div class="table_title">产品标识码</div> -->

        <table >
          <tbody>
            <tr>
              <td class="name" colspan="4">{{ item.origin }}</td>
            </tr>
            <!-- <tr>
              <td class="name" colspan="4">产品标识码</td>
            </tr> -->
            <tr>
              <td class="">捆包号</td>
              <td class="" colspan="3">{{ item.cargoBarcode }}</td>
            </tr>
              <tr>
              <td class="">客户名称</td>
              <td class="" colspan="3">{{ item.customerName }}</td>
            </tr>
            <tr>
              <td class="">重量(t)</td>
              <td class="" >{{ item.weight }}</td>
              <td class="">材质</td>
              <td class="" >{{ item.model }}</td>
            </tr>

            <tr>
              <td class="">品名</td>
              <td class="" colspan="2">{{ item.productName }}</td>
              <td style="padding:5px" rowspan="5">
                <qriously :id="'qriously'+index" :value="item.qrCode" :size="135" v-show="getFlag"/>
                <img :src="item.src" alt="" v-show="!getFlag">
                </td>

            </tr>
            <!-- <tr>
              <td class="">材质</td>
              <td class="" colspan="2">{{ item.model }}</td>
            </tr> -->
             <tr>
              <td class="">规格</td>
              <td class="" colspan="2">{{ item.specification }}</td>
            </tr>
            <!-- <tr>
              <td class="">重量（吨）</td>
              <td class="" colspan="2">{{ item.weight }}</td>
            </tr>
            <tr>
              <td class="">数量</td>
              <td class="" colspan="2">1</td>
            </tr> -->
            <tr>
              <td class="">生产日期</td>
              <td class="" colspan="2">{{ item.createTime | formatDate }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import {
  updatePackagRecord // 主数据-打印
} from '@/api/wms-stock'
import DonMessage from '@/utils/message'
export default {
  data () {
    return {
      totalNum: 0,
      totalWeightDouble: 0,
      id: '',
      ids: '',
      initQCode: '',
      idAll: '',
      type: 0,
      getData: null,
      flag: 0,
      getFlag: true,
      list: []
    }
  },
  created () {
    // 生命周期里接收参数
    var arr = this.$route.query.list // 接受参数关键代码
    this.list = eval('(' + arr + ')')

    // this.$nextTick(function () {
    //   debugger
    //   this.list.forEach((item, index) => {
    //     // item.qrCode = this.utf16to8(item.qrCode)
    //     item.src = ''
    //     let canvas = document.querySelector('#qriously canvas')
    //     item.src = canvas.toDataURL('image/png')
    //   })
    // })
  },
  mounted () {
    this.list.forEach((item, index) => {
      item.qrCode = this.utf16to8(item.qrCode)
      item.src = ''
      let canvas = document.querySelector('#qriously' + index + ' ' + 'canvas')

      item.src = canvas.toDataURL('image/png')
    })
  },
  filters: {
    formatDate: function (value) {
      let date = new Date(value)
      let y = date.getFullYear()
      let MM = date.getMonth() + 1
      MM = MM < 10 ? '0' + MM : MM
      let d = date.getDate()
      d = d < 10 ? '0' + d : d
      let h = date.getHours()
      h = h < 10 ? '0' + h : h
      let m = date.getMinutes()
      m = m < 10 ? '0' + m : m
      return y + '-' + MM + '-' + d + ' ' + h + ':' + m
    }
  },
  methods: {
    // 打印内容
    printContext () {
      this.getFlag = false
      this.list.forEach((item, index) => {
      // item.qrCode = this.utf16to8(item.qrCode)
        item.src = ''
        let canvas = document.querySelector('#qriously' + index + ' ' + 'canvas')
        item.src = canvas.toDataURL('image/png')
      })
      setTimeout(() => {
        this.$print(this.$refs.print)
      }, 500)
    },
    // 二维码支持中文
    utf16to8 (str) {
      var out, i, len, c
      out = ''
      len = str.length
      for (i = 0; i < len; i++) {
        c = str.charCodeAt(i)
        if (c >= 0x0001 && c <= 0x007f) {
          out += str.charAt(i)
        } else if (c > 0x07ff) {
          out += String.fromCharCode(0xe0 | ((c >> 12) & 0x0f))
          out += String.fromCharCode(0x80 | ((c >> 6) & 0x3f))
          out += String.fromCharCode(0x80 | ((c >> 0) & 0x3f))
        } else {
          out += String.fromCharCode(0xc0 | ((c >> 6) & 0x1f))
          out += String.fromCharCode(0x80 | ((c >> 0) & 0x3f))
        }
      }
      return out
    },
    // 点击返回上一步
    back () {
      this.$router.go(-1) // 返回上一层
    }
  }
}
</script>

<style scoped lang='less'>
button {
  width: 100px !important;
}
.name {
 font-weight: 700;
 font-size: 20px;
}
ul {
  border: 1px solid black;
  width: 75%;
  position: relative;
  left: 14%;
  .li {
    position: absolute;
    right: 2px;
    bottom: 2px;
    height: 120px;
    width: 120px;
    text-align: center;
    .qriously {
      height: 100%;
    }
    // border: 1px solid black;
  }
  li {
    display: flex;
    width: 100%;

    .span1 {
      text-align: center;
      width: 30%;
      border-right: 1px solid black;
      border-bottom: 1px solid black;
    }
    .span2 {
      text-align: center;
      width: 70%;
      border-bottom: 1px solid black;
    }
    .span3 {
      text-align: center;
      width: 30%;
      word-wrap: break-word;
      word-break: normal;
      border-right: 1px solid black;
      border-bottom: 1px solid black;
    }
  }
}
.div{
  // margin-top: 60px;
  // margin-bottom: 20px;
  // height: 300px;
  // height: 230px;
  // line-height: 300px;

}
#printMe {
  font-size: 10px;
  position: relative;
  // padding: 10px;
  // margin-bottom: 10px;
  box-sizing: border-box;
  //   height: 500px;
  width: 100%;

  .table_title {
    width: 100%;
    height: 30px;
    font-size: 20px;
    text-align: center;
    font-weight: 700;
    // margin: 10 0;
  }
}

table,
td {
  border: 1px solid blue;

}
table {
  border-collapse: collapse;
}

table,
th,
td {
  border: 1px solid black;
}
table {
  width: 100%;
  margin: 0 auto;
  margin-bottom: 10px;
  td {
    // width: 17%;
    // padding: 4px 4px;
    text-align: center;
    height: 32px;
  }
  .td {
    // width: 17%;
  }
  .td1 {
    width: 14%;
  }
  .kunbao {
    width: 17%;
  }
}
</style>
