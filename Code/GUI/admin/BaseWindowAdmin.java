package GUI.admin;

import GUI.BaseWindow;
import GUI.main.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Created by camhl on 04.04.2017.
 */
public class BaseWindowAdmin extends BaseWindow{
    private JPanel mainPanel;
    private JButton addUserButton;
    private JButton editUserButton;
    private JButton deleteUserButton;
    private JButton logOutButton;
    private JPanel cardContainer;
    private JPanel buttonPanel;

    private JPanel addUserCard;
    private JPanel startCard;
    private JPanel editUserCard;
    private JPanel deleteUserCard;

    private CardLayout layout;

    public BaseWindowAdmin(){
        ButtonListener actionListener = new ButtonListener();

        addUserButton.addActionListener(actionListener);
        editUserButton.addActionListener(actionListener);
        //deleteUserButton.addActionListener(actionListener);
        logOutButton.addActionListener(actionListener);

        //Add the JPanels from other classes into our window
        addUserCard = new AddAdminUser().getMainPanel();
        startCard = new JPanel();
        //editUserCard = new editAdminUser().getMainPanel();
        //deleteUserCard = new DeleteAdminUser().getMainPanel();

        startCard.setLayout(new FlowLayout());
        startCard.add(new JLabel(new ImageIcon(createImage())));

        //The name here is used when calling the .show() method on CardLayout
        cardContainer.add("Add user", addUserCard);
        cardContainer.add("Start", startCard);
        //cardContainer.add("Edit user", editUserCard);
        //cardContainer.add("Delete user", deleteUserCard);

        //CardLayout administers the different cards
        layout = (CardLayout)cardContainer.getLayout();
        layout.show(cardContainer, "Start");

        setContentPane(getMainPanel());
        setTitle("Admin window");
        pack();
        setVisible(true);
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

            /*else if(buttonPressed.equals("Edit user")){
                layout.show(cardContainer, "Edit user");
            }*/

            else if(buttonPressed.equals("Delete user")){
                layout.show(cardContainer, "Delete user");
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

        JFrame frame = new JFrame("Admin"); //Creating JFrame
        frame.setContentPane(new BaseWindowAdmin().getMainPanel()); //Setting content pane to rootPanel, which shows the window allowing the administrator to add user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //The window will close if you press exit
        frame.pack();  //Creates a window out of all the components
        frame.setVisible(true);   //Setting the window visible
    }
}
