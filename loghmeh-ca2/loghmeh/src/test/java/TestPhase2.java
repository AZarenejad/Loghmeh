import Classes.Interface;
import Exceptions.RestaurantExist;
import Handlers.*;
import io.javalin.Javalin;
import junit.framework.TestCase;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.assertj.core.api.Assertions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utilities.Utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class TestPhase2 {

    private Utility utils;
    Interface system;
    String filename;
    Javalin app;
    @Before
    public void setup() throws IOException, ParseException {
        this.system = new Interface();
        system.addUser("Alireza" , "Zarenejad" , "+989102796696" , "alireza.zarenejad99@gmail.com" );
        this.filename = "src/main/resources/TestP2.txt";
        this.app = Javalin.create().start(1234);
        File file = new File(this.filename);
        Scanner scan = new Scanner(file);
        String jsonRestaurants = scan.nextLine();
        scan.close();
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        jsonArray = (JSONArray) parser.parse(jsonRestaurants);
        for(int i=0;i<jsonArray.size();i++){
            try{
                system.addRestaurantCommand((JSONObject)jsonArray.get(i));
            }catch (RestaurantExist e){
                System.out.println(e.getMessage());
            }
        }

        app.get("user", new ShowUserHandler(system));
        app.get("restaurants", new ShowAllRestaurantsHandler(system));
        app.get("restaurants/:id" ,new ShowRestaurantByIdHandler(system));
        app.post("restaurants/add_food",new AddToCartHandler(system));
        app.post("increase_credit", new IncreaseCredit(system));
        app.get("show_cart", new ShowCart(system));
        app.post("finalize", new FinalizeOrder(system));
    }

    @Test
    public void seeRestInfoTest() throws IOException{
        HttpResponse response = Unirest.get("http://localhost:1234/restaurants/123").asString();
        Assertions.assertThat(response.getStatus()).isEqualTo(404);
        response = Unirest.get("http://localhost:1234/restaurants/5e445b6c6ab90e0af6068dbb").asString();
        Assertions.assertThat(response.getStatus()).isEqualTo(403);
        response = Unirest.get("http://localhost:1234/restaurants/5e445b6c6ab90e0af6068d73").asString();
        String expected = "<!DOCTYPE html>\n" +
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
                "        <li>id: 5e445b6c6ab90e0af6068d73</li>\n" +
                "        <li>name: آیران دونر (فودکورت اسپرلوس)</li>\n" +
                "        <li>location: (2,-4)</li>\n" +
                "        <li>logo: <img src=https://static.snapp-food.com/media/cache/vendor_logo/uploads/images/vendors/logos/5e33db13d7c4b.jpg alt=\"logo\"></li>\n" +
                "        <li>menu: \n" +
                "        \t<ul>\n" +
                "<li>\n" +
                "                    <img src=https://static.snapp-food.com/200x201/cdn/53/30/5/vendor/5e3059805da9a.jpeg alt=\"logo\">\n" +
                "                    <div>اسکندر کباب</div>\n" +
                "                    <div>12000 Toman</div>\n" +
                "                    <form action=\"add_food\" method=\"POST\">\n" +
                "                        <button type=\"submit\">addToCart</button>\n" +
                "                        <input type=\"hidden\" name=\"restaurantName\" value=\"آیران دونر (فودکورت اسپرلوس)\">\n" +
                "                        <input type=\"hidden\" name=\"foodName\" value=\"اسکندر کباب\">\n" +
                "                    </form>\n" +
                "                </li><li>\n" +
                "                    <img src=https://static.snapp-food.com/200x201/cdn/53/30/5/vendor/5e3af94303a78.jpeg alt=\"logo\">\n" +
                "                    <div>دلمه برگ مو</div>\n" +
                "                    <div>30000 Toman</div>\n" +
                "                    <form action=\"add_food\" method=\"POST\">\n" +
                "                        <button type=\"submit\">addToCart</button>\n" +
                "                        <input type=\"hidden\" name=\"restaurantName\" value=\"آیران دونر (فودکورت اسپرلوس)\">\n" +
                "                        <input type=\"hidden\" name=\"foodName\" value=\"دلمه برگ مو\">\n" +
                "                    </form>\n" +
                "                </li>        \t</ul>\n" +
                "        </li>\n" +
                "    </ul>\n" +
                "</body>\n" +
                "</html>";
        Assertions.assertThat(response.getStatus()).isEqualTo(200);
        TestCase.assertEquals(expected, response.getBody().toString());
    }

    @Test
    public void chooseFoodTest() throws IOException{
        system.getUser().setRestaurantToBuyFrom("آیران دونر (فودکورت اسپرلوس)");
        system.getUser().addFoodToCart("اسکندر کباب",12000);
        HttpResponse response = Unirest.get("http://localhost:1234/show_cart").asString();
        String expected = "<!DOCTYPE html>\n" +
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
                "    <div>آیران دونر (فودکورت اسپرلوس)</div>\n" +
                "    <ul><li>اسکندر کباب: \u200C1</li>    </ul>\n" +
                "    <form action=\"finalize\" method=\"POST\">\n" +
                "        <button type=\"submit\">finalize</button>\n" +
                "    </form>\n" +
                "</body>\n" +
                "</html>";
        TestCase.assertEquals(response.getBody().toString(), expected);

        long userCredit = system.getUser().getCredit();
        boolean foodToBuy = system.getUser().foodToBuy();
        long moneyToPay = system.getUser().moneyToPayForOrder();
        TestCase.assertTrue(foodToBuy);
        TestCase.assertEquals(moneyToPay, 12000);
    }

    @After
    public void tearDown() {
        app.stop();
    }
}
