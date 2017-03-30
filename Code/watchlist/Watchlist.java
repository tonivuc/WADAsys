package watchlist;

import athlete.Athlete;
import athlete.AthleteGlobinDate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by tvg-b on 29.03.2017.
 */
public class Watchlist extends DatabaseConnection.DatabaseManager {

    private int numberOfAthletes;

    public Watchlist () {

        try (ResultSet res = getStatement().executeQuery("SELECT count(athleteID) AS numberOfAthletes FROM Athlete")) {

            while (res.next()) {
                numberOfAthletes = res.getInt("numberOfAthletes");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        disconnect();

    }

    /**
     * Takes a date
     *
     * @param date
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    public ArrayList<Athlete> getSuspiciousAthletes (LocalDate date) throws SQLException, ClassNotFoundException{

        ArrayList<Athlete> athletes = new ArrayList<Athlete>();

        for (int i = 0; i < numberOfAthletes; i++) {

            Athlete athlete = new Athlete(i);
            AthleteGlobinDate agd = athlete.getLastMeasuredGlobinLevel();

            if (agd.getHaemoglobinLevel() > athlete.getExpectedGlobinLevel(date)) {
                athletes.add(athlete);
            }

        }

        return athletes;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Watchlist wl = new Watchlist();
        ArrayList<Athlete> athletes = wl.getSuspiciousAthletes(LocalDate.now());

        for (int i = 0; i < athletes.size(); i++) {
            System.out.println(athletes.get(i));
        }
    }

}
