package backend;

/**
 *
 * @author Trym Vegard Gjelseth-Borgen
 */

/**
 * Class made make a Sport Object.
 */
public class Sport {

    /**
     * Name of the sport
     */
    private String sport;

    /**
     * Constructs a new Sport Object
     * @param sport name of the sport
     */
    public Sport (String sport) {
        this.sport = sport;
    }

    /**
     * Returns the sports name
     * @return String
     */
    public String getSport () {
        return sport;
    }
}
