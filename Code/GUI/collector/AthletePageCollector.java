package GUI.collector;

import GUI.BaseWindow;

import javax.swing.*;

/**
 * Created by Nora on 05.04.2017.
 */
public class AthletePageCollector extends BaseWindow {
    private JButton findLocationButton;
    private JTextField dateField;
    private JPanel rootPanel;
    private JPanel mapPanel;
    private JPanel athleteInfoPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;



    public static void main(String[] args) {
        //athletePanelCollector frame = new athletePanelCollector();
        JFrame frame = new JFrame("Athlete information"); //Creating JFrame
        frame.setContentPane(new AthletePageCollector().rootPanel); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        //newPanel.setContentPane(new AthleteSearchPanel().getMainPanel());
        //frame.setContentPane(new athletePanelCollector().getMainPanel());
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}
