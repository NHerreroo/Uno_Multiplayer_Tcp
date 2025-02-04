import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteUnoTcp {
    private String hostname;
    private int port;
    private String nombre;

    public ClienteUnoTcp(String hostname, int port, String nombre) {
        this.hostname = hostname;
        this.port = port;
        this.nombre = nombre;
    }

    public void connect() {
        try (Socket socket = new Socket(hostname, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Conectado al servidor " + hostname);

            // Enviar el nombre al servidor
            out.println(nombre);

            new Thread(() -> escucharMensajes(in)).start();

            Scanner sc = new Scanner(System.in);
            String mensaje;
            while (true) {
                mensaje = sc.nextLine();
                if (mensaje.equalsIgnoreCase("salir")) break;
                out.println(mensaje);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void escucharMensajes(BufferedReader in) {
        try {
            String mensaje;
            while ((mensaje = in.readLine()) != null) {
                System.out.println(mensaje);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}