package GUI.analyst;

import GUI.BaseWindow;
import GUI.login.LoginWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

//import databaseConnectors.SearchHelp;


/**
 * Created by camhl on 31.03.2017.
 */
public class BaseWindowAnalyst extends JPanel {
    private JPanel rootPanel;
    private JButton athleteSearchButton;
    private JButton logOutButton;
    private JButton watchListButton;
    private JButton button4;
    private JPanel topPanel;
    //Every time we use CardLayout, the JPanel containing it should be named cardContainer
    private JPanel cardContainer;

    //Cards that need acces from other methods
    private JPanel searchCard;
    private JPanel watchlistCard;

    public BaseWindowAnalyst(){

        ButtonListener actionListener = new ButtonListener();

        athleteSearchButton.addActionListener(actionListener);
        watchListButton.addActionListener(actionListener);
        logOutButton.addActionListener(actionListener);

        //Add the JPanels from other classes into our window
        searchCard = new AthleteSearchPanel().getMainPanel();
        System.out.println("Making watchlist card");
        watchlistCard = new WatchlistPanel(LocalDate.now()).getMainPanel();
        //The name here is used when calling the .show() method on CardLayout
        cardContainer.add("search", searchCard);
        cardContainer.add("watchlist", watchlistCard);

    }

    private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent actionEvent){
            String buttonPressed = actionEvent.getActionCommand();

            //CardLayout administers the different cards
            CardLayout layout = (CardLayout)cardContainer.getLayout();

            if (buttonPressed.equals("Athlete search")) {
                System.out.println("Athlete search clicked!");
                layout.show(cardContainer, "search");
            }

            else if (buttonPressed.equals("Watch-list")) {
                System.out.println("Watchlist clicked!");
                layout.show(cardContainer,"watchlist");
            }

            else if(buttonPressed.equals("Log out")) {
                int option = JOptionPane.YES_NO_OPTION;

                if ((JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "WARNING", option)) == JOptionPane.YES_OPTION) {
                    //yes option
                    new LoginWindow("Login").setLoggedin(false);
                }
                //no option
            }

        }


    }
    public JPanel getMainPanel() {
        return rootPanel;
    }


    public static void main(String[]args){
        //BaseWindow window = new BaseWindow();
        BaseWindow window = new BaseWindow();
        window.setContentPane(new BaseWindowAnalyst().getMainPanel());
        window.pack();  //Creates a window out of all the components
        window.setVisible(true);   //Setting the window visible
    }
}
