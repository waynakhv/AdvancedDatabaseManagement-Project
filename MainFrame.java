package handlers;

import panels.MainPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final int width = 900;
    private final int height = 600;

    MainPanel mainPanel;

    public MainFrame() {
        super("Advanced Database Project");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setBounds((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - width) / 2, (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - height) / 2, width, height);

        this.setLayout(null);


        mainPanel = new MainPanel();

        this.setContentPane(mainPanel);


        this.setVisible(true);
    }

    public MainPanel getMainPanel(){
        return mainPanel;
    }

}
