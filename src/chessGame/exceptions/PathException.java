package chessGame.exceptions;

public class PathException extends Exception {

    public PathException(String message) {
        String error = StatusException.ConsoleColors.RED+message;
        System.out.print(error);
    }

    public class ConsoleColors {
        public static final String RED = "\033[0;31m";
    }
}
