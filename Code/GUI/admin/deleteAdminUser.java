package admin;
import backend.Athlete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static com.sun.glass.ui.Cursor.setVisible;

public class deleteAdminUser extends JFrame{
    private JButton addUserButton;
    private JButton editUserButton;
    private JButton deleteUserButton;
    private JButton logOutButton;
    private JTextField searchTextField;
    private JPanel rootpanel;
    private JButton searchButton;
    private JButton deleteButton;
    private JScrollPane AthleteList;
    private JPanel MidPanel;

    public deleteAdminUser() {

    searchButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });

    }

    public static void main(String[] args){

       /* deleteAdminUser window = new deleteAdminUser();
        window.setVisible(true);*/

        JFrame frame = new JFrame("Add user"); //Creating JFrame
        frame.setContentPane(new deleteAdminUser().rootpanel); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}