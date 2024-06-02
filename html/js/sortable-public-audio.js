// const words = {};
const enArray = shuffleArray(words);
const cnArray = shuffleArray(words);
const audio = document.getElementById("voice");
const audioSource = document.getElementById("voice-source");
const ulElementEn = document.getElementById("en");
const ulElementCn = document.getElementById("cn");
const practice = document.getElementById("practice");
const explanation = document.getElementById("explanation");
initUI(enArray, cnArray);

var video = document.getElementById("voice");
video.addEventListener("loadeddata", function () {
    console.log("视频加载完成，可以播放了");
    audio.play();
});

const sortable = Sortable.create(en, {
    group: "shared",
    animation: 200,
    onStart: function (evt) {
        var itemEl = evt.item;
        var dataEn = itemEl.getAttribute("data-en");
        var en = dataEn.replace(/\s+/g, "_");
        audioSource.src = `/assets/audio/${en}.mp3`;
        audio.load();
    },
    onEnd: function (evt) {
        // var itemEl = evt.item;
        // var index = 0;
        // var en = itemEl.getAttribute("data-en");
        // var cnList = document.getElementById("cn");
        // var lis = cnList.getElementsByTagName("li");
        // for (var i = 0; i < lis.length; i++) {
        //     var li = lis[i];
        //     if (li.getAttribute("data-en") === en) {
        //         index = i;
        //         console.log("序号为: " + i);
        //     }
        // }

        // var enList = document.getElementById("en");
        // var nthLi = enList.getElementsByTagName("li")[index]; // 获取第 N 个 li 元素，注意索引从 0 开始
        // if (nthLi) {
        //     var dataEn = nthLi.getAttribute("data-en");
        //     var en = dataEn.replace(/\s+/g, "_");
        //     audioSource.src = `/assets/audio/${en}_cn.mp3`;
        //     audio.load();
        // }


        const liElements = ulElementCn.querySelectorAll("li");
        const currentArray = [];
        liElements.forEach((li) => {
            currentArray.push({ en: li.getAttribute("data-en") });
        });
        console.log(enArray)
        console.log(currentArray)
        const r = compareArrays(enArray, currentArray);
        console.log(r)
        if (r) {
            sortable.option("disabled", true); // set
            nextPromp();
        }
    },
});

const sortable2 = Sortable.create(cn, {
    group: "shared",
    animation: 200,
    onStart: function (evt) {
        var itemEl = evt.item;
        var dataEn = itemEl.getAttribute("data-en");
        var en = dataEn.replace(/\s+/g, "_");
        audioSource.src = `/assets/audio/${en}.mp3`;
        audio.load();
    },
    onEnd: function (evt) {
        // var itemEl = evt.item;
        // var index = 0;
        // var en = itemEl.getAttribute("data-en");
        // var cnList = document.getElementById("cn");
        // var lis = cnList.getElementsByTagName("li");
        // for (var i = 0; i < lis.length; i++) {
        //     var li = lis[i];
        //     if (li.getAttribute("data-en") === en) {
        //         index = i;
        //         console.log("序号为: " + i);
        //     }
        // }

        // var enList = document.getElementById("en");
        // var nthLi = enList.getElementsByTagName("li")[index]; // 获取第 N 个 li 元素，注意索引从 0 开始
        // if (nthLi) {
        //     var dataEn = nthLi.getAttribute("data-en");
        //     var en = dataEn.replace(/\s+/g, "_");
        //     audioSource.src = `/assets/audio/${en}_cn.mp3`;
        //     audio.load();
        // }

        const liElements = ulElementCn.querySelectorAll("li");
        const currentArray = [];
        liElements.forEach((li) => {
            currentArray.push({ en: li.getAttribute("data-en") });
        });
        console.log(enArray)
        console.log(currentArray)
        const r = compareArrays(enArray, currentArray);
        console.log(r)
        if (r) {
            sortable.option("disabled", true); // set
            nextPromp();
        }
    },
});

function initUI(wordsLeft, wordsRight) {
    wordsLeft.forEach((word) => {
        const liElement = document.createElement("li");
        liElement.setAttribute("data-en", word.en);
        liElement.textContent = word.cn;
        ulElementEn.appendChild(liElement);
    });
    wordsRight.forEach((word) => {
        const liElement = document.createElement("li");
        liElement.setAttribute("data-en", word.en);
        liElement.textContent = word.en;
        ulElementCn.appendChild(liElement);
    });
}
function nextPromp() {
    var r = confirm("Congratulations! Whether to show explanation?");
    if (r == true) {
        practice.style.display = "none";
        explanation.style.display = "block";
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
function compareArrays(originArray, array) {
    if (originArray.length !== array.length) {
        console.log(98)
        return false;
    }
    // 逐个比较数组内元素
    for (let i = 0; i < originArray.length; i++) {
        if (originArray[i]["en"] !== array[i]["en"]) {
            return false;
        }
    }
    // 如果所有元素都相同，则顺序一致
    return true;
}
