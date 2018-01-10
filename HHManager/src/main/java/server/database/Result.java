package server.database;

import server.database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A result object representing the data and connection
 *
 * @author Andreas
 */
public class Result implements AutoCloseable {
    /**
     * The result set returned from the database
     */
    private ResultSet resultSet;

    /**
     * The statement representing the connection to the database
     */
    private Statement statement = null;

    /**
     * A preprared statement object if the connection were prepared
     */
    private PreparedStatement preparedStatement = null;

    /**
     * Storing the result set and statement in the result object
     *
     * @param rs the result set
     * @param s the statement
     */
    public Result(ResultSet rs, Statement s){
        this.resultSet = rs;
        this.statement = s;
    }

    /**
     * Storing the result set and statement in the result object
     *
     * @param rs the result set
     * @param s the statement
     */
    public Result(ResultSet rs, PreparedStatement s){
        this.resultSet = rs;
        this.preparedStatement = s;
    }

    /**
     * @return the ResultSet
     */
    public ResultSet getResultSet() {
        return resultSet;
    }

    /**
     * Close the connection to the database and the resultset it self
     */
    public void close(){
        try {
            this.resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error closing result set\n\n" + e.getMessage());
        }
        DatabaseConnection.closeConnection(this.statement == null ? this.preparedStatement : this.statement);
    }
}
