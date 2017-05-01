package GUI.collector;

/**
 *
 * @author Camilla Haahiem Larsen
 */

import GUI.athlete.AthleteSearchPanel;
import GUI.common.BaseWindow;
import GUI.common.Profile;
import GUI.main.MainWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class creates the main window used by the Collector type of user.
 */
public class BaseWindowCollector extends BaseWindow {

    /**
     * The mainPanel/rootPanel that everything is contained in.
     */
    private JPanel rootPanel;

    /**
     * The button the user presses if he/she wants to go into the profile menu.
     */
    private JButton profileButton;

    /**
     * The button the user presses if he she wants to log out.
     */
    private JButton logOutButton;

    /**
     * The button the user presses if he she wants to search for an athlete.
     */
    private JButton searchButton;

    /**
     * The JPanel where all the buttons are contained.
     */
    private JPanel buttonPanel;

    /**
     * The JPanel where all the cards are contained.
     */
    private JPanel cardContainer;

    /**
     * The JPanel that contains the search panel.
     */
    private AthleteSearchPanel searchCard;

    /**
     * The JPanel that contains the athlete card.
     */
    private JPanel athleteCard;

    /**
     * The panel that contains the profile card.
     */
    private JPanel profileCard;

    /**
     * The layout of the cards.
     */
    private CardLayout layout;

    /**
     * ID of of the athlete chosen by the user in the search panel.
     */
    private int athleteID;

    /**
     * Username of the logged in user.
     */
    private String username;

    /**
     * Constructs the BaseWindow for the usertype collector.
     * @param username Username of the user that logged in.
     */
    public BaseWindowCollector(String username){
        this.username = username;
        setup(); //creates the logo

        cardContainer.setBorder(new EmptyBorder(20, 20, 20, 20));



        //Add the JPanels from other classes into our window
        searchCard = new AthleteSearchPanel();
        profileCard = new Profile(username).getMainPanel();
        athleteCard = new AthletePageCollector(athleteID, username).getMainPanel();

        //The name here is used when calling the .show() method on CardLayout
        cardContainer.add("search", searchCard);
        cardContainer.add("profile", profileCard);
        cardContainer.add("athlete", athleteCard);

        //Setting the layout
        layout = (CardLayout)cardContainer.getLayout();

        //sets the submitButton as default so that when enter is presset the Actionevent runs
        //getRootPane().setDefaultButton(searchButton);

        //Adding all the buttons to the buttonlistener.
        ButtonListener buttonListener = new ButtonListener();
        searchButton.addActionListener(buttonListener);
        profileButton.addActionListener(buttonListener);
        logOutButton.addActionListener(buttonListener);

        searchCard.getJTable().getSelectionModel().addListSelectionListener(createListSelectionListener(searchCard.getJTable()));

        //Essential for the JFrame-portion of the window to work:
        setContentPane(getMainPanel());
        setTitle("Blood collector window");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    /**
     * ButtonListener for the buttons in the buttonPanel.
     */
    private class ButtonListener implements ActionListener {

        /**
         * Checks if any of the buttons was pressed.
         * @param actionEvent event.
         */
        public void actionPerformed(ActionEvent actionEvent) {
            String buttonPressed = actionEvent.getActionCommand();
            CardLayout layout = (CardLayout)cardContainer.getLayout();


            if(buttonPressed.equals("Search for athlete")){

                layout.show(cardContainer,"search");

            }

            if(buttonPressed.equals("Profile")){

                System.out.print("Profile");
                layout.show(cardContainer, "profile");

            }


            if(buttonPressed.equals("Log out")){
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

    /**
     * Adds a listener to the search panel.
     * @param resultsTable the table in the search panel.
     * @return ListSelectionListener
     */
    ListSelectionListener createListSelectionListener(JTable resultsTable) {
        ListSelectionListener listener = new ListSelectionListener() {

            /**
             * Checks if any of the values changed in the search panel table.
             * @param event event
             */
            public void valueChanged(ListSelectionEvent event) {
                System.out.println("valueChanged fired: "+event);
                //Keeps it from firing twice (while value is adjusting as well as when it is done)

                if (!event.getValueIsAdjusting() && searchCard.getJTable().hasFocus()) {//This line prevents double events

                    int row = resultsTable.getSelectedRow();
                    try {
                        athleteID = Integer.parseInt((String)resultsTable.getValueAt(row, 3));
                    }
                    catch (java.lang.ArrayIndexOutOfBoundsException e) {
                        System.out.println("Program can continue, but we got "+e);
                    }
                    //Gets the ID from the table and passes it to the method
                    athleteCard = new AthletePageCollector(athleteID, username).getMainPanel();
                    cardContainer.add("athlete", athleteCard);

                    layout.show(cardContainer,"athlete");
                    pack();
                    // System.out.println(resultsTable.getValueAt(resultsTable.getSelectedRow(), 3));
                }
            }
        };
        return listener;
    }

    /**
     * Returns the mainPanel/rootPanel.
     * @return JPanel
     */
    public JPanel getMainPanel() {
        return rootPanel;
    }

}
