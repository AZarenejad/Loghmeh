package Classes;

import org.json.simple.JSONObject;

public class Location {
    private double x;
    private double y;

    public Location(JSONObject locationInfo){
        this.x = (double)locationInfo.get("x");
        this.y = (double) locationInfo.get("y");
    }

    public Location(double x , double y){
        setX(x);
        setY(y);
    }

    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }


}
