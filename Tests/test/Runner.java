package test;

import databaseConnectors.DatabaseConnection;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        AthleteGlobinDateTest.class,
        AthleteTest.class,
AvgHaemoglobinLevelTest.class,
DatabaseConnectionTest.class,
LocationAdderTest.class,
RandomPasswordGeneratorTest.class,
UserManagerTest.class,
UserTest.class})
public class Runner {
    @BeforeClass
    public static void setup() throws Exception {

        new DatabaseConnection().setVariables();

    }

    @AfterClass
    public static void teardown() throws Exception {

    }
}
