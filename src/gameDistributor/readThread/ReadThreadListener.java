package gameDistributor.readThread;

import chessGame.gameEnum.Colors;
import chessGame.gameEnum.Characters;
import chessGame.exceptions.*;
import chessGame.*;

public interface ReadThreadListener {

    void startNachricht(String username, Colors color) throws SetException, StatusException;
    void moveNachricht(Colors color, Characters figur, int stuck, CharacterPosition bewegung) throws OutLineException, PathException, StatusException, CharacterException;
    void verbindungSchliessen();
}
