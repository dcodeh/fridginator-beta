package console;

import java.util.HashMap;

import appl.Fridge;
import model.Item;
import model.User;

/**
 * List all of the items in the fridge, and who's sharing them
 * @author dcodeh
 *
 */
public class ItemsCommand extends Command {

    private static final String help = "List all of the items in the fridge, and the users sharing them";
    public static final String keyword = "items";
    private static final String[] requiredArguments = {}; // no arguments
    
    public ItemsCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        
        // TODO dcodeh finish this
        
        Item item = null; // this is def wrong, but it makes the errors go away.
        // TODO dcodeh add this to initCommands()
        
        HashMap<User, Number> usersSharingItem = item.getUsersSharingItem();
        
        if(usersSharingItem != null) {
            for(User u : usersSharingItem.keySet()) {
                System.out.println(u.getUsername() + " unshared " + item.getName());
                u.unshareItem(item);
            }
        } else {
            System.out.println("No users to unshare item.");
        }
        
        return ExitCode.SUCCESS;
    }

}
