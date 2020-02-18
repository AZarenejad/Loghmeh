package Classes;

import org.json.simple.JSONObject;

public class Location {
    private long x;
    private long y;

    public Location(JSONObject locationInfo){
        this.x = (long)locationInfo.get("x");
        this.y = (long) locationInfo.get("y");
    }

    public Location(long x , long y){
        setX(x);
        setY(y);
    }

    public void setX(long x){
        this.x = x;
    }
    public void setY(long y){
        this.y = y;
    }

    public long getX(){
        return x;
    }
    public long getY(){
        return y;
    }


    public String toString(){
        return "(" + Long.toString(this.x) + "," + Long.toString(this.y) +")";
    }


}
