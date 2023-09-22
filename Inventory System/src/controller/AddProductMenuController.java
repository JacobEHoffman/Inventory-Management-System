package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.getAllParts;
import static model.Inventory.getAllProducts;

public class AddProductMenuController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<Part> addProductTable;
    @FXML
    private TableColumn<Part, Integer> addProductPartIdCol;
    @FXML
    private TableColumn<Part, String> addProductNameCol;
    @FXML
    private TableColumn<Part, Integer> addProductInvLevelCol;
    @FXML
    private TableColumn<Part, Double> addProductPriceCol;
    @FXML
    private TableView<Part> associatedTable;
    @FXML
    private TableColumn<Part, Integer> associatedPartIdCol;
    @FXML
    private TableColumn<Part, String> associatedNameCol;
    @FXML
    private TableColumn<Part, Integer> associatedInvLevelCol;
    @FXML
    private TableColumn<Part, Double> associatedPriceCol;
    @FXML
    private Button addProductSearchBtn;
    @FXML
    private Button addProductAddBtn;
    @FXML
    private Button addProductRemovePartBtn;
    @FXML
    private Button addProductSaveBtn;
    @FXML
    private Button addProductCancelBtn;
    @FXML
    private TextField addProductTxt;
    @FXML
    private TextField addProductID;
    @FXML
    private TextField addProductName;
    @FXML
    private TextField addProductInv;
    @FXML
    private TextField addProductPrice;
    @FXML
    private TextField addProductMax;
    @FXML
    private TextField addProductMin;

    /**
     *
     * @param actionEvent when search clicked, search for part to add to associated parts
     */
    @FXML
    void onActionSearchAddProduct(ActionEvent actionEvent) {

        try
        {
            Inventory.lookupPart(addProductTxt.getText());
            if(Inventory.getAllFilteredParts().isEmpty())
            {
                Part part = Inventory.lookupPart(Integer.parseInt(addProductTxt.getText()));
                Inventory.getAllFilteredParts().add(part);
            }
            addProductTable.setItems(Inventory.getAllFilteredParts());
        }
        catch(NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Search Inconclusive");
            alert.setContentText("Unable to find a part pertaining to search, please try again");
            alert.showAndWait();
        }
    }

    /**
     *
     * @param actionEvent when add clicked, add part to associated parts if one is selected
     */
    @FXML
    void onActionAddProduct(ActionEvent actionEvent) {

        addProductTable.getSelectionModel().getSelectedItem();
        if(addProductTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR: No Part Selected");
            alert.setContentText("Please select a part to add");
            alert.showAndWait();
        }
        else
        {
            Product.addAssociatedPart(addProductTable.getSelectionModel().getSelectedItem());
        }

    }

    /**
     *
     * @param actionEvent when remove associated part clicked, remove associated part from list
     */
    @FXML
    void onActionRemoveProduct(ActionEvent actionEvent) {

        associatedTable.getSelectionModel().getSelectedItem();
        if(associatedTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR: No Part Selected");
            alert.setContentText("Please select an associated part to remove");
            alert.showAndWait();
        }
        else
        {
            Product.deleteAssociatedPart(associatedTable.getSelectionModel().getSelectedItem());
        }
    }

    /**
     *
     * @param actionEvent when save clicked, save modified product to allProducts list and return to main menu
     */
    @FXML
    void onActionSaveAddProduct(ActionEvent actionEvent) {

        try
        {
            int id = getAllProducts().size()+1;
            String name = addProductName.getText();
            int stock = Integer.parseInt(addProductInv.getText());
            double price = Double.parseDouble(addProductPrice.getText());
            int max = Integer.parseInt(addProductMax.getText());
            int min = Integer.parseInt(addProductMin.getText());


            if(max < min)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialogue");
                alert.setContentText("The max stock for an item must be more than the minimum");
                alert.showAndWait();
                return;
            }
            else if(stock > max || stock < min)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialogue");
                alert.setContentText("The current inventory level must be within the set min and max");
                alert.showAndWait();
                return;
            }
            else
            {
                Inventory.addProduct(new Product(id, name, price, stock, min, max));
            }



            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }
        catch(NumberFormatException | IOException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Please enter a valid value for each text field");
            alert.showAndWait();
        }
    }

    /**
     *
     * @param actionEvent when cancel clicked, check with user then return to main menu
     * @throws IOException
     */
    @FXML
    void onActionCancelAddProduct(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");

        addProductTable.setItems(Inventory.getAllParts());
        addProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedTable.setItems(Product.getAllAssociatedParts());
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }



}
