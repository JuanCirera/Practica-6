/**
 * Clase para definir la información genérica de un centro médico
 * */
public class Centro implements Estadistica{
    //ATRIBUTOS
    protected String nombreCentro;
    protected String direccionCentro;
    protected int identificador;
    private Paciente consultas[];
    private Persona trabajadores[];
    protected int limiteConsultas;
    protected int contMedicos;
    protected int contAdministrativos;

    //FUNCIONES-METODOS

    @Override
    public int diasPorMes(int month) {
        return 0;
    }

    @Override
    public void mostrarEstado() {

    }
}
