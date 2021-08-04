package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;

import java.io.IOException;
import java.util.Optional;

/**
 * This class creates the ModifyPartFormController window.
 */
public class ModifyPartFormController {

    private Stage stage; // Required in order to build window
    private Parent scene;

    @FXML
    private RadioButton InHouseRadioBtn;
    @FXML
    private ToggleGroup modifyToggleGroup;
    @FXML
    private RadioButton OutsourcedRadioBtn;
    @FXML
    private TextField modifyPartIDTxt;
    @FXML
    private TextField modifyPartNameTxt;
    @FXML
    private TextField modifyPartInvTxt;
    @FXML
    private TextField modifyPartPriceTxt;
    @FXML
    private TextField modifyPartMaxTxt;
    @FXML
    private TextField modifyPartmachineIdTxt;
    @FXML
    private TextField modifyPartMinTxt;
    @FXML
    private Label modifyMCLabel;

    Alert alert = new Alert(Alert.AlertType.NONE);

    /** 
     * This in House radio button action sets the Machine Id text on label.
     */
    
    @FXML
    private void InHouseRadioBtn(ActionEvent event) {
        modifyMCLabel.setText("Machine ID");
    }

    /**
     * This Outsourced radio button action sets theCompany Name text on label.
     */
    @FXML
    private void OutsourcedRadioBtn(ActionEvent event) {
        modifyMCLabel.setText("Company Name");
    }

    /**
     * This button action will validate the input values given in the text fields 
     * and then modify the part details in inventory list by replacing new values at the index of old values.
     */ 
    @FXML
    private void modifyPartSaveBtn(ActionEvent event) throws IOException {
        alert.setAlertType(Alert.AlertType.ERROR);
        try {
            //find the object of obl part
            Part obj = Inventory.lookupPart(Integer.parseInt(modifyPartIDTxt.getText()));
            int oldIndex = Inventory.getAllParts().indexOf(obj);

            //store inputs in variables
            String partName = modifyPartNameTxt.getText();
            int partID = Integer.parseInt(modifyPartIDTxt.getText());
            int minPrice = Integer.parseInt(modifyPartMinTxt.getText());
            int maxPrice = Integer.parseInt(modifyPartMaxTxt.getText());
            int invPrice = Integer.parseInt(modifyPartInvTxt.getText());
            double partPrice = Double.parseDouble(modifyPartPriceTxt.getText());

            //perform validations
            if (minPrice < 0) {
                alert.setContentText("Min must be greater then 0.");
                alert.show();
            } else if (maxPrice < 0) {
                alert.setContentText("Max must be greater then 0.");
                alert.show();
            } else if (partPrice < 0) {
                alert.setContentText("Price must be greater then 0.");
                alert.show();
            } else if (minPrice > maxPrice) {
                alert.setContentText("Min price must be less or equal to Max price.");
                alert.show();
            } else if (invPrice > maxPrice || invPrice < minPrice) {
                alert.setContentText("Part Inv must be between Min and Max.");
                alert.show();
            } else {
                //after validation if Outsourced radio button is selected then call the upDatePart method in inventory class and pas the objects
                if (OutsourcedRadioBtn.isSelected()) {
                    Inventory.updatePart(oldIndex, new Outsourced(partID, partName, partPrice, invPrice, minPrice, maxPrice, modifyPartmachineIdTxt.getText()));

                } else {
                    //after validation if InHouse radio button is selected then call the upDatePart method in inventory class and pas the objects
                    Inventory.updatePart(oldIndex, new InHouse(partID, partName, partPrice, invPrice, minPrice, maxPrice, Integer.parseInt(modifyPartmachineIdTxt.getText())));
                }

                //after updating return back to Main Form screen
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch (NumberFormatException e) {
            alert.setContentText("An Error Has Occurred " + e.getMessage());
            alert.show();

        }
    }

    /**
    * This cancel button action will confirm cancellation and return user to Main Form screen if OK is pressed.
    */
    @FXML
    private void modifyPartCancelBtn(ActionEvent event) throws IOException {
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel confirmation");
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**
     * This method sets the data in text fields of Modify Part Form that a user selects from table for updating
     * so a user can easily see previous data in fields and can change any data.
     * @param part
     */
    public void setDataOnTextFields(Part part) {
        modifyPartIDTxt.setText(String.valueOf(part.getId()));
        modifyPartNameTxt.setText(part.getName());
        modifyPartInvTxt.setText(String.valueOf(part.getStock()));
        modifyPartPriceTxt.setText(String.format("%,.2f", part.getPrice()));
        modifyPartMinTxt.setText(String.valueOf(part.getMin()));
        modifyPartMaxTxt.setText(String.valueOf(part.getMax()));

        if (part instanceof Outsourced) {
            OutsourcedRadioBtn.setSelected(true);
            InHouseRadioBtn.setSelected(false);
            modifyMCLabel.setText("Company Name");
            modifyPartmachineIdTxt.setText(((Outsourced) part).getCompanyName());
        }

        if (part instanceof InHouse) {
            InHouseRadioBtn.setSelected(true);
            OutsourcedRadioBtn.setSelected(false);
            modifyMCLabel.setText("Machine ID");
            modifyPartmachineIdTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
        }
    }

}
