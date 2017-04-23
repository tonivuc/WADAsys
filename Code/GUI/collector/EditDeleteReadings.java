package GUI.collector;

import backend.AthleteGlobinDate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Nora on 23.04.2017.
 */
public class EditDeleteReadings {
    private JPanel rootPanel;
    private JButton deleteButton;
    private JButton editButton;
    private JTextField readingField;
    private JLabel dateLabel;
    private String dateString;

    private AthleteGlobinDate athleteGlobinDate;
    private double globinReading;
    private int athleteID;
    private String date;

    public EditDeleteReadings(double globinReading, String date, int athleteID) {
        this.date = date;
        this.globinReading = globinReading;
        this.athleteID = athleteID;

        dateLabel.setText("" + date);
        readingField.setText("" + globinReading);


        ButtonListener actionListener = new ButtonListener();
        editButton.addActionListener(actionListener);
    }

    public class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {

            int confirmation = JOptionPane.showConfirmDialog(null, "Date: " + dateLabel.getText().trim() +
                    "\nHaemoglobin level: " + readingField.getText().trim() +
                    "\n \n Are you sure you want to edit this athlete? ", "Edit user", JOptionPane.YES_NO_OPTION);

            if (confirmation == 0) {    //If the user presses the YES-option
                athleteGlobinDate = new AthleteGlobinDate();  //creates a object of Athlete, so that the user can be added to the Database.

                String newReading = readingField.getText();

                athleteGlobinDate.setup();


                if (!newReading.equals(athleteGlobinDate.getHaemoglobinLevel()))

                {

                    System.out.println("reading");
                    athleteGlobinDate.updateInfo(newReading, "globin_reading", athleteID, date);

                }
                athleteGlobinDate.disconnect();
            }
        }
    }

    public JPanel getMainPanel(){
        return rootPanel;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Edit haemoglobin level"); //Creating JFrame
        frame.setContentPane(new EditDeleteReadings(15, "20170423", 1).rootPanel); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }

}



