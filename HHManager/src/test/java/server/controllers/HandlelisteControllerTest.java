package server.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.restklasser.Handleliste;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.*;

public class HandlelisteControllerTest {

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

}