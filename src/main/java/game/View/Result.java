package game.View;

import game.Controller.PlayerController;
import game.Controller.PointsController;
import game.MineSweeper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class Result extends JDialog {
    Result(Boolean win, boolean multi){
        int width = 300;
        int height = 400;
        BufferedImage backBImage = null;
        try {
            backBImage = win?
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/win.png"))):
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/lose.png")));
        }catch(Exception ignored){}
        assert backBImage != null;
        Image backImage = backBImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        JLabel background = new JLabel(new ImageIcon(backImage));
        setContentPane(background);
        setLayout(null);
        JLabel res = new JLabel();
        res.setFont(new Font("SansSerif", Font.BOLD, 16));
        res.setBounds(100,15,200,35);

        if(multi){
            if(PointsController.getP1() > PointsController.getP2()){
                res.setText("Player 1 Won");
                res.setForeground(Color.cyan);
            }
            else if (PointsController.getP1() < PointsController.getP2()){
                res.setText("Player 2 Won");
                res.setForeground(Color.orange);
            }
            else{
                res.setForeground(Color.green);
                res.setText("A Draw!");
                res.setBounds(110,15,200,35);
            }

            JLabel p1 = new JLabel("Player 1 scored: " + PointsController.getP1());
            JLabel p2 = new JLabel("Player 2 scored: " + PointsController.getP2());
            p1.setBounds(75,45,200,50);
            p2.setBounds(75,75,200,50);
            p1.setForeground(Color.white);
            p2.setForeground(Color.white);
            p1.setFont(new Font("SansSerif", Font.BOLD, 16));
            p2.setFont(new Font("SansSerif", Font.BOLD, 16));
            add(p1);
            add(p2);
        }
        else{
            res.setText(win? "Congrats You Won" : "GameOver You Lost");
            res.setForeground(win? Color.green : Color.red);
            res.setBounds(65,20,200,35);


            JLabel p1 = new JLabel("You scored: " + PointsController.getP0());
            p1.setBounds(85,70,200,50);
            p1.setFont(new Font("SansSerif", Font.BOLD, 16));
            p1.setForeground(Color.white);
            add(p1);
        }

        add(res);
        new Thread(() -> {
            try {
                sleep(100);
                ButtonMisc.playSound(win? "win.wav":"lose.wav");
            } catch (InterruptedException e) {
                  e.printStackTrace();
            }
        }).start();

        setVisible(true);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setLocation(650,200);
        setSize(width,height)   ;
    }
}
