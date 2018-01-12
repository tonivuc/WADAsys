package server.controllers;

import org.junit.Test;

import static org.junit.Assert.*;

public class HusholdningControllerTest {
    @Test
    public void getNavn() throws Exception {
        assertEquals("team6", HusholdningController.getNavn(1));
    }

}