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
     * @return cont - total de visitas
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


    /**
     * Función de ordenación de fechas por su año. SHELL
     * @param v vector de objetos
     */
    public static void orderByYear_desc(Fecha v[]) { //esta función ordenará por el dorsal
        try{
            int d, i, cont = 0; //inicializo mi variable contador a 0
            Fecha ele;
            boolean ordenado;
            while (v[cont] != null) {
                cont++;
            }
            int num_ele = cont; // solo llegará hasta la posición que tenga inicializada sin llegar a los nulos (ya que no los puede ordenar)
            d = num_ele / 2; // distancia de comparación
                /* La ordenación se realiza mientras la distancia de comparación d
                sea menor igual que 1 */
            while (d >= 1) {
                ordenado = false;
                /* ordena el vector para la distancia d */
                while (!ordenado) { // !ordenado es como poner ordenado == false
                    ordenado = true;
                    for (i = 0; i < num_ele - d; i++)
                        // si el elemento i es mayor que i+d los intercambia
                        if (v[i].getYear() < (v[i + d].getYear())) {
                            ele = v[i];
                            v[i] = v[i + d];
                            v[i + d] = ele;
                            ordenado = false; // el vector no estaba ordenado
                        } /* fin if */
                } /* fin while !ordenado */
                /* calcula la nueva distancia de comparación d */
                d = d / 2;
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println(ANSI_RED+"Error al intentar ordenar los pacientes."+ANSI_RESET);
        }
    }


    @Override
    public void mostrarEstado() {
        //Como no se especifica nada voy a ordenar solo el año, aparecerá primero el año más reciente
        orderByYear_desc(visitasMedicas);
        System.out.println(dni+", "+nombre+" "+apellido1+" "+apellido2+"\n"+"última visita: "+fechaNac.toString());
    }



    /**
     * Añade una visita al array visitasMedicas si cumple las condiciones
     * @param visita - objeto de tipo Fecha
     * @return boolean - true si la fecha de visita se ha añadido correctamente al array, false si la fecha ya existe
     * */
    protected boolean addVisita(Fecha visita){
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
