package GUI.collector;

import GUI.BaseWindow;
import GUI.athlete.AddBloodSample;
import backend.Athlete;
//import backend.AthleteGlobinDate;
import backend.GoogleMaps;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author Nora Othilie
 */

public class AthletePageCollector extends BaseWindow {

    /**
     * The button the user presses to display a location on the map after insterting date.
     */
    private JButton findLocationButton;

    /**
     * Button used if the user wants to add a blood sample.
     */
    private JButton newBloodSample;

    /**
     * Button used if the user wants to see all the haemoglobin readings of an athlete.
     */
    private JButton allReadings;

    /**
     * Button used if the user wants to see all the locations of an athlete.
     */
    private JButton allLocations;

    /**
     * Button used for zooming out of the map.
     */
    private JButton zoomoutButton;

    /**
     * Button used for zooming in on the map.
     */
    private JButton zoominButton;

    /**
     * Button used if the user want to edit the selected athlete's information.
     */
    private JButton editButton;

    /**
     * Field where the user inputs a date for finding the location of the selected athlete.
     */
    private JTextField dateField;

    /**
     * The rootPanel/mainPanel where everything is contained.
     */
    private JPanel rootPanel;

    /**
     * The JPanel where the map is contained.
     */
    private JPanel mapPanel;

    /**
     * The JPanel where the info of the athlete is contained.
     */
    private JPanel athleteInfoPanel;

    /**
     * Label displaying the name of the selected athlete.
     */
    private JLabel Name;

    /**
     * Label displaying the selected athlete's telephone number.
     */
    private JLabel Telephone;

    /**
     * Label displaying the sport of the selected athlete.
     */
    private JLabel Sport;

    /**
     * Label displaying the nationality of the selected athlete.
     */
    private JLabel Nationality;

    /**
     * Label displaying the current location of the selected athlete.
     */
    private JLabel CurrentLocation;

    /**
     * Location text.
     */
    private JLabel locationText;

    /**
     * ScrollPane containing for JTable.
     */
    private JScrollPane scrollPane;

    /**
     * ScrollPane for JTable.
     */
    private JScrollPane scrollPane2;

    /**
     * JTable for the locations of the selected athlete.
     */
    private JTable locationList;

    /**
     * JTable displaying the readings that the user logged inn has submitted to the selected athlete.
     */
    private JTable readingsList;

    /**
     * Button used to refresh the page.
     */
    private JButton refreshButton;

    /**
     * The JPanel/card that contains the map.
     */
    private JPanel mapCard;

    /**
     * The athlete selected by the user.
     */
    private Athlete athlete;

    /**
     * A location selected by the user. Set to the athlete's current location by default.
     */
    private String location;

    /**
     * The amount of zoom on the map. Set to 12 by default.
     */
    private String zoom;

    /**
     * TableModel for a JTable.
     */
    private DefaultTableModel dm;

    /**
     * TableModel for a JTable.
     */
    private DefaultTableModel dm2;

    /**
     * An Athlete Object.
     */
    private Athlete tableSetup;

    /**
     * The user that is logged in and has entered the athlete page.
     */
    private String entry_creator;

    /**
     * Constructs the AthletePageCollector panel based on athleteID and the user that is logged in.
     * @param athleteID athleteID of the selected athlete.
     * @param entry_creator username of the user logged in.
     */
    public AthletePageCollector(int athleteID, String entry_creator){
        this.entry_creator = entry_creator;
        athlete = new Athlete(athleteID);
        this.zoom = "12";

        //Setting padding around the frame
        Border padding = BorderFactory.createEmptyBorder(0, 100, 50, 100);
        getMainPanel().setBorder(padding);

        readingsList.setDefaultEditor(Object.class, null);
        locationList.setDefaultEditor(Object.class, null);


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
        zoominButton.addActionListener(actionListener);
        zoomoutButton.addActionListener(actionListener);
        refreshButton.addActionListener(actionListener);

        readingListSetup();
        locationListSetup();;

        //adds a listSelectionListener to the readingList
        locationList.getSelectionModel().addListSelectionListener(createListSelectionListener2(locationList));

        //adds a listSelectionListener to the readingList
        readingsList.getSelectionModel().addListSelectionListener(createListSelectionListener(readingsList));



    }

    /**
     * Adds rows to the readings table.
     */
    private void populateRowsReadings() {
        String[][] results = tableSetup.getReadingsUser(entry_creator);
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
        dm2 = (DefaultTableModel) locationList.getModel();
        dm2.addColumn("From date");
        dm2.addColumn("To date");
        dm2.addColumn("Location");


        this.tableSetup = new Athlete(athlete.getAthleteID());
        populateRowsLocations();

    }

    /**
     * Creates columns for the reading list.
     */
    private void readingListSetup(){

        dm = (DefaultTableModel) readingsList.getModel();
        dm.addColumn("Date");
        dm.addColumn("Reading");

        this.tableSetup = new Athlete(athlete.getAthleteID());
        populateRowsReadings();
    }


