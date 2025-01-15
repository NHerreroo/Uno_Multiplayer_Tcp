public class CLI {

    //Colores
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    public void mostrarMenu(){
        // Arte ASCII de UNO con colores
        System.out.println(ANSI_RED + " ██      ██" + ANSI_YELLOW + " ███     ██" + ANSI_BLUE + "   ██████  " +  ANSI_GREEN + " " + " ██ " + ANSI_RESET);
        System.out.println(ANSI_RED + " ██      ██" + ANSI_YELLOW + " ████    ██" + ANSI_BLUE + " ██      ██ " + ANSI_GREEN + " ██ " +       ANSI_RESET);
        System.out.println(ANSI_RED + " ██      ██" + ANSI_YELLOW + " ██ ██   ██" + ANSI_BLUE + " ██      ██ " + ANSI_GREEN + " ██ " +       ANSI_RESET);
        System.out.println(ANSI_RED + " ██      ██" + ANSI_YELLOW + " ██   ██ ██" + ANSI_BLUE + " ██      ██ " + ANSI_GREEN + "   "        + ANSI_RESET);
        System.out.println(ANSI_RED + "  ████████ " + ANSI_YELLOW + " ██     ███" + ANSI_BLUE + "   ██████  " +  ANSI_GREEN +  " "+ " ██"  + ANSI_RESET);

        System.out.println("El juego de cartas para toda la familia.");
        System.out.println();

        System.out.println("SELECCIONA UNA OPCION:");
        System.out.println("1. CREAR PARTIDA (Iniciar servidor)");
        System.out.println("2. UNIRSE A UNA PARTIDA");
        System.out.println("3. SALIR");
    }
}
