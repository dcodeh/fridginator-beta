/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package model;

import java.util.Date;

public class WholeItem extends Item {
    
    /*
     * Used to determine if the serialized objects are compatible at deserialization time.
     * This should be changed any time serialized fields are changed!
     * 
     * History:
     * Version  Value       Notes
     * 0.1      1L          Initial version
     */
    private static final long serialVersionUID = 1L;

    public WholeItem(String name, String unit, int expWeeklyUsage, int weeklyUsage, int desiredQty, int quantity, boolean predictable, int minQty) {
        super(name, unit, predictable, true /* isWhole */);
        this.expWeeklyUsage = expWeeklyUsage;
        this.weeklyUsage = weeklyUsage;
        this.desiredQty = desiredQty;
        this.quantity = quantity;
        this.minQty = minQty;
    }

    @Override
    public Integer getExpWeeklyUsage() {
        return this.expWeeklyUsage.intValue();
    }

    @Override
    public Integer getWeeklyUsage() {
        return (Integer) this.weeklyUsage.intValue();
    }

    @Override
    public Integer getDesiredQty() {
        return (Integer) this.desiredQty.intValue();
    }

    @Override
    public Integer getQuantity() {
        return (Integer) this.quantity.intValue();
    }

    @Override
    public void setExpWeeklyUsage(Number exp) {
        this.expWeeklyUsage = exp.intValue();
    }

    @Override
    public void setWeeklyUsage(Number usage) {
        this.weeklyUsage = usage.intValue();
    }

    @Override
    public void setDesiredQty(Number qty) {
        this.desiredQty = qty.intValue();
    }

    @Override
    public void setQuantity(Number qty) {        
        Date now = new Date();
        
        if(lastUpdatedDate != null) {
            double oldQuantity = this.getQuantity();
            double diff = qty.intValue() - oldQuantity;
            long diffTimeMS = now.getTime() - lastUpdatedDate.getTime();
            double diffTimeWeeks = diffTimeMS * 0.000000001653439; // a magic number from ddg
            
            // if we're replenishing the item, track the date, but not the change in stock
            if(qty.doubleValue() <= oldQuantity) {
                // using the item, track the usage
                setWeeklyUsage(diff / diffTimeWeeks);
            }
            
            this.quantity = qty.intValue();
        } else {
            lastUpdatedDate = now;
            this.quantity = qty.intValue();
        }
    }

    @Override
    public void decrementQty(Number qty) {
        int currQty = this.quantity.intValue();
        int deltaQty = qty.intValue();
        
        setQuantity(currQty - deltaQty);
    }

    @Override
    public Integer getMinQuantity() {
        return this.minQty.intValue();
    }

    @Override
    public void setMinQuantity(Number qty) {
        this.minQty = qty.intValue();
    }

    @Override
    public void shareItemWithUser(User u, Number qty) {
        this.usersSharing.put(u, qty.intValue());
        // TODO calculate quantities
    }
    
    @Override
    public void unshareItemWithUser(User u) {
        super.unshareItemWithUser(u);
        // TODO recalculate quantities
    }

    @Override
    public Integer getUserExpUsage(User u) {
        return usersSharing.get(u).intValue();
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public void incrementQty(Number qty) {
        int currQty = this.quantity.intValue();
        int deltaQty = qty.intValue();
        
        setQuantity(currQty + deltaQty);
    }

}