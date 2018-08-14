package model;


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
    
}
