package chessGame.characters;

import chessGame.gameEnum.Colors;
import chessGame.gameEnum.Characters;
import chessGame.exceptions.*;
import chessGame.*;

public class Figur {
    private Characters name;
    private Colors color;
    private CharacterPosition position;
    private String Status;
    private int stuck;

    public Figur(Characters name, Colors color, CharacterPosition position, String status, int stuck) {
        this.name = name;
        this.color = color;
        this.position = position;
        this.Status = status;
        this.stuck = stuck;
    }

    public boolean move(Colors color, CharacterPosition newPosition) throws CharacterException, OutLineException {
       return false;
    }

    public CharacterPosition yWerteInvertieren(CharacterPosition pos, Colors color) {
        if (color==Colors.BLACK) {
            return pos = new CharacterPosition(pos.getxWert(), Math.negateExact(pos.getyWert()));
        } else {
            return pos;
        }
    }

    public Characters getName() {
        return name;
    }

    public void setName(Characters name) {
        this.name = name;
    }

    public Colors getColor() {
        return this.color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public CharacterPosition getPosition() {
        return position;
    }

    public void setPosition(CharacterPosition position) {
        this.position = position;
    }

    public int getStuck() {
        return stuck;
    }

    public void setStuck(int stuck) {
        this.stuck = stuck;
    }
}
