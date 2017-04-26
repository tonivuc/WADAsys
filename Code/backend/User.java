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

    public boolean updatePassword (String newPassword) {

        String cryptedPassword = new CryptWithMD5().cryptWithMD5(newPassword);

        if(updatePasswordSql(cryptedPassword)){
            return true;
        }

        return false;
    }

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
        User user = new User();

        System.out.println(new CryptWithMD5().cryptWithMD5("Geirmama321"));
    }
}

