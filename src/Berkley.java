import java.util.ArrayList;

public class Berkley{

	public static void main(String[] args) {

		ArrayList<String> servidores = new ArrayList<>();
		servidores.add("192.168.0.101");
		servidores.add("192.168.0.102");
		Coordenador coordenador = new Coordenador("192.168.0.100", servidores, 7013);

		for (int i = 0; i < coordenador.getSize(); i++) {
			System.out.println(coordenador.getServidor(i) + "  " + coordenador.getPorta());
		}
	}

}
