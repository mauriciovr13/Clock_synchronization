import socket

udp_ip = "127.0.0.1"
udp_port = 5005

sock = socket.socket(socket.AF_INET, # Internet 
				 socket.SOCK_DGRAM)  # UDP

# mensagem = "Teste"
while True:
    sendMessage = input("Digite Sua Mensagem: ")
    # sendMessage = bytes(sendMessage, 'utf-8')
    sendMessage = str.encode(sendMessage)
    sock.sendto(sendMessage, (udp_ip, udp_port))