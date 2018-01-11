package server.controllers;

// Vi har controller-klasser fordi da kan vi teste login-funksjonalitet uten å bruke services. JUnit og sånn.
// Her kan vi også ha SQL-kall

import server.database.DatabaseConnection;
import server.restklasser.Bruker;
import java.sql.*;
/*

public class BrukerController {

    DatabaseConnection dbConection;
    public static PreparedStatement preparedStatement;

    public boolean registrerBruker(Bruker bruker){
        String pass = bruker.getPassord();
        String navn = bruker.getNavn();
        String epost = bruker.getEpost();
        String epostLedig = "SELECT epost FROM bruker WHERE epost = ?";

        String query = "INSERT INTO bruker (passord, navn, epost) VALUES (?, ?, ?)";


        try{
            preparedStatement = con.prepareStatement(epostLedig);
            preparedStatement.setString(1,epost);
            ResultSet rs = preparedStatement.executeQuery(epostLedig);
            if (rs != null){
                return false;
            }
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,pass);
            preparedStatement.setString(2,navn);
            preparedStatement.setString(3,epost);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean rettEpostOgPass(String epost, String passord){
        String query = "SELECT passord FROM bruker WHERE epost = ?";
        try{
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,epost);
            ResultSet rs = preparedStatement.executeQuery(query);
            if (rs.toString() == passord){
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;

    }

}
*/
