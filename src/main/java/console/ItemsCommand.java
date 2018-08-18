/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package console;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

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
        
        HashSet<Item> itemsInFridge = fridge.getItems();
        
        if(itemsInFridge == null) {
            System.out.println("No Items to report.");
            return ExitCode.SUCCESS;
        }
        
        Iterator<Item> itemsIterator = itemsInFridge.iterator();
        
        while(itemsIterator.hasNext()) {
            Item item = itemsIterator.next();
            
            System.out.println(item.getName() + " shared by:");
            HashMap<User, Number> usersSharingItem = item.getUsersSharingItem();
            
            if(usersSharingItem != null) {
                for(User u : usersSharingItem.keySet()) {
                    System.out.println("\t" + u.getUsername() + "\t" + usersSharingItem.get(u) + " " + item.getUnit());
                }
            } else {
                System.out.println("(nobody)");
            }
            
            // newline because yeah
            System.out.println();
        }
        
        return ExitCode.SUCCESS;
    }

}
