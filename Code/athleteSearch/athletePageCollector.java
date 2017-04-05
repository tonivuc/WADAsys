package athleteSearch;

import GUI.BaseWindow;
import GUI.analyst.AthleteSearchPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Nora on 30.03.2017.
 */
public class athletePageCollector extends BaseWindow {
    private JButton logOutButton;
    private JPanel rootPanel;
    private JButton findLocationButton;
    private JTextField dateTextField;
    private JTextArea textArea1;
    private JPanel search;

    public athletePageCollector() {
        this.search = new AthleteSearchPanel().getMainPanel();
        findLocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
    }

    public JPanel getMainPanel(){
        return rootPanel;
    }

    public static void main(String[] args) {
        athletePageCollector frame = new athletePageCollector();
        //JFrame frame = new JFrame("Athlete information"); //Creating JFrame
        //frame.setContentPane(new athletePageCollector().rootPanel); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        //newPanel.setContentPane(new AthleteSearchPanel().getMainPanel());
        frame.setContentPane(new athletePageCollector().getMainPanel());
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
        }


 