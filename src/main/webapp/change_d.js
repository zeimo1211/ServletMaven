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
function change_d() {

    var dno = document.getElementById("dno").value;
    var dname = document.getElementById("dname").value;
    var dspot = document.getElementById("dspot").value;
    var dontime = document.getElementById("dontime").value;
    var dofftime= document.getElementById("dofftime").value;
    // 发送数据到后端Servlet
    if (!dno || !dname || !dspot || !dontime|| !dofftime) {
        alert("请填写完整的修改信息");
    } else {
        var xhr7 = new XMLHttpRequest();
        xhr7.open("POST", 'http://localhost:8080/ServletMaven/changed', true);
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
        var data7 = `dno=${dno}&dname=${dname}&dspot=${dspot}&dontime=${dontime}&dofftime=${dofftime}`;

        // 发送数据
        xhr7.send(data7);
        //alert(gender);
    }
}
