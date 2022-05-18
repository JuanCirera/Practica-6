package interfaz;

import javax.swing.*;
import java.awt.*;

/**
 * Clase que crea un panel-central sobre el que van todos los demás paneles, su razón de existencia es porque mantiene centrados los
 * GridBagLayout, si no no haría falta.
 * */
public class Central extends javax.swing.JPanel{

    public Central(Color bg) {

        GridBagLayout layout = new GridBagLayout();

        this.setLayout(layout);
        this.setBackground(bg);
//        this.setBackground(Color.decode("#000000"));

    }
}
