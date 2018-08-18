/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package model;

import java.util.Objects;

/**
 * This is the thing that gets added to the users' shopping lists
 * @author dcodeh
 *
 */
public class ShoppingListItem implements java.io.Serializable {

    /*
     * Used to determine if the serialized objects are compatible at deserialization time.
     * This should be changed any time serialized fields are changed!
     * 
     * History:
     * Version  Value       Notes
     * 0.1      1L          Initial version
     */
    private static final long serialVersionUID = 1L;
    
    private PurchasableQuantity quantity;
    private boolean isCheckedOff;
    
    public ShoppingListItem(PurchasableQuantity pq) {
        quantity = pq;
        isCheckedOff = false;
    }
    
    public void setIsCheckedOff(boolean checked) {
        isCheckedOff = checked;
    }
    
    public boolean getIsCheckedOff() {
        return isCheckedOff;
    }
    
    public PurchasableQuantity getPurchasableQuantity() {
        return quantity;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof ShoppingListItem) {
            ShoppingListItem that = (ShoppingListItem) o;
            if(this.getIsCheckedOff() == that.getIsCheckedOff() &&
               this.getPurchasableQuantity().equals(that.getPurchasableQuantity())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(quantity, isCheckedOff);
    }
    
}
