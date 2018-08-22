/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package model;

import java.util.Objects;

/**
 * This class represents a thing that can be purchased. 
 * @author dcodeh
 */
public class PurchasableQuantity implements java.io.Serializable {

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
	 * The unit string for this item
	 * e.g. ct, gal, pint, ton, etc.
	 */
	private final String unit;
	
	/**
	 * The amount of this item, in the units specified
	 */
    private final Number amount;
    
    /**
     * How much this thing costs.
     * e.g. an arm and a leg
     */
    private double price;
    
    /**
     * Construct a new purchasable quantity
     * @param unit   The unit of this purchasable quantity...ct, gal, oz, ton, etc.
     * @param amount How much do you get with this quantity? (in units of unit)
     * @param price  How much does it cost?
     */
    public PurchasableQuantity(String unit, Number amount, double price) {
        this.unit = unit;
        this.amount = amount;
        this.price = price;
    }
    
    /**
     * @return The unit of this item
     */
    public String getUnit() {
        return this.unit;
    }
    
    /**
     * @return How much stuff you get in the specified units
     * (see getUnit)
     */
    public Number getAmount() {
        return this.amount;
    }
    
    /**
     * @return The cost of this item/quantity combination
     */
    public double getPrice() {
        return this.price;
    }
    
    /**
     * Change the price of this item. Capitalism is quite a drug.
     * To change the quantity, you'll need to construct a new object
     * @param newPrice
     */
    public void updatePrice(double newPrice) {
        this.price = newPrice;
    }
    
    /**
     * @return The price per unit of this item
     */
    public double getUnitPrice() {
        return this.price / this.amount.doubleValue();
    }
    
    /**
     * {@inheritDoc}
     */
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
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(unit, amount);
    }
    
    /**
     * @return A nicely formatted string with the details this object holds
     */
    @Override
    public String toString() {
        return String.format("%.2f %s (~$%.2f)", this.getAmount().doubleValue(),
                             this.getUnit(),
                             this.getPrice());
    }
}
