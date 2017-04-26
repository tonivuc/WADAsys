package GUI.athlete;

import GUI.BaseWindow;
import backend.AthleteGlobinDate;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.event.*;
import java.security.Key;

import static javax.swing.JOptionPane.showConfirmDialog;
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


    public AddBloodSample(int athleteID, JFrame parentFrame, String entry_creator) {
        this.athleteID = athleteID;
        this.quit = false;
        this.parentFrame = parentFrame;
        this.entry_creator = entry_creator;

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


        /*button1.addActionListener(new ActionListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    System.out.println("Hello");

                    String dateString = date.getText();
                    String readingString = haemoglobinlevel.getText();

                    if(new AthleteGlobinDate(athleteID).addHaemoglobinReading(readingString, dateString)){
                        parentFrame.dispose();
                    }

                    //JOptionPane.showMessageDialog(null , "You've Submitted the name " + nameInput.getText());
                }

            }

            public void actionPerformed(ActionEvent e) {

                String dateString = date.getText();
                String readingString = haemoglobinlevel.getText();

                if(new AthleteGlobinDate(athleteID).addHaemoglobinReading(readingString, dateString)){
                    parentFrame.dispose();
                }


                /*java.sql.Date sql = null;

                try{
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    Date parsed = format.parse(dateString);
                    sql = new java.sql.Date(parsed.getTime());
                    System.out.println(sql);
                }catch(Exception ex){
                    System.out.println("ADDBLOODSAMPLE: Date in wrong formate.");
                    showMessageDialog(null, "Wrong date format. \n\nPlease use the format: yyyyMMdd.");
                }*/

                //String haemoglobinString = haemoglobinlevel.getText();
                /*double haemoglobinDouble = 0;
                try{
                    haemoglobinDouble = Double.parseDouble(haemoglobinString);
                }catch(Exception exe){
                    System.out.println("ADDBLOODSAMPLE: haemoglobinDouble not a double.");
                    showMessageDialog(null, "Haemoglobin level must be a decimal number.\n\nPlease try again.");
                }*/

               /* if(haemoglobinDouble < 5 || haemoglobinDouble > 30){

                    showMessageDialog(null, "Haemoglobin level not reasonable. \n\nPlease check that your input is correct.");
                    sql = null;
                }

                if(sql != null && haemoglobinDouble != 0) {

                    Athlete athlete = new Athlete(athleteID);

                    int confirmation = showConfirmDialog(null, "Haemoglobin level: " +
                            haemoglobinlevel.getText().trim() + "\nDate: " + sql +
                            "\nAthlete: " + athlete.getFirstname() + " " + athlete.getLastname() +
                            "\n \nAre you sure you want to add haemoglobin level?", "Submit", JOptionPane.YES_NO_OPTION);
                    if (confirmation == 0) { //yes confirmation

                        AthleteGlobinDate athleteGlobinDate = new AthleteGlobinDate(haemoglobinDouble, sql, athleteID);
                        athleteGlobinDate.addHaemoglobinLevel();

                        showMessageDialog(null, "Haemoglobin level was registered successfully.");
                        parentFrame.dispose();


                    }
                }
            }
        });*/

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
            addInput();

        }

        public void addInput(){
            String dateString = date.getText();
            String readingString = haemoglobinlevel.getText();

            if(new AthleteGlobinDate(athleteID).addHaemoglobinReading(readingString, dateString, entry_creator)){
                parentFrame.dispose();
            }
        }

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