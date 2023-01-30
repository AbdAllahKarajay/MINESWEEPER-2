package game.Model;

import java.io.Serializable;

public class Cell implements Serializable {
    private boolean isCovered = true;
    private boolean isMine = false;
    private boolean isMarked1 = false;
    private boolean isMarked2 = false;
    private int nearbyCount = 0;
    private int player = 0;


    public boolean isBothMarked() {
        return (isMarked1 && isMarked2);
    }
    public boolean isMarked1() {
        return (isMarked1);
    }
    public boolean isMarked2() {
        return (isMarked2);
    }
    public boolean isMine() {
        return isMine;
    }
    public boolean isCovered() {
        return isCovered;
    }
    public boolean nonNear() {
        return nearbyCount == 0;
    }
    public int nearbyCount() {
        return nearbyCount;
    }

    public void setCovered(boolean covered) {
        isCovered = covered;
    }
    public void setMine() {
        isMine = true;
    }
    public void setMarked1(boolean marked1) {
        isMarked1 = marked1;
    }
    public void setMarked2(boolean marked2) {
        isMarked2 = marked2;
    }
    public void increaseNearbyCount() {
        this.nearbyCount++;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }
}