package setup;

import GUI.main.MainWindow;
import databaseConnectors.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Created by camhl on 29.04.2017.
 */
public class ConfigWindow extends JFrame {
    private JTextField driverTextField;
    private JPanel panel1;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton setupButton;

    private DatabaseConnection dbc;

    public ConfigWindow() {

        String file = "Code/setup/config";
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String hei = "";
            while((hei = br.readLine()) != null) {
                System.out.println(hei);
            }

        }catch(IOException x){

        }

        getMainPanel().setBorder(new EmptyBorder(50, 50, 50, 50));

        ButtonListener buttonListener = new ButtonListener();

        setupButton.addActionListener(buttonListener);

        //sets the submitButton as default so that when enter is presset the Actionevent runs
        //setupButton.getRootPane().setDefaultButton(setupButton);



        driverTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(driverTextField.getText().equals("com.mysql.jdbc.Driver")){
                    driverTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(driverTextField.getText().equals("")){
                    driverTextField.setText("com.mysql.jdbc.Driver");
                }
            }
        });


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
                String databaseDriver = driverTextField.getText();
                String username = usernameTextField.getText();
                char[] password = passwordField.getPassword();
                String passwordString = "";

                for (int i = 0; i < password.length; i++) { //goes throuh the whole array and creates a String.
                    passwordString += password[i];
                }

                if (databaseDriver.equals("") || username.equals("") || passwordString.equals("")) {
                    JOptionPane.showMessageDialog(null, "You can not leave a field empty. \n\n Please try again.");
                } else {
                    writeToConfig(databaseDriver, username, passwordString);

                    if (connectToDatabase()) {

                        //writeToConfig(databaseDriver, username, passwordString);

                        System.out.println("Connected to database.");

                        java.sql.Connection con = dbc.getConnection();

                        File sqlScript = new File("Code/setup/databaseScript.sql");

                        executeSqlScript(con, sqlScript);

                        JOptionPane.showMessageDialog(null, "ConfigWindow complete. Create tables complete.");

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

    public void writeToConfig(String databaseDriver, String username, String password){

        String outputString = databaseDriver + "\n" + username + "\n" + password;

        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("Code/setup/config")));
            writer.write(outputString);
        } catch (IOException ex) {
            System.out.println(ex.toString());
            // report
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }


    }

    public void executeSqlScript(Connection conn, File inputFile) {

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
        scanner.close();
    }

    public boolean connectToDatabase(){

        this.dbc = new DatabaseConnection();
        dbc.setVariables();

        if(dbc.getConnection() == null){
            return false;
        }

        else{
            return true;
        }

    }

    public JPanel getMainPanel(){
        return panel1;
    }

    public static void main(String[] args) {
        ConfigWindow setup = new ConfigWindow();
    }
}




