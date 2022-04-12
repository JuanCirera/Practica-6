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
        return super.diasPorMes(month);
    }


    @Override
    public void mostrarEstado() {
        for(Paciente p:consultas){
            if(p!=null){
                System.out.println(p.getDni()+", "+p.getNombre()+" "+p.getApellido1()+" "+p.getApellido2());
            }else{
                System.out.println(ANSI_BGREEN+"Libre"+ANSI_RESET);
            }
        }
    }

    private static void addPaciente(Paciente enf, int consulta){}
    private static void removePaciente(Paciente enf){}


    /**
     * Funcion que crea un nuevo objeto medico de forma manual o aleatoria
     * @param aleatorio - true, el objeto se crea automáticamente, false, se piden los datos por teclado.
     * @return void
     * */
    protected void addMedico(boolean aleatorio, Clinica cl){
        try {
            if (aleatorio) {
                String dni;
                do {
                    dni = Faker.generarNIF_NIE();
                }while (GestionMedica.checkDNI(dni));
                int id;
                do {
                    id = Faker.devolverEnteros(false, trabajadores.length);
                }while(checkWorkerID(id, cl));
                String n = Faker.devolverNombre();
                String ap1 = Faker.devolverApellidos();
                String ap2 = Faker.devolverApellidos();
                String sexo = Faker.devolverSexo(); //Aqui no necesito controlar nada porque esta funcion solo devuelve dos posibles cadenas
                Fecha fNac = new Fecha(); // Si se crean aleatorios he decidido que tengan la fecha por defecto, 0-0-0.
                String especialidad = Faker.devolverEspecialidad();

                Medico m = new Medico(dni, id, n, ap1, ap2, sexo, fNac, especialidad);
                cl.contMedicos++;
                totalTrabajadores=cl.contMedicos+cl.contAdministrativos;
                if(totalTrabajadores>cl.trabajadores.length){
                    if(aumentarTrabajadores(cl)) {
                        cl.trabajadores[totalTrabajadores - 1] = m;
                    }else {
                        System.out.println("-_-");
                    }
                }else{
                    cl.trabajadores[totalTrabajadores - 1] = m;
                }

            } else {
                String sexo, especialidad;
                String dni = PeticionDatos.pedirNIF_NIE("> dni: ");
                int id;
                do{
                    id = PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
                }while(checkWorkerID(id, cl));
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
    protected void addAdmin(boolean aleatorio, Clinica cl){
        try {
            if (aleatorio) {
                String dni;
                do {
                    dni = Faker.generarNIF_NIE();
                }while (GestionMedica.checkDNI(dni));
                int id;
                do {
                    id = Faker.devolverEnteros(false, trabajadores.length);
                }while(checkWorkerID(id, cl));
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
                    }else {
                        System.out.println("-_-");
                    }
                }else{
                    cl.trabajadores[totalTrabajadores - 1] = a;
                }

            } else {
                String sexo, area;
                String dni = PeticionDatos.pedirNIF_NIE("> dni: ");
                int id;
                do{
                    id = PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
                }while(checkWorkerID(id, cl));
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
    protected boolean checkWorkerID(int id, Clinica cl){
        if(totalTrabajadores>=1) {
            for (int i = 0; i < totalTrabajadores; i++) {
                if (cl.trabajadores[i] != null) {
                    if (cl.trabajadores[i].getID() == id) {
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
