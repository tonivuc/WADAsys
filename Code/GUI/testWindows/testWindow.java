package GUI.testWindows;

import GUI.BaseWindow;

import javax.swing.*;

/**
 * Created by Toni on 18.04.2017.
 */
public class testWindow extends BaseWindow{
    private JButton button1;
    private JPasswordField passwordField1;
    private JPanel mainPanel;

    testWindow() {

        //VIKTIG, ALLE KONSTRUKTØRER SOM BRUKER FORMS MÅ HA DETTE
        setContentPane(mainPanel);
    }

    public static void main(String[] args) {
        //BaseWindow window = new BaseWindow("Cool stuff");
        testWindow window = new testWindow();
        //window.setContentPane(new testWindow().mainPanel);
        window.setVisible(true);
    }
}
