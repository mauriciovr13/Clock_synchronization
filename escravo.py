import socket
import os

udp_ip = "127.0.0.1"
udp_port = 5005

# socket.AF_INET (Internet)
# socket.SOCK_DGRAM (UDP)
sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
                     
# Faz o bind local. Associa um socket com um IP e uma Porta.
sock.bind((udp_ip, udp_port))

while True:
    mensage, addr = sock.recvfrom(1024) # Tamanho do buffer eh 1024 bytes
    mensage = mensage.decode()
    print("Mensagem recebida:", mensage)


