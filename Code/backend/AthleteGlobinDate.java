package backend;

import databaseConnectors.DatabaseManager;

import java.sql.PreparedStatement;
import java.util.Date;

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
        setup();
        this.haemoglobinLevel = haemoglobinLevel;
        this.date = date;
        this.athlete_id = athlete_id;

    }

    public AthleteGlobinDate (double haemoglobinLevel, java.sql.Date date) {
        setup();
        this.haemoglobinLevel = haemoglobinLevel;
        this.date = date;

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

    public boolean addHaemoglobinLevel(){


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

            getConnection().close(); //Closing connection

        }catch(Exception e){
            System.out.println("REGISTER HAEMOGLOBINLEVEL: Something went wrong." + e.toString());
            e.printStackTrace();
        }
        return true;
    }

    public double getHaemoglobinLevel() {
        return haemoglobinLevel;
    }

    public Date getFromDate () {
        return fromdate;
    }

    public Date getToDate () {
        return toDate;
    }

    public java.sql.Date getDate () {
        return date;
    }

    public String getFirstname () {
        return firstname;
    }

    public String getLastname () {
        return lastname;
    }

    public String toString () {
        return firstname + " " + lastname + " " + haemoglobinLevel + " " + date;
    }

    public String toStringExpected () {
        return firstname + " " + lastname + " " + haemoglobinLevel + " " + fromdate + " - " + toDate;
    }

}
