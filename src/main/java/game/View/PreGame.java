package game.View;

import game.Controller.RulesController;
import game.Controller.SaveController;
import game.MineSweeper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Objects;
import java.util.Scanner;

public class PreGame extends JFrame{
    public PreGame(){
        try{
            SwingUtilities.updateComponentTreeUI(this);
        }catch(Exception e){e.printStackTrace();}

        BufferedImage backBImage = null;
        try {
            backBImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/background.png")));
        }catch(Exception ignored){}
        assert backBImage != null;
        Image backImage = backBImage.getScaledInstance(800, 700, Image.SCALE_DEFAULT);
        JLabel background = new JLabel(new ImageIcon(backImage));
        setContentPane(background);
        setLayout(null);
        setSize(800,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //SinglePlayer
        JButton single = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/SP.png"))));
        ButtonMisc.HoverAndPress(single, "SP", true);
        single.setOpaque(false);
        single.setContentAreaFilled(false);
        single.setBorderPainted(false);
        single.setBounds(400 - 200 -15, 470,210,63);
        single.addActionListener(e -> {
            setVisible(false);
            dispose();
            MineSweeper.ms.setVisible(true);
            try{
                File config = new File("config.txt");
                File temp = new File("temp.txt");
                FileWriter fw = new FileWriter(temp);
                BufferedWriter bw = new BufferedWriter(fw);

                //first line
                Scanner scanner = new Scanner(config);
                bw.write(Integer.toString(Board.n_mines));
                scanner.nextInt();
                bw.write(scanner.nextLine());

                //rest
                String str;
                while(scanner.hasNextLine()){
                    bw.newLine();
                    str = scanner.nextLine();
                    bw.write(str);
                }
                bw.flush();
                bw.close();
                scanner.close();

                config.delete();
                temp.renameTo(config);
                RulesController.setRules();
            }catch (Exception xe){
                xe.printStackTrace();
            }
            Board.gameController.Start(false);
            MineSweeper.board.paintComponent(MineSweeper.board.getGraphics());
        });
        add(single);

        //MultiPlayer
        JButton multi = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/MP.png"))));
        ButtonMisc.HoverAndPress(multi, "MP", true);
        multi.setOpaque(false);
        multi.setContentAreaFilled(false);
        multi.setBorderPainted(false);
        multi.setBounds(400 + 15, 470,210,63);
        multi.addActionListener(e -> {
            setVisible(false);
            dispose();
            MineSweeper.ms.setVisible(true);
            try{
                File config = new File("config.txt");
                File temp = new File("temp.txt");
                FileWriter fw = new FileWriter(temp);
                BufferedWriter bw = new BufferedWriter(fw);

                //first line
                Scanner scanner = new Scanner(config);
                bw.write(Integer.toString(Board.n_mines));
                scanner.nextInt();
                bw.write(scanner.nextLine());

                //rest
                String str;
                while(scanner.hasNextLine()){
                    bw.newLine();
                    str = scanner.nextLine();
                    bw.write(str);
                }
                bw.flush();
                bw.close();
                scanner.close();

                config.delete();
                temp.renameTo(config);
                RulesController.setRules();
            }catch (Exception xe){
                xe.printStackTrace();
            }
            Board.gameController.Start(true);
            MineSweeper.board.paintComponent(MineSweeper.board.getGraphics());
        });
        add(multi);

        //Last Game
        JButton LoadLast = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/LG.png"))));
        ButtonMisc.HoverAndPress(LoadLast, "LG", true);
        LoadLast.setOpaque(false);
        LoadLast.setContentAreaFilled(false);
        LoadLast.setBorderPainted(false);
        LoadLast.setBounds(400 -100, 550,210,63);
        LoadLast.addActionListener(e -> {
            setVisible(false);
            dispose();
            MineSweeper.ms.setVisible(true);
            try{
                File config = new File("config.txt");
                File temp = new File("temp.txt");
                FileWriter fw = new FileWriter(temp);
                BufferedWriter bw = new BufferedWriter(fw);

                //first line
                Scanner scanner = new Scanner(config);
                bw.write(Integer.toString(Board.n_mines));
                scanner.nextInt();
                bw.write(scanner.nextLine());

                //rest
                String str;
                while(scanner.hasNextLine()){
                    bw.newLine();
                    str = scanner.nextLine();
                    bw.write(str);
                }
                bw.flush();
                bw.close();
                scanner.close();

                config.delete();
                temp.renameTo(config);
                RulesController.setRules();
            }catch (Exception xe){
                xe.printStackTrace();
            }
            SaveController.load(0);
        });
        add(LoadLast);

        //nof mines config
        RulesController.configAll();
        JLabel num = new JLabel(Integer.toString(Board.n_mines));
        num.setFont(new Font("SansSerif", Font.BOLD, 16));
        num.setForeground(Color.white);
        setNumColor(num);

        JButton add = new JButton();
        JButton sub = new JButton();

        add.addActionListener(e -> {
            if(Board.n_mines == 390) return;
            Board.n_mines += 10;
            num.setText(Integer.toString(Board.n_mines));
            setNumColor(num);
        });
        sub.addActionListener(e -> {
            if(Board.n_mines == 10) return;
            Board.n_mines -= 10;
            num.setText(Integer.toString(Board.n_mines));
            setNumColor(num);
        });


        add.setOpaque(false);
        add.setContentAreaFilled(false);
        add.setBorderPainted(false);
        sub.setOpaque(false);
        sub.setContentAreaFilled(false);
        sub.setBorderPainted(false);
        ButtonMisc.HoverAndPress(add,"up", false);
        ButtonMisc.HoverAndPress(sub,"down", false);

//        num.setBorder(BorderFactory.createTitledBorder("Mines"));
        JLabel mine = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/mine.png"))));


        mine.setBounds(680 + 35, 555,30,30);
        num.setBounds(680, 550,70,40);
        add.setBounds(680 - 40, 540,30,30);
        sub.setBounds(680 - 40, 575,30,30);

        add(mine);
        add(num);
        add(add);
        add(sub);
        setVisible(true);
        setResizable(false);

    }

    void setNumColor(JLabel num){
        if(Board.n_mines < 60) num.setForeground(Color.green);
        else if(Board.n_mines < 110) num.setForeground(Color.yellow);
        else if(Board.n_mines < 180) num.setForeground(Color.orange);
        else if(Board.n_mines < 250) num.setForeground(Color.red);
        else num.setForeground(Color.black);
    }
}
