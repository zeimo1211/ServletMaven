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
function showSignOutMessage() {
    alert("签退成功！");
}
function sign() {
    var username = localStorage.getItem('username');
    var data = "username=" + username ;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", 'http://localhost:8080/ServletMaven/sign', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var response = JSON.parse(xhr.responseText);
            if (response.success) {
                alert("签到成功！");
            } else {
                alert("签到失败");
            }
        }
    };
    xhr.send(data);
}