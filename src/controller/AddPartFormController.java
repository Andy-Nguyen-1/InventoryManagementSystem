package controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import java.util.ResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.lang.*;
import java.util.Optional;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Andy Nguyen
 */

/**
 * This class is the controller for the Add Part form.
 */
public class AddPartFormController implements Initializable {

    @FXML
    private RadioButton inHouseRadioBtn;
    @FXML
    private RadioButton outSourceRadioBtn;
    @FXML
    private ToggleGroup sourceToggleGroup;
    @FXML
    private Label MachineIdLbl;
    @FXML
    private TextField PartIDTxt;
    @FXML
    private TextField PartNameTxt;
    @FXML
    private TextField PartInvTxt;
    @FXML
    private TextField PartPriceTxt;
    @FXML
    private TextField PartMaxTxt;
    @FXML
    private TextField MachineIdTxt;
    @FXML
    private TextField PartMinTxt;

    Alert a = new Alert(AlertType.NONE);

     /**
     * This method initially sets default MachineIdLbl label to Machine ID.
     * @param url
     * @param resourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MachineIdLbl.setText("Machine ID");
    }

    /**
     * This radio button action sets text Machine ID on MachineIdLbl label.
     */ 
    @FXML
    void inHouseRadioBtn(ActionEvent event) {
        MachineIdLbl.setText("Machine ID");
    }

    /**
     * This radio button action sets text Company Name on MachineIdLbl label.
     */ 
    @FXML
    void outSourceRadioBtn(ActionEvent event) {
        MachineIdLbl.setText("Company Name");
    }

    /**
     * This button click methods will validate and save the given inputs by calling their respective setter methods.
     * <p><b>
     * In the first submission Java threw an exception error when the user tried to enter alphanumeric value in the inventory field. 
     * I located the cause of the issue at controller.AddPartFormController.PartSaveBtn.
     * I added setAlertType(AlertType.ERROR); to show an error when the user tries to enter an alphanumeric value 
     * and I added try { 
     * 
     * }catch(NumberFormatException e) {
            a.setContentText("An Error has occurred " + e.getMessage());
            a.show(); to handle errors.
     * </b></p>
     */
    @FXML
    private void PartSaveBtn(ActionEvent event) throws IOException {
        a.setAlertType(AlertType.ERROR);
        int minPrice, maxPrice, invPrice;
        double partPrice;
        String partName;
        try {
            minPrice = Integer.parseInt(PartMinTxt.getText());
            maxPrice = Integer.parseInt(PartMaxTxt.getText());
            invPrice = Integer.parseInt(PartInvTxt.getText());
            partPrice = Double.parseDouble(PartPriceTxt.getText());
            partName = PartNameTxt.getText();

            //performs validations
            if (minPrice < 0) {
                a.setContentText("Min must be greater then 0.");
                a.show();
            } else if (maxPrice < 0) {
                a.setContentText("Max must be greater then 0.");
                a.show();
            } else if (partPrice < 0) {
                a.setContentText("Price must be greater then 0.");
                a.show();
            } else if (minPrice > maxPrice) {
                a.setContentText("Min price must be less or equal to Max price.");
                a.show();
            } else if (invPrice > maxPrice || invPrice < minPrice) {
                a.setContentText("Part Inv must be between Min and Max.");
                a.show();
            } else {
                if (inHouseRadioBtn.isSelected()) {
                    Inventory.addPart(new InHouse((int) (Math.random() * 100), partName, partPrice, invPrice, minPrice, maxPrice, Integer.parseInt(MachineIdTxt.getText())));
                } else {
                    Inventory.addPart(new Outsourced((int) (Math.random() * 100), partName, partPrice, invPrice, minPrice, maxPrice, MachineIdTxt.getText()));
                }

                Stage stage;
                Parent scene;
                //move to Mainform screen
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch (NumberFormatException e) {
            a.setContentText("An Error Has Occurred " + e.getMessage());
            a.show();

        }
    }

    /**
     * This method will take user back to main screen after confirmation.
     */
    @FXML
    private void PartCancelBtn(ActionEvent event) throws IOException {
        Stage stage;
        Parent scene;

        a.setAlertType(AlertType.CONFIRMATION);
        a.setTitle("Cancel Confirmation");
        a.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> option = a.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

}
