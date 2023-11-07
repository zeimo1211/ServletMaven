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
function change_w() {
    const username = localStorage.getItem('username');
    const wname = document.getElementById("wname").value;
    const gender = document.querySelector('input[name="gender"]:checked').value;
    const wphone = document.getElementById("wphone").value;
    const waddress = document.getElementById("waddress").value;

    // 发送数据到后端Servlet
    const xhr7 = new XMLHttpRequest();
    xhr7.open("POST", 'http://localhost:8080/ServletMaven/change_w', true);
    xhr7.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr7.onreadystatechange = function () {
        if (xhr7.readyState === 4 && xhr7.status === 200) {
            const response = xhr7.responseText;
            alert(response); // 根据需要显示成功或失败消息
        }
    };

    // 构建要发送的数据
    const data7 = `username=${username}&wname=${wname}&gender=${gender}&wphone=${wphone}&waddress=${waddress}`;

    // 发送数据
    xhr7.send(data7);
}
