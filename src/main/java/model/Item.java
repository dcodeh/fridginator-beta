package model;

import java.util.ArrayList;

public abstract class Item {

    protected String name;
    protected String unit;
    protected Number expWeeklyUsage;
    protected Number weeklyUsage;
    protected Number desiredQty;
    protected Number quantity;
    
    public Item(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    public String getName() {
        return this.name;
    }
    
    public String getUnit() {
        return this.unit;
    }
    
    public abstract Number getExpWeeklyUsage();
    public abstract Number getWeeklyUsage();
    public abstract Number getDesiredQty();
    public abstract Number getQuantity();
    
    public abstract void setExpWeeklyUsage(Number exp);
    public abstract void setWeeklyUsage(Number usage);
    public abstract void setDesiredQty(Number qty);
    public abstract void setQuantity(Number qty);

    public abstract void decrementQty(Number qty);
}
