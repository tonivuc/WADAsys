import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Created by Nora on 22.03.2017.
 */
public class addAdminUser{
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
    private String button = null;
    private JRadioButton bloodAnalyst;
    private JRadioButton bloodCollectingOfficer;


    public addAdminUser() {
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(bloodAnalyst);
        buttonGroup.add(bloodCollectingOfficer);
        bloodAnalyst.setActionCommand(bloodAnalyst.getText());
        bloodCollectingOfficer.setActionCommand(bloodCollectingOfficer.getText());

        addUserButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int confirmation = JOptionPane.showConfirmDialog(null, "First name: " + firstname.getText() + "\nLast name: " + lastname.getText() +
                        "\nTelephone number: " + telephone.getText() + "\nUsername: " + username.getText() +
                        "\nPassword: " + password.getText() + "\nUser: " + buttonGroup.getSelection().getActionCommand() +
                        "\n \n Are you sure you want to add this user? ", "Add user", JOptionPane.YES_NO_OPTION);

                if (confirmation == 0) {
                    try {
                        String databaseDriver = "com.mysql.jdbc.Driver";
                        String databaseName = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/toniv?user=toniv&password=kuanZ4Yk";
                        Class.forName(databaseDriver);
                        Connection myConnection = DriverManager.getConnection(databaseName);
                        Statement myStatement = myConnection.createStatement();

                        String sql = "Insert into User"
                                + "(firstname, lastname, telephone, username, password)"
                                + "Values (?,?,?,?,?)";
                                + "Insert into analyst"
                                        + "(firstname, lastname, telephone, username, password)"
                                        + "Values (LAST_INSERT_ID())";

                        PreparedStatement preparedStmt = myConnection.prepareStatement(sql);
                        preparedStmt.setString(1, firstname.getText());
                        preparedStmt.setString(2, lastname.getText());
                        preparedStmt.setInt(3, Integer.parseInt(telephone.getText()));
                        preparedStmt.setString(4, username.getText());
                        preparedStmt.setString(5, password.getText());

                        // execute the preparedstatement
                        preparedStmt.execute();

                        myConnection.close();

                    } catch (Exception exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Add user");
        frame.setContentPane(new addAdminUser().addUser);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
