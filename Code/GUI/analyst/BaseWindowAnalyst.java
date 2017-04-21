package GUI.analyst;

import GUI.BaseWindow;
import GUI.admin.Profile;
import GUI.athlete.AthleteSearchPanel;
import GUI.collector.AthletePageCollector;
import GUI.login.LoginWindow;
import GUI.main.MainWindow;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

//import databaseConnectors.SearchHelp;


/**
 * Created by camhl on 31.03.2017.
 */
public class BaseWindowAnalyst extends BaseWindow {
    private JPanel rootPanel;
    private JButton athleteSearchButton;
    private JButton logOutButton;
    private JButton watchListButton;
    private JButton profileButton;
    private JPanel topPanel;
    //Every time we use CardLayout, the JPanel containing it should be named cardContainer
    private JPanel cardContainer;

    //Cards that need acces from other methods
    private JPanel searchCard;
    private JPanel watchlistCard;
    private JPanel profileCard;
    private JPanel athleteCard;
    private CardLayout layout;

    public BaseWindowAnalyst(String username){

        ButtonListener actionListener = new ButtonListener();

        athleteSearchButton.addActionListener(actionListener);
        watchListButton.addActionListener(actionListener);
        profileButton.addActionListener(actionListener);
        logOutButton.addActionListener(actionListener);

        //Add the JPanels from other classes into our window
        searchCard = new AthleteSearchPanel();
        watchlistCard = new WatchlistPanel(LocalDate.now()).getMainPanel();
        profileCard = new Profile(username).getMainPanel();
        //The name here is used when calling the .show() method on CardLayout
        cardContainer.add("search", searchCard);
        cardContainer.add("watchlist", watchlistCard);
        cardContainer.add("profile", profileCard);

        //searchCard.getJTable().getSelectionModel().addListSelectionListener(createListSelectionListener(searchCard.getJTable()));
        layout = (CardLayout)cardContainer.getLayout();

        //Essential for the JFrame portion of the window to work:
        setContentPane(getMainPanel());
        setTitle("Analyst window");
        pack();
        setVisible(true);

    }

    private class ButtonListener implements ActionListener{
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
        //BaseWindow window = new BaseWindow();
        BaseWindowAnalyst window = new BaseWindowAnalyst("Analyst");
    }
}
