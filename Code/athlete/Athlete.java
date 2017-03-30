package athlete;

import DatabaseConnection.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tvg-b on 23.03.2017.
 */
public class Athlete extends DatabaseManager {

    // private final Connection connection;
    // private final Statement statement;

    int athleteID;
    String firstname;
    String lastname;
    String gender;
    String nationality;
    String sport;
    int telephone;
    double normalHeamoglobinLevel; // The expected base haemoglobin level, dependent on gender

    public Athlete (int athleteID) throws ClassNotFoundException, SQLException {

        this.athleteID = athleteID;

        // connection = getConnection();
        // statement = connection.createStatement();

        setup();
        ResultSet res = getStatement().executeQuery("SELECT * FROM Athlete WHERE athleteID = '"+athleteID+"'");

        while(res.next()) {
            this.firstname = res.getString("firstname");
            this.lastname = res.getString("lastname");
            this.gender = res.getString("gender");
            this.nationality = res.getString("nationality");
            this.sport = res.getString("sport");
            this.telephone = res.getInt("telephone");
        }

        if (gender.equalsIgnoreCase("male")) {
            normalHeamoglobinLevel = 16;
        } else {
            normalHeamoglobinLevel = 14;
        }

        disconnect();
    }

    public String getFirstname () {
        return firstname;
    }

    public String getLastname () {
        return lastname;
    }

    public String getNationality () {
        return nationality;
    }

    public String getSport () {
        return sport;
    }

    public String getGender () {
        return gender;
    }

    public int getTelephone () {
        return telephone;
    }

    public int getAthleteID () {
        return athleteID;
    }

    /**
     * Returns an ArrayList with AthleteGlobinDate objects that contains
     * all the measured haemoglobin dates, the corresponding dates and the athlete's name.
     *
     * @return
     * @throws SQLException
     */

    public ArrayList<AthleteGlobinDate> getMeasuredAthleteGlobinDates () throws SQLException {

        ArrayList<AthleteGlobinDate> athleteGlobinDates = new ArrayList<AthleteGlobinDate>();

        setup();

        ResultSet res1 = getStatement().executeQuery( "SELECT Athlete.firstname, Athlete.lastname, Globin_readings.globin_reading, Globin_readings.date FROM Athlete LEFT JOIN Globin_readings ON Globin_readings.athleteID = Athlete.athleteID WHERE Athlete.athleteID = '"+athleteID+"'");
        while (res1.next()) {
            String firstname = res1.getString("firstname");
            String lastname = res1.getString("lastname");
            double globinReading = res1.getDouble("globin_reading");
            java.util.Date date = res1.getDate("date");

            AthleteGlobinDate agd = new AthleteGlobinDate(globinReading, date, firstname, lastname);
            athleteGlobinDates.add(agd);

        }
        disconnect();

        return athleteGlobinDates;
    }


    /**
     * Returns an ArrayList containing AthleteGlobinDate objects that contains
     * from and to date for every place the athlete goes to. The objects also contains the max haemoglobin level at
     * that place, and the name of the athlete.
     *
     * @return
     * @throws SQLException
     */

    private ArrayList<AthleteGlobinDate> getExpectedAthleteGlobinDates () throws SQLException {

        ArrayList<AthleteGlobinDate> athleteGlobinDates = new ArrayList<AthleteGlobinDate>();
        double expectedHaemoglobinLevel = 0;

        setup();

        ResultSet res1 = getStatement().executeQuery("SELECT Athlete.firstname, Athlete.lastname, Athlete.gender, Location.altitude, Athlete_Location.from_date, Athlete_Location.to_date\n" +
                                                     "FROM Athlete\n" +
                                                     "LEFT JOIN Athlete_Location ON Athlete.athleteID = Athlete_Location.athleteID\n" +
                                                     "LEFT JOIN Location ON Athlete_Location.latitude = Location.latitude AND Athlete_Location.longitude = Location.longitude\n" +
                                                     "WHERE Athlete.athleteID = '"+athleteID+"'");

        while (res1.next()) {
            String firstname = res1.getString("firstname");
            String lastname = res1.getString("lastname");
            String gender = res1.getString("gender");
            float altitude = res1.getFloat("altitude");
            Date fromdate = res1.getDate("from_date");
            Date todate = res1.getDate("to_date");


            if (gender.equalsIgnoreCase("Male")) {
                expectedHaemoglobinLevel = getMaxGlobinLevel(altitude, true);
            } else {
                expectedHaemoglobinLevel = getMaxGlobinLevel(altitude, false);
            }

            expectedHaemoglobinLevel = Math.round((expectedHaemoglobinLevel * 100)) / 100.0;

            AthleteGlobinDate agd = new AthleteGlobinDate(expectedHaemoglobinLevel, fromdate, todate, firstname, lastname);
            athleteGlobinDates.add(agd);
        }
        disconnect();

        return athleteGlobinDates;
    }


