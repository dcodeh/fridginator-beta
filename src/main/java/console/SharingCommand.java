package console;

import java.util.HashMap;

import appl.Fridge;
import model.Item;
import model.User;

/**
 * See which users are sharing an item
 * @author dcodeh
 *
 */
public class SharingCommand extends Command {

    private static final String help = "See which users are sharing an item";
    public static final String keyword = "sharing";
    private static final String[] requiredArguments = {"item_name"}; // takes no arguments
    
    public SharingCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        
        String itemName = args[0];
        Item item = fridge.getItemByName(itemName);
        
        if(item == null) {
            // TODO dcodeh unify this in Constants.java for future errors
            System.out.println("Unknown Item!");
            return ExitCode.FAILURE;
        }
        
        HashMap<User, Number> usersSharing = item.getUsersSharingItem();
        
        if(usersSharing != null) {
            System.out.println("Users sharing " + item.getName() + ":");
            System.out.println("Username\t\tAmount (" + item.getUnit() + ")");
            for(User u : usersSharing.keySet()) {
                System.out.println(u.getUsername() + "\t\t\t" + usersSharing.get(u));
            }
            System.out.println("------------- \n" + 
                    "End of Report");
        } else {
            System.out.println("No users are sharing this item.");
        }
        
        return ExitCode.SUCCESS; // that wasn't so hard!
    }

}
