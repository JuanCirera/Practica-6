import utilidades.Faker;
import utilidades.Fecha;
import utilidades.GestionArray;
import utilidades.PeticionDatos;

import java.io.File;

/**
 * @author Juan Fco Cirera
 * */
public class GestionMedica {

    //ATRIBUTOS CONSTANTES
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BGREEN = "\u001B[32;1m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_BBLUE = "\u001B[34;1m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";

    private static int opcionMenu;

    private static Centro centrosMedicos[]=new Centro[5];

    /**
     * Muestra el menu de inicio con las opciones del programa
     */
    private static void mostrarMenu() {

        System.out.println(" "); //Espacio en blanco
        System.out.println("╔══════════════════════════"+ANSI_BGREEN+" GESTIÓN MÉDICA ═╬═"+ANSI_RESET);
        opcionMenu = PeticionDatos.pedirEnteroPositivo(true, """ 
                ║ 1-> Gestionar hospital                                      
                ║ 2-> Gestionar clínica                                   
                ║ 3-> Gestionar personal                                      
                ║ 4-> Gestionar paciente                                      
                ║ 5-> Mostrar estadísticas de los centros médicos             
                ║ 6-> Mostrar estadísticas de las personas                    
                ║ 0-> Salir                                                   
                ╚ Elige una opción: """);
        System.out.println(" "); //Espacio en blanco
    }


    //TODO: analizar si estas funciones se pueden juntar en una usando parametros
    private static void gestionarCentro(boolean hospital, boolean clinica){
        String hcs="",hc="";
        //Si hospital/clinica es true todos los string donde aparezcan se cambian, ademas de la informacion que muestra. Chapuzilla temporal
        if(hospital){hcs="hospitales";hc="hospital";
        } else if(clinica){hcs="clínicas";hc="clínica";}

        System.out.println(" "); //Espacio en blanco
        opcionMenu = PeticionDatos.pedirEnteroPositivo(true, "║ 1-> Mostrar "+hcs+"\n"+"║ 2-> Crear "+hc+"\n"+"╚ > ");
        System.out.println(" "); //Espacio en blanco

        switch (opcionMenu){
            case 1:
                System.out.println(ANSI_BBLUE+hcs.toUpperCase()+ANSI_RESET);
                //TODO:Mostrar los hospitales (ID, nombre)
                for(Centro i:centrosMedicos){
                    if(i!=null) {
                        System.out.println(i.getID() + " " + i.getNombre());
                    }
                }
                opcionMenu= PeticionDatos.pedirEnteroPositivo(false, "> Elige un centro (ID): ");
                System.out.println(" "); //Espacio en blanco
                opcionMenu = PeticionDatos.pedirEnteroPositivo(true, """ 
                    ║ 1-> Mostrar información                                      
                    ║ 2-> Modificar datos
                    ║ 3-> Volver al menú                                                                                   
                    ╚ > """);
                System.out.println(" "); //Espacio en blanco
                //TODO: Segun el ID elegido:
                if(opcionMenu==1){
                    //TODO:Mostrar datos del hospital seleccionado
                }else if(opcionMenu==2){
                    int id=PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
                    String nombre=PeticionDatos.pedirCadena( "> nombre: ");
                    String direccion=PeticionDatos.pedirCadena( "> dirección: ");
                    int consultas=PeticionDatos.pedirEnteroPositivo( false, "> nº de consultas: ");
                    int trabajadores=PeticionDatos.pedirEnteroPositivo( false, "> nº de trabajadores: ");
                    //TODO: hacer los set a los atributos del objeto hospital
                }
                break;
            case 2:
                int id=PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
                String nombre=PeticionDatos.pedirCadena( "> nombre: ");
                String direccion=PeticionDatos.pedirCadena( "> dirección: ");
                int consultas=PeticionDatos.pedirEnteroPositivo( false, "> nº de consultas: ");
                int trabajadores=PeticionDatos.pedirEnteroPositivo( false, "> nº de trabajadores: ");
                //TODO: Crear nuevo objeto de tipo hospital y guardarlo en array hsopitales
                break;
            case 3:
                System.out.println(ANSI_YELLOW+"Volviendo al inicio..."+ANSI_RESET);
                break;
            default:
                break;
        }
    }

    /**
     * Gestiona las operaciones sobre los objetos persona
     * @param personal - si el objeto persona a gestionar es un medico/administrativo
     * @param paciente - si el objeto paciente es un paciente
     * */
    private static void gestionarPersona(boolean personal, boolean paciente){
        String dni=PeticionDatos.pedirNIF_NIE("> DNI: ");
        //TODO:Si este dni existe en cualquiera de los arrays de persona, se muestra:
        System.out.println(" "); //Espacio en blanco
        opcionMenu = PeticionDatos.pedirEnteroPositivo(true, """ 
                ║ 1-> Modificar datos                                      
                ║ 2-> Modificar ubicación
                ║ 3-> Añadir día    
                ║ 4-> Despedir                                                                                   
                ╚ > """);
        System.out.println(" "); //Espacio en blanco

        switch (opcionMenu){
            case 1:
                int id=PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
                String nombre=PeticionDatos.pedirCadena( "> nombre: ");
                String apellido1=PeticionDatos.pedirCadena( "> primer apellido: ");
                String apellido2=PeticionDatos.pedirCadena( "> segundo apellido: ");
                String genero=PeticionDatos.pedirCadena( "> sexo: ");
    //            Fecha fecha=PeticionDatos.pedirCadena( "> fecha nacimiento: "); TODO: modificar esto para que se pueda pedir todo junto
                //TODO: Hacer los set para cada atributo
                break;
            case 2:
                System.out.println(" "); //Espacio en blanco
                opcionMenu = PeticionDatos.pedirEnteroPositivo(true, """ 
                    ║ 1-> Cambiar de consulta                                     
                    ║ 2-> Cambiar de hospital                                                                                 
                    ╚ > """);
                System.out.println(" "); //Espacio en blanco

                if(opcionMenu==1){
        //                int consulta=objeto.getConsulta();
                    System.out.println("Actualmente en consulta "+"Variable");
                    int nuevaConsulta=PeticionDatos.pedirEnteroPositivo(false, "> nueva consulta: ");
                    //TODO: hacer el set al atributo consulta
                }else if(opcionMenu==2){
        //                int hospital=objeto.getConsulta();
                    System.out.println("Actualmente en hospital "+"Variable");
                    int nuevaHospital=PeticionDatos.pedirEnteroPositivo(false, "> nueva hospital(ID): ");
                    //TODO: mover del array del hospital en el que este al array del nuevo hospital
                }
                break;
            case 3:
                //TODO: llamar a la funcion addDiaTrabajado/addVisita del objeto en cuestion
            case 4:
                //TODO: guardar el objeto en cuestion en un archivo del que no se lea al iniciar, y poner su poosicion en el array a null.
                break;
            default:
                break;
        }
    }


    private static void estadisticasCentrosMedicos(){
        int mes=PeticionDatos.pedirEnteroPositivo(false, "> mes: ");
        //TODO: mostrar centros, segun el elegido mostrar sus estadisticas
    }


    private static void estadisticasPersonas(){
        //TODO: mostrar centros
        System.out.println(ANSI_BBLUE+"Tipo de persona"+ANSI_RESET);
        opcionMenu = PeticionDatos.pedirEnteroPositivo(true, """ 
                ║ 1-> Paciente                                     
                ║ 2-> Médico   
                ║ 3-> Administrativo                                                                                  
                ╚ > """);
        System.out.println(" "); //Espacio en blanco

        //Segun el centro seleccionado antes:
        switch (opcionMenu){
            case 1:
                //TODO:mostrar todos los pacientes(id, nombre)
                break;
            case 2:
                //TODO:mostrar todos los medicos(id, nombre)
                break;
            case 3:
                //TODO:mostrar todos los administrativos(id, nombre)
                break;
            default:
                break;
        }

        //Segun la persona elegida mostrar sus estad
    }


    /**
     * Funcion que comprueba si la estructura de ficheros existe
     * @return true si los directorios y minimo uno de los archivos existe, false si no existe ningun archivo o ningun directorio.
     * */
    private static boolean checkConfig(){
        File dir=new File("datos");
        File subDir1=new File("datos/centros");
        File subDir2=new File("datos/personas");

        if(dir.exists() && subDir1.exists() && subDir2.exists()){
            File h=new File("datos/centros/hospitales.txt");
            File c=new File("datos/centros/clinicas.txt");
            File p=new File("datos/personas/pacientes.txt");
            File m=new File("datos/personas/medicos.txt");
            File a=new File("datos/personas/administrativos.txt");

            if (h.exists() || c.exists() || p.exists() || m.exists() || a.exists()){
                return true;
            }else{
                return false;
            }
        }else{
//            dir.mkdir(); TODO: Hacer esto cuando se vaya a cerrar el programa
//            subDir1.mkdir();
//            subDir2.mkdir();
            return false;
        }
    }


    /**
     * Comprueba si un identificador de Centro ya existe
     * @param id - identificador de Centro a comprobar
     * @return boolean - true, si coincide con alguno, false si no existe
     * */
    private static boolean checkID(int id){
        for(Centro c: centrosMedicos){
            if(c!=null && c.getID()==id){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }


    /**
     * Funcion que crea un nuevo objeto Centro segun sea hospital o clinica
     * @param hospital - true, Si el objeto se quiere crear de tipo hospital
     * @param clinica - true, si es de tipo clinica
     * */
    private static void nuevoCentro(boolean hospital, boolean clinica, boolean init){

        if(hospital){
            //Si el programa inicia por primera vez se crean 2 Hospitales con 2 Medicos y 2 Admins
            if(init){
                for(int i=0;i<2;i++) {
                    int id;
                    String n=Faker.devolverHospitales();
                    String dir="C/Felicidad, 7";
                    do {
                        id = Faker.devolverEnteros(false, centrosMedicos.length);
                    }while(checkID(id));
                    int plantas=Faker.devolverEnteros(false,10);
                    int hab=Faker.devolverEnteros(false,20);

                    Hospital h = new Hospital(n, dir, id, plantas, hab); // 1 hospital por vuelta
                    //Una vez creado el objeto se guarda en una posicion vacía del array centrosMedicos[]
                    centrosMedicos[Centro.contCentros-1]=h;
//                    for(int x=0;x<2;i++) { // 2 médicos por vuelta
//                        h.addMedico(true);
//                    }
//                    for(int y=0;y<2;i++) { // 2 Admins por vuelta
//                        h.addAdmin(true);
//                    }
                }

            }else{ //Si el programa ya se había ejecutado antes, solo se creara un hospital cada vez que se llame a esta funcion
                String n=PeticionDatos.pedirCadena("> nombre: ");
                String dir=PeticionDatos.pedirCadena("> dirección: ");
                int id=PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
                int plantas=PeticionDatos.pedirEnteroPositivo(false, "> plantas: ");
                int hab=PeticionDatos.pedirEnteroPositivo(false, "> habitaciones: ");
                Hospital h=new Hospital(n,dir,id,plantas,hab);

                for(Centro p: centrosMedicos) {
                    if(p==null) {
                        p=h;
                    }
                }
            }

        }else if(clinica){
            if(init){
                String n=Faker.devolverHospitales();
                String dir="C/Felicidad, 7";
                int id=Faker.devolverEnteros(false,centrosMedicos.length);

                for(int i=0;i<2;i++) {
                    Clinica c=new Clinica(n,dir,id);
                    for(Centro p: centrosMedicos) {
                        if(p==null) {
                            p=c;
                        }
                    }
//                    for(int x=0;x<2;i++) { // 2 médicos por vuelta
//                        c.addMedico(true);
//                    }
//                    for(int y=0;y<2;i++) { // 2 Admins por vuelta
//                        c.addAdmin(true);
//                    }
                }
            }else{
                String n=PeticionDatos.pedirCadena("> nombre: ");
                String dir=PeticionDatos.pedirCadena("> dirección: ");
                int id=PeticionDatos.pedirEnteroPositivo(false, "> ID: ");

                Clinica c=new Clinica(n,dir,id);
                for(Centro i: centrosMedicos) {
                    if(i==null) {
                        i=c;
                    }
                }
            }
        }
    }


    /**
     * Esta función va a crear objetos aleatorios por defecto para que la aplicación funcione
     * */
    private static void estadoPorDefecto(){
        //Hospitales
        nuevoCentro(true,false, true);

        //Clínicas
        nuevoCentro(false,true, true);
    }

    /**
     * Esta funcion va a leer los datos que existan en los archivos y los va a cargar en el programa
     * */
    private static void cargarConfig(){

    }

    /**
     * Función que crea los archivos necesarios para guardar el estado de la aplicación en el momento de salir.
     * */
    private static boolean guardarEstado(){
        return false;
    }


    /**
     * Funcion de inicio del programa que llama el main
     * */
    private static void init(){
        //TODO:comprobar si existe o no algun archivo, si no crear objetos por defecto sin almacenar aun nada
        if(!checkConfig()){ //se llama a la funcion checkConfig para comprobar si existe o no algún archivo previo.
            estadoPorDefecto();
        }else{
            cargarConfig();
        }

        //** MENU PRINCIPAL **
        do {
            mostrarMenu();

            switch (opcionMenu) {
                case 1:
                    //Se llama a la funcion gestionarCentro y se le pasan dos booleanos para indicar lo que se quiere gestionar y lo que no.
                    gestionarCentro(true, false);
                    break;
                case 2:
                    gestionarCentro(false, true);
                    break;
                case 3:
                    gestionarPersona(true, false);
                    break;
                case 4:
                    gestionarPersona(false, true);
                    break;
                case 5:
                    estadisticasCentrosMedicos();
                    break;
                case 6:
                    estadisticasPersonas();
                    break;
                case 0:
                    System.out.println(ANSI_YELLOW + "Saliendo..." + ANSI_RESET);
                    break;
                default:
                    System.out.println(ANSI_RED + "ERROR: Opción no válida." + ANSI_RESET);
                    break;
            }
        } while (opcionMenu != 0);

    }


    public static void main(String[] args) {
        //TODO: dejar el main con una única llamada, crear un constructor que llame a todas las funciones
        //el nombre es temporal
        init();
    }
}
