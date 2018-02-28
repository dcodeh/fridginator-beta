package console;

import appl.Fridge;
import console.Command.ExitCode;
import fridginator.Constants;
import fridginator.PasswordHelper;
import model.Item;
import model.User;

/**
 * Closes stdin and ends the session
 * @author dcodeh
 *
 */
public class PasswdCommand extends Command {

    private static final String help = "Change a user's password";
    public static final String keyword = "passwd";
    private static final String[] requiredArguments = {"user", "password"}; // takes no arguments
    
    public PasswdCommand() {
        super(help, keyword, requiredArguments);
    }

    @Override
    public ExitCode doAction(String[] args, Fridge fridge) {
        
        User u = fridge.getUser(args[0]);
        String password = args[1];
        
        if(u == null) {
            System.out.println("Unkown user!");
            return ExitCode.FAILURE;
        }
        
        if(!PasswordHelper.validPassword(password)) {
            return ExitCode.FAILURE;
        }
        
        u.setPassword(password);
        
        System.out.println(u.getUsername() + "'s new password is " + u.getPassword());
        System.out.println("Keep that in a safe place!");
        
        return ExitCode.SUCCESS;
    }

}
