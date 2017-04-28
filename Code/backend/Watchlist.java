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
import java.util.List;

/**
 * Class made to create a watchlist of all the athletes that has a higher measured
 * haemoglobin level than they should.
 */
public class Watchlist extends DatabaseManager{


    /**
     * Takes a date and returns an ArrayList of athletes that have a measured haemoglobin level that exceeds
     * the expected haemoglobin level.
     * @param date date of which the expected levels are to be compared with the actual levels
     * @return ArrayLis of suspicious athletes
     */
    public List<Athlete> getSuspiciousAthletes (LocalDate date) {

        List<Athlete> athletes = new ArrayList<Athlete>();
        ArrayList<String> athleteIDs = getAthleteIDs();

            for (int i = 0; i < athleteIDs.size(); i++) {

                Athlete athlete = new Athlete(Integer.parseInt(athleteIDs.get(i)));
                setup();
                AthleteGlobinDate agd = athlete.getLastMeasuredGlobinLevel(date);
                disconnect();

                double expectedGlobinLevel = athlete.getExpectedGlobinLevel(date);

                if (agd != null && agd.getHaemoglobinLevel() != 0 && expectedGlobinLevel != 0 && agd.getHaemoglobinLevel() > expectedGlobinLevel) {
                    athletes.add(athlete);
                }
            }

        return athletes;
    }

    /**
     * Gets all the athleteIDs from the database and puts them into an ArrayList.
     * @return ArrayList of all the athleteIDs in the database
     */
    private ArrayList<String> getAthleteIDs () {
        ArrayList<String> athleteIDs = new ArrayList<String>();

        setup();
        try {
            String query = "SELECT athleteID FROM Athlete";
            ResultSet res = getStatement().executeQuery(query);

            while (res.next()) {
                athleteIDs.add("" + res.getInt("athleteID"));
            }
            res.close();
            disconnect();
            return athleteIDs;

        } catch (SQLException e) {
            System.out.println("SQLException in method getAthleteIDs in class Watchlist.java: " + e);
        }
        disconnect();
        return null;
    }

    public static void main(String[] args) {
        Watchlist wl = new Watchlist();
        List<Athlete> athletes = wl.getSuspiciousAthletes(LocalDate.now());

        for (int i = 0; i < athletes.size(); i++) {

            System.out.println(athletes.get(i).toString());
        }
    }
    
}
