import CryptoJS from 'crypto-js'
/**
 *加密处理
 */
export const encryption = (params) => {
  let {
    data,
    type,
    param,
    key
  } = params
  const result = JSON.parse(JSON.stringify(data))
  if (type === 'Base64') {
    param.forEach(ele => {
      result[ele] = btoa(result[ele])
    })
  } else {
    key = CryptoJS.enc.Latin1.parse(key)
    param.forEach(ele => {
      var data = result[ele]
      var iv = key
      // 加密
      var encrypted = CryptoJS.AES.encrypt(
        data,
        key, {
          iv: iv,
          mode: CryptoJS.mode.CBC,
          padding: CryptoJS.pad.ZeroPadding
        })
      result[ele] = encrypted.toString()
    })
  }
  return result
}
// 方向转化
export const direction = (angle) => {
  // 北方向
  if (angle >= 0 && angle <= 22 || angle >= 338 && angle <= 360) {
    return '1'
  }
  // 东北方向
  if (angle >= 23 && angle <= 67) {
    return '2'
  }
  // 东方向
  if (angle >= 68 && angle <= 112) {
    return '3'
  }
  // 东南方向
  if (angle >= 113 && angle <= 157) {
    return '4'
  }
  // 南方向
  if (angle >= 158 && angle <= 202) {
    return '5'
  }
  // 西南方向
  if (angle >= 203 && angle <= 247) {
    return '6'
  }
  // 西方向
  if (angle >= 248 && angle <= 292) {
    return '7'
  }
  // 西北方向
  if (angle >= 293 && angle <= 337) {
    return '8'
  }
}
// 表格空数据处理
export const emptyFn = (row, column, cellValue) => {
  let str = ''
  if (cellValue || cellValue === 0) {
    if (column.property.search('Price') != -1 || column.property.search('taxation') != -1) {
      str = format(2, cellValue)
    } else if (column.property.search('Money') != -1) {
      str = format(2, cellValue)
    } else if (column.property.search('eight') != -1) {
      if (row.unitMeasurement) {
        str = cellValue + row.unitMeasurement
      } else {
        str = format(3, cellValue)
      }
    } else {
      str = cellValue
    }
  } else {
    str = '-'
  }
  function format (n, num) {
    var result = parseFloat(num)
    let str = ''
    if (isNaN(result)) {
      str = num
    } else {
      let a = Math.pow(10, n)
      result = Math.round(num * a) / a
      var s_x = result.toString()
      var pos_decimal = s_x.indexOf('.')
      if (pos_decimal < 0) {
        pos_decimal = s_x.length
        s_x += '.'
      }
      while (s_x.length <= pos_decimal + n) {
        s_x += '0'
      }
      str = s_x
    }

    return str
  }
  return str
}
// 时间格式化
// flag 1:'YYYY-MM-dd hh-mm'
export const dateTimeFormat = (row, column, cellValue, index, flag) => {
  var t = new Date(cellValue) // row 表示一行数据, transferTime 表示要格式化的字段名称
  var month =
    t.getMonth() + 1 < 10 ? '0' + (t.getMonth() + 1) : t.getMonth() + 1
  var date = t.getDate() < 10 ? '0' + t.getDate() : t.getDate()
  var hour = t.getHours() < 10 ? '0' + t.getHours() : t.getHours()
  var minutes = t.getMinutes() < 10 ? '0' + t.getMinutes() : t.getMinutes()
  var seconds = t.getSeconds() < 10 ? '0' + t.getSeconds() : t.getSeconds()
  var str = ''
  if (cellValue == null) {
    str = ''
  } else if (flag == 1) {
    str = t.getFullYear() + '-' + month + '-' + date + ' ' + hour + ':' + minutes
  } else {
    str =
      t.getFullYear() +
      '-' +
      month +
      '-' +
      date +
      ' ' +
      hour +
      ':' +
      minutes +
      ':' +
      seconds
  }
  return str
}
// 时间格式化
export const dateTimeFormat1 = (row, column, cellValue) => {
  var t = new Date(cellValue) // row 表示一行数据, transferTime 表示要格式化的字段名称
  var month =
    t.getMonth() + 1 < 10 ? '0' + (t.getMonth() + 1) : t.getMonth() + 1
  var date = t.getDate() < 10 ? '0' + t.getDate() : t.getDate()
  var hour = t.getHours() < 10 ? '0' + t.getHours() : t.getHours()
  var minutes = t.getMinutes() < 10 ? '0' + t.getMinutes() : t.getMinutes()
  var seconds = t.getSeconds() < 10 ? '0' + t.getSeconds() : t.getSeconds()
  var str = ''
  if (cellValue == null || cellValue == '' || cellValue == undefined) {
    str = ''
  } else {
    str =
      t.getFullYear() +
      '-' +
      month +
      '-' +
      date +
      ' ' +
      hour +
      ':' +
      minutes +

      ':' +
      seconds
  }
  return str
}
// 日期格式化
export const dateFormat = (row, column, cellValue) => {
  var t = new Date(cellValue) // row 表示一行数据, transferTime 表示要格式化的字段名称
  var month =
    t.getMonth() + 1 < 10 ? '0' + (t.getMonth() + 1) : t.getMonth() + 1
  var date = t.getDate() < 10 ? '0' + t.getDate() : t.getDate()
  var str = ''
  if (cellValue == null) {
    str = ''
  } else {
    str =
      t.getFullYear() +
      '-' +
      month +
      '-' +
      date
  }
  return str
}

