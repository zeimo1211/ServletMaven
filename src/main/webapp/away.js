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

function submitForm() {
    var startTime = document.getElementById("startTime").value;
    var endTime = document.getElementById("endTime").value;
    var reason = document.getElementById("reason").value;
    
    if (!startTime || !endTime || !reason ) {
        alert("请填写完整的申请信息");
    } else {
        alert("申请提交成功！");
        document.getElementById("leaveForm").reset();
    }
}