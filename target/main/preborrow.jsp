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
        var datas = [];
        function createSummary(step,count) {
            const cartContainer = document.getElementById('cart-container');
            const summary = document.createElement('div');
            summary.className = 'summary';


            // Create the item name (h3 element)
            const title = document.createElement('h2');
            title.textContent = "總結";

            // Create the item count (p element)
            const tstep = document.createElement('p');
            tstep.textContent = `物品類別: `+step+` 類`;
            
            // Create the item count (p element)
            const tcnt = document.createElement('p');
            tcnt.textContent = `總數: `+count+` 個`;

            // Create the item count (p element)
            const updatebtn = document.createElement('button');
            updatebtn.textContent = `更新`;
            updatebtn.className = 'send-button';
            updatebtn.setAttribute("id","updatebtn");
            // Create the item count (p element)
            const sendbtn = document.createElement('button');
            sendbtn.textContent = `送出`;
            sendbtn.className = 'send-button';
            sendbtn.setAttribute("id","sendbtn");


            updatebtn.addEventListener('click', () => {
                // update 
                const jsonStr = JSON.stringify(datas);
                $.ajax({
                type: "POST",
                url: "/main/api/pbupdate", // The JSP to load elements
                contentType: 'application/json', // Set the content type to JSON
                data: jsonStr,

                success: function(data) {
                    console.log(data);
                    window.location.href = 'preborrow.jsp';

                }
            });
            });

            sendbtn.addEventListener('click', () => {
                // generate qr code
                window.location.href='confirmBorrow';
            });



            summary.appendChild(title);
            summary.appendChild(tstep);
            summary.appendChild(tcnt);
            summary.appendChild(updatebtn);
            summary.appendChild(sendbtn);

            // Append the cart item to the cart container
            cartContainer.appendChild(summary);
            if (count == 0){
                sendbtn.style.backgroundColor='gray'
                sendbtn.disabled = true;
            }
        }
        // Function to generate a shopping cart item block
        function createCartItem(itemName, count, imageUrl,id) {
            const cartContainer = document.getElementById('cart-container');

            // Create the cart item div
            const cartItem = document.createElement('div');
            cartItem.className = 'cart-item';

            // Create the item image
            const itemImage = document.createElement('img');
            itemImage.src = imageUrl;
            itemImage.alt = itemName;

            // Create the item details div
            const itemDetails = document.createElement('div');
            itemDetails.className = 'cart-item-details';

            const itemDetails2 = document.createElement('div');
            itemDetails.className = 'cart-item-details';


            // Create increment and decrement buttons
            const incrementButton = document.createElement('button');
            incrementButton.textContent = '+';

            const decrementButton = document.createElement('button');
            decrementButton.textContent = '-';

            // Set event listeners for incrementing and decrementing the count
            //let currentCount = count; // Initialize the count
            let current = id;
            incrementButton.addEventListener('click', () => {
                datas[current]++;
                itemCount.textContent = `數量: `+datas[current];
                change = true;
                const btn = document.getElementById('sendbtn');
                btn.style.backgroundColor='gray'
                btn.disabled = true;
            });

            decrementButton.addEventListener('click', () => {
                if (datas[current] > 0) {
                    datas[current]--;
                    itemCount.textContent = `數量: `+datas[current];
                }
                change = true;
                const btn = document.getElementById('sendbtn');
                btn.style.backgroundColor='gray'
                btn.disabled = true;
            });

            

            // Create the item name (h3 element)
            const itemNameElement = document.createElement('h3');
            itemNameElement.textContent = itemName;

            // Create the item count (p element)
            const itemCount = document.createElement('p');
            itemCount.textContent = `數量: `+count;

            // Append the elements to the cart item
            itemDetails.appendChild(itemNameElement);
            itemDetails2.appendChild(decrementButton);
            itemDetails2.appendChild(itemCount);
            itemDetails2.appendChild(incrementButton);
            cartItem.appendChild(itemImage);
            cartItem.appendChild(itemDetails);
            cartItem.appendChild(itemDetails2);
            
            // Append the cart item to the cart container
            cartContainer.appendChild(cartItem);
        }

        function loadCart() {
            $.ajax({
                type: "GET",
                url: "/main/api/listborrow", // The JSP to load elements
                dataType: 'json',
                success: function(data) {
                    console.log(data);
                    const gridContainer = document.getElementById('cart-container');
                    gridContainer.innerHTML  = "";
                    var totalcnt = 0;
                    var totalstep = 0;
                    for(var i = 0 ; i < data.length;i++){
                        var element = data[i];
                        datas.push(element.count);
                        totalcnt += element.count;
                        totalstep += 1;
                        createCartItem(element.name,element.count,element.fullPath,i);
                    }
                    createSummary(totalstep,totalcnt);


                }
            });
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
            <div class="cart-container" id="cart-container">
                <!-- Cart items -->
                
        
                <!-- ... (add more cart items as needed) -->
        
                <!-- Summary section -->
                <div class="summary">
                    <h2>Summary</h2>
                    <p>Total Items: 5</p>
                    <p>Total Price: $50.00</p>
                    <button class="send-button">Send</button>
                </div>
            </div>
            <script>loadCart();</script>
        <% } else { %>
            <p>請先至右上角登入!</p>
        <% } %>
    </div>
</body>
</html>
