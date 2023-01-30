package game.View;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import game.MineSweeper;
import game.Model.Cell;
import game.Controller.*;
import game.Controller.SaveController;

import static java.lang.Thread.sleep;

public class Board extends JPanel {
    public final static int rows = 20;
    public final static int cols = 20;
    public static int n_mines = 50;
    public static final int cell_size = 25;


    public static final int n_images = 37;

    Image[] img;

    FieldController fieldController;
    public static GameController gameController;
    public static PlayerController playerController;
    public static final int spaceX = 1;
    public static final int spaceY = 2;
    public static final int width = cols  * cell_size + spaceX * rows;
    public static final int height = rows * cell_size + spaceY * cols;

    public Board() {
        gameController = new GameController();
        playerController = new PlayerController();
        fieldController = new FieldController();
        initBoard();
        gameController.Start(false);
    }

    private void initBoard() {

        setSize(width, height);

        img = new Image[n_images];
        for (int i = 0; i < n_images; i++) {
            java.net.URL imgURL = getClass().getResource("/images/board/" + i + ".png");
            if (imgURL == null) continue;
            img[i] = (new ImageIcon(imgURL)).getImage();
        }

        addMouseListener(new SweepAdapter());
    }


    public void paintComponent(Graphics g) {
        int covered = 0; //covered empty cells, gets calculated below
        int mines1 = n_mines;
        int mines2 = n_mines;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cur_cell = gameController.field.cells[i][j];
                SquareView square = new SquareView();

                if(cur_cell.isCovered()){
                    if (!gameController.isInGame()) { //we care if wrong or not
                        if (cur_cell.isBothMarked()) { //both
                            if (cur_cell.isMine()) { // wrong both
                                square.bothMarked();
                                PointsController.sucMark(1);
                                PointsController.sucMark(2);
                            } else {
                                mines1--;mines2--;
                                square.bothMarkedW();
                                PointsController.unsucMark(1);
                                PointsController.unsucMark(2);
                            }
                        } else if (cur_cell.isMarked1()) { // p1
                            if (cur_cell.isMine()) { //wrong p1
                                square.Marked1();
                                PointsController.sucMark(1);
                            } else {
                                mines1--;
                                square.Marked1W();
                                PointsController.unsucMark(1);
                            }
                        } else if (cur_cell.isMarked2()) { //p2
                            if (cur_cell.isMine()) { //wrong p2
                                square.Marked2();
                                PointsController.sucMark(2);
                            } else {
                                mines2--;
                                square.Marked2W();
                                PointsController.unsucMark(2);
                            }
                        } else if (cur_cell.isMine()) { //mine
                            square.Mine();
                        } else { //covered empty
                            square.Cover();
                        }
                    } else {
                        if (cur_cell.isBothMarked()){ //both
                            mines2--;mines1--;
                            square.bothMarked();
                        } else if (cur_cell.isMarked1()) { //p1
                            mines1--;
                            square.Marked1();
                        } else if (cur_cell.isMarked2()) { //p2
                            mines2--;
                            square.Marked2();
                        } else { //covered
                            if (!cur_cell.isMine()) covered++;
                            square.Cover();
                        }
                    }
                }else {
                    square.uncovered(cur_cell);
                }
                gameController.setMines1(mines1);
                gameController.setMines2(mines2);
                if(!gameController.isMulti()) MineSweeper.statusBar.setP1("mines left: " + gameController.getMines1());
                else if(!gameController.hasLoss()) MineSweeper.statusBar.setP2("mines left: " + gameController.getMines2());


                g.drawImage(img[square.getImgNum()], j * (cell_size + spaceX) ,
                        i * (cell_size + spaceY), cell_size, cell_size, this);
            }
        }

        if (covered == 0 && gameController.isInGame()) {
            Board.gameController.game.Win = true;
            gameController.end();
            paintComponent(g);
            MineSweeper.statusBar.setP1("Won");
            MineSweeper.statusBar.p1.setForeground(Color.green);
            if(gameController.isMulti()){
                playerController.toggle_player();
                PointsController.win(playerController.current_player);
                gameController.decideWinner();
            }
        } else if (!gameController.isInGame()) {
            MineSweeper.statusBar.setP1("Game Over!");
            MineSweeper.statusBar.p1.setForeground(Color.red);
            if(gameController.isMulti()) {
                gameController.decideWinner();
                new Result(Board.gameController.game.Win, true);
            }
            else{
                MineSweeper.statusBar.updatePoints();
                new Result(Board.gameController.game.Win, false);
            }
        }
    }

    private class SweepAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            int row = y / (cell_size + spaceY);
            int col = x / (cell_size + spaceX);
            boolean doRepaint = false;

