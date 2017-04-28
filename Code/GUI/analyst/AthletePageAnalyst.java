package GUI.analyst;

/**
 *
 * @author Nora Othilie
 */


import GUI.common.BaseWindow;
import GUI.chart.HaemoglobinChart;
import backend.Athlete;
import backend.GoogleMaps;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Class made to handle functionality with athlete page GUI.
 */
public class AthletePageAnalyst extends BaseWindow {

    /**
     * The button that the user uses to find an athlete's location.
     */
    private JButton findLocationButton;

    /**
     * The button the user presses to edit athlete info.
     */
    private JButton editButton;

    /**
     * Used to zoom in on the map.
     */
    private JButton zoominButton;

    /**
     * Used to zoom out on the map.
     */
    private JButton zoomoutButton;

    /**
     * Used to switch between map and graph.
     */
    private JButton graphMapButton;

    /**
     * Textfield where the user inputs a date to find a location.
     */
    private JTextField dateField;

    /**
     * The mainPanel/rootPanel where everything is contained.
     */
    private JPanel rootPanel;

    /**
     * The panel where the graph/map is contained.
     */
    private JPanel graphMapPanel;

    /**
     * The panel where info is contained.
     */
    private JPanel infoPanel;

    /**
     * Label that displays the selected athlete's name.
     */
    private JLabel name;

    /**
     * Label that displays the selected athlete's thelephone number.
     */
    private JLabel telephone;

    /**
     * Label that displays the selected athlete's sport.
     */
    private JLabel sport;

    /**
     * Label that displays the selected athlete's nationality.
     */
    private JLabel nationality;

    /**
     * Label that displays the athlete's current location.
     */
    private JLabel currentLocation;

    /**
     * Table that displays all the locations of the athlete.
     */
    private JTable locationTable;

    /**
     * Scrollbar for the athleteLocation table.
     */
    private JScrollPane scrollBar;

    /**
     * Label displaying a locaiton.
     */
    private JLabel locationLabel;
    private JTable readingsList;
    private JScrollPane scrollPane;

    /**
     * The card/JPanel that holds the map.
     */
    private JPanel mapCard;

    /**
     * The card/JPanel that holds the graph.
     */
    private JPanel graphCard;

    /**
     * The JPanel that holds the different cards.
     */
    private JPanel cardHolder;

    /**
     * An Athlete Object.
     */
    private Athlete athlete;

    /**
     * Location in a String.
     */
    private String location;

    /**
     * The layout of the different cards.
     */
    private CardLayout layout;

    /**
     * Decides the amount of zoom on the map.
     */
    private String zoom;

    /**
     * The tableModel used for the tabels.
     */
    private DefaultTableModel dm;
    private DefaultTableModel dm2;

    /**
     * An Athlete Object.
     */
    private Athlete tableSetup;

    /**
     * Constructor that creates an athletePage based on an athleteID.
     * @param athleteID athleteID of the athlete being displayed in the panel.
     */
    public AthletePageAnalyst(int athleteID){
        athlete = new Athlete(athleteID);
        this.zoom = "12";

        readingsList.setDefaultEditor(Object.class, null);
        locationTable.setDefaultEditor(Object.class, null);

        //Setting all the text information to information about the chosen athlete
        name.setText(athlete.getFirstname() + " " + athlete.getLastname());
        telephone.setText(athlete.getTelephone());
        sport.setText(athlete.getSport());
        nationality.setText(athlete.getNationality());
        graphMapButton.setText("Show graph");
        locationLabel.hide();

        //Setting padding around the frame
        Border padding = BorderFactory.createEmptyBorder(0, 100, 50, 100);
        getMainPanel().setBorder(padding);


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
        graphCard = new HaemoglobinChart(600,600,athleteID).makeJPanel();
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
        editButton.addActionListener(actionListener);
        zoominButton.addActionListener(actionListener);
        zoomoutButton.addActionListener(actionListener);
        graphMapButton.addActionListener(actionListener);


        this.tableSetup = new Athlete(athlete.getAthleteID());

        //Setting up the location- and readings-tables.
        locationListSetup();
        readingListSetup();


        //adds a listSelectionListener to the readingList
        locationTable.getSelectionModel().addListSelectionListener(createListSelectionListener(locationTable));
        //readingsList.hide();
        //scrollBar.hide();

    }

    /**
     * Adds rows to the readings table.
     */
    private void populateRowsReadings() {
        String[][] results = tableSetup.getReadingsUser(null);
        for (int i = 0; i < results.length; i++) {
            dm.addRow(results[i]);
        }
    }

    /**
     * Adds rows to the locations table.
     */
    private void populateRowsLocations() {
        String[][] results = tableSetup.getLocationsArray();
        for (int i = 0; i < results.length; i++) {
            dm2.addRow(results[i]);
            System.out.println(results[i][0] + results[i][1] + results[i][2] + "\n" + results[i]);
        }
    }

    /**
     * Creates columns for the location list.
     */
    private void locationListSetup(){
        dm2 = (DefaultTableModel) locationTable.getModel();
        dm2.addColumn("From date");
        dm2.addColumn("To date");
        dm2.addColumn("Location");

        populateRowsLocations();

    }

    /**
     * Creates columns for the reading list.
     */
    private void readingListSetup(){

        dm = (DefaultTableModel) readingsList.getModel();
        dm.addColumn("Date");
        dm.addColumn("Reading");

        populateRowsReadings();
    }

    /**
     * The selectionListener for the locationTable.
     * @param resultsTable table of lcations.
     * @return ListSelectionListener.
     */
    ListSelectionListener createListSelectionListener(JTable resultsTable) {
        ListSelectionListener listener = new ListSelectionListener() {

            /**
             * Checks if a value is changed.
             * @param event event
             */
            public void valueChanged(ListSelectionEvent event) {
                //Keeps it from firing twice (while value is adjusting as well as when it is done)
                if (!event.getValueIsAdjusting()) {//This line prevents double events

                    int row = resultsTable.getSelectedRow();
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


    private class ButtonListener implements ActionListener {

        /**
         * Listener that checks for actions on the different buttons.
         * @param actionEvent actionEvent.
         */
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
                frame.setContentPane(new EditAthlete(athlete.getAthleteID(), frame).getMainPanel()); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
                frame.setLocation(350, 50); //Improvised way to center the window? -Toni
                frame.pack();  //Creates a window out of all the components
                frame.setLocationRelativeTo(null);
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

    /**
     * Returns the mainPanel/rootPanel.
     * @return JPanel
     */
    public JPanel getMainPanel () {
        return rootPanel;
    }

}
