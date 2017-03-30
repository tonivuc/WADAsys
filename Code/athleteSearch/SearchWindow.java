package athleteSearch;

import login.BaseWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.KeyEvent;

/**
 * Created by Toni on 29.03.2017.
 */
public class SearchWindow extends BaseWindow{ //Should actually extend BaseWindow

    private JTable resultsTable;
    private JTextField searchField;
    private JLabel headerLabel;
    DefaultTableModel dm;

    public SearchWindow() {
        createColumns();
    }

    //Creates the columns used in the GUI
    private void createColumns() {
        dm = (DefaultTableModel) resultsTable.getModel();

        dm.addColumn("Name");
        dm.addColumn("Nationality");
        dm.addColumn("Sport");
    }

    //Add row data
    private void populateRow(String name, String nationality, String sport) {

        String[] rowData = {name,nationality,sport};

        dm.addRow(rowData);


    }

    //Filter data
    private void filter(String query) {
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        resultsTable.setRowSorter(tr);

        tr.setRowFilter(RowFilter.regexFilter(query));
    }

    private void searchFieldKeyReleased(KeyEvent evt) {
        String query = searchField.getText();
        filter(query);
    }

    public static void main(String[]args) {
        SearchWindow aWindow = new SearchWindow();
        aWindow.setVisible(true);
    }
}
