package GUI.admin;

import javax.swing.*;

public class deleteAdminUser {
    JPanel panel1;
    JList list1;
    JButton button1;
    JTextField textField1;

    public deleteAdminUser() {

    }

    public JPanel getMainPanel(){
        return panel1;
    }

    public static void main(String[] args){

       /* deleteAdminUser window = new deleteAdminUser();
        window.setVisible(true);*/

        JFrame frame = new JFrame("Add user"); //Creating JFrame
        frame.setContentPane(new deleteAdminUser().panel1); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}