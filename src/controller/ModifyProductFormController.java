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

/**
 *
 * @author Andy Nguyen
 */

/**
 * This class is the controller for the Modify Product Form.
 */
public class ModifyProductFormController implements Initializable {

    private Stage stage;
    private Parent scene;
    private ObservableList<Part> tempList = FXCollections.observableArrayList();
    @FXML
    private TextField modifyProductSearchTxt;
    @FXML
    private TextField modifyProductIDTxt;
    @FXML
    private TextField modifyProductNameTxt;
    @FXML
    private TextField modifyProductInvTxt;
    @FXML
    private TextField modifyProductPriceTxt;
    @FXML
    private TextField modifyProductMaxTxt;
    @FXML
    private TextField modifyProductMinTxt;

    @FXML
    private TableView<Part> modifyTable1;
    @FXML
    private TableColumn<Part, Integer> partIdTable1;
    @FXML
    private TableColumn<Part, String> partNameTable1;
    @FXML
    private TableColumn<Part, Integer> partInvTable1;
    @FXML
    private TableColumn<Part, Double> partPriceTable1;
    @FXML
    private TableView<Part> modifyTable2;
    @FXML
    private TableColumn<Part, Integer> partIdTable2;
    @FXML
    private TableColumn<Part, String> partNameTable2;
    @FXML
    private TableColumn<Part, Integer> partInvTable2;
    @FXML
    private TableColumn<Part, Double> partPriceTable2;

    Alert alert = new Alert(Alert.AlertType.NONE);

    /**
     * This method confirms an item is selected and adds selected item from table11 to table2 and in tempList.
     */ 
    @FXML
    private void addInAssiociationBtn(ActionEvent event) {
         try {
            Inventory.lookupPart(modifyTable1.getSelectionModel().getSelectedItem().getId());
            tempList.add(modifyTable1.getSelectionModel().getSelectedItem());
            modifyTable2.setItems(tempList);
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select an item to add.");
            alert.setTitle("Error Message");
            alert.showAndWait();
        }
         
    }

    /**
     * This button action method will confirm from user to remove an associated part from table 2 and then remove it from the table.
     * @param event 
     */
    @FXML
    private void removeAssociationBtn(ActionEvent event) {
        
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel confirmation");
        alert.setContentText("Are you sure you want to remove this part?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.isPresent() && option.get() == ButtonType.OK) {
            try {
                tempList.remove(modifyTable2.getSelectionModel().getSelectedItem()); //remove selected table item list
                modifyTable2.setItems(tempList); //refresh table items
            } catch (NullPointerException ex) {

                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Selection error!");
                alert.setContentText("Please select a part.");
                alert.showAndWait();
            }
        }
    }
    
    /**
     * This cancel button action will confirm for cancellation from user and then return user to Main form screen.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void cancelModifyBtn(ActionEvent event) throws IOException {
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
     * This button action method validate the user inputs and modify the product details in inventory list.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void saveModifyBtn(ActionEvent event) throws IOException {
        alert.setAlertType(Alert.AlertType.ERROR);
        try {
            Product obj = Inventory.lookupProduct(Integer.parseInt(modifyProductIDTxt.getText())); //look for product in inventory list
            int oldIndex = Inventory.getAllProducts().indexOf(obj); //check the index of product
            Product newProduct;
            //store inputs in variables
            String productName = modifyProductNameTxt.getText();
            int productID = Integer.parseInt(modifyProductIDTxt.getText());
            int minPrice = Integer.parseInt(modifyProductMinTxt.getText());
            int maxPrice = Integer.parseInt(modifyProductMaxTxt.getText());
            int invPrice = Integer.parseInt(modifyProductInvTxt.getText());
            double productPrice = Double.parseDouble(modifyProductPriceTxt.getText());

            //perform validations
            if (minPrice < 0) {
                alert.setContentText("Min must be greater then 0.");
                alert.show();
            } else if (maxPrice < 0) {
                alert.setContentText("Max must be greater then 0.");
                alert.show();
            } else if (productPrice < 0) {
                alert.setContentText("Price must be greater then 0.");
                alert.show();
            } else if (minPrice > maxPrice) {
                alert.setContentText("Min price must be less or equal to Max price.");
                alert.show();
            } else if (invPrice > maxPrice || invPrice < minPrice) {
                alert.setContentText("Part Inv must be between Min and Max.");
                alert.show();
            } else {
                //update old index with new modified product
                Inventory.updateProduct(oldIndex, newProduct = new Product(productID, productName, productPrice, invPrice, minPrice, maxPrice));
                tempList.forEach((part) -> {
                    newProduct.addAssociatedPart(part);
                });
                //go to Main form after updating
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
     * This method takes the product object and sets then product on table1 in Modify Product Form.
     * @param product
     */
    public void sendProduct(Product product) {
        modifyProductIDTxt.setText(String.valueOf(product.getId()));
        modifyProductNameTxt.setText(product.getName());
        modifyProductInvTxt.setText(String.valueOf(product.getStock()));
        modifyProductPriceTxt.setText(String.format("%,.2f", product.getPrice()));
        modifyProductMaxTxt.setText(String.valueOf(product.getMax()));
        modifyProductMinTxt.setText(String.valueOf(product.getMin()));
        //add product to tempList
        product.getAllAssociatedParts().forEach((part) -> {
            tempList.add(part);
        });
    }

    /**
     * Initial method that will run while this window open and it will initialize the tables.
     * @param url
     * @param resourceBundle 
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyTable1.setItems(Inventory.getAllParts());
        partIdTable2.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameTable2.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvTable2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceTable2.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyTable2.setItems(tempList);
        partIdTable1.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameTable1.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvTable1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceTable1.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This onEnterPressPart search method searches for the part and show in table.
     * If no part is found the application will show a message.
     * @param event 
     */
    @FXML
    private void onEnterPressPartSearch(KeyEvent event) {
        ObservableList<Part> pww = Inventory.getAllParts();
        FilteredList<Part> filteredData = new FilteredList<>(pww, p -> true);
        if (event.getCode().equals(KeyCode.ENTER)) {
            filteredData.setPredicate(part -> {
                int id = part.getId();
                if (modifyProductSearchTxt.getText() == null || modifyProductSearchTxt.getText().isEmpty()) {
                    modifyTable1.setItems(pww);
                }
                if (part.getName().toLowerCase().contains(modifyProductSearchTxt.getText().toLowerCase())) {
                    return true;
                }
                if (String.valueOf(id).contains(modifyProductSearchTxt.getText())) {
                    return true;
                }
                return false;

            });
             if(filteredData.size()==0){
                
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Sorry, the part you are looking for does not exist!");
                        alert.show();
            }
             else{
            SortedList<Part> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(modifyTable1.comparatorProperty());
            modifyTable1.setItems(sortedData);
             }

        }
    }
}
