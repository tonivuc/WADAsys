package GUI.analyst;

import backend.Athlete;
import backend.Sport;
import backend.Watchlist;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tvg-b on 30.03.2017.
 */

public class WatchlistPanel extends JPanel {

    private JPanel mainPanel;
    private JTable athleteTable;
    private JScrollPane tableScrollPane;
    private JComboBox comboBox1;
    private LocalDate date;
    private Watchlist watchlist;
    private List<Athlete> listAthletes;
    private TableModel tableModel;

    public WatchlistPanel(LocalDate date) {

        this.date = date;
        this.watchlist = new Watchlist();
        this.listAthletes = watchlist.getSuspiciousAthletes(LocalDate.now());

        List<Sport> sports = new ArrayList<Sport>();

        String sport = listAthletes.get(0).getSport();
        sports.add(new Sport(sport));

        for (int i = 0; i < listAthletes.size(); i++) {

            boolean sportExists = false;

            for (int j = 0; j < sports.size(); j++) {
                if (sports.get(j).getSport().equalsIgnoreCase(listAthletes.get(i).getSport())) {
                    sportExists = true;
                }
            }

            if (!sportExists) {
                Sport newSport = new Sport(listAthletes.get(i).getSport());
            }

        }

        for (int i = 0; i < sports.size(); i++) {
            System.out.println(sports.get(i).getSport());
        }

        Collections.sort(listAthletes, Collections.reverseOrder());

        for (int i = 0; i < listAthletes.size(); i++) {
            System.out.println(listAthletes.get(i) + ", " + listAthletes.get(i).getGlobinDeviation());
        }

        mainPanel.setLayout(new BorderLayout());
        athleteTable = new JTable();

        Object[] columnNames = {"First name", "Last name", "Nationality", "backend.Sport", "Haemoglobin %"};


        DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames);

        for (Athlete athlete : listAthletes) {
            Object[] o = new Object[5];
            o[0] = athlete.getFirstname();
            o[1] = athlete.getLastname();
            o[2] = athlete.getNationality();
            o[3] = athlete.getSport();
            o[4] = athlete.getGlobinDeviation() + " %";
            model.addRow(o);
        }
        athleteTable.setModel(model);

        tableScrollPane = new JScrollPane(athleteTable);
        mainPanel.add(comboBox1, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.SOUTH);

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