export const dateFormat1 = (date, type = '-') => {
  if (date == null) {
    return
  }
  const dat = new Date(date)
  const year = dat.getFullYear()
  const month = (dat.getMonth() + 1).toString().padStart(2, 0)
  const day = (dat.getDate()).toString().padStart(2, 0)
  const hours = (dat.getHours()).toString().padStart(2, 0)
  const minutes = (dat.getMinutes()).toString().padStart(2, 0)
  const second = (dat.getSeconds()).toString().padStart(2, 0)
  if (type == 'CHN') {
    return `${year}年${month}月${day}日`
  } else if (type == '/') {
    return `${year}/${month}/${day}`
  } else if (type == 'CHN-YYYY-MM-ss h-m-s') {
    return `${year}年${month}月${day}日 ${hours}:${minutes}:${second}`
  } else if (type == 'YYYY-MM-ss') {
    return `${year}-${month}-${day}`
  } else if (type == 'YYYY-MM-ss h-0-0') {
    return `${year}-${month}-${day} ${hours}:00:00`
  } else if (type == 'YYYY-MM') {
    return `${year}-${month}`
  } else {
    return `${year}-${month}-${day} ${hours}:${minutes}:${second}`
  }
}
export const toFixed = (num, n) => {
  if (n > 20 || n < 0) {
    throw new RangeError('toFixed() digits argument must be between 0 and 20')
  }
  const number = num
  if (isNaN(number) || number >= Math.pow(10, 21)) {
    return number.toString()
  }
  if (typeof (n) == 'undefined' || n == 0) {
    return (Math.round(number)).toString()
  }

  let result = number.toString()
  const arr = result.split('.')

  // 整数的情况
  if (arr.length < 2) {
    result += '.'
    for (let i = 0; i < n; i += 1) {
      result += '0'
    }
    return result
  }

  const integer = arr[0]
  const decimal = arr[1]
  if (decimal.length == n) {
    return result
  }
  if (decimal.length < n) {
    for (let i = 0; i < n - decimal.length; i += 1) {
      result += '0'
    }
    return result
  }
  result = integer + '.' + decimal.substr(0, n)
  const last = decimal.substr(n, 1)

  // 四舍五入，转换为整数再处理，避免浮点数精度的损失
  if (parseInt(last, 10) >= 5) {
    const x = Math.pow(10, n)
    result = (Math.round((parseFloat(result) * x)) + 1) / x
    result = result.toFixed(n)
  }

  return result
}
/* eslint no-extend-native: ["error", { "exceptions": ["Number"] }] */
Number.prototype.toFixed = function (n) {
  if (n > 20 || n < 0) {
    throw new RangeError('toFixed() digits argument must be between 0 and 20')
  }
  const number = this
  if (isNaN(number) || number >= Math.pow(10, 21)) {
    return number.toString()
  }
  if (typeof (n) == 'undefined' || n == 0) {
    return (Math.round(number)).toString()
  }

  let result = number.toString()
  const arr = result.split('.')

  // 整数的情况
  if (arr.length < 2) {
    result += '.'
    for (let i = 0; i < n; i += 1) {
      result += '0'
    }
    return result
  }

  const integer = arr[0]
  const decimal = arr[1]
  if (decimal.length == n) {
    return result
  }
  if (decimal.length < n) {
    for (let i = 0; i < n - decimal.length; i += 1) {
      result += '0'
    }
    return result
  }
  result = integer + '.' + decimal.substr(0, n)
  const last = decimal.substr(n, 1)

  // 四舍五入，转换为整数再处理，避免浮点数精度的损失
  if (parseInt(last, 10) >= 5) {
    const x = Math.pow(10, n)
    result = (Math.round((parseFloat(result) * x)) + 1) / x
    result = result.toFixed(n)
  }

  return result
}
// 无内容显示占位符(疏港列表重量无内容处理)
export const formatterRowEmpty = (row, column, cellValue, index) => {
  // console.log(Boolean(cellValue), cellValue);
  if (!cellValue) {
    return '-'
  } else {
    return cellValue
  }
}
// 数组差集 flag 1、求A数组比B数组多得值，2、求B数组比A数组多得值
export const arrChaJi = (arrA, arrB, flag) => {
  let jiaoArr = arrA.filter(function (val) { return arrB.indexOf(val) > -1 })
  let resultArr = []
  if (flag == 1) {
    resultArr = arrA.filter(function (val) { return jiaoArr.indexOf(val) === -1 })
  } else {
    resultArr = arrB.filter(function (val) { return jiaoArr.indexOf(val) === -1 })
  }
  return resultArr
}

