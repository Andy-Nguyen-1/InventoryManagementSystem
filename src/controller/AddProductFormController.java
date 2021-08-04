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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;

/**
 *
 * @author Andy Nguyen
 */

/**
 * This class is the controller for the Add Product form.
 */
public class AddProductFormController implements Initializable {

    private Stage stage;
    private Parent scene;

    ObservableList<Part> assiociatePartsList = FXCollections.observableArrayList();
    @FXML
    private TextField searchProductTxt;
    @FXML
    private TableView<Part> addProductTbl;
    @FXML
    private TableView<Part> associateProductTbl;
    @FXML
    private TextField productNameTxt;
    @FXML
    private TextField productIncTxt;
    @FXML
    private TextField productPriceTxt;
    @FXML
    private TextField productMinTxt;
    @FXML
    private TextField ProductMaxTxt;

    @FXML
    private TableColumn<Part, Integer> productPartIDCol;
    @FXML
    private TableColumn<Part, String> productPartNameCol;
    @FXML
    private TableColumn<Part, Integer> productInvCol;
    @FXML
    private TableColumn<Part, Double> productPriceCol;
    @FXML
    private TableColumn<Part, String> asstPartNameCol;
    @FXML
    private TableColumn<Part, Integer> asstPartInvCol;
    @FXML
    private TableColumn<Part, Double> asstPartPriceCol;
    @FXML
    private TableColumn<Part, Integer> asstPartPartIDCol;
    @FXML
    private TextField autoIDTxt;
    Alert alert = new Alert(AlertType.NONE);

    /**
     * Initial method which invokes when this screen calls and it sets the metadata on table.
     * @param url
     * @param resourceBundle 
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductTbl.setItems(Inventory.getAllParts());
        associateProductTbl.setItems(assiociatePartsList);
        asstPartPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        asstPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        asstPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        asstPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method will remove a selected associated part
     */ 
    @FXML
    private void removeAssociatePartBtn(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setContentText("Are you sure you want to remove this part?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK) {
            try {
                lookupPart(associateProductTbl.getSelectionModel().getSelectedItem().getId());
                assiociatePartsList.remove(associateProductTbl.getSelectionModel().getSelectedItem());
                associateProductTbl.setItems(assiociatePartsList);
            } catch (NullPointerException ex) {
                Alert alert1 = new Alert(AlertType.ERROR);
                alert1.setTitle("Error Message");
                alert1.showAndWait();
            }
        }
    }

   /**
    * This method is used to look up for a part and return it
    */ 
    private Part lookupPart(int id) {
        Part part;
        for (int i = 0; i < assiociatePartsList.size(); i++) {
            part = assiociatePartsList.get(i);
            if (part.getId() == assiociatePartsList.get(i).getId()) {
                return part;
            }
        }
        return null;
    }

    /**
     * Button action method to cancel a product page and return user to main screen.
     */ 
    @FXML
    private void productCancelBtn(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Cancel Confirmation");
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
     * Button action method to add a selected product in associated part and show in associated table.
     */ 
    @FXML
    private void productAddBtn(ActionEvent event) {
        try {
            Inventory.lookupPart(addProductTbl.getSelectionModel().getSelectedItem().getId());
            assiociatePartsList.add(addProductTbl.getSelectionModel().getSelectedItem());
            associateProductTbl.setItems(assiociatePartsList);
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select an item to add.");
            alert.setTitle("Error Message");
            alert.showAndWait();
        }
    }

    /**
     * This button action method takes the given inputs from text fields, validates them and then adds it to the product observable list
     * <p><b>
     * In the first submission Java threw an exception error when the user tried to enter alphanumeric value in the inventory field.
     * I located the issue at controller.AddProductFormController.productSaveBtn 
     * I added setAlertType(AlertType.ERROR); to show an error when the user tries to enter an alphanumeric value 
     * and I added try { 
     * 
     * }catch(NumberFormatException e) {
            a.setContentText("An Error has occurred " + e.getMessage());
            a.show(); to handle errors.
     * </b></p>
     */
    @FXML
    private void productSaveBtn(ActionEvent event) throws IOException {
        alert.setAlertType(AlertType.ERROR);

        int productMax, productMin, productInc;
        double productPrice;
        String productName;
        try {

            productInc = Integer.parseInt(productIncTxt.getText());
            productMax = Integer.parseInt(ProductMaxTxt.getText());
            productMin = Integer.parseInt(productMinTxt.getText());
            productPrice = Double.parseDouble(productPriceTxt.getText());
            productName = productNameTxt.getText();
            //perform validations
            if (productMin < 0) {
                alert.setContentText("Min must be greater then 0.");
                alert.show();
            } else if (productMax < 0) {
                alert.setContentText("Max must be greater then 0.");
                alert.show();
            } else if (productPrice < 0) {
                alert.setContentText("Price must be greater then 0.");
                alert.show();
            } else if (productMin > productMax) {
                alert.setContentText("Min price must be less or equal to Max price.");
                alert.show();
            } else if (productInc > productMax || productInc < productMin) {
                alert.setContentText("Part Inv must be between Min and Max.");
                alert.show();
            } else {
                //add product to the product observer list
                int ran=(int) (Math.random() * 100);
                Inventory.addProduct(new Product(ran, productName, productPrice, productInc, productMin, productMax));
                Product partt = Inventory.lookupProduct(ran);
                for (Part part : assiociatePartsList) {
                    partt.addAssociatedPart(part);
                }

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
     * This is the onKeyEnter method which will display the records matching from the input in text field when enter is pressed.
     * I changed this from onKeyTyped method and added a message to appear if no parts are found.
     */ 
    @FXML
    private void onKeyEnterSearchPart(KeyEvent event) {
        ObservableList<Part> pww = Inventory.getAllParts();
        FilteredList<Part> filteredData = new FilteredList<>(pww, pa -> true);
        if (event.getCode().equals(KeyCode.ENTER)) {
            filteredData.setPredicate(part -> {
                int id = part.getId();
                if (searchProductTxt.getText() == null || searchProductTxt.getText().isEmpty()) {
                    addProductTbl.setItems(pww);
                }
                if (part.getName().toLowerCase().contains(searchProductTxt.getText().toLowerCase())) {
                    return true;
                }
                if (String.valueOf(id).contains(searchProductTxt.getText())) {
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
        sorted.comparatorProperty().bind(addProductTbl.comparatorProperty());
        addProductTbl.setItems(sorted);
        }
      }
    }
}
