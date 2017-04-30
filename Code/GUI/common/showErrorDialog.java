package GUI.common;

import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by Toni on 29.04.2017.
 */
public class showErrorDialog {

    showErrorDialog(String text, Exception e) {
        showMessageDialog(null,text+e,"Error",JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        new showErrorDialog("SQL ERROR! ",new NullPointerException());
    }

}
