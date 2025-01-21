import java.util.Random;

public class Carta {

    private int numero;
    private Colores color;
    private String cardType;

    private static final Random RANDOM = new Random();

    private enum Colores {
        ROJO("\u001B[31m"),
        AMARILLO("\u001B[33m"),
        VERDE("\u001B[32m"),
        AZUL("\u001B[34m");

        private final String ansiCode;

        Colores(String ansiCode) {
            this.ansiCode = ansiCode;
        }

        public String getAnsiCode() {
            return ansiCode;
        }

    }

    public Carta() {
        this.numero = getRandomNum();
        this.color = getRandomColor();
    }

    public int getRandomNum() {
        return RANDOM.nextInt(10);
    }

    public static Colores getRandomColor() {
        int randIndex = RANDOM.nextInt(Colores.values().length);
        return Colores.values()[randIndex];
    }

    @Override
    public String toString() {
        String resetColor = "\u001B[0m"; // Código para reiniciar el color
        return resetColor + "- " + color.getAnsiCode() + "[" + numero + "] " + resetColor;
    }
}
