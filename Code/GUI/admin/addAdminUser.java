package GUI.admin;
import backend.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

public class addAdminUser {

    private JTextField firstname;
    private JTextField lastname;
    private JTextField telephone;
    private JTextField username;
    private JTextField password;
    private JButton addUserButton1;
    private JPanel addUser;
    private JRadioButton bloodAnalyst;
    private JRadioButton bloodCollectingOfficer;
    private JPanel rootPanel;
    private User user;

    public addAdminUser() {

        ButtonGroup buttonGroup = new ButtonGroup(); //Creating a buttongroup that includes the radiobutton, so that you can only click one of them.
        buttonGroup.add(bloodAnalyst);
        buttonGroup.add(bloodCollectingOfficer);

        bloodAnalyst.setActionCommand(bloodAnalyst.getText());   //Setting actionCommand to be able to return the kind of user in the confirmation message
        bloodCollectingOfficer.setActionCommand(bloodCollectingOfficer.getText());

        addUserButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {  //When the "Add user" button is clicked a confirmaton message will appear showing the users input

                int confirmation = JOptionPane.showConfirmDialog(null, "First name: " + firstname.getText().trim() + "\nLast name: " + lastname.getText().trim() +
                        "\nTelephone number: " + telephone.getText().trim() + "\nUsername: " + username.getText().trim() +
                        "\nPassword: " + password.getText().trim() + "\nUser: " + buttonGroup.getSelection().getActionCommand() +
                        "\n \n Are you sure you want to add this user? ", "Add user", JOptionPane.YES_NO_OPTION);

                if (confirmation == 0) {    //If the user presses the YES-option
                    user = new User();  //creates a object of User, so that the user can be added to the Database.
                    user.setup();    //Setup the connection to the database
                    try {
                        if (buttonGroup.getSelection().getActionCommand().equals("Blood analyst")) {
                            if(user.registerUser(firstname.getText(),
                                                lastname.getText(),
                                                telephone.getText(),
                                                username.getText(),
                                                password.getText(),
                                                "Analyst")) {
                                showMessageDialog(null, "Analyst was registered!");
                            } else {
                                showMessageDialog(null, "Registration failed. Username unavaliable.");
                            }

                        } else {
                            if(user.registerUser(firstname.getText(),
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

                            } else{
                                showMessageDialog(null, "Registration failed. Username unavaliable.");
                            }

                        }

                    } catch (Exception exc) {   //Catching exeption
                        exc.printStackTrace();
                    }
                    user.disconnect();   //closes the connection to the database
                }
            }
        });
    }

    public JPanel getMainPanel(){
        return rootPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Add user"); //Creating JFrame
        frame.setContentPane(new addAdminUser().rootPanel); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }

}

