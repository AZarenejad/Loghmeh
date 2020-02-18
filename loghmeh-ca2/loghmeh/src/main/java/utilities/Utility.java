package utilities;

import Classes.Interface;
import Exceptions.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

public class Utility {

    public static void executeCommand(String command, Interface system) throws IOException {
        try {
            system.manageCommand(command);
        }catch(RestaurantExist e){
            System.out.println(e.getMessage());
        }catch (RestaurantNotExist e){
            System.out.println(e.getMessage());
        }catch (FoodExist e){
            System.out.println(e.getMessage());
        }catch (FoodNotExist e){
            System.out.println(e.getMessage());
        }catch (BuyError e){
            System.out.println(e.getMessage());
        }catch (InvalidCommand e){
            System.out.println(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addRestaurantFromJSONArray(Interface system, JSONArray jsonArray) {
        for(int i=0;i<jsonArray.size();i++){
            try{
                system.addRestaurantCommand((JSONObject)jsonArray.get(i));
            }catch (RestaurantExist e){
                System.out.println(e.getMessage());
            }
        }
    }
}

