package GUI.chart;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;


import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.Date;
import java.util.List;

/**
 * Created by Toni on 22.03.2017.
 */
public class HaemoglobinChart extends XYChart {

    //WORK IN PROGRESS BY TONI

    public HaemoglobinChart(int width, int length) {
        super(width, length);
        setTitle("Haemoglobin comparison");
        setXAxisTitle("Date of measurement");
        setYAxisTitle("Haemoglobin level");
    }

    public void createLine(String lineName, double[] xData, double[] yData) {
        XYSeries series = addSeries(lineName, xData, yData);
        series.setMarker(SeriesMarkers.CIRCLE);
    }

    public void createLineWList(String lineName, List<Date> dates,List<Double> values) {
        XYSeries series = addSeries(lineName, dates, values);
        series.setMarker(SeriesMarkers.CIRCLE);
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
