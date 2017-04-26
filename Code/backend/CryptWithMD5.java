package backend;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by camhl on 19.04.2017.
 *
 * A class that has it's one purpose of crypting passwords using MD5 method.
 * <h1>http://stackoverflow.com/questions/6592010/encrypt-and-decrypt-a-password-in-java</h1>
 */

public class CryptWithMD5 {

    /**
     * Message digests are secure one-way hash functions that take
     * arbitrary-sized data and output a fixed-length hash value.
     */
    private static MessageDigest md;

    /**
     * Takes a String and crypts it with MD5 cryptation. Returns the crypted String.
     * @param password password as a String that you want to crypt
     * @return String
     */
    public static String cryptWithMD5(String password){
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] passBytes = password.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<digested.length;i++){
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CryptWithMD5.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;


    }
}