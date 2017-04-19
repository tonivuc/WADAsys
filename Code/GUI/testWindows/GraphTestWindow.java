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

/**
 * Created by Toni on 18.04.2017.
 */
public class GraphTestWindow extends BaseWindow {
    private JButton logoutButton;
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel graphPanel;

    List<Date> xData = new ArrayList<Date>();
    List<Double> yData = new ArrayList<Double>();

    public GraphTestWindow() {

        //WORK IN PROGRESS BY TONI, DO NOT DELETE
        setTitle("Graph window");

        Athlete testAthlete = new Athlete(1);
        ArrayList<AthleteGlobinDate> measures = testAthlete.getMeasuredAthleteGlobinDates();


        for (int i = 1; i < measures.size(); i++) {

            xData.add(measures.get(i).getDate());
            yData.add(measures.get(i).getHaemoglobinLevel());
        }

        HaemoglobinChart testChart = new HaemoglobinChart(500,300);
        //XYSeries series = testChart.addSeries("Haemoglobin readings",xData,yData);
        testChart.createLineWList("Super-line!",xData,yData);
        JPanel testing = new XChartPanel(testChart);
        graphPanel.add(testing);
        validate(); //Litt usikker på hva som skal valideres.

        setContentPane(mainPanel);

    }

    public JPanel getRootPanel() {
        return mainPanel;
    }

    public static void main(String[] args) {
        GraphTestWindow window = new GraphTestWindow();
        //window.pack();
        window.setVisible(true);


    }
}
