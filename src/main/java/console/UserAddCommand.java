/**
 * Copyright (c) 2018 David Cody Burrows
 * See LICENSE file for details
 */
package console;

import appl.Fridge;
import fridginator.PasswordHelper;
import model.User;

/**
 * This command adds a new user to the system
 * @author dcodeh
 *
 */
public class UserAddCommand extends Command {

    private static final String help = "Adds a new user to the friginator system. " +
                                       "Usernames and Passwords cannot contain spaces";
    public static final String keyword = "useradd";
    private static final String[] requiredArguments = {"username", "password"};
    
    public UserAddCommand() {
        super(help, keyword, requiredArguments);
    }

    /**
     * {@inheritDoc}
     * 
     * Usernames and Passwords must not have spaces, and must obey the
     * length criteria in Constants.java
     */
    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        
        String username, password;
        
        username = args[0];
        password = args[1];
        
        // rigorous validation...1 strike and you're out
        if(password.equals("password")) {
            System.out.println("That's the worst password you could possibly choose! Try again.");
            return ExitCode.FAILURE;
        } else if(password.contains("1234")){
            System.out.println("Really? 1234!?");
        }
        
        if(!PasswordHelper.validPassword(password)) {
            return ExitCode.FAILURE;
        }
        
        if(!PasswordHelper.validUsername(username)) {
            return ExitCode.FAILURE;
        }
           
        User newUser = new User(username, password);
        fridge.addUser(newUser);
        
        System.out.println("Done.");
        
        return ExitCode.SUCCESS; // everything is broken!
    }

}
