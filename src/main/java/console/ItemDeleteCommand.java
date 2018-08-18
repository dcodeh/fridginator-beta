/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package console;

import java.util.ArrayList;
import java.util.HashMap;

import appl.Fridge;
import model.Item;
import model.User;

/**
 * Remove an item from the fridge, after unsharing it and making sure it's safe to do so!
 * @author dcodeh
 *
 */
public class ItemDeleteCommand extends Command {

    private static final String help = "Delete an item from the fridge";
    public static final String keyword = "itemdel";
    private static final String[] requiredArguments = {"item_name"};
    
    public ItemDeleteCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        
        String itemName = args[0];
        Item item = fridge.getItemByName(itemName);

        if(item == null) {
            System.out.println("Unknown Item!");
            return ExitCode.FAILURE;
        }
        
        fridge.removeItem(item);
        
        HashMap<User, Number> usersSharingItem = item.getUsersSharingItem();
        ArrayList<User> usersToRemoveFromItem = new ArrayList<User>();
        
        if(usersSharingItem != null) {
            for(User u : usersSharingItem.keySet()) {
                System.out.println(u.getUsername() + " unshared " + item.getName());
                u.unshareItem(item);
                usersToRemoveFromItem.add(u);
            }
            
            // remove users from the item separately to avoid concurrent modification of usersSharingItem
            for(User u : usersToRemoveFromItem) {
                item.unshareItemWithUser(u);
            }
            
        } else {
            System.out.println("No users to unshare item.");
        }

        System.out.println("Goodbye, " + item.getName());
        item = null;
        
        return ExitCode.SUCCESS;
    }

}
