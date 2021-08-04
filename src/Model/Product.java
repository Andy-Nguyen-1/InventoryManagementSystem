package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Andy Nguyen
 */

/**
 * This class creates Product objects.
 */

public class Product {

   //declaration of associatedParts ObservableList
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    //declaration of variables
    private int id, stock, min, max;
    private String name;
    private double price;

    /**
     * Parameterize constructor of Product, which set the values in variables.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    //Setter methods to set the vales of variables

    /**
     * Setter method to set product ID.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter method to set product name.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter method to set product price.
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Setter method to set product stock level.
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Setter method to set product min.
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Setter method to set product max.
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

//Getter methods to get the values of variables

    /**
     * Getter method to return Product ID.
     * @return ProductId
     */
    public int getId() {
        return id;
    }

    /**
     * Getter method to return product name.
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method to return product price.
     * @return product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Getter method to return product stock.
     * @return product stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Getter method to return product min.
     * @return product min
     */
    public int getMin() {
        return min;
    }

    /**
     * Getter method to return product max.
     * @return product max
     */
    public int getMax() {
        return max;
    }

    /**
     * addAssociatedPart method to add given parts in associatedParts ObservableList.
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * deleteAssociatedPart method to remove selectedAssociatedPart part form associatedParts ObservableList.
     * @param selectedAssociatedPart
     * @return remove selectedAssociatedPart
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * getAllAssociatedParts to return associatedParts ObservableList.
     * @return associatedParts ObservableList
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
