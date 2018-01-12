package server.controllers;

import server.database.ConnectionPool;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GenereltController {

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
}


