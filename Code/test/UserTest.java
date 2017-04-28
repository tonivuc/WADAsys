package test;

import backend.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by camhl on 20.04.2017.
 */
public class UserTest {
    private User user;
    private String username = "Geirmama";

    @Before
    public void setUp() throws Exception {
        user = new User(username);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getName() throws Exception {

        assertEquals(user.getFirstname(), "Geirboy");


    }

    @Test
    public void getTelephone() throws Exception {
        assertEquals(user.getTelephone(), "97988434");

    }

}