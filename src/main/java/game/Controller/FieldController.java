package game.Controller;

import game.Model.Field;
import game.View.Board;

import java.util.Random;

public class FieldController {

    public void initField(int r, int c,int n_mines) {
        var random = new Random();
        int i = 0;
        Field field = Board.gameController.field;
        field.setInitialized(true);

        int rows = Board.rows;
        int cols = Board.cols;

        while (i < n_mines) {
            int col = (int) (cols * random.nextDouble());
            int row = (int) (rows * random.nextDouble());

            if(col >= c-1 && col <= c+1 && row >= r-1 && row <= r+1) continue;

            if ((col < cols) && (row < rows) && !field.cells[row][col].isMine()) {
                field.cells[row][col].setMine();
                i++;

                if(row > 0) field.cells[row-1][col].increaseNearbyCount(); //left
                if(col > 0) field.cells[row][col-1].increaseNearbyCount(); //top
                if(row < rows-1) field.cells[row+1][col].increaseNearbyCount(); //right
                if(col < cols-1) field.cells[row][col+1].increaseNearbyCount(); // below
                if(row > 0 && col > 0) field.cells[row-1][col-1].increaseNearbyCount(); //lt
                if(row < rows-1 && col < cols-1) field.cells[row+1][col+1].increaseNearbyCount(); //rb
                if(row > 0 && col < cols-1) field.cells[row-1][col+1].increaseNearbyCount(); //lb
                if(row < rows-1 && col > 0) field.cells[row+1][col-1].increaseNearbyCount(); //rt
            }
        }
    }

    public boolean fill_square(int row, int col, int player){
        Field field = Board.gameController.field;
        if (field.cells[row][col].isCovered()) {
            CellController.Uncover(field.cells[row][col], player); // covered empty
            return field.cells[row][col].nonNear(); //fill_flood(row, col, player);
        }
        return false;
    }
    public void fill_flood(int row, int col, int player){
        int rows = Board.rows;
        int cols = Board.cols;

        boolean[] empty = new boolean[8];
        if (row > 0) { //left
            empty[0] = fill_square(row-1,col,player);
        }
        if (col > 0){ //top
            empty[1] = fill_square(row,col-1,player);
        }
        if (row < rows - 1){ //right
            empty[2] = fill_square(row+1,col,player);
        }
        if (col < cols - 1){ //bottom
            empty[3] = fill_square(row,col+1,player);
        }
        if (row > 0 && col > 0){ //lt
            empty[4] = fill_square(row-1,col-1,player);
        }
        if (row < rows - 1 && col < cols - 1){ //rb
            empty[5] = fill_square(row+1,col+1,player);
        }
        if (row > 0 && col < cols - 1){ //lb
            empty[6] = fill_square(row-1,col+1,player);
        }
        if (row < rows - 1 && col > 0){ //rt
            empty[7] = fill_square(row+1,col-1,player);
        }

        boolean willFill = false;
        for (int i = 0; i < 8; i++) {
            if(empty[i]){
                willFill = true;
                break;
            }
        }
//        final int sleep = 10;
        if(willFill) {
//            MineSweeper.board.paintComponent(MineSweeper.board.getGraphics());
//            Thread.sleep(sleep);
            if(empty[0]){
                fill_flood(row-1,col,player);
            }

            if(empty[1]){
                fill_flood(row,col-1,player);
            }

            if(empty[2]){
                fill_flood(row+1,col,player);
            }

            if(empty[3]){
                fill_flood(row,col+1,player);
            }

            if(empty[4]){
                fill_flood(row-1,col-1,player);
            }

            if(empty[5]){
                fill_flood(row+1,col+1,player);
            }

            if(empty[6]){
                fill_flood(row-1,col+1,player);
            }

            if(empty[7]){
                fill_flood(row+1,col-1,player);
            }
        }
    }
}
