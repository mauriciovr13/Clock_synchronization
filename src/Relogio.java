import java.util.Date;
import java.util.Random;

public class Relogio {
    private long erro;

    public Relogio() {
        Random rng = new Random();
        erro = rng.nextInt(30) * 1000;
    }

    public long getRelogio() {
        return (System.currentTimeMillis() + erro);
    }

    public void ajustaRelogio(long a) {
        erro += a;
    }

    public Date getDate() {
        return new Date(System.currentTimeMillis() + erro);
    }

    public static void main(String[] args) {
        Relogio r = new Relogio();
        System.out.println(r.getRelogio());
        System.out.println(r.getDate());
        r.ajustaRelogio(5000);
        System.out.println(r.getRelogio());
        System.out.println(r.getDate());
    }
}
