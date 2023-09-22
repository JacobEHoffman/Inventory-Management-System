package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/**
 * Javadoc files are included in the Javadocs file contained in the submission
 *
 * FUTURE ENHANCEMENT: One function of the program that I thought of but did not implement is a check to ensure that there is a sufficient
 * inventory of associated parts to be able to create a specific product. For example if a product required 2 of part A
 * and 1 of part B to produce, there would be a check and ensuing error if one were to attempt to produce the product
 * when there is only 1 part A in the inventory.
 */
public class Main extends Application {


    @Override
    public void init(){
        System.out.println("starting");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        //primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 900, 300));
        primaryStage.show();
    }

    @Override
    public void stop(){
        System.out.println("Terminated");
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        /**
            Sample parts that are an instance of InHouse to populate the main menu parts table view
         */
        Inventory.addPart(new InHouse(1, "D20", 0.55, 15, 1,50, 20));
        Inventory.addPart(new InHouse(2, "D12", 0.55, 25, 1, 50, 12));
        Inventory.addPart(new InHouse(3, "D6", 0.55, 20, 1, 50, 6));
        Inventory.addPart(new InHouse(4, "D4", 0.55, 35, 1, 50, 3));
        Inventory.addPart(new InHouse(5, "DND Rulebook", 5.99, 20, 1, 50, 30));


        /**
         * Sample parts that are an instance of Outsourced to populate the main menu parts table view
         */
        Inventory.addPart(new Outsourced(6, "Goblin Miniature", 1.99, 22, 1, 100, "Miniature Mania"));
        Inventory.addPart(new Outsourced(7, "Orc Miniature", 1.99, 33, 1, 100, "Miniature Mania"));
        Inventory.addPart(new Outsourced(8, "Human Token", 0.99, 70, 1, 100, "Token Craft"));
        Inventory.addPart(new Outsourced(9, "Elf Token", 0.99, 55, 1, 100, "Token Craft"));
        Inventory.addPart(new Outsourced(10, "Dwarf Token", 0.99, 46, 1, 100, "Token Craft"));

        /**
         * Sample products to populate the main menu products table view
         */
        Inventory.addProduct(new Product(1,"DND Starter Set", 19.99, 10, 1, 20));
        Inventory.addProduct(new Product(2, "DND Dice Pack", 4.99, 5, 1, 20));
        Inventory.addProduct(new Product(3, "DND Monster Miniature Pack", 9.99, 10, 1, 50));
        Inventory.addProduct(new Product(4, "DND Character Token Pack", 4.99, 30, 1,50));

        launch(args);

    }
}
