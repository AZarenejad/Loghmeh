import Classes.Interface;
import Handlers.*;
import io.javalin.Javalin;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import utilities.Request;
import utilities.Utility;


public class Main {

    public static void main(String[] args) throws Exception {
        String url = "http://138.197.181.131:8080/restaurants";
        Interface system = new Interface();
        system.addUser("Alireza" , "Zarenejad" , "+989102796696" , "alireza.zarenejad99@gmail.com" );
        Javalin app = Javalin.create().start(7000);
        String jsonRestaurants = Request.get(url);
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        jsonArray = (JSONArray) parser.parse(jsonRestaurants);
        Utility.addRestaurantFromJSONArray(system, jsonArray);

        app.get("user", new ShowUserHandler(system));
        app.get("restaurants", new ShowAllRestaurantsHandler(system));
        app.get("restaurants/:id" ,new ShowRestaurantByIdHandler(system));
        app.post("restaurants/add_food",new AddToCartHandler(system));
        app.post("increase_credit", new IncreaseCredit(system));
        app.get("show_cart", new ShowCart(system));
        app.post("finalize", new FinalizeOrder(system));
    }

}