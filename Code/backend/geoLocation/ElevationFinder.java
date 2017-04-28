package backend.geoLocation;

/**
 * @author Abhishek Somani
 * Edited and inplemented by tvg-b on 23.04.2017.
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Class used to find the elevation of an athlete based on an address only.
 */
public class ElevationFinder {

	/**
	 * Takes a location (on this form "Country, City, etc,"), converts the adress using the AddressConverter class,
	 * into latitude and longitude coordinates. It then uses the Google Elevation API to translate from
	 * longitude and latitude to altitude and returns the altitude as a float. If the address/location is not found
	 * it will return 0f.
	 *
	 * @param location a String containing the fullAddress on this format "Country, city, address, etc.".
	 * @return float a describing the altitude of the location.
	 */

	public float getElevation (String location) {

		float latitude = 0f;
		float longitude = 0f;
		float elevation = 0f;

		GoogleResponse locationRes = new AddressConverter().convertToLatLong(location);
		if(locationRes.getStatus().equals("OK"))
		{
			for(Result result : locationRes.getResults())
			{
				latitude = Float.parseFloat(result.getGeometry().getLocation().getLat());
				longitude = Float.parseFloat(result.getGeometry().getLocation().getLng());
			}
		}
		else
			{
			System.out.println("Something went wrong when converting from location to latitude " +
					"ang longitude in method getElevation i class ElevationFinder: " + locationRes.getStatus());
		}


		try {
			URL url = new URL("https://maps.googleapis.com/maps/api/elevation/json?locations=" + latitude + "," + longitude);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			String line, outputString = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			while ((line = reader.readLine()) != null) {
				outputString += line;
			}

			ElevatorPojo ep = (ElevatorPojo) backend.geoLocation.JsonGenerator.generateTOfromJson(outputString, ElevatorPojo.class);


			for(Results altitudeRes: ep.getResults()) {

				String elevationString = altitudeRes.getElevation();
				elevation = Float.parseFloat(elevationString);
				return elevation;
			}



		} catch (MalformedURLException e) {
			System.out.println("MalformedURLException in method getElevation in class ElevationFinder.java: " + e);
		} catch (ProtocolException e) {
			System.out.println("ProtocolException in method getElevation in class ElevationFinder.java: " + e);
		} catch (IOException e) {
			System.out.println("IOException in method getElevation in class ElevationFinder.java: " + e);
		}

		return elevation;
	}
}
