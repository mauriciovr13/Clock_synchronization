import java.io.*; 
import java.net.*; 
  
class Server { 
  public static void main(String args[]) throws Exception 
    { 
     try
     { 
      DatagramSocket serverSocket = new DatagramSocket(5002);
  
      byte[] receiveData = new byte[1024]; 
      byte[] sendData  = new byte[1024]; 
  
      while(true) 
        { 
  
          receiveData = new byte[1024]; 

          DatagramPacket receivePacket = 
             new DatagramPacket(receiveData, receiveData.length); 


          serverSocket.receive(receivePacket); 

          String message = new String(receivePacket.getData()); 
  
          InetAddress IPAddress = receivePacket.getAddress(); 
		
			if (message.equals("TEMPO")) {
				DatagramPacket enviar;
				double tempo = System.nanoTime();
	          	int port = receivePacket.getPort(); 
				String mensagem = Double.toString(tempo);
				byte[] bytesParaEnviar = mensagem.getBytes();
				
				enviar = new DatagramPacket(bytesParaEnviar, bytesParaEnviar.length, IPAddress,port);
			
			serverSocket.send(enviar);
			} else {

				//arruma o relogio
			}
  
        } 

     }
      catch (SocketException ex) {
        System.out.println(ex);
        System.exit(1);
      }

    } 
}  

