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
    <title>具AIoT智能化管理與虛實整合優化路徑規劃之倉儲系統</title>
    <style>
        /* Header styles */
        /* Header styles */
        .custom-heading {
            font-size: 24px;
            font-weight: bold;
        }
        #header {
            height: 35px;
            background-color: #333;
            color: #fff;
            padding: 10px;
            display: flex; /* Use flexbox for layout */
            justify-content: space-between; /* Align items at each end */
        }
        
        #top-left, #top-right {
            display: inline-block; /* Make elements inline-block to appear on the same line */
        }

        /* Login/Logout link styles */
        #top-left a, #top-right a {
            color: #fff;
            text-decoration: none;
            margin-left: 10px;
        }

        /* Content block styles */
        #content {
            margin: 20px;
            text-align: center;
        }

        /* Content block styles */
        #content {
            margin: 20px;
            text-align: center; /* Center-align the content */
        }

            /* Center-align the form within the content block */
        #content form {
            display: inline-block;
            text-align: left; /* Reset the text alignment for form elements */
        }
    </style>
    <link rel="stylesheet" href="assets/css/main.css" />
</head>
<body>
    <div id="header">
        <div id="top-left">
            <a href="index.jsp" class="custom-heading"> 具AIoT智能化管理與虛實整合優化路徑規劃之倉儲系統 </a>
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
        <% if (session.getAttribute("login") != null && 
        (boolean) session.getAttribute("login") ) { %>
            
            
        <% } else { %>
            <p>請先至右上角登入!</p>
        <% } %>
    </div>
</body>
</html>
