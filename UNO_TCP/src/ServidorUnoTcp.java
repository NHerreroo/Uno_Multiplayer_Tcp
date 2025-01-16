import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorUnoTcp {
    int port;

    List<InetAddress> playersIpList = new ArrayList<>();

    public ServidorUnoTcp(int port ) {
        this.port = port;
    }

    public void listen() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("SERVIDOR: Servidor escuchando en el puerto: " + port);
            playersIpList.add(InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()));
            showPlayerList();

            while(true) { //esperar connexió del client i llançar thread
                clientSocket = serverSocket.accept();
                InetAddress clientAddress = clientSocket.getInetAddress();

                if (!clientAddress.isLoopbackAddress()){
                    System.out.println("SERVIDOR: Cliente conectado: " + clientAddress);
                }

                playersIpList.add(clientAddress);
                showPlayerList();

            }
        } catch (IOException ex) {
            System.err.println("SERVIDOR: Error en el servidor: " + ex.getMessage());
        }
    }


    public void showPlayerList() {
        System.out.println("---- PLAYERS LIST -----");
        System.out.println();

        try {
            String localIp = InetAddress.getLocalHost().getHostAddress();
            playersIpList.forEach(ip -> {
                if (ip.equals(localIp)) {
                    System.out.println(ip + " (TU)");
                } else {
                    System.out.println(ip);
                }
            });
        } catch (UnknownHostException e) {
            System.err.println("Error retrieving local IP address: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        //int port = Integer.parseInt(args[0]);
        ServidorUnoTcp srv = new ServidorUnoTcp(559);
        srv.listen();

    }
}
