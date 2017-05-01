package backend;

/**
 *
 * @author Camilla Haaeim Larsen
 */
import databaseConnectors.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that is used to manage a user. It has functionality like deleting a user,
 * finding users in the database and some other functions for logging in the right user.
 */
public class UserManager extends DatabaseManager{

    /**
     * Checks if the username exict in the database.
     * @param username username of the user you want ot find.
     * @return boolean true if successful, false if not.
     */
    public boolean findUser(String username) {
        String selectUsername = "SELECT * FROM User WHERE username = '" + username.trim() + "'";
        //String selectUsername = "SELECT * FROM Analyst";
        setup(); //ConfigWindow the connection to the database

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

    /**
     * Finds the usertype (collector, analyst or admin) of a user.
     * @param username username of the user you want to find the type of
     * @return int
     */
    public int findUsertype(String username){
        String usertype = "";
        ResultSet res = null;
        int actualUsertype = -1;
        boolean continuesearch = true;

        setup(); //ConfigWindow the connection to the database

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

    /**
     * Help method to findUserType(). Returns "Admin" if "i" is "0",
     * "Analyst" if "i" is "1" and "Collector" if "i" is "2".
     * @param i index that you get from the "findUserType(String username)" method.
     * @return String the type of user
     */
    public String findUserByIndex(int i){
        if(i == 0) return "Admin";
        if(i == 1) return "Analyst";
        if(i == 2) return "Collector";

        return null;
    }

    /**
     * Delete a user from the database. Returns true if successful, and false if the user does not exist
     * or something went wrong while connecting to the database.
     * @param username username of the user you want to delete.
     * @return returns false if the user does not exist
     */
    public boolean deleteUser(String username){
        // Finds out what kind of usertype the user is (Admin, Analyst, Collector)
        int usertypeInt = findUsertype(username);
        String usertype = findUserByIndex(usertypeInt);
        setup(); //ConfigWindow the connection to the database

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

    /**
     * Register a new user to the system and puts it in the database.
     * @param firstname first name of the user you want to register.
     * @param lastname  last name of the user you want to register.
     * @param telephone telephone number of the user you want to register.
     * @param username  username of the user you want to register.
     * @param password  password of the user you want to register.
     * @param usertype  usertype of the user you want to register.
     * @return boolean true if successful, false if not.
     */
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

    /**
     * Checks if username exist and that the password is correct.
     * Using two helping methods to do so.
     * Returns true if ok.
     * @param username the username of the user you want to check if exist.
     * @param password the password you want to check if correct.
     * @return boolean true, username exist and password is correct, false if not.
     */
    public boolean login(String username, String password) {
        if (findUser(username) == false) return false;
        if(checkPassword(username, password) == false) return false;

        return true;


    }

    /**
     * Input password is being crypted with MD5 and compared with the crypted password saved in the dataBase.
     * The actual password is never uncrypted, this is for safety reasons.
     * @param username username of the user you want to check password for.
     * @param password password that you want to check.
     * @return boolean true if correct password, false if wrong password or something went wrong when fetching from database.
     */
    public boolean checkPassword(String username, String password) {

        //Creating a mySql-statement
        String selectPassword = "SELECT password FROM User WHERE username = '" + username + "'";

        //Crypting the input with MD5
        String cryptInput = new CryptWithMD5().cryptWithMD5(password);

        boolean ok = true;
        String actualPassword = "";

        setup(); //ConfigWindow the connection to the database

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
}
