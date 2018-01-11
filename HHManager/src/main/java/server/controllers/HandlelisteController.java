package server.controllers;

import server.database.ConnectionPool;

import java.sql.*;

public class HandlelisteController {


    public static void lagHandleliste(String tittel, int husholdningsId, Date frist, boolean offentlig, int skaperId) throws SQLException {

        final String INSERT_Handleliste =
                "INSERT INTO table_name (husholdningsId, frist, offentlig, navn, skaperId) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_Handleliste);)
        {
            statement.setInt(1, husholdningsId);
            statement.setDate(2, frist);
            //med mer...

            try (ResultSet resultSet = statement.executeQuery()) {
                //exist = resultSet.next();
            }
        }
    }
}
