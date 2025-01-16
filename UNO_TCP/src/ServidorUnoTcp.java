import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorUnoTcp {
    int port;
    ArrayList<InetAddress> ips = new ArrayList<>();

    public ServidorUnoTcp(int port ) {
        this.port = port;
    }

    public void listen() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Servidor escuchando en el puerto: " + port);

            while(true) { //esperar connexió del client i llançar thread
                clientSocket = serverSocket.accept();

                //Arreglar que si es la de loopback (YO) no mostrar en pantalla, solo los jugadores que se estan conectando
                InetAddress ip = clientSocket.getInetAddress();

//                if (!ip.equals(InetAddress)){
//                    System.out.println("Cliente conectado: " + ip);
//                }

//                ips.add(ip);
//                System.out.println(ip);
            }
        } catch (IOException ex) {
            System.err.println("Error en el servidor: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        //int port = Integer.parseInt(args[0]);
        ServidorUnoTcp srv = new ServidorUnoTcp(5558);
        srv.listen();

    }
}
