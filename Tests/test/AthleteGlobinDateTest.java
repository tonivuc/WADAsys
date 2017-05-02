package test;

import backend.Athlete;
import backend.AthleteGlobinDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by camhl on 26.04.2017.
 */
public class AthleteGlobinDateTest {
    AthleteGlobinDate athleteGlobinDate;
    AthleteGlobinDate athleteGlobinDate2;
    private double haemoglobinLevel;
    private java.sql.Date sqlDate;
    @Before
    public void setUp() throws Exception {
        this.haemoglobinLevel = 16;
        this.sqlDate = new Athlete(45).checkDateFormat("20170501");

        this.athleteGlobinDate = new AthleteGlobinDate(haemoglobinLevel, sqlDate, 45);
        this.athleteGlobinDate2 = new AthleteGlobinDate(16, sqlDate);



    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getHaemoglobinLevel() throws Exception {

        assertEquals(16, athleteGlobinDate.getHaemoglobinLevel(), 0);
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

        assertEquals(sqlDate, athleteGlobinDate2.getDate());

    }

}