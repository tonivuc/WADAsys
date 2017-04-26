package GUI.analyst;

import backend.Athlete;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Nora on 22.04.2017.
 */
public class EditAthlete {
    private JButton editButton;
    private JTextField firstnameField;
    private JTextField lastnameField;
    private JTextField telephoneField;
    private JTextField nationalityField;
    private JComboBox genderComboBox;
    private JLabel athleteIDLabel;
    private JTextField sportField;
    private JPanel rootPanel;

    private Athlete athlete;
    private int athleteID;

    public EditAthlete(int athleteID) {
        this.athleteID = athleteID;
        this.athlete = new Athlete();

        Border padding = BorderFactory.createEmptyBorder(100, 100, 100, 100);
        getMainPanel().setBorder(padding);

        athleteIDLabel.setText("" + athleteID);
        firstnameField.setText(athlete.getFirstname(athleteID));
        lastnameField.setText(athlete.getLastname(athleteID));
        telephoneField.setText(athlete.getTelephone(athleteID));
        nationalityField.setText(athlete.getNationality(athleteID));
        sportField.setText(athlete.getSport(athleteID));

        genderComboBox.addItem("Male");
        genderComboBox.addItem("Female");


        ButtonListener actionListener = new ButtonListener();
        editButton.addActionListener(actionListener);
    }

    public class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {

            int confirmation = JOptionPane.showConfirmDialog(null, "First name: " + firstnameField.getText().trim() +
                            "\nLast name: " + lastnameField.getText().trim() +
                            "\nTelephone number: " + telephoneField.getText().trim() +
                            "\nNationality: " + nationalityField.getText().trim() +
                            "\nSport: " + sportField.getText().trim() +
                            "\nGender: " + genderComboBox.getSelectedItem() +
                    "\n \n Are you sure you want to edit this athlete? ", "Edit user", JOptionPane.YES_NO_OPTION);

            if (confirmation == 0) {    //If the user presses the YES-option
                athlete = new Athlete();  //creates a object of Athlete, so that the athlete can be added to the Database.

                String newFirstname = firstnameField.getText();
                String newLastname = lastnameField.getText();
                String newTelephone = telephoneField.getText();
                String newNationality = nationalityField.getText();
                String newSport = sportField.getText();
                String newGender = (String) genderComboBox.getSelectedItem();

                athlete.setup();

                if (!newFirstname.equals(athlete.getFirstname(athleteID)))

                {

                    System.out.println("first name");
                    athlete.updateInfo(newFirstname, "firstname", athleteID);

                }

                if (!newLastname.equals(athlete.getLastname(athleteID)))

                {

                    System.out.println("last name");
                    athlete.updateInfo(newLastname, "lastname", athleteID);

                }

                if (!newTelephone.equals(athlete.getTelephone(athleteID)))

                {

                    System.out.println("telephone");
                    athlete.updateInfo(newTelephone, "telephone", athleteID);

                }

                if (!newNationality.equals(athlete.getNationality(athleteID)))

                {

                    System.out.println("nationality");
                    athlete.updateInfo(newNationality, "nationality", athleteID);

                }

                if (!newSport.equals(athlete.getSport(athleteID)))

                {

                    System.out.println("sport");
                    athlete.updateInfo(newSport, "sport", athleteID);

                }

                if (!newGender.equals(athlete.getGender(athleteID)))

                {

                    System.out.println("gender");
                    athlete.updateInfo(newGender, "gender", athleteID);

                }

                athlete.disconnect();
            }
        }
    }

    public JPanel getMainPanel(){
        return rootPanel;
    }


        public static void main(String[] args) {
        JFrame frame = new JFrame("Edit athlete"); //Creating JFrame
        frame.setContentPane(new EditAthlete(1).rootPanel); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }

}
