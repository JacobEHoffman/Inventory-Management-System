package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.getAllProducts;

public class ModifyProductMenuController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<Part> modProductTable;
    @FXML
    private TableColumn<Part, Integer> modProductPartIdCol;
    @FXML
    private TableColumn<Part, String> modProductNameCol;
    @FXML
    private TableColumn<Part, Integer> modProductInvLevelCol;
    @FXML
    private TableColumn<Part, Double> modProductPriceCol;
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
    private TextField modifyProductID;
    @FXML
    private TextField modifyProductName;
    @FXML
    private TextField modifyProductInv;
    @FXML
    private TextField modifyProductPrice;
    @FXML
    private TextField modifyProductMax;
    @FXML
    private TextField modifyProductMin;
    @FXML
    private Button modProductAddBtn;
    @FXML
    private Button modProductRemovePartBtn;
    @FXML
    private Button modProductSaveBtn;
    @FXML
    private Button modProductCancelBtn;
    @FXML
    private TextField modProductTxt;
    @FXML
    private Button modProductSearchBtn;

    /**
     *
     * @param actionEvent when search clicked, search for part to add to associated parts
     */
    @FXML
    void onActionSearchModProduct(ActionEvent actionEvent) {

        try
        {
            Inventory.lookupPart(modProductTxt.getText());
            if(Inventory.getAllFilteredParts().isEmpty())
            {
                Part part = Inventory.lookupPart(Integer.parseInt(modProductTxt.getText()));
                Inventory.getAllFilteredParts().add(part);
            }
            modProductTable.setItems(Inventory.getAllFilteredParts());
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
    void OnActionAddModProduct(ActionEvent actionEvent) {

        modProductTable.getSelectionModel().getSelectedItem();
        if(modProductTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR: No Part Selected");
            alert.setContentText("Please select a part to add");
            alert.showAndWait();
        }
        else
        {
            Product.addAssociatedPart(modProductTable.getSelectionModel().getSelectedItem());
        }
    }

    /**
     *
     * @param actionEvent when remove associated part clicked, remove associated part from list
     */
    @FXML
    void OnActionRemoveModProduct(ActionEvent actionEvent) {

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
    void onActionSaveModProduct(ActionEvent actionEvent) {

        try
        {
            int id = getAllProducts().size()+1;
            String name = modifyProductName.getText();
            int stock = Integer.parseInt(modifyProductInv.getText());
            double price = Double.parseDouble(modifyProductPrice.getText());
            int max = Integer.parseInt(modifyProductMax.getText());
            int min = Integer.parseInt(modifyProductMin.getText());


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
                Inventory.updateProduct(id, new Product(id, name, price, stock, min, max));
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
    void onActionCancelModProduct(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Your changes will not be saved");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     *
     * @param product send product information to be modified
     */
    public void sendProduct(Product product){

        modifyProductID.setText(String.valueOf(product.getId()));
        modifyProductName.setText(product.getName());
        modifyProductInv.setText(String.valueOf(product.getStock()));
        modifyProductPrice.setText(String.valueOf(product.getPrice()));
        modifyProductMax.setText(String.valueOf(product.getMax()));
        modifyProductMin.setText(String.valueOf(product.getMin()));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Initialized");

        modProductTable.setItems(Inventory.getAllParts());
        modProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProductInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedTable.setItems(Product.getAllAssociatedParts());
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }



}
