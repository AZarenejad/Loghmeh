package Exceptions;

public class BuyError extends Exception{
    private String message;
    public BuyError(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
