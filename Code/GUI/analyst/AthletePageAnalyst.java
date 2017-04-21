package GUI.analyst;

import GUI.BaseWindow;
//import GUI.analyst.NewBloodSample;
import backend.Athlete;
import backend.AthleteGlobinDate;
import backend.Location;
import backend.Map;

import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by Nora on 05.04.2017.
 */
public class AthletePageAnalyst extends BaseWindow {
    private JButton findLocationButton;

    private JButton allReadings;
    private JButton allLocations;

    private JTextField dateField;
    private JPanel rootPanel;
    private JPanel graphMapPanel;
    private JPanel infoPanel;
    private JLabel name;
    private JLabel telephone;
    private JLabel sport;
    private JLabel nationality;
    private JLabel currentLocation;
    private JLabel locationText;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JPanel mapCard;
    private Athlete athlete;
    private Location location;
    private JFrame thisFrame;

    public AthletePageAnalyst(int athleteID){

        athlete = new Athlete(athleteID);
        thisFrame = this;

        name.setText(athlete.getFirstname() + " " + athlete.getLastname());
        telephone.setText(athlete.getTelephone());
        sport.setText(athlete.getSport());
        nationality.setText(athlete.getNationality());


        try{
            location = athlete.getLocation(LocalDate.now());
            currentLocation.setText(location.getCity() + ", " + location.getCountry());
            locationText.setText(location.getCity() + ", " + location.getCountry());
        }catch (Exception e){
            System.out.println("GETLOCATION: No location registered." + e.toString());
            currentLocation.setText("Unknown");

        }




        if(location != null){
            mapCard = new Map().getMap(Float.toString(location.getLatitude()), Float.toString(location.getLongitude()));
            graphMapPanel.add(mapCard);

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
        allLocations.addActionListener(actionListener);
        allReadings.addActionListener(actionListener);



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




                Location newLocation = athlete.getLocation(sql.toLocalDate());

                if(newLocation != null){
                    graphMapPanel.removeAll();
                    graphMapPanel.updateUI();
                    mapCard = new Map().getMap(Float.toString(newLocation.getLatitude()), Float.toString(newLocation.getLongitude()));
                    graphMapPanel.add(mapCard);
                    graphMapPanel.updateUI();
                    locationText.setText(newLocation.getCity() + ", " + newLocation.getCountry());

                    System.out.println(newLocation.getCity() + ", " + newLocation.getCountry());
                }
                else{
                    locationText.setText("Location missing for the given date");
                }





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

        }
    }

    public JPanel getMainPanel () {
        return rootPanel;
    }


    public static void main(String[] args) {
        //athletePanelCollector frame = new athletePanelCollector();
        JFrame frame = new JFrame("Athlete information"); //Creating JFrame
        frame.setContentPane(new AthletePageAnalyst(1).getMainPanel()); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        //newPanel.setContentPane(new AthleteSearchPanel().getMainPanel());
        //frame.setContentPane(new athletePanelCollector().getMainPanel());
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}
