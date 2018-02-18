package model;

import java.util.HashSet;

/**
 * This class is the common ancestor of all items in the fridge.
 * @author dcodeh
 */
public abstract class Item {

    /**
     * The name of this item...what a descriptive name!
     * Best if it doesn't have spaces...
     */
    protected String name;
    
    /**
     * The unit that this item..what a descriptive name!
     * Best if it's a singular human-readable name such as
     * gal
     * ct
     * lb
     * qt
     */
    protected String unit;
    
    /**
     * The total amount of this item that experts *think* will be used during the week (for all users).
     * 
     * Actual mileage may vary.
     * 
     * Should be 0 if the item is not predictable.
     */
    protected Number expWeeklyUsage;
    
    /**
     * The actual amount that gets used per week (for all users).
     * 
     * This should be determined by history.
     */
    protected Number weeklyUsage;
    
    /**
     * The amount of this item that you want to have in your fridge at any given time
     */
    protected Number desiredQty;
    
    /**
     * The minimum quantity you would like to have of a non-predictable item.
     * Fridginator will restock unpredictable items when they reach this level
     */
    protected Number minQty;
    
    /**
     * The unit sizes that you can buy in this item
     */
    protected HashSet<PurchasableQuantity> purchasableQuantities;
    
    /**
     * The current amount you have in stock right now.
     */
    protected Number quantity;
    
    /**
     * Predictable items:
     */
    protected boolean predictable;
    
    public Item(String name, String unit, boolean predictable) {
        this.name = name;
        this.unit = unit;
        this.predictable = predictable;
    }

    public String getName() {
        return this.name;
    }
    
    public String getUnit() {
        return this.unit;
    }
    
    public void addPurchaseQty(PurchasableQuantity pqty) {
        this.purchasableQuantities.add(pqty);
    }
    
    public void removePurchaseQty(PurchasableQuantity pqty) {
        this.purchasableQuantities.remove(pqty);
    }
    
    public abstract Number getExpWeeklyUsage();
    public abstract Number getWeeklyUsage();
    public abstract Number getDesiredQty();
    public abstract Number getQuantity();
    public abstract Number getMinQuantity(Number qty);
    
    public abstract void setExpWeeklyUsage(Number exp);
    public abstract void setWeeklyUsage(Number usage);
    public abstract void setDesiredQty(Number qty);
    public abstract void setQuantity(Number qty);
    public abstract void setMinQuantity(Number qty);
    
    public abstract void decrementQty(Number qty);
}
