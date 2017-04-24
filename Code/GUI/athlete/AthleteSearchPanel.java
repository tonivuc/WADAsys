package GUI.athlete;

import GUI.BaseWindow;
import backend.SearchHelp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toni on 29.03.2017.
 * Write new UserSearchPanel() to use
 */
public class AthleteSearchPanel extends JPanel {


    //These are connected to UserSearchPanel.form
    private JPanel mainPanel;
    private JTable resultsTable;
    private JScrollPane scrollPane; //Collumn names don't show unless the JTable(resultsTable) is inside this
    private JTextField searchField;
    private JLabel headerLabel;

    private JPanel checkBoxPanel;
    private JCheckBox nameCheckBox;
    private JCheckBox countryCheckBox;
    private JCheckBox sportCheckBox;
    private JCheckBox athleteIDCheckBox;

    private RowFilter currentFilter;

    //Except for this baby
    DefaultTableModel dm;

    private boolean athleteIsChosen;
    private static int athleteIDChosen;

    private SearchHelp searchConnection;

    /**
     * Constructor. Creates an UserSearchPanel. It is not a JPanel.
     * To use it you must get the JPanel by calling getMainPanel();
     */
    public AthleteSearchPanel() {

        setLayout(new BorderLayout()); //For resizing to work properly this must be added
        add(getMainPanel(), BorderLayout.CENTER);

        createColumns();
        this.searchConnection = new SearchHelp();
        populateRows();
        resultsTable.setDefaultEditor(Object.class, null);

        // Not in use because the listener has been moved to the parent JFrame.
        //resultsTable.getSelectionModel().addListSelectionListener(createListSelectionListener());

        //Add listeners
        searchField.addKeyListener(keyListener);
        resultsTable.addFocusListener(focusListener);
        nameCheckBox.addActionListener(actionListener);
        countryCheckBox.addActionListener(actionListener);
        sportCheckBox.addActionListener(actionListener);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTable getJTable() {
        return resultsTable;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    /**
     * Listens for events fired when someone clicks on a JList.
     * Inside it checks if .getValueIsAdjusting() to avoid doing the stuff inside the method twice.
     * Inside the valueChanged method it currently prints out the value of the selected row.
     * In the future it will open an Athlete page.
     *
     * @return
     */
    /* Not in use because the listener has been moved to the parent JFrame.
    ListSelectionListener createListSelectionListener() {
        ListSelectionListener listener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                //Keeps it from firing twice (while value is adjusting as well as when it is done)
                if (!event.getValueIsAdjusting()) {//This line prevents double events

                    int row = resultsTable.getSelectedRow();

                    System.out.println(resultsTable.getValueAt(row, 3));
                    // System.out.println(resultsTable.getValueAt(resultsTable.getSelectedRow(), 3));
                }
            }
        };
        return listener;
    }
    */

    /**
     * Creates the four collumns in the JTable.
     * Name, Nationality, Sport and AthleteID.
     * Adds them to the DefaultTableModel, which it gets from the resultsTable by using getModel()
     * and casting to DefaultTableModel. Assigns this model to 'dm'.
     */
    private void createColumns() {
        dm = (DefaultTableModel) resultsTable.getModel();

        dm.addColumn("Name");
        dm.addColumn("Nationality");
        dm.addColumn("Sport");
        dm.addColumn("AthleteID");
    }

    /**
     * Add one row of data to the DefaultTableModel.
     *
     * @param name first and last name of the athlete
     * @param nationality name of the country the athlete is representing
     * @param sport main sport the athlete is participating in
     * @param athleteID ID of the athlete, stored in the WADA-sys database
     */
    private void populateRow(String name, String nationality, String sport, String athleteID) {
        String[] rowData = {name, nationality, sport, athleteID};
        dm.addRow(rowData);
    }

    /**
     * Main function used to populate rows from the SQL server
     * Gets a String[][] of results from the searchConnection class that calls .getAthletes().
     * Then adds them to the DefaultTableModel using a for-loop.
     */
    private void populateRows() {
        String[][] results = searchConnection.getAthletes();
        for (int i = 0; i < results.length; i++) {
            dm.addRow(results[i]);
        }
    }

    /**
     * Takes a String, here called 'query' as a parameter. Then sorts the DefaultTableModel
     * so that only rows containing that word are shown. Also calls on the updateRegexFilter method
     * to ensure it respects the search choices of the client when filtering.
     * @param query String. Usually a word. Typed into the TextField.
     */
    private void filter(String query) {
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        resultsTable.setRowSorter(tr);
        tr.setRowFilter(updateRegexFilter(query));
    }

    /**
     * Returns a new RowFilter based on the choices of the user.
     * Checks the four JCheckBoxes added to the panel.
     * If the JTextBox is checked it creates a RowFilter.regexFilter based on the query param
     * and adds it to a List of RowFilters.
     * In the end the list is combined into one RowFilter using
     * RowFilter.orFilter()
     *
     * @param query The text/query to filter by
     * @return The new combined RowFilter
     */
    private RowFilter updateRegexFilter(String query) {

        List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(4);


        if (nameCheckBox.isSelected()) {
            filters.add(RowFilter.regexFilter("(?i)"+query,0));
        }
        if (countryCheckBox.isSelected()) {
            filters.add(RowFilter.regexFilter("(?i)"+query,1));
        }
        if (sportCheckBox.isSelected()) {
            filters.add(RowFilter.regexFilter("(?i)"+query,2));
        }
        if (athleteIDCheckBox.isSelected()) {
            filters.add(RowFilter.regexFilter("(?i)"+query,3));
        }

        return RowFilter.orFilter(filters);
    }

    ///////////////////////////
    //LISTENERS BELOW
    ///////////////////////////


    private KeyListener keyListener = new KeyListener() {
        /**
         * When key is released in the search JTextField, the text is fetched,
         * trimmed and then sent as a parameter in the filter() method.
         * @param evt
         */
        @Override
        public void keyReleased(KeyEvent evt) {
            String query = searchField.getText();
            query.trim();
            filter(query);
        }

        /**
         * Handle the key typed event from the text field.
         */
        @Override
        public void keyTyped(KeyEvent e) {
            //..
        }

        /**
         * Handle the key pressed event from the text field.
         */
        @Override
        public void keyPressed(KeyEvent e) {
            //..
        }
    };


    //Needed to implement FocusEvent on the searchField
    //
    private FocusListener focusListener = new FocusListener() {
        /**
         * The point here is to remove the selection from the JTable when you click on the Search bar.
         * The reason for this is to avoid some NullPointerExceptions during searching.
         * @param e
         */
        public void focusGained(FocusEvent e) {
        }

        /**
         * Not in use
         * @param e
         */
        public void focusLost(FocusEvent e) {
            resultsTable.clearSelection();
        }
    };


    /**
     * ActionListener is used on the JCheckBoxes
     */
    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            filter(searchField.getText());
        }
    };


    //Main function used for testing.
    public static void main(String[]args) {
        BaseWindow aWindow = new BaseWindow("Athlete search");
        //aWindow.setContentPane(new UserSearchPanel().getMainPanel());
        aWindow.add(new AthleteSearchPanel());
        aWindow.pack();
        aWindow.setVisible(true);

        /* Code below tests the database connection via the SearchHelp class
        SearchHelp connectionz = new SearchHelp();

        for (int i = 0; i < 8; i++) {
            System.out.println(connectionz.getAthletes()[i][0]);
            System.out.println(connectionz.getAthletes()[i][1]);
            System.out.println(connectionz.getAthletes()[i][2]);

        }
        */


        /* If we need a seperate thread we can use this
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UserSearchPanel vindu = new UserSearchPanel();
                vindu.pack();
                vindu.setVisible(true);
            }
        });
        */

    }


}
