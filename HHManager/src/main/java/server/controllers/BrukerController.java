package server.controllers;

// Vi har controller-klasser fordi da kan vi teste login-funksjonalitet uten å bruke services. JUnit og sånn.
// Her kan vi også ha SQL-kall

import server.database.ConnectionPool;
import server.restklasser.*;

import java.sql.*;

/**
 * Her ligger logikken til restklassen Bruker. Den kobler opp mot database-poolen ved hjelp av Connection pool.
 * Her blir sql-setninger behandlet.
 */
public class BrukerController {
    private static PreparedStatement ps;
    private static Statement s;
    private final static String TABELLNAVN = "bruker";

    public static String getBrukernavn (int brukerid) {
        return GenereltController.getString("navn", TABELLNAVN, brukerid);
    }

    public static String getEpost (int brukerid) {
        return GenereltController.getString("epost", TABELLNAVN, brukerid);
    }

    public static String getFavoritthusholdning(int brukerid) {
        return GenereltController.getString("favorittHusholdning", TABELLNAVN, brukerid);
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
    public boolean loginOk(String epost, String passord) {
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

    public Bruker getBrukerData(String epost){

        Bruker bruker = new Bruker();
        String getBrukerId = "SELECT brukerId FROM bruker WHERE epost = ?";
        int brukerId = 0;

        try(Connection con = ConnectionPool.getConnection()){
            ps = con.prepareStatement(getBrukerId);
            ps.setString(1, epost);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    brukerId = rs.getInt("brukerId");
                }
            }

            ResultSet rs;

            String hentMineGjørmål = "SELECT * FROM gjøremål WHERE utførerId = "+brukerId;
            s = con.createStatement();
            rs = s.executeQuery(hentMineGjørmål);

            while(rs.next()){
                Gjøremål gjøremål = new Gjøremål();
                gjøremål.setBeskrivelse(rs.getString("beskrivelse"));
                int fullført = rs.getInt("fullført");
                if (fullført == 1){
                    gjøremål.setFullført(true);
                }else{
                    gjøremål.setFullført(false);
                }
                gjøremål.setGjøremålId(rs.getInt("gjøremålId"));
                gjøremål.setHhBrukerId(brukerId);
                gjøremål.setFrist(rs.getDate("frist"));
                bruker.addGjøremål(gjøremål);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return bruker;
    }
}
