package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> allFilteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allFilteredProducts = FXCollections.observableArrayList();

    /**
     *
     * @param part the part to add to allParts observable list
     */
    public static void addPart(Part part){
        allParts.add(part);
    }

    /**
     *
     * @param product the product to add to allProducts observable list
     */
    public static void addProduct(Product product){
        allProducts.add(product);
    }

    /**
     *
     * @param partId search allParts observable list
     * @return the applicable part if found otherwise return null
     */
    public static Part lookupPart(int partId){

        for(Part part : getAllParts())
        {
            if(part.getId() == partId)
            {
                return part;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No part found");
        alert.show();
        return null;

    }

    /**
     *
     * @param productId search allProducts observable list
     * @return the applicable product if found otherwise return null
     */
    public static Product lookupProduct(int productId){

        for(Product product : getAllProducts())
        {
            if(product.getId() == productId)
            {
                return product;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No product found");
        alert.show();
        return null;
    }

    /**
     *
     * @param partName search allParts observable list
     * @return the applicable part(s) if found otherwise return full list
     */
    public static ObservableList<Part> lookupPart(String partName){

        if(!getAllFilteredParts().isEmpty())
        {
            getAllFilteredParts().clear();
        }

        for(Part part : getAllParts())
        {
            if(part.getName().contains(partName))
            {
                getAllFilteredParts().add(part);
            }
        }

        if(getAllFilteredParts().isEmpty())
        {
            return getAllParts();
        }
        else {
            return getAllFilteredParts();
        }
    }

    /**
     *
     * @param productName search allProducts observable list
     * @return the applicable product(s) if found otherwise return full list
     */
    public static ObservableList<Product> lookupProduct(String productName){

        if(!getAllFilteredProducts().isEmpty())
        {
            getAllFilteredProducts().clear();
        }

        for(Product product : getAllProducts())
        {
            if(product.getName().contains(productName))
            {
                getAllFilteredProducts().add(product);
            }
        }

        if(getAllFilteredProducts().isEmpty())
        {
            return getAllProducts();
        }
        else {
            return getAllFilteredProducts();
        }
    }

    /**
     *
     * @param index the index related to part id
     * @param selectedPart the part to update
     */
    public static void updatePart(int index, Part selectedPart){

        int i =-1;

        for(Part part : getAllParts())
        {
            i++;

            if(part.getId() == index)
            {
                getAllParts().set(i, selectedPart);
            }
        }
    }

    /**
     *
     * @param index the index related to product id
     * @param newProduct the product to update
     */
    public static void updateProduct(int index, Product newProduct){

        int i =-1;

        for(Product product : getAllProducts())
        {
            i++;

            if(product.getId() == index)
            {
                getAllProducts().set(i, newProduct);
            }
        }
    }

    /**
     *
     * @param part the part to delete
     * @return true and remove part if is contained in allParts list, otherwise return false
     */
    public static boolean deletePart(Part part){

        if(allParts.contains(part))
        {
            return allParts.remove(part);
        }
        else
        {
            return false;
        }

    }

    /**
     *
     * @param product the product to delete
     * @return true and remove product if is contained in allProducts list, otherwise return false
     */
    public static boolean deleteProduct(Product product){

        if(allProducts.contains(product))
        {
            return allProducts.remove(product);
        }
        else
        {
            return false;
        }

    }


    /**
     *
     * @return the observable list allParts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     *
     * @return the observable list allProducts
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /**
     *
     * @return the observable list allFilteredParts
     */
    public static ObservableList<Part> getAllFilteredParts(){
        return allFilteredParts;
    }

    /**
     *
     * @return the observable list allFilteredProducts
     */
    public static ObservableList<Product> getAllFilteredProducts(){
        return allFilteredProducts;
    }


}
