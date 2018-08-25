/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package console;

import java.util.Map;

import appl.Fridge;
import model.Item;
import model.PurchasableQuantity;
import model.ShoppingList;
import model.ShoppingListItem;
import model.User;

/**
 * Update inventory with all of the checked off shared items in users' lists.
 * @author dcodeh
 */
public class RestockCommand extends Command {

    private static final String help = "Update inventory for shared items that were checked off";
    public static final String keyword = "restock";
    private static final String[] requiredArguments = {}; // takes no arguments
    
    public RestockCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        
        System.out.println("Restocking shared items...");
        
        for(User u : fridge.getAllUsers()) {
            System.out.println("Restocking " + u.getUsername() + "'s items");
            ShoppingList list = u.getShoppingList();
            Map<Item, ShoppingListItem> sharedList = list.getSharedList();
            for(Item i : sharedList.keySet()) {
                ShoppingListItem listItem = sharedList.get(i);
                
                if(listItem.getIsCheckedOff()) {
                    PurchasableQuantity pq = listItem.getPurchasableQuantity();
                    i.incrementQty(pq.getAmount());
                    u.spendMoney(pq.getPrice());
                    
                    for(User otherUser:fridge.getAllUsers()) {
                        if(!otherUser.equals(u)) {
                            otherUser.saveMoney(pq.getPrice());
                        }
                    }
                } else {
                    System.out.println(u.getUsername() + " didn't buy " + i.getName());
                }
            }
            
            u.clearSharedList();
        }
        
        return ExitCode.SUCCESS;
    }

}
