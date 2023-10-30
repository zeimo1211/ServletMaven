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

// 获取用户名和密码输入框的引用
const usernameInput = document.querySelector('input[type="text"]');
const passwordInput = document.querySelector('input[type="password"]');

// 获取登录按钮的引用
const loginButton = document.querySelector('.login-button');

// 添加登录按钮的点击事件处理程序
async function login() {
    const username = usernameInput.value;
    const password = passwordInput.value;

    try {
        const response = await fetch('http://localhost:8080/ServletMaven/kaoqin', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        });
        const responseText = await response.text(); // 获取响应作为文本
        console.log(responseText); // 记录响应以进行调试
        const data = await response.json();

        if (data.success) {
            localStorage.setItem('username', username);
            localStorage.setItem('isLoggedIn', 'true');
            window.location.href = '/worker_home.html';
        } else {
            alert('登录失败，请检查用户名和密码。');
        }
    } catch (error) {
        console.error('登录出错:', error);
    }
}

document.getElementById("password").addEventListener("keyup", function(event) {
    if (event.key === "Enter") {
        login();
    }
});


// 添加事件监听器，监听回车键
document.getElementById("password").addEventListener("keyup", function(event) {
    if (event.key === "Enter") {
        login(); // 当按下回车键时调用登录函数
    }
});