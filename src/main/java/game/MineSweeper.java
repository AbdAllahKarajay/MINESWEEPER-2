package game;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import game.Controller.GameController;
import game.Controller.SaveController;
import game.View.*;
import game.View.MenuBar;

/**
 *
 * @author AbdAllah-Karajay
 */


public class MineSweeper extends JFrame {
    public static final String docPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
    public static Board board;
    public static StatusBar statusBar;
    public static JPanel rules;
    public static JLabel timer;



    private LookAndFeel nimbus;

    public JButton generateNimbusButton() {
        try {
            LookAndFeel current = UIManager.getLookAndFeel(); //capture the current look and feel

            if (nimbus == null) { //only initialize Nimbus once
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
                nimbus = UIManager.getLookAndFeel();
            } else {
                UIManager.setLookAndFeel(nimbus); //set look and feel to nimbus
            }
            JButton button = new JButton(); //create the button
            UIManager.setLookAndFeel(current); //return the look and feel to its original state
            return button;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            return new JButton();
        }
    }

    public MineSweeper() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException ignored) {}


        PreGame preGame = new PreGame();

        int width = Board.width + 30;
        int height = Board.height + StatusBar.height + 100;
        BufferedImage backBImage = null;
        try {
            backBImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/background2.png")));
        }catch(Exception ignored){}
        assert backBImage != null;
        Image backImage = backBImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        JLabel background = new JLabel(new ImageIcon(backImage));
        setContentPane(background);
        setLayout(null);

        //sidePanel---------------------------------------------------------------------------------------------------



        //main panel---------------------------------------------------------------------------------------------------
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(null);
        statusBar = new StatusBar();
        statusBar.setBounds(0,0,width-30,StatusBar.height);
        mainPanel.add(statusBar);
        timer = new JLabel(" ");
        timer.setFont(new Font("SansSerif", Font.BOLD, 14));
        timer.setBounds(width/2 -32 , StatusBar.height -5 ,30,25);
        timer.setForeground(Color.white);
        mainPanel.add(timer);
        board = new Board();
        board.setBounds(0,StatusBar.height+25,Board.width,Board.height);
        mainPanel.add(board);
        mainPanel.setBounds(7,0,width,height);
        add(mainPanel);



        setVisible(false);
        setResizable(false);
        setSize(width,height);
        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                try {
                    preGame.setVisible(true);
                    if(GameController.multiThread != null) GameController.multiThread.stop();
                } catch (Exception ignored){}
            }
        });
        preGame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                try {
                    if(SaveController.quickSaveThread != null) SaveController.quickSaveThread.join();
                } catch (Exception ignored){}
            }
        });

        setJMenuBar(new MenuBar());
    }
    public static MineSweeper ms;
    public static void main(String[] args) {
        ms = new MineSweeper();
    }
}
