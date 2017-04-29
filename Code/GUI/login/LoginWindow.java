package GUI.login;

/**
 *
 * @author Toni Vucic
 */

import GUI.common.BaseWindow;
import backend.RandomPasswordGenerator;
import backend.User;
import backend.UserManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static backend.SendEmail.sendMailToUser;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Class made to handle the GUI associated with login.
 */
public class LoginWindow extends BaseWindow implements ActionListener {

    /**
     * The field where the user inputs his/her username.
     */
    private JTextField usernameInput;

    /**
     * The field where the user inputs his/her password.
     */
    private JPasswordField passwordField;

    /**
     * The mainPanel/rootPanel where everything is contained.
     */
    private JPanel mainPanel;

    /**
     * Value used to decide what type of user that has logged in.
     */
    private int loginType;

    /**
     * True if the user successfully logged in, false if not.
     */
    private static boolean loggedin;

    /**
     * Button the user presses to log in.
     */
    private JButton submitButton;

    /**
     * Button the user presses if he/she forgot his/her password.
     */
    private JButton forgotPasswordButton;

    /**
     * Frame that does the loading screen.
     */
    private JFrame loadingFrame;


    /**
     * Constructs the LoginWindow for the user.
     * @param title The title of the window.
     * @param buttonListener ButtonListener for the buttons in the window.
     */
    public LoginWindow(String title, ActionListener buttonListener) {
        loginWindowCommon(title);
        submitButton.addActionListener(buttonListener); //This listener was created in the main class
    }

    /**
     * Makes a new loginWindow.
     * @param title the title of the window.
     */
    public void loginWindowCommon(String title) {

        createLoadingScreen();

        setTitle(title); //sets title
        setDefaultLookAndFeelDecorated(true);

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
        bottomContainer.add(forgotPasswordButton, BorderLayout.NORTH);


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
        setLocationRelativeTo(null);
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


    /**
     * Translates the input field of the username to a String.
     * @return String
     */

    public String getUsername() {
        String usernameString = usernameInput.getText();
        return usernameString;
    }

    /**
     * Translates the input field of the password to a String.
     * @return String
     */
    private String getPassword() {
        char[] password = passwordField.getPassword();
        String passwordString = "";

        for (int i = 0; i < password.length; i++) { //goes throuh the whole array and creates a String.
            passwordString += password[i];
        }
        return passwordString;
    }

    /**
     * Used to check which button called the ActionEvent in the MainWindow class
     * @return JButton
     */
    public JButton getSubmitButton() {
        return submitButton;
    }

    /**
     * Returns the loggedin boolaen.
     * @return boolean
     */
    public boolean isLoggedin(){
        return loggedin;
    }

    /**
     * Returns the type of the user that logged in.
     * @return int
     */
    public int getLoginType(){
        return loginType;
    }

    /**
     * Returns the mainPanel/rootPanel.
     * @return JPanel
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }


    /**
     * Checks to see if the forgot password button was clicked. If it was clicked the user gets
     * to write in his/her email, and a new randomly generated password will be sent to him/her.
     * @param e event
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        String email;

        if (e.getSource().equals(forgotPasswordButton)) {
            email = showInputDialog(null, "Enter your email and hit OK to send a new password to your email");

            boolean passwordGenrated = false;

            while (!passwordGenrated) {

                if (email == "" || email == null) {
                    break;
                }

                String newPassword = new RandomPasswordGenerator().getRandomPassword();
                String[] username = {email};


                UserManager userManager = new UserManager();

                if (userManager.findUser(email)) {

                    User user = new User(email);

                    if (user.updatePassword(newPassword)) {
                        showMessageDialog(null, "A new password is being sent to your email.");
                        sendMailToUser(username, "Did you forget your password?", "Here is your new randomly generated password " + newPassword + ". You can change your new password inside Profile, if you don't want to remember this long ass poem of a password");
                        passwordGenrated = true;
                        break;

                    } else {
                        showMessageDialog(null, "The email does not exist in our system.");
                    }

                    email = showInputDialog(null, "Enter your email and hit OK to send a new password to your email");
                }
            }
        }
    }

    /**
     * Handles the submit button.
     */
    class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent theEvent) {
            // If LoginWindow is to be used without the MainWindow class, this has to be commented out.
            //performLogin();
        }
    }

    /**
     * Creates a loading screen with a custom .gif-file
     */
    public void createLoadingScreen () {
        new Thread(() -> {
            this.loadingFrame = new JFrame();
            ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/loadingGIF/blood_gif.gif"));
            JLabel label = new JLabel(imageIcon);
            loadingFrame.getContentPane().add(label);
            loadingFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            loadingFrame.setUndecorated(true);
            loadingFrame.pack();
            loadingFrame.setLocationRelativeTo(null);
            loadingFrame.setVisible(false);
        }).start();
    }

    /**
     * @param isVisible true to show loading screen, false to hide.
     */
    public void showLoadingScreen (boolean isVisible) {
        loadingFrame.setVisible(isVisible);
    }

    /**
     * Takes the text from the textfields and tries to login with them.
     */
    public void performLogin() {

        UserManager testUser = new UserManager();

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

}