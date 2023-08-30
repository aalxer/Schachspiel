package chessGame.characters;

import chessGame.exceptions.CharacterException;
import chessGame.exceptions.OutLineException;
import chessGame.gameEnum.Colors;
import chessGame.CharacterPosition;
import chessGame.gameEnum.Characters;

public class Koenig extends Figur {
    public Koenig(Colors color, CharacterPosition position, int stuck) {
        super(Characters.KOENIG, color, position, "Im Spiel", stuck);
    }

    public boolean move(Colors color, CharacterPosition newPosition) throws CharacterException, OutLineException {

        //Erlaubte Bewegung für einen Bauer
        if ((newPosition.getxWert() == 0 && newPosition.getyWert() == 1) || ((newPosition.getxWert() == 1 || newPosition.getxWert() == -1) && (newPosition.getyWert() == 1 || newPosition.getyWert() == 0))) {

            // falls Schwarz müssen die Y-Werte invertiert werden, damit sie richtig auf den Board eintragen können
            newPosition = yWerteInvertieren(newPosition,color);

            int newX = getPosition().getxWert() + newPosition.getxWert();
            int newY = getPosition().getyWert() + newPosition.getyWert();
            setPosition(new CharacterPosition(newX, newY));
            return true;
        } else{
            throw new CharacterException("unerlaubte Bewegung für die Figur\n");
        }
    }
}
