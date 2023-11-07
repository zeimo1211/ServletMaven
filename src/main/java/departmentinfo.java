import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/departmentinfo")
public class departmentinfo extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username"); // 从前端获取用户名

        try {
            // 建立数据库连接
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kaoqin?useSSL=false&serverTimezone=UTC", "root", "077418");

            // 查询员工对应的部门编号
            String queryWorker = "SELECT d.dno FROM department_worker dw JOIN department d ON dw.dno = d.dno WHERE dw.wno = ?";
            PreparedStatement workerStatement = conn.prepareStatement(queryWorker);
            workerStatement.setString(1, username);
            ResultSet workerResult = workerStatement.executeQuery();

            if (workerResult.next()) {
                int departmentNumber = workerResult.getInt("dno");

                // 查询部门信息
                String queryDepartment = "SELECT * FROM department WHERE dno = ?";
                PreparedStatement departmentStatement = conn.prepareStatement(queryDepartment);
                departmentStatement.setInt(1, departmentNumber);
                ResultSet departmentResult = departmentStatement.executeQuery();

                if (departmentResult.next()) {
                    // 从数据库获取部门信息
                    String dname = departmentResult.getString("dname");
                    String dspot = departmentResult.getString("dspot");
                    String dontime = departmentResult.getString("dontime");
                    String dofftime = departmentResult.getString("dofftime");

                    // 构造JSON响应
                    String json = "{\"部门名称\":\"" + dname + "\",\"部门位置\":\"" + dspot + "\",\"上班时间\":\"" + dontime + "\",\"下班时间\":\"" + dofftime + "\"}";

                    PrintWriter out = response.getWriter();
                    out.print(json);
                } else {
                    // 如果没有找到匹配的部门记录，返回一个空的 JSON 对象
                    String emptyJson = "{}";
                    PrintWriter out = response.getWriter();
                    out.print(emptyJson);
                }
            } else {
                // 如果没有找到匹配的员工记录，返回一个空的 JSON 对象
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
