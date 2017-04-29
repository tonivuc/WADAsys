package test;

import backend.AvgHaemoglobinLevel;
import backend.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * Created by Toni on 28.04.2017.
 */
public class AvgHaemoglobinLevelTest {

    private AvgHaemoglobinLevel avgGlobin;

    @Before
    public void setUp() throws Exception {
        avgGlobin = new AvgHaemoglobinLevel();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getName() throws Exception {
        assertEquals(avgGlobin.getAverageLevel("male", Date.valueOf(LocalDate.of(2016,5,25))),14.5,0.1);
        //NOTE: AssertEquals could fail because of changes in the database
    }
}
