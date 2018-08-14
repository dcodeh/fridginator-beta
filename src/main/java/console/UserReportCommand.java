package console;

import java.util.Iterator;

import appl.Fridge;
import model.User;

/**
 *  Use this command to see how much money each user has spent/saved
 * @author dcodeh
 *
 */
public class UserReportCommand extends Command {

    private static final String help = "See how much money users have spent/saved";
    public static final String keyword = "report";
    private static final String[] requiredArguments = {}; // no arguments
    
    public UserReportCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        
        Iterator<User> userIterator = fridge.getAllUsers().iterator();
        double totalSaved = 0.0;
        double totalSpent = 0.0;
        
        while(userIterator.hasNext()) {
            User u = userIterator.next();
            
            System.out.println("\n## " + u.getUsername() + " ##"); // how pretty
            
            System.out.printf("%d shared items\n", u.getNumSharedItems());
            System.out.printf("Spent: $%.2f\n", u.getMoneySpent());
            System.out.printf("Saved: $%.2f\n", u.getMoneySaved());
            
            totalSaved += u.getMoneySaved();
            totalSpent += u.getMoneySpent();
            
        }
        System.out.println("-------------");
        System.out.printf("Total Spent: $%.2f\n"
                        + "TotalSaved: $%.2f\n", totalSpent, totalSaved);
        System.out.println("------------- \n" + 
                           "End of Report");
        
        return ExitCode.SUCCESS; // everything is working!
    }

}
