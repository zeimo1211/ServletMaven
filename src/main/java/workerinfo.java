import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/workerinfo")
public class workerinfo extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username"); // 从前端获取用户名
        //System.out.println(username);
        try {
            // 建立数据库连接
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kaoqin?useSSL=false&serverTimezone=UTC", "root", "077418");

            String query = "SELECT * FROM worker WHERE wno = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // 从数据库获取员工信息
                String wname = resultSet.getString("wname");
                String gender = resultSet.getString("gender");
                String wphone = resultSet.getString("wphone");
                String wjob = resultSet.getString("wjob");
                String wstate = resultSet.getString("wstate");
                String waddress = resultSet.getString("waddress");

                // 构造JSON响应
                String json = "{\"姓名\":\"" + wname + "\",\"性别\":\"" + gender + "\",\"电话号码\":\"" + wphone + "\",\"职务\":\"" + wjob + "\",\"状态\":\"" + wstate + "\",\"住址\":\"" + waddress + "\"}";

                PrintWriter out = response.getWriter();
                out.print(json);
            } else {
                // 如果没有找到匹配的记录，返回一个空的 JSON 对象
                String emptyJson = "{}";
                PrintWriter out = response.getWriter();
                out.print(emptyJson);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            // 在出现异常时返回错误信息
            String errorJson = "{\"error\":\"An error occurred\"}";
            PrintWriter out = response.getWriter();
            out.print(errorJson);
        }
    }
}
