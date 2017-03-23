/**
 * Created by Nora on 16.03.2017.
 */

/**
 * TestValutaVindu.java  E.L.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class searchWindow extends JFrame {
    private JTextField searching = new JTextField(30);

    public searchWindow (String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(100, 100);

        setLayout(new BorderLayout(2, 2)); // 2 pixels between the components
        add(new NorthPanel(), BorderLayout.NORTH);
        add(new SouthPanel(), BorderLayout.SOUTH);
        pack();
    }

    private class NorthPanel extends JPanel {
        public NorthPanel() {
            setLayout(new GridLayout(1, 2));
            JLabel text = new JLabel("Search", JLabel.LEFT);
            add(text);
            add(searching);
        }
    }

    private class SouthPanel extends JPanel {
        public SouthPanel() {
            setLayout(new GridLayout(1, 2));
            JButton buttonS = new JButton("Search");
            add(buttonS);
            Buttonlistener buttonlistener = new Buttonlistener();
            buttonS.addActionListener(buttonlistener);
        }
    }

    private class Buttonlistener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String s = event.getActionCommand();
           /* String search = null;
            try {
                search = (searching*/
            }
        }
    }

    class searchWindowMain {
        public static void main(String[] args) {
            searchWindow aWindow = new searchWindow("Search engine");
            aWindow.setVisible(true);
        }
    }
