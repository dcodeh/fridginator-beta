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
    
    public InventoryCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        
        HashSet<Item> itemsInFridge = fridge.getItems();
        
        if(itemsInFridge == null) {
            System.out.println("No Items to inventory.");
            return ExitCode.SUCCESS; // just go home early.
        }

        Iterator<Item> itemsIterator = itemsInFridge.iterator();
        
        while(itemsIterator.hasNext()) {
            Item item = itemsIterator.next();
            
        }
        
        return ExitCode.SUCCESS;
    }

}
