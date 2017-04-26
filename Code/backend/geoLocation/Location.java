package backend.geoLocation;

/**
 * @author Abhishek Somani
 * Edited and inplemented by tvg-b on 23.04.2017.
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
     * @param lat
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
     * @param lng
     */
    public void setLng(String lng) {
        this.lng = lng;
    }
}