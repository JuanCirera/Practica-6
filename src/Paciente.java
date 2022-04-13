import utilidades.Fecha;

/**
 * Clase que gestiona la información de una persona tipo paciente, con información específica.
 * */
public class Paciente extends Persona{
    //ATRIBUTOS
    Fecha visitasMedicas[];

    //CONSTRUCTOR
    //Inicializa el array con 5 posiciones
    public Paciente(String dni, int identificador, String nombre, String apellido1, String apellido2, String sexo, Fecha fechaNac){
        super(dni, identificador, nombre, apellido1, apellido2, sexo, fechaNac);
        visitasMedicas=new Fecha[5];
    }

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
            if(i!=null && i.getMonth()==mes){
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



    /**
     * Añade una visita al array visitasMedicas si cumple las condiciones
     * @param visita - objeto de tipo Fecha
     * @return boolean - true si la fecha de visita se ha añadido correctamente al array, false si la fecha ya existe
     * */
    private boolean addVisita(Fecha visita){
        //Solo se comprueba que el año sea mayor que el de nacimiento porque la comprobación de que no
        // pase la fecha actual ya se hace en la clase Fecha
        if(visita.getYear() > fechaNac.getYear()){
            int cont=0;
            for(Fecha i:visitasMedicas){
                if(i!=null){ //Si la posicion es null(no hay objeto fecha) se incrementa un contador
                    cont++;
                    visitasMedicas[cont-1]=visita; //Se resta uno para empezar desde la posicion 0
                    return true;
                }else if(i==visita){
                    return false;
                }
            }
        }
        return false;
    }

}
