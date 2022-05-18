package interfaz;

import logica.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Clase que crea un panel-datos que segun el constructor muestra texto plano, una tabla o un JList
 * */
public class DataPanel extends JPanel {

    protected JTextArea texto;
    protected JTable tabla;
    protected DefaultTableModel model;
    protected JLabel t1;
    protected JRadioButton encontradas[];
    protected JScrollPane scroll;
    protected GridBagConstraints c;
    protected JPanel panelRadio;
    protected JList<String> estadoConsultas;


    /**
     * Panel por defecto con un JTextArea
     * */
    public DataPanel(Color bg){
        FlowLayout layout=new FlowLayout();
        setLayout(layout);
        setBackground(bg);

        //COMPONENTES
        texto=new JTextArea(20,35);
        texto.setEditable(false);
        add(texto);
    }


    /**
     * Panel por defecto con un JList
     * */
    public DataPanel(Color bg, boolean temp, String consultas[]){
//        FlowLayout layout=new FlowLayout();
        GridBagLayout layout=new GridBagLayout();
        setLayout(layout);
        setBackground(bg);
        c = new GridBagConstraints();

        //COMPONENTES
        c.gridx = 0;
        c.gridy = 0;
//        c.anchor=GridBagConstraints.WEST;
        c.insets= new Insets(15,0,10,0);
        t1=new JLabel();
        add(t1, c);

        c.gridx = 0;
        c.gridy = 1;
//        c.anchor=GridBagConstraints.WEST;
        c.insets= new Insets(0,0,10,0);
        estadoConsultas=new JList<>(consultas);
        add(estadoConsultas, c);
        scroll=new JScrollPane(estadoConsultas);
        add(scroll, c);
    }


    /**
     * Constructor que solo muestra texto en un JTable
     * */
    public DataPanel(Color bg, String columnNames[]){
//        GridLayout layout=new GridLayout();
        FlowLayout layout=new FlowLayout();
        setLayout(layout);
        setBackground(bg);

        //COMPONENTES
        scroll=new JScrollPane();
        add(scroll);

        model = new DefaultTableModel(null, columnNames);
        tabla=new JTable(model);
        scroll.setViewportView(tabla);
    }


    /**
     * Constructor para las Stats de personas
     * */
    public DataPanel(Persona p[], int tipo, Color bg){
        int cont=0;
        encontradas=new JRadioButton[p.length];
        t1=new JLabel();

        panelRadio=new JPanel();
        GridBagLayout layout=new GridBagLayout();
        c = new GridBagConstraints();
        setLayout(layout);
        panelRadio.setLayout(layout);
        panelRadio.setBackground(bg);
        setBackground(bg);

        ButtonGroup group3= new ButtonGroup();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor=GridBagConstraints.WEST;
        c.insets= new Insets(0,0,20,0);
        add(t1, c);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor=GridBagConstraints.WEST;
        c.insets= new Insets(0,120,10,0);
        scroll=new JScrollPane(panelRadio);
        add(scroll, c);

        c.insets= new Insets(0,0,0,0);
        JRadioButton r=null;
        for(Persona person: p){
            if(person!=null && tipo==2 && person instanceof Medico) {
                Medico m=(Medico) person;
                cont++;
                r = new JRadioButton("ID " + m.getID() + " " + m.getNombre()+", "+m.getApellido1()+" "+m.getApellido2());
                encontradas[cont - 1] = r;
                group3.add(r);
                panelRadio.add(r, c);
                c.gridy++;
            }else if(person!=null && tipo==3 && person instanceof Administrativo){
                Administrativo a=(Administrativo) person;
                cont++;
                r = new JRadioButton("ID " + a.getID() + " " + a.getNombre()+", "+a.getApellido1()+" "+a.getApellido2());
                encontradas[cont - 1] = r;
                group3.add(r);
                panelRadio.add(r, c);
                c.gridy++;
            }
        }

//        c.gridx = 0;
        c.gridy +=1;
        c.insets= new Insets(30,0,0,0);
        texto=new JTextArea(10,35);
//        for(String cadena: p) {
//            texto.append(cadena+"\n");
//        }
        texto.setEditable(false);
        add(texto, c);
//        texto.setMargin(new Insets(10, 10, 10, 10));
    }


    /**
     * Constructor para las Stats de pacientes
     * */
    public DataPanel(ArrayList<Paciente> p, Color bg){
        int cont=0;
        encontradas=new JRadioButton[p.size()];
        t1=new JLabel();

        panelRadio=new JPanel();
        GridBagLayout layout=new GridBagLayout();
        c = new GridBagConstraints();
        setLayout(layout);
        panelRadio.setLayout(layout);
        panelRadio.setBackground(bg);
        setBackground(bg);

        ButtonGroup group3= new ButtonGroup();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor=GridBagConstraints.WEST;
        c.insets= new Insets(0,0,20,0);
        add(t1, c);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor=GridBagConstraints.WEST;
        c.insets= new Insets(0,120,10,0);
        scroll=new JScrollPane(panelRadio);
        add(scroll, c);

        c.insets= new Insets(0,0,0,0);
        JRadioButton r=null;
        for(Paciente patient: p){
            if(patient!=null) {
                cont++;
                r = new JRadioButton("ID " + patient.getID() + " " + patient.getNombre()+", "+patient.getApellido1()+" "+patient.getApellido2());
                encontradas[cont - 1] = r;
                group3.add(r);
                panelRadio.add(r, c);
                c.gridy++;
            }
        }

//        c.gridx = 0;
        c.gridy +=1;
        c.insets= new Insets(30,0,0,0);
        texto=new JTextArea(10,35);
//        for(String cadena: p) {
//            texto.append(cadena+"\n");
//        }
        texto.setEditable(false);
        add(texto, c);
//        texto.setMargin(new Insets(10, 10, 10, 10));
    }

}
