public class CLI {

    //Colores
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";



    public void mostrarMenu(){
        System.out.print(ANSI_RED + "U");
        System.out.print(ANSI_YELLOW + "N");
        System.out.print(ANSI_BLUE + "O" + ANSI_RESET);

        System.out.println();

        System.out.println("1. CREATE A GAME");
        System.out.println("2. JOIN A GAME");
        System.out.println("3. QUIT");
    }
}
