package model;

import java.util.HashSet;

/**
 * Represents all of the information needed for a user.
 * @author dcodeh
 */
public class User {
    
    private String username;
    private String password;
    private HashSet<Item> sharedItems;
    private double moneySaved;
    private double moneySpent;
    
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

}
