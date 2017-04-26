package GUI.collector;

import backend.Athlete;
import backend.AthleteGlobinDate;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Nora on 23.04.2017.
 */
public class EditDeleteReadings {
    private JPanel rootPanel;
    private JButton deleteButton;
    private JButton editButton;
    private JTextField readingField;
    private JLabel dateLabel;
    private JButton cancelButton;
    private String dateString;

    private AthleteGlobinDate athleteGlobinDate;
    private double globinReading;
    private String date;
    private JFrame parentFrame;

    private Athlete athlete;

    public EditDeleteReadings(double globinReading, String date, int athleteID, JFrame parentFrame) {
        this.date = date;
        this.globinReading = globinReading;
        this.athlete = new Athlete(athleteID);
        this.athleteGlobinDate = new AthleteGlobinDate();
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

    public JPanel getMainPanel(){
        return rootPanel;
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Edit haemoglobin level"); //Creating JFrame
        frame.setContentPane(new EditDeleteReadings(16.34262, "20170321", 7, new JFrame()).rootPanel); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible


    }

}



