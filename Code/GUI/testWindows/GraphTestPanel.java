package GUI.testWindows;

import GUI.common.BaseWindow;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Toni on 18.04.2017.
 */
public class GraphTestPanel extends JPanel {
    private JPanel graphPanel;



    public GraphTestPanel(XYChart finishedChart) {

        setLayout(new BorderLayout());

        JPanel testing = new XChartPanel(finishedChart);
        add(testing, BorderLayout.CENTER);
        validate(); //Litt usikker paa hva som skal valideres.

    }

    public JPanel getMainPanel() {
        return this;
    }

    public static void main(String[] args) {
        BaseWindow window = new BaseWindow();
        window.setLayout(new BorderLayout());
        window.setSize(new Dimension(1000,500));
        //window.add(new GraphTestPanel()); MUST HAVE
        //window.setContentPane(new GraphTestPanel());
       // window.pack();
        window.setVisible(true);


    }
}
