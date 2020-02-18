package Classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.json.simple.JSONObject;

public class Menu {
    private String name;
    private String description;
    private double popularity;
    private long price;
    private String urlImage;

    public Menu(JSONObject menuInfo){
        this.name = menuInfo.get("name").toString();
        this.description = menuInfo.get("description").toString();
        this.popularity = (double) menuInfo.get("popularity");
        this.price = (long) menuInfo.get("price");
        this.urlImage = menuInfo.get("image").toString();
    }

    public Menu(String foodName , String foodDescription , double popularity , long price , String urlImage){
        this.name = foodName;
        this.description = foodDescription;
        this.popularity = popularity;
        this.price = price;
        this.urlImage = urlImage;
    }

    public String getName(){
        return name;
    }

    public double getPopularity(){
        return popularity;
    }

    public String getDescription() { return this.description; }

    public long getPrice() { return this.price; }

    @JsonIgnore
    public String getUrlImage(){
        return this.urlImage;
    }

}
