package utilities;

import Classes.Interface;
import Exceptions.*;

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
            System.out.println("An exception occur!");
        }
    }
}
