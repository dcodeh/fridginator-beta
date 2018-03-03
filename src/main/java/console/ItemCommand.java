package console;

import java.util.Scanner;

import appl.Fridge;
import model.Item;

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
        
        if(isPredictable) {
            desiredAmount = getDesiredAmount(isNew, unit, item);
        } else {
            // TODO unpredictable items!
        }
        
        // give birth to the new items down here (only if isNew)
        
        cleanup();
        
        return ExitCode.SUCCESS; // everything is working!
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
                return response;
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
                System.out.println("Invalid double value!");
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
            switch(getYNResponse("Predictable Usage? (y/n)", false /* allowEmpty */)) {
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

