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

function s_b2() {
    const startTime = document.getElementById("startTime").value;
    const endTime = document.getElementById("endTime").value;

    if (!startTime || !endTime ) {
        alert("请填写完整的统计信息");
    } else {
        var data9 = "&startTime=" + startTime + "&endTime=" + endTime ;
        var xhr9 = new XMLHttpRequest();
        xhr9.open("POST", 'http://localhost:8080/ServletMaven/sb2', true);
        xhr9.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr9.onreadystatechange = function() {
            if (xhr9.readyState === 4) {
                if (xhr9.status === 200) {
                    if (xhr9.responseText) {
                        var response = JSON.parse(xhr9.responseText);
                        displayArrivals(response);
                    } else {
                        console.error("No response data received.");
                    }
                } else {
                    console.error("Failed to fetch data. Status code: " + xhr9.status);
                }
            }
        };

        xhr9.send(data9);

    }
}

function displayArrivals(arrivals) {
    var resultContainer = document.getElementById('tableContainer');

    // 清空resultContainer内容
    resultContainer.innerHTML = '';

    if (arrivals.length > 0) {
        var table = document.createElement('table');
        var thead = document.createElement('thead');
        var tbody = document.createElement('tbody');

        var headerRow = document.createElement('tr');
        var headerCell1 = document.createElement('th');
        headerCell1.textContent = '员工号';
        var headerCell2 = document.createElement('th');
        headerCell2.textContent = '销假日期';
        var headerCell3 = document.createElement('th');
        headerCell3.textContent = '原因';

        headerRow.appendChild(headerCell1);
        headerRow.appendChild(headerCell2);
        headerRow.appendChild(headerCell3);
        thead.appendChild(headerRow);
        table.appendChild(thead);

        for (var i = 0; i < arrivals.length; i++) {
            var arrival = arrivals[i];
            var row = document.createElement('tr');
            var cell1 = document.createElement('td');
            cell1.textContent = arrival.wno;
            var cell2 = document.createElement('td');
            cell2.textContent = arrival.litime;
            var cell3 = document.createElement('td');
            cell3.textContent = arrival.lireason;
            row.appendChild(cell1);
            row.appendChild(cell2);
            row.appendChild(cell3);
            tbody.appendChild(row);
        }

        table.appendChild(tbody);
        resultContainer.appendChild(table);
    } else {
        // 结果为空时显示提示信息
        resultContainer.textContent = '无销假员工';
    }
}
