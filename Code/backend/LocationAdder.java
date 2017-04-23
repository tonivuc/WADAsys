package backend;

import databaseConnectors.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tvg-b on 23.04.2017.
 */


public class LocationAdder extends DatabaseManager {

    public LocationAdder () {

    }

    public void addLocations (ArrayList<String[]> locationList) {

        setup();
        for (int i = 0; i < locationList.size(); i++) {

            String[] location = locationList.get(i);

            if (athleteExists(location[0], location[1])) {
                int athleteID = getAthleteID(location[0], location[1]);
                addLocation(location, athleteID);

            } else {

                try {
                    String query = "INSERT INTO Athlete (firstname, lastname) VALUES ('" + location[0] + "', '" + location[1] + "')";
                    System.out.println("New athlete added");
                    getStatement().executeUpdate(query);
                    int athleteID = getAthleteID(location[0], location[1]);
                    addLocation(location, athleteID);
                    System.out.println("Location added for new athlete");

                } catch (SQLException e) {
                    System.out.println("SQLException in method addLocations in class LocationAdder.java: " + e);
                }


            }




        }
        disconnect();

    }

    public void addLocation (String[] locationArray, int athleteID) {

        String location = locationArray[2];

        java.sql.Date sqlFromDate = null;
        java.sql.Date sqlToDate = null;
        sqlFromDate = makeSQLDate(locationArray[3]);
        sqlToDate = makeSQLDate(locationArray[4]);

        try {

            if (!locationOverlaps(athleteID, locationArray[3], locationArray[4])) {
                String query = "INSERT INTO Athlete_Location (athleteID, from_date, to_date, location) VALUES ('" + athleteID + "', '" + sqlFromDate + "', '" + sqlToDate + "', '" + location + "')";
                getStatement().executeUpdate(query);
                System.out.println("Athlete_Location: " + location + " added");

                if (!locationExists(location)) {
                    float altitude = 0;
                    String newLocationQuery = "INSERT INTO Location (location, altitude) VALUES ('"+ location +"', '" + altitude + "')";
                    getStatement().executeUpdate(newLocationQuery);
                }
            }



        } catch (SQLException e) {
            System.out.println("SQLException int method addLocation in class Athlete.java: " + e);
        }
    }

    public int getAthleteID (String firstname, String lastname) {

        int athleteID = 0;

        try {
            String query = "SElECT athleteID FROM Athlete WHERE Athlete.firstname = '"+ firstname +"' AND Athlete.lastname = '"+ lastname +"'";
            ResultSet res = getStatement().executeQuery(query);

            while (res.next()) {

                athleteID = res.getInt("athleteID");
                res.close();
                return athleteID;
            }

            res.close();

        } catch (SQLException e) {
            System.out.println("SQLException in method athleteExists in class LocationAdder.java: " + e);

        }

        return athleteID;
    }

    public boolean locationExists (String location) {
        try {
            String query = "SELECT location FROM Location WHERE location = '"+ location +"'";
            ResultSet res = getStatement().executeQuery(query);

            while (res.next()) {
                res.close();
                return true;
            }
            res.close();
        } catch (SQLException e) {
            System.out.println("SQLException in method locationExists in class LocationAdder.java: " + e);
        }

        return false;
    }

    public boolean athleteExists (String firstname, String lastname) {

        try {
            String query = "SElECT athleteID FROM Athlete WHERE Athlete.firstname = '"+ firstname +"' AND Athlete.lastname = '"+ lastname +"'";
            ResultSet res = getStatement().executeQuery(query);

            while (res.next()) {
                res.close();
                return true;
            }
            res.close();

        } catch (SQLException e) {
            System.out.println("SQLException in method athleteExists in class LocationAdder.java: " + e);
        }

        return false;

    }

    public java.sql.Date makeSQLDate (String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date javaDate = null;
        try {
            javaDate = sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println("ParseException in method makeSqlDate in class LocationAdder.java: " + e);
        }

        java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());

        return sqlDate;
    }

    public boolean locationOverlaps (int athleteID, String newFromDate, String newToDate) {


        java.sql.Date newSQLfromDate = makeSQLDate(newFromDate);
        java.sql.Date newSQLtoDate = makeSQLDate(newToDate);

        try {

            String query = "SELECT Athlete_Location.from_date, Athlete_Location.to_date FROM Athlete_Location WHERE athleteID = '"+ athleteID +"'";
            ResultSet res = getStatement().executeQuery(query);

            while (res.next()) {
                if (newSQLfromDate.equals(res.getDate("from_date")) || newSQLtoDate.equals(res.getDate("to_date"))) {
                    res.close();
                    return true;
                }
            }

            res.close();

        } catch (SQLException e) {
            System.out.println("SQLException in method locationOverlaps in class LocationAdder.java: " + e);
        }

        return false;

    }

    public static void main(String[] args) {


        LocationAdder la = new LocationAdder();
        CSVReader csvr = new CSVReader();

        ArrayList<String[]> locationsList = csvr.getCSVContent();
        la.addLocations(locationsList);



    }

}
