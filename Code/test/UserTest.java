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

    @Before
    public void setUp() throws Exception {
        user = new User();

    }

    @After
    public void tearDown() throws Exception {

    }

    /*@Test
    public void login() throws Exception {
        assertTrue(user.login("Geirmama", "Geirmama123"));
        assertFalse(user.login("Geirmama", "WrongPassword"));
        assertFalse(user.login("WrongUsername", "Geirmama123"));

    }

    @Test
    public void checkPassword() throws Exception {
        assertFalse(user.checkPassword("Geirmama", "WrongPassword"));
        assertTrue(user.checkPassword("Geirmama", "Geirmama123"));

    }

    @Test
    public void findUser() throws Exception {
        assertTrue(user.findUser("Geirmama"));
        assertFalse(user.findUser("UserNotExcisting"));

    }

    @Test
    public void findUsertype() throws Exception {
        int ADMIN = 0;
        int ANALYST = 1;
        int COLLECTOR = 2;

        assertEquals(user.findUsertype("Geirmama"), ADMIN);
        assertEquals(user.findUsertype("Collector"), COLLECTOR);
        assertEquals(user.findUsertype("Analyst"), ANALYST);


    }

    @Test
    public void findUserByIndex() throws Exception {

        assertEquals(user.findUsertype("Admin"), 0);
        assertEquals(user.findUsertype("Analyst"), 1);
        assertEquals(user.findUsertype("Collector"), 2);
    }

    @Test
    public void registerUser() throws Exception {
        assertFalse(user.registerUser("Geir", "Mama", "123", "Geirmama", "Geirmama123", "Admin"));
        assertTrue(user.registerUser("Firstname", "Lastname", "321", "TestUser", "TestUser123", "Collector"));

    }

    @Test
    public void deleteUser() throws Exception {

        assertTrue(user.deleteUser("TestUser"));

    }*/

    @Test
    public void getName() throws Exception {

        assertEquals(user.getName("Geirmama"), "Geir Mama");


    }

    @Test
    public void getTelephone() throws Exception {
        assertEquals(user.getTelephone("Geirmama"), "97988434");

    }

}