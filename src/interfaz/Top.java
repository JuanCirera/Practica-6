package interfaz;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Top extends javax.swing.JPanel{

    protected JLabel titulo;

    public Top(Color bg) {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBackground(bg);
        this.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.decode("#414141")));

//        c.gridx=0; //Esto es para posicionar el componente en el grid. Este esta en la 0,0
//        c.gridy=0;
//        c.weightx = 0;
//        c.weighty = 1;
        titulo=new JLabel();
        this.add(titulo);

    }


    protected void setStyle(String title, int size, String fg, int paddingTop, int paddingLeft){
        titulo.setText(title);
        titulo.setFont(new Font("Tahoma", Font.BOLD, size));
        titulo.setForeground(Color.decode(fg));
        titulo.setBorder(new EmptyBorder(paddingTop, paddingLeft, 0, 0));
    }


    @Override
    protected void paintComponent(Graphics g) {
        BufferedImage image=null;
        try {
            image = ImageIO.read(new File("src/img/cruz_roja.png"));
        } catch (IOException e) {
            System.out.println("Error al cargar la imagen del banner");
        }
        super.paintComponent(g);
        g.drawImage(image, 15, 0, this); // see javadoc for more info on the parameters
    }

}
