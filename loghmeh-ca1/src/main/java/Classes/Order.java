package Classes;

public class Order {
    private String foodName;
    private int count;



    public Order(String foodName){
        this.foodName = foodName;
        count = 1 ;
    }


    public String getFoodName(){
        return this.foodName;
    }

    public int getCount() { return this.count; }

    public void increase_count(){
        this.count += 1;
    }








}
