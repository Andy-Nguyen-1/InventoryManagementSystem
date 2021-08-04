package Model;

/**
 *
 * @author Andy Nguyen
 */

/**Part.Java class*/
public abstract class Part {

    //variables
    private int id, stock, min, max;
    private String name;
    private double price;

    /**
     * Parameterized constructor of Part class which set values to variables
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    //Setter methods to set the values of variables

    /**
     * Setter method to set part id. 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter method to set part name.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter to set part price.
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Setter to set part stock level.
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Setter to set part min.
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Setter to set part max.
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    //Getter methods to get the values of variables

    /**
     * Getter method to return part id.
     * @return part id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter method to return part name.
     * @return part name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method to return part price.
     * @return part price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Getter method to return part stock.
     * @return part stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Getter method to return part min.
     * @return part min
     */
    public int getMin() {
        return min;
    }

    /**
     * Getter method to return part max.
     * @return part max
     */
    public int getMax() {
        return max;
    }

}
