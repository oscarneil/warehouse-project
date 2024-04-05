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
        body {
            font-size: 22px;
            margin: 0px;
            padding: 0px;
            font-family: Arial, sans-serif;
        }
        .custom-heading {
            font-size: 24px;
            font-weight: bold;
        }
        #header {
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
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        function createGridItem(imageSrc, title, info,url,shelfNo) {

            const gridContainer = document.getElementById('grid-container');
            const gridItem = document.createElement('a');
            gridItem.className = 'grid-item';
            gridItem.setAttribute("href",url);

            const shelfT = document.createElement('h3');
            shelfT.textContent = "櫃位編號："+shelfNo;

            
            const image = document.createElement('img');
            image.src = imageSrc;
            image.alt = title;

            const heading = document.createElement('h3');
            heading.textContent = title;

            const paragraph = document.createElement('p');
            paragraph.textContent = info;
            
            gridItem.appendChild(shelfT);
            gridItem.appendChild(image);
            gridItem.appendChild(heading);
            gridItem.appendChild(paragraph);

            gridContainer.appendChild(gridItem);
        }

        function loadElements() {
            $.ajax({
                type: "GET",
                url: "/main/api/lists", // The JSP to load elements
                dataType: 'json',
                success: function(data) {
                    /* 下一行 0313 demo 用，只取id最後兩個物件 */
                    data = data.sort((a, b) => a.id - b.id).slice(-2);
                    /* 上一行 0313 demo 用，只取id最後兩個物件 */
                    console.log(data);
                    const gridContainer = document.getElementById('grid-container');
                    gridContainer.innerHTML  = "";
                    for(var i = 0 ; i < data.length;i++){
                        var element = data[i];
                        createGridItem(element.fullPath,element.name,"剩餘庫存 " +element.count,element.fullINFO_url,element.shelfNo);
                    }
                }
            });
        }
    </script>
</head>
<body>
    <div id="header">
        <div id="top-left">
            <a href="index.jsp" class="custom-heading"> 具AIoT智能化管理與虛實整合優化路徑規劃之倉儲系統</a>
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
            <div class="grid-container" id = "grid-container">
                
            </div>
        <% } else { %>
            <p>請先至右上角登入!</p>
        <% } %>
    </div>
    <% if (session.getAttribute("login") != null && 
        (boolean) session.getAttribute("login") ) { %>
        <script>
            loadElements();
        </script>
    <% } %>
</body>
</html>
