package interfaz;

import logica.Centro;
import logica.Clinica;
import logica.Hospital;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GestionCentro extends JPanel{

    protected GridBagConstraints c;
    protected JButton opciones[]; //Array donde se almacenan objetos JButton con sus propiedades
    protected JRadioButton radioButtons[]; //Decido guardar los componentes en arrays normales para que no se sobrepase nunca el límite.
    protected JScrollPane scroll;
    protected JPanel panelRadio;

    public GestionCentro(int tipo, int nOpciones, Centro centros[], Color bg){
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
            if(tipo==1 && centros[i]!=null && centros[i] instanceof Hospital) {
                Hospital h=(Hospital) centros[i];
                JRadioButton rh = new JRadioButton("ID " + h.getID() + " " + h.getNombre());
                radioButtons[i]=rh;
                group.add(radioButtons[i]);
                panelRadio.add(radioButtons[i], c);
            }else if(tipo==2 && centros[i]!=null && centros[i] instanceof Clinica){
                Clinica cl=(Clinica) centros[i];
                JRadioButton rcl = new JRadioButton("ID " + cl.getID() + " " + cl.getNombre());
                radioButtons[i]=rcl;
                group.add(radioButtons[i]);
                panelRadio.add(radioButtons[i], c);
            }

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
