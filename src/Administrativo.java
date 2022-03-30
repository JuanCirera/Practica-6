import utilidades.Fecha;
/**
 * Clase que gestiona la información de una persona tipo administrativo, con información específica.
 * */
public class Administrativo extends Persona{
    //ATRIBUTOS
    String area;
    Fecha diasTrabajados[];

    //FUNCIONES-METODOS

    @Override
    public int diasPorMes(int month) {
        return super.diasPorMes(month);
    }

    @Override
    public void mostrarEstado() {
        super.mostrarEstado();
    }

    private boolean addDiaTrabajado(Fecha dt){
        return false;
    }

    private boolean validarArea(String area){
        return false;
    }

    //TODO: validar fechaNac > 18 and < 65

}
