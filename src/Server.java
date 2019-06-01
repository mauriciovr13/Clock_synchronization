import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

class Server {
    public static void main(String[] args) throws Exception {
        Relogio relogio = new Relogio();
        try {
            DatagramSocket serverSocket = new DatagramSocket(5002);

            byte[] receiveData = new byte[1024];
            byte[] sendData  = new byte[1024];

            while(true) {
                DatagramPacket pacoteReceber = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(pacoteReceber);
                String mensagem = new String(pacoteReceber.getData(), pacoteReceber.getOffset(), pacoteReceber.getLength(), StandardCharsets.UTF_8);
                InetAddress enderecoIP = pacoteReceber.getAddress();

                if (mensagem.equals("TEMPO")) {
                    DatagramPacket enviar;
                    long tempo = relogio.getRelogio();
                    int porta = pacoteReceber.getPort();
                    byte[] bytesParaEnviar = ByteBuffer.allocate(Long.BYTES).putLong(tempo).array();

                    System.out.println(tempo);
                    enviar = new DatagramPacket(bytesParaEnviar, bytesParaEnviar.length, enderecoIP, porta);
                    serverSocket.send(enviar);
                }
                else {
                    //arruma o relogio
                }
            }
        }
        catch (SocketException ex) {
            System.out.println("ERRO");
        }
    }
}