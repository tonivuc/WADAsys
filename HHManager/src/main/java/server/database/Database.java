package server.database;

import org.apache.commons.dbcp2.BasicDataSource;
import server.controllers.BrukerController;
import server.restklasser.Bruker;

import java.sql.Connection;
import java.sql.SQLException;

public final class Database {

    private static final BasicDataSource dataSource = new BasicDataSource();

    static {
        dataSource.setDriverClassName("com.mysql.jdbc.Driver"); //Set the specific driver "class name"
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



    public static void main(String[] args) {

        Database testDatabase = new Database();
        Bruker testBruker = new Bruker();
        BrukerController controller = new BrukerController();
        testBruker.setBrukerId(1);
        testBruker.setPassord("passord1");


        try {
            if (!controller.exist(testBruker)) {
                System.out.println("Bruker finnes ikke");
            } else {
                System.out.println("Brukeren finnes!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}