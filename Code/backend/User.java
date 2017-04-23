package backend;
import databaseConnectors.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Created by camhl on 16.03.2017.
 * Fixet av Toni
 */


public class User extends DatabaseManager {
    String username;
    String firstname;
    String lastname;
    String telephone;

    public User() {

    }

    public User(String username) {
        this.username = username;

        try {
            setup();
            ResultSet res = getStatement().executeQuery("SELECT * FROM User WHERE username = '" + username + "'");
            while (res.next()) {
                this.firstname = res.getString("firstname");
                this.lastname = res.getString("lastname");
                this.telephone = res.getString("telephone");
            }
            res.close();

        } catch (SQLException e) {
            System.out.println("SQL exception in constructor in User.java: " + e);
        }

        disconnect();
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

    /*
    * Input password is being crypted with MD5 and compared with the crypted password saved in the dataBase.
    * The actual password is never uncrypted, this is for safety reasons.
    */
    public boolean checkPassword(String username, String password) {

        //Creating a mySql-statement
        String selectPassword = "SELECT password FROM User WHERE username = '" + username + "'";

        //Crypting the input with MD5
        String cryptInput = new CryptWithMD5().cryptWithMD5(password);

        boolean ok = true;
        String actualPassword = "";

        setup(); //Setup the connection to the database

        try {
            ResultSet res = null;
            res = getStatement().executeQuery(selectPassword);
            if(res.next()){
                actualPassword = res.getString("password");
            }

            //Comparing the crypted input password with the crypted password in the database.
            if (cryptInput.equals(actualPassword)) {
                res.close();
                disconnect();
                return true;
            }

            res.close();

        } catch (SQLException e) {
            System.out.println("CHECK PASSWORD: Lost connection to the database.." + e.toString());
        }

        System.out.print("Wrong password.");
        disconnect();
        return false;

    }

    public boolean updatePassword (String newPassword, String username) {

        String cryptedPassword = new CryptWithMD5().cryptWithMD5(newPassword);

        setup();

        try {
            // create the java mysql update preparedstatement
            String query = "UPDATE User SET PASSWORD = '" + cryptedPassword + "' WHERE username = '" + username + "'";
            getStatement().executeUpdate(query);
            return true;
        } catch (Exception e) {
            System.out.println("UPDATEPASSWORD: Sql.. " + e.toString());
        }
        disconnect();

        return false;
    }

    public boolean updatePassword(String oldpassword, String newPassword1, String newPassword2, String username) {

        if (checkPassword(username, oldpassword) == false) return false;

        if (newPassword1.equals(newPassword2)) {

            String cryptedPassword = new CryptWithMD5().cryptWithMD5(newPassword1);

            setup();

            try {

                // create the java mysql update preparedstatement
                String query = "UPDATE User SET PASSWORD = '" + cryptedPassword + "' WHERE username = '" + username + "'";
                getStatement().executeUpdate(query);

                return true;
            } catch (Exception e) {
                System.out.println("UPDATEPASSWORD: Sql.. " + e.toString());
            }
            disconnect();
        }

        return false;
    }

    public boolean updateInfo(String newData, String columnName, String username) {
            setup();
            try {
                if (columnName.equals("username")) {

                    int usertypeInt = findUsertype(username);
                    String usertype = findUserByIndex(usertypeInt);

                    String query = "UPDATE " + usertype + " SET username = '" + newData + "' WHERE username = '" + username + "'";
                    Statement stm = getStatement();
                    stm.executeUpdate(query);
                }

                // create the java mysql update preparedstatement
                String query = "UPDATE User SET " + columnName + " = '" + newData + "' WHERE username = '" + username + "'";
                Statement stm = getStatement();
                stm.executeUpdate(query);

                return true;

            } catch (Exception e) {
                System.out.println("UPDATEINFO: Sql.. " + e.toString());
            } disconnect();
        return false;
    }

    public boolean findUser(String username) {
        String selectUsername = "SELECT * FROM User WHERE username = '" + username.trim() + "'";
        //String selectUsername = "SELECT * FROM Analyst";
        setup(); //Setup the connection to the database

        try {

            ResultSet res = null;
            res = getStatement().executeQuery(selectUsername);

            if (res.next()) {
                if(res.getString("Username").equals(username.trim())) {
                    res.close();
                    disconnect();
                    return true; //brukeren finnes
                }

            }
            System.out.println("The user does not exsist");
            res.close();


        } catch (SQLException e) {
            System.out.println("FIND USER: Lost connection to the database.." + e.toString());
        }

        disconnect();


        return false; //brukeren finnes ikke.

    }

    public int findUsertype(String username){
        String usertype = "";
        ResultSet res = null;
        int actualUsertype = -1;
        boolean continuesearch = true;

        setup(); //Setup the connection to the database

        try {

            for (int i = 0; i < 3; i++) {
                usertype = findUserByIndex(i);
                res = getStatement().executeQuery("SELECT * FROM " + usertype + " WHERE username = '" + username + "'");

                if (res.next()) {
                    actualUsertype = i;
                    continuesearch = false;
                }

            }
            res.close();

        } catch(Exception e){
            System.out.println("FINDUSERTYPE:" + e.toString());
        }

        disconnect();

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
        setup(); //Setup the connection to the database

        try {
            //Deletes the row in both table "User" and the spesific usertype.
            //getStatement().executeQuery("DELETE FROM " + usertype + " WHERE username = '" + username + "'");
            //getStatement().executeQuery("DELETE FROM User WHERE username = '" + username + "'");

            PreparedStatement st = getConnection().prepareStatement("DELETE FROM " + usertype + " WHERE username = '" + username + "'");
            //st.setString(1,name);
            st.executeUpdate();

            PreparedStatement st2 = getConnection().prepareStatement("DELETE FROM User WHERE username = '" + username + "'");
            //st.setString(1,name);
            st2.executeUpdate();

            //Double checks that the user actually was deleted sucsessfully
            ResultSet res = getStatement().executeQuery("SELECT * FROM " + usertype + " WHERE username = '" + username + "'");
            if(!(res.next())){
                System.out.println("User deletet sucsessfully.");
                res.close();
                disconnect();
                return true;
            }else {
                System.out.println("User was not deleted..");
                res.close();
                disconnect();
                return false;
            }
        } catch(SQLException e){
            System.out.println("DELETEUSER: Something went wrong." + e.toString());
        }

        disconnect();
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
        setup();

        CryptWithMD5 cp = new CryptWithMD5();
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
            preparedStmt.setString(5, cp.cryptWithMD5(password.trim()));

            preparedStmt.execute(); //Executing the prepared statement

            String query2 = "INSERT INTO "
                    + usertype
                    + "(username)"
                    + "VALUES (?)";
            PreparedStatement preparedStmt2 = getConnection().prepareStatement(query2);
            preparedStmt2.setString(1, username);

            preparedStmt2.execute(); //Executing the prepared statement

        }catch(Exception e){
            System.out.println("REGISTERUSER: Something went wrong." + e.toString());
            e.printStackTrace();
        }

        disconnect();
        return true;
    }

