package backend.geoLocation;

/**
 * @author Abhishek Somani
 * Edited and inplemented by Trym Vegard Gjelseth-Borgen
 */

/**
 * Class made to store and describe a result.
 */
public class Results {

    private Location location;
    private String elevation;
    private String resolution;

    /**
     * Returns the instance variable location.
     * @return Location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Takes a Location Object and sets the instance variable location to that Location Object.
     * @param location location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Returns the instance variable elevation.
     * @return String
     */
    public String getElevation() {
        return elevation;
    }

    /**
     * Takes a String and sets the instance variable elevation to that String.
     * @param elevation elevation
     */
    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    /**
     * Returns the instance variable resolution.
     * @return String
     */
    public String getResolution() {
        return resolution;
    }

    /**
     * Takes a String and sets the instance variable resolution to that String.
     * @param resolution resolution
     */
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    /**
     * Returns a String the instance variables location, elevation and resolution
     * @return String
     */
    @Override
    public String toString() {
        return "ClassPojo [location = " + location + ", elevation = " + elevation + ", resolution = " + resolution + "]";
    }
}