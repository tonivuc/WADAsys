package backend;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by tvg-b on 04.04.2017.
 */
public class WatchlistTableModel extends AbstractTableModel {
    private static final int FIRSTNAME = 0;
    private static final int LASTNAME = 1;
    private static final int SPORT = 2;
    private static final int NATIONALITY = 3;
    private static final int HAEMOGLOBINLEVEL = 4;

    private String[] columnNames = {"First name", "Last name", "Sport", "Nationality", "Haemoglobin %"};
    private List<Athlete> listAthletes;

    public WatchlistTableModel (List<Athlete>listAthletes) {
        this.listAthletes = listAthletes;
    }

    @Override
    public int getRowCount() {
        return columnNames.length;
    }

    @Override
    public int getColumnCount() {
        return listAthletes.size();
    }

    @Override
    public String getColumnName (int index) {
        return columnNames[index];
    }

    public Class<?> getColumnClass (int index) {

        if (listAthletes.isEmpty()) {
            return Object.class;
        }

        System.out.println(getValueAt(0, index).getClass());
        return getValueAt(0, index).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Athlete athlete = listAthletes.get(rowIndex);
        Object returnValue = null;

        switch (columnIndex) {
            case FIRSTNAME:
                returnValue = athlete.getFirstname();
                break;

            case LASTNAME:
                returnValue = athlete.getLastname();
                break;

            case SPORT:
                returnValue = athlete.getSport();
                break;

            case NATIONALITY:
                returnValue = athlete.getNationality();
                break;

            case HAEMOGLOBINLEVEL:
                returnValue = athlete.getGlobinDeviation(LocalDate.now());
                System.out.println(athlete.getExpectedGlobinLevel(LocalDate.now()));
                break;

            default:
                throw new IllegalArgumentException("Invalid Column Index");
        }

        return returnValue;
    }

}
