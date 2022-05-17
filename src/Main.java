
import interfaz.VentanaPrincipal;
import logica.GestionMedica;

/**
 * Clase principal del programa desde donde se va a ejecutar en un futuro la interfaz gráfica
 * */
public class Main {
    public static void main(String[] args) {
        GestionMedica app=new GestionMedica();
        app.init();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal(app);
            } //Le paso por parámetro el objeto GestionMédica para poder acceder a él desde la interfaz
        });
    }
}
