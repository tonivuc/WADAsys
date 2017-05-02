package test;

import backend.Athlete;
import backend.AthleteGlobinDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

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
        assertEquals(athlete.getTelephone(), "79831320");

    }

    @Test
    public void getAthleteID() throws Exception {
        assertEquals(athlete.getAthleteID(), 1);

    }

    @Test
    public void getLocation() throws Exception {

        java.sql.Date sqlDate = athlete.checkDateFormat("20170426");
        LocalDate date = sqlDate.toLocalDate();
        System.out.println(athlete.getLocation(date));

        assertEquals("Norway, Trondheim", athlete.getLocation(date));

    }

    @Test
    public void getMeasuredAthleteGlobinDates() throws Exception {
        java.sql.Date sqlDate = athlete.checkDateFormat("20170211");

        assertEquals(1, athlete.getMeasuredAthleteGlobinDates().get(0).getAthleteID());
        assertEquals(21.2, athlete.getMeasuredAthleteGlobinDates().get(1).getHaemoglobinLevel(), 0);
        assertEquals(sqlDate, athlete.getMeasuredAthleteGlobinDates().get(1).getDate());


    }

    @Test
    public void getExpectedGlobinLevel() throws Exception {
        ArrayList<AthleteGlobinDate> arrayList = athlete.getExpectedAthleteGlobinDates();

        assertEquals(1, arrayList.get(0).getAthleteID());
        assertEquals(16, arrayList.get(0).getHaemoglobinLevel(), 0.5);

    }

    @Test
    public void getLastMeasuredGlobinLevel() throws Exception {
        athlete.setup();
        assertEquals(15.0, athlete.getLastMeasuredGlobinLevel(LocalDate.now()).getHaemoglobinLevel(), 0);
        athlete.disconnect();
    }

    @Test
    public void getGlobinDeviation() throws Exception {

        athlete.setup();
        double measured = athlete.getLastMeasuredGlobinLevel(LocalDate.now()).getHaemoglobinLevel();
        double expected = athlete.getExpectedAthleteGlobinDates().get(0).getHaemoglobinLevel();
        double res = (measured / expected) * 100;
        athlete.disconnect();

        assertEquals(res, athlete.getGlobinDeviation(), 1.1); //not completely acurate



    }

    @Test
    public void getReadingsUser() throws Exception {
        String[][] res = athlete.getReadingsUser("Collector");

        java.sql.Date sqlDate = new Athlete(athlete.getAthleteID()).checkDateFormat("20170426");
        LocalDate date = sqlDate.toLocalDate();

        assertEquals("2017-04-23", res[0][0]);
        assertEquals("15", res[0][1]);

    }

}