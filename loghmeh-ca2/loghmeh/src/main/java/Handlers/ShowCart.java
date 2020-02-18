package Handlers;

import Classes.Interface;
import Classes.Order;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ShowCart implements Handler {
    private Interface system;

    public ShowCart(Interface system) {
        this.system = system;
    }

    public void handle(Context context) throws Exception {
        String response = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>User</title>\n" +
                "    <style>\n" +
                "        li, div, form {\n" +
                "        \tpadding: 5px\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div>"+system.getUser().giveYourRestaurantYouBuy()+"</div>\n" +
                "    <ul>";
        for(Order order:system.getUser().getOrders()){
            response+="<li>"+order.getFoodName()+": \u200C" + order.getCount()+"</li>";
        }
         response += "    </ul>\n" +
                "    <form action=\"finalize\" method=\"POST\">\n" +
                "        <button type=\"submit\">finalize</button>\n" +
                "    </form>\n" +
                "</body>\n" +
                "</html>";
        context.html(response);
    }
}
