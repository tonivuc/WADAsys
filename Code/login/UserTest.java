package login;
import java.sql.*;
import DatabaseConnection.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.JUnit4;

import javax.swing.plaf.nimbus.State;
import javax.xml.crypto.Data;

import static org.junit.Assert.*;

/**
 * Created by camhl on 22.03.2017.
 */
public class UserTest {

    private User user;
    private int ADMIN = 0;
    private int ANALYSOR = 1;
    private int COLLECTOR = 2;
    private static DatabaseConnection databaseConnection = new DatabaseConnection();
    private static Statement statement;

    @BeforeClass
    public static void setUpClass() throws Exception{
        statement = databaseConnection.getStatement();
    }


    @Before
    public void setUp() throws Exception {

        user = new User(statement);
    }

    @After
    public void tearDown() throws Exception {
        //user.closeAll();

    }

    @Test
    public void testLogin() {
        assertTrue(user.login("Geirmama", "Geirmama123"));
        assertFalse(user.login("GeirmamA", "Geirmama1235"));

    }

    @Test
    public void testCheckPassword() {
        assertTrue(user.login("Geirmama", "Geirmama123"));
        assertFalse(user.login("Geirmama", "Geirmama1235"));


    }

    @Test
    public void testFindUsertype() throws Exception {
        int actualUsertype = user.findUsertype("Geirmama");
        int expectedUserType = ADMIN;
        int notExpectedUserType = COLLECTOR;

        assertEquals(expectedUserType, actualUsertype);
        assertNotSame(notExpectedUserType, actualUsertype);


    }

    @Test
    public void testFindUsertypeByIndex(){
        assertEquals(user.findUserByIndex(0).toUpperCase(), ADMIN);
        assertEquals(user.findUserByIndex(2).toUpperCase(), COLLECTOR);

    }
}