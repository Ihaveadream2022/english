const domainPrefix = window.location.host.includes("github") ? "/english/html" : "";
const JSONPrefix = window.location.pathname.slice(1, -5);
const h5Meaning = document.getElementById("meaning");
const ulElementCn = document.getElementById("cn");
const ulElementEn = document.getElementById("en");
var colors = [];
var playAudioSources = [];
var playAudioSourcesCurrentIndex = 0;
var playIsPlaying = false;
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
    fetch(domainPrefix + "/json/" + JSONPrefix + "-" + page + ".json")
        .then((response) => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then((data) => {
            const enArray = shuffleArray(data.itemHtmlList);
            const cnArray = shuffleArray(data.itemHtmlList);
            playAudioSources = enArray.map((v) => {
                return { en: v.en.replace(/\s+/g, "_"), cn: v.cn, tts: v.tts };
            });
            colors = ["rgb(103, 39, 223)", "rgb(189, 83, 111)", "rgb(42, 135, 14)", "rgb(201, 196, 182)", "rgb(28, 186, 216)", "rgb(63, 55, 231)", "rgb(153, 48, 244)", "rgb(7, 239, 225)", "rgb(247, 42, 195)", "rgb(31, 106, 124)", "rgb(169, 82, 61)", "rgb(108, 216, 86)", "rgb(68, 124, 174)", "rgb(19, 233, 169)", "rgb(233, 167, 68)", "rgb(98, 155, 222)", "rgb(239, 107, 60)", "rgb(22, 68, 22)", "rgb(199, 253, 255)", "rgb(152, 107, 161)"];
            initUI(data.meaning, enArray, cnArray);
            var listItems = $("li");
            listItems.click(function () {
                const result = playAudioSources.find(({ en }) => en === $(this).data("en"));
                listPlayer.src = "data:audio/mp3;base64," + result.tts;
                listPlayer.load();
                listPlayer.play();

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
        })
        .catch((error) => {
            console.error("There was a problem with the fetch operation:", error);
        });
}
function initUI(meaning, enList, cnList) {
    h5Meaning.innerHTML = "";
    ulElementEn.innerHTML = "";
    ulElementCn.innerHTML = "";
    h5Meaning.innerHTML = meaning;
    enList.forEach((word) => {
        const en = word.en.replace(/\s+/g, "_");
        const liElement = document.createElement("li");
        liElement.setAttribute("data-en", word.en);
        liElement.textContent = word.en;
        ulElementEn.appendChild(liElement);
    });
    cnList.forEach((word) => {
        const liElement = document.createElement("li");
        liElement.setAttribute("data-en", word.en);
        liElement.innerHTML = word.cn.length > 10 ? '<span style="font-size:10px;">' + word.cn + "</span>" : word.cn;
        ulElementCn.appendChild(liElement);
    });
}
function shuffleArray(originArray) {
    const array = originArray.slice();
    for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
    }
    return array;
}
function doPlay() {
    if (!playIsPlaying) {
        playIsPlaying = true;
        playAudioSourcesCurrentIndex = 0;
        playBtn.innerHTML = "&#9209;";
        listPlayer.addEventListener("ended", playHandler, false);
        this.playHandler();
    } else {
        playIsPlaying = false;
        playBtn.innerHTML = "&#9654;";
        listPlayer.removeEventListener("ended", playHandler, false);
    }
}
function playHandler() {
    if (playAudioSources.length > 0) {
        listPlayer.src = "data:audio/mp3;base64," + playAudioSources[playAudioSourcesCurrentIndex].tts;
        listPlayer.load();
        listPlayer.play();
        playAudioSourcesCurrentIndex++;
        if (playAudioSourcesCurrentIndex >= playAudioSources.length) {
            playAudioSourcesCurrentIndex = 0;
        }
    }
}
function recognize(word) {
    const wordEn = word.replace(/\s+/g, "_");
    const result = playAudioSources.find(({ en }) => en === wordEn);
    const randomIndex = Math.floor(Math.random() * colors.length);
    const randomColor = colors.splice(randomIndex, 1)[0];
    $('[data-en="' + wordEn + '"]').css({ background: randomColor, "border-color": randomColor, "font-weight": "900", color: "#fff" });
    listPlayer.src = "data:audio/mp3;base64," + result.tts;
    listPlayer.load();
    listPlayer.play();
    canvas.erase();
}
fetchData(1);
