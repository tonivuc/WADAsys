package server.controllers;

import server.database.ConnectionPool;
import server.restklasser.*;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class HusholdningController {
    private static final String TABELLNAVN = "husholdning";
    private static PreparedStatement ps;
    private static Statement s;

    public static String getNavn (int id) {
        return GenereltController.getString("navn", TABELLNAVN, id);
    }
    public Husholdning getHusholdningData(String epost){
        Husholdning huset = new Husholdning();
        int fav = 0;
        int brukerId = 0;
        int handlelisteId = 0;
        String hentFav = "SELECT favorittHusholdning, brukerId FROM bruker WHERE epost = ?";

        try(Connection con = ConnectionPool.getConnection()){
            ps = con.prepareStatement(hentFav);
            ps.setString(1, epost);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    fav = rs.getInt("favorittHusholdning");
                    brukerId = rs.getInt("brukerId");
                    if (fav == 0){
                        return null;
                    }
                    huset.setHusholdningId(fav);
                }

            }
            String hentHus = "SELECT * FROM husholdning WHERE husholdningId = "+fav;
            String hentNyhetsinnlegg = "SELECT * FROM nyhetsinnlegg WHERE husholdningId = "+fav;
            String hentAlleMedlemmer = "SELECT navn FROM hhmedlem LEFT JOIN bruker ON bruker.brukerId = hhmedlem.brukerId WHERE husholdningId = "+fav;
            String hentHandleliste = "SELECT navn, handlelisteId FROM handleliste WHERE husholdningId = "+fav +" AND (offentlig = 1 OR skaperId = "+brukerId+")";
            String hentVarer = "SELECT vareNavn, kjøpt FROM vare WHERE handlelisteId = "+handlelisteId;

            s = con.createStatement();
            ResultSet rs = s.executeQuery(hentHus);
            while (rs.next()){
                String husNavn = rs.getString("navn");
                huset.setNavn(husNavn);
            }

            s = con.createStatement();
            rs = s.executeQuery(hentNyhetsinnlegg);
            while(rs.next()){
                Nyhetsinnlegg nyhetsinnlegg = new Nyhetsinnlegg();
                nyhetsinnlegg.setNyhetsinnleggId(rs.getInt("nyhetsinnleggId"));
                nyhetsinnlegg.setTekst(rs.getString("tekst"));
                nyhetsinnlegg.setDato(rs.getDate("dato"));
                nyhetsinnlegg.setForfatterId(rs.getInt("forfatterId"));
                nyhetsinnlegg.setHusholdningId(fav);
                huset.addNyhetsinnlegg(nyhetsinnlegg);
            }


            s = con.createStatement();
            rs = s.executeQuery(hentAlleMedlemmer);
            while(rs.next()){
                Bruker bruker = new Bruker();
                bruker.setNavn(rs.getString("navn"));
                huset.addMedlem(bruker);
            }
            Handleliste handleliste = new Handleliste();
            s = con.createStatement();
            rs = s.executeQuery(hentHandleliste);
            while (rs.next()){

                handleliste.setTittel(rs.getString("navn"));
                handleliste.setHandlelisteId(rs.getInt("handlelisteId"));
                handleliste.setHusholdningId(fav);
                huset.addHandleliste(handleliste);
                handlelisteId = rs.getInt("handlelisteId");
            }

            s = con.createStatement();
            rs = s.executeQuery(hentVarer);
            while(rs.next()){
                Vare vare = new Vare();
                vare.setHandlelisteId(handlelisteId);
                vare.setVarenavn(rs.getString("vareNavn"));
                int i = rs.getInt("kjøpt");
                if (i == 1){
                    vare.setKjøpt(true);
                }else{
                    vare.setKjøpt(false);
                }
                handleliste.addVarer(vare);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return huset;
    }
}
