package setup;

import GUI.main.MainWindow;
import databaseConnectors.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * @author Camilla Haaheim Larsen
 * Created by camhl on 29.04.2017.
 * Configures the database and running the sql script.
 * Also adds a user "Admin" password "Admin" to the database.
 */
public class ConfigWindow extends JFrame {
    /**
     * Input driver name
     */
    private JTextField hostTextField;
    /**
     * Main panel
     */
    private JPanel panel1;
    /**
     * Input username
     */
    private JTextField usernameTextField;
    /**
     * Input password
     */
    private JPasswordField passwordField;
    /**
     * Button for trying to set up database.
     */
    private JButton setupButton;
    private JTextField databaseTextField;
    /**
     * Connection to the database
     */
    private DatabaseConnection dbc;

    /**
     * Main logic for the window. Uses a couple of help methods to check
     * if the connection is ok and to write to file.
     */
    public ConfigWindow() {

        getMainPanel().setBorder(new EmptyBorder(50, 50, 50, 50));

        ButtonListener buttonListener = new ButtonListener();

        setupButton.addActionListener(buttonListener);


        //Essential for the JFrame portion of the window to work:
        setContentPane(getMainPanel());
        setTitle("Analyst window");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Handles the setup button.
     */
    class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent theEvent) {


            int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to continue?", "Confirmation", JOptionPane.YES_NO_OPTION);

            if (confirmation == 0) {
                String host = hostTextField.getText();
                String database = databaseTextField.getText();
                String username = usernameTextField.getText();
                char[] password = passwordField.getPassword();
                String passwordString = "";

                for (int i = 0; i < password.length; i++) { //goes through the whole array and creates a String.
                    passwordString += password[i];
                }

                if (host.equals("") || database.equals("") || username.equals("") || passwordString.equals("")) {
                    JOptionPane.showMessageDialog(null, "You can not leave a field empty. \n\n Please try again.");
                } else {
                    writeToConfig(host, database, username, passwordString);

                    if (connectToDatabase()) {


                        java.sql.Connection con = dbc.getConnection();

                        File sqlScript = findFile("databaseScript.sql");

                        executeSqlScript(con, sqlScript);

                        JOptionPane.showMessageDialog(null, "Successfully connected to the database!");

                        dispose();
                        new MainWindow();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Connection with database failed.");
                    }
                }
            }
        }
    }

    /**
     * Method that writes the username, password and databasedriver name to config.txt
     * @param host will be written to config.txt.
     * @param database will be written to config.txt.
     * @param username will be written to config.txt.
     * @param password will be written to config.txt.
     */
    public void writeToConfig(String host, String database, String username, String password){

        String outputString = host + "\n" + database + "\n" + username + "\n" + password;

        Writer writer = null;

        try {

            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(findFile("config.txt"))));
            writer.write(outputString);


        } catch (IOException ex) {
            System.out.println(ex.toString());
            // report
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }


    }

    /**
     * @param fileName The name of the file you want to find where the .jar file is running.
     * @return Returns the file as a file.
     */
    public File findFile (String fileName) {

        String dirPath = "";

        try {
            dirPath = ConfigWindow.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File filRunningInDir = new File(dirPath);

        if (dirPath.endsWith(".jar")) {
            filRunningInDir = filRunningInDir.getParentFile();
        }

        return new File(filRunningInDir, fileName);
    }

    /**
     * Checks if there are any tables in the database.
     * @param con Connection
     * @return true if there are, false if not.
     */
    public boolean tablesExist (Connection con) {

        Statement statement;
        try {
            statement = con.createStatement();
            String query = "SELECT username FROM User";
            ResultSet res = statement.executeQuery(query);
            while (res.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("tables does not exist");
            return false;
        }

        return false;
    }

    /**
     * Method that runs the SqlScript that contains all the queries to set up the database.
     * @param conn connection to the database.
     * @param inputFile The file is going to be executed.
     */
    public void executeSqlScript(Connection conn, File inputFile) {

        if (tablesExist(conn)) {
            return;
        }

        System.out.println("Tries do execute statements");
        // Delimiter
        String delimiter = ";";

        // Create scanner
        Scanner scanner;
        try {
            scanner = new Scanner(inputFile).useDelimiter(delimiter);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return;
        }

        // Loop through the SQL file statements
        Statement currentStatement = null;
        while(scanner.hasNext()) {

            // Get statement
            String rawStatement = scanner.next() + delimiter;
            try {
                // Execute statement
                currentStatement = conn.createStatement();
                currentStatement.execute(rawStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Release resources
                if (currentStatement != null) {
                    try {
                        currentStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                currentStatement = null;
            }
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        scanner.close();
    }

    /**
     * This method checks if the username, password and databasedriver is correct
     * @return true if a connection was created
     * @return false if a connection was not created and connection is null
     */
    public boolean connectToDatabase(){

        dbc.setVariables();
        dbc = new DatabaseConnection();

        if(dbc.getConnection() == null){
            return false;

        } else{

            return true;
        }
    }

    /**
     *
     * @return the main panel of the window
     */
    public JPanel getMainPanel(){
        return panel1;
    }

}




