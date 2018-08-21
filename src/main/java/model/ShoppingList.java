/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
    private HashMap<Item, ShoppingListItem> sharedItems;
    
    
    /**
     * The total amount that this user is expected to pay for shopping this week.
     */
    private double sharedCost;
    
    public ShoppingList() {
        personalItems = new HashMap<String, Boolean>();
        sharedItems = new HashMap<Item, ShoppingListItem>();
        sharedCost = 0.0;
    }
    
    /**
     * @param line The name of the thing you want to add to your personal list
     * Fridginator doesn't track this at all -- but it will store it for you
     * exactly how you type it
     */
    public void addPersonalItem(String line) {
        personalItems.put(line.trim(), false);
    }
    
    public void removePersonalItem(String line) {
        personalItems.remove(line);
    }
    
    /**
     * Adds a shared item to a user's list. Quantity will be added if
     * an item already exists.
     * @param i The shared (tracked) item to add to a user's shopping list
     */
    public void addSharedItem(Item i, PurchasableQuantity pq) {
        ShoppingListItem listItem = sharedItems.get(i);
        
        if(listItem == null) {
            sharedItems.put(i, new ShoppingListItem(pq));
        } else {
            PurchasableQuantity existingPQ = listItem.getPurchasableQuantity();
            PurchasableQuantity combinedPQ = new PurchasableQuantity(pq.getUnit(), 
                                                                     pq.getAmount().doubleValue() + existingPQ.getAmount().doubleValue(), 
                                                                     pq.getPrice() + existingPQ.getPrice());
            sharedItems.put(i, new ShoppingListItem(combinedPQ));
        }
        
        sharedCost += pq.getPrice();
    }
    
    /**
     * @param i The shared item to remove, if it exists 
     */
    public void removeSharedItem(Item i) {
        if(sharedItems.containsKey(i)) {
            sharedCost -= sharedItems.get(i).getPurchasableQuantity().getPrice();
            sharedItems.remove(i);
        }
    }
    
    /**
     * You don't need groceries. Spend your money on whatever you want!
     */
    public void clearPersonalList() {
        this.personalItems.clear();
    }
    
    /**
     * Screw your roommates! Don't buy eggs ever again
     */
    public void clearSharedList() {
        this.sharedItems.clear();
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
    public void setSharedList(HashMap<Item, ShoppingListItem> list) {
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
        sharedItems.get(i).setIsCheckedOff(checked);
    }
    
    /**
     * See how much this user is expected to pay for their shared items.
     * @return
     */
    public double getsharedListCost() {
        return this.sharedCost;
    }

    public HashMap<Item, ShoppingListItem> getSharedList() {
        return this.sharedItems;
    }
    
    /**
     * @return a list of the information needed to display shared items
     * on the list page.
     */
    public List<SharedItemObject> getSharedListForTemplate() {
        ArrayList<SharedItemObject> sharedList = new ArrayList<>();
        
        for(Item item : sharedItems.keySet()) {
            ShoppingListItem shoppingListItem = sharedItems.get(item);
            if(shoppingListItem != null) {
                PurchasableQuantity pq = shoppingListItem.getPurchasableQuantity();
                sharedList.add(new SharedItemObject(item.getName(), pq.getAmount(),
                                                    pq.getPrice(), pq.getUnit(),
                                                    shoppingListItem.getIsCheckedOff()));
            }
        }
        
        return sharedList;
    }
    
    /**
     * @return a list of information needed to display personal items 
     * on the list page
     */
    public List<PersonalItemObject> getPersonalListForTemplate() {
        ArrayList<PersonalItemObject> personalList = new ArrayList<>();
        
        for(String item : personalItems.keySet()) {
            personalList.add(new PersonalItemObject(item, personalItems.get(item)));
        }
        
        return personalList;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(personalItems, sharedItems);
    }
    
    @Override 
    public boolean equals(Object o) {
        if(o instanceof ShoppingList) {
            ShoppingList list = (ShoppingList) o;
            return personalItems.equals(list.getPersonalList()) &&
                   sharedItems.equals(list.getSharedList());
        } else {
            return false;
        }
    }
}
