package Classes;

import java.util.ArrayList;

public class User {
    private ArrayList<Order> orders;
    private String chosenRestaurantNameToBuy;



    public User(){
        orders = new ArrayList<Order>();
    }

    public void finalizeOrder(){
        orders = new ArrayList<Order>();
        chosenRestaurantNameToBuy = null;
    }

    public void addFoodToCart(String foodname){
        boolean find = false;
        for(int i=0;i<orders.size();i++){
            if(orders.get(i).getFoodName().equals(foodname)){
                find = true;
                orders.get(i).increase_count();
                break;
            }
        }
        if(!find){
            orders.add(new Order(foodname));
        }

    }

    public boolean startChoosingFood(){
        return chosenRestaurantNameToBuy==null;
    }

    public String giveYourRestaurantYouBuy(){
        return chosenRestaurantNameToBuy;
    }

    public boolean isBuyFromOtherRestaurant(String name){
        return !chosenRestaurantNameToBuy.equals(name);
    }

    public void setRestaurantToBuyFrom(String name){
        this.chosenRestaurantNameToBuy= name;
    }

    public ArrayList<Order> getOrders(){
        return  orders;
    }

}
