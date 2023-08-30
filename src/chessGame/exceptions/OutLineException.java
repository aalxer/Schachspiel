package chessGame.exceptions;

public class OutLineException extends Exception{

    public OutLineException(String message) {
        String error = StatusException.ConsoleColors.RED+message;
        System.out.print(error);
    }

    public void massage(String error) {
        String error1 = StatusException.ConsoleColors.RED+error;
        System.out.print(error1);
    }

    public class ConsoleColors {
        public static final String RED = "\033[0;31m";
    }
}
