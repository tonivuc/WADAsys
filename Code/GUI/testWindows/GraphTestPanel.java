package GUI.testWindows;

import GUI.BaseWindow;
import GUI.chart.HaemoglobinChart;
import backend.Athlete;
import backend.AthleteGlobinDate;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Toni on 18.04.2017.
 */
public class GraphTestPanel extends JPanel {
    private JPanel graphPanel;

    List<Date> xData = new ArrayList<Date>();
    List<Double> yData = new ArrayList<Double>();

    public GraphTestPanel() {

        setLayout(new BorderLayout());

        //WORK IN PROGRESS BY TONI, DO NOT DELETE

        Athlete testAthlete = new Athlete(1);
        ArrayList<AthleteGlobinDate> measures = testAthlete.getMeasuredAthleteGlobinDates();


        for (int i = 1; i < measures.size(); i++) {

            xData.add(measures.get(i).getDate());
            yData.add(measures.get(i).getHaemoglobinLevel());
        }

        HaemoglobinChart testChart = new HaemoglobinChart(700,400);
        //XYSeries series = testChart.addSeries("Haemoglobin readings",xData,yData);
        testChart.createLineWList("Super-line!",xData,yData);
        JPanel testing = new XChartPanel(testChart);
        add(testing, BorderLayout.CENTER);
        validate(); //Litt usikker på hva som skal valideres.

    }

    public JPanel getMainPanel() {
        return this;
    }

    public static void main(String[] args) {
        BaseWindow window = new BaseWindow();
        window.setLayout(new BorderLayout());
        window.setSize(new Dimension(1000,500));
        window.add(new GraphTestPanel());
        //window.setContentPane(new GraphTestPanel());
       // window.pack();
        window.setVisible(true);


    }
}
