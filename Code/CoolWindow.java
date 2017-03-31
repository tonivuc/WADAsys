import GUI.AthleteSearchPanel;
import GUI.BaseWindow;

import javax.swing.*;

/**
 * Created by Toni on 30.03.2017.
 */
public class CoolWindow extends BaseWindow {

    CoolWindow() {
        JPanel panelet = new JPanel();
        add(panelet);
        panelet.add(new JButton("Test"));
        //AthleteSearchPanel innerPanel = new AthleteSearchPanel();
        //add(innerPanel);
        pack();
    }

    public static void main(String[] args) {
        CoolWindow newWindow = new CoolWindow();
        newWindow.setVisible(true);
    }
}
