package backend.geoLocation;

/**
 * @author Abhishek Somani
 * Edited and inplemented by tvg-b on 23.04.2017.
 */


import com.google.gson.Gson;

public final class JsonGenerator {
/*
 * this class is for generating JSON	
 */
	
	/*
	 * initialization of Gson class 
	 */
	private static Gson gson = new Gson();
	
	/**
	 * Takes a Object as parameter and generates a JSON from MODEL class
	 * @param to
	 * @return String 
	 */
	public static String generateJson(Object to) {
		return null == to ? "" : gson.toJson(to);
	}

	/**
	 * Generates the transfer object from the given JSON using Google Gson.
	 * @param json
	 * @param class1
	 * @return Object
	 */

	public static Object generateTOfromJson(String json, Class<?> class1) {
		return gson.fromJson(json, class1);
	}
	
}
