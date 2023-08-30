package gameDistributor.readThread;

import chessGame.exceptions.*;
import chessGame.gameEnum.Colors;
import chessGame.CharacterPosition;
import chessGame.gameEnum.Characters;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReadThread extends Thread {

    private final ReadThreadListener listener;
    private final InputStream is;

    private final int START_METHODE = 0;
    private final int MOVE_METHODE = 1;

    // Farbe übersetzen
    private final int WHITE_COLOR = 0;
    private final int BLACK_COLOR = 1;

    // Figuren übersetzen
    private final String BAUER = "bauer";
    private final String TURM = "turm";
    private final String SPRINGER = "springer";
    private final String LAEFER = "laefer";
    private final String DAME = "dame";
    private final String KOENIG = "koenig";

    public ReadThread(InputStream is, ReadThreadListener listener) {
        this.is = is;
        this.listener = listener;
    }

    public void run() {
        try {
            while(true) {
                DataInputStream dis = new DataInputStream(this.is);
                int commandID = dis.readInt();
                switch (commandID) {
                    case START_METHODE -> {
                        this.deserialzedStart();
                    }
                    case MOVE_METHODE -> {
                        this.deserialzedMove();
                    }
                }
            }
        } catch (IOException | SetException e) {
            System.out.print(e.getMessage());
            this.listener.verbindungSchliessen();
        } catch (OutLineException | PathException | StatusException | CharacterException e) {
            e.printStackTrace();
        }
    }

    public void deserialzedStart() throws SetException {
        DataInputStream dis = new DataInputStream(this.is);
        Colors color;

        try {
            // Daten aus dem Stream lesen und in Variable speichern :
            String username = dis.readUTF();
            int colorInt = dis.readInt();
            switch (colorInt) {
                case BLACK_COLOR -> color = Colors.BLACK;
                case WHITE_COLOR -> color = Colors.WHITE;
                default -> throw new SetException("Unexpected value: " + colorInt);
            }
            // Listener informieren damit die Super.Methode aufgerufen wird
            this.listener.startNachricht(username, color);

        } catch (IOException | StatusException e) {
            System.out.print(e.getMessage());
        }
    }

    private void deserialzedMove() throws OutLineException, CharacterException, StatusException, PathException {

        DataInputStream dis = new DataInputStream(this.is);

        Colors color;
        Characters figur;
        int stueck;
        CharacterPosition bewegung = null;

        try {
            // Daten aus dem Stream lesen und in Variable speichern :
            int colorInt = dis.readInt();
            switch (colorInt) {
                case WHITE_COLOR -> color = Colors.WHITE;
                case BLACK_COLOR -> color = Colors.BLACK;
                default -> throw new IllegalStateException("Unexpected value: " + dis.readInt());
            }

            switch (dis.readUTF()) {
                case BAUER -> figur = Characters.BAUER;
                case TURM -> figur = Characters.TURM;
                case SPRINGER -> figur = Characters.SPRINGER;
                case LAEFER -> figur = Characters.LAEFER;
                case DAME -> figur = Characters.DAME;
                case KOENIG -> figur = Characters.KOENIG;
                default -> throw new IllegalStateException("Unexpected value: " + dis.readUTF());
            }
            stueck = dis.readInt();
            bewegung = new CharacterPosition(dis.readInt(),dis.readInt());

            // Listener informieren damit die Super.Methode aufgerufen wird
            this.listener.moveNachricht(color, figur, stueck, bewegung);

        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}