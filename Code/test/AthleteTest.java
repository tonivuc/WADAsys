package test;

import backend.Athlete;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by camhl on 20.04.2017.
 */
public class AthleteTest {
    Athlete athlete;

    @Before
    public void setUp() throws Exception {

        athlete = new Athlete(1);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getFirstname() throws Exception {
        assertEquals(athlete.getFirstname(), "Petter");

    }

    @Test
    public void getLastname() throws Exception {
        assertEquals(athlete.getLastname(), "Northug");

    }

    @Test
    public void getNationality() throws Exception {
        assertEquals(athlete.getNationality(), "Norway");

    }

    @Test
    public void getSport() throws Exception {
        assertEquals(athlete.getSport(), "Cross Country Skiing");

    }

    @Test
    public void getGender() throws Exception {
        assertEquals(athlete.getGender(), "Male");

    }

    @Test
    public void getTelephone() throws Exception {
        assertEquals(athlete.getTelephone(), "79831321");

    }

    @Test
    public void getAthleteID() throws Exception {
        assertEquals(athlete.getAthleteID(), 1);

    }

    @Test
    public void getLocation() throws Exception {


    }

    @Test
    public void getMeasuredAthleteGlobinDates() throws Exception {

    }

    @Test
    public void getExpectedGlobinLevel() throws Exception {

    }

    @Test
    public void getLastMeasuredGlobinLevel() throws Exception {

    }

    @Test
    public void getGlobinDeviation() throws Exception {

    }

}