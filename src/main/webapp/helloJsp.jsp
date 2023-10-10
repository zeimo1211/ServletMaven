<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2022/4/19
  Time: 13:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello world</title>
</head>
<body>

String docType = "<!DOCTYPE html> \n";
out.println(docType +
"<html>\n" +
"<head><title>" + title + "</title></head>\n" +
"<body bgcolor=\"#f0f0f0\">\n" +
"<h1 align=\"center\">" + title + "</h1>\n" +
"<ul>\n" +
    "  <li><b>web name</b>:"
        + name + "\n" +
        "  <li><b>address</b>:"
        + request.getParameter("url") + "\n" +
        "</ul>\n" +
"</body></html>");
</body>
</html>