/*
 * JS 时间格式化
 * type 时间格式（yyyy-MM-dd hh:ii:ss / MM-dd / hh:ii / yyyy-MM）可自定义
 * date 毫秒时间戳（1554954127000）
 * 使用：timeFormat('yyyy-MM-dd hh:ii:ss',1554954127000)
 * 说明：紧支持毫秒级时间戳，传统秒级 Unix 时间戳需要乘以 1000 转换为毫秒
 */
export const dateFormatUtil = (type, date) => {
  var date = new Date(date)
  var o = {
    'M+': date.getMonth() + 1,	// 月份
    'd+': date.getDate(),		// 日
    'h+': date.getHours(),		// 小时
    'i+': date.getMinutes(),	// 分
    's+': date.getSeconds()	// 秒
  }
  if (/(y+)/.test(type)) {
    type = type.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
  };
  for (var k in o) {
    if (new RegExp('(' + k + ')').test(type)) {
      type = type.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
    };
  }
  return type
}
// 一维数组根据相同的属性值分成二维数组
export function sortArr (arr, str) {
  var _arr = []
  var _t = []
  // 临时的变量
  var _tmp

  // 按照特定的参数将数组排序将具有相同值得排在一起
  arr = arr.sort(function (a, b) {
    var s = a[str]
    var t = b[str]

    return s < t ? 1 : -1
  })

  if (arr.length) {
    _tmp = arr[0][str]
  }
  // console.log( arr );
  // 将相同类别的对象添加到统一个数组
  for (var i in arr) {
    // console.log( _tmp);
    if (arr[i][str] === _tmp) {
      // console.log(_tmp)
      _t.push(arr[i])
    } else {
      _tmp = arr[i][str]
      _arr.push(_t)
      _t = [arr[i]]
    }
  }
  // 将最后的内容推出新数组
  _arr.push(_t)
  return _arr
}
// 数字转换为大写金额
export function ToString (n) {
  if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n)) {
    return '数据非法' // 判断数据是否大于0
  }

  var unit = '仟佰拾亿仟佰拾万仟佰拾元角分'
  var str = ''
  n += '00'

  var indexpoint = n.indexOf('.') // 如果是小数，截取小数点前面的位数

  if (indexpoint >= 0) {
    n = n.substring(0, indexpoint) + n.substr(indexpoint + 1, 2) // 若为小数，截取需要使用的unit单位
  }

  unit = unit.substr(unit.length - n.length) // 若为整数，截取需要使用的unit单位
  for (var i = 0; i < n.length; i++) {
    str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i) // 遍历转化为大写的数字
  }
  console.log(str)

  return str.replace(/零(仟|佰|拾|角)/g, '零').replace(/(零)+/g, '零').replace(/零(万|亿|元)/g, '$1').replace(/(亿)万|壹(拾)/g, '$1$2').replace(/^元零?|零分/g, '').replace(/元$/g, '元整') // 替换掉数字里面的零字符，得到结果
}
