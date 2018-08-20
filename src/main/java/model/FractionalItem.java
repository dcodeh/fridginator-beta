/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package model;

import java.util.Date;

public class FractionalItem extends Item {

    /*
     * Used to determine if the serialized objects are compatible at deserialization time.
     * This should be changed any time serialized fields are changed!
     * 
     * History:
     * Version  Value       Notes
     * 0.1      1L          Initial version
     */
    private static final long serialVersionUID = 1L;

    public FractionalItem(String name, String unit, double expWeeklyUsage, double weeklyUsage, double desiredQty, double quantity, boolean predictable, double minQty) {
        super(name, unit, predictable, false /* isWhole */);
        this.expWeeklyUsage = expWeeklyUsage;
        this.weeklyUsage = weeklyUsage;
        this.desiredQty = desiredQty;
        this.quantity = quantity;
        this.minQty = minQty;
    }

    @Override
    public Double getExpWeeklyUsage() {
        return this.expWeeklyUsage.doubleValue();
    }

    @Override
    public Double getWeeklyUsage() {
        return this.weeklyUsage.doubleValue();
    }

    @Override
    public Double getDesiredQty() {
        return this.desiredQty.doubleValue();
    }

    @Override
    public Double getQuantity() {
        return this.quantity.doubleValue();
    }

    @Override
    public void setExpWeeklyUsage(Number exp) {
        this.expWeeklyUsage = exp.doubleValue();
    }

    @Override
    public void setWeeklyUsage(Number usage) {
        this.weeklyUsage = usage.doubleValue();
    }

    @Override
    public void setDesiredQty(Number qty) {
        this.desiredQty = qty.doubleValue();
    }

    @Override
    public void setQuantity(Number qty) {
        Date now = new Date();
        
        if(lastUpdatedDate != null) {
            double oldQuantity = this.getQuantity();
            double diff = qty.doubleValue() - oldQuantity;
            long diffTimeMS = now.getTime() - lastUpdatedDate.getTime();
            double diffTimeWeeks = diffTimeMS * 0.000000001653439; // a magic number from ddg
            
            setWeeklyUsage(diff / diffTimeWeeks);
            
            this.quantity = qty.doubleValue();
        } else {
            lastUpdatedDate = now;
            this.quantity = qty.doubleValue();
        }
    }

    @Override
    public void decrementQty(Number qty) {
        double currQty = this.quantity.doubleValue();
        double deltaQty = qty.doubleValue();
        
        this.quantity = currQty - deltaQty;
    }

    @Override
    public Double getMinQuantity() {
        return this.minQty.doubleValue();
    }

    @Override
    public void setMinQuantity(Number qty) {
        this.minQty = qty.doubleValue();
    }

    @Override
    public void shareItemWithUser(User u, Number qty) {
        this.usersSharing.put(u, qty.doubleValue());
        // TODO calculate quantities!
    }
    
    @Override
    public void unshareItemWithUser(User u) {
        super.unshareItemWithUser(u);
        // TODO recalculate quantities
    }

    @Override
    public Number getUserExpUsage(User u) {
        if(predictable) {
            return usersSharing.get(u).doubleValue();
        } else {
            // TODO dcodeh this is a little bit of a band aid
            return 0.0; // we don't track usage for unpredictable items
        }
    }

}
