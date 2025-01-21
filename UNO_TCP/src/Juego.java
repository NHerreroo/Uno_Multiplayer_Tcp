import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Juego {

    public List<Jugador> jugadores;
    public int indexJugadorActual;
    private boolean direccion;
    private Carta lastCard;

    public Juego(List<Jugador> jugadores) {
        this.jugadores = jugadores;
        this.indexJugadorActual = 0;
        this.direccion = true;
        this.lastCard = new Carta();
    }

    public void iniciarJuego() {
        Scanner sc = new Scanner(System.in);
        Jugador jugador = jugadores.get(0); // Solo un jugador
        jugador.getInitialDeck();

        System.out.println("El juego comienza con la carta: " + lastCard);

        while (true) {
            System.out.println("\nTu mano:");
            jugador.showHand();
            System.out.println("Última carta en la mesa: " + lastCard);

            System.out.println("---- Elige una carta para jugar (1 a " + jugador.getHand().size() + ") o 0 para robar: ----");
            int selectedCard = sc.nextInt();

            if (selectedCard == 0) { // Robar carta
                jugador.drawCard(1, null);
                System.out.println("Has robado una carta.");
            } else if (selectedCard > 0 && selectedCard <= jugador.getHand().size()) {
                Carta cartaSeleccionada = jugador.getHand().get(selectedCard - 1);

                if (puedeJugar(cartaSeleccionada)) {
                    lastCard = cartaSeleccionada;
                    jugador.getHand().remove(selectedCard - 1);
                    System.out.println("Has jugado: " + lastCard);

                    aplicarEfecto(lastCard);

                    if (jugador.getHand().isEmpty()) {
                        System.out.println("\n¡Has ganado! ¡Felicidades!");
                        break;
                    }
                } else {
                    System.out.println("No puedes jugar esa carta. Intenta de nuevo.");
                }
            } else {
                System.out.println("Selección inválida. Intenta de nuevo.");
            }
        }

        sc.close();
    }

    private boolean puedeJugar(Carta carta) {
        return carta.getColor() == lastCard.getColor() ||
                carta.getTipoCarta() == lastCard.getTipoCarta() ||
                carta.getTipoCarta() == Carta.TipoCarta.NORMAL && carta.getNumero() == lastCard.getNumero();
    }

    public void aplicarEfecto(Carta carta) {
        switch (carta.getTipoCarta()) {
            case MAS_DOS -> {
                System.out.println("Efecto: Roba 2 cartas (aún no implementado).");
            }
            case SALTA -> {
                System.out.println("Efecto: Turno saltado (aún no implementado).");
            }
            case REVERSA -> {
                System.out.println("Efecto: Cambio de sentido (irrelevante con un jugador).");
            }
            default -> {
           
            }
        }
    }

    public static void main(String[] args) {
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador());
        Juego juego = new Juego(jugadores);
        juego.iniciarJuego();
    }
}
