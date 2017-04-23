package GUI.login;

import GUI.BaseWindow;
import GUI.main.MainWindow;
import backend.CSVReader;
import backend.LocationAdder;
import backend.RandomPasswordGenerator;
import backend.User;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import static backend.SendEmail.sendPasswordToUser;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by Toni on 16.03.2017.
 */
public class LoginWindow extends BaseWindow implements ActionListener {

    private JTextField usernameInput;
    private JPasswordField passwordField;
    private JPanel mainPanel;
    private int loginType;
    private static boolean loggedin;
    private JButton submitButton;
    private JButton forgotPasswordButton;

    //Two almost identical constructors for now. One that takes in the ButtonListener and one that does not
    public LoginWindow(String title) {
        loginWindowCommon(title);
    }

    //Another flavour for the constructor
    public LoginWindow(String title, ActionListener buttonListener) {
        loginWindowCommon(title);
        submitButton.addActionListener(buttonListener); //This listener was created in the main class
    }

    public void loginWindowCommon(String title) {
        setTitle(title); //sets title
        setDefaultLookAndFeelDecorated(true);
        setLocation(750, 300);



        //Sets the boolean to false bacause the user is not logged in yet.
        loggedin = false;

        //Create main panel for the frame
        mainPanel = new JPanel();

        //Create the rest of the containers
        JPanel topContainer = new JPanel();
        JPanel centerContainer = new JPanel(new BorderLayout());
        JPanel bottomContainer = new JPanel();
        JPanel rightMargin = new JPanel();
        JPanel leftMargin = new JPanel();

        //Setting up main panel
        LayoutManager layout = new BorderLayout();
        mainPanel.setLayout(layout);
        Border padding = BorderFactory.createEmptyBorder(100, 100, 100, 100);
        mainPanel.setBorder(padding);
        this.setContentPane(mainPanel);

        //Creating layout items
        JLabel headerText = new JLabel("WADA Monitoring System");
        this.usernameInput = new JTextField("username", 10);
        this.passwordField = new JPasswordField("password", 10);

        passwordField.setEchoChar((char) 0);
        submitButton = new JButton("Log in");
        ButtonListener submitListener = new ButtonListener();


        forgotPasswordButton = new JButton("Forgot password?");
        forgotPasswordButton.addActionListener(this);


        headerText.setFont(new Font("serif", Font.BOLD, 20));


        //Resize stuff
        usernameInput.setPreferredSize( new Dimension( 200, 22 ) );
        passwordField.setPreferredSize( new Dimension( 200, 22 ) );
        passwordField.setMaximumSize( new Dimension( 200, 22 ) );

        //Set borders
        topContainer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        centerContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Add layout items to layout
        mainPanel.add(topContainer, BorderLayout.NORTH);
        topContainer.add(headerText, BorderLayout.CENTER);
        mainPanel.add(centerContainer, BorderLayout.CENTER);
        centerContainer.add(usernameInput, BorderLayout.NORTH);
        centerContainer.add(passwordField, BorderLayout.CENTER);
        centerContainer.add(submitButton, BorderLayout.SOUTH);

        //Add bottomContainer to mainPanel, and forgotPasswordButton to bottomContainer
        mainPanel.add(bottomContainer, BorderLayout.SOUTH);
        bottomContainer.add(forgotPasswordButton, BorderLayout.SOUTH);



        //sets the submitButton as default so that when enter is presset the Actionevent runs
        submitButton.getRootPane().setDefaultButton(submitButton);

        //Adds the submitbutton to an actionlistener.
        //submitButton.addActionListener(submitListener);



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
                if(usernameInput.getText().equals("")){
                    usernameInput.setText("username");
                }
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
                if(passwordField.getText().equals("")){
                    passwordField.setText("password");
                }
            }
        });

        //!!!! IMPORTANT !!!
        pack();
        setVisible(true);

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
    private String getPassword() {
        char[] password = passwordField.getPassword();
        String passwordString = "";

        for (int i = 0; i < password.length; i++) { //goes throuh the whole array and creates a String.
            passwordString += password[i];
        }
        return passwordString;
    }

    //Used to check which button called the ActionEvent in the MainWindow class
    public JButton getSubmitButton() {
        return submitButton;
    }

    public boolean isLoggedin(){
        return loggedin;
    }

    public void setLoggedin(boolean loggedin){
        this.loggedin = loggedin;
    }

    public int getLoginType(){
        return loginType;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String email;

        email = showInputDialog(null, "Enter your email and hit OK to send a new password to your email");
        boolean passwordGenrated = false;

        while (!passwordGenrated) {

            if (email == "" || email == null) {
                break;
            }

            String newPassword = new RandomPasswordGenerator().getRandomPassword();
            String[] username = {email};

            User user = new User();

            if (user.findUser(email)) {

                if (user.updatePassword(newPassword, email)) {
                    showMessageDialog(null, "A new password is being sent to your email.");
                    sendPasswordToUser(username, "Did you forget your password?", "Here is your new randomly generated password " + newPassword + ". You can change your new password inside Profile, if you don't want to remember this long ass poem of a password");
                    passwordGenrated = true;
                    break;

                } else {
                    showMessageDialog(null, "Something went wrong when creating a new password.");
                }

            } else {
                showMessageDialog(null, "The email does not exist in our system.");
            }

            email = showInputDialog(null, "Enter your email and hit OK to send a new password to your email");

        }


    }


    //Handles the Submit button
    class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent theEvent) {
            // If LoginWindow is to be used without the MainWindow class, this has to be commented out.
            //performLogin();
        }
    }

    //Takes the text from the textfields and tries to login with them
    public void performLogin() {
        User testUser = new User();

        String password = getPassword();
        String username = getUsername();

        if(password == "" || password == null){
            System.out.println("Passwordfield is empty");
        }
        if(username == "" || password == null){
            System.out.println("Usernamefield is empty");
        }

        //Creates an User object to check the password and username

        if (testUser.login(username, password)) {
            loggedin = true;
            loginType = testUser.findUsertype(username);

            //Adds locations from the CSV-file into the database before logging in
            /*
            CSVReader csvReader = new CSVReader();
            ArrayList<String[]> locationList = csvReader.getCSVContent();
            LocationAdder la = new LocationAdder();
            la.addLocations(locationList);
            */

            System.out.println("Login Ok!");
        } else {
            showMessageDialog(null, "Login failed!");
            loggedin = false;
        }

        System.out.println("You pushed the button.");
    }

    //Main function!!
    public static void main(String[] args) {
        MainWindow aWindow = new MainWindow();
        //aWindow.setVisible(true);
    }
}