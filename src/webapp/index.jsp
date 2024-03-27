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
    <style>
        /* 基本樣式 */
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }
        /* 標題樣式 */
        .banner {
            font-size: 22px;
            background-color: #333;
            color: #fff;
            text-align: center;
            padding: 10px;
        }
        /* 區塊樣式 */
        .container {
            display: flex;
            height: 100vh;
        }
        .left-section, .middle-section, .right-section {
            flex: 1;
            padding: 20px;
        }
        /* 各區塊樣式 */
        .left-section {
            background-color: #f0f0f0;
        }
        .middle-section {
            background-color: #e0e0e0;
        }
        .right-section {
            background-color: #d0d0d0;
        }

        .profile-picture {
            width: 200px; /* 調整圖像的寬度 */
            height: 200px; /* 調整圖像的高度 */
            border: 2px solid #333; /* 添加邊框，可以根據需要調整顏色和寬度 */
            border-radius: 0%; /* 將圖像圓角化，以形成圓形的效果 */
            overflow: hidden; /* 隱藏圖像超出邊框的部分 */

        }

        .profile-picture img {
            width: 100%; /* 確保圖像填滿整個父元素（即方框） */
            height: 100%; /* 確保圖像填滿整個父元素（即方框） */
            object-fit: cover; /* 使圖像等比例地填充整個容器，並裁剪超出的部分 */
        }

        .profile-content{
            margin-top: 150px;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .lend-component{
            text-align: left;
            margin-left: 50px;
        }

        .lend-picture {
            width: 100px; /* 調整圖像的寬度 */
            height: 100px; /* 調整圖像的高度 */
            border: 2px solid #333; /* 添加邊框，可以根據需要調整顏色和寬度 */
            border-radius: 0%; /* 將圖像圓角化，以形成圓形的效果 */
            overflow: hidden; /* 隱藏圖像超出邊框的部分 */

        }

        .lend-picture img {
            width: 100%; /* 確保圖像填滿整個父元素（即方框） */
            height: 100%; /* 確保圖像填滿整個父元素（即方框） */
            object-fit: cover; /* 使圖像等比例地填充整個容器，並裁剪超出的部分 */
        }

        .lend-item{
            margin-top: 0px;
            display: flex;
            justify-content: left;
        }

        .lend-picture lend-itemContent{
            display: inline-block;
        }

        .lend-itemContent{
            display: flex;
            align-items: center;
            margin-left: 20px;
        }

        .welcome-message {
            font-size: 28px;
            font-weight: bold;
            color: #333;
            text-align: center;
            margin-top: 20px;
            padding: 10px;
            background-color: #f0f0f0;
            border-radius: 5px;
        }

        /* Header styles */
        /* Header styles */
        #header {
            height: 30px;
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
            text-align: center; /* Center-align the content */
        }

            /* Center-align the form within the content block */
        #content form {
            display: inline-block;
            text-align: left; /* Reset the text alignment for form elements */
        }
    </style>
<!--  <link rel="stylesheet" href="assets/css/main.css" /> -->
</head>
<body>
    <div id="header" class="banner">
        <div id="top-left">
            <a href="index.jsp">首頁</a>
        </div>
        <div id="top-right">
            <% if (session.getAttribute("login") == null || 
                    ! (boolean) session.getAttribute("login") ) { %>
                <a href="login.jsp">登入</a>
            <% } else {%>
                <a href="http://140.130.4.49:748/">產品履歷</a>
                <a href="listelement.jsp">物品列表</a>
                <a href="preborrow.jsp">選取表</a>
                <!-- <a href="prereturn.jsp">退還表</a> -->
                <a href="history.jsp">紀錄</a>
                <a href="logout">登出</a>
            <% } %>
        </div>
    </div>
    <div id="content" class="container">
        <div class="left-section">
            <!-- 左側區塊 -->
            <% if (session.getAttribute("login") != null &&
            (boolean) session.getAttribute("login") ) { %>
                <div class="profile-content">
                    <div class="profile-picture">
                        <img src="image/login_profile.jpg">
                    </div>
                </div>
                <p class="welcome-message">Welcome, <%= uData.name %></p>
            <% } else { %>
                <p class="welcome-message">請先至右上角登入!</p>
            <% } %>

        </div>

        <div class="middle-section">
                <!-- 中間區塊 -->
            <h1>智能AIoT物流倉儲管理系統</h1>
            <br>
            <div class="lend-component">
                <% if (session.getAttribute("login") != null &&
                    (boolean) session.getAttribute("login") ) { %>
                        <h2>借用刀具:</h2>
                        <div class="lend-item">
                            <div class="lend-picture">
                                <img src="file/Drill01.jpg">
                            </div>
                            <div class="lend-itemContent">
                                <h2>刀具 : 2</h2>
                            </div>
                        </div>
                        <br>

                        <div class="lend-item">
                            <div class="lend-picture">
                                <img src="file/Consumable01.jpg">
                            </div>
                            <div class="lend-itemContent">
                                <h2>耗材 : 3</h2>
                            </div>
                        </div>
                    <br>
                <% } %>
            </div>
        </div>

        <div class="right-section">
            <!-- 右側區塊 -->
        </div>
    </div>
</body>
</html>
