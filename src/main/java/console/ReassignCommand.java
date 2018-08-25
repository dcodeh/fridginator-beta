/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package console;

import java.util.Map;

import appl.Fridge;
import model.Item;
import model.ShoppingList;
import model.ShoppingListItem;
import model.User;

/**
 * Reassign a shared item from one user to another.
 * @author dcodeh
 */
public class ReassignCommand extends Command {

    private static final String help = "Reassign a shared item from one user to another";
    public static final String keyword = "reassign";
    private static final String[] requiredArguments = {"originalUser", "item", "newUser"};
    
    public ReassignCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        
        User originalUser = fridge.getUser(args[0]);
        Item sharedItem = fridge.getItemByName(args[1]);
        User newUser = fridge.getUser(args[2]);
        
        if(originalUser == null) {
            System.out.println("Unknown original user!");
            return ExitCode.FAILURE;
        }
        
        if(sharedItem == null) {
            System.out.println("Unknown item!");
            return ExitCode.FAILURE;
        }
        
        if(newUser == null) {
            System.out.println("Unknown new user!");
            return ExitCode.FAILURE;
        }
        
        ShoppingList originalList = originalUser.getShoppingList();
        Map<Item, ShoppingListItem> originalSharedList = originalList.getSharedList();
        
        if(originalSharedList.get(sharedItem) == null) {
            System.out.println(originalUser.getUsername() + " hasn't been assigned " + sharedItem.getName());
            return ExitCode.FAILURE;
        } else {
            System.out.println(sharedItem.getName() + " was removed from " + originalUser.getUsername() + "'s list, "
                                                    + "and was added to " + newUser.getUsername() + "'s list.");
            ShoppingListItem assignment = originalSharedList.remove(sharedItem);
            newUser.assignSharedItem(sharedItem, assignment.getPurchasableQuantity());
        }
        
        return ExitCode.SUCCESS;
    }

}