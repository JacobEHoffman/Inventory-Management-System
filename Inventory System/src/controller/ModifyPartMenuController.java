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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyPartMenuController implements Initializable {


    Stage stage;
    Parent scene;

    @FXML
    private RadioButton modPartHouseRadio;
    @FXML
    private RadioButton modPartOutRadio;
    @FXML
    private ToggleGroup modInOut;
    @FXML
    private TextField modifyPartID;
    @FXML
    private TextField modifyPartName;
    @FXML
    private TextField modifyPartInv;
    @FXML
    private TextField modifyPartPrice;
    @FXML
    private TextField modifyPartMax;
    @FXML
    private TextField modifyPartMachineIdOrCompany;
    @FXML
    private TextField modifyPartMin;
    @FXML
    private Label modMachineOrCompany;
    @FXML
    private Button modPartSaveButton;
    @FXML
    private Button modPartCancelButton;

    /**
     *
     * @param actionEvent set text to machine id if part is InHouse
     */
    @FXML
    void onActionModPartIn(ActionEvent actionEvent) {modMachineOrCompany.setText("Machine ID"); }

    /**
     *
     * @param actionEvent set text to company name if part is Outsourced
     */
    @FXML
    void onActionModPartOut(ActionEvent actionEvent) {modMachineOrCompany.setText("Company Name"); }

    /**
     *
     * @param actionEvent If save is clicked, save modified part to allParts list and return to main menu
     * @throws IOException
     */
    @FXML
    void onActionSaveModPart(ActionEvent actionEvent) throws IOException {

        try
        {
            int id = Integer.parseInt(modifyPartID.getText());
            String name = modifyPartName.getText();
            int stock = Integer.parseInt(modifyPartInv.getText());
            double price = Double.parseDouble(modifyPartPrice.getText());
            int max = Integer.parseInt(modifyPartMax.getText());
            int min = Integer.parseInt(modifyPartMin.getText());
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
            else if(!modPartHouseRadio.isSelected() && !modPartOutRadio.isSelected())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialogue");
                alert.setContentText("Please select whether the part is InHouse or Outsourced");
                alert.showAndWait();
                return;
            }
            else if(modPartHouseRadio.isSelected()){
                machineId = Integer.parseInt(modifyPartMachineIdOrCompany.getText());
                Inventory.updatePart(id, new InHouse(id, name, price, stock, min, max, machineId));
            }
            else{
                companyName = modifyPartMachineIdOrCompany.getText();
                Inventory.updatePart(id, new Outsourced(id, name, price, stock, min, max, companyName));
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
    void onActionCancelModPart(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Your changes will not be saved");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     *
     * @param part send part information to be modified
     */
    public void sendPart(Part part)
    {
        modifyPartID.setText(String.valueOf(part.getId()));
        modifyPartName.setText(part.getName());
        modifyPartInv.setText(String.valueOf(part.getStock()));
        modifyPartPrice.setText(String.valueOf(part.getPrice()));
        modifyPartMax.setText(String.valueOf(part.getMax()));
        modifyPartMin.setText(String.valueOf(part.getMin()));

        if(part instanceof InHouse)
        {
            modPartHouseRadio.setSelected(true);
            modMachineOrCompany.setText("Machine ID");
            modifyPartMachineIdOrCompany.setText(String.valueOf(((InHouse) part).getMachineId()));
        }
        else if(part instanceof Outsourced)
        {
            modPartOutRadio.setSelected(true);
            modMachineOrCompany.setText("Company Name");
            modifyPartMachineIdOrCompany.setText(String.valueOf(((Outsourced) part).getCompanyName()));
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");
    }
}
