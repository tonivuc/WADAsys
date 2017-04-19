package GUI.collector;

import GUI.BaseWindow;
import GUI.athlete.MapCard;
import backend.Athlete;
import backend.Location;
import backend.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Nora on 05.04.2017.
 */
public class AthletePageCollector extends BaseWindow {
    private JButton findLocationButton;
    private JTextField dateField;
    private JPanel rootPanel;
    private JPanel mapPanel;
    private JPanel athleteInfoPanel;
    private JLabel Name;
    private JLabel Telephone;
    private JLabel Sport;
    private JLabel Nationality;
    private JLabel CurrentLocation;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JPanel mapCard;
    private Athlete athlete;
    private Location location;

    public AthletePageCollector(int athleteID){

        athlete = new Athlete(athleteID);

        Name.setText(athlete.getFirstname() + " " + athlete.getLastname());
        Telephone.setText(athlete.getTelephone());
        Sport.setText(athlete.getSport());
        Nationality.setText(athlete.getNationality());


        try{
            location = athlete.getLocation(LocalDate.now());
            CurrentLocation.setText(location.getCity() + ", " + location.getCountry());
        }catch (Exception e){
            System.out.println("GETLOCATION: No location registered." + e.toString());
            CurrentLocation.setText("Unknown");

        }


       /* if(location != null){
            add(new Map().getMap(Float.toString(location.getLatitude()), Float.toString(location.getLongitude())));
        }*/

        mapCard = new MapCard(Float.toString(location.getLatitude()), Float.toString(location.getLongitude())).getMainPanel();
        mapPanel.add("map", mapCard);
        //CardLayout layout = (CardLayout)mapPanel.getLayout();
        //layout.show(mapPanel, "map");

        ButtonListener actionListener = new ButtonListener();

        findLocationButton.addActionListener(actionListener);


    }

    public void setAthleteID(int athleteID){
        this.athlete = new Athlete(athleteID);
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            String buttonPressed = actionEvent.getActionCommand();

            if(buttonPressed.equals("Find location")){



            }

        }
    }



    public static void main(String[] args) {
        //athletePanelCollector frame = new athletePanelCollector();
        JFrame frame = new JFrame("Athlete information"); //Creating JFrame
        frame.setContentPane(new AthletePageCollector(1).rootPanel); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        //newPanel.setContentPane(new AthleteSearchPanel().getMainPanel());
        //frame.setContentPane(new athletePanelCollector().getMainPanel());
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}
