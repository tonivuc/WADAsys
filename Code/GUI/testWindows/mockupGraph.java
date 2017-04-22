package GUI.testWindows;

import GUI.BaseWindow;
import GUI.chart.HaemoglobinChart;
import backend.Athlete;
import backend.AthleteGlobinDate;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class mockupGraph extends BaseWindow {

    JPanel rootPanel;
    JPanel topPanel;
    JPanel graphPanel;
    JButton logoutButton;

    List<Date> xData = new ArrayList<Date>();
    List<Double> yData = new ArrayList<Double>();

    public mockupGraph(int athleteID) {

        //WORK IN PROGRESS BY TONI, DO NOT DELETE

        Athlete testAthlete = new Athlete(athleteID);
        ArrayList<AthleteGlobinDate> measures = testAthlete.getMeasuredAthleteGlobinDates();


        for (int i = 0; i < (measures.size()-1); i++) {

            xData.add(measures.get(i).getDate());
            yData.add(measures.get(i).getHaemoglobinLevel());
        }

        HaemoglobinChart testChart = new HaemoglobinChart(500,300);
        //XYSeries series = testChart.addSeries("Haemoglobin readings",xData,yData);
        testChart.createLineWList("Super-line!",xData,yData);
        JPanel pnlChart = new XChartPanel(testChart);
        validate(); //Litt usikker på hva som skal valideres.
        graphPanel.add(pnlChart);
        //When having trouble adding objects to a JPanel, change the layout manger of the panel.


        setContentPane(rootPanel);


    }

    public JPanel getMainPanel() {
        return rootPanel;
    }

    public static void main(String[] args) {
        mockupGraph window = new mockupGraph(1);
        window.pack();
        window.setVisible(true);


    }
}
