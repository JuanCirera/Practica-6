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

    private static int opcionMenu, idCentro, idPersona; //Estos dos atributos son para controlar opciones del menu principal y submenus.

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


    //TODO: analizar si estas funcion se puede mejorar
    private static void gestionarCentro(boolean hospital, boolean clinica){
//        int idCentro;
        String hcs="",hc="";
        //Si hospital/clinica es true todos los string donde aparezcan se cambian, ademas de la informacion que muestra. Chapuzilla temporal
        if(hospital){hcs="hospitales";hc="hospital";
        } else if(clinica){hcs="clínicas";hc="clínica";}

        System.out.println(" "); //Espacio en blanco
        System.out.println("╔═ "+ANSI_BBLUE+"Inicio/Gestionar "+hc+ANSI_RESET);
        opcionMenu = PeticionDatos.pedirEnteroPositivo(true, "║ 1-> Mostrar "+hcs+"\n"+"║ 2-> Crear "+hc+"\n"+"║ 3-> Volver al menu"+"\n"+"╚ > ");
        System.out.println(" "); //Espacio en blanco

        switch (opcionMenu){
            case 1:
                System.out.println(ANSI_BBLUE+hcs.toUpperCase()+ANSI_RESET);

                for(Centro c:centrosMedicos){
                    if(hospital && c!=null && c instanceof Hospital) {
                        Hospital h=(Hospital) c;
                        System.out.println(h.getID() + " " + h.getNombre());
                    }else if(clinica && c!=null && c instanceof Clinica){
                        Clinica cl=(Clinica) c;
                        System.out.println(cl.getID() + " " + cl.getNombre());
                    }
                }

                idCentro= PeticionDatos.pedirEnteroPositivo(false, ANSI_YELLOW+"Elige un centro (ID): "+ANSI_RESET);

                for(Centro c: centrosMedicos) {
                    if (c != null && c instanceof Hospital) {
                        Hospital h = (Hospital) c;

                        if (h.getID() == idCentro) {
                            System.out.println(" "); //Espacio en blanco
                            System.out.println("╔═ "+ANSI_BBLUE+"Inicio/Gestionar Hospital/"+h.getNombre()+ANSI_RESET);
                            opcionMenu = PeticionDatos.pedirEnteroPositivo(true, """ 
                                    ║ 1-> Mostrar información                                      
                                    ║ 2-> Modificar datos
                                    ║ 3-> Volver al menú                                                                                   
                                    ╚ > """);
                            System.out.println(" "); //Espacio en blanco

                            if (opcionMenu == 1) {
                                    System.out.println(ANSI_BBLUE + "Datos del centro:" + ANSI_RESET);
                                    System.out.println(h.toString());

                            } else if (opcionMenu == 2) {
                                int id;
                                do {
                                    id = PeticionDatos.pedirEnteroPositivo(false, "> ID "+"("+idCentro+")"+": ");
                                }while(checkID(id));
                                String nombre = PeticionDatos.pedirCadena("> nombre: ");
                                String direccion = PeticionDatos.pedirCadenaLimite(true, true, 70, "> dirección: ");
    //                            int consultas = PeticionDatos.pedirEnteroPositivo(false, "> nº de consultas: ");
    //                            int trabajadores = PeticionDatos.pedirEnteroPositivo(false, "> nº de trabajadores: ");

                                h.setID(id);
                                h.setNombreCentro(nombre);
                                h.setDireccion(direccion);
                            }
                        }
                    }
                }
//                System.out.println(ANSI_YELLOW+"Volviendo al inicio..."+ANSI_RESET);
                break;
            case 2:
                int id=PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
                String nombre=PeticionDatos.pedirCadena( "> nombre: ");
                String direccion=PeticionDatos.pedirCadena( "> dirección: ");
                int consultas=PeticionDatos.pedirEnteroPositivo( false, "> nº de consultas: ");
                int trabajadores=PeticionDatos.pedirEnteroPositivo( false, "> nº de trabajadores: ");
                //TODO: Crear nuevo objeto de tipo hospital y guardarlo en array

                break;
            case 3:
                System.out.println(ANSI_YELLOW+"Volviendo al inicio..."+ANSI_RESET);
                break;
            default:
                System.out.println(ANSI_RED+"Error. Elige una de las opciones listadas."+ANSI_RESET);
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


    /**
     * Funcion que muestra las estadísticas según el tipo de objeto elegido.
     * @param centro - Si se quieren las stats de un objeto Centro
     * @param personal - Si se quieren las stats de un objeto Persona
     * */
    private static void estadisticas(boolean centro, boolean personal){
//        String errorMsg=" ";
        boolean idCentroExists=false;
        //PEDIR MES
        //Se pide el mes del que se quiere mostrar las stats mientras que no se introduzca un valor válido.
        int mes;
        do {
            System.out.println("╔═ " + ANSI_BBLUE + "Inicio/Mostrar estadísticas" + ANSI_RESET);
            mes = PeticionDatos.pedirEnteroPositivo(false, "║" + "\n" + "╚ > mes: ");
        }while (!Fecha.checkMonth(mes)); //Con la clase Fecha compruebo que el mes sea válido, 1-12

        //MOSTRAR CENTROS
        Centro centroStats=null;
        do {
            System.out.println(" "); //LINEA
            System.out.println(ANSI_BBLUE + "CENTROS" + ANSI_RESET);
            for (Centro c : centrosMedicos) {
                if (c != null) {
                    System.out.println(c.toString());
                }
            }
            idCentro = PeticionDatos.pedirEnteroPositivo(false, ANSI_YELLOW + "> Elige un centro(ID): " + ANSI_RESET);

            if(checkID(idCentro)){
                for(Centro c: centrosMedicos){
                    if(c!=null && c.getID()==idCentro) {
                        centroStats = c; //Este objeto Centro lo necesito para almacenar el centro que coincide con la id introducida. (Lo necesito fuera del for)
                    }
                }
                idCentroExists = true;
            }else{
                idCentroExists=false;
            }

            if(!idCentroExists){
                System.out.println(ANSI_RED+"Error. El id introducido no existe."+ANSI_RESET);
            }
        }while (!idCentroExists);
        //Esto se va a ejecutar mientras el idCentro introducido no exista, además se informa al usuario

        if(centro){
            for(Centro c:centrosMedicos){
                if(c!=null && c.getID()==idCentro){
                    System.out.println(" "); //LINEA
                    System.out.println("╔═ "+ANSI_BBLUE+"Inicio/Estadísticas del mes "+mes+"/"+c.getNombre()+ANSI_RESET);
                    c.mostrarEstado();
                    System.out.println(c.diasPorMes(mes));
                }
            }
        }else if(personal){
            do {
                System.out.println(" ");
                System.out.println("╔═ " + ANSI_BBLUE + "Inicio/Mostrar estadísticas de personas" + ANSI_RESET);
                opcionMenu = PeticionDatos.pedirEnteroPositivo(true, """ 
                        ║ 1-> Paciente                                     
                        ║ 2-> Médico   
                        ║ 3-> Administrativo  
                        ║ 4-> Volver                                                                                
                        ╚ > """);
                System.out.println(" "); //Espacio en blanco
                switch (opcionMenu) {
                    case 1:
                        //TODO:mostrar todos los pacientes(id, nombre)
                        System.out.println(ANSI_YELLOW + "En desarrollo..." + ANSI_RESET);
                        break;
                    case 2:
                        //Paso por parámetro el objeto centro anterior a esta funcion, ya que lo necesita en su bucle.
                        mostrarStatsPersonal(mostrarPersonal(true, false), centroStats, mes);
                        break;
                    case 3:
                        mostrarStatsPersonal(mostrarPersonal(false, true), centroStats, mes);
                        break;
                    case 4:
                        System.out.println(ANSI_YELLOW + "Volviendo al inicio..." + ANSI_RESET);
                        break;
                    default:
                        System.out.println(ANSI_RED + "Error. Elige una de las opciones." + ANSI_RESET);
                        break;
                }
            }while (opcionMenu!=4);
        }
    }


    /**
     * Funcion que muestra un listado con el personal de un tipo que existe en un centro
     * @param medicos - lista todos los medicos
     * @param admins - lista todos los admins
     * @return int - idPersona introducido por teclado
     * */
    private static int mostrarPersonal(boolean medicos, boolean admins){
        int idPersona=0;
        int notNull=0;
        for(Centro c: centrosMedicos){
            if(c!=null && c.getID()==idCentro){
                for(Persona p: c.trabajadores){
                    if(medicos && p!=null && p instanceof Medico) {
                        notNull++;
                        Medico m=(Medico) p;
                        System.out.println(m.toString());
                    }else if(admins && p!=null && p instanceof Administrativo) {
                        notNull++;
                        Administrativo a=(Administrativo) p;
                        System.out.println(a.toString());
                    }else if(notNull==0){ //Si no existe ninguna posicion no nula, o sea esta vacio el array muestra un mensaje de aviso
                        System.out.println(ANSI_YELLOW+"Aviso. No existe ninguna persona en este centro."+ANSI_RESET);
                        mostrarMenu(); //vuelve al menu
                        //Esta condición se me ha ocurrido de casualidad porque no se me han creado las personas por un error,
                        // normalmente no debería saltar porque desde el inicio hay personas,
                        //pero ya la dejo ahi por si acaso.
                    }
                }
                idPersona=PeticionDatos.pedirEnteroPositivo(false, ANSI_YELLOW+"> ID: "+ANSI_RESET);
            }
        }
        return idPersona;
    }


    /**
     * Funcion que muestra las stats del personal
     * @param id - identificador de la persona
     * @param c - objeto centro al que pertenece esa persona
     * @param mes - mes del que se quieren extraer las stats
     * */
    private static void mostrarStatsPersonal(int id, Centro c, int mes){
        System.out.println(" ");
        System.out.println(ANSI_BBLUE+"Inicio/Estadísticas del mes "+mes+ANSI_RESET);

        for(Persona i: c.trabajadores) {
            if (i != null && i.getID() == id) {
                i.mostrarEstado();
                System.out.println("Ha trabajado un total de " + i.diasPorMes(mes) + " días.");
            }
        }
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
            if(init){ Hospital h=null; //TODO
                for(int i=0;i<2;i++) {
                    int id;
                    String n=Faker.devolverHospitales();
                    String dir="C/Por defecto, 0";
                    do {
                        id = Faker.devolverEnteros(false, centrosMedicos.length);
                    }while(checkID(id));
                    int plantas=Faker.devolverEnteros(false,10);
                    int hab=Faker.devolverEnteros(false,20);

                     h = new Hospital(n, dir, id, plantas, hab); // 1 hospital por vuelta
                    //Una vez creado el objeto se guarda en una posicion vacía del array centrosMedicos[]
                    centrosMedicos[Centro.contCentros-1]=h;

                    for(int x=0;x<2;x++) { // 2 médicos por vuelta
                        h.addMedico(true, h);
                    }
                    for(int y=0;y<2;y++) { // 2 Admins por vuelta
                        h.addAdmin(true, h);
                    }
                }

            }else{ //Si el programa ya se había ejecutado antes, solo se creara un hospital cada vez que se llame a esta funcion
                String n=PeticionDatos.pedirCadena("> nombre: ");
                String dir=PeticionDatos.pedirCadena("> dirección: ");
                int id;
                do{
                    id=PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
                }while(checkID(id));
                int plantas=PeticionDatos.pedirEnteroPositivo(false, "> plantas: ");
                int hab=PeticionDatos.pedirEnteroPositivo(false, "> habitaciones: ");
                Hospital h=new Hospital(n,dir,id,plantas,hab);

                centrosMedicos[Centro.contCentros-1]=h;
            }

        }else if(clinica){
            if(init){
                Clinica cl=null;
                for(int i=0;i<2;i++) {
                    String n=Faker.devolverClinicas();
                    String dir="C/Por defecto, 0";
                    int id;
                    do{
                        id=Faker.devolverEnteros(false,centrosMedicos.length);
                    }while(checkID(id));

                    cl=new Clinica(n,dir,id);
                    centrosMedicos[Centro.contCentros-1]=cl;

                    for(int x=0;x<2;x++) { // 2 médicos por vuelta
                        cl.addMedico(true, cl);
                    }
                    for(int y=0;y<2;y++) { // 2 Admins por vuelta
                        cl.addAdmin(true, cl);
                    }
                }
            }else{
                String n=PeticionDatos.pedirCadena("> nombre: ");
                String dir=PeticionDatos.pedirCadena("> dirección: ");
                int id=PeticionDatos.pedirEnteroPositivo(false, "> ID: ");

                Clinica c=new Clinica(n,dir,id);
                centrosMedicos[Centro.contCentros-1]=c;
            }
        }
    }


    /**
     * Esta funcion comprueba que no se cree una persona nueva con un dni que ya exista en el contexto global del programa
     * @param dni a comprobar
     * @return boolean - true, si encuentra un dni que coincida, false si no encuentra nada.
     * */
    protected static boolean checkDNI(String dni){
        for(Centro c: centrosMedicos){ //Busca en todos los centros
            if(c!=null) {
                for (Persona p : c.trabajadores) { //En todos los trabajadores de cada centro
                    if (p != null && p.getDni().equals(dni)) { //Comprueba que el dni de cada trabajador de cada centro sea el mismo que el parametro
                        return true;
                    }
                }
            }
        }
        return false;
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
        //TODO: Guardar los objetos en sus archivos correspondientes
//            dir.mkdir(); TODO: Hacer esto cuando se vaya a cerrar el programa
//            subDir1.mkdir();
//            subDir2.mkdir();
        return false;
    }


    /**
     * Funcion chapucera para aumentar el tamaño de un array fijo que se ha llenado
     * */
    //No la hago ni modular porque sé que no voy a usar esto más, si no la metía en la libreria.
    private static void aumentarCentrosMedicos(){
        //Se crea un nuevo array de tipo Cetro con el doble de la longitud del array centrosMedicos actual.
        Centro centrosMedicosCopia[]=new Centro[centrosMedicos.length*2];
        //Despues apuntamos el antiguo array al nuevo espacio en memoria.
        centrosMedicos=centrosMedicosCopia;
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
                    estadisticas(true,false);
                    break;
                case 6:
                    estadisticas(false,true);
                    break;
                case 0:
                    char guardar=Character.toUpperCase(PeticionDatos.pedirCaracter("¿Desea guardar el estado actual? S/N: "));

                    if(guardar=='S'){
                        System.out.println(ANSI_YELLOW + "Guardando..."+ ANSI_RESET);
                        guardarEstado();
                    }else{
                        System.out.println(ANSI_YELLOW + "Saliendo..." + ANSI_RESET);
                    }
                    break;
                default:
                    System.out.println(ANSI_RED + "ERROR: Opción no válida." + ANSI_RESET);
                    break;
            }
        } while (opcionMenu != 0);

    }


    public static void main(String[] args) {
        init();
    }
}
