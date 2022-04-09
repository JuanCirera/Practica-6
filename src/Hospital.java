import utilidades.Faker;
import utilidades.Fecha;
import utilidades.PeticionDatos;

public class Hospital extends Centro{
    //ATRIBUTOS
    private int plantas;
    private int habitacionesPorPlanta;
    private Paciente habitaciones[];

    //CONSTRUCTOR
    public Hospital(String nombreCentro, String direccionCentro, int identificador, int plantas, int habitacionesPorPlanta){
        super(nombreCentro, direccionCentro, identificador);
        this.plantas=plantas;
        this.habitacionesPorPlanta=habitacionesPorPlanta;
    }

    //FUNCIONES-METODOS

    @Override
    public int diasPorMes(int month) {
        return super.diasPorMes(month);
    }

    @Override
    public void mostrarEstado() {
        super.mostrarEstado();
    }

    private static void addPaciente(Paciente enf, int consulta){}
    private static void addPaciente(Paciente enf, int planta, int habitacion){}
    private static void removePaciente(Paciente enf){}


    /**
     * Funcion que crea un nuevo objeto medico de forma manual o aleatoria
     * @param aleatorio - true, el objeto se crea automáticamente, false, se piden los datos por teclado.
     * @return Medico
     * */
    public Medico addMedico(boolean aleatorio){
        if(aleatorio){
            String dni = Faker.generarNIF_NIE();
            int id = Faker.devolverEnteros(false, trabajadores.length);
            String n = Faker.devolverNombre();
            String ap1 = Faker.devolverApellidos();
            String ap2 = Faker.devolverApellidos();
            String sexo = Faker.devolverSexo();
            Fecha fNac = new Fecha(); // Si se crean aleatorios he decidido que tengan la fecha por defecto, todo a 0.
            String especialidad=Faker.devolverEspecialidad();

            Medico m=new Medico(dni, id, n, ap1, ap2, sexo, fNac, especialidad);
            return m;

        }else{
            String sexo, especialidad;
            String dni = PeticionDatos.pedirNIF_NIE("> dni: ");
            int id = PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
            String n = PeticionDatos.pedirCadena("> nombre: ");
            String ap1 = PeticionDatos.pedirCadena("> primer apellido: ");
            String ap2 = PeticionDatos.pedirCadena("> segundo apellido: ");
            do {
                sexo = PeticionDatos.pedirCadena("> sexo: ");
                if(!Persona.validarGenero(sexo)){
                    System.out.println(ANSI_RED+"Error. El dato introducido no es correcto."+ANSI_RESET);
                }
            }while(!Persona.validarGenero(sexo));
            Fecha fNac = new Fecha(PeticionDatos.pedirEnteroPositivo(true, "> dia:"), //En manual si que pide los datos de la fecha
                    PeticionDatos.pedirEnteroPositivo(true, "> mes:"),
                    PeticionDatos.pedirEnteroPositivo(true, "> año:"));
            do {
                especialidad = PeticionDatos.pedirCadena("> especialidad: ");
                if(!Medico.validarEspecialidad(especialidad)){
                    System.out.println(ANSI_RED+"Error. El dato introducido no es correcto."+ANSI_RESET);
                }
            }while(!Medico.validarEspecialidad(especialidad));

            Medico m=new Medico(dni, id, n, ap1, ap2, sexo, fNac, especialidad);
            return m;
        }
    }


    /**
     * Funcion que crea un nuevo objeto administrativo de forma manual o aleatoria
     * @param aleatorio - true, el objeto se crea automáticamente, false, se piden los datos por teclado.
     * @return Administrativo
     * */
    public Administrativo addAdmin(boolean aleatorio){
        if(aleatorio){
            String dni = Faker.generarNIF_NIE();
            int id = Faker.devolverEnteros(false, trabajadores.length);
            String n = Faker.devolverNombre();
            String ap1 = Faker.devolverApellidos();
            String ap2 = Faker.devolverApellidos();
            String sexo = Faker.devolverSexo();
            Fecha fNac = new Fecha(); // Si se crean aleatorios he decidido que tengan la fecha por defecto, todo a 0.
            String area=Faker.devolverArea();

            Administrativo a=new Administrativo(dni, id, n, ap1, ap2, sexo, fNac, area);
            return a;

        }else {
            String sexo,area;
            String dni = PeticionDatos.pedirNIF_NIE("> dni: ");
            int id = PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
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
            do{
                area = PeticionDatos.pedirCadena("> área: ");
                if (!Persona.validarGenero(sexo)) {
                    System.out.println(ANSI_RED + "Error. El dato introducido no es correcto." + ANSI_RESET);
                }
            }while(!Administrativo.validarArea(area));

            Administrativo a=new Administrativo(dni, id, n, ap1, ap2, sexo, fNac, area);
            return a;
        }
    }

    public void removePersonal(){
        //TODO
    }
}
