package Exceptions;

public class RestaurantNotExist extends Exception {
    private String message;
    public RestaurantNotExist(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
