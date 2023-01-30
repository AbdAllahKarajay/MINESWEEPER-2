package game.Model;


import game.View.Board;

import java.io.Serializable;

public class Game implements Serializable {
    public boolean inGame = true;
    public int left1_mines;
    public int left2_mines;
    public boolean hasLoss = false;
    public boolean multi;
    public boolean Win;


    public Game(boolean multi){
        this.multi = multi;
        left1_mines = Board.n_mines;
        left2_mines = Board.n_mines;
    }
}
