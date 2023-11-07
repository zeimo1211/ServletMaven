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
    var reason0 = document.getElementById("reason").value;
    var leaveTypeElement = document.querySelector('input[name="leaveType"]:checked');
    var leaveType = leaveTypeElement ? leaveTypeElement.value : ''; // 使用.value获取选中的值
    var reason = leaveType + "：" + reason0;

    if (!startTime || !endTime || !reason || !leaveType) {
        alert("请填写完整的申请信息");
    } else {
        var data4 = "username=" + username +"&type=" + "请假"+  "&startTime=" + startTime + "&endTime=" + endTime + "&reason=" + reason;
        var xhr4 = new XMLHttpRequest();
        xhr4.open("POST", 'http://localhost:8080/ServletMaven/leave', true);
        xhr4.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr4.onreadystatechange = function() {
            if (xhr4.readyState == 4 && xhr4.status == 200) {
                var response = JSON.parse(xhr4.responseText);
                if (response.success) {
                    alert("申请请假成功！");
                } else {
                    alert("申请请假失败");
                }
            }else {
                console.error('申请请假出错:', xhr4.status, xhr4.statusText);
            }
        };
        xhr4.send(data4);
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
    var reason0 = document.getElementById("reason").value;
    var leaveTypeElement = document.querySelector('input[name="leaveType"]:checked');
    var leaveType = leaveTypeElement ? leaveTypeElement.value : ''; // 使用.value获取选中的值
    var reason = leaveType + "：" + reason0;

    if (!startTime || !endTime || !reason || !leaveType) {
        alert("请填写完整的申请信息");
    } else {
        var data5 = "username=" + username +"&type=" + "销假"+  "&startTime=" + startTime + "&endTime=" + endTime + "&reason=" + reason;
        var xhr5 = new XMLHttpRequest();
        xhr5.open("POST", 'http://localhost:8080/ServletMaven/leave', true);
        xhr5.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr5.onreadystatechange = function() {
            if (xhr5.readyState == 4 && xhr5.status == 200) {
                var response = JSON.parse(xhr5.responseText);
                if (response.success) {
                    alert("申请销假成功！");
                } else {
                    alert("申请销假失败");
                }
            }else {
                console.error('申请销假出错:', xhr5.status, xhr5.statusText);
            }
        };
        xhr5.send(data5);
        document.getElementById("leaveForm").reset();
    }
}