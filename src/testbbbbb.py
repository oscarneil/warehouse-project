import socket

def start_server():
    server_ip = 'localhost'  # Change this to the IP address of your server
    server_port = 8765  # Change this to the port your server is listening on

    # Create a socket object
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    
    # Bind the socket to the address and port
    server_socket.bind((server_ip, server_port))
    
    # Listen for incoming connections
    server_socket.listen(5)
    print(f"Server started at {server_ip}:{server_port}")
    
    while True:
        # Accept a new connection
        client_socket, client_address = server_socket.accept()
        print(f"Connection from {client_address} has been established.")
        
        # Receive data from the client
        data = client_socket.recv(1024).decode('utf-8')
        print(f"Received data: {data}")
        
        # Optionally send a response back to the client
        response = "Data received successfully"
        client_socket.send(response.encode('utf-8'))
        
        # Close the client connection
        client_socket.close()

if __name__ == "__main__":
    start_server()