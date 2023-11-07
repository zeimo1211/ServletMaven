import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/changew")
public class change_w extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取前端传递的数据
        String username = request.getParameter("username");
        String wname = request.getParameter("wname");
        String gender = request.getParameter("gender");
        String wphone = request.getParameter("wphone");
        String waddress = request.getParameter("waddress");
        // 数据库连接信息
        String url = "jdbc:mysql://localhost:3306/kaoqin?useSSL=false&serverTimezone=UTC";
        String dbUser = "root";
        String dbPassword = "077418";

        try {
            // 加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            // 建立数据库连接
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);

            // 调用方法插入数据
            if (userchange(connection, username,wname,gender,wphone,waddress)) {
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
    private boolean userchange(Connection connection, String username , String wname,String gender,String wphone, String waddress) throws SQLException {

    String updateQuery = "UPDATE worker SET wname=?, gender=?, wphone=?, waddress=? WHERE wno=?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

        preparedStatement.setString(1, wname);
        preparedStatement.setString(2, gender);
        preparedStatement.setString(3, wphone);
        preparedStatement.setString(4, waddress);
        preparedStatement.setString(5, username);

        // 执行更新操作
        int rowsUpdated = preparedStatement.executeUpdate();

        if (rowsUpdated > 0) {
            return true;
        } else {
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
