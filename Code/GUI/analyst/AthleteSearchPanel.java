package GUI.analyst;

import GUI.BaseWindow;
import backend.SearchHelp;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Toni on 29.03.2017.
 */
public class AthleteSearchPanel implements KeyListener { //Should actually extend BaseWindow


    //These are connected to AthleteSearchPanel.form
    private JTable resultsTable;
    private JScrollPane scrollPane; //Collumn names don't show unless the JTable(resultsTable) is inside this
    private JTextField searchField;
    private JLabel headerLabel;
    private JPanel mainPanel;

    //Except for these two babies ;)
    DefaultTableModel dm;

    private boolean athleteIsChosen;
    private int athleteIDChosen;

    private SearchHelp searchConnection;

    //Constructor
    public AthleteSearchPanel() {

        createColumns();
        searchField.addKeyListener(this);
        this.searchConnection = new SearchHelp();
        populateRows();
        // WARNING: Line below causes errors if attempting to search while row is selected. Otherwise works fine.
        // Searching while row is selected should never happen anyway.
        // Makes it so that you cannot edit the displayed information in the JTable.
        resultsTable.setDefaultEditor(Object.class, null);

        //Adds a listener to the table
        resultsTable.getSelectionModel().addListSelectionListener(createListSelectionListener());

        //Kinda irrelevant once we make it so that when you click on a name this entire window is replaced.
        //searchField.addFocusListener(this);

    }

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

    //Creates the columns used in the GUI
    private void createColumns() {
        dm = (DefaultTableModel) resultsTable.getModel();

        dm.addColumn("Name");
        dm.addColumn("Nationality");
        dm.addColumn("Sport");
        dm.addColumn("AthleteID");

        //TableColumn hiddenColumn = resultsTable.getColumnModel().getColumn(3);
        //resultsTable.getColumnModel().removeColumn(hiddenColumn);
    }

    public int getAthleteIDChosen() {
        return athleteIDChosen;
    }

    public boolean athleteIsChosen() {
        return athleteIsChosen;
    }

    //Creates placeholder rows used for testing
    /*
    private void createRows() {
        populateRow("Arne Sande","Norway","Nynorsk");
        populateRow("Azjerz Hakan","Azerbadjan","Chess");
        populateRow("Baba Jaan","Iran","Pipesmoking");
        populateRow("Petter Northug","Norway","Racing");
    }
    */

    //Add one row of data to DefaultTableModel
    private void populateRow(String name, String nationality, String sport) {
        String[] rowData = {name, nationality, sport};
        dm.addRow(rowData);
    }

    //Main function used to populate rows from the SQL server
    private void populateRows() {
        String[][] results = searchConnection.getAthletes();
        for (int i = 0; i < results.length; i++) {
            dm.addRow(results[i]);
        }
    }

    //Filter data being shown
    private void filter(String query) {
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        resultsTable.setRowSorter(tr);

        tr.setRowFilter(RowFilter.regexFilter(query));
    }



    ///////////////////////////
    //IMPLEMENTATIONS BELOW
    ///////////////////////////

    //Methods needed to implement KeyListener. We are using keyReleased.
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

    //Needed to implement FocusEvent on the searchField
    //The point here is to remove the selection from the JTable when you click on the Search bar.
    //The reason for this is to avoid some NullPointerExceptions during searching.
    //Does not work as expected at the moment (Only clears one collumn) and is thus disabled.
    public void focusGained(FocusEvent e) {
        //resultsTable.clearSelection();
        //System.out.println("Focus gained"+ e);
    }

    public void focusLost(FocusEvent e) {
        //System.out.println("Focus lost"+ e);
    }

    public JPanel getMainPanel() {
        return mainPanel;
        //To use, use:
        //newPanel.setContentPane(new AthleteSearchPanel().getMainPanel());
    }




    //Main function used for testing.
    public static void main(String[]args) {
        BaseWindow aWindow = new BaseWindow("Athlete search");
        aWindow.setContentPane(new AthleteSearchPanel().getMainPanel());
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
                AthleteSearchPanel vindu = new AthleteSearchPanel();
                vindu.pack();
                vindu.setVisible(true);
            }
        });
        */

    }


}