    /**
     * Takes an altitude value and a boolean (true if male, false if female) as parameters, and returns the max haemoglobin level
     * at that specific altitude.
     *
     * @param altitude
     * @param male
     * @return
     */

    private double getMaxGlobinLevel (float altitude, boolean male) {

        double maxGlobinLevel = 0;

        if (male) {
            double increase = (altitude/250) / 100;
            maxGlobinLevel = normalHeamoglobinLevel + normalHeamoglobinLevel * increase;
            return maxGlobinLevel;
        } else {
            double increase = (altitude/250) * 0.7 / 100;
            maxGlobinLevel = normalHeamoglobinLevel + normalHeamoglobinLevel * increase;
            return maxGlobinLevel;
        }
    }


    /**
     * Takes a LocalDate object as parameter and returns the athletes expected haemoglobin level at that specific date.
     * Returns 0 if there is noe data about the athlete at that date.
     *
     * @param date
     * @return
     * @throws SQLException
     */

    public double getExpectedGlobinLevel(LocalDate date) throws SQLException {

        double expGlobinLevel = 0;
        double globinLevel = normalHeamoglobinLevel;

        ArrayList<AthleteGlobinDate> agd = getExpectedAthleteGlobinDates();

        for (int i = 0; i < agd.size(); i++) {

            LocalDate fromDate = Instant.ofEpochMilli(agd.get(i).getFromDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate toDate = Instant.ofEpochMilli(agd.get(i).getToDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

            long daysBetween = ChronoUnit.DAYS.between(fromDate, toDate);
            long fromDaysBetween = ChronoUnit.DAYS.between(fromDate, date);
            long toDaysBetween = ChronoUnit.DAYS.between(date, toDate);

            if (fromDaysBetween >= 0 && toDaysBetween >= 0) {

                globinLevel = globinLevel + (agd.get(i).getHaemoglobinLevel() - globinLevel) / (1 + Math.pow(Math.E, -0.5 * (fromDaysBetween - 14)));
                globinLevel = Math.round((globinLevel * 100)) / 100.0;
                return globinLevel;

            }

            globinLevel = globinLevel + (agd.get(i).getHaemoglobinLevel() - globinLevel) / (1 + Math.pow(Math.E, -0.5 * (daysBetween - 14)));
            globinLevel = Math.round((globinLevel * 100)) / 100.0;

            System.out.println("mordi");

        }

        return 0;

    }


    /**
     * Returns an AthleteGlobinObject containing the latest measured haemoglobin level, and the date
     * if was measured.
     *
     * @return
     * @throws SQLException
     */

    public AthleteGlobinDate getLastMeasuredGlobinLevel () throws SQLException {

        AthleteGlobinDate athleteGlobinDate;

        setup();

        ResultSet res = getStatement().executeQuery("SELECT max(date) as latestdate, globin_reading FROM Globin_readings WHERE athleteID = '"+athleteID+"'");
        LocalDate latestdate = null;
        Date date = null;
        double globinReading = 0;

        while (res.next()) {
            date = res.getDate("latestdate");
            latestdate = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            globinReading = res.getDouble("globin_reading");
        }

        disconnect();

        LocalDate currentDate = LocalDate.now();

        long daysBetween = ChronoUnit.DAYS.between(latestdate, currentDate);

        if (daysBetween > 28 ) {
            return null;
        }

        athleteGlobinDate = new AthleteGlobinDate(globinReading, date);
        return athleteGlobinDate;

    }


    public double getGlobinDeviation (LocalDate date) throws SQLException {

        double globinDeviation = 0;

        globinDeviation = getLastMeasuredGlobinLevel().getHaemoglobinLevel() / getExpectedGlobinLevel(date) * 100;

        System.out.println(getLastMeasuredGlobinLevel().getHaemoglobinLevel());
        System.out.println(getExpectedGlobinLevel(date));

        return globinDeviation;

    }


    /**
     * Returns the athletes full name, gender, nationality, sport and telephonenumber.
     *
     * @return
     */

    public String toString () {
        return firstname + " " + lastname + ", " + gender + ", " + nationality + ", " + sport + ", " + telephone;
    }


    public static void main(String[] args) {



        try {


            Athlete athlete = new Athlete(1);
            System.out.println(athlete.getFirstname() + " " + athlete.getLastname() +  "'s globin deviation is at " + athlete.getGlobinDeviation(LocalDate.now()) + "%");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
