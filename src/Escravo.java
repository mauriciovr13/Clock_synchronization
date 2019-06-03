import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

class Escravo{
    public static void main(String[] args) throws Exception {
        Relogio relogio = new Relogio();
        System.out.println("Servidor iniciado!");
        try {
            DatagramSocket serverSocket = new DatagramSocket(5002);

            byte[] receiveData = new byte[1024];
            byte[] bytesParaEnviar;

            while(true) {
                DatagramPacket pacoteReceber = new DatagramPacket(receiveData, receiveData.length);
                DatagramPacket pacoteEnviar;

                serverSocket.receive(pacoteReceber);
                String mensagem = new String(pacoteReceber.getData(), pacoteReceber.getOffset(), pacoteReceber.getLength(), StandardCharsets.UTF_8);

                InetAddress enderecoIP = pacoteReceber.getAddress();
                int porta = pacoteReceber.getPort();

                if (mensagem.equals("TEMPO")) {
                    long tempo = relogio.getRelogio();
                    bytesParaEnviar = ByteBuffer.allocate(Long.BYTES).putLong(tempo).array();

                    pacoteEnviar = new DatagramPacket(bytesParaEnviar, bytesParaEnviar.length, enderecoIP, porta);
                    serverSocket.send(pacoteEnviar);
                }
                else {
                    relogio.ajustaRelogio(Long.parseLong(mensagem));
                    System.out.println(relogio.getDate());

                    String mensagemEnviar = "OK";
                    bytesParaEnviar = mensagemEnviar.getBytes(StandardCharsets.UTF_8);
                    pacoteEnviar = new DatagramPacket(bytesParaEnviar, bytesParaEnviar.length, enderecoIP, porta);
                    serverSocket.send(pacoteEnviar);
                }
            }
        }
        catch (SocketException ex) {
            System.out.println("ERRO");
        }
    }
}
