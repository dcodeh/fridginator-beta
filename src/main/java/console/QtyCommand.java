package console;

import appl.Fridge;
import fridginator.AsciiBarBuilder;
import model.Item;

/**
 * This command adjusts the quantity of an item in the fridge
 * @author dcodeh
 */
public class QtyCommand extends Command {

    private static final String help = "Adjust the quantity of an item in the fridge";
    public static final String keyword = "qty";
    private static final String[] requiredArguments = {"item", "quantity"};
    
    // bar formatting options
    private final static int BAR_LEN = 30;
    private final static char FILL_CHAR = '=';
    private final static char EMPTY_CHAR = '_';
    private final static char CRITICAL_CHAR = '!';
    
    public QtyCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        
        String itemArg = args[0];
        String quantityArg = args[1];
        
        Item item = fridge.getItemByName(itemArg);
        
        if(item == null) {
            System.out.println("Unknown Item!");
            return ExitCode.SUCCESS;
        }
        
        Number newQuantity;
        if(item.getIsWhole()) {
            try {
                newQuantity = Integer.valueOf(quantityArg);
            } catch(NumberFormatException nfe) {
                System.out.println("That's not an Integer. Better luck next time!");
                return ExitCode.FAILURE;
            }
            
            item.setQuantity(newQuantity.intValue());
            
        } else {
            try {
                newQuantity = Double.valueOf(quantityArg);
            } catch(NumberFormatException nfe) {
                System.out.println("That's not a Double. Better luck next time!");
                return ExitCode.FAILURE;
            }
            
            item.setQuantity(newQuantity.doubleValue());
            
        }
        
        System.out.println("Updated quantity.");
        
        if(item.getIsPredictable()) {
            System.out.print("\t");
            System.out.print(item.getName());
            System.out.print("\t\t");
            System.out.print(item.getQuantity() + " " + item.getUnit());
            System.out.print("\t\t");
            System.out.print(AsciiBarBuilder.buildInventoryBar(BAR_LEN, FILL_CHAR, EMPTY_CHAR, CRITICAL_CHAR, 
                                                               0 /* min */, 
                                                               item.getDesiredQty().doubleValue() /* max */, 
                                                               item.getQuantity().doubleValue() /* value */, 
                                                               -1 /* criticalValue (don't want it)*/));
            System.out.println(" " + item.getDesiredQty() + " " + item.getUnit());
        } else {
            System.out.print("\t");
            System.out.print(item.getName());
            System.out.print("\t\t");
            System.out.print(item.getQuantity() + " " + item.getUnit());
            System.out.print("\t\t");
            System.out.print(AsciiBarBuilder.buildInventoryBar(BAR_LEN, FILL_CHAR, EMPTY_CHAR, CRITICAL_CHAR, 
                                                               0 /* min */, 
                                                               item.getDesiredQty().doubleValue() /* max */, 
                                                               item.getQuantity().doubleValue() /* value */, 
                                                               item.getMinQuantity().doubleValue() /* criticalValue */));
            System.out.println(" " + item.getDesiredQty() + " " + item.getUnit());
            System.out.println("\t\t      ! " + item.getMinQuantity() + " " + item.getUnit());
        }
        
        return ExitCode.SUCCESS;
    }

}
