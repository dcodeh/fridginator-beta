/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package model;

/**
 * This object stores only the necessary information to be passed to the
 * freemarker list page template. 
 * @author dcodeh
 */
public class SharedItemObject implements java.io.Serializable{

    /*
     * Used to determine if the serialized objects are compatible at deserialization time.
     * This should be changed any time serialized fields are changed!
     * 
     * History:
     * Version  Value       Notes
     * 0.1      1L          Initial version
     */
    private static final long serialVersionUID = 1L;
    
    private String itemName;
    private Number qty;
    private Number price;
    private String unit;
    private boolean checked;
    
    public SharedItemObject(String itemName, Number qty, Number price, String unit, boolean checked) {
        this.itemName = itemName;
        this.qty = qty;
        this.price = price;
        this.unit = unit;
        this.checked = checked;
    }
    
    public String getLine() {
        return String.format("%.2f %s %s (~$%.2f)", qty.doubleValue(), unit, itemName, price.doubleValue());
    }
    
    public String getName() {
        return this.itemName;
    }
    
    public boolean getIsChecked() {
        return this.checked;
    }
}
