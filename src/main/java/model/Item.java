package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

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
    
    /**
     * Stores all of the users who are sharing this item, and the quantities they expect to use.
     */
    protected HashMap<User, Number> usersSharing;
    
    /**
     * Common information for all items....though this item can't be instantiated directly.
     * @param name The name of this item...best without spaces
     * @param unit The unit for this item...best as a singular label (e.g. gal, ct, etc)
     * @param predictable Whether or not this item's usage is regular every week
     */
    public Item(String name, String unit, boolean predictable) {
        this.name = name;
        this.unit = unit;
        this.predictable = predictable;
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
     * See who all is partaking in this item, and their expected usage
     * @return A glorious HashMap
     */
    public HashMap<User, Number> getUsersSharingItem() {
        return this.usersSharing;
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
    public abstract void unshareItemWithUser(User u);
    
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
}
