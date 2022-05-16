package interfaz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DataPanel extends JPanel {

    protected JTextArea texto;
    protected JTable tabla;
    protected DefaultTableModel model;
    protected JLabel t1;
    protected JRadioButton encontradas[];
    protected JScrollPane scroll;
    protected GridBagConstraints c;


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
     * Constructor que solo muestra texto en un JTable
     * */
    public DataPanel(Color bg, String columnNames[]){
//        GridLayout layout=new GridLayout();
        FlowLayout layout=new FlowLayout();
        setLayout(layout);
        setBackground(bg);

        //COMPONENTES
//        String[] columnNames = { "ID", "DNI/NIE", "Nombre", "Especialidad/Área"}; //TODO

        scroll=new JScrollPane();
        add(scroll);

        model = new DefaultTableModel(null, columnNames);
        tabla=new JTable(model);
        scroll.setViewportView(tabla);
    }


    /**
     * Constructor para las Stats de personas
     * */
    public DataPanel(String p[], Color bg){
        int cont=0;
        encontradas=new JRadioButton[p.length];
        t1=new JLabel();

        GridBagLayout layout=new GridBagLayout();
        c = new GridBagConstraints();
        setLayout(layout);
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
        c.insets= new Insets(0,180,10,0);

        for(String person: p){
            cont++;
            JRadioButton r=new JRadioButton("ID "+cont+" "+person); //TODO:Aqui habria un getID
            encontradas[cont-1]=r;
            group3.add(r);
            add(r, c);
            c.gridy++;
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
