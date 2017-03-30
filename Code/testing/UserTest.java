package testing;

import backend.User;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by camhl on 22.03.2017.
 */
public class UserTest{
    private User user;
    private int ADMIN = 0;
    private int ANALYSOR = 1;
    private int COLLECTOR = 2;

    @Before
    public void start() throws Exception {
        this.user = new User();
        user.setup();
    }

    @After
    public void tearDown() throws Exception {
        user.disconnect();
    }

    @Test
    public void login() throws Exception {
        assertTrue(user.login("Geirmama", "Geirmama123"));
        assertFalse(user.login("GeirmamA", "Geirmama1235"));
    }

    @Test
    public void checkPassword() throws Exception {

        assertTrue(user.login("Geirmama", "Geirmama123"));
        assertFalse(user.login("Geirmama", "Geirmama1235"));

    }

    @Test
    public void findUser() throws Exception {
        assertTrue(user.findUser("Geirmama"));
        assertFalse(user.findUser("Geirmama321"));
    }

    @Test
    public void findUsertype() throws Exception {

        int actualUsertype = user.findUsertype("Geirmama");
        int expectedUserType = ADMIN;
        int notExpectedUserType = COLLECTOR;
        assertEquals((long) expectedUserType, (long) actualUsertype);
        assertNotSame(Integer.valueOf(notExpectedUserType), Integer.valueOf(actualUsertype));

    }

    @Test
    public void findUserByIndex() throws Exception {

        assertEquals(user.findUserByIndex(0).toUpperCase(), "ADMIN");
        assertEquals(user.findUserByIndex(1).toUpperCase(), "ANALYST");
        assertEquals(user.findUserByIndex(2).toUpperCase(), "COLLECTOR");

    }

    @Test
    public void registerUser() throws Exception {
        //assertTrue(user.registerUser("Ola", "Nordmann", "12345678", "OlaNor", "OlaNor123", "Collector"));
        //assertFalse(user.registerUser("Ola", "Nordmann", "12345678", "OlaNor", "OlaNor123", "Collector"));



    }

    @Test
    public void deleteUser() throws Exception {
        //assertTrue(user.deleteUser("OlaNor"));

    }

    @Test
    public void getName() throws Exception {
        assertEquals(user.getName("Geirmama"), "Geir Mama");

    }

}