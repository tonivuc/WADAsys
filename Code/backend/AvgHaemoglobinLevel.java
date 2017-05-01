package backend;

import databaseConnectors.DatabaseManager;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
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
     * @return The processed and correctly written name of the gender (sex really in our case). "Invalid" if the input is wrong.
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
            return "Invalid";
        }
    }

    /**
     * Gets all the months with data for the specified gender, and gets the average Haemoglobin level for that month.
     * This is then added to a List of Doubles to be used by the charting class.
     * @param gender The gender of the athletes you want to find the average level of.
     * @return Average haemoglobin levels. Index of the readings corresponds to the months returned by the getAllMonths method.
     * @throws SQLException Gets thrown is the SQL query fails, most likely because the database connection is severed.
     * @throws IllegalArgumentException Usually thrown when the gender input is wrong.
     * @see #getAllMonths(String)
     */
    public List<Double> getAverageLevels(String gender) throws SQLException, IllegalArgumentException{

        List<Double> levels = new ArrayList<Double>();
        List<java.util.Date> months = getAllMonths(gender);

        boolean genderInputError = false;
        boolean sqlError = false;

        if (!setup()) {
            sqlError = true;
        };

        for (int i = 0; i < months.size(); i++) {
            double avgLvl = getAverageLevel(gender,months.get(i));
            if (avgLvl != (double)-1 && avgLvl != (double)-2) {
                levels.add(avgLvl);
            }
            if (avgLvl == (double)-1) {
                genderInputError = true;
            }
            else if (avgLvl == (double)-2) {
                sqlError = true;
            }
        }
        if (genderInputError) {
            throw new IllegalArgumentException(gender+" is not a valid input. Valid: m,f,male,female");
        }
        if (sqlError) {
            throw new SQLException("Database error in AvgHaemoglobinLevel.java");
        }
        disconnect();
        return levels;
    }

    /**
     * Gets the average Haemoglobin level for a certain gender and month.
     * Calls checkGender. Then converts the inputted java.util.Date object to a java.sql.Date.
     * Creates a startDate and endDate for the month using lengthOfMonth() and minusDays(14) and plusDays();
     * Queries the database for the average reading then returns it.
     * @param gender takes paramaters m,f,Male/male,Female/female
     * @param dateInput Must be given like: YYYY-MM-15
     * @return Average haemoglobin level for that month and gender. -1 if gender input is wrong. -2 if SQL error.
     */
    public double getAverageLevel(String gender, java.util.Date dateInput) { //Yes social justice warriors, i KNOW gender isn't binary :)

        gender = checkGender(gender);
        if (gender.equalsIgnoreCase("Invalid")) {
            return (double)-1;
        }
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
        } catch (SQLException e) {
            System.out.println("SQL Exception in AvgHaemoglobinLevel: " + e);
            return (double)-2;
        }

        return avgGlobin;
    };

    /**
     * Returns a List of java.util.Dates formated like YYYY-MM-15
     * The reason it returns it with a fixed date to the 15th of each month is because it is only used in the AvgGlobinLevelChart
     * and we have decided that the average reading of a month should be displayed on the 15th of that month.
     * Class queries the database and returns the result.
     * @param gender Takes: m,f,Male,Female (upper and lowercase)
     * @return List of java.util.Dates
     * @throws IllegalArgumentException Gets thrown if the gender is wrong
     * @throws SQLException Gets thrown is the SQL query fails, most likely because the database connection is severed.
     */
    public List<java.util.Date> getAllMonths(String gender) throws IllegalArgumentException, SQLException{
        gender = checkGender(gender);
        if (gender.equalsIgnoreCase("Invalid")) {
            throw new IllegalArgumentException("Invalid gender. Valid: m,f,male,female");
        }

        List<java.util.Date> months = new ArrayList<java.util.Date>();

        ResultSet res = null;

        //Get all the dates by getting MONTH() and YEAR() from the query above
        String monthQuery = "SELECT DATE_FORMAT(date, '%Y-%m-15') AS Month from Globin_readings NATURAL JOIN Athlete WHERE Athlete.gender = '"+gender+"' GROUP BY DATE_FORMAT(date, '%m-%Y') ORDER BY Month ASC";

        try {
            if (!setup()) {
                throw new SQLException("Error fetching months from database in AvgHaemoglobinLevel");
            }
            res = getStatement().executeQuery(monthQuery);

            while (res.next()) {
                months.add(res.getDate(1));
            }
        } catch (SQLException e) {
            throw new SQLException("Error fetching months from database in AvgHaemoglobinLevel");
        }
        finally {
            disconnect();
        }
        return months;
    }

}
