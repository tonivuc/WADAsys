package watchlist;

import athlete.Athlete;
import login.BaseWindow;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by tvg-b on 30.03.2017.
 */
public class WatchlistWindow extends BaseWindow {

    private JPanel panel1;
    private JButton athleteSearchButton;
    private JButton watchListButton;
    private JButton placeHolderButton;
    private JButton logOutButton;
    private JComboBox SortBy;
    private JTable athleteTable;
    private JScrollPane tableScrollPane;
    private LocalDate date;

    public WatchlistWindow (LocalDate date) {

        this.date = date;

        SortBy.addItem("Sport");
        SortBy.addItem("Nationality");
        SortBy.addItem("Haemoglobin level");

        Watchlist watchlist = new Watchlist();
        ArrayList<Athlete> athletes = null;
        athletes = watchlist.getSuspiciousAthletes(LocalDate.of(2017, 04, 10));

        Object[][] rowData = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
                { "Row2-Column1", "Row2-Column2", "Row2-Column3" } };

        Object[] columnNames = { "Column One", "Column Two", "Column Three" };

        athleteTable = new JTable(rowData, columnNames);

        tableScrollPane = new JScrollPane(athleteTable);

        /*
        for (int i = 1; i < athletes.size() - 1; i++) {
            dtm.addRow(new Object[]{athletes.get(i).getFirstname(), athletes.get(i).getLastname(), athletes.get(i).getSport(), athletes.get(i).getNationality(), athletes.get(i).getGlobinDeviation(date)});
        }
        */

    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("WatchlistWindow");
        jFrame.setContentPane(new WatchlistWindow(LocalDate.of(2017,04,10)).panel1);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
