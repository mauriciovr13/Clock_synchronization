import java.io.IOException;
import java.util.ArrayList;

public class Berkley{

    public static void main(String[] args) {

        ArrayList<String> servidores = new ArrayList<>();
        servidores.add("192.168.0.100");
        servidores.add("192.168.0.102");
        Coordenador coordenador = new Coordenador("192.168.0.100", servidores, 5002);

        try {
            ArrayList<Long> tempos = coordenador.getTempoServidores();
            coordenador.escreverTempo(tempos);
        }
        catch(IOException ioe) {
            System.out.println("ERRO");
        }
    }
}
