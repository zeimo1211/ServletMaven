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

function signin() {
    var username = localStorage.getItem('username');
    var data1 = "username=" + username + "&type=" + "上班";
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
    xhr1.send(data1);
}
function signout() {
    var username = localStorage.getItem('username');
    var data2 = "username=" + username + "&type=" + "下班";
    var xhr2 = new XMLHttpRequest();
    xhr2.open("POST", 'http://localhost:8080/ServletMaven/sign', true);
    xhr2.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr2.onreadystatechange = function() {
        if (xhr2.readyState == 4 && xhr2.status == 200) {
            var response = JSON.parse(xhr2.responseText);
            if (response.success) {
                alert("签退成功！");
            } else {
                alert("签退失败");
            }
        }else {
            console.error('签退出错:', xhr1.status, xhr1.statusText);
        }
    };
    xhr2.send(data2);
}