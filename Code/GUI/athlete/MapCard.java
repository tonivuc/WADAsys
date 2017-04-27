package GUI.athlete;

import backend.Map;
import javax.swing.*;

/**
 *
 * @author Camilla Haaheim Larsen
 */

public class MapCard extends JPanel{

    /**
     * The mainPanel/rootPanel where everything is contained.
     */
    private JPanel rootPanel;

    /**
     * Constructs the MapCard panel with latitude and longitude.
     * @param latitude the longitude used on the map to find location.
     * @param longitude the latitude used on the map to find location.
     */
    public MapCard(String latitude, String longitude){

        add(new Map().getMap(latitude, longitude));

    }

    /**
     * Returns the mainPanel/rootPanel.
     * @return JPanel
     */
    public JPanel getMainPanel(){
        return rootPanel;
    }

}
