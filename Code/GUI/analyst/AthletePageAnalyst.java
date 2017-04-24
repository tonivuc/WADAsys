package GUI.analyst;

import GUI.BaseWindow;
import GUI.testWindows.mockupGraph;
import backend.Athlete;
import backend.GoogleMaps;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static javax.swing.JOptionPane.showMessageDialog;

//import GUI.analyst.NewBloodSample;

/**
 * Created by Nora on 05.04.2017.
 */
public class AthletePageAnalyst extends BaseWindow {

    private JButton findLocationButton;
    //private JButton allReadings;
    private JButton allLocationsButton;
    private JButton editButton;
    private JButton zoominButton;
    private JButton zoomoutButton;
    private JButton graphMapButton;


    private JTextField dateField;
    private JPanel rootPanel;
    private JPanel graphMapPanel;
    private JPanel infoPanel;
    private JLabel name;
    private JLabel telephone;
    private JLabel sport;
    private JLabel nationality;
    private JLabel currentLocation;
    private JTable locationTable;
    private JScrollPane scrollBar;
    private JLabel locationLabel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JPanel mapCard;
    private JPanel graphCard;
    private JPanel cardHolder;
    private Athlete athlete;
    private String location;
    private JFrame thisFrame;
    private CardLayout layout;

    private String zoom;

    DefaultTableModel dm;
    private Athlete tableSetup;

    private boolean athleteIsChosen;
    private static java.sql.Date dateChosen;
    private static double readingChosen;
    private String entry_creator;

    public AthletePageAnalyst(int athleteID){

        athlete = new Athlete(athleteID);
        thisFrame = this;
        this.zoom = "12";

        name.setText(athlete.getFirstname() + " " + athlete.getLastname());
        telephone.setText(athlete.getTelephone());
        sport.setText(athlete.getSport());
        nationality.setText(athlete.getNationality());
        graphMapButton.setText("Show graph");
        locationLabel.hide();


        /*
        *Fills in the information about the athlete
         */
        if(athlete.getTelephone() == null) telephone.setText("Unknown");
        if(athlete.getSport() == null) sport.setText("Unknown");
        if(athlete.getNationality() == null) nationality.setText("Unknown");

        location = athlete.getLocation(LocalDate.now());
        if(location != null) {
            currentLocation.setText(location);
        }

        /*
        *Adds the two cards (map and graph) to the cardholdet
        *
         */
        graphCard = new mockupGraph(athleteID).getMainPanel();
        if(location != null){
            //mapCard = new Map().getMap(Float.toString(location.getLatitude()), Float.toString(location.getLongitude()));
            mapCard = new GoogleMaps().createMap(location, zoom);



        }
        graphMapPanel.add("graph", graphCard);
        graphMapPanel.add("map", mapCard);

        /*
        * Showing the map card on the layout for cardholder
         */
        layout = (CardLayout)graphMapPanel.getLayout();
        layout.show(graphMapPanel, "map");


        /*
        *Adding keylistener and focuslistener to datefield
         */
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


        /*
        * Creating an actionlistener and adding all the buttons to it.
        *
         */
        ButtonListener actionListener = new ButtonListener();

        findLocationButton.addActionListener(actionListener);
        //allLocationsButton.addActionListener(actionListener);
        //allReadings.addActionListener(actionListener);
        editButton.addActionListener(actionListener);
        zoominButton.addActionListener(actionListener);
        zoomoutButton.addActionListener(actionListener);
        graphMapButton.addActionListener(actionListener);

        //Create columns for the readingList
        dm = (DefaultTableModel) locationTable.getModel();
        dm.addColumn("From date");
        dm.addColumn("To date");
        dm.addColumn("Location");

        this.tableSetup = new Athlete(athlete.getAthleteID());
        populateRows();

        //adds a listSelectionListener to the readingList
        locationTable.getSelectionModel().addListSelectionListener(createListSelectionListener(locationTable));
        //readingsList.hide();
        //scrollBar.hide();

    }

    private void populateRows() {
        String[][] results = tableSetup.getLocationsArray(athlete.getAthleteID());
        for (int i = 0; i < results.length; i++) {
            dm.addRow(results[i]);
            System.out.println(results[i][0] + results[i][1] + results[i][2] + "\n" + results[i]);
        }
    }

