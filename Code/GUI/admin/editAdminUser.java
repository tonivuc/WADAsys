/*package admin;
import DatabaseConnection.DatabaseManager;
import login.User;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;

public class editAdminUser extends DatabaseManager{
    private JButton addUserButton;
    private JButton deleteUserButton;
    private JButton editUserButton;
    private JButton logOutButton;
    private JPanel editUser;
    private JTextField searchJTextField;
    private JScrollPane scrollPane;
    User user = new User();





    /*private JTable table;

    table = new JTable();

    public editAdminUser() {

        searchJTextField = new JTextField();
        searchJTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try{
                    String query = "select * from User where name = ?";
                    PreparedStatement preparedStmt = myConnection.prepareStatement(query);
                    preparedStmt.setString(1, searchJTextField.getText() );
                    ResultSet resultSet = preparedStmt.executeQuery();

                    while (resultSet.next()) {
                    }

                    preparedStmt.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                super.keyReleased(e);
            }
        });
    }
}
*/