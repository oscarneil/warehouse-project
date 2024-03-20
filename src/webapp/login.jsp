<%@ page language="java" %>
<%@ page session="true" %>
<%@page import="gjt.usblab.data.userData"%>
<%
userData uData = null;
if (session.getAttribute("userData") != null ){
    uData = (userData)session.getAttribute("userData");
}

%>
<!DOCTYPE html>
<html>
    <head>
        <title>智能AIoT物流倉儲管理系統</title>
        <link rel="stylesheet" href="assets/css/main.css" />
    </head>
    <body>

        <div id="header">
            <div id="top-left">
                <a href="index.jsp">首頁</a>
            </div>
            <div id="top-right">
                <% if (session.getAttribute("login") == null || 
                    ! (boolean) session.getAttribute("login") ) { %>
                <a href="login.jsp">登入</a>
            <% } else {%>
                <a href="listelement.jsp">物品列表</a>
                <a href="preborrow.jsp">選取表</a>
                <!-- <a href="prereturn.jsp">退還表</a> -->
                <a href="history.jsp">紀錄</a>
                <a href="logout">登出</a>
            <% } %>
            </div>
        </div>
        <div id="content">

            <h1>登入</h1>
            <form action="LoginServlet" method="POST">
                <label for="account">Account:</label>
                <input type="text" id="account" name="account" required><br><br>
        
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required><br><br>
                <input type="submit" value="Login">
            </form>
        </div>
    </body>
</html>
