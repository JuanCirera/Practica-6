package logica;

import utilidades.Fecha;

/**
 * Clase que crea objetos logica.Clinica y se encarga de añadirlos a los arrays correspondientes
 * */
public class Clinica extends Centro{
    //ATRIBUTOS
    private int contConsultas=0;
    protected int totalTrabajadores=0;

    //CONSTRUCTOR
    public Clinica(String nombreCentro, String direccionCentro, int identificador, int consultas){
        super(nombreCentro, direccionCentro, identificador, consultas);
        trabajadores=new Persona[5]; //Fun fact: Se me olvidó iniciar estos dos arrays y me volví loco porque esta clase no creaba personas y la de logica.Hospital si...
        this.consultas=new Paciente[consultas];
    }

    //FUNCIONES_METODOS
    @Override
    public int diasPorMes(int month) {
        int cont=0;
        //Consulta
        for(Paciente p: consultas){
            if(p!=null) {
                for (Fecha f : p.visitasMedicas) {
                    //Si el mes del objeto guardado coincide con el mes pasado por parámetro
                    if (f != null && f.getMonth() == month) {
                        cont++; //Se incrementa en 1 el total
                    }
                }
            }
        }
        return cont;
    }


    @Override
    public void mostrarEstado() {
        int cont=0;
        for(Paciente p: consultas){
            cont++;
            if(p!=null){
                System.out.println("Consulta "+cont+": "+p.getDni()+", "+p.getNombre()+" "+
                        p.getApellido1()+" "+p.getApellido2());
            }else{
                System.out.println(ANSI_BGREEN+cont+" Libre"+ANSI_RESET);
            }
        }
    }


    /**
     * Funcion para añadir un paciente al array consultas
     * @param enf - objeto paciente a añadir
     * @param consulta - numero de consulta donde meterlo
     * */
    protected boolean addPaciente(Paciente enf, int consulta){
        if(consultas[consulta-1]==null) {
            consultas[consulta-1] = enf; //El paciente solo se coloca si la consulta esta vacía
            return true;
        }else {
            System.out.println(ANSI_RED+"Error. Esta consulta está ocupada."+ANSI_RESET);
        }
        return false;
    }


    /**
     * Funcion para eliminar un paciente, en este caso solo de una consulta
     * @param enf - objeto paciente a eliminar
     * @return boolean - true si la posicion se ha podido "vaciar", false si no.
     * */
    protected boolean removePaciente(Paciente enf){
        for (int i = 0; i < consultas.length; i++) {
            if (consultas[i] != null && consultas[i] == enf) {
                consultas[i] = null;
                return true;
            }
        }
        return false;
    }


    /**
     * Funcion para eliminar un trabajador, no importa si es medico o admin
     * @param worker - objeto persona a eliminar
     * */
    protected boolean removePersonal(Persona worker){
        for (int i = 0; i < trabajadores.length; i++) {
            if (trabajadores[i] != null && trabajadores[i] == worker) {
                trabajadores[i] = null;
                return true;
            }
        }
        return false;
    }


    /**
     * Funcion que crea un nuevo objeto medico de forma manual o aleatoria
     * @return boolean
     * */
    protected boolean addMedico(Medico m){
        try {
            totalTrabajadores++;
            //GUARDAR
            if(totalTrabajadores>trabajadores.length){
                if(aumentarTrabajadores()) {
                    trabajadores[totalTrabajadores - 1] = m;
                    return true;
                }else {
                    System.out.println(ANSI_RED+"Error Crítico. El array trabajadores de este centro no se puede redimensionar."+ANSI_RESET);
                }
            }else{
                trabajadores[totalTrabajadores - 1] = m;
                return true;
            }

        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println(ANSI_RED+"Error. Se ha llegado al máximo de trabajadores"+ANSI_RESET);
        }catch (NullPointerException e){
            System.out.println(ANSI_YELLOW+"Aviso. No existen trabajadores"+ANSI_RESET);
        }
        return false;
    }


    /**
     * Funcion que crea un nuevo objeto administrativo de forma manual o aleatoria
     * @return boolean
     * */
    protected boolean addAdmin(Administrativo a){
        try {
            totalTrabajadores++;
            if(totalTrabajadores>trabajadores.length){
                if(aumentarTrabajadores()) {
                    trabajadores[totalTrabajadores - 1] = a;
                    return true;
                }else {
                    System.out.println(ANSI_RED+"Error Crítico. El array trabajadores de este centro no se puede redimensionar."+ANSI_RESET);
                }
            }else{
                trabajadores[totalTrabajadores - 1] = a;
                return true;
            }

        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println(ANSI_RED+"Error. Se ha llegado al máximo de trabajadores"+ANSI_RESET);
        }catch (NullPointerException e){
            System.out.println(ANSI_YELLOW+"Aviso. No existen trabajadores"+ANSI_RESET);
        }
        return false;
    }


    /**
     * Funcion que comprueba si el id de un trabajador existe ya
     * @param id - identificador del objeto logica.Persona, medico o admin
     * @return boolean - true si lo encuentra, false si no coincide con ninguno
     * */
    protected boolean checkWorkerID(int id){
        if(totalTrabajadores>=1) {
            for (int i = 0; i < totalTrabajadores; i++) {
                if (trabajadores[i] != null) {
                    if (trabajadores[i].getID() == id) {
                        return true;
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }


    private boolean aumentarTrabajadores(){
        //Se crea un nuevo array de tipo Cetro con el doble de la longitud del array centrosMedicos actual.
        Persona trabajadoresCopia[] = new Persona[trabajadores.length * 2];
        //Despues apuntamos el antiguo array al nuevo espacio en memoria.
        trabajadores = trabajadoresCopia;
        if (trabajadores.length == trabajadoresCopia.length) {
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        return super.toString() + "\n" +
                contConsultas+ " ocupadas"+"\n"+
                totalTrabajadores + " trabajadores";
    }
}
