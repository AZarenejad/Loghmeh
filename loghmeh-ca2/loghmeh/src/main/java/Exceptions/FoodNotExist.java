package Exceptions;

public class FoodNotExist extends Exception {
    private String message;
    public FoodNotExist(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
