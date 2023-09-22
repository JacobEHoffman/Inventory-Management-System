package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    /**
     * declare fields
     */
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id, stock, min, max;
    private String name;
    private double price;

    /**
     * constructor
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    /**
     *    Declare Methods
     */

    /**
     *   Setters
     */

    /**
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @param stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     *
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     *
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Getters
     */

    /**
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }
    /**
     *
     * @return the stock
     */
    public int getStock() {
        return stock;
    }
    /**
     *
     * @return the min
     */
    public int getMin() {
        return min;
    }
    /**
     *
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * Modify Functions
     */

    /**
     *
     * @param part add part to associated parts observable list
     */
    public static void addAssociatedPart(Part part){ associatedParts.add(part); }

    /**
     *
     * @param part delete part from associated parts observable list
     * @return true and remove part if it is in the list, else return false
     */
    public static boolean deleteAssociatedPart(Part part){

        if(associatedParts.contains(part))
        {
            return associatedParts.remove(part);
        }
        else
        {
            return false;
        }
    }

    /**
     *
     * @return associated parts list
     */
    public static ObservableList<Part> getAllAssociatedParts(){ return associatedParts; }


}
