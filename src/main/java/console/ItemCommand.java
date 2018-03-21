package console;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import appl.Fridge;
import model.FractionalItem;
import model.Item;
import model.PurchasableQuantity;
import model.WholeItem;

/**
 * Create or modify an item in the fridge
 * @author dcodeh
 *
 */
public class ItemCommand extends Command {

    private static final String help = "Create or modify an Item in the Fridge!";
    public static final String keyword = "item";
    private static final String[] requiredArguments = {"item_name"};  
    
    private Scanner stdin;
    
    public ItemCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        
        stdin = new Scanner(System.in);
        
        String itemName = args[0];
        Item item = fridge.getItemByName(itemName);
        boolean isNew = (item == null);
        
        // each method call is a step in the item entry wizard
        // the return values of these really only matter if it's a new item
        insertOrUpdate(isNew); // prints a message
        boolean isWholeItem = getIsWholeItemYN(isNew, item); // asks user if item is whole or fractional
        String unit = getUnit(isNew, item); // gets user entered unit string
        boolean isPredictable = getIsPredictableYN(isNew, item); // ask the user if the item is predictable
        
        double desiredAmount = 0.0;
        double minAmount = 0.0;
        
        desiredAmount = getDesiredAmount(isNew, unit, item);
        
        if(!isPredictable) {
            // TODO dcodeh make sure the user can't be stupid and make the min amount larger than the max amount!
            minAmount = getMinimumAmount(isNew, unit, item);
        }
        
        HashSet<PurchasableQuantity> purchasable = getPurchasableQuantities(isNew, item, unit, isWholeItem);
        
        // give birth to the new items down here (only if isNew)
        if(isNew) {
            Item newItem;
            
            if(isWholeItem) {
                newItem = new WholeItem(itemName, unit, 0, 0, (int) desiredAmount, 0, isPredictable, (int) minAmount);
                newItem.setPurchasableQuantities(purchasable);
            } else {
                newItem = new FractionalItem(itemName, unit, 0.0, 0.0, desiredAmount, 0.0, isPredictable, minAmount);
                newItem.setPurchasableQuantities(purchasable);
            }
            
            fridge.addItem(newItem);
        } else {
            // TODO handle updating items (may be easiest if done in input methods
        }
                
