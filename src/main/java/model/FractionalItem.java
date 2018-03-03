package model;

public class FractionalItem extends Item {

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
        this.quantity = qty.doubleValue();
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
    public boolean equals(Object o) {
        if(o instanceof FractionalItem) {
            return super.equals(o);
        } else {
            return false;
        }
    }

    @Override
    public Number getUserExpUsage(User u) {
        return usersSharing.get(u).doubleValue();
    }

}
