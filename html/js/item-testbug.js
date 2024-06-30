const domainPrefix = window.location.host.includes("github") ? "/english/html" : "";
const JSONPrefix = window.location.pathname.split("/").pop().replace(".html", "");

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

    nextBtn.addEventListener("click", function (e) {
        e.preventDefault();
        if (currentPage < totalPages) {
            currentPage++;
            curPageElement.value = currentPage.toString();
            fetchData(currentPage);
        }
    });
}
// Fetch Data From a JSON File
function fetchData(page) {
    fetch(domainPrefix + "/json/" + JSONPrefix + "-" + page + ".json")
        .then((response) => {
            console.log("response", response);
            // if (!response.ok) {
            //     throw new Error("Network response was not ok");
            // }
            // return response.json();
        })
        .then((data) => {
            console.log("data", data);
        })
        .catch((error) => {
            console.error("There was a problem with the fetch operation:", error);
        });
}
fetchData(1);
bindEventPage();
