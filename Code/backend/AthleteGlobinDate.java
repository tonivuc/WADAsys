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
        this.athlete_id = athlete_id;
    }

    public boolean addHaemoglobinLevel(){
        setup();


        try {

            String query = "INSERT INTO Globin_readings"               //Adding user into the "User"-table in the database
                    + "(athleteID, globin_reading, date)"   //Adding first name, last name, telephone, username, password
                    + "VALUES (?,?,?)";       //The values comes from user-input


            //getStatement().executeQuery(query);
            PreparedStatement preparedStmt = getConnection().prepareStatement(query);  //Adding the user into the database, getting the users input
            preparedStmt.setInt(1, athlete_id);
            preparedStmt.setDouble(2, getHaemoglobinLevel());
            preparedStmt.setDate(3, getDate());

            preparedStmt.execute(); //Executing the prepared statement

        }catch(Exception e){
            System.out.println("REGISTER HAEMOGLOBINLEVEL: Something went wrong." + e.toString());
            e.printStackTrace();
        }

        disconnect();
        return true;
    }

    public boolean addHaemoglobinReading(String readingInput, String dateInput){

        this.date = checkDateFormat(dateInput);
        this.haemoglobinLevel = checkReadingFormat(readingInput);
        Athlete athlete = new Athlete(athlete_id);

        if(date != null && haemoglobinLevel != -1){
            if(haemoglobinLevel < 5 || haemoglobinLevel > 30){

                showMessageDialog(null, "Haemoglobin level not reasonable. \n\nPlease check that your input is correct.");
                return false;
            }

            int confirmation = showConfirmDialog(null, "Haemoglobin level: " +
                    haemoglobinLevel + "\nDate: " + date +
                    "\nAthlete: " + athlete.getFirstname() + " " + athlete.getLastname() +
                    "\n \nAre you sure you want to add haemoglobin level?", "Submit", JOptionPane.YES_NO_OPTION);

            if (confirmation == 0) { //yes confirmation

                addHaemoglobinLevel();

                showMessageDialog(null, "Haemoglobin level was registered successfully.");
                return true;


            }
        }

        return false;


    }

    public java.sql.Date checkDateFormat(String dateString){

        java.sql.Date sqlDate = null;

        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date parsed = format.parse(dateString);
            sqlDate = new java.sql.Date(parsed.getTime());
            System.out.println(sqlDate);
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

    public String allReadings(){

        //ArrayList<String> allReadings = new ArrayList<>();
        String allReadings = "";

        try {

            String query = "SELECT * FROM Globin_readings WHERE athleteID = '" + athlete_id + "'  ORDER by date desc";

            ResultSet res = getStatement().executeQuery(query);

            while (res.next()) {

                allReadings += "Date: " + res.getDate("date") + ", reading: " + res.getDouble("globin_reading") + "\n";

                //allReadings().add("Date: " + res.getDate("date") + ", reading: " + res.getDouble("globin_reading"));

            }

            res.close();
        }catch(Exception e){
            System.out.println("GETALLREADINGS: " + e.toString());
        }


        return allReadings;


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

    public boolean updateInfo(String newData, String columnName, int athleteID, String date) {
        setup();
        //if (columnName.equals("athleteID")) {
        try {
            // create the java mysql update preparedstatement
            String query = "UPDATE Globin_readings SET " + columnName + " = '" + newData + "' WHERE athleteID = '" + athleteID + "' AND date = " + date;
            Statement stm = getStatement();
            stm.executeUpdate(query);

            return true;

        } catch (Exception e) {
            System.out.println("UPDATEINFO: Sql.. " + e.toString());
        } disconnect();
        return false;
    }

    public boolean deleteReading(int athleteID, String date){
        //System.out.println("Kom inn her");
        setup(); //Setup the connection to the database
        String sqlDate = date;
        /*try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date parsed = format.parse(date);
            sqlDate = new java.sql.Date(parsed.getTime());
        }catch (ParseException e){
            System.out.println("Wrong format");
        }*/


        try {
            //getStatement().executeQuery("DELETE FROM " + columnName + " WHERE athleteID = '" + athleteID + "' AND date = " + date);

            PreparedStatement st = getConnection().prepareStatement("DELETE FROM  Globin_readings WHERE athleteID = '" + athleteID + "' AND date = '" + sqlDate + "'");
            //st.setString(1,name);
            st.executeUpdate();

            //Double checks that the user actually was deleted sucsessfully
            ResultSet res = getStatement().executeQuery("SELECT * FROM Globin_readings WHERE athleteID = '" + athleteID + "' AND date = '" + sqlDate + "'");
            if(!(res.next())){
                System.out.println("Haemoglobin level deleted sucsessfully.");
                res.close();
                disconnect();
                return true;
            }else {
                System.out.println("Haemoglobin level was not deleted..");
                res.close();
                disconnect();
                return false;
            }
        } catch(SQLException e){
            System.out.println("DELETEUSER: Something went wrong." + e.toString());
        }

        disconnect();
        return false;
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
