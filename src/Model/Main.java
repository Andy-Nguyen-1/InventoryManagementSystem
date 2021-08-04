package Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Andy Nguyen
 */

  /**
   * This class Runs the inventory management system.
   * <p><b>
   * A future enhancement that I would add to this application is a "last modified date/time" section. 
   * This will help employees and management keep track of when the product or part was last modified.
   * </b></p>
   */
public class Main extends Application {

    public static StringBuilder errorMessages = new StringBuilder();

    /**
     * This method loads the Main form of the application.
     * @param primaryStage
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * This main method launches the application.
     * @param args
     */
    public static void main(String[] args) {
        //create some parts and add them into inventory
        InHouse obj1 = new InHouse(1, "Brakes", 15.00, 10, 2, 13, 2324);
        InHouse obj2 = new InHouse(2, "Wheel", 11.00, 16, 1, 18, 2325);
        Outsourced obj3 = new Outsourced(3, "Seat", 15.00, 10, 5, 100, "Toyota");
        Inventory.addPart(obj1);
        Inventory.addPart(obj2);
        Inventory.addPart(obj3);

        // Create some products and add them into inventory
        Product obj4 = new Product(1000, "Giant Bike", 299.99, 5, 1, 8);
        Product obj5 = new Product(1001, "Tricycle", 99.99, 3, 1, 12);
        Inventory.addProduct(obj4);
        Inventory.addProduct(obj5);
        launch(args);
    }
}
