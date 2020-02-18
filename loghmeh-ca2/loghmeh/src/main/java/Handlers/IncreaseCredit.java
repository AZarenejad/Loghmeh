package Handlers;

import Classes.Interface;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class IncreaseCredit implements Handler {



    private Interface system;

    public IncreaseCredit(Interface system) {
        this.system = system;
    }

    public void handle(Context context) throws Exception {
        String incrementValueString = context.formParam("credit","0");
        int increasement = 0;
        if(incrementValueString.equals("")){
            increasement= 0;
        }
        else{
            increasement =  Integer.parseInt(incrementValueString);
        }
        system.getUser().increaseCredit((long)increasement);
        context.redirect("user");
    }
}
