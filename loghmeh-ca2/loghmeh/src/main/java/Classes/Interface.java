package Classes;

import Exceptions.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import org.apache.commons.lang3.ArrayUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

public class Interface {
    private ArrayList<Restaurant> restaurants;
    private User user;
    private Javalin app;


    public Interface() {
        restaurants = new ArrayList<Restaurant>();
    }

    public void addUser(String name , String family , String phone , String email){
        user = new User("1",name , family , phone , email);
    }

    public void manageCommand(String command) throws RestaurantExist , RestaurantNotExist ,
            FoodExist , IOException , FoodNotExist , BuyError ,InvalidCommand {
        String commandName = command.split(" ", 2)[0];
        String jsonData;
        try {
            if (commandName.equals("addRestaurant")) {
                JSONObject jsonObject = JSONConverter(command);
                this.addRestaurantCommand(jsonObject);
            }
            else if (commandName.equals("addFood")) {
                JSONObject jsonObject = JSONConverter(command);
                this.addFoodCommand(jsonObject);
            }
            else if (commandName.equals("getRestaurants")) {
                this.getAllRestaurantsCommand();
            }
            else if (commandName.equals("getRestaurant")) {
                JSONObject jsonObject = JSONConverter(command);
                this.getRestaurantInfoCommand(jsonObject);
            }
            else if (commandName.equals("getFood")) {
                JSONObject jsonObject = JSONConverter(command);
                this.getFoodInfoCommand(jsonObject);
            }

            else if (commandName.equals("addToCart")) {
                JSONObject jsonObject = JSONConverter(command);
                this.addToCartCommand(jsonObject);
            }

            else if (commandName.equals("getCart")) {
                this.getCartCommand();
            }

            else if (commandName.equals("finalizeOrder")) {
                this.finalizeOrderCommand();
            }
            else if(commandName.equals("getRecommendedRestaurants")){
                this.getRecommendedRestaurants();
            }

            else {
                throw new InvalidCommand("Invalid Command!");
            }
        } catch (ParseException ignored) {
        }
    }

    private static JSONObject JSONConverter(String command) throws ParseException {
        String jsonData;
        jsonData = command.split(" ", 2)[1];
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        jsonObject = (JSONObject) parser.parse(jsonData);
        return jsonObject;
    }


