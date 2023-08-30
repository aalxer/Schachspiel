package gameDistributor;

import chessGame.gameEnum.Colors;
import chessGame.gameEnum.Characters;
import chessGame.exceptions.*;
import chessGame.characters.Figur;
import chessGame.*;
import gameDistributor.readThread.*;
import gameDistributor.tcpThread.*;
import java.io.*;
import java.util.ArrayList;

public class GameDistributor extends ChessImpl implements TCPStreamCreatedListener, ReadThreadListener {
    private OutputStream os;
    private InputStream is;

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

    // sie wird informiert, sobald wir ein Socket haben (eine bestandene Verbindung)
    // sie initialisiert meine Input- und OutputStream
    @Override
    public void streamCreated(TCPStream channel) {
        try {
            this.is = channel.getInputStream();
            this.os = channel.getOutputStream();

            // inputStream übergeben und Thread starten:
            new ReadThread(this.is,this).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Sender Seite : (Serialisierung)
    @Override
    public ArrayList<Figur> start(String username, Colors color) throws SetException, StatusException {
        // Verbindung checken
        if(this.connected()) {
            // die App Implementierung wird aufgerufen
            super.start(username,color);

            // auf diesem Stream werden die Daten geschrieben und später durch Sockets wegschicken
            DataOutputStream dos = new DataOutputStream(this.os);
            try {
                dos.writeInt(this.START_METHODE);
                dos.writeUTF(username);
                // color wird übersetzt, weil wir nur primitive Datentypen schicken können
                switch (color) {
                    case BLACK -> {
                        dos.writeInt(this.BLACK_COLOR);
                    }
                    case WHITE -> {
                        dos.writeInt(this.WHITE_COLOR);
                    }
                }
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        }

        return null;
    }

    // Sender Seite : (Serialisierung)
    @Override
    public boolean move(Colors color, Characters figur, int stuck, CharacterPosition bewegung) throws OutLineException, CharacterException, StatusException, PathException {
        // Verbindung checken
        if(this.connected()) {
            // App Implementierung aufrufen
            super.move(color, figur, stuck, bewegung);

            // Daten schicken
            DataOutputStream dos = new DataOutputStream(this.os);
            try {
                // Eingaben in InputStream schreiben :
                dos.writeInt(this.MOVE_METHODE);
                switch (color) {
                    case WHITE -> dos.writeInt(this.WHITE_COLOR);
                    case BLACK -> dos.writeInt(this.BLACK_COLOR);
                }
                switch (figur) {
                    case BAUER -> dos.writeUTF(this.BAUER);
                    case TURM -> dos.writeUTF(this.TURM);
                    case LAEFER -> dos.writeUTF(this.LAEFER);
                    case KOENIG -> dos.writeUTF(this.KOENIG);
                    case DAME -> dos.writeUTF(this.DAME);
                    case SPRINGER -> dos.writeUTF(this.SPRINGER);
                }
                dos.writeInt(stuck);
                dos.writeInt(bewegung.getxWert());
                dos.writeInt(bewegung.getyWert());

            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        }
        return false;
    }

    private boolean connected() {
        if (this.os != null && this.is != null) {
            return true;
        }
        System.out.print("keine Verbindung !\n");
        return false;
    }

    // Read Thread :
    @Override
    public void startNachricht(String username, Colors color) throws SetException, StatusException {
        System.out.print("Start Nachricht empfangen..\n");
        super.start(username, color);
    }

    @Override
    public void moveNachricht(Colors color, Characters figur, int stuck, CharacterPosition bewegung) throws OutLineException, PathException, StatusException, CharacterException {
        System.out.print("Move Nachricht empfangen..\n");
        super.move(color, figur, stuck, bewegung);
    }

    @Override
    public void verbindungSchliessen() {
        this.os = null;
        this.is = null;
    }
}