    ListSelectionListener createListSelectionListener(JTable resultsTable) {
        ListSelectionListener listener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                //Keeps it from firing twice (while value is adjusting as well as when it is done)
                if (!event.getValueIsAdjusting()) {//This line prevents double events

                    int row = resultsTable.getSelectedRow();
                    //int athleteID = Integer.parseInt((String)resultsTable.getValueAt(row, 3));
                    location = (String)resultsTable.getValueAt(row, 2);

                    graphMapPanel.removeAll();
                    graphMapPanel.updateUI();
                    mapCard = new GoogleMaps().createMap(location, zoom);
                    graphMapPanel.add("map", mapCard);
                    graphMapPanel.add("graph", graphCard);
                    graphMapPanel.updateUI();
                    locationLabel.setText("Location: " + location);

                    layout.show(graphMapPanel, "map");

                    System.out.println(location);

                }
            }
        };
        return listener;
    }


    public void setAthleteID(int athleteID){
        this.athlete = new Athlete(athleteID);
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            String buttonPressed = actionEvent.getActionCommand();

            //CardLayout administers the different cards
            CardLayout layout = (CardLayout)graphMapPanel.getLayout();

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
                    graphMapPanel.removeAll();
                    //graphMapPanel.updateUI();
                    mapCard = new GoogleMaps().createMap(location, zoom);
                    graphMapPanel.add("map", mapCard);
                    graphMapPanel.add("graph", graphCard);
                    graphMapPanel.updateUI();
                    locationLabel.setText("Location: " + location);

                    System.out.println(location);
                }
                else{
                    locationLabel.setText("Location missing for the given date");
                }
            }


            /*if(buttonPressed.equals("All haemoglobin readings")){

                AthleteGlobinDate athleteGlobinDate = new AthleteGlobinDate(athlete.getAthleteID());
                athleteGlobinDate.setup();

                showMessageDialog(null, athleteGlobinDate.allReadings(),  "All readings", JOptionPane.INFORMATION_MESSAGE);

                athleteGlobinDate.disconnect();
            }*/


            if(buttonPressed.equals("Zoom out")) {

                int zoomInt = Integer.parseInt(zoom.trim());

                if (zoomInt >= 3) {

                    zoomInt -= 2;

                    zoom = "" + zoomInt;

                    graphMapPanel.removeAll();
                    mapCard = new GoogleMaps().createMap(location, zoom);
                    graphMapPanel.add("map", mapCard);
                    graphMapPanel.add("graph", graphCard);
                    layout.show(graphMapPanel, "map");
                    graphMapPanel.updateUI();
                }
            }

            if(buttonPressed.equals("Zoom in")){

                int zoomInt = Integer.parseInt(zoom.trim());

                if (zoomInt <= 16) {

                    zoomInt += 2;

                    zoom = "" + zoomInt;

                    graphMapPanel.removeAll();
                    mapCard = new GoogleMaps().createMap(location, zoom);
                    graphMapPanel.add("map", mapCard);
                    graphMapPanel.add("graph", graphCard);
                    layout.show(graphMapPanel, "map");
                    graphMapPanel.updateUI();
                }
            }

            if(buttonPressed.equals("Edit")){
                JFrame frame = new JFrame("Edit athlete"); //Creating JFrame
                frame.setContentPane(new EditAthlete(1).getMainPanel()); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
                frame.pack();  //Creates a window out of all the components
                frame.setVisible(true);   //Setting the window visible
            }

            if(buttonPressed.equals("Show graph")){

                layout.show(graphMapPanel, "graph");
                graphMapButton.setText("Show map");
                zoominButton.hide();
                zoomoutButton.hide();

            }

            if(buttonPressed.equals("Show map")){

                layout.show(graphMapPanel, "map");
                graphMapButton.setText("Show graph");
                zoominButton.show();
                zoomoutButton.show();
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
        //newPanel.setContentPane(new UserSearchPanel().getMainPanel());
        //frame.setContentPane(new athletePanelCollector().getMainPanel());
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}
