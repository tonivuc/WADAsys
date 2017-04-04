package GUI.analyst;

import backend.Athlete;
import backend.Watchlist;
import backend.WatchlistTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tvg-b on 30.03.2017.
 */

public class WatchlistPanel extends JPanel {

    private JPanel mainPanel;
    private JTable athleteTable;
    private JScrollPane tableScrollPane;
    private LocalDate date;
    private Watchlist watchlist;
    private List listAthletes;

    public WatchlistPanel(LocalDate date) {

        this.date = date;
        this.watchlist = new Watchlist();


        this.listAthletes = watchlist.getSuspiciousAthletes(date);
        TableModel tableModel = new WatchlistTableModel(listAthletes);
        athleteTable = new JTable(tableModel);

        athleteTable.setAutoCreateRowSorter(true);

        add(new JScrollPane(athleteTable), BorderLayout.CENTER);

        /*
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        */
    }

    public List<Athlete> createListAthlete() {
        List<Athlete> listAthlete = new ArrayList<>();

        return listAthlete;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}