package GUI.analyst;

import backend.Watchlist;
import backend.WatchlistTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by tvg-b on 30.03.2017.
 */

public class WatchlistPanel {

    private JPanel mainPanel;
    private JTable athleteTable;
    private JScrollPane tableScrollPane;
    private LocalDate date;
    private Watchlist watchlist;
    private List listAthletes;
    TableModel tableModel;

    public WatchlistPanel(LocalDate date) {

        mainPanel.setLayout(new BorderLayout());

        this.date = date;
        this.watchlist = new Watchlist();

        this.listAthletes = watchlist.getSuspiciousAthletes(date);
        tableModel = new WatchlistTableModel(listAthletes);
        athleteTable = new JTable(tableModel);

        tableScrollPane = new JScrollPane(athleteTable);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);


        athleteTable.setAutoCreateRowSorter(true);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[]args){
        JFrame frame = new JFrame("Watchlist"); //Creating JFrame
        frame.setContentPane(new WatchlistPanel(LocalDate.of(2017, 04, 10)).getMainPanel()); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}