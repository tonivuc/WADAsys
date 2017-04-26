package backend;

import databaseConnectors.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tvg-b on 29.03.2017.
 */
public class Watchlist extends DatabaseManager {

    private int numberOfAthletes; // The number of athletes currently in the systems database

    public Watchlist () {

        setup();

        try (ResultSet res = getStatement().executeQuery("SELECT count(athleteID) AS numberOfAthletes FROM Athlete")) {

            while (res.next()) {
                numberOfAthletes = res.getInt("numberOfAthletes");
            }
            res.close();


        } catch (SQLException e) {
            System.out.println("SQL Exception in constructor in class Watchlist: " + e);
        }
        disconnect();
    }

    /**
     * Takes a date and returns an ArrayList of athletes that have a measured haemoglobin level that exceeds
     * the expected haemoglobin level.
     *
     * @param date
     * @return ArrayList<athlete>
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    public List<Athlete> getSuspiciousAthletes (LocalDate date) {

        List<Athlete> athletes = new ArrayList<Athlete>();
        ArrayList<String> athleteIDs = getAthleteIDs();


        for (int i = 0; i < athleteIDs.size(); i++) {

            Athlete athlete = new Athlete(Integer.parseInt(athleteIDs.get(i)));
            athlete.setItUp();
            AthleteGlobinDate agd = athlete.getLastMeasuredGlobinLevel(date);
            athlete.takeItDown();

            double expectedGlobinLevel = athlete.getExpectedGlobinLevel(date);

            if (agd != null && agd.getHaemoglobinLevel() != 0 && expectedGlobinLevel != 0 && agd.getHaemoglobinLevel() > expectedGlobinLevel) {
                athletes.add(athlete);
            }
        }

        return athletes;
    }

    public ArrayList<String> getAthleteIDs () {
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


    /**
     * Main method for testing of the class
     * @param args
     */

    public static void main(String[] args) {

        Watchlist wl = new Watchlist();
        LocalDate date = LocalDate.now();
        List<Athlete> athletes = wl.getSuspiciousAthletes(date);


        for (int i = 0; i < athletes.size(); i++) {
            System.out.println(athletes.get(i) + " " + athletes.get(i).getGlobinDeviation() + " %");
        }



    }

}
