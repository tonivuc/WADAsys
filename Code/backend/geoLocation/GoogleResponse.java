package backend.geoLocation;

/**
 * @author Abhishek Somani
 * Edited and inplemented by tvg-b on 23.04.2017.
 */

public class GoogleResponse {

    private Result[] results ;
    private String status ;

    /**
     * Returns the instance variable results. This variable is an array of Results Objects.
     * @return Result[]
     */
    public Result[] getResults() {
        return results;
    }

    /**
     * Takes an Array of Results and sets the instance variable results to that Array.
     * @param results
     */
    public void setResults(Result[] results) {
        this.results = results;
    }

    /**
     * Returns the instance variable
     * @return String
     */
    public String getStatus() {
        return status;
    }

    /**
     * Takes a String and sets the instance variable status to that String.
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
