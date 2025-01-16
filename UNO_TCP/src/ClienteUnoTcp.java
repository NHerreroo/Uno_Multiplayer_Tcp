import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteUnoTcp extends Thread {

    String playerName;
    String hostname;
    int port;



    public ClienteUnoTcp(String hostname, int port, String playerName) {
        this.hostname = hostname;
        this.port = port;
        this.playerName = playerName;

    }

    public void connect(){
        try {
            InetAddress serverAddress = InetAddress.getByName(hostname);

            // Comprobar si la dirección del servidor no es loopback
            if (!serverAddress.isLoopbackAddress()) {
                try (Socket socket = new Socket(serverAddress, port)) {
                    System.out.println("CLIENTE: Conectado al servidor: " + hostname + " en el puerto: " + port);
                }
            }else{
                System.out.println("CLIENTE: " + InetAddress.getLocalHost().getHostAddress() + " (TU)");
            }

        } catch (Exception e) {
            System.err.println("CLIENTE: Error al conectar al servidor: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
		/*if (args.length != 2) {
            System.err.println(
                "Usage: java ClientTcpAdivina <host name> <port number>");
            System.exit(1);
        }*/

        // String hostName = args[0];
        // int portNumber = Integer.parseInt(args[1]);
        Scanner sc = new Scanner(System.in);
        String playerName = sc.nextLine();

        ClienteUnoTcp clientTcp = new ClienteUnoTcp("localhost",559, playerName);
        clientTcp.start();
    }
}
