/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package appl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import console.BlankCommand;
import console.Command;
import console.HelpCommand;
import console.InventoryCommand;
import console.ItemCommand;
import console.ItemDeleteCommand;
import console.ItemsCommand;
import console.ListCommand;
import console.ListGenCommand;
import console.PasswdCommand;
import console.QtyCommand;
import console.ShareItemCommand;
import console.SharedCommand;
import console.SharingCommand;
import console.UnshareItemCommand;
import console.UsageCommand;
import console.Command.ExitCode;
import console.ExitCommand;
import console.UserAddCommand;
import console.UserRemoveCommand;
import console.UserReportCommand;
import model.Item;
import model.PurchasableQuantity;
import model.User;

/**
 * Stores and tracks all of the shared and unshared items.
 * 
 * @author dcodeh
 */
public class Fridge implements java.io.Serializable {

    // huh it's empty

    /*
     * Used to determine if the serialized objects are compatible at deserialization
     * time. This should be changed any time serialized fields are changed!
     * 
     * History: Version Value Notes 0.1 1L Initial version
     */
    private static final long serialVersionUID = 1L;

    private void readObject(ObjectInputStream in) {
        try {
            in.defaultReadObject();
        } catch (ClassNotFoundException e) {
            System.out.println("This stream tastes like garbage...couldnt find a class");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("The filesystem is bullying me");
            e.printStackTrace();
        }
        initCommands();
    }

    /**
     * Holds all of the items shared between users
     */
    private HashSet<Item> fridge;

    /**
     * Holds all of the participating users
     */
    private HashMap<String, User> users;

    /**
     * Holds all of the acceptable console commands, for easy extensibility :w dang
     * it eclipse! This should not be stored when the fridge gets serialized
     */
    private transient HashMap<String, Command> commands;

    public Fridge() {
        fridge = new HashSet<Item>();
        users = new HashMap<String, User>();

        initCommands();
    }

    public void addItem(Item i) {
        fridge.add(i);
    }

    public void removeItem(Item i) {
        fridge.remove(i);
    }

    public void addUser(User u) {
        users.put(u.getUsername(), u);
    }

