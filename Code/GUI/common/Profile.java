package GUI.common;

import backend.User;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author Camilla Haaheim Larsen
 */

public class Profile extends JFrame{

    /**
     * The mainPanel/rootPanel that everything is contained in.
     */
    private JPanel panel1;

    /**
     * Field where the user enters his/her current password.
     */
    private JTextField currentPasswordPanel;

    /**
     * Field where the user enters his/her new password.
     */
    private JPasswordField passwordField1;

    /**
     * Field where the user enters his/her new password again for confirmation.
     */
    private JPasswordField passwordField2;

    /**
     * Button the user presses if he/she wants to update his/her password.
     */
    private JButton updatePasswordButton;

    /**
     * Button the user presses if he/she wants to update his/her infomation.
     */
    private JButton editInformationButton;

    /**
     * The field where the user can edit his/her first name.
     */
    private JTextField firstnameField;

    /**
     * The field where the user can edit his her telephone number.
     */
    private JTextField telephoneField;

    /**
     * The field where the user can edit his/her last name.
     */
    private JTextField lastnameField;

    /**
     * The label that displays the users username.
     */
    private JLabel usernameLabel;

    /**
     * User Object of the user that is logged in.
     */
    private User user;

    /**
     * The username of the user that is logged in.
     */
    private String username;

    /**
     * Constructs a new Profile panel where the user can edit her information or make a new password.
     * @param username The username of the logged in user.
     */
    public Profile(String username){
        this.username = username;
        this.user = new User(username);

        //Setting padding around the frame
        Border padding = BorderFactory.createEmptyBorder(0, 100, 50, 100);
        getMainPanel().setBorder(padding);

        usernameLabel.setText(username);
        //user.getName(username);
        firstnameField.setText(user.getFirstname());
        lastnameField.setText(user.getLastname());
        telephoneField.setText(user.getTelephone());

        ButtonListener actionListener = new ButtonListener();

        updatePasswordButton.addActionListener(actionListener);
        editInformationButton.addActionListener(actionListener);


    }

    public class ButtonListener implements ActionListener {

        /**
         * Checks if a button was pressed.
         * @param actionEvent event.
         */
        public void actionPerformed(ActionEvent actionEvent) {
            String buttonPressed = actionEvent.getActionCommand();

            if (buttonPressed.equals("Edit information")) {

                String newFirstname = firstnameField.getText();
                String newLastname = lastnameField.getText();
                String newTelephone = telephoneField.getText();


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

                if (buttonPressed.equals("Update password")) {

                    int confirmation = showConfirmDialog(null, "Are you sure you want to update password?", "WARNING", JOptionPane.YES_NO_OPTION);
                    if (confirmation == 0) { //yes option

                        String password1 = user.fromCharToString(passwordField1.getPassword());
                        String password2 = user.fromCharToString(passwordField2.getPassword());

                        String currentPassword = currentPasswordPanel.getText();

                        user.setup();

                        if (user.updatePassword(currentPassword, password1, password2)) {
                            showMessageDialog(null, "Password updated!");
                            passwordField1.setText("");
                            passwordField2.setText("");
                            currentPasswordPanel.setText("");

                        } else {
                            showMessageDialog(null, "Password pas not updated. \nPlease check that your input is correct.");
                        }

                        user.disconnect();
                    }
                }
            }
        }
    }

    /**
     * Returns the mainPanel/rootPanel.
     * @return JPanel
     */
    public JPanel getMainPanel(){
            return panel1;
        }

}
