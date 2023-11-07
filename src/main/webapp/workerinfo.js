function updateDateTime() {
    const datetimeElement = document.getElementById("datetime");
    const now = new Date();
    const options = {
        weekday: 'long',
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
    };
    const formattedDate = now.toLocaleDateString('zh-CN', options);
    datetimeElement.textContent = formattedDate;
}

// 初始加载时更新日期和时间
updateDateTime();

// 每秒更新一次日期和时间
setInterval(updateDateTime, 1000);

// 获取信息内容
// 发送Ajax请求到后端Servlet
document.addEventListener("DOMContentLoaded", function () {
    var username = localStorage.getItem("username");


    // 创建XMLHttpRequest对象
    var xhr5 = new XMLHttpRequest();
    xhr5.open("POST", 'http://localhost:8080/ServletMaven/workerinfo', true);

    // 设置请求头以指定数据类型
    xhr5.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr5.onreadystatechange = function () {
        if (xhr5.readyState === 4 && xhr5.status === 200) {
            if (xhr5.responseText) {
                var response = JSON.parse(xhr5.responseText);

                // 创建表格行并填充数据
                var table = document.querySelector("table");
                var newRow = table.insertRow();
                var cell1 = newRow.insertCell(0);
                var cell2 = newRow.insertCell(1);
                cell1.textContent = "工号";
                cell2.textContent = username;
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
    xhr5.send("username=" + username);
});



