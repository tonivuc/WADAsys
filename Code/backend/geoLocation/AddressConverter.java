package backend.geoLocation;

/**
 * @author Abhishek Somani
 * Edited and inplemented by Trym Vegard Gjelseth-Borgen
 */

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Class that converts from a regular address to longitude and latitude coordinates.
 */
public class AddressConverter {

    /*
     * Geocode request URL. Here see we are passing "json" it means we will get
     * the output in JSON format. You can also pass "xml" instead of "json" for
     * XML output. For XML output URL will be
     * "http://maps.googleapis.com/maps/api/geocode/xml";
     */

    private static final String URL = "http://maps.googleapis.com/maps/api/geocode/json";

    /**
     * Here the fullAddress String is in format like
     * "address,city,state,zipcode". Here address means "street number + route".
     * Creates an java.net.URL object by passing the request URL in
     * constructor. The full adress streing is being converted into
     * UTF-8 format. You will get Exception if you don't convert your
     * address in UTF-8 format. In parameter in the URL we also need
     * to pass "sensor" parameter. Sensor (required parameter)
     * Indicates whether or not the geocoding request comes
     * from a device with a location sensor. This value must be either true
     * or false.
     *
     * @param fullAddress a String containing the fullAddress on this format "Country, city, address, etc.".
     * @return GoogleRespons a GoogleRespons object containing an array of results and a status.
     */
    public GoogleResponse convertToLatLong(String fullAddress) {

        try {
            URL url = new URL(URL + "?address="
                    + URLEncoder.encode(fullAddress, "UTF-8") + "&sensor=false");
            // Open the Connection
            URLConnection conn = url.openConnection();

            InputStream in = conn.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            GoogleResponse response = (GoogleResponse) mapper.readValue(in, GoogleResponse.class);
            in.close();
            return response;

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
