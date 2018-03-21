package console;

import appl.Fridge;
import model.Item;
import model.User;

/**
 * Share an item with a user.
 * @author dcodeh
 *
 */
public class ShareItemCommand extends Command {

    private static final String help = "Share an item with a user";
    public static final String keyword = "share";
    private static final String[] requiredArguments = {"item_name", "user"};
    
    public ShareItemCommand() {
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
        
        return ExitCode.SUCCESS;
    }

}