        return ExitCode.SUCCESS; // everything is working!
    }
    
    /**
     * Get the user's minimum desired amount for this item...Remember, this is the amount that triggers 
     * Fridginator to restock this item.
     * @param isNew Whether this is an insert or an update
     * @param unit The unit for this item
     * @param item The item to update (could be null if new)
     * @return The minimum amount that the user is comfortable with having
     */
    private double getMinimumAmount(boolean isNew, String unit, Item item) {
        double amount = 0.0;
        
        if(isNew) {
            String prompt = "Minimum Amount (" + unit + "): ";
            return getEnteredDouble(prompt, false /* allowEmpty */);
        } else {
            String prompt = "Minimum Amount (" + unit + ") [" + item.getMinQuantity() + "]: ";
            double response = getEnteredDouble(prompt, true /* allowEmpty */);
            
            if(response < 0) {
                // user accepted empty value
                amount = item.getMinQuantity().doubleValue();
            } else {
                amount = response;
                
                // update the item
                item.setMinQuantity(item.getIsWhole() ? (int) amount : amount); 
            }
        }
        
        return amount;
    }

    /**
     * Prompts user for the quantities that can be bought in a store. 
     * @param isNew Whether this is an item insertion or update
     * @param item The Item that is being updated (or null if not applicable)
     * @param unit The unit string for this item
     * @param isWhole Whether or not to truncate input values
     * @return The set of purchasable quantities
     */
    private HashSet<PurchasableQuantity> getPurchasableQuantities(boolean isNew, Item item, String unit, boolean isWhole) {
        HashSet<PurchasableQuantity> purchasable = new HashSet<PurchasableQuantity>();
                
        String entry;
        boolean validInput = false;
        ArrayList<Double> quantities = null;
        
        if(!isNew) {
            // display all of the old purchasable quantities
            System.out.println("\nCurrent stored quantities:");
            System.out.println("Quantity (" + item.getUnit() + ")\tPrice\t\tUnit Price");
            for(PurchasableQuantity pq : item.getPurchasableQuantities()) {
                
                if(item.getIsWhole()) {
                    System.out.printf("%d \t\t$%.2f \t\t$%.2f/%s\n", pq.getAmount().intValue(), pq.getPrice(), pq.getUnitPrice(), pq.getUnit());
                } else {
                    System.out.printf("%.2f \t\t$%.2f \t\t$%.2f/%s\n", pq.getAmount().doubleValue(), pq.getPrice(), pq.getUnitPrice(), pq.getUnit());
                }
            }
        }
        
        while(!validInput) {
            System.out.print("\nPurchasable Quantities (comma separated): ");
            entry = stdin.nextLine();
            
            if(entry.isEmpty()) {
                if(isNew) {
                    System.out.println("You must enter at least 1 purchasable quantity!");
                    // fail immediately
                } else {
                    // accepting previously entered purchasable quantities, nothing else is needed
                    return item.getPurchasableQuantities();
                }
            } else {
                // send off to validation
                quantities = processQuantityInput(entry);
                
                if(quantities != null) {
                    validInput = true;
                }
            }
        }
        
        // enter the prices
        for(Double d : quantities) {
            // get a price for each quantity
            boolean validPrice = false;
            double price = 0.0;
            while(!validPrice) {
                System.out.printf("Exp. Price <%.2f %s>: $", d, unit);
                
                String input = stdin.nextLine();
                
                if(!input.isEmpty()) {
                    try {
                        price = Double.valueOf(input);
                        validPrice = true;
                    } catch(NumberFormatException nfe) {
                        System.out.println("Good try, but " + input + " is not a number.");
                    }
                }
                
            }
            
            // we have all of the pieces to build a quantity now.
            PurchasableQuantity pq;
            if(isWhole) {
                pq = new PurchasableQuantity(unit, d.intValue(), price);
            } else {
                pq = new PurchasableQuantity(unit, d, price);
            }
            
            purchasable.add(pq);
            
        }
        
        return purchasable;
    }
    
    /**
     * Translates the garble the user enters into a nice ArrayList of doubles, if possible.
     * 
     * Note: Will return null if it gets unhappy, so watch yourself.
     *  
     * @param line The line the user entered, of comma separated purchasable quantities.
     * @return An ArrayList of the quantities the user entered. Using doubles for everything because we
     * can truncate them later if necessary.
     */
    private ArrayList<Double> processQuantityInput(String line) {
        ArrayList<Double> quantities = new ArrayList<Double>();
        
        String[] pieces = line.split(",");
        
        for(int i = 0; i < pieces.length; ++i) {
            try {
                quantities.add(Double.valueOf(pieces[i].trim()));
            } catch(NumberFormatException nfe) {
                System.out.println("Good try, but " + pieces[i] + " is not a number.");
                return null;
            }
        }
        
        return quantities;
    }

    /**
     * Get the desired amount to have in the fridge for this PREDICTABLE item
     * @param isNew Whether or not we're creating a new item
     * @param unit The unit string for this item
     * @param item The item to update if we're not creating a new one
     * @return The double value of the value the user enters...even if the item is Whole, the 
     * decimal can just be truncated later.
     */
    private double getDesiredAmount(boolean isNew, String unit, Item item) {
        
        double amount = 0.0;
        
        if(isNew) {
            String prompt = "Desired Amount (" + unit + "): ";
            return getEnteredDouble(prompt, false /* allowEmpty */);
        } else {
            String prompt = "Desired Amount (" + unit + ") [" + item.getDesiredQty() + "]: ";
            double response = getEnteredDouble(prompt, true /* allowEmpty */);
            
            if(response < 0) {
                // user accepted empty value
                amount = item.getDesiredQty().doubleValue();
            } else {
                amount = response;
                
                // update the item
                item.setDesiredQty(item.getIsWhole() ? (int) amount : amount); 
            }
        }
        
        return amount;
    }

    /**
     * Get a number entered from the console 
     * @param prompt The text to display before waiting for input
     * @param allowEmpty Is the user allowed to accept the default value?
     * @return The entered value (positive), or default (-1.0)
     */
    private double getEnteredDouble(String prompt, boolean allowEmpty) {
        
        boolean valid = false;
        double value = 0.0;
        
        while(!valid) {
            System.out.print(prompt);
            
            String entered = stdin.nextLine();
            
            if(allowEmpty) {
                if(entered.isEmpty()) {
                    return -1.0;
                }
            }
            
            try {
                value = Double.valueOf(entered);
                valid = true;
                // if we get past here we're good
            } catch(NumberFormatException nfe) {
                // gotcha! you didn't type a good number...try again
                System.out.println("Good try, but that's not a number");
                valid = false;
            }
        }
        
        return Math.abs(value);
    }
    
    /**
     * Ask the user if the item is predictable or not.
     * @param isNew Whether or not an item is being updated
     * @param item The item to reference if this is an update (could be null)
     * @return The user's response
     */
    private boolean getIsPredictableYN(boolean isNew, Item item) {
        
        if(isNew) {
            switch(getYNResponse("Predictable Usage? (y/n): ", false /* allowEmpty */)) {
                case 1:
                    return true;
                default:
                    return false; 
            }
        } else {
            return item.getIsPredictable();
        }
    }

    /**
     * Asks the user what the unit string for the item is
     * @param isNew If the item is being created for the first time
     * @param item The item object for use if this is an update...could be null!
     * @return The unit of the item
     */
    private String getUnit(boolean isNew, Item item) {
        
        String unit = "";
        
        if(isNew) {
            System.out.print("Unit: ");
            unit = stdin.nextLine();
            // TODO disallow empty unit??
        } else {
            System.out.printf("Unit [%s]: ", item.getUnit());
            unit = stdin.nextLine();
            
            if(unit.isEmpty()) {
                unit = item.getUnit();
            } else {
                item.setUnit(unit);
            }
        }
        
        return unit;
    }

    /**
     * Get the user's response to a yes/no question
     * @param prompt The text to display to the user before prompting
     * @param allowEmpty Allow the user to leave the prompt empty (accept default value)
     * @return 1 if yes/y/Y/1, 0 if no/n/N/0, -1 if empty
     */
    private int getYNResponse(String prompt, boolean allowEmpty) {
        boolean valid = false;
        int response = 0;
        
        while(!valid) {
            
            System.out.print(prompt);
            
            String s = stdin.nextLine();
            s.toLowerCase();
            
            if(s.equals("y") || s.equals("yes") || s.equals("1")) {
                response = 1;
                valid = true;
            } else if(s.equals("n") || s.equals("no") || s.equals("0")){
                response = 0;
                valid = true;
            } else if(allowEmpty) {
                if(s.isEmpty()) {
                    response = -1;
                    valid = true;
                }
            }
            
        }       
        
        return response;
        
    }
    
    /**
     * Ask the user if the item is whole or not. This can only be done on creation.
     * @param isNew Whether or not an item is being updated
     * @return The user's response
     */
    private boolean getIsWholeItemYN(boolean isNew, Item i) {
        if(isNew) {
            switch(getYNResponse("Whole Item? (y/n): ", false /* allowEmpty */)) {
                case 1:
                    return true;
                default:
                    return false;
            }
        } else {
            return i.getIsWhole();
        }
        
    }

    /**
     * Prints a message informing the user of the type of modification they are making.
     * @param isNew Prints new item message if true; prints existing item message if false.
     */
    private void insertOrUpdate(boolean isNew) {
        if(isNew) {
            System.out.println("##     Entering new Item     ##");
        } else {
            System.out.println("##   Editing existing Item   ##\n" + 
                               "## leave blank for no change ##");
        }
    }
    
    /**
     * Be a good citizen. Always close your resources when you're done with them.
     */
    private void cleanup() {
        if(stdin != null) {
            stdin.close();
        }
    }

}

