package GUI.collector;

import GUI.BaseWindow;
import GUI.analyst.AthleteSearchPanel;
import GUI.login.LoginWindow;
import GUI.main.MainWindow;

import javax.smartcardio.Card;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by camhl on 31.03.2017.
 */
public class BaseWindowCollector extends BaseWindow {
    private JPanel rootPanel;
    private JButton profileButton;
    private JButton logOutButton;
    private JButton searchButton;
    private JPanel buttonPanel;
    private JPanel cardContainer;

    private AthleteSearchPanel searchCard;
    private JPanel athleteCard;
    private CardLayout layout;


    public BaseWindowCollector(){

        cardContainer.setBorder(new EmptyBorder(20, 20, 20, 20));

        //Add the JPanels from other classes into our window
        searchCard = new AthleteSearchPanel();

        //The name here is used when calling the .show() method on CardLayout
        cardContainer.add("search", searchCard);

        ButtonListener buttonListener = new ButtonListener();

        //sets the submitButton as default so that when enter is presset the Actionevent runs
        //getRootPane().setDefaultButton(searchButton);

        //Adds the submitbutton to an actionlistener.
        searchButton.addActionListener(buttonListener);
        logOutButton.addActionListener(buttonListener);

        searchCard.getJTable().getSelectionModel().addListSelectionListener(createListSelectionListener(searchCard.getJTable()));
        layout = (CardLayout)cardContainer.getLayout();

        //Essential for the JFrame-portion of the window to work:
        setContentPane(getMainPanel());
        setTitle("Blood collector window");
        pack();
        setVisible(true);

    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            String buttonPressed = actionEvent.getActionCommand();
            CardLayout layout = (CardLayout)cardContainer.getLayout();


            if(buttonPressed.equals("Search for athlete")){

                layout.show(cardContainer,"search");

            }

            else if(buttonPressed.equals("Testing requests")){



            }

            else if(buttonPressed.equals("Log out")){
                int option = JOptionPane.YES_NO_OPTION;

                if((JOptionPane.showConfirmDialog (null, "Are you sure you want to log out?","WARNING", option)) == JOptionPane.YES_OPTION){
                    //yes option
                    dispose();
                    new MainWindow();

                }
                //no option
            }
        }
    }

    //Adds a listener to the table
    ListSelectionListener createListSelectionListener(JTable resultsTable) {
        ListSelectionListener listener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                //Keeps it from firing twice (while value is adjusting as well as when it is done)
                if (!event.getValueIsAdjusting()) {//This line prevents double events

                    int row = resultsTable.getSelectedRow();
                    int athleteID = Integer.parseInt((String)resultsTable.getValueAt(row, 3));
                    //Gets the ID from the table and passes it to the method
                    athleteCard = new AthletePageCollector(athleteID).getMainPanel();
                    cardContainer.add("athlete", athleteCard);
                    layout.show(cardContainer,"athlete");
                    pack();


                    System.out.println(resultsTable.getValueAt(row, 3));
                    // System.out.println(resultsTable.getValueAt(resultsTable.getSelectedRow(), 3));
                }
            }
        };
        return listener;
    }

    public JPanel getMainPanel() {
        return rootPanel;
    }


    public static void main(String[]args){
        //JFrame frame = new JFrame("Base Window"); //Creating JFrame

        //frame.setContentPane(new BaseWindowCollector().rootPanel); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
        BaseWindowCollector frame = new BaseWindowCollector();

    }

}
