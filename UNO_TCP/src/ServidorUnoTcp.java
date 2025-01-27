import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorUnoTcp {
    private int port;
    private List<ClienteHandler> clientes = new ArrayList<>();
    private boolean partidaIniciada = false;

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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void enviarMensajeATodos(String mensaje) {
        for (ClienteHandler cliente : clientes) {
            cliente.enviarMensaje(mensaje);
        }
    }

    public synchronized void iniciarPartida() {
        partidaIniciada = true;
        enviarMensajeATodos("La partida ha comenzado.");
        // Aquí podrías inicializar la lógica del juego.
    }

    public synchronized List<String> obtenerNombresJugadores() {
        List<String> nombres = new ArrayList<>();
        for (ClienteHandler cliente : clientes) {
            nombres.add(cliente.getNombre());
        }
        return nombres;
    }
}

class ClienteHandler implements Runnable {
    private Socket socket;
    private ServidorUnoTcp servidor;
    private PrintWriter out;
    private String nombre;

    public ClienteHandler(Socket socket, ServidorUnoTcp servidor) {
        this.socket = socket;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Bienvenido al servidor UNO. Ingresa tu nombre:");
            nombre = in.readLine();

            servidor.enviarMensajeATodos(nombre + " se ha unido a la partida.");
            mostrarJugadores();

            String mensaje;
            while ((mensaje = in.readLine()) != null) {
                if (mensaje.equalsIgnoreCase("iniciar")) {
                    servidor.iniciarPartida();
                    break;
                }
                servidor.enviarMensajeATodos(nombre + ": " + mensaje);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
    }

    public void enviarMensaje(String mensaje) {
        if (out != null) {
            out.println(mensaje);
        }
    }

    public String getNombre() {
        return nombre;
    }

    private void cerrarConexion() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        servidor.enviarMensajeATodos(nombre + " ha abandonado la partida.");
    }

    private void mostrarJugadores() {
        List<String> jugadores = servidor.obtenerNombresJugadores();
        out.println("Jugadores en la sala:");
        for (String jugador : jugadores) {
            out.println("- " + jugador);
        }
    }
}
