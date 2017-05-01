package GUI.admin;

/**
 *
 * @author Toni Vucic
 */

import backend.SearchHelp;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> Used to find a specific user in the database and open his/her page.
 * The class creates a type of JPanel that includes among other things a JTextField
 * to type who you are searching for into, a JTable to display the users and some JCheckBoxes to choose
 * what you want to search for. </p>
 */
public class UserSearchPanel extends JPanel {


    /**
     * The mainPanel of the userSearchPanel.
     */
    private JPanel mainPanel;

    /**
     * The JTable that contains the result of the search.
     */
    private JTable resultsTable;

    /**
     * JSrollPane that contains the JTable.
     * Column usernames don't show unless the JTable(resultsTable) is inside this.
     */
    private JScrollPane scrollPane;

    /**
     * The field that the user enters text when he/she wants to search for something.
     */
    private JTextField searchField;

    /**
     * Label that contains the header.
     */
    private JLabel headerLabel;

    /**
     * CheckBoxPanel that contains different checkboxes for want you want to
     * include in your search.
     */
    private JPanel checkBoxPanel;

    /**
     * JCheckBox for username.
     */
    private JCheckBox usernameCheckBox;

    /**
     * JCheckBox for name
     */
    private JCheckBox nameCheckBox;

    /**
     * JCheckBox for telephone number.
     */
    private JCheckBox telephoneCheckBox;

    /**
     * The default table model.
     */
    private DefaultTableModel dm;

    /**
     * A new SearchHelp.
     */
    private SearchHelp searchConnection;

    /**
     * Constructor. Creates an UserSearchPanel. It is not a JPanel.
     * To use it you must get the JPanel by calling getMainPanel();
     */
    public UserSearchPanel() {
        setLayout(new BorderLayout());
        add(getMainPanel());
        createColumns();
        this.searchConnection = new SearchHelp();
        populateRows();
        resultsTable.setDefaultEditor(Object.class, null);

        // Not in use because the listener has been moved to the parent JFrame.
        //resultsTable.getSelectionModel().addListSelectionListener(createListSelectionListener());

        //Add listeners
        searchField.addKeyListener(keyListener);
        resultsTable.addFocusListener(focusListener);
        usernameCheckBox.addActionListener(actionListener);
        nameCheckBox.addActionListener(actionListener);
        telephoneCheckBox.addActionListener(actionListener);
    }

    /**
     * Returns the mainPanel/rootPanel.
     * @return JPanel.
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Returns the resultsTable.
     * @return JTable.
     */
    public JTable getJTable() {
        return resultsTable;
    }

    /**
     * Creates the four collumns in the JTable.
     * username, name, telephone and AthleteID.
     * Adds them to the DefaultTableModel, which it gets from the resultsTable by using getModel()
     * and casting to DefaultTableModel. Assigns this model to 'dm'.
     */
    private void createColumns() {
        dm = (DefaultTableModel) resultsTable.getModel();

        dm.addColumn("Username");
        dm.addColumn("Name");
        dm.addColumn("Telephone");
    }

    /**
     * Add one row of data to the DefaultTableModel.
     * @param username first and last username of the athlete
     * @param name username of the country the athlete is representing
     * @param telephone main telephone the athlete is participating in
     */
    private void populateRow(String username, String name, String telephone) {
        String[] rowData = {username, name, telephone};
        dm.addRow(rowData);
    }

    /**
     * Main function used to populate rows from the SQL server
     * Gets a String[][] of results from the searchConnection class that calls .getAthletes().
     * Then adds them to the DefaultTableModel using a for-loop.
     */
    private void populateRows() {
        String[][] results = searchConnection.getUsers();
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
     * RowFilter.orFilter().
     * @param query The text/query to filter by
     * @return The new combined RowFilter
     */
    private RowFilter updateRegexFilter(String query) {

        List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(3);

        if (usernameCheckBox.isSelected()) {
            filters.add(RowFilter.regexFilter("(?i)"+query,0));
        }
        if (nameCheckBox.isSelected()) {
            filters.add(RowFilter.regexFilter("(?i)"+query,1));
        }
        if (telephoneCheckBox.isSelected()) {
            filters.add(RowFilter.regexFilter("(?i)"+query,2));
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



    private FocusListener focusListener = new FocusListener() {

        /**
         * Not in use
         * @param e
         */
        public void focusGained(FocusEvent e) {}

        /**
         * The point here is to remove the selection from the JTable when you click anywhere else..
         * The reason for this is to avoid some NullPointerExceptions during searching.
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

}
