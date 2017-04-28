package backend;

/**
 *
 * @author Toni Vucic
 */

import databaseConnectors.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Underlaying data-class for the AthleteSearchPanel and UserSearchPanel
 * Cant't be named SearchConnection because it conflicts with something else...
 * @author Toni Vucic
 */

public class SearchHelp extends DatabaseManager {

    /**
     * Gets a String[][] of data about all the users.
     * The data being: username, name (first + last name), and telephone
     * @return A two-dimensional array of user data. Each row is a user, and each collumn username, name and telephone number.
     */
    public String[][] getUsers() {
        setup();

        String basicQuery = "SELECT username, CONCAT_WS(\" \", firstname, lastname) AS 'name', telephone FROM User";
        String[][] queryResult = new String[0][0];
        ResultSet res = null;

        try {
            res = getStatement().executeQuery(basicQuery);
            int columnCount = res.getMetaData().getColumnCount();

            int rows = getRows(res);
            queryResult = new String[rows][columnCount];

            int i = 0;
            while (res.next()) {

                queryResult[i][0] = res.getString("username");
                queryResult[i][1] = res.getString("name");
                queryResult[i][2] = res.getString("telephone");
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

    /**
     * Gets a String[][] of data about all the athletes.
     * That being name (first+last name), nationality, sport and athleteID
     * @return Each row is a different athlete. Each collumn: name (first+last name), nationality, sport and athleteID (in that order)
     */

    public String[][] getAthletes() {

        setup();

        String basicQuery = "SELECT CONCAT_WS(\" \", firstname, lastname) AS 'name', nationality, sport, athleteID FROM Athlete";
        String[][] queryResult = new String[0][0];
        ResultSet res = null;

        try {
            res = getStatement().executeQuery(basicQuery);
            int columnCount = res.getMetaData().getColumnCount();

            int rows = getRows(res);
            queryResult = new String[rows][columnCount];

            int i = 0;
            while (res.next()) {

                queryResult[i][0] = res.getString("name");
                queryResult[i][1] = res.getString("nationality");
                queryResult[i][2] = res.getString("sport");
                queryResult[i][3] = res.getString("athleteID");
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

    /**
     * Returns the number of rows in a ResultSet
     * @param res ResultSet from a database query
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


}
