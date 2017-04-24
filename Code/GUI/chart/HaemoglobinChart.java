package GUI.chart;

import GUI.BaseWindow;
import backend.Athlete;
import backend.AthleteGlobinDate;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;


import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Toni on 22.03.2017.
 * This class creates an XYChart object containing measured and estimated haemoglobin levels for an athlete.
 * To use the chart in a GUI, call makeJPanel() and use the return value.
 */
public class HaemoglobinChart extends XYChart {

    private List<Date> xDataMeasured = new ArrayList<Date>();
    private List<Double> yDataMeasured = new ArrayList<Double>();

    private List<Date> xDataExpected = new ArrayList<Date>();
    private List<Double> yDataExpected = new ArrayList<Double>();

    /**
     * Main and only constructor. Creates an XYChart with the specified width and height.
     * Due to BorderLayoutManager being used in the makeJPanel() class, it can be resized after this.
     * 
     * @param width pixels
     * @param height pixels
     * @param athleteID must match an existing athleteID in the database.
     */
    public HaemoglobinChart(int width, int height, int athleteID) {
        //XYChart related stuff
        super(width, height);
        setTitle("Haemoglobin comparison");
        setXAxisTitle("Date of measurement");
        setYAxisTitle("Haemoglobin level");
        getStyler().setDatePattern("dd. MMM YYYY");

        fillGraphWData(athleteID);

        createLineWList("Measured \nhaemoglobin \nlevels",xDataMeasured,yDataMeasured);
        createLineWList("Expected \nhaemoglobin \nlevels", xDataExpected, yDataExpected);
    }

    /**
     * Takes an athleteID and fills the four List objects with data from the database using a for-loop.
     * @param athleteID Passed on from the constructor of the class.
     */
    private void fillGraphWData(int athleteID) {
        Athlete testAthlete = new Athlete(athleteID);
        ArrayList<AthleteGlobinDate> measures = testAthlete.getMeasuredAthleteGlobinDates();

        //Add readings to the data lists
        try {
            for (int i = 0; i < measures.size(); i++) {
                java.sql.Date theDate = measures.get(i).getDate();
                xDataMeasured.add(theDate);
                yDataMeasured.add(measures.get(i).getHaemoglobinLevel());
                xDataExpected.add(theDate);
                yDataExpected.add(testAthlete.getExpectedGlobinLevel(theDate.toLocalDate()));
            }
        }
        catch (NullPointerException e) {
            System.out.println("Data required to draw the graph is missing: "+e);
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
     *
     * @param lineName
     * @param xDataMeasured
     * @param yDataMeasured
     * @see #addSeries
     */
    private void createLineWDouble(String lineName, double[] xDataMeasured, double[] yDataMeasured) {
        XYSeries series = addSeries(lineName, xDataMeasured, yDataMeasured);
        series.setMarker(SeriesMarkers.CIRCLE);
    }

    /**
     * Calls the addSeries method. Also sets the series marker to CIRCLE.
     *
     * @param lineName
     * @param dates
     * @param values
     * @see #addSeries
     */
    private void createLineWList(String lineName, List<Date> dates,List<Double> values) {
        XYSeries series = addSeries(lineName, dates, values);
        series.setMarker(SeriesMarkers.CIRCLE);
    }

    public static void main(String[] args) {
        HaemoglobinChart testChart = new HaemoglobinChart(700,400,1);
        BaseWindow window = new BaseWindow();
        window.add(testChart.makeJPanel());
        window.setVisible(true);
        window.pack();
    }

    //Kopier denne koden inn i konstruktøren der du vil vise grafen!
    /*
        double[] dates = {1,2,3,4};
        double[] globinReadings = {6,9,3,2};

        HaemoglobinChart testChart = new HaemoglobinChart(500,300);
        testChart.createLine("haemoglobin",dates,globinReadings);

        JPanel pnlChart = new XChartPanel(testChart);
        add(pnlChart, BorderLayout.CENTER);
        validate(); //Litt usikker på hva som skal valideres.

     */

}
