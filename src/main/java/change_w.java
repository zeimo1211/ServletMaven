import javax.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/change_w")
public class change_w extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

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
            // 建立数据库连接
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);

            // 构建更新SQL语句
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
                out.println("修改成功");
            } else {
                out.println("修改失败");
            }

            // 关闭数据库连接
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("修改失败：" + e.getMessage());
        }
    }
}

