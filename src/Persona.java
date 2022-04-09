import utilidades.Fecha;

import java.util.Calendar;

/**
 * Clase con la información común que define a una persona
 * */
public class Persona implements Estadistica{
    //ATRIBUTOS
    protected String dni;
    protected int identificador;
    protected String nombre;
    protected String apellido1;
    protected String apellido2;
    protected String sexo;
    protected Fecha fechaNac;

    //CONSTRUCTOR
    public Persona(String dni, int id, String nombre, String ap1, String ap2, String sexo, Fecha f){
        this.dni=dni;
        this.identificador=id;
        this.nombre=nombre;
        this.apellido1=ap1;
        this.apellido2=ap2;
        this.sexo=sexo;
        this.fechaNac=f;
    }

    //FUNCIONES-METODOS

    @Override
    public int diasPorMes(int month) {
        return 0;
    }

    @Override
    public void mostrarEstado() {

    }

    /**
     * Comprueba que la fecha sea correcta, validando el año de nacimiento
     * @param fechaNac - objeto fecha que se necesita comprobar
     * @return boolean - true si el año es correcto, false si no cumple la condicion (120 años)
     * */
    protected boolean validarFechaNac(Fecha fechaNac){
        //Estas dos líneas son para obtener el año actual
        Calendar calendar = Calendar.getInstance();
        int actual=calendar.get(Calendar.YEAR);
        //Se llama al atributo año del objeto fecha que se haya creado y se hace la comprobacion
        if(actual - fechaNac.getYear()>=1 && actual - fechaNac.getYear()<=120){
            return true;
        }else {
            return false;
        }
    }


    /**
     * Funcion de clase que comprueba si el sexo de una persona es válido.
     * @param sexo introducido
     * @return boolean - Devuelve true si coincide con hombre/mujer y false si no coincide con ninguno.
     * */
    protected static boolean validarGenero(String sexo){
        if (sexo.equals("hombre") || sexo.equals("mujer")){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public String toString() {
        return "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellido1 + ", " + apellido2 + '\'';
    }
}
