import java.io.IOException;
import java.util.ArrayList;

public class Berkley{

    public static void main(String[] args) {

        ArrayList<String> servidores = new ArrayList<>();
        servidores.add("192.168.0.103");
        servidores.add("192.168.0.104");
        Coordenador coordenador = new Coordenador("192.168.0.100", servidores, 5002);

        try {
            ArrayList<Long> tempos = coordenador.getTempoServidores();
            System.out.println("Tempo antes do ajuste");
            coordenador.escreverTempo(tempos);
            long media = calcularMedia(tempos);
            coordenador.ajustaTempo(tempos, media);
            System.out.println("\nTempo depois do ajuste");
            coordenador.escreverTempo(tempos);
        }
        catch(IOException ioe) {
            System.out.println("Erro com o socket.");
        }
    }

    private static long calcularMedia(ArrayList<Long> tempos){
        long media = 0;
        for (Long tempo : tempos) {
            media += tempo;
        }
        return media / tempos.size();
    }
}
