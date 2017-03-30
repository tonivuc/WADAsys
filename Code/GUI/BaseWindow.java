package GUI;
import javax.swing.*;

/**
 * Created by Toni on 16.03.2017.
 */
public class BaseWindow extends JFrame {

    //Ting og tang som er felles for ALLE vinduene i programme
    public void init() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}