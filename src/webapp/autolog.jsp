<html>
    <head>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            var last = -1;
            function refreshLogs() {
                fetchLogs(); // Fetch new logs from the API
                setTimeout(refreshLogs, 1250); // Refresh every 5 seconds (adjust as needed)    
            }
            function appendLog(logData) {
                const logsContainer = document.getElementById('logsContainer');
                const logElement = document.createElement('div');
                logElement.innerText = logData;
                logsContainer.appendChild(logElement);
                // Scroll to the bottom of the container to show the latest log
                logsContainer.scrollTop = logsContainer.scrollHeight;
            }
            var vd;
            function fetchLogs(){
                $.ajax({
                    url: '/main/api/logs',
                    method: 'GET',
                    dataType: 'json',
                    data: {
            		    "lastINT":last
          		    },
                    success: function(data) {
                        for (var i = 0 ; i < data.length;i++){
                            appendLog(data[i]['text']);
                            last = parseInt(data[i]['id']);
                        }
                    }
                });
                
            }
        </script>
        <style>
            .logs-container {
                height: 100%;
                font-size:230%;
                overflow-y: auto;
                border: 1px solid #ccc;
            }
        </style>
    </head>
    <body>
        <div class="logs-container" id="logsContainer">
            <!-- The logs will be appended here -->
        </div>
        <script>refreshLogs();</script>
    </body>
</html>