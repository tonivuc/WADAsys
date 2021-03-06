package GUI.common;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BaseWindow extends JFrame {

    protected boolean isLoggedOut;

    //Constructor with title
    public BaseWindow(String windowTitle) {
        super(windowTitle);
        setup();


    }

    //Constructor without title

    public BaseWindow() {
        setup();
        setTitle("Base Window");

    }

    //Things that are common for both constructors, that means: All windows.
    public void setup() {
        //setPreferredSize(new Dimension(1400, 700));
        setLocationRelativeTo(null); //Improvised way to center the window? -Toni
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(createFDImage());
    }

    /*public void repaintAndValidate() {
        validate();
        repaint();
    }*/

    protected void disposeFrame() {
        dispose();
    }

    protected static Image createFDImage() {
        //Create a 16x16 pixel image.
        BufferedImage bi = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);

        //Draw into it.
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 20, 20);
        g.setColor(Color.BLUE);
        g.drawString("BB", 1, 16);
        //g.fillOval(5, 3, 6, 6);

        //Clean up.
        g.dispose();

        //Return it.
        return bi;
    }

}