package interfaz;

import javax.swing.*;
import javax.tools.JavaCompiler;
import java.awt.*;

public class Stats extends JPanel {

    protected GridBagConstraints c;
    protected JRadioButton centros[];
    protected JRadioButton personas[];
    protected String tipo[]={"Paciente", "Médico", "Administrativo"};
    protected JLabel t1, t2, t3;
    protected JComboBox desplegable;


    /**
     * Constructor para las ststs de centros
     * */
    public Stats(int nCentros, Color bg){ //TODO: esto es temporal por aqui entraria un arraylist

        //LAYOUT
        GridBagLayout layout=new GridBagLayout();
        c = new GridBagConstraints();
//        GridLayout layout=new GridLayout(nCampos, 2);
//        layout.setVgap(30);
        setLayout(layout);
        setBackground(bg);

        centros=new JRadioButton[nCentros];
        personas=new JRadioButton[3];

        ButtonGroup group= new ButtonGroup();
        ButtonGroup group2= new ButtonGroup();

        c.gridx = 0;
        c.gridy = 0;
        c.anchor=GridBagConstraints.EAST;
        c.insets= new Insets(0,0,30,0);

        t1=new JLabel();
        add(t1,c);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor=GridBagConstraints.EAST;
        c.gridwidth=2;
        c.insets= new Insets(0,0,30,0);

        desplegable=new JComboBox();
        add(desplegable,c);

        c.gridx = 0;
        c.gridy = 2;
        c.anchor=GridBagConstraints.WEST;
        c.gridwidth=1;
        c.insets= new Insets(0,0,0,0);

        t2=new JLabel();
        add(t2,c);

        c.gridx = 0;
        c.gridy = 3;
        c.anchor=GridBagConstraints.EAST;
        c.gridwidth=2;
        c.insets= new Insets(0,0,10,0);

        for(int i=0;i<centros.length;i++){
            JRadioButton r=new JRadioButton("ID "+i+" Pruebas");
            centros[i]=r;
            group.add(centros[i]);
            add(centros[i], c);
            c.gridy++;
        }
    }


    public Stats(int tiposPersona, String centro[], Color bg){
        //LAYOUT
        GridBagLayout layout=new GridBagLayout();
        c= new GridBagConstraints();
        setLayout(layout);
        setBackground(bg);

        centros=new JRadioButton[centro.length];
        personas=new JRadioButton[3];

        ButtonGroup group= new ButtonGroup();
        ButtonGroup group2= new ButtonGroup();

        c.gridx = 0;
        c.gridy = 0;
        c.anchor=GridBagConstraints.EAST;
        c.insets= new Insets(0,0,30,0);

        t1=new JLabel();
        add(t1,c);

        c.gridx = 1;
        c.anchor=GridBagConstraints.WEST;
        c.gridwidth=2;
        c.insets= new Insets(0,5,30,0);

        desplegable=new JComboBox();
        add(desplegable,c);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor=GridBagConstraints.EAST;
        c.gridwidth=1;
        c.insets= new Insets(0,0,30,0);

        t3=new JLabel();
        add(t3,c);

        c.gridx = 1;
        c.anchor=GridBagConstraints.WEST;
//        c.gridwidth=3;
        c.insets= new Insets(0,5,30,0);

        for(int i=0;i<personas.length;i++){
            JRadioButton r=new JRadioButton(tipo[i]);
            personas[i]=r;
            group2.add(personas[i]);
            add(personas[i], c);
            c.gridx++;
        }

        c.gridx = 0;
        c.gridy = 2;
        c.anchor=GridBagConstraints.EAST;
        c.gridwidth=1;
        c.insets= new Insets(0,0,30,0);

        t2=new JLabel();
        add(t2,c);

        c.gridx = 1;
        c.gridy = 2;
        c.anchor=GridBagConstraints.WEST;
        c.gridwidth=3;
        c.insets= new Insets(0,5,10,0);

        for(int i=0;i<centros.length;i++){
            JRadioButton r=new JRadioButton("ID "+i+" "+centro[i]);
            centros[i]=r;
            group.add(centros[i]);
            add(centros[i], c);
            c.gridy++;
        }
    }
}
