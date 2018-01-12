package server.controllers;

import static org.junit.Assert.*;

public class BrukerControllerTest {
    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @org.junit.Test
    public void getBrukernavn() throws Exception {
        assertEquals("bruker1", BrukerController.getBrukernavn(1));
    }

    @org.junit.Test
    public void getEpost() throws Exception {
        assertEquals("bruker1@mail.no", BrukerController.getEpost(1));
    }

    @org.junit.Test
    public void getFavoritthusholdning() throws Exception {
        assertEquals("1", BrukerController.getFavoritthusholdning(1));
    }

    @org.junit.Test
    public void loginOk() throws Exception {
    }
}