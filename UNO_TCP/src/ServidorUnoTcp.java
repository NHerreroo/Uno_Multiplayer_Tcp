import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
            while(true) { //esperar connexió del client i llançar thread
                clientSocket = serverSocket.accept();
                System.out.println("Connexió acceptada: " + clientSocket.getInetAddress());
                //Llançar Thread per establir la comunicació
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorUnoTcp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        //int port = Integer.parseInt(args[0]);
        ServidorUnoTcp srv = new ServidorUnoTcp(5558);
        srv.listen();

    }
}
