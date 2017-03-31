package GUI.analyst;

import GUI.BaseWindow;
import databaseConnectors.SearchHelp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by camhl on 31.03.2017.
 */
public class BaseWindowAnalyst extends BaseWindow {
    private JPanel rootPanel;
    private JButton athleteSearchButton;
    private JButton logOutButton;
    private JButton watchListButton;
    private JButton button4;
    private JPanel topPanel;
    private JPanel midPanel;

    public BaseWindowAnalyst(){

        ButtonListener actionListener = new ButtonListener();

        athleteSearchButton.addActionListener(actionListener);
        watchListButton.addActionListener(actionListener);
        logOutButton.addActionListener(actionListener);

    }

    private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent actionEvent){
            String buttonPressed = actionEvent.getActionCommand();

            if (buttonPressed.equals("Athlete search")) {


            } else if (buttonPressed.equals("Log out")) {
                int option = JOptionPane.YES_NO_OPTION;

                if((JOptionPane.showConfirmDialog (null, "Are you sure you want to log out?","WARNING", option)) == JOptionPane.YES_OPTION){
                    //yes option
                }
                //no option

            } else if (buttonPressed.equals("Watch-list")) {

            }
        }
    }

    public static void main(String[]args){
        JFrame frame = new JFrame("Base Window"); //Creating JFrame
        frame.setContentPane(new BaseWindowAnalyst().rootPanel); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}
