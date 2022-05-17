package interfaz;

import logica.*;
import utilidades.PeticionDatosSwing;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class VentanaPrincipal extends javax.swing.JFrame {

    //ATRIBUTOS
    private GestionMedica app;
    private GridBagConstraints c = new GridBagConstraints();
    private Central central;
    private Menu m;
    private JFrame frame;
    private JLabel titulo, version;
    private ArrayList<String> hospitales=new ArrayList<>();
    private ArrayList<String> clinicas=new ArrayList<>();
    private ArrayList<Paciente> pacientesHospital=new ArrayList<>();
    //Declaro este array como final para que no se pueda modificar.
    private final String MESES[]={"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto","Septiembre","Octubre", "Noviembre", "Diciembre"};

    //COLORES DE LOS PANELES
    //Con estos dos atributos puedo cambiar rápidamente los colores de todos los paneles y componentes.
    private Color bgColorCentral=Color.decode("#333333");
    private Color bgColorTop=Color.decode("#2B2B2B");
    private Color bgColorTextField=Color.decode("#3C3C3C");

    //PANELES
    private Top topType1;
    private Bottom bottomType1,bottomType2,bottomType3;

    ImageIcon img = new ImageIcon("src/img/p6-icon.png");


    public VentanaPrincipal(GestionMedica app) {

        this.app=app; //Inicializo el objeto GestionMédica con el que crea el MAIN del proyecto

        //FRAME
        frame = new JFrame("Gestión Médica");
        frame.setSize(700,650);
        frame.setLocation(550,100);
//        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() { //En caso de que se pulse el boton de cerrar de windows
            public void windowClosing(WindowEvent e) {
                salir();    //Se llama a mi funcion salir para evitar que se cierre sin guardar.
            }
        });
        frame.setVisible(true);
        frame.setIconImage(img.getImage());

        inicia();
    }


    private void inicia() {

        //TOP PANELS
        titulo=new JLabel();
        topType1=new Top(bgColorTop);
        topType1.setStyle("Gestión Médica", 40,"#FFFFFF", 0, 180);
        frame.add(topType1, BorderLayout.NORTH);
        //Dejo la posibilidad de que pueda haber mas de una instancia de este panel

        //CENTRAL PANEL
        central=new Central(bgColorCentral);
        c.gridx = 0;
        c.gridy = 0;
        m = new Menu(6, bgColorCentral);
        central.add(m, c);
        setMenuStyle(m);
        //COMPONENTES
        m.opciones[0].addActionListener(evt->gestionCentro(m,1, 4)); //TODO: llamar a la funcion que creara otro objeto menu con distintos atributos
        m.opciones[0].setText("Gestionar Hospital");

        m.opciones[1].addActionListener(evt->gestionCentro(m,2,4));
        m.opciones[1].setText("Gestionar Clínica");

        m.opciones[2].addActionListener(evt->gestionPersona(2));
        m.opciones[2].setText("Gestionar Personal");

        m.opciones[3].addActionListener(evt->gestionPersona(1));
        m.opciones[3].setText("Gestionar Paciente");

        m.opciones[4].addActionListener(evt->statsCentros(m));
        m.opciones[4].setText("Estadísticas de los centros");

        m.opciones[5].addActionListener(evt->statsPersonas(m));
        m.opciones[5].setText("Estadísticas de las personas");

        frame.add(central, BorderLayout.CENTER);

        //BOTTOM PANELS
        version=new JLabel();

        bottomType1=new Bottom(version, 1, bgColorCentral);
        frame.add(bottomType1, BorderLayout.SOUTH); //Este lo añado desde el principio porque aparece al arrancar
        setStyleBottomType1();
//        bottomType1.setVisible(true);

        bottomType2=new Bottom(2, bgColorCentral);
//        frame.add(bottomType2, BorderLayout.SOUTH);
        setStyleBottomType2();

        bottomType3=new Bottom(1, bgColorCentral);
//        frame.add(bottomType3, BorderLayout.SOUTH);
        setStyleBottomType3();
    }


    protected void setStyleBottomType1(){
        version.setText("Alfa 0.4 © Juan Fco Cirera "); //TODO: cambiar version en cada commit
        version.setFont(new Font("Calibri", Font.PLAIN, 12));
        version.setForeground(Color.decode("#555555"));
        version.setBorder(new EmptyBorder(0, 0, 0, 465)); //Chapucilla para que se separe este texto de los botones.


        for(JButton b: bottomType1.opciones){
            b.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(bgColorCentral, 1),
                    BorderFactory.createEmptyBorder(5, 7, 5, 7)));
            b.setFont(new Font("Verdana", 1, 16));
            b.setForeground(Color.decode("#FFFFFF"));

            b.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    b.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.decode("#6E6E6E"), 1),
                            BorderFactory.createEmptyBorder(5, 7, 5, 7)));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    b.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(bgColorCentral, 1),
                            BorderFactory.createEmptyBorder(5, 7, 5, 7)));
                }
            });
        }

        bottomType1.opciones[0].setBackground(Color.decode("#5C5C5C"));
        bottomType1.opciones[0].setText("Salir");
        //Este listener lo dejo porque el Type1 siempre va a hacer lo MISMO
        bottomType1.opciones[0].addActionListener(evt->VentanaPrincipal.salir());
    }


    protected void setStyleBottomType2(){

        for(JButton b: bottomType2.opciones){
            b.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(bgColorCentral, 1),
                    BorderFactory.createEmptyBorder(5, 7, 5, 7)));
            b.setForeground(Color.decode("#FFFFFF"));
            b.setFont(new Font("Verdana", 1, 16));

            b.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    b.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.decode("#6E6E6E"), 1),
                            BorderFactory.createEmptyBorder(5, 7, 5, 7)));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    b.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(bgColorCentral, 1),
                            BorderFactory.createEmptyBorder(5, 7, 5, 7)));
                }
            });
        }

        bottomType2.opciones[0].setBackground(Color.decode("#00B0F0"));
        bottomType2.opciones[0].setText("Aceptar");
//        bottomType1.opciones[0].addActionListener(evt->VentanaPrincipal.salir());

        bottomType2.opciones[1].setBackground(Color.decode("#5C5C5C"));
        bottomType2.opciones[1].setText("Volver");
//        bottomType1.opciones[1].addActionListener(evt->VentanaPrincipal.salir());
    }


    protected void setStyleBottomType3(){

        for(JButton b: bottomType3.opciones){
            b.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(bgColorCentral, 1),
                    BorderFactory.createEmptyBorder(5, 7, 5, 7)));
            b.setForeground(Color.decode("#FFFFFF"));
            b.setFont(new Font("Verdana", 1, 16));

            b.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    b.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.decode("#6E6E6E"), 1),
                            BorderFactory.createEmptyBorder(5, 7, 5, 7)));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    b.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(bgColorCentral, 1),
                            BorderFactory.createEmptyBorder(5, 7, 5, 7)));
                }
            });
        }

        bottomType3.opciones[0].setBackground(Color.decode("#5C5C5C"));
        bottomType3.opciones[0].setText("Volver");
