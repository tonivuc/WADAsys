package GUI.admin;

import GUI.BaseWindow;
import backend.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class deleteAdminUser extends BaseWindow {

    private User user;
    private JPanel rootpanel;
    private JButton deleteButton;
    private JPanel searchPanel;

    //UNFINISHED: We will have search panel here similar to the AthleteSearchPanel
    public deleteAdminUser() {

    }

    public JPanel getMainPanel() {
        return rootpanel;
    }

    public static void main(String[] args){

        JFrame frame = new JFrame("Add user"); //Creating JFrame
        frame.setContentPane(new deleteAdminUser().getMainPanel()); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}