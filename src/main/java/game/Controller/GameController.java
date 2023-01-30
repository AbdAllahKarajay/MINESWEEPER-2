package game.Controller;

import game.Model.Cell;
import game.Model.Field;
import game.Model.Game;
import game.View.Board;
import game.MineSweeper;
import game.View.StatusBar;

import java.awt.*;

public class GameController{
    public Game game;
    public Field field;
    public static PlayerController.MultiPlayerThread multiThread;


    public boolean isInGame() {
        return game.inGame;
    }

    public void Start(boolean multi) {
        MineSweeper.statusBar.p1.setForeground(Color.white);
        MineSweeper.statusBar.p2.setForeground(Color.white);

        game = new Game(multi);
        Board.playerController.setCurrent_player(0);
        PointsController.reset();
        field = new Field();
        RulesController.configAll();
        RulesController.setRules();
        PointsController.setRules();
        field.cells = new Cell[Board.rows][Board.cols];

        for (int i = 0; i < Board.rows; i++) {
            for (int j = 0; j < Board.cols; j++) {
                field.cells[i][j] = new Cell();
            }
        }

        MineSweeper.statusBar.setP1("mines left: " + game.left1_mines);
        MineSweeper.statusBar.setP2("");
        MineSweeper.statusBar.checkMulti();


        if(MineSweeper.rules != null) MineSweeper.rules.repaint();
        if(multiThread != null) {
            multiThread.stop();
            MineSweeper.timer.setText(" ");
        }
        if(isMulti()){
            multiThread = new PlayerController.MultiPlayerThread();
            multiThread.start();
            game.hasLoss = false;
            MineSweeper.statusBar.setP2("mines left: " + game.left2_mines);
            MineSweeper.timer.setForeground(Color.black);
        }
    }

    public void decideWinner() {
        MineSweeper.statusBar.updatePoints();
        if(PointsController.p1 > PointsController.p2){
            MineSweeper.statusBar.setP1("You Won");
            MineSweeper.statusBar.p1.setForeground(Color.green);
            MineSweeper.statusBar.setP2("You Lost");
            MineSweeper.statusBar.p2.setForeground(Color.red);
        }else if(PointsController.p1 < PointsController.p2){
            MineSweeper.statusBar.setP1("You Lost");
            MineSweeper.statusBar.p1.setForeground(Color.red);
            MineSweeper.statusBar.setP2("You Won");
            MineSweeper.statusBar.p2.setForeground(Color.green);
        }else{
            MineSweeper.statusBar.setP1("DRAW");
            MineSweeper.statusBar.setP2("DRAW");
            MineSweeper.statusBar.p1.setForeground(Color.green);
            MineSweeper.statusBar.p2.setForeground(Color.green);
        }
    }

    public void end() {
        game.inGame = false;
    }
    public boolean isMulti(){
        return game.multi;
    }
    public boolean hasLoss(){
        return game.hasLoss;
    }
    public void Loss(){
        game.hasLoss = true;
    }

    public void setMines1(int x) {
        game.left1_mines = x;
    }
    public int getMines1() {
        return game.left1_mines;
    }
    public void setMines2(int x) {
        game.left2_mines = x;
    }
    public int getMines2() {
        return game.left2_mines;
    }

    public void setLoss(boolean v){
        game.hasLoss = v;
    }
}