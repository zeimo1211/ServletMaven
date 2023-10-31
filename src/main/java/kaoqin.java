import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/kaoqin")
    public class kaoqin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 从请求中获取用户名和密码参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //System.out.println("Username: " + username);
        //System.out.println("Password: " + password);
        // 数据库连接配置

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String dbUrl = "jdbc:mysql://localhost:3306/kaoqin?useSSL=false&serverTimezone=UTC";  // 数据库URL
        String dbUser = "root";  // 数据库用户名
        String dbPassword = "077418";  // 数据库密码

        try {
            // 建立数据库连接
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            // 调用方法检查用户登录
            if (checkUserLogin(connection, username, password)) {
                // 如果登录成功，发送成功响应
                sendSuccessResponse(response);
            } else {
                // 如果登录失败，发送失败响应
                sendFailureResponse(response);
            }
        } catch (SQLException e) {
            // 捕获数据库操作异常并打印
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 设置响应状态为服务器内部错误
        }
    }

    // 检查用户登录的方法
    private boolean checkUserLogin(Connection connection, String username, String password) throws SQLException {
        // SQL查询语句，用于从数据库中检索具有给定用户名和密码的用户信息
        String sql = "SELECT * FROM worker WHERE wno = ? AND keyword = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // 在SQL查询中使用占位符'?'来代替用户名和密码，以避免SQL注入攻击
            statement.setString(1, username);  // 将方法参数中的用户名设置为第一个占位符
            statement.setString(2, password);  // 将方法参数中的密码设置为第二个占位符

            try (ResultSet resultSet = statement.executeQuery()) {
                // 执行SQL查询并将结果存储在ResultSet对象中
                // 如果查询返回结果，表示用户名和密码匹配，表示用户登录成功

                return resultSet.next();  // 返回true表示登录成功，否则返回false表示登录失败
            }
        }
    }


    // 发送成功响应的方法
    private void sendSuccessResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");  // 设置响应内容类型为JSON
        try (PrintWriter out = response.getWriter()) {
            out.print("{\"success\": true}");  // 返回成功的JSON响应
            out.flush();
        }
    }

    // 发送失败响应的方法
    private void sendFailureResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");  // 设置响应内容类型为JSON
        try (PrintWriter out = response.getWriter()) {
            out.print("{\"success\": false}");  // 返回失败的JSON响应
            out.flush();
        }
    }
}
