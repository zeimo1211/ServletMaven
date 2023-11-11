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

@WebServlet("/sadd")
public class sadd extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/kaoqin?useSSL=false&serverTimezone=UTC";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "077418";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        JsonArray arrivals = fetchAddsFromDB(startTime, endTime);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // 使用PrintWriter手动构造JSON字符串并输出
        out.print(buildJsonString(arrivals));
        //System.out.println("已发送至前端的JSON数据: " + buildJsonString(arrivals));
    }

    private JsonArray fetchAddsFromDB(String startTime, String endTime) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        try  {
            // 建立数据库连接
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            CallableStatement cstmt = conn.prepareCall("{CALL CalculateOvertime(?, ?)}");
            cstmt.setString(1, startTime);
            cstmt.setString(2, endTime);
            ResultSet rs = cstmt.executeQuery();

            while (rs.next()) {
                JsonObject jsonObject = Json.createObjectBuilder()
                        .add("wno", rs.getInt("wno"))
                        .add("wname", rs.getString("wname"))
                        .add("work_date", rs.getString("work_date"))
                        .add("overtime_duration", rs.getString("overtime_duration"))
                        .build();
                jsonArrayBuilder.add(jsonObject);
                //System.out.println("获取结果：wno: " + rs.getInt("wno") + ", wname: " + rs.getString("wname")+ ", work_date: " + rs.getString("work_date")+ ", overtime_duration: " + rs.getString("overtime_duration"));
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
            jsonString.append("\"wname\":\"").append(jsonObject.getString("wname")).append("\",");
            jsonString.append("\"work_date\":\"").append(jsonObject.getString("work_date")).append("\",");
            jsonString.append("\"overtime_duration\":\"").append(jsonObject.getString("overtime_duration")).append("\"");
            jsonString.append("}");

            if (i < jsonArray.size() - 1) {
                jsonString.append(",");
            }
        }

        jsonString.append("]");
        return jsonString.toString();
    }

}
