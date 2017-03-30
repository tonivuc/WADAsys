package login;
import DatabaseConnection.DatabaseConnection;
import chart.HaemoglobinChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.FocusListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Statement;

import static com.sun.javafx.fxml.expression.Expression.add;

/**
 * Created by Toni on 16.03.2017. Oppdatert i dag. JPanel.setContentPane() <-- Hvordan
 */
public class LoginWindow extends BaseWindow {

    JTextField usernameInput;
    JPasswordField passwordField;

    public LoginWindow(String title) {
        setTitle(title); //sets title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Establishing layout
        LayoutManager layout = new BorderLayout();
        setLayout(layout);

        JPanel topContainer = new JPanel();
        JPanel centerContainer = new JPanel();
        JPanel bottomContainer = new JPanel();
        JPanel rightMargin = new JPanel();
        JPanel leftMargin = new JPanel();

        //Creating layout items
        JLabel headerText = new JLabel("WADA Monitoring System");

        this.usernameInput = new JTextField("username", 10);
        this.passwordField = new JPasswordField("password", 10);
        passwordField.setEchoChar((char) 0);
        JButton submitButton = new JButton("Submit");
        ButtonListener submitListener = new ButtonListener();


        //Add layout items to layout
        add(topContainer, BorderLayout.NORTH);
        topContainer.add(headerText, BorderLayout.CENTER);
        add(centerContainer, BorderLayout.CENTER);
        centerContainer.add(usernameInput, BorderLayout.NORTH);
        centerContainer.add(passwordField, BorderLayout.CENTER);
        centerContainer.add(submitButton, BorderLayout.SOUTH);

        //sets the submitButton as default so that when enter is presset the Actionevent runs
        submitButton.getRootPane().setDefaultButton(submitButton);

        //Adds the submitbutton to an actionlistener.
        submitButton.addActionListener(submitListener);



        //Listeners
        //Used to clear the default text when the user wants to type his username
        usernameInput.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (usernameInput.getText().equals("username")) {
                    usernameInput.setText(null);
                }

            }

            public void focusLost(FocusEvent e) {
                //...
            }
        });

        //Used to clear the default text when the user wants to type his password
        passwordField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("password")) {
                    passwordField.setText(null);
                }
                passwordField.setEchoChar('*');

            }

            public void focusLost(FocusEvent e) {
                //...
            }
        });


        pack();

        //Small "hack" that makes the text not dissapear from headerText.
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                headerText.grabFocus();
                headerText.requestFocus(); //or inWindow
            }
        });
    }

    //Translates the textInputField to a String.

    public String getUsername() {
        String usernameString = usernameInput.getText();
        return usernameString;
    }

    //Translates the passwordField (that returns an char array) to a String.

    public String getPassword() {
        char[] password = passwordField.getPassword();
        String passwordString = "";

        for (int i = 0; i < password.length; i++) { //goes throuh the whole array and creates a String.
            passwordString += password[i];
        }
        return passwordString;
    }


    class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent theEvent) {
            //Finds the button that was clicked
            JButton theButton = (JButton) theEvent.getSource();

            User testUser;

            String password = getPassword();
            String username = getUsername();

            if(password == "" || password == null){
                System.out.println("Passwordfield is empty");
            }
            if(username == "" || password == null){
                System.out.println("Usernamefield is empty");
            }

            //Creates an User object to check the password and username
            try {
                testUser = new User();

                if (testUser.login(username, password)) {
                    System.out.println("Login Ok!");
                } else {
                    System.out.println("Login Failed!");
                }

            } catch (Exception e) {
                System.out.println("BUTTONLISTENER: Something went wrong." + e.toString());
            }


            System.out.println("You pushed the button.");
        }
    }

    public static void main(String[] args) throws Exception{
        DatabaseConnection databaseConnection = new DatabaseConnection();

        LoginWindow aWindow = new LoginWindow("Login", databaseConnection.getStatement());
        aWindow.setVisible(true);
    }
}