import utilidades.Fecha;

/**
 * Clase que gestiona la información de una persona tipo paciente, con información específica.
 * */
public class Paciente extends Persona{
    //ATRIBUTOS
    Fecha visitasMedicas[];

    //FUNCIONES-METODOS

    @Override
    /**
     * Devuelve el numero de visitas en un mes introducido
     * @param mes del que sacar el número de visitas
     * @reurn cont - total de visitas
     * */
    public int diasPorMes(int mes) {
        int cont=0;
        for (Fecha i:visitasMedicas){
            if(i.getMonth()==mes){
                cont++;
            }
        }
        return cont;
    }

    @Override
    public void mostrarEstado() {
        System.out.println(dni+", "+nombre+" "+apellido1+" "+apellido2);
        //TODO:Copiar la funcion de ordenar objetos, solo para el año y el mes. Dia??
    }

    private boolean addVisita(utilidades.Fecha visita){
        return false;
    }

}
