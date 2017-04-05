package GUI.collector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Nora on 30.03.2017.
 */
public class AthletePanelCollector extends JPanel{
    private JButton logOutButton;
    private JPanel map;
    private JPanel rootPanel;
    private JButton newBloodSampleButton;
    private JButton findLocationButton;
    private JTextField dateTextField;


    public JPanel getMainPanel() {
        return rootPanel;
        //To use, use:
        //newPanel.setContentPane(new AthleteSearchPanel().getMainPanel());
    }
}
