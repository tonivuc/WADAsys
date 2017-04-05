package GUI.testWindows;

import GUI.analyst.AthleteSearchPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Toni on 29.03.2017.
 */
public class AthleteSearchWindow extends JFrame {

    //private JPanel panel1; //Should actually extend BaseWindow
    //These are connected to AthleteSearchPanel.formow.form

    //Constructor
    public AthleteSearchWindow() {
        setMinimumSize(new Dimension(300,200));
        JPanel changablePanel = new JPanel();
        add(changablePanel);
        pack();

    }

    public static void main(String[] args) {
        AthleteSearchWindow newWindow = new AthleteSearchWindow();
        newWindow.setContentPane(new AthleteSearchPanel().getMainPanel());
        newWindow.setVisible(true);
    }
}
