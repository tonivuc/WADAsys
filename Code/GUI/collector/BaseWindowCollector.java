package GUI.collector;

import GUI.BaseWindow;
import GUI.analyst.AthleteSearchPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by camhl on 31.03.2017.
 */
public class BaseWindowCollector extends BaseWindow {
    private JPanel rootPanel;
    private JPanel searchCardContainer;
    private JPanel athleteCardContainer;
    private JButton testingRequestsButton;
    private JButton logOutButton;
    private JButton searchButton;

    private JPanel searchCard;
    private JPanel athleteCard;

    public BaseWindowCollector(){

        super();


        //Add the JPanels from other classes into our window
        searchCard = new AthleteSearchPanel().getMainPanel();
        athleteCard = new AthletePanelCollector().getMainPanel();
        //The name here is used when calling the .show() method on CardLayout
        searchCardContainer.add("search", searchCard);
        athleteCardContainer.add("athlete", athleteCard);

        ButtonListener buttonListener = new ButtonListener();

        //sets the submitButton as default so that when enter is presset the Actionevent runs
        //getRootPane().setDefaultButton(searchButton);

        //Adds the submitbutton to an actionlistener.
        searchButton.addActionListener(buttonListener);

    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            String buttonPressed = actionEvent.getActionCommand();
            CardLayout layout = (CardLayout)searchCardContainer.getLayout();


            if(buttonPressed.equals("Search for athlete")){

                layout.show(searchCardContainer,"search");

            }

            else if(buttonPressed.equals("Testing requests")){



            }

            else if(buttonPressed.equals("Log out")){


            }
        }
    }

    public JPanel getMainPanel() {
        return rootPanel;
    }

    public static void main(String[]args){
        JFrame frame = new JFrame("Base Window"); //Creating JFrame
        frame.setContentPane(new BaseWindowCollector().rootPanel); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}