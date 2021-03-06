package backend;

/**
 *
 * @author Camilla Haaheim Larsen
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Class made for creating an image of a map for visualising the
 * athletes location in the different athlete pages.
 */
public class GoogleMaps extends JFrame {

    final Logger log = Logger.getLogger(GoogleMaps.class.getName());
    private JPanel contentPane;
    private URL url;

    /**
     * Create the frame.
     */
    public GoogleMaps() {

    }

    public JPanel createMap(String location, String zoom) {

        setTitle("Map");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JFrame test = new JFrame("Google Maps");

        try {
            // String latitude = "-25.9994652";
            // String longitude = "28.3112051";

            // String location = JOptionPane
            //        .showInputDialog(" please enter the desired loccation");// get

            // the
            // location
            // for
            // geo
            // coding

            Scanner sc = new Scanner(location);
            Scanner sc2 = new Scanner(location);
            String marker = "";

            //String path = JOptionPane
            //        .showInputDialog("what is your destination?");

            //String zoom = JOptionPane
            //        .showInputDialog("how far in do you want to zoom?\n"
            //                + "0(whole world), 3 (whole country), 12(zoomed out, whole city) - 19 (zoomed in, building)");

            String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?";
            while (sc.hasNext()) {// add location to imageUrl
                imageUrl = imageUrl + sc.next();
            }


            imageUrl += "&zoom=" + zoom;

            marker = "&markers=color:red|";
            while (sc2.hasNext()) {// add marker location to marker
                marker = marker + sc2.next() + ",";

            }
            marker = marker.substring(0, marker.length() - 1);

            imageUrl = imageUrl + "&size=620x620&scale=2&maptype=hybrid"
                    + marker;
            //
            //log.info("Generated url");

            String destinationFile = "image.jpg";

            // read the map image from Google
            // then save it to a local file: image.jpg
            //
            this.url = new URL(imageUrl);


            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destinationFile);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
            //log.info("Created image.jpg");

            is.close();
            os.close();
            sc.close();
            sc2.close();
            //log.info("Closed util's");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
            log.severe("Could not create image.jpg");
        }// fin getting and storing image

        ImageIcon imageIcon = new ImageIcon((new ImageIcon("image.jpg"))
                .getImage().getScaledInstance(500, 600,
                        java.awt.Image.SCALE_SMOOTH));
        contentPane.setLayout(null);
        JLabel imgMap = new JLabel(imageIcon);
        imgMap.setBounds(5, 5, 500, 600);
        //this.imgMap = imgMap;
        contentPane.add(imgMap);

        return contentPane;
    }

}