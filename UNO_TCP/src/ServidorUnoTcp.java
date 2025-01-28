import java.io.*;
import java.net.*;
import java.util.*;

// ServidorUnoTcp.java
import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorUnoTcp {
    private int port;
    public List<ClienteHandler> clientes = new ArrayList<>();
    private boolean partidaIniciada = false;
    private int turnoActual = 0;
    private Carta cartaActual = new Carta();

    public ServidorUnoTcp(int port) {
        this.port = port;
    }

    public void listen() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor iniciado en el puerto " + port);

            while (!partidaIniciada) {
                Socket clienteSocket = serverSocket.accept();
                ClienteHandler clienteHandler = new ClienteHandler(clienteSocket, this);
                clientes.add(clienteHandler);
                new Thread(clienteHandler).start();

                if (clientes.size() == 2) {
                    iniciarPartida();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void iniciarPartida() {
        partidaIniciada = true;
        enviarMensajeATodos("La partida ha comenzado. Primera carta: " + cartaActual);
        notificarTurno();
    }

    public synchronized void procesarJugada(int idJugador, String jugada) {
        if (idJugador != turnoActual) {
            enviarMensaje(clientes.get(idJugador), "No es tu turno.");
            return;
        }

        Jugador jugador = clientes.get(idJugador).getJugador();

        if (jugada.equals("0")) { // Robar carta
            Carta nuevaCarta = new Carta();
            jugador.getHand().add(nuevaCarta);
            enviarMensaje(clientes.get(idJugador), "Has robado una carta: " + nuevaCarta);
        } else {
            try {
                int index = Integer.parseInt(jugada) - 1;
                if (index < 0 || index >= jugador.getHand().size()) {
                    enviarMensaje(clientes.get(idJugador), "Jugada inválida. Intenta de nuevo.");
                    return;
                }

                Carta cartaSeleccionada = jugador.getHand().get(index);
                if (puedeJugar(cartaSeleccionada)) {
                    cartaActual = cartaSeleccionada;
                    jugador.getHand().remove(index);
                    enviarMensajeATodos("Jugador " + idJugador + " jugó: " + cartaActual);

                    if (jugador.getHand().isEmpty()) {
                        enviarMensajeATodos("Jugador " + idJugador + " ha ganado la partida!");
                        System.exit(0);
                    }
                } else {
                    enviarMensaje(clientes.get(idJugador), "No puedes jugar esa carta. Intenta con otra.");
                    return;
                }
            } catch (Exception e) {
                enviarMensaje(clientes.get(idJugador), "Jugada inválida. Intenta de nuevo.");
                return;
            }
        }

        turnoActual = (turnoActual + 1) % clientes.size();
        notificarTurno();
    }

    private boolean puedeJugar(Carta carta) {
        return carta.getColor() == cartaActual.getColor() ||
                carta.getTipoCarta() == cartaActual.getTipoCarta() ||
                (carta.getTipoCarta() == Carta.TipoCarta.NORMAL && carta.getNumero() == cartaActual.getNumero());
    }

    private void notificarTurno() {
        for (int i = 0; i < clientes.size(); i++) {
            if (i == turnoActual) {
                enviarMensaje(clientes.get(i), "======================================================\n" +
                        "Es tu turno.\n" +
                        "Carta actual en juego: " + cartaActual + "\n" +
                        "Tu mano:\n" + formatearMano(clientes.get(i).getJugador().getHand()) +
                        "\nSelecciona una carta ingresando el número correspondiente o escribe 0 para robar una carta.\n" +
                        "======================================================");
            } else {
                enviarMensaje(clientes.get(i), "Espera tu turno.");
            }
        }
    }

    private String formatearMano(List<Carta> mano) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mano.size(); i++) {
            sb.append(i + 1).append(". ").append(mano.get(i)).append("\n");
        }
        return sb.toString();
    }

    public synchronized void enviarMensaje(ClienteHandler cliente, String mensaje) {
        cliente.enviarMensaje(mensaje);
    }

    public synchronized void enviarMensajeATodos(String mensaje) {
        for (ClienteHandler cliente : clientes) {
            cliente.enviarMensaje(mensaje);
        }
    }
}

class ClienteHandler implements Runnable {
    private Socket socket;
    private ServidorUnoTcp servidor;
    private PrintWriter out;
    private BufferedReader in;
    private Jugador jugador;

    public ClienteHandler(Socket socket, ServidorUnoTcp servidor) {
        this.socket = socket;
        this.servidor = servidor;
        this.jugador = new Jugador();
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            jugador.getInitialDeck();

            String mensaje;
            while ((mensaje = in.readLine()) != null) {
                servidor.procesarJugada(servidor.clientes.indexOf(this), mensaje);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarMensaje(String mensaje) {
        if (out != null) {
            out.println(mensaje);
        }
    }

    public Jugador getJugador() {
        return jugador;
    }
}