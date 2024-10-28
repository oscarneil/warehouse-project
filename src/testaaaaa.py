import socket
import threading
def Access03(QRCODE):
    server_ip = 'localhost' # Change this to the IP address of your server
    server_port = 8764  # Change this to the port your server is listening on

    # Create a socket object
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Connect to the server
    client_socket.connect((server_ip, server_port))
    client_socket.send(("03A:{0}-".format(QRCODE)).encode('utf-8'))
    client_socket.close()
    
def Access02():
    server_ip = 'localhost' # Change this to the IP address of your server
    server_port = 8764  # Change this to the port your server is listening on

    # Create a socket object
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Connect to the server
    client_socket.connect((server_ip, server_port))
    client_socket.send(("02A00001-").encode('utf-8'))
    client_socket.close()
    
def Led02():
    server_ip = 'localhost' # Change this to the IP address of your server
    server_port = 8764  # Change this to the port your server is listening on

    # Create a socket object
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Connect to the server
    client_socket.connect((server_ip, server_port))
    client_socket.send(("02L00001-").encode('utf-8'))
    client_socket.close()
    
def Expensive02():
    server_ip = 'localhost' # Change this to the IP address of your server
    server_port = 8764  # Change this to the port your server is listening on

    # Create a socket object
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Connect to the server
    client_socket.connect((server_ip, server_port))
    client_socket.send(("02E00001-").encode('utf-8'))
    client_socket.close()

def Comsumable02():
    server_ip = 'localhost' # Change this to the IP address of your server
    server_port = 8764  # Change this to the port your server is listening on

    # Create a socket object
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Connect to the server
    client_socket.connect((server_ip, server_port))
    client_socket.send(("02C0000-").encode('utf-8'))
    client_socket.close()
    
def Vending02():
    server_ip = 'localhost' # Change this to the IP address of your server
    server_port = 8764  # Change this to the port your server is listening on

    # Create a socket object
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Connect to the server
    client_socket.connect((server_ip, server_port))
    client_socket.send(("02V00001-").encode('utf-8'))
    client_socket.close()
        
def Expensive03():
    server_ip = 'localhost' # Change this to the IP address of your server
    server_port = 8764  # Change this to the port your server is listening on

    # Create a socket object
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Connect to the server
    client_socket.connect((server_ip, server_port))
    client_socket.send("03E:11263137:8460BD33-".encode('utf-8'))
    client_socket.close()
    
def Vending03():
    server_ip = 'localhost' # Change this to the IP address of your server
    server_port = 8764  # Change this to the port your server is listening on

    # Create a socket object
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Connect to the server
    client_socket.connect((server_ip, server_port))
    client_socket.send("03V:11263137:8460BD33-".encode('utf-8'))
    client_socket.close()
    
def Consumable03():
    server_ip = 'localhost' # Change this to the IP address of your server
    server_port = 8764  # Change this to the port your server is listening on

    # Create a socket object
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Connect to the server
    client_socket.connect((server_ip, server_port))
    client_socket.send("03C:0000:150:8460BD33-".encode('utf-8'))
    client_socket.close()
    
def nodeDeviceInit():
    Access02();
    Led02();
    Comsumable02();
    Expensive02();
    Vending02();
    
 # threads = []
 # Change this number to the number of simultaneous packets you want to send

 # for _ in range(10):
 #   thread = threading.Thread(target=Access02)
 #   threads.append(thread)
 #  thread.start()

 #for thread in threads:
 #  thread.join()