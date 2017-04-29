package GUI.analyst;

/**
 *
 * @author Nora Othilie
 */

import backend.Athlete;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Class made to handle the GUI of the edit athlete functionality.
 */

public class EditAthlete {

    private JButton confirmButton;
    private JTextField firstnameField;
    private JTextField lastnameField;
    private JTextField telephoneField;
    private JTextField nationalityField;
    private JComboBox genderComboBox;
    private JLabel athleteIDLabel;
    private JTextField sportField;
    private JPanel rootPanel;
    private JButton cancelButton;

    private Athlete athlete;
    private int athleteID;
    private JFrame parentFrame;

    public EditAthlete(int athleteID, JFrame parentFrame) {
        this.athleteID = athleteID;
        try {
            this.athlete = new Athlete(athleteID);
        }
        catch (SQLException e) {
            showMessageDialog(null, "Error fetching data from database: "+e+ "Source: "+e.getCause(),"Database Error",JOptionPane.ERROR_MESSAGE);
        }
        this.parentFrame = parentFrame;

        parentFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);



        Border padding = BorderFactory.createEmptyBorder(100, 100, 100, 100);
        getMainPanel().setBorder(padding);

        athleteIDLabel.setText("" + athleteID);
        firstnameField.setText(athlete.getFirstname());
        lastnameField.setText(athlete.getLastname());
        telephoneField.setText(athlete.getTelephone());
        nationalityField.setText(athlete.getNationality());
        sportField.setText(athlete.getSport());

        genderComboBox.addItem("Male");
        genderComboBox.addItem("Female");


        ButtonListener actionListener = new ButtonListener();
        confirmButton.addActionListener(actionListener);
        cancelButton.addActionListener(actionListener);
    }

    public class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {

            String buttonPressed = actionEvent.getActionCommand();
            if (buttonPressed.equals("Confirm")) addInput();
            if (buttonPressed.equals("Cancel")) parentFrame.dispose();

        }

        public void addInput(){

            int confirmation = JOptionPane.showConfirmDialog(null, "First name: " + firstnameField.getText().trim() +
                            "\nLast name: " + lastnameField.getText().trim() +
                            "\nTelephone number: " + telephoneField.getText().trim() +
                            "\nNationality: " + nationalityField.getText().trim() +
                            "\nSport: " + sportField.getText().trim() +
                            "\nGender: " + genderComboBox.getSelectedItem() +
                    "\n \n Are you sure you want to edit this athlete? ", "Edit user", JOptionPane.YES_NO_OPTION);

            if (confirmation == 0) {    //If the user presses the YES-option
                //athlete = new Athlete();  //creates a object of Athlete, so that the athlete can be added to the Database.

                String newFirstname = firstnameField.getText();
                String newLastname = lastnameField.getText();
                String newTelephone = telephoneField.getText();
                String newNationality = nationalityField.getText();
                String newSport = sportField.getText();
                String newGender = (String) genderComboBox.getSelectedItem();

                athlete.setup();

                if (!newFirstname.equals(athlete.getFirstname()))

                {

                    System.out.println("first name");
                    athlete.updateInfo(newFirstname, "firstname", athleteID);

                }

                if (!newLastname.equals(athlete.getLastname()))

                {

                    System.out.println("last name");
                    athlete.updateInfo(newLastname, "lastname", athleteID);

                }

                if (!newTelephone.equals(athlete.getTelephone()))

                {

                    System.out.println("telephone");
                    athlete.updateInfo(newTelephone, "telephone", athleteID);

                }

                if (!newNationality.equals(athlete.getNationality()))

                {

                    System.out.println("nationality");
                    athlete.updateInfo(newNationality, "nationality", athleteID);

                }

                if (!newSport.equals(athlete.getSport()))

                {

                    System.out.println("sport");
                    athlete.updateInfo(newSport, "sport", athleteID);

                }

                if (!newGender.equals(athlete.getGender()))

                {

                    System.out.println("gender");
                    athlete.updateInfo(newGender, "gender", athleteID);

                }

                athlete.disconnect();
                parentFrame.dispose();
            }
        }
    }

    public JPanel getMainPanel(){
        return rootPanel;
    }

        public static void main(String[] args) {
        JFrame frame = new JFrame("Edit athlete"); //Creating JFrame
        frame.setContentPane(new EditAthlete(1, new JFrame()).rootPanel); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}
