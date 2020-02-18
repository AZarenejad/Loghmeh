package Handlers;

import Classes.Interface;
import Classes.Restaurant;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ShowAllRestaurantsHandler implements Handler {

    private Interface system;
    public ShowAllRestaurantsHandler(Interface system) {
        this.system = system;
    }

    public void handle(Context context) throws Exception {
        String response ="";
        if(system.getRestaurantsAvailableForUser().size()==0){
            response +="There is no available restaurants for you!";
        }
        else{
            response+="<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Restaurants</title>\n" +
                    "    <style>\n" +
                    "        table {\n" +
                    "            text-align: center;\n" +
                    "            margin: auto;\n" +
                    "        }\n" +
                    "        th, td {\n" +
                    "            padding: 5px;\n" +
                    "            text-align: center;\n" +
                    "        }\n" +
                    "        .logo{\n" +
                    "            width: 100px;\n" +
                    "            height: 100px;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <table>\n" +
                    "        <tr>\n" +
                    "            <th>id</th>\n" +
                    "            <th>logo</th>\n" +
                    "            <th>name</th>\n" +
                    "        </tr>\n";

            for(Restaurant restaurant:system.getRestaurantsAvailableForUser()){
                response+=   "        <tr>\n" +
                        "            <td>" + restaurant.getId() +"</td>\n" +
                        "            <td><img class=\"logo\" src="+restaurant.getImageUrl()+" alt=\"logo\"></td>\n" +
                        "            <td>" + restaurant.getName() +"</td>\n" +
                        "        </tr>\n";
            }
            response+="       </tr>\n" +
                    "    </table>\n" +
                    "</body>\n" +
                    "</html>";
        }
        context.html(response);
    }
}
