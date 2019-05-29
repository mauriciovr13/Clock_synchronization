import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;

public class Coordenador {
    private static final int TIMEOUT = 250;
    private ArrayList<String> servidores;
    private int porta;
    private String coordenadorIP;
    private Relogio relogio;

    public Coordenador(String coordenadorIP, ArrayList<String> servidores, int porta) {
        this.servidores = servidores;
        this.porta = porta;
        this.coordenadorIP = coordenadorIP;
        relogio = new Relogio();
    }

    public ArrayList<Long> getTempoServidores() throws IOException {
        ArrayList<Long> temposServidores = new ArrayList<>();

        String mensagem = "TEMPO";
        byte[] bytesParaEnviar = mensagem.getBytes();
        byte[] bytesParaReceber = new byte[8];

        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(TIMEOUT);

        DatagramPacket pacoteEnviar;
        DatagramPacket pacoteReceber;
        boolean todosPacotesRecebidos;
        do {
            temposServidores.clear();
            todosPacotesRecebidos = true;
            for (int i = 0; i < servidores.size() && todosPacotesRecebidos; ++i) {
                InetAddress enderecoServer = InetAddress.getByName(servidores.get(i));
                pacoteEnviar = new DatagramPacket(bytesParaEnviar, bytesParaEnviar.length, enderecoServer, porta);
                pacoteReceber = new DatagramPacket(bytesParaReceber, bytesParaReceber.length);

                double tempoInicio = System.nanoTime();
                socket.send(pacoteEnviar);
                try {
                    socket.receive(pacoteReceber);
                    double tempoFinal = System.nanoTime();
                    double rtt = (tempoFinal - tempoInicio) / 1000000;
                    temposServidores.add(ByteBuffer.wrap(bytesParaReceber).getLong() - (long)(rtt / 2));
                } catch (InterruptedIOException e) {
                    todosPacotesRecebidos = false;
                }
            }
        } while(!todosPacotesRecebidos);
        socket.close();
        temposServidores.add(relogio.getRelogio());

        return temposServidores;
    }

    public void escreverTempo(ArrayList<Long> tempos) {
        System.out.println("Servidor: " + coordenadorIP + "Tempo: " + tempos.get(tempos.size() - 1) + " Data: " + new Date(tempos.get(tempos.size() - 1)));
        for (int i = 0; i < servidores.size(); ++i) {
            System.out.println("Servidor: " + servidores.get(i) + "Tempo: " + tempos.get(i) + " Data: " + new Date(tempos.get(i)));
        }
    }

    public static void main(String[] args) {
        ArrayList<String> servidores = new ArrayList<>();
        servidores.add("192.168.0.101");
        servidores.add("192.168.0.102");
        Coordenador coordenador = new Coordenador("192.168.0.100", servidores, 7013);
        for (int i = 0; i < coordenador.servidores.size(); i++) {
            System.out.println(coordenador.servidores.get(i) + "  " + coordenador.porta);
        }
    }
}