    public void removeUser(String username) {
        users.remove(username);
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    /**
     * Sees if the garbage the user entered into the console is intelligible. If it
     * is, then do the thing.
     * 
     * Otherwise, spit in their face.
     * 
     * @param commandLine
     *            The full line from stdin
     */
    public ExitCode interpretCommands(String commandLine) {

        // figure out what the user is talking about
        String[] arguments = commandLine.split(" ");
        String keyword = arguments[0];

        Command command = commands.get(keyword);

        // if it doesn't make sense, kick 'em out
        if (command == null) {
            System.out.println("Unkown command!");
            return ExitCode.FAILURE;
        }

        // if wrong number of arguments, kick 'em out
        if (arguments.length != command.getNumArguments()) {
            System.out.println("Wrong number of arguments!");
            System.out.println(command.getHelp(true /* showUsage */));
            return ExitCode.FAILURE;
        }

        // found a matching command, and number of arguments, so let them in to the
        // party
        String[] passedArgs = new String[command.getNumArguments() - 1];
        for (int i = 1; i < arguments.length; ++i) {
            passedArgs[i - 1] = arguments[i];
        }

        return command.doAction(passedArgs, this);
    }

    /**
     * Sets up all of the commands to be interpreted in this session.
     */
    private void initCommands() {
        commands = new HashMap<String, Command>();

        // sets up all of the commands accepted by the admin terminal
        commands.put(UserAddCommand.keyword, new UserAddCommand());
        commands.put(UserRemoveCommand.keyword, new UserRemoveCommand());
        commands.put(HelpCommand.keyword, new HelpCommand());
        commands.put(ExitCommand.keyword, new ExitCommand());
        commands.put(PasswdCommand.keyword, new PasswdCommand());
        commands.put(UserReportCommand.keyword, new UserReportCommand());
        commands.put(SharedCommand.keyword, new SharedCommand());
        commands.put(ItemCommand.keyword, new ItemCommand());
        commands.put(ItemDeleteCommand.keyword, new ItemDeleteCommand());
        commands.put(BlankCommand.keyword, new BlankCommand());
        commands.put(SharingCommand.keyword, new SharingCommand());
        commands.put(ShareItemCommand.keyword, new ShareItemCommand());
        commands.put(UnshareItemCommand.keyword, new UnshareItemCommand());
        commands.put(ItemsCommand.keyword, new ItemsCommand());
        commands.put(InventoryCommand.keyword, new InventoryCommand());
        commands.put(QtyCommand.keyword, new QtyCommand());
        commands.put(UsageCommand.keyword, new UsageCommand());
        commands.put(ListGenCommand.keyword, new ListGenCommand());
        commands.put(ListCommand.keyword, new ListCommand());
    }

    public Collection<Command> getCommands() {
        return commands.values();
    }

    public HashSet<Item> getItems() {
        return fridge;
    }

    /**
     * Find an Item in the fridge by its name. Note: This method will shamelessly
     * return null if it's not found.
     * 
     * TODO dcodeh consider adding another hashmap for itemname to item for faster
     * lookup...although RAM may not be plentiful
     * 
     * @param itemName
     *            the name of the Item to search for
     * @return The Item if found, null otherwise
     */
    public Item getItemByName(String itemName) {
        // we gotta search...UGH

        for (Item i : fridge) {
            if (i.getName().equals(itemName)) {
                // just get outta here!
                return i;
            }
        }

        // welp, didn't find it
        return null;
    }

    /**
     * This method automatically adds shared items to users' lists. It will try to
     * get the best deal for all of the users involved, and will balance the cost
     * the best it can.
     * 
     * TODO dcodeh consider backtracking in the future
     * 
     * Pseudocode: - First, check through all of the items in the fridge to populate
     * the global shopping list. - For each predictable item, calculate the amount
     * we need, and add it to the list. - For each unpredictable item, if we have
     * less than or equal to the critical quantity, calculate the amount we need to
     * get back to the desired amount, and add it to the list.
     * 
     * - Now, go through each item we need on the global list, and assign a quantity
     * to a user - Determine a Quantity to use - if the amount we need is equal to a
     * purchasable quantity, use that quantity - otherwise, while the quantity
     * needed is greater than 0 - start with the biggest quantity (best deal), back
     * to top - if we went over the desired quantity by less than the smallest
     * purchasable quantity, use it - otherwise, try again with a smaller one
     * 
     * - When assigning an item to a user, assign it to a user who shares the item
     * whose list cost is the lowest
     * 
     */
    public void generateSharedLists() {

        // determine the items we need
        HashMap<Item, Number> itemsNeeded = new HashMap<Item, Number>();
        for (Item item : this.getItems()) {

            if (item.getIsPredictable()) {
                // determine the amount we need
                double difference = item.getDesiredQty().doubleValue() - item.getQuantity().doubleValue();

                // only add it to the list if it's half of the smallest PQ
                PurchasableQuantity smallestPQ = item.getSmallestPurchasableQuantity();
                if (difference >= smallestPQ.getAmount().doubleValue() / 2) {
                    // it's worth it
                    itemsNeeded.put(item, difference);
                }

            } else {

                // only determine if we need this item if it's at or below the critical amount
                if (item.getQuantity().doubleValue() <= item.getMinQuantity().doubleValue()) {
                    double difference = item.getDesiredQty().doubleValue() - item.getQuantity().doubleValue();

                    itemsNeeded.put(item, difference);

                }

            }

        }

        // Start assigning items to users
        for (Item item : itemsNeeded.keySet()) {

            Number quantityNeededNumber = itemsNeeded.get(item);
            double quantityNeeded = quantityNeededNumber.doubleValue();

            boolean keepLooping = true;
            while (keepLooping) {
                PurchasableQuantity quantityToUse = null;

                // first see if there's an exact match for the quantity we need
                for (PurchasableQuantity pq : item.getPurchasableQuantities()) {
                    if (pq.getAmount().doubleValue() == quantityNeeded) {
                        quantityToUse = pq;
                        break;
                    } else if (pq.getAmount().doubleValue() > quantityNeeded) {
                        // already passed it,
                        break;
                    }
                }

                // no exact match, try some others
                if (quantityToUse == null) {
                    // TODO dcodeh make sure this is starting with the larest pq
                    for(PurchasableQuantity pq : item.getPurchasableQuantities()) {
                        double difference = quantityNeeded - pq.getAmount().doubleValue();
                        if(difference <= 0) {
                            // we chose a pq that goes over. This is OK as long as we went over by less than the smallest PQ
                            difference = Math.abs(difference);
                            if(difference < item.getSmallestPurchasableQuantity().getAmount().doubleValue()) {
                                // TODO dcodeh watch out for falling nulls...hard hats are required
                                quantityToUse = pq;
                            }
                        } else {
                            // this is probably the largest PQ, and we didn't get too much, so 
                            quantityToUse = pq;
                        }
                    }
                }
                
                if(quantityToUse == null ) {
                    // if we still couldn't figure it out, just pick the smallest one
                    quantityToUse = item.getSmallestPurchasableQuantity();
                }

                // assign it to someone
                HashMap<User, Number> usersSharingItem = item.getUsersSharingItem();
                if (usersSharingItem == null || usersSharingItem.size() == 0) {
                    System.out.println("Nobody is sharing " + item.getName() + ", and no one can buy it!");
                    break; // don't waste any more time
                }

                // find the person sharing the item who is committed to paying the least so far
                User victim = null;
                for (User u : usersSharingItem.keySet()) {
                    if (victim == null) {
                        victim = u;
                    } else {
                        if (u.getShoppingListSharedCost() < victim.getShoppingListSharedCost()) {
                            victim = u;
                        }
                    }
                }

                victim.assignSharedItem(item, quantityToUse);
                System.out.println(victim.getUsername() + " is responsible for buying " + quantityToUse.getAmount()
                        + quantityToUse.getUnit() + " of " + item.getName());
                
                quantityNeeded -= quantityToUse.getAmount().doubleValue();
                keepLooping = quantityNeeded > 0;
                
            }
        }

    }
    
    public void clearUserSharedLists() {
        for(User u : users.values()) {
            u.clearSharedList();
        }
    }

}
