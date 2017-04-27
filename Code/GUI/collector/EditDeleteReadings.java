package GUI.collector;

import backend.Athlete;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Nora Othilie
 */

public class EditDeleteReadings {

    /**
     * The mainPanel/rootPanel where everything is contained.
     */
    private JPanel rootPanel;

    /**
     * The button the user presses if he/she wants to delete a haemoglobin reading.
     */
    private JButton deleteButton;

    /**
     * The button the user presses if he/she wants to edit a haemoglobin reading.
     */
    private JButton editButton;

    /**
     * The field where the user writes the new haemoglobin reading.
     */
    private JTextField readingField;

    /**
     * Label displaying the date the reading was submitted.
     */
    private JLabel dateLabel;

    /**
     * Cancel button for canceling to delete/edit reading.
     */
    private JButton cancelButton;

    /**
     * The haemoglobin reading chosen by the user to edit/delete.
     */
    private double globinReading;

    /**
     * The date the selected haemoglobin reading was taken.
     */
    private String date;

    /**
     * The JFrame this panel is within.
     */
    private JFrame parentFrame;

    /**
     * The athlete that the selected haemoglobin reading was taken on.
     */
    private Athlete athlete;

    /**
     * Constructs a new EditDeleteReadings panel where a user can edit and delete the selected
     * reading from the selected athlete.
     * @param globinReading The reading the user wants to edit/delete.
     * @param date The date when the reading was taken.
     * @param athleteID The athleteID of the athlete that the reading was taken on.
     * @param parentFrame The frame that this panel is within.
     */
    public EditDeleteReadings(double globinReading, String date, int athleteID, JFrame parentFrame) {
        this.date = date;
        this.globinReading = globinReading;
        this.athlete = new Athlete(athleteID);
        this.parentFrame = parentFrame;

        parentFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        Border padding = BorderFactory.createEmptyBorder(100, 50, 100, 50);
        getMainPanel().setBorder(padding);

        dateLabel.setText(date);
        readingField.setText("" + globinReading);


        ButtonListener actionListener = new ButtonListener();
        editButton.addActionListener(actionListener);
        deleteButton.addActionListener(actionListener);
        cancelButton.addActionListener(actionListener);
    }

    public class ButtonListener implements ActionListener {

        /**
         * Checks to see if any button was pressed.
         * @param actionEvent event
         */
        public void actionPerformed(ActionEvent actionEvent) {

            String buttonPressed = actionEvent.getActionCommand();

            if (buttonPressed.equals("Edit")) {

                int confirmation = JOptionPane.showConfirmDialog(null, "Date: " + dateLabel.getText().trim() +
                        "\nHaemoglobin level: " + readingField.getText().trim() +
                        "\n \n Are you sure you want to edit this athlete? ", "Edit user", JOptionPane.YES_NO_OPTION);

                if (confirmation == 0) {    //YES-option

                    String newReading = readingField.getText();

                    if(athlete.updateReading(newReading,"globin_reading", date)) {

                        JOptionPane.showMessageDialog(parentFrame, "Reading updated!");
                        parentFrame.dispose();
                    }
                }
            }

            if (buttonPressed.equals("Delete")) {
                int confirmation = JOptionPane.showConfirmDialog(null, "Date: " + dateLabel.getText().trim() +
                        "\nHaemoglobin level: " + readingField.getText().trim() +
                        "\n \n Are you sure you want to delete this haemoglobin level? ", "Delete haemoglobin level", JOptionPane.YES_NO_OPTION);

                if (confirmation == 0) {    //If the user presses the YES-option

                    if(athlete.deleteReading(date)){
                        JOptionPane.showMessageDialog(parentFrame, "Reading updated!");
                    }
                    else{
                        JOptionPane.showMessageDialog(parentFrame, "Something went wrong..");
                    }
                    parentFrame.dispose();

                }
            }

            if(buttonPressed.equals("Cancel")){
                parentFrame.dispose();
            }
        }
    }

    /**
     * Returns the mainPane/rootPanel.
     * @return JPanel
     */
    public JPanel getMainPanel(){
        return rootPanel;
    }

}



