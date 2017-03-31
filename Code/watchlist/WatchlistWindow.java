package watchlist;

import backend.Athlete;
import backend.Watchlist;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by tvg-b on 30.03.2017.
 */

public class WatchlistWindow extends JFrame {

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

    public WatchlistWindow (LocalDate date) {

        this.date = date;

        SortBy.addItem("Sport");
        SortBy.addItem("Nationality");
        SortBy.addItem("Haemoglobin level");

        Watchlist watchlist = new Watchlist();
        ArrayList<Athlete> athletes = new ArrayList<Athlete>();

        athletes = watchlist.getSuspiciousAthletes(LocalDate.of(2017, 04, 10));



        tablePanel.setLayout(new BorderLayout());

        athleteTable = new JTable(new DefaultTableModel(new Object[]{"First name", "Last name", "Sport", "Nationality", "telephone", "Haemoglobin level"}, 0));

        DefaultTableModel model = (DefaultTableModel) athleteTable.getModel();

        for (int i = 0; i < athletes.size(); i++) {
            model.addRow(new Object[]{athletes.get(i).getFirstname(),
                                      athletes.get(i).getLastname(),
                                      athletes.get(i).getSport(),
                                      athletes.get(i).getNationality(),
                                      athletes.get(i).getTelephone(),
                                      athletes.get(i).getGlobinDeviation() + " %"});
        }



        tableScrollPane = new JScrollPane(athleteTable);

        tablePanel.add(tableScrollPane, BorderLayout.CENTER);


    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("WatchlistWindow");
        jFrame.setContentPane(new WatchlistWindow(LocalDate.of(2017,04,10)).mainPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}