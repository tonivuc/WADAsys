package server.controllers;

import server.database.ConnectionPool;
import server.restklasser.Handleliste;

import java.sql.*;

public class HandlelisteController {

    public static Date getFrist(int handlelisteId) {
        return GenereltController.getDate("frist","handleliste", handlelisteId);
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
