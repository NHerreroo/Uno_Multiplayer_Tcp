import java.util.ArrayList;

public class Jugador {
    private String name;
    private ArrayList<Carta> hand = new ArrayList<>();
    private boolean isActive;

    public Jugador() {
        // Constructor vacío por defecto
    }

    public Jugador(String name) {
        this.name = name;
    }

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

    public ArrayList<Carta> getHand() {
        return hand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}