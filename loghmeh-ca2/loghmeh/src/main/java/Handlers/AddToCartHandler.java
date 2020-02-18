package Handlers;

import Classes.Interface;
import Classes.Restaurant;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class AddToCartHandler implements Handler {
    private Interface system;
    public AddToCartHandler(Interface system) {
        this.system = system;
    }

    public void handle(Context context) throws Exception {
        String restaurantName = context.formParam("restaurantName");
        String foodName = context.formParam("foodName");
        long foodPrice = system.findRestaurant(restaurantName).findFoodMenu(foodName).getPrice();


        if (system.getUser().startChoosingFood()){
            system.getUser().setRestaurantToBuyFrom(restaurantName);
            system.getUser().addFoodToCart(foodName,foodPrice);
            System.out.println("user add food " + foodName + " with price " + foodPrice+ " from restaurant " + restaurantName +" successfully done!");
        }
        else if(system.getUser().isBuyFromOtherRestaurant(restaurantName)){
            context.html("<html><body><h1>You already have food from other restaurant.</h1></body></html>");
            return;
        }
        else{
            system.getUser().addFoodToCart(foodName,foodPrice);
            System.out.println("user add food " + foodName + " with price " + foodPrice+ " from restaurant " + restaurantName +" successfully done!");
        }
        context.redirect("http://localhost:7000/show_cart");
    }
}
