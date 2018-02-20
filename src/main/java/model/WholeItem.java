package model;

public class WholeItem extends Item {
    
    public WholeItem(String name, String unit, int expWeeklyUsage, int weeklyUsage, int desiredQty, int quantity, boolean predictable, int minQty) {
        super(name, unit, predictable);
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
        this.quantity = qty.intValue();
    }

    @Override
    public void decrementQty(Number qty) {
        int currQty = this.quantity.intValue();
        int deltaQty = qty.intValue();
        
        this.quantity = currQty - deltaQty;
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
    }

    @Override
    public void unshareItemWithUser(User u) {
        this.usersSharing.remove(u);
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof WholeItem) {
            return super.equals(o);
        } else {
            return false;
        }
    }

}
