package backend;

/**
 * Created by tvg-b on 21.04.2017.
 */
public class Sport {

    private String sport;

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
