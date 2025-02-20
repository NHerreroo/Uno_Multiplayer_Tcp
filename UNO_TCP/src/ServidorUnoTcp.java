import java.io.*;
import java.net.*;
import java.util.*;

import java.io.*;
import java.net.*;
import java.util.*;

import java.io.*;
import java.net.*;
import java.util.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorUnoTcp {
    private int port;
    private boolean servidorActivo = true;
    public List<ClienteHandler> clientes = new ArrayList<>();
    private int turnoActual = 0;
    private Carta cartaActual = new Carta();
    private ServerSocket serverSocket;

    public ServidorUnoTcp(int port) {
        this.port = port;
    }

    public void listen() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Servidor iniciado en el puerto " + port);

            while (servidorActivo) {
                Socket clienteSocket = serverSocket.accept();
                ClienteHandler clienteHandler = new ClienteHandler(clienteSocket, this);
                clientes.add(clienteHandler);
                new Thread(clienteHandler).start();

                if (clientes.size() == 2) {
                    iniciarPartida();
                }
            }
        } catch (IOException e) {
            if (servidorActivo) {
                e.printStackTrace();
            }
        } finally {
            cerrarServidor();
        }
    }

    private synchronized void iniciarPartida() {
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
                    enviarMensajeATodos("Jugador " + clientes.get(idJugador).getNombre() + " jugó: " + cartaActual);

                    if (jugador.getHand().isEmpty()) {
                        enviarMensajeATodos("Jugador " + clientes.get(idJugador).getNombre() + " ha ganado la partida!");
                        detenerServidor();
                        return;
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
        return carta.getColor() == cartaActual.getColor() || carta.getNumero() == cartaActual.getNumero();
    }

    private void notificarTurno() {
        for (int i = 0; i < clientes.size(); i++) {
            if (i == turnoActual) {
                enviarMensaje(clientes.get(i), "======================================================\n" +
                        "Es tu turno, " + clientes.get(i).getNombre() + ".\n" +
                        "Carta actual en juego: " + cartaActual + "\n" +
                        "Tu mano:\n" + formatearMano(clientes.get(i).getJugador().getHand()) +
                        "\nSelecciona una carta ingresando el número correspondiente o escribe 0 para robar una carta.\n" +
                        "\nSi lo deseas, escribe 'salir' para ir al menú princiapl.\n" +
                        "======================================================");
            } else {
                enviarMensaje(clientes.get(i), "Espera tu turno. Es el turno de " + clientes.get(turnoActual).getNombre() + ".");
                enviarMensaje(clientes.get(i), "Si lo deseas, escribe 'salir' para ir al menú principal.");
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

    public synchronized void notificarDesconexion(ClienteHandler clienteDesconectado) {
        clientes.remove(clienteDesconectado);

        if (!clientes.isEmpty()) {
            ClienteHandler jugadorRestante = clientes.get(0);
            jugadorRestante.enviarMensaje("El jugador " + clienteDesconectado.getNombre() + " se ha desconectado.");
            jugadorRestante.enviarMensaje("¡Has ganado automáticamente la partida!");
            jugadorRestante.enviarMensaje("Escribe 'menu' para volver al menú principal.");
        } else {
            System.out.println("Todos los jugadores se han desconectado. Cerrando el servidor...");
            detenerServidor();
        }
    }

    public void detenerServidor() {
        servidorActivo = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cerrarServidor() {
        System.out.println("Servidor cerrado.");
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

/**
 * Clase interna para manejar la conexión de cada cliente
 */
class ClienteHandler implements Runnable {
    private Socket socket;
    private ServidorUnoTcp servidor;
    private PrintWriter out;
    private BufferedReader in;
    private Jugador jugador;
    private String nombre;

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

            // Recibir el nombre del jugador
            this.nombre = in.readLine();
            System.out.println("Jugador conectado: " + nombre);

            jugador.getInitialDeck();

            String mensaje;
            while ((mensaje = in.readLine()) != null) {
                if (mensaje.equalsIgnoreCase("salir") || mensaje.equalsIgnoreCase("menu")) {
                    System.out.println("El jugador " + nombre + " ha salido de la partida.");
                    servidor.notificarDesconexion(this);
                    break;
                }
                servidor.procesarJugada(servidor.clientes.indexOf(this), mensaje);
            }
        } catch (IOException e) {
            System.out.println("El jugador " + nombre + " se ha desconectado.");
            servidor.notificarDesconexion(this);
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public String getNombre() {
        return nombre;
    }
}