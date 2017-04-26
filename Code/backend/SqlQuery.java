package backend;

import databaseConnectors.DatabaseManager;

import java.sql.PreparedStatement;

/**
 * Created by camhl on 26.04.2017.
 */
public class SqlQuery extends DatabaseManager{

    public boolean addHaemoglobinLevel(String entry_creator, double reading, java.sql.Date date, int athlete_id){
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

        }catch(Exception e){
            System.out.println("REGISTER HAEMOGLOBINLEVEL: Something went wrong." + e.toString());
            return false;
        }

        disconnect();
        return true;
    }


}
