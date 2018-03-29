package console;

import java.util.Scanner;

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
        
        @SuppressWarnings("resource") // this will be closed when the program exits
        Scanner stdin = new Scanner(System.in);
        
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
        
        boolean valid = false;
        double expUsage = 0.0;
        
        if(item.getIsPredictable()) {
            while(!valid) {
                
                if(alreadyShared) {
                    expUsage = item.getUserExpUsage(user).doubleValue();
                    System.out.println(user.getUsername() + " already shares " + item.getName() + "...leave empty for no change.");
                    System.out.print("Expected Usager per week (" + item.getUnit() + ") [" + expUsage + "]: ");
                } else {
                    System.out.print("Expected Usage per week (" + item.getUnit() + "): ");
                }
                
                String entered  = stdin.nextLine();
    
                if(entered.isEmpty()) {
                    if(alreadyShared) {
                        valid = true;
                        break;
                    } else {
                        System.out.println("You must enter a value.");
                    }
                } else {
                    try {
                        expUsage = Double.valueOf(entered);
                    } catch (NumberFormatException nfe) {
                        System.out.println("Good try, but that's not a number...");
                    }
                    
                    if(expUsage > 0.0) {
                        valid = true;
                    }
                }
                
            }
        }
        
        if(item.getIsWhole()) {
            item.setUserExpUsage(user, (int) expUsage);
        } else {
            item.setUserExpUsage(user, expUsage);
        }
        
        user.shareItem(item);
        
        System.out.println("Done.");
        
        return ExitCode.SUCCESS;
    }

}
