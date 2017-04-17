package GUI;

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
 * Created by camhl on 04.04.2017.
 */
public class MainWindow implements ActionListener{

    private LoginWindow frame;

    public MainWindow() {
        //We are using the listener we created here, in the LoginWindow class, and can thus can acces it here.
        frame = new LoginWindow("Login", this::actionPerformed);
        frame.setIconImage(createFDImage());
    }

    //Main login window logic.
    public void actionPerformed(ActionEvent e)
    {
        //FEATURE REQUEST: Check the origin of the ActionEvent. (f.eks. e.getSource())
        //Logs in using the credentials the user typed into the text fields
        frame.performLogin();

        //Checks if logged in
        if (frame.isLoggedin()) {

            String loginType = new User().findUserByIndex(frame.getLoginType());

            if (loginType.equals("Analyst")) {

                System.out.println("Analyst was logged in");

                frame.setTitle("Analyst base window");
                System.out.println("Please wait, loading Analyst Window...");
                frame.setContentPane((new BaseWindowAnalyst()).getMainPanel()); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
                frame.pack();  //Creates a window out of all the components

            } else if (loginType.equals("Collector")) {

                System.out.println("Collector was logged in");

                frame.setTitle("Collector base window");
                frame.setContentPane((new BaseWindowCollector()).getMainPanel()); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
                frame.pack();  //Creates a window out of all the components

            } else if (loginType.equals("Admin")) {

                System.out.println("Admin was logged in");

                frame.setTitle("Admin Window");
                frame.setContentPane((new BaseWindowAdmin()).getMainPanel()); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
                frame.pack();  //Creates a window out of all the components

            }

        }
        // display/center the jdialog when the button is pressed
    }

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
        MainWindow mainFrame = new MainWindow();
    }

}