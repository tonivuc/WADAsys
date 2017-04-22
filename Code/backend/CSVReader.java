package backend;

/**
 * Created by tvg-b on 22.04.2017.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {



    public CSVReader () {

    }

    public static void main(String[] args) {

        String csvFile = "/Users/tvg-b/documents/NTNU/SYS/WADASYSNEW/WADA_sys/CSV_files/locations20170422.csv";
        String line = "";
        String cvsSplitBy = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use semicolon as separator
                String[] location = line.split(cvsSplitBy);

                System.out.println("Name: " + location[0] + " , location: " + location[1] + ", fromDate: " + location[2] + ", toDate: " + location[3]);


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
