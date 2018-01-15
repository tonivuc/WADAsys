package server.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.database.ConnectionPool;
import server.restklasser.Handleliste;
import server.restklasser.Vare;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.*;

public class HandlelisteControllerTest {

    //Burde ha en @Before som sjekker om du har nødvendig data i databasen, legger det inn
    //så du senere kan teste get-metoder.

    @Test
    public void slettHandleliste() throws Exception {
        assertTrue(HandlelisteController.slettHandleliste(10));
    }

    @Test
    public void getHandlelister() throws Exception {
        ArrayList<Handleliste> handlelister = HandlelisteController.getHandlelister(1,1);

        System.out.println("Handlelister:");
        for (Handleliste object: handlelister) {
            //assertNotNull("Ingen varer ble returnert",object); Ubrukelig test, kjører ikke hvis array er tom
            System.out.println(object.getTittel());
        }
    }

    @Test
    public void getVarer() throws Exception {

        try (Connection connection = ConnectionPool.getConnection()){
            ArrayList<Vare> varer =HandlelisteController.getVarer(1,connection);

            System.out.println("Varer i handlelisteId 1");
            for (Vare object: varer) {
                //assertNotNull("Ingen varer ble returnert",object); Ubrukelig test, kjører ikke hvis array er tom
                System.out.println(object.getVarenavn());
            }
        }
    }

    @Test
    public void lagHandleliste() throws Exception {

        Handleliste testHandleliste = new Handleliste();
        testHandleliste.setHusholdningId(2);
        testHandleliste.setTittel("Den beste listen");
        testHandleliste.setFrist(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        testHandleliste.setOffentlig(true);
        testHandleliste.setSkaperId(2);

        int nyHandlelisteId = HandlelisteController.lagHandleliste(testHandleliste);
        assertTrue("nyHandleListeId skal være stærre enn -1 ", nyHandlelisteId > -1);
    }

    @Test
    public void getHandleliste() throws Exception {
        Handleliste liste = HandlelisteController.getHandleliste(1);
        assertEquals(1, liste.getHandlelisteId());
    }

}