package model;

import java.util.HashMap;

/**
 * Stores items that a user should buy for themselves and for their room mates.
 * @author dcodeh
 */
public class ShoppingList implements java.io.Serializable {
	
	/*
	 * Used to determine if the serialized objects are compatible at deserialization time.
	 * This should be changed any time serialized fields are changed!
	 * 
	 * History:
	 * Version	Value		Notes
	 * 0.1		1L			Initial version
	 */
	private static final long serialVersionUID = 1L;

    /**
     * All of the items the user wants to buy for themselves
     */
    private HashMap<String, Boolean> personalItems;
    
    /**
     * All of the items the user should buy for his/her room mates
     */
    private HashMap<Item, Boolean> sharedItems;
    
    public ShoppingList() {
        personalItems = new HashMap<String, Boolean>();
        sharedItems = new HashMap<Item, Boolean>();
    }
    
    /**
     * @param line The name of the thing you want to add to your personal list
     * Fridginator doesn't track this at all -- but it will store it for you
     * exactly how you type it
     */
    public void addPersonalItem(String line) {
        personalItems.put(line.trim(), false);
    }
    
    /**
     * @param i The shared (tracked) item to add to a user's shopping list
     */
    public void addSharedItem(Item i) {
        sharedItems.put(i, false);
    }
    
    /**
     * You don't need groceries. Spend your money on whatever you want!
     */
    public void clearPersonalList() {
        this.personalItems.clear();
    }
    
    /**
     * Swap out the whole list
     * @param list
     */
    public void setPersonalList(HashMap<String, Boolean> list) {
        this.personalItems = list;
    }
    
    /**
     * Swap out the whole list!
     * @param list
     */
    public void setSharedList(HashMap<Item, Boolean> list) {
        this.sharedItems = list;
    }
    
    public HashMap<String, Boolean> getPersonalList() {
        return this.personalItems;
    }
    
    /**
     * Check or uncheck an item on your personal list
     * @param i       The line item to check
     * @param checked Whether or not the item is checked
     */
    public void setPersonalItemCheckStatus(String i, boolean checked) {
        personalItems.replace(i, checked);
    }
    
    /**
     * Check or uncheck an item on the shared item list
     * @param i       The item to check
     * @param checked Whether or not it's checked
     */
    public void setSharedItemCheckStatus(Item i, boolean checked) {
        sharedItems.replace(i, checked);
    }
}
