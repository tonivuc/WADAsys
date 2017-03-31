package backend;

/**
 * Created by tvg-b on 31.03.2017.
 */
public class Location {

    private float longitude;
    private float latitude;
    private float altitude;
    private String city;
    private String country;

    public Location (float longitude, float latitude, float altitude, String city, String country) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.city = city;
        this.country = country;
    }

    public float getLongitude () {
        return longitude;
    }

    public float getLatitude () {
        return latitude;
    }

    public float getAltitude () {
        return altitude;
    }

    public String getCity () {
        return city;
    }

    public String getCountry () {
        return country;
    }

    public String toString () {
        return longitude + ", " + latitude + ", " + altitude + ", " + country + ", " + city;
    }

}
