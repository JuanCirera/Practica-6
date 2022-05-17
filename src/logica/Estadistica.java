package logica;

/**
 * Define aquellas funciones útiles para sacar estadística de la aplicación
 * */
public interface Estadistica {
    //FUNCIONES
    public int diasPorMes(int month);
    //He decidido modificar esta funcion para que en vez de hacer un print devuelva toda la cadena.
    public String mostrarEstado();
}
