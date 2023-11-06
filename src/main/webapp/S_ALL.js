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

function generateTable() {
    const startTime = document.getElementById("startTime").value;
    const endTime = document.getElementById("endTime").value;

    if (startTime && endTime) {
        // 获取表格容器
        const tableContainer = document.getElementById("tableContainer");

        // 创建表格元素
        const table = document.createElement("table");

        // 创建表头
        const headerRow = table.insertRow(0);
        const headers = ["姓名", "工号", "姓名", "工号"];

        for (let i = 0; i < headers.length; i++) {
            const th = document.createElement("th");
            th.textContent = headers[i];
            headerRow.appendChild(th);
        }

        // 模拟生成若干行数据
        const data = [
            ["John", "12345", "Alice", "56789"],
            ["Bob", "67890", "Eve", "98765"],
            ["Charlie", "24680", "Grace", "13579"]
        ];

        // 创建数据行
        for (let i = 0; i < data.length; i++) {
            const row = table.insertRow(i + 1);
            for (let j = 0; j < data[i].length; j++) {
                const cell = row.insertCell(j);
                cell.textContent = data[i][j];
            }
        }

        // 清空表格容器并添加新表格
        tableContainer.innerHTML = "";
        tableContainer.appendChild(table);
    } else {
        alert("请先选择开始时间和结束时间");
    }
}