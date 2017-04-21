package GUI.admin;

import GUI.BaseWindow;
import backend.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by camhl on 21.04.2017.
 */
public class Profile extends BaseWindow{
    private JPanel panel1;
    private JTextField currentPasswordPanel;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton updatePasswordButton;
    private JButton editInformationButton;
    private JTextField usernameField;
    private JTextField firstnameField;
    private JTextField telephoneField;
    private JTextField lastnameField;


    private User user;
    private String username;

    public Profile(String username){
        this.username = username;
        this.user = new User();

        usernameField.setText(username);
        user.getName(username);
        firstnameField.setText(user.getFirstname());
        lastnameField.setText(user.getLastname());
        telephoneField.setText(user.getTelephone(username));

        ButtonListener actionListener = new ButtonListener();

        updatePasswordButton.addActionListener(actionListener);
        editInformationButton.addActionListener(actionListener);


    }

    public class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent actionEvent) {
            String buttonPressed = actionEvent.getActionCommand();

            if(buttonPressed.equals("Edit information")){

                String newUsername = usernameField.getText();
                String newFirstname = firstnameField.getText();
                String newLastname = lastnameField.getText();
                String newTelephone = telephoneField.getText();

                user.setup();

                if(!newUsername.equals(username)){

                    System.out.println("username");
                    user.updateInfo(newUsername, "username", username);

                }

                if(!newFirstname.equals(user.getName(username))){

                    System.out.println("name");
                    //user.updateInfo(newName, "firstname", username);

                }

                if(!newTelephone.equals(user.getTelephone(username))){

                    System.out.println("telephone");


                }

                user.disconnect();



            }

            if(buttonPressed.equals("Update password")){

                int confirmation = showConfirmDialog(null, "Are you sure you want to update password?", "WARNING", JOptionPane.YES_NO_OPTION);
                if(confirmation == 0){ //yes option

                    String password1 = user.fromCharToString(passwordField1.getPassword());
                    String password2 = user.fromCharToString(passwordField2.getPassword());

                    String currentPassword = currentPasswordPanel.getText();

                    user.setup();

                    if(user.updatePassword(currentPassword, password1, password2, username)){
                        showMessageDialog(null, "Password updated!");
                        passwordField1.setText("");
                        passwordField2.setText("");
                        currentPasswordPanel.setText("");

                    }
                    else{
                        showMessageDialog(null, "Password pas not updated. \nPlease check that your input is correct.");
                    }

                    user.disconnect();
                }
            }


        }

        public JPanel getMainPanel(){
            return panel1;
        }
    }

    public static void main(String[] args) {
        //athletePanelCollector frame = new athletePanelCollector();
        JFrame frame = new JFrame("Athlete information"); //Creating JFrame
        frame.setContentPane(new Profile("Geirmama").panel1); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        //newPanel.setContentPane(new AthleteSearchPanel().getMainPanel());
        //frame.setContentPane(new athletePanelCollector().getMainPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}
