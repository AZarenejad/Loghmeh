package Classes;

import org.json.simple.JSONObject;

public class Menu {
    private String name;
    private String description;
    private double popularity;
    private long price;

    public Menu(JSONObject menuInfo){
        this.name = menuInfo.get("name").toString();
        this.description = menuInfo.get("description").toString();
        this.popularity = (double) menuInfo.get("popularity");
        this.price = (long) menuInfo.get("price");
    }

    public Menu(String foodName , String foodDescription , double popularity , long price){
        this.name = foodName;
        this.description = foodDescription;
        this.popularity = popularity;
        this.price = price;
    }

    public String getName(){
        return name;
    }

    public double getPopularity(){
        return popularity;
    }

    public String getDescription() { return this.description; }

    public long getPrice() { return this.price; }

}
