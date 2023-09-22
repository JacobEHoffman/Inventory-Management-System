package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.getAllParts;

public class AddPartMenuController implements Initializable {



    Stage stage;
    Parent scene;

    @FXML
    private RadioButton addPartHouseRadio;
    @FXML
    private RadioButton addPartOutRadio;
    @FXML
    private ToggleGroup addInOut;
    @FXML
    private TextField addPartID;
    @FXML
    private TextField addPartName;
    @FXML
    private TextField addPartInv;
    @FXML
    private TextField addPartPrice;
    @FXML
    private TextField addPartMax;
    @FXML
    private TextField addPartMachineIdOrCompany;
    @FXML
    private TextField addPartMin;
    @FXML
    private Label addMachineOrCompany;
    @FXML
    private Button addPartSaveButton;
    @FXML
    private Button addPartCancelButton;

    /**
     *
     * @param actionEvent set text to machine id if part is InHouse
     */
    @FXML
    void onActionAddPartIn(ActionEvent actionEvent) {
        addMachineOrCompany.setText("Machine ID");
    }

    /**
     *
     * @param actionEvent set text to company name if part is Outsourced
     */
    @FXML
    void onActionAddPartOut(ActionEvent actionEvent) {
        addMachineOrCompany.setText("Company Name");
    }

    /**
     *
     * @param actionEvent If save is clicked, save new part to allParts list and return to main menu
     * @throws IOException
     *
     * RUNTIME ERROR: I came upon an issue with the InHouse and Outsourced radio buttons in which they were both able to be selected and
     * deselected at the same time. To resolve this issue I added them to a toggle group in which only one could be selected
     * at a time and thus only display one text(Machine ID or Company Name).
     */
    @FXML
    void onActionSaveAddPart(ActionEvent actionEvent) throws IOException {

        try
        {
            int id = getAllParts().size()+1;
            String name = addPartName.getText();
            int stock = Integer.parseInt(addPartInv.getText());
            double price = Double.parseDouble(addPartPrice.getText());
            int max = Integer.parseInt(addPartMax.getText());
            int min = Integer.parseInt(addPartMin.getText());
            int machineId = 0;
            String companyName = null;

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
            else if(!addPartHouseRadio.isSelected() && !addPartOutRadio.isSelected())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialogue");
                alert.setContentText("Please select whether the part is InHouse or Outsourced");
                alert.showAndWait();
                return;
            }
            else if(addPartHouseRadio.isSelected()){
                machineId = Integer.parseInt(addPartMachineIdOrCompany.getText());
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
            }
            else if(addPartOutRadio.isSelected()){
                companyName = addPartMachineIdOrCompany.getText();
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
            }


            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }
        catch(NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Please enter a valid value for each text field");
            alert.showAndWait();
        }
    }

    /**
     *
     * @param actionEvent when cancel clicked, check with user and then return to main menu
     * @throws IOException
     */
    @FXML
    void onActionCancelAddPart(ActionEvent actionEvent) throws IOException {

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
    }



}
