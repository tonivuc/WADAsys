package GUI.analyst;

import backend.Athlete;
import backend.Watchlist;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.Collections;
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
    private List<Athlete> listAthletes;
    TableModel tableModel;

    public WatchlistPanel(LocalDate date) {
        /*
        mainPanel.setLayout(new BorderLayout());

        this.date = date;
        this.watchlist = new Watchlist();

        this.listAthletes = watchlist.getSuspiciousAthletes(date);
        tableModel = new WatchlistTableModel(listAthletes);
        athleteTable = new JTable(tableModel);

        tableScrollPane = new JScrollPane(athleteTable);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);


        athleteTable.setAutoCreateRowSorter(true);
        */

        this.date = date;
        this.watchlist = new Watchlist();
        this.listAthletes = watchlist.getSuspiciousAthletes(LocalDate.now());
        Collections.sort(listAthletes, Collections.reverseOrder());

        for (int i = 0; i < listAthletes.size(); i++) {
            System.out.println(listAthletes.get(i) + ", " + listAthletes.get(i).getGlobinDeviation(LocalDate.of(2017, 04, 10)));
        }

        mainPanel.setLayout(new BorderLayout());
        athleteTable = new JTable();

        Object[] columnNames = {"First name", "Last name", "Nationality", "Sport", "Haemoglobin %"};


        DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames);

        for (Athlete athlete : listAthletes) {
            Object[] o = new Object[5];
            o[0] = athlete.getFirstname();
            o[1] = athlete.getLastname();
            o[2] = athlete.getNationality();
            o[3] = athlete.getSport();
            o[4] = athlete.getGlobinDeviation(date) + " %";
            model.addRow(o);
        }
        athleteTable.setModel(model);

        tableScrollPane = new JScrollPane(athleteTable);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[]args){
        JFrame frame = new JFrame("Watchlist"); //Creating JFrame
        frame.setContentPane(new WatchlistPanel(LocalDate.now()).getMainPanel()); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}