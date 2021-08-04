package Model;

/**
 *
 * @author Andy Nguyen
 */

/** This is the InHouse class. A subclass of Part class */

public class InHouse extends Part {

    /** variable */
    private int machineId;

     /** 
      * Parameterized constructor
      * @param id
      * @param name
      * @param price
      * @param stock
      * @param min
      * @param max
      * @param machineId 
      */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        //set values in parent class
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Setter method to set the machineId value.
     * @param machineId
     */
    
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Getter method to return the machineId number for a part.
     * @return machineId 
     */
    public int getMachineId() {
        return machineId;
    }

}
