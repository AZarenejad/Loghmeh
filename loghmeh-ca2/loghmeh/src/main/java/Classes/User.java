package Classes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class User {
    private String id;
    private String userFirstName;
    private String userLastName;
    private String phoneNumber;
    private String email;
    private long credit;
    private ArrayList<Order> orders;
    private String chosenRestaurantNameToBuy;



    public User(String id,String name , String family , String phone , String email){
        this.id = id;
        this.userFirstName = name;
        this.userLastName = family;
        this.phoneNumber = phone;
        this.email = email;
        this.credit = 0;
        orders = new ArrayList<Order>();
    }


    public boolean foodToBuy(){
        return orders.size()!=0;
    }

    public long moneyToPayForOrder(){
        long pay=0;
        for(Order order:orders){
            pay+= order.getCount()*order.getFoodPrice();
        }
        return pay;
    }


    public void increaseCredit(Long increaseAmount){
        this.credit+= increaseAmount;
    }


    public String getId(){
        return this.id;
    }

    public String getUserFirstName(){
        return this.userFirstName;
    }


    public String getUserLastName(){
        return this.userLastName;
    }


    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public String getEmail(){
        return this.email;
    }


    public long getCredit(){
        return this.credit;
    }

    public void finalizeOrder(long moneyToPay){
        orders = new ArrayList<Order>();
        chosenRestaurantNameToBuy = null;
        this.credit-=moneyToPay;
    }

    public void addFoodToCart(String foodname , long foodPrice){
        boolean find = false;
        for(int i=0;i<orders.size();i++){
            if(orders.get(i).getFoodName().equals(foodname)){
                find = true;
                orders.get(i).increase_count();
                break;
            }
        }
        if(!find){
            orders.add(new Order(foodname , foodPrice));
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
