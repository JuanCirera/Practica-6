import utilidades.Faker;
import utilidades.Fecha;
import utilidades.PeticionDatos;

import java.io.*;
import java.util.Scanner;

/**
 * Clase que se encarga de gestionar centros, personas, hacer comprobaciones, etc.
 * @author Juan Fco Cirera
 * */
public class GestionMedica implements Serializable {

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

    private Centro centrosMedicos[]=new Centro[5];
    //Aqui se guarda la planta y hab introducidas por teclado, como no puedo devolverlos a la vez en una funcion, esto es lo que se me ha ocurrido.
    private static int planta=0, hab=0;
    //La idea de usar este objeto no tiene sentido, he decidido serializar el array centrosMedicos. Más explicacion en recuperarEstado()
    //    private static GestionMedica app;


    /**
     * Resetea a 0 los valores de planta y hab para evitar problemas entre funciones distintas.
     * */
    private static void resetHabitacion(){
        planta=0;
        hab=0;
    }


    /**
     * Muestra el menu de inicio con las opciones del programa
     */
    protected void menuInicio() {
        int opcionInicio;
        do {
            System.out.println(" "); //Espacio en blanco
            System.out.println("╔══════════════════════════"+ANSI_BGREEN+" GESTIÓN MÉDICA ═╬═"+ANSI_RESET);
            opcionInicio = PeticionDatos.pedirEnteroPositivo(true, """ 
                    ║ 1-> Gestionar hospital                                      
                    ║ 2-> Gestionar clínica                                   
                    ║ 3-> Gestionar personal                                      
                    ║ 4-> Gestionar paciente                                      
                    ║ 5-> Mostrar estadísticas de los centros médicos             
                    ║ 6-> Mostrar estadísticas de las personas                    
                    ║ 0-> Salir                                                   
                    ╚ Elige una opción: """);
//            System.out.println(" "); //Espacio en blanco

            switch (opcionInicio) {
                case 1:
                    //Se llama a la funcion gestionarCentro y se le pasan dos booleanos para indicar lo que se quiere gestionar y lo que no.
                    gestionarCentro(1);
                    break;
                case 2:
                    gestionarCentro(2);
                    break;
                case 3:
                    gestionarPersona(2);
                    break;
                case 4:
                    gestionarPersona(1);
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
                        System.out.print(ANSI_YELLOW + "Guardando..."+ ANSI_RESET);
                        if(guardarEstado("datos/app.ser", "datos/static.ser")){
                            System.out.print(ANSI_GREEN + " OK"+ ANSI_RESET);
                            System.exit(0);
                        }
                    }else{
                        System.out.println(" ");
                        System.out.println(ANSI_YELLOW + "Saliendo..." + ANSI_RESET);
                        System.exit(0);
                        //Tengo que salir del programa usando exit porque si udo break al salir del do-while entra de nuevo en el main y llama otra vez a init() y
                        //se muestra de nuevo el menuInicio()
                    }
                    break;
                default:
                    System.out.println(ANSI_RED + "ERROR: Opción no válida." + ANSI_RESET);
                    break;
            }
        } while (opcionInicio != 0);
    }


    /**
     * Muestra un listado de los centros segun el tipo
     * @param tipo 1-Hospital, 2-Clinica, 0-Ambos.
     * */
    protected void mostrarCentros(int tipo) {
        ordenarPorIDcentro(centrosMedicos);
        if(tipo==1) {
            for (Centro c : centrosMedicos) {
                if (c != null && c instanceof Hospital) {
                Hospital h=(Hospital) c;
                System.out.println(h.getID() + " " + h.getNombre());
                }
            }
        }else if(tipo==2){
            for (Centro c : centrosMedicos) {
                if (c != null && c instanceof Clinica) {
                    Clinica cl=(Clinica) c;
                    System.out.println(cl.getID() + " " + cl.getNombre());
                }
            }
        }else{
            for (Centro c : centrosMedicos) {
                if (c != null) {
                    System.out.println(c.getID() + " " + c.getNombre());
                }
            }
        }
    }


    /**
     * Elimina el centro que coincida con el id pasado por parámetro.
     * @param id del centro a borrar
     * @return boolean - true, si se ha borrado con éxito, false si encuentra mínimo una persona
     * */
    protected boolean removeCentro(int id){
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
        return false;
    }


    //Gracias a esta funcion me quito tener que estar a cada momento comprobando que objeto es el que tiene la id que se tiene que introducir cada dos por tres.
    /**
     * Esta funcion obtiene mediante un id el objeto centro y lo devuelve si coincide con éste.
     * @param id del objeto centro que se quiere obtener.
     * @return Centro - objeto centro que se necesita para realizar alguna operación con él.
     * */
    protected Centro whichCenter(int id){
        Centro thisCenter=null; //Declaro aqui el objeto a null porque la función se empeña en devolverlo fuera del for...
        for(Centro c: centrosMedicos){
            if(c!=null && c.getID()==id){
                thisCenter=c;
            }
        }
        return thisCenter;
    }


    /**
     * Esta funcion obtiene mediante un dni el numero de consulta y lo devuelve si coincide con éste.
     * @param dni del objeto paciente.
     * @param c - centro medico donde esta el paciente.
     * @return int - numero de consulta donde está el paciente, 0 si no lo encuentra en niguna consulta.
     * */
    protected static int whichConsulta(String dni, Centro c){
        int thisConsulta=0, cont=0; //Declaro aqui el objeto a null porque la función se empeña en devolverlo fuera del for...
        if(c!=null) {
            for (Paciente p : c.consultas) {
                cont++;
                if (p != null && p.getDni().equals(dni)) {
                    thisConsulta = cont;
                }
            }
        }
        return thisConsulta;
    }


    //Como las funciones no pueden devolver más de un valor pues para saber en qué planta y habitacion está el paciente tengo que usar dos funciones identicas.
    /**
     * Esta funcion obtiene mediante un dni el numero de planta y lo devuelve.
     * @param dni del objeto paciente.
     * @param h - hospital donde esta el paciente.
     * @return int - numero de planta.
     * */
    protected static int whichFloor(String dni, Hospital h){
        int thisFloor=0; //Declaro aqui el objeto a null porque la función se empeña en devolverlo fuera del for...
        for (int i=0;i<h.habitaciones.length;i++) {
            for(int x=0;x<h.habitaciones[i].length;x++) {
                if (h.habitaciones[i][x] != null && h.habitaciones[i][x].getDni().equals(dni)) {
                    thisFloor = i; //Devuelve la planta y la habitacion donde esta el paciente
                }
            }
        }
        return thisFloor;
    }


    /**
     * Esta funcion obtiene mediante un dni el numero de habitacion y lo devuelve.
     * @param dni del objeto paciente.
     * @param h - hospital donde esta el paciente.
     * @return int - numero de habitación.
     * */
    protected static int whichRoom(String dni, Hospital h){
        int thisRoom=0; //Declaro aqui el objeto a null porque la función se empeña en devolverlo fuera del for...
        for (int i=0;i<h.habitaciones.length;i++) {
            for(int x=0;x<h.habitaciones[i].length;x++) {
                if (h.habitaciones[i][x] != null && h.habitaciones[i][x].getDni().equals(dni)) {
                    thisRoom = x; //Devuelve la planta y la habitacion donde esta el paciente
                }
            }
        }
        return thisRoom;
    }


    /**
     * Esta funcion obtiene mediante un DNI el objeto persona y lo devuelve si coincide con éste.
     * @param dni del objeto centro que se quiere obtener.
     * @return Persona - objeto persona que se necesita para realizar alguna operación con él.
     * */
    protected Persona whichPerson(String dni){
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


    /**
     * Esta funcion obtiene mediante un ID el objeto persona y lo devuelve si coincide con éste.
     * @param idPersona del objeto persona que se quiere obtener.
     * @return Persona - objeto persona que se necesita para realizar alguna operación con él.
     * */
    protected Persona whichPerson(int idPersona){
        Persona thisPerson=null;
        for(Centro c: centrosMedicos){
            if (c != null) {
                for (Persona p : c.consultas) {
                    if (p != null && p.getID()==idPersona) {
                        thisPerson = p;
                    }
                }
                for (Persona p: c.trabajadores){
                    if (p != null && p.getID()==idPersona) {
                        thisPerson = p;
                    }
                }
                if(c instanceof Hospital) {
                    Hospital h=(Hospital) c;
                    for (int i=0;i<h.habitaciones.length;i++) {
                        for(int x=0;x<h.habitaciones[i].length;x++) {
                            if (h.habitaciones[i][x] != null && h.habitaciones[i][x].getID()==idPersona) {
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
    protected static int centroSubMenu1(String hc, String hcs){
        System.out.println(" "); //Espacio en blanco
        System.out.println("╔═ "+ANSI_BBLUE+"Inicio/Gestionar "+hc+ANSI_RESET);
        int opcion = PeticionDatos.pedirEnteroPositivo(true, "║ 1-> Mostrar "+hcs+"\n"+"║ 2-> Crear "+hc+"\n"+"║ 3-> Eliminar "+hc+"\n"+"║ 4-> Volver al menu"+"\n"+"╚ > ");
        return opcion;
    }


    /**
     * Muestra el segundo submenu de gestion centros
     * */
    protected static int centroSubMenu2(String tipoCentro, Centro h){
        System.out.println(" "); //Espacio en blanco
        System.out.println("╔═ " + ANSI_BBLUE + "Inicio/Gestionar "+tipoCentro+"/" + h.getNombre() + ANSI_RESET);
        int opcion = PeticionDatos.pedirEnteroPositivo(true, """ 
                                        ║ 1-> Mostrar información                                      
                                        ║ 2-> Modificar datos
                                        ║ 3-> Volver al menú                                                                                   
                                        ╚ > """);
//        System.out.println(" "); //Espacio en blanco
        return opcion;
    }


    /**
     * Pide los datos del centro y los actualiza mediante los setter del objeto
     * @param c - objeto centro a actualizar
     * */
    protected static void modificarCentro(Centro c){
        //El id no tiene sentido cambiarlo, para eso es mejor eliminarlo entero
        String nombre = PeticionDatos.pedirCadenaLimite(false, true, 70, "> nombre ("+c.getNombre()+")"+": ");
        String direccion = PeticionDatos.pedirCadenaLimite(true, true, 70, "> dirección ("+c.getDireccionCentro()+")"+": ");
        c.setNombreCentro(nombre);
        c.setDireccion(direccion);
    }


    //Esta función es temporal no me ha dado tiempo de mejorarla
    /**
     * Funcion que gestiona un objeto centro segun si es hospital o clinica
     * @param tipo 1-Hospital, 2-Clinica.
     * */
    protected void gestionarCentro(int tipo){
        String hcs="",hc="";
        int opcion;
        //Si hospital/clinica es true todos los string donde aparezcan se cambian, ademas de la informacion que muestra. Chapuzilla temporal
        if(tipo==1){
            hcs="hospitales";hc="hospital";
        } else if(tipo==2){
            hcs="clínicas";hc="clínica";
        }

        switch (centroSubMenu1(hc, hcs)){
            case 1:
                //** MOSTRAR CENTROS Y PEDIR ID**
                int idCentro= pedirIDcentro(false, tipo);

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
                                        System.out.println(" ");//linea
                                        System.out.println(ANSI_YELLOW+"Volviendo al inicio..."+ANSI_RESET);
                                        menuInicio();
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
                                        System.out.println(" ");
                                        System.out.println(ANSI_YELLOW+"Volviendo al inicio..."+ANSI_RESET);
                                        menuInicio();
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

            case 2:
                //** CREAR CENTRO **
                if(tipo==1) {
                    nuevoCentro(true, false, false);
                }else{
                    nuevoCentro(false, true, false);
                }
                break;

            case 3:
                //** ELIMINAR CENTRO **
                idCentro= pedirIDcentro(false, tipo);

                if(removeCentro(idCentro)){
                    System.out.println(" ");//linea
                    System.out.println(ANSI_BGREEN+"Eliminado con éxito"+ANSI_RESET);
                }else{
                    System.out.println(" ");//linea
                    System.out.println(ANSI_YELLOW + "Aviso. El centro elegido contiene personas, no puede ser eliminado." + ANSI_RESET);
                }
                break;

            case 4:
                System.out.println(" ");
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
    protected static int tipoPersona(){
        int opcion = PeticionDatos.pedirEnteroPositivo(true, """ 
                        ║ 1-> Paciente                                     
                        ║ 2-> Médico   
                        ║ 3-> Administrativo  
                        ║ 4-> Volver                                                                                
                        ╚ > """);
//        System.out.println(" "); //Espacio en blanco
        return opcion;
    }


    /**
     * Funcion que actualiza los datos de una persona en concreto usando su DNI
     * @param dniA de la persona que se quiere modificar.
     * */
    protected void modificarPersona(String dniA){
        Persona p=whichPerson(dniA); //Saco el objeto persona mediante su dni, asi puedo acceder a sus getters/setters

        String dni=PeticionDatos.pedirNIF_NIE("> DNI "+"("+p.getDni()+")"+": ");
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
     * Muestra un menu para mostrar las personas existentes o crear nuevas.
     * @return int - opcion elegida
     * */
    protected static int personaSubMenu1(int tipo){
        String t="", ts="";
        if(tipo==1){
            t="paciente";
            ts="pacientes";
        }else{
            t="trabajador";
            ts="trabajadores";
        }
        System.out.println(" ");
        System.out.println("╔═ " + ANSI_BBLUE + "Inicio/Gestionar "+t+ ANSI_RESET);
        int opcion = PeticionDatos.pedirEnteroPositivo(true, "║ 1-> Mostrar "+ts+"\n"+"║ 2-> Crear "+t+"\n"+"║ 3-> Volver"+"\n"+"╚ > ");
//        System.out.println(" "); //Espacio en blanco
        return opcion;
    }


    /**
     * Muestra un menu para gestionar una persona en caso de que ya exista.
     * @param p objeto persona a gestionar.
//     * @return int - opcion elegida.
     * */
    protected void personaSubMenu2(Persona p){
        int opcion=0;
        Centro c;
        String dni=p.getDni();
        do {
            System.out.println(" ");//linea
            System.out.println("╔═ " + ANSI_BBLUE + "Inicio/Gestionar Persona/"+p.getNombre()+ ANSI_RESET);
            opcion = PeticionDatos.pedirEnteroPositivo(true, """ 
                        ║ 1-> Modificar datos                                      
                        ║ 2-> Modificar ubicación
                        ║ 3-> Añadir día    
                        ║ 4-> Despedir
                        ║ 5-> Volver                                                                                 
                        ╚ > """);
            System.out.println(" "); //Espacio en blanco
//            return opcion;

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
//                            if(!Fecha.isWeekend(day,month,year)) {
                            f = new Fecha(day, month, year);
//                            }else{
//                                System.out.println(ANSI_YELLOW+"Aviso. La fecha introducida no es un día laborable"+ANSI_RESET);
//                            }
                        if(m.addDiaTrabajado(f)){
                            System.out.println(ANSI_BGREEN+"Día añadido correctamente."+ANSI_RESET);
                        }else{
                            System.out.println(ANSI_YELLOW+"Aviso. La fecha introducida no es un día laborable."+ANSI_RESET);
                        }
                    }else{
                        Administrativo admin=(Administrativo) p;
//                            if(!Fecha.isWeekend(day,month,year)) {
                            f = new Fecha(day, month, year);
//                            }else{
//                                System.out.println(ANSI_YELLOW+"Aviso. La fecha introducida no es un día laborable"+ANSI_RESET);
//                            }
                        if(admin.addDiaTrabajado(f)){
                            System.out.println(ANSI_BGREEN+"Día añadido correctamente."+ANSI_RESET);
                        }else{
                            System.out.println(ANSI_YELLOW+"Aviso. La fecha introducida no es un día laborable."+ANSI_RESET);
                        }
                    }
                    break;
                case 4:
                    Persona person=whichPerson(dni);
                    //Antes de vaciar su posicion en el array, se guarda(no se pa que) llamando a esta funcion.
                    boolean saved=saveFired(person);
                    //PERSONAL
                    c=whereWorking(dni);
                    if(c instanceof Hospital && person instanceof Medico || person instanceof Administrativo){
                        Hospital h=(Hospital) c;
                        if(h.removePersonal(person) && saved){
                            System.out.println(ANSI_BGREEN+"Trabajador eliminado con éxito."+ANSI_RESET);
                        }
                    }else if(c instanceof Clinica && person instanceof Medico || person instanceof Administrativo){
                        Clinica cl=(Clinica) c;
                        if(cl.removePersonal(person) && saved){
                            System.out.println(ANSI_BGREEN+"Trabajador eliminado con éxito."+ANSI_RESET);
                        }
                    }
                    //PACIENTES
                    if(person instanceof Paciente) {
                        c = whereAdmitted(dni); //Se busca en que centro está la persona a la que pertenece ese dni
                        //Aqui tengo que liar esto porque whichPerson devuelve un objeto Persona y me hace falta un Paciente
                        Persona cualquiera = whichPerson(dni);
                        Paciente paciente = null;
                        if (cualquiera instanceof Paciente) {
                            paciente = (Paciente) cualquiera;
                        }
                        if (c instanceof Hospital) { //Si el centro es un hospital, se "elimina" de sus arrays
                            Hospital h = (Hospital) c;
                            h.removePaciente(paciente);
                        } else { //Si no se elimina del array consultas de clinica
                            Clinica cl = (Clinica) c;
                            cl.removePaciente(paciente);
                        }
                    }
                    break;
                case 5:
                    System.out.println(" ");
                    System.out.println(ANSI_YELLOW + "Volviendo al inicio..." + ANSI_RESET);
                    menuInicio();
                    break;
                default:
                    System.out.println(ANSI_RED + "Error. Elige una de las opciones listadas." + ANSI_RESET);
                    break;
            }
        }while (opcion!=5);
    }


    /**
     * Pide por teclado un numero de consulta y lo devuelve si la posicion no está ocupada
     * @return int - numero de consulta
     * */
    protected static int pedirConsulta(Centro c){
        int consulta;
        do {
            System.out.println(" ");
            consulta = PeticionDatos.pedirEnteroPositivo(false, "> consulta: ");
            if(!checkConsulta(consulta, c)){
                System.out.println(ANSI_YELLOW+"Aviso. La consulta está ocupada."+ANSI_RESET);
            }
        }while (!checkConsulta(consulta, c));

        return consulta;
    }


    /**
     * Pide por teclado un numero de planta y habitacion. Guarda los valores en los atributos o no segun checkHabitacion()
     * */
    protected static void pedirHabitacion(Hospital h){
        do {
            System.out.println(" ");
            planta = PeticionDatos.pedirEnteroPositivo(false, "> planta: ");
            hab = PeticionDatos.pedirEnteroPositivo(false, "> habitación: ");
            if(!checkHabitacion(planta, hab, h)){
                System.out.println(ANSI_YELLOW+"Aviso. La consulta está ocupada."+ANSI_RESET);
            }
        }while (!checkHabitacion(planta, hab, h));

    }


    /**
     * Pide por teclado un mes y lo devuelve si es válido.
     * @return int - mes válido introducido
     * */
    protected static int pedirMes(){
        //Se pide el mes del que se quiere mostrar las stats mientras que no se introduzca un valor válido.
        int mes;
        do {
            System.out.println(" ");
            System.out.println("╔═ " + ANSI_BBLUE + "Inicio/Mostrar estadísticas" + ANSI_RESET);
            mes = PeticionDatos.pedirEnteroPositivo(false, "║" + "\n" + "╚ > mes: ");
            //Aqui no muestro ningun mensaje porque ya lo hace checkMonth
        }while (!Fecha.checkMonth(mes)); //Con la clase Fecha compruebo que el mes sea válido, 1-12
        return mes;
    }


    /**
     * Pide por teclado un identificador de persona y comprueba si ya existe o no.
     * @param nuevaPersona true si se está creando una nueva persona, false si solo se necesita un id existente.
     * @return int - id introducido.
     * */
    protected int pedirIDpersona(boolean nuevaPersona){
        int idPersona=0;
        if(nuevaPersona) {
            do {
                idPersona = PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
                if (checkPersonID(idPersona)) {
                    System.out.println(ANSI_YELLOW + "Aviso. El id introducido ya está registrado." + ANSI_RESET);
                }
            } while (checkPersonID(idPersona));
        }else{
            do {
                idPersona = PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
                if (!checkPersonID(idPersona)) {
                    System.out.println(ANSI_YELLOW + "Aviso. El id introducido no está registrado." + ANSI_RESET);
                }
            } while (!checkPersonID(idPersona));
        }
        return idPersona;
    }


    /**
     * Pide por teclado un identificador de persona y comprueba si ya existe o no.
     * @param nuevaPersona true si se está creando una nueva persona, false si solo se necesita un id existente.
     * @return int - id introducido.
     * */
    protected String pedirDNI(boolean nuevaPersona){
        String dni="";
        if(nuevaPersona) {
            do {
                System.out.println(" ");
                dni = PeticionDatos.pedirNIF_NIE("> DNI: ");
                if (checkDNI(dni)) {
                    System.out.println(ANSI_YELLOW + "Aviso. El dni introducido ya está registrado." + ANSI_RESET);
                }
            } while (checkDNI(dni));
        }else{
            do {
                System.out.println(" ");
                dni = PeticionDatos.pedirNIF_NIE("> DNI: ");
                if (!checkDNI(dni)) {
                    System.out.println(ANSI_YELLOW + "Aviso. El dni introducido no está registrado." + ANSI_RESET);
                }
            } while (!checkDNI(dni));
        }
        return dni;
    }


    /**
     * Pide por teclado un identificador de centro y comprueba si ya existe o no.
     * @param nuevoCentro true si se está creando un nuevo centro, false si solo es para pedir un id existente.
     * @param tipo de centro a mostrar en caso de que !nuevoCentro. 1-H, 2-CL, O-defecto
     * @return int - id introducido.
     * */
    protected int pedirIDcentro(boolean nuevoCentro, int tipo){
        int idCentro=0;
        String titulo="";
        if(nuevoCentro) {
            do {
                System.out.println(" ");

                idCentro = PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
                if (checkCenterID(idCentro)) {
                    System.out.println(ANSI_YELLOW + "Aviso. El id introducido ya esta registrado." + ANSI_RESET);
                }
            } while (checkCenterID(idCentro));
        }else {
            if(tipo==1){
                titulo="HOSPITALES";
            }else{
                titulo="CLÍNICAS";
            }
            do {
                System.out.println(" ");
                System.out.println(ANSI_BBLUE +titulo+ ANSI_RESET);
                mostrarCentros(tipo);

                idCentro = PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
                if (!checkCenterID(idCentro)) {
                    System.out.println(ANSI_YELLOW + "Aviso. El id introducido no esta registrado." + ANSI_RESET);
                }
            } while (!checkCenterID(idCentro));
        }
        return idCentro;
    }


    /**
     * Pide la ubicacion donde quiere ser guardado un paciente, si en consulta o planta.
     * @param titulo a mostrar encima del menu.
     * @return int - opcion elegida.
     * */
    protected static int pedirUbicacion(String titulo){
        System.out.println(" "); //Espacio en blanco
        System.out.println(titulo);
        int opcion = PeticionDatos.pedirEnteroPositivo(true, """
                    ║ 1-> Consulta
                    ║ 2-> Planta
                    ╚ > """);
        System.out.println(" "); //Espacio en blanco
        return opcion;
    }


    /**
     * Mueve a la persona de ubicación.
     * */
    protected void moverPersona(String dni){
        int opcion=0;
        do {
            System.out.println(" "); //Espacio en blanco
            System.out.println("╔═ " + ANSI_BBLUE + "Inicio/Gestionar Persona/Cambiar ubicación" + ANSI_RESET);
            opcion = PeticionDatos.pedirEnteroPositivo(true, """ 
                    ║ 1-> Cambiar de consulta
                    ║ 2-> Cambiar de habitación                                  
                    ║ 3-> Cambiar de centro
                    ║ 4-> Volver                                                                           
                    ╚ > """);
            System.out.println(" "); //Espacio en blanco

            Persona p = whichPerson(dni);

            switch (opcion) {
                case 1:
                    Paciente paciente=null, nuevoPaciente=null;
                    Centro c=null;
                    if(p instanceof Paciente) {
                        paciente=(Paciente) p;
                        c = whereAdmitted(dni);
                    }else{
                        //Si la persona es un trabajador obviamente no es un paciente a la vez por lo que creo un objeto paciente con los datos
                        //del trabajador y asi poder añadirlo a una consulta
                        c=whereWorking(dni);
                        nuevoPaciente=new Paciente(p.getDni(), p.getID(), p.getNombre(), p.getApellido1(), p.getApellido2(), p.getSexo(), p.getFechaNac());
                    }

                    int antiguaConsulta=whichConsulta(dni, c);

                    System.out.println("Actualmente en consulta " + antiguaConsulta);
                    int nuevaConsulta = pedirConsulta(c);
                    if(p instanceof Paciente) {
                        c.consultas[nuevaConsulta-1] = paciente;
                    }else {
                        c.consultas[nuevaConsulta-1] = nuevoPaciente;
                    }
                    c.consultas[antiguaConsulta] = null;
                    break;
                case 2:
                    Centro thisCenter=whereAdmitted(dni);
                    //Si el centro donde está ingresada la persona es un hospital se puede mover de habitacion
                    try {
                        if (thisCenter instanceof Hospital) {
                            Hospital h = (Hospital) thisCenter;
                            int oldFloor = whichFloor(dni, h);
                            int oldRoom = whichRoom(dni, h);
                            System.out.println("Actualmente en la planta " + planta + ", habitación " + hab);
                            pedirHabitacion(h);
                            //Se guarda el paciente en la nueva posicion introducida por teclado.
                            h.habitaciones[planta][hab] = (Paciente) p; //Se hace un downcast para meterlo en habitaciones
                            //Se vacía su antigua posición en el array.
                            h.habitaciones[oldFloor][oldRoom] = null;
                            System.out.println(ANSI_BGREEN + "Paciente reubicado correctamente." + ANSI_RESET);
                            resetHabitacion(); //reseteo los valores porque ya he hecho la operacion arriba.

                        } else {
                            System.out.println(ANSI_YELLOW + p.getNombre() + " no está ingresado/a en ningún hospital." + ANSI_RESET);
                        }
                    }catch (ArrayIndexOutOfBoundsException e){
                        System.out.println(ANSI_RED+"Error. La habitación introducida no existe"+ANSI_RESET);
                    }

                    break;
                case 3:
                    Centro actual=whereAdmitted(dni);
                    System.out.println("Actualmente en " + actual.getNombre()+", ID "+actual.getID());
                    int nuevoCentro = pedirIDcentro(false, 0);
                    Centro nuevo=whichCenter(nuevoCentro);
                    //Si el centro elegido es un hospital
                    if(nuevo instanceof Hospital){
                        Hospital hNuevo = (Hospital) nuevo;
                        //Se comprueba que no este en consulta
                        if(whichConsulta(dni,actual)==0) { //Si esta funcion devuelve 0 que es el valor por defecto significa que to el array esta vacío.
                            planta = whichFloor(dni, hNuevo);
                            hab = whichRoom(dni, hNuevo);
                            //Se comprueba que la habitacion de destino no esté ocupada ya.
                            if(!checkHabitacion(planta, hab, hNuevo)){
                                System.out.println(ANSI_RED + "Error. La habitación de destino está ocupada. Se debe introducir manualmente." + ANSI_RESET);
                                pedirHabitacion(hNuevo);
                            }
                            //Se mete en la misma habitacion pero en el otro centro
                            hNuevo.habitaciones[planta][hab]=(Paciente) p;
                            resetHabitacion();
                        //Y si está en una consulta en el viejo centro, se mueve al mismo número en el nuevo
                        }else{
                            int n=whichConsulta(dni,actual);
                            //Si la consulta de destino está ocupada no almacena al nuevo objeto, si no sobreescribiría el anterior.
                            //Muestra un mensaje al usuario y pide la consulta manualmente hasta que no esté ocupada
                            if(!checkConsulta(n, hNuevo)) {
                                System.out.println(ANSI_RED + "Error. La consulta de destino está ocupada. Se debe introducir manualmente." + ANSI_RESET);
                                int nueva=pedirConsulta(hNuevo);
                                hNuevo.consultas[nueva]=(Paciente) p;
                            }
                            hNuevo.consultas[whichConsulta(dni,actual)]=(Paciente) p;
                        }

                    }else{
                        Clinica clNuevo=(Clinica) nuevo;
                        int n=whichConsulta(dni,actual);
                        if(!checkConsulta(n, clNuevo)) {
                            System.out.println(ANSI_RED + "Error. La consulta de destino está ocupada. Se debe introducir manualmente." + ANSI_RESET);
                            int nueva=pedirConsulta(clNuevo);
                            clNuevo.consultas[nueva]=(Paciente) p;
                        }
                        clNuevo.consultas[whichConsulta(dni,actual)]=(Paciente) p;
                    }

                    break;
                case 4:
                    System.out.println(" ");
                    System.out.println(ANSI_YELLOW + "Volviendo al inicio..." + ANSI_RESET);
                    menuInicio();
                    break;
            }
        }while (opcion!=4);
    }


    /**
     * Funcion que comprueba el centro donde trabaja una persona de tipo medico o admin
     * @param dni del objeto persona.
     * @return Centro - objeto centro donde trabaja esa persona.
     * */
    protected Centro whereWorking(String dni){
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
     * Funcion que comprueba el centro donde está ingresado o en consulta un paciente
     * @param dni del objeto persona.
     * @return Centro - objeto centro donde esta ese paciente.
     * */
    protected Centro whereAdmitted(String dni){
        Centro admittedHere=null;
        for(Centro c:centrosMedicos){
            if(c!=null){
                for(Persona p: c.consultas){
                    if(p!=null && p.getDni().equals(dni)){
                        admittedHere=c;
                    }
                }
                //Si no lo encuentra arriba hay que comprobar las habitaciones, claro solo si es un hospital
                if(c instanceof Hospital){
                    Hospital h=(Hospital) c;
                    for(int i=0;i<h.habitaciones.length;i++){
                        for(int x=0;x<h.habitaciones[i].length;x++) {
                            if (h.habitaciones[i][x] != null && h.habitaciones[i][x].getDni().equals(dni)) {
                                admittedHere = c;
                            }
                        }
                    }
                }
            }
        }
        return admittedHere;
    }


    /**
     * Gestiona las operaciones sobre los objetos persona
//     * @param personal - si el objeto persona a gestionar es un medico/administrativo
//     * @param paciente - si el objeto paciente es un paciente
     * */
    protected void gestionarPersona(int tipo){

        int opcion=0;
        do {
            opcion = personaSubMenu1(tipo);

            switch (opcion) {
                case 1:
                    mostrarPersonas(tipo);
                    break;
                case 2:
                    System.out.println(" ");
                    String dni=PeticionDatos.pedirNIF_NIE("> DNI: ");
//                    String dni=pedirDNI(false);
                    if(checkDNI(dni)){
                        if(tipo==1) { //Si la gestion es para paciente y se mete un dni de trabajador se pedira si ingresar al trabajador
                            System.out.println(ANSI_YELLOW+"El dni introducido está registrado como trabajador."+ANSI_RESET);
                            char respuesta=Character.toUpperCase(PeticionDatos.pedirCaracter("> ¿Desea ingresarlo? S/N: "));
                            if(respuesta=='S'){
                                Persona p=whichPerson(dni);
                                //TODO: funcion para ingresar trabajadores
//                                ingresarPersona(p);
                                nuevaPersona(false, 1); //TODO: pero como le paso los datos del trabajador?

                            }

                        }else{
                            personaSubMenu2(whichPerson(dni));
                        }
                    }else{
                        System.out.println(ANSI_YELLOW+"El dni introducido no está registrado."+ANSI_RESET);
                        char respuesta=Character.toUpperCase(PeticionDatos.pedirCaracter("> ¿Desea crear una nueva persona? S/N: "));
                        if(respuesta=='S'){
                            if(tipo==2 || tipo==3) {
                                System.out.println(" ");
                                System.out.println("2. Médico" + "\n" + "3. Administrativo");
                                int workerType = PeticionDatos.pedirEnteroPositivo(false, "> Elige una opción: ");
                                nuevaPersona(false, workerType);
                            }else {
                                nuevaPersona(false, tipo);
                            }
                        }else{
                            System.out.println(" ");
                            System.out.println(ANSI_YELLOW+"Volviendo al inicio..."+ANSI_RESET);
                            menuInicio();
                            break;
                        }
                    }
                    break;
                case 3:
                    System.out.println(" ");
                    System.out.println(ANSI_YELLOW + "Volviendo al inicio..." + ANSI_RESET);
                    break;
            }
        }while (opcion!=3);

    }


    /**
     * Funcion que crea un nuevo objeto Persona y llamar a su funcion add correspondiente
     * @param tipo de objeto persona, 1(Paciente) 2(Médico) 3(Admin)
     * */
    protected Persona nuevaPersona(boolean aleatorio, int tipo) {
        //** PETICIÓN DE DATOS **
        int id=0;
        String especialidad="", area="", dni="", n="", ap1="", ap2="", sexo="";
        Fecha fNac=null;
        Paciente p = null;
        Medico m=null;
        Administrativo a=null;

        //ALEATORIOS
        if(aleatorio) {
            Centro.totalTrabajadores++;
            do {
                id = Faker.devolverEnteros(false, Centro.totalTrabajadores+1);
            } while (checkPersonID(id));

            do{
                dni=Faker.generarNIF_NIE();
            }while (checkDNI(dni));
            n = Faker.devolverNombre();
            ap1 = Faker.devolverApellidos();
            ap2 = Faker.devolverApellidos();
            sexo = Faker.devolverSexo();
            fNac = new Fecha(); // Si se crean aleatorios he decidido que tengan la fecha por defecto, 0-0-0.
            //De forma aleatoria solo se pueden crear trabajadores, como el enunciado no dice nada de los pacientes, se queda asi.
            if (tipo == 2) {
                do {
                    especialidad = Faker.devolverEspecialidad();
                } while (!Medico.validarEspecialidad(especialidad));

                m = new Medico(dni, id, n, ap1, ap2, sexo, fNac, especialidad);
                return m;

            } else if (tipo == 3) {
                do {
                    area = Faker.devolverArea();
                } while (!Administrativo.validarArea(area));

                a = new Administrativo(dni, id, n, ap1, ap2, sexo, fNac, area);
                return a;
            }

        }else {
            //MANUALES
            dni=pedirDNI(true);

            if(tipo==1) {
                do {
                    id = pedirIDpersona(true);

                    if (checkPersonID(id)) {
                        if(whichPerson(id) instanceof Medico || whichPerson(id) instanceof Administrativo) {
                            char respuesta = Character.toUpperCase(PeticionDatos.pedirCaracter(ANSI_CYAN + "El ID introducido está registrado como trabajador. ¿Desea ingresarlo? S/N: " + ANSI_RESET));
                            Persona w=whichPerson(id); //Guardo el objeto persona en w

                            if (respuesta == 'S') {
                                System.out.println(w.toString()); //Muestro sus datos
                                //Se crea un paciente con los datos de la persona que ya existía (trabajador), siempre y cuando no sea otro paciente
                                Paciente paciente=new Paciente(w.getDni(), w.getID(), w.getNombre(), w.getApellido1(), w.getApellido2(), w.getSexo(), w.getFechaNac());
                                //Se asigna a un centro
                                addPatientTo(paciente); //LLamo a la funcion que asigna a un paciente a consulta o planta en un Hospital
                                System.out.println(" ");
                                menuInicio();

                            }else{
                                personaSubMenu2(w);

                            }
                            break;

                        }else{
                            System.out.println(ANSI_YELLOW+"El ID introducido ya está registrado como paciente."+ANSI_RESET);
                            System.out.println(" ");
                            System.out.println(whichPerson(id).toString());
                            personaSubMenu2(whichPerson(id));
                        }
                    }
                }while (checkPersonID(id));

            }else{
                id=pedirIDpersona(true);
            }

            n = PeticionDatos.pedirCadenaLimite(false, true, 70, "> nombre: ");
            ap1 = PeticionDatos.pedirCadenaLimite(false, true, 70, "> primer apellido: ");
            ap2 = PeticionDatos.pedirCadenaLimite(false, true, 70, "> segundo apellido: ");
            do {
                sexo = PeticionDatos.pedirCadena("> sexo: ");
                if (!Persona.validarGenero(sexo)) {
                    System.out.println(ANSI_YELLOW + "Aviso. El dato introducido no es correcto." + ANSI_RESET);
                }
            } while (!Persona.validarGenero(sexo));

            int day = PeticionDatos.pedirEnteroPositivo(false, "> día: ");
            int month = PeticionDatos.pedirEnteroPositivo(false, "> mes: ");
            int year = PeticionDatos.pedirEnteroPositivo(false, "> año: ");
            fNac = new Fecha(day, month, year);

            if (tipo == 2) {
                do {
                    especialidad = PeticionDatos.pedirCadena("> especialidad: ");
                    if (!Medico.validarEspecialidad(especialidad)) {
                        System.out.println(ANSI_RED + "Error. El dato introducido no es correcto." + ANSI_RESET);
                    }
                } while (!Medico.validarEspecialidad(especialidad));
            } else if (tipo == 3) {
                do {
                    area = PeticionDatos.pedirCadena("> área: ");
                    if (!Persona.validarGenero(sexo)) {
                        System.out.println(ANSI_RED + "Error. El dato introducido no es correcto." + ANSI_RESET);
                    }
                } while (!Administrativo.validarArea(area));
            }

            // ** CREACIÓN DE LOS OBJETOS **
            if(tipo==1){
                p = new Paciente(dni, id, n, ap1, ap2, sexo, fNac);
                if(addPatientTo(p)){
                    System.out.println(ANSI_BGREEN+"Paciente creado correctamente"+ANSI_RESET);
                }

            } else if (tipo == 2) {
                m = new Medico(dni, id, n, ap1, ap2, sexo, fNac, especialidad);
                if(addWorkerTo(m)){
                    System.out.println(ANSI_BGREEN+"Médico creado correctamente"+ANSI_RESET);
                }

            } else if (tipo == 3) {
                a = new Administrativo(dni, id, n, ap1, ap2, sexo, fNac, area);
                if(addWorkerTo(a)){
                    System.out.println(ANSI_BGREEN+"Administrativo creado correctamente"+ANSI_RESET);
                }
            }
        }
        return null; //Me pide aqui que devuelva algun objeto, pero como solo necesito devolver los dos aleatorios, aqui devuelvo null
    }


    //Podría haber usado la funcion nuevaPersona, pero como necesito datos de un objeto que ya existe no puedo volver a pedirlos.
    /**
     * Ingresa un objeto persona de tipo medico/admin que ya existe.
     * @param p - objeto persona a ingresar
     * @return boolean - true si se ha añadido con éxito al centro correspondiente, false si no.
     * */
    protected boolean ingresarPersona(Persona p){
        Paciente trabIngresado=new Paciente(p.getDni(), p.getID(), p.getNombre(), p.getApellido1(), p.getApellido2(),
                p.getSexo(), p.getFechaNac());
        mostrarCentros(0);
        int idCentro=PeticionDatos.pedirEnteroPositivo(false, "> ID: ");
        //TODO:copiar la opcion de crear paciente de nuevaPersona

        return false;
    }


    /**
     * Lista los centros y con uno ya elegido lo añade.
     * @param p - persona a añadir
     * @return boolean - true si la función addMedico/Admin() del centro elegido devuelve verdadero, false si no.
     * */
    protected boolean addWorkerTo(Persona p){
        //Se listan los centros y se elige uno
        int idCentro=pedirIDcentro(false, 0);
        Centro c=whichCenter(idCentro);
        //Sobre el centro elegido se colocará el objeto persona

        if(c instanceof Hospital) {
            Hospital h=(Hospital) c;
            if (p instanceof Medico) {
                if(h.addMedico((Medico) p)) {
                    return true;
                }
            } else {
                if(h.addAdmin((Administrativo) p)) {
                    return true;
                }
            }
        }else{
            Clinica cl=(Clinica) c;
            if (p instanceof Medico) {
                if(cl.addMedico((Medico) p)) {
                    return true;
                }
            } else {
                if(cl.addAdmin((Administrativo) p)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Lista los centros y con uno ya elegido pide los datos del paciente y lo añade a ese centro.
     * @param p - persona a añadir
     * @return boolean - true si la función addPaciente() del centro elegido devuelve verdadero, false si no.
     * */
    protected boolean addPatientTo(Persona p){
        //Se listan los centros y se elige uno
        int idCentro=pedirIDcentro(false, 0);
        Centro c=whichCenter(idCentro);
        //Sobre el centro elegido se colocará el objeto persona

        int nConsulta, nPlanta, nHabitacion;

        if(c instanceof Hospital) {
            Hospital h=(Hospital) c;

            if (p instanceof Paciente) {

                int opcion=pedirUbicacion("╔═ " + ANSI_BBLUE + ".../Asignar centro/" + h.getNombre() + "/Seleccionar Ubicación" + ANSI_RESET);

                switch (opcion) {
                    case 1:
                        h.mostrarEstado();
                        if (h.addPaciente((Paciente) p, pedirConsulta(h))) { //Meto la llamada a la funcion en el if y asi aprovecho para informar del resultado
                            System.out.println(ANSI_BGREEN + "Paciente creado correctamente" + ANSI_RESET);
                        }
                        break;
                    case 2:
                        do {
                            pedirHabitacion(h);
                            if (!checkHabitacion(planta, hab, h)) {
                                System.out.println(ANSI_YELLOW + "Aviso. La habitación introducida está ocupada." + ANSI_RESET);
                            }
                        } while (!checkHabitacion(planta, hab, h));

                        if (h.addPaciente((Paciente) p, planta, hab)) {
                            System.out.println(ANSI_BGREEN + "Paciente creado correctamente" + ANSI_RESET);
                        }

                        resetHabitacion();

                        break;
                }
            }
        }else{
            Clinica cl=(Clinica) c;

            if (p instanceof Paciente) {
                cl.mostrarEstado();
                if(cl.addPaciente((Paciente) p, pedirConsulta(cl))) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Funcion que muestra las estadísticas según el tipo de objeto elegido.
     * @param centro Si se quieren las stats de un objeto Centro
     * @param personal Si se quieren las stats de un objeto Persona
     * */
    protected void estadisticas(boolean centro, boolean personal){

        //PEDIR MES
        int mes=pedirMes();

        //PEDIR CENTRO
        int idCentro=pedirIDcentro(false, 0);

        if(centro){
            for(Centro c:centrosMedicos){
                if(c!=null && c.getID()==idCentro){
                    System.out.println(" "); //LINEA
                    System.out.println(ANSI_BBLUE+"Inicio/Estadísticas del mes "+mes+"/"+c.getNombre()+ANSI_RESET);
                    c.mostrarEstado();
                    System.out.println(c.diasPorMes(mes));
                }
            }
        }else if(personal){
            int opcion;
            do {
                System.out.println(" ");//linea
                System.out.println("╔═ " + ANSI_BBLUE + "Inicio/Mostrar estadísticas de personas" + ANSI_RESET);
                opcion=tipoPersona();
                switch (opcion) { //Llamo a tipo de persona que devuelve un entero para controlar este switch
                    case 1:
                        int idPaciente=0;
                        //En caso de que no existan pacientes, como al inicio del programa, no se pedirá el ID ni se mostrará ninguna stat,
                        //se informa al usuario y aparece de nuevo el menu anterior
                        if(mostrarPacientes(whichCenter(idCentro))) {
                            idPaciente = pedirIDpersona(false);
                            mostrarStatsPaciente(whichPerson(idPaciente), mes);
                        }
                        //Para el personal no controlo esto, no lo creo necesario porque siempre se crean personas de este tipo al arrancar
                        break;
                    case 2:
                        int idPersona=mostrarPersonal(2, whichCenter(idCentro));
                        mostrarStatsPersonal(whichPerson(idPersona), mes);
                        break;
                    case 3:
                        idPersona=mostrarPersonal(3, whichCenter(idCentro));
                        mostrarStatsPersonal(whichPerson(idPersona), mes);
                        break;
                    case 4:
                        System.out.println(" ");
                        System.out.println(ANSI_YELLOW + "Volviendo al inicio..." + ANSI_RESET);
                        menuInicio();
                        break;
                    default:
                        System.out.println(ANSI_RED + "Error. Elige una de las opciones." + ANSI_RESET);
                        break;
                }
            }while (opcion!=4);
        }
    }


    /**
     * Funcion que muestra un listado con el personal de un tipo que existe en un centro
     * @param tipo de trabajador, 2(Médico) 3(Admin)
     * @return int - idPersona introducido por teclado
     * */
    protected int mostrarPersonal(int tipo, Centro c) {
        int idPersona=0;
        System.out.println(" ");//linea

        for(Persona p: c.trabajadores){
            if(p!=null && tipo==2 && p instanceof Medico) {
                Medico m=(Medico) p;
                System.out.println(m.toString());
            }else if(p!=null && tipo==3 && p instanceof Administrativo) {
                Administrativo a=(Administrativo) p;
                System.out.println(a.toString());
            }
        }
        idPersona=pedirIDpersona(false);

        return idPersona;
    }


    /**
     * Funcion que muestra las stats del personal
     * @param worker - objeto persona, en concreto medico/admin
     * @param mes - mes del que se quieren extraer las stats
     * */
    protected static void mostrarStatsPersonal(Persona worker, int mes){
        System.out.println(" ");
        System.out.println(ANSI_BBLUE+".../"+worker.getClass().getSimpleName()+"/Estadísticas del mes "+mes+ANSI_RESET);

        worker.mostrarEstado();
        System.out.println("Ha trabajado un total de " + worker.diasPorMes(mes) + " días.");

    }


    /**
     * Funcion que muestra las stats del personal
     * @param paciente - identificador de la persona
     * @param mes - mes del que se quieren extraer las stats
     * */
    protected static void mostrarStatsPaciente(Persona paciente, int mes){
        System.out.println(" ");
        System.out.println(ANSI_BBLUE+".../"+paciente.getClass().getSimpleName()+"/Estadísticas del mes "+mes+ANSI_RESET);

        paciente.mostrarEstado();
        System.out.println("Ha realizado " + paciente.diasPorMes(mes) + " visitas.");

    }


    /**
     * Muestra los pacientes que tiene un centro medico, tanto en consultas como en habitaciones si es un Hospital
     * @param c - centro del que se quieren mostrar los pacientes
     * @return boolean - true si existe al menos un paciente, false si no hay nada
     * */
    protected static boolean mostrarPacientes(Centro c){
        String mensaje="";
        System.out.println(" ");//linea
        for(Paciente p: c.consultas){
            if(p!=null) {
                System.out.println(p.getID() + ", " + p.getNombre() + " " + p.getApellido1() + " " + p.getApellido2());
                return true;
            }
//            else {
//                mensaje=ANSI_YELLOW+"Aún no hay pacientes registrados."+ANSI_RESET;
//            }
        }
        if(c instanceof Hospital){
            Hospital h=(Hospital) c;
            for (int x = 0; x < h.habitaciones.length; x++) {
                for (int z = 0; z < h.habitaciones[x].length; z++) {
                    if (h.habitaciones[x][z] != null) {
                        System.out.println(h.habitaciones[x][z].getID()+", "+h.habitaciones[x][z].getNombre()+" "+h.habitaciones[x][z].getApellido1()+" "+
                                           h.habitaciones[x][z].getApellido2());
                        return true;
                    }
//                    else {
//                        mensaje=ANSI_YELLOW+"Aún no hay pacientes registrados."+ANSI_RESET;
//                    }
                }
            }
        }
//        System.out.println(mensaje);
        return false;
    }


    /**
     * Muestra todas las personas registradas.
     * */
    protected void mostrarPersonas(int tipo){
        String mensaje="";
        System.out.println(" ");//linea
        for(Centro c: centrosMedicos){
            if(c!=null) {
                if(c instanceof Hospital){
                    Hospital h=(Hospital) c;
                    //Se muestra un listado según el tipo de persona que hay en todos los centros
                    if(tipo==2 || tipo==3) {
                        ordenarPorIDpersona(h.trabajadores);
                        for (Persona worker : h.trabajadores) {
                            if (worker != null) {
                                System.out.println(worker.toString());
//                                return true;
                            }
//                            else {
//                                mensaje = ANSI_YELLOW + "Aún no hay trabajadores registrados." + ANSI_RESET;
//                            }
                        }
                    }else {
                        ordenarPorIDpersona(h.consultas);
                        for (Paciente paciente : h.consultas) {
                            if (paciente != null) {
                                System.out.println(paciente.toString());
//                                return true;
                            } else {
                                mensaje = ANSI_YELLOW + "Aún no hay pacientes registrados." + ANSI_RESET;
                            }
                        }

                        for (int x = 0; x < h.habitaciones.length; x++) {
                            for (int z = 0; z < h.habitaciones[x].length; z++) {
                                if (h.habitaciones[x][z] != null) {
                                    System.out.println(h.habitaciones[x][z].toString());
//                                    return true;
                                } else {
                                    mensaje = ANSI_YELLOW + "Aún no hay pacientes registrados." + ANSI_RESET;
                                }
                            }
                        }
                    }
                }else{
                    Clinica cl=(Clinica) c;
                    if(tipo==2 || tipo==3) {
                        ordenarPorIDpersona(cl.trabajadores);
                        for (Persona worker : cl.trabajadores) {
                            if (worker != null) {
                                System.out.println(worker.toString());
//                                return true;
                            }
//                            else {
//                                mensaje = ANSI_YELLOW + "Aún no hay trabajadores registrados." + ANSI_RESET;
//                            }
                        }
                    }else {
                        ordenarPorIDpersona(cl.consultas);
                        for (Paciente paciente : cl.consultas) {
                            if (paciente != null) {
                                System.out.println(paciente.toString());
//                                return true;
                            } else {
                                mensaje = ANSI_YELLOW + "Aún no hay pacientes registrados." + ANSI_RESET;
                            }
                        }
                    }
                }
//                return true;
            }
//            else {
//                mensaje=ANSI_YELLOW+"Aún no hay pacientes registrados."+ANSI_RESET;
//            }
        }

        System.out.println(mensaje);
//        return false;
    }


    /**
     * Funcion que comprueba si la estructura de ficheros existe
     * @return true si los directorios y minimo uno de los archivos existe, false si no existe ningun archivo o ningun directorio.
     * */
    protected static boolean checkConfig(){
        File dir=new File("datos");
//        File subDir1=new File("datos/centros");
        File subDir2=new File("datos/personas");

        if(dir.exists() && /*subDir1.exists() &&*/ subDir2.exists()){
//            File h=new File("datos/centros/hospitales.txt");
//            File c=new File("datos/centros/clinicas.txt");
//            File p=new File("datos/personas/pacientes.txt");
//            File m=new File("datos/personas/medicos.txt");
//            File a=new File("datos/personas/administrativos.txt");
            File app=new File("datos/app.ser");
//            File f=new File("datos/personas/fired.txt"); No se va a leer de él por lo que no importa si existe o no.

            if (/*h.exists() || c.exists() || p.exists() || m.exists() || a.exists()*/app.exists()){
                return true;
            }else{
                return false;
            }
        }else{
            //En caso de que los directorios no existan lo que voy a hacer es crear el arbol de directorios y dejarlos vacios.
            dir.mkdir();
//            subDir1.mkdir();
            subDir2.mkdir();
            return false;
        }
    }


    /**
     * Comprueba si un identificador de Centro ya existe
     * @param id - identificador de Centro a comprobar
     * @return boolean - true, si coincide con alguno, false si no existe
     * */
    protected boolean checkCenterID(int id){
        for(Centro c: centrosMedicos){
            if(c!=null && c.getID()==id){
                return true;
            }
        }
        return false;
    }


    /**
     * Comprueba si una consulta esta vacía
     * @param n - numero de la consulta a comprobar
     * @return boolean - true, si la consulta esta vacía, false si está ocupada
     * */
    protected static boolean checkConsulta(int n, Centro c){
        try {
            if (c instanceof Hospital) {
                Hospital h = (Hospital) c;
                if (h.consultas[n - 1] == null) {
                    return true;
                }
            } else {
                Clinica cl = (Clinica) c;
                if (cl.consultas[n - 1] == null) {
                    return true;
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Error. La consulta introducida no existe.");
        }
        return false;
    }


    /**
     * Comprueba si una habitacion esta vacía
     * @param planta
     * @param habitacion
     * @return boolean - true, si la habitacion esta vacía, false si está ocupada
     * */
    protected static boolean checkHabitacion(int planta, int habitacion, Hospital h){

        if(h.habitaciones[planta][habitacion]==null) {
            return true;
        }
        return false;
    }


    /**
     * Comprueba si un identificador de Persona ya existe
     * @param id - identificador de Persona a comprobar
     * @return boolean - true, si coincide con alguno, false si no existe
     * */
    protected boolean checkPersonID(int id){
        //Lo comprueba en todos los arrays porque al no existir aun el objeto no se sabe de qué tipo es
        for(Centro c: centrosMedicos){
            if(c!=null){
                for(Persona p: c.trabajadores){
                    if(p!=null && p.getID()==id){
                        return true;
                    }
                }
                for(Persona p: c.consultas){
                    if(p!=null && p.getID()==id){
                        return true;
                    }
                }
                if(c instanceof Hospital) {
                    Hospital h=(Hospital) c;
                    for (int i=0;i<h.habitaciones.length;i++) {
                        for(int x=0;x<h.habitaciones[i].length;x++) {
                            if (h.habitaciones[i][x] != null && h.habitaciones[i][x].getID()==id) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    /**
     * Funcion que crea un nuevo objeto Centro segun sea hospital o clinica
     * @param hospital true, Si el objeto se quiere crear de tipo hospital
     * @param clinica true, si es de tipo clinica
     * @param init Si la aplicacion se inicia por primera vez
     * */
    protected void nuevoCentro(boolean hospital, boolean clinica, boolean init){

        if(hospital){
            //Si el programa inicia por primera vez se crean 2 Hospitales con 2 Medicos y 2 Admins
            if(init){
                Hospital h;
                for(int i=0;i<2;i++) {
                    int id;
                    String n=Faker.devolverHospitales();
                    String dir="C/Por defecto, 0";
                    do {
                        id = Faker.devolverEnteros(false, centrosMedicos.length);
                    }while(checkCenterID(id));
                    int consultas=Faker.devolverEnteros(false,15);
                    int plantas=Faker.devolverEnteros(false,10);
                    int hab=Faker.devolverEnteros(false,20);

                     h = new Hospital(n, dir, id, plantas, hab, consultas); // 1 hospital por vuelta
                    //Una vez creado el objeto se guarda en una posicion vacía del array centrosMedicos[]
                    centrosMedicos[Centro.contCentros-1]=h;

                    for(int x=0;x<2;x++) { // 2 médicos por vuelta
                        h.addMedico((Medico) nuevaPersona(true, 2));
                    }
                    for(int y=0;y<2;y++) { // 2 Admins por vuelta
                        h.addAdmin((Administrativo) nuevaPersona(true, 3));
                    }
                }

            }else{ //Si el programa ya se había ejecutado antes, solo se creara un hospital cada vez que se llame a esta funcion
                int idCentro=pedirIDcentro(true, 0);
                String n=PeticionDatos.pedirCadena("> nombre: ");
                String dir=PeticionDatos.pedirCadena("> dirección: ");
                int consultas=PeticionDatos.pedirEnteroPositivo(false, "> consultas: ");
                int plantas=PeticionDatos.pedirEnteroPositivo(false, "> plantas: ");
                int hab=PeticionDatos.pedirEnteroPositivo(false, "> habitaciones: ");

                Hospital h=new Hospital(n,dir,idCentro,plantas,hab,consultas);
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
                        id=Faker.devolverEnteros(false, centrosMedicos.length);
                    }while(checkCenterID(id));
                    int consultas=Faker.devolverEnteros(false,15);

                    cl=new Clinica(n,dir,id,consultas);
                    centrosMedicos[Centro.contCentros-1]=cl;

                    for(int x=0;x<2;x++) { // 2 médicos por vuelta
                        nuevaPersona(true, 2);
                    }
                    for(int y=0;y<2;y++) { // 2 Admins por vuelta
                        nuevaPersona(true, 3);
                    }
                }
            }else{
                int idCentro=pedirIDcentro(true, 2);
                String n=PeticionDatos.pedirCadena("> nombre: ");
                String dir=PeticionDatos.pedirCadena("> dirección: ");
                int consultas=PeticionDatos.pedirEnteroPositivo(false, "> consultas: ");

                Clinica c=new Clinica(n,dir,idCentro,consultas);
                centrosMedicos[Centro.contCentros-1]=c;
            }
        }
    }


    /**
     * Esta funcion comprueba que no se cree una persona nueva con un dni que ya exista en el contexto global del programa
     * @param dni a comprobar
     * @return boolean - true, si encuentra un dni que coincida, false si no encuentra nada.
     * */
    protected boolean checkDNI(String dni){
        for(Centro c: centrosMedicos){ //Busca en todos los centros
            if(c!=null) {
                for (Persona p : c.trabajadores) { //En todos los trabajadores de cada centro
                    if (p != null && p.getDni().equals(dni)) { //Comprueba que el dni de cada trabajador de cada centro sea el mismo que el parametro
                        return true;
                    }
                }
                for (Paciente p : c.consultas) {
                    if (p != null && p.getDni().equals(dni)) {
                        return true;
                    }
                }
                if(c instanceof Hospital) {
                    Hospital h=(Hospital) c;
                    for (int i=0;i<h.habitaciones.length;i++) {
                        for(int x=0;x<h.habitaciones[i].length;x++) {
                            if (h.habitaciones[i][x] != null && h.habitaciones[i][x].getDni().equals(dni)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    //Los trabajadores pueden tener el mismo dni que un paciente, pero entre trabajadores no se puede repetir.


    /**
     * Esta función va a crear objetos aleatorios por defecto para que la aplicación funcione
     * */
    protected void estadoPorDefecto(){
        //Hospitales
        nuevoCentro(true,false, true);

        //Clínicas
        nuevoCentro(false,true, true);
    }


    /**
     * Esta funcion va a leer los datos que existan en los archivos y los va a cargar en el programa
     * */
    protected static void cargarConfig(){

    }


    /**
     * Función que crea los archivos necesarios para guardar el estado de la aplicación en el momento de salir.
     * @param objPath ruta del archivo para los objetos.
     * @param staticPath ruta del archivo para los atributos de clase.
     * @return boolean - true si se ha guardado bie, false si no.
     * */
    protected boolean guardarEstado(String objPath, String staticPath){
        try{
            //GUARDAR OBJETO
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(objPath));
            //Si el array centrosMedicos contiene realmente todos los datos del programa pues únicamente se serializa el array.
            oos.writeObject(centrosMedicos);
            oos.close();
            //GUARDAR ATRIBUTOS STATIC
            //La chapuza que se me ha ocurrido es meter estos atributos en un array de enteros(menos mal que son todos el mismo tipo de dato)
            int estaticos[]={Centro.contCentros, Centro.contMedicos, Centro.contAdministrativos, Centro.totalTrabajadores};
            //Ahora este array lo guardo en un archivo tal cual está.
            //Tengo que usar BufferedWriter para tener acceso al metodo newLine y que se guarde cada valor en una linea.
            BufferedWriter file=new BufferedWriter(new FileWriter(staticPath));
            for(int i: estaticos) {
                file.write(Integer.toString(i));
                file.newLine();
            }
            file.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(ANSI_RED + "Ha ocurrido un error mientras se guardaba." + ANSI_RESET);
            return false;
        }
    }


    /**
     * Recupera el estado del programa que había sido guardado en la anterior ejecución.
     * @param objPath ruta del archivo para los objetos.
     * @param staticPath ruta del archivo para los atributos de clase.
     * @return boolean - true, si se ha leido correctamente, false si no.
     * */
    protected boolean recuperarEstado(String objPath, String staticPath){
        Centro anteriorEstado[];
        int estaticos[]={Centro.contCentros, Centro.contMedicos, Centro.contAdministrativos, Centro.totalTrabajadores};

        try{
            //SE CARGA EL OBJETO
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(objPath));
            anteriorEstado=(Centro []) ois.readObject();
            ois.close(); //Se cierra la lectura del archivo app.ser
            //Una vez leido el objeto guardado se apunta el espacio de memoria de centrosMedicos al array anteriorEstado que tiene los datos anteriores.
            centrosMedicos=anteriorEstado;

            //SE CARGAN LOS ATRIBUTOS ESTATICOS
            FileReader file=new FileReader(staticPath);
            Scanner lector=new Scanner(file); //Se le pasa a Scanner el archivo en vez de System.in
            int cont=0;
            while (lector.hasNextInt()) { //Mientras se lea un entero
                cont++; //va a incrementar el contador
                estaticos[cont-1]=lector.nextInt(); //para almacenar en esa posicion lo que hay leído Scanner del archivo
            }
            lector.close(); //Se cierra la lectura del archivo static.ser

            //Les asigno a cada atributo el valor que tenían.
            Centro.contCentros=estaticos[0];
            Centro.contMedicos=estaticos[1];
            Centro.contAdministrativos=estaticos[2];
            Centro.totalTrabajadores=estaticos[3];

            return true; //Se ha leido/recuperado el objeto correctamente

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false; //Ha ocurrido algo
        }
    }


    /**
     * Guarda en un archivo la persona antes de ser despedida, de este archivo no se va a leer más.
     * @param worker persona de tipo medico/admin a guardar.
     * @return boolean - true, si se ha guardado correctamente, false si algo ha fallado.
     * */
    protected static boolean saveFired(Persona worker){
        String path="datos/personas/fired.ser"; //La ruta no la paso por parámetro porque no va a cambiar y solo se usa en esta funcion
        try{
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(worker); //Con el objeto oos llamo a la funcion de escribir y le paso por parámetro el parámetro de esta funcion.
            oos.close();
            return true;
        } catch (IOException e) {
//            e.printStackTrace(); //Aqui me saltaba una excepcion porque el atributo fechaNac es un objeto Fecha y esa clase NO es serializable...
            System.out.println(ANSI_RED + "Ha ocurrido un error mientras se guardaba." + ANSI_RESET);
            return false;
        }
    }


    /**
     * Funcion para aumentar el tamaño de un array fijo que se ha llenado
     * */
    protected void aumentarCentrosMedicos(){
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
     * Función de ordenación de centros por su ID. SHELL
     * @param v vector de objetos
     */
    protected static void ordenarPorIDcentro(Centro v[]) { //esta función ordenará por el dorsal
        try{
            int d, i, cont = 0; //inicializo mi variable contador a 0
            Centro ele;
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
                        if (v[i].getID() > (v[i + d].getID())) {
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
            System.out.println(ANSI_RED+"Error al intentar ordenar los centros."+ANSI_RESET);
        }
    }


    /**
     * Función de ordenación de personas por su ID. SHELL
     * @param v vector de objetos
     */
    public static void ordenarPorIDpersona(Persona v[]) { //esta función ordenará por el dorsal
        try {
            int d, i, cont = 0; //inicializo mi variable contador a 0
            Persona ele;
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
                        if (v[i].getID() > (v[i + d].getID())) {
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
            System.out.println(ANSI_RED+"Error al intentar ordenar las personas."+ANSI_RESET);
        }
    }


    /**
     * Funcion de inicio del programa que hace las comprobaciones iniciales y llama al menu principal
     * */
    protected void init(){
//        app=new GestionMedica();
        if(!checkConfig()){ //se llama a la funcion checkConfig para comprobar si existe o no algún archivo previo.
            estadoPorDefecto();
        }else{
            recuperarEstado("datos/app.ser", "datos/static.ser");
        }
        menuInicio();
    }

//Este ya no sirve, esta clase es como las demás tiene sus objetos.
//    public static void main(String[] args) {
//        init();
//    }
}
