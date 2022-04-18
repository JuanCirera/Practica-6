import utilidades.Faker;
import utilidades.Fecha;
import utilidades.PeticionDatos;

public class Hospital extends Centro{
    //ATRIBUTOS
    private int plantas;
    private int habitacionesPorPlanta;
    protected Paciente habitaciones[][]; //[planta][habitación]
    private int contHabitaciones=0;
    private int contConsultas=0;
    protected int totalTrabajadores=0;

    //CONSTRUCTOR
    public Hospital(String nombreCentro, String direccionCentro, int identificador, int consultas, int plantas, int habitacionesPorPlanta){
        super(nombreCentro, direccionCentro, identificador, consultas);
        this.plantas=plantas;
        this.habitacionesPorPlanta=habitacionesPorPlanta;
        trabajadores=new Persona[5]; //Inicializo este array para poder meter el personal al inicio
        this.consultas=new Paciente[consultas]; //Lo mismo para este aunque no se van a crear pacientes al inicio
        habitaciones=new Paciente[plantas][habitacionesPorPlanta]; //El limite de ambas dimensiones será lo que haya almacenado en cada uno de los dos atributos
    }


    //FUNCIONES-METODOS

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
        //Habitacion
        //Aqui he tenido que usar un for normal para el bidimensional porque con el for each me estaba rayando, no sabia como llamar a visitasMedicas
        for(int i=0;i<habitaciones.length;i++){
            for(int x=0;x<habitaciones[i].length;x++){
                if(habitaciones[i][x]!=null){
                    for(Fecha f: habitaciones[i][x].visitasMedicas){
                        if (f != null && f.getMonth() == month) {
                            cont++;
                        }
                    }
                }
            }
        }
        //Despues de sumar todas las coincidencias(si existen) se devuelve el total.
        return cont;
    }


    @Override
    public void mostrarEstado() {
        System.out.println(ANSI_BBLUE+"CONSULTAS"+ANSI_RESET);
        int cont=0;
        for(int y=0;y<consultas.length;y++){
//            cont++;
            if(consultas[y]!=null){
                System.out.println("Consulta "+y+": "+consultas[y].getDni()+", "+consultas[y].getNombre()+" "+
                                    consultas[y].getApellido1()+" "+consultas[y].getApellido2());
            }else{
                System.out.println(ANSI_BGREEN+y+" Libre"+ANSI_RESET);
            }
        }
        System.out.println(" ");
        System.out.println(ANSI_BBLUE+"HABITACIONES"+ANSI_RESET);
        for(int i=0;i<habitaciones.length;i++){
            for(int x=0;x<habitaciones[i].length;x++){
                if(habitaciones[i][x]!=null){
                    System.out.println("Planta "+i+", habitación "+x+": "+"hay uno metio");
                }
            }
        }
    }


    /**
     * Funcion para añadir un paciente al array consultas
     * @param enf - objeto paciente a añadir
     * @param consulta - numero de consulta donde meterlo
     * @return boolean - true si el paciente se ha guardado con éxito, false si no.
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
     * Funcion para añadir un paciente al array consultas
     * @param enf - objeto paciente a añadir
     * @param planta - numero de planta
     * @param habitacion - numero de habitación donde meter al paciente
     * @return boolean - true si el paciente se ha guardado con éxito, false si no.
     * */
    protected boolean addPaciente(Paciente enf, int planta, int habitacion){
        if(habitaciones[planta][habitacion]==null){
            habitaciones[planta][habitacion]=enf;
            return true;
        }else{
            System.out.println(ANSI_RED+"Error. Esta habitación está ocupada."+ANSI_RESET);
        }
        return false;
    }


    /**
     * Funcion para eliminar un paciente
     * @param enf - objeto paciente a eliminar
     * @return boolean - true si lo consigue eliminar, false si no encuentra el objeto en ningun array
     * */
    protected boolean removePaciente(Paciente enf) {
        for (int i = 0; i < consultas.length; i++) {
            if (consultas[i] != null && consultas[i] == enf) {
                consultas[i] = null;
                return true;
            }
        }
        for (int i = 0; i < habitaciones.length; i++) {
            for (int x = 0; x < habitaciones[i].length; x++) {
                if (habitaciones[i][x] != null && habitaciones[i][x] == enf) {
                    habitaciones[i][x] = null;
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Funcion para eliminar un trabajador, no importa si es medico o admin.
     * @param worker - objeto persona a eliminar.
     * @return boolean - true si la posicion se ha podido "vaciar", false si no.
     * */
    protected boolean removePersonal(Persona worker){
        for (int i = 0; i < trabajadores.length; i++) {
            if (trabajadores[i] != null && trabajadores[i] == worker) {
                trabajadores[i] = null;
                return true;
            }
        }
        //TODO: Pero y si la persona tamb es un paciente?
        return false;
    }


    /**
     * Funcion que crea un nuevo objeto medico de forma manual o aleatoria
     * @return boolean - true si se ha creado u guardado correctamente el objeto, false si no.
     * */
    protected boolean addMedico(Medico m){
        try {
            totalTrabajadores++;
            //** GUARDAR **
            //Cuando el array trabajadores se llene se llamara a la funcion aumentarTrabajadores() que le duplicará los espacios.
            if(totalTrabajadores>trabajadores.length){
                if(aumentarTrabajadores()) {
                    trabajadores[totalTrabajadores - 1] = m;
                    return true;
                }else {
                    System.out.println(ANSI_RED+"Error Crítico. El array trabajadores de este centro no se puede redimensionar."+ANSI_RESET);
                }
            }else{//Si al array aun le queda sitio
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
     * @return boolean - true si se ha creado u guardado correctamente el objeto, false si no.
     * */
    protected boolean addAdmin(Administrativo a){
        try {
            totalTrabajadores++;
            //** GUARDAR **
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
     * @param id - identificador del objeto Persona, medico o admin
     * @return boolean - true si lo encuentra, false si no coincide con ninguno
     * */
    private boolean checkWorkerID(int id){
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

    //El array habitaciones y consultas no lo aumento porque no lo creo necesario, si un hospital tiene un número limitado de habitaciones cuando se llegue
    //al límite simplemente no se pueden meter más pacientes, lo mismo para las consultas.


    @Override
    public String toString() {
        return super.toString() + "\n" +
                contConsultas+ " ocupadas"+"\n"+
                totalTrabajadores + " trabajadores"+"\n"+
                plantas + " plantas" + "\n" +
                habitacionesPorPlanta + " habitaciones por planta" + "\n" +
                contHabitaciones + " ocupadas";
    }
}
