/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package console;

import appl.Fridge;
import model.Item;
import model.User;

/**
 *  See all of the items and amounts each user has shared.
 * @author dcodeh
 *
 */
public class SharedCommand extends Command {

    private static final String help = "See all of the items a user is sharing, and how much of each";
    public static final String keyword = "shared";
    private static final String[] requiredArguments = {"user"};   
    
    public SharedCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        
        String username = args[0];
        
        User user = fridge.getUser(username);
        
        if(user == null) {
            System.out.println("Unkown user!");
            return ExitCode.FAILURE;
        }
        
        System.out.println(username + " is sharing the following items:");
        
        // commence the report!
        for(Item i : user.getSharedItems()) {
            
            if(i.getIsWhole()) {
                // use integer format
                System.out.printf("%s: \t\t\t%d %s", i.getName(), i.getUserExpUsage(user), i.getUnit());
            } else {
                // use float format with 2 decimals
                System.out.printf("%s: \t\t%.2f %s", i.getName(), i.getUserExpUsage(user), i.getUnit());
            }
            
            System.out.println();
        }
        
        System.out.println("------------- \n" + 
                           "End of Report");
        
        return ExitCode.SUCCESS; // everything is working!
    }

}

