package model;

import java.util.Comparator;

/**
 * Comparator for keeping PurchasableQuantities in order
 * @author dcodeh
 */
public class PQComparator implements Comparator<PurchasableQuantity>, java.io.Serializable {

    /*
     * Used to determine if the serialized objects are compatible at deserialization
     * time. This should be changed any time serialized fields are changed!
     * 
     * History: Version Value Notes 0.1 1L Initial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    public int compare(PurchasableQuantity p1, PurchasableQuantity p2) {
        if(p1 == null || p2 == null) {
            return 0;
        }
        
        double p1Amount = p1.getAmount().doubleValue();
        double p2Amount = p2.getAmount().doubleValue();
        
        // I have to do this gross if statement because since we're dealing with doubles,
        // we'll lose precision if we cast the result of a subtraction to an int
        if(p1Amount > p2Amount) {
            return 1;
        } else if(p2Amount > p1Amount) {
            return -1;
        } else {
            // they must be equal!
            return 0;
        }
    }

}
