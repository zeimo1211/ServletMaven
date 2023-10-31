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
    var xhr1 = new XMLHttpRequest();
    xhr1.open("POST", 'http://localhost:8080/ServletMaven/sign', true);
    xhr1.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr1.onreadystatechange = function() {
        if (xhr1.readyState == 4 && xhr1.status == 200) {
            var response = JSON.parse(xhr1.responseText);
            if (response.success) {
                alert("签到成功！");
            } else {
                alert("签到失败");
            }
        }else {
            console.error('签到出错:', xhr1.status, xhr1.statusText);
        }
    };
    xhr1.send(data);
}