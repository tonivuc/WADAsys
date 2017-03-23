package login;
import chart.HaemoglobinChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.sun.javafx.fxml.expression.Expression.add;

/**
 * Created by Toni on 16.03.2017. Oppdatert i dag.
 */
public class LoginWindow extends BaseWindow {

    public LoginWindow(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Establishing layout
        LayoutManager layout = new BorderLayout();
        setLayout(layout);

        JPanel topContainer = new JPanel();
        JPanel centerContainer = new JPanel();
        JPanel bottomContainer = new JPanel();
        JPanel rightMargin = new JPanel();
        JPanel leftMargin = new JPanel();

        //Creating layout items
        JLabel headerText = new JLabel("WADA Monitoring System");

        JTextField brukernavnInput = new JTextField("username",10);
        JPasswordField passwordField = new JPasswordField("password",10);
        passwordField.setEchoChar((char)0);
        JButton submitKnapp = new JButton("Submit");

        //Add layout items to layout
        add(topContainer, BorderLayout.NORTH);
        topContainer.add(headerText, BorderLayout.CENTER);
        add(centerContainer,BorderLayout.CENTER);
        centerContainer.add(brukernavnInput, BorderLayout.NORTH);
        centerContainer.add(passwordField, BorderLayout.CENTER);
        centerContainer.add(submitKnapp, BorderLayout.SOUTH);


        //Listeners
        //Used to clear the default text when the user wants to type his username
        brukernavnInput.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (brukernavnInput.getText().equals("username")) {
                    brukernavnInput.setText(null);
                }

            }

            public void focusLost(FocusEvent e) {
                //...
            }
        });

        //Used to clear the default text when the user wants to type his password
        passwordField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("password")) {
                    passwordField.setText(null);
                }
                passwordField.setEchoChar('*');

            }

            public void focusLost(FocusEvent e) {
                //...
            }
        });




        pack();

        //Liten "hack" som gjør at teksten ikke fjernes fra headerText
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                headerText.grabFocus();
                headerText.requestFocus();//or inWindow
            }
        });

    }

    public static void main(String[] args) {
        LoginWindow aWindow = new LoginWindow("Login");
        aWindow.setVisible(true);
    }
}
