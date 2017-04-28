package backend.geoLocation;

/**
 * @author Abhishek Somani
 * Edited and inplemented by tvg-b on 23.04.2017.
 */


public class ElevatorPojo {

	private Results[] results;
	private String status;

	/**
	 * Retuns an Array of results objects.
	 * @return Results[]
	 */
	public Results[] getResults ()
	    {
	        return results;
	    }


	/**
	 * Takes an Array of Result objects and the instance variable results to that that Array.
	 * @param results Array of results
	 */
	public void setResults (Results[] results)
	    {
	        this.results = results;
	    }


	/**
	 * Reutns the status.
	 * @return String
	 */
	public String getStatus ()
	{
		return status;
	}

	/**
	 * Takes a String parameter and sets the instance variable status to that string.
	 * @param status status
	 */
	public void setStatus (String status)
	{
		this.status = status;
	}


	/**
	 * Returns the result Array and status as a String.
	 * @return String
	 */
	@Override
	public String toString()
	    {
	        return "ClassPojo [results = "+results+", status = "+status+"]";
	    }
}
