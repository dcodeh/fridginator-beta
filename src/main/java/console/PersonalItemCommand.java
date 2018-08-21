/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package console;

import appl.Fridge;
import model.User;

/**
 * Add or remove a personal item from a list
 * @author dcodeh
 *
 */
public class PersonalItemCommand extends Command {

    private static final String help = "add or remove personal items to/from a user's list";
    public static final String keyword = "personalitem";
    private static final String[] requiredArguments = {"user", "action", "line"};
    
    public PersonalItemCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        
        String username = args[0];
        User u = fridge.getUser(username);
        
        if(u == null) {
            System.out.println("Unknown user");
            return ExitCode.FAILURE;
        }
                
        String action = args[1];
        if(action.equals("add")) {
            u.addPersonalItem(args[2]);
            System.out.println("Added '" + args[2] + "' to " + u.getUsername() + "'s personal list");
        } else if(action.equals("remove")) {
            u.removePersonalItem(args[2]);
            System.out.println("Removed '" + args[2] + "' from " + u.getUsername() + "'s personal list");
        } else {
            System.out.println("Unknown action!");
            return ExitCode.FAILURE;
        }
        
        return ExitCode.SUCCESS;
    }

}
