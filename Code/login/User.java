package login;
import java.sql.*;
import DatabaseConnection.*;

import javax.xml.crypto.Data;


/**
 * Created by camhl on 16.03.2017.
 * Fixet av Toni
 */


public class User extends DatabaseManager{

    public User(){
        setup();
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
            res = getStatement().executeQuery(selectPassword);
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

        System.out.print("Wrong password.");
        return false;

    }

    public boolean findUser(String username) {
        String selectUsername = "SELECT * FROM User WHERE username = '" + username.trim() + "'";
        //String selectUsername = "SELECT * FROM Analyst";


        try {

            ResultSet res = null;
            res = getStatement().executeQuery(selectUsername);

            if (res.next()) {
                if(res.getString("Username").equals(username.trim())) {
                    res.close();
                    return true; //brukeren finnes
                }

            }
            System.out.println("The user does not exsist");
            res.close();


        } catch (SQLException e) {
            System.out.println("FIND USER: Lost connection to the database.." + e.toString());
        }




        return false; //brukeren finnes ikke.

    }

    public int findUsertype(String username){
        String usertype = "";
        ResultSet res = null;
        int actualUsertype = -1;
        boolean continuesearch = true;

        try {

            for (int i = 0; i < 3; i++) {
                usertype = findUserByIndex(i);
                res = getStatement().executeQuery("SELECT * FROM " + usertype + " WHERE username = '" + username + "'");

                if (res.next()) {
                    actualUsertype = i;
                    continuesearch = false;
                }

            }
        } catch(Exception e){
            System.out.println("FINDUSERTYPE:" + e.toString());
        }

        return actualUsertype;
    }

    public String findUserByIndex(int i){
        if(i == 0) return "Admin";
        if(i == 1) return "Analyst";
        if(i == 2) return "Collector";

        return null;
    }

    public boolean deleteUser(String username){
        // Finds out what kind of usertype the user is (Admin, Analyst, Collector)
        int usertypeInt = findUsertype(username);
        String usertype = findUserByIndex(usertypeInt);

        try {
            //Deletes the row in both table "User" and the spesific usertype.
            getStatement().executeQuery("DELETE FROM " + usertype + " WHERE username = '" + username + "'");
            getStatement().executeQuery("DELETE FROM User WHERE username = '" + username + "'");

            //Double checks that the user actually was deleted sucsessfully
            ResultSet res = getStatement().executeQuery("SELECT * FROM " + usertype + " WHERE username = '" + username + "'");
            if(!(res.next())){
                System.out.println("User deletet sucsessfully.");
                return true;
            }else {
                System.out.println("User was not deleted..");
                return false;
            }
        } catch(SQLException e){
            System.out.println("DELETEUSER: Something went wrong." + e.toString());
        }

        return false;
    }

   /* public void editName(String username, String firstname, String lastname){

        String query = "UPDATE User SET firstname = ? WHERE username = ?";               //Adding user into the "User"-table in the database
                //+ "(firstname, lastname)"   //Adding first name, last name, telephone, username, password
                //+ "VALUES (?,?)";       //The values comes from user-input

        try {
            PreparedStatement preparedStmt = getConnection().prepareStatement(query);  //Adding the user into the database, getting the users input
            //preparedStmt.setString(1, firstname);
            preparedStmt.setString(1, lastname);
            preparedStmt.setString(2, username);
        }catch(SQLException e){
            System.out.println("EDITNAME(USER): " + e.toString()); //..
        }
    }*/

    public boolean registerUser(String firstname, String lastname, String telephone, String username, String password, String usertype){
        if(findUser(username) == true) { return false; } //return false if the username is already used

        try {

            String query = "INSERT INTO User"               //Adding user into the "User"-table in the database
                    + "(firstname, lastname, telephone, username, password)"   //Adding first name, last name, telephone, username, password
                    + "VALUES (?,?,?,?,?)";       //The values comes from user-input


            //getStatement().executeQuery(query);
            PreparedStatement preparedStmt = getConnection().prepareStatement(query);  //Adding the user into the database, getting the users input
            preparedStmt.setString(1, firstname.trim());
            preparedStmt.setString(2, lastname.trim());
            preparedStmt.setInt(3, Integer.parseInt(telephone));
            preparedStmt.setString(4, username.trim());
            preparedStmt.setString(5, password.trim());

            preparedStmt.execute(); //Executing the prepared statement

            String query2 = "INSERT INTO "
                    + usertype
                    + "(username)"
                    + "VALUES (?)";
            PreparedStatement preparedStmt2 = getConnection().prepareStatement(query2);
            preparedStmt2.setString(1, username);

            preparedStmt2.execute(); //Executing the prepared statement

            getConnection().close(); //Closing connection

        }catch(Exception e){
            System.out.println("REGISTERUSER: Something went wrong." + e.toString());
            e.printStackTrace();
        }
        return true;
    }

    public String getName(String username){
        String query = "SELECT firstname, lastname FROM User WHERE username = '" + username + "'";
        String fullName = "";

        try {
            ResultSet res = getStatement().executeQuery(query);

            if(res.next()){
                fullName = res.getString("firstname").trim() + " " + res.getString("lastname").trim();
            }
        }catch(Exception e){
            System.out.println("GETNAME: " + e.toString());
        }

        return fullName;
    }
}