//        bottomType1.opciones[0].addActionListener(evt->);

    }


    protected void setStyleJList(JList<String> list){

        list.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
        list.setBackground(Color.decode("#3C3C3C"));
        list.setForeground(Color.decode("#CDCDCD"));
        list.setFont(new Font("Verdana", 1, 16));
    }


    protected void setStyleJRadioButton(JRadioButton radio[]){
        for(JRadioButton r: radio){
            if(r!=null) {
                r.setBackground(bgColorCentral);
                r.setForeground(Color.decode("#CDCDCD"));
                r.setFont(new Font("Verdana", 1, 16));
            }
        }
    }


    protected void setMenuStyle(Menu m) {

        for(JButton b: m.opciones){
            b.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(bgColorCentral, 1),
                    BorderFactory.createEmptyBorder(5, 7, 5, 7)));

            b.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    b.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.decode("#6E6E6E"), 1),
                            BorderFactory.createEmptyBorder(5, 7, 5, 7)));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    b.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(bgColorCentral, 1),
                            BorderFactory.createEmptyBorder(5, 7, 5, 7)));
                }
            });

            b.setFont(new Font("Verdana", 1, 18));
            b.setForeground(Color.decode("#FFFFFF"));
            b.setBackground(Color.decode("#5C5C5C"));
        }

    }


    protected void gestionCentro(JPanel anterior, int tipo, int opciones){

        GestionCentro gc;

        app.mostrarCentros(tipo); //TODO
        gc=new GestionCentro(tipo, opciones, app.centrosMedicos, bgColorCentral); //Esto crea un nuevo panel al que se le pasa las opciones a mostrar
        if(tipo==1) {
            continuar(anterior, gc, "Inicio>Gestionar Hospital");
        }else{
            continuar(anterior, gc, "Inicio>Gestionar Clínica");
        }
        bottomType1.setVisible(false);
        rmActionListener(bottomType3);
        frame.add(bottomType3, BorderLayout.SOUTH);
        bottomType3.setVisible(true);

        //COMPONENTES
        setStyleScrollPane(gc.scroll);
        gc.scroll.setPreferredSize(new Dimension(420, 350));
        gc.scroll.setBorder(new EmptyBorder(new Insets(0,0,0,0)));

        for(JButton b: gc.opciones){
            b.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(bgColorCentral, 1),
                    BorderFactory.createEmptyBorder(5, 7, 5, 7)));

            b.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    b.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.decode("#6E6E6E"), 1),
                            BorderFactory.createEmptyBorder(5, 7, 5, 7)));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    b.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(bgColorCentral, 1),
                            BorderFactory.createEmptyBorder(5, 7, 5, 7)));
                }
            });

            b.setFont(new Font("Verdana", 1, 18));
            b.setForeground(Color.decode("#FFFFFF"));
            b.setBackground(Color.decode("#5C5C5C"));
        }

        gc.opciones[0].addActionListener(evt->mostrarDatos(tipo, gc));
//        opcion6.addMouseListener(this);
        gc.opciones[0].setText("Mostrar información");

        gc.opciones[1].addActionListener(evt->modificarCentro(tipo, gc));
//        opcion6.addMouseListener(this);
        gc.opciones[1].setText("Modificar");

        gc.opciones[2].addActionListener(evt-> nuevoCentroPanel(tipo, gc));
//        opcion6.addMouseListener(this);
        gc.opciones[2].setText("+ Nuevo");

        gc.opciones[3].addActionListener(evt-> confirmRmCentro(getSelectedCenter(gc), gc, anterior, gc));
//        opcion6.addMouseListener(this);
        gc.opciones[3].setText("- Eliminar");

        setStyleJRadioButton(gc.radioButtons);
