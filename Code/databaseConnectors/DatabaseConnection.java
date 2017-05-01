package databaseConnectors;

/**
 *
 * @author Camilla Haaheim Larsen
 */

import setup.ConfigWindow;
import java.io.*;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class that is made sets up a database connection.
 */
public class DatabaseConnection {

    /**
     * The connection to the database.
     */
    private  Connection connection;

    /**
     * The driver of the database.
     */
    private static String databaseDriver;

    /**
     * The username of the owner of the database.
     */
    private static String username;

    /**
     * The password to the database.
     */
    private static String password;

    /**
     * Sets up the database connection, with the username and password.
     */
    public DatabaseConnection(){
        String databaseName = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/" + username + "?user=" + username + "&password=" + password;

        try{
            Class.forName(databaseDriver);
            this.connection = DriverManager.getConnection(databaseName);
        }catch(Exception e){

        }

    }

    /**
     * Method that reads the file that contains username, password and databaseDriver
     * and gives the static local variables its values.
     */

    public static void setVariables(){


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


        try(BufferedReader br = new BufferedReader(new FileReader(new File(filRunningInDir, "config.txt")))) {
            databaseDriver = br.readLine();
            username = br.readLine();
            password = br.readLine();
        }catch(IOException x) {
            System.out.println("config file is empty");
        }

            //System.out.println(databaseDriver + "\n" + username + "\n" + password + "\n");
    }

    /**
     * Retuns the connection to the database. Or null if connecting to it failed.
     * @return Connection.
     */
    public Connection getConnection(){
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        }
        catch (SQLException e) {
            System.out.println("Something went wrong trying to close database connection: "+e);
        }
        catch (NullPointerException e) {
            System.out.println("Error closing connection. It appears there was never a connection in the first place! Error: "+e);
        }
    }

}