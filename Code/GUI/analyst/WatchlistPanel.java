package GUI.analyst;

import backend.Athlete;
import backend.Sport;
import backend.Watchlist;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tvg-b on 30.03.2017.
 */

public class WatchlistPanel extends JPanel {

    private JPanel mainPanel;
    private JTable athleteTable;
    private JScrollPane tableScrollPane;
    private JComboBox comboBox1;
    private Watchlist watchlist;
    private final List<Athlete> listAthletes;
    private DefaultTableModel model;
    private final List<Sport> sports;
    private final Object[] columnNames = {"First name", "Last name", "Nationality", "Sport", "Haemoglobin %"};

    public WatchlistPanel() {

        this.watchlist = new Watchlist();
        this.listAthletes = watchlist.getSuspiciousAthletes(LocalDate.now());

        sports = new ArrayList<Sport>();

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
                sports.add(newSport);
            }

        }

        comboBox1.addItem("All sports");

        for (int i = 0; i < sports.size(); i++) {
            comboBox1.addItem(sports.get(i).getSport());
        }


        ActionListener cbActionListener = new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {

                String s = (String) comboBox1.getSelectedItem();//get the selected item

                DefaultTableModel updatedModel = new DefaultTableModel(new Object[0][0], columnNames);

                List<Athlete> updatedAthleteList = new ArrayList<Athlete>();

                for (int i = 0; i < listAthletes.size() ; i++) {
                    updatedAthleteList.add(listAthletes.get(i));
                }

                Iterator<Athlete> i = updatedAthleteList.iterator();

                while (i.hasNext()) {
                    Athlete a = i.next();

                    if (s.equalsIgnoreCase("All sports")) break;

                    if (!a.getSport().equalsIgnoreCase(s)) {
                        i.remove();
                    }
                }

                for (Athlete athlete : updatedAthleteList) {
                    Object[] o = new Object[5];
                    o[0] = athlete.getFirstname();
                    o[1] = athlete.getLastname();
                    o[2] = athlete.getNationality();
                    o[3] = athlete.getSport();
                    o[4] = athlete.getGlobinDeviation() + " %";
                    updatedModel.addRow(o);
                }

                athleteTable.setModel(updatedModel);

            }
        };

        Collections.sort(listAthletes, Collections.reverseOrder());

        mainPanel.setLayout(new BorderLayout());
        athleteTable = new JTable();


        model = new DefaultTableModel(new Object[0][0], columnNames);

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
        athleteTable.setDefaultEditor(Object.class, null);
        comboBox1.addActionListener(cbActionListener);
        tableScrollPane = new JScrollPane(athleteTable);
        mainPanel.add(comboBox1, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[]args){
        JFrame frame = new JFrame("Watchlist"); //Creating JFrame
        frame.setContentPane(new WatchlistPanel().getMainPanel()); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}