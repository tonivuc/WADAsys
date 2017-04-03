package watchlist;

import GUI.collector.BaseWindowCollector;
import backend.Athlete;
import backend.Watchlist;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by tvg-b on 30.03.2017.
 */

<<<<<<< Updated upstream:Code/watchlist/WatchlistWindow.java
public class WatchlistWindow extends JFrame {
=======
public class WatchlistPanel extends BaseWindowCollector {
>>>>>>> Stashed changes:Code/GUI/analyst/WatchlistPanel.java

    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JPanel tablePanel;
    private JPanel sortPanel;
    private JButton athleteSearchButton;
    private JButton watchListButton;
    private JButton placeHolderButton;
    private JButton logOutButton;
    private JComboBox SortBy;
    private JTable athleteTable;
    private JScrollPane tableScrollPane;
    private LocalDate date;
<<<<<<< Updated upstream:Code/watchlist/WatchlistWindow.java

    public WatchlistWindow (LocalDate date) {
=======
    private Watchlist watchlist;
    private Athlete[] athletes;


    public WatchlistPanel(LocalDate date) {
>>>>>>> Stashed changes:Code/GUI/analyst/WatchlistPanel.java

        this.date = date;
        this.watchlist = new Watchlist();
        SortBy.addItem("Sport");
        SortBy.addItem("Nationality");
        SortBy.addItem("Haemoglobin level");

<<<<<<< Updated upstream:Code/watchlist/WatchlistWindow.java
        Watchlist watchlist = new Watchlist();
        ArrayList<Athlete> athletes = new ArrayList<Athlete>();

        athletes = watchlist.getSuspiciousAthletes(LocalDate.of(2017, 04, 10));

=======
        this.athletes = getAthletes(date);
>>>>>>> Stashed changes:Code/GUI/analyst/WatchlistPanel.java

        tablePanel.setLayout(new BorderLayout());

        athleteTable = new JTable(new DefaultTableModel(new Object[]{"First name", "Last name", "Sport", "Nationality", "telephone", "Haemoglobin level"}, 0));

        DefaultTableModel model = (DefaultTableModel) athleteTable.getModel();

        for (int i = 0; i < athletes.length; i++) {
            model.addRow(new Object[]{athletes[i].getFirstname(),
                                      athletes[i].getLastname(),
                                      athletes[i].getSport(),
                                      athletes[i].getNationality(),
                                      athletes[i].getTelephone(),
                                      athletes[i].getGlobinDeviation(date) + " %"});
        }


<<<<<<< Updated upstream:Code/watchlist/WatchlistWindow.java
        Comparator<Athlete> comGlobin = (o1, o2) -> {

            if(o1.getTelephone() > o2.getTelephone()) {
                return 1;
            }
=======
        tableScrollPane = new JScrollPane(athleteTable);
>>>>>>> Stashed changes:Code/GUI/analyst/WatchlistPanel.java

            return -1;

<<<<<<< Updated upstream:Code/watchlist/WatchlistWindow.java
        };

        Collections.sort(athletes, comGlobin);
=======
    }

    public Athlete[] getAthletes (LocalDate date) {
        Athlete[] athletes;
        ArrayList<Athlete> athletesList = new ArrayList<Athlete>();
        athletesList = watchlist.getSuspiciousAthletes(date);
        athletes = new Athlete[athletesList.size()];
>>>>>>> Stashed changes:Code/GUI/analyst/WatchlistPanel.java

        for (int i = 0; i < athletes.length; i++) {
            athletes[i] = athletesList.get(i);
        }

        if (athletes[0] != null) return athletes;

<<<<<<< Updated upstream:Code/watchlist/WatchlistWindow.java
        tableScrollPane = new JScrollPane(athleteTable);

        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

=======
        return null;
    }
>>>>>>> Stashed changes:Code/GUI/analyst/WatchlistPanel.java

    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("WatchlistWindow");
        jFrame.setContentPane(new WatchlistWindow(LocalDate.of(2017,04,10)).mainPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}