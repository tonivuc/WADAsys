package GUI.analyst;

/**
 *
 * @author Camilla Haaheim Larsen
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
 * Class made to handle functionality in association with the analyst's base window.
 */
public class BaseWindowAnalyst extends BaseWindow {

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
    private JPanel watchlistCard;

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
     * Constructs the BaseWindow for the analyst.
     * @param username username of the user that enters the BaseWindow.
     */
    public BaseWindowAnalyst(String username){

        cardContainer.setBorder(new EmptyBorder(20, 20, 20, 20));

        //Adding all the buttons to the buttonlistener
        ButtonListener actionListener = new ButtonListener();
        athleteSearchButton.addActionListener(actionListener);
        watchListButton.addActionListener(actionListener);
        profileButton.addActionListener(actionListener);
        logOutButton.addActionListener(actionListener);

        //Add the JPanels from other classes into our window
        searchCard = new AthleteSearchPanel();
        watchlistCard = new WatchlistPanel().getMainPanel();
        profileCard = new Profile(username).getMainPanel();
        //athleteCard = new AthletePageAnalyst(athleteID).getMainPanel();

        //The name here is used when calling the .show() method on CardLayout
        cardContainer.add("search", searchCard);
        cardContainer.add("watchlist", watchlistCard);
        cardContainer.add("profile", profileCard);
        //cardContainer.add("athlete", athleteCard);

        //Setting the layout
        layout = (CardLayout)cardContainer.getLayout();

        //Adding the searchCard to an listSelectionListener
        searchCard.getJTable().getSelectionModel().addListSelectionListener(createListSelectionListener(searchCard.getJTable()));
        //Adding the wathclistCard to an listSelectionListener
        //watchlistCard.getJTable().getSelectionModel().addListSelectionListener(createListSelectionListener(watchlistCard.getJTable()));

        //Essential for the JFrame portion of the window to work:
        setContentPane(getMainPanel());
        setTitle("Analyst window");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private class ButtonListener implements ActionListener{
        /**
         * ButtonListener for all the buttons in the window.
         * @param actionEvent actionEvent
         */
        public void actionPerformed(ActionEvent actionEvent){
            String buttonPressed = actionEvent.getActionCommand();

            //CardLayout administers the different cards
            CardLayout layout = (CardLayout)cardContainer.getLayout();

            if (buttonPressed.equals("Athlete search")) {
                layout.show(cardContainer, "search");

            }

            if (buttonPressed.equals("Watch-list")) {
                System.out.println("Watchlist clicked!");
                layout.show(cardContainer,"watchlist");
                System.out.println("Watchlist displayed!");
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
                //no option
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
                if (!event.getValueIsAdjusting() && searchCard.getJTable().hasFocus()) {//This line prevents double events

                    int row = resultsTable.getSelectedRow();
                    int athleteID = Integer.parseInt((String)resultsTable.getValueAt(row, 3));
                    //Gets the ID from the table and passes it to the method
                    athleteCard = new AthletePageAnalyst(athleteID).getMainPanel();
                    cardContainer.add("athlete", athleteCard);
                    layout.show(cardContainer,"athlete");
                    pack();
                    setLocationRelativeTo(null);
                    setVisible(true);


                    System.out.println(resultsTable.getValueAt(row, 3));
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
