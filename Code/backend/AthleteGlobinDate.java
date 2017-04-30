package backend;

/**
 *
 * @author Trym Vegard Gjelseth-Borgen
 */

import databaseConnectors.DatabaseManager;
import java.util.Date;

/**
 * Class that is mainly used to connect haemoglobin readings with a date
 * for calculating expected haemoglobin levels for athletes, as well
 * as for making the expected and measured haemoglobin levels graphs.
 */
public class AthleteGlobinDate {

    /**
     *  A haemoglobin level set in one of the constructors.
     */
    private double haemoglobinLevel;

    /**
     * A Date.
     */
    private java.sql.Date date;

    /**
     * Another date.
     */
    private Date fromdate;

    /**
     * A third date.
     */
    private Date toDate;

    /**
     * The id of the athlete that corresponds to the haemoglobinLevel.
     */
    private int athleteID;

    /**
     * Constructs an AthleteGlobinDate Object where the instance variables haemoglobinLevel,
     * date, and athleteID is being set.
     * @param haemoglobinLevel a haemoglobin level
     * @param date  a date
     * @param athleteID an athleteID
     */
    public AthleteGlobinDate (double haemoglobinLevel, java.sql.Date date, int athleteID) {
        this.haemoglobinLevel = haemoglobinLevel;
        this.date = date;
        this.athleteID = athleteID;
    }

    /**
     * Constructs an AthleteGlobinDate Object where the instance variables haemoglobinLevel
     * and date is being set.
     * @param haemoglobinLevel a haemoglobin level
     * @param date a date
     */
    public AthleteGlobinDate (double haemoglobinLevel, java.sql.Date date) {
        this.haemoglobinLevel = haemoglobinLevel;
        this.date = date;
    }

    /**
     * Construcs an AthleteGlobinDate Object where the instance variables haemoglobinLevel
     * , fromdate, todate and athleteID is being set.
     * @param haemoglobinLevel a haemoglobin level
     * @param fromdate a date
     * @param toDate a date
     * @param athleteID an athleteID
     */
    public AthleteGlobinDate (double haemoglobinLevel, Date fromdate, Date toDate, int athleteID) {
        this.haemoglobinLevel = haemoglobinLevel;
        this.fromdate = fromdate;
        this.toDate = toDate;
        this.athleteID = athleteID;
    }

    /**
     * Returns the instance variable HaemoglobinLevel.
     * @return double
     */
    public double getHaemoglobinLevel() {
        return haemoglobinLevel;
    }

    /**
     * Returns the instance variable fromDate.
     * @return Date
     */
    public Date getFromDate () {
        return fromdate;
    }

    /**
     * Returns the instance variable toDate.
     * @return Date
     */
    public Date getToDate () {
        return toDate;
    }

    /**
     * Returns the instance variable date.
     * @return java.sql.Date
     */
    public java.sql.Date getDate () {
        return date;
    }

    /**
     * Returns the instance variable athleteID
     * @return int
     */
    public int getAthleteID(){
        return athleteID;
    }

    /**
     * Returns the instance variables athleteID, haemoglobin level, and date.
     * @return String
     */
    public String toString () {
        return athleteID + " " + haemoglobinLevel + " " + date;
    }

    /**
     * Returns the instance variables athleteID, haemoglobinLevel, from date and to date.
     * @return String
     */
    public String toStringExpected () {
        return athleteID + " " + haemoglobinLevel + " " + fromdate + " - " + toDate;
    }
}
