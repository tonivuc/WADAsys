package DatabaseConnection;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by camhl on 29.03.2017.
 */
public abstract class DatabaseManager {
    private Statement statement;
    private Connection connection;
    private DatabaseConnection databaseConnection;

    protected boolean setup() {
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

    public Statement getStatement(){
        return statement;
    }

    public Connection getConnection(){
        return databaseConnection.getConnection();
    }

    public void disconnect(){
        try{
            connection.close();
            statement.close();
        }catch(Exception e){
            System.out.println("DISCONNECT: Could not disconnect connection/statement." + e.toString());
        }

    }
}
