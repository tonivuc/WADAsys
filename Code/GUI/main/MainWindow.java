package GUI.main;

import GUI.analyst.BaseWindowAnalyst;
import GUI.collector.BaseWindowCollector;
import GUI.login.LoginWindow;
import backend.User;

import javax.swing.*;

/**
 * Created by camhl on 04.04.2017.
 */
public class MainWindow {
    public static void main(String[] args) {
        /*JFrame frame = new JFrame("Main Window"); //Creating JFrame
        frame.setContentPane(new Main().cardContainer); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible*/


        LoginWindow loginWindow = new LoginWindow("Login");
        loginWindow.setVisible(true);
        boolean ok = true;

        while(ok) {

            if (loginWindow.isLoggedin()) {

                String loginType = new User().findUserByIndex(loginWindow.getLoginType());

                if (loginType.equals("Analyst")) {

                    System.out.println("Analyst was logged in");

                    loginWindow.setVisible(false);

                    JFrame analystFrame = new JFrame("Base Window"); //Creating JFrame
                    analystFrame.setContentPane(new BaseWindowAnalyst().getMainPanel()); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
                    analystFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
                    analystFrame.setSize(600, 600);
                    analystFrame.setLocation(700, 300);
                    analystFrame.pack();  //Creates a window out of all the components
                    analystFrame.setVisible(true);   //Setting the window visible
                    ok = false;
                }

                if (loginType.equals("Collector")) {

                    System.out.println("Collector was logged in");

                    loginWindow.setVisible(false);

                    JFrame collectorFrame = new JFrame("Base Window"); //Creating JFrame
                    collectorFrame.setContentPane(new BaseWindowCollector().getMainPanel()); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
                    collectorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
                    collectorFrame.setSize(600, 600);
                    collectorFrame.setLocation(700, 300);
                    collectorFrame.pack();  //Creates a window out of all the components
                    collectorFrame.setVisible(true);   //Setting the window visible
                    ok = false;


                }

                if (loginType.equals("Admin")) {

                    System.out.print("Admin was logged in");
                    ok = false;
                }
            }
        }

        System.out.println("End of file.");
    }
}
