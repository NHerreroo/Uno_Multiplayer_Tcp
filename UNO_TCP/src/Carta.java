import java.util.Random;

public class Carta {

    private int numero;
    private String color;

    private enum coloresEnum {
        ROJO,
        AMARILLO,
        VERDE,
        AZUL
    }

    public int getRandomNum() {
        return new Random().nextInt(10);
    }

    public String getRandomColor() {
        int randIndex = new Random().nextInt(coloresEnum.values().length);
        return coloresEnum.values()[randIndex].toString();
    }

    public static void main(String[] args) {
        Carta c = new Carta();
        System.out.println(c.getRandomColor() + " " + c.getRandomNum());
    }
}
