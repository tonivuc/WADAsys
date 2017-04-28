package GUI.testWindows;

import GUI.common.BaseWindow;

import javax.swing.*;

/**
 * Created by Toni on 20.04.2017.
 */
public class testJPanel extends JPanel{
    private JLabel hiLabel;
    private JTextField mainTextfield;
    private JPanel mainPanel;
    private int niceInt = 0;

    testJPanel() {
        add(mainPanel);
        niceInt = 2;
    }

    public int getNiceInt() {
        return niceInt;
    }

    public static void main(String[] args) {
        BaseWindow window = new BaseWindow();
        window.add(new testJPanel());
        window.pack();
        window.setVisible(true);
    }
}
