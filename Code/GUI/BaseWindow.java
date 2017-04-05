package GUI;
import javax.swing.*;
import java.awt.*;

/**
 * Created by Toni on 16.03.2017.
 */
public class BaseWindow extends JFrame {
    protected boolean isLoggedOut;

    //Ting og tang som er felles for ALLE vinduene i programme

    public BaseWindow() {

        setSize(600, 600);
        setLocation(700, 300);
        //Center window
        //this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setupBaseWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}