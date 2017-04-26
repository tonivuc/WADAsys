package backend;

import databaseConnectors.DatabaseManager;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by tvg-b on 22.03.2017.
 */

public class AthleteGlobinDate extends DatabaseManager {

    
    private double haemoglobinLevel;
    private java.sql.Date date;
    private String firstname;
    private String lastname;
    private Date fromdate;
    private Date toDate;
    private int athlete_id;
    private Athlete athlete;

    public AthleteGlobinDate (double haemoglobinLevel, java.sql.Date date, int athlete_id) {
        this.haemoglobinLevel = haemoglobinLevel;
        this.date = date;
        this.athlete_id = athlete_id;

    }

    public AthleteGlobinDate (double haemoglobinLevel, java.sql.Date date) {
        this.haemoglobinLevel = haemoglobinLevel;
        this.date = date;

    }

    public AthleteGlobinDate (){

    }

    public AthleteGlobinDate (double haemoglobinLevel,  java.sql.Date date, String firstname, String lastname) {
        this.haemoglobinLevel = haemoglobinLevel;
        this.date = date;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public AthleteGlobinDate (double haemoglobinLevel, Date fromdate, Date toDate, String firstname, String lastname) {
        this.haemoglobinLevel = haemoglobinLevel;
        this.fromdate = fromdate;
        this.toDate = toDate;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public AthleteGlobinDate(int athlete_id){
        this.athlete = new Athlete(athlete_id);
    }

    public int addHaemoglobinReading(String readingInput, String dateInput, String entry_creator){

        this.date = checkDateFormat(dateInput);
        this.haemoglobinLevel = checkReadingFormat(readingInput);

        if(date != null && haemoglobinLevel != -1){
            if(haemoglobinLevel < 5 || haemoglobinLevel > 30){

                return -1;  //haemoglobin reading out of bounds
            }

            int confirmation = showConfirmDialog(null, "Haemoglobin level: " +
                    haemoglobinLevel + "\nDate: " + date +
                    "\nAthlete: " + athlete.getFirstname() + " " + athlete.getLastname() +
                    "\n \nAre you sure you want to add haemoglobin level?", "Submit", JOptionPane.YES_NO_OPTION);

            if (confirmation == 0) { //yes confirmation

                if(new SqlQuery().addHaemoglobinLevel(entry_creator, haemoglobinLevel, date, athlete_id)){
                    return 1; //registration was successfull
                }

                return -2; //registration failed
            }
        }

        return 0; //no confirmation


    }

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

    public String[][] getReadingsUser(int athleteID, String username) {

        setup();

        String basicQuery = "SELECT globin_reading, date FROM Globin_readings WHERE athleteID = '" + athleteID + "' AND entry_creator = '" + username + "' ORDER BY date";
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

                queryResult[i][0] = res.getString("date");
                queryResult[i][1] = res.getString("globin_reading");
                i++;
            }
            res.close();
            disconnect();
            return queryResult;

        } catch (SQLException e) {
            System.out.println("CHECK QUERY: Lost connection to the database.." + e.toString());
            disconnect();
            return queryResult;
        }
    }



    //Returns number of rows
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
     * Returns the HaemoglobinLevel.
     * @return double
     */

    public double getHaemoglobinLevel() {
        return haemoglobinLevel;
    }


    /**
     * Returns the fromDate
     * @return Date
     */

    public Date getFromDate () {
        return fromdate;
    }


    /**
     * Returns the toDate
     * @return Date
     */

    public Date getToDate () {
        return toDate;
    }


    public java.sql.Date getDate () {
        return date;
    }


    /**
     * Returns the first name
     * @return String
     */

    public String getFirstname () {
        return firstname;
    }


    /**
     * Returns the last name
     * @return String
     */

    public String getLastname () {
        return lastname;
    }

    public boolean updateReading(String newData, String columnName, int athleteID, String date) {

        boolean res = new SqlQuery().updateReading(newData, columnName, athleteID, date);

        if(res){
            return true;
        }
        return false;
    }

    public boolean deleteReading(int athleteID, String date){
        if(new SqlQuery().deleteReading(athleteID, date)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Returns the first name, last name, haemoglobin level, and date.
     * @return String
     */


    public String toString () {
        return firstname + " " + lastname + " " + haemoglobinLevel + " " + date;
    }


    /**
     * Returns the first name, last name, haemoglobinLevel, from date and to date.
     * @return String
     */

    public String toStringExpected () {
        return firstname + " " + lastname + " " + haemoglobinLevel + " " + fromdate + " - " + toDate;
    }

}
