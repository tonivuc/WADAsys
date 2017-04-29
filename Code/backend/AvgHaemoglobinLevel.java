package backend;

import databaseConnectors.DatabaseManager;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Database/calculation class for the AvgGlobinLevelChart.
 * Supplies the required data from the database with different methods like getAverageLevels() and getAllMonths()
 * Created by Toni on 28.04.2017.
 */
public class AvgHaemoglobinLevel extends DatabaseManager{

    /**
     * Method checks if gender is valid. If it is written wrong it returns the way it is written in the database.
     * Also lets you use "m" and "f" in stead of having to type "male" and "female".
     * @param input The string that will be checked.
     * @return The processed and correctly written name of the gender (sex really in our case).
     */
    private String checkGender(String input) {
        if (input.equalsIgnoreCase("Male") || input.equalsIgnoreCase("m")) {
            return "Male";
        }
        else if (input.equalsIgnoreCase("Female") || input.equalsIgnoreCase("f")) {
            return "Female";
        }
        else {
            System.out.println("Invalid gender. Allowed: Male/male, m, Female/female, f");
        }
        return "Invalid";
    }

    /**
     * Gets all the months with data for the specified gender, and gets the average Haemoglobin level for that month.
     * This is then added to a List of Doubles to be used by the charting class.
     * @param gender
     * @return Average haemoglobin levels. Index of the readings corresponds to the months returned by the getAllMonths method.
     * @see #getAllMonths(String)
     */
    public List<Double> getAverageLevels(String gender) {

        List<Double> levels = new ArrayList<Double>();
        List<java.util.Date> months = getAllMonths(gender);

        setup();
        for (int i = 0; i < months.size(); i++) {
            levels.add(getAverageLevel(gender,months.get(i)));
        }
        disconnect();
        return levels;
    }

    /**
     * Gets the average Haemoglobin level for a certain gender and month.
     * Calls checkGender. Then converts the inputted java.util.Date object to a java.sql.Date.
     * Creates a startDate and endDate for the month using lengthOfMonth() and minusDays(14) and plusDays();
     * Queries the database for the average reading then returns it.
     *
     * @param gender takes paramaters m,f,Male/male,Female/female
     * @param dateInput Must be given like: YYYY-MM-15
     * @return Average haemoglobin level for that month and gender
     */
    public double getAverageLevel(String gender, java.util.Date dateInput) { //Yes social justice warriors, i KNOW gender isn't binary :)

        gender = checkGender(gender);
        java.sql.Timestamp sqlTimeStamp = new java.sql.Timestamp(dateInput.getTime());
        Date dateInMonth = new Date(sqlTimeStamp.getTime());

        Date startDate = Date.valueOf(dateInMonth.toLocalDate().minusDays(14));
        //System.out.println("Startdate: "+startDate);
        int monthLength = dateInMonth.toLocalDate().lengthOfMonth();
        Date endDate = Date.valueOf(dateInMonth.toLocalDate().plusDays(monthLength-15));
        //System.out.println("endDate: "+endDate);


        String query = "SELECT AVG(globin_reading) FROM Globin_readings NATURAL JOIN Athlete WHERE date >= '"+startDate+"' && date <= '"+endDate+"' && Athlete.gender = '"+gender+"'";
        ResultSet res = null;

        double avgGlobin = 0;
        try {
            res = getStatement().executeQuery(query);
            while(res.next()) {
                avgGlobin = res.getDouble(1);
            }
            //System.out.println("Globin: "+avgGlobin);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        return avgGlobin;
    };

    /**
     * Returns a list of java.util.Dates formated like YYYY-MM-15
     * The reason it returns it with a fixed date to the 15th of each month is because it is only used in the AvgGlobinLevelChart
     * and we have decided that the average reading of a month should be displayed on the 15th of that month.
     * Class queries the database and returns the result.
     * @param gender Takes: m,f,Male,Female (upper and lowercase)
     * @return
     */
    public List<java.util.Date> getAllMonths(String gender) {

        List<java.util.Date> months = new ArrayList<java.util.Date>();

        ResultSet res = null;

        //Get all the dates by getting MONTH() and YEAR() from the query above
        String monthQuery = "SELECT DATE_FORMAT(date, '%Y-%m-15') AS Month from Globin_readings NATURAL JOIN Athlete WHERE Athlete.gender = '"+gender+"' GROUP BY DATE_FORMAT(date, '%m-%Y') ORDER BY Month ASC";

        try {
            setup();
            res = getStatement().executeQuery(monthQuery);

            while (res.next()) {
                months.add(res.getDate(1));
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        finally {
            disconnect();
        }

        return months;
    }

    public static void main(String[] args) {
        AvgHaemoglobinLevel test = new AvgHaemoglobinLevel();
        //test.getAllMonths("male");
        test.getAverageLevels("female");
    }
}
