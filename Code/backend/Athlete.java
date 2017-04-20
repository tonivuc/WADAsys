package backend;

import databaseConnectors.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Created by tvg-b on 23.03.2017.
 */
public class Athlete extends DatabaseManager {

    int athleteID;
    String firstname;
    String lastname;
    String gender;
    String nationality;
    String sport;
    String telephone;
    double normalHeamoglobinLevel; // The expected base haemoglobin level, dependent on gender

    public Athlete(int athleteID) {

        this.athleteID = athleteID;

        try {
            setup();

            ResultSet res = getStatement().executeQuery("SELECT * FROM Athlete WHERE athleteID = '" + athleteID + "'");

            while (res.next()) {
                this.firstname = res.getString("firstname");
                this.lastname = res.getString("lastname");
                this.gender = res.getString("gender");
                this.nationality = res.getString("nationality");
                this.sport = res.getString("sport");
                this.telephone = res.getString("telephone");
            }

            if (gender.equalsIgnoreCase("male")) {
                this.normalHeamoglobinLevel = 16;
            } else {
                this.normalHeamoglobinLevel = 14;
            }

        } catch (SQLException e) {
            System.out.println("SQL exception in constructor in Athlete.java: " + e);
        }

        disconnect();
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getNationality() {
        return nationality;
    }

    public String getSport() {
        return sport;
    }

    public String getGender() {
        return gender;
    }

    public String getTelephone() {
        return telephone;
    }

    public int getAthleteID() {
        return athleteID;
    }

    /**
     * Takes a LocalDate object as parameter and returns a Location-object that corresponds with the input parameter.
     * Returns null if there are no info about the athletes whereabouts at that date.
     *
     * @param date
     * @return
     */

    public Location getLocation(LocalDate date) {

        Location location = null;

        try {
            setup();

            ResultSet res = getStatement().executeQuery("SELECT Location.longitude, Location.latitude, Location.altitude, Location.country, Location.city\n" +
                    "FROM Athlete\n" +
                    "LEFT JOIN Athlete_Location ON Athlete.athleteID = Athlete_Location.athleteID\n" +
                    "LEFT JOIN Location ON Athlete_Location.latitude = Location.latitude AND Athlete_Location.longitude = Location.longitude\n" +
                    "WHERE Athlete.athleteID = '" + athleteID + "'\n" +
                    "AND Athlete_Location.from_date < '" + date + "'\n" +
                    "AND Athlete_Location.to_date > '" + date + "'");

            while (res.next()) {
                float longitude = res.getFloat("longitude");
                float latitude = res.getFloat("latitude");
                float altitude = res.getFloat("altitude");
                String city = res.getString("city");
                String country = res.getString("country");

                if (longitude == 0) {
                    disconnect();
                    return null;
                }

                location = new Location(longitude, latitude, altitude, city, country);
            }

        } catch (SQLException e) {
            System.out.println("SQL exception in method getLocation in Athlete.java: " + e);
        }

        disconnect();

        return location;
    }

    /**
     * Returns an ArrayList with AthleteGlobinDate objects that contains
     * all the measured haemoglobin levels, the corresponding dates and the athlete's name.
     * If there are noe measured haemoglobin levels for the athlete, the functions returns null.
     *
     * @return
     */

    public ArrayList<AthleteGlobinDate> getMeasuredAthleteGlobinDates() {

        ArrayList<AthleteGlobinDate> athleteGlobinDates = new ArrayList<AthleteGlobinDate>();

        try {
            setup();

            ResultSet res1 = getStatement().executeQuery("SELECT Athlete.firstname, Athlete.lastname, Globin_readings.globin_reading, Globin_readings.date FROM Athlete LEFT JOIN Globin_readings ON Globin_readings.athleteID = Athlete.athleteID WHERE Athlete.athleteID = '" + athleteID + "'");
            while (res1.next()) {
                String firstname = res1.getString("firstname");
                String lastname = res1.getString("lastname");
                double globinReading = res1.getDouble("globin_reading");
                Date date = res1.getDate("date");

                if (globinReading != 0) {
                    AthleteGlobinDate agd = new AthleteGlobinDate(globinReading, (java.sql.Date) date, firstname, lastname);
                    athleteGlobinDates.add(agd);
                }


            }

        } catch (SQLException e) {
            System.out.println("SQL exception in method getMeasuredAthleteGlobinDates() in Athlete.java: " + e);
        }

        if (athleteGlobinDates == null) {

            disconnect();
            return null;
        }

        disconnect();
        return athleteGlobinDates;
    }


    /**
     * Returns an ArrayList containing AthleteGlobinDate objects that contains
     * from and to date for every place the athlete goes to. The objects also contains the max haemoglobin level at
     * that place, and the name of the athlete. Returns null if the athlete has no locations added.
     *
     * @return
     */

    private ArrayList<AthleteGlobinDate> getExpectedAthleteGlobinDates() {

        ArrayList<AthleteGlobinDate> athleteGlobinDates = new ArrayList<AthleteGlobinDate>();
        double expectedHaemoglobinLevel = 0;

        try {

            setup();

            ResultSet res1 = getStatement().executeQuery("SELECT Athlete.firstname, Athlete.lastname, Athlete.gender, Location.altitude, Athlete_Location.from_date, Athlete_Location.to_date\n" +
                    "FROM Athlete\n" +
                    "LEFT JOIN Athlete_Location ON Athlete.athleteID = Athlete_Location.athleteID\n" +
                    "LEFT JOIN Location ON Athlete_Location.latitude = Location.latitude AND Athlete_Location.longitude = Location.longitude\n" +
                    "WHERE Athlete.athleteID = '" + athleteID + "'");

            while (res1.next()) {
                String firstname = res1.getString("firstname");
                String lastname = res1.getString("lastname");
                String gender = res1.getString("gender");
                float altitude = res1.getFloat("altitude");
                Date fromdate = res1.getDate("from_date");
                Date todate = res1.getDate("to_date");

                if (fromdate == null || todate == null) {

                    disconnect();
                    return null;
                }

                if (gender.equalsIgnoreCase("Male")) {
                    expectedHaemoglobinLevel = getMaxGlobinLevel(altitude, true);
                } else {
                    expectedHaemoglobinLevel = getMaxGlobinLevel(altitude, false);
                }

                expectedHaemoglobinLevel = Math.round((expectedHaemoglobinLevel * 100)) / 100.0;

                AthleteGlobinDate agd = new AthleteGlobinDate(expectedHaemoglobinLevel, fromdate, todate, firstname, lastname);
                athleteGlobinDates.add(agd);
            }

        } catch (SQLException e) {
            System.out.println("SQL exception in method getExpectedAthleteGlobinDates() in Athlete.java: " + e);
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

    private double getMaxGlobinLevel(float altitude, boolean male) {

        double maxGlobinLevel = 0;

        if (male) {
            double increase = (altitude / 250) / 100;
            maxGlobinLevel = normalHeamoglobinLevel + normalHeamoglobinLevel * increase;
            return maxGlobinLevel;
        } else {
            double increase = (altitude / 250) * 0.7 / 100;
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
     */

    public double getExpectedGlobinLevel(LocalDate date) {

        double expGlobinLevel = 0;
        double globinLevel = normalHeamoglobinLevel;

        ArrayList<AthleteGlobinDate> agd = getExpectedAthleteGlobinDates();

        if (agd == null) {
            return 0;
        }

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

        }

        return 0;

    }

    /**
     * Returns an AthleteGlobinObject containing the latest measured haemoglobin level, and the date
     * if was measured. If the latest measured haemoglobin level is more than 4 weeks older than the parameter date,
     * the function will return null. If the athlete never have taken any haemoglobin tests,
     * the function will return null.
     *
     * @param currentDate
     * @return
     */

    public AthleteGlobinDate getLastMeasuredGlobinLevel(LocalDate currentDate) {


        AthleteGlobinDate athleteGlobinDate;
        LocalDate latestdate = null;
        Date date = null;
        double globinReading = 0;

        try {
            setup();

            ResultSet res = getStatement().executeQuery("SELECT max(date) AS latestdate, globin_reading FROM Globin_readings WHERE athleteID = '" + athleteID + "'");

            while (res.next()) {
                date = res.getDate("latestdate");
                latestdate = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                globinReading = res.getDouble("globin_reading");
            }


        } catch (SQLException e) {
            System.out.println("SQL exception in method getLastMeasuredGlobinLevel() in Athlete.java: " + e);
        }

        long daysBetween = ChronoUnit.DAYS.between(latestdate, currentDate);

        if (daysBetween > 28 || date == null) {
            disconnect();
            return null;
        }

        athleteGlobinDate = new AthleteGlobinDate(globinReading, (java.sql.Date) date);
        disconnect();
        return athleteGlobinDate;
    }


    /**
     * Takes a date and returns the athletes actual haemoglobin level in percentage compared
     * to the expected haemoglobin level. Example, if the actual level is 9.0 and the expected
     * is 10.0, the function will return 90.0.
     *
     * @param date
     * @return
     * @throws SQLException
     */

    public double getGlobinDeviation(LocalDate date) {

        double globinDeviation = 0;
        if (getLastMeasuredGlobinLevel(date).getHaemoglobinLevel() == 0 || getExpectedGlobinLevel(date) == 0) {
            return 0;
        }

        globinDeviation = Math.round(getLastMeasuredGlobinLevel(date).getHaemoglobinLevel() / getExpectedGlobinLevel(date) * 10000) / 100.0;
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

}
