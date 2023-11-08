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
    var username = localStorage.getItem('username');
    var wname = document.getElementById("wname").value;
    var genderElement = document.querySelector('input[name="gender"]:checked');
    var gender = genderElement ? genderElement.value : ''; // 使用.value获取选中的值
    var wphone = document.getElementById("wphone").value;
    var waddress = document.getElementById("waddress").value;

    // 发送数据到后端Servlet
    if (!wname || !gender || !wphone || !waddress) {
        alert("请填写完整的修改信息");
    } else {
        var xhr7 = new XMLHttpRequest();
        xhr7.open("POST", 'http://localhost:8080/ServletMaven/changew', true);
        xhr7.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr7.onreadystatechange = function () {
            if (xhr7.readyState == 4 && xhr7.status == 200) {
                var response = JSON.parse(xhr7.responseText);
                if (response.success) {
                    alert("修改成功！");
                } else {
                    alert("修改失败");
                }
            } else {
                console.error('修改出错:', xhr7.status, xhr7.statusText);
            }
        };

        // 构建要发送的数据
        var data7 = `username=${username}&wname=${wname}&gender=${gender}&wphone=${wphone}&waddress=${waddress}`;

        // 发送数据
        xhr7.send(data7);
        //alert(gender);
    }
}
