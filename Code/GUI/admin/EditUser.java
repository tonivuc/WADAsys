package GUI.admin;

/**
 *
 * @author Nora Othilie
 */

import GUI.common.BaseWindow;
import backend.User;
import backend.UserManager;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Class that is made to handle GUI associated with editing a user.
 */
public class EditUser extends JFrame {

    private EditUser frame = this;

    /**
     * The the button the Admin presses if the he/she wants to edit a user.
     */
    private JButton editUserButton;

    /**
     * Info entry field for first name.
     */
    private JTextField firstnameField;

    /**
     * Info entry field for last name.
     */
    private JTextField lastnameField;

    /**
     * Info entry field for telephone number.
     */
    private JTextField telephoneField;

    /**
     * The label that displays the username of the user that the Admin wants to edit.
     */
    private JLabel usernameLabel;

    /**
     * The mainPanel/rootPanel that everything is contained within.
     */
    private JPanel rootPanel;

    /**
     * Info entry field for the password.
     */
    private JPasswordField passwordField;

    /**
     * The button that the Admin presses if he/she wants to delete the selected user.
     */
    private JButton deleteUserButton;

    /**
     * A User Object.
     */
    private User user;

    /**
     * The username of the user that the Admin has Selected.
     */
    private String username;

    /**
     * Constructs a new EditUser Object, that creates a EditUser panel that is used
     * to edit a specific user.
     * @param username The username of the user that the Admin wants to edit/delete.
     * @param parentFrame the parent frame of this panel.
     */
    public EditUser(String username, JFrame parentFrame) {
        setLocationRelativeTo(parentFrame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.username = username;
        this.user = new User(username);
        this.frame = this;

        setTitle("Editing user: "+username);

        Border padding = BorderFactory.createEmptyBorder(100, 100, 100, 100);
        getMainPanel().setBorder(padding);

        firstnameField.setText(user.getFirstname());
        lastnameField.setText(user.getLastname());
        telephoneField.setText(user.getTelephone());
        usernameLabel.setText(username);

        ButtonListener actionListener = new ButtonListener();

        editUserButton.addActionListener(actionListener);
        deleteUserButton.addActionListener(actionListener);

        setContentPane(getMainPanel());
    }

    public class ButtonListener implements ActionListener {

        /**
         * The ActionListener that checks if a user har pressed any buttons.
         * @param actionEvent event of the action
         */
        public void actionPerformed(ActionEvent actionEvent) {
            UserManager userManager = new UserManager();

            if (actionEvent.getSource().equals(editUserButton)) {

                //Confirmation message asking if the data is correct.

                int confirmation = JOptionPane.showConfirmDialog(frame, "First name: " + firstnameField.getText().trim() +
                        "\nLast name: " + lastnameField.getText().trim() +
                        "\nTelephone number: " + telephoneField.getText().trim() +
                        "\nUsername: " + usernameLabel.getText().trim() +
                        "\nPassword: hidden" +
                        "\n \n Are you sure you want to edit this user? ", "Edit user", JOptionPane.YES_NO_OPTION);

                if (confirmation == 0) {    //If the user presses the YES-option
                    user = new User(username);  //creates a object of User, so that the new information can be added to the Database.

                    String newFirstname = firstnameField.getText();
                    String newLastname = lastnameField.getText();
                    String newTelephone = telephoneField.getText();
                    String newPassword = new String(passwordField.getPassword());

                    user.setup();  //Sets up connection to the database

                    if (!newFirstname.equals(user.getFirstname()))

                    {

                        System.out.println("first name");
                        user.updateInfo(newFirstname, "firstname");

                    }

                    if (!newLastname.equals(user.getLastname()))

                    {

                        System.out.println("last name");
                        user.updateInfo(newLastname, "lastname");

                    }

                    if (!newTelephone.equals(user.getTelephone()))

                    {

                        System.out.println("telephone");
                        user.updateInfo(newTelephone, "telephone");

                    }
                    System.out.println(newPassword);
                    if (newPassword == null || newPassword.equals("")) {

                    } else {
                        if (!userManager.checkPassword(newPassword, username)) {
                            int confirmation2 = showConfirmDialog(frame, "Are you sure you want to update password?", "WARNING", JOptionPane.YES_NO_OPTION);
                            if (confirmation2 == 0) { //yes option

                                if (user.updatePassword(newPassword)) {
                                    showMessageDialog(frame, "Password updated!");

                                } else {
                                    showMessageDialog(frame, "Password pas not updated. \nPlease check that your input is correct.");
                                }

                            }
                        } else {
                            showMessageDialog(frame, "New password is the same as the old password.");
                        }

                    }

                    user.disconnect();  //Disconnects from the database
                    dispose();
                }

                //Confirmation message asking if the user is sure it wants to delete a user

            } else if (actionEvent.getSource().equals(deleteUserButton)) {
                int confirmation = JOptionPane.showConfirmDialog(frame, "First name: " + firstnameField.getText().trim() +
                        "\nLast name: " + lastnameField.getText().trim() +
                        "\nTelephone number: " + telephoneField.getText().trim() +
                        "\nUsername: " + usernameLabel.getText().trim() +
                        "\nPassword: hidden" +
                        "\n \n Are you sure you want to delete this user? ", "Delete user", JOptionPane.YES_NO_OPTION);

                if (confirmation == 0) {    //If the user presses the YES-option

                    userManager.deleteUser(username);
                    showMessageDialog(frame, "User "+username +" has been deleted.");
                    frame.dispose();

                }
            }


        }
    }

    /**
     * Returns the rootPanel/mainPanel.
     * @return JPanel
     */
    public JPanel getMainPanel() {
        return rootPanel;
    }

}
