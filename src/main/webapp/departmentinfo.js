function updateDateTime() {
    const datetimeElement = document.getElementById("datetime");
    const now = new Date();
    const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit', second: '2-digit' };
    const formattedDate = now.toLocaleDateString('zh-CN', options);
    datetimeElement.textContent = formattedDate;
}

// 初始加载时更新日期和时间
updateDateTime();

// 每秒更新一次日期和时间
setInterval(updateDateTime, 1000);

document.addEventListener("DOMContentLoaded", function () {
    var username = localStorage.getItem("username");

    // 创建XMLHttpRequest对象
    var xhr6 = new XMLHttpRequest();
    xhr6.open("POST", 'http://localhost:8080/ServletMaven/departmentinfo', true);

    // 设置请求头以指定数据类型
    xhr6.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr6.onreadystatechange = function () {
        if (xhr6.readyState === 4 && xhr6.status === 200) {
            if (xhr6.responseText) {
                var response = JSON.parse(xhr6.responseText);

                // 创建表格行并填充数据
                var table = document.querySelector("table");
                for (var prop in response) {
                    var row = table.insertRow();
                    var cell1 = row.insertCell(0);
                    var cell2 = row.insertCell(1);
                    cell1.textContent = prop;
                    cell2.textContent = response[prop];
                }
            } else {
                console.error("No response data received.");
            }
        }
    };

    // 发送POST请求，将用户名作为数据发送
    xhr6.send("username=" + username);
});