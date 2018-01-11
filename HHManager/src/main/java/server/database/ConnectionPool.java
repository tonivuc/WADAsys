package server.database;

import org.apache.commons.dbcp2.BasicDataSource;
import server.controllers.BrukerController;
import server.restklasser.Bruker;

import java.sql.Connection;
import java.sql.SQLException;

public final class ConnectionPool {

    //Magisk black box som gjør alt for oss
    private static final BasicDataSource dataSource = new BasicDataSource();

    //Attributter for tilkobling til databsen
    static {
        dataSource.setDriverClassName("com.mysql.jdbc.Driver"); //Set the specific driver "class name"
        dataSource.setUrl("jdbc:mysql://mysql.stud.iie.ntnu.no:3306/g_tdat2003_t6");
        dataSource.setUsername("g_tdat2003_t6");
        dataSource.setPassword("uz4rZOca");
    }

    private ConnectionPool() {
        //
    }

    //Lager en connection mot databasen hvis en connection ikke finnes. Finnes en connection tar den
    //en fra connection poolen.
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closeAllConnections() throws SQLException{
        dataSource.close();
    }

    public static BasicDataSource getDataSource() {
        return dataSource;
    }



    public static void main(String[] args) {

        Bruker testBruker = new Bruker();
        BrukerController controller = new BrukerController();
        testBruker.setBrukerId(1);
        testBruker.setPassord("passord1");


        try (Connection connection = ConnectionPool.getConnection() ){
            if (controller.exist(testBruker)) {
                System.out.println("Bruker finnes!");
            } else {
                System.out.println("Brukeren finnes ikke");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}