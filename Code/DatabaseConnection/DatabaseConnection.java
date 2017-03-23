package DatabaseConnection;
import login.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * Created by camhl on 16.03.2017.
 * Bare tester litt jeg! -Toni
 */
public class DatabaseConnection{

    private static Connection connection;
    protected static Statement statement;
    protected static String databaseDriver = "com.mysql.jdbc.Driver";

    /*
    * Method that returns a Statement for further use of the connection of the database
    */
    public static Statement getStatement(String username, String password, String databaseDriver) throws Exception{
        String databaseName = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/" + username + "?user=" + username + "&password=" + password;
        Class.forName(databaseDriver);
        connection = DriverManager.getConnection(databaseName);
        statement = connection.createStatement();

        return statement;
    }

    /*
    * Method that closes the statement and the connection used.
    */

    public void closeAll() throws Exception{
        statement.close();
        connection.close();
    }


    public static void main(String[] args) throws Exception {


        //DatabaseConnection databaseConnection = new DatabaseConnection();
        //String databaseDriver = "com.mysql.jdbc.Driver";

        //Statement statement = databaseConnection.getStatement("toniv", "kuanZ4Yk", databaseDriver);



        User user = new User();

        String username = "Geirmama";
        String pw = "Geirmama123";

        if(user.login(username,pw)){
            System.out.print("Login completed!");
        }
        else{
            System.out.println("Login failed!");
        }

        if (user.findUsertype(username) == 0){
            System.out.println("Type: ADMIN");
        }

        if (user.findUsertype(username) == 1){
            System.out.println("Type: ANALYST");
        }

        if (user.findUsertype(username) == 2){
            System.out.println("Type: COLLECTOR");
        }

        if(user.findUsertype(username) == -1){
            System.out.println("Something went wrong");
        }

        user.closeAll();

    }

}