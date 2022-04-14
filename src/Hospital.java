import utilidades.Faker;
import utilidades.Fecha;
import utilidades.PeticionDatos;

public class Hospital extends Centro{
    //ATRIBUTOS
    private int plantas;
    private int habitacionesPorPlanta;
    protected Paciente habitaciones[][]; //[planta][habitación]
    private int contHabitaciones=0;
    protected int totalTrabajadores=0;

    //CONSTRUCTOR
    public Hospital(String nombreCentro, String direccionCentro, int identificador, int plantas, int habitacionesPorPlanta){
        super(nombreCentro, direccionCentro, identificador);
        this.plantas=plantas;
        this.habitacionesPorPlanta=habitacionesPorPlanta;
        trabajadores=new Persona[5]; //Inicializo este array para poder meter el personal al inicio
        consultas=new Paciente[5]; //Lo mismo para este aunque no se van a crear pacientes al inicio
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
        for(Paciente p:consultas){
            cont++;
            if(p!=null){
                System.out.println("Consulta "+cont+": "+p.getDni()+", "+p.getNombre()+" "+p.getApellido1()+" "+p.getApellido2());
            }else{
                System.out.println(ANSI_BGREEN+cont+" Libre"+ANSI_RESET);
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
     * */
    protected void addPaciente(Paciente enf, int consulta){
        if(consultas[consulta]==null) {
            consultas[consulta] = enf; //El paciente solo se coloca si la consulta esta vacía
        }else {
            System.out.println(ANSI_RED+"Error. Esta consulta está ocupada."+ANSI_RESET);
        }
    }


    /**
     * Funcion para añadir un paciente al array consultas
     * @param enf - objeto paciente a añadir
     * @param planta - numero de planta
     * @param habitacion - numero de habitación donde meter al paciente
     * */
    protected void addPaciente(Paciente enf, int planta, int habitacion){
        if(habitaciones[planta][habitacion]==null){
            habitaciones[planta][habitacion]=enf;
        }else{
            System.out.println(ANSI_RED+"Error. Esta habitación está ocupada."+ANSI_RESET);
        }
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
     * Funcion para eliminar un trabajador
     * @param worker - objeto persona a eliminar
     * */
    public void removePersonal(Persona worker){
        //TODO: tener en cuenta si es medico o admin???
    }


    /**
     * Funcion que crea un nuevo objeto medico de forma manual o aleatoria
     * @param aleatorio - true, el objeto se crea automáticamente, false, se piden los datos por teclado.
     * @return void
     * */
    protected void addMedico(boolean aleatorio, Hospital h){
        try {
            if (aleatorio) {
                String dni;
                do {
                    dni = Faker.generarNIF_NIE();
                }while (GestionMedica.checkDNI(dni));
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
                contMedicos++;
                totalTrabajadores=contMedicos+contAdministrativos;
                if(totalTrabajadores>trabajadores.length){
                    if(aumentarTrabajadores(h)) {
                        trabajadores[totalTrabajadores - 1] = m;
                    }else {
                        System.out.println("-_-");
                    }
                }else{
                    trabajadores[totalTrabajadores - 1] = m;
                }

            } else {
                String sexo, especialidad;
                String dni = PeticionDatos.pedirNIF_NIE("> dni: ");
                int id;
                do{
                    id = PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
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
                trabajadores[(contMedicos+contAdministrativos)- 1] = m;
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println(ANSI_RED+"Error. Se ha llegado al máximo de trabajadores"+ANSI_RESET);
        }catch (NullPointerException e){
            System.out.println(ANSI_YELLOW+"Aviso. No existen trabajadores"+ANSI_RESET);
        }
    }


    /**
     * Funcion que crea un nuevo objeto administrativo de forma manual o aleatoria
     * @param aleatorio - true, el objeto se crea automáticamente, false, se piden los datos por teclado.
     * @return void
     * */
    protected void addAdmin(boolean aleatorio, Hospital h){
        try {
            if (aleatorio) {
                String dni;
                do {
                    dni = Faker.generarNIF_NIE();
                }while (GestionMedica.checkDNI(dni));
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
                contAdministrativos++;
                totalTrabajadores=contMedicos+contAdministrativos;
                if(totalTrabajadores>trabajadores.length){
                    if(aumentarTrabajadores(h)) {
                        trabajadores[totalTrabajadores - 1] = a;
                    }else {
                        System.out.println("-_-");
                    }
                }else{
                    trabajadores[totalTrabajadores - 1] = a;
                }

            } else {
                String sexo, area;
                String dni = PeticionDatos.pedirNIF_NIE("> dni: ");
                int id;
                do{
                    id = PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
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
                trabajadores[(contMedicos+contAdministrativos)- 1] = a;
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println(ANSI_RED+"Error. Se ha llegado al máximo de trabajadores"+ANSI_RESET);
        }catch (NullPointerException e){
            System.out.println(ANSI_YELLOW+"Aviso. No existen trabajadores"+ANSI_RESET);
        }
    }



    /**
     * Funcion que comprueba si el id de un trabajador existe ya
     * @param id - identificador del objeto Persona, medico o admin
     * @return boolean - true si lo encuentra, false si no coincide con ninguno
     * */
    private boolean checkWorkerID(int id){
        int contTrabajadores=contMedicos+contAdministrativos;
        if(contTrabajadores>=1) {
            for (int i = 0; i < contTrabajadores; i++) {
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


    private boolean aumentarTrabajadores(Centro h){
        //Se crea un nuevo array de tipo Cetro con el doble de la longitud del array centrosMedicos actual.
        Persona trabajadoresCopia[] = new Persona[h.trabajadores.length * 2];
        //Despues apuntamos el antiguo array al nuevo espacio en memoria.
        h.trabajadores = trabajadoresCopia;
        if (h.trabajadores.length == trabajadoresCopia.length) {
            return true;
        }
        return false;
    }

    //El array habitaciones y consultas no lo aumento porque no lo creo necesario, si un hospital tiene un número limitado de habitaciones cuando se llegue
    //al límite simplemente no se pueden meter más pacientes, lo mismo para las consultas.


    @Override
    public String toString() {
        return super.toString() + "\n" +
                plantas + " plantas" + "\n" +
                habitacionesPorPlanta + " habitaciones por planta" + "\n" +
                contHabitaciones + " ocupadas";
    }
}
