package backend.geoLocation;

/**
 * @author Abhishek Somani
 * Edited and inplemented by tvg-b on 23.04.2017.
 */

import org.codehaus.jackson.annotate.JsonIgnore;

public class Geometry {

    private Location location ;
    private String location_type;

    @JsonIgnore
    private Object bounds;

    @JsonIgnore
    private Object viewport;

    /**
     * Retuns the instance variable location.
     * @return Location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Takes a location as parameter and sets the instance variable location to that location.
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Returns the instance variable location_type.
     * @return String
     */
    public String getLocation_type() {
        return location_type;
    }

    /**
     * Takes a String as parameter and sets the instance variable location_type to that String.
     * @param location_type
     */
    public void setLocation_type(String location_type) {
        this.location_type = location_type;
    }

    /**
     * Returns the instance variable bounds.
     * @return Object
     */
    public Object getBounds() {
        return bounds;
    }

    /**
     * Takes a Object and sets the instance variable bounds to that Object.
     * @param bounds
     */
    public void setBounds(Object bounds) {
        this.bounds = bounds;
    }

    /**
     * Returns the instance variable viewport.
     * @return Object
     */
    public Object getViewport() {
        return viewport;
    }

    /**
     * Takes a Object and sets the instance variable viewport to that Object.
     * @param viewport
     */
    public void setViewport(Object viewport) {
        this.viewport = viewport;
    }
}
