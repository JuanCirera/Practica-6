import utilidades.Fecha;

import java.util.Calendar;

/**
 * Clase que gestiona la información de una persona tipo administrativo, con información específica.
 * */
public class Administrativo extends Persona{
    //ATRIBUTOS
    String area;
    Fecha diasTrabajados[];

    //CONSTRUCTOR
    //Inicializa el array con 5 posiciones
    public Administrativo(String dni, int identificador, String nombre, String apellido1, String apellido2, String sexo, Fecha fechaNac, String area){
        super(dni, identificador, nombre, apellido1, apellido2, sexo, fechaNac);
        this.area=area;
        diasTrabajados=new Fecha[5];
    }

    //FUNCIONES-METODOS

    @Override
    /**
     * Devuelve el número de dias trabajados en un mes introducido
     * @param mes del que sacar el número de dias
     * @reurn cont - total de trabajados
     * */
    public int diasPorMes(int mes) {
        int cont=0;
        for (Fecha i:diasTrabajados){
            if(i!=null && i.getMonth()==mes){
                cont++;
            }
        }
        return cont;
    }

    @Override
    public void mostrarEstado() {
        System.out.println(dni+", "+nombre+" "+apellido1+" "+apellido2);
    }


    /**
     * Funcion que añade un dia de trabajo a un administrativo
     * @param dt - objeto de tipo Fecha con el dia trabajado
     * @return boolean - true si la fecha no existe y es válida (lunes-viernes), false si la fecha existe ya o es inválida (sábado-domingo)
     * */
    protected boolean addDiaTrabajado(Fecha dt){
        int cont=0;
        if((dt.getYear() - fechaNac.getYear()) > 22 && (dt.getYear() - fechaNac.getYear()) < 70){
            for(Fecha i:diasTrabajados){
                if(i==null){ //Si la posicion es nula va a comprobar que el día sea laborable, por ejemplo que no sea domingo ni sabado.
                    if(!dt.isWeekend()) {
                        cont++;
                        diasTrabajados[cont - 1] = dt;
                        return true;
                    }
                }else if(i==dt){
                    return false;
                }
            }
        }
        return false;
    }


    public static boolean validarArea(String area){
        area.toLowerCase();
        if(area.equals("traumatología") || area.equals("dermatología") || area.equals("oftalmología") || area.equals("neurología") || area.equals("urgencias") || area.equals("quirofano") || area.equals("administracion")) {
            return true;
        }else {
            return false;
        }
    }


    @Override
    public boolean validarFechaNac(Fecha fechaNac) {
        Calendar calendar = Calendar.getInstance();
        int actual=calendar.get(Calendar.YEAR);
        //Se llama al atributo año del objeto fecha que se haya creado y se hace la comprobacion
        if(actual - fechaNac.getYear()>18 && actual - fechaNac.getYear()<65){
            return true;
        }else {
            return false;
        }
    }
}
