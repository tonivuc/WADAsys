package GUI.admin;

/**
 *
 * @author Nora Othilie
 */

import backend.UserManager;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Class made to handle GUI associated with adding a new user into the system.
 */
public class AddUser {

    /**
     * The panel that everything is inside except from the rootpanel/mainpanel.
     */
    private JPanel addUser;

    /**
     * JTextField where the Admin writes the first name of the user he/she wants to add.
     */
    private JTextField firstname;

    /**
     * JTextField where the Admin writes the last name of the user he/she wants to add.
     */
    private JTextField lastname;

    /**
     * JTextField where the Admin writes the telephone number of the user he/she wants to add.
     */
    private JTextField telephone;

    /**
     * JTextField where the Admin writes the username of the user he/she wants to add.
     */
    private JTextField username;

    /**
     * JTextField where the Admin writes the password of the user he/she wants to add.
     */
    private JTextField password;

    /**
     * JButton that the Admin presses when he/she have inserted all the info he/she needs to add a new user.
     */
    private JButton addUserButton1;

    /**
     * JRadioButton that the Admin selects if he/she wants the new user to be a blood analyst.
     */
    private JRadioButton bloodAnalyst;

    /**
     * JRadioButton that the Admin selects if he/she wants the new user to be a blood collecting officer.
     */
    private JRadioButton bloodCollectingOfficer;

    /**
     * The rootPanel (mainPanel) that you add in other JFrames to show the AddUser Panel.
     */
    private JPanel rootPanel;
    private JRadioButton adminRadioButton;

    /**
     * Method takes input from the Admin (firstname, lastname, telephone, username and password) that is needed
     * to add a user to the database. When the add user button is pressed, a confirm dialog will pop up asking
     * if the information is correct. If the Admin confirms, a user object will be created. It will add the user
     * to the User table in the database as well as the table for blood collecting officers or doping analysts.
     * The username must available because username is primary key.
     */
    public AddUser() {

        ButtonGroup buttonGroup = new ButtonGroup(); //Creating a buttongroup that includes the radiobutton, so that you can only click one of them.
        buttonGroup.add(bloodAnalyst);
        buttonGroup.add(bloodCollectingOfficer); //
        buttonGroup.add(adminRadioButton);

        Border padding = BorderFactory.createEmptyBorder(100, 100, 100, 100);
        getMainPanel().setBorder(padding);

        bloodAnalyst.setActionCommand(bloodAnalyst.getText());   //Setting actionCommand to be able to return the kind of user in the confirmation message
        bloodCollectingOfficer.setActionCommand(bloodCollectingOfficer.getText());
        adminRadioButton.setActionCommand(adminRadioButton.getText());

        addUserButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {  //When the "Add user" button is clicked a confirmaton message will appear showing the users input
                if(firstname.getText().equals("") || lastname.getText().equals("") || telephone.getText().equals("") ||
                        username.getText().equals("") || password.getText().equals("")){
                    showMessageDialog(null, "You can not leave a field empty. \n\n Please try again");
                }

                int confirmation = JOptionPane.showConfirmDialog(null, "First name: " + firstname.getText().trim() +
                        "\nLast name: " + lastname.getText().trim() +
                        "\nTelephone number: " + telephone.getText().trim() +
                        "\nUsername: " + username.getText().trim() +
                        "\nPassword: " + password.getText().trim() +
                        "\nUser: " + buttonGroup.getSelection().getActionCommand() +
                        "\n \n Are you sure you want to add this user? ", "Add user", JOptionPane.YES_NO_OPTION);

                if (confirmation == 0) {    //If the user presses the YES-option
                    UserManager userManager = new UserManager();
                    try {
                        int telephoneInt = Integer.parseInt(telephone.getText());

                        if (buttonGroup.getSelection().getActionCommand().equals("Blood analyst")) {
                            if (userManager.registerUser(firstname.getText(),
                                    lastname.getText(),
                                    telephone.getText(),
                                    username.getText(),
                                    password.getText(),
                                    "Analyst")) {
                                showMessageDialog(null, "Analyst was registered!");
                                firstname.setText("");
                                lastname.setText("");
                                telephone.setText("");
                                username.setText("");
                                password.setText("");
                            } else {
                                showMessageDialog(null, "Registration failed. Username unavaliable.");
                            }
                        }

                        if(buttonGroup.getSelection().getActionCommand().equals("Blood collecting officer")) {
                            if (userManager.registerUser(firstname.getText(),
                                    lastname.getText(),
                                    telephone.getText(),
                                    username.getText(),
                                    password.getText(),
                                    "Collector")) {
                                showMessageDialog(null, "Collector was registered!");
                                firstname.setText("");
                                lastname.setText("");
                                telephone.setText("");
                                username.setText("");
                                password.setText("");
                            } else {
                                showMessageDialog(null, "Registration failed. Username unavaliable.");
                            }
                        }

                        if(buttonGroup.getSelection().getActionCommand().equals("Admin")) {
                            if (userManager.registerUser(firstname.getText(),
                                    lastname.getText(),
                                    telephone.getText(),
                                    username.getText(),
                                    password.getText(),
                                    "Admin")) {
                                showMessageDialog(null, "Admin was registered!");
                                firstname.setText("");
                                lastname.setText("");
                                telephone.setText("");
                                username.setText("");
                                password.setText("");

                            } else {
                                showMessageDialog(null, "Registration failed. Username unavaliable.");
                            }
                        }



                    } catch(NumberFormatException nfe) {
                        showMessageDialog(null, "Telephonenumber must be a 8 digits number. \n\nPlease try again.");

                    } catch (Exception exc) {   //Catching exeption
                        exc.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Method returns the rootPanel so the AddUser window will be shown after pressing the Add user button
     * in other windows in the program.
     * @return JPanel
     */
    public JPanel getMainPanel(){
        return rootPanel;
    }

}

