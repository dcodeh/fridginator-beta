package console;

import java.util.HashSet;
import java.util.Iterator;

import appl.Fridge;
import fridginator.AsciiBarBuilder;
import model.Item;

/**
 * View all of the items, and how much we have of each in stock
 * @author dcodeh
 *
 */
public class InventoryCommand extends Command {

    private static final String help = "View a breakdown of how much of each item is in the fridge.";
    public static final String keyword = "inventory";
    private static final String[] requiredArguments = {}; // no arguments
    
    // bar formatting options
    private final static int BAR_LEN = 30;
    private final static char FILL_CHAR = '=';
    private final static char EMPTY_CHAR = '_';
    private final static char CRITICAL_CHAR = '!';
    
    public InventoryCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        
        HashSet<Item> itemsInFridge = fridge.getItems();
        
        if(itemsInFridge.size() == 0) {
            System.out.println("No Items to inventory.");
            return ExitCode.SUCCESS; // just go home early.
        }

        Iterator<Item> itemsIterator = itemsInFridge.iterator();
        HashSet<Item> predictableItems = new HashSet<Item>();
        HashSet<Item> otherItems = new HashSet<Item>();
        
        // organize the items by type
        while(itemsIterator.hasNext()) {
            Item item = itemsIterator.next();
            
            if(item.getIsPredictable()) {
                predictableItems.add(item);
            } else {
                otherItems.add(item);
            }
        }
        
        // print them all nice and pretty-like
        // predictable items
        System.out.println("Predictable Items:");
        System.out.println("\tName\t\tQty\t\tBar\t\t\t\tDesired Qty");
        for(Item i : predictableItems) {
            System.out.print("\t");
            System.out.print(i.getName());
            System.out.print("\t\t");
            System.out.print(i.getQuantity() + " " + i.getUnit());
            System.out.print("\t\t");
            System.out.print(AsciiBarBuilder.buildInventoryBar(BAR_LEN, FILL_CHAR, EMPTY_CHAR, CRITICAL_CHAR, 
                                                               0 /* min */, 
                                                               i.getDesiredQty().doubleValue() /* max */, 
                                                               i.getQuantity().doubleValue() /* value */, 
                                                               -1 /* criticalValue (don't want it)*/));
            System.out.println(" " + i.getDesiredQty() + " " + i.getUnit());
        }
        
        System.out.println();
        
        System.out.println("Other Items:");
        System.out.println("\tName\t\tQty\t\tBar\t\t\t\tDesired Qty");
        for(Item i : otherItems) {
            System.out.print("\t");
            System.out.print(i.getName());
            System.out.print("\t\t");
            System.out.print(i.getQuantity() + " " + i.getUnit());
            System.out.print("\t\t");
            System.out.print(AsciiBarBuilder.buildInventoryBar(BAR_LEN, FILL_CHAR, EMPTY_CHAR, CRITICAL_CHAR, 
                                                               0 /* min */, 
                                                               i.getDesiredQty().doubleValue() /* max */, 
                                                               i.getQuantity().doubleValue() /* value */, 
                                                               i.getMinQuantity().doubleValue() /* criticalValue */));
            System.out.println(" " + i.getDesiredQty() + " " + i.getUnit());
            System.out.println("\t\t      ! " + i.getMinQuantity() + " " + i.getUnit());
        }
        
        return ExitCode.SUCCESS;
    }

}