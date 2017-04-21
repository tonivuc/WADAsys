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

    private int numberOfAthletes;

    public Watchlist () {

        setup();

        try (ResultSet res = getStatement().executeQuery("SELECT count(athleteID) AS numberOfAthletes FROM Athlete")) {

            while (res.next()) {
                numberOfAthletes = res.getInt("numberOfAthletes");
            }
            disconnect();

        } catch (SQLException e) {
            disconnect();
            System.out.println(e);
        }


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

        for (int i = 1; i < numberOfAthletes - 1; i++) {

            Athlete athlete = new Athlete(i);
            AthleteGlobinDate agd = athlete.getLastMeasuredGlobinLevel(date);

            if (agd.getHaemoglobinLevel() != 0 && athlete.getExpectedGlobinLevel(date) != 0 && agd.getHaemoglobinLevel() > athlete.getExpectedGlobinLevel(date)) {
                athletes.add(athlete);
            }
        }

        return athletes;
    }

    public static void main(String[] args) {
        Watchlist wl = new Watchlist();
        LocalDate date = LocalDate.now();
        List<Athlete> athletes = wl.getSuspiciousAthletes(date);

        for (int i = 0; i < athletes.size(); i++) {
            System.out.println(athletes.get(i) + " " + athletes.get(i).getGlobinDeviation(date) + " %");
        }
    }

}