    public String getName(String username){
        String query = "SELECT firstname, lastname FROM User WHERE username = '" + username + "'";
        String fullName = "";
        setup();

        try {
            ResultSet res = getStatement().executeQuery(query);

            if(res.next()){
                this.firstname = res.getString("firstname");
                this.lastname = res.getString("lastname");
                fullName = firstname + " " + lastname;
            }
            res.close();
        }catch(Exception e){
            System.out.println("GETNAME: " + e.toString());
        }

        disconnect();
        return fullName;
    }

    public String getFirstname(){
        return firstname;
    }

    public String getLastname(){
        return lastname;
    }

    public String getFirstname(String username){
        String query = "SELECT firstname FROM User WHERE username = '" + username + "'";
        String firstnameString = "";
        setup();

        try {
            ResultSet res = getStatement().executeQuery(query);

            if(res.next()){
                firstnameString = res.getString("firstname").trim();
            }
            res.close();
        }catch(Exception e){
            System.out.println("GETFIRSTNAME: " + e.toString());
        }

        disconnect();
        return firstnameString;
    }

    public String getLastname(String username){
        String query = "SELECT lastname FROM User WHERE username = '" + username + "'";
        String lastnameString = "";
        setup();

        try {
            ResultSet res = getStatement().executeQuery(query);

            if(res.next()){
                lastnameString = res.getString("lastname").trim();
            }
            res.close();
        }catch(Exception e){
            System.out.println("GETLASTNAME: " + e.toString());
        }

        disconnect();
        return lastnameString;
    }


    public String getTelephone(String username){
        String query = "SELECT telephone FROM User WHERE username = '" + username + "'";
        String telephone = "";
        setup();

        try {
            ResultSet res = getStatement().executeQuery(query);

            if(res.next()){
                telephone = res.getString("telephone");
            }
            res.close();
        }catch(Exception e){
            System.out.println("GETTELEPHONE: " + e.toString());
        }

        disconnect();
        return telephone;

    }

    public String fromCharToString(char[] passwordChar){

        String passwordString = "";

        for (int i = 0; i < passwordChar.length; i++) { //goes throuh the whole array and creates a String.
            passwordString += passwordChar[i];
        }
        return passwordString;

    }

    public static void main(String[]args){
        User user = new User();

        System.out.println(new CryptWithMD5().cryptWithMD5("Geirmama321"));

        user.updatePassword("Geirmama321", "Geirmama123", "Geirmama123", "Geirmama");
    }
}

