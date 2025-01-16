import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
        CLI cli = new CLI();
        boolean running = true;
        Scanner sc = new Scanner(System.in);

        InetAddress ip = InetAddress.getLocalHost();

        cli.mostrarMenu();
        while (running){
            int opcion = sc.nextInt();

            switch (opcion){
                case 1:
                    System.out.println("Iniciando servidor y cliente local...");
                    System.out.println("Tu IP: " + ip.getHostAddress());
                    new Thread(() -> {
                        ServidorUnoTcp serv = new ServidorUnoTcp(5558);
                        serv.listen();
                    }).start();
                    ClienteUnoTcp cliente = new ClienteUnoTcp("localhost", 5558);
                    cliente.connect();
                    break;
                case 2:
                    System.out.println("Ingresa la dirección IP del servidor: ");
                    sc.nextLine();
                    String hostname = sc.nextLine();
                    ClienteUnoTcp clienteRemoto = new ClienteUnoTcp(hostname, 5558);
                    clienteRemoto.connect();
                    break;
                case 3:
                    System.out.println("Saliendo del juego. ¡Hasta luego!");
                    running = false;
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
        sc.close();
    }
}