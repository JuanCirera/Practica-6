import utilidades.Fecha;
/**
 * Clase que gestiona la información de una persona tipo médico, con información específica.
 * */
public class Medico extends Persona{
    //ATRIBUTOS
    String especialidad;
    utilidades.Fecha  diasTrabajados[];

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

    private boolean validarEspecialidad(String especialidad){
        return false;
    }

    //TODO: validar fechaNac > 22 and < 70

}
