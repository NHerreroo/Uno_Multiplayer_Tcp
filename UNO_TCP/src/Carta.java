import java.util.Random;

public class Carta {

    private int numero;
    private Colores color;
    private TipoCarta tipoCarta;

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

    enum TipoCarta {
        NORMAL,    // Carta normal con número
        MAS_DOS,   // Carta +2
        SALTA,     // Carta Salta Turno
        REVERSA    // Carta Cambio de Sentido
    }

    public Carta() {
        this.tipoCarta = getRandomTipoCarta();
        if (tipoCarta == TipoCarta.NORMAL) {
            this.numero = getRandomNum();
        } else {
            this.numero = -1; // No tiene número en cartas especiales
        }
        this.color = getRandomColor();
    }

    private int getRandomNum() {
        return RANDOM.nextInt(10); // Números del 0 al 9
    }

    private static Colores getRandomColor() {
        int randIndex = RANDOM.nextInt(Colores.values().length);
        return Colores.values()[randIndex];
    }

    private TipoCarta getRandomTipoCarta() {
        return TipoCarta.NORMAL;
//        int randIndex = RANDOM.nextInt(10); // Ajusta la probabilidad aquí
//        if (randIndex < 6) {
//            return TipoCarta.NORMAL; // 60% probabilidad de carta normal
//        } else if (randIndex < 8) {
//            return TipoCarta.MAS_DOS; // 20% probabilidad de +2
//        } else if (randIndex == 8) {
//            return TipoCarta.SALTA; // 10% probabilidad de Salta Turno
//        } else {
//            return TipoCarta.REVERSA; // 10% probabilidad de Cambio de Sentido
//        }
    }

    @Override
    public String toString() {
        String resetColor = "\u001B[0m"; // Código para reiniciar el color
        String tipo = "";

        switch (tipoCarta) {
            case NORMAL -> tipo = "[" + numero + "]";
            case MAS_DOS -> tipo = "+2";
            case SALTA -> tipo = "Salta Turno";
            case REVERSA -> tipo = "Cambio de Sentido";
        }

        return resetColor + "- " + color.getAnsiCode() + tipo + resetColor;
    }

    public TipoCarta getTipoCarta() {
        return tipoCarta;
    }

    public Colores getColor() {
        return color;
    }

    public int getNumero() {
        return numero;
    }
}
