package model;

import java.util.HashMap;

/**
 * Stores items that a user should buy for themselves and for their room mates.
 * @author dcodeh
 */
public class ShoppingList {

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
    
    public void addPersonalItem(String line) {
        personalItems.put(line.trim(), false);
    }
    
    public void addSharedItem(Item i) {
        sharedItems.put(i, false);
    }
    
    public void clearPersonalList() {
        this.personalItems.clear();
    }
    
    public void setPersonalList(HashMap<String, Boolean> list) {
        this.personalItems = list;
    }
    
    public void setSharedList(HashMap<Item, Boolean> list) {
        this.sharedItems = list;
    }
    
    public HashMap<String, Boolean> getPersonalList() {
        return this.personalItems;
    }
    
    public void setPersonalItemCheckStatus(String i, boolean checked) {
        personalItems.replace(i, checked);
    }
    
    public void setSharedItemCheckStatus(Item i, boolean checked) {
        sharedItems.replace(i, checked);
    }
}
