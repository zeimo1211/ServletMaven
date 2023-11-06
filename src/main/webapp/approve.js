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

function approve(button) {
    // 弹窗显示已通过
    alert('已通过');

    // 获取行并移除
    var row = button.parentNode.parentNode;
    row.parentNode.removeChild(row);
}

var reasonCells = document.querySelectorAll('.reason');
for (var i = 0; i < reasonCells.length; i++) {
    reasonCells[i].addEventListener('click', function () {
        // 获取理由单元格的内容
        var reasonText = this.textContent;
        // 弹窗显示详细内容
        alert('详细内容: ' + reasonText);
    });
}