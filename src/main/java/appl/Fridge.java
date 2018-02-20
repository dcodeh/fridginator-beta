package appl;

import java.util.HashSet;

import model.Item;

/**
 * Stores and tracks all of the shared and unshared items.
 * @author dcodeh
 */
public class Fridge {

    // huh it's empty
    
    private HashSet<Item> fridge;

    public Fridge() {
        fridge = new HashSet<Item>();
    }
    
    public void addItem(Item i) {
        fridge.add(i);
    }
    
    public void removeItem(Item i) {
        fridge.remove(i);
    }
    
}
