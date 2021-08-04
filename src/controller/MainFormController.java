package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Andy Nguyen
 */

/**
 * This class is the controller for the main screen functions.
 */

public class MainFormController implements Initializable {

    private Stage stage;
    private Parent scene;
    @FXML
    private TextField partSearchTxt;
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TextField productSearchText;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInvCol;
    @FXML
    private TableColumn<Product, Double> productPriceColl;

    Alert alert = new Alert(Alert.AlertType.NONE);

    /**
     * This initialize method invokes when opening of this screen and will fill the table with data.
     * @param url
     * @param resourceBundle
     */ 
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getAllParts());
        productTable.setItems(Inventory.getAllProducts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColl.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This add part action button will close the current screen and open the add part form screen.
     */ 
    @FXML
    private void addPartBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This button will validate that the table row is selected and then move user to Modify form screen
     * @param event
     * @throws IOException 
     */
    @FXML
    private void modifyPartBtn(ActionEvent event) throws IOException {
        try {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("/view/ModifyPartForm.fxml"));
            load.load();
            ModifyPartFormController MC = load.getController();
            MC.setDataOnTextFields(partTable.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(load.getRoot()));
            stage.show();
        } catch (NullPointerException ex) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error!");
            alert.setContentText("Please select an item to modify.");
            alert.show();
        }
    }

    /**
     * This delete button action validates if a part from table is selected and then deletes selected part from inventory list.
     * @param event 
     */
    @FXML
    private void deletePartBtn(ActionEvent event) {
        try {
            Inventory.lookupPart(partTable.getSelectionModel().getSelectedItem().getId());
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete confirmation!");
            alert.setContentText("Are you sure you want to delete the selected part?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent() && option.get() == ButtonType.OK) {
                Inventory.deletePart(partTable.getSelectionModel().getSelectedItem()); // deletes Part object
                partTable.setItems(Inventory.lookupPart(partSearchTxt.getText())); // refresh filtered table
            }
        } catch (NullPointerException e) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error!");
            alert.setContentText("Please select an item to delete.");
            alert.show();
        }
    }
    
    /**
     * This add product button action will move the user to the add product form screen.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void addProductBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This modify product button action first validates an item from table is selected and then move user to the modify product form.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void modifyProductBtn(ActionEvent event) throws IOException {
        try {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("/view/ModifyProductForm.fxml"));
            load.load();
            ModifyProductFormController PC = load.getController();
            PC.sendProduct(productTable.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(load.getRoot()));
            stage.show();
        } catch (NullPointerException e) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error!");
            alert.setContentText("Please select an item to modify.");
            alert.showAndWait();
        }

    }

    /**
     * This delete product button action validates an item from table is selected and then confirms from user for deleting
     * and deletes the product from inventory list.
     * @param event 
     */
    @FXML
    private void deleteProductBtn(ActionEvent event) {
        try {
            Inventory.lookupProduct(productTable.getSelectionModel().getSelectedItem().getId());
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete confirmation!");
            alert.setContentText("Are you sure you want to delete this product?");

            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent() && option.get() == ButtonType.OK) {

                // Checks if product has an associatedPart
                if (Inventory.lookupProduct(productTable.getSelectionModel().getSelectedItem().getId()).getAllAssociatedParts().size() > 0) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message!");
                    alert.setContentText("Please remove it's associated part first.");
                    alert.show();
                } else {
                    //delete the product and refresh the table
                    Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
                    productTable.setItems(Inventory.lookupProduct(productSearchText.getText()));
                }
            }
        } catch (NullPointerException e) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error Message!");
            alert.setContentText("Please select a product to delete.");
            alert.showAndWait();
        }
    }

    
    /**
     * Button action to exit the application.
     * @param event 
     */
    @FXML
    private void exitBtn(ActionEvent event) {
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit confirmation");
        alert.setContentText("Are you sure you want to exit the application?");

        Optional<ButtonType> option = alert.showAndWait();
        if (option.isPresent() && option.get() == ButtonType.OK) {
            System.exit(0);
        }

    }

   /**
    * This onEnterPressPartSearch method searches in inventory list, if the part is found return in table.
    * @param event 
    */
    @FXML
    private void onEnterPressPartSearch(KeyEvent event) {
        ObservableList<Part> pww = Inventory.getAllParts();
        FilteredList<Part> filteredData = new FilteredList<>(pww, pa -> true);
        if (event.getCode().equals(KeyCode.ENTER)) {
            filteredData.setPredicate(part -> {
                int id = part.getId();
                if (partSearchTxt.getText() == null || partSearchTxt.getText().isEmpty()) {
                     partTable.setItems(pww);
                }
                if (part.getName().toLowerCase().contains( partSearchTxt.getText().toLowerCase())) {
                    return true;
                }
                 if (String.valueOf(id).contains(partSearchTxt.getText())) {
                        return true;
                    }
                return false;

            });
             if(filteredData.size()==0){
                
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Sorry, the part you are looking for does not exist!");
                        alert.show();
            }
             
             else {
            SortedList<Part> sorted = new SortedList<>(filteredData);
            sorted.comparatorProperty().bind(partTable.comparatorProperty());
            partTable.setItems(sorted);
             }
        }
    }
    /**
     * This onEnterPressProductSearch method searches inventory list for the product.
     * If the product is found, then it will return in the table if not, a message will pop up.
     * @param event 
     */
    @FXML
    private void onEnterPressProductSearch(KeyEvent event) {
        ObservableList<Product> pww = Inventory.getAllProducts();
        FilteredList<Product> filteredData = new FilteredList<>(pww, p -> true);
        if (event.getCode().equals(KeyCode.ENTER)) {
            filteredData.setPredicate(product -> {
                int id = product.getId();
                if (productSearchText.getText() == null || productSearchText.getText().isEmpty()) {
                     productTable.setItems(pww);
                }
                if (product.getName().toLowerCase().contains( productSearchText.getText().toLowerCase())) {
                    return true;
                }
                 if (String.valueOf(id).contains(productSearchText.getText())) {
                        return true;
                    }
                return false;

            });
        if(filteredData.size()==0){
                
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Sorry, the product you are looking for does not exist!");
                        alert.show();
            }
        else {
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(productTable.comparatorProperty());
        productTable.setItems(sortedData);
            }
        
        }
        
    }

}
