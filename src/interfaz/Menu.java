package interfaz;

import javax.swing.*;
import java.awt.*;

/**
 * Clase que crea un panel-menu con JButtons
 * */
public class Menu extends javax.swing.JPanel{

//    private JButton opcion;
//    GridBagConstraints c = new GridBagConstraints();
    protected JButton opciones[]; //Array donde se almacenan objetos JButton con sus propiedades


    public Menu(int nOpciones, Color bg){
        //Layout
        GridLayout layout=new GridLayout(nOpciones, 0);
        layout.setVgap(20);
        this.setLayout(layout);
//        this.setSize(100, 500);
        this.setBackground(bg);

        //COMPONENTES
        opciones=new JButton[nOpciones];

        for(int i=0;i<opciones.length;i++){
            JButton opcion= new JButton();
            opciones[i]=opcion;
            add(opciones[i]);
        }

    }


//    private void iniciarComponentes() {
//
//        opciones[0].setFont(new Font("Verdana", 1, 18));
//        opciones[0].setForeground(Color.decode("#FFFFFF"));
//        opciones[0].setBackground(Color.decode("#5C5C5C"));
//        opciones[0].addActionListener(evt->pulsado()); //TODO: llamar a la funcion que creara otro objeto menu con distintos atributos
////        opcion1.addMouseListener(this);
////        opcion1.setBorder(BorderFactory.createEmptyBorder(10, 55, 10, 55));
//        opciones[0].setSize(70, 30);
//        opciones[0].setText("Gestionar Hospital");
//
//        opciones[1].setFont(new Font("Verdana", 1, 18));
//        opciones[1].setForeground(Color.decode("#FFFFFF"));
//        opciones[1].setBackground(Color.decode("#5C5C5C"));
////        opcion2.addActionListener(evt->pulsado(f,c));
////        opcion2.addMouseListener(this);
//        opciones[1].setText("Gestionar Clínica");
//
//        opciones[2].setFont(new Font("Verdana", 1, 18));
//        opciones[2].setForeground(Color.decode("#FFFFFF"));
//        opciones[2].setBackground(Color.decode("#5C5C5C"));
////        opcion3.addActionListener(evt->pulsado(f,c));
////        opcion3.addMouseListener(this);
//        opciones[2].setText("Gestionar Personal");
//
//        opciones[3].setFont(new Font("Verdana", 1, 18));
//        opciones[3].setForeground(Color.decode("#FFFFFF"));
//        opciones[3].setBackground(Color.decode("#5C5C5C"));
////        opcion4.addActionListener(evt->pulsado(f,c));
////        opcion4.addMouseListener(this);
//        opciones[3].setText("Gestionar Paciente");
//
//        opciones[4].setFont(new Font("Verdana", 1, 18));
//        opciones[4].setForeground(Color.decode("#FFFFFF"));
//        opciones[4].setBackground(Color.decode("#5C5C5C"));
////        opcion5.addActionListener(evt->pulsado(f,c));
////        opcion5.addMouseListener(this);
//        opciones[4].setText("Estadísticas de los centros");
//
//        opciones[5].setFont(new Font("Verdana", 1, 18));
//        opciones[5].setForeground(Color.decode("#FFFFFF"));
//        opciones[5].setBackground(Color.decode("#5C5C5C"));
////        opcion6.addActionListener(evt->pulsado(f,c));
////        opcion6.addMouseListener(this);
//        opciones[5].setText("Estadísticas de las personas");
//    }

}