    /**
     * Adds a listener to the readings table.
     * @param resultsTable the table that the listener is added to.
     * @return ListSelectionListener
     */
    ListSelectionListener createListSelectionListener(JTable resultsTable) {

        ListSelectionListener listener = new ListSelectionListener() {

            /**
             * Checks to see if a value has changed.
             * @param event event
             */
            public void valueChanged(ListSelectionEvent event) {
                //Keeps it from firing twice (while value is adjusting as well as when it is done)
                if (!event.getValueIsAdjusting()) {//This line prevents double events

                    int row = resultsTable.getSelectedRow();
                    //int athleteID = Integer.parseInt((String)resultsTable.getValueAt(row, 3));

                    if(row == -1)
                        return;
                    double reading = Double.parseDouble((String)resultsTable.getValueAt(row, 1));

                    try {
                        /*SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                        Date parsed = format.parse((String) resultsTable.getValueAt(row, 1));
                        java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());*/

                        //java.sql.Date sqlDate = java.sql.Date.valueOf( todayLocalDate );

                        String dateString = (String) resultsTable.getValueAt(row, 0);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  //format of the input date: dateString
                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");   //the format we want after parsing
                        Date convertedCurrentDate = sdf.parse(dateString);
                        String date = sdf2.format(convertedCurrentDate);

                        JFrame frame = new JFrame();
                        EditDeleteReadings editDeleteReadings = new EditDeleteReadings(reading, date, athlete.getAthleteID(), frame);
                        //EditDeleteBloodsample editDeleteBloodsample = new EditDeleteBloodsample();
                        frame.setContentPane(editDeleteReadings.getMainPanel());
                        frame.setLocation(350, 50); //Improvised way to center the window? -Toni
                        frame.pack();  //Creates a window out of all the components
                        frame.setVisible(true);   //Setting the window visible

                    }catch(Exception e){
                        System.out.println("EDIT/DELETE READING:" + e.toString());
                    }

                    /*readingsList.setColumnSelectionAllowed(false);
                    readingsList.setRowSelectionAllowed(false);
                    readingsList.setRowSelectionInterval(row, row);*/


                }
            }
        };
        return listener;
    }

    /**
     * Adds a listener to the locations table.
     * @param resultsTable The table that the listener is added to.
     * @return ListSelectionListener
     */
    ListSelectionListener createListSelectionListener2(JTable resultsTable) {
        ListSelectionListener listener2 = new ListSelectionListener() {

            /**
             * Checks to see if a values has changed.
             * @param event event
             */
            public void valueChanged(ListSelectionEvent event) {
                //Keeps it from firing twice (while value is adjusting as well as when it is done)
                if (!event.getValueIsAdjusting()) {//This line prevents double events

                    int row = resultsTable.getSelectedRow();
                    //int athleteID = Integer.parseInt((String)resultsTable.getValueAt(row, 3));
                    location = (String)resultsTable.getValueAt(row, 2);

                    mapPanel.removeAll();
                    mapPanel.updateUI();
                    mapCard = new GoogleMaps().createMap(location, zoom);
                    mapPanel.add(mapCard);
                    mapPanel.updateUI();
                    locationText.setText(location);

                    System.out.println(location);

                }
            }
        };
        return listener2;
    }


    private class ButtonListener implements ActionListener {

        /**
         * Checks to see if any of the buttons in the panel is being pressed.
         * @param actionEvent event
         */
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
                    showMessageDialog(null, locationText.getText(), "NB", JOptionPane.INFORMATION_MESSAGE);


                }
            }

            if(buttonPressed.equals("New blood sample")) {

                BaseWindow frame = new BaseWindowCollector("Collector");
                AddBloodSample addBloodSample = new AddBloodSample(athlete.getAthleteID(), frame, entry_creator);
                frame.setContentPane(addBloodSample.getMainPanel());
                frame.setLocation(350, 50); //Improvised way to center the window? -Toni
                frame.pack();  //Creates a window out of all the components
                frame.setVisible(true);   //Setting the window visible

                    if (addBloodSample.getIsClosed()) {
                        //clearRows();
                        readingListSetup();
                        scrollPane.updateUI();
                    }
                    System.out.println("...");



            }

            if(buttonPressed.equals("Show haemoglobin readings")){

                /*scrollPane.show();
                scrollPane2.hide();
                allReadings.setText("Hide haemoglobin readings");*/


            }

            if(buttonPressed.equals("Hide haemoglobin readings")){

                scrollPane.hide();
                allReadings.setText("Show haemoglobin readings");


            }

            if(buttonPressed.equals("All future locations")){

                /*scrollPane2.show();
                scrollPane.hide();*/

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

            if(buttonPressed.equals("Refresh")) {
                DefaultTableModel model = (DefaultTableModel) readingsList.getModel();
                if(readingsList.getSelectedRow() != -1) {
                    if (readingsList.getRowCount() == 0) {
                        showMessageDialog(null, "Table is empty");
                    } else {
                        showMessageDialog(null, "Refreshing");
                    }
                }else{
                    model.removeRow(readingsList.getSelectedRow());
                }

                model.getRowCount();
                for(int i=0; i<model.getRowCount(); i++){
                    model.removeRow(i);
                }
                populateRowsReadings();
                readingsList.updateUI();

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
