package server.controllers;

// Vi har controller-klasser fordi da kan vi teste login-funksjonalitet uten å bruke services. JUnit og sånn.
// Her kan vi også ha SQL-kall

import server.database.ConnectionPool;
import server.restklasser.Bruker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Her ligger logikken til restklassen. Den kobler opp mot database-poolen ved hjelp av Connection pool.
 * Her blir sql-setninger behandlet.
 */
public class BrukerController {
    public static PreparedStatement ps;


    /**
     * Generell metode for hente ut data fra databasen. Henter bare én celle.
     *
     * @param type :navnet på kolonnen man skal hente fra
     * @param brukerid til den akutelle brukeren
     *
     * @return innholdet i cellen
     */
    public static String getGenerelt (String type, int brukerid) {
        String sqlsetning = "SELECT "+ type + " from bruker where brukerid=?";
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlsetning)){
            //preparedStatement.setString(1, type);
            preparedStatement.setString(1, Integer.toString(brukerid));
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getString(type);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getBrukernavn (int brukerid) {
        return getGenerelt("navn", brukerid);
    }

    public static String getEpost (int brukerid) {
        return getGenerelt("epost", brukerid);
    }

    public static String getPassordhash (int brukerid) {
        return "";
    }

    public static boolean loginOk(String epost, String passordhash) {
        return true;
    }

    public static String getFavoritthusholdning(int brukerid) {
        return getGenerelt("favorittHusholdning", brukerid);
    }

    private static final String SQL_EXIST = "SELECT * FROM bruker WHERE brukerId=? AND passord=?";

    public static boolean exist(Bruker user) throws SQLException {
        boolean exist = false;

        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_EXIST)) {
            preparedStatement.setString(1, Integer.toString(user.getBrukerId()));
            preparedStatement.setString(2, user.getPassord());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                exist = resultSet.next(); //Kek
            }
        }

        return exist;
    }

    public boolean registrerBruker(Bruker bruker) {
        String pass = bruker.getPassord();
        String navn = bruker.getNavn();
        String epost = bruker.getEpost();
        String epostLedig = "SELECT epost FROM bruker WHERE epost = ?";

        String query = "INSERT INTO bruker (passord, navn, epost) VALUES (?, ?, ?)";


        try (Connection con = ConnectionPool.getConnection()){

            ps = con.prepareStatement(epostLedig);
            ps.setString(1, epost);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    String res = rs.getString("epost");
                    if (res!=(epost)){
                        return false;
                    }
                }
            }
            ps = con.prepareStatement(query);
            ps.setString(1, pass);
            ps.setString(2, navn);
            ps.setString(3, epost);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean rettEpostOgPass(String epost, String passord) {
        String query = "SELECT passord FROM bruker WHERE epost = ?";
        try (Connection con = ConnectionPool.getConnection()) {
            ps = con.prepareStatement(query);
            ps.setString(1, epost);
            try (ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    String res = rs.getString("passord");
                    if (res.equals(passord)) {
                        return true;
                    }
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}




/*
public class BrukerController {

    DatabaseConnection dbConection;
    public static PreparedStatement preparedStatement;

    public boolean registrerBruker(Bruker bruker) {
        String pass = bruker.getPassord();
        String navn = bruker.getNavn();
        String epost = bruker.getEpost();
        String epostLedig = "SELECT epost FROM bruker WHERE epost = ?";

        String query = "INSERT INTO bruker (passord, navn, epost) VALUES (?, ?, ?)";


        try {
            preparedStatement = con.prepareStatement(epostLedig);
            preparedStatement.setString(1, epost);
            ResultSet rs = preparedStatement.executeQuery(epostLedig);
            if (rs != null) {
                return false;
            }
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, pass);
            preparedStatement.setString(2, navn);
            preparedStatement.setString(3, epost);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
*/


