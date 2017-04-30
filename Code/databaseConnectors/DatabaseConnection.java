package databaseConnectors;

/**
 *
 * @author Camilla Haaheim Larsen
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class that is made sets up a database connection.
 */
public class DatabaseConnection{

    /**
     * The connection to the database.
     */
    private  Connection connection;

    /**
     * The driver of the database.
     */
    private  String databaseDriver = "com.mysql.jdbc.Driver";

    /**
     * The username of the owner of the database.
     */
    private  String username = "toniv";

    /**
     * The password to the database.
     */
    private  String password = "kuanZ4Yk";

    /**
     * Sets up the database connection, with the username and password.
     */
    public DatabaseConnection(){
        String databaseName = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/" + username + "?user=" + username + "&password=" + password;
        try {
            Class.forName(databaseDriver);
            this.connection = DriverManager.getConnection(databaseName);
        }catch(Exception e){
            System.out.println("DATABASECONNECTION: Something went wrong in the constructor." + e.toString());
        }

    }

    /**
     * Retuns the connection to the database. Or null if connecting to it failed.
     * @return Connection. If
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