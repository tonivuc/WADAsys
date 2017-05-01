package test;

import backend.UserManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by camhl on 26.04.2017.
 */
public class UserManagerTest {
    private UserManager user;

    @Before
    public void setUp() throws Exception {
        user = new UserManager();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
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

        assertEquals(user.findUserByIndex(0), "Admin");
        assertEquals(user.findUserByIndex(1), "Analyst");
        assertEquals(user.findUserByIndex(2), "Collector");
    }

    @Test
    public void registerUser() throws Exception {

        assertFalse(user.registerUser("Geir", "Mama", "123", "Geirmama", "Geirmama123", "Admin"));
        assertTrue(user.registerUser("Firstname", "Lastname", "321", "TestUser", "TestUser123", "Collector"));

    }

    @Test
    public void deleteUser() throws Exception {

        assertTrue(user.deleteUser("TestUser"));

    }
}