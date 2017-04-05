package GUI;
import javax.swing.*;
import java.awt.*;

/**
 * Created by Toni on 16.03.2017.
 */
public class BaseWindow extends JFrame {

    //Ting og tang som er felles for ALLE vinduene i programme

    public BaseWindow() {
        //Center window
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setupBaseWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}