<!DOCTYPE html>
<html>
<head>
    <title>具AIoT智能化管理與虛實整合優化路徑規劃之倉儲系統</title>
    <style>
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
            display: inline-block;
        }
        #top-left a, #top-right a {
            color: #fff;
            text-decoration: none;
            margin-left: 10px;
        }
        #content {
            margin: 20px;
            text-align: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            font-size: 18px;
            text-align: left;
        }
        table th, table td {
            padding: 12px;
            border: 1px solid #ddd;
        }
        table th {
            background-color: #f2f2f2;
        }
        .form-check {
            display: flex;
            align-items: center;
        }

        .form-check-input {
            width: 50px;
            height: 25px;
            -webkit-appearance: none;
            appearance: none;
            background-color: #c6c6c6;
            border-radius: 25px;
            position: relative;
            outline: none;
            cursor: pointer;
            transition: background-color 0.2s;
        }

        .form-check-input:checked {
            background-color: #4caf50;
        }

        .form-check-input::before {
            content: '';
            position: absolute;
            width: 23px;
            height: 23px;
            border-radius: 50%;
            background-color: #fff;
            top: 1px;
            left: 1px;
            transition: transform 0.2s;
        }

        .form-check-input:checked::before {
            transform: translateX(25px);
        }

        .form-check-label {
            margin-left: 10px;
            cursor: pointer;
            font-weight: bold;
            margin-right: 25px;
        }
        .form-check-container {
            display: flex;
            justify-content: flex-end;
            margin-bottom: 20px;
        }
        #resetQuantityButton {
                background-color: #FF9797; /* Soft grey color */
                color: #fff;
                border: none;
                padding: 8px 16px; /* Smaller padding */
                font-size: 18px; /* Smaller font size */
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s, transform 0.3s;
                margin-right: 50px; /* Adjust spacing as needed */
        }

        #resetQuantityButton:hover {
            background-color: #0056b3; /* Darker shade for hover effect */
            transform: scale(1.05); /* Slightly enlarge on hover */
        }
    </style>
    <link rel="stylesheet" href="assets/css/main.css" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        function loadLendingTools() {
            $.ajax({
                type: "GET",
                url: "/main/api/loadLendingTools", // API 端點，請根據實際情況修改
                dataType: 'json',
                success: function(data) {
                    const tableBody = document.getElementById('tools-table-body');
                    tableBody.innerHTML = "";
                    data.forEach(function(tool) {
                        const row = document.createElement('tr');
                        const nameCell = document.createElement('td');
                        const quantityCell = document.createElement('td');
                        const lendDateCell = document.createElement('td');
                        const returnDateCell = document.createElement('td');
                        const returnStatusCell = document.createElement('td');

                        nameCell.textContent = tool.name;
                        quantityCell.textContent = tool.quantity;
                        returnStatusCell.textContent = tool.returnStatus;
                        returnDateCell.textContent = tool.returnDate;
                        lendDateCell.textContent = tool.lendDate;

                        row.appendChild(nameCell);
                        row.appendChild(quantityCell);
                        row.appendChild(lendDateCell);
                        row.appendChild(returnDateCell);
                        row.appendChild(returnStatusCell);

                        tableBody.appendChild(row);
                    });
                },
                error: function(xhr, status, error) {
                    console.error("AJAX error:", status, error);
                }
            });
        }

        function loadSwitchState() {
            $.ajax({
                type: "GET",
                url: "/main/api/loadSwitchState",
                dataType: 'json',
                success: function(data) {
                    const switchButton = document.getElementById('flexSwitchCheckDefault');
                    switchButton.checked = data;
                    console.log("Data of returnable:",data);
                },
                error: function(xhr, status, error) {
                    console.error("AJAX 错误:", status, error);
                }
            });
        }

        $(document).ready(function() {
            loadLendingTools();
            loadSwitchState();
               setInterval(function() {
                    loadLendingTools();
                    loadSwitchState();
                }, 1000);

            $('#resetQuantityButton').click(function() {
                console.log('重置數量按鈕被點擊了');
                    $.ajax({
                        type: "GET",
                        url: "/main/api/resetKnifeCount5",
                        success: function(response) {
                            console.log('重置刀具數量(5)成功:', response);
                        },
                        error: function(xhr, status, error) {
                            console.error("AJAX 错误:", status, error);
                        }
                    });
                });
            });

        document.addEventListener('DOMContentLoaded', function() {
            const switchButton = document.getElementById('flexSwitchCheckDefault');

            switchButton.addEventListener('change', function() {
                const isChecked = this.checked;
                $.ajax({
                    type: "GET",
                    url: "/main/api/updateReturnableStatus",
                    data: { status: isChecked },
                    success: function(response) {
                        console.log('狀態更新成功:', response);
                    },
                    error: function(xhr, status, error) {
                        console.error("AJAX 错误:", status, error);
                    }
                });
            });
        });




    </script>
</head>
<body>
    <div id="header">
        <div id="top-left">
            <a href="index.jsp" class="custom-heading">具AIoT智能化管理與虛實整合優化路徑規劃之倉儲系統</a>
        </div>
        <div id="top-right">
            <a href="listelement.jsp">物品列表</a>
            <a href="preborrow.jsp">選取表</a>
            <a href="history.jsp">紀錄</a>
            <a href="logout">登出</a>
        </div>
    </div>
    <div id="content">
        <h2>借用的刀具列表</h2>
            <div class="form-check-container">
                <button id="resetQuantityButton" class="btn btn-primary">重置數量</button>
            </div>
            <div class="form-check-container">
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault">
                    <label class="form-check-label" for="flexSwitchCheckDefault">啟動歸還刀具</label>
                </div>
            </div>
        <table>
            <thead>
                <tr>
                    <th>刀具名稱</th>
                    <th>借用數量</th>
                    <th>借出日期</th>
                    <th>歸還日期</th>
                    <th>狀態</th>
                </tr>
            </thead>
            <tbody id="tools-table-body">
            </tbody>
        </table>
    </div>
</body>
</html>
