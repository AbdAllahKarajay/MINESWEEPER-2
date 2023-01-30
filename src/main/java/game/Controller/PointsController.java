package game.Controller;

import game.MineSweeper;

public class PointsController{
    static int p0 = 0;
    static int p1 = 0;
    static int p2 = 0;

    private static int sucMark_V = RulesController.sucM;
    private static int unsucMark_V = RulesController.unsucM;
    private static int uncoverMine_V = RulesController.lose;
    private static int uncover_V = RulesController.uncover;
    private static int win_V = RulesController.win;

    public static void setRules(){
        sucMark_V = RulesController.sucM;
        unsucMark_V = RulesController.unsucM;
        uncoverMine_V = RulesController.lose;
        uncover_V = RulesController.uncover;
        win_V = RulesController.win;
    }


    public static void reset(){
        p0 = 0;
        p1 = 0;
        p2 = 0;
    }

    public static void sucMark(int player){
        if(player == 1){
            p0 += sucMark_V;
            p1 += sucMark_V;
        }
        else if (player == 2) p2 += sucMark_V;
    }

    public static void unsucMark(int player){
        if(player == 1){
            p0 += unsucMark_V;
            p1 += unsucMark_V;
        }
        else if (player == 2) p2 += unsucMark_V;
    }
    public static void uncoverMine(int player){
        if(player == 1) p1 += uncoverMine_V;
        else if (player == 2) p2 += uncoverMine_V;
        else p0 += uncoverMine_V;
    }
    public static void uncover(int player){
        if(player == 1) p1 += uncover_V;
        else if(player == 2) p2 += uncover_V;
        else p0 += uncover_V;
    }
    public static void win(int player){
        if(player == 1) p1 += win_V;
        else if (player == 2) p2 += win_V;
        else p0 += win_V;
    }

    public static int getP1() {
        return p1;
    }

    public static int getP2() {
        return p2;
    }

    public static int getP0() {
        return p0;
    }

    public static void setPoints(int p0, int p1, int p2) {
        PointsController.p0 = p0;
        PointsController.p1 = p1;
        PointsController.p2 = p2;
        MineSweeper.statusBar.updatePoints();
    }
}