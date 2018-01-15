package server.controllers;

import server.database.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Generell controller klasse. Her legges metoder hvis det viser seg at noe kode dupliserer seg i to av controllerklassene
 */
public class GenereltController {

    /**
     * Henter verdien fra én celle. Cellens innhold må være en string i databasen.
     * Lager en generell select-setning, men bruker bare id som identifikasjon.
     * Se variabelen "sqlsetning" for mal.
     *
     * @param kollonne
     * @param tabell
     * @param id
     * @return verdien til cellen fra select-setningen.
     */
    static String getString (String kollonne, String tabell, int id) {
        String sqlsetning = "SELECT "+ kollonne + " from "+ tabell + " where " + tabell + "id=?";
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlsetning)){
            preparedStatement.setString(1, Integer.toString(id));
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getString(kollonne);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
