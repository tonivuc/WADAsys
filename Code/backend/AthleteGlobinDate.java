package backend;

import databaseConnectors.DatabaseManager;

import java.util.Date;

/**
 * Created by tvg-b on 22.03.2017.
 */

public class AthleteGlobinDate extends DatabaseManager {

    
    private double haemoglobinLevel;
    private java.sql.Date date;
    private Date fromdate;
    private Date toDate;
    private int athleteID;

    public AthleteGlobinDate (double haemoglobinLevel, java.sql.Date date, int athleteID) {
        this.haemoglobinLevel = haemoglobinLevel;
        this.date = date;
        this.athleteID = athleteID;
    }

    public AthleteGlobinDate (double haemoglobinLevel, java.sql.Date date) {
        this.haemoglobinLevel = haemoglobinLevel;
        this.date = date;
    }

    public AthleteGlobinDate (){

    }

    public AthleteGlobinDate (double haemoglobinLevel, Date fromdate, Date toDate, int athleteID) {
        this.haemoglobinLevel = haemoglobinLevel;
        this.fromdate = fromdate;
        this.toDate = toDate;
        this.athleteID = athleteID;
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
     * Returns the first name, last name, haemoglobin level, and date.
     * @return String
     */
    public String toString () {
        return athleteID + " " + haemoglobinLevel + " " + date;
    }

    /**
     * Returns the first name, last name, haemoglobinLevel, from date and to date.
     * @return String
     */
    public String toStringExpected () {
        return athleteID + " " + haemoglobinLevel + " " + fromdate + " - " + toDate;
    }

}
