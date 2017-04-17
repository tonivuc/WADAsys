package GUI;
import javax.swing.*;

public class BaseWindow extends JFrame {

    protected boolean isLoggedOut;

    //Constructor with title
    public BaseWindow(String windowTitle) {
        super(windowTitle);
        setup();
    }

    //Constructor without title

    public BaseWindow() {
        setup();
    }

    //Things that are common for both constructors, that means: All windows.
    private void setup() {
        setSize(600, 600);
        //setLocation(700, 300); Improvised way to center the window? -Toni
        //Center window
        this.setLocationRelativeTo(null); //Better way to center
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    protected void disposeFrame() {
        dispose();
    }

    public static void main(String[] args) {
        //BaseWindow noStringWindow = new BaseWindow();
        BaseWindow testBase = new BaseWindow("HellO!");
    }
}