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

// 添加事件监听器，监听回车键
document.getElementById("password").addEventListener("keyup", function(event) {
    if (event.key === "Enter") {
        blogin(); // 当按下回车键时调用登录函数
    }
});

// 获取用户名和密码输入框的引用
const usernameInput = document.querySelector('input[type="text"]');
const passwordInput = document.querySelector('input[type="password"]');

// 获取登录按钮的引用
const loginButton = document.querySelector('.login-button');
function blogin() {
    const username = usernameInput.value;
    const password = passwordInput.value;
    var data = "username=" + username + "&password=" + password;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", 'http://localhost:8080/ServletMaven/boss', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var response = JSON.parse(xhr.responseText);
            if (response.success) {
                localStorage.setItem('username', username);
                localStorage.setItem('isLoggedIn', 'true');
                window.location.href = 'boss_home.html';
            } else {
                alert('登录失败，请检查用户名，密码和是否具有管理员权限。');
            }
        }else {
            console.error('登录出错:', xhr.status, xhr.statusText);
        }
    };

    xhr.send(data);

}