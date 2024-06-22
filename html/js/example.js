if (window.location.host.includes("github")) {
    var domainPrefix = "/english/html";
} else {
    var domainPrefix = "";
}
const scrollElm = document.getElementById("scroll");
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
            colors = ["rgb(103, 39, 223)", "rgb(189, 83, 111)", "rgb(42, 135, 14)", "rgb(201, 196, 182)", "rgb(28, 186, 216)", "rgb(63, 55, 231)", "rgb(153, 48, 244)", "rgb(7, 239, 225)", "rgb(247, 42, 195)", "rgb(31, 106, 124)", "rgb(169, 82, 61)", "rgb(108, 216, 86)", "rgb(68, 124, 174)", "rgb(19, 233, 169)", "rgb(233, 167, 68)", "rgb(98, 155, 222)", "rgb(239, 107, 60)", "rgb(22, 68, 22)", "rgb(199, 253, 255)", "rgb(152, 107, 161)"];
            initUI(data);
            var opionSpans = $(".option-span");
            opionSpans.click(function () {
                var thisValue = $(this).data("text");
                var sentenceItemActive = $(".sentence-item.active");
                var sentenceItemActiveValue = sentenceItemActive.data("text");
                tipsElem.textContent = thisValue;
                $(".option-span").removeClass("active");
                $(this).addClass("active");
                if (sentenceItemActiveValue !== undefined) {
                    if (sentenceItemActiveValue == thisValue) {
                        console.log("BINGO");
                        if ($(this).hasClass("matched")) {
                            var backgroundColor = $(this).css("background-color");
                            sentenceItemActive.css({ background: backgroundColor, "font-weight": "900", color: "#fff" });
                            sentenceItemActive.addClass("matched");
                        } else {
                            const randomIndex = Math.floor(Math.random() * colors.length);
                            const randomColor = colors.splice(randomIndex, 1)[0];
                            $(this).css({ background: randomColor, "font-weight": "900", color: "#fff" });
                            $(this).addClass("matched");
                            sentenceItemActive.css({ background: randomColor, "font-weight": "900", color: "#fff" });
                            sentenceItemActive.addClass("matched");
                        }
                    }
                }
            });
            var sentenceItem = $(".sentence-item");
            sentenceItem.click(function () {
                if (!$(this).hasClass("matched")) {
                    $(".sentence-item").removeClass("active");
                    $(this).addClass("active");
                    var optionSpanActive = $(".option-span.active");
                    var optionSpanActiveValue = optionSpanActive.data("text");
                    if (optionSpanActiveValue !== undefined) {
                        var thisValue = $(this).data("text");
                        if (optionSpanActiveValue == thisValue) {
                            console.log("BINGO");
                            if (optionSpanActive.hasClass("matched")) {
                                var backgroundColor = optionSpanActive.css("background-color");
                                $(this).css({ background: backgroundColor, "font-weight": "900", color: "#fff" });
                                $(this).addClass("matched");
                            } else {
                                const randomIndex = Math.floor(Math.random() * colors.length);
                                const randomColor = colors.splice(randomIndex, 1)[0];
                                $(this).css({ background: randomColor, "font-weight": "900", color: "#fff" });
                                $(this).addClass("matched");
                                optionSpanActive.css({ background: randomColor, "font-weight": "900", color: "#fff" });
                                optionSpanActive.addClass("matched");
                            }
                        }
                    }
                }
            });
        })
        .catch((error) => {
            console.error("There was a problem with the fetch operation:", error);
        });
}
function initUI(data) {
    scrollElm.innerHTML = "";

    const nameDiv = document.createElement("div");
    nameDiv.setAttribute("class", "name");
    nameDiv.textContent = data.name;
    scrollElm.appendChild(nameDiv);

    const optionDiv = document.createElement("div");
    optionDiv.setAttribute("class", "option");
    for (var i = 0; i < data.meanings.length; i++) {
        const optionUl = document.createElement("ul");
        optionUl.setAttribute("class", "option-ul");
        var optionLiEmpty;
        for (var k = 0; k < data.meanings[i].length; k++) {
            if (k === 0) {
                const optionLi = document.createElement("li");
                optionLi.setAttribute("class", "option-li type");
                optionLi.textContent = data.meanings[i][k];
                optionUl.appendChild(optionLi);
            } else {
                if (k % 2 == 1) {
                    optionLiEmpty = document.createElement("li");
                    optionLiEmpty.setAttribute("class", "option-li");
                }
                const optionSpan = document.createElement("span");
                optionSpan.setAttribute("class", "option-span");
                optionSpan.setAttribute("data-text", data.meanings[i][0] + data.meanings[i][k]);
                optionSpan.textContent = data.meanings[i][k].substr(0, 8);
                optionLiEmpty.appendChild(optionSpan);
                if (k % 2 == 0) {
                    optionUl.appendChild(optionLiEmpty);
                }
                if (k == data.meanings[i].length - 1) {
                    console.log(data.meanings[i][k]);
                    console.log(data.meanings[i].length);
                    console.log((data.meanings[i].length - 1) % 2);
                    if ((data.meanings[i].length - 1) % 2 != 0) {
                        const optionSpanEmpty = document.createElement("span");
                        optionSpanEmpty.setAttribute("class", "option-span");
                        optionSpanEmpty.setAttribute("data-text", "nothing");
                        optionSpanEmpty.innerHTML = "&nbsp;";
                        optionLiEmpty.appendChild(optionSpanEmpty);
                        optionUl.appendChild(optionLiEmpty);
                    }
                }
            }
        }
        optionDiv.appendChild(optionUl);
    }
    scrollElm.appendChild(optionDiv);

    const sentenceDiv = document.createElement("div");
    sentenceDiv.setAttribute("class", "sentence");
    for (var i = 0; i < data.examples.length; i++) {
        const sentenceItemDiv = document.createElement("div");
        sentenceItemDiv.setAttribute("class", "sentence-item");
        sentenceItemDiv.setAttribute("data-text", data.examples[i].key);
        sentenceItemDiv.innerHTML = data.examples[i].example;
        sentenceDiv.appendChild(sentenceItemDiv);
    }
    scrollElm.appendChild(sentenceDiv);
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
