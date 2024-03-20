import socket
import time
def door(QRCODE):
    server_ip = 'localhost' # Change this to the IP address of your server
    server_port = 8764  # Change this to the port your server is listening on

    # Create a socket object
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Connect to the server
    client_socket.connect((server_ip, server_port))
    client_socket.send(b"TYPE:03-ID:0001-")
    client_socket.send(("qrcode:{0}-".format(QRCODE)).encode('utf-8'))
    client_socket.close()
def shelf(CODE):
    # Define the server's IP address and port number
    server_ip = 'localhost' # Change this to the IP address of your server
    server_port = 8764  # Change this to the port your server is listening on

    # Create a socket object
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Connect to the server
    client_socket.connect((server_ip, server_port))
    client_socket.send(b"TYPE:02-ID:0001-")
    #client_socket.send(b"qrcode:-")
    #barcode
    client_socket.send(b"RFID:7777777-")
    client_socket.send(b"barcode:13574376-")
    #client_socket.send(b"consum:0001-")
    #client_socket.send(("consum:{0}-".format(CODE)).encode('utf-8'))

    client_socket.close()
