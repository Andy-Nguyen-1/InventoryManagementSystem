package Model;


/**
 *
 * @author Andy Nguyen
 */

/** This is the outsourced part class. A subclass of Part class */
public class Outsourced extends Part {

    //variable
    private String companyName;

    /**
     * Parameterized constructors
     * @param id
     * @param price
     * @param name
     * @param min
     * @param max
     * @param companyName
     * @param stock
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        //sets values in parent class
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Setter to set the companyName value.
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Getter to get the companyName value.
     * @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }

}
