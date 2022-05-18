package interfaz;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Clase que crea un panel-sur
 * */
public class Bottom extends javax.swing.JPanel{

    protected JButton opciones[]; //Array donde se almacenan objetos JButton con sus propiedades
    protected JLabel text;


    public Bottom(int nOpciones, Color bg){
        this.setBackground(bg);
        this.setLayout(new FlowLayout(FlowLayout.RIGHT));

        //COMPONENTES
        text=new JLabel();
        this.add(text);
        text.setVisible(false);
        opciones=new JButton[nOpciones];

        for(int i=0;i<opciones.length;i++){
            JButton opcion= new JButton();
            opciones[i]=opcion;
            add(opciones[i]);
        }
    }

}
