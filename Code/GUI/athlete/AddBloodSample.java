package GUI.athlete;

/**
 *
 * @author Camilla Haaheim Larsen
 */

import GUI.collector.AthletePageCollector;
import GUI.common.BaseWindow;
import backend.Athlete;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.event.*;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Class made to handle GUI for the add blood sample functionality.
 */
public class AddBloodSample extends BaseWindow{

    /**
     * Field where the user writes a haemoglobin level he/she wants to add.
     */
    private JTextField haemoglobinlevel;

    /**
     * Field where the user writes the date when the test was taken.
     */
    private JTextField date;

    /**
     * Confirm button to add the haemoglobin sample.
     */
    private JButton button1;

    /**
     * The mainPanel/rootPanel where everything is contained.
     */
    private JPanel rootPanel;

    /**
     * Cancel button to cancel.
     */
    private JButton cancelButton;

    /**
     * ID of the athlete that the blood sample is being added to .
     */
    private int athleteID;

    /**
     * The frame this panel is contained within.
     */
    private JFrame parentFrame;

    /**
     * The user that submitted the sample.
     */
    private String entry_creator; //username

    /**
     * boolean value
     */
    private boolean isClosed;

    /**
     * AthletePageCollector Object used to refresh to GUI on the AthletePageCollector panel.
     */
    private AthletePageCollector apc;

    /**
     * Constructs a new AddBloodSample panel.
     * @param athleteID athleteID of the athlete that the user selected.
     * @param parentFrame the frame that contains this panel.
     * @param entry_creator the user submitting the blood sample.
     * @param apc AthletePageCollector Object from the parent frame.
     */
    public AddBloodSample(int athleteID, JFrame parentFrame, String entry_creator, AthletePageCollector apc){
        this.athleteID = athleteID;
        this.parentFrame = parentFrame;
        this.entry_creator = entry_creator;
        this.apc = apc;

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

        /**
         * Checks if a key is pressed.
         * @param e event
         */
        public void keyPressed(KeyEvent e) {

            addInput();

        }

        /**
         * Checks if a key is released.
         * @param e event
         */
        public void keyReleased(KeyEvent e){

        }

        /**
         * Checks if a key is Typed.
         * @param e event
         */
        public void keyTyped(KeyEvent e){

        }

        /**
         * Checks if the cancel or the confirm button is being pressed.
         * @param e event
         */
        public void actionPerformed(ActionEvent e){
            String buttonPressed = e.getActionCommand();
            if(buttonPressed.equals("Confirm")) addInput();
            if(buttonPressed.equals("Cancel")) parentFrame.dispose();


        }

        /**
         * Adds the input the user has entered.
         */
        public void addInput(){
            String dateString = date.getText();
            String readingString = haemoglobinlevel.getText();
            readingString = readingString.replaceAll(",",".");

            try {

                int result = new Athlete(athleteID).addHaemoglobinReading(readingString, dateString, entry_creator);
                if(result == -1){
                    showMessageDialog(null, "Haemoglobin level not reasonable. \n\nPlease check that your input is correct.");
                }


                if(result == 1){
                    showMessageDialog(null, "Haemoglobin level was registered successfully.");
                    apc.updateReadingTable();
                    setIsClosed(true);
                    parentFrame.dispose();
                }

                if(result == -2){
                    showMessageDialog(null, "Something went wrong. Reading was not registered. \n\nPlease try again.");
                }

                if(result == -3) {
                    showMessageDialog(null, "Database Error stemming from Athlete. \n Can't connect to database","Database Error",JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (SQLException e) {
                showMessageDialog(null, "Database Error creating new Athlete. \n Can't connect to database","Database Error",JOptionPane.ERROR_MESSAGE);
            }




        }

    }

    /**
     * Returns the isClosed instance variable.
     * @return boolean
     */
    public boolean getIsClosed(){
        return isClosed;
    }

    /**
     * Sets the isClosed variable
     * @param isClosed boolean true or false depending on what you want to set it to.
     */
    public void setIsClosed(boolean isClosed){
        this.isClosed = isClosed;
    }

    /**
     * Returns the mainPanel/rootPanel.
     * @return JPanel
     */
    public JPanel getMainPanel(){
        return rootPanel;
    }

}