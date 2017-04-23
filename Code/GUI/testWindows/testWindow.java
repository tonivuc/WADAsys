package GUI.testWindows;

import GUI.BaseWindow;
import junit.framework.Test;

import javax.swing.*;
import java.net.URL;

/**
 * Created by Toni on 18.04.2017.
 */
public class testWindow extends BaseWindow{
    private JButton button1;
    private JPasswordField passwordField1;
    private JPanel mainPanel;

    testWindow() {
        URL location = this.getClass().getProtectionDomain().getCodeSource().getLocation();
        String loc = location.toString();
        //System.out.println(location.getFile());
        //add(new testJPanel())
        //testJPanel panel = new testJPanel();
        //mainPanel.add(panel);
        setContentPane(mainPanel);

    }

    public static void main(String[] args) {

        //BaseWindow window = new BaseWindow("Cool stuff");
        testWindow window = new testWindow();
        //window.setContentPane(new testWindow().mainPanel);
        window.pack();
        window.setVisible(true);
    }
}
