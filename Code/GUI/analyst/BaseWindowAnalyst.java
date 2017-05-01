package GUI.analyst;


/**
 *
 * @author Camilla Haaheim Larsen
 */

import GUI.athlete.AthleteSearchPanel;
import GUI.chart.AvgGlobinLevelChart;
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
 * Class made to handle functionality in association with the analyst's base window.
 */
public class BaseWindowAnalyst extends BaseWindow{

    /**
     * The rootPabel/mainPanel where everything is contained.
     */
    private JPanel rootPanel;

    /**
     * The butten the user presses if he/she wants to search for an athlete.
     */
    private JButton athleteSearchButton;

    /**
     * The button the user presses if he/she wants to log out.
     */
    private JButton logOutButton;

    /**
     * The button the user presses if he/she wants to look at the watchlist.
     */
    private JButton watchListButton;

    /**
     * The button the user presses if he/she wants to edit his/her profile.
     */
    private JButton profileButton;

    /**
     * The button the user presses if he/she wants to check where most of the athletes are right now
     */
    private JButton athletesAtEachLocationButton;

    /**
     * The button the user presses if he/she wants to check the average logged Haemoglobin levels for both genders.
     */
    private JButton averageHaemoglobinLevelsButton;

    /**
     * The topPanel
     */
    private JPanel topPanel;

    /**
     * The JPanel containing the cardLayout.
     */
    private JPanel cardContainer;

    /**
     * The card/JPanel that contains the searchPanel.
     */
    private AthleteSearchPanel searchCard;

    /**
     * The card/JPanel that contains the watchlistPanel.
     */
    private WatchlistPanel watchlistCard;

    /**
     * The card/JPanel that contains the profilePanel.
     */
    private JPanel profileCard;

    /**
     * The card/JPanel that contains the athletePanel.
     */
    private JPanel athleteCard;

    /**
     * The layout of the cards.
     */
    private CardLayout layout;

    /**
     * Used to make sure the creation of the JPanel is only ran once.
     */
    private boolean avgGlobinButton1stTimeClicked = true;
    /**
     * Used to make sure the creation of the JPanel is only ran once.
     */
    private boolean athletesEachLocation1stTimeClicked = true;

    /**
     * Constructs the BaseWindow for the analyst.
     * @param username username of the user that enters the BaseWindow.
     */
    public BaseWindowAnalyst(String username){
        setup(); //creates the logo


        cardContainer.setBorder(new EmptyBorder(20, 20, 20, 20));

        //Adding all the buttons to the buttonlistener
        ButtonListener actionListener = new ButtonListener();
        athleteSearchButton.addActionListener(actionListener);
        watchListButton.addActionListener(actionListener);
        profileButton.addActionListener(actionListener);
        logOutButton.addActionListener(actionListener);
        averageHaemoglobinLevelsButton.addActionListener(actionListener);
        athletesAtEachLocationButton.addActionListener(actionListener);

        //Add the JPanels from other classes into our window
        searchCard = new AthleteSearchPanel();
        watchlistCard = new WatchlistPanel();
        profileCard = new Profile(username).getMainPanel();


        //The name here is used when calling the .show() method on CardLayout
        cardContainer.add("search", searchCard);
        cardContainer.add("watchlist", watchlistCard.getMainPanel());
        cardContainer.add("profile", profileCard);
        //cardContainer.add("athlete", athleteCard);

        //Setting the layout
        layout = (CardLayout)cardContainer.getLayout();

        //Adding the searchCard to an listSelectionListener
        searchCard.getJTable().getSelectionModel().addListSelectionListener(createListSelectionListener(searchCard.getJTable()));

        //Adding the wathclistCard to an listSelectionListener
        watchlistCard.getJTable().getSelectionModel().addListSelectionListener(createListSelectionListener(watchlistCard.getJTable()));

        //Essential for the JFrame portion of the window to work:
        setContentPane(getMainPanel());
        setTitle("Analyst window");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    /**
     * ButtonListener for all the buttons in the window.
     */
    private class ButtonListener implements ActionListener{
        /**
         * ButtonListener for all the buttons in the window.
         * @param actionEvent actionEvent
         */
        public void actionPerformed(ActionEvent actionEvent){
            String buttonPressed = actionEvent.getActionCommand();

            //CardLayout administers the different cards
            CardLayout layout = (CardLayout)cardContainer.getLayout();

            //This could have been a switch but oh well :)
            if (buttonPressed.equals("Athlete search")) {
                layout.show(cardContainer, "search");

            }

            if (buttonPressed.equals("Watch-list")) {
                layout.show(cardContainer,"watchlist");
            }

            if (buttonPressed.equals("Average Haemoglobin levels")) {
                if (avgGlobinButton1stTimeClicked) {
                    cardContainer.add("avgGlobinLevels", new AvgGlobinLevelChart(600,500).makeJPanel());
                    avgGlobinButton1stTimeClicked = false;
                }
                layout.show(cardContainer, "avgGlobinLevels");
            }

            if (buttonPressed.equals("Number of athletes at locations")) {
                if (athletesEachLocation1stTimeClicked) {
                    cardContainer.add("numOfAthletesAtLoc", new AthleteLocationListPanel());
                    athletesEachLocation1stTimeClicked = false;
                }
                layout.show(cardContainer, "numOfAthletesAtLoc");
            }

            if (buttonPressed.equals("Profile")){
                layout.show(cardContainer, "profile");
            }

            if(buttonPressed.equals("Log out")) {
                int option = JOptionPane.YES_NO_OPTION;

                if ((JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "WARNING", option)) == JOptionPane.YES_OPTION) {
                    //yes option
                    new MainWindow();
                    dispose();
                }
                //no option automatically closes the window
            }
        }
    }

    /**
     * Listener added to the searchTable.
     * @param resultsTable table to add the listener to.
     * @return ListSelectionListener
     */
    ListSelectionListener createListSelectionListener(JTable resultsTable) {
        ListSelectionListener listener = new ListSelectionListener() {

            /**
             * Checks if a value is changed.
             * @param event event
             */
            public void valueChanged(ListSelectionEvent event) {
                //Keeps it from firing twice (while value is adjusting as well as when it is done)
                int row = resultsTable.getSelectedRow();


                if (!event.getValueIsAdjusting() && searchCard.getJTable().hasFocus()) {//This line prevents double events

                    int athleteID = Integer.parseInt((String) resultsTable.getValueAt(row, 3));
                    //Gets the ID from the table and passes it to the method
                    athleteCard = new AthletePageAnalyst(athleteID).getMainPanel();
                    cardContainer.add("athlete", athleteCard);
                    layout.show(cardContainer, "athlete");
                    pack();
                    setLocationRelativeTo(null);
                    setVisible(true);
                }

                if (!event.getValueIsAdjusting() && watchlistCard.getJTable().hasFocus()) {
                    String idString = "" + resultsTable.getValueAt(row, 0);
                    int athleteID = Integer.parseInt(idString.trim());
                    athleteCard = new AthletePageAnalyst(athleteID).getMainPanel();
                    cardContainer.add("athlete", athleteCard);
                    layout.show(cardContainer, "athlete");
                    pack();
                    setLocationRelativeTo(null);
                    setVisible(true);
                }
            }

        }; return listener;
    }

    /**
     * Returns the mainPanel/rootPanel.
     * @return JPanel
     */
    public JPanel getMainPanel() {
        return rootPanel;
    }

}
