import utilidades.Faker;
import utilidades.Fecha;
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

    //ATRIBUTOS
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


    //Esta funcion y los booleanos en vez de ser una solucion me ha creado más problemas, le he metido por parámetro esto a casi todas las funciones, algo no cuadra...
    private static void mostrarCentros(/*boolean hospital, boolean clinica*/) {
        for(Centro c:centrosMedicos){
            if(/*hospital &&*/ c!=null /*&& c instanceof Hospital*/) {
//                Hospital h=(Hospital) c;
//                System.out.println(h.getID() + " " + h.getNombre());
                System.out.println(c.getID() + " " + c.getNombre());
            }
//            else if(/*clinica &&*/ c!=null && c instanceof Clinica){
//                Clinica cl=(Clinica) c;
//                System.out.println(cl.getID() + " " + cl.getNombre());
//            }
            //Lo dejo comentado para ver como era
        }
    }


    //De momento lo dejo asi, ya por lo menos no me desquicio al ver lo de arriba
    private static void mostrarHospitales(){
        for(Centro c:centrosMedicos){
            if(c!=null && c instanceof Hospital) {
                Hospital h=(Hospital) c;
                System.out.println(h.getID() + " " + h.getNombre());
            }
        }
    }


    private static void mostrarClinicas(){
        for(Centro c:centrosMedicos){
            if(c!=null && c instanceof Clinica) {
                Clinica cl=(Clinica) c;
                System.out.println(cl.getID() + " " + cl.getNombre());
            }
        }
    }


    /**
     * Elimina el centro que coincida con el id pasado por parámetro.
     * @param id del centro a borrar
     * @return boolean - true, si se ha borrado con éxito, false si encuentra mínimo una persona
     * */
    private static boolean removeCentro(int id){
        //He probado a usar un for each pero solo se hace un set null al objeto en sí, la posicion del array se queda igual, es raro, por eso he usado un for normal.
        //No se trata de eliminar un objeto, sino de VACIAR la posición de un ARRAY.

        //Booleanos para hacer la comprobacion final antes de vaciar la posicion
        boolean consultasVacias=true, habitacionesVacias=true, sinTrabajadores=true;

        //Se comprueba cada uno de los arrays pertenecientes a Centro que guarden objetos persona
        for(int a=0;a<centrosMedicos.length;a++) {
            if (centrosMedicos[a] != null && centrosMedicos[a].getID() == id) {
                //CONSULTAS (PACIENTES)
                for (int i = 0; i < centrosMedicos[a].consultas.length; i++) {
                    if(centrosMedicos[a].consultas[i]!=null){
                        consultasVacias=false;
                    }
                }
                //TRABAJADORES
                for (int y = 0; y < centrosMedicos[a].trabajadores.length; y++) {
                    if (centrosMedicos[a].trabajadores[y] != null) {
                        sinTrabajadores=false;
                    }
                }
                //HABITACIONES (PACIENTES)
                if (centrosMedicos[a] instanceof Hospital) {
                    Hospital h = (Hospital) centrosMedicos[a];
                    for (int x = 0; x < h.habitaciones.length; x++) {
                        for (int z = 0; z < h.habitaciones[x].length; z++) {
                            if (h.habitaciones[x][z] != null) {
                                habitacionesVacias=false; //Y se devuelve false como que ha encontrado una persona y no puede eliminarlo
                            }
                        }
                    }
                }
                if(centrosMedicos[a] instanceof Hospital){ //Este es especial para hospital
                    if(consultasVacias && habitacionesVacias && sinTrabajadores){
                        centrosMedicos[a]=null;
                        return true;
                    }
                }else if(centrosMedicos[a] instanceof Clinica){
                    if (consultasVacias && sinTrabajadores) {
                        //Si los arrays no han encontrado ni una posicion que NO sea nula, pues la posicion donde se encuentra el centro se setea a null
                        centrosMedicos[a] = null;
                        return true;
                    }
                }
            }
        }
//        System.out.println(" ");//Linea
//        centroSubMenu1();
        return false;
    }


    //Gracias a esta funcion me quito tener que estar a cada momento comprobando que objeto es el que tiene la id que se tiene que introducir cada dos por tres.
    /**
     * Esta funcion obtiene mediante un id el objeto centro y lo devuelve si coincide con éste.
     * @param id del objeto centro que se quiere obtener.
     * @return Centro - objeto centro que se necesita para realizar alguna operación con él.
     * */
    private static Centro whichCenter(int id){
        Centro thisCenter=null; //Declaro aqui el objeto a null porque la función se empeña en devolverlo fuera del for...
        for(Centro c: centrosMedicos){
            if(c!=null && c.getID()==id){
                thisCenter=c;
            }
        }
        return thisCenter;
    }


    /**
     * Esta funcion obtiene mediante un DNI el objeto persona y lo devuelve si coincide con éste.
     * @param dni del objeto centro que se quiere obtener.
     * @return Persona - objeto persona que se necesita para realizar alguna operación con él.
     * */
    private static Persona whichPerson(String dni){
        Persona thisPerson=null;
        for(Centro c: centrosMedicos){
            if (c != null) {
                for (Persona p : c.consultas) {
                    if (p != null && p.getDni().equals(dni)) {
                        thisPerson = p;
                    }
                }
                for (Persona p: c.trabajadores){
                    if (p != null && p.getDni().equals(dni)) {
                        thisPerson = p;
                    }
                }
                if(c instanceof Hospital) {
                    Hospital h=(Hospital) c;
                    for (int i=0;i<h.habitaciones.length;i++) {
                        for(int x=0;x<h.habitaciones[i].length;x++) {
                            if (h.habitaciones[i][x] != null && h.habitaciones[i][x].getDni().equals(dni)) {
                                thisPerson = h.habitaciones[i][x];
                            }
                        }
                    }
                }
            }
        }
        return thisPerson;
    }


    //Estas funciones se entienden mejor si se ve el esquema del menu, creo que más o menos se entienden llamándolas asi
    /**
     * Muestra el primer submenu de gestion centros
     * */
    private static void centroSubMenu1(String hc, String hcs){
        System.out.println("╔═ "+ANSI_BBLUE+"Inicio/Gestionar "+hc+ANSI_RESET);
        opcionMenu = PeticionDatos.pedirEnteroPositivo(true, "║ 1-> Mostrar "+hcs+"\n"+"║ 2-> Crear "+hc+"\n"+"║ 3-> Eliminar "+hc+"\n"+"║ 4-> Volver al menu"+"\n"+"╚ > ");
        System.out.println(" "); //Espacio en blanco
    }


    /**
     * Muestra el segundo submenu de gestion centros
     * */
    private static int centroSubMenu2(String tipoCentro, Centro h){
        System.out.println(" "); //Espacio en blanco
        System.out.println("╔═ " + ANSI_BBLUE + "Inicio/Gestionar "+tipoCentro+"/" + h.getNombre() + ANSI_RESET);
        int opcionMenu = PeticionDatos.pedirEnteroPositivo(true, """ 
                                        ║ 1-> Mostrar información                                      
                                        ║ 2-> Modificar datos
                                        ║ 3-> Volver al menú                                                                                   
                                        ╚ > """);
        System.out.println(" "); //Espacio en blanco
        return opcionMenu;
    }


    /**
     * Pide los datos del centro y los actualiza mediante los setter del objeto
     * @param c - objeto centro a actualizar
     * */
    private static void modificarCentro(Centro c){
        //El id no tiene sentido cambiarlo, para eso es mejor eliminarlo entero
        String nombre = PeticionDatos.pedirCadenaLimite(false, true, 70, "> nombre ("+c.getNombre()+")"+": ");
        String direccion = PeticionDatos.pedirCadenaLimite(true, true, 70, "> dirección ("+c.getDireccionCentro()+")"+": ");
        c.setNombreCentro(nombre);
        c.setDireccion(direccion);
    }


    //TODO: analizar si esta funcion se puede mejorar, tiene muchas cosas duplicadas
    private static void gestionarCentro(boolean hospital, boolean clinica){
        String hcs="",hc="";
        int opcion;
        //Si hospital/clinica es true todos los string donde aparezcan se cambian, ademas de la informacion que muestra. Chapuzilla temporal
        if(hospital){hcs="hospitales";hc="hospital";
        } else if(clinica){hcs="clínicas";hc="clínica";}

        //Se muestra el primer submenu de la Opción 1-2
        centroSubMenu1(hc, hcs);

        switch (opcionMenu){
            //** MOSTRAR CENTROS **
            case 1:
                System.out.println(ANSI_BBLUE+hcs.toUpperCase()+ANSI_RESET);
                if(hospital) {
//                    mostrarCentros(true, false);
                    mostrarHospitales();
                }else{
//                    mostrarCentros(false, true);
                    mostrarClinicas();
                }

                idCentro= PeticionDatos.pedirEnteroPositivo(false, ANSI_YELLOW+"> Elige un centro (ID): "+ANSI_RESET);

                for(Centro c: centrosMedicos) {
                    if (c != null && c instanceof Hospital) {
                        Hospital h = (Hospital) c;

                        if (h.getID() == idCentro) {
                            do {
                                //Se muestra el segundo submenu
                                opcion=centroSubMenu2("Hospital", h);
                                switch (opcion){
                                    case 1:
                                        System.out.println(ANSI_BBLUE + "Datos del centro:" + ANSI_RESET);
                                        System.out.println(h.toString());
                                        break;
                                    case 2:
                                        modificarCentro(h);
                                        break;
                                    case 3:
                                        System.out.println(ANSI_YELLOW+"Volviendo al inicio..."+ANSI_RESET);
                                        mostrarMenu();
                                        break;
                                    default:
                                        System.out.println(ANSI_RED+"Error. Elige una de las opciones listadas."+ANSI_RESET);
                                        break;
                                }
                            }while (opcion!=3); //Mientras no se introduzca 3 no se vuelve al menu de inicio, asi da más control al usuario
                        }
                    }else if(c != null && c instanceof Clinica){
                        Clinica cl=(Clinica) c;
                        if (cl.getID() == idCentro) {
                            do {
                                opcion=centroSubMenu2("Clínica", cl);
                                switch (opcion){
                                    case 1:
                                        System.out.println(ANSI_BBLUE + "Datos del centro:" + ANSI_RESET);
                                        System.out.println(cl.toString());
                                        break;
                                    case 2:
                                        modificarCentro(cl);
                                        break;
                                    case 3:
                                        System.out.println(ANSI_YELLOW+"Volviendo al inicio..."+ANSI_RESET);
                                        mostrarMenu();
                                        break;
                                    default:
                                        System.out.println(ANSI_RED+"Error. Elige una de las opciones listadas."+ANSI_RESET);
                                        break;
                                }
                            }while (opcion!=3); //Mientras no se introduzca 3 no se vuelve al menu de inicio, asi da más control al usuario
                        }
                    }
                }
                break;
            //** CREAR CENTRO **
            case 2:
                if(hospital) {
                    nuevoCentro(true, false, false);
                }else{
                    nuevoCentro(false, true, false);
                }
                break;
            //** ELIMINAR CENTRO **
            case 3:
                System.out.println(ANSI_BBLUE+hcs.toUpperCase()+ANSI_RESET);
                if(hospital) {
                    mostrarHospitales();
                }else{
                    mostrarClinicas();
                }
                idCentro= PeticionDatos.pedirEnteroPositivo(false, ANSI_YELLOW+"Elige un centro (ID): "+ANSI_RESET);
                if(removeCentro(idCentro)){
                    System.out.println(" ");//linea
                    System.out.println(ANSI_BGREEN+"Eliminado con éxito"+ANSI_RESET);
                }else{
                    System.out.println(" ");//linea
                    System.out.println(ANSI_YELLOW + "Aviso. El centro elegido contiene personas, no puede ser eliminado." + ANSI_RESET);
                }
                break;
            case 4:
                System.out.println(ANSI_YELLOW+"Volviendo al inicio..."+ANSI_RESET);
                break;
            default:
                System.out.println(ANSI_RED+"Error. Elige una de las opciones listadas."+ANSI_RESET);
                break;
        }
    }


    /**
     * Muestra un menu con los 3 tipos de objeto persona
     * @return int - objeto persona elegido
     * */
    private static int tipoPersona(){
        int opcionMenu = PeticionDatos.pedirEnteroPositivo(true, """ 
                        ║ 1-> Paciente                                     
                        ║ 2-> Médico   
                        ║ 3-> Administrativo  
                        ║ 4-> Volver                                                                                
                        ╚ > """);
        System.out.println(" "); //Espacio en blanco
        return opcionMenu;
    }


    /**
     * Funcion que actualiza los datos de una persona en concreto usando su DNI
     * @param dni de la persona que se quiere modificar.
     * */
    private static void modificarPersona(String dni){
        Persona p=whichPerson(dni);
        String n = PeticionDatos.pedirCadena("> nombre "+"("+p.getNombre()+")"+": ");
        String ap1 = PeticionDatos.pedirCadena("> primer apellido "+"("+p.getApellido1()+")"+": ");
        String ap2 = PeticionDatos.pedirCadena("> segundo apellido "+"("+p.getApellido2()+")"+": ");
        String sexo = PeticionDatos.pedirCadena("> sexo "+"("+p.getSexo()+")"+": ");
        System.out.println("Fecha de nacimiento "+"("+p.getFechaNac()+")");
        int day=PeticionDatos.pedirEnteroPositivo(false, "> día: ");
        int month=PeticionDatos.pedirEnteroPositivo(false, "> mes: ");
        int year=PeticionDatos.pedirEnteroPositivo(false, "> año: ");
        Fecha f=new Fecha(day, month, year);

        p.setDni(dni);
        p.setNombre(n);
        p.setApellido1(ap1);
        p.setApellido2(ap2);
        p.setSexo(sexo);
        p.setFechaNac(f);
    }


    /**
     * Muestra un menu para gestionar una persona en caso de que ya exista.
     * @return int - opcion elegida
     * */
    private static int personaSubMenu1(){
        System.out.println(" "); //Espacio en blanco
        int opcion = PeticionDatos.pedirEnteroPositivo(true, """ 
                    ║ 1-> Modificar datos                                      
                    ║ 2-> Modificar ubicación
                    ║ 3-> Añadir día    
                    ║ 4-> Despedir
                    ║ 5-> Volver                                                                                 
                    ╚ > """);
        System.out.println(" "); //Espacio en blanco
        return opcion;
    }


    /**
     * Mueve a la persona de ubicación.
     * @return int - opcion elegida
     * */
    private static void moverPersona(String dni){
        System.out.println(" "); //Espacio en blanco
        int opcion = PeticionDatos.pedirEnteroPositivo(true, """ 
                            ║ 1-> Cambiar de consulta                                     
                            ║ 2-> Cambiar de hospital                                                                                 
                            ╚ > """);
        System.out.println(" "); //Espacio en blanco

        switch (opcion) {
            case 1:
                //                int consulta=objeto.getConsulta();
                System.out.println("Actualmente en consulta " + "Variable");
                int nuevaConsulta = PeticionDatos.pedirEnteroPositivo(false, "> nueva consulta: ");
                //TODO: hacer el set al atributo consulta
                break;
            case 2:
                //                int hospital=objeto.getConsulta();
                System.out.println("Actualmente en hospital " + "Variable");
                int nuevaHospital = PeticionDatos.pedirEnteroPositivo(false, "> nueva hospital(ID): ");
                //TODO: mover del array del hospital en el que este al array del nuevo hospital
            break;
        }
    }


    /**
     * Funcion que comprueba el centro donde trabaja una persona de tipo medico o admin
     * @param dni del objeto persona.
     * @return Centro - objeto centro donde trabaja esa persona.
     * */
    private static Centro whereWorking(String dni){
        Centro workingHere=null;
        for(Centro c:centrosMedicos){
            if(c!=null){
                for(Persona p: c.trabajadores){
                    if(p!=null && p.getDni().equals(dni)){
                        workingHere=c;
                    }
                }
            }
        }
        return workingHere;
    }


    /**
     * Gestiona las operaciones sobre los objetos persona
     * @param personal - si el objeto persona a gestionar es un medico/administrativo
     * @param paciente - si el objeto paciente es un paciente
     * */
    private static void gestionarPersona(/*boolean personal, boolean paciente*/){

        String dni= PeticionDatos.pedirNIF_NIE("> DNI: ");

        if(checkDNI(dni)) {
            int opcion;
            do {
                opcion = personaSubMenu1();
                Persona p=whichPerson(dni);

                switch (opcion) {
                    case 1:
                        modificarPersona(dni);
                        break;
                    case 2:
                        moverPersona(dni);
                        break;
                    case 3:
                        System.out.println(ANSI_BBLUE+"Introduce la fecha completa del día a añadir"+ANSI_RESET);
                        int day=PeticionDatos.pedirEnteroPositivo(false, "> día: ");
                        int month=PeticionDatos.pedirEnteroPositivo(false, "> mes: ");
                        int year=PeticionDatos.pedirEnteroPositivo(false, "> año: ");
                        Fecha f=null;

                        if(p instanceof Paciente){
                            Paciente paciente=(Paciente) p;
                            f = new Fecha(day, month, year);
                            paciente.addVisita(f);
                        }else if(p instanceof Medico){
                            Medico m=(Medico) p;
                            //Compruebo si la fecha introducida corresponde a un dia laborable o no. En mi caso solo compruebo los fines de semana, creo
                            //que para esto es más que suficiente
                            if(!Fecha.isWeekend(day,month,year)) {
                                f = new Fecha(day, month, year);
                            }else{
                                System.out.println("Aviso. La fecha introducida no es un día laborable");
                            }
                            m.addDiaTrabajado(f);
                        }else{
                            Administrativo admin=(Administrativo) p;
                            if(!Fecha.isWeekend(day,month,year)) {
                                f = new Fecha(day, month, year);
                            }else{
                                System.out.println("Aviso. La fecha introducida no es un día laborable");
                            }
                            admin.addDiaTrabajado(f);
                        }
                    case 4:
                        //TODO: guardar el objeto en cuestion en un archivo del que no se lea al iniciar, y poner su posicion en el array a null.
                        //TODO: mandar esto a una funcion??
                        //PERSONAL
                        Centro c=whereWorking(dni);
                        if(c instanceof Hospital){
                            Hospital h=(Hospital) c;
                            h.removePersonal(whichPerson(dni));
                        }else{
                            Clinica cl=(Clinica) c;
                            cl.removePersonal(whichPerson(dni));
                        }
                        //PACIENTES
                        //TODO: whereAdmitted()
                        if(c instanceof Hospital){
                            Hospital h=(Hospital) c;
                            h.removePaciente(whichPerson(dni));
                        }else{
                            Clinica cl=(Clinica) c;
                            cl.removePaciente(whichPerson(dni));
                        }
                        break;
                    case 5:
                        System.out.println(ANSI_YELLOW + "Volviendo al inicio..." + ANSI_RESET);
                        break;
                    default:
                        System.out.println(ANSI_RED + "Error. Elige una de las opciones listadas." + ANSI_RESET);
                        break;
                }
            }while (opcion!=5);
        }else{ //Si el dni no existe
            System.out.println(ANSI_YELLOW+"Aviso. El dni introducido no está registrado."+ANSI_RESET);
            char respuesta=PeticionDatos.pedirCaracter("> ¿Desea crear una nueva persona? S/N");
            if(respuesta=='S'){
//                nuevaPersona();
            }else{
                System.out.println(ANSI_YELLOW + "Volviendo al inicio..." + ANSI_RESET);
                mostrarMenu();
            }
        }
    }

    //TODO:Arreglar esto que me da algo de verlo XD
    private static void nuevaPersona(boolean paciente, boolean medico, boolean admin, String dni){
        //Peticion de datos
        int id;
//        do {
//            id = PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
//        }while (checkPersonID(id));
        String n=PeticionDatos.pedirCadenaLimite(false, true, 70, "> nombre: ");
        String ap1=PeticionDatos.pedirCadenaLimite(false, true, 70, "> primer apellido: ");
        String ap2=PeticionDatos.pedirCadenaLimite(false, true, 70, "> segundo apellido: ");
        String sexo;
        do {
            sexo = PeticionDatos.pedirCadena("> sexo: ");
        }while (Persona.validarGenero(sexo));
        int day=PeticionDatos.pedirEnteroPositivo(false, "> día: ");
        int month=PeticionDatos.pedirEnteroPositivo(false, "> mes: ");
        int year=PeticionDatos.pedirEnteroPositivo(false, "> año: ");
        Fecha f=new Fecha(day, month, year);

        if(paciente){
//            Paciente p=new Paciente(dni, id, n, ap1, ap2, sexo, f);
        }

        char respuesta=Character.toUpperCase(PeticionDatos.pedirCaracter("¿Asignar a hospital o clínica? H/C: "));
        if(respuesta=='H'){
//            mostrarCentros(true, false);
            idCentro=PeticionDatos.pedirEnteroPositivo(false, "> Elige un hospital(ID): ");
            for(Centro c: centrosMedicos){
                if(c!=null && c.getID()==idCentro){
                    if(c instanceof Hospital){
                        Hospital h=(Hospital) c;
                        if(paciente){
                            System.out.println(" "); //Espacio en blanco
                            opcionMenu = PeticionDatos.pedirEnteroPositivo(true, """ 
                            ║ 1-> Consulta                                     
                            ║ 2-> Planta                                                                                 
                            ╚ > """);
                            System.out.println(" "); //Espacio en blanco
                            switch (opcionMenu){
                                case 1:
//                                    h.addPaciente();
                                    break;
                                case 2:
                                    break;
                            }
                        }

                    }
                }
            }
        }else{
//            mostrarCentros(false, true);
            idCentro=PeticionDatos.pedirEnteroPositivo(false, "> Elige una clínica(ID): ");
        }
        //TODO: esto ya lo hace la funcion addPersona de cada centro, preguntar primero donde asignar, asi llamo a la funcion add que corresponda...
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
            mostrarCentros();
            idCentro = PeticionDatos.pedirEnteroPositivo(false, ANSI_YELLOW + "> Elige un centro(ID): " + ANSI_RESET);

            if(checkCenterID(idCentro)){
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
                    System.out.println("=> "+ANSI_BBLUE+"Inicio/Estadísticas del mes "+mes+"/"+c.getNombre()+ANSI_RESET);
                    c.mostrarEstado();
                    System.out.println(c.diasPorMes(mes));
                }
            }
        }else if(personal){
            do {
                System.out.println("╔═ " + ANSI_BBLUE + "Inicio/Mostrar estadísticas de personas" + ANSI_RESET);
                switch (tipoPersona()) { //Llamo a tipo de persona que devuelve un entero para controlar este switch
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
    private static boolean checkCenterID(int id){
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
                    }while(checkCenterID(id));
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
                int id;
                do{
                    id=PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
                    if(checkCenterID(id)){
                        System.out.println(ANSI_YELLOW+"Aviso. El id introducido ya está registrado."+ANSI_RESET);
                    }
                }while(checkCenterID(id));
                String n=PeticionDatos.pedirCadena("> nombre: ");
                String dir=PeticionDatos.pedirCadena("> dirección: ");
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
                    }while(checkCenterID(id));

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
                int id;
                do{
                    id=PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
                    if(checkCenterID(id)){
                        System.out.println(ANSI_YELLOW+"Aviso. El id introducido ya está registrado."+ANSI_RESET);
                    }
                }while(checkCenterID(id));
                String n=PeticionDatos.pedirCadena("> nombre: ");
                String dir=PeticionDatos.pedirCadena("> dirección: ");

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
        //Despues hay que guardar los datos del array viejo en la copia para no perderlos
        for(int i=0;i<centrosMedicos.length;i++){
            centrosMedicosCopia[i]=centrosMedicos[i];
        }
        //Po último apuntamos el antiguo array al nuevo espacio en memoria.
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
