import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/sb")
public class sb extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/kaoqin?useSSL=false&serverTimezone=UTC";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "077418";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        JsonArray arrivals = fetchleavesFromDB(startTime, endTime);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // 使用PrintWriter手动构造JSON字符串并输出
        out.print(buildJsonString(arrivals));
        System.out.println("已发送至前端的JSON数据: " + buildJsonString(arrivals));
    }

    private JsonArray fetchleavesFromDB(String startTime, String endTime) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        try  {
            // 建立数据库连接
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            CallableStatement cstmt = conn.prepareCall("{CALL GetLeaveRecords(?, ?)}");
            cstmt.setString(1, startTime);
            cstmt.setString(2, endTime);
            ResultSet rs = cstmt.executeQuery();

            while (rs.next()) {
                JsonObject jsonObject = Json.createObjectBuilder()
                        .add("wno", rs.getInt("wno"))
                        .add("listate", rs.getString("listate"))
                        .add("litime", rs.getString("litime"))
                        .add("lireason", rs.getString("lireason"))
                        .build();
                jsonArrayBuilder.add(jsonObject);
                System.out.println("获取结果：wno: " + rs.getInt("wno") + ", listate: " + rs.getString("listate")+ ", litime: " + rs.getString("litime")+ ", lireason: " + rs.getString("lireason"));
            }
        }
        catch (SQLException e) {
            // 处理数据库连接或查询异常
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return jsonArrayBuilder.build();
    }

    private String buildJsonString(JsonArray jsonArray) {
        StringBuilder jsonString = new StringBuilder();
        jsonString.append("[");

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.getJsonObject(i);
            jsonString.append("{");
            jsonString.append("\"wno\":").append(jsonObject.getInt("wno")).append(",");
            jsonString.append("\"litime\":\"").append(jsonObject.getString("litime")).append("\",");
            jsonString.append("\"lireason\":\"").append(jsonObject.getString("lireason")).append("\"");
            jsonString.append("}");

            if (i < jsonArray.size() - 1) {
                jsonString.append(",");
            }
        }

        jsonString.append("]");
        return jsonString.toString();
    }

}
