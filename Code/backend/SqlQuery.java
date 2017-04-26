package backend;

import databaseConnectors.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Camilla Haaheim Larsen
 */

public class SqlQuery extends DatabaseManager {


    public boolean addHaemoglobinLevel(String entry_creator, double reading, java.sql.Date date, int athlete_id) {
        setup();


        try {

            String query = "INSERT INTO Globin_readings"               //Adding user into the "User"-table in the database
                    + "(athleteID, globin_reading, date, entry_creator)"   //Adding first name, last name, telephone, username, password
                    + "VALUES (?,?,?,?)";       //The values comes from user-input


            //getStatement().executeQuery(query);
            PreparedStatement preparedStmt = getConnection().prepareStatement(query);  //Adding the user into the database, getting the users input
            preparedStmt.setInt(1, athlete_id);
            preparedStmt.setDouble(2, reading);
            preparedStmt.setDate(3, date);
            preparedStmt.setString(4, entry_creator);

            preparedStmt.execute(); //Executing the prepared statement

        } catch (Exception e) {
            System.out.println("REGISTER HAEMOGLOBINLEVEL: Something went wrong." + e.toString());
            return false;
        }

        disconnect();
        return true;
    }

    public boolean updateReading(String newReading, String columnName, int athleteID, String date) {
        setup();
        //if (columnName.equals("athleteID")) {
        try {
            // create the java mysql update preparedstatement
            String query = "UPDATE Globin_readings SET " + columnName + " = '" + newReading + "' WHERE athleteID = '" + athleteID + "' AND date = " + date;
            Statement stm = getStatement();
            stm.executeUpdate(query);

            return true;

        } catch (Exception e) {
            System.out.println("UPDATEINFO: Sql.. " + e.toString());
        }
        disconnect();
        return false;
    }

    public boolean deleteReading(int athleteID, String date){
        //System.out.println("Kom inn her");
        setup(); //Setup the connection to the database
        String sqlDate = date;

        try {
            //getStatement().executeQuery("DELETE FROM " + columnName + " WHERE athleteID = '" + athleteID + "' AND date = " + date);

            PreparedStatement st = getConnection().prepareStatement("DELETE FROM  Globin_readings WHERE athleteID = '" + athleteID + "' AND date = '" + sqlDate + "'");
            //st.setString(1,name);
            st.executeUpdate();

            //Double checks that the user actually was deleted sucsessfully
            ResultSet res = getStatement().executeQuery("SELECT * FROM Globin_readings WHERE athleteID = '" + athleteID + "' AND date = '" + sqlDate + "'");
            if(!(res.next())){
                System.out.println("Haemoglobin level deleted sucsessfully.");
                res.close();
                disconnect();
                return true;
            }else {
                System.out.println("Haemoglobin level was not deleted..");
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
}
