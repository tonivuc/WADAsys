package backend;

import databaseConnectors.DatabaseManager;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by tvg-b on 23.03.2017.
 */

public class Athlete extends DatabaseManager implements Comparable<Athlete> {

    private int athleteID; // Unique integer that is represents only one athlete
    private String firstname;
    private String lastname;
    private String gender;
    private String nationality;
    private String sport;
    private String telephone;
    private double normalHeamoglobinLevel; // The expected base haemoglobin level, dependent on gender
    private double globinDeviation; // A percentage based variable calculated by comparing the athletes actual, and expected haemoglobin level

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
            res.close();

            if (gender != null && gender.equalsIgnoreCase("male")) {
                this.normalHeamoglobinLevel = 16;
            } else if (gender != null && gender.equalsIgnoreCase("female")){
                this.normalHeamoglobinLevel = 14;
            }

            getGlobinDeviation(LocalDate.now());


        } catch (SQLException e) {
            System.out.println("SQL exception in constructor in Athlete.java: " + e);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException in cunstructor in Athlete.java. Athlete ID is probably invalid: " + e);
        }

        disconnect();
    }

    /**
     * Returns the athlete's first name.
     * @return
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Returns the athlete's last name.
     * @return String
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Returns the athlete's nationality.
     * @return String
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Returns the athlete's sport.
     * @return String
     */
    public String getSport() {
        return sport;
    }

    /**
     * Returns the athlete's gender.
     * @return String
     */
    public String getGender() {
        return gender;
    }

    /**
     * Returns the athlete's Telephone number.
     * @return String
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Returns the athlete's AthleteID.
     * @return int
     */
    public int getAthleteID() {
        return athleteID;
    }

    /**
     * Takes a LocalDate object as parameter and returns a Location-object that corresponds with the input parameter.
     * Returns null if there are no info about the athletes whereabouts at that date.
     * @param date date as a LocalDate Object
     * @return String
     */
    public String getLocation(LocalDate date) {

        String location = "";

        try {
            setup();
            ResultSet res = getStatement().executeQuery("SELECT Athlete_Location.location " +
                    "FROM Athlete\n" +
                    "LEFT JOIN Athlete_Location ON Athlete.athleteID = Athlete_Location.athleteID " +
                    "WHERE Athlete.athleteID = '" + athleteID + "' " +
                    "AND Athlete_Location.from_date <= '" + date + "' " +
                    "AND Athlete_Location.to_date >= '" + date + "'");



            while (res.next()) {
                location = res.getString("location");
            }
            res.close();

        } catch (SQLException e) {
            System.out.println("SQL exception in method getLocation in Athlete.getLocation().java: " + e);
        }

        disconnect();
        return location;
    }

    /**
     * Returns an ArrayList with AthleteGlobinDate objects that contains
     * all the measured haemoglobin levels, the corresponding dates and the athlete's name.
     * If there are noe measured haemoglobin levels for the athlete, the functions returns null.
     * @return ArrayList<AthleteGlobinDate>
     */
    public ArrayList<AthleteGlobinDate> getMeasuredAthleteGlobinDates() {

        ArrayList<AthleteGlobinDate> athleteGlobinDates = new ArrayList<AthleteGlobinDate>();

        try {
            setup();
            ResultSet res = getStatement().executeQuery("SELECT Athlete.athleteID, Globin_readings.globin_reading, Globin_readings.date FROM Globin_reading WHERE Athlete.athleteID = '" + athleteID + "'");

            while (res.next()) {
                int athleteID = res.getInt("athleteID");
                double globinReading = res.getDouble("globin_reading");
                Date date = res.getDate("date");

                if (globinReading != 0) {
                    AthleteGlobinDate agd = new AthleteGlobinDate(globinReading, (java.sql.Date) date, firstname, lastname);
                    athleteGlobinDates.add(agd);
                }
            }
            res.close();g

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
     * @return ArrayList<AthleteGlobinDate>
     */
    public ArrayList<AthleteGlobinDate> getExpectedAthleteGlobinDates() {

        ArrayList<AthleteGlobinDate> athleteGlobinDates = new ArrayList<AthleteGlobinDate>();
        double expectedHaemoglobinLevel = 0;

        setup();

        try {

            ResultSet res1 = getStatement().executeQuery("SELECT Athlete.firstname, Athlete.lastname, Athlete.gender, Athlete_Location.from_date, Athlete_Location.to_date, Athlete_Location.location, Location.altitude\n" +
                    "FROM Athlete\n" +
                    "LEFT JOIN Athlete_Location ON Athlete.athleteID = Athlete_Location.athleteID\n" +
                    "LEFT JOIN Location ON Athlete_Location.location = Location.location\n" +
                    "WHERE Athlete.athleteID = '" + athleteID + "'");

            while (res1.next()) {
                String firstname = res1.getString("firstname");
                String lastname = res1.getString("lastname");
                String gender = res1.getString("gender");
                float altitude = res1.getFloat("altitude");
                Date fromdate = res1.getDate("from_date");
                Date todate = res1.getDate("to_date");

                if (fromdate == null || todate == null) {
                    res1.close();
                    disconnect();
                    return null;
                }

                if (gender != null && gender.equalsIgnoreCase("Male")) {
                    expectedHaemoglobinLevel = getMaxGlobinLevel(altitude, true);
                } else if (gender != null && gender.equalsIgnoreCase("Female")){
                    expectedHaemoglobinLevel = getMaxGlobinLevel(altitude, false);
                }

                expectedHaemoglobinLevel = Math.round((expectedHaemoglobinLevel * 100)) / 100.0;

                AthleteGlobinDate agd = new AthleteGlobinDate(expectedHaemoglobinLevel, fromdate, todate, firstname, lastname);
                athleteGlobinDates.add(agd);
            }
            res1.close();

        } catch (SQLException e) {
            System.out.println("SQL exception in method getExpectedAthleteGlobinDates() in Athlete.java: " + e);
        }

        disconnect();
        return athleteGlobinDates;
    }

    /**
     * Takes an altitude value as a float and a boolean (true if male, false if female) as parameters, and returns the maximum
     * natural haemoglobin level that a person can have at that specific altitude.
     * @param altitude altitude as a float value
     * @param male if the athlete is male a true value, if the athlete is female a false value
     * @return double
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
     * Takes a LocalDate Object as parameter and returns the athletes expected haemoglobin level at that specific date.
     * Returns 0 if there is noe data about the athlete at that date.
     * @param date date as a LocalDate Object
     * @return double
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
     * if was measured. If the latest measured haemoglobin level is more than 4 weeks or older than the parameter date,
     * the function will return null. If the athlete never have taken any haemoglobin tests,
     * the function will return null.
     * @param currentDate currentDate as a LocalDate Object
     * @return AthleteGlobinDate
     */
    public AthleteGlobinDate getLastMeasuredGlobinLevel(LocalDate currentDate) {

        AthleteGlobinDate athleteGlobinDate;
        LocalDate latestdate = null;
        Date date = null;
        double globinReading = 0;

        try {
            ResultSet res = getStatement().executeQuery("SELECT date AS latestdate, globin_reading FROM Globin_readings WHERE athleteID = '" + athleteID + "' ORDER BY date DESC LIMIT 1;");

            while (res.next()) {
                date = res.getDate("latestdate");
                latestdate = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                globinReading = res.getDouble("globin_reading");
            }
            res.close();

        } catch (SQLException e) {
            System.out.println("SQL exception in method getLastMeasuredGlobinLevel() in Athlete.java: " + e);
        }


        long daysBetween = 0;

        if (latestdate != null)  {
            daysBetween = ChronoUnit.DAYS.between(latestdate, currentDate);
        }

        if (daysBetween > 28 || date == null) {
            return null;
        }
        athleteGlobinDate = new AthleteGlobinDate(globinReading, (java.sql.Date) date);
        return athleteGlobinDate;
    }

    /**
     * Takes a date and sets the athlete's globinDeviation variable to a percentage compared
     * to the expected haemoglobin level. Example, if the actual level is 9.0 and the expected
     * is 10.0, the function will return 90.0.
     * @param date date a LocalDate Object
     */
    private void getGlobinDeviation(LocalDate date) {

        globinDeviation = 0;
        AthleteGlobinDate agd = getLastMeasuredGlobinLevel(date);
        double lastMesuredGlobinlevel = 0;

        if (agd != null) {
            lastMesuredGlobinlevel = agd.getHaemoglobinLevel();
        }

        double expectedGlobinLevel = getExpectedGlobinLevel(date);

        if (lastMesuredGlobinlevel != 0 && expectedGlobinLevel != 0) {
            globinDeviation = Math.round(lastMesuredGlobinlevel / expectedGlobinLevel * 10000) / 100.0;
        }
    }

    /**
     * Returns the instance variable globinDeviation.
     * @return double
     */
    public double getGlobinDeviation() {
        return globinDeviation;
    }

    /**
     * Updates the info of the athlete.
     * @param newData newData that is going to be inserted into the Athlete Database at a specific column
     * @param columnName columnName of the column where data is going to be inserted
     * @param athleteID athleteID athleteID of the athlete that newData is being added to
     * @return boolean boolean true if successful and false if unsuccessful.
     */
    public boolean updateInfo(String newData, String columnName, int athleteID) {
        setup();
        //if (columnName.equals("athleteID")) {
        try {
            // create the java mysql update preparedstatement
            String query = "UPDATE Athlete SET " + columnName + " = '" + newData + "' WHERE athleteID = '" + athleteID + "'";
            Statement stm = getStatement();
            stm.executeUpdate(query);

            return true;

            } catch (Exception e) {
                System.out.println("UPDATEINFO: Sql.. " + e.toString());
            } disconnect();
        return false;
    }

    /**
     * Updates the reading of the athlete.
     * @param newReading newReading that is going to be inserted into the Athlete Database at a specific column
     * @param columnName columnName of the column where data is going to be inserted
     * @param date date as a LocalDate Object
     * @return boolean true if updated, false if not
     */
    public boolean updateReading(String newReading, String columnName, String date) {
        SqlQuery sqlQuery = new SqlQuery();

        System.out.println("reading");
        if(sqlQuery.updateReading(newReading, "globin_reading", athleteID, date)) {
            return true;
        }
        return false;
    }

    /**
     * Takes a date and deletes the the athlete's haemoglobin reading at that specific date.
     * @param date as a LocalDate Object
     * @return boolean true if deleted, false if not
     */
    public boolean deleteReading(String date){
        if(new SqlQuery().deleteReading(athleteID, date)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Adds haemoglobin level to the athlete in the database.
     * @param readingInput readingInput String that is the haemoglobinLevel that will get inserted into the database. Must be between 5 and 30.
     * @param dateInput dateInput String that is the date when the haemoglobin reading was done.
     * @param entry_creator entry_creator String that is the user that took the haemoglobin reading.
     * @return int int 1 if successful, 0 if the user exits the window, -1 if the haemoglobin reading is out of bounds, -2 if the registration fails.
     */
    public int addHaemoglobinReading(String readingInput, String dateInput, String entry_creator){

        java.sql.Date date = checkDateFormat(dateInput);
        double haemoglobinLevel = checkReadingFormat(readingInput);

        if(date != null && haemoglobinLevel != -1){
            if(haemoglobinLevel < 5 || haemoglobinLevel > 30){

                return -1;  //haemoglobin reading out of bounds
            }

            int confirmation = showConfirmDialog(null, "Haemoglobin level: " +
                    haemoglobinLevel + "\nDate: " + date +
                    "\nAthlete: " + getFirstname() + " " + getLastname() +
                    "\n \nAre you sure you want to add haemoglobin level?", "Submit", JOptionPane.YES_NO_OPTION);

            if (confirmation == 0) { //yes confirmation

                if(new SqlQuery().addHaemoglobinLevel(entry_creator, haemoglobinLevel, date, athleteID)){
                    return 1; //registration was successfull
                }

                return -2; //registration failed
            }
        }

        return 0; //no confirmation
    }

    /**
     * Takes a String, checks if it has the right format and then formats it into java.sql.Date.
     * Returns a java.sql.Date Object. Returns null if the String could not be made into a
     * java.sql.Date Object.
     * @param dateString
     * @return java.sql.Date
     */
    public java.sql.Date checkDateFormat(String dateString){

        java.sql.Date sqlDate = null;

        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date parsed = format.parse(dateString);
            sqlDate = new java.sql.Date(parsed.getTime());
            return sqlDate;
        }catch(Exception ex){
            System.out.println("ADDBLOODSAMPLE: Date in wrong formate.");
            showMessageDialog(null, "Wrong date format. \n\nPlease use the format: yyyyMMdd.");
        }

        return null;
    }

    /**
     * Takes a String, and makes it into a double. Returns the double if the parsing went OK.
     * Returns -1 if something went wrong while parsing.
     * @param readingString
     * @return
     */
    public double checkReadingFormat(String readingString){
        double haemoglobinDouble = 0;

        try{
            haemoglobinDouble = Double.parseDouble(readingString);
            return haemoglobinDouble;
        }catch(Exception exe){
            System.out.println("ADDBLOODSAMPLE: haemoglobinDouble not a double.");
            showMessageDialog(null, "Haemoglobin level must be a decimal number.\n\nPlease try again.");
        }
        return -1;
    }

    /**
     * Gets all the locations of and the from_date and to_date, of the athlete, puts them into a String[][]
     * and returns it.
     * @return String[][]
     */
    public String[][] getLocationsArray(){
        setup();

        String basicQuery = "SELECT from_date, to_date, location FROM Athlete_Location WHERE athleteID = '" + athleteID + "' ORDER BY from_date DESC";
        String[][] queryResult = null;
        ResultSet res = null;

        try {
            queryResult = new String[0][0];
            res = getStatement().executeQuery(basicQuery);
            int columnCount = res.getMetaData().getColumnCount();

            int rows = getRows(res);
            queryResult = new String[rows][columnCount];

            int i = 0;
            while (res.next()) {

                queryResult[i][0] = res.getString("from_date");
                queryResult[i][1] = res.getString("to_date");
                queryResult[i][2] = res.getString("location");
                i++;
            }
            res.close();
            disconnect();
            return queryResult;

        } catch (SQLException e) {
            System.out.println("GETLOCATIONSARRAY: Lost connection to the database.." + e.toString());
            disconnect();
            return queryResult;
        }
    }

    /**
     * Helping method to getLocationsArray() that returns the number of rows int the Array.
     * @param res
     * @return int
     */
    public int getRows(ResultSet res){

        int totalRows = 0;
        try {
            res.last();
            totalRows = res.getRow();
            res.beforeFirst();
        }
        catch(Exception ex)  {
            return 0;
        }
        return totalRows ;
    }

    /**
     * Returns the athletes full name, gender, nationality, sport and telephonenumber.
     * @return String
     */
    public String toString () {
        return firstname + " " + lastname + ", " + gender + ", " + nationality + ", " + sport + ", " + telephone;
    }

    /**
     * CompareTo method that compares the globinDeviation variable of one Athlete with another. Returns 1 if
     * this.globinDeviation is the highest, 0 if they are equal and -1 if this.globinDeviation is the smallest.
     * @param o
     * @return int
     */
    @Override
    public int compareTo(Athlete o) {
        double CompareGlobin = ((Athlete) o).getGlobinDeviation();

        if (globinDeviation > o.getGlobinDeviation()) {
            return 1;
        } else if (globinDeviation < o.getGlobinDeviation()) {
            return -1;
        } else {
            return 0;
        }
    }
}
