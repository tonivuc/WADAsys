package GUI.testWindows;

import GUI.BaseWindow;
import GUI.chart.HaemoglobinChart;
import backend.Athlete;
import backend.AthleteGlobinDate;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYSeries;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AthleteSearchWindow extends BaseWindow {

    List<Date> xData = new ArrayList<Date>();
    List<Double> yData = new ArrayList<Double>();

    public AthleteSearchWindow() {

        //WORK IN PROGRESS BY TONI, DO NOT DELETE

        Athlete testAthlete = new Athlete(1);
        ArrayList<AthleteGlobinDate> measures = testAthlete.getMeasuredAthleteGlobinDates();


        for (int i = 1; i < measures.size(); i++) {

            xData.add(measures.get(i).getDate());
            yData.add(measures.get(i).getHaemoglobinLevel());
        }

        HaemoglobinChart testChart = new HaemoglobinChart(500,300);
        //XYSeries series = testChart.addSeries("Haemoglobin readings",xData,yData);
        testChart.createLineWList("Super-line!",xData,yData);
        JPanel pnlChart = new XChartPanel(testChart);
        add(pnlChart);
        validate(); //Litt usikker på hva som skal valideres.
    }

    public static void main(String[] args) {
        AthleteSearchWindow window = new AthleteSearchWindow();


    }
}
