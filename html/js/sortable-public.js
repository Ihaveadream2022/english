if (window.location.host.includes("github")) {
    var domainPrefix = "/english/html";
} else {
    var domainPrefix = "";
}
const ulElementCn = document.getElementById("cn");
const ulElementEn = document.getElementById("en");
const voice = document.getElementById("voice");
var colors = ["rgb(103, 39, 223)", "rgb(189, 83, 111)", "rgb(42, 135, 14)", "rgb(201, 196, 182)", "rgb(28, 186, 216)", "rgb(63, 55, 231)", "rgb(153, 48, 244)", "rgb(7, 239, 225)", "rgb(247, 42, 195)", "rgb(31, 106, 124)", "rgb(169, 82, 61)", "rgb(108, 216, 86)", "rgb(68, 124, 174)", "rgb(19, 233, 169)", "rgb(233, 167, 68)", "rgb(98, 155, 222)", "rgb(239, 107, 60)", "rgb(22, 68, 22)", "rgb(199, 253, 255)", "rgb(152, 107, 161)"];
document.addEventListener("DOMContentLoaded", function () {
    // 获取 Pre 和 Next 按钮元素
    var prevBtn = document.getElementById("prevBtn");
    var nextBtn = document.getElementById("nextBtn");
    // 获取当前页数和总页数元素
    var curPageElement = document.getElementById("curpage");
    var totalPageElement = document.getElementById("total");
    // 初始当前页数和总页数
    var currentPage = parseInt(curPageElement.textContent);
    var totalPages = parseInt(totalPageElement.textContent);
    // 添加点击事件监听器
    prevBtn.addEventListener("click", function () {
        if (currentPage > 1) {
            currentPage--;
            curPageElement.textContent = currentPage.toString();
            fetchData(currentPage);
        }
    });
    nextBtn.addEventListener("click", function () {
        if (currentPage < totalPages) {
            currentPage++;
            curPageElement.textContent = currentPage.toString();
            fetchData(currentPage);
        }
    });
});
function fetchData(page) {
    fetch(domainPrefix + "/json/" + page + ".json")
        .then((response) => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then((data) => {
            const words = data;
            const enArray = shuffleArray(words);
            const cnArray = shuffleArray(words);
            colors = ["rgb(103, 39, 223)", "rgb(189, 83, 111)", "rgb(42, 135, 14)", "rgb(201, 196, 182)", "rgb(28, 186, 216)", "rgb(63, 55, 231)", "rgb(153, 48, 244)", "rgb(7, 239, 225)", "rgb(247, 42, 195)", "rgb(31, 106, 124)", "rgb(169, 82, 61)", "rgb(108, 216, 86)", "rgb(68, 124, 174)", "rgb(19, 233, 169)", "rgb(233, 167, 68)", "rgb(98, 155, 222)", "rgb(239, 107, 60)", "rgb(22, 68, 22)", "rgb(199, 253, 255)", "rgb(152, 107, 161)"];
            initUI(enArray, cnArray);
            var listItems = $("li");
            listItems.click(function () {
                $(this).siblings().removeClass("active");
                $(this).addClass("active");
                var enActive = $("#en").find("li.active").eq(0);
                var cnAvtive = $("#cn").find("li.active").eq(0);
                if ($(this).parent().attr("id") == "en") {
                    var audio = $("#audio-" + enActive.data("en")).get(0);
                    audio.load();
                    audio.play();
                }
                if (enActive.data("en") === cnAvtive.data("en")) {
                    const randomIndex = Math.floor(Math.random() * colors.length);
                    const randomColor = colors.splice(randomIndex, 1)[0];
                    enActive.css({ background: randomColor });
                    cnAvtive.css({ background: randomColor });
                }
            });
        })
        .catch((error) => {
            console.error("There was a problem with the fetch operation:", error);
        });
}
function initUI(en, cn) {
    ulElementEn.innerHTML = "";
    ulElementCn.innerHTML = "";
    en.forEach((word) => {
        const en = word.en.replace(/\s+/g, "_");
        const sourceElement = document.createElement("source");
        sourceElement.type = "audio/mp3";
        sourceElement.src = "data:audio/mp3;base64," + word.tts;

        const audioElement = document.createElement("audio");
        audioElement.id = `audio-${en}`;
        audioElement.controls = 1;
        audioElement.style.display = "none"; // 设置样式为不显示
        audioElement.textContent = "Your browser does not support the audio element.";
        audioElement.appendChild(sourceElement);

        const liElement = document.createElement("li");
        liElement.setAttribute("data-en", en);
        liElement.textContent = word.en;
        liElement.appendChild(audioElement);

        ulElementEn.appendChild(liElement);
    });
    cn.forEach((word) => {
        const en = word.en.replace(/\s+/g, "_");
        const liElement = document.createElement("li");
        liElement.setAttribute("data-en", en);
        liElement.textContent = word.cn;
        ulElementCn.appendChild(liElement);
    });
}
function nextPromp() {
    confirm("Congratulations! Whether to show explanation?");
}
function shuffleArray(originArray) {
    const array = originArray.slice();
    for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
    }
    return array;
}
function compare() {
    const ulElementEn = document.getElementById("en");
    const ulElementCn = document.getElementById("cn");
    const ulLisCn = ulElementCn.querySelectorAll("li");
    const cnArray = [];
    ulLisCn.forEach((li) => {
        cnArray.push({ en: li.getAttribute("data-en") });
    });
    const ulLisEn = ulElementEn.querySelectorAll("li");
    const enArray = [];
    ulLisEn.forEach((li) => {
        enArray.push({ en: li.getAttribute("data-en") });
    });
    if (cnArray.length !== enArray.length) {
        return false;
    }
    for (let i = 0; i < cnArray.length; i++) {
        if (cnArray[i]["en"] !== enArray[i]["en"]) {
            return false;
        }
    }
    return true;
}
function recognize(data) {
    const en = data.replace(/\s+/g, "_");
    const randomIndex = Math.floor(Math.random() * colors.length);
    const randomColor = colors.splice(randomIndex, 1)[0];
    $('[data-en="' + en + '"]').each(function (i, element) {
        $(this).css({ background: randomColor });
        canvas.erase();
        var audio = $("#audio-" + en).get(0);
        audio.load();
        audio.play();
    });
}
function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}
fetchData(1);
// 禁用缩放
function addMeta() {
    $("head").append('<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />');
}
setTimeout(addMeta, 3000);
// 禁用双指放大
document.documentElement.addEventListener(
    "touchstart",
    function (event) {
        if (event.touches.length > 1) {
            event.preventDefault();
        }
    },
    {
        passive: false,
    }
);
// 禁用双击放大
var lastTouchEnd = 0;
document.documentElement.addEventListener(
    "touchend",
    function (event) {
        var now = Date.now();
        if (now - lastTouchEnd <= 300) {
            event.preventDefault();
        }
        lastTouchEnd = now;
    },
    {
        passive: false,
    }
);
