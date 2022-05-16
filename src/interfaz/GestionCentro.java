package interfaz;

import javax.swing.*;
import java.awt.*;

public class GestionCentro extends JPanel{

    protected GridBagConstraints c;
    protected JButton opciones[]; //Array donde se almacenan objetos JButton con sus propiedades
    protected JRadioButton radioButtons[];
    protected JScrollPane scroll;
    protected JPanel panelRadio;

    public GestionCentro(int nOpciones, String centros[], Color bg){
        //Layout
        panelRadio=new JPanel();
        GridBagLayout layout=new GridBagLayout();
        c= new GridBagConstraints();
        this.setLayout(layout);
        panelRadio.setLayout(layout);
//        this.setSize(100, 500);
        this.setBackground(bg);
        panelRadio.setBackground(bg);

        //COMPONENTES
        opciones=new JButton[nOpciones];
        radioButtons=new JRadioButton[centros.length];

        ButtonGroup group= new ButtonGroup();

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth=4;
//        c.insets= new Insets(0,0,10,0);

        scroll=new JScrollPane(panelRadio);
        add(scroll, c);

//        c.gridx = 0;
//        c.gridy = 0;
        c.gridwidth=1;
        c.anchor=GridBagConstraints.WEST;
//        c.insets= new Insets(0,0,10,0);

        for(int i=0;i<centros.length;i++){
            JRadioButton r=new JRadioButton(centros[i]);
            radioButtons[i]=r;
            group.add(radioButtons[i]);
            panelRadio.add(radioButtons[i], c);
            c.gridy++;
        }

        c.gridx=0;
        c.gridy++;
        c.gridwidth=1;
        c.insets= new Insets(30,20,50,0);

        for(int i=0;i<opciones.length;i++){
            JButton opcion= new JButton();
            opciones[i]=opcion;
            add(opciones[i], c);
            c.gridx++;
        }

    }
}
