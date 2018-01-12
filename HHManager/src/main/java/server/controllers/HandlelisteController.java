package server.controllers;

import server.database.ConnectionPool;
import server.restklasser.Handleliste;
import server.restklasser.Vare;

import java.sql.*;
import java.util.ArrayList;

public class HandlelisteController {

    public static Date getFrist(int handlelisteId) {
        return GenereltController.getDate("frist","handleliste", handlelisteId);
    }

    public static Handleliste getHandleliste(int handlelisteId) throws SQLException{
        final String getQuery = "SELECT * FROM handleliste WHERE handlelisteId = "+handlelisteId+"";
        final String getVarer = "SELECT * FROM vare WHERE handlelisteId = "+handlelisteId+"";


        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement getStatement = connection.prepareStatement(getQuery);
             PreparedStatement getVarerStatement = connection.prepareStatement(getVarer)){

            ResultSet tomHandleliste = getStatement.executeQuery();
            ResultSet varerResultset = getVarerStatement.executeQuery();
            Vare[] varer = lagVarerAvSQL(varerResultset);

            Handleliste handleliste = new Handleliste(handlelisteId);
            tomHandleliste.next();
            handleliste.setHusholdningId(tomHandleliste.getInt("husholdningId"));
            handleliste.setSkaperId(tomHandleliste.getInt("skaperId"));
            handleliste.setTittel(tomHandleliste.getString("navn"));
            handleliste.setOffentlig((tomHandleliste.getInt("offentlig"))==1); //Gjør om tinyInt til boolean
            handleliste.setFrist(tomHandleliste.getDate("frist"));
            handleliste.setVarer(varer);

            return handleliste;
        }
    }

    /**
     * Tar et resultset fra vare-tabellen og gjør det om til en array av varer.
     * @param resultSet ResultSet av varer fra SQL-severen.
     * @return Vare[]
     */
    private static Vare[] lagVarerAvSQL(ResultSet resultSet) throws SQLException{
        ArrayList<Vare> varer = new ArrayList<Vare>();
        int antElementer = 0;
        while (resultSet.next()) {
            antElementer++;
            Vare nyVare = new Vare();
            nyVare.setVareId(resultSet.getInt("vareId"));
            nyVare.setKjøperId(resultSet.getInt("kjøperId"));
            nyVare.setVarenavn(resultSet.getString("vareNavn"));
            nyVare.setKjøpt((resultSet.getInt("kjøpt"))==1); //Hvis resultatet == 1, får man true
            nyVare.setDatoKjøpt(resultSet.getDate("datoKjøpt"));
            varer.add(nyVare);
        }

        //Dropper ArrayList siden jeg tror det er lettere å sende vanlig array til JavaScript
        return varer.toArray(new Vare[antElementer]);

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
            insertStatement.setInt(1, handlelisteData.getHusholdningsId());
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
