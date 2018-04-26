package console;

import java.util.HashSet;

import appl.Fridge;
import model.Item;

/**
 * See the actual versus expected usages for each item.
 * @author dcodeh
 *
 */
public class UsageCommand extends Command {

    private static final String help = "See actual vs expected usage for each item.";
    public static final String keyword = "usage";
    private static final String[] requiredArguments = {}; // takes no arguments
    
    public UsageCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        
        HashSet<Item> items = fridge.getItems();
        
        if(items == null) {
            System.out.println("Nothing in the fridge.");
            return ExitCode.SUCCESS;
        }
        
        for(Item i : items) {
            System.out.printf("== %s ==\n", i.getName());
            Number actual = i.getWeeklyUsage();
            Number expected = i.getExpWeeklyUsage();
            
            if(!expected.equals(0)) {
                System.out.println("E: " + expected + " " + i.getUnit() + "/week");
            }
            
            if(!actual.equals(0)) {
                System.out.println("A: " + actual + " " + i.getUnit() + "/week");
            }
            
            System.out.println(); // newline, yeah!
            
        }
        
        return ExitCode.SUCCESS; 
    }

}