//        gc.radioButtons[0].setSelected(true);

        bottomType3.opciones[0].addActionListener(evt->volver(gc, bottomType3));
    }


    protected void gestionPersona(int tipo){
        //TODO
        String trab[]={"1 12345678Z, Juan Francisco", "2 983465981F, Maria Jover", "3 096581291G, Rafael Cirera", "4 001746125A, Mariano Rajoy"};
        String pac[]={"David", "Pepe", "Antonio", "Francisco"};

        bottomType1.setVisible(false);
        frame.add(bottomType3, BorderLayout.SOUTH);
        bottomType3.setVisible(true);
        Menu gp=new Menu(2, bgColorCentral);;
//        continuar(m, gp, "Inicio>Gestionar Personal");
        setMenuStyle(gp);
        bottomType3.opciones[0].addActionListener(evt->volver(gp, bottomType3));

        if(tipo==2 || tipo==3){
            continuar(m, gp, "Inicio>Gestionar Personal");
            gp.opciones[0].setText("Mostrar Trabajadores");
            gp.opciones[0].addActionListener(evt->mostrarPersonas(tipo, gp, trab));
            gp.opciones[1].setText("Nuevo Trabajador");
            gp.opciones[1].addActionListener(evt->nuevaPersona(gp, tipo));

        }else{
            continuar(m, gp, "Inicio>Gestionar Paciente");
            gp.opciones[0].setText("Mostrar Pacientes");
            gp.opciones[0].addActionListener(evt->mostrarPersonas(tipo, gp, pac));
            gp.opciones[1].setText("Nuevo Paciente");
//            gp.opciones[1].addActionListener(evt->mostrarDatos(1, m));
        }
    }


    protected void nuevaPersona(JPanel anterior, int tipo){
        String type;
        if(tipo==1){
            type="paciente";
        }else {
            type="trabajador";
        }

        pedirDNI pDNI=new pedirDNI(1,  bgColorCentral);
        continuar(anterior, pDNI, "Inicio>Gestionar Personal>Crear "+type);
        bottomType3.setVisible(false);
        rmActionListener(bottomType2);
        frame.add(bottomType2, BorderLayout.SOUTH);
        bottomType2.setVisible(true);

        pDNI.t1.setText("Introduce el DNI del "+type+" que quieres crear/gestionar");
        pDNI.t1.setFont(new Font("Tahoma", 0, 16));
        pDNI.t1.setForeground(Color.decode("#CDCDCD"));
        pDNI.t1.setBackground(Color.decode("#5C5C5C"));

        pDNI.t2.setFont(new Font("Tahoma", 0, 16));
        pDNI.t2.setForeground(Color.ORANGE);
        pDNI.t2.setText("El DNI introducido no existe. ¿Desea crear un nuevo "+type+"?");
        pDNI.t2.setVisible(false);

        //Texto para los campos
        pDNI.titulos[0].setText("DNI:");

        for(JTextField campo: pDNI.campos){
            campo.setBackground(bgColorTextField);
            campo.setFont(new Font("Tahoma", 0, 18));
            campo.setForeground(Color.decode("#FFFFFF"));
            campo.setCaretColor(Color.decode("#FFFFFF"));
            campo.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.decode("#414141")));
        }

        pDNI.campos[0].setDocument(new JTextFieldLimit(9));
        pDNI.campos[0].getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            /**
             * Esta funcion comprueba si el DNI introducido es válido NO comprueba si está o no registrado
             * */
            public void warn() {
                String dni= pDNI.campos[0].getText();
                //Si el texto del JTextField es igual a este DNI temporal -> VERDE
                if(PeticionDatosSwing.checkNIF_NIE(dni)==0) {
                    pDNI.campos[0].setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.decode("#00B050")));
                //Si el texto del JTextField es DISTINTO y el JTextField NO está vacío -> ROJO
                }else if(PeticionDatosSwing.checkNIF_NIE(dni)!=0 &&  !dni.equals("")){
                    pDNI.campos[0].setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.decode("#C00000")));
                //En en caso de que este vacío vuelve a su color -> INICIAL
                }else{
                    pDNI.campos[0].setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.decode("#414141")));
                }
            }
        });

        bottomType2.opciones[0].addActionListener(evt-> System.out.println(pDNI.campos[0].getText()));
        bottomType2.opciones[1].addActionListener(evt->volver(pDNI, bottomType2));
    }


    protected void mostrarDatos(int tipo, JPanel anterior){

        Centro c=getSelectedCenter(anterior);

        String type;
        if(tipo==1){
            type="Hospital";
        }else{
            type="Clínica";
        }

         DataPanel dp=new DataPanel(bgColorCentral);
//        central.add(nuevoCentro);
        bottomType2.setVisible(false);
        frame.add(bottomType3, BorderLayout.SOUTH);
//        bottomType3.setVisible(true);
        bottomType3.opciones[0].addActionListener(evt-> volver(dp, bottomType3));
        continuar(anterior, dp, "Inicio>Gestionar "+type+">Mostrar Datos");
        setStyleJTextArea(dp.texto);

        //COMPONENTES
        setStyleJTextArea(dp.texto);
        if(c instanceof Hospital) {
            Hospital h=(Hospital)c;
            dp.texto.setText(h.toString());
        }else{
            Clinica cl=(Clinica) c;
            dp.texto.setText(cl.toString());
        }
        dp.texto.setMargin(new Insets(0,80,0,0));
    }


    protected void setStyleJLabel(JLabel label){
        label.setBackground(bgColorCentral);
        label.setForeground(Color.decode("#CDCDCD"));
        label.setFont(new Font("Tahoma", 0, 16));
    }


    protected void setStyleJTextArea(JTextArea label){
        label.setBackground(bgColorCentral);
        label.setForeground(Color.decode("#CDCDCD"));
        label.setFont(new Font("Tahoma", 0, 18));
    }


    /**
     * Da un estilo determinado a todos los scrollPane
     * @param scroll - JScrollPane a modificar
     * */
    protected void setStyleScrollPane(JScrollPane scroll){
        scroll.getViewport().setBackground(bgColorCentral);
        scroll.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.decode("#414141")));
        JPanel corner=new JPanel();
        corner.setBackground(bgColorTop);
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, corner);

        //CUSTOM SCROLLBAR
        scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.decode("#505050");
                this.trackColor = bgColorCentral;
                this.trackHighlightColor= Color.decode("#808080");
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(bgColorCentral);
                button.setBorder(BorderFactory.createMatteBorder(1,1,1,1,bgColorCentral));
                return button;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = super.createIncreaseButton(orientation);
                button.setBackground(bgColorCentral);
                button.setBorder(BorderFactory.createMatteBorder(1,1,1,1,bgColorCentral));
                return button;
            }
        });
    }


    protected void setStyleJComboBox(JComboBox combo){

        combo.setBackground(Color.decode("#3C3C3C"));
        combo.setForeground(Color.decode("#CDCDCD"));
        combo.setFont(new Font("Tahoma", 0, 16));
        combo.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.decode("#505050")));

        //CUSTOM SCROLLBAR
        combo.setUI(new BasicComboBoxUI() {

            @Override
            protected JButton createArrowButton(){
                return new BasicArrowButton(BasicArrowButton.SOUTH, Color.decode("#505050"), Color.decode("#505050"),
                        Color.decode("#3C3C3C"), Color.decode("#505050"));
            }

            @Override
            protected ComboPopup createPopup() {
                return new BasicComboPopup(comboBox) {

                    @Override
                    protected JScrollPane createScroller() {
                        JScrollPane scroller = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                        scroller.getVerticalScrollBar().setUI(new BasicScrollBarUI() {

                            @Override
                            protected void configureScrollBarColors() {
                                this.thumbColor = Color.decode("#505050");
                                this.trackColor = bgColorCentral;
                                this.trackHighlightColor= Color.decode("#808080");
                            }

                            @Override
                            protected JButton createDecreaseButton(int orientation) {
                                return createZeroButton();
                            }

                            @Override
                            protected JButton createIncreaseButton(int orientation) {
                                return createZeroButton();
                            }

                            @Override
                            public Dimension getPreferredSize(JComponent c) {
                                return new Dimension(10, super.getPreferredSize(c).height);
                            }

                            private JButton createZeroButton() {
                                return new JButton() {
                                    @Override
                                    public Dimension getMinimumSize() {
                                        return new Dimension(new Dimension(0, 0));
                                    }

                                    @Override
                                    public Dimension getPreferredSize() {
                                        return new Dimension(new Dimension(0, 0));
                                    }

                                    @Override
                                    public Dimension getMaximumSize() {
                                        return new Dimension(new Dimension(0, 0));
                                    }
                                };
                            }
                        });
                        return scroller;
                    }
                };
            }
        });
    }


    protected void setStyleJTable(JTable tabla){
//        mp.tabla.setEnabled(false); Desactiva la interacción del raton
        tabla.setDefaultEditor(Object.class, null);
        tabla.setFont(new Font("Tahoma", 0, 18));
        tabla.setBackground(bgColorCentral);
        tabla.setForeground(Color.decode("#CDCDCD"));
        tabla.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.decode("#414141")));
        tabla.setGridColor(Color.decode("#414141"));
//        table.setShowGrid(false); Para quitarlo directamente
        tabla.getTableHeader().setBackground(bgColorTop);
        tabla.getTableHeader().setForeground(Color.decode("#CDCDCD"));
        tabla.getTableHeader().setFont(new Font("Tahoma", 1, 16));
        tabla.getTableHeader().setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.decode("#414141")));
        tabla.setRowHeight(30);
