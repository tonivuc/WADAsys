package GUI.collector;

import javax.swing.*;

/**
 * Created by Nora on 23.04.2017.
 */
public class EditDeleteReadings {
    private JPanel rootPanel;
    private JButton deleteButton;
    private JButton editButton;
    private JTextField dateField;
    private JTextField readingField;

    public EditDeleteReadings(double globinReading, java.sql.Date date, int athleteID) {
        //this.athleteID = athleteID;

        //dateField.setText(athlete.getDate(athleteID));
        //readingField.setText(athlete.getLastname(athleteID));

       /* ButtonListener actionListener = new ButtonListener();
        editButton.addActionListener(actionListener);
    }

    public class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {

            int confirmation = JOptionPane.showConfirmDialog(null, "Date: " + dateField.getText().trim() +
                    "\nHaemoglobin level: " + readingField.getText().trim() +
                    "\n \n Are you sure you want to edit this athlete? ", "Edit user", JOptionPane.YES_NO_OPTION);

            if (confirmation == 0) {    //If the user presses the YES-option
                //athlete = new Athlete();  //creates a object of Athlete, so that the user can be added to the Database.

                String newDate = dateField.getText();
                String newReading = readingField.getText();

                //athlete.setup();

                if (!newDate.equals(athlete.getDate(athleteID)))

                {

                    System.out.println("date");
                    athlete.updateInfo(newDate, "firstname", athleteID);

                }

                if (!newReading.equals(athlete.getReading(athleteID)))

                {

                    System.out.println("reading");
                    athlete.updateInfo(newReading, "lastname", athleteID);

                }
                //athlete.disconnect();
            }
        }*/
    }

    public JPanel getMainPanel(){
        return rootPanel;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Edit haemoglobin level"); //Creating JFrame
        frame.setContentPane(new EditDeleteReadings(13.4, new java.sql.Date(20170422), 1).rootPanel); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }

}



