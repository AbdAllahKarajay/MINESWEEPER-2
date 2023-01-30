package game.View;

import game.Model.Cell;

class SquareView {
    private final int cover_img;
    private final int p1_mark_img;
    private final int p1W_mark_img;
    private final int p2_mark_img;
    private final int p2W_mark_img;
    private final int both_mark_img;
    private final int bothW_mark_img;
    private final int mine_img;

    {
        cover_img = 20;
        p1_mark_img = 21;
        p1W_mark_img = 22;
        p2_mark_img = 23;
        p2W_mark_img = 24;
        both_mark_img = 25;
        bothW_mark_img = 26;
        mine_img = 36;
    }
    private int imgNum = 0;

    public int getImgNum() {
        return imgNum;
    }
    public void setImgNum(int v) {
        imgNum = v;
    }

    public void bothMarked(){
        imgNum = both_mark_img;
    }
    public void bothMarkedW(){
        imgNum = bothW_mark_img;
    }
    public void Marked1(){
        imgNum = p1_mark_img;
    }
    public void Marked1W(){
        imgNum = p1W_mark_img;
    }
    public void Marked2(){
        imgNum = p2_mark_img;
    }
    public void Marked2W(){
        imgNum = p2W_mark_img;
    }
    public void Cover(){
        imgNum = cover_img;
    }
    public void Mine(){
        imgNum = mine_img;
    }

    public void uncovered(Cell cell) {
        imgNum = (cell.isMine())? 9 : cell.nearbyCount();
        if(cell.getPlayer() == 2){
            imgNum +=10;
        }else if(cell.getPlayer() == 0) imgNum += Board.n_images - 10;
    }
}