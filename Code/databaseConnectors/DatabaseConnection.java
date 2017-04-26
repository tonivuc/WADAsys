package databaseConnectors;
import backend.User;
import backend.UserManager;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 * Created by camhl on 16.03.2017.
 */
public class DatabaseConnection{

    private  Connection connection;

    private  String databaseDriver = "com.mysql.jdbc.Driver";
    private  String username = "toniv";
    private  String password = "kuanZ4Yk";

    public DatabaseConnection(){
        String databaseName = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/" + username + "?user=" + username + "&password=" + password;
        try {
            Class.forName(databaseDriver);
            this.connection = DriverManager.getConnection(databaseName);
        }catch(Exception e){
            System.out.println("DATABASECONNECTION: Something went wrong in the constructor." + e.toString());
        }

    }

    public Connection getConnection(){
        return connection;
    }


    public static void main(String[] args) throws Exception {


        //DatabaseConnection databaseConnection = new DatabaseConnection();
        //String databaseDriver = "com.mysql.jdbc.Driver";

        //Statement statement = databaseConnection.getStatement("toniv", "kuanZ4Yk", databaseDriver);



        UserManager user = new UserManager();

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

    }

}