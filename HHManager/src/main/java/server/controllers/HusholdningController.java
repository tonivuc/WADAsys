package server.controllers;

import server.database.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HusholdningController {
    private static final String TABELLNAVN = "husholdning";

    public static String getNavn (int id) {
        return GenereltController.getGenerelt("navn", TABELLNAVN, id);
    }
}
