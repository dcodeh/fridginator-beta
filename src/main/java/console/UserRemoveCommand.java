/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package console;

import appl.Fridge;
import model.Item;
import model.User;

/**
 * This command will kill a user!
 * @author dcodeh
 *
 */
public class UserRemoveCommand extends Command {

    private static final String help = "Banish a certain user from using Fridginator!";
    public static final String keyword = "userdel";
    private static final String[] requiredArguments = {"username"};
    
    public UserRemoveCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        
        User u = fridge.getUser(args[0]);
        
        if(u == null) {
            System.out.println("Unkown user!");
            return ExitCode.FAILURE;
        }
        
        for(Item i : fridge.getItems()) {
            if(i.isUserSharingItem(u)) {
                i.unshareItemWithUser(u);
            }
        }
        
        fridge.removeUser(args[0]);
        
        System.out.println("Done.");
        return ExitCode.SUCCESS; // everything is broken!
    }

}
