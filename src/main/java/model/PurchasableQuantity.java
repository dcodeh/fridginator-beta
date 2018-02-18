package model;

import java.util.Objects;

public class PurchasableQuantity {

    private String unit;
    private Number amount;
    private double price;
    
    public PurchasableQuantity(String unit, Number amount, double price) {
        this.unit = unit;
        this.amount = amount;
        this.price = price;
    }
    
    public String getUnit() {
        return this.unit;
    }
    
    public Number getAmount() {
        return this.amount;
    }
    
    public double getPrice() {
        return this.price;
    }
    
    public void updatePrice(double newPrice) {
        this.price = newPrice;
    }
    
    public double getUnitPrice() {
        return this.price / this.amount.doubleValue();
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof PurchasableQuantity) {
            PurchasableQuantity that = (PurchasableQuantity) o;
            
            return this.amount.equals(that.amount) &&
                   this.unit.equals(that.getUnit()) &&
                   this.price == that.getPrice();
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(unit + amount);
    }
}
