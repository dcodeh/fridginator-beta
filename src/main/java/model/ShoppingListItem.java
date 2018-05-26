package model;


/**
 * This is the thing that gets added to the users' shopping lists
 * @author dcodeh
 *
 */
public class ShoppingListItem {

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
