package interfaz;

import javax.swing.*;
import java.awt.*;

/**
 * Clase que crea un panel-formulario para pedir un DNI
 * */
public class pedirDNI extends JPanel {
    protected GridBagConstraints c;
    protected JTextField campos[];
    protected JLabel titulos[];
    protected JLabel t1, t2;

    public pedirDNI(int nCampos, int anchoCampos,  Color bg){
        //LAYOUT
        GridBagLayout layout=new GridBagLayout();
        c = new GridBagConstraints();
//        GridLayout layout=new GridLayout(nCampos, 2);
//        layout.setVgap(30);
        setLayout(layout);
        setBackground(bg);

        //COMPONENTES
        campos=new JTextField[nCampos];
        titulos=new JLabel[nCampos];

//        c.weightx = 1;
//        c.weighty = 10;

        c.gridy=0;
        c.gridx=0;
        c.anchor=GridBagConstraints.WEST;
//        c.gridwidth=2;
        c.insets= new Insets(0,0,70,0);

        t1=new JLabel();
        add(t1, c);

        c.gridy=1;
        c.gridx=0;
        c.anchor=GridBagConstraints.WEST;
//        c.gridwidth=1;
        c.insets= new Insets(0,0,30,5);

        for(int i=0;i<titulos.length;i++){
            JLabel titulo= new JLabel();
            titulos[i]=titulo;
            add(titulos[i], c);
            titulos[i].setFont(new Font("Tahoma", 0, 18));
            titulos[i].setForeground(Color.decode("#A5BABA"));
            c.gridy++;
        }

        c.gridx=0;
        c.gridy=1;
        c.anchor=GridBagConstraints.WEST;
//        c.gridwidth=1;
        c.insets= new Insets(0,50,30,0);

        for(int i=0;i<campos.length;i++){
            JTextField campo= new JTextField( anchoCampos);
            campos[i]=campo;
            add(campos[i], c);
            c.gridy++;
        }

        c.gridx=0;
        c.gridy+=1;
        c.anchor=GridBagConstraints.WEST;
//        c.gridwidth=2;
        c.insets= new Insets(20,0,0,0);

        t2=new JLabel();
        add(t2, c);
    }

}
