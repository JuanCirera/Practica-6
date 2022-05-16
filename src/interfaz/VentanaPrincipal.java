package interfaz;

import utilidades.Faker;
import utilidades.PeticionDatos;
import utilidades.PeticionDatosSwing;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaPrincipal extends javax.swing.JFrame {

    //ATRIBUTOS
    GridBagConstraints c = new GridBagConstraints();
    private Central central;
    private Menu m;
    private JFrame frame;
    private JLabel titulo, version;
    private String hospitales[]=new String[15];
    private String clinicas[]=new String[15];

    //COLORES DE LOS PANELES
    //Con estos dos atributos puedo cambiar rápidamente los colores de todos los paneles y componentes.
    private Color bgColorCentral=Color.decode("#333333");
    private Color bgColorTop=Color.decode("#2B2B2B");
    private Color bgColorTextField=Color.decode("#3C3C3C");

    //PANELES
    private Top topType1;
    private Bottom bottomType1,bottomType2,bottomType3;

    ImageIcon img = new ImageIcon("src/img/p6-icon.png");


    public VentanaPrincipal() {
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
        version.setText("Alfa 0.3 © Juan Fco Cirera "); //TODO: cambiar version en cada commit
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

        if(tipo==1) {
            for(int i=0;i<hospitales.length;i++){
                hospitales[i]=Faker.devolverHospitales();
            }
            gc=new GestionCentro(opciones, hospitales, bgColorCentral); //Esto crea un nuevo panel al que se le pasa las opciones a mostrar
            continuar(anterior, gc, "Inicio>Gestionar Hospital");
            bottomType2.opciones[1].addActionListener(evt->volver(gc, bottomType2));
        }else{
            for(int i=0;i<clinicas.length;i++){
                clinicas[i]=Faker.devolverClinicas();
            }
            gc=new GestionCentro(opciones, clinicas, bgColorCentral); //Esto crea un nuevo panel al que se le pasa las opciones a mostrar
            continuar(anterior, gc, "Inicio>Gestionar Clínica");
            bottomType2.opciones[1].addActionListener(evt->volver(gc, bottomType2));
        }

        bottomType1.setVisible(false);
        rmActionListener(bottomType2);
        frame.add(bottomType2, BorderLayout.SOUTH);
        bottomType2.setVisible(true);
        bottomType2.opciones[0].addActionListener(evt-> System.out.println(topType1.titulo.getText()));

        //COMPONENTES

        setStyleScrollPane(gc.scroll);
        gc.scroll.setPreferredSize(new Dimension(390, 350));
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

//        gc.opciones[0].addActionListener(evt->mostrarDatos(tipo, gc, centros));
//        opcion6.addMouseListener(this);
        gc.opciones[0].setText("Mostrar información");

        gc.opciones[1].addActionListener(evt->modificarCentro(tipo, gc));
//        opcion6.addMouseListener(this);
        gc.opciones[1].setText("Modificar");

        gc.opciones[2].addActionListener(evt->nuevoCentro(tipo, gc));
//        opcion6.addMouseListener(this);
        gc.opciones[2].setText("+ Nuevo");

        gc.opciones[3].addActionListener(evt->confirmar());
//        opcion6.addMouseListener(this);
        gc.opciones[3].setText("- Eliminar");

        for(JRadioButton r: gc.radioButtons){
            r.setBackground(bgColorCentral);
            r.setForeground(Color.decode("#CDCDCD"));
            r.setFont(new Font("Verdana", 1, 16));
        }
        gc.radioButtons[0].setSelected(true);
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


    protected void mostrarDatos(int tipo, JPanel anterior, String arr[]){
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

        //COMPONENTES
        //TODO: No es la mejor manera de mostrar esto con una tabla
        setStyleJTextArea(dp.texto);
//        dp.texto.setText(); //TODO: meter aqui el toString del centro seleccionado
    }


    protected void setStyleJLabel(JLabel label){
        label.setBackground(bgColorCentral);
        label.setForeground(Color.decode("#CDCDCD"));
        label.setFont(new Font("Tahoma", 0, 16));
    }


    protected void setStyleJTextArea(JTextArea label){
        label.setBackground(bgColorCentral);
        label.setForeground(Color.decode("#CDCDCD"));
        label.setFont(new Font("Tahoma", 0, 16));
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


    protected void nuevoCentro(int tipo, JPanel anterior){
        String type;
        if(tipo==1){
            type="Hospital";
        }else {
            type="Clínica";
        }

        InputForm nuevoCentro=new InputForm(5, bgColorCentral);
        continuar(anterior, nuevoCentro, "Inicio>Gestionar "+type+">Crear "+type);
        rmActionListener(bottomType2);
        frame.add(bottomType2, BorderLayout.SOUTH);
        bottomType2.setVisible(true);


        //Texto para los campos
        nuevoCentro.titulos[0].setText("Identificador:");
        nuevoCentro.titulos[1].setText("Nombre:");
        nuevoCentro.titulos[2].setText("Dirección:");
        nuevoCentro.titulos[3].setText("Consultas:");
        nuevoCentro.titulos[4].setText("Trabajadores:");

        //Mensaje informativo
        JLabel info=new JLabel();
        nuevoCentro.c.gridx=1;
        nuevoCentro.c.gridy+=1;
        nuevoCentro.add(info, nuevoCentro.c);
        setStyleJLabel(info);
        info.setForeground(Color.decode("#92D050"));
        info.setText("Nuevo centro añadido");
        info.setVisible(false);

        //Restricciones Campos

        //Bordes para los JTextField
        Border greenBorder=new MatteBorder(1,1,1,1,Color.decode("#00B050"));
        Border redBorder=new MatteBorder(1,1,1,1,Color.decode("#C00000"));
        Border defaultBorder= new MatteBorder(1,1,1,1,Color.decode("#414141"));


        nuevoCentro.campos[0].getDocument().addDocumentListener(new DocumentListener() {
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
                if(nuevoCentro.campos[0].getText().equals("")){
                    nuevoCentro.campos[0].setBorder(defaultBorder);
                //Si el texto del JTextField cumple las restricciones -> VERDE
                }else if(PeticionDatosSwing.checkEnteroPositivo(nuevoCentro.campos[0].getText())==0) {
                    nuevoCentro.campos[0].setBorder(greenBorder);
                //Si el texto del JTextField imcumple alguna de las restricciones -> ROJO
                }else if(PeticionDatosSwing.checkEnteroPositivo(nuevoCentro.campos[0].getText())!=0){
                    nuevoCentro.campos[0].setBorder(redBorder);
                }
            }
        });

        nuevoCentro.campos[1].getDocument().addDocumentListener(new DocumentListener() {
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
                if(nuevoCentro.campos[1].getText().equals("")){
                    nuevoCentro.campos[1].setBorder(defaultBorder);
                //Si el texto del JTextField cumple las restricciones -> VERDE
                }else if(PeticionDatosSwing.checkCadenaLimite(false, true, 70, nuevoCentro.campos[1].getText())==0) {
                    nuevoCentro.campos[1].setBorder(greenBorder);
                //Si el texto del JTextField imcumple alguna de las restricciones -> ROJO
                }else if(PeticionDatosSwing.checkCadenaLimite(false, true, 70, nuevoCentro.campos[1].getText())!=0){
                    nuevoCentro.campos[1].setBorder(redBorder);
                }
            }
        });

        nuevoCentro.campos[2].getDocument().addDocumentListener(new DocumentListener() {
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
                if(nuevoCentro.campos[2].getText().equals("")){
                    nuevoCentro.campos[2].setBorder(defaultBorder);
                    //Si el texto del JTextField cumple las restricciones -> VERDE
                }else if(PeticionDatosSwing.checkCadenaLimite(true, true, 70, nuevoCentro.campos[2].getText())==0) {
                    nuevoCentro.campos[2].setBorder(greenBorder);
                    //Si el texto del JTextField imcumple alguna de las restricciones -> ROJO
                }else if(PeticionDatosSwing.checkCadenaLimite(true, true, 70, nuevoCentro.campos[2].getText())!=0){
                    nuevoCentro.campos[2].setBorder(redBorder);
                }
            }
        });

        nuevoCentro.campos[3].getDocument().addDocumentListener(new DocumentListener() {
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
                if(nuevoCentro.campos[3].getText().equals("")){
                    nuevoCentro.campos[3].setBorder(defaultBorder);
                    //Si el texto del JTextField cumple las restricciones -> VERDE
                }else if(PeticionDatosSwing.checkEnteroPositivo(nuevoCentro.campos[3].getText())==0) {
                    nuevoCentro.campos[3].setBorder(greenBorder);
                    //Si el texto del JTextField imcumple alguna de las restricciones -> ROJO
                }else if(PeticionDatosSwing.checkEnteroPositivo(nuevoCentro.campos[3].getText())!=0){
                    nuevoCentro.campos[3].setBorder(redBorder);
                }
            }
        });

        nuevoCentro.campos[4].getDocument().addDocumentListener(new DocumentListener() {
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
                if(nuevoCentro.campos[4].getText().equals("")){
                    nuevoCentro.campos[4].setBorder(defaultBorder);
                    //Si el texto del JTextField cumple las restricciones -> VERDE
                }else if(PeticionDatosSwing.checkEnteroPositivo(nuevoCentro.campos[4].getText())==0) {
                    nuevoCentro.campos[4].setBorder(greenBorder);
                    //Si el texto del JTextField imcumple alguna de las restricciones -> ROJO
                }else if(PeticionDatosSwing.checkEnteroPositivo(nuevoCentro.campos[4].getText())!=0){
                    nuevoCentro.campos[4].setBorder(redBorder);
                }
            }
        });

        bottomType2.opciones[0].addActionListener(evt-> getCampo(nuevoCentro.campos, info));
        bottomType2.opciones[1].addActionListener(evt->volver(nuevoCentro, bottomType2));
    }


    /**
     * Obtiene los datos introducidos en el formulario a través de JTextField
     * @param campos - Array de JTextField
     * */
    protected void getCampo(JTextField campos[]){
        for(JTextField campo: campos){
            System.out.println(campo.getText());
            //TODO:Estos datos deberian meterse en el constructor del nuevo centro
            campo.setText(""); //Para limpiar los campos una vez creado el centro
        }
    }


    /**
     * Obtiene los datos introducidos en el formulario a través de JTextField
     * @param campos - Array de JTextField
     * */
    protected void getCampo(JTextField campos[], JLabel info){
        for(JTextField campo: campos){
            System.out.println(campo.getText());
            //TODO:Estos datos deberian meterse en el constructor del nuevo centro
            campo.setText(""); //Para limpiar los campos una vez creado el centro
            //TODO:Si al crear el objeto no hay ningun error se muestra esto
            info.setVisible(true);
        }
    }


    protected void modificarCentro(int tipo, JPanel anterior){
        String type;
        if(tipo==1){
            type="Hospital";
        }else{
            type="Clínica";
        }

        InputForm modCentro=new InputForm(5, bgColorCentral);
        continuar(anterior, modCentro,"Inicio>Gestionar "+type+">Modificar Datos");
        rmActionListener(bottomType2);
        frame.add(bottomType2, BorderLayout.SOUTH);
        bottomType2.setVisible(true);

        //Texto para los campos
        modCentro.titulos[0].setText("Identificador (1):"); //TODO: Hacer los get a los atributos actuales y concatenar al lado con un color mas oscuro
        modCentro.titulos[1].setText("Nombre (Torrecárdenas):");
        modCentro.titulos[2].setText("Dirección (Calle prueba):");
        modCentro.titulos[3].setText("Consultas (20):");
        modCentro.titulos[4].setText("Trabajadores (100):");

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

        bottomType2.opciones[0].addActionListener(evt-> getCampo(modCentro.campos, info));
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


        String meses[]={"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto","Septiembre","Octubre", "Noviembre", "Diciembre"};

        for(String mes: meses){
            sc.desplegable.addItem(mes);
        }
        sc.desplegable.setBackground(Color.decode("#3C3C3C"));
        sc.desplegable.setForeground(Color.decode("#CDCDCD"));
        sc.desplegable.setFont(new Font("Tahoma", 0, 16));
        sc.desplegable.setPreferredSize(new Dimension(110, 25));
        setStyleJComboBox(sc.desplegable);

        for(JRadioButton r: sc.centros){
            r.setBackground(bgColorCentral);
            r.setForeground(Color.decode("#CDCDCD"));
            r.setFont(new Font("Tahoma", 1, 16));
        }

        if(sc.centros[0]!=null) {
            sc.centros[0].setSelected(true);
        }

//        bottomType2.opciones[0].addActionListener(evt-> mostrarStats(sc, 1 )); //TODO: se deberia comprobar de que clase es instancia la seleccion del ComboBox
        bottomType2.opciones[1].addActionListener(evt->volver(sc, bottomType2));
    }


    protected void statsPersonas(JPanel anterior){
        int tipo=2; //TODO
        int tipoPersona;

        String centro[]={"Torrecárdenas", "Mediterráneo", "Virgen del Mar"};
//        String c[]={"Clinica Almeria", "Clinica programadores", "Clinica pruebas"};

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


        String meses[]={"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto","Septiembre","Octubre", "Noviembre", "Diciembre"};

        for(String mes: meses){
            sc.desplegable.addItem(mes);
        }

        sc.desplegable.setBackground(Color.decode("#3C3C3C"));
        sc.desplegable.setForeground(Color.decode("#CDCDCD"));
        sc.desplegable.setFont(new Font("Tahoma", 0, 16));
        sc.desplegable.setPreferredSize(new Dimension(110, 25));
        sc.desplegable.addActionListener(e -> checkMes((String) sc.desplegable.getSelectedItem()));
        sc.desplegable.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.decode("#505050")));
        setStyleJComboBox(sc.desplegable);

        for(JRadioButton r: sc.personas){
            r.setBackground(bgColorCentral);
            r.setForeground(Color.decode("#CDCDCD"));
            r.setFont(new Font("Tahoma", 0, 16));
            r.addActionListener(e -> checkPerson(r.getText()));
        }

        for(JRadioButton r: sc.centros){
            r.setBackground(bgColorCentral);
            r.setForeground(Color.decode("#CDCDCD"));
            r.setFont(new Font("Tahoma", 0, 16));
            r.addActionListener(e -> checkCenter(r.getText()));
        }

        if(sc.centros[0]!=null) {
            sc.centros[0].setSelected(true);
        }

