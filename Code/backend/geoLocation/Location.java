package backend.geoLocation;

/**
 * @author Abhishek Somani
 * Edited and inplemented by Trym Vegard Gjelseth-Borgen
 */

/**
 * Class made to describe a location.
 */
public class Location {

    private String lat;
    private String lng;

    /**
     * Returns the instance variable lat.
     * @return String
     */
    public String getLat() {
        return lat;
    }

    /**
     * Takes a String and sets the instance variable lat to that String.
     * @param lat latiturde
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     * Returns the instance variable lng.
     * @return String
     */
    public String getLng() {
        return lng;
    }

    /**
     * Takes a String and sets the instance variable lng to that String
     * @param lng longitude
     */
    public void setLng(String lng) {
        this.lng = lng;
    }
}