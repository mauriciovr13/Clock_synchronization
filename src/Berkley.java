import java.io.IOException;
import java.util.ArrayList;

public class Berkley{

    public static void main(String[] args) {

        ArrayList<String> servidores = new ArrayList<>();
        servidores.add("127.0.0.1");
        servidores.add("192.168.0.101");
        Coordenador coordenador = new Coordenador("192.168.0.13", servidores, 5002);

        try {
            ArrayList<Long> tempos = coordenador.getTempoServidores();
            System.out.println("Tempo antes do ajuste");
            coordenador.escreverTempo(tempos);
            long media =  calculaMedia(tempos);
            coordenador.ajustaTempo(tempos, media);
            System.out.println("\nTempo depois do ajuste");
            coordenador.escreverTempo(tempos);


        }
        catch(IOException ioe) {
            System.out.println("ERRO");
        }
    }

    public static long calculaMedia(ArrayList<Long> tempos){
        long media = 0;
        for(int i = 0; i < tempos.size(); i++) {
            media += tempos.get(i);
        }
        return media/tempos.size();
    }
}
