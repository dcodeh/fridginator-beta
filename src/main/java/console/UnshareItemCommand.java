package console;

import java.util.Scanner;

import appl.Fridge;
import model.Item;
import model.User;

/**
 * Cut someone off from the supply of some item.
 * @author dcodeh
 *
 */
public class UnshareItemCommand extends Command {

    private static final String help = "Cut someone off from an item...I refuse to be an enabler.";
    public static final String keyword = "unshare";
    private static final String[] requiredArguments = {"item_name", "user"};
    
    public UnshareItemCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
                
        String itemName = args[0];
        String username = args[1];
        
        Item item = fridge.getItemByName(itemName);
        
        if(item == null) {
            System.out.println("What dat? (unkown item)");
            return ExitCode.FAILURE;
        }
        
        User user = fridge.getUser(username);
        
        if(user == null) {
            System.out.println("Who dat? (unkown user)");
            return ExitCode.FAILURE;
        }
        
        // see if the user already shares this item
        boolean alreadyShared = item.isUserSharingItem(user);
        
        if(alreadyShared) {
            item.unshareItemWithUser(user);
            user.unshareItem(item);
            // TODO dcodeh reacalculate!
        } else {
            System.out.println(user.getUsername() + " is not sharing " + item.getName());
        }
        
        return ExitCode.SUCCESS;
    }

}
