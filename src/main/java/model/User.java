package model;

import java.util.HashSet;
import java.util.Objects;

/**
 * Represents all of the information needed for a user.
 * @author dcodeh
 */
public class User implements java.io.Serializable {
    
    /*
     * Used to determine if the serialized objects are compatible at deserialization time.
     * This should be changed any time serialized fields are changed!
     * 
     * History:
     * Version  Value       Notes
     * 0.1      1L          Initial version
     */
    private static final long serialVersionUID = 1L;
    
    private String username;
    private String password;
    private HashSet<Item> sharedItems;
    private double moneySaved;
    private double moneySpent;
    private ShoppingList list;
    private boolean isSignedIn;
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        
        sharedItems = new HashSet<Item>();
        moneySaved = 0.0;
        moneySpent = 0.0;
        
        list = new ShoppingList();
        isSignedIn = false;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public HashSet<Item> getSharedItems() {
        return sharedItems;
    }
    
    public void setSharedItems(HashSet<Item> sharedItems) {
        this.sharedItems = sharedItems;
    }
    
    public void unshareItem(Item i) {
        this.sharedItems.remove(i);
    }
    
    public void shareItem(Item i) {
        this.sharedItems.add(i);
    }
    
    public double getMoneySaved() {
        return this.moneySaved;
    }
    
    public double getMoneySpent() {
        return this.moneySpent;
    }
    
    public void spendMoney(double d) {
        this.moneySpent += d;
    }
    
    public void saveMoney(double d) {
        this.moneySaved += d;
    }
    
    public void resetMoneySpent() {
        this.moneySpent = 0.0;
    }
    
    public void resetMoneySaved() {
        this.moneySaved = 0.0;
    }
    
    public boolean getIsSignedIn() {
        return isSignedIn;
    }
    
    public void setIsSignedIn(boolean isSignedIn) {
        this.isSignedIn = isSignedIn;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof User) {
            User that = (User) o;
            
            // good 'nuff
            return this.username.equals(that.getUsername()) &&
                   this.password.equals(that.getPassword());
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        // good 'nuff
        return Objects.hash(username, password);
    }

    public int getNumSharedItems() {
        return sharedItems.size();
    }

}
