package chessGame;

import chessGame.gameEnum.Colors;
import chessGame.gameEnum.Characters;
import chessGame.exceptions.*;
import chessGame.characters.Figur;

import java.io.IOException;
import java.util.ArrayList;

public interface Chess {

    /**
     * Zwei Farben werden angeboten, der erster Spieler entscheidet sich für eine und der zweite bekommt die andere.
     * Dem Spieler werden die 16 Figuren (in der ausgewählten Farbe) als ArrayList initialisiert.
     * @param color die angebotenen Farben Black,White
     * @param username Benutzer wählt sich einen Namen aus
     * @return : 16 Figure in der ausgewählten Farbe
     * @throws : SetException, falls ein dritter Spieler sich anmelden würde
     * @throws : StatusException, falls die Methode in den falschen Zustand aufgerufen wird
     */
    ArrayList<Figur> start(String username, Colors color) throws SetException, StatusException, IOException;

    /**
     * Sobald 2 Spieler sich im Spiel befinden, können durch diese Methode mit dem Spieler starten
     * @param color Spieler gibt die Farbe der Figur, die er bereits ausgewählt hat
     * @param figur eine Figur zum Bewegen muss genannt werden
     * @param stuck bei den Figuren, von den es mehrere im Schachspiel gibt, muss konkret ausgewählt werden
     * @param bewegung die gewünschte Bewegung in X, Y Richtung
     * @return True Falls die Bewegung erlaubt oder der Spieler gewonnen hat und False, falls eine Exception ausgelöst wurde
     * @throws : OutLineException, falls die Position der Figur nach der Eingabe (x,y) außerhalb des Schachbretts liegen würde
     * @throws : FigurException, falls die Eingabe (x,y) für die Figur nicht erlaubt ist
     * @throws : StatusException, falls die Methode in den falschen Zustand aufgerufen wird
     */
    boolean move(Colors color, Characters figur, int stuck, CharacterPosition bewegung) throws OutLineException, CharacterException, StatusException, PathException;
}
