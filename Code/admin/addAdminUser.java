package admin;
import DatabaseConnection.DatabaseManager;
import login.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class addAdminUser extends DatabaseManager {

    private JButton addUserButton;
    private JButton logOutButton;
    private JButton editUserButton;
    private JButton deleteUserButton;
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

    public addAdminUser() {

        // hallo

        ButtonGroup buttonGroup = new ButtonGroup(); //Creating a buttongroup that includes the radiobutton, so that you can only click one of them.
        buttonGroup.add(bloodAnalyst);
        buttonGroup.add(bloodCollectingOfficer);

        bloodAnalyst.setActionCommand(bloodAnalyst.getText());   //Setting actionCommand to be able to return the kind of user in the confirmation message
        bloodCollectingOfficer.setActionCommand(bloodCollectingOfficer.getText());

        addUserButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {  //When the "Add user" button is clicked a confirmaton message will appear showing the users input

                User user = new User();
                if(user.findUser(username.getText()) == true){
                    JOptionPane.showMessageDialog(null, "Username is unavailable");
                } else {

                    int confirmation = JOptionPane.showConfirmDialog(null, "First name: " + firstname.getText().trim() + "\nLast name: " + lastname.getText().trim() +
                            "\nTelephone number: " + telephone.getText().trim() + "\nUsername: " + username.getText().trim() +
                            "\nPassword: " + password.getText().trim() + "\nUser: " + buttonGroup.getSelection().getActionCommand() +
                            "\n \n Are you sure you want to add this user? ", "Add user", JOptionPane.YES_NO_OPTION);

                    if (confirmation == 0) {    //If the user presses the YES-option

                        setup();
                        try {
                            if (buttonGroup.getSelection().getActionCommand().equals(bloodAnalyst)) {

                                registerUser("Analyst");

                            } else {
                                registerUser("Collector");
                            }

                        } catch (Exception exc) {   //Catching exeption
                            exc.printStackTrace();
                        }
                        disconnect();
                    }
                }
            }
        });
    }

    public void registerUser(String usertype){
        try {

            String query = "INSERT INTO User"               //Adding user into the "User"-table in the database
                    + "(firstname, lastname, telephone, username, password)"   //Adding first name, last name, telephone, username, password
                    + "VALUES (?,?,?,?,?)";       //The values comes from user-input


            //getStatement().executeQuery(query);
            PreparedStatement preparedStmt = getConnection().prepareStatement(query);  //Adding the user into the database, getting the users input
            preparedStmt.setString(1, firstname.getText().trim());
            preparedStmt.setString(2, lastname.getText().trim());
            preparedStmt.setInt(3, Integer.parseInt(telephone.getText().trim()));
            preparedStmt.setString(4, username.getText().trim());
            preparedStmt.setString(5, password.getText().trim());

            preparedStmt.execute();

            String query2 = "INSERT INTO "
                    + usertype
                    + "(username)"
                    + "VALUES (?)";
            PreparedStatement preparedStmt2 = getConnection().prepareStatement(query2);
            preparedStmt2.setString(1, username.getText());

            //Executing the prepared statement
            preparedStmt2.execute();

            getConnection().close(); //Closing connection
        }catch(Exception e){
            System.out.println("REGISTERUSER: Something went wrong." + e.toString());
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        JFrame frame=new JFrame("Add user"); //Creating JFrame
        frame.setContentPane(new addAdminUser().rootPanel); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible// }
        }

}

