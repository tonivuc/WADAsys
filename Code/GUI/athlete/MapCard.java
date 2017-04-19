package GUI.athlete;

import GUI.BaseWindow;
import backend.Map;
import backend.Map;

import javax.swing.*;


/**
 * Created by camhl on 19.04.2017.
 */
public class MapCard extends JFrame{
    private JPanel rootPanel;

    public MapCard(String latitude, String longitude){

        Map map = new Map();
        add(new Map().getMap(latitude, longitude));
        pack();
        setVisible(true);
    }

    public JPanel getMainPanel(){
        return rootPanel;
    }


}
