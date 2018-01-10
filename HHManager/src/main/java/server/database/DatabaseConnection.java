package server.database;

import java.sql.*;

public class DatabaseConnection {
    private static String database = "g_tdat2003_t6";
    private static String username = "g_tdat2003_t6";
    private static String password = "uz4rZOca";
    private static String url = "mysql.stud.iie.ntnu.no";
    public static String connectionURL = null;

    /**
     * Fetch the data from the database based on the query and the parameters
     * Use this if the user is able to change variables in the query to avoid
     * SQL injections
     *
     * @param query the SQL query
     * @param params the parameters to put into the query
     *
     * @return A Result object with the data
     */
    public static Result fetchData(String query, String[] params){
        if(query == null)
            return null;
        PreparedStatement statement = getConnection(query);
        if(statement == null)
            return null;
        prepare(statement, params);
        ResultSet rs;
        try {
            rs = statement.executeQuery();
            statement.getConnection().commit();
            return new Result(rs, statement);
        } catch (SQLException e) {
            System.out.println("Error fetching data\n\n" + e.getMessage());
        }
        return null;
    }

    /**
     * Update the database according to the provided query
     * Use this if the user is able to change variables in the query to avoid
     * SQL injections
     *
     * @param query the SQL query
     * @param params the parameters to put into the query
     *
     * @return true if success, else false if no success
     */
    public static boolean update(String query, String[] params){
        try {
            PreparedStatement statement = getConnection(query);
            prepare(statement, params);
            boolean ret = statement.executeUpdate() != 0;
            statement.getConnection().commit();
            closeConnection(statement);
            return ret;
        } catch (SQLException e) {
            System.out.println("Error updating database\n\n" + e.getMessage());
        }
        return false;
    }

    /**
     * Run a query that returns the success and is designed for deleting data
     * Use this if the user is able to change variables in the query to avoid
     * SQL injections
     *
     * @param query the SQL query
     * @param params the parameters for the statement
     *
     * @return true if the delete was successful
     */
    public static boolean delete(String query, String[] params){
        PreparedStatement statement = getConnection(query);
        prepare(statement, params);
        return execute(statement);
    }

    /**
     * Run a query that returns the success and is designed for inserting data
     * Use this if the user is able to change variables in the query to avoid
     * SQL injections
     *
     * @param query the SQL query
     * @param params the parameters for the statement
     *
     * @return true if the insert was successful
     */
    public static boolean insert(String query, String[] params){
        PreparedStatement statement = getConnection(query);
        prepare(statement, params);
        return execute(statement);
    }

    /**
     * @param query the query to make the statement object from
     *
     * @return A statement object with a new connection that is SQL injection safe
     */
    private static PreparedStatement getConnection(String query){
        String databaseConnectionURL = "jdbc:mysql://" + url + "/" + database;
        try {
            Connection c = connectionURL == null ? DriverManager.getConnection(databaseConnectionURL, username, password) : DriverManager.getConnection(connectionURL);
            c.setAutoCommit(false);
            return c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error connection to database\n\n" + e.getMessage());
        }
        return null;
    }

    /**
     * Close the connection and statement
     *
     * @param statement the statement to close
     */
    static void closeConnection(PreparedStatement statement){
        if(statement == null)
            return;
        try {
            Connection c = statement.getConnection();
            statement.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in closing SQL Prepared Connection\n\n" + e.getMessage());
        }
    }

    /**
     * Close the connection and statement
     *
     * @param statement the statement to close
     */
    static void closeConnection(Statement statement){
        if(statement == null)
            return;
        try {
            Connection c = statement.getConnection();
            statement.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in closing SQL Normal Connection\n\n" + e.getMessage());
        }
    }

    /**
     * Add the parameters to the statement
     *
     * @param statement the statement to prepare the data for
     * @param params the data to put into the statement
     */
    private static void prepare(PreparedStatement statement, String[] params){
        try {
            for(int i = 0; i < params.length; i++){
                statement.setString(i + 1, params[i]);
            }
        } catch (SQLException e) {
            System.out.println("Error in preparing statement\n\n" + e.getMessage());
        }
    }

    /**
     * Execute a prepared statement and commit the statement
     *
     * @param statement the statement width the query and data
     *
     * @return true if the execution was successful
     */
    private static boolean execute(PreparedStatement statement){
        try {
            if (statement == null)
                return false;
            statement.execute();
            statement.getConnection().commit();
            closeConnection(statement);
            return true;
        } catch (SQLException e) {
            System.out.println("Error in executing query\n" + statement.toString() + "\n\n" + e.getMessage());
            closeConnection(statement);
            return false;
        }
    }
}