//        mp.tabla.getTableHeader().setResizingAllowed(false);
        tabla.getTableHeader().setReorderingAllowed(false);

    }


    protected void mostrarPersonas(int tipo, JPanel anterior, String arr[]){
        String type, t, text="";
        if(tipo==1){
            t="Pacientes";
            type="Pacientes";
        }else{
            t="Personal";
            type="Trabajadores";
        }

        String[] columnNames = { "ID", "DNI/NIE", "Nombre", "Especialidad/Área"};

        DataPanel mp=new DataPanel(bgColorCentral, columnNames);
        bottomType2.setVisible(false);
        frame.add(bottomType3, BorderLayout.SOUTH);
        bottomType3.opciones[0].addActionListener(evt-> volver(mp, bottomType3));
        continuar(anterior, mp, "Inicio>Gestionar "+t+">Mostrar "+type);

        //COMPONENTES
        for(int i=1;i<=35;i++) {
            Object[] aux = { i, "12345678Z", "Nombre Pruebas", "Pruebas" };
            mp.model.addRow(aux);
        }

        mp.tabla.getColumnModel().getColumn(0).setMaxWidth(45);
        setStyleJTable(mp.tabla);

        setStyleScrollPane(mp.scroll);
        mp.scroll.setPreferredSize(new Dimension(600, 400));

    }


    protected void nuevoCentroPanel(int tipoCentro, JPanel anterior){
        String type;
        if(tipoCentro ==1){
            type="Hospital";
        }else {
            type="Clínica";
        }

        InputForm nuevo;

        if(tipoCentro==1) {
            nuevo = new InputForm(7, bgColorCentral);
            nuevo.titulos[5].setText("Plantas:");
            nuevo.titulos[6].setText("Habitaciones por planta:");
        }else{
            nuevo = new InputForm(5, bgColorCentral);
        }

        continuar(anterior, nuevo, "Inicio>Gestionar "+type+">Crear "+type);
        bottomType3.setVisible(false);
        rmActionListener(bottomType2);
        frame.add(bottomType2, BorderLayout.SOUTH);
        bottomType2.setVisible(true);


        //Texto para los campos
        nuevo.titulos[0].setText("Identificador:");
        nuevo.titulos[1].setText("Nombre:");
        nuevo.titulos[2].setText("Dirección:");
        nuevo.titulos[3].setText("Consultas:");
        nuevo.titulos[4].setText("Trabajadores:");

        //Mensaje informativo
        JLabel info=new JLabel();
        nuevo.c.gridx=1;
        nuevo.c.gridy+=1;
        nuevo.add(info, nuevo.c);
        setStyleJLabel(info);
        info.setForeground(Color.decode("#92D050"));
        info.setText("Nuevo centro añadido");
        info.setVisible(false);

        //Restricciones Campos

        //Bordes para los JTextField
        Border greenBorder=new MatteBorder(1,1,1,1,Color.decode("#00B050"));
        Border redBorder=new MatteBorder(1,1,1,1,Color.decode("#C00000"));
        Border defaultBorder= new MatteBorder(1,1,1,1,Color.decode("#414141"));


        nuevo.campos[0].getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                info.setVisible(false);
                String campo=nuevo.campos[0].getText();
//                int idCentro=Integer.parseInt(campo);

                //En el caso de que este vacío vuelve a su color -> INICIAL
                if(nuevo.campos[0].getText().equals("")){
                    nuevo.campos[0].setBorder(defaultBorder);
                //Si el texto del JTextField cumple las restricciones -> VERDE
                }else if(PeticionDatosSwing.checkEnteroPositivo(campo)==0) {
                    if(app.checkCenterID(Integer.parseInt(campo))){
                        nuevo.campos[0].setBorder(redBorder);
                    }else {
                        nuevo.campos[0].setBorder(greenBorder);
                    }
                //Si el texto del JTextField incumple alguna de las restricciones -> ROJO
                }else if(PeticionDatosSwing.checkEnteroPositivo(campo)!=0){
                    nuevo.campos[0].setBorder(redBorder);
                //Comprueba que el ID del nuevo centro no exista ya.
                }
            }
        });

        nuevo.campos[1].getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                info.setVisible(false);
                //En el caso de que este vacío vuelve a su color -> INICIAL
                if(nuevo.campos[1].getText().equals("")){
                    nuevo.campos[1].setBorder(defaultBorder);
                //Si el texto del JTextField cumple las restricciones -> VERDE
                }else if(PeticionDatosSwing.checkCadenaLimite(false, true, 70, nuevo.campos[1].getText())==0) {
                    nuevo.campos[1].setBorder(greenBorder);
                //Si el texto del JTextField imcumple alguna de las restricciones -> ROJO
                }else if(PeticionDatosSwing.checkCadenaLimite(false, true, 70, nuevo.campos[1].getText())!=0){
                    nuevo.campos[1].setBorder(redBorder);
                }
            }
        });

        nuevo.campos[2].getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                info.setVisible(false);
                //En el caso de que este vacío vuelve a su color -> INICIAL
                if(nuevo.campos[2].getText().equals("")){
                    nuevo.campos[2].setBorder(defaultBorder);
                    //Si el texto del JTextField cumple las restricciones -> VERDE
                }else if(PeticionDatosSwing.checkCadenaLimite(true, true, 70, nuevo.campos[2].getText())==0) {
                    nuevo.campos[2].setBorder(greenBorder);
                    //Si el texto del JTextField imcumple alguna de las restricciones -> ROJO
                }else if(PeticionDatosSwing.checkCadenaLimite(true, true, 70, nuevo.campos[2].getText())!=0){
                    nuevo.campos[2].setBorder(redBorder);
                }
            }
        });

        nuevo.campos[3].getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                info.setVisible(false);
                //En el caso de que este vacío vuelve a su color -> INICIAL
                if(nuevo.campos[3].getText().equals("")){
                    nuevo.campos[3].setBorder(defaultBorder);
                    //Si el texto del JTextField cumple las restricciones -> VERDE
                }else if(PeticionDatosSwing.checkEnteroPositivo(nuevo.campos[3].getText())==0) {
                    nuevo.campos[3].setBorder(greenBorder);
                    //Si el texto del JTextField imcumple alguna de las restricciones -> ROJO
                }else if(PeticionDatosSwing.checkEnteroPositivo(nuevo.campos[3].getText())!=0){
                    nuevo.campos[3].setBorder(redBorder);
                }
            }
        });

        nuevo.campos[4].getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                info.setVisible(false);
                //En el caso de que este vacío vuelve a su color -> INICIAL
                if(nuevo.campos[4].getText().equals("")){
                    nuevo.campos[4].setBorder(defaultBorder);
                    //Si el texto del JTextField cumple las restricciones -> VERDE
                }else if(PeticionDatosSwing.checkEnteroPositivo(nuevo.campos[4].getText())==0) {
                    nuevo.campos[4].setBorder(greenBorder);
                    //Si el texto del JTextField imcumple alguna de las restricciones -> ROJO
                }else if(PeticionDatosSwing.checkEnteroPositivo(nuevo.campos[4].getText())!=0){
                    nuevo.campos[4].setBorder(redBorder);
                }
            }
        });

        if(tipoCentro==1){
            nuevo.campos[5].getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    warn();
                }
                public void removeUpdate(DocumentEvent e) {
                    warn();
                }
                public void insertUpdate(DocumentEvent e) {
                    warn();
                }

                public void warn() {
                    info.setVisible(false);
                    //En el caso de que este vacío vuelve a su color -> INICIAL
                    if(nuevo.campos[5].getText().equals("")){
                        nuevo.campos[5].setBorder(defaultBorder);
                        //Si el texto del JTextField cumple las restricciones -> VERDE
                    }else if(PeticionDatosSwing.checkEnteroPositivo(nuevo.campos[5].getText())==0) {
                        nuevo.campos[5].setBorder(greenBorder);
                        //Si el texto del JTextField imcumple alguna de las restricciones -> ROJO
                    }else if(PeticionDatosSwing.checkEnteroPositivo(nuevo.campos[5].getText())!=0){
                        nuevo.campos[5].setBorder(redBorder);
                    }
                }
            });
            nuevo.campos[6].getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    warn();
                }
                public void removeUpdate(DocumentEvent e) {
                    warn();
                }
                public void insertUpdate(DocumentEvent e) {
                    warn();
                }

                public void warn() {
                    info.setVisible(false);
                    //En el caso de que este vacío vuelve a su color -> INICIAL
                    if(nuevo.campos[6].getText().equals("")){
                        nuevo.campos[6].setBorder(defaultBorder);
                        //Si el texto del JTextField cumple las restricciones -> VERDE
                    }else if(PeticionDatosSwing.checkEnteroPositivo(nuevo.campos[6].getText())==0) {
                        nuevo.campos[6].setBorder(greenBorder);
                        //Si el texto del JTextField imcumple alguna de las restricciones -> ROJO
                    }else if(PeticionDatosSwing.checkEnteroPositivo(nuevo.campos[6].getText())!=0){
                        nuevo.campos[6].setBorder(redBorder);
                    }
                }
            });
        }

        bottomType2.opciones[0].addActionListener(evt-> nuevoCentro(nuevo.campos, info, tipoCentro));
        bottomType2.opciones[1].addActionListener(evt->volver(nuevo, bottomType2));
    }


    /**
     * Obtiene los datos introducidos en el formulario a través de JTextField
     * @param campos - Array de JTextField
     * */
    protected void nuevoCentro(JTextField campos[], JLabel info, int tipoCentro){

        String n="", dir="";
        int idCentro=0,consultas=0, plantas=0, hab=0;

        for(JTextField campo: campos){
            idCentro=Integer.parseInt(campos[0].getText());
            //TODO: Esto es un misterio le meto una "t" solamente en el nombre y me borra el centro anterior, solo pasa con esa letra o_o
            n= campos[1].getText();
            dir=campos[2].getText();
            consultas=Integer.parseInt(campos[3].getText());
            if(tipoCentro==1) {
                plantas = Integer.parseInt(campos[4].getText());
                hab = Integer.parseInt(campos[5].getText());
            }
        }

        int contCentros=Centro.getContCentros();
        if(contCentros>=app.centrosMedicos.length) {
            if(app.aumentarCentrosMedicos()) {
                if (tipoCentro == 1) {
                    Hospital h = new Hospital(n, dir, idCentro, plantas, hab, consultas);
                    app.centrosMedicos[contCentros - 1] = h;
                } else {
                    Clinica cl = new Clinica(n, dir, idCentro, consultas);
                    app.centrosMedicos[contCentros - 1] = cl;
                }
            }
        }else{
            if (tipoCentro == 1) {
                Hospital h = new Hospital(n, dir, idCentro, plantas, hab, consultas);
                app.centrosMedicos[contCentros - 1] = h;
            } else {
                Clinica cl = new Clinica(n, dir, idCentro, consultas);
                app.centrosMedicos[contCentros - 1] = cl;
            }
        }

        for(JTextField campo: campos){
            campo.setText(""); //Para limpiar los campos una vez creado el centro
        }

        info.setVisible(true);
    }


