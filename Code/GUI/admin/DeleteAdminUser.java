package GUI.admin;

import backend.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteAdminUser extends JFrame {

    private JButton deleteButton;
    private JPanel searchPanel;
    private JLabel usernameLabel;
    private JLabel firstnameLabel;
    private JLabel lastnameLabel;
    private JLabel telephoneLabel;
    private JPanel rootPanel;

    private User user;
    private String username;

    public DeleteAdminUser(String username) {
        this.username = username;
        this.user = new User();

        usernameLabel.setText(username);
        firstnameLabel.setText(user.getFirstname(username));
        lastnameLabel.setText(user.getLastname(username));
        telephoneLabel.setText(user.getTelephone(username));

        ButtonListener actionListener = new ButtonListener();

        deleteButton.addActionListener(actionListener);

    }

    public class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {

            int confirmation = JOptionPane.showConfirmDialog(null, "First name: " + firstnameLabel.getText().trim() +
                    "\nLast name: " + lastnameLabel.getText().trim() +
                    "\nTelephone number: " + telephoneLabel.getText().trim() +
                    "\nUsername: " + usernameLabel.getText().trim() +
                    "\n \n Are you sure you want to delete this user? ", "Delete user", JOptionPane.YES_NO_OPTION);

            if (confirmation == 0) {    //If the user presses the YES-option

                user.deleteUser(username);

            }
        }
    }



    public static void main(String[] args) {

        JFrame frame = new JFrame("Delete user"); //Creating JFrame
        frame.setContentPane(new DeleteAdminUser("camilhl").rootPanel); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}