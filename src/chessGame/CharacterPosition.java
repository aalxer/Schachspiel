package chessGame;

public class CharacterPosition {
    private int xWert;
    private int yWert;

    public CharacterPosition(int x, int y) {
        this.xWert=x;
        this.yWert=y;
    }

    public int getxWert(){
        return this.xWert;
    }

    public void setxWert(int xWert) {
        this.xWert = xWert;
    }

    public int getyWert(){
        return this.yWert;
    }

    public void setyWert(int yWert) {
        this.yWert = yWert;
    }
}
