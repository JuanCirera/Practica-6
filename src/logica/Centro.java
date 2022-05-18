package logica;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase para definir la información genérica de un centro médico
 * */
public class Centro implements Estadistica, Serializable {
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
    public Paciente consultas[];
    public Persona trabajadores[];
    protected int limiteConsultas;
    protected static int contMedicos=0;
    protected static int contAdministrativos=0;
    protected static int totalTrabajadores=contMedicos+contAdministrativos;
    protected static int contCentros=0;
    protected ArrayList<Medico> medicos=new ArrayList<>();
    protected ArrayList<Administrativo> admins=new ArrayList<>();

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

    public void setLimiteConsultas(int limiteConsultas) {
        this.limiteConsultas = limiteConsultas;
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

    public int getLimiteConsultas() {
        return limiteConsultas;
    }

    public static int getContCentros() {
        return contCentros;
    }

    //FUNCIONES-METODOS

    @Override
    public int diasPorMes(int month) {
        return 0;
    }

    @Override
    public String mostrarEstado() {
        return "";
    }


    @Override
    public String toString() {
        return  "ID " + identificador + '\n' +
                nombreCentro + '\n' +
                direccionCentro+"\n"+
                limiteConsultas+" consultas";
    }
}
