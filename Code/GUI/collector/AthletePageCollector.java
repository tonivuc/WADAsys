package GUI.collector;

import GUI.BaseWindow;
//import GUI.analyst.NewBloodSample;
import GUI.athlete.AddBloodSample;
import GUI.athlete.MapCard;
import backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by Nora on 05.04.2017.
 */
public class AthletePageCollector extends BaseWindow {
    private JButton findLocationButton;
    private JButton newBloodSample;
    private JButton allReadings;
    private JButton allLocations;
    private JButton zoomoutButton;
    private JButton zoominButton;
    private JButton editButton;

    private JTextField dateField;
    private JPanel rootPanel;
    private JPanel mapPanel;
    private JPanel athleteInfoPanel;
    private JLabel Name;
    private JLabel Telephone;
    private JLabel Sport;
    private JLabel Nationality;
    private JLabel CurrentLocation;
    private JLabel locationText;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JPanel mapCard;
    private Athlete athlete;
    private String location;
    private JFrame thisFrame;
    private String zoom;

    public AthletePageCollector(int athleteID){

        athlete = new Athlete(athleteID);
        thisFrame = this;
        this.zoom = "12";

        Name.setText(athlete.getFirstname() + " " + athlete.getLastname());
        Telephone.setText(athlete.getTelephone());
        Sport.setText(athlete.getSport());
        Nationality.setText(athlete.getNationality());


        try{
            location = athlete.getLocation(LocalDate.now());
            CurrentLocation.setText(location);
            locationText.setText(location);
        }catch (Exception e){
            System.out.println("GETLOCATION: No location registered." + e.toString());
            CurrentLocation.setText("Unknown");

        }




       if(location != null){
           //mapCard = new Map().getMap(Float.toString(location.getLatitude()), Float.toString(location.getLongitude()));
           mapCard = new GoogleMaps().createMap(location, zoom);

           mapPanel.add(mapCard);

        }

        dateField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(dateField.getText().length() >= 8) e.consume();
            }
        });


        dateField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (dateField.getText().equals("yyyyMMdd")) {
                    dateField.setText(null);
                }


            }

            public void focusLost(FocusEvent e) {
                if (dateField.getText().equals("")) {
                    dateField.setText("yyyyMMdd");
                }
                //...
            }
        });

        /*mapCard = new MapCard(Float.toString(location.getLatitude()), Float.toString(location.getLongitude())).getMainPanel();
        mapPanel.add("map", mapCard);
        CardLayout layout = (CardLayout)mapPanel.getLayout();
        layout.show(mapPanel, "map");*/

        ButtonListener actionListener = new ButtonListener();

        findLocationButton.addActionListener(actionListener);
        newBloodSample.addActionListener(actionListener);
        allLocations.addActionListener(actionListener);
        allReadings.addActionListener(actionListener);
        zoominButton.addActionListener(actionListener);
        zoomoutButton.addActionListener(actionListener);
        editButton.addActionListener(actionListener);

    }
    public void setAthleteID(int athleteID){
        this.athlete = new Athlete(athleteID);
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            String buttonPressed = actionEvent.getActionCommand();

            if(buttonPressed.equals("Find location")){

                String dateString = dateField.getText();

                java.sql.Date sql = null;

                try{
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    Date parsed = format.parse(dateString);
                    sql = new java.sql.Date(parsed.getTime());
                    System.out.println(sql);
                }catch(Exception e){
                    System.out.println("FINDLOCATION: Date in wrong formate.");
                    showMessageDialog(null, "Wrong date format. \n\nPlease use the format: yyyyMMdd.");
                }


                location = athlete.getLocation(sql.toLocalDate());

                if(location != ""){
                    mapPanel.removeAll();
                    mapPanel.updateUI();
                    mapCard = new GoogleMaps().createMap(location, zoom);
                    mapPanel.add(mapCard);
                    mapPanel.updateUI();
                    locationText.setText(location);

                    System.out.println(location);
                }
                else{
                    locationText.setText("Location missing for the given date");
                }





            }

            if(buttonPressed.equals("New blood sample")){

                BaseWindow frame = new BaseWindowCollector("Collector");
                AddBloodSample addBloodSample = new AddBloodSample(athlete.getAthleteID(),frame);
                frame.setContentPane(addBloodSample.getMainPanel());
                frame.pack();  //Creates a window out of all the components
                frame.setVisible(true);   //Setting the window visible


            }

            if(buttonPressed.equals("All haemoglobin readings")){

                AthleteGlobinDate athleteGlobinDate = new AthleteGlobinDate(athlete.getAthleteID());
                athleteGlobinDate.setup();

                showMessageDialog(null, athleteGlobinDate.allReadings(),  "All readings", JOptionPane.INFORMATION_MESSAGE);

                athleteGlobinDate.disconnect();

            }

            if(buttonPressed.equals("All future locations")){

                athlete.setup();
                showMessageDialog(null, athlete.allFutureLocations(), "All future locations", JOptionPane.INFORMATION_MESSAGE);
                athlete.disconnect();

            }

            if(buttonPressed.equals("Zoom out")) {

                int zoomInt = Integer.parseInt(zoom.trim());

                if (zoomInt >= 3) {

                    zoomInt -= 2;

                    zoom = "" + zoomInt;

                    mapPanel.removeAll();
                    mapPanel.updateUI();
                    mapCard = new GoogleMaps().createMap(location, zoom);
                    mapPanel.add(mapCard);
                    mapPanel.updateUI();

                }
            }

            if(buttonPressed.equals("Zoom in")){

                int zoomInt = Integer.parseInt(zoom.trim());

                if (zoomInt <= 16) {

                    zoomInt += 2;

                    zoom = "" + zoomInt;

                    mapPanel.removeAll();
                    mapPanel.updateUI();
                    mapCard = new GoogleMaps().createMap(location, zoom);
                    mapPanel.add(mapCard);
                    mapPanel.updateUI();

                }

            }

            if(buttonPressed.equals("Edit")){

            }

        }
    }

    public JPanel getMainPanel () {
        return rootPanel;
    }


    public static void main(String[] args) {
        //athletePanelCollector frame = new athletePanelCollector();
        JFrame frame = new JFrame("Athlete information"); //Creating JFrame
        frame.setContentPane(new AthletePageCollector(1).rootPanel); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        //newPanel.setContentPane(new UserSearchPanel().getMainPanel());
        //frame.setContentPane(new athletePanelCollector().getMainPanel());
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}
