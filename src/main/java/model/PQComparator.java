package model;

import java.util.Comparator;

/**
 * Comparator for keeping PurchasableQuantities in order
 * @author dcodeh
 */
public class PQComparator implements Comparator<PurchasableQuantity> {

    /**
     * {@inheritDoc}
     */
    public int compare(PurchasableQuantity p1, PurchasableQuantity p2) {
        if(p1 == null || p2 == null) {
            return 0;
        }
        
        return (int) (p1.getAmount().doubleValue() - p2.getAmount().doubleValue());
    }

}