//    /**
//     * Obtiene los datos introducidos en el formulario a través de JTextField
//     * @param campos - Array de JTextField
//     * */
//    protected void checkCampo(JTextField campos[], JLabel info){
//        for(JTextField campo: campos){
//            System.out.println(campo.getText());
//            //TODO:Estos datos deberian meterse en el constructor del nuevo centro
//            campo.setText(""); //Para limpiar los campos una vez creado el centro
//            //TODO:Si al crear el objeto no hay ningun error se muestra esto
//            info.setVisible(true);
//        }
//    }


    protected Centro getSelectedCenter(JPanel actual){
        int idCentro=0;

        if(actual instanceof GestionCentro) {
            GestionCentro gc = (GestionCentro) actual;
            for (JRadioButton r : gc.radioButtons) {
                if (r != null && r.isSelected()) {
                    String cadena = r.getText();
                    idCentro = Integer.parseInt(cadena.substring(3, 4));
                }
            }
        }else if(actual instanceof Stats){
            Stats s = (Stats) actual;
            String cadena= s.desplegableCentros.getSelectedItem().toString();
            idCentro = Integer.parseInt(cadena.substring(3, 4));
        }

        Centro c=app.whichCenter(idCentro);
        return c;
    }


    protected void modificarCentro(int tipo, JPanel anterior){

        Centro c=getSelectedCenter(anterior);

        String type;
        if(tipo==1){
            type="Hospital";
        }else{
            type="Clínica";
        }

        InputForm modCentro=new InputForm(5, bgColorCentral);
        continuar(anterior, modCentro,"Inicio>Gestionar "+type+">Modificar Datos");
        bottomType3.setVisible(false);
        rmActionListener(bottomType2);
        frame.add(bottomType2, BorderLayout.SOUTH);
        bottomType2.setVisible(true);

        //Texto para los campos
        if(tipo==1) {
            Hospital h=(Hospital) c;
            modCentro.titulos[0].setText("Identificador ("+h.getID()+"):");
            modCentro.titulos[1].setText("Nombre ("+h.getNombre()+"):");
            modCentro.titulos[2].setText("Dirección ("+h.getDireccionCentro()+"):");
            modCentro.titulos[3].setText("Consultas ("+h.getLimiteConsultas()+"):");
            modCentro.titulos[4].setText("Trabajadores ("+h.getTotalTrabajadores()+"):");
        }else{
            Clinica cl=(Clinica)c;
            modCentro.titulos[0].setText("Identificador ("+cl.getID()+"):");
            modCentro.titulos[1].setText("Nombre ("+cl.getNombre()+"):");
            modCentro.titulos[2].setText("Dirección ("+cl.getDireccionCentro()+"):");
            modCentro.titulos[3].setText("Consultas ("+cl.getLimiteConsultas()+"):");
            modCentro.titulos[4].setText("Trabajadores ("+cl.getTotalTrabajadores()+"):");
        }

        //Mensaje informativo
        JLabel info=new JLabel();
        modCentro.c.gridx=1;
        modCentro.c.gridy+=1;
        modCentro.add(info, modCentro.c);
        setStyleJLabel(info);
        info.setForeground(Color.decode("#92D050"));
        info.setText("Datos modificados correctamente");
        info.setVisible(false);

        //Bordes para los JTextField
        Border greenBorder=new MatteBorder(1,1,1,1,Color.decode("#00B050"));
        Border redBorder=new MatteBorder(1,1,1,1,Color.decode("#C00000"));
        Border defaultBorder= new MatteBorder(1,1,1,1,Color.decode("#414141"));

        //RESTRICCIONES PARA LOS CAMPOS
        modCentro.campos[0].getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                info.setVisible(false);
                //En el caso de que este vacío vuelve a su color -> INICIAL
                if(modCentro.campos[0].getText().equals("")){
                    modCentro.campos[0].setBorder(defaultBorder);
                    //Si el texto del JTextField cumple las restricciones -> VERDE
                }else if(PeticionDatosSwing.checkEnteroPositivo(modCentro.campos[0].getText())==0) {
                    modCentro.campos[0].setBorder(greenBorder);
                    //Si el texto del JTextField imcumple alguna de las restricciones -> ROJO
                }else if(PeticionDatosSwing.checkEnteroPositivo(modCentro.campos[0].getText())!=0){
                    modCentro.campos[0].setBorder(redBorder);
                }
            }
        });

        modCentro.campos[1].getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                info.setVisible(false);
                //En el caso de que este vacío vuelve a su color -> INICIAL
                if(modCentro.campos[1].getText().equals("")){
                    modCentro.campos[1].setBorder(defaultBorder);
                    //Si el texto del JTextField cumple las restricciones -> VERDE
                }else if(PeticionDatosSwing.checkCadenaLimite(false, true, 70, modCentro.campos[1].getText())==0) {
                    modCentro.campos[1].setBorder(greenBorder);
                    //Si el texto del JTextField imcumple alguna de las restricciones -> ROJO
                }else if(PeticionDatosSwing.checkCadenaLimite(false, true, 70, modCentro.campos[1].getText())!=0){
                    modCentro.campos[1].setBorder(redBorder);
                }
            }
        });

        modCentro.campos[2].getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                info.setVisible(false);
                //En el caso de que este vacío vuelve a su color -> INICIAL
                if(modCentro.campos[2].getText().equals("")){
                    modCentro.campos[2].setBorder(defaultBorder);
                    //Si el texto del JTextField cumple las restricciones -> VERDE
                }else if(PeticionDatosSwing.checkCadenaLimite(true, true, 70, modCentro.campos[2].getText())==0) {
                    modCentro.campos[2].setBorder(greenBorder);
                    //Si el texto del JTextField imcumple alguna de las restricciones -> ROJO
                }else if(PeticionDatosSwing.checkCadenaLimite(true, true, 70, modCentro.campos[2].getText())!=0){
                    modCentro.campos[2].setBorder(redBorder);
                }
            }
        });

        modCentro.campos[3].getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                info.setVisible(false);
                //En el caso de que este vacío vuelve a su color -> INICIAL
                if(modCentro.campos[3].getText().equals("")){
                    modCentro.campos[3].setBorder(defaultBorder);
                    //Si el texto del JTextField cumple las restricciones -> VERDE
                }else if(PeticionDatosSwing.checkEnteroPositivo(modCentro.campos[3].getText())==0) {
                    modCentro.campos[3].setBorder(greenBorder);
                    //Si el texto del JTextField imcumple alguna de las restricciones -> ROJO
                }else if(PeticionDatosSwing.checkEnteroPositivo(modCentro.campos[3].getText())!=0){
                    modCentro.campos[3].setBorder(redBorder);
                }
            }
        });

        modCentro.campos[4].getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                info.setVisible(false);
                //En el caso de que este vacío vuelve a su color -> INICIAL
                if(modCentro.campos[4].getText().equals("")){
                    modCentro.campos[4].setBorder(defaultBorder);
                    //Si el texto del JTextField cumple las restricciones -> VERDE
                }else if(PeticionDatosSwing.checkEnteroPositivo(modCentro.campos[4].getText())==0) {
                    modCentro.campos[4].setBorder(greenBorder);
                    //Si el texto del JTextField imcumple alguna de las restricciones -> ROJO
                }else if(PeticionDatosSwing.checkEnteroPositivo(modCentro.campos[4].getText())!=0){
                    modCentro.campos[4].setBorder(redBorder);
                }
            }
        });

