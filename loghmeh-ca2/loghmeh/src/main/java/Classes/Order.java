package Classes;

public class Order {
    private String foodName;
    private int count;
    private long foodPrice;



    public Order(String foodName , long foodPrice){
        this.foodName = foodName;
        count = 1 ;
        this.foodPrice = foodPrice;
    }


    public String getFoodName(){
        return this.foodName;
    }

    public int getCount() { return this.count; }

    public void increase_count(){
        this.count += 1;
    }


    public long getFoodPrice(){
        return this.foodPrice;
    }








}
