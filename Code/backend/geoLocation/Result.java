package backend.geoLocation;

/**
 * @author Abhishek Somani
 * Edited and inplemented by Trym Vegard Gjelseth-Borgen
 */

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Class made to describe and store a result.
 */
public class Result {

    private String formatted_address;
    private boolean partial_match;
    private Geometry geometry;

    @JsonIgnore
    private String place_id;

    @JsonIgnore
    private Object address_components;

    @JsonIgnore
    private Object types;


    /**
     * Returns the instance variable formatted_address.
     * @return String
     */
    public String getFormatted_address() {
        return formatted_address;
    }

    /**
     * Takes a String and sets the instance variable formatted_address to that String.
     * @param formatted_address formatted_address
     */
    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    /**
     * Returns the instance variable partial_match as a true or false value.
     * @return boolean
     */
    public boolean isPartial_match() {
        return partial_match;
    }

    /**
     * Takes a boolean and sets the instance variable partial_match to that boolean.
     * @param partial_match partial_match
     */
    public void setPartial_match(boolean partial_match) {
        this.partial_match = partial_match;
    }

    /**
     * Returns the instance variable geometry.
     * @return Geometry
     */
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * Takes a Geometry Object and sets the instance variable geometry to that Geometry Object.
     * @param geometry geometry
     */
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    /**
     * Returns a the instance variable address_components.
     * @return Object
     */
    public Object getAddress_components() {
        return address_components;
    }

    /**
     * Takes a Object and sets the instance variable address_components to that Object.
     * @param address_components adress_components
     */
    public void setAddress_components(Object address_components) {
        this.address_components = address_components;
    }

    /**
     * Returns the instance variable types.
     * @return Object
     */
    public Object getTypes() {
        return types;
    }

    /**
     * Takes a Object and sets the instance variable types to that Object.
     * @param types types
     */
    public void setTypes(Object types) {
        this.types = types;
    }
}