//        bottomType2.opciones[0].addActionListener(evt-> getCampo(modCentro.campos, info));
        bottomType2.opciones[1].addActionListener(evt->volver(modCentro, bottomType2));
    }


    protected void statsCentros(JPanel anterior){
        int tipo=1; //TODO
        Stats sc=new Stats(4, bgColorCentral);
        bottomType1.setVisible(false);
        rmActionListener(bottomType1);
        frame.add(bottomType2, BorderLayout.SOUTH);
        bottomType2.setVisible(true);
        continuar(anterior, sc, "Inicio>Estadísticas de los centros");

        sc.t1.setText("Elige el mes del que obtener la información: ");
        sc.t1.setForeground(Color.decode("#CDCDCD"));
        sc.t1.setFont(new Font("Tahoma", 0, 16));

        sc.t2.setText("Elige un centro: ");
        sc.t2.setForeground(Color.decode("#CDCDCD"));
        sc.t2.setFont(new Font("Tahoma", 0, 16));
        sc.t2.setHorizontalAlignment(SwingConstants.LEFT);

        for(String mes: MESES){
            sc.desplegableMeses.addItem(mes);
        }

        for (Centro c: app.centrosMedicos) {
            if(c!=null) {
                sc.desplegableCentros.addItem("ID " + c.getID() + " " + c.getNombre());
            }
        }

        sc.desplegableMeses.setPreferredSize(new Dimension(110, 25));
        setStyleJComboBox(sc.desplegableMeses);

        setStyleJComboBox(sc.desplegableCentros);

        setStyleJRadioButton(sc.centros);

        if(sc.centros[0]!=null) {
            sc.centros[0].setSelected(true);
        }

        bottomType2.opciones[0].addActionListener(evt-> mostrarStatsCentros(sc, sc.desplegableMeses.getSelectedItem().toString()));
        bottomType2.opciones[1].addActionListener(evt->volver(sc, bottomType2));
    }


    protected void statsPersonas(JPanel anterior){
        int tipo=2; //TODO
        int tipoPersona;

        String centro[]={"Torrecárdenas", "Mediterráneo", "Virgen del Mar"};

        Stats sc=new Stats(3, centro, bgColorCentral);
        bottomType1.setVisible(false);
        rmActionListener(bottomType1);
        frame.add(bottomType2, BorderLayout.SOUTH);
        bottomType2.setVisible(true);
        continuar(anterior, sc, "Inicio>Estadísticas de las personas");

        sc.t1.setText("Elige el mes del que obtener la información: ");
        sc.t1.setForeground(Color.decode("#CDCDCD"));
        sc.t1.setFont(new Font("Tahoma", 0, 16));

        sc.t3.setText("Tipo de persona: ");
        sc.t3.setForeground(Color.decode("#CDCDCD"));
        sc.t3.setFont(new Font("Tahoma", 0, 16));
        sc.t3.setHorizontalAlignment(SwingConstants.LEFT);

        sc.t2.setText("Elige un centro: ");
        sc.t2.setForeground(Color.decode("#CDCDCD"));
        sc.t2.setFont(new Font("Tahoma", 0, 16));
        sc.t2.setHorizontalAlignment(SwingConstants.LEFT);

        for(String mes: MESES){
            sc.desplegableMeses.addItem(mes);
        }

        sc.desplegableMeses.setPreferredSize(new Dimension(110, 25));
//        sc.desplegableMeses.addActionListener(e -> checkMes((String) sc.desplegableMeses.getSelectedItem()));
        setStyleJComboBox(sc.desplegableMeses);

        setStyleJRadioButton(sc.personas);

        for (Centro c: app.centrosMedicos) {
            if(c!=null) {
                sc.desplegableCentros.addItem("ID " + c.getID() + " " + c.getNombre());
            }
        }

        sc.desplegableCentros.addActionListener(e -> getSelectedCenter(sc));
        setStyleJComboBox(sc.desplegableCentros);

        if(sc.centros[0]!=null) {
            sc.centros[0].setSelected(true);
        }

        bottomType2.opciones[0].addActionListener(evt-> mostrarStatsPersonal(sc, sc.desplegableMeses.getSelectedItem().toString()));
        bottomType2.opciones[1].addActionListener(evt->volver(sc, bottomType2));
    }


    /**
     * Obtiene el JradioButton seleccionado y segun sea devuelve el tipo de persona.
     * @param actual - Jpanel
     * @return int - 1->Paciente, 2->Trabajador
     * */
    protected int getPerson(JPanel actual){
        Stats sp=(Stats) actual;
        String persona="";

        for(JRadioButton r: sp.personas){
            if(r!=null && r.isSelected()){
                persona=r.getText();
            }
        }

        if(persona=="Paciente"){
            return 1;
        }else if(persona=="Médico"){
            return 2;
        }else{
            return 3;
        }
    }


    /**
     * Obtiene la posicion del mes seleccionado en el JComboBox
     * @param actual - Jpanel
     * @return int - mes en formato numérico
     * */
    protected int getMes(JPanel actual){
        Stats sp=(Stats) actual;

        return sp.desplegableMeses.getSelectedIndex()+1;

    }


    /**
     * Muestra los pacientes que tiene un centro medico, tanto en consultas como en habitaciones si es un Hospital
     * @param c - centro del que se quieren mostrar los pacientes
     * @return boolean - true si existe al menos un paciente, false si no hay nada
     * */
    protected int mostrarPacientes(Centro c/*, JTextArea t*/){
        String mensaje="";
        System.out.println(" ");//linea
        int nulo=0, cont=0;

        for(Paciente p: c.consultas){
            if(p!=null) {
                pacientesHospital.add(p);
            } else {
                nulo++;
            }
        }

        if(c instanceof Hospital){
            Hospital h=(Hospital) c;
            for (int x = 0; x < h.habitaciones.length; x++) {
                for (int z = 0; z < h.habitaciones[x].length; z++) {
                    if (h.habitaciones[x][z] != null) {
                        pacientesHospital.add(h.habitaciones[x][z]);
                    } else {
                        nulo++;
                    }
                }
            }
        }
        return nulo;
    }



    /**
     * Muestra un panel con las estadísticas del tipo de persona elegida
     * @param anterior - JPanel desde donde se llama a esta funcion
     * @param mesP - Mes del que obtener los datos
     * */
    protected void mostrarStatsPersonal(JPanel anterior, String mesP){

        int tipoPersona=getPerson(anterior);
        int mes=getMes(anterior);
        Centro c=getSelectedCenter(anterior);

        bottomType2.setVisible(false);
        rmActionListener(bottomType2);
        frame.add(bottomType3, BorderLayout.SOUTH);
        bottomType3.setVisible(true);

        Hospital h=null;
        Clinica cl=null;

        if(c instanceof Hospital){
            h=(Hospital) c;
        }else{
            cl=(Clinica) c;
        }

        //COMPONENTES
        //Se que esto es una chapuza, pero si me da tiempo lo mejoro.
        if(tipoPersona==1 && c instanceof Hospital) {
            int nulo=mostrarPacientes(h);
            DataPanel pacientes=new DataPanel(pacientesHospital,bgColorCentral);
            continuar(anterior, pacientes, "Inicio>Estadísticas de los centros>Estadísticas de "+mesP);
            bottomType3.opciones[0].addActionListener(evt-> volver(pacientes, bottomType3));

            pacientes.t1.setText("Pacientes de "+h.getNombre()+": ");
            setStyleJLabel(pacientes.t1);

            setStyleScrollPane(pacientes.scroll);
            pacientes.scroll.setBorder(new EmptyBorder(new Insets(0,0,0,0)));
            setStyleJRadioButton(pacientes.encontradas);

            setStyleJTextArea(pacientes.texto);
            pacientes.texto.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(1,0,1,0 ,Color.decode("#414141") ),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            int total=h.consultas.length+h.habitaciones.length;

            if(nulo>=total){
                pacientes.texto.setText("Aún no hay pacientes registrados");
                pacientes.texto.setForeground(Color.decode("#BFB500"));
            }

            for(JRadioButton r: pacientes.encontradas){
                if(r!=null) {
                    r.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int idPersona=Integer.parseInt(r.getText().substring(3,4));
                            Persona p=app.whichPerson(idPersona);
                            pacientes.texto.setText(app.getStatsPersonal(p, mes));
                        }
                    });
                }
            }

        }else if(tipoPersona==1 && c instanceof Clinica){
            DataPanel pacientes=new DataPanel(cl.consultas, 1, bgColorCentral);
            continuar(anterior, pacientes, "Inicio>Estadísticas de los centros>Estadísticas de "+mesP);
            bottomType3.opciones[0].addActionListener(evt-> volver(pacientes, bottomType3));

            pacientes.t1.setText("Pacientes de "+cl.getNombre()+": ");
            setStyleJLabel(pacientes.t1);

            setStyleScrollPane(pacientes.scroll);
            pacientes.scroll.setBorder(new EmptyBorder(new Insets(0,0,0,0)));
            setStyleJRadioButton(pacientes.encontradas);

            setStyleJTextArea(pacientes.texto);
            pacientes.texto.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(1,0,1,0 ,Color.decode("#414141") ),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));

            for(JRadioButton r: pacientes.encontradas){
                if(r!=null) {
                    r.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int idPersona=Integer.parseInt(r.getText().substring(3,4));
                            Persona p=app.whichPerson(idPersona);
                            pacientes.texto.setText(app.getStatsPersonal(p, mes));
                        }
                    });
                }
            }

        }else if(tipoPersona==2){
            DataPanel medicos=new DataPanel(c.trabajadores, 2, bgColorCentral);
            continuar(anterior, medicos, "Inicio>Estadísticas de los centros>Estadísticas de "+mesP);
            bottomType3.opciones[0].addActionListener(evt-> volver(medicos, bottomType3));

            medicos.t1.setText("Médicos de "+c.getNombre()+": ");
            setStyleJLabel(medicos.t1);

            setStyleScrollPane(medicos.scroll);
            medicos.scroll.setBorder(new EmptyBorder(new Insets(0,0,0,0)));
            setStyleJRadioButton(medicos.encontradas);

            setStyleJTextArea(medicos.texto);
            medicos.texto.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(1,0,1,0 ,Color.decode("#414141") ),
                    BorderFactory.createEmptyBorder(25, 5, 5, 5)));

            for(JRadioButton r: medicos.encontradas){
                if(r!=null) {
                    r.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int idPersona=Integer.parseInt(r.getText().substring(3,4));
                            Persona p=app.whichPerson(idPersona);
                            medicos.texto.setText(app.getStatsPersonal(p, mes));
                        }
                    });
                }
            }
        }else if(tipoPersona==3){
            DataPanel admins=new DataPanel(c.trabajadores, 3, bgColorCentral);
            continuar(anterior, admins, "Inicio>Estadísticas de los centros>Estadísticas de "+mesP);
            bottomType3.opciones[0].addActionListener(evt-> volver(admins, bottomType3));

            admins.t1.setText("Administrativos de "+c.getNombre()+": ");
            setStyleJLabel(admins.t1);

            setStyleScrollPane(admins.scroll);
            admins.scroll.setBorder(new EmptyBorder(new Insets(0,0,0,0)));
            setStyleJRadioButton(admins.encontradas);

            setStyleJTextArea(admins.texto);
            admins.texto.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(1,0,1,0 ,Color.decode("#414141") ),
                    BorderFactory.createEmptyBorder(25, 5, 5, 5)));

            for(JRadioButton r: admins.encontradas){
                if(r!=null) {
                    r.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int idPersona=Integer.parseInt(r.getText().substring(3,4));
                            Persona p=app.whichPerson(idPersona);
                            admins.texto.setText(app.getStatsPersonal(p, mes));
                        }
                    });
                }
            }
        }
    }


    protected void mostrarStatsCentros(JPanel anterior, String  mesP){
        int mes=getMes(anterior);
        Centro centro=getSelectedCenter(anterior);
        int cont=0, libres=0;

        String consultas[]=new String[centro.consultas.length];
        String habitaciones[];

        for(Paciente p: centro.consultas){
            cont++;
            if(p!=null){
                consultas[cont-1]=cont+" > "+p.getNombre()+", "+p.getApellido1()+" "+p.getApellido2();
            }else{
//                consultas[cont-1]=cont+" > "+" Libre";
                libres++;
            }
        }

        if(libres>=centro.consultas.length) {
            consultas[0] = "Todas las consultas libres";
        }


        DataPanel msc=new DataPanel(bgColorCentral, true, consultas);
        continuar(anterior, msc, "...>Estadísticas de centros>"+centro.getNombre()+", "+mesP);

        bottomType2.setVisible(false);
        rmActionListener(bottomType2);
        frame.add(bottomType3, BorderLayout.SOUTH);
        bottomType3.setVisible(true);

        setStyleJLabel(msc.t1);
        msc.t1.setText("CONSULTAS");
        msc.t1.setFont(new Font("Tahoma", 1, 16));
//        msc.t2.setText("HABITACIONES");
//        setStyleJTextArea(msc.texto);
        setStyleScrollPane(msc.scroll);
        setStyleJList(msc.estadoConsultas);
        msc.estadoConsultas.setPreferredSize(new Dimension(350, 0));

        msc.scroll.setBorder(BorderFactory.createLineBorder(Color.decode("#414141"), 1));

        if(centro instanceof Hospital) {
            Hospital h=(Hospital) centro;
            int longitud=h.habitaciones.length*h.habitaciones[0].length; //Hay que multiplicarlo, no sumarlo...
            habitaciones = new String[longitud];
            int contador=0;

            for (int x = 0; x < h.habitaciones.length; x++) {
                for (int z = 0; z < h.habitaciones[x].length; z++) {
                    if (h.habitaciones[x][z] != null) {
                        habitaciones[z]=x+"-"+z+h.habitaciones[x][z].getNombre()+", "+h.habitaciones[x][z].getApellido1()+
                                " "+h.habitaciones[x][z].getApellido2();
                    }else{
                        contador++;
                    }
                }
            }

            if(contador>=longitud) {
                habitaciones[0] = "Todas las habitaciones libres";
            }

            msc.c.gridx = 0;
            msc.c.gridy = 2;
            msc.c.insets= new Insets(10,0,0,0);
            JLabel t2=new JLabel("HABITACIONES");
            msc.add(t2, msc.c);

            msc.c.gridx = 0;
            msc.c.gridy = 3;
            JList<String> estadoHabitaciones=new JList<>(habitaciones);
            msc.add(estadoHabitaciones, msc.c);
            estadoHabitaciones.setPreferredSize(new Dimension(350, 0));
            JScrollPane scroll=new JScrollPane(estadoHabitaciones);
            msc.add(scroll, msc.c);
            setStyleJLabel(t2);
            t2.setFont(new Font("Tahoma", 1, 16));
            setStyleScrollPane(scroll);
            setStyleJList(estadoHabitaciones);
        }

        bottomType3.opciones[0].addActionListener(evt->volver(msc, bottomType3));
    }


    /**
     * Elimina el ActionListener del boton de Aceptar del bottom panel. Es un apaño, pero como no sé cómo funciona swing pues asi se queda.
     * @param currentBottom - bottom panel donde esta el boton
     * */
    protected void rmActionListener(Bottom currentBottom){
        if(currentBottom.opciones.length>1){
            for(ActionListener listener: currentBottom.opciones[0].getActionListeners()){
                currentBottom.opciones[0].removeActionListener(listener);
            }
        }
    }


    protected void continuar(JPanel anterior, JPanel siguiente, String path){
        anterior.setVisible(false);
        central.add(siguiente);
        siguiente.setVisible(true);
        topType1.setStyle(path, 16, "#CDCDCD", 30, 115);
    }


    protected void volver(JPanel currentCentral, Bottom currentBottom){
        rmActionListener(currentBottom);
        currentCentral.setVisible(false);
        currentBottom.setVisible(false);
        frame.add(bottomType1, BorderLayout.SOUTH);
        central.add(m, c);
        frame.add(central, BorderLayout.CENTER);
        m.setVisible(true);
        bottomType1.setVisible(true);
//        bottom.repaint();
        m.setVisible(true);
        topType1.setStyle("Gestión Médica",40,"#FFFFFF", 0, 170);
    }


    protected static void salir(){

        int opcion=JOptionPane.showConfirmDialog(null, "¿Desea guardar el estado actual?", "Guardar y Salir", JOptionPane.OK_CANCEL_OPTION);
        if(opcion==1){
            //TODO:se guarda y luego sale
            System.exit(0);
        }else{
            System.exit(0);
        }

    }


    protected void confirmRmCentro(Centro c, JPanel actual, JPanel anterior, JPanel nuevo){

        int opcion=JOptionPane.showConfirmDialog(null, "Está apunto de eliminar el objeto ¿Está seguro?", "Confirmación de la operación", JOptionPane.OK_CANCEL_OPTION);

        if(opcion==0){
            if(app.removeCentro(c.getID())){
                System.out.println("Objeto eliminado");
                JOptionPane.showMessageDialog(null, "El centro "+c.getNombre()+" ha sido eliminado correctamente.", "Mensaje de información", JOptionPane.INFORMATION_MESSAGE);
                //Por mas que intento que se actualice al eliminarlo no quiere...(Arreglado)
                central.remove(actual); //No quitar esta línea que hace falta, si no se solapa to.
                //Ni revalidate, ni repaint ni nada, llamo otra vez a la funcion y que se cree to de nuevo :)
                gestionCentro(anterior, 1, 4);
            }else{
//                System.out.println("Error. El centro tiene personas.");
                JOptionPane.showMessageDialog(null, "El centro contiene personas, primero debes dar de baja todo su personal.", "Error al eliminar", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            System.out.println("Operación cancelada");
        }
    }


//    public static void main(String args[]) {
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new VentanaPrincipal();
//            }
//        });
//    }

}
