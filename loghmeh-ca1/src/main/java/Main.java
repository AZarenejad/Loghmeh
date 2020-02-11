import Classes.Interface;
import Exceptions.*;
import org.json.simple.JSONObject;
import org.json.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import utilities.Utility;


public class Main {
    public static void print(Object object){
        java.lang.System.out.println(object);
    }
    public static void main(String[] args) throws IOException,RestaurantExist , RestaurantNotExist, FoodExist , InvalidCommand {

        Interface system = new Interface();
        String command = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(java.lang.System.in));
        while(true) {
            command = reader.readLine();
            Utility.executeCommand(command, system);
        }

    }
}