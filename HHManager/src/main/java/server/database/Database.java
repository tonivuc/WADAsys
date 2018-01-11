package server.database;

import org.apache.commons.dbcp2.BasicDataSource;
import server.restklasser.Bruker;

import javax.servlet.ServletException;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Database {

    private static final BasicDataSource dataSource = new BasicDataSource();

    static {
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://mysql.stud.iie.ntnu.no:3306/g_tdat2003_t6");
        dataSource.setUsername("g_tdat2003_t6");
        dataSource.setPassword("uz4rZOca");
    }

    private Database() {
        //
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private static final String SQL_EXIST = "SELECT * FROM bruker WHERE brukerId=? AND passord=?";

    public boolean exist(Bruker user) throws SQLException {
        boolean exist = false;

        try (
                Connection connection = Database.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_EXIST);
        ) {
            statement.setString(1, Integer.toString(user.getBrukerId()));
            statement.setString(2, user.getPassord());

            try (ResultSet resultSet = statement.executeQuery()) {
                exist = resultSet.next();
            }
        }

        return exist;
    }

    public static void main(String[] args) {

        Database testDatabase = new Database();
        Bruker testBruker = new Bruker();
        testBruker.setBrukerId(1);
        testBruker.setPassord("passord1");


        try {
            if (!testDatabase.exist(testBruker)) {
                System.out.println("Bruker finnes ikke");
            } else {
                System.out.println("Brukeren finnes!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}