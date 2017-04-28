package backend;

/**
 *
 * @author Trym Vegard Gjelseth-Borgen
 */

import databaseConnectors.DatabaseManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class made to create an ArrayList of AthleteLocations.
 */
public class AthleteLocationsList extends DatabaseManager {

    /**
     * Returns an ArrayList of AthleteLocation Objects that each contains
     * a location and the number of people at that location at the given date.
     * @param date Number of people at that date.
     * @return ArrayList of AthleteLocation Objects.
     */
    public ArrayList<AthleteLocation> getAthleteLocations (LocalDate date) {
        ArrayList<AthleteLocation> locationsList = new ArrayList<AthleteLocation>();

        setup();

        String query = "SELECT location, count(location) AS numberOfPeople FROM Athlete_Location\n" +
                       "WHERE from_date <= '"+date+"' && to_date >= '"+date+"'\n" +
                       "GROUP BY location";

        try {
            ResultSet res = getStatement().executeQuery(query);

            while (res.next()) {

                String location = res.getString("location");
                int numberOfPeople = res.getInt("numberOfPeople");
                AthleteLocation al = new AthleteLocation(location, numberOfPeople);

                locationsList.add(al);
            }

            res.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Collections.sort(locationsList, Collections.reverseOrder());
        disconnect();
        return locationsList;
    }

    public static void main(String[] args) {
        AthleteLocationsList athleteLocationList = new AthleteLocationsList();
        athleteLocationList.getAthleteLocations(LocalDate.now());
    }
}
