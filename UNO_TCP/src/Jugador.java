import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Jugador {

    private String name;

    private ArrayList<Carta> hand = new ArrayList<>();
    private boolean isActive;


    public void getInitialDeck(){
        for (int i = 0; i < 7; i++){
            hand.add(new Carta());
        }
    }

    public void drawCard(int num, String color) {
        hand.add(new Carta());
    }

    public void showHand(){
        for (Carta c : hand) System.out.println((hand.indexOf(c) + 1) + " " + c);
    }

    public static void main(String[] args) {
        Jugador j = new Jugador();
        j.getInitialDeck();

        j.showHand();

    }

    public ArrayList<Carta> getHand() {
        return hand;
    }
}
