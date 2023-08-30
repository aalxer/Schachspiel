package chessGame;

import chessGame.gameEnum.Colors;
import chessGame.gameEnum.Characters;
import chessGame.gameEnum.Status;
import chessGame.exceptions.*;
import chessGame.characters.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ChessImpl implements Chess {

    private ArrayList<Figur> figuren = new ArrayList<>();
    private Figur[][] board = new Figur[8][8];
    private Status status = Status.START;
    private final HashMap<Colors, String> spieler = new HashMap<>();     //Key , Value

    @Override
    public ArrayList<Figur> start(String username, Colors color) throws SetException, StatusException {
        // Prüfen ob der richtige Status ist
        if(this.status != Status.START){
            throw new StatusException("Falscher Zustand!\n");
        }

        // ob bereits 2Spieler im Spiel
        if (this.spieler.values().size() == 2) {
            throw new SetException("Max. 2 Spieler!\n");
        }

        // Farben unterscheiden
        switch (color) {
            case BLACK -> {
                if (this.spieler.get(Colors.BLACK) == null) {
                    // Die Methode 'setFiguren' erstellt die Figuren und tragt sie in 'Figur[][] board' ein
                    return this.setFiguren(Colors.BLACK,username);
                } else {
                    // Die Methode 'setFiguren' erstellt die Figuren und tragt sie in 'Figur[][] board' ein
                    return this.setFiguren(Colors.WHITE,username);
                }
            }

            case WHITE -> {
                if (this.spieler.get(Colors.WHITE) == null) {
                    // Die Methode 'setFiguren' erstellt die Figuren und tragt sie in 'Figur[][] board' ein
                    return this.setFiguren(Colors.WHITE,username);
                } else {
                    // Die Methode 'setFiguren' erstellt die Figuren und tragt sie in 'Figur[][] board' ein
                    return this.setFiguren(Colors.BLACK,username);
                }
            }

        }
        return null;
    }

    @Override
    public boolean move(Colors color, Characters figur, int stuck, CharacterPosition bewegung) throws OutLineException, CharacterException, StatusException, PathException{
        // Prüfen ob der richtige Status ist
        if(this.status == Status.START || this.status == Status.END){
            throw new StatusException("Warte auf Gegner!\n");
        }

        // Ob diese Farbe dran ist
        if(color == Colors.BLACK && this.status == Status.ROUND_WHITE || color == Colors.WHITE && this.status == Status.ROUND_BLACK) {
            throw new StatusException("Du bist nicht dran !\n");
        }

        // figur werde in der Liste gesucht
        for (Figur f : this.figuren) {
            if (f.getName() == figur && f.getColor()==color && f.getStuck()==stuck) {
                System.out.print(ConsoleColors.BLACK+this.figuren.size()+" Figuren im Spiel, "+f.getName()+" befindet sich gerade auf : X "+f.getPosition().getxWert()+" , Y "+f.getPosition().getyWert()+"\n");

                // Ob die eingegebene Bewegung + aktuelle Position außerhalb des Boards liegt
                switch (color) {
                    case WHITE -> {
                        if (f.getPosition().getxWert()+bewegung.getxWert() > 7 || f.getPosition().getyWert()+bewegung.getyWert() > 7) {
                            throw new OutLineException("die eingegebene Position liegt außerhalb des Spielfeldes\n");
                        }
                    }

                    case BLACK -> {
                        if (f.getPosition().getxWert()+bewegung.getxWert() > 7 || f.getPosition().getyWert()+Math.negateExact(bewegung.getyWert()) > 7) {
                            throw new OutLineException("die eingegebene Position liegt außerhalb des Spielfeldes\n");
                        }
                    }
                }

                // Path überprüfen
                if (!this.checkPath(f,bewegung)) {
                    throw new PathException("Bahn ist nicht frei\n");
                }

                // die Methode Schlagen() hab ich in 2 Teile zerlegt damit ich sie in dieser Klasse verwenden kann und nicht in den jeweiligen Figurenklassen
                // Schlagen Teil 1 : wenn die Position belegt ist, speichere die Figure, die darauf steht
                Figur figurOnBoard = this.schlagen(f,bewegung);

                //Die Methode überprüft die Eingaben und werft eine Exception bei falscher Positionseingabe,
                //sonst wird die Position der Figur in der FigurenListe umgeschrieben
                f.move(color, bewegung);

                // Schlagen Teil 2 : sobald die Methode move() ohne Exception durchgeführt wurde, lösche die vorher gespeicherte Figur
                if (figurOnBoard!=null) {
                    this.figuren.remove(figurOnBoard);
                    System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "Figur " + figurOnBoard.getName() + " wurde von " + f.getName() + " geschlagen \n");

                    // Falls der König geschlagen wurde, dann Game Over
                    if (figurOnBoard.getName() == Characters.KOENIG) {
                        this.gameOver();
                    }
                }

                // Figuren aktualisieren und neu eintragen
                return this.boardRefresh(f);
            }
        }
        System.out.print(ConsoleColors.RED+"Prüfe den eingegebenen Figur-Namen und das gewünschte Stück davon !\n");
        return false;
    }

    private ArrayList<Figur> setFiguren(Colors farbe, String name){
        ArrayList<Figur> setFiguren = new ArrayList<>();
        // Erstellung der Figuren in Abhängigkeit der Farbe, um später die move Methode nach der Farbe zu implementieren
        if (farbe==Colors.BLACK){
            setFiguren.add(new Turm(farbe,new CharacterPosition(0,7),1));
            setFiguren.add(new Springer(farbe,new CharacterPosition(1,7),1));
            setFiguren.add(new Laefer(farbe,new CharacterPosition(2,7),1));
            setFiguren.add(new Dame(farbe,new CharacterPosition(3,7),1));
            setFiguren.add(new Koenig(farbe,new CharacterPosition(4,7),1));
            setFiguren.add(new Laefer(farbe,new CharacterPosition(5,7),2));
            setFiguren.add(new Springer(farbe,new CharacterPosition(6,7),2));
            setFiguren.add(new Turm(farbe,new CharacterPosition(7,7),2));
            for(int i=0;i<8;i++){
                setFiguren.add(new Bauer(farbe,new CharacterPosition(i,6),i));
            }
            System.out.print(ConsoleColors.BLACK+"du hast die Schwarzen bekommen, deine Figuren sind bereit\n");
        } else {
            setFiguren.add(new Turm(farbe,new CharacterPosition(0,0),1));
            setFiguren.add(new Springer(farbe,new CharacterPosition(1,0),1));
            setFiguren.add(new Laefer(farbe,new CharacterPosition(2,0),1));
            setFiguren.add(new Dame(farbe,new CharacterPosition(3,0),1));
            setFiguren.add(new Koenig(farbe,new CharacterPosition(4,0),1));
            setFiguren.add(new Laefer(farbe,new CharacterPosition(5,0),2));
            setFiguren.add(new Springer(farbe,new CharacterPosition(6,0),2));
            setFiguren.add(new Turm(farbe,new CharacterPosition(7,0),2));
            for(int i=0;i<8;i++){
                setFiguren.add(new Bauer(farbe,new CharacterPosition(i,1),i));
            }
            System.out.print(ConsoleColors.BLACK+"du hast die Weißen bekommen, deine Figuren sind bereit\n");
        }
        // alte Figuren eintragen
        setFiguren.addAll(this.figuren);
        // die Figuren Listen austauschen
        this.figuren = setFiguren;
        // Figuren auf dem Board eintragen
        for (Figur f : this.figuren) {
            this.board[f.getPosition().getxWert()][f.getPosition().getyWert()] = f;
        }
        // Farbe dem Username zuordnen
        this.spieler.put(farbe,name);
        // ob schon 2Spieler im Spiel geworden
        if (this.spieler.values().size() == 2) {
            this.setStatus(Status.ROUND_WHITE);
            System.out.print(ConsoleColors.YELLOW_UNDERLINED+this.status+"\n");
        }
        return setFiguren;
    }

    private boolean boardRefresh(Figur f) {
        // neues Board erstellen und alle Figuren nach der Bewegung neu eintragen
        Figur[][] newBoard = new Figur[8][8];
        for (Figur ff : this.figuren) {
            newBoard[ff.getPosition().getxWert()][ff.getPosition().getyWert()] = ff;
        }
        // mit dem alten Board umtauschen
        this.board = newBoard;
        // Status ändern
        if(this.status==Status.ROUND_WHITE){
            this.status=Status.ROUND_BLACK;
        } else {
            this.status=Status.ROUND_WHITE;
        }
        System.out.print(ConsoleColors.GREEN+this.figuren.size()+" Figuren im Spiel, "+f.getName()+" befindet sich nach der Bewegung auf : X "+f.getPosition().getxWert()+" , Y "+f.getPosition().getyWert()+"\n");
        System.out.print(ConsoleColors.YELLOW_UNDERLINED+this.status+"\n");
        return true;
    }

    private boolean checkPath(Figur figur, CharacterPosition position) {
        // falls Schwarz müssen die Y-Werte invertiert werden, damit sie richtig auf den Board eintragen können
        if (figur.getColor()==Colors.BLACK) {
            position = new CharacterPosition(position.getxWert(), Math.negateExact(position.getyWert()));
        }

        // horizontaler Norden
        if (figur.getPosition().getxWert() == position.getxWert()+figur.getPosition().getxWert() && figur.getPosition().getyWert() < position.getyWert()) {
            for (int i = figur.getPosition().getyWert() + 1; i < position.getyWert(); i++) {
                if (this.board[figur.getPosition().getxWert()][i] != null) {
                    return false;
                }
            }
        }

        // horizontaler Osten
        if (figur.getPosition().getxWert() < position.getxWert() && figur.getPosition().getyWert() == position.getyWert()+figur.getPosition().getyWert()) {
            for (int i = figur.getPosition().getxWert() + 1; i < position.getxWert(); i++) {
                if (this.board[i][figur.getPosition().getyWert()] != null) {
                    return false;
                }
            }
        }
        // horizontaler Süden
        if (figur.getPosition().getxWert() == position.getxWert()+figur.getPosition().getxWert() && figur.getPosition().getyWert() > position.getyWert()) {
            for (int i = figur.getPosition().getyWert() - 1; i > position.getyWert()+figur.getPosition().getyWert() ; i--) {
                if (this.board[figur.getPosition().getxWert()][i] != null) {
                    return false;
                }
            }
        }

        // horizontaler Westen
        if (figur.getPosition().getxWert() > position.getxWert() && figur.getPosition().getyWert() == position.getyWert()+figur.getPosition().getyWert()) {
            for (int i = figur.getPosition().getxWert() - 1; i > position.getxWert()+figur.getPosition().getxWert() ; i--) {
                if (this.board[i][figur.getPosition().getyWert()] != null) {
                    return false;
                }
            }
        }

        // diagonaler Nordost
        if (position.getxWert() == position.getyWert()) {
            for (int xWerte = figur.getPosition().getxWert() + 1 , yWerte = figur.getPosition().getyWert() + 1 ; xWerte < position.getxWert()+figur.getPosition().getxWert() && yWerte < position.getyWert()+figur.getPosition().getyWert() ; xWerte++ , yWerte++ ) {
                if (this.board[xWerte][yWerte] != null) {
                    return false;
                }
            }
        }

        // diagonaler Nordwest
        if (Math.negateExact(position.getxWert()) == position.getyWert()) {
            for (int xWerte = figur.getPosition().getxWert() - 1; xWerte > position.getxWert()+figur.getPosition().getxWert(); xWerte--) {
                for (int yWerte = figur.getPosition().getyWert() + 1; yWerte < position.getyWert()+figur.getPosition().getyWert(); yWerte++){
                    if (this.board[xWerte][yWerte] != null) {
                        return false;
                    }
                }
            }
        }

        // diagonaler Südost
        if (position.getxWert() == Math.negateExact(position.getyWert())) {
            for (int xWerte = figur.getPosition().getxWert() + 1 , yWerte = figur.getPosition().getyWert() - 1 ; xWerte < position.getxWert()+figur.getPosition().getxWert() && yWerte > position.getyWert()+figur.getPosition().getyWert() ; xWerte++ , yWerte-- ) {
                if (this.board[xWerte][yWerte] != null) {
                    return false;
                }
            }
        }

        // diagonaler Südwest
        if (position.getxWert() == position.getyWert()) {
            for (int xWerte = figur.getPosition().getxWert() - 1 , yWerte = figur.getPosition().getyWert() - 1 ; xWerte > position.getxWert()+figur.getPosition().getxWert() && yWerte > position.getyWert()+figur.getPosition().getyWert() ; xWerte-- , yWerte-- ) {
                if (this.board[xWerte][yWerte] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    private Figur schlagen(Figur f, CharacterPosition bewegung) throws PathException {
        // Y-Werte für die schwarzen Figuren invertieren
        if (f.getColor()==Colors.BLACK) {
            bewegung = new CharacterPosition(bewegung.getxWert(), Math.negateExact(bewegung.getyWert()));
        }

        // ob die Position belegt ist
        if (this.board[f.getPosition().getxWert()+bewegung.getxWert()][f.getPosition().getyWert()+bewegung.getyWert()]!=null) {
            Figur figurOnBoard = this.board[f.getPosition().getxWert()+bewegung.getxWert()][f.getPosition().getyWert()+bewegung.getyWert()];

            // falls die die Figur auf der Position die gleiche Farbe hat
            if (f.getColor()==figurOnBoard.getColor()) {
                throw new PathException("Bahn ist nicht frei\n");
            }

            return figurOnBoard;

        }
        return null;
    }

    private void gameOver() {
        // FigurenListe und Board leeren
        this.figuren.clear();
        this.board=null;

        // der Gewinner nennen
        if (this.status==Status.ROUND_WHITE) {
            System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT+"*=*=*=Das Spiel ist zu Ende, " + this.spieler.get(Colors.WHITE) + " hat gewonnen=*=*=*\n");
        } else {
            System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT+"*=*=*=Das Spiel ist zu Ende, " + this.spieler.get(Colors.BLACK) + " hat gewonnen=*=*=*\n");
        }

        // Status auf Ende setzen
        this.setStatus(Status.END);

        // Programm beenden
        //System.exit(0);
    }

    public Status getStatus() {

        return status;
    }

    public void setStatus(Status status) {

        this.status = status;
    }

    public static class ConsoleColors {

        public static final String BLACK = "\033[0;30m";
        public static final String GREEN = "\033[0;32m";
        public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";
        public static final String YELLOW_UNDERLINED = "\033[4;33m";
        public static final String RED = "\033[0;31m";
    }
}