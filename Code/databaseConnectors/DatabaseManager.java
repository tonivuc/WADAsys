package databaseConnectors;

import java.sql.Connection;
import java.sql.Statement;

/**
 *
 * @author Camilla Haaheim Larsen
 */

public abstract class DatabaseManager {

    /**
     * The statement used to execute queries to the database.
     */
    private static Statement statement;

    /**
     * The connection to the database.
     */
    private Connection connection;

    /**
     * A DatabaseConnection Object.
     */
    private DatabaseConnection databaseConnection;

    /**
     * Sets up the connection to the database;
     * @return boolean true if successful, false if not.
     */
    public boolean setup() {
        databaseConnection = new DatabaseConnection();
        this.connection = databaseConnection.getConnection();
        try{
           this.statement = connection.createStatement();
           return true;
       }catch(Exception e){
           System.out.println("DATABASEMANAGER: Something went wrong in creating statement." + e.toString());
           return false;
       }
    }

    /**
     * Returns the statement.
     * @return Statement.
     */
    public static Statement getStatement(){
        return statement;
    }

    /**
     * Returns the connection.
     * @return Connection.
     */
    public Connection getConnection(){

        return connection;
    }

    /**
     * Disconnects from the database.
     */
    public void disconnect(){
        try{
            connection.close();
            statement.close();
        }catch(Exception e){
            System.out.println("DISCONNECT: Could not disconnect connection/statement." + e.toString());
        }

    }

}
