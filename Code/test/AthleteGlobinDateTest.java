package test;

import backend.Athlete;
import backend.AthleteGlobinDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by camhl on 26.04.2017.
 */
public class AthleteGlobinDateTest {
    AthleteGlobinDate athleteGlobinDate;
    private double haemoglobinLevel;
    private java.sql.Date sqlDate;
    @Before
    public void setUp() throws Exception {
        this.haemoglobinLevel = 16.333;
        this.sqlDate = new Athlete().checkDateFormat("20170426");

        athleteGlobinDate = new AthleteGlobinDate(haemoglobinLevel, sqlDate, 1);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getHaemoglobinLevel() throws Exception {

        assertEquals(16.333, athleteGlobinDate.getHaemoglobinLevel(), 0);
    }

    @Test
    public void getFromDate() throws Exception {

        assertEquals(null, athleteGlobinDate.getFromDate());

    }

    @Test
    public void getToDate() throws Exception {

        assertEquals(null, athleteGlobinDate.getToDate());

    }

    @Test
    public void getDate() throws Exception {

        assertEquals(sqlDate, athleteGlobinDate.getToDate());

    }

}