    public boolean isRestaurantExist(String id){
        for (int i=0;i<restaurants.size();i++){
            if(restaurants.get(i).getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    public void addRestaurantCommand(JSONObject jsonObject) throws RestaurantExist{
        String new_restaurant_name = jsonObject.get("name").toString();
        String new_restaurant_id = jsonObject.get("id").toString();
        if(isRestaurantExist(new_restaurant_id)){
            throw new RestaurantExist("You want to add new Restaurant with id " + new_restaurant_id + " which already exist!");
        }
        else{
            Restaurant new_rest = new Restaurant(jsonObject);
            restaurants.add(new_rest);
            System.out.println("adding restaurant " + new_restaurant_name +" with id " + new_restaurant_id+" successfully finished!");
        }
    }

    public Restaurant findRestaurant(String name){
        for(int i=0;i<restaurants.size();i++){
            if (restaurants.get(i).getName().equals(name)){
                return restaurants.get(i);
            }
        }
        return null;
    }

    public void addFoodCommand(JSONObject jsonObject) throws RestaurantNotExist , FoodExist {
        String new_food_name = jsonObject.get("name").toString();
        String new_food_description = jsonObject.get("description").toString();
        double popularity = (double) jsonObject.get("popularity");
        long price = ((long) jsonObject.get("price"));
        String restaurant_name = jsonObject.get("restaurantName").toString();
        if (!isRestaurantExist(restaurant_name)) {
            throw new RestaurantNotExist("You want to add new menu to restaurant that not exist!");
        } else {
            Restaurant rest = findRestaurant(restaurant_name);
            Menu new_menu = new Menu(new_food_name, new_food_description, popularity, price,"");
            rest.addNewMenuToRestaurant(new_menu);
        }
    }

    public void getAllRestaurantsCommand(){
        System.out.println("Restaurants which are available are:");
        for (int i=0;i<restaurants.size();i++){
            System.out.println(Integer.toString(i+1) + ": " + restaurants.get(i).getName());
        }
    }

    public void getRestaurantInfoCommand(JSONObject jsonObject) throws RestaurantNotExist , IOException {
        String restaurant_name = jsonObject.get("name").toString();
        if(!isRestaurantExist(restaurant_name)){
            throw new RestaurantNotExist("You need Info about Restaurant " + restaurant_name + " which is not exist!");
        }
        else{
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(findRestaurant(restaurant_name));
            System.out.println(jsonString);
        }
    }

    public void getFoodInfoCommand(JSONObject jsonObject)throws RestaurantNotExist , FoodNotExist , IOException{
        String food_name = jsonObject.get("foodName").toString();
        String restaurant_name = jsonObject.get("restaurantName").toString();
        if (!isRestaurantExist(restaurant_name)){
            throw new RestaurantNotExist("You need Info of food " + food_name +" in restaurant " + restaurant_name +" which restaurant not exist!");
        }
        else{
            Restaurant rest = findRestaurant(restaurant_name);
            if(!rest.containFoodInMenu(food_name)){
                throw new FoodNotExist("food " + food_name + " not exist in restaurant " + restaurant_name);
            }
            else{
                Menu food_menu = rest.findFoodMenu(food_name);
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = mapper.writeValueAsString(food_menu);
                System.out.println(jsonString);
            }
        }
    }

    public void addToCartCommand(JSONObject jsonObject)throws RestaurantNotExist , BuyError , FoodNotExist {
        String food_name = jsonObject.get("foodName").toString();
        long foodPrice = (long)jsonObject.get("price");
        String restaurant_name = jsonObject.get("restaurantName").toString();
        if (!isRestaurantExist(restaurant_name)){
            throw new RestaurantNotExist("You want to choose food to buy from restaurant " + restaurant_name +" which is not exist!");
        }
        else{
            Restaurant rest = findRestaurant(restaurant_name);
            if(!rest.containFoodInMenu(food_name)){
                throw new FoodNotExist("food " + food_name + " not exist in restaurant " + restaurant_name +"!");
            }
            else{
                if (user.startChoosingFood()){
                   user.setRestaurantToBuyFrom(restaurant_name);
                }
                else if(user.isBuyFromOtherRestaurant(restaurant_name)){
                    throw new BuyError("you already has food from other restaurant " +
                            user.giveYourRestaurantYouBuy() +".Can not choose this food from " + restaurant_name +"!");
                }
                user.addFoodToCart(food_name,foodPrice);
                System.out.println("adding food " + food_name + " from restaurant " + restaurant_name +" succesfully done!");
            }
        }
    }

    public void getCartCommand() throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(user);
        System.out.println(jsonString);
    }

    public void finalizeOrderCommand() throws IOException{
        getCartCommand();
        user.finalizeOrder(0);
        System.out.println("Your ordered Succesfully!");
    }

    public String[] getRecommendedRestaurants() {
        HashMap<String, Double> restaurant_point_like = new HashMap<String, Double>();
        for (int i=0;i<restaurants.size();i++){
            Restaurant curr = restaurants.get(i);
            restaurant_point_like.put(curr.getName(),curr.averageFoodPopulation()/curr.distanceFromUser());
        }
        String[] point_sort = getTopRestaurant(restaurant_point_like);
        System.out.println("Here you can see restaurants point in order!");
        System.out.println(Collections.singletonList(Arrays.toString(point_sort)));
        return point_sort;
    }


    public static HashMap<String, Double> sortByValue(HashMap<String, Double> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double> > list =
                new LinkedList<Map.Entry<String,Double> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    @JsonIgnore
    public String[] getTopRestaurant(HashMap<String, Double> hm) {
        HashMap<String, Double> point_sort = sortByValue(hm);
        String[] rest_names = new String[point_sort.keySet().size()];
        point_sort.keySet().toArray(rest_names);
        ArrayUtils.reverse(rest_names);
        if(rest_names.length > 2)
            return Arrays.copyOfRange(rest_names, 0, 3);
        return Arrays.copyOfRange(rest_names, 0, rest_names.length);
    }

    @JsonIgnore
    public ArrayList<Restaurant> getRestaurantsAvailableForUser(){
        ArrayList<Restaurant> result=new ArrayList<Restaurant>();
        for (Restaurant restaurant:restaurants){
            if(restaurant.distanceFromUser()<=170){
                result.add(restaurant);
            }
        }
        return result;
    }

    public Restaurant getRestaurantWithId(String id){
        for (Restaurant restaurant:restaurants){
            if (restaurant.getId().equals(id)){
               return restaurant;
            }
        }
        return null;
    }

    public boolean restaurantAvailable(Restaurant restaurant){
      return (restaurant.distanceFromUser()<=170);
    }




    @JsonIgnore
    public User getUser(){
        return this.user;
    }
}