package backend;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by tvg-b on 22.04.2017.
 */
public class RandomPasswordGenerator {

    private SecureRandom random = new SecureRandom();

    public String getRandomPassword() {
        return new BigInteger(130, random).toString(32);
    }
}
