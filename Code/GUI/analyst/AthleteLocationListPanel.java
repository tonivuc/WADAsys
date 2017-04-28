package GUI.analyst;

/**
 *
 * @author Trym Vegard Gjelseth-Borgen
 */

import backend.AthleteLocation;
import backend.AthleteLocationsList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class made for handling GUI associated with the athlete location table.
 */
public class AthleteLocationListPanel extends JPanel {

    /**
     * The mainPanel/rootPanel where everything is contained.
     */
    private JPanel mainPanel;

    /**
     * The table that contains the locations.
     */
    private JTable locationsTable;

    /**
     * JSrollPane that makes the locations table scrollable.
     */
    private JScrollPane tableScrollPane;

    /**
     * The tableModel for the locationsTable.
     */
    private DefaultTableModel model;

    /**
     * The names of the columns.
     */
    private final Object[] columnNames = {"Locations", "Number Of People"};

    /**
     * The GUI that shows the athletes locations at todays date.
     */
    public AthleteLocationListPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        model = new DefaultTableModel(new Object[0][0], columnNames);

        ArrayList<AthleteLocation> locationsList = new AthleteLocationsList().getAthleteLocations(LocalDate.now());

        for (int i = 0; i < locationsList.size(); i++) {
            Object[] o = new Object[2];
            o[0] = locationsList.get(i).getLocation();
            o[1] = locationsList.get(i).getNumberOfPeople();
            model.addRow(o);
        }

        locationsTable.setModel(model);

        tableScrollPane = new JScrollPane(locationsTable);

        mainPanel.add(tableScrollPane, BorderLayout.NORTH);

    }

    /**
     * Returns the mainPanel/rootPanel.
     * @return JPanel
     */
    public JPanel getMainPanel () {
        return mainPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Athlete Locations List");
        frame.add(new AthleteLocationListPanel().getMainPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 300));
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
