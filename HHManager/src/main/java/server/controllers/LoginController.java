package server.controllers;

// Vi har controller-klasser fordi da kan vi teste login-funksjonalitet uten å bruke services. JUnit og sånn.
// Her kan vi også ha SQL-kall

import server.database.Database;
import server.restklasser.Bruker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    private static final String SQL_EXIST = "SELECT * FROM bruker WHERE brukerId=? AND passord=?";

    public boolean exist(Bruker user) throws SQLException {
        boolean exist = false;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_EXIST);) {
            statement.setString(1, Integer.toString(user.getBrukerId()));
            statement.setString(2, user.getPassord());

            try (ResultSet resultSet = statement.executeQuery()) {
                exist = resultSet.next();
            }
        }

        return exist;
    }
}
