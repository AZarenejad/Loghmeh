package Handlers;

import Classes.Interface;
import Classes.Menu;
import Classes.Restaurant;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.concurrent.atomic.AtomicReference;

public class ShowRestaurantByIdHandler implements Handler {
    private Interface system;
    private String id;
    public ShowRestaurantByIdHandler (Interface system) {
        this.system = system;
    }

    public void handle(Context context) throws Exception {
        this.id = context.pathParam("id");
        String response ="";
        Restaurant restaurant = system.getRestaurantWithId(this.id);
        if(restaurant == null){
            context.status(404);
        }
        else if(!system.restaurantAvailable(restaurant)){
            context.status(403);
        }
        else{
            response+="<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Restaurant</title>\n" +
                    "    <style>\n" +
                    "        img {\n" +
                    "        \twidth: 50px;\n" +
                    "        \theight: 50px;\n" +
                    "        }\n" +
                    "        li {\n" +
                    "            display: flex;\n" +
                    "            flex-direction: row;\n" +
                    "        \tpadding: 0 0 5px;\n" +
                    "        }\n" +
                    "        div, form {\n" +
                    "            padding: 0 5px\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <ul>\n" +
                    "        <li>id: " + restaurant.getId() + "</li>\n" +
                    "        <li>name: " + restaurant.getName()+"</li>\n" +
                    "        <li>location: " + restaurant.getLocation().toString() +"</li>\n" +
                    "        <li>logo: <img src=" + restaurant.getImageUrl() + " alt=\"logo\"></li>\n" +
                    "        <li>menu: \n" +
                    "        \t<ul>\n";
            for(Menu menu:restaurant.getMenus()) {
                response += "<li>\n" +
                        "                    <img src=" + menu.getUrlImage()+" alt=\"logo\">\n" +
                        "                    <div>"+menu.getName()+"</div>\n" +
                        "                    <div>"+menu.getPrice()+" Toman</div>\n" +
                        "                    <form action=\"add_food\" method=\"POST\">\n" +
                        "                        <button type=\"submit\">addToCart</button>\n" +
                        "                        <input type=\"hidden\" name=\"restaurantName\" value=\"" +restaurant.getName()+"\">\n" +
                        "                        <input type=\"hidden\" name=\"foodName\" value=\"" +menu.getName()+"\">\n" +
                        "                    </form>\n" +
                        "                </li>";
            }

            response += "        \t</ul>\n" +
                    "        </li>\n" +
                    "    </ul>\n" +
                    "</body>\n" +
                    "</html>";
        }
        context.html(response);
    }
}
