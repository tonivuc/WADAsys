package GUI.admin;

import GUI.BaseWindow;
import GUI.main.MainWindow;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Camilla Haaheim Larsen
 */

public class BaseWindowAdmin extends BaseWindow{

    private BaseWindow frame = this;

    /**
     * The mainPanel of this panel.
     */
    private JPanel mainPanel;

    /**
     * The button the Admin presses if he/she wants to add a new user.
     */
    private JButton addUserButton;

    /**
     * The button the Admin presses if he/she wants to edit a already existing user.
     */
    private JButton editUserButton;

    /**
     * The button the Admin presses if he/she wants to delete a user.
     */
    private JButton deleteUserButton;

    /**
     * The button the Admin presses if he/she wants to log out.
     */
    private JButton logOutButton;

    /**
     * The container that holds cards.
     */
    private JPanel cardContainer;

    /**
     * The panel that all the main buttons are inside.
     */
    private JPanel buttonPanel;

    /**
     * The card that holds the add user functionality.
     */
    private JPanel addUserCard;

    /**
     * The card that hold the searchPanel.
     */
    private UserSearchPanel searchCard;

    /**
     * The card that holds the delete user functionality
     */
    private JPanel deleteUserCard;

    /**
     * The layout of the different cards.
     */
    private CardLayout layout;

    /**
     * Constructs a new BaseWindow for the Admin user. This BaseWindow contains a menu
     * bar at the top with different buttons that the Admin presses to enter the different functionalities.
     */
    public BaseWindowAdmin(){

        ButtonListener actionListener = new ButtonListener();

        addUserButton.addActionListener(actionListener);
        editUserButton.addActionListener(actionListener);
        logOutButton.addActionListener(actionListener);

        //Add the JPanels from other classes into our window
        addUserCard = new AddUser().getMainPanel();
        searchCard = new UserSearchPanel();

        //The name here is used when calling the .show() method on CardLayout
        cardContainer.add("Add user", addUserCard);
        cardContainer.add("search",searchCard);

        //CardLayout administers the different cards
        layout = (CardLayout)cardContainer.getLayout();
        layout.show(cardContainer, "search");

        searchCard.getJTable().getSelectionModel().addListSelectionListener(createListSelectionListener(searchCard.getJTable()));

        setContentPane(getMainPanel());
        setTitle("Admin window");
        pack();
        setVisible(true);
    }

    /**
     * Adds a ActionListener to the searchpanel.
     * @param resultsTable JTable with the different results.
     * @return Listener
     */
    ListSelectionListener createListSelectionListener(JTable resultsTable) {
        ListSelectionListener listener = new ListSelectionListener() {

            /**
             * Checks if a value is changed.
             * @param event the selection event
             */
            public void valueChanged(ListSelectionEvent event) {
                //Keeps it from firing twice (while value is adjusting as well as when it is done)
                if (!event.getValueIsAdjusting() && searchCard.getJTable().hasFocus()) {//This line prevents double events

                    int row = resultsTable.getSelectedRow();

                    String username = (String)resultsTable.getValueAt(row, 0);
                    System.out.println("Selected row username: "+username);
                    //Gets the ID from the table and passes it to the method
                    EditUser editUserWindow = new EditUser(username, frame);
                    //layout.show(cardContainer,"username");
                    editUserWindow.setLocation(600, 100); //Improvised way to center the window? -Toni
                    editUserWindow.pack();
                    editUserWindow.setVisible(true);



                    //System.out.println(resultsTable.getValueAt(row, 2));
                    // System.out.println(resultsTable.getValueAt(resultsTable.getSelectedRow(), 3));
                }
            }
        };
        return listener;
    }


    public class ButtonListener implements ActionListener {
        /**
         * Checks which button is being pressed
         * @param actionEvent the event of the action prefromed
         */
        public void actionPerformed(ActionEvent actionEvent) {
            String buttonPressed = actionEvent.getActionCommand();

            if(buttonPressed.equals("Add user")){
                layout.show(cardContainer,"Add user");
            }

            else if(buttonPressed.equals("Edit/delete users")){
                layout.show(cardContainer, "search");
            }

            else if(buttonPressed.equals("Log out")){
                int option = JOptionPane.YES_NO_OPTION;

                if((JOptionPane.showConfirmDialog (null, "Are you sure you want to log out?","WARNING", option)) == JOptionPane.YES_OPTION){
                    //yes option
                    new MainWindow();
                    dispose();
                }
                //no option
            }
        }
    }

    /**
     * Returns the mainPanel.
     * @return JPanel
     */
    public JPanel getMainPanel(){
        return mainPanel;
    }

}
