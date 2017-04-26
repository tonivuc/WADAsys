package backend;

/**
 * Created by tvg-b on 22.04.2017.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {

    /**
     * A String describing the location of the CSV-file
     */
    private String csvFile = "/Users/tvg-b/documents/NTNU/SYS/WADASYSNEW/WADA_sys/CSV_files/locations.csv";

    /**
     * Empty line used to input a line from the CSV-file
     */
    private String line = "";

    /**
     * A String that tells the getCSVContent method by which sign the lines in the CSV-files
     * should be split by.
     */
    private String CSVSPLITBY = ";";

    /**
     * Constructs a new CSVReader Object that sets location of the CSV-file and what to slpit the lines by.
     * @param splitCSVby String that tells what to split the CSV-file by
     * @param csvFileLocation String that describes the location of the CSV-file
     */
    public CSVReader (String splitCSVby, String csvFileLocation) {
        this.csvFile = csvFileLocation;
        this.CSVSPLITBY = splitCSVby;
    }

    /**
     * Constructs a new CSVReader Object that keeps the original instance variables.
     */
    public CSVReader () {
    }

    /**
     * Gets the CSV-content of a CSV saved in the csvFile location. Returns an ArrayList
     * with String[], where each String[] in the ArrayList contains one line from the CSV-file
     * where colons are separated by CVSSPLITBY.
     * @return ArrayList<String[]>
     */
    public ArrayList<String[]> getCSVContent () {

        ArrayList<String[]> stringList = new ArrayList<String[]>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {

                String[] location = line.split(CSVSPLITBY);
                stringList.add(location);

            }

            return stringList;

        } catch (IOException e) {
            System.out.println("IOException method getCSVContent in class CSVReader.java: " + e );
        }

        return null;
    }
}
