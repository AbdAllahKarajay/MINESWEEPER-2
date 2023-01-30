package game.Controller;

import game.Model.Cell;

public class CellController{
    public static void Uncover(Cell cell, int player){
        if(cell.isMine()) PointsController.uncoverMine(player);
        else PointsController.uncover(player);
        cell.setCovered(false);
        if(player == 1) cell.setPlayer(1);
        else if(player == 2) cell.setPlayer(2);
        else cell.setPlayer(0);
    }
}