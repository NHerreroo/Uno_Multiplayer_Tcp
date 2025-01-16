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

    String hostname;
    int port;


    public ClienteUnoTcp(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;

    }

    public void connect(){
        try (Socket socket = new Socket(InetAddress.getByName(hostname), port)){
            System.out.println("Conectado al servidor: " + hostname + " en el puerto: " + port);
        } catch (Exception e) {
            System.err.println("Error al conectar al servidor: " + e.getMessage());
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
        ClienteUnoTcp clientTcp = new ClienteUnoTcp("localhost",5558);
        clientTcp.start();
    }
}
