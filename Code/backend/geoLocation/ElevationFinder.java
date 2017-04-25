package backend.geoLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class ElevationFinder {

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

	public static void main(String[] args) {

		ElevationFinder elevationFinder = new ElevationFinder();
		System.out.println(elevationFinder.getElevation("Italy, Seiser Alm") + " meters");

	}

}
