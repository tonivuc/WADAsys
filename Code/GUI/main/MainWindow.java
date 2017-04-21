package GUI.main;

import GUI.admin.BaseWindowAdmin;
import GUI.analyst.BaseWindowAnalyst;
import GUI.collector.BaseWindowCollector;
import GUI.login.LoginWindow;
import backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Created by camhl on 04.04.2017. Rewritten by toniv 17.04.2017-18.04.2017.
 * A few notes:
 * To create any of the windows (Analyst, Collector, Admin) simply do new 'WindowName', the rest is handled in the constructor
 * To use any of the JPanels (which are in fact no longer JPanels due to the way GUI forms work), use: JPanel panel = new 'Panelname'().getMainPanel()
 */

public class MainWindow implements ActionListener{

    private LoginWindow frame;

    /**
     * Constructor. Creates the main window for the program using a
     * LoginWindow.
     */
    public MainWindow() {
        //We are using the listener we created here, in the LoginWindow class, and can thus can acces it here.
        frame = new LoginWindow("Login", this::actionPerformed);
    }

    /**
     * Main logic of the MainWindow is driven by the ActionEvent
     * fired from the submitButton in LoginWindow.
     *
     * @param e ActionEvent passed into the LoginWindow that is caught by this function once fired.
     */
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("ActionEvent intercepted by MainWindow");
        //FEATURE REQUEST: Check the origin of the ActionEvent. (f.eks. e.getSource())
        //Logs in using the credentials the user typed into the text fields
        frame.performLogin();

        //Checks if logged in
        if (frame.isLoggedin()) {

            String loginType = new User().findUserByIndex(frame.getLoginType());

            if (loginType.equals("Analyst")) {

                System.out.println("Analyst was logged in");

                BaseWindowAnalyst analystWindow = new BaseWindowAnalyst();
                frame.dispose();  //Creates a window out of all the components

            } else if (loginType.equals("Collector")) {

                System.out.println("Collector was logged in");

                BaseWindowCollector collectorWindow = new BaseWindowCollector(); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
                frame.dispose();
                //frame.setVisible(false);  //Creates a window out of all the components

            } else if (loginType.equals("Admin")) {

                System.out.println("Admin was logged in");

                BaseWindowAdmin adminWindow = new BaseWindowAdmin(); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
                frame.dispose();  //Creates a window out of all the components

            }

        }
        // display/center the jdialog when the button is pressed
    }



    public static void main(String[] args) {
        MainWindow mainFrame = new MainWindow();
    }

}