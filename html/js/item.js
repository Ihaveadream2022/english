const domainPrefix = window.location.host.includes("github") ? "/english/html" : "";
const JSONPrefix = window.location.pathname.split("/").pop().replace(".html", "");
const ulElementCn = document.getElementById("cn");
const ulElementEn = document.getElementById("en");
const exampleElement = document.getElementById("example");
const audioListElement = document.getElementById("audioList");
const audioListPlayBtn = document.getElementById("audioListPlayBtn");
var audioElements = document.querySelectorAll("audio");
var audioCallbackHandlers = [];
var audioLoadedCount = 0;
var audioListIsPlaying = false;
var colors = [];

// Bind Page Event
function bindEventPage() {
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
}

// Handwriting + Audio
function recognize(word) {
    const wordEn = word.replace(/\s+/g, "_");
    const randomIndex = Math.floor(Math.random() * colors.length);
    const randomColor = colors.splice(randomIndex, 1)[0];
    $('[data-en="' + wordEn + '"]').css({ background: randomColor, "border-color": randomColor, "font-weight": "900", color: "#fff" });
    $("#audio-" + wordEn)[0].play();
    canvas.erase();
}
// Play Audio
function doPlay() {
    audioElements = document.querySelectorAll("audio");
    if (audioElements.length > 0) {
        if (!audioListIsPlaying) {
            audioElements.forEach((audio, key, data) => {
                const nextKey = key >= data.length - 1 ? 0 : key + 1;
                const audioCallbackHandler = audioCallback(data[nextKey].id);
                audio.pause();
                audio.currentTime = 0;
                audio.addEventListener("ended", audioCallbackHandler);
                audioCallbackHandlers[key] = audioCallbackHandler;
            });
            audioListIsPlaying = true;
            audioListPlayBtn.innerHTML = "&#9209;";
            audioElements[0].play();
        } else {
            stopPlay();
        }
    }
}
function stopPlay() {
    if (audioListIsPlaying && audioElements.length > 0) {
        audioListIsPlaying = false;
        audioListPlayBtn.innerHTML = "&#9654;";
        audioElements.forEach((audio, key) => {
            audio.removeEventListener("ended", audioCallbackHandlers[key]);
        });
    }
}
function audioCallback(audioID) {
    return function () {
        const itemNext = document.getElementById(audioID);
        itemNext.play();
    };
}
// Functions
function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}
function initUI(data) {
    const en = shuffleArray(data);
    const cn = shuffleArray(data);
    ulElementEn.innerHTML = "";
    ulElementCn.innerHTML = "";
    audioListElement.innerHTML = "";
    en.forEach((word) => {
        const en = word.en.replace(/\s+/g, "_");
        const liElement = document.createElement("li");
        liElement.setAttribute("data-en", en);
        liElement.textContent = word.en;
        if (word.examples && word.examples.length) {
            const spanElement = document.createElement("span");
            const eventHandler = exampleUIRender(word);
            spanElement.setAttribute("class", "example-num");
            spanElement.textContent = "[" + word.examples.length + "]";
            spanElement.addEventListener("click", eventHandler);
            liElement.appendChild(spanElement);
        }
        ulElementEn.appendChild(liElement);

        const sourceElement = document.createElement("source");
        sourceElement.type = "audio/mp3";
        sourceElement.src = "data:audio/mp3;base64," + word.tts;

        const audioElement = document.createElement("audio");
        audioElement.id = `audio-${en}`;
        audioElement.preload = "auto";
        audioElement.controls = 1;
        audioElement.style.display = "none"; // 设置样式为不显示
        audioElement.textContent = "Your browser does not support the audio element.";
        audioElement.appendChild(sourceElement);
        audioListElement.appendChild(audioElement);
    });
    cn.forEach((word) => {
        const en = word.en.replace(/\s+/g, "_");
        const liElement = document.createElement("li");
        liElement.setAttribute("data-en", en);
        liElement.innerHTML = word.cn.length > 10 ? '<span style="font-size:10px;">' + word.cn + "</span>" : word.cn;
        ulElementCn.appendChild(liElement);
    });

    // Bind Event
    var listItems = $("#practice li");
    listItems.unbind("click");
    listItems.click(function () {
        stopPlay();
        const itemValue = $(this).data("en");
        const isLeftColumn = $(this).parent().attr("id") == "en";
        if (isLeftColumn) {
            const audio = $("#audio-" + itemValue)[0];
            if (audio.readyState == 0) {
                alert("Please try it later");
            }
            if (audio.currentTime > 0) {
                audio.load();
            }
            audio.play();
        }
        $(this).siblings().removeClass("active");
        $(this).addClass("active");
        var enActive = $("#en").find("li.active").eq(0);
        var cnAvtive = $("#cn").find("li.active").eq(0);
        if (enActive.data("en") === cnAvtive.data("en")) {
            const randomIndex = Math.floor(Math.random() * colors.length);
            const randomColor = colors.splice(randomIndex, 1)[0];
            enActive.css({ background: randomColor, "font-weight": "900", color: "#fff" });
            cnAvtive.css({ background: randomColor, "font-weight": "900", color: "#fff" });
        }
    });
}
function exampleUIClear() {
    if (!exampleElement.innerHTM) {
        var opionSpans = $(".option-span");
        var sentenceItem = $(".sentence-item");
        opionSpans.unbind("click");
        sentenceItem.unbind("click");
        exampleElement.innerHTML = "";
        exampleElement.style.display = "none";
    }
}
function exampleUIRender(item) {
    return function () {
        console.log(item);
        exampleElement.innerHTML = "";
        if (item.examples && item.examples.length > 0) {
            const tipsElem = document.createElement("div");
            tipsElem.setAttribute("class", "example-tips");

            const nameDiv = document.createElement("div");
            nameDiv.setAttribute("class", "name");
            nameDiv.textContent = item.cn;
            nameDiv.appendChild(tipsElem);
            exampleElement.appendChild(nameDiv);

            const optionDiv = document.createElement("div");
            optionDiv.setAttribute("class", "option");
            for (var i = 0; i < item.meanings.length; i++) {
                const optionUl = document.createElement("ul");
                optionUl.setAttribute("class", "option-ul");
                var optionLiEmpty;
                for (var k = 0; k < item.meanings[i].length; k++) {
                    if (k === 0) {
                        const optionLi = document.createElement("li");
                        optionLi.setAttribute("class", "option-li type");
                        optionLi.textContent = item.meanings[i][k];
                        optionUl.appendChild(optionLi);
                    } else {
                        if (k % 2 == 1) {
                            optionLiEmpty = document.createElement("li");
                            optionLiEmpty.setAttribute("class", "option-li");
                        }
                        const optionSpan = document.createElement("span");
                        optionSpan.setAttribute("class", "option-span");
                        optionSpan.setAttribute("data-text", item.meanings[i][0] + item.meanings[i][k]);
                        optionSpan.textContent = item.meanings[i][k].substr(0, 8);
                        optionLiEmpty.appendChild(optionSpan);
                        if (k % 2 == 0) {
                            optionUl.appendChild(optionLiEmpty);
                        }
                        if (k == item.meanings[i].length - 1) {
                            console.log(item.meanings[i][k]);
                            console.log(item.meanings[i].length);
                            console.log((item.meanings[i].length - 1) % 2);
                            if ((item.meanings[i].length - 1) % 2 != 0) {
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
            exampleElement.appendChild(optionDiv);

            const sentenceDiv = document.createElement("div");
            sentenceDiv.setAttribute("class", "sentence");
            for (var i = 0; i < item.examples.length; i++) {
                const sentenceItemDiv = document.createElement("div");
                sentenceItemDiv.setAttribute("class", "sentence-item");
                sentenceItemDiv.setAttribute("data-text", item.examples[i].key);
                sentenceItemDiv.innerHTML = item.examples[i].value;
                sentenceDiv.appendChild(sentenceItemDiv);
            }
            exampleElement.appendChild(sentenceDiv);
            exampleElement.style.display = "block";

            // Bind Event
            var opionSpans = $(".option-span");
            opionSpans.unbind("click");
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
            sentenceItem.unbind("click");
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
        }
    };
}
function shuffleArray(originArray) {
    const array = originArray.slice();
    for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
    }
    return array;
}

// Fetch Statics
function fetchStatics(page) {
    fetch(domainPrefix + "/json/statics.json")
        .then((response) => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then((data) => {
            var curPageElement = document.getElementById("curpage");
            var totalPageElement = document.getElementById("total");
            curPageElement.value = data.itemsFileFrom;
            totalPageElement.textContent = data.itemsFileEnd;
            bindEventPage();
            fetchData(data.itemsFileFrom);
        })
        .catch((error) => {
            console.error("There was a problem with the fetch operation:", error);
        });
}
// Fetch Data From a JSON File
function fetchData(page) {
    fetch(domainPrefix + "/json/" + JSONPrefix + "-" + page + ".json")
        .then((response) => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then((data) => {
            initUI(data);
            colors = ["rgb(103, 39, 223)", "rgb(189, 83, 111)", "rgb(42, 135, 14)", "rgb(201, 196, 182)", "rgb(28, 186, 216)", "rgb(63, 55, 231)", "rgb(153, 48, 244)", "rgb(7, 239, 225)", "rgb(247, 42, 195)", "rgb(31, 106, 124)", "rgb(169, 82, 61)", "rgb(108, 216, 86)", "rgb(68, 124, 174)", "rgb(19, 233, 169)", "rgb(233, 167, 68)", "rgb(98, 155, 222)", "rgb(239, 107, 60)", "rgb(22, 68, 22)", "rgb(199, 253, 255)", "rgb(152, 107, 161)"];
        })
        .catch((error) => {
            console.error("There was a problem with the fetch operation:", error);
        });
}
fetchStatics();
