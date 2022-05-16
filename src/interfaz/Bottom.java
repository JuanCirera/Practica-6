package interfaz;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Bottom extends javax.swing.JPanel{

    protected JButton opciones[]; //Array donde se almacenan objetos JButton con sus propiedades


    public Bottom(JLabel text, int nOpciones, Color bg){
        this.setBackground(bg);
        this.setLayout(new FlowLayout(FlowLayout.RIGHT));

        //COMPONENTES
        this.add(text);
        opciones=new JButton[nOpciones];

        for(int i=0;i<opciones.length;i++){
            JButton opcion= new JButton();
            opciones[i]=opcion;
            add(opciones[i]);
        }
    }

    public Bottom(int nOpciones, Color bg){
        this.setBackground(bg);
        this.setLayout(new FlowLayout(FlowLayout.RIGHT));

        //COMPONENTES
        opciones=new JButton[nOpciones];

        for(int i=0;i<opciones.length;i++){
            JButton opcion= new JButton();
            opciones[i]=opcion;
            add(opciones[i]);
        }
    }

//    protected void setStyle(String texto, String bgColor){ //TODO:Que hacer con la opcion de aceptar
//        opciones[0].setFont(new Font("Verdana", 1, 16));
//        opciones[0].setForeground(Color.decode("#FFFFFF"));
//        opciones[0].setBackground(Color.decode(bgColor));
//        opciones[0].setText(texto);
//        opciones[0].addActionListener(evt->VentanaPrincipal.salir());
//    }

}
