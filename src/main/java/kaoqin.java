import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class kaoqin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String dbUrl = "jdbc:mysql://localhost:3306/kaoqin";
        String dbUser = "root";
        String dbPassword = "077418";

        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            // 定义SQL查询来检查用户是否存在
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // 登录成功
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.print("{\"success\": true}");
                out.flush();
            } else {
                // 登录失败
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.print("{\"success\": false}");
                out.flush();
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // 处理数据库连接错误
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
