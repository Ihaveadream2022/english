function dataURLtoFile(dataurl, filename = "file") {
    let arr = dataurl.split(",");
    let mime = arr[0].match(/:(.*?);/)[1];
    let suffix = mime.split("/")[1]; // 获取图片的后缀，png jpg等
    let bstr = atob(arr[1]); // 对base64进行解码
    let n = bstr.length;
    let u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], `${filename}.${suffix}`, {
        type: mime,
    });
}
// 压缩图片
export function compressImage(path, file, config) {
    return new Promise((resolve, reject) => {
        var img = new Image();
        img.src = path;
        const isLt2M = file.size / 1024 / 1024 < 4;
        img.onload = function () {
            if (isLt2M) {
                resolve(file);
            } else {
                var that = this;
                var w = that.width;
                var h = that.height;
                var scale = w / h;
                if (w > config.width) {
                    w = config.width || config.height * scale;
                    h = config.height || config.width / scale;
                }
                var quality = 1; // 默认图片质量为1
                // 生成canvas
                var canvas = document.createElement("canvas");
                var ctx = canvas.getContext("2d");
                // 创建属性节点
                var anw = document.createAttribute("width");
                anw.nodeValue = w;
                var anh = document.createAttribute("height");
                anh.nodeValue = h;
                canvas.setAttributeNode(anw);
                canvas.setAttributeNode(anh);
                ctx.drawImage(that, 0, 0, w, h);
                if (config.quality && config.quality <= 1 && config.quality > 0) {
                    quality = config.quality;
                }
                var base64 = canvas.toDataURL("image/*", quality);
                let files = dataURLtoFile(base64);
                // var blob = convertBase64UrlToBlob(base64)
                // 回调函数返回base64的值，也可根据自己的需求返回blob的值
                resolve(files);
            }
        };
    });
}
