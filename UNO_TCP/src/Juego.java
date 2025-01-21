import java.util.ArrayList;
import java.util.List;

public class Juego {

    public List<Jugador> jugadores;
    public int indexJugadorActual;
    private boolean direccion;
    private Carta lastCard = new Carta();

    public Juego(List<Jugador> jugadores){
        this.jugadores = jugadores;
        this.indexJugadorActual = 0;
        this.direccion = true;
    }

    public void iniciarJuego(){
        for (Jugador jugador : jugadores){
            jugador.getInitialDeck();
        }

        System.out.println("El juego comienza con la carta: " + lastCard);
    }

    public void aplicarEfecto(Carta carta){

    }
}
