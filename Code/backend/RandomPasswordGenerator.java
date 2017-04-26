package backend;

/**
 *
 * @author Trym Vegard Gjelseth-Borgen
 */

import java.math.BigInteger;
import java.security.SecureRandom;

public class RandomPasswordGenerator {

    /**
     * A very random variable
     */
    private SecureRandom random = new SecureRandom();

    /**
     * Returns a giant random set of numbers and letters.
     * @return String
     */
    public String getRandomPassword() {
        return new BigInteger(130, random).toString(32);
    }
}
