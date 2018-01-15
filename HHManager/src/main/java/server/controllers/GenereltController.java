package server.controllers;

import server.database.ConnectionPool;

import java.sql.Connection;
import java.sql.Date;
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

    static int getInt(String kollonne, String tabell, int id) {
        String sqlsetning = "SELECT "+ kollonne + " from "+ tabell + " where " + tabell + "id=?";
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlsetning)){
            preparedStatement.setString(1, Integer.toString(id));
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(kollonne);
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    static Date getDate(String kollonne, String tabell, int id) {
        String sqlsetning = "SELECT "+ kollonne + " from "+ tabell + " where " + tabell + "id=?";
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlsetning)){
            preparedStatement.setString(1, Integer.toString(id));
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getDate(kollonne);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static boolean slettRad(int id) {
        return false;
    }

    /**
     * Oppdaterer/endrer én celle. Må bruke id.
     *
     * @param tabell som skal oppdateres
     * @param hvaOppdater som skal oppdateres
     * @param setId id til dataen som skal endres
     * @param whereId id til etiteten som skal oppdateres
     */
    static void update(String tabell, String hvaOppdater, String setId, String whereId) {
        String q = "update " + tabell + " set " + hvaOppdater + "Id=? where " + tabell + "Id=?";
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(q)) {
            preparedStatement.setString(1, setId);
            preparedStatement.setString(2, whereId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


