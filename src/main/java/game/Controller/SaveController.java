package game.Controller;

import game.MineSweeper;
import game.Model.Save;
import game.View.Board;

import javax.swing.*;
import java.io.*;



public class SaveController {
    public static QuickSaveThread quickSaveThread;

    public static void quickSave() {
        if(quickSaveThread != null) {
            quickSaveThread.stop();
        }
        quickSaveThread = new QuickSaveThread();
        quickSaveThread.start();
    }

    public static class QuickSaveThread extends Thread {
        public void run()
        {
            try{
                save(0);
            }
            catch (Exception e) {
                // Throwing an exception
                System.out.println("Exception is caught");
            }
        }
    }

    public static void load(int fNum){
        try {
            FileInputStream fis = new FileInputStream(MineSweeper.docPath + "/MineSweeper 2.0/save"+ fNum + ".dat");
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);

            Save save = (Save)ois.readObject();
            Board.gameController.field = save.field;
            Board.gameController.game = save.game;
            Board.playerController.current_player = save.player;
            PointsController.setPoints(save.p0, save.p1, save.p2);
            RulesController.setRulesFromArray(save.rules);
            Board.n_mines = save.mines;
            RulesController.setRules();
            PointsController.setRules();
            ois.close();

            if(Board.gameController.isMulti()) {
                GameController.multiThread = new PlayerController.MultiPlayerThread();
                GameController.multiThread.start();
                GameController.multiThread.i = 0;
                MineSweeper.timer.setText("10");
            }else{
                MineSweeper.timer.setText(" ");
                MineSweeper.statusBar.setP2(" ");
                if(GameController.multiThread != null) GameController.multiThread.stop();
            }

            MineSweeper.statusBar.checkMulti();
            MineSweeper.statusBar.turn(Board.playerController.current_player);
            MineSweeper.board.repaint();
        }catch (Exception fileEx){
            noSave(fNum);
        }
    }
    public static void noSave(int fNum){
        JDialog no = new JDialog(MineSweeper.ms, "No Save");
        JLabel msg = new JLabel("There is no save in save " + fNum);
        msg.setBounds(75,60,150,50);
        no.add(msg);
        JButton bt = new JButton("OK");
        bt.setBounds(120,140,60,50);
        bt.addActionListener(e -> {
            no.setVisible(false);
            no.dispose();
        });
        no.add(bt);
        no.setLayout(null);
        no.setLocationRelativeTo(null);
        no.setSize(300,250);
        no.setResizable(false);
        no.setVisible(true);
    }
    public static void save(int fNum){
        try {
            File dir = new File(MineSweeper.docPath + "/MineSweeper 2.0");
            boolean res = true;
            if(!dir.exists())res = dir.mkdir();
            if(!res) return;
            FileOutputStream fos = new FileOutputStream(MineSweeper.docPath + "/MineSweeper 2.0/save"+ fNum + ".dat");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            Save save = new Save();
            save.field = Board.gameController.field;
            save.game = Board.gameController.game;
            save.player = Board.playerController.current_player;
            save.rules = new int[]{RulesController.uncover, RulesController.win, RulesController.sucM, RulesController.unsucM, RulesController.lose};
            save.mines = Board.n_mines;
            save.p0 = PointsController.getP0();
            save.p1 = PointsController.getP1();
            save.p2 = PointsController.getP2();

            oos.writeObject(save);
            oos.close();
        } catch (IOException fileEx){
            fileEx.printStackTrace();
        }
    }
}
