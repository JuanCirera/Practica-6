import java.util.Arrays;

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
    protected static int contMedicos=0;
    protected static int contAdministrativos=0;
    protected static int totalTrabajadores=contMedicos+contAdministrativos;
    protected static int contCentros=0;


    //CONSTRUCTOR
    public Centro(String nombreCentro, String direccionCentro, int identificador, int consultas){
        this.nombreCentro=nombreCentro;
        this.direccionCentro=direccionCentro;
        this.identificador=identificador;
        this.limiteConsultas=consultas;
        contCentros++;
    }

    //SETTERS

    protected void setID(int identificador) {
        this.identificador = identificador;
    }

    public void setNombreCentro(String nombreCentro) {
        this.nombreCentro = nombreCentro;
    }

    public void setDireccion(String direccionCentro) {
        this.direccionCentro = direccionCentro;
    }

    //GETTERS
    public int getID() {
        return identificador;
    }

    public String getNombre() {
        return nombreCentro;
    }

    public String getDireccionCentro() {
        return direccionCentro;
    }


    //FUNCIONES-METODOS

    @Override
    public int diasPorMes(int month) {
        return 0;
    }

    @Override
    public void mostrarEstado() {

    }


    @Override
    public String toString() {
        return  ANSI_BGREEN+"ID " + identificador +ANSI_RESET+ '\n' +
                nombreCentro + '\n' +
                direccionCentro+"\n"+
                limiteConsultas+" consultas";
    }
}
