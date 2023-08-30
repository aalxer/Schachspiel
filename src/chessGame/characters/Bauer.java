package chessGame.characters;

import chessGame.gameEnum.Colors;
import chessGame.gameEnum.Characters;
import chessGame.exceptions.CharacterException;
import chessGame.exceptions.OutLineException;
import chessGame.*;

public class Bauer extends Figur {

    public Bauer(Colors color, CharacterPosition position, int stuck) {
        super(Characters.BAUER, color, position, "Im Spiel",stuck);
    }

    public boolean move(Colors color, CharacterPosition newPosition) throws CharacterException, OutLineException {
        // Erlaubte Bewegung für einen Bauer
        if((newPosition.getxWert()==0 && newPosition.getyWert()==1) || (newPosition.getxWert()==1 && newPosition.getyWert()==1) || (newPosition.getxWert()==-1 && newPosition.getyWert()==1)){
            // falls Schwarz müssen die Y-Werte invertiert werden, damit sie richtig auf den Board eintragen können
            newPosition = yWerteInvertieren(newPosition,color);
            /*
            if (color==Colors.BLACK) {
                newPosition = new FigurPosition(newPosition.getxWert(), Math.negateExact(newPosition.getyWert()));
            }
             */

            int newX = getPosition().getxWert() + newPosition.getxWert();
            int newY = getPosition().getyWert() + newPosition.getyWert();
            setPosition(new CharacterPosition(newX,newY));
            return true;
        } else {
            throw new CharacterException("unerlaubte Bewegung für die Figur\n");
        }
    }
}
