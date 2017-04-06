package backend;
import databaseConnectors.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Toni on 30.03.2017.
 */
//Cant't be named SearchConnection because it conflicts with something else...
public class SearchHelp extends DatabaseManager {

    /*
    public SearchHelp() {
        setup();
    }
    */

    public String[][] getAthletes() {

        setup();

        String selectPassword = "SELECT CONCAT_WS(\" \", firstname, lastname) AS 'name', nationality, sport FROM Athlete";
        String[][] queryResult = new String[0][0];
        ResultSet res = null;

        try {
            res = getStatement().executeQuery(selectPassword);
            int columnCount = res.getMetaData().getColumnCount();

            int rows = getRows(res);
            queryResult = new String[rows][3];

            int i = 0;
            while (res.next()) {

                queryResult[i][0] = res.getString("name");
                queryResult[i][1] = res.getString("nationality");
                queryResult[i][2] = res.getString("sport");
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

