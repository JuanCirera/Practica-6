/**
 * Clase para definir la información genérica de un centro médico
 * */
public class Centro implements Estadistica{
    //CONSTANTES
    protected static final String ANSI_RESET = "\u001B[0m";
    protected static final String ANSI_GREEN = "\u001B[32m";
    protected static final String ANSI_BGREEN = "\u001B[32;1m";
    protected static final String ANSI_BLUE = "\u001B[34m";
    protected static final String ANSI_BBLUE = "\u001B[34;1m";
    protected static final String ANSI_RED = "\u001B[31m";
    protected static final String ANSI_YELLOW = "\u001B[33m";
    protected static final String ANSI_CYAN = "\u001B[36m";

    //ATRIBUTOS
    protected String nombreCentro;
    protected String direccionCentro;
    protected int identificador;
    protected Paciente consultas[];
    protected Persona trabajadores[];
    protected int limiteConsultas;
    protected int contMedicos;
    protected int contAdministrativos;
    protected static int contCentros;

    //CONSTRUCTOR
    public Centro(String nombreCentro, String direccionCentro, int identificador){
        this.nombreCentro=nombreCentro;
        this.direccionCentro=direccionCentro;
        this.identificador=identificador;
        contCentros++;
    }

    //SETTERS-GETTERS

    public int getID() {
        return identificador;
    }

    public String getNombre() {
        return nombreCentro;
    }


    //FUNCIONES-METODOS

    @Override
    public int diasPorMes(int month) {
        return 0;
    }

    @Override
    public void mostrarEstado() {

    }


}
