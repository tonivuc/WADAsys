package GUI.admin;

import GUI.BaseWindow;
import backend.User;
import backend.UserManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by Nora on 21.04.2017.
 */
public class EditUser extends BaseWindow {

    private BaseWindow frame = this;
    private JButton editUserButton;
    private JTextField firstnameField;
    private JTextField lastnameField;
    private JTextField telephoneField;
    private JLabel usernameLabel;
    private JPanel rootPanel;
    private JPasswordField passwordField;
    private JButton deleteUserButton;

    private User user;
    private String username;

    public EditUser(String username, JFrame parentFrame) {
        setLocationRelativeTo(parentFrame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.username = username;
        this.user = new User();
        this.frame = this;

        setTitle("Editing user: "+username);

        Border padding = BorderFactory.createEmptyBorder(100, 100, 100, 100);
        getMainPanel().setBorder(padding);

        firstnameField.setText(user.getFirstname(username));
        lastnameField.setText(user.getLastname(username));
        telephoneField.setText(user.getTelephone(username));
        usernameLabel.setText(username);

        ButtonListener actionListener = new ButtonListener();

        editUserButton.addActionListener(actionListener);
        deleteUserButton.addActionListener(actionListener);

        setContentPane(getMainPanel());
    }

    public class ButtonListener implements ActionListener {
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

                    if (!newFirstname.equals(user.getFirstname(username)))

                    {

                        System.out.println("first name");
                        user.updateInfo(newFirstname, "firstname", username);

                    }

                    if (!newLastname.equals(user.getLastname(username)))

                    {

                        System.out.println("last name");
                        user.updateInfo(newLastname, "lastname", username);

                    }

                    if (!newTelephone.equals(user.getTelephone(username)))

                    {

                        System.out.println("telephone");
                        user.updateInfo(newTelephone, "telephone", username);

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
                    showMessageDialog(frame, "User " + username + " edited");
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

    public JPanel getMainPanel() {
        return rootPanel;
    }

    public static void main(String[] args) {
        EditUser frame = new EditUser("Geirmama",null);
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}
