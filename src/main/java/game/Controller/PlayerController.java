package game.Controller;

import game.MineSweeper;
import game.View.Board;
import game.View.ButtonMisc;

import java.awt.*;

public class PlayerController {
    public int current_player= 0;

    void setCurrent_player(int p){current_player = p;}

    public static class MultiPlayerThread extends Thread {
        public int i;
        public void run()
        {
            try{
                while (Board.gameController.isInGame() && !Board.gameController.hasLoss()) {
                    for (i = 1; i <11; i++) {
                        if(!Board.gameController.isInGame()) return;
                        MineSweeper.timer.setText(Integer.toString(10-i));
                        if(i>4) ButtonMisc.playSound("tick.wav");
                        sleep(1000);
                    }
                    ButtonMisc.playSound("timeup.wav");
                    Board.playerController.toggle_player();
                }
            }
            catch (Exception e) {
                // Throwing an exception
                System.out.println("Exception is caught");
            }
        }
    }

    public void toggle_player() {
        if (current_player == 2){
            current_player--;
        }
        else {
            current_player++;
        }
        MineSweeper.statusBar.turn(current_player);

    }


    public void loss() {
        if(current_player == 1){
            MineSweeper.statusBar.setP1("You are out");
            MineSweeper.statusBar.p1.setForeground(Color.black);
        }else{
            MineSweeper.statusBar.setP2("You are out");
            MineSweeper.statusBar.p2.setForeground(Color.black);
        }
        MineSweeper.timer.setForeground(Color.gray);
        Board.gameController.setLoss(true);
        Board.gameController.Loss();
    }
}