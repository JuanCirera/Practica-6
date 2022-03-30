import utilidades.PeticionDatos;
/**
 * @author Juan Fco Cirera
 * */
public class GestionMedica {

    //ATRIBUTOS CONSTANTES
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BGREEN = "\u001B[32;1m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_BBLUE = "\u001B[34;1m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";

    private static int opcionMenu;

    /**
     * Muestra el menu de inicio con las opciones del programa
     */
    private static void mostrarMenu() {

        System.out.println(" "); //Espacio en blanco
        System.out.println("╔══════════════════════════"+ANSI_BGREEN+" GESTIÓN MÉDICA ═╬═"+ANSI_RESET);
        opcionMenu = PeticionDatos.pedirEnteroPositivo(true, """ 
                ║ 1-> Gestionar hospital                                      
                ║ 2-> Gestionar clínica                                   
                ║ 3-> Gestionar personal                                      
                ║ 4-> Gestionar paciente                                      
                ║ 5-> Mostrar estadísticas de los centros médicos             
                ║ 6-> Mostrar estadísticas de las personas                    
                ║ 0-> Salir                                                   
                ╚ Elige una opción: """);
        System.out.println(" "); //Espacio en blanco

    }

    private static void mostrarSubMenuOpcion1(){}
//    private static void
    private static void mostrarSubMenuOpcion1_1(){}



    public static void main(String[] args) {
        //TODO: dejar el main con una única llamada, crear un constructor que llame a todas las funciones
        mostrarMenu();
    }
}
