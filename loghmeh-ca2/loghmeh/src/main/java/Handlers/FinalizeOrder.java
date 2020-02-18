package Handlers;

import Classes.Interface;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class FinalizeOrder implements Handler {

    private Interface system;

    public FinalizeOrder(Interface system) {
        this.system = system;
    }

    public void handle(Context context) throws Exception {
        long userCredit = system.getUser().getCredit();
        boolean foodToBuy = system.getUser().foodToBuy();
        long moneyToPay = system.getUser().moneyToPayForOrder();
        if (foodToBuy && moneyToPay<=userCredit){
            System.out.println("user finalize order!");
            system.getUser().finalizeOrder(moneyToPay);
            context.redirect("http://localhost:7000/restaurants/");
        }
        else{
            //user nothing to buy or not enough credit
            context.status(400);
        }


    }
}
