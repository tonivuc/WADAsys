package GUI.admin;

import backend.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Nora on 21.04.2017.
 */
public class editAdminUser extends JFrame {
    private JButton editUserButton;
    private JTextField firstnameField;
    private JTextField lastnameField;
    private JTextField telephoneField;
    private JLabel usernameLabel;
    private JPanel rootPanel;

    private User user;
    private String username;

    public editAdminUser(String username) {
        this.username = username;
        this.user = new User();

        firstnameField.setText(user.getFirstname(username));
        lastnameField.setText(user.getLastname(username));
        telephoneField.setText(user.getTelephone(username));
        usernameLabel.setText(username);

        ButtonListener actionListener = new ButtonListener();

        editUserButton.addActionListener(actionListener);
    }

    public class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {

            int confirmation = JOptionPane.showConfirmDialog(null, "First name: " + firstnameField.getText().trim() + "\nLast name: " + lastnameField.getText().trim() +
                    "\nTelephone number: " + telephoneField.getText().trim() + "\nUsername: " + usernameLabel.getText().trim() +
                    "\n \n Are you sure you want to edit this user? ", "Edit user", JOptionPane.YES_NO_OPTION);

            if (confirmation == 0) {    //If the user presses the YES-option
                user = new User();  //creates a object of User, so that the user can be added to the Database.

                String newUsername = usernameLabel.getText();
                String newFirstname = firstnameField.getText();
                String newLastname = lastnameField.getText();
                String newTelephone = telephoneField.getText();

                user.setup();

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

                user.disconnect();
            }
        }

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Edit user"); //Creating JFrame
        frame.setContentPane(new editAdminUser("Geirmama").rootPanel); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}
