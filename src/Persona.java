/**
 * Clase con la información común que define a una persona
 * */
public class Persona implements Estadistica{
    //ATRIBUTOS
    protected String dni;
    protected String nombre;
    protected String apellido1;
    protected String apellido2;
    protected String genero;
    protected int identificador;
    protected utilidades.Fecha fechaNac;

    //FUNCIONES-METODOS

    @Override
    public int diasPorMes(int month) {
        return 0;
    }

    @Override
    public void mostrarEstado() {

    }
}
