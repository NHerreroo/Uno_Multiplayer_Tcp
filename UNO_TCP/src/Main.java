import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) throws UnknownHostException {
        while (true) {
            mostrarMenu();
        }
    }

    public static void mostrarMenu() throws UnknownHostException {
        CLI cli = new CLI();
        Scanner sc = new Scanner(System.in);
        InetAddress ip = InetAddress.getLocalHost();
        String playerName;

        cli.mostrarMenu();
        int opcion = sc.nextInt();
        sc.nextLine(); // Consumir salto de línea

        switch (opcion) {
            case 1:
                System.out.println("Ingresa tu nombre: ");
                playerName = sc.nextLine();

                System.out.println("---------------------------------------");
                System.out.println("Iniciando servidor y cliente local...");
                System.out.println(ANSI_GREEN + "Tu IP: " + ip.getHostAddress() + ANSI_RESET);

                ServidorUnoTcp servidor = new ServidorUnoTcp(5559);
                new Thread(servidor::listen).start();

                try {
                    Thread.sleep(500); // Esperar a que el servidor inicie
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ClienteUnoTcp cliente = new ClienteUnoTcp("localhost", 5559, playerName);
                cliente.connect();
                break;

            case 2:
                System.out.println("Ingresa tu nombre: ");
                playerName = sc.nextLine();

                System.out.println("Ingresa la dirección IP del servidor: ");
                String hostname = sc.nextLine();

                ClienteUnoTcp clienteRemoto = new ClienteUnoTcp(hostname, 5559, playerName);
                clienteRemoto.connect();
                break;

            case 3:
                System.out.println("Saliendo del juego. ¡Hasta luego!");
                System.exit(0);
                break;

            default:
                System.out.println("Opción no válida. Inténtalo de nuevo.");
        }
    }
}