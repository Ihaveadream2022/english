const canvasElem = document.getElementById("canvas");
const recognizeResult = document.getElementById("recognize-result");
const width = document.body.clientWidth;
const height = document.body.clientHeight * 0.92;
canvasElem.width = width;
canvasElem.height = height;
function canvasShow() {
    if (canvasElem.style.display === "none" || canvasElem.style.display === "") {
        canvasElem.style.display = "block";
        recognizeResult.style.display = "block";
    } else {
        canvasElem.style.display = "none";
        recognizeResult.style.display = "none";
    }
}

var canvas = new handwriting.Canvas(canvasElem, 3);

canvas.setCallBack(function (data, err) {
    if (err) {
        alert(err);
        throw err;
    } else {
        document.getElementById("recognize-result").innerHTML = data[0];
        recognize(data[0]);
    }
});
canvas.setLineWidth(5);

//Set options
canvas.setOptions({
    language: "en",
    numOfReturn: 1,
});

var paintMode = false;
var x = 918,
    y = 549;
document.onmousemove = function (e) {
    x = e.clientX;
    y = e.clientY;
};
document.addEventListener("keydown", (event) => {
    if (event.code === "KeyP") {
        if (!paintMode) {
            paintMode = true;
            document.getElementById("paintMode").innerHTML = "ON";
            sendMouseEvent();
        }
    }
});
document.addEventListener("keyup", (event) => {
    if (event.code === "KeyP") {
        if (paintMode) {
            paintMode = false;
            document.getElementById("paintMode").innerHTML = "OFF";
            sendMouseEvent();
        }
    }
});
function sendMouseEvent() {
    var eventName = paintMode ? "mousedown" : "mouseup";
    var element = document.getElementById("canvas");
    var clickEvent = document.createEvent("MouseEvents");
    clickEvent.initMouseEvent(eventName, true, true, window, 0, 0, 0, x, y, false, false, false, false, 0, null);
    element.dispatchEvent(clickEvent);
}
