const domainPrefix = window.location.host.includes("github") ? "/english/html" : "";
const JSONPrefix = window.location.pathname.slice(1, -5);
const titleElement = document.getElementById("title");
const contentElement = document.getElementById("content");
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
            initUI(data);
            $("p").click(function () {
                $(this).siblings().removeClass("active");
                $(this).addClass("active");
            });
        })
        .catch((error) => {
            console.error("There was a problem with the fetch operation:", error);
        });
}
function initUI(data) {
    titleElement.innerHTML = data.name;
    contentElement.innerHTML = data.content;
}
fetchData(1);
