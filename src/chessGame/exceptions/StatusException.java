package chessGame.exceptions;

public class StatusException extends Exception {

    public StatusException(String message) {
        String error = ConsoleColors.RED+message;
        System.out.print(error);
    }

    public class ConsoleColors {
        public static final String RED = "\033[0;31m";
    }
}
