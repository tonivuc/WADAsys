package GUI.chart;

import backend.AvgHaemoglobinLevel;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.markers.SeriesMarkers;
import GUI.common.BaseWindow;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * @author Toni Vucic
 * This class creates an XYChart object containing measured and estimated haemoglobin levels for an athlete.
 * To use the chart in a GUI, call makeJPanel() and use the return value.
 */

public class AvgGlobinLevelChart extends XYChart {

    /**
     * A List of Dates used for the x values of the measured haemoglobin level graph.
     */
    private List<Date> xDataMeasured = new ArrayList<Date>();

    /**
     * A List of Doubles used for the y values of the measured haemoglobin level graph.
     */
    private List<Double> yDataMeasured = new ArrayList<Double>();

    /**
     * A List of Dates used for the x values of the expected haemoglobin level graph.
     */
    private List<Date> xDataExpected = new ArrayList<Date>();

    /**
     * A List of Doubles used for the y values of the expected haemoglobin level graph.
     */
    private List<Double> yDataExpected = new ArrayList<Double>();

    /**
     * Main and only constructor. Creates an XYChart with the specified width and height.
     * Due to BorderLayoutManager being used in the makeJPanel() class, it can be resized after this.
     * @param width pixels
     * @param height pixels
     */
    public AvgGlobinLevelChart(int width, int height) {
        //XYChart related stuff
        super(width, height);
        setTitle("Average Haemoglobin levels for each month");
        setXAxisTitle("Date of measurement");
        setYAxisTitle("Haemoglobin level");
        getStyler().setDatePattern("MMM YYYY");

        fillGraphWData();
    }

    /**
     * Fills graph with data using createLineWList and the AvgHaemoglobinLevel objects functions.
     */
    private void fillGraphWData() {

        //Add readings to the data lists
            AvgHaemoglobinLevel avgHaemoglobinGetter = new AvgHaemoglobinLevel();
            try {
                createLineWList("Male",avgHaemoglobinGetter.getAllMonths("male"),avgHaemoglobinGetter.getAverageLevels("male"), true);
                createLineWList("Female", avgHaemoglobinGetter.getAllMonths("female"), avgHaemoglobinGetter.getAverageLevels("female"), false);
            } catch (SQLException e) {
                //showMessageDialog(null, "Error: "+e,"Database Error",JOptionPane.ERROR_MESSAGE);
                setTitle("Database connection error, no data.");
            }
            catch (IllegalArgumentException e) {
                System.out.println("Gender input is wrong. Please check spellling in AvgGlobinLevelChart");
                setTitle("No data due to system error");
            }
             catch (NullPointerException e) {
                System.out.println("Data required to draw the graph is missing: "+e);
                setTitle("No data due to error: "+e);

            }

    }

    /**
     * Creates a JPanel with with a BorderLayout. Then adds the XYChart by using
     * new XChartPanel(this) (calling to the XYChart)
     * @return a resizable JPanel
     */
    public JPanel makeJPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel = new XChartPanel(this);
        return mainPanel;
    }

    /**
     * Calls the addSeries method. Also sets the series marker to CIRCLE.
     * @param lineName The name of the line on the Grapch
     * @param dates Data for the x-axis.
     * @param values Data for the y-axis.
     * @param gender True = male, False = female. Decides the graph color.
     * @see #addSeries
     */
    private void createLineWList(String lineName, List<Date> dates,List<Double> values, boolean gender) {
        if (dates.size() != 0 && values.size() != 0) {
            XYSeries series = addSeries(lineName, dates, values);
            series.setMarker(SeriesMarkers.CIRCLE);
            if (gender == false) { //Male = true
                series.setLineColor(XChartSeriesColors.RED);
                series.setMarkerColor(XChartSeriesColors.RED);
            }
        }
        else {
            System.out.println("Graph is missing data");
            throw new NullPointerException("No haemoglobin data for this athlete.");
        }

    }

    public static void main(String[] args) {
        AvgGlobinLevelChart testChart = new AvgGlobinLevelChart(700,400);
        BaseWindow window = new BaseWindow();
        window.add(testChart.makeJPanel());
        window.setVisible(true);
        window.pack();
    }

}
