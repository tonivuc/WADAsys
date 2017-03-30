package athlete;

import java.util.Date;

/**
 * Created by tvg-b on 22.03.2017.
 */

public class AthleteGlobinDate {

    
    private double haemoglobinLevel;
    private Date date;
    private String firstname;
    private String lastname;
    private Date fromdate;
    private Date toDate;

    public AthleteGlobinDate (double haemoglobinLevel, Date date) {
        this.haemoglobinLevel = haemoglobinLevel;
        this.date = date;
    }

    public AthleteGlobinDate (double haemoglobinLevel, Date date, String firstname, String lastname) {
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

    public double getHaemoglobinLevel() {
        return haemoglobinLevel;
    }

    public Date getFromDate () {
        return fromdate;
    }

    public Date getToDate () {
        return toDate;
    }

    public Date getDate () {
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
