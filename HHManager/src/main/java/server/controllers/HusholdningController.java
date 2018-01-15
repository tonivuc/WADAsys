package server.controllers;

import server.database.ConnectionPool;
import server.util.RandomGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HusholdningController {
    private static final String TABELLNAVN = "husholdning";

    public static String getNavn (int id) {
        return GenereltController.getString("navn", TABELLNAVN, id);
    }

    /**
     * BRUKES BARE HVIS navnet er en random string. Navn er egentlig ikke PK.
     *
     * @param rName Den tilfeldige stringen som skal søkes etter
     * @return id til string
     */
    private static int getId (String rName) {
        String sqlsetning = "select husholdningId from husholdning where navn=" + rName;
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlsetning)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("husholdningId");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * lager en ny husholdning
     * @param navn navnet på den nye husholdningen
     */
    public static int ny (String navn) {
        // plan:
        // lage en random string
        // lage ny husholdning med randomstring som navn
        // hente id til samme randomstring
        // endre random string til det faktiske navnet
        String sqlsetning = "insert into " + TABELLNAVN + "(navn) values (?)";
        String randomString = RandomGenerator.stringuln(5);
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlsetning)){
            preparedStatement.setString(1, randomString);
            preparedStatement.executeUpdate();
            PreparedStatement andrePrep = connection.prepareStatement(
                    "select husholdningId from husholdning where navn=" + randomString);
            andrePrep.executeQuery();
            PreparedStatement nyttPrep = connection.prepareStatement(
                    "UPDATE " + TABELLNAVN + " SET navn = ? WHERE husholdningId = ?");
            nyttPrep.setString(1, navn);
            nyttPrep.setString(2, Integer.toString(id));
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Endrer navn på en husholdning gitt id.
     *
     * @param id
     * @param nyttNavn
     */
    public static void endreNavn (int id, String nyttNavn) {
        String sqlsetning = "update " + TABELLNAVN + " set navn = ? where husholdningid = ?";
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlsetning)){
            preparedStatement.setString(1, nyttNavn);
            preparedStatement.setString(2, Integer.toString(id));
            preparedStatement.executeUpdate();
        } catch (Exception e) {
                e.printStackTrace();
        }
    }

    /**
     * Sletter husholdning gitt id
     *
     * @param id
     */
    public static void slett (int id) {
        String sqlsetning = "DELETE FROM " + TABELLNAVN +
                " WHERE husholdningId = ?";
        try(Connection connection = ConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlsetning)) {
            preparedStatement.setString(1, Integer.toString(id));
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(ny("iuiuiuiuiiu"));
        //endreNavn(6, "nyerenyerenyere");
        //slett(8);
    }
}