//        bottomType2.opciones[0].addActionListener(evt-> mostrarStats(sc, 1, )); //TODO: se deberia comprobar de que clase es instancia la seleccion del ComboBox
        bottomType2.opciones[1].addActionListener(evt->volver(sc, bottomType2));
    }

    //TODO
    protected void checkPerson(String tipo){
        if(tipo=="Paciente"){
//            return 1;
            System.out.println(tipo);
        }else{
//            return 2;
            System.out.println(tipo);
        }
    }

    //TODO
    protected void checkMes(String tipo){
//        if(tipo=="Paciente"){
//            return 1;
            System.out.println(tipo);
//        }else{
//            return 2;
//            System.out.println(tipo);
//        }
    }

    //TODO
    protected void checkCenter(String tipo){
//        if(tipo=="Paciente"){
//            return 1;
        System.out.println(tipo);
//        }else{
//            return 2;
//            System.out.println(tipo);
//        }
    }


    protected void mostrarStats(JPanel anterior, int mes, int idCentro, int tipo){
        bottomType2.setVisible(false);
        rmActionListener(bottomType2);
        frame.add(bottomType3, BorderLayout.SOUTH);
        bottomType3.setVisible(true);

        //COMPONENTES
        if(tipo==1) {
            String[] columnNames = { "ID", "DNI/NIE", "Nombre", "Especialidad/Área"};
            DataPanel ms=new DataPanel(bgColorCentral, columnNames);
            continuar(anterior, ms, "Inicio>Estadísticas de los centros>Estadísticas de "+mes);
            bottomType3.opciones[0].addActionListener(evt-> volver(ms, bottomType3));
            ms.texto.setBackground(bgColorCentral);
            ms.texto.setForeground(Color.decode("#CDCDCD"));
            ms.texto.setFont(new Font("Arial", 0, 16));
            ms.texto.setMargin(new Insets(0, 0, 0, 0));
        }else{
            String personas[]={"Juan", "Maria", "Rafa", "David"};
            DataPanel sp=new DataPanel(personas, bgColorCentral);
            continuar(anterior, sp, "Inicio>Estadísticas de los centros>Estadísticas de "+mes);
            bottomType3.opciones[0].addActionListener(evt-> volver(sp, bottomType3));

            sp.t1.setText("Pacientes del Hospital Torrecárdenas: ");
            sp.t1.setForeground(Color.decode("#CDCDCD"));
            sp.t1.setFont(new Font("Tahoma", 0, 16));

            for(JRadioButton r: sp.encontradas){
                r.setBackground(bgColorCentral);
                r.setForeground(Color.decode("#CDCDCD"));
                r.setFont(new Font("Tahoma", 0, 16));
            }

            sp.texto.setBackground(bgColorCentral);
            sp.texto.setForeground(Color.decode("#CDCDCD"));
            sp.texto.setFont(new Font("Arial", 0, 16));
            sp.texto.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(1,0,0,0,Color.decode("#414141")),
                    BorderFactory.createEmptyBorder(15, 0, 5, 5)));
        }
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


    protected static void confirmar(){

        int opcion=JOptionPane.showConfirmDialog(null, "Está apunto de eliminar el objeto ¿Está seguro?", "Confirmación de la operación", JOptionPane.OK_CANCEL_OPTION);
        if(opcion==1){
            //TODO:lo elimina
//            System.exit(0);
            System.out.println("Objeto eliminado");
        }else{
//            System.exit(0);
            System.out.println("Operación cancelada");
        }

    }


    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal();
            }
        });
    }

}
