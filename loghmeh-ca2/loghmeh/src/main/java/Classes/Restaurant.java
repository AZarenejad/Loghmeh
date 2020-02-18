package Classes;

import Exceptions.FoodExist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Restaurant {
    private String id;
    private String name;
    private Location location;
    private String description;
    private String imageUrl;
    private ArrayList<Menu> menus;


    public Restaurant(JSONObject restaurantInfo){
        this.id = restaurantInfo.get("id").toString();
        this.name = restaurantInfo.get("name").toString();
        this.location = new Location((JSONObject)restaurantInfo.get("location"));
        this.imageUrl = restaurantInfo.get("logo").toString();
        this.menus = new ArrayList<Menu>();
//        this.description = restaurantInfo.get("description").toString();
        JSONArray array_menus = (JSONArray) restaurantInfo.get("menu");
        for (int i = 0; i < array_menus.size(); i++) {
            if(!containFoodInMenu((((JSONObject) array_menus.get(i)).get("name")).toString())){
                menus.add(new Menu((JSONObject) array_menus.get(i)));
            }
            else{
                System.out.println("The food " + (((JSONObject) array_menus.get(i)).get("name").toString()) + " is already in menu.");
            }
        }
    }

    public Restaurant(String id , String name , String description, String urlLogo , Location location , ArrayList<Menu> menus){
        this.description = description;
        this.id = id;
        this.name = name;
        this.location = location;
        this.menus = menus;
        this.imageUrl = urlLogo;
    }

    public Boolean containFoodInMenu(String foodName){
        for (int i=0;i<menus.size();i++){
            if(menus.get(i).getName().equals(foodName)){
                return true;
            }
        }
        return false;
    }

    public String getName(){
        return name;
    }

    public void addNewMenuToRestaurant(Menu new_menu) throws FoodExist{
        String new_food_name = new_menu.getName();
        if(!containFoodInMenu(new_food_name)){
            menus.add(new_menu);
            System.out.println("new food (" + new_food_name +") " + "add to menu of restaurant " + this.name +"  Successfully!");
        }
        else{
            throw new FoodExist("You want to add " + new_food_name + " as a new food to menu that already exist this food in menu!");
        }
    }


    public double averageFoodPopulation(){
        double sum = 0.0;
        for (int i=0;i<menus.size();i++){
            sum+= menus.get(i).getPopularity();
        }
        return sum/menus.size();
    }

    public double distanceFromUser(){
        return Math.sqrt(location.getX()*location.getX() + location.getY()*location.getY());
    }

    public Menu findFoodMenu(String name){
        for(int i=0;i<menus.size();i++){
            if(menus.get(i).getName().equals(name)){
                return menus.get(i);
            }
        }
        return null;
    }

    public String getDescription(){
        return this.description;
    }

    public String getId(){
        return this.id;
    }

    @JsonIgnore
    public ArrayList<String> getAllFoods() {
        ArrayList<String> foodsName = new ArrayList<String>();
        for(Menu m: this.menus)
            foodsName.add(m.getName());
        return foodsName;
    }


    public Location getLocation() { return this.location; }

    public ArrayList<Menu> getMenus() {return this.menus; }

    @JsonIgnore
    public String getImageUrl(){
        return  this.imageUrl;
    }
}
