/**
 * Clase que gestiona la información de una persona tipo paciente, con información específica.
 * */
public class Paciente extends Persona{
    //ATRIBUTOS
    utilidades.Fecha visitasMedicas[];

    //FUNCIONES-METODOS

    @Override
    public int diasPorMes(int month) {
        return super.diasPorMes(month);
    }

    @Override
    public void mostrarEstado() {
        super.mostrarEstado();
    }

    private boolean addVisita(utilidades.Fecha visita){
        return false;
    }

}
