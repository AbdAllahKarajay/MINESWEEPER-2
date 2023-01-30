package game.Controller;

import game.MineSweeper;
import game.View.Board;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class RulesController {
    public static int uncover = 1;
    public static int win = 25;
    public static int sucM = 5;
    public static int unsucM = -40;
    public static int lose = -80;

    public static JLabel r1 = new JLabel();
    public static JLabel r2 = new JLabel();
    public static JLabel r3 = new JLabel();
    public static JLabel r4 = new JLabel();
    public static JLabel r5 = new JLabel();
    public static JLabel mines = new JLabel();

    public static void setRules(){
        r1.setText(RulesController.uncover +" point for uncovering a square");
        r2.setText(RulesController.win +" point for uncovering square to win");
        r3.setText(RulesController.sucM+" point for successful mark");
        r4.setText(RulesController.unsucM + "point for unsuccessful mark");
        r5.setText(RulesController.lose + " point for uncovering mine");
        mines.setText(Board.n_mines + " mines");

        if(MineSweeper.rules != null) MineSweeper.rules.repaint();
    }

    public static void setRulesFromArray(int[] a){
        uncover = a[0];
        win = a[1];
        sucM = a[2];
        unsucM = a[3];
        lose = a[4];
    }

    public static void configAll(){
        File config = new File("config.txt");
        try {
            Scanner scanner = new Scanner(config);
            //mines
            Board.n_mines=scanner.nextInt();

            //rules
            scanner.nextLine();
            int[] rules = new int[5];
            rules[0] = scanner.nextInt();
            scanner.nextLine();
            rules[1] = scanner.nextInt();
            scanner.nextLine();
            rules[2] = scanner.nextInt();
            scanner.nextLine();
            rules[3] = scanner.nextInt();
            scanner.nextLine();
            rules[4] = scanner.nextInt();
            setRulesFromArray(rules);

            scanner.close();
        }catch(Exception ignored){}
    }
}
