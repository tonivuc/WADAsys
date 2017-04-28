package backend;

/**
 *
 * @author Trym Vegard Gjelseth-Borgen
 */

/**
 * Class made to store a location together with the number of
 * people at that location. The Object is naturally compared
 * by number of people.
 */
public class AthleteLocation implements Comparable<AthleteLocation> {

    /**
     * A location.
     */
    private String location;

    /**
     * The number of people at a location.
     */
    private int numberOfPeople;

    /**
     * Constructs a new AthleteLocation Object.
     * @param location location for the athletes.
     * @param numberOfPeople the number of athletes at that location.
     */
    public AthleteLocation(String location, int numberOfPeople) {
        this.location = location;
        this.numberOfPeople = numberOfPeople;
    }

    /**
     * Returns the location.
     * @return String
     */
    public String getLocation () {
        return location;
    }

    /**
     * Returns the number of people at that location.
     * @return int
     */
    public int getNumberOfPeople () {
        return numberOfPeople;
    }

    /**
     * Compares the Object by number of people.
     * @param o another AthleteLocation Object.
     * @return int
     */
    @Override
    public int compareTo(AthleteLocation o) {
        if (numberOfPeople > o.getNumberOfPeople()) {
            return 1;
        } else if (numberOfPeople < o.getNumberOfPeople()) {
            return -1;
        } else {
            return 0;
        }
    }
}
