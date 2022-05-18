package interfaz;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InputForm extends JPanel {

    protected GridBagConstraints c;
    public JTextField campos[];
    protected JLabel titulos[];

    public InputForm(int nCampos, int anchoCampo, Color bg){
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
        c.anchor=GridBagConstraints.EAST;
        c.insets= new Insets(0,0,30,5);

        for(int i=0;i<titulos.length;i++){
            JLabel titulo= new JLabel();
            titulos[i]=titulo;
            add(titulos[i], c);
            titulos[i].setFont(new Font("Tahoma", 0, 18));
            titulos[i].setForeground(Color.decode("#A5BABA"));
            c.gridy++;
        }

        c.gridx=1;
        c.gridy=0;
        c.anchor=GridBagConstraints.WEST;
        c.insets= new Insets(0,0,30,0);

        for(int i=0;i<campos.length;i++){
            JTextField campo= new JTextField( anchoCampo);
            campos[i]=campo;
            add(campos[i], c);
            campos[i].setFont(new Font("Tahoma", 0, 18));
            campos[i].setBackground(Color.decode("#3C3C3C"));
            campos[i].setForeground(Color.decode("#FFFFFF"));
            campos[i].setCaretColor(Color.decode("#FFFFFF"));
            campos[i].setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.decode("#414141")));
            c.gridy++;
        }

    }
}
