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
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

    //@FXML Annotations
    @FXML
    private Button addPartButton;
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvLevelCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private Button modifyPartButton;
    @FXML
    private Button deletePartButton;
    @FXML
    private TextField productSearchBar;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInvLevelCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    @FXML
    private Button addProductButton;
    @FXML
    private Button modifyProductButton;
    @FXML
    private Button deleteProductButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label TheLabel;
    @FXML
    private TextField partSearchBar;
    @FXML
    private Button partSearchBarBtn;
    @FXML
    private Button productSearchBarBtn;


    //Action Handlers


    /**
     *
     * @param actionEvent when clicked, open AddPartMenu
     * @throws IOException
     */
    @FXML
    void onActionOpenAddPart(ActionEvent actionEvent) throws IOException {

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     *
     * @param actionEvent when clicked, open ModifyPartMenu if an item is selected
     * @throws IOException
     */
    @FXML
    void onActionOpenModPart(ActionEvent actionEvent) throws IOException {

        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPartMenu.fxml"));
            loader.load();

            ModifyPartMenuController MPartMController = loader.getController();
            MPartMController.sendPart(partTableView.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part to modify");
            alert.showAndWait();
        }



    }

    /**
     *
     * @param actionEvent when clicked, deletes a part if one is selected
     */
    @FXML
    void onActionDeletePart(ActionEvent actionEvent) {

        Part partToDelete = partTableView.getSelectionModel().getSelectedItem();
        if(partTableView.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR: No Part Selected");
            alert.setContentText("Please select a part to delete");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure you want to delete part: " + partToDelete.getName() + "?");
            alert.setContentText("Click Ok to confirm");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK)
            {
                Inventory.deletePart(partToDelete);
            }
        }

    }

    /**
     *
     * @param actionEvent when clicked, open AddProductMenu
     * @throws IOException
     */
    @FXML
    void onActionOpenAddProduct(ActionEvent actionEvent) throws IOException {

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     *
     * @param actionEvent when clicked, open ModifyProductMenu if an item is selected
     * @throws IOException
     */
    @FXML
    void onActionOpenModProduct(ActionEvent actionEvent) throws IOException {

        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProductMenu.fxml"));
            loader.load();

            ModifyProductMenuController MProductMController = loader.getController();
            MProductMController.sendProduct(productTableView.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please select a product to modify");
            alert.showAndWait();
        }
    }

    /**
     *
     * @param actionEvent when clicked, deletes a part if one is selected
     */
    @FXML
    void onActionDeleteProduct(ActionEvent actionEvent) {

        Product productToDelete = productTableView.getSelectionModel().getSelectedItem();
        if(productTableView.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR: No Product Selected");
            alert.setContentText("Please select a product to delete");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure you want to delete product: " + productToDelete.getName() + "?");
            alert.setContentText("Click Ok to confirm");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK)
            {
                Inventory.deleteProduct(productToDelete);
            }//If they press ok then remove product
        }
    }

    /**
     *
     * @param actionEvent when search clicked, searches for part by id and name and returns if it is contained in allParts list
     */
    @FXML
    void onActionSearchPart(ActionEvent actionEvent) {

        try
        {
            Inventory.lookupPart(partSearchBar.getText());
            if(Inventory.getAllFilteredParts().isEmpty())
            {
                Part part = Inventory.lookupPart(Integer.parseInt(partSearchBar.getText()));
                Inventory.getAllFilteredParts().add(part);
            }
            partTableView.setItems(Inventory.getAllFilteredParts());
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
     * @param actionEvent when search clicked, searches for part by id and name and returns if it is contained in allProducts list
     */
    @FXML
    void onActionSearchProduct(ActionEvent actionEvent) {

        try
        {
            Inventory.lookupProduct(productSearchBar.getText());
            if(Inventory.getAllFilteredProducts().isEmpty())
            {
                Product product = Inventory.lookupProduct(Integer.parseInt(productSearchBar.getText()));
                Inventory.getAllFilteredProducts().add(product);
            }
            productTableView.setItems(Inventory.getAllFilteredProducts());
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
     * @param actionEvent when clicked, exits program
     */
    @FXML
    void onActionExit(ActionEvent actionEvent) {

        System.exit(0);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

        System.out.println("Initialized");
    }

}
