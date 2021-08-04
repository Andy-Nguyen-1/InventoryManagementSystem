package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Andy Nguyen
 */

/**
 * This is the Inventory class. Contains methods for adding and editing parts and products to the inventory lists.
 */

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * addPart method. Adds new Part into allParts Observable List
     * @param newPart
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
    * addProduct method. Adds new Product into allProducts Observable List
    * @param newProduct
    */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * lookupPart search for a part in allParts ObservableList by part id and return it. 
     * @param partId
     * @return product id if found otherwise return null
     */
    
    public static Part lookupPart(int partId) {
        Part part;
        for (int i = 0; i < allParts.size(); i++) {
            part = getAllParts().get(i);

            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * lookupProduct method to search for a product in allProducts ObservableList
     * @param productId
     * @return partId if found otherwise return null
     */
    public static Product lookupProduct(int productId) {
        Product product;
        for (int j = 0; j < allProducts.size(); j++) {
            product = getAllProducts().get(j);

            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * lookupPart method to search for matching parts in allParts array with given name or id and show in table view.
     * @param partName
     * @return matched parts
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        Part part;
        for (int i = 0; i < allParts.size(); i++) {
            part = getAllParts().get(i);

            if (part.getName().contains(partName)) {
                matchingParts.add(part);
            }
        }
        try {
            part = lookupPart(Integer.parseInt(partName));
            if (part != null) {
                matchingParts.add(part);
            }
        } catch (Exception e) {

        }

        return matchingParts;
    }

    /**
     * lookupProduct method to search for matching products in allProducts array with given name or id and show in table view
     * @param productName
     * @return matched product
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();
        Product product;
        for (int i = 0; i < allProducts.size(); i++) {
            product = getAllProducts().get(i);

            if (product.getName().contains(productName)) {
                matchingProducts.add(product);
            }
        }

        try {
            product = Inventory.lookupProduct(Integer.parseInt(productName));
            if (product != null) {
                matchingProducts.add(product);
            }
        } catch (Exception e) {

        }

        return matchingProducts;
    }

    /**
     * updatePart takes index and selected part as a parameter and updates the selected part at given index in allParts array.
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {
        getAllParts().set(index, selectedPart);
    }

    /**
     * updateProduct takes index and newProduct part as parameter and updates the selected product at given index in allProduct array.
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct) {
        getAllProducts().set(index, newProduct);
    }

    /**
     * deletePart method takes selectedPart as parameter and search for that part in allParts array and remove it.
     * @param selectedPart
     * @return removes selectedPart 
     */
    public static boolean deletePart(Part selectedPart) {

        Part part;
        for (int i = 0; i < allParts.size(); i++) {
            part = getAllParts().get(i);
            if (part.getId() == selectedPart.getId()) {
                return getAllParts().remove(part);
            }
        }
        return false;
    }


    /**
     * deleteProduct method takes selectedProduct as parameter and search for that product in allProducts array and remove it.
     * @param selectedProduct
     * @return removes selectedProduct
     */
    public static boolean deleteProduct(Product selectedProduct) {
        Product product;
        for (int i = 0; i < allProducts.size(); i++) {
            product = getAllProducts().get(i);
            if (product.getId() == selectedProduct.getId()) {
                return getAllProducts().remove(product);
            }
        }

        return false;
    }

    /**
     * getAllParts method 
     * @return ObservableList of allParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * getAllProducts method 
     * @return ObservableList of allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
