package backend;

import javax.swing.*;
import java.awt.*;

/**
 * Created by tvg-b on 22.04.2017.
 */
public class ProgressBar extends JPanel{

    JProgressBar pbar;

    static final int MY_MINIMUM = 0;
    static final int MY_MAXIMUM = 100;

    public ProgressBar() {
        // initialize Progress Bar
        pbar = new JProgressBar();
        pbar.setMinimum(MY_MINIMUM);
        pbar.setMaximum(MY_MAXIMUM);
        // add to JPanel
        //add(pbar);
    }

    public JProgressBar getPbar () {
        return pbar;
    }

    public void updateBar(int newValue) {
        pbar.setValue(newValue);
    }

    public static void main(String args[]) {

        final ProgressBar it = new ProgressBar();

        JFrame frame = new JFrame("Progress Bar Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(it);
        frame.pack();
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setVisible(true);

        // run a loop to demonstrate raising
        for (int i = MY_MINIMUM; i <= MY_MAXIMUM; i++) {
            final int percent = i;
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        it.updateBar(percent);
                    }
                });
                java.lang.Thread.sleep(100);
            } catch (InterruptedException e) {
                ;
            }
        }
    }
}
