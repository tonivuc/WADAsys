package backend;

/**
 *
 * @author Trym Vegard Gjelseth-Borgen
 */

import backend.geoLocation.ElevationFinder;
import databaseConnectors.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * When a new locations is being added trough the CSV-file this class is used to
 * add both the location and a new athlete if the athlete does not already exist.
 * If the athlete exists in database, only a new locatio will be added.
 */
public class LocationAdder extends DatabaseManager {

    /**
     * Adds locations to athletes in the database, with from and to dates. If the athlete does not exist,
     * a new athlete is created. This is only based on the athlete's name. Meaning to athletes with identical
     * names can not exist in the system. New athletes will only be added with full names. The rest must be
     * manually inserted by a user. If the location of the athlete does not already exist in the database,
     * a new location will be created and added to the database. The height of the location will be found
     * added to the database using the name of the location only. If the name of the location is not found in
     * the Google Maps, the location will still be added, but the height will be set to 0 meters.
     * @param csvList An ArrayList containing String[] that contains cells from the CSV-files.
     */
    public void addLocations (ArrayList<String[]> csvList) {

        setup();
        try {
            for (int i = 0; i < csvList.size(); i++) {

                String[] location = csvList.get(i);

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

        } catch (Exception e) {
            System.out.print("Something went wrong adding the locations of the athletes to the program. Error: "+e);
        }
        finally {
            disconnect();
        }

    }

    /**
     * Adds a new location to an athlete with from date, and to date into the database.
     * If the athlete already has a location at the in the database, with the same
     * from date and to date, a new location will not be added.
     * If the location does not already exist in the database, it will be inserted together
     * with its altitude into the database. If the location is not found in Google Maps,
     * the location will still be added, but the altitude will be set to 0 meters.
     * @param csvLine a line from a CSV-file (firstName, lastName, location, fromDate, toDate)
     * @param athleteID athleteID of the athlete you want to add new locations to.
     */
    private void addLocation (String[] csvLine, int athleteID) {

        String location = csvLine[2];

        java.sql.Date sqlFromDate = null;
        java.sql.Date sqlToDate = null;
        sqlFromDate = makeSQLDate(csvLine[3]);
        sqlToDate = makeSQLDate(csvLine[4]);

        try {

            if (!locationOverlaps(athleteID, csvLine[3], csvLine[4])) {
                String query = "INSERT INTO Athlete_Location (athleteID, from_date, to_date, location) VALUES ('" + athleteID + "', '" + sqlFromDate + "', '" + sqlToDate + "', '" + location + "')";
                getStatement().executeUpdate(query);
                System.out.println("Athlete_Location: " + location + " added");

                if (!locationExists(location)) {
                    ElevationFinder ef = new ElevationFinder();
                    float altitude = ef.getElevation(location);
                    String newLocationQuery = "INSERT INTO Location (location, altitude) VALUES ('"+ location +"', '" + altitude + "')";
                    getStatement().executeUpdate(newLocationQuery);
                }
            }

        } catch (SQLException e) {
            System.out.println("SQLException int method addLocation in class Athlete.java: " + e);
        }
    }

    /**
     * Gets the athleteID of an athlete based on his/her first name and last name.
     * Returns 0 if the athlete does not already exist in the database.
     * @param firstname first name of the athlete
     * @param lastname last name of the athlete
     * @return int the athlete's athleteID
     */
    private int getAthleteID (String firstname, String lastname) {

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

    /**
     * Checks the database if a location exist based on the locations name. If a location exists,
     * it will return true, if not it will return false.
     * @param location a String with the name of the location on the from (Country, City, Adrdess, etc.)
     * @return boolean true if exists, false if not
     */
    private boolean locationExists (String location) {
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

    /**
     * Checks the databse if an athlete exists based on the athletes first and last name. if an
     * athlete exist it will return true, if not it will return false.
     * @param firstname first name of the athlete
     * @param lastname last name of the athlete
     * @return boolean true if exist, false if not
     */
    private boolean athleteExists (String firstname, String lastname) {

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

    /**
     * Creates a java.sql.Date Object from a String on the format "yyyy-MM-dd".
     * Returns null if the date String is in the wrong format.
     * @param dateString String date on the format "yyyy-MM-dd".
     * @return java.sql.Date
     */
    private java.sql.Date makeSQLDate (String dateString) {
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

    /**
     * Checks if a from date and a to date, already has been added to the athlete's locations.
     * if so, it will return true, if not it will return false.
     * @param athleteID athleteID of the athlete you want to check for
     * @param newFromDate newFromDate that you want to check for
     * @param newToDate newToDate that you want to check for
     * @return boolean true if they already exist in the database, false if not
     */
    private boolean locationOverlaps (int athleteID, String newFromDate, String newToDate) {

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
        CSVReader csvReader = new CSVReader();
        ArrayList<String[]> ls = csvReader.getCSVContent();

        new Thread(new Runnable() {
            @Override
            public void run() {
                LocationAdder la = new LocationAdder();
                la.addLocations(ls);
            }
        }).start();

    }
}
