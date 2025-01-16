import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.IllegalFormatCodePointException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorUnoTcp {
    int port;

    public ServidorUnoTcp(int port ) {
        this.port = port;
    }

    public void listen() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("SERVIDOR: Servidor escuchando en el puerto: " + port);

            while(true) { //esperar connexió del client i llançar thread
                clientSocket = serverSocket.accept();
                InetAddress clientAddress = clientSocket.getInetAddress();

                if (!clientAddress.isLoopbackAddress()){
                    System.out.println("SERVIDOR: Cliente conectado: " + clientAddress);
                }

            }
        } catch (IOException ex) {
            System.err.println("SERVIDOR: Error en el servidor: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        //int port = Integer.parseInt(args[0]);
        ServidorUnoTcp srv = new ServidorUnoTcp(559);
        srv.listen();

    }
}
