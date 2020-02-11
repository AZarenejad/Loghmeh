import Classes.Interface;
import Classes.Order;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utilities.Utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class loghmehTest {
    private Utility utils;
    Interface system;
    String filename;

    @Before
    public void setup() throws IOException {
        this.filename = "src/main/resources/TestJson.txt";
        utils = new Utility();
        ArrayList<String> commands = (ArrayList<String>) Files.readAllLines(Paths.get(this.filename));
        system = new Interface();
        for (String command : commands) {
            utils.executeCommand(command, system);
        }
        commands.clear();
    }

    @Test
    public void finalizeOrderTest() {
        ArrayList<Order> orders = this.system.getUser().getOrders();
        TestCase.assertTrue(orders.isEmpty());
        TestCase.assertNull(this.system.getUser().giveYourRestaurantYouBuy());
    }

    @Test
    public void AddFoodTest() {
        ArrayList<String> expFoods = new ArrayList<String>();
        expFoods.add("gheimeh");
        expFoods.add("kabab");
        TestCase.assertEquals(expFoods, system.findRestaurant("RestaurantBB").getAllFoods());
    }

    @Test
    public void getRecommendedRestaurantsTest() {
        String[] topRestaurantExp = {"RestaurantBB", "RestaurantAA", "RestaurantCC"};
        String[] topRestaurantAct = system.getRecommendedRestaurants();
        TestCase.assertEquals(topRestaurantAct[0], topRestaurantExp[0]);
        TestCase.assertEquals(topRestaurantAct[1], topRestaurantExp[1]);
        TestCase.assertEquals(topRestaurantAct[2], topRestaurantExp[2]);
    }

    @After
    public void teardown() {
        this.utils = null;
        this.system = null;
    }
}
