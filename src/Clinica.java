import utilidades.Faker;
import utilidades.Fecha;
import utilidades.PeticionDatos;

public class Clinica extends Centro{

    protected int totalTrabajadores=0;


    //CONSTRUCTOR
    public Clinica(String nombreCentro, String direccionCentro, int identificador){
        super(nombreCentro, direccionCentro, identificador);
        trabajadores=new Persona[5]; //Fun fact: Se me olvidó iniciar estos dos arrays y me volví loco porque esta clase no creaba personas y la de Hospital si...
        consultas=new Paciente[5];
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
        for(Paciente p:consultas){
            cont++;
            if(p!=null){
                System.out.println("Consulta "+cont+": "+p.getDni()+", "+p.getNombre()+" "+p.getApellido1()+" "+p.getApellido2());
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
        if(consultas[consulta]==null) {
            consultas[consulta] = enf; //El paciente solo se coloca si la consulta esta vacía
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
     * @param aleatorio - true, el objeto se crea automáticamente, false, se piden los datos por teclado.
     * @return void
     * */
    protected boolean addMedico(boolean aleatorio, Clinica cl, String dni){
        try {
            if (aleatorio) {
                //PETICIÓN DE DATOS
                int id;
                do {
                    id = Faker.devolverEnteros(false, trabajadores.length);
                }while(checkWorkerID(id));
                String n = Faker.devolverNombre();
                String ap1 = Faker.devolverApellidos();
                String ap2 = Faker.devolverApellidos();
                String sexo = Faker.devolverSexo(); //Aqui no necesito controlar nada porque esta funcion solo devuelve dos posibles cadenas
                Fecha fNac = new Fecha(); // Si se crean aleatorios he decidido que tengan la fecha por defecto, 0-0-0.
                String especialidad = Faker.devolverEspecialidad();

                Medico m = new Medico(dni, id, n, ap1, ap2, sexo, fNac, especialidad);
                cl.contMedicos++;
                totalTrabajadores=cl.contMedicos+cl.contAdministrativos;
                //GUARDAR
                if(totalTrabajadores>cl.trabajadores.length){
                    if(aumentarTrabajadores(cl)) {
                        cl.trabajadores[totalTrabajadores - 1] = m;
                        return true;
                    }else {
                        System.out.println(ANSI_RED+"Error Crítico. El array trabajadores de este centro no se puede redimensionar."+ANSI_RESET);
                    }
                }else{
                    cl.trabajadores[totalTrabajadores - 1] = m;
                    return true;
                }

            } else {
                //PETICIÓN DE DATOS
                String sexo, especialidad;
                int id;
                do{
                    id = PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
                    if(checkWorkerID(id)){
                        System.out.println(ANSI_RED + "Aviso. El id introducido ya está registrado." + ANSI_RESET);
                    }
                }while(checkWorkerID(id));
                String n = PeticionDatos.pedirCadena("> nombre: ");
                String ap1 = PeticionDatos.pedirCadena("> primer apellido: ");
                String ap2 = PeticionDatos.pedirCadena("> segundo apellido: ");
                //Aqui si lo controlo porque se introduce manualmente
                do {
                    sexo = PeticionDatos.pedirCadena("> sexo: ");
                    if (!Persona.validarGenero(sexo)) {
                        System.out.println(ANSI_RED + "Error. El dato introducido no es correcto." + ANSI_RESET);
                    }
                } while (!Persona.validarGenero(sexo));
                Fecha fNac = new Fecha(PeticionDatos.pedirEnteroPositivo(true, "> dia:"), //En manual si que pide los datos de la fecha
                        PeticionDatos.pedirEnteroPositivo(true, "> mes:"),
                        PeticionDatos.pedirEnteroPositivo(true, "> año:"));
                do {
                    especialidad = PeticionDatos.pedirCadena("> especialidad: ");
                    if (!Medico.validarEspecialidad(especialidad)) {
                        System.out.println(ANSI_RED + "Error. El dato introducido no es correcto." + ANSI_RESET);
                    }
                } while (!Medico.validarEspecialidad(especialidad));

                Medico m = new Medico(dni, id, n, ap1, ap2, sexo, fNac, especialidad);
                contMedicos++;
                totalTrabajadores=cl.contMedicos+cl.contAdministrativos;
                //GUARDAR
                if(totalTrabajadores>cl.trabajadores.length){
                    if(aumentarTrabajadores(cl)) {
                        cl.trabajadores[totalTrabajadores - 1] = m;
                        return true;
                    }else {
                        System.out.println(ANSI_RED+"Error Crítico. El array trabajadores de este centro no se puede redimensionar."+ANSI_RESET);
                    }
                }else{
                    cl.trabajadores[totalTrabajadores - 1] = m;
                    return true;
                }
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
     * @param aleatorio - true, el objeto se crea automáticamente, false, se piden los datos por teclado.
     * @return void
     * */
    protected boolean addAdmin(boolean aleatorio, Clinica cl, String dni){
        try {
            if (aleatorio) {
                //PETICIÓN DE DATOS
                int id;
                do {
                    id = Faker.devolverEnteros(false, trabajadores.length);
                }while(checkWorkerID(id));
                String n = Faker.devolverNombre();
                String ap1 = Faker.devolverApellidos();
                String ap2 = Faker.devolverApellidos();
                String sexo = Faker.devolverSexo();
                Fecha fNac = new Fecha(); // Si se crean aleatorios he decidido que tengan la fecha por defecto, 0-0-0.
                String area = Faker.devolverArea();

                Administrativo a = new Administrativo(dni, id, n, ap1, ap2, sexo, fNac, area);
                cl.contAdministrativos++;
                totalTrabajadores=cl.contMedicos+cl.contAdministrativos;
                if(totalTrabajadores>cl.trabajadores.length){
                    if(aumentarTrabajadores(cl)) {
                        cl.trabajadores[totalTrabajadores - 1] = a;
                        return true;
                    }else {
                        System.out.println(ANSI_RED+"Error Crítico. El array trabajadores de este centro no se puede redimensionar."+ANSI_RESET);
                    }
                }else{
                    cl.trabajadores[totalTrabajadores - 1] = a;
                    return true;
                }

            } else {
                //PETICIÓN DE DATOS
                String sexo, area;
                int id;
                do{
                    id = PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
                    if(checkWorkerID(id)){
                        System.out.println(ANSI_RED + "Aviso. El id introducido ya está registrado." + ANSI_RESET);
                    }
                }while(checkWorkerID(id));
                String n = PeticionDatos.pedirCadena("> nombre: ");
                String ap1 = PeticionDatos.pedirCadena("> primer apellido: ");
                String ap2 = PeticionDatos.pedirCadena("> segundo apellido: ");
                do {
                    sexo = PeticionDatos.pedirCadena("> sexo: ");
                    if (!Persona.validarGenero(sexo)) {
                        System.out.println(ANSI_RED + "Error. El dato introducido no es correcto." + ANSI_RESET);
                    }
                } while (!Persona.validarGenero(sexo));
                Fecha fNac = new Fecha(PeticionDatos.pedirEnteroPositivo(true, "> dia:"), //En manual si que pide los datos de la fecha
                        PeticionDatos.pedirEnteroPositivo(true, "> mes:"),
                        PeticionDatos.pedirEnteroPositivo(true, "> año:"));
                do {
                    area = PeticionDatos.pedirCadena("> área: ");
                    if (!Persona.validarGenero(sexo)) {
                        System.out.println(ANSI_RED + "Error. El dato introducido no es correcto." + ANSI_RESET);
                    }
                } while (!Administrativo.validarArea(area));

                Administrativo a = new Administrativo(dni, id, n, ap1, ap2, sexo, fNac, area);
                contAdministrativos++;
                totalTrabajadores=cl.contMedicos+cl.contAdministrativos;
                //GUARDAR
                if(totalTrabajadores>cl.trabajadores.length){
                    if(aumentarTrabajadores(cl)) {
                        cl.trabajadores[totalTrabajadores - 1] = a;
                        return true;
                    }else {
                        System.out.println(ANSI_RED+"Error Crítico. El array trabajadores de este centro no se puede redimensionar."+ANSI_RESET);
                    }
                }else{
                    cl.trabajadores[totalTrabajadores - 1] = a;
                    return true;
                }
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


    private boolean aumentarTrabajadores(Clinica cl){
        //Se crea un nuevo array de tipo Cetro con el doble de la longitud del array centrosMedicos actual.
        Persona trabajadoresCopia[] = new Persona[cl.trabajadores.length * 2];
        //Despues apuntamos el antiguo array al nuevo espacio en memoria.
        cl.trabajadores = trabajadoresCopia;
        if (cl.trabajadores.length == trabajadoresCopia.length) {
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
