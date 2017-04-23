package GUI.admin;

import GUI.BaseWindow;
import GUI.collector.AthletePageCollector;
import GUI.main.MainWindow;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Created by camhl on 04.04.2017.
 */
public class BaseWindowAdmin extends BaseWindow{
    private BaseWindow frame = this;
    private JPanel mainPanel;
    private JButton addUserButton;
    private JButton editUserButton;
    private JButton deleteUserButton;
    private JButton logOutButton;
    private JPanel cardContainer;
    private JPanel buttonPanel;

    private JPanel addUserCard;
    private UserSearchPanel searchCard;
    //private EditUser editUserWindow;
    private JPanel deleteUserCard;

    private CardLayout layout;

    public BaseWindowAdmin(){
        ButtonListener actionListener = new ButtonListener();

        addUserButton.addActionListener(actionListener);
        editUserButton.addActionListener(actionListener);
        //deleteUserButton.addActionListener(actionListener);
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

    //Adds a listener to the table
    ListSelectionListener createListSelectionListener(JTable resultsTable) {
        ListSelectionListener listener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                //Keeps it from firing twice (while value is adjusting as well as when it is done)
                if (!event.getValueIsAdjusting() && searchCard.getJTable().hasFocus()) {//This line prevents double events

                    int row = resultsTable.getSelectedRow();

                    String username = (String)resultsTable.getValueAt(row, 0);
                    System.out.println("Selected row username: "+username);
                    //Gets the ID from the table and passes it to the method
                    EditUser editUserWindow = new EditUser(username, frame);
                    //layout.show(cardContainer,"username");
                    editUserWindow.pack();
                    editUserWindow.setVisible(true);



                    //System.out.println(resultsTable.getValueAt(row, 2));
                    // System.out.println(resultsTable.getValueAt(resultsTable.getSelectedRow(), 3));
                }
            }
        };
        return listener;
    }


    protected Image createImage() {
        //Create a 200x200 pixel image.
        BufferedImage bi = new BufferedImage(500, 200, BufferedImage.TYPE_INT_RGB);

        //Draw into it.
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 500, 200);
        g.setColor(Color.BLACK);
        Font font = new Font("Verdana", Font.BOLD, 20);
        g.setFont(font);
        g.drawString("You are now logged in as admin ..", 30, 50);
        //g.fillOval(5, 3, 6, 6);

        //Clean up.
        g.dispose();

        //Return it.
        return bi;
    }

    public class ButtonListener implements ActionListener {
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

    public JPanel getMainPanel(){
        return mainPanel;
    }

    public static void main(String[]args){

        BaseWindowAdmin frame = new BaseWindowAdmin(); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}
