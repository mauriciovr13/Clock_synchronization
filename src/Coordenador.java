import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

public class Coordenador {
    private static final int TIMEOUT = 250;
    private ArrayList<String> servidores;
    private int porta;
    private String coordenadorIP;
    private Relogio relogio;
    private DatagramPacket pacoteEnviar;
    private DatagramPacket pacoteReceber;

    public Coordenador(String coordenadorIP, ArrayList<String> servidores, int porta) {
        this.servidores = servidores;
        this.porta = porta;
        this.coordenadorIP = coordenadorIP;
        relogio = new Relogio();
    }

    public ArrayList<Long> getTempoServidores() throws IOException {
        ArrayList<Long> temposServidores = new ArrayList<>();

        String mensagem = "TEMPO";
        byte[] bytesParaEnviar = mensagem.getBytes(StandardCharsets.UTF_8);
        byte[] bytesParaReceber = new byte[8];

        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(TIMEOUT);

        boolean todosPacotesRecebidos;
        do {
            temposServidores.clear();
            todosPacotesRecebidos = true;
            for (int i = 0; i < servidores.size() && todosPacotesRecebidos; ++i) {
                InetAddress enderecoServer = InetAddress.getByName(servidores.get(i));

                pacoteEnviar = new DatagramPacket(bytesParaEnviar, bytesParaEnviar.length, enderecoServer, porta);
                pacoteReceber = new DatagramPacket(bytesParaReceber, bytesParaReceber.length);

                socket.send(pacoteEnviar);
                try {
                    socket.receive(pacoteReceber);
                    temposServidores.add(ByteBuffer.wrap(pacoteReceber.getData()).getLong());
                } catch (InterruptedIOException e) {
                    todosPacotesRecebidos = false;
                }
            }
        } while(!todosPacotesRecebidos);
        socket.close();

        temposServidores.add(relogio.getRelogio());

        return temposServidores;
    }

    public void ajustaTempo(ArrayList<Long> tempos, long media) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(TIMEOUT);

        for(int i = 0; i < tempos.size()-1; i++) {
            long desvio = media - tempos.get(i);

            String mensagem = Long.toString(desvio);
            byte[] bytesParaEnviar = mensagem.getBytes(StandardCharsets.UTF_8);
            byte[] bytesParaReceber = new byte[1024];

            InetAddress enderecoServer = InetAddress.getByName(servidores.get(i));

            pacoteEnviar = new DatagramPacket(bytesParaEnviar, bytesParaEnviar.length, enderecoServer, porta);
            pacoteReceber = new DatagramPacket(bytesParaReceber, bytesParaReceber.length);

            socket.send(pacoteEnviar);
            try {
                socket.receive(pacoteReceber);
                tempos.set(i, tempos.get(i) + desvio);
            } catch (InterruptedIOException e) {
                --i;
            }
        }
        socket.close();

        int tam = tempos.size() - 1;
        relogio.ajustaRelogio(media - tempos.get(tam));
        long desvio = media - tempos.get(tam);
        tempos.set(tam, tempos.get(tam) + desvio);
    }

    public void escreverTempo(ArrayList<Long> tempos) {
        System.out.println("Coordenador: " + coordenadorIP + " Tempo: " + tempos.get(tempos.size() - 1) + " Data: " + new Date(tempos.get(tempos.size() - 1)));
        for (int i = 0; i < servidores.size(); ++i) {
            long tempo = tempos.get(i);
            System.out.println("Escravo: " + servidores.get(i) + " Tempo: " + tempo + " Data: " + new Date(tempo));
        }
    }
}
