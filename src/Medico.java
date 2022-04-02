import utilidades.Fecha;

import java.util.Calendar;

/**
 * Clase que gestiona la información de una persona tipo médico, con información específica.
 * */
public class Medico extends Persona{
    //ATRIBUTOS
    String especialidad;
    Fecha  diasTrabajados[];

    //FUNCIONES-METODOS

    @Override
    /**
     * Devuelve el número de dias trabajados en un mes introducido
     * @param mes del que sacar el número de dias
     * @reurn cont - total de trabajados
     * */
    public int diasPorMes(int mes) {
        int cont=0;
        for (Fecha i:diasTrabajados){
            if(i.getMonth()==mes){
                cont++;
            }
        }
        return cont;
    }

    @Override
    public void mostrarEstado() {
        System.out.println(dni+", "+nombre+" "+apellido1+" "+apellido2);
    }

    private boolean addDiaTrabajado(Fecha dt){
        return false;
    }

    /**
     * Comprueba si la especialidad introducida por parametro es alguna de las permitidas
     * @param especialidad
     * @return boolean - true si coincide con alguna, false si no
     * */
    private boolean validarEspecialidad(String especialidad){
        if(especialidad.equals("Traumatología") || especialidad.equals("Dermatología") || especialidad.equals("Oftalmología") || especialidad.equals("Neurología"))
            return true;
        else {
            return false;
        }
    }

    @Override
    public boolean validarFechaNac(Fecha fechaNac) {
        Calendar calendar = Calendar.getInstance();
        int actual=calendar.get(Calendar.YEAR);
        //Se llama al atributo año del objeto fecha que se haya creado y se hace la comprobacion
        if(actual - fechaNac.getYear()>22 && actual - fechaNac.getYear()<70){
            return true;
        }else {
            return false;
        }
    }
}
