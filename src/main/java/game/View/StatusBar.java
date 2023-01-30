package game.View;


import game.Controller.PointsController;
import game.Controller.RulesController;
import game.MineSweeper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;


public class StatusBar extends JPanel {

    public final JLabel p1 = new JLabel("");
    public final JLabel p2 = new JLabel("");
    private final JLabel p1Label = new JLabel("");
    private final JLabel p2Label = new JLabel("");
    private final JLabel p1points = new JLabel("");
    private final JLabel p2points = new JLabel("");

    public static final int height = 60;

    public StatusBar() {
        super();
        setOpaque(false);
        setSize(Board.cols * Board.cell_size, height);
        setLayout(null);
        JPanel player1 = new JPanel();
        JPanel player2 = new JPanel();
        player1.setOpaque(false); player2.setOpaque(false);
        // ver layout
        player1.setLayout(new BoxLayout(player1, BoxLayout.Y_AXIS));
        player2.setLayout(new BoxLayout(player2, BoxLayout.Y_AXIS));

        //fixed size
        Dimension dm = new Dimension(120, height);
        player1.setPreferredSize(dm);
        player2.setPreferredSize(dm);


        p1Label.setForeground(Color.white);
        p1.setForeground(Color.white);
        p1points.setForeground(Color.white);
        player1.add(p1Label);
        player1.add(p1);
        player1.add(p1points);
        player1.setBounds(10,0,100,height);
        add(player1);

        p2Label.setForeground(Color.white);
        p2.setForeground(Color.white);
        p2points.setForeground(Color.white);
        player2.add(p2Label);
        player2.add(p2);
        player2.add(p2points);
        player2.setBounds(400,0,100,height);
        add(player2);

        JButton single = new JButton();
        ButtonMisc.HoverAndPress(single, "SP", 120, 31);
        single.setOpaque(false);
        single.setContentAreaFilled(false);
        single.setBorderPainted(false);
        single.setBounds(115,height/2,120,31);
        single.addActionListener(e -> {
            Board.gameController.Start(false);
            MineSweeper.board.paintComponent(MineSweeper.board.getGraphics());
        });
        add(single);

        JButton multiplayer = new JButton();
        ButtonMisc.HoverAndPress(multiplayer, "MP", 120, 31);
        multiplayer.setOpaque(false);
        multiplayer.setContentAreaFilled(false);
        multiplayer.setBorderPainted(false);
        multiplayer.setBounds(260,height/2,120,31);
        multiplayer.addActionListener(e -> {
            Board.gameController.Start(true);
            MineSweeper.board.paintComponent(MineSweeper.board.getGraphics());
        });
        add(multiplayer);

        JLabel mines = new JLabel("" + Board.n_mines);
        mines.setForeground(Color.white);
        mines.setBounds(185, 100, 50, 50);

    }

    public void setP1(String text) {
        p1.setText(text);
    }
    public void setP2(String text) {
        p2.setText(text);
    }
    public void checkMulti(){
        if(Board.gameController.isMulti()){
            p1Label.setText("Player 1");
            p2Label.setText("Player 2");
            updatePoints();
        }else{

            p1Label.setText("");
            p2Label.setText("");
            p1points.setText("Points: 0");
            p2points.setText("");
        }
    }

    public void turn(int player){
        if(player == 1){
            p1Label.setForeground(Color.cyan);
            p2Label.setForeground(Color.white);
            MineSweeper.timer.setForeground(Color.cyan);
        }else if(player == 2) {
            MineSweeper.timer.setForeground(Color.orange);
            p1Label.setForeground(Color.white);
            p2Label.setForeground(Color.orange);
        }else MineSweeper.timer.setForeground(Color.white);
    }

    public void updatePoints(){
        if(Board.gameController.isMulti()){
            p1points.setText("Points: " + PointsController.getP1());
            p2points.setText("Points: " + PointsController.getP2());
        }else{
            p1points.setText("Points: " + PointsController.getP0());
        }
    }
}
