package test;

import backend.RandomPasswordGenerator;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by camhl on 26.04.2017.
 */
public class RandomPasswordGeneratorTest {


    @Test
    public void getRandomPassword(){
        RandomPasswordGenerator randomPasswordGenerator = new RandomPasswordGenerator();
        assertNotEquals(randomPasswordGenerator.getRandomPassword(), randomPasswordGenerator.getRandomPassword());
    }

}