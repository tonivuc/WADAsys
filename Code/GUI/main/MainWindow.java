package GUI;

import GUI.admin.BaseWindowAdmin;
import GUI.analyst.BaseWindowAnalyst;
import GUI.collector.BaseWindowCollector;
import GUI.login.LoginWindow;
import backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by camhl on 04.04.2017.
 */
public class MainWindow {

    protected static Image createFDImage() {
        //Create a 16x16 pixel image.
        BufferedImage bi = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);

        //Draw into it.
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 20, 20);
        g.setColor(Color.BLUE);
        g.drawString("BB", 1, 16);
        //g.fillOval(5, 3, 6, 6);

        //Clean up.
        g.dispose();

        //Return it.
        return bi;
    }

    public static void main(String[] args) {
        /*JFrame frame = new JFrame("Main Window"); //Creating JFrame
        frame.setContentPane(new Main().cardContainer); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible*/


        LoginWindow loginWindow = new LoginWindow("Login");
        loginWindow.setIconImage(createFDImage());
        loginWindow.setSize(600, 600);
        loginWindow.setLocation(700, 300);
        loginWindow.pack();
        loginWindow.setVisible(true);
        boolean ok = true;

        while(ok) {

            if (loginWindow.isLoggedin()) {

                String loginType = new User().findUserByIndex(loginWindow.getLoginType());

                if (loginType.equals("Analyst")) {

                    System.out.println("Analyst was logged in");

                    loginWindow.setVisible(false);

                    JFrame analystFrame = new JFrame("Base Window"); //Creating JFrame
                    analystFrame.setContentPane((new BaseWindowAnalyst()).getMainPanel()); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
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
                    collectorFrame.setContentPane((new BaseWindowCollector()).getMainPanel()); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
                    collectorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
                    collectorFrame.setSize(600, 600);
                    collectorFrame.setLocation(700, 300);
                    collectorFrame.pack();  //Creates a window out of all the components
                    collectorFrame.setVisible(true);   //Setting the window visible
                    ok = false;
                }

                if (loginType.equals("Admin")) {

                    System.out.println("Collector was logged in");

                    loginWindow.setVisible(false);

                    JFrame collectorFrame = new JFrame("Admin Window"); //Creating JFrame
                    collectorFrame.setContentPane((new BaseWindowAdmin()).getMainPanel()); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
                    collectorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
                    collectorFrame.setSize(600, 600);
                    collectorFrame.setLocation(700, 300);
                    collectorFrame.pack();  //Creates a window out of all the components
                    collectorFrame.setVisible(true);   //Setting the window visible
                    ok = false;

                }
            }
        }

        System.out.println("End of file.");
    }
}