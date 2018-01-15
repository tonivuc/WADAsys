package server.controllers;

import server.database.ConnectionPool;
import server.restklasser.Handleliste;
import server.restklasser.Vare;

import java.sql.*;
import java.util.ArrayList;

public class HandlelisteController {


    /**
     * Henter en sql-date som gjelder for en handleliste.
     * @param handlelisteId Unik ID for å identifisere hver handleliste
     * @return Date
     */
    public static Date getFrist(int handlelisteId) {
        return GenereltController.getDate("frist","handleliste", handlelisteId);
    }

    public static ArrayList<Handleliste> getHandlelister(int husholdningId, int brukerId) throws SQLException {

        //Hent offentlige handlelister som hører til husholdningen, samt private handlelister som hører til brukeren og husholdningen
        final String getQuery = "SELECT * FROM handleliste WHERE (husholdningId = "+husholdningId+" AND offentlig = 1) OR (skaperId = "+brukerId+" AND husholdningId = "+husholdningId+" AND offentlig = 0)";
        ArrayList<Handleliste> handlelister = new ArrayList<Handleliste>();


        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement getStatement = connection.prepareStatement(getQuery)) {

            ResultSet tomHandleliste = getStatement.executeQuery();

            while (tomHandleliste.next()) {
                int handlelisteId = tomHandleliste.getInt("handlelisteId");
                //Hent varer som hører til hver handleliste
                ArrayList<Vare> varer = getVarer(handlelisteId, connection);
                handlelister.add(lagHandlelisteObjekt(tomHandleliste, handlelisteId, varer));
            }
            return handlelister;
        }

    }

    private static Handleliste lagHandlelisteObjekt(ResultSet tomHandleliste, int handlelisteId, ArrayList<Vare> varer) throws SQLException{

        Handleliste handleliste = new Handleliste(handlelisteId);
        handleliste.setHusholdningId(tomHandleliste.getInt("husholdningId"));
        handleliste.setSkaperId(tomHandleliste.getInt("skaperId"));
        handleliste.setTittel(tomHandleliste.getString("navn"));
        handleliste.setOffentlig((tomHandleliste.getInt("offentlig"))==1); //Gjør om tinyInt til boolean
        handleliste.setFrist(tomHandleliste.getDate("frist"));
        handleliste.setVarer(varer);

        return handleliste;
    }

    /**
     * Send inn en handlelisteId for å få et Handleliste-objekt fra databasen.
     * Kobler seg også opp mot varer-tabellen for å fylle handlelisten med varer.
     * @param handlelisteId Unik ID for å identifisere hver handleliste
     * @return Handleliste Et fullt handlelisteobjekt.
     */
    public static Handleliste getHandleliste(int handlelisteId) throws SQLException{
        final String getQuery = "SELECT * FROM handleliste WHERE handlelisteId = "+handlelisteId+"";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement getStatement = connection.prepareStatement(getQuery)){

            ResultSet tomHandleliste = getStatement.executeQuery();

            ArrayList<Vare> varer =  getVarer(handlelisteId,connection);
            tomHandleliste.next();
            return lagHandlelisteObjekt(tomHandleliste,handlelisteId,varer);
        }
    }

    /**
     * Hjelpemetode. Tar imot et resultset fra vare-tabellen og gjør det om til en array av varer.
     * @param handlelisteId ResultSet av varer fra SQL-severen.
     * @return Vare[]
     */
    public static ArrayList<Vare> getVarer(int handlelisteId, Connection connection) throws SQLException{

        final String getVarer = "SELECT * FROM vare WHERE handlelisteId = "+handlelisteId+"";
        ArrayList<Vare> varer = new ArrayList<Vare>();
        int antElementer = 0;

        try (PreparedStatement getVarerStatement = connection.prepareStatement(getVarer)){
            ResultSet varerResultset = getVarerStatement.executeQuery();

            while (varerResultset.next()) {
                antElementer++;
                Vare nyVare = new Vare();
                nyVare.setVareId(varerResultset.getInt("vareId"));
                nyVare.setKjøperId(varerResultset.getInt("kjøperId"));
                nyVare.setVarenavn(varerResultset.getString("vareNavn"));
                nyVare.setKjøpt((varerResultset.getInt("kjøpt"))==1); //Hvis resultatet == 1, får man true
                nyVare.setDatoKjøpt(varerResultset.getDate("datoKjøpt"));
                varer.add(nyVare);
            }
        }
        return varer;
    }


    /**
     * Sletter en handleliste fra databasen
     * @param handlelisteId ID som unikt identifiserer en handleliste
     * @return boolean True hvis det lykkes, false hvis det feilet
     */
    public static boolean slettHandleliste(int handlelisteId) throws SQLException {
        String sletteQuery = "DELETE FROM handleliste WHERE handlelisteId="+handlelisteId+"";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement slettStatement = connection.prepareStatement(sletteQuery)) {

            try {
                slettStatement.executeUpdate();
                //Returnerer True selv om tingen allerede har blitt slettet... Men det er kanskje ok.
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }


    /**
     * Tar imot et Handleliste-objekt og registrerer det i databasen.
     * Returnerer IDen til handlelisten i databasen hvis det lykkes.
     * @param handlelisteData Et handlelisteobjekt med all nødvendig informasjon. Vi ser bort fra handlelisteId.
     * @return int Handlelistens ID, eller -1 hvis noe gikk galt.
     */
    public static int lagHandleliste(Handleliste handlelisteData) throws SQLException {

        final String INSERT_Handleliste =
                "INSERT INTO handleliste (husholdningId, frist, offentlig, navn, skaperId) VALUES (?, ?, ?, ?, ?)";
        final String getHandlelisteId = "SELECT LAST_INSERT_ID()";
        int success = -1;
        int nyHandlelisteId = -1;

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(INSERT_Handleliste);
             PreparedStatement getStatement = connection.prepareStatement(getHandlelisteId))
        {
            insertStatement.setInt(1, handlelisteData.getHusholdningId());
            insertStatement.setDate(2, handlelisteData.getFrist());
            insertStatement.setBoolean(3, handlelisteData.isOffentlig());
            insertStatement.setString(4,handlelisteData.getTittel());
            insertStatement.setInt(5, handlelisteData.getSkaperId());

            //Kjør insert-kall
            try {
                success = insertStatement.executeUpdate();

                //Hvis insert lykkes, hent den nye HandlelisteIDen.
                try (ResultSet results = getStatement.executeQuery()){
                    results.next();
                    nyHandlelisteId = results.getInt(1 );
                    return nyHandlelisteId;
                }
                catch (Exception e) {
                    e.printStackTrace();
                    nyHandlelisteId = -1;
                    return nyHandlelisteId;
                }
            } catch (Exception e) {
                e.printStackTrace();
                success = -1;
                return -1;
            }
        }
    }
}
