/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package console;

import java.util.HashMap;

import appl.Fridge;
import model.Item;
import model.ShoppingList;
import model.ShoppingListItem;
import model.User;

/**
 * This displays the list for a certain user
 * 
 * @author dcodeh
 */
public class ListCommand extends Command {

    private static final String help = "Display the personal and shared items for a user.";
    public static final String keyword = "list";
    private static final String[] requiredArguments = { "user" };

    public ListCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {

        String username = args[0];
        User user = fridge.getUser(username);

        // go home early
        if (user == null) {
            System.out.println("Could not locate that user!");
            return ExitCode.FAILURE;
        }

        System.out.println(user.getUsername() + "'s personal list items:");
        ShoppingList list = user.getShoppingList();
        HashMap<String, Boolean> personalList = list.getPersonalList();
        for (String item : personalList.keySet()) {
            if (personalList.get(item)) {
                System.out.println("X " + item);
            } else {
                System.out.println("_ " + item);
            }
        }
        
        System.out.println();
        System.out.println(user.getUsername() + "'s shared list items:");
        HashMap<Item, ShoppingListItem> sharedList = list.getSharedList();
        for(Item item : sharedList.keySet()) {
            ShoppingListItem listItem = sharedList.get(item);
            
            if(listItem != null) {
                if(listItem.getIsCheckedOff()) {
                    System.out.println("X " + listItem.getPurchasableQuantity() + " " + item.getName());
                } else {
                    System.out.println("_ " + listItem.getPurchasableQuantity() + " " + item.getName());
                }
            }
        }
        System.out.println();

        return ExitCode.SUCCESS;
    }

}