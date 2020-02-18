package Exceptions;

public class InvalidCommand extends Exception {
    private String message;
    public InvalidCommand(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
