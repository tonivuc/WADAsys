package GUI.athlete;

import GUI.BaseWindow;
import backend.Athlete;
import backend.AthleteGlobinDate;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.event.*;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by camhl on 20.04.2017.
 */
public class AddBloodSample extends BaseWindow{
    private JTextField haemoglobinlevel;
    private JTextField date;
    private JButton button1;
    private JPanel rootPanel;
    private JButton cancelButton;
    private int athleteID;
    private boolean quit;
    private JFrame parentFrame;
    private String entry_creator; //username

    private boolean isClosed;


    public AddBloodSample(int athleteID, JFrame parentFrame, String entry_creator){
        this.athleteID = athleteID;
        this.quit = false;
        this.parentFrame = parentFrame;
        this.entry_creator = entry_creator;

        Border padding = BorderFactory.createEmptyBorder(100, 100, 100, 100);
        getMainPanel().setBorder(padding);

        getRootPane().setDefaultButton(button1);

        parentFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        date.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(date.getText().length() >= 8) e.consume();
            }
        });

        //Sets the focus to the button in stead of the first text field.
        button1.addAncestorListener(new AncestorListener() {
            public void ancestorAdded(AncestorEvent ae) {
                button1.requestFocus();
            }

            public void ancestorMoved(AncestorEvent ae){

            }

            public void ancestorRemoved(AncestorEvent ae){

            }


        });

        //sets the submitButton as default so that when enter is presset the Actionevent runs
        //button1.getRootPane().setDefaultButton(button1);

        //Listeners
        //Used to clear the default text when the user wants to type his username
        haemoglobinlevel.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (haemoglobinlevel.getText().equals("haemoglobin level")) {
                    haemoglobinlevel.setText(null);
                }


            }

            public void focusLost(FocusEvent e) {
                if (haemoglobinlevel.getText().equals("")) {
                    haemoglobinlevel.setText("haemoglobin level");
                }
                //...
            }
        });

        //Used to clear the default text when the user wants to type his password
        date.addFocusListener(new FocusListener() {

              @Override
              public void focusGained(FocusEvent e) {
                  if (String.valueOf(date.getText()).equals("date (yyyyMMdd)")) {
                      date.setText(null);
                  }

              }

            public void focusLost(FocusEvent e) {
                if (date.getText().equals("")) {
                    date.setText("date (yyyyMMdd)");
                }
                //...
            }
        });

        ButtonListener buttonlistener = new ButtonListener();
        button1.addActionListener(buttonlistener);
        button1.addKeyListener(buttonlistener);
        cancelButton.addActionListener(buttonlistener);
    }

    public class ButtonListener implements ActionListener, KeyListener {

        public void keyPressed(KeyEvent e) {

            addInput();

        }

        public void keyReleased(KeyEvent e){

        }

        public void keyTyped(KeyEvent e){

        }

        public void actionPerformed(ActionEvent e){
            String buttonPressed = e.getActionCommand();
            if(buttonPressed.equals("Confirm")) addInput();
            if(buttonPressed.equals("Cancel")) parentFrame.dispose();


        }

        public void addInput(){
            String dateString = date.getText();
            String readingString = haemoglobinlevel.getText();

            int result = new Athlete(athleteID).addHaemoglobinReading(readingString, dateString, entry_creator);

            if(result == -1){

                showMessageDialog(null, "Haemoglobin level not reasonable. \n\nPlease check that your input is correct.");
            }


            if(result == 1){
                showMessageDialog(null, "Haemoglobin level was registered successfully.");
                setIsClosed(true);
                parentFrame.dispose();
            }

            if(result == -2){
                showMessageDialog(null, "Something went wrong. Reading was not registered. \n\nPlease try again.");
            }

        }

    }

    public boolean getIsClosed(){
        return isClosed;
    }

    public void setIsClosed(boolean isClosed){
        this.isClosed = isClosed;
    }

    public JPanel getMainPanel(){
        return rootPanel;
    }

    public boolean getQuit(){
        return quit;
    }


    public static void main(String[]args){

        //athletePanelCollector frame = new athletePanelCollector();
        JFrame frame = new JFrame("Athlete information"); //Creating JFrame
        frame.setContentPane(new AddBloodSample(1, frame, "Collector").rootPanel); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        //newPanel.setContentPane(new AthleteSearchPanel().getMainPanel());
        //frame.setContentPane(new athletePanelCollector().getMainPanel());
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible

    }
}