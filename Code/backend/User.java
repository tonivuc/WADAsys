package backend;
import databaseConnectors.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Camilla Haaheim Larsen
 * Fixed by Toni
 *
 */

public class User extends DatabaseManager {

    /**
     * The user's username (email).
     */
    String username;

    /**
     * The user's first name.
     */
    String firstname;

    /**
     * The user's last name.
     */
    String lastname;

    /**
     * The user's telephone number.
     */
    String telephone;

    /**
     * Constructs a new User Object. Sets all the instance variables based on the username,
     * with data from the database.
     * @param username username of the user
     */
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

    public User(){

    }

    /**
     * Updates the password of the user, crypts it and sends it into the database.
     * @param newPassword the new password that the user is going to have
     * @return boolean true if successful, false if not
     */
    public boolean updatePassword (String newPassword) {

        String cryptedPassword = new CryptWithMD5().cryptWithMD5(newPassword);

        if(updatePasswordSql(cryptedPassword)){
            return true;
        }

        return false;
    }

    /**
     * Updates the password of the user, crypts it and sends it into the database. If the old password
     * is not correct, the password will not be updated and it will return false. if newPassword1, and newPassword2
     * is not equal, the password will not be updated, and it will return false. If they are equal, and the oldpassword
     * is the same at the actual old password in the database, the password will be updated, and it will return true.
     * @param oldpassword the user's old password
     * @param newPassword1 the user's new password
     * @param newPassword2 the user's new password
     * @return boolean true if updated, false if not
     */
    public boolean updatePassword(String oldpassword, String newPassword1, String newPassword2) {

        if (new UserManager().checkPassword(username, oldpassword) == false) return false;

        if (newPassword1.equals(newPassword2)) {

            String cryptedPassword = new CryptWithMD5().cryptWithMD5(newPassword1);

            if(updatePasswordSql(cryptedPassword)){
                return true;
            }
        }

        return false;
    }

    /**
     * Updates the user's password in the database.
     * @param cryptedPassword the new password
     * @return boolean true if successful, false if not
     */
    public boolean updatePasswordSql(String cryptedPassword){

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

    /**
     * Updates a chosen column in the user table in the database with new data.
     * @param newData the new data the will be sent into the database
     * @param columnName the name of the column that it will be sent to
     * @param username the username of the user
     * @return boolean true if successful, false if not
     */
    public boolean updateInfo(String newData, String columnName, String username) {
        UserManager userManager = new UserManager();
        setup();
            try {
                if (columnName.equals("username")) {

                    int usertypeInt = userManager.findUsertype(username);
                    String usertype = userManager.findUserByIndex(usertypeInt);

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

        System.out.println(new CryptWithMD5().cryptWithMD5("Geirmama321"));
    }
}

