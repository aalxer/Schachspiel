package console;

import chessGame.*;
import chessGame.gameEnum.Colors;
import chessGame.gameEnum.Characters;

import java.util.Scanner;

public class Console {

    private final Scanner input = new Scanner(System.in);

    public int readInteger(String text) {
        try {
            System.out.print(ConsoleColors.BLACK+text);
            return Integer.parseInt(input.nextLine());
        } catch (Exception e) {
            System.out.print(ConsoleColors.BLACK+"Falsche Eingabe ! \n");
            return readInteger(text);
        }
    }

    public String readString(String text) {
        try {
            System.out.print(ConsoleColors.BLACK+text);
            return input.nextLine();
        } catch (Exception e){
            System.out.print(ConsoleColors.BLACK+"Falsche Eingabe !");
            return readString(text);
        }
    }

    public Colors readColor(String text) {
        try {
            System.out.print(ConsoleColors.BLACK+text);
            return Colors.valueOf(input.nextLine().toUpperCase());
        } catch (Exception e) {
            System.out.print(ConsoleColors.BLACK+"Falsche Eingabe !\n");
            return readColor(text);
        }
    }

    public Characters readFigur(String text) {
        try {
            System.out.print(ConsoleColors.BLACK+text);
            return Characters.valueOf(input.nextLine().toUpperCase());
        } catch (Exception e) {
            System.out.print(ConsoleColors.BLACK+"Falsche Eingabe !\n");
            return readFigur(text);
        }
    }

    public CharacterPosition readPosition(String text) {
        try {
            System.out.print(ConsoleColors.BLACK+text);
            return new CharacterPosition(this.readInteger("Enter X-Value : "), this.readInteger("Enter Y-Value : "));
        } catch (Exception e) {
            System.out.print(ConsoleColors.BLACK+"Falsche Eingabe !\n");
            return readPosition(text);
        }
    }

    public static class ConsoleColors {

        public static final String BLACK = "\033[0;30m";
        public static final String GREEN = "\033[0;32m";
        public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";
        public static final String YELLOW_UNDERLINED = "\033[4;33m";
        public static final String RED = "\033[0;31m";
    }
}
