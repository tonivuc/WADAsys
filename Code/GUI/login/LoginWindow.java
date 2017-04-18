package GUI.login;

import GUI.BaseWindow;
import GUI.main.MainWindow;
import backend.User;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.FocusListener;

import static com.sun.javafx.fxml.expression.Expression.add;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by Toni on 16.03.2017. Oppdatert i dag. JPanel.setContentPane() <-- Hvordan
 */
public class LoginWindow extends BaseWindow {

    private JTextField usernameInput;
    private JPasswordField passwordField;
    private JPanel mainPanel;
    private int loginType;
    private static boolean loggedin;
    private JButton submitButton;

    //Two almost identical constructors for now. One for the buttonListener and one without

    public void loginWindowCommon(String title) {
        setTitle(title); //sets title
        setDefaultLookAndFeelDecorated(true);
        setIconImage(new ImageIcon("http://tinypic.com/r/wwln9e/9").getImage());


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
        submitButton = new JButton("Submit");
        ButtonListener submitListener = new ButtonListener();

        headerText.setFont(new Font("serif", Font.BOLD, 14));


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

    //Constructor
    public LoginWindow(String title) {
        loginWindowCommon(title);
    }

    //Another flavour for the constructor
    public LoginWindow(String title, ActionListener buttonListener) {
        loginWindowCommon(title);
        submitButton.addActionListener(buttonListener); //This listener was created in the main class
    }

    //Translates the textInputField to a String.
    private String getUsername() {
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