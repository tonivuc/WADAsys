package GUI;
import javax.swing.*;

/**
 * Created by Toni on 16.03.2017.
 */
public class BaseWindow extends JFrame {

    //Ting og tang som er felles for ALLE vinduene i programme

    public BaseWindow() {
        //Her er det helt tomt gitt!
    }

    public void setupBaseWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}