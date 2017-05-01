package GUI.analyst;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;

/**
 * Created by tvg-b on 21.04.2017.
 */
public class LoadingBar extends JPanel {

    private JButton button1;
    private JPanel mainPanel;

    public LoadingBar () {

    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(this.getWidth()/2, this.getHeight()/2);
        Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
        arc.setFrameFromCenter(new Point(0,0), new Point(120, 120));
        arc.setAngleStart(0);
        arc.setAngleExtent(90);
        g2.setColor(Color.red);
        g2.draw(arc);
        g2.fill(arc);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
