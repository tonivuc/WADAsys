package backend;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import java.io.IOException;

/**
 * Created by tvg-b on 05.04.2017.
 */
public class Maps {

    private static final String API_KEY = "AIzaSyA9dBCViC3DWA8i55UIH_xyTaWS3I-Emk4";

    public Maps () {

        GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);

        GeocodingResult[] results = new GeocodingResult[0];

        try {
            results = GeocodingApi.geocode(context,"1600 Amphitheatre Parkway Mountain View, CA 94043").await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(results[0].formattedAddress);

    }

    public static void main(String[] args) {
        Maps maps = new Maps();
    }

}
