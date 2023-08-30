package chessGame.characters;

import chessGame.gameEnum.Colors;
import chessGame.gameEnum.Characters;
import chessGame.exceptions.CharacterException;
import chessGame.exceptions.OutLineException;
import chessGame.*;

public class Laefer extends Figur {
    public Laefer(Colors color, CharacterPosition position, int stuck) {
        super(Characters.LAEFER, color, position, "Im Spiel", stuck);
    }

    public boolean move(Colors color, CharacterPosition newPosition) throws CharacterException, OutLineException {

        // Erlaubte Bewegung für einen Bauer
        if ((newPosition.getxWert() == newPosition.getyWert()) || (Math.negateExact(newPosition.getxWert()) == newPosition.getyWert())) {

            // falls Schwarz müssen die Y-Werte invertiert werden, damit sie richtig auf den Board eintragen können
            newPosition = yWerteInvertieren(newPosition,color);

            int newX = getPosition().getxWert() + newPosition.getxWert();
            int newY = getPosition().getyWert() + newPosition.getyWert();
            setPosition(new CharacterPosition(newX, newY));
            return true;
        } else {
            throw new CharacterException("unerlaubte Bewegung für die Figur\n");
        }
    }
}
