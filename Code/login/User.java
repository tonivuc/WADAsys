package login;
import java.sql.*;
import DatabaseConnection.*;


/**
 * Created by camhl on 16.03.2017.
 * Fixet av Toni
 */


public class User{
    private String username;
    //private Connection connection;
    private Statement statement;
    DatabaseConnection databaseConnection = new DatabaseConnection();


    /*
    * Constructor for creating a connection to the database, to check password and username later.
     */
    public User(Statement statement) throws Exception{
        this.statement = statement;

    }


    /*public User(Statement statement) {
        this.statement = statement;
    }*/


    public String getUserName() {
        return username;
    }

    /*
    * Checks if username exist and that the password is correct.
    * Using two helping methods to do so.
    *
    * Returns true if ok.
    */

    public boolean login(String username, String password) {
        if (findUser(username) == false) return false;
        if(checkPassword(username, password) == false) return false;

        return true;


    }

    public boolean checkPassword(String username, String password) {

        String selectPassword = "SELECT password FROM User WHERE username = '" + username + "'";


        boolean ok = true;
        String actualPassword = "";

        try {
            ResultSet res = null;
            res = statement.executeQuery(selectPassword);
            if(res.next()){
                actualPassword = res.getString("password");
            }

            if (password.equals(actualPassword)) {
                res.close();
                return true;
            }

            res.close();

        } catch (SQLException e) {
            System.out.println("CHECK PASSWORD: Lost connection to the database.." + e.toString());
        }


        return false;

    }

    public boolean findUser(String username) {
        String selectUsername = "SELECT * FROM User WHERE username = '" + username.trim() + "'";
        //String selectUsername = "SELECT * FROM Analyst";


        try {

            ResultSet res = null;
            res = statement.executeQuery(selectUsername);

            if (res != null) {
                res.close();
                return true;
            }
            res.close();


        } catch (SQLException e) {
            System.out.println("FIND USER: Lost connection to the database.." + e.toString());
        }




        return false;

    }

    public int findUsertype(String username) throws Exception{
        String usertype = "";
        ResultSet res = null;
        int actualUsertype = -1;
        boolean continuesearch = true;

        for(int i = 0; i < 3; i++){
            usertype = findUserByIndex(i);
            res = statement.executeQuery("SELECT * FROM " + usertype + " WHERE username = '" + username + "'");

            if(res.next()){
                actualUsertype = i;
                continuesearch = false;
            }

        }

        return actualUsertype;
    }

    public String findUserByIndex(int i){
        if(i == 0) return "Admin";
        if(i == 1) return "Analyst";
        if(i == 2) return "Collector";

        return null;
    }
}

