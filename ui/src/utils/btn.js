import stores from "../stores";

/**
 * btnIdArr是总后台CRM配置的所有菜单,
 * stores.getters["auth/MENU_INFO"]是租户CRM系统分配给业务账号的权限菜单。
 * 将他们进行比对，排除业务账号不具备的权限菜单
 * 该方式只与显示与否有关，与接口请求无关
 * 返回按钮的数据库ID字段组成的数组
 */
export function getBtn(btnIdArr, url) {
    let flagArr = [];
    let id = getQueryVariable("menuOrBtnId");
    let MorBlist = null;
    if (id) {
        // 地址后面有id得时候取id，没有得时候用url
        MorBlist = getChidlren(id);
    } else {
        // MorBlist = getChidlren(null, window.location.href.substring(window.location.href.indexOf('#') + 1))
        MorBlist = getChidlren(null, url);
    }
    // 获取的权限菜单内的按钮，存在于btnIdArr中时，放入返回数组flagArr中
    btnIdArr.forEach((item, index) => {
        let id = "";
        if (MorBlist) {
            let flag = MorBlist.some((e) => {
                if (e.code == item) {
                    id = e.id;
                }
                return e.code == item;
            });
            if (flag) {
                flagArr.push(id);
            } else {
                flagArr.push(false);
            }
        }
    });
    return flagArr;
}
function getQueryVariable(variable) {
    var src = window.location.href;
    let query = src.slice(src.lastIndexOf("?") + 1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] === variable) {
            return pair[1];
        }
    }
    return false;
}
function getChidlren(id, url) {
    var hasFound = false; // 表示是否有找到id值
    var result = null;
    let data = stores.getters["auth/MENU_INFO"];
    var fn = function (data) {
        if (Array.isArray(data) && !hasFound) {
            // 判断是否是数组并且没有的情况下，
            data.forEach((item) => {
                if (id) {
                    if (item.id == id) {
                        // 数据循环每个子项，并且判断子项下边是否有id值
                        result = item.buttonDetails; // 返回的结果等于每一项
                        hasFound = true; // 并且找到id值
                    } else if (item.childMenu || item.buttonDetails) {
                        if (item.buttonDetails) {
                            fn(item.buttonDetails); // 递归调用下边的子项
                        } else {
                            fn(item.childMenu); // 递归调用下边的子项
                        }
                    }
                } else {
                    if (item.accessUrl == url) {
                        // 数据循环每个子项，并且判断子项下边是否有id值
                        result = item.buttonDetails; // 返回的结果等于每一项
                        hasFound = true; // 并且找到id值
                    } else if (item.childMenu || item.buttonDetails) {
                        if (item.buttonDetails) {
                            fn(item.buttonDetails); // 递归调用下边的子项
                        } else {
                            fn(item.childMenu); // 递归调用下边的子项
                        }
                    }
                }
            });
        }
    };
    fn(data); // 调用一下
    return result;
}