//            System.out.println(x + " "+ col);
//            System.out.println(y + " "+ row);

            if(!gameController.isInGame())return;
            if (!gameController.field.isInitialized()) fieldController.initField(row,col,n_mines);

            if ((x < cols * (cell_size + spaceX)) && (y < rows * (cell_size + spaceY) )) { //&& (y > statusBar.height)

                Cell cur_cell = gameController.field.cells[row][col];

                if (e.getButton() == MouseEvent.BUTTON3) {

                    if (cur_cell.isCovered()) { // the cell is covered

                        doRepaint = true;

                        if (playerController.current_player == 2) {  // p2
                            if (cur_cell.isMarked2()) {
                                gameController.field.cells[row][col].setMarked2(false);
                            } else {
                                if (gameController.getMines2() > 0) {
                                    gameController.field.cells[row][col].setMarked2(true);
                                } else {
                                    MineSweeper.statusBar.setP1("No marks left");
                                }
                            }
                        } else { // p1
                            if (cur_cell.isMarked1()) { //the cell is marked by player 1
                                gameController.field.cells[row][col].setMarked1(false);
                            } else {
                                if (gameController.getMines1() > 0) {
                                    gameController.field.cells[row][col].setMarked1(true);
                                } else {
                                    MineSweeper.statusBar.setP1("No marks left");
                                }
                            }
                        }
                    }
                }else { //left click
                    if(gameController.isMulti()){
                        if(cur_cell.isBothMarked() || !cur_cell.isCovered())return; //both marked or uncovered

                        if(cur_cell.isMarked2()){ // marked by 2
                            if(playerController.current_player == 1) {
                                gameController.field.cells[row][col].setMarked2(false);
                                cur_cell = gameController.field.cells[row][col];
                            }else return;
                        }else if(cur_cell.isMarked1()){
                            if(playerController.current_player == 1)return;
                            else {
                                gameController.field.cells[row][col].setMarked1(false);
                                cur_cell = gameController.field.cells[row][col];
                            }
                        }

                    }else{
                        if(!cur_cell.isCovered() || cur_cell.isMarked1()){
                            return;
                        }
                    }
                    doRepaint = true;

                    ButtonMisc.playSound("uncover.wav");
                    if(cur_cell.isMine()){
                        CellController.Uncover(gameController.field.cells[row][col],playerController.current_player);
                        ButtonMisc.playSound("explosion.wav");
                        if(GameController.multiThread != null) GameController.multiThread.stop();
                        if(gameController.isMulti() && !gameController.hasLoss()) {
                            playerController.toggle_player();
                            playerController.loss();
                        }else gameController.end();
                    }
                    else {
                        CellController.Uncover(gameController.field.cells[row][col],playerController.current_player);
                        if(cur_cell.nonNear()){
                            fieldController.fill_flood(row,col, playerController.current_player);
                        }
                    }
                    if(gameController.isMulti() && !gameController.hasLoss()){
                        playerController.toggle_player();
                        GameController.multiThread.i = 0;
                        MineSweeper.timer.setText("10");

                    }
                }
                if(doRepaint) paintComponent(getGraphics());
            }

            MineSweeper.statusBar.updatePoints();
            SaveController.quickSave();
        }
    }
}
