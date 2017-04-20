package GUI.analyst;

import GUI.BaseWindow;
import backend.Athlete;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import GUI.BaseWindow;
import backend.Athlete;
import backend.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showInputDialog;

/**
 * Created by Nora on 05.04.2017.
 */
/*public class NewBloodSample extends BaseWindow {

    private JTextField haemoglobinLevelTextField;
    private JTextField dateTextField;
    private JButton submitButton;
    private JPanel rootPanel;


    public NewBloodSample() {

        Buttonlistener actionListener = new Buttonlistener();


    }



    private class Buttonlistener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            String buttonPressed = actionEvent.getActionCommand();

            if(buttonPressed.equals("Submit")){

                String dateString = dateTextField.getText();
                String haemoglobinlevelString = haemoglobinLevelTextField.getText();

                int confirmation = showConfirmDialog(null, "Haemoglobin level : " + haemoglobinlevelString
                + "\nDate: " + dateString + "\n\nAre you sure you want to add haemoglobin level?", "Submit", JOptionPane.YES_NO_OPTION);

                if (confirmation == 0){ //yes



                }



            }

        }
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Athlete information"); //Creating JFrame
        frame.setContentPane(new NewBloodSample().rootPanel); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        //newPanel.setContentPane(new AthleteSearchPanel().getMainPanel());
        //frame.setContentPane(new athletePanelCollector().getMainPanel());
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}*/