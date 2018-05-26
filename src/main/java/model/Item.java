package model;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeSet;
/**
 * This class is the common ancestor of all items in the fridge.
 * @author dcodeh
 */
public abstract class Item implements java.io.Serializable {

    /*
	 * Used to determine if the serialized objects are compatible at deserialization time.
	 * This should be changed any time serialized fields are changed!
	 * 
	 * History:
	 * Version  Value       Notes
	 * 0.1      1L          Initial version
	 */
	private static final long serialVersionUID = 1L;

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
     * Used to calculate weeklyUsage
     * Just for one week this time.
     */
    protected Date lastUpdatedDate;
    
    /**
     * The actual amount that gets used per week (for all users).
     * 
     * This should be determined by history. (See above)
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
    protected TreeSet<PurchasableQuantity> purchasableQuantities;
    
    /**
     * The current amount you have in stock right now.
     */
    protected Number quantity;
    
    /**
     * Predictable items:
     */
    protected boolean predictable;
    
    /**
     * Stores all of the users who are sharing this item, and the quantities they expect to use.
     */
    protected HashMap<User, Number> usersSharing;
    
    /**
     * Whether or not the item is whole (should be set by the children
     */
    protected boolean isWhole;
    
    /**
     * Common information for all items....though this item can't be instantiated directly.
     * @param name The name of this item...best without spaces
     * @param unit The unit for this item...best as a singular label (e.g. gal, ct, etc)
     * @param predictable Whether or not this item's usage is regular every week
     */
    public Item(String name, String unit, boolean predictable, boolean isWhole) {
        this.name = name;
        this.unit = unit;
        this.predictable = predictable;
        this.isWhole = isWhole;
        this.usersSharing = new HashMap<User, Number>();
    }

    /**
     * Take a guess...
     * @return The name of this item
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * This is a puzzler...
     * @return The unit of this item
     */
    public String getUnit() {
        return this.unit;
    }
    
    /**
     * Add a purchasable unit quantity to this item...maybe a 3/4 gallon of milk?
     * @param pqty The constructed quantity to add
     */
    public void addPurchaseQty(PurchasableQuantity pqty) {
        this.purchasableQuantities.add(pqty);
    }
    
    /**
     * Remove a purchasable unit quantity from this item...it turns out companies lose money selling milk in 3/4 gallons
     * @param pqty The quantity to remove
     */
    public void removePurchaseQty(PurchasableQuantity pqty) {
        this.purchasableQuantities.remove(pqty);
    }
    
    /**
     * Just grab the whole list of purchasable quantities...brute force. I like it. 
     * @return The whole list
     */
    public TreeSet<PurchasableQuantity> getPurchasableQuantities() {
        return this.purchasableQuantities;
    }
    
    /**
     * Just set the whole new list by brute force...I like your style.
     * @param newSet The new set of purchasable quantities.
     */
    public void setPurchasableQuantities(TreeSet<PurchasableQuantity> newSet) {
        this.purchasableQuantities = newSet;
    }
    
    /**
     * See who all is partaking in this item, and their expected usage
     * @return A glorious HashMap
     */
    public HashMap<User, Number> getUsersSharingItem() {
        return this.usersSharing;
    }
    
    /**
     * Get the amount of this item that the user expects to use
     * @param u The user to search for
     * @return The Number containing the user's expected usage, or NULL
     */
    public abstract Number getUserExpUsage(User u);
    
    /**
     * @param u See if this user is sharing the item
     * @return Whether or not user u is sharing it
     */
    public boolean isUserSharingItem(User u) {
        return this.usersSharing.containsKey(u);
    }
    
    public void setUserExpUsage(User u, Number n) {
        this.usersSharing.put(u, n);
        
        double totalUsage = 0.0;
        for(Number usage : this.usersSharing.values()) {
            totalUsage += usage.doubleValue();
        }
        
        this.expWeeklyUsage = totalUsage;
    }
    
    /**
     * @return The total expected weekly usage of this item (for all users) 
     */
    public abstract Number getExpWeeklyUsage();
    
    /**
     * @return The total weekly usage of this item, determined by history (for all users). 
     */
    public abstract Number getWeeklyUsage();
    
    /**
     * @return The amount of this item you would like to have in your fridge
     */
    public abstract Number getDesiredQty();
    
    /**
     * @return The amount of this item currently in your fridge
     */
    public abstract Number getQuantity();
    
    /**
     * @return The minimum amount of this product you would like to have in your fridge
     */
    public abstract Number getMinQuantity();
    
    /**
     * @return Whether or not this item is predictable from week to week
     */
    public boolean getIsPredictable() {
        return this.predictable;
    }
    
    /**
     * @return Whether or not this item should be measured with integer quantities
     */
    public boolean getIsWhole() {
        return this.isWhole;
    }
    
    /**
     * @param exp Manually set the expected usage for this item per week for all users.
     */
    public abstract void setExpWeeklyUsage(Number exp);
    
    /**
     * @param usage Manually set how much of this item will be used per week for all users.
     */
    public abstract void setWeeklyUsage(Number usage);
    
    /**
     * @param qty Change the desired amount of this item in your fridge.
     */
    public abstract void setDesiredQty(Number qty);
    
    /**
     * @param qty Change the amount of this item you currently have in your fridge.
     */
    public abstract void setQuantity(Number qty);
    
    /**
     * @param qty Change the minimum amount you want to have in your fridge.
     */
    public abstract void setMinQuantity(Number qty);
    
    /**
     * @param qty Reduce the amount of this item you have in your fridge
     */
    public abstract void decrementQty(Number qty);
    
    /**
     * @param u Share the love with this user
     * @param qty How much love this user wants to use each week.
     */
    public abstract void shareItemWithUser(User u, Number qty);
    
    /**
     * @param u Banish this user from the glorious riches of this item.
     */
    public void unshareItemWithUser(User u) {
        this.usersSharing.remove(u);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, unit, expWeeklyUsage, weeklyUsage, desiredQty, minQty, quantity, predictable);
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof Item) {
            Item that = (Item) o;
            
            // just see if everything is the same...easy
            return this.name.equals(that.getName()) &&
                   this.unit.equals(that.getUnit()) && 
                   this.expWeeklyUsage.equals(that.getExpWeeklyUsage()) &&
                   this.weeklyUsage.equals(that.getWeeklyUsage()) &&
                   this.desiredQty.equals(that.getDesiredQty()) &&
                   this.minQty.equals(that.getMinQuantity()) &&
                   this.quantity.equals(that.getQuantity()) &&
                   (this.predictable == that.getIsPredictable());
        } else {
            return false;
        }
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setIsPredictable(boolean p) {
        this.predictable = p;
    }
    
    public PurchasableQuantity getSmallestPurchasableQuantity() {
        return purchasableQuantities.last();
    }
    
    public PurchasableQuantity getLargestPurchasableQuantity() {
        return purchasableQuantities.first();
    }
}
