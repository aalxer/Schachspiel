import console.Console;
import chessGame.gameEnum.Colors;
import chessGame.gameEnum.Characters;
import chessGame.exceptions.*;
import chessGame.*;
import gameDistributor.GameDistributor;
import gameDistributor.tcpThread.TCPStream;

public class ChessUI {
    public static void main(String[] args) {
        Console console = new Console();
        // -----------------------------------------------------------------------------------------------------
        // gameDistributor hat das Interface TCPStreamCreatedListener,
        // TCP-Stream Class akzeptiert Listener, um sie zu informieren,
        // deswegen kann er sich als Listener in Class TCP-Stream anmelden,
        // sodass er informiert wird, sobald was passiert (wann und wie ist in der TCP-Stream Class festgelegt)
        // worüber wird er informiert, entscheidet das Interface TCPStreamCreatedListener
        // -----------------------------------------------------------------------------------------------------
        GameDistributor gameDistributor = new GameDistributor();
        Colors selectedColor = null;

        // TCP-Stream >>
        int port = 2462;
        boolean asServer;
        int setupTCP = console.readInteger("Enter 0 or 1 : ");
        switch (setupTCP) {
            case 0 -> {
                System.out.println("init as TCP client");
                asServer = false;
            }
            case 1 -> {
                System.out.println("init as TCP server");
                asServer = true;
            }
            default -> throw new IllegalStateException("Unexpected value: " + setupTCP);
        }
        String name = "TCPStream";

        TCPStream tcpStream = new TCPStream(port, asServer, name, gameDistributor);

        // TCP Start >>
        tcpStream.start();

        // Set player >>
        try {
            String username = console.readString("Enter Username : \n");
            Colors color = console.readColor("Enter Color : ");
            selectedColor = color;
            gameDistributor.start(username, color);
        } catch (SetException | StatusException e) {
            System.out.print("try later !\n");
        }
        // Game start >>
        gameLoop(gameDistributor,console,selectedColor);

        // Game End .. TCP Close >>
        tcpStream.kill();
        System.out.print("Verbindung abgebaut");
    }

    public static void gameLoop(Chess game, Console console, Colors color) {
        boolean playing = true;
        while (playing) {
            try {
                Characters figur = console.readFigur("Figur : ");
                int stueck = console.readInteger("Stuck : ");
                CharacterPosition pos = console.readPosition("gewünschte Bewegung\n");
                game.move(color, figur, stueck, pos);
            } catch (OutLineException | PathException | CharacterException | StatusException e) {
                gameLoop(game,console,color);
            }
        }
    }
}