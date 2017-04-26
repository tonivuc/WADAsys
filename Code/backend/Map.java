package backend;

/**
 *
 * @author Camilla Haaheim Larsen
 */

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class Map {

    /**
     * Uses the Google Maps API to make a map from coordinates (latitude, longitude). Takes the location map
     * and puts it into a JPanel. It then returns the JPanel.
     * @param latitude latitude of the place you want to display on the map
     * @param longitude longitude of the place you want to display on the map
     * @return JPanel JPanel containing the map
     */
    public JPanel getMap(String latitude, String longitude){

        JPanel test = new JPanel();

        try {

            String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?center="
                    + latitude
                    + ","
                    + longitude
                    + "&zoom=11&size=612x612&scale=2&maptype=roadmap";
            String destinationFile = "image.jpg";

            // read the map image from Google
            // then save it to a local file: image.jpg

            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destinationFile);
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // create a GUI component that loads the image: image.jpg

        ImageIcon imageIcon = new ImageIcon((new ImageIcon("image.jpg"))
                .getImage().getScaledInstance(630, 600,
                        java.awt.Image.SCALE_SMOOTH));
        test.add(new JLabel(imageIcon));
        // show the GUI window
        test.setVisible(true);

        return test;
    }

}
