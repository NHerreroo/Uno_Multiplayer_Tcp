import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Jugador {

    private String nombre;

    private Map<Integer, String> mano = new HashMap<Integer,String>();


    public void agregarCarta(int num, String color) {
        mano.put(num,color);
    }

    public static void main(String[] args) {
        Jugador j = new Jugador();
        Carta c = new Carta();

        j.mano.put(c.getRandomNum(),c.getRandomColor());
        j.mano.put(c.getRandomNum(),c.getRandomColor());
        j.mano.put(c.getRandomNum(),c.getRandomColor());
        j.mano.put(c.getRandomNum(),c.getRandomColor());
        j.mano.put(c.getRandomNum(),c.getRandomColor());
        j.mano.put(c.getRandomNum(),c.getRandomColor());
        j.mano.put(c.getRandomNum(),c.getRandomColor());
        j.mano.put(c.getRandomNum(),c.getRandomColor());

        j.mano.forEach((num, color) -> System.out.println("Num: " + num + ", Color: " + color));
    }

}
