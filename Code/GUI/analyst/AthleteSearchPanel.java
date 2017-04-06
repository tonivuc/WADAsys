package GUI.analyst;

import backend.SearchHelp;

import javax.swing.*;
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
public class AthleteSearchPanel extends JPanel implements KeyListener { //Should actually extend BaseWindow


    //These are connected to AthleteSearchPanel.formow.form
    private JTable resultsTable;
    private JTextField searchField;
    private JLabel headerLabel;
    private JPanel mainPanel;
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
        resultsTable.addFocusListener(new ListListener());
    }

    public class ListListener implements FocusListener{
        public ListListener(){


        }
        public void focusGained(FocusEvent e) {
            //displayMessage("Focus gained", e);
        }

        public void focusLost(FocusEvent e) {
           // displayMessage("Focus lost", e);
        }

    }



    public int getAthleteIDChosen() {
        return athleteIDChosen;
    }

    public boolean athleteIsChosen() {
        return athleteIsChosen;
    }




    //Creates the columns used in the GUI
    private void createColumns() {
        dm = (DefaultTableModel) resultsTable.getModel();

        dm.addColumn("Name");
        dm.addColumn("Nationality");
        dm.addColumn("Sport");
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

    //Add one row of dato to DefaultTableModel
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

    public JPanel getMainPanel() {
        return mainPanel;
        //To use, use:
        //newPanel.setContentPane(new AthleteSearchPanel().getMainPanel());
    }

    //Main function used for testing.

    public static void main(String[] args) {

        JFrame frame = new JFrame("Base Window"); //Creating JFrame
        frame.setContentPane(new AthleteSearchPanel().getMainPanel()); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible

        SearchHelp connectionz = new SearchHelp();

        for (int i = 0; i < 8; i++) {
            System.out.println(connectionz.getAthletes()[i][0]);
            System.out.println(connectionz.getAthletes()[i][1]);
            System.out.println(connectionz.getAthletes()[i][2]);

        }
    }
}

        /* If we need a seperate thread we can use this
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AthleteSearchPanel vindu = new AthleteSearchPanel();
                vindu.pack();
                vindu.setVisible(true);
            }
        });


    }

}*/
