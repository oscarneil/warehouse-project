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
    <script>
        var change = false;
        var next = false;
        var end = false;
        var tt;
        function loadInfo() {
            
            $.ajax({
                type: "GET",
                url: "/main/api/picking", // The JSP to load elements
                dataType: 'json',
                data : {
                    next: next,
                    end: end
                },
                success: function(data) {
                    tt = data;
                    var color = data.LEDcolor;
                    // update form
                    // 1. map info
                    // 2. led color
                    // 3. item list
                    // 4. current item
                    // 5. time left
                    // 6. pop info (when lend)
                    // 7. qrcode
                    try {
                        document.getElementById("qr-code").setAttribute("src","file/"+data.QRCodeURL);
                        document.getElementById("showitem").setAttribute("src",data.mainData.fullPath);
                        document.getElementById("itemname").innerText = data.mainData.name;
                        document.getElementById("iteminfoCNT1").innerText = "已拿取："+data.mainData.holdCount;
                        document.getElementById("iteminfoCNT2").innerText = "需拿取："+data.mainData.count;
                    }catch(error){

                    }
                    if (data.finish){
                        document.getElementById("showitem").setAttribute("src","file/finish.jpg");
                        document.getElementById("itemname").innerText = "揀貨完畢！";
                        document.getElementById("iteminfoCNT1").innerText = "已拿取：N/A";
                        document.getElementById("iteminfoCNT2").innerText = "需拿取：N/A";
                        document.getElementById("nextbtn").innerText = "完成揀貨";
                    }
                    if (data.wait){
                        document.getElementById("showitem").setAttribute("src","file/scan.png");
                        document.getElementById("itemname").innerText = "請至門禁處掃描！";
                        document.getElementById("iteminfoCNT1").innerText = "已拿取：N/A";
                        document.getElementById("iteminfoCNT2").innerText = "需拿取：N/A";
                        document.getElementById("avatime").innerText = "剩餘時間：尚未掃描";
                        const nextbtn = document.getElementById('nextbtn');
                        const closebtn = document.getElementById('closebtn');
                        nextbtn.disabled  = true;
                    }
                    else {
                        document.getElementById("avatime").innerText = "剩餘時間："+data.minutes+"分"+data.seconds+"秒";
                        const nextbtn = document.getElementById('nextbtn');
                        const closebtn = document.getElementById('closebtn');
                        nextbtn.disabled  = false;
                    }
                    

                    document.getElementById("listtb").innerHTML = "";
                    // create table
                    var tables = document.getElementById("listtb")
                    const tr1 = document.createElement('tr');
                    
                    
                    const td11 = document.createElement('th');
                    td11.innerText = "貨物名稱";
                    const td12 = document.createElement('th');
                    td12.innerText = "狀態";
                    const td13 = document.createElement('th');
                    td13.innerText = "位置";

                    
                    tr1.appendChild(td11);
                    tr1.appendChild(td12);
                    tr1.appendChild(td13);

                    tables.appendChild(tr1);
                    for (var i = 0; i < data.datas.length;i++){
                        var item = data.datas[i];
                        const tr = document.createElement('tr');
                        const td1 = document.createElement('td');
                        td1.innerText = ""+item.name;
                        const td2 = document.createElement('td');
                        td2.innerText = ""+item.holdCount +"/"+item.count+ "("+(item.holdCount * 100 / item.count)+"%)";
                        const td3 = document.createElement('td');
                        td3.innerText = "1 公尺";

                        tr.appendChild(td1);
                        tr.appendChild(td2);
                        tr.appendChild(td3);

                        tables.appendChild(tr);
                    }

                    // draw map

                    // Get the canvas element and its 2D drawing context
                    var canvas = document.getElementById("map");
                    var ctx = canvas.getContext("2d");
                    ctx.clearRect(0, 0, canvas.width, canvas.height);

                    // Set the width and height of each cell
                    var cellWidth = 50;
                    var cellHeight = 50;
                    var circleRadius = 30;
                    // Define the pattern as an array of rows
                    var pattern = [
                        ".......",
                        ".##.##.",
                        ".......",
                        "   .   ",
                        "###.###",
                        ".......",
                        "   .   ",
                    ];
                    
                    var num = 0;
                    // Loop through the pattern and draw each cell
                    for (var i = 0; i < pattern.length; i++) {
                        for (var j = 0; j < pattern[i].length; j++) {
                            var cell = pattern[i][j];
                            // Calculate the top-left corner of the cell
                            var x = j * cellWidth;
                            var y = i * cellHeight;
                            if (cell === "#") {
                                // Draw a filled rectangle (black)
                                ctx.fillStyle = "black";
                                ctx.fillRect(x, y, cellWidth, cellHeight);
                            } else if (cell === ".") {
                                if (data.paths != null && data.paths.includes(num)) {

                                    ctx.fillStyle = color;
                                    ctx.beginPath();
                                    ctx.arc(x + cellWidth / 2, y + cellHeight / 2, circleRadius-10, 0, 2 * Math.PI);
                                    ctx.fill();
                                }
                                else {
                                    ctx.fillStyle = "black";
                                    ctx.beginPath();
                                    ctx.arc(x + cellWidth / 2, y + cellHeight / 2, circleRadius-10, 0, 2 * Math.PI);
                                    ctx.stroke();
                                }
                                
                                
                            }
                            num++;
                        }
                    }
                    if (data.pop){
                        // when lend pop item information and click confirm to continue
                        const lendModal = document.getElementById('lendModal');
                        lendModal.style.display = 'block';

                        document.getElementById('ITEM-NAME').innerText = "物品名稱："+data.popData.name;
                        document.getElementById('BAR-CODE').innerText = "物品編號："+data.popData.filename;
                    }

                }
            });
            next = false;
            end = false;
        }
    </script>
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
                <a href="prereturn.jsp"></a>
                <a href="history.jsp">紀錄</a>
                <a href="logout">登出</a>
            <% } %>
        </div>
    </div>
    <div id="content">        
        <% if (session.getAttribute("login") != null && 
        (boolean) session.getAttribute("login") ) { %>
            <div class="flex-container">
                <div class="row row1">
                    <!-- First row with three columns -->
                    <div class="column1">
                        <!-- Left column: QR code -->
                        <img  id="qr-code" >
                        <h2 id="avatime">

                        </h2>
                        <button id="closebtn" class="workorderbtn">完成揀貨</button>
                    </div>
                    <div class="column2">
                        <!-- Middle column: Item image -->
                        <div class="item-image">
                            <img id="showitem" >
                        </div>
                        <h2 id="itemname">

                        </h2>
                    </div>
                    <div class="column3">
                        <!-- Right column: Item information -->
                        <div class="item-info">
                            <p id="iteminfoCNT1">已拿取：</p>
                            <p id="iteminfoCNT2">需拿取：</p>
                        </div>
                        <button id="nextbtn" class="workorderbtn">下一樣物品</button>
                    </div>
                </div>
                <div class="row row3" >
                    <!-- Third row: Mini map -->
                    <canvas id="map" width="350" height="400"></canvas>
                </div>

                <div class="row row2" >
                    <!-- Second row: Progress table -->
                    <table id="listtb">
                        <tr>
                            <th>貨物名稱</th>
                            <th>狀態</th>
                            <th>距離</th>
                        </tr>
                        <tr>
                            <td>001</td>
                            <td>In Progress</td>
                            <td>1 公尺</td>
                        </tr>
                        <!-- Add more rows as needed -->
                    </table>
                </div>
        
                
            </div>
            <script>
                setInterval(loadInfo, 1000);
                const nextButton = document.getElementById('nextbtn');
                const closeButton = document.getElementById('closebtn');

                nextButton.addEventListener('click', () => {
                    next = true;
                    loadInfo();
                });

                closeButton.addEventListener('click', () => {
                    end = true;
                    loadInfo();
                });
            </script>

            <!-- Modal dialog for lending -->
            <div id="lendModal" class="modal">
                <div class="modal-content">
                    <span class="close" id="closeModal">&times;</span>
                    <h2>管理物品</h2>
                    <p id="ITEM-NAME"></p>
                    <p id="BAR-CODE"></p>
                    <p>成功租借！</p>
                    <button id="confirmLend">確認</button>
                </div>
            </div>

            <script>
                const lendModal = document.getElementById('lendModal');
        
                // Close the modal when the close button is clicked
                const closeModal = document.getElementById('closeModal');
                closeModal.addEventListener('click', () => {
                    lendModal.style.display = 'none';
                });
        

                // Perform lending action (you can customize this)
                const confirmLendButton = document.getElementById('confirmLend');
                confirmLendButton.addEventListener('click', () => {
                    // Perform lending action here (e.g., AJAX request to the server)
                    // Close the modal
                    lendModal.style.display = 'none';
                    
                });
            </script>
        <% } else { %>
            <p>請先至右上角登入!</p>
        <% } %>
    </div>
</body>
</html>
