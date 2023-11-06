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

function submitForm1() {
    var username = localStorage.getItem('username');

    var startTimeInput = document.getElementById("startTime");
    var endTimeInput = document.getElementById("endTime");
    var startTimeValue = startTimeInput.value;
    var endTimeValue = endTimeInput.value;
    // 添加秒数（00）
    var startTimeWithSeconds = startTimeValue + ":00";
    var endTimeWithSeconds = endTimeValue + ":00";
    // 将时间戳格式从 "T" 替换为空格
    var startTime = startTimeWithSeconds.replace("T", " ");
    var endTime = endTimeWithSeconds.replace("T", " ");

    var reason = document.getElementById("reason").value;
    
    if (!startTime || !endTime || !reason ) {
        alert("请填写完整的申请信息");
    } else {
        var data3 = "username=" + username +"&type=" + "出差"+  "&startTime=" + startTime + "&endTime=" + endTime + "&reason=" + reason;
        var xhr3 = new XMLHttpRequest();
        xhr3.open("POST", 'http://localhost:8080/ServletMaven/away', true);
        xhr3.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr3.onreadystatechange = function() {
            if (xhr3.readyState == 4 && xhr3.status == 200) {
                var response = JSON.parse(xhr3.responseText);
                if (response.success) {
                    alert("申请出差成功！");
                } else {
                    alert("申请出差失败");
                }
            }else {
                console.error('申请出差出错:', xhr3.status, xhr3.statusText);
            }
        };
        xhr3.send(data3);
        document.getElementById("leaveForm").reset();
    }
}
function submitForm2() {
    var username = localStorage.getItem('username');

    var startTimeInput = document.getElementById("startTime");
    var endTimeInput = document.getElementById("endTime");
    var startTimeValue = startTimeInput.value;
    var endTimeValue = endTimeInput.value;
    // 添加秒数（00）
    var startTimeWithSeconds = startTimeValue + ":00";
    var endTimeWithSeconds = endTimeValue + ":00";
    // 将时间戳格式从 "T" 替换为空格
    var startTime = startTimeWithSeconds.replace("T", " ");
    var endTime = endTimeWithSeconds.replace("T", " ");

    var reason = document.getElementById("reason").value;

    if (!startTime || !endTime || !reason ) {
        alert("请填写完整的申请信息");
    } else {
        var data3 = "username=" + username +"&type=" + "回岗"+  "&startTime=" + startTime + "&endTime=" + endTime + "&reason=" + reason;
        var xhr3 = new XMLHttpRequest();
        xhr3.open("POST", 'http://localhost:8080/ServletMaven/away', true);
        xhr3.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr3.onreadystatechange = function() {
            if (xhr3.readyState == 4 && xhr3.status == 200) {
                var response = JSON.parse(xhr3.responseText);
                if (response.success) {
                    alert("申请回岗成功！");
                } else {
                    alert("申请回岗失败");
                }
            }else {
                console.error('申请回岗出错:', xhr3.status, xhr3.statusText);
            }
        };
        xhr3.send(data3);
        document.getElementById("leaveForm").reset();
    }
}