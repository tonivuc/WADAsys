package athleteSearch;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Nora on 30.03.2017.
 */
public class athletePageCollector {
    private JButton logOutButton;
    private JTextField athleteNameSportOrTextField;
    private JPanel rootPanel;
    private JButton findLocationButton;
    private JTextField dateTextField;
    private JTextArea textArea1;


    public athletePageCollector() {
        findLocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Athlete information"); //Creating JFrame
        frame.setContentPane(new athletePageCollector().rootPanel); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
        }

    }
