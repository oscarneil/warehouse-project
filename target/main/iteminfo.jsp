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
    <title>具AIoT之智慧零售系統</title>
    <link rel="stylesheet" href="assets/css/main.css" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
        <h1>具AIoT之智慧零售系統</h1>
        
        <% if (session.getAttribute("login") != null && 
        (boolean) session.getAttribute("login") ) { %>
            <div class="item-container">
                <div class="item-image">
                    <img src="<%= request.getAttribute("img_path") %>" alt="Item Image" style="max-width: 100%;">
                </div>
                <div class="item-details">
                    <h1><%= request.getAttribute("name") %></h1>
                    <p>庫存: <%= request.getAttribute("count") %></p>
                    <p>備註: <%= request.getAttribute("info") %></p>
                    <button id="backButton" class="lend-button">返回</button>
                    <button id="lendButton" class="lend-button">選取</button>
                </div>
            </div>
            <!-- Modal dialog for lending -->
            <div id="lendModal" class="modal">
                <div class="modal-content">
                    <span class="close" id="closeModal">&times;</span>
                    <h2>管理物品</h2>
                    <p>選擇要補充的數量:</p>
                    <div class="number-input">
                        <button id="decrement">-</button>
                        <input type="number" id="lendQuantity" min="1" value="1">
                        <button id="increment">+</button>
                    </div>
                    <button id="confirmLend">選取</button>
                </div>
            </div>

            <script>
                // Show the lend modal when the "lend" button is clicked
                const lendButton = document.getElementById('lendButton');
                const backbutton = document.getElementById('backButton');
                const lendModal = document.getElementById('lendModal');
        
                backbutton.addEventListener('click', () => {
                    window.location.href = "listelement.jsp";
                });


                lendButton.addEventListener('click', () => {
                    lendModal.style.display = 'block';
                });
        
                // Close the modal when the close button is clicked
                const closeModal = document.getElementById('closeModal');
                closeModal.addEventListener('click', () => {
                    lendModal.style.display = 'none';
                });
        
                // Increment and decrement the number of items
                const decrementButton = document.getElementById('decrement');
                const incrementButton = document.getElementById('increment');
                const lendQuantity = document.getElementById('lendQuantity');

                decrementButton.addEventListener('click', () => {
                    if (lendQuantity.value > 1) {
                        lendQuantity.value = parseInt(lendQuantity.value) - 1;
                    }
                });

                incrementButton.addEventListener('click', () => {
                    lendQuantity.value = parseInt(lendQuantity.value) + 1;
                });

                // Perform lending action (you can customize this)
                const confirmLendButton = document.getElementById('confirmLend');
                confirmLendButton.addEventListener('click', () => {
                    const quantity = lendQuantity.value;
                    // Perform lending action here (e.g., AJAX request to the server)
                    // Close the modal
                    lendModal.style.display = 'none';
                    window.location.href = 'lend?id=<%= request.getAttribute("id") %>&cnt='+lendQuantity.value;
                });
            </script>
        <% } else { %>
            <p>請先至右上角登入!</p>
        <% } %>
    </div>
</body>
</html>
