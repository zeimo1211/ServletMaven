import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.TimeZone;

@WebServlet("/sign")
public class sign extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 从请求中获取用户名和密码参数
        //System.out.println("username: " );
        String username = request.getParameter("username");
        String type = request.getParameter("type");


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

            // 调用方法插入数据
            if (usersign(connection, username,type)) {
                sendSuccessResponse(response);
            } else {
                // 如果插入失败，发送失败响应
                sendFailureResponse(response);
            }
        } catch (SQLException e) {
            // 捕获数据库操作异常并打印
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 设置响应状态为服务器内部错误
        }
    }

    // 插入签到信息
    private boolean usersign(Connection connection, String username , String type) throws SQLException {
        String sql = "INSERT INTO work_info (wno, wistate, witime) VALUES (?, ?, ?)";

        // 获取当前时间用于签到
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // 设置时区为东八区
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");

        // 调整时区
        timestamp.setTime(timestamp.getTime() + timeZone.getRawOffset());


        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, username);
            statement.setString(2, type );
            statement.setTimestamp(3, timestamp);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted == 1) {
                return true;
            }
            return false;
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
