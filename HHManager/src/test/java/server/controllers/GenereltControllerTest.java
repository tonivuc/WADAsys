package server.controllers;

import org.junit.Test;

import static org.junit.Assert.*;

public class GenereltControllerTest {
    @Test
    public void getGenerelt() throws Exception {
        assertEquals("team6", GenereltController.getGenerelt("navn", "husholdning", 1));
    }

}