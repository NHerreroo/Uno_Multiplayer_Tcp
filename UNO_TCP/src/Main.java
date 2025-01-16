import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) throws UnknownHostException {
        CLI cli = new CLI();
        boolean running = true;
        Scanner sc = new Scanner(System.in);
        InetAddress ip = InetAddress.getLocalHost();
        String playerName;

        cli.mostrarMenu();
        while (running){
            int opcion = sc.nextInt();

            switch (opcion){
                case 1:
                    System.out.println("Ingresa tu nombre: ");
                    sc.next(); //esto va aqui por q si no pilla lo siguiente
                    playerName = sc.nextLine();

                    System.out.println("---------------------------------------");
                    System.out.println("Iniciando servidor y cliente local...");
                    System.out.println(ANSI_GREEN + "Tu IP: " + ip.getHostAddress() + ANSI_RESET);
                    new Thread(() -> {
                        ServidorUnoTcp serv = new ServidorUnoTcp(559);
                        serv.listen();
                    }).start();

                    ClienteUnoTcp cliente = new ClienteUnoTcp("localhost", 559, playerName);
                    cliente.connect();
                    break;

                case 2:
                    System.out.println("Ingresa tu nombre: ");
                    playerName = sc.nextLine();
                    sc.next();

                    System.out.println("Ingresa la dirección IP del servidor: ");
                    sc.nextLine();
                    String hostname = sc.nextLine();

                    ClienteUnoTcp clienteRemoto = new ClienteUnoTcp(hostname, 559, playerName);
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