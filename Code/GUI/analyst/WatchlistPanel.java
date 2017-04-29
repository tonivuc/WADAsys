package GUI.analyst;

/**
 * @author Trym Vegard Gjelseth-Borgen
 */

import backend.Athlete;
import backend.Sport;
import backend.Watchlist;

import javax.swing.*;
import javax.swing.border.Border;
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
 * Class made to handle GUI for the watchlist functionality.
 */
public class WatchlistPanel extends JPanel {

    /**
     * The mainPanel/rootPanel where everything is contained.
     */
    private JPanel mainPanel;

    /**
     * A table of the athletes on the watchlist.
     */
    private JTable athleteTable;

    /**
     * Contains the athleteTable.
     */
    private JScrollPane tableScrollPane;

    /**
     * ComboBox for choosing which watchlist the user wants to be displayed based on sport.
     */
    private JComboBox comboBox1;

    /**
     * A Watchlist Object containing athletes on the watchlist.
     */
    private Watchlist watchlist;

    /**
     * List of athletes.
     */
    private List<Athlete> listAthletes;

    /**
     * The tableModel for the JTable.
     */
    private DefaultTableModel model;

    /**
     * A list of Sports.
     */
    private final List<Sport> sports;

    /**
     * The columns in the watchlist table.
     */
    private final Object[] columnNames = {"AthleteID", "First name", "Last name", "Nationality", "Sport", "Haemoglobin %"};

    /**
     * Constructs the WatchlistPanel.
     */
    public WatchlistPanel() {

        this.watchlist = new Watchlist();
        this.listAthletes = watchlist.getSuspiciousAthletes(LocalDate.now());

        //Setting padding around the frame
        Border padding = BorderFactory.createEmptyBorder(0, 100, 50, 100);
        getMainPanel().setBorder(padding);


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

            /**
             * Checks to se if someone pressed the comboBox, and if that case, what did they choose.
             * @param e event
             */
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

                addRowsAndSetModel(updatedModel, updatedAthleteList);

            }
        };

        Collections.sort(listAthletes, Collections.reverseOrder());

        mainPanel.setLayout(new BorderLayout());
        athleteTable = new JTable();
        model = new DefaultTableModel(new Object[0][0], columnNames);

        addRowsAndSetModel(model, listAthletes);

        athleteTable.setDefaultEditor(Object.class, null);

        comboBox1.addActionListener(cbActionListener);
        tableScrollPane = new JScrollPane(athleteTable);
        mainPanel.add(comboBox1, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

    }

    /**
     * Adds all the rows from the List into the model and sets the JTable's model to the updated model.
     * @param model the model that is going to updated.
     * @param athletes The List of athletes that are going to be added to the moddel.
     */
    public void addRowsAndSetModel (DefaultTableModel model, List<Athlete> athletes) {
        for (Athlete athlete : athletes) {
            Object[] o = new Object[6];
            o[0] = athlete.getAthleteID();
            o[1] = athlete.getFirstname();
            o[2] = athlete.getLastname();
            o[3] = athlete.getNationality();
            o[4] = athlete.getSport();
            o[5] = athlete.getGlobinDeviation() + " %";
            model.addRow(o);
        }

        athleteTable.setModel(model);
    }

    /**
     * Returns the mainPanel/rootPanel.
     * @return JPanel
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
    public JTable getJTable() { return athleteTable; }

    public static void main(String[] args) {
        JFrame frame = new JFrame("YO");
        frame.add(new WatchlistPanel().getMainPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 500));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}