if (window.location.host.includes("github")) {
    var domainPrefix = "/english/html";
} else {
    var domainPrefix = "";
}
const exampleUl = document.getElementById("example-ul");
const tipsElem = document.getElementById("tips");
var colors = [];
document.addEventListener("DOMContentLoaded", function () {
    // 获取 Pre 和 Next 按钮元素
    var prevBtn = document.getElementById("prevBtn");
    var nextBtn = document.getElementById("nextBtn");
    // 获取当前页数和总页数元素
    var curPageElement = document.getElementById("curpage");
    var totalPageElement = document.getElementById("total");
    // 初始当前页数和总页数
    var currentPage = parseInt(curPageElement.value);
    var totalPages = parseInt(totalPageElement.textContent);
    // 添加点击事件监听器
    prevBtn.addEventListener("click", function () {
        if (currentPage > 1) {
            currentPage--;
            curPageElement.value = currentPage.toString();
            fetchData(currentPage);
        }
    });
    nextBtn.addEventListener("click", function () {
        if (currentPage < totalPages) {
            currentPage++;
            curPageElement.value = currentPage.toString();
            fetchData(currentPage);
        }
    });
    curPageElement.addEventListener("keydown", function (event) {
        if (event.key === "Enter") {
            var inputPage = parseInt(curPageElement.value);
            if (!isNaN(inputPage) && inputPage >= 1 && inputPage <= totalPages) {
                currentPage = inputPage;
                fetchData(currentPage);
            } else {
                alert("请输入有效的页码（1 至 " + totalPages + "）");
                curPageElement.value = currentPage.toString();
            }
        }
    });
});
function fetchData(page) {
    fetch(domainPrefix + "/json/item-example-" + page + ".json")
        .then((response) => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then((data) => {
            const exampleArr = shuffleArray(data);
            initUI(exampleArr);
            var opionLis = $(".option-ul li");
            opionLis.click(function () {
                const key = $(this).parents(".item").data("key");
                const value = $(this).text();
                const wholeText = $(this).data("text");
                tipsElem.textContent = wholeText;
                console.log(key);
                console.log(value);
                if (key == value) {
                    $(this).css({ background: "#000", "font-weight": "900", color: "#fff" });
                }
            });
        })
        .catch((error) => {
            console.error("There was a problem with the fetch operation:", error);
        });
}
function initUI(data) {
    exampleUl.innerHTML = "";
    for (var i = 0; i < data.length; i++) {
        const sentenceDiv = document.createElement("div");
        sentenceDiv.setAttribute("class", "sentence");
        sentenceDiv.innerHTML = data[i].example;

        const optionDiv = document.createElement("div");
        optionDiv.setAttribute("class", "option");
        for (var j = 0; j < data[i].meaning.length; j++) {
            const optionUl = document.createElement("ul");
            optionUl.setAttribute("class", "option-ul");
            var colors = ["rgb(103, 39, 223)", "rgb(189, 83, 111)", "rgb(42, 135, 14)", "rgb(201, 196, 182)", "rgb(28, 186, 216)", "rgb(63, 55, 231)", "rgb(153, 48, 244)", "rgb(7, 239, 225)", "rgb(247, 42, 195)", "rgb(31, 106, 124)", "rgb(169, 82, 61)", "rgb(108, 216, 86)", "rgb(68, 124, 174)", "rgb(19, 233, 169)", "rgb(233, 167, 68)", "rgb(98, 155, 222)", "rgb(239, 107, 60)", "rgb(22, 68, 22)", "rgb(199, 253, 255)", "rgb(152, 107, 161)"];
            for (var k = 0; k < data[i].meaning[j].length; k++) {
                const randomIndex = Math.floor(Math.random() * colors.length);
                const randomColor = colors.splice(randomIndex, 1)[0];
                const optionLi = document.createElement("li");
                optionLi.style.backgroundColor = randomColor;
                if (k === 0) {
                    optionLi.style.backgroundColor = "#fff";
                    optionLi.style.fontWeight = "900";
                }
                optionLi.textContent = data[i].meaning[j][k].substr(0, 8);
                optionLi.setAttribute("data-text", data[i].meaning[j][k]);
                optionUl.appendChild(optionLi);
            }
            optionDiv.appendChild(optionUl);
        }

        const itemLi = document.createElement("li");
        itemLi.setAttribute("class", "item");
        itemLi.setAttribute("data-key", data[i].key);
        itemLi.appendChild(sentenceDiv);
        itemLi.appendChild(optionDiv);

        exampleUl.appendChild(itemLi);
    }
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
function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}
fetchData(1);
