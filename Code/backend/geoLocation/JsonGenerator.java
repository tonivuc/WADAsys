package backend.geoLocation;

/**
 * @author Abhishek Somani
 * Edited and inplemented by Trym Vegard Gjelseth-Borgen
 */

import com.google.gson.Gson;

/**
 * Class for generating a Json.
 */
public final class JsonGenerator {

	private static Gson gson = new Gson();
	
	/**
	 * Takes a Object as parameter and generates a JSON from MODEL class
	 * @param to What Object is being translated into a JSON
	 * @return String 
	 */
	public static String generateJson(Object to) {
		return null == to ? "" : gson.toJson(to);
	}

	/**
	 * Generates the transfer object from the given JSON using Google Gson.
	 * @param json json to generate from
	 * @param class1 class
	 * @return Object
	 */

	public static Object generateTOfromJson(String json, Class<?> class1) {
		return gson.fromJson(json, class1);
	}
	
}
