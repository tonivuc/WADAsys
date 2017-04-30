package databaseConnectors;

/**
 *
 * @author Camilla Haaheim Larsen
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    public void setVariables(){
        String file = "Code/setup/config";
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            this.databaseDriver = br.readLine();
            this.username = br.readLine();
            this.password = br.readLine